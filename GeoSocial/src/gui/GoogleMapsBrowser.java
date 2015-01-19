package gui;

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
	
	/** Constructor */
	public GoogleMapsBrowser(Composite parent) {
		super(parent, SWT.NONE);
		
		/* Add HTML : HTML code contains API Key and basic javascript for interaction with the map */
		try {
			String html = FileUtils.readFileToString(new File("html/maps.html"));
			//TODO Implement a function to customize the HTML String, replacing keys by specific parameters
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
