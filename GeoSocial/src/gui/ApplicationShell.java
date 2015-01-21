package gui;

import instagram.ImageDatabase;
import googlemaps.JavascriptAPI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ApplicationShell extends Shell {
	
	public final static String SHELL_TITLE = "GeoSocial";
	
	private ImageDatabase database;

	/**
	 * Create the shell.
	 * @param display
	 */
	public ApplicationShell(Display display,ImageDatabase database) {
		super(display, SWT.SHELL_TRIM);
		this.database = database;
		createContents();
		setSize(1000,500);

		open();
	     while (!isDisposed ()) {
				if (!display.readAndDispatch ()) display.sleep (); 
			} 
		display.dispose ();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("GeoSocial");
		setLayout(new GridLayout(2, false));
		
		GoogleMapsBrowser browser = new GoogleMapsBrowser(this);
		GridData gd_browser = new GridData(GridData.FILL_BOTH);
		browser.setLayoutData(gd_browser);
		
		
		new InstagramBrowser(this,database,browser);
		
		
		Button button = new Button(this,SWT.PUSH);
		button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		button.setText("Update Coordinates");
		
		button.addSelectionListener(new SelectionListener() {

	        public void widgetSelected(SelectionEvent event) {
	        	browser.execute(JavascriptAPI.centerMap(34.397, 35.644));
	        	browser.execute(JavascriptAPI.addMarker(34.397, 35.644,JavascriptAPI.BLUE_MARKER));
	        }

			public void widgetDefaultSelected(SelectionEvent arg0) {}

	      });
		

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	

	
}
