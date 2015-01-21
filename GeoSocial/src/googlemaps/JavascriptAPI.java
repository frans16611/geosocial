package googlemaps;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class JavascriptAPI {

	private final String apiKey;
	public final static String BLUE_MARKER = "http://maps.google.com/mapfiles/ms/icons/blue-dot.png";
	public final static String RED_MARKER = "http://maps.google.com/mapfiles/ms/icons/red-dot.png";
	public final static String PURPLE_MARKER = "http://maps.google.com/mapfiles/ms/icons/purple-dot.png";
	public final static String YELLOW_MARKER = "http://maps.google.com/mapfiles/ms/icons/yellow-dot.png";
	public final static String GREEN_MARKER = "http://maps.google.com/mapfiles/ms/icons/green-dot.png";
	
	public JavascriptAPI(String apiKey){
		this.apiKey = apiKey;
	}
	
	public static String centerMap(double lat, double lng){
		return "map.setCenter({lat: "+lat+", lng: "+lng+"});";
	}
	
	public static String addMarker(double lat, double lng){
		return "new google.maps.Marker({position: {lat: "+lat+", lng: "+lng+"}, map: map});"; 
	}
	
	public static String addMarker(double lat, double lng, String markerIcon){
		return "new google.maps.Marker({position: {lat: "+lat+", lng: "+lng+"}, map: map,icon:'"+markerIcon+"'});"; 
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
