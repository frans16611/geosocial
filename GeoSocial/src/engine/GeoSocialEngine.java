package engine;

import googlemaps.GoogleMapsEventHandler;
import googlemaps.JavascriptAPI;
import instagram.InstagramAPI;
import instagram.InstagramImage;
import instagram.JSONReader;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class GeoSocialEngine extends GoogleMapsEventHandler {
	
	private InstagramAPI instaAPI;
	
	
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
	
		/* Add Marker at center */
		browser.execute(JavascriptAPI.addMarker(lat, lng,JavascriptAPI.YELLOW_MARKER));
		/* Draw circle around central Marker */
		browser.execute(JavascriptAPI.drawCircle(lat, lng, 1000,Color.BLUE));

		for(InstagramImage image : media){

			browser.execute(JavascriptAPI.addMarkerWithInfo(
					image.getLatitude(), 
					image.getLongitude(),
					JavascriptAPI.PURPLE_MARKER,
					image.getHTMLInfoDescription(), 
					image.getLongId()));
		}
	}

}
