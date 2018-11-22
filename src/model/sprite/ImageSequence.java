package model.sprite;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageSequence {
	private static final int BASE = 8;
	private int picAmount;
	private Image[] images;
	private int returnNumber = 0;
	private int index = 0;

	public ImageSequence(Image[] images, int amount) {
		this.images = images;
		picAmount = amount;
	}

	public Image nextImage() {
		returnNumber = returnNumber + 1 == BASE ? 0 : returnNumber + 1 ;
		if (returnNumber == BASE - 1)
			index++;
		return images[index % picAmount];
	}

}
