package deliverymgnt;
import deliverymgnt.services.impl.GeoComputingServiceImpl;

public class TestGoogleApi {
	
	public static void main(String[] args) {
		GeoComputingServiceImpl impl = new GeoComputingServiceImpl();
		System.out.println("Distance(m): " + impl.computeDistance("Iowa", "Chicago"));
		System.out.println("ArrivalTime(seconds): " + impl.computeArrivalTime("Iowa", "Chicago"));
	}
}
