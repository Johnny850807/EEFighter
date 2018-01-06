import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * Write a description of class Controller here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Controller extends Thread {
    private View viewer;
    private Spirit spirit;
    private ArrayList<Role> roles = new ArrayList<>();
    private HashMap<Role,Effect> effects =  new HashMap<>();
    private boolean[] checkStates;
    private Thread me;
    private boolean gameStart = false;
    private int numFood =500;
    private long delta=20;
    /**
     * The main program here
     */
    public void run() {
       long before;
       while (Thread.currentThread() == me) { 
               before = System.currentTimeMillis();
               if (gameStart) {
                checkAllStateUpdate();
         
                chkConflict(spirit); //check whether conflict, and updates the effects
     
                viewer.refresh(effects); //call viewer to refresh the screen
       
                clearEffects();
              }
              long after =System.currentTimeMillis();
              long sleep =  (delta - (after-before) );
              if (sleep<0) 
                 sleep = 3;     
              try { Thread.sleep(sleep);} catch(InterruptedException e) {System.out.println(e); break;}//隡銝�蝘��
        }
    }
    public void startGame() {
        gameStart = true;
    }
    public void setNumFood(int n) {
        numFood = n;
    }
    public void prepare() {
		// prepare the roles
		addRole(new Spirit(this, true)); // add the player
		for (int i = 0; i < numFood; i++) {
			// FoodImageSquence sp = new FoodImageSquence(); //<------- Add Red Spirits here
			// addRole(sp);
		}
		viewer.setRoles(roles);
		this.setPlayer((Spirit) roles.get(0)); // get the first as player in Controller
		viewer.setPlayer((Spirit) roles.get(0)); // get the first as

  
    }
    
    
    public Controller( View v) {
        super("Controller");
        viewer = v;
        me =this;
    }
    public void setPlayer(Spirit sp){
        spirit =sp;
    }
   //check if all roles have updated state 
   private  boolean checkAllStateUpdate() {
     for (Role r: roles) {
          //<------ Ask all spirits to make next move()
        }
        return true;
    }
    
    
   //Synchronized the state refresh action 
    public  void notify(Model md) {
     //do nothing here 
    }
    public synchronized void addRole(Role sp) {
        roles.add(sp);
    }
        
    public  void end() {
        for (Role r: roles)
          r.end();
    }
    public void start(Spirit sp) {
         sp.go();    
    }
    public void goOn(Spirit sp) {
       //???????????????? //<----- ask spirit to go on
    }
    public void halt(Spirit sp) {
         //??????? //<----- ask spirit to halt
    }
    
    public void move(Spirit sp, ActionType act, DIR4 dir) {
       //??????????? // <--------------- ask spirit to form a request 
    }
    private synchronized void clearEffects() {
     Object[] list = effects.values().toArray();
     for (int i=0; i< list.length; i++) {
         Effect e =(Effect) list[i];
         e.decCycles();
         if (true) {      //<------ check if effect cycles reaches zero
             Role r2 = e.getDestRole();   
             r2.end();//stop the role  
             roles.remove(r2);                     
             effects.remove(r2); //remove the effects
                 
            }
      }

    }
    
    
    private synchronized boolean chkConflict(Spirit sp) {
            Role r1 =(Role) sp;
            Model md1 = r1.getModel();
            int x1 = md1.getX();
            int y1 = md1.getY();
            int dw1 = md1.getW();
            int dh1 = md1.getH();
            for (Role r2: roles) {
                  if (r1==r2) continue;
                  if (effects.containsKey(r2)) continue;
                  Model md2 = r2.getModel();
                  int x2 = md2.getX();
                  int y2 = md2.getY();
                  int dw2 = md2.getW();
                  int dh2 = md2.getH();
                  if ( (x1+dw1) < (x2) || (x2+dw2) < (x1))
                      continue; //no conflict
                  else if    ( (y1+dh1) < (y2) || (y2+dh2) < (y1))
                     continue; //no conflict
                  else {// conflict detected
                      effects.put(r2, new Effect(r1, r2, (int)(x1+x2)/2, (int)(y1+y2)/2,50, 50, 6) );  //<-------- effects for 6 cycles
                      ((Spirit)r1).bigger();
                  }
            }
            if (effects.size() > 0) return true;
            else return false;
    }
    public synchronized  void display(Graphics g) {
         for (Role r : roles) {   
          //????????? //<-------------- ask spirit to display 
         
         }
         if (effects!=null) {
           for (Effect e : effects.values()) {
               //????????? //<-------------- ask effect to display 
                
           }
        }
    }
   
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        
    }   
    static void createAndShowGUI() {
        
      final View view = new View(); //for used in anonymous class
       Controller ctl = new Controller(view);
       view.setController(ctl);
       
       JFrame win = new JFrame("Control Spirit");
     
       win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       win.addWindowListener(new WindowAdapter(){ 
               public void windowActivated( WindowEvent e){ 
                 view.requestFocusInWindow(); //it is necessary  to keep the keyboard event can be accept
              } 
              public void windowOpened( WindowEvent e){ 
                 view.requestFocusInWindow(); //it is necessary to keep the keyboard event can be accept
              } 
      }); 
       win.setContentPane(view);
       win.setSize(1000, 1000);
       win.validate();
     
       
       win.setVisible(true);
       view.requestFocusInWindow();
        
       ctl.prepare();   

    }
}
