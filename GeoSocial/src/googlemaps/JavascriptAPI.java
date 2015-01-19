package googlemaps;

public class JavascriptAPI {

	public static String centerMap(double lat, double lng){
		return "map.setCenter({lat: "+lat+", lng: "+lng+"});";
	}
	
	public static String addMarker(double lat, double lng){
		return "new google.maps.Marker({position: {lat: "+lat+", lng: "+lng+"}, map: map});"; 
	}
}
