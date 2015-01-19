package gui;

import googlemaps.JavascriptAPI;
import instagram.ImageDatabase;
import instagram.InstagramImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * 
 * @author Francois Philipp
 *
 */
public class InstagramBrowser extends ScrolledComposite {

	/** 
	 * Number of Instagram Images to load simultaneously
	 */
	private int nbImages;

	/**
	 * 
	 * @param parent Composite
	 * @param database Database of images (TODO for test only remove)
	 * @param browser TODO Should not be there, should be connected to browser in top component
	 */
	public InstagramBrowser(Composite parent, ImageDatabase database, Browser browser) {
		super(parent, SWT.V_SCROLL);
		setExpandHorizontal(true);
		setExpandVertical(true);
		final Composite composite = new Composite(this, SWT.NONE);
		setContent(composite);
		nbImages = 4;
		composite.setLayout(new GridLayout(2,true));

		for(int i = 0 ; i<nbImages;i++){
			Label imageLabel = new Label(composite, SWT.BORDER);
			imageLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
			InstagramImage image = database.getAndDownloadRandomImage(true);
			imageLabel.setImage(new Image(composite.getDisplay(),image.getFilename(true)));
			imageLabel.addMouseListener(new ImageClickListener(image,browser));
		}

		Button button = new Button(composite, SWT.PUSH);
		GridData gdButton = new GridData();
		gdButton.horizontalSpan = 2;
		button.setLayoutData(gdButton);
		button.setText("Load More ..."); 
		button.addSelectionListener(new LoadMoreSelectionListener(button, composite, database, browser));
		setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		pack();
	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * 
	 * @author Francois Philipp
	 *
	 * Special class to performs customs actions when an InstagramImage is clicked
	 */
	public class ImageClickListener implements MouseListener{

		private Browser browser;
		private InstagramImage image;

		public ImageClickListener(InstagramImage image, Browser browser){
			this.browser = browser;
			this.image = image;
		}

		@Override
		public void mouseDoubleClick(MouseEvent arg0) {

		}

		@Override
		public void mouseDown(MouseEvent arg0) {
			if(image.isLocalized()){
				browser.execute(JavascriptAPI.centerMap(image.getLatitude(), image.getLongitude()));
				browser.execute(JavascriptAPI.addMarker(image.getLatitude(), image.getLongitude()));
			}else{
				System.out.println("Image Not Localized");
			}
		}

		@Override
		public void mouseUp(MouseEvent arg0) {

		}

	}

	/**
	 * 
	 * @author Francois Philipp
	 *
	 * Selection Listener that dispose the Button it was originally assigned, add more content, and assign itself to a newly created button
	 */
	public class LoadMoreSelectionListener implements SelectionListener{

		private Button button;
		private Composite composite;
		private ImageDatabase database;
		private Browser browser;

		public LoadMoreSelectionListener(Button button, Composite composite, ImageDatabase database, Browser browser) {
			this.button = button;
			this.composite = composite;
			this.database = database;
			this.browser = browser;
		}

		public void widgetDefaultSelected(SelectionEvent arg0) {}

		public void widgetSelected(SelectionEvent arg0) {
			for(int i = 0 ; i<nbImages;i++){
				Label imageLabel = new Label(composite, SWT.BORDER);
				imageLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
				InstagramImage image = database.getAndDownloadRandomImage(true);
				imageLabel.setImage(new Image(composite.getDisplay(),image.getFilename(true)));
				imageLabel.addMouseListener(new ImageClickListener(image,browser));
			}
			button.dispose();
			button = new Button(composite, SWT.PUSH);
			GridData gdButton = new GridData();
			gdButton.horizontalSpan = 2;
			button.setLayoutData(gdButton);
			button.setText("Load More ...");
			button.addSelectionListener(this);
			setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			composite.layout();

		}
	}
}

