
/**
 * Write a description of class State here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class State {
    private int x;
    private int y;
    private ImageSequence is;
    private Controller oberver;
    private Spirit self;
    
    State(Controller ctl, Spirit sp, int x, int y, ImageSequence seq) {
        this.x = x;
        this.y = y;
        this.is =seq;
        this.self = sp;
        oberver = ctl;
        oberver.notify(this);
    }
   
    
    public void setState(int x, int y, ImageSequence is) { this.x =x; this.y =y;this.is = is;oberver.notify(this); }
    public int getX() { return x;}
    public int getY() { return y;}
    public ImageSequence getImageSequence() { return is;}
    public Spirit getSelf() {return self;}
    
}
