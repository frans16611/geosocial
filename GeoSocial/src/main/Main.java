package main;

import engine.GeoSocialEngine;
import gui.ApplicationShell;
import instagram.ImageDatabase;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.swt.widgets.Display;
import org.json.JSONException;

public class Main {

	public final static boolean DEBUG = true;
	
	public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException, JSONException {

		
//		InstagramAPI instaAPI = new InstagramAPI();
//		String json = instaAPI.getPopularMedia();
//		String json = instaAPI.searchLocation(48.85, 2.29, 2000);
//		System.out.println(json);
		
		/*Write to file*/
//		File file = new File("json.json");
//		FileUtils.writeStringToFile(file,json);
		
//		File file = new File("json.json");
//		String json = FileUtils.readFileToString(file);
//		
//		List<InstagramLocation> locations = JSONReader.readLocations(json);
		
//		List<InstagramImage> popular = JSONReader.readPopular(json);
		ImageDatabase database = new ImageDatabase();
//		for(InstagramImage image : popular){
//			database.addItem(image);
//		}
//		System.out.println("Size of Database = "+database.size());
//		
//		database.exportDatabase("database.dat");
		
		database.importDatabase("database.dat");
//		System.out.println("Size of Database = "+database.size());
//		
		Display display = new Display();
//		ImageDisplay id = new ImageDisplay();
//		
//		id.randomImage(database);
//		
//		database.close();
//		System.out.println("Size of Database = "+database.size());
		
		GeoSocialEngine engine = new GeoSocialEngine();
		new ApplicationShell(display,database,engine);

	}
}


