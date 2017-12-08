package deliverymgnt;
import java.io.IOException;

import com.google.maps.*;
import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.GeoApiContext.Builder;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

public class TestGoogleApi {
	private static final String API_KEY = "AIzaSyDk6nuzNX_4ynBGfS61n2OVPePbeSSMxEY";
	private static final Builder builder = new GeoApiContext.Builder().apiKey(API_KEY);
	private static final GeoApiContext context = builder.build();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context); 
        try {
			DistanceMatrix trix = req.origins("Vancouver BC")
			        .destinations("San Francisco")
			        .mode(TravelMode.DRIVING)
			        .avoid(RouteRestriction.HIGHWAYS)
			        .language("fr-FR")
			        .await();
			for(DistanceMatrixRow i : trix.rows) {
				for(DistanceMatrixElement j : i.elements) {
					System.out.println("Distance: " + j.distance.humanReadable);
					System.out.println("Duration: " + j.duration.humanReadable);
				}
			}
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
	}
}
