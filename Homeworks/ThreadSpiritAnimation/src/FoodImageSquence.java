import java.awt.*;
import java.io.*;
import javax.imageio.*;
public class FoodImageSquence implements Cloneable
{
    private String baseFileName;
    private String fileExt;
    private int numFiles;
    private Image[] imgs ;
    private int index = -1;
    /**
     * Constructor for objects of class ImageSequence
     */
    public FoodImageSquence(String bfname, String fext, int n)  {
       imgs=new Image[n];
       numFiles = n;
       baseFileName =bfname;
       fileExt=fext;
       try {
           for (int i=0; i<numFiles; i++)
              imgs[i] = ImageIO.read(new File(bfname+i+"."+fext));  //load the digits
        } catch (Exception e) {e.printStackTrace();} 
    }
    /**
     * Get the next image of the sequence
     */
    public Image next(boolean cycle) {
        if (!cycle && index == numFiles-1) {
            index = -1;
            return null;
        }
        index = (index+1) % numFiles;
        return imgs[index];
    }
    /**
     * set the index
     */
    public void reset () { index = -1;}
    
   
}
