package model.sprite;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageSequence {
	private int picAmount;
	private Image[] images;
	private int returnNumber = 0;
	private int index = 0;

	public ImageSequence(Image[] images, int amount) {
		this.images = images;
		picAmount = amount;
	}

	public Image nextImage() {
		returnNumber++;
		if (returnNumber % 4 == 0)
			index++;
		return images[index % picAmount];
	}

}
