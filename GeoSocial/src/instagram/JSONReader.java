package instagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.Main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONReader {

	private final static String JSON_TAG_DATA = "data";
	private final static String JSON_TAG_ID = "id";
	private final static String JSON_TAG_TYPE = "type";
	private final static String JSON_TAG_CREATED_TIME = "created_time";
	private final static String JSON_TAG_URL = "url";
	private final static String JSON_TAG_RESOLUTION = "standard_resolution";
	private final static String JSON_TAG_THUMBNAIL = "thumbnail";
	private final static String JSON_TAG_IMAGES = "images";
	private final static String JSON_TAG_IMAGE_TYPE= "image";
	private final static String JSON_TAG_FILTER = "filter";
	private final static String JSON_TAG_USER = "user";
	private final static String JSON_TAG_USERNAME = "username";
	private final static String JSON_TAG_LOCATION = "location";
	private final static String JSON_TAG_LATITUDE = "latitude";
	private final static String JSON_TAG_LONGITUDE = "longitude";
	private final static String JSON_TAG_NAME = "name";

	public final static boolean READ_VIDEO_DEFAULT = false;

	/** Return List of Instagram Images contained in the JSONString */
	public static List<InstagramImage> readMedia(String json){
		return readMedia(json,READ_VIDEO_DEFAULT);
	}

	/** Return List of Instagram Images contained in the JSONString */
	public static List<InstagramImage> readMedia(String json, boolean includeVideo){
		JSONObject obj;
		JSONArray jsonArray;
		List<InstagramImage> images = new ArrayList<InstagramImage>();

		try {
			obj = new JSONObject(json);
			jsonArray = obj.getJSONArray(JSON_TAG_DATA);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject media = jsonArray.getJSONObject(i);
				if(media.getString(JSON_TAG_TYPE).matches(JSON_TAG_IMAGE_TYPE)){
					images.add(readMedia(media));
				}				
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return images;
	}
	
	/** Read Instagram Locations from JSONString */
	public static List<InstagramLocation> readLocations(String json){
		JSONObject obj;
		JSONArray jsonArray;
		List<InstagramLocation> locations = new ArrayList<InstagramLocation>();
	

		try {
			obj = new JSONObject(json);
			jsonArray = obj.getJSONArray(JSON_TAG_DATA);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject loc = jsonArray.getJSONObject(i);
				locations.add(new InstagramLocation(
						Long.valueOf(loc.getString(JSON_TAG_ID)),
						loc.getDouble(JSON_TAG_LATITUDE), 
						loc.getDouble(JSON_TAG_LATITUDE), 
						loc.getString(JSON_TAG_NAME)));
				if(Main.DEBUG)
					System.out.print(locations.get(locations.size()-1).description());

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return locations;
	}
	
	/** Read Instagram media described by JSONObject into an InstagramImage Object */
	private static InstagramImage readMedia(JSONObject media) throws JSONException,IOException{

		InstagramImage image = new InstagramImage(media.getString(JSON_TAG_ID));
		image.setCreated_time(Integer.valueOf(media.getString(JSON_TAG_CREATED_TIME)));
		image.setUrl(media.getJSONObject(JSON_TAG_IMAGES).getJSONObject(JSON_TAG_RESOLUTION).getString(JSON_TAG_URL));
		image.setThumbnail(media.getJSONObject(JSON_TAG_IMAGES).getJSONObject(JSON_TAG_THUMBNAIL).getString(JSON_TAG_URL));
		image.setFilter(media.getString(JSON_TAG_FILTER));
		image.setUsername(media.getJSONObject(JSON_TAG_USER).getString(JSON_TAG_USERNAME));
		/* Included in Id */
		//image.setUserid(media.getJSONObject(JSON_TAG_USER).getString(JSON_TAG_ID));
		try{
			image.setLatitude(media.getJSONObject(JSON_TAG_LOCATION).getDouble(JSON_TAG_LATITUDE));
			image.setLongitude(media.getJSONObject(JSON_TAG_LOCATION).getDouble(JSON_TAG_LONGITUDE));
		}catch(JSONException e){
			image.setLatitude(0.0);
			image.setLongitude(0.0);
		}
		//image.downloadThumbnail();
		if(Main.DEBUG)
			System.out.print(image.description());
		
		return image;		
	}

}
