package deliverymgnt.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import deliverymgnt.domainclasses.Address;
import deliverymgnt.domainclasses.CourierService;
import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryMethod;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Locker;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.domainclasses.Package;
import deliverymgnt.domainclasses.PackageSize;
import deliverymgnt.domainclasses.Warehouse;
import deliverymgnt.services.impl.GeoComputingServiceImpl;

public class DeliveryBusinessLogic {
	
	// ***********************************************
	// ** Rules to generate Deliveries and Packages **
	// ***********************************************
	// Maximum weight of a delivery: 30 lbs
	// Maximum weight of a package: 20 lbs (9.07 kg)
	// Small: <= 5 lbs (< 2.26 kg)
	// Medium: 5 -> 13 (2.26 kg - 5.8 kg) 
	// Large: >= 13 -> 20 (6.3 kg - 9 kg)
	
	// ***********************************************
	// ** Analysis mechanism **************************
	// ***********************************************
	// *** Delivery type
	// ****** Home         -> Drone | Courier
	// ****** Locker       ->         Courier
	// *** Distance
	// ****** <= 30 miles  -> Drone
	// ****** > 30 miles   ->       | Courier
	// *** Shipping Weight (the maximum between Actual Weight and Volumetric Weight)
	// ***** <= 5          -> Drone
	// ***** > 5           ->       | Courier
	
	public static void processOrder(Order order, 
			OrderService orderService,
			WarehouseService warehouseService,
			DeliveryService deliveryService) throws Exception {
		
		// Switch order to Processing
		order.setOrderStatus(OrderStatus.Processing);
		
		double distance = findWarehouse(order, warehouseService);
		
		// for maximum configuration 
		if (distance > 50.0) {
			distance = 50.0;
		}
		else if (distance < 1) {
			distance = 1;
		}
		
		DeliveryType type = order.getDeliveryType();
		double shippingWeight = order.calculateTotalShippingWeight();
		
		System.out.println(" >>>>> delivery type: " + type);
		System.out.println(" >>>>> distance: " + distance);
		System.out.println(" >>>>> shipping weight: " + shippingWeight);
		
		pack(order, type, shippingWeight, distance, deliveryService);
		
		orderService.save(order);
		System.out.println(" >>>>> Process order " + order.getId() +  " successfully!");		
	}
	
	private static double findWarehouse(Order order, WarehouseService warehouseService) {
		
		Address addr = order.getDeliveryAddress();
		String deliveryAddress = addr.toString();
		
		// Get all warehouses
		List<Warehouse> warehouses = warehouseService.findAll();
		
		// Filter those warehouses that can satisfy all items of order
		// ... (next release)
		
		// Find the nearest warehouse with delivery address
		GeoComputingService googleMapSvc = new GeoComputingServiceImpl();
		double nearestDist = Double.MAX_VALUE;
		Warehouse warehouse = null;
		for(Warehouse w : warehouses) {
			String warehouseAddress = w.getWarehouseAddress().toString();
			double dist = googleMapSvc.computeDistance(warehouseAddress, deliveryAddress);
			
			System.out.println(">>>>>");
			System.out.println(" Distance from warehouse " + w.getName() + "(" + warehouseAddress  + ")");
			System.out.println(" to delivery address (" + deliveryAddress  + ") is:");
			System.out.println("    " + dist + " miles");
			System.out.println(">>>>>");
			
			if (dist < nearestDist) {
				nearestDist = dist;
				warehouse = w;
			} 
		}
	
		if (warehouse != null) {
			System.out.println(">>>>> Selected warehouse: ");
			System.out.println(">>>>>   name: " + warehouse.getName());
			System.out.println(">>>>>   distance" + nearestDist);
			
			order.setWarehouse(warehouse);
			return nearestDist;
		} else {
			System.out.println(">>>>> NO WAREHOUSE");
		}
		
		return -1;
	}
	
