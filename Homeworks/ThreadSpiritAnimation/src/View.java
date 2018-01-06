import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Image;

import java.io.FileInputStream;
import java.util.*;
/**
 * Write a description of class View here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class View extends JPanel implements KeyListener
{
   private ArrayList<Role> roles ;
   private HashMap<Role, Effect> effects;
   Spirit spirit; //the player
   Controller ctl;
   ControlPanel ctlPanel;
   FacePanel facePanel;
   
   boolean startGame = false;
   Image bagImg;
   
   View() {  
    try {  bagImg =  ImageIO.read(new File("pics/author2.png"));} catch(Exception e) {}
     ctlPanel = new ControlPanel();
     facePanel = new FacePanel();

     setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20,16,10,20), BorderFactory.createLineBorder(Color.black)));
     setLayout(new BorderLayout());
     add(facePanel, BorderLayout.CENTER);
     add(ctlPanel, BorderLayout.SOUTH);
     
     addKeyListener(this);
     
   }
   public void setRoles(ArrayList<Role> rs) {roles = rs; }
   public void setPlayer(Spirit sp) {
       spirit = sp;
    }
   public void setController(Controller ctl) {
       this.ctl = ctl;
   }
   
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
       if (spirit==null) return;
       if (!startGame) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP://up
                ctl.move(spirit, ActionType.WALK, DIR4.NORTH); break;            
            case KeyEvent.VK_LEFT://left
                ctl.move(spirit, ActionType.WALK, DIR4.WEST); break;            
            case KeyEvent.VK_DOWN://down
                ctl.move(spirit, ActionType.WALK, DIR4.SOUTH); break;             
            case KeyEvent.VK_RIGHT://right
                ctl.move(spirit, ActionType.WALK, DIR4.EAST); break;   
        }
    }
    public void keyReleased(KeyEvent e) {}
     
   
   /**
    * refresh the state of a role
    */
   public synchronized void refresh(HashMap<Role, Effect> effects) {
       this.effects = effects;
       repaint();
      /*
       Role r = (Role) state.getSelf();
    
      int x = state.getX();
      int y = state.getY();
      spirit = state.getSelf();
      ImageSequence is = state.getImageSequence();
      
      facePanel.setUp(x, y, is);
      */
    }
   private class ControlPanel extends JPanel implements ActionListener {
     JButton m, s, sg, ss;
     ControlPanel () {
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      add(m = new JButton("halt")); //stop the spirit
      add(ss = new JButton(" go ")); //triggering the spirit
      add(s = new JButton("start")); // End the game
      add(sg = new JButton("up"));  //not used for now
      
      m.setActionCommand("halt");
      ss.setActionCommand(" go ");
      s.setActionCommand("start");
      sg.setActionCommand("up");
      
      m.setBackground(Color.cyan);
      s.setBackground(Color.cyan);
      sg.setBackground(Color.cyan);
      ss.setBackground(Color.cyan);
      m.addActionListener(this);
      s.addActionListener(this);
      sg.addActionListener(this);
      ss.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        if (spirit==null) return;
        switch (e.getActionCommand()) {
            case "halt": if (startGame) ctl.halt(spirit); break;
            case "start": 
                   if (s.getText().equals("start")) {
                        s.setText("end");//change label
                        startGame=true;
                        ctl.startGame();//�霈���見, 銝阡��lay
                        ctl.start();
                      } else {
                           startGame=false;
                           s.setText("start") ;
                           ctl.end();//stop countdown
                           System.exit(0);
                      }
            break;   
         //   case "up": ctl.move(spirit, ActionType.WALK, DIR4.NORTH); break;
            case " go ": if (startGame) ctl.goOn(spirit); break;   
        }
    }
   
  }
  private class FacePanel extends JPanel {
     private int x=0;
     private int y=0;
     Image frame ;
     FacePanel() { 
        setLayout(new BorderLayout());
        setBackground(Color.black);
        int w = getWidth();
        int h = getHeight();
        this.setDoubleBuffered(true);
     }
  
     
     /**
      * 鞎痊�摮��＊蝷�
      */
     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         //======================= 憿舐內�摮�� ========================
        if (!startGame)  return;
        if (roles!=null)  {
           ctl.display(g);  //<--------- ask controller to display 
       //===============================
         View.this.requestFocusInWindow(); ////it is necessary : to keep the keyboard event can be accept
     }
  }
}
 
}
