package deliverymgnt.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryMethod;
import deliverymgnt.domainclasses.DeliveryOption;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.OrderItem;
import deliverymgnt.domainclasses.OrderStatus;
import deliverymgnt.domainclasses.Package;
import deliverymgnt.domainclasses.PackageSize;

@Component
public class PackagingBusiness {

	@Autowired
	private static OrderService orderService1;
	
	@Autowired
	private static DeliveryService deliveryService1;
	
	public static void ProcessOrder(Order order, DeliveryService deliveryService, OrderService orderService) throws Exception {
		
		// Switch order to Processing
		order.setOrderStatus(OrderStatus.Processing);
		
		// Rules to Generate Deliveries and Packages
		// Maximum weight of a delivery: 30 lbs
		// Maximum weight of a package: 20 lbs (9.07 kg)
		// Small: <= 5 lbs (< 2.26 kg)
		// Medium: 5 -> 13 (2.26 kg - 5.8 kg) 
		// Large: >= 13 -> 20 (6.3 kg - 9 kg)
		
		// Analyze
		// *** Delivery type
		// ****** Home         -> Drone | Courier
		// ****** Locker       ->         Courier
		DeliveryOption option = order.getDeliveryOption();
		
		// *** Distance
		// ****** <= 30 miles  -> Drone
		// ****** > 30 miles   ->       | Courier
		// (UPD) Run a logic to detect the warehouse to collect all products
		// (UPD) Call Google Map service to calculate distance
		double distance = 28.5;
		
		// *** Freight Weight (the maximum between Actual Weight and Volumetric Weight)
		// ***** <= 5          -> Drone
		// ***** > 5           ->       | Courier
		//order.getOrderItems();
		double shippingWeight = order.calculateTotalShippingWeight();
		
		System.out.println(" >>>>> delivery option: " + option);
		System.out.println(" >>>>> distance: " + distance);
		System.out.println(" >>>>> shipping weight: " + shippingWeight);
		 
		List<Delivery> deliveries = new ArrayList<>();
		
		// Case 1: All by drone
		// 1 delivery for ALL packages
		if (option == DeliveryOption.HomeDelivery && distance <= 30 && shippingWeight <= 5) {
			// 1 SMALL delivery
			System.out.println("Case 1 entered ...");
			Delivery delivery = packageForTheSameDeliveryMethod(order, DeliveryMethod.Drone, distance);
			deliveries.add(delivery);
		}

		// Case 2: all by courier
		// 1 delivery for ALL packages
		else if (option == DeliveryOption.LockerPickupDelivery || distance > 30) {
			System.out.println("Case 2 entered ...");	
			Delivery delivery = packageForTheSameDeliveryMethod(order, DeliveryMethod.Courier, distance);
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
			
			Delivery droneDelivery = packageForTheSameDeliveryMethod(droneOrderItems.toArray(new OrderItem[0]), DeliveryMethod.Drone, distance);
			Delivery courierDelivery = packageForTheSameDeliveryMethod(courierOrderItems.toArray(new OrderItem[0]), DeliveryMethod.Courier, distance);
			
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
		order.setOrderStatus(OrderStatus.Processed);
		orderService.save(order);
		System.out.println(" >>>>> Process order " + order.getId() +  " successfully!");		
	}
	
	// Use for case 1 & 2 (the whole order has only one delivery method)
	private static Delivery packageForTheSameDeliveryMethod(Order order, DeliveryMethod deliveryMethod, double distance) throws Exception {
		
		// If Drone delivery    : there is only 1 SMALL PACKAGE (5 lbs) for all order items
		// If Courier delivery  : there might be more than 1, MULTIPLE PACKAGES, different SIZES
		
		DeliveryOption option = order.getDeliveryOption();
		DeliveryType type = (option == DeliveryOption.HomeDelivery ? DeliveryType.HomeDelivery : DeliveryType.LockerPickupDelivery);
		
		Delivery delivery = new Delivery(order, 
				type, deliveryMethod, 
				DeliveryStatus.Entered, 
				order.getDeliveryAddress(), 
				distance);
		
		// If Drone delivery    : there is only 1 SMALL PACKAGE (5 lbs) for all order items
		if (deliveryMethod == DeliveryMethod.Drone) {
			
			Package pkg = packageForDroneDelivery(order.getOrderItems().toArray(new OrderItem[0]));
			delivery.addPackage(pkg);
		}
		
		// If Courier delivery  : there might be more than 1, MULTIPLE PACKAGES, different SIZES
		else {
			List<Package> packages = packageForCourierDelivery(order.getOrderItems().toArray(new OrderItem[0]));
			delivery.addPackages(packages);
		}

        return delivery;
	}
	
	// Use for case 3: mix of two delivery methods
	private static Delivery packageForTheSameDeliveryMethod(OrderItem[] orderItems, DeliveryMethod deliveryMethod, double distance) throws Exception {
		
		// If Drone delivery    : there is only 1 SMALL PACKAGE (5 lbs) for all order items
		// If Courier delivery  : there might be more than 1, MULTIPLE PACKAGES, different SIZES
		
		Order order = orderItems[0].getOrder();
		
		DeliveryOption option = order.getDeliveryOption();
		DeliveryType type = (option == DeliveryOption.HomeDelivery ? DeliveryType.HomeDelivery : DeliveryType.LockerPickupDelivery);
		
		Delivery delivery = new Delivery(order, 
				type, deliveryMethod, 
				DeliveryStatus.Entered, 
				order.getDeliveryAddress(), 
				distance);

		// If Drone delivery    : there is only 1 SMALL PACKAGE (5 lbs) for all order items
		if (deliveryMethod == DeliveryMethod.Drone) {
			
			Package pkg = packageForDroneDelivery(orderItems);
			delivery.addPackage(pkg);
		}
		// If Courier delivery  : there might be more than 1, MULTIPLE PACKAGES, different SIZES
		else {
			List<Package> packages = packageForCourierDelivery(orderItems);
			delivery.addPackages(packages);
		}
        
        return delivery;
	}
	
	// Use for Drone
	private static Package packageForDroneDelivery(OrderItem[] orderItems) {
		Order order = orderItems[0].getOrder();
		Package p = new Package(order);
    	for(OrderItem oi : orderItems) {
    		p.addOrderItem(oi);
    	}
    	return p;
	}
	
	// Use for Courier
	private static List<Package> packageForCourierDelivery(OrderItem[] orderItems) throws Exception {
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
        for(Integer key: packaging.keySet()) {
            
        	// start
        	if (endOrderItem > 0) {
        		startOrderItem = endOrderItem + 1; 
        	}
        	
        	// end
        	endOrderItem = endOrderItem + packaging.get(key);  
        	
        	Package p = new Package(order);
        	
        	// first package item
        	OrderItem firstOi = orderItems[startOrderItem];
        	double firstOiShWeight = firstOi.calculateShippingWeight();
        	if (firstOiShWeight <= 5) { // SMALL
        		p.setSize(PackageSize.Small);
        	}
        	else if (firstOiShWeight <= 13) { // MEDIUM
        		p.setSize(PackageSize.Medium);
        	}
        	else if (firstOiShWeight <= 20) {
        		p.setSize(PackageSize.Large);
        	}
        	else {
        		throw new Exception("The order item is oversized: " + firstOiShWeight);
        	}
        	
        	// add order item to packages
        	for(int i = startOrderItem; i <= endOrderItem; i++) {
        		p.addOrderItem(orderItems[i]);
        	}
        	
        	packages.add(p);
        }
        
        return packages;
		
	}
}