	private static void pack(Order order, DeliveryType type, 
			double shippingWeight, double distance, 
			DeliveryService deliveryService) throws Exception { 
		
		order.setOrderStatus(OrderStatus.Packaging);
		
		List<Delivery> deliveries = new ArrayList<>();
		
		// Case 1: All by drone
		// 1 delivery for ALL packages
		if (type == DeliveryType.HomeDelivery && distance <= 30 && shippingWeight <= 5) {
			// 1 SMALL delivery
			System.out.println("Case 1 entered ...");
			Delivery delivery = packForTheSameDeliveryMethod(order, DeliveryMethod.Drone, distance);
			deliveries.add(delivery);
		}

		// Case 2: all by courier
		// 1 delivery for ALL packages
		else if (type == DeliveryType.LockerPickupDelivery || distance > 30) {
			System.out.println("Case 2 entered ...");	
			Delivery delivery = packForTheSameDeliveryMethod(order, DeliveryMethod.Courier, distance);
			deliveries.add(delivery);
		}
		
		// Case 3: mix with drone and courier
		// 1 delivery for ALL packages
		else {
			System.out.println("Case 3 entered ...");
			List<OrderItem> sortedOrderItems = order.getSortedOrderItemsByShippingWeight();
			
			List<OrderItem> droneOrderItems = new ArrayList<>();
			List<OrderItem> courierOrderItems = new ArrayList<>();
			
			double weight = 0.0;
			for(OrderItem oi : sortedOrderItems) {
				if ((oi.calculateShippingWeight() + weight) <= 5) {
					droneOrderItems.add(oi);
					weight += oi.calculateShippingWeight();
				}
				else {
					courierOrderItems.add(oi);
				}
			}
			
			// BUG
			Delivery droneDelivery = packForTheSameDeliveryMethod(droneOrderItems.toArray(new OrderItem[0]), DeliveryMethod.Drone, distance);
			Delivery courierDelivery = packForTheSameDeliveryMethod(courierOrderItems.toArray(new OrderItem[0]), DeliveryMethod.Courier, distance);
			
			deliveries.add(droneDelivery);
			deliveries.add(courierDelivery);
		}
		
		// Save DB and Checking
		// Save DB: deliveries
		for(Delivery delivery : deliveries) {
			
			delivery = deliveryService.save(delivery);
		
			// delivery is now returned from DB, should (lazily) load child data
			for(Package p : delivery.getPackages()) {
				System.out.print(" >>>>>>>>>> Package " + p.getId() +  " total shipping weight: ");
				double totalShWeight = 0.0;
				for(OrderItem oi : p.getOrderItems()) {
					totalShWeight += oi.calculateShippingWeight();
				}
				System.out.print(totalShWeight);
				System.out.println("");
			}
		}
		
		// Update DB: Order status
		order.setOrderStatus(OrderStatus.Packaged);
	}
	
	void delivering() {} // will be in another scheduling, with DeliveryHandler deliver
	
	void tracking() {} // will be in another scheduling, with DeliveryHandler deliver
	
	// Use for case 1 & 2 (the whole order has only one delivery method)
	private static Delivery packForTheSameDeliveryMethod(Order order, DeliveryMethod deliveryMethod, double distance) throws Exception {
		
		DeliveryType type = order.getDeliveryType(); //(option == DeliveryOption.HomeDelivery ? DeliveryType.HomeDelivery : DeliveryType.LockerPickupDelivery);
		Date deadline = order.getDeliveryDeadline();
		
		Delivery delivery = new Delivery(order, 
				type, deliveryMethod, 
				DeliveryStatus.Entered, 
				order.getDeliveryAddress(),
				deadline, distance, distance);
		
		Locker locker = order.getLocker();
		if (locker != null) {
			delivery.setLocker(locker);
		}
		
		// If Drone delivery    : there is only 1 SMALL PACKAGE (5 lbs) for all order items
		if (deliveryMethod == DeliveryMethod.Drone) {
			
			Package pkg = packForDroneDelivery(order.getOrderItems().toArray(new OrderItem[0]));
			delivery.addPackage(pkg);
		}
		
		// If Courier delivery  : there might be more than 1, MULTIPLE PACKAGES, different SIZES
		else {
			
			Date orderDate = order.getOrderDate();
			long diff = deadline.getTime() - orderDate.getTime();
			int days = (int)(diff / (1000 * 60 * 60 * 24));
			CourierService courierService = days <= 3 ? CourierService.Express : CourierService.Normal;
			delivery.setCourierService(courierService);
			
			List<Package> packages = packForCourierDelivery(order.getOrderItems().toArray(new OrderItem[0]));
			delivery.addPackages(packages);
		}

        return delivery;
	}
	
