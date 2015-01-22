package googlemaps;

import gui.GoogleMapsBrowser;
import main.Main;


public abstract class GoogleMapsEventHandler {

	protected GoogleMapsBrowser browser;
	
	public void handleEvent(Object[] inputs) {
		String eventType = (String) inputs[0];
		switch(eventType){
			case "doubleclick": 
				if(Main.DEBUG)
					System.out.println("Double Click"); 
				
				/* Parse inputs */
				doubleclick(((Number)inputs[1]).doubleValue(),((Number)inputs[2]).doubleValue());
				break;
			case "rightclick": 
				if(Main.DEBUG)
					System.out.println("Right Click");
				
				/* Parse inputs */
				/* Parse inputs */
				rightclick(((Number)inputs[1]).doubleValue(),((Number)inputs[2]).doubleValue());
				break;
			default: System.out.println("Unknown Event"); 
		}
		
	}
	
	public void setBrowser(GoogleMapsBrowser browser){
		this.browser = browser;
	}
	
	protected abstract void doubleclick(double lat,double lng);
	protected abstract void rightclick(double lat,double lng);
	


}
