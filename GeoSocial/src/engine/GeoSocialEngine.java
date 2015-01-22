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
	
	InstagramAPI instaAPI;
	
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
			browser.execute(JavascriptAPI.addMarker(image.getLatitude(), image.getLongitude(), JavascriptAPI.PURPLE_MARKER));
		}
	}

}