	// Use for case 3: mix of two delivery methods
	private static Delivery packForTheSameDeliveryMethod(OrderItem[] orderItems, DeliveryMethod deliveryMethod, double distance) throws Exception {
		
		Order order = orderItems[0].getOrder();
		DeliveryType type = order.getDeliveryType();
		Date deadline = order.getDeliveryDeadline();
		
		Delivery delivery = new Delivery(order, 
				type, deliveryMethod, 
				DeliveryStatus.Entered, 
				order.getDeliveryAddress(),
				deadline, distance, distance);

		Locker locker = order.getLocker();
		if (locker != null) {
			delivery.setLocker(locker);
		}
		
		// If Drone delivery    : there is only 1 SMALL PACKAGE (5 lbs) for all order items
		if (deliveryMethod == DeliveryMethod.Drone) {
			
			Date orderDate = order.getOrderDate();
			long diff = deadline.getTime() - orderDate.getTime();
			int days = (int)(diff / (1000 * 60 * 60 * 24));
			CourierService courierService = days <= 3 ? CourierService.Express : CourierService.Normal;
			delivery.setCourierService(courierService);
			
			Package pkg = packForDroneDelivery(orderItems);
			delivery.addPackage(pkg);
		}
		// If Courier delivery  : there might be more than 1, MULTIPLE PACKAGES, different SIZES
		else {
			List<Package> packages = packForCourierDelivery(orderItems);
			delivery.addPackages(packages);
		}
        
        return delivery;
	}
	
	// Use for Drone
	private static Package packForDroneDelivery(OrderItem[] orderItems) {
		Order order = orderItems[0].getOrder();
		Package p = new Package(PackageSize.Small, order);
    	for(OrderItem oi : orderItems) {
    		p.addOrderItem(oi);
    	}
    	return p;
	}
	
	// Use for Courier
	private static List<Package> packForCourierDelivery(OrderItem[] orderItems) throws Exception {
		// Packaging logic
		Order order = orderItems[0].getOrder();
		Hashtable<Integer, Integer> packaging = new Hashtable<>();

		Arrays.sort(orderItems, new Comparator<OrderItem>() {
			@Override
			public int compare(OrderItem o1, OrderItem o2) {
				double diff = o1.calculateShippingWeight() - o2.calculateShippingWeight();
				return diff < 0 ? -1 : (diff == 0 ? 0 : 1);
			}
		});
		
		double curAllowedSize = 5; // 13, 20
		int curPackageNo = 1;
		double curPackageSize = 0;
		
		for(OrderItem oi : orderItems) {
			double sWeight = oi.calculateShippingWeight();
			
			// Consider if the order item exceeds current package
			if (sWeight + curPackageSize <= curAllowedSize) {
				if (!packaging.contains(curPackageNo)) {
					packaging.put(curPackageNo, 1);
				} else { 
					packaging.put(curPackageNo, packaging.get(curPackageNo) + 1);
				}
				curPackageSize += sWeight;
			}
			
			// If it exceeds, create a NEW PACKAGE
			else {
				// Consider if the order item needs a larger package size
				if (sWeight > curAllowedSize) {
					if (sWeight > 13) { // need LARGE package
						curAllowedSize = 20;
					}
					if (sWeight > 5) { // need MEDIUM package
						curAllowedSize = 13;
					}
				}
				
				// In case there is no package yet
				int noPackages = packaging.size();
				if (noPackages > 0) { // If there is already some packages, update the next package
					curPackageNo++;
				}
				
				packaging.put(curPackageNo, 1);
			}
		}
		
		// Test
        for(Integer key: packaging.keySet()){
            System.out.println(" >>>>> Package ("+ key + ") has " + packaging.get(key) + " order item(s)");
        }
		
        // Create packages
        List<Package> packages = new ArrayList<>();
        int startOrderItem = 0;
        int endOrderItem = -1;
        
        int noPackages = packaging.size();
        for (int pNo = 1; pNo <= noPackages; pNo++) {
        	
        	// start
        	if (endOrderItem > -1) {
        		startOrderItem = endOrderItem + 1; 
        	}
        	
        	// end
        	endOrderItem = endOrderItem + packaging.get(pNo);
        	
        	Package pkg = new Package(order);
        	
        	// first package item
        	OrderItem firstOi = orderItems[startOrderItem];
        	double firstOiShWeight = firstOi.calculateShippingWeight();
        	if (firstOiShWeight <= 5) { // SMALL
        		pkg.setSize(PackageSize.Small);
        	}
        	else if (firstOiShWeight <= 13) { // MEDIUM
        		pkg.setSize(PackageSize.Medium);
        	}
        	else if (firstOiShWeight <= 20) { // LARGE
        		pkg.setSize(PackageSize.Large);
        	}
        	else {
        		throw new Exception("The order item is oversized: " + firstOiShWeight);
        	}
        	
        	// add order item to packages
        	for(int i = startOrderItem; i <= endOrderItem; i++) {
        		pkg.addOrderItem(orderItems[i]);
        	}
        	
        	packages.add(pkg);
        }
        
        return packages;
	}
}
