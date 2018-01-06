package model.sprite;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageSequence {
	private String baseFileName; // �Ϥ��ɥD�W
	private String fileType; // �Ϥ��ɪ��[�W(�ppng��)
	private int picAmount; // ����e���ɭӼ�
	private Image[] images; // �x�s����e������(Image����}�C)
	private int index = -1; // ����e������

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
	
	public Image next() { //���o�U�@�i�e��
		 index = (index+1) % picAmount; //�p��U�@�e������
		 return images[index];
		 }
		 public void reset () { index = -1;} //���]���_�l���A
}
