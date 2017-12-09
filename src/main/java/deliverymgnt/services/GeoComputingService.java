package deliverymgnt.services;

public interface GeoComputingService {
	public long computeDistance(String from, String to);
	public long computeArrivalTime(String from, String to);
}
