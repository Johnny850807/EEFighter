package model.sprite;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageSequence {
	private int picAmount;
	private Image[] images;
	private int index = -1;

	public ImageSequence(Image[] images, int amount) {
		this.images = images;
		picAmount = amount;
	}

	public ImageSequence(Image image, int n) {
		images = new Image[n];
		picAmount = n;
		for (int i = 0; i < picAmount; i++)
			images[i] = image;
	}

	public Image getImage() {
		index = (index == picAmount - 1) ? -1 : index;
		return images[index + 1];
	}

	public Image nextImage() {
		index = (index + 1) % picAmount;
		return images[index];
	}

}
