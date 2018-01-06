package model.sprite;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageSequence {
	private String baseFileName;
	private String fileType; 
	private int picAmount;
	private Image[] images; 
	private int index = -1; 

	public ImageSequence(String bfname, String fext, int n) {
		images = new Image[n];
		picAmount = n;
		baseFileName = bfname;
		fileType = fext;
		try {
			for (int i = 0; i < picAmount; i++)
				images[i] = ImageIO.read(new File(bfname + i + "." + fext));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ImageSequence(Image image, int n) {
		images = new Image[n];
		picAmount = n;
		for (int i = 0; i < picAmount; i++)
			images[i] = image;
	}

	public Image getImage() {
		index = index == 3 ? -1 : index;
		return images[index + 1];
	}

	public Image nextImage() { 
		index = (index + 1) % picAmount; 
		return images[index];
	}

}
