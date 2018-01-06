import java.awt.*;
import java.awt.Image;
public class Effect {
       Role source;
       Role dest;
        int x;
        int y;
        int dw;
        int dh;
        int cycles;
        ImageSequence is;
        Effect (Role s, Role d, int x, int y, int dw, int dh, int cycles) {
          this.source = s;
          this.dest = d;
          this.x = x;
          this.y = y;
          this.is =new ImageSequence("pics/effect/fire/fire","png", 4);
          this.dw = dw;
          this.dh = dh;
          this.cycles= cycles;
        }
    public int getX() { return x;}
    public int getY() { return y;}
    public ImageSequence getImageSequence() { return is;}
    public int getW() {return dw;}
    public int getH() {return dh;}
    public Role getSourceRole() { return source;}
    public Role getDestRole() { return dest;}
    public void decCycles() { if (cycles>0) cycles--;}
    public int getCycles() {return cycles;}
    public  void display (Graphics g) {       
             Image frame = is.next(true);
             g.drawImage(frame,x, y, dw, dh, null ); 
    }
  }