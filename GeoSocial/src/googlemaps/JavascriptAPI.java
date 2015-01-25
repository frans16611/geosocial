package googlemaps;

import java.awt.Color;
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
	
	/**
	 * Center Map at the given coordinates
	 * @param lat
	 * @param lng
	 * @return Javascript Code to execute 
	 */
	public static String centerMap(double lat, double lng){
		return "map.setCenter({lat: "+lat+", lng: "+lng+"});";
	}
	
	/**
	 * Add a generic marker at the given coordinates
	 * @param lat
	 * @param lng
	 * @return Javascript Code to execute
	 */
	public static String addMarker(double lat, double lng){
		return "new google.maps.Marker({position: {lat: "+lat+", lng: "+lng+"}, map: map});"; 
	}
	
	/**
	 * Add a custom marker at the given coordinates
	 * @param lat
	 * @param lng
	 * @param markerIcon URL of the marker image
	 * @return Javascript Code to execute
	 */
	public static String addMarker(double lat, double lng, String markerIcon){
		return "new google.maps.Marker({position: {lat: "+lat+", lng: "+lng+"}, map: map,icon:'"+markerIcon+"'});"; 
	}
	
	/**
	 * Add a marker displaying a InfoWindow when clicked 
	 * @param lat
	 * @param lng
	 * @param markerIcon
	 * @param infoHtml HTML code of the infoWindow content
	 * @param id of the marker and info window: each id should be unique to the map
	 * @return Javascript Code to execute
	 */
	public static String addMarkerWithInfo(double lat, double lng, String markerIcon, String infoHtml, long id){
		String script = "";
		script += "var contentString"+id+" = '"+infoHtml+"';\n";
		script += "var infowindow"+id+" = new google.maps.InfoWindow({content: contentString"+id+"});\n";
		script += "var marker"+id+" = "+addMarker(lat,lng,markerIcon)+"\n";
		script += "google.maps.event.addListener(marker"+id+", 'click', function() { infowindow"+id+".open(map,marker"+id+");});";
		return script;
	}
	
	
	/**
	 * Draw a circle on the map with the given parameters
	 * @param lat
	 * @param lng
	 * @param radius in meters
	 * @param color of the circle
	 * @return Javascript code to execute
	 */
	public static String drawCircle(double lat, double lng, double radius, Color color){
		String script = "";
		String colorCode = "#"+Integer.toHexString(color.getRGB()).substring(2);
		script += "var circleOptions = {"
				+ "strokeColor: '"+colorCode+"', "
				+ "strokeOpacity: 0.8, "
				+ "strokeWeight: 2, "
				+ "fillColor: '"+colorCode+"',"
				+ "fillOpacity: 0.35,"
				+ "map: map,"
				+ "center: {lat: "+lat+", lng: "+lng+"},"
				+ "radius: "+radius+" };\n";
		script += "cityCircle = new google.maps.Circle(circleOptions);";
		System.out.println(script);
		return script;
	}
	

	public static String drawCircle(double lat, double lng, double radius){
		return drawCircle(lat, lng, radius, Color.RED);
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
