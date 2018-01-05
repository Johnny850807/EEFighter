package model.sprite;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageSequence {
	private String baseFileName; // 圖片檔主名
	private String fileType; // 圖片檔附加名(如png檔)
	private int picAmount; // 分鏡畫面檔個數
	private Image[] images; // 儲存分鏡畫面圖檔(Image物件陣列)
	private int index = -1; // 分鏡畫面索引

	public ImageSequence(String bfname, String fext, int n) {
		images = new Image[n];
		picAmount = n;
		baseFileName = bfname;
		fileType = fext;
		try {
			for (int i = 0; i < picAmount; i++)
				images[i] = ImageIO.read(new File(bfname + i + "." + fext)); // load the pic files
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Image next() { //取得下一張畫面
		 index = (index+1) % picAmount; //計算下一畫面索引
		 return images[index];
		 }
		 public void reset () { index = -1;} //重設為起始狀態
}
