import java.util.*;
import java.awt.*;
import java.awt.Image;
/**
 * Write a description of class Spirit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spirit extends Thread implements Role, Cloneable {
   private final int MIN_X=0;
   private final int MIN_Y=0;
   private final int MAX_X=946;
   private final int MAX_Y=650;
   private final int IDW=3;//increase w per eat
   private final int IDH=3;//increase w per eat
   
   protected Thread me;
   protected ImageSequence[][] actionImgs  =  {
       { new ImageSequence("pics/halt0/halt","png", 1),  new ImageSequence("pics/halt1/halt","png", 1), 
         new ImageSequence("pics/halt2/halt","png", 1),  new ImageSequence("pics/halt3/halt","png", 1)},
       { new ImageSequence("pics/walk0/walk","png", 3), new ImageSequence("pics/walk1/walk","png", 3),
         new ImageSequence("pics/walk2/walk","png", 3),new ImageSequence("pics/walk3/walk","png", 3)}}; 
   protected Controller ctl ;      
   protected int curX;
   protected int curY;
   protected ActionType curAct;
   protected DIR4 curDir;
   protected final int deltaDist= 5; //5 px at each move
   protected Model state; //Note that State class is confict with theThread.State in name, so wu use Model here
   protected boolean  startGame=false;
   protected ArrayList<Request> requests;
   
   protected class Request {
       private ActionType act;
       private DIR4 dir;
       Request(ActionType act, DIR4 dir) {
           this.act = act;
           this.dir = dir;
        }
    }
    
   Spirit(Controller ctl) { //for red spirit
       this.ctl = ctl;
       curAct = ActionType.HALT;
       curDir=DIR4.EAST;
       curX = (int)(Math.random()*(MAX_X-MIN_X) + MIN_X); //located randomly of the screen
       curY = (int)(Math.random()*(MAX_Y-MIN_Y) + MIN_Y);
       
       state = new Model(ctl, this, curX, curY,30, 30, actionImgs[0][1]);     
       me=this;
        go();
       requests=new ArrayList<>();
   }
   Spirit(Controller ctl, boolean player) {//for ywllow spirit
       this.ctl = ctl;
       curAct = ActionType.HALT;
       curDir=DIR4.EAST;
       curX=(int) (MIN_X + MAX_X)/2; //located at center of the screen
       curY=(int) (MIN_Y + MAX_Y)/2;
       
       state = new Model(ctl, this, curX, curY,30, 30, actionImgs[0][1]);     
       me=this;
        go();
       requests=new ArrayList<>();
   }
   //for requests from users
   public void addRequest(ActionType act, DIR4 dir) {
       if (requests==null) requests=new ArrayList<>();
      //??????????????????? //<----- add a new request
    }
  public void processRequest() {
         if (requests.size()>0) {
           Request r = requests.remove(0);
           //??????????????????????  //<------------- execute the request
        }
     
    }
   public Model getModel() { return state;}
   
   public void run() { //main of spirit
           if (requests.size()>0) 
              processRequest();//process user requests
           else //go on the same act
             move(curAct, curDir);    
    }
    
   public void bigger() {
       state.setW(state.getW()+IDW);
       state.setH(state.getH()+IDH);
    }
   public  void go() {
       startGame=true;
       int d =(int)( Math.random()*4); //random choose a direction
       DIR4[] dir = DIR4.values();
       //?????????????????????  //<-------------- start to walk
    }
    public  void goOn() {
      if (curAct==ActionType.HALT) 
          curAct = ActionType.WALK;
      //?????????????????????  //<-------------- go on
    }
    
    public  void halt() {
       move(ActionType.HALT, curDir); 
    }
    
   public void end() {
       startGame=false;
       me = null;
    }
   public void changeImages() {
       actionImgs[0][0]  =  new ImageSequence("pics/food/halt0/halt","png", 1);
        actionImgs[0][1]  = new ImageSequence("pics/food/halt1/halt","png", 1) ;
        actionImgs[0][2]  = new ImageSequence("pics/food/halt2/halt","png", 1);
        actionImgs[0][3]  = new ImageSequence("pics/food/halt3/halt","png", 1);
    
        actionImgs[1][0]  =  new ImageSequence("pics/food/walk0/walk","png", 3);
        actionImgs[1][1]  =  new ImageSequence("pics/food/walk1/walk","png", 3) ;
        actionImgs[1][2]  = new ImageSequence("pics/food/walk2/walk","png", 3);
        actionImgs[1][3]  = new ImageSequence("pics/food/walk3/walk","png", 3);
    }
    
   public  void move(ActionType act, DIR4 dir) {//will change its state of location
       boolean bounced = false;
       ImageSequence seq=null;
       switch (curAct) {
           case HALT: 
                switch (act) {
                    case HALT: 
                       seq = actionImgs[ActionType.HALT.ordinal()][dir.ordinal()] ; //get the action frames
                       break; //nothing changed
                    case WALK:
                       bounced =updatePosition(dir);
                       //seq =?????????????????????????????????????  //<---------- get the action frames for next state
                       seq.reset();
                       break;
                }
             break;
           case WALK: 
               switch (act) {
                    case HALT:
                       seq = actionImgs[ActionType.HALT.ordinal()][dir.ordinal()] ; //get the action frames
                       break; //nothing changed
                    case WALK:                
                       bounced =updatePosition(dir);   
                        seq = actionImgs[ActionType.WALK.ordinal()][dir.ordinal()] ; //get the action frames
                       if (dir != curDir)  seq.reset();
                } 
             break;    
        }
        curAct = act; //this will change interal state of the object spirit
        if (!bounced) 
            curDir = dir;
        else;
           //curDir = ?????????????????????????? //<-----------------if bounced what will it be ?
       
        //state.????????????????????????????; //<---------------change state will notify the conntroller and cased to wait
     
    }
    private DIR4 reverseDir(DIR4 dir) {
        switch (dir) {
            case NORTH: return DIR4.SOUTH;
            case SOUTH: return DIR4.NORTH;
            case EAST: return DIR4.WEST;
            case WEST: return DIR4.EAST;           
        }
        return null;
    }
    //change position and direction if necessary
    //check if bounced event happens?
    private boolean updatePosition(DIR4 dir) {
        if  (dir ==  DIR4.NORTH)
           if ( (curY -deltaDist ) >= MIN_Y )
              curY -= deltaDist; 
           else  {
              curY = MIN_Y;
              return true;
            }
             
        if  (dir ==  DIR4.SOUTH)
           if  ( (curY + deltaDist +state.getH()) <= MAX_Y )
              curY += deltaDist;
         else  {
              curY = MAX_Y-state.getH();
              return true;
            }
        if (dir ==  DIR4.EAST) 
            if ( (curX +deltaDist+state.getW() ) <= MAX_X ) 
                curX += deltaDist;
         else  {
              curX = MAX_X-state.getW();
              return true;
            }
        if  (dir ==  DIR4.WEST)
            if ( (curX -deltaDist ) >= MIN_X ) 
               curX -= deltaDist;
        else  {
              curX = MIN_X;
              return true;
            }
    
    return false;
   }
   public synchronized  void display(Graphics g) {
          int x = state.getX();
          int y = state.getY();
          int dw = state.getW(); //width of the object
          int dh = state.getH(); //height of the object
          ImageSequence is = state.getImageSequence();
          Image frame =is.next(true);  //<---------------------------- get the next frame
          g.drawImage(frame,x, y, dw, dh, null ); 
    }
}
