package instagram;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;

import org.apache.commons.io.FileUtils;

public class InstagramImage {

	private String filter;
	private String username;
	private long userid;
	private long id;
	private long createdTime;
	private String url;
	private String thumbnail;
	
	private boolean valid;

	private double latitude;
	private double longitude;
		
	public InstagramImage(String id){
		setId(id);
		valid = true; //By Default, the image is considered as valid
	}
	
	/** User readable String describing the Instagram image */
	public String description(){
		if(isLocalized()){
			return  "ID: "+id+"\n"+
				    "User: "+username+"\n"+
					"UserID: "+userid+"\n"+
					"Filter: "+filter+"\n"+
				    "Created on: "+ getCreatedTimeAsString()+"\n"+
				    "Latitude: "+latitude+"\n"+
				    "Longitude: "+longitude+"\n"+
				    "Thumbnail: "+thumbnail+"\n"+
				    "URL: "+url+"\n";
		}else{
			return  "ID: "+id+"\n"+
				    "User: "+username+"\n"+
					"UserID: "+userid+"\n"+
					"Filter: "+filter+"\n"+
				    "Created on: "+ getCreatedTimeAsString()+"\n"+
				    "Thumbnail: "+thumbnail+"\n"+
				    "URL: "+url+"\n";
		}
	}
	
	public boolean downloadImage(boolean thumb) throws IOException {
		URL url;
		if(thumb){
			url = new URL(thumbnail);
		}else{
			url = new URL(this.url);
		}
		
		InputStream is;
		try{
			 is = url.openStream();
		}catch(FileNotFoundException e){
			/* URL not valid or file not available anymore */
			valid = false;
			System.out.println("Invalid Image");
			return false;
		}
		
		OutputStream os = new FileOutputStream(getFilename(thumb));
	 
		byte[] b = new byte[2048];
		int length;
	 
		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
	 
		is.close();
		os.close();
		return true; /*Success*/
	}
	
	public String getFilename(boolean thumb){
		URL url;
		try{
			if(thumb){
				url = new URL(thumbnail);
			}else{
				url = new URL(this.url);
			}
			String fileName = url.getFile();
			return "./figures" + fileName.substring(fileName.lastIndexOf("/"));
		}catch (IOException e){
			e.printStackTrace();
			return "./figures/default.jpg";
		}
		
	}
	
	public boolean isLocalized(){
		return latitude!=0.0;
	}
	
	public boolean isValid(){
		return valid;
	}
	
	public String getHTMLInfoDescription(){
		String infoRef;
		try{
			infoRef = FileUtils.readFileToString(new File("html/infoWindow.html"));
		}catch(IOException e){
			e.printStackTrace();
			infoRef = "";
		}
		infoRef = infoRef.replaceFirst("keyUser", username);
		infoRef = infoRef.replaceFirst("keyFilter", filter);
		infoRef = infoRef.replaceFirst("keyID", String.valueOf(id));
		infoRef = infoRef.replaceFirst("keyURL", thumbnail);
		infoRef = infoRef.replaceFirst("keyDate", getCreatedTimeAsString());
		return infoRef;
	}
	
	public byte[] getByteData(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeLong(id);
			dos.writeLong(userid);
			dos.writeUTF(filter);
			dos.writeUTF(username);
			dos.writeLong(createdTime);
			dos.writeUTF(url);
			dos.writeUTF(thumbnail);
			dos.writeDouble(latitude);
			dos.writeDouble(longitude);
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	public static InstagramImage readByteData(DataInputStream dis){
		InstagramImage image;

		try {
			long id = dis.readLong();
    	    long user_id = dis.readLong();
    	    image = new InstagramImage(id+"_"+user_id);
    	    image.setFilter(dis.readUTF());
    	    image.setUsername(dis.readUTF());
    	    image.setCreatedTime(dis.readLong());
    	    image.setUrl(dis.readUTF());
    	    image.setThumbnail(dis.readUTF());
    	    image.setLatitude(dis.readDouble());
    	    image.setLongitude(dis.readDouble());
    	    return image;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String user) {
		this.username = user;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long user) {
		this.userid = user;
	}
	public String getId() {
		return id+"_"+userid;
	}
	public long getLongId() {
		return id;
	}
	public void setId(String id) {
		this.id = Long.valueOf(id.substring(0, id.indexOf("_")));
		this.userid = Long.valueOf(id.substring(id.indexOf("_")+1));
	}
	public long getCreatedTime() {
		return createdTime;
	}
	public String getCreatedTimeAsString(){
		return DateFormat.getDateInstance().format((new java.util.Date((long) createdTime*1000)));
	}
	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
