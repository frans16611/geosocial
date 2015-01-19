package instagram;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public class ImageDatabase {

	private HashMap<Long,InstagramImage> database;
	private String filename;
	private final int DATABASE_SUCCESS=1;
	private final int DATABASE_ALREADY_THERE=0;
	//private final int DATABASE_FAIL=-1;

	public ImageDatabase(){
		database = new HashMap<Long,InstagramImage>();
	}

	public int addItem(InstagramImage image){
		if(!database.containsKey(image.getLongId())){
			database.put(image.getLongId(), image);
			return DATABASE_SUCCESS;
		}else
			return DATABASE_ALREADY_THERE;

	}

	public void removeItem(InstagramImage image){
		database.remove(image.getLongId());
	}
	
	public InstagramImage getRandomItem(){
		int item = new Random().nextInt(size());
		System.out.println("Random index "+item);
		int i=0;
		for(InstagramImage image : database.values()){
			if(i==item){
				return image;
			}
			i++;
		}
		return null;
	}
	
	public InstagramImage getAndDownloadRandomImage(){
		return getAndDownloadRandomImage(false);
	}
	
	public InstagramImage getAndDownloadRandomImage(boolean thumb){
		boolean imageAvailable=false;
		
		InstagramImage instaImage;
		
		
		do{
			instaImage = getRandomItem();
			/* Download image if not avalaible */
			try {
				if(!FileUtils.directoryContains(new File("figures"), new File(instaImage.getFilename(thumb)))){
					if(instaImage.downloadImage(thumb)){
						System.out.println("Downloading Image ...");
						imageAvailable = true;
					}else{
						System.out.println("Error! Image not available");
					}
				}else{
					imageAvailable = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}while(!imageAvailable);
			
		return instaImage;
	}
	
	public int size(){
		return database.size();
	}

	public void exportDatabase(String filename){
		File databaseFile = new File(filename);
		this.filename = filename;
		try {
			FileUtils.forceDelete(databaseFile);
			for(InstagramImage image : database.values()){

				FileUtils.writeByteArrayToFile(databaseFile, image.getByteData(),true);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void importDatabase(String filename){
		InstagramImage image;
		this.filename = filename;
		try{
			FileInputStream fis = new FileInputStream(filename);
			DataInputStream dis = new DataInputStream(fis);
		
			while(dis.available()>0)
			{
				image = InstagramImage.readByteData(dis);
				database.put(image.getLongId(), image);
			}
			
			dis.close();
			fis.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void close(){
		/* Sanity Check: if images are not valid anymore remove them from database */
		//TODO Maybe check via Instagram API if the URL didnt change
		int i = 0;
		List<Long> unvalidKeys = new ArrayList<Long>();
		for(InstagramImage image : database.values()){
			if(!image.isValid()){
				unvalidKeys.add(image.getLongId());
			}
		}
		for(long id : unvalidKeys){
			database.remove(id);
			i++;		
		}
		System.out.println("Removing "+i+" invalid images");
		exportDatabase(filename);
	}

}
