package instagram;


public class InstagramLocation {

	private double longitude;
	private double latitude;
	private String name;
	private long id;

	public InstagramLocation(long id,double lat,double lng, String name){
		this.id = id;
		longitude = lng;
		latitude = lat;
		this.name = name;
	}

	public String description(){
		return  "ID: "+id+"\n"+
				"Latitude: "+latitude+"\n"+
				"Longitude: "+longitude+"\n"+
				"Name: "+name+"\n";
	}

	public long getID(){
		return id;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getName() {
		return name;
	}


}
