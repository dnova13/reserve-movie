package reserve;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Thumbnail {

	public Thumbnail(){
		
	}
	
	public static void createImage(int width , int height, String loadFile, String saveFile, int zoom) throws IOException{
		
		File save = new File(saveFile);
		FileInputStream fis = new FileInputStream(loadFile);
		BufferedImage im = ImageIO.read(fis);
		
		if (zoom<=0) zoom = 1;
		
		int width1 = width;//im.getWidth() / zoom;
		int height1 = height;//im.getHeight() / zoom;
		
		BufferedImage thumb = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = thumb.createGraphics();
		
		g2.drawImage(im, 0, 0, width1, height1, null);
		ImageIO.write(thumb, "jpg", save);
	}
	
	public static void main(String args[]){
		
//		String loadFile[] = new String[ordDataList.size()];
//		String saveFile[] = new String[ordDataList.size()];
//		
//		loadFile[i] = "images/movies/"+fno[i]+".jpg";
//		saveFile[i] = "images/thumb/"+fno[i]+".jpg";
//		
//		try {
//			Thumbnail.createImage(150,200, loadFile[i], saveFile[i], 10);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		
		
//		String loadFile = "images/movies/20001.jpg";
//		String saveFile = "images/thumb/20001.jpg";
//		int zoom = 5;
//		
//		try {
//			Thumbnail.createImage(loadFile, saveFile, zoom);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
