Transparent Image Editor:

http://www180.lunapic.com/editor/



1. �����M��

import java.io.*;
import javax.imageio.*;
import java.awt.Image;

2. �p����J�ϧ�?
  Image img;
  try {  
      img  = ImageIO.read(new File("0-9/digit0.jpg"));  //�ϥ�ImageIO������J�Ϥ�0-9/digit0.jpg
  } catch (Exception e) { e.printStackTrace(); //��ܿ��~�T��

3. �p����ܹϧ�?

  �bJPane���󤤹�@�U�C�ʧ@:

    public void paintComponent(Graphics g) {
         super.paintComponent(g);

         //======================= �A����� =========================
 	 //���p��A����m�P�j�p

         int w = getWidth();//get the width of the panel
         int h = getHeight(); //get the height of the panel
         dw = ..... //width per digit image
         dh=.....   //height per digit image
         x=... //�Ϥ����W����X�@��
	 y=... //�Ϥ����W����Y�@��
         g.drawImage(img ,x, y, dw, dh, this ); //��ܹϧΪ��� img �b (x, y)��m, �e:dw, ��: dh���Ŷ���
						//�̫�Ѽƶ�gthis�Y�i
						       
        }

4.  �p�󼽩�MP3?

�M��:
import javazoom.jl.player.*; // (�Х[�J: jl1.0.1.jar�ɮר�BlueJ��Library)
import java.io.FileInputStream;

Sample Code for play MP3:

  try{

    FileInputStream fis = new FileInputStream("File XXXX.mp3");//�[�J�A��mp3�ɦW
    Player playMP3 = new Player(fis);

    playMP3.play();

   //playMp3.close(); �i��������\��

  } catch(Exception e){System.out.println(e);}


  
====================================================


�`�N:

1. �y�Шt��
   X: ��V�y��(���k����)
   Y: �a�V�y��(���U����)

2. ��L���ƫ��O:
  g.setColor(Color.cyan);//�]�w�C��
  g.fillRoundRect(x, y, w, h, wa, ha); //��߶���諬: wa: �꩷��� , wh: �꩷�a�� 
  g.fillRect(x, y, w, h);//��ߪ��諬

3. ��ݭn���e�e���ɽа���: repaint();

    �Ҧp:���Ҥ�
private class FacePanel extends JPanel {
...
 private void setUp(int m, int s) {
         this.m = m;
         this.s = s;
 
         repaint();
     }
...
}