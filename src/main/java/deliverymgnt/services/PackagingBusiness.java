package deliverymgnt.services;

import java.util.HashSet;
import java.util.Set;

import deliverymgnt.domainclasses.Delivery;
import deliverymgnt.domainclasses.Order;

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
		// Q: 
		
		// 3 possible cases
		// C1: only use drone (home delivery, allowed distance, total allowed weight: 5lbs)
		// C2: only use courier (pickup delivery, OR over possible distance of drone)
		// C3: mix drone and courier (home delivery, allowed distance for drone, total allowed weight might exceed 5lbs)
		
		return new HashSet<>();
	}
}
