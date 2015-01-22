package instagram;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class InstagramAPI {

	public final static String API_SCHEME = "https";
	public final static String API_HOST = "api.instagram.com/v1";

	public final static String API_PARAM_CLIENT_ID = "client_id";
	public final static String API_PARAM_LATITUDE = "lat";
	public final static String API_PARAM_LONGITUDE = "lng";
	public final static String API_PARAM_DISTANCE = "distance";
	public final static String API_PARAM_MIN_TIMESTAMP = "min_timestamp";
	public final static String API_PARAM_MAX_TIMESTAMP = "max_timestamp";

	private final static String API_ENDPOINT_POPULAR_MEDIA = "/media/popular";
	private final static String API_ENDPOINT_SEARCH_LOCATIONS = "/locations/search";
	private final static String API_ENDPOINT_SEARCH_MEDIA = "/media/search";
	
	/**
	 * HTTP Client to execute API requests
	 */
	private CloseableHttpClient httpClient;
	
	/**
	 * Key for accessing Instagram API
	 */
	private final String apiKey;

	/** 
	 * Constructor with custom API key
	 */
	public InstagramAPI(String apiKey){
		this.apiKey = apiKey;
		/* Init Http Client*/
		httpClient = HttpClients.createDefault();
	}
	
	//TODO Create unified API method with Parameters as ENUM
	
	/**
	 * 
	 * @param lat Latitude
	 * @param lng Longitude
	 * @param dist Max Distance around the center 
	 * @param minTimestamp UNIX Timestamp, lower bound for date
	 * @param maxTimestamp UNIX Timestamp, higher bound for date
	 * @return JSON Response of Instagram search request
	 */
	public String search(double lat, double lng, int dist, long minTimestamp, long maxTimestamp){
		
		URI uri;
		if(dist>5000)dist=5000;
		else if(dist < 0) dist=100;
		// TODO Check validity of lat and lng
		try {
			uri = new URIBuilder().setScheme(InstagramAPI.API_SCHEME)
					.setHost(InstagramAPI.API_HOST)
					.setPath(InstagramAPI.API_ENDPOINT_SEARCH_MEDIA)
					.setParameter(API_PARAM_LATITUDE, String.valueOf(lat))
					.setParameter(API_PARAM_LONGITUDE, String.valueOf(lng))
					.setParameter(API_PARAM_DISTANCE, String.valueOf(dist))
					.setParameter(API_PARAM_MIN_TIMESTAMP, String.valueOf(minTimestamp))
					.setParameter(API_PARAM_MAX_TIMESTAMP, String.valueOf(maxTimestamp))
					.setParameter(InstagramAPI.API_PARAM_CLIENT_ID, apiKey)
					.build();
		} catch (URISyntaxException e) {
			System.out.println("ERROR: Invalid URI");
			e.printStackTrace();
			return "";
		}
		
		return executeRequest(uri);
	}
	
	public String search(double lat, double lng, int dist){
		URI uri;
		if(dist>5000)dist=5000;
		else if(dist < 0) dist=100;
		// TODO Check validity of lat and lng
		try {
			uri = new URIBuilder().setScheme(InstagramAPI.API_SCHEME)
					.setHost(InstagramAPI.API_HOST)
					.setPath(InstagramAPI.API_ENDPOINT_SEARCH_MEDIA)
					.setParameter(API_PARAM_LATITUDE, String.valueOf(lat))
					.setParameter(API_PARAM_LONGITUDE, String.valueOf(lng))
					.setParameter(API_PARAM_DISTANCE, String.valueOf(dist))
					.setParameter(InstagramAPI.API_PARAM_CLIENT_ID, apiKey)
					.build();
		} catch (URISyntaxException e) {
			System.out.println("ERROR: Invalid URI");
			e.printStackTrace();
			return "";
		}
		return executeRequest(uri);
	}
	
	/**
	 * 
	 * @param lat Latitude
	 * @param lng Longitude
	 * @param dist Distance (max 5000)
	 * @return JSON String representing registered Instagram locations near this coordinates
	 */
	public String searchLocation(double lat, double lng, int dist){

		/* Build URI */
		URI uri;
		if(dist>5000)dist=5000;
		else if(dist < 0) dist=100;
		// TODO Check validity of lat and lng
		try {
			uri = new URIBuilder().setScheme(InstagramAPI.API_SCHEME)
					.setHost(InstagramAPI.API_HOST)
					.setPath(InstagramAPI.API_ENDPOINT_SEARCH_LOCATIONS)
					.setParameter(API_PARAM_LATITUDE, String.valueOf(lat))
					.setParameter(API_PARAM_LONGITUDE, String.valueOf(lng))
					.setParameter(API_PARAM_DISTANCE, String.valueOf(dist))
					.setParameter(InstagramAPI.API_PARAM_CLIENT_ID, apiKey)
					.build();
		} catch (URISyntaxException e) {
			System.out.println("ERROR: Invalid URI");
			e.printStackTrace();
			return "";
		}
		
		return executeRequest(uri);
	}

	/** Return JSON String of Instagram of Popular Media API Entrypoint */
	public String getPopularMedia(){
		
		/* Build URI */
		URI uri;
		try {
			uri = new URIBuilder().setScheme(InstagramAPI.API_SCHEME)
					.setHost(InstagramAPI.API_HOST)
					.setPath(InstagramAPI.API_ENDPOINT_POPULAR_MEDIA)
					.setParameter(InstagramAPI.API_PARAM_CLIENT_ID, apiKey)
					.build();
		} catch (URISyntaxException e) {
			System.out.println("ERROR: Invalid URI");
			e.printStackTrace();
			return "";
		}

		return executeRequest(uri);
	}
	
	private String executeRequest(URI uri){
		String jsonResponse;
		
		/* GET Method */
		HttpGet httpget = new HttpGet(uri);
		System.out.println(httpget.getURI());
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(httpget);
			jsonResponse = IOUtils.toString(response.getEntity().getContent());
			response.close();
			return jsonResponse;
		} catch (IOException | IllegalStateException e) {
			System.out.println("ERROR: Invalid HTTP GET Method");
			e.printStackTrace();
			return "";
		}
	}
}
