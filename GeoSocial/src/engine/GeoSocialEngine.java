package engine;

import googlemaps.GoogleMapsEventHandler;
import googlemaps.JavascriptAPI;
import instagram.InstagramAPI;
import instagram.InstagramImage;
import instagram.JSONReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class GeoSocialEngine extends GoogleMapsEventHandler {
	
	private InstagramAPI instaAPI;
	
	private final String infoWindow =  "<div id=\"content\">"
			+ "<div id=\"siteNotice\">"
			+ "</div>"
			+ "<div id=\"bodyContent\">"
			+ "<img src=\"keyURL\" alt=\"Instagram\">"
			+ "<p>"
			+ "<b>User:</b> keyUser <br>"
			+ "<b>ID:</b> keyID <br>"
			+ "<b>Filter:</b> keyFilter <br>"
			+ "</p>"
			+ "</div>"
			+ "</div>";
	
	public GeoSocialEngine() {
		try{
			String apiKey = FileUtils.readFileToString(new File("keys/Instagram.txt"));
			instaAPI = new InstagramAPI(apiKey);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	@Override
	protected void doubleclick(double lat, double lng) {

	}

	@Override
	protected void rightclick(double lat, double lng) {
		
		String json = instaAPI.search(lat, lng, 1000);
		List<InstagramImage> media = JSONReader.readMedia(json);
		
		for(InstagramImage image : media){
			
			String customInfoWindow = infoWindow;
			customInfoWindow = customInfoWindow.replaceFirst("keyUser", image.getUsername());
			customInfoWindow = customInfoWindow.replaceFirst("keyFilter", image.getFilter());
			customInfoWindow = customInfoWindow.replaceFirst("keyID", String.valueOf(image.getLongId()));
			customInfoWindow = customInfoWindow.replaceFirst("keyURL", image.getThumbnail());
			
			//browser.execute(JavascriptAPI.addMarker(image.getLatitude(), image.getLongitude(), JavascriptAPI.PURPLE_MARKER));
			browser.execute(JavascriptAPI.addMarkerWithInfo(image.getLatitude(), image.getLongitude(), JavascriptAPI.PURPLE_MARKER,customInfoWindow));
		}
	}

}
