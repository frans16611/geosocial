package googlemaps;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class JavascriptAPI {

	private final String apiKey;
	
	public JavascriptAPI(String apiKey){
		this.apiKey = apiKey;
	}
	
	public static String centerMap(double lat, double lng){
		return "map.setCenter({lat: "+lat+", lng: "+lng+"});";
	}
	
	public static String addMarker(double lat, double lng){
		return "new google.maps.Marker({position: {lat: "+lat+", lng: "+lng+"}, map: map});"; 
	}
	
	/**
	 * 
	 * @return Locally stored API Key for Google Maps Javascript API
	 */
	public static String getLocalAPIKey(){
		String key;
		try{
			key = FileUtils.readFileToString(new File("keys/GoogleMaps.txt"));
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("[ERROR] Google Maps API Key cannot be loaded");
			key = "";
		}
		return key;
	}
	
	/**
	 * 
	 * @return custom API Key for Google Maps Javascript API defined in Javaspcript Object
	 */
	public String getAPIKey(){
		return apiKey;
	}
}
