package deliverymgnt.services;

import java.util.HashSet;
import java.util.Set;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.DeliveryStatus;
import deliverymgnt.domainclasses.DeliveryType;
import deliverymgnt.domainclasses.Order;
import deliverymgnt.domainclasses.Package;
import deliverymgnt.domainclasses.PackageSize;

public class PackagingBusiness {

	public static Set<Delivery> ProcessOrder(Order order) {
		
		// to analyze an order 
		// select the most optimal way to create packages
		// and deliveries
		
		// 1st prioritize: Cost
		// 2nd prioritize: Available // assume that drone is always available (create 15-20 drones)
		
		// 1st prioritize: Cost
		// 1.1 Prioritize: Drone
		// Q: delivery type? (Home or Locker)
		// Q: distance? collecting address to order's delivery address
		
		// 3 possible cases
		// C1: only use drone (home delivery, allowed distance, total allowed weight: 5lbs)
		// C2: only use courier (pickup delivery, OR over possible distance of drone)
		// C3: mix drone and courier (home delivery, allowed distance for drone, total allowed weight might exceed 5lbs)
		
		
		// Analyze
		// *** Delivery type
		// ****** Home         -> Drone | Courier
		// ****** Locker       ->         Courier
		DeliveryType type = order.getDeliveryType();
		
		// *** Distance
		// ****** <= 30 miles  -> Drone
		// ****** > 30 miles   ->       | Courier
		double distance = 28.5;
		
		// *** Freight Weight (the maximum between Actual Weight and Volumetric Weight)
		// ***** <= 5          -> Drone
		// ***** > 5           ->       | Courier
		double freightWeight = Math.max(order.calculateTotalVolumetricWeight(),
				order.calculateTotalActualWeight());
		
		 
		// Case 1: all by drone
		if (type == DeliveryType.HomeDelivery &&
			distance <= 30 &&
			freightWeight <= 5) {
			// 2 SMALL packages: >= 2.5 and remaining
			// 1 delivery
			
			Delivery delivery = new Delivery(order, DeliveryStatus.Entered, order.getDeliveryAddress(), 
					distance, DeliveryType.HomeDelivery);
			
			Package p1 = new Package(PackageSize.Small, order);
			Package p2 = new Package(PackageSize.Small, order);
			
			delivery.addPackage(p1);
			delivery.addPackage(p2);
			
			// save to DB
			
		}

		// Case 2: all by courier
		// 1 delivery for ALL packages
//		else if (false) {
//			
//		}
		
		// Generate Deliveries and Packages
		// Maximum weight of a delivery: 30 lbs
		// Maximum weight of a package: 20 lbs (9.07 kg)
		// Small: <= 5 lbs (< 2.26 kg)
		// Medium: 5 -> 13 (2.26 kg - 5.8 kg) 
		// Large: >= 14 -> 20 (6.3 kg - 9 kg)
		
		
		
		// Case 3: mix with drone and courier
//		else {
//			
//		}
		
		
		
		
		
		return new HashSet<>();
	}
}
