package gui;

import googlemaps.JavascriptAPI;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Francois Philipp
 *
 * Browser container for GoogleMaps 
 */
public class GoogleMapsBrowser extends Browser {
	
	private final static String GOOGLEMAPS_PARAM_API_KEY = "ParamAPIKey";
	private final static String GOOGLEMAPS_PARAM_DEFAULT_LAT = "ParamDftLat";
	private final static String GOOGLEMAPS_PARAM_DEFAULT_LNG = "ParamDftLng";
	
	private final static double DEFAULT_LAT = 48.12910855261931;
	private final static double DEFAULT_LNG = 11.621398363378148;
	
	/** Constructor */
	public GoogleMapsBrowser(Composite parent) {
		super(parent, SWT.NONE);
		
		/* Add HTML : HTML code contains API Key and basic javascript for interaction with the map */
		try {
			/* Read HTML Template */
			String html = FileUtils.readFileToString(new File("html/maps.html"));
			
			html = filloutTemplate(html);
			
			this.setText(html);
		} catch (IOException e) {
			System.out.println("[ERROR] HTML content for GoogleMapsBrowser can not be found");
			e.printStackTrace();
		}
		
		/* Internal Java function that can be called from Javascript  */ 
		new GetCoordinatesFunction(this, "getCoordinates");
	}
	
	/** Disable the check that prevents subclassing of SWT components */
	protected void checkSubclass() {}

	/** Replace given Parameters in HTML with custom/default values */
	private String filloutTemplate(String html){
		
		html = html.replaceFirst(GOOGLEMAPS_PARAM_API_KEY, JavascriptAPI.getLocalAPIKey());
		html = html.replaceFirst(GOOGLEMAPS_PARAM_DEFAULT_LAT, String.valueOf(DEFAULT_LAT));
		html = html.replaceFirst(GOOGLEMAPS_PARAM_DEFAULT_LNG, String.valueOf(DEFAULT_LNG));

		System.out.println(html);
		return html;
	}
	
	/**
	 * 
	 * @author Francois Philipp
	 * Internal class collecting coordinates when Google Maps is right clicked
	 */
	static class GetCoordinatesFunction extends BrowserFunction {
		GetCoordinatesFunction (Browser browser, String name) {
			super (browser, name);
		}
		@Override
		public Object function (Object[] arguments) {
			System.out.println ("lat: " + ((Number)arguments[0]).doubleValue() + ", lng: " + ((Number)arguments[1]).doubleValue());
			return null;
		}
	}

}
