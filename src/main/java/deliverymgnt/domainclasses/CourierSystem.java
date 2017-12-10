package deliverymgnt.domainclasses;

import java.util.Hashtable;


public final class CourierSystem {

	private static Hashtable<Integer, Hashtable<Integer, Double>> expressServiceMappings = generateExpressServiceMappings();
	
	private static Hashtable<Integer, Hashtable<Integer, Double>> normalServiceMappings = generateNormalServiceMappings();
	
	private static Hashtable<Integer, Hashtable<Integer, Double>> generateExpressServiceMappings() {
		
		Hashtable<Integer, Hashtable<Integer, Double>> mappings = new Hashtable<>();
		
		Hashtable<Integer, Double> distanceZone1Weights = new Hashtable<>();
		Hashtable<Integer, Double> distanceZone2Weights = new Hashtable<>();
		mappings.put(1, distanceZone1Weights);
		mappings.put(2, distanceZone2Weights);
		
		// Zone 1: 1 - 150 miles
		distanceZone1Weights.put(1, 16.50);
		distanceZone1Weights.put(2, 16.78);
		distanceZone1Weights.put(3, 17.07);
		distanceZone1Weights.put(4, 17.58);
		distanceZone1Weights.put(5, 18.10);
		distanceZone1Weights.put(6, 18.89);
		distanceZone1Weights.put(7, 19.69);
		distanceZone1Weights.put(8, 20.48);
		distanceZone1Weights.put(9, 21.56);
		distanceZone1Weights.put(10, 22.58);
		distanceZone1Weights.put(11, 23.60);
		distanceZone1Weights.put(12, 24.85);
		distanceZone1Weights.put(13, 26.61);
		distanceZone1Weights.put(14, 27.92);
		distanceZone1Weights.put(15, 28.99);
		distanceZone1Weights.put(16, 29.67);
		distanceZone1Weights.put(17, 30.58);
		distanceZone1Weights.put(18, 31.61);
		distanceZone1Weights.put(19, 32.85);
		distanceZone1Weights.put(20, 33.99);
		distanceZone1Weights.put(21, 35.57);
		distanceZone1Weights.put(22, 36.66);
		distanceZone1Weights.put(23, 37.56);
		distanceZone1Weights.put(24, 38.41);
		distanceZone1Weights.put(25, 39.67);
		// ...
		
	
		// Zone 2: 151 - 300 miles
		distanceZone2Weights.put(1, 17.18);
		distanceZone2Weights.put(2, 17.53 );
		distanceZone2Weights.put(3,  18.10 );
		distanceZone2Weights.put(4,  18.72);
		distanceZone2Weights.put(5, 19.34 );
		distanceZone2Weights.put(6,  20.08 );
		distanceZone2Weights.put(7,  22.01);
		distanceZone2Weights.put(8, 24.00);
		distanceZone2Weights.put(9,  25.07 );
		distanceZone2Weights.put(10,  26.73);
		distanceZone2Weights.put(11,  28.14);
		distanceZone2Weights.put(12, 29.79 );
		distanceZone2Weights.put(13, 31.32);
		distanceZone2Weights.put(14, 32.96 );
		distanceZone2Weights.put(15, 33.76);
		distanceZone2Weights.put(16, 34.89);
		distanceZone2Weights.put(17, 35.92);
		distanceZone2Weights.put(18, 37.11 );
		distanceZone2Weights.put(19, 38.13);
		distanceZone2Weights.put(20, 39.77 );
		distanceZone2Weights.put(21, 41.31 );
		distanceZone2Weights.put(22, 43.30);
		distanceZone2Weights.put(23, 44.26);
		distanceZone2Weights.put(24,  46.02);
		distanceZone2Weights.put(25,  47.32);
		// ...
		
		return mappings;
	}
	
