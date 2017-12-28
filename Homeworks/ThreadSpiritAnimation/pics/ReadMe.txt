Transparent Image Editor:

http://www180.lunapic.com/editor/



1. 相關套件

import java.io.*;
import javax.imageio.*;
import java.awt.Image;

2. 如何載入圖形?
  Image img;
  try {  
      img  = ImageIO.read(new File("0-9/digit0.jpg"));  //使用ImageIO物件載入圖片0-9/digit0.jpg
  } catch (Exception e) { e.printStackTrace(); //顯示錯誤訊息

3. 如何顯示圖形?

  在JPane物件中實作下列動作:

    public void paintComponent(Graphics g) {
         super.paintComponent(g);

         //======================= 你的顯示 =========================
 	 //先計算適當的位置與大小

         int w = getWidth();//get the width of the panel
         int h = getHeight(); //get the height of the panel
         dw = ..... //width per digit image
         dh=.....   //height per digit image
         x=... //圖片左上角的X作標
	 y=... //圖片左上角的Y作標
         g.drawImage(img ,x, y, dw, dh, this ); //顯示圖形物件 img 在 (x, y)位置, 寬:dw, 高: dh的空間內
						//最後參數填寫this即可
						       
        }

4.  如何播放MP3?

套件:
import javazoom.jl.player.*; // (請加入: jl1.0.1.jar檔案到BlueJ的Library)
import java.io.FileInputStream;

Sample Code for play MP3:

  try{

    FileInputStream fis = new FileInputStream("File XXXX.mp3");//加入適當的mp3檔名
    Player playMP3 = new Player(fis);

    playMP3.play();

   //playMp3.close(); 可關閉播放功能

  } catch(Exception e){System.out.println(e);}


  
====================================================


注意:

1. 座標系統
   X: 橫向座標(往右為正)
   Y: 縱向座標(往下為正)

2. 其他美化指令:
  g.setColor(Color.cyan);//設定顏色
  g.fillRoundRect(x, y, w, h, wa, ha); //實心圓邊方型: wa: 圓弧橫長 , wh: 圓弧縱長 
  g.fillRect(x, y, w, h);//實心長方型

3. 當需要重畫畫面時請執行: repaint();

    例如:本例中
private class FacePanel extends JPanel {
...
 private void setUp(int m, int s) {
         this.m = m;
         this.s = s;
 
         repaint();
     }
...
}