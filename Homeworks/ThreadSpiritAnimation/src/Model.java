
/**
 * Write a description of class State here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Model {
    private int x;
    private int y;
    private ImageSequence is;
    private Controller oberver;
    private Spirit self;
    private int objectW;
    private int objectH;
    
    Model(Controller ctl, Spirit sp, int x, int y, int dw, int dh, ImageSequence seq) {
        this.x = x;
        this.y = y;
        this.is =seq;
        this.objectW = dw;
        this.objectH = dh;
        this.self = sp;
        oberver = ctl;
    }
   
    
    public synchronized void setState(int x, int y, ImageSequence is) { this.x =x; this.y =y;this.is = is;oberver.notify(this); }
    public int getX() { return x;}
    public int getY() { return y;}
    public ImageSequence getImageSequence() { return is;}
    public Spirit getSelf() {return self;}
    public int getW() {return objectW;}
    public int getH() {return objectH;}
    public void setW(int w) { objectW=w;}
    public void setH(int h) { objectH=h;}
    
}
