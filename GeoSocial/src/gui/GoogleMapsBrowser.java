package gui;

import googlemaps.GoogleMapsEventHandler;
import googlemaps.JavascriptAPI;

import java.io.File;
import java.io.IOException;

import main.Main;

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
	/* Unused */
	//private final static String GOOGLEMAPS_PARAM_MARKER = "ParamMarker";
	
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
	}
	
	/** Constructor */
	public GoogleMapsBrowser(Composite parent, GoogleMapsEventHandler eventHandler) {
		super(parent, SWT.NONE);
		eventHandler.setBrowser(this);
		
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
		new GoogleMapsEventFunction(this, "notifyEvent", eventHandler);
	}
	
	/** Disable the check that prevents subclassing of SWT components */
	protected void checkSubclass() {}

	/** Replace given Parameters in HTML with custom/default values */
	private String filloutTemplate(String html){
		
		html = html.replaceFirst(GOOGLEMAPS_PARAM_API_KEY, JavascriptAPI.getLocalAPIKey());
		html = html.replaceFirst(GOOGLEMAPS_PARAM_DEFAULT_LAT, String.valueOf(DEFAULT_LAT));
		html = html.replaceFirst(GOOGLEMAPS_PARAM_DEFAULT_LNG, String.valueOf(DEFAULT_LNG));
		
		if(Main.DEBUG)
			System.out.println(html);
		return html;
	}
	
	/**
	 * 
	 * @author Francois Philipp
	 * Internal class handling GoogleMaps event in a generic way
	 */
	static class GoogleMapsEventFunction extends BrowserFunction {
		private GoogleMapsEventHandler eventHandler;
		GoogleMapsEventFunction (Browser browser, String name, GoogleMapsEventHandler eventHandler) {
			super (browser, name);
			this.eventHandler = eventHandler;
		}
		@Override
		public Object function (Object[] arguments) {
			eventHandler.handleEvent(arguments);
			return null;
		}
	}

}
