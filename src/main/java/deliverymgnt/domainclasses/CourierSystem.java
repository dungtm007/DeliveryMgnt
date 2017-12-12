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
		distanceZone1Weights.put(26, 40.85);
		distanceZone1Weights.put(27, 42.16);
		distanceZone1Weights.put(28, 43.52);
		distanceZone1Weights.put(29, 44.72);
		distanceZone1Weights.put(30, 45.5);
		distanceZone1Weights.put(31, 46.87);
		distanceZone1Weights.put(32, 47.56);
		distanceZone1Weights.put(33, 48.91);
		distanceZone1Weights.put(34, 50.45);
		distanceZone1Weights.put(35, 51.47);
		distanceZone1Weights.put(36, 52.26);
		distanceZone1Weights.put(37, 53.23);
		distanceZone1Weights.put(38, 54.13);
		distanceZone1Weights.put(39, 55.84);
		distanceZone1Weights.put(40, 57.26);
		distanceZone1Weights.put(41, 58.73);
		distanceZone1Weights.put(42, 59.7);
		distanceZone1Weights.put(43, 60.95);
		distanceZone1Weights.put(44, 61.97);
		distanceZone1Weights.put(45, 62.25);
		distanceZone1Weights.put(46, 64.18);
		distanceZone1Weights.put(47, 65.55);
		distanceZone1Weights.put(48, 66.85);
		distanceZone1Weights.put(49, 68.21);
		distanceZone1Weights.put(50, 69.17);
		distanceZone1Weights.put(51, 70.19);
		distanceZone1Weights.put(52, 70.99);
		distanceZone1Weights.put(53, 72.41);
		distanceZone1Weights.put(54, 73.44);
		distanceZone1Weights.put(55, 74.79);
		distanceZone1Weights.put(56, 75.98);
		distanceZone1Weights.put(57, 76.9);
		distanceZone1Weights.put(58, 78.32);
		distanceZone1Weights.put(59, 79.11);
		distanceZone1Weights.put(60, 79.39);
		distanceZone1Weights.put(61, 81.55);
		distanceZone1Weights.put(62, 82.17);
		distanceZone1Weights.put(63, 82.45);
		distanceZone1Weights.put(64, 82.73);
		distanceZone1Weights.put(65, 87.16);
		distanceZone1Weights.put(66, 88.25);
		distanceZone1Weights.put(67, 89.04);
		distanceZone1Weights.put(68, 90.12);
		distanceZone1Weights.put(69, 91.59);
		distanceZone1Weights.put(70, 93.01);
		distanceZone1Weights.put(71, 93.3);
		distanceZone1Weights.put(72, 93.58);
		distanceZone1Weights.put(73, 93.86);
		distanceZone1Weights.put(74, 94.15);
		distanceZone1Weights.put(75, 97.49);
		distanceZone1Weights.put(76, 97.83);
		distanceZone1Weights.put(77, 98.12);
		distanceZone1Weights.put(78, 99.03);
		distanceZone1Weights.put(79, 101.59);
		distanceZone1Weights.put(80, 101.87);
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
		distanceZone2Weights.put(26, 48.63);
		distanceZone2Weights.put(27, 50.05);
		distanceZone2Weights.put(28, 51.69);
		distanceZone2Weights.put(29, 52.66);
		distanceZone2Weights.put(30, 54.08);
		distanceZone2Weights.put(31, 55.9);
		distanceZone2Weights.put(32, 57.14);
		distanceZone2Weights.put(33, 58.62);
		distanceZone2Weights.put(34, 59.7);
		distanceZone2Weights.put(35, 61.52);
		distanceZone2Weights.put(36, 62.87);
		distanceZone2Weights.put(37, 63.78);
		distanceZone2Weights.put(38, 64.75);
		distanceZone2Weights.put(39, 66.45);
		distanceZone2Weights.put(40, 68.21);
		distanceZone2Weights.put(41, 69.86);
		distanceZone2Weights.put(42, 71.56);
		distanceZone2Weights.put(43, 73.04);
		distanceZone2Weights.put(44, 73.94);
		distanceZone2Weights.put(45, 75.76);
		distanceZone2Weights.put(46, 76.61);
		distanceZone2Weights.put(47, 78.14);
		distanceZone2Weights.put(48, 80.01);
		distanceZone2Weights.put(49, 81.83);
		distanceZone2Weights.put(50, 82.85);
		distanceZone2Weights.put(51, 83.82);
		distanceZone2Weights.put(52, 85.07);
		distanceZone2Weights.put(53, 86.76);
		distanceZone2Weights.put(54, 88.18);
		distanceZone2Weights.put(55, 89.72);
		distanceZone2Weights.put(56, 90.86);
		distanceZone2Weights.put(57, 92.33);
		distanceZone2Weights.put(58, 93.92);
		distanceZone2Weights.put(59, 95.05);
		distanceZone2Weights.put(60, 95.91);
		distanceZone2Weights.put(61, 97.21);
		distanceZone2Weights.put(62, 99.08);
		distanceZone2Weights.put(63, 100.62);
		distanceZone2Weights.put(64, 101.64);
		distanceZone2Weights.put(65, 103.74);
		distanceZone2Weights.put(66, 104.7);
		distanceZone2Weights.put(67, 106.8);
		distanceZone2Weights.put(68, 107.66);
		distanceZone2Weights.put(69, 107.82);
		distanceZone2Weights.put(70, 110.89);
		distanceZone2Weights.put(71, 112.42);
		distanceZone2Weights.put(72, 113.27);
		distanceZone2Weights.put(73, 113.56);
		distanceZone2Weights.put(74, 113.84);
		distanceZone2Weights.put(75, 116.72);
		distanceZone2Weights.put(76, 117.02);
		distanceZone2Weights.put(77, 117.3);
		distanceZone2Weights.put(78, 117.64);
		distanceZone2Weights.put(79, 124.17);
		distanceZone2Weights.put(80, 125.36);
		
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
		distanceZone1Weights.put(26, 13.28);
		distanceZone1Weights.put(27, 13.6);
		distanceZone1Weights.put(28, 13.84);
		distanceZone1Weights.put(29, 14.13);
		distanceZone1Weights.put(30, 14.28);
		distanceZone1Weights.put(31, 14.45);
		distanceZone1Weights.put(32, 14.55);
		distanceZone1Weights.put(33, 14.69);
		distanceZone1Weights.put(34, 14.78);
		distanceZone1Weights.put(35, 15.05);
		distanceZone1Weights.put(36, 15.27);
		distanceZone1Weights.put(37, 15.66);
		distanceZone1Weights.put(38, 15.88);
		distanceZone1Weights.put(39, 16.11);
		distanceZone1Weights.put(40, 16.13);
		distanceZone1Weights.put(41, 16.34);
		distanceZone1Weights.put(42, 16.59);
		distanceZone1Weights.put(43, 16.81);
		distanceZone1Weights.put(44, 17.2);
		distanceZone1Weights.put(45, 17.21);
		distanceZone1Weights.put(46, 17.44);
		distanceZone1Weights.put(47, 17.56);
		distanceZone1Weights.put(48, 17.57);
		distanceZone1Weights.put(49, 17.61);
		distanceZone1Weights.put(50, 17.69);
		distanceZone1Weights.put(51, 17.7);
		distanceZone1Weights.put(52, 17.71);
		distanceZone1Weights.put(53, 17.72);
		distanceZone1Weights.put(54, 17.73);
		distanceZone1Weights.put(55, 17.74);
		distanceZone1Weights.put(56, 17.75);
		distanceZone1Weights.put(57, 18.16);
		distanceZone1Weights.put(58, 18.17);
		distanceZone1Weights.put(59, 18.49);
		distanceZone1Weights.put(60, 18.68);
		distanceZone1Weights.put(61, 19.05);
		distanceZone1Weights.put(62, 19.38);
		distanceZone1Weights.put(63, 19.45);
		distanceZone1Weights.put(64, 19.8);
		distanceZone1Weights.put(65, 20.21);
		distanceZone1Weights.put(66, 20.5);
		distanceZone1Weights.put(67, 20.77);
		distanceZone1Weights.put(68, 20.93);
		distanceZone1Weights.put(69, 21.17);
		distanceZone1Weights.put(70, 21.27);
		distanceZone1Weights.put(71, 21.38);
		distanceZone1Weights.put(72, 21.67);
		distanceZone1Weights.put(73, 22.22);
		distanceZone1Weights.put(74, 23.24);
		distanceZone1Weights.put(75, 24.32);
		distanceZone1Weights.put(76, 25.25);
		distanceZone1Weights.put(77, 26.22);
		distanceZone1Weights.put(78, 26.77);
		distanceZone1Weights.put(79, 27.98);
		distanceZone1Weights.put(80, 28.74);
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
		distanceZone2Weights.put(26, 15.3);
		distanceZone2Weights.put(27, 15.67);
		distanceZone2Weights.put(28, 16.19);
		distanceZone2Weights.put(29, 16.5);
		distanceZone2Weights.put(30, 16.75);
		distanceZone2Weights.put(31, 16.94);
		distanceZone2Weights.put(32, 16.98);
		distanceZone2Weights.put(33, 17.53);
		distanceZone2Weights.put(34, 17.75);
		distanceZone2Weights.put(35, 18.51);
		distanceZone2Weights.put(36, 18.52);
		distanceZone2Weights.put(37, 18.94);
		distanceZone2Weights.put(38, 19.23);
		distanceZone2Weights.put(39, 19.61);
		distanceZone2Weights.put(40, 19.87);
		distanceZone2Weights.put(41, 20.34);
		distanceZone2Weights.put(42, 20.79);
		distanceZone2Weights.put(43, 20.9);
		distanceZone2Weights.put(44, 21.31);
		distanceZone2Weights.put(45, 21.63);
		distanceZone2Weights.put(46, 21.64);
		distanceZone2Weights.put(47, 21.92);
		distanceZone2Weights.put(48, 22.03);
		distanceZone2Weights.put(49, 22.04);
		distanceZone2Weights.put(50, 22.15);
		distanceZone2Weights.put(51, 22.16);
		distanceZone2Weights.put(52, 22.17);
		distanceZone2Weights.put(53, 22.2);
		distanceZone2Weights.put(54, 22.24);
		distanceZone2Weights.put(55, 22.28);
		distanceZone2Weights.put(56, 22.49);
		distanceZone2Weights.put(57, 22.64);
		distanceZone2Weights.put(58, 22.65);
		distanceZone2Weights.put(59, 22.88);
		distanceZone2Weights.put(60, 23.32);
		distanceZone2Weights.put(61, 24.05);
		distanceZone2Weights.put(62, 24.81);
		distanceZone2Weights.put(63, 25.05);
		distanceZone2Weights.put(64, 25.54);
		distanceZone2Weights.put(65, 26.02);
		distanceZone2Weights.put(66, 26.48);
		distanceZone2Weights.put(67, 26.5);
		distanceZone2Weights.put(68, 27.24);
		distanceZone2Weights.put(69, 27.25);
		distanceZone2Weights.put(70, 27.3);
		distanceZone2Weights.put(71, 27.32);
		distanceZone2Weights.put(72, 27.62);
		distanceZone2Weights.put(73, 27.63);
		distanceZone2Weights.put(74, 27.66);
		distanceZone2Weights.put(75, 28.44);
		distanceZone2Weights.put(76, 29.48);
		distanceZone2Weights.put(77, 30.16);
		distanceZone2Weights.put(78, 30.74);
		distanceZone2Weights.put(79, 32.02);
		distanceZone2Weights.put(80, 32.93);
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
