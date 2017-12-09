package deliverymgnt.services.impl;

import java.io.IOException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.GeoApiContext.Builder;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import deliverymgnt.services.GeoComputingService;

public class GeoComputingServiceImpl implements GeoComputingService {
	private static final String API_KEY = "AIzaSyDk6nuzNX_4ynBGfS61n2OVPePbeSSMxEY";
	private static final Builder builder = new GeoApiContext.Builder().apiKey(API_KEY);
	private static final GeoApiContext context = builder.build();
	
	@Override
	public long computeDistance(String from, String to) {
		long ret = 0;
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context); 
        try {
			DistanceMatrix trix = req.origins(from)
			        .destinations(to)
			        .mode(TravelMode.DRIVING)
			        .avoid(RouteRestriction.HIGHWAYS)
			        .language("en")
			        .await();
			ret = trix.rows[0].elements[0].distance.inMeters;
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public long computeArrivalTime(String from, String to) {
		long ret = 0;
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context); 
        try {
			DistanceMatrix trix = req.origins(from)
			        .destinations(to)
			        .mode(TravelMode.DRIVING)
			        .avoid(RouteRestriction.HIGHWAYS)
			        .language("en")
			        .await();
			ret = trix.rows[0].elements[0].duration.inSeconds;
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