	private static Hashtable<Integer, Hashtable<Integer, Double>> generateNormalServiceMappings() {
		
		Hashtable<Integer, Hashtable<Integer, Double>> mappings = new Hashtable<>();
		
		Hashtable<Integer, Double> distanceZone1Weights = new Hashtable<>();
		Hashtable<Integer, Double> distanceZone2Weights = new Hashtable<>();
		mappings.put(1, distanceZone1Weights);
		mappings.put(2, distanceZone2Weights);
		
		// Zone 1: 1 - 150 miles
		distanceZone1Weights.put(1, 7.25);
		distanceZone1Weights.put(2, 8.00);
		distanceZone1Weights.put(3, 8.13);
		distanceZone1Weights.put(4, 8.44);
		distanceZone1Weights.put(5, 8.45);
		distanceZone1Weights.put(6, 8.73);
		distanceZone1Weights.put(7, 9.22);
		distanceZone1Weights.put(8, 9.47);
		distanceZone1Weights.put(9, 9.65);
		distanceZone1Weights.put(10, 9.87);
		distanceZone1Weights.put(11, 10.30);
		distanceZone1Weights.put(12, 10.47);
		distanceZone1Weights.put(13, 10.72);
		distanceZone1Weights.put(14, 10.99);
		distanceZone1Weights.put(15, 11.06);
		distanceZone1Weights.put(16, 11.18);
		distanceZone1Weights.put(17, 11.38);
		distanceZone1Weights.put(18, 11.40);
		distanceZone1Weights.put(19, 11.50);
		distanceZone1Weights.put(20, 11.66);
		distanceZone1Weights.put(21, 12.01);
		distanceZone1Weights.put(22, 12.27);
		distanceZone1Weights.put(23, 12.41);
		distanceZone1Weights.put(24, 12.65);
		distanceZone1Weights.put(25, 12.79);
		// ...
		
	
		// Zone 2: 151 - 300 miles
		distanceZone2Weights.put(1, 7.92);
		distanceZone2Weights.put(2,  8.62);
		distanceZone2Weights.put(3,  8.99 );
		distanceZone2Weights.put(4, 9.29);
		distanceZone2Weights.put(5, 9.38);
		distanceZone2Weights.put(6,  9.64 );
		distanceZone2Weights.put(7,  9.88);
		distanceZone2Weights.put(8,10.13);
		distanceZone2Weights.put(9,  10.37);
		distanceZone2Weights.put(10,  10.41);
		distanceZone2Weights.put(11,  10.72);
		distanceZone2Weights.put(12, 11.06);
		distanceZone2Weights.put(13, 11.43);
		distanceZone2Weights.put(14, 11.63);
		distanceZone2Weights.put(15, 11.88);
		distanceZone2Weights.put(16, 12.11);
		distanceZone2Weights.put(17, 12.48);
		distanceZone2Weights.put(18,12.65 );
		distanceZone2Weights.put(19, 12.92);
		distanceZone2Weights.put(20, 13.15);
		distanceZone2Weights.put(21, 13.67);
		distanceZone2Weights.put(22, 13.92);
		distanceZone2Weights.put(23, 14.30);
		distanceZone2Weights.put(24, 14.63);
		distanceZone2Weights.put(25,  14.93);
		// ...
		
		
		return mappings;
	}
	
	private static Hashtable<Integer, Hashtable<Integer, Double>> getPriceMappings(CourierService deliveryService) {
		
		if (deliveryService == CourierService.Express) 
		{
			if (expressServiceMappings == null) {
				expressServiceMappings = generateExpressServiceMappings();
			}
			return expressServiceMappings;
		}
		else 
		{
			if (normalServiceMappings == null) {
				normalServiceMappings = generateNormalServiceMappings();
			}
			return normalServiceMappings;
		}
	}
	
	public static double calculateDeliveryCost(CourierService deliveryService, double weight, double distance) {
		
		Hashtable<Integer, Hashtable<Integer, Double>> mappings = getPriceMappings(deliveryService);
		Hashtable<Integer, Double> zone;
		
		if (distance <= 150) { // zone 1
			zone = mappings.get(1);
		}
		else { // zone 2
			zone = mappings.get(2);
		}
		
		return zone.get(Math.round((float)weight)); 
	}
	
}
