import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Image;

import java.io.FileInputStream;
/**
 * Write a description of class View here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class View extends JPanel implements KeyListener
{
   Spirit spirit;
   Controller ctl;
   ControlPanel ctlPanel;
   FacePanel facePanel;
 //  Player playMP3;
   View() {  
     ctlPanel = new ControlPanel();
     facePanel = new FacePanel();

     setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20,16,10,20), BorderFactory.createLineBorder(Color.black)));
     facePanel.setBackground(Color.BLACK);
     setLayout(new BorderLayout());
     add(facePanel, BorderLayout.CENTER);
     add(ctlPanel, BorderLayout.SOUTH);
     
     addKeyListener(this);
     /*
     addMouseListener(new MouseAdapter()  {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                View.this.requestFocus();
                super.mouseClicked(e);
            }
        });
        */
   }
   public void setController(Controller ctl) {
       this.ctl = ctl;
   }
   
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
       if (spirit==null) return;
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
     
     
   
   public void refresh(State state) {
      int x = state.getX();
      int y = state.getY();
      spirit = state.getSelf();
      ImageSequence is = state.getImageSequence();
      
      facePanel.setUp(x, y, is);
    }
   private class ControlPanel extends JPanel implements ActionListener {
     JButton m, s, sg, ss;
     ControlPanel () {
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      add(m = new JButton("left"));
      add(s = new JButton("right"));
      add(sg = new JButton("up"));
      add(ss = new JButton("down"));
      m.setActionCommand("left");
      s.setActionCommand("right");
      sg.setActionCommand("up");
      ss.setActionCommand("down");
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
            case "left": ctl.move(spirit, ActionType.WALK, DIR4.WEST); break;
            case "right": ctl.move(spirit, ActionType.WALK, DIR4.EAST); break;   
            case "up": ctl.move(spirit, ActionType.WALK, DIR4.NORTH); break;
            case "down": ctl.move(spirit, ActionType.WALK, DIR4.SOUTH); break;   
        }
    }
   
  }
  private class FacePanel extends JPanel {
     private int x=0;
     private int y=0;
     Image frame ;
     FacePanel() { 
        setLayout(new BorderLayout());
     }
     private void setUp(int x, int y, ImageSequence is) {
         this.x = x;
         this.y = y;
         frame = is.next(true);
         repaint();
     }
     
     /**
      * 負責數字圖的顯示
      */
     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         //======================= 顯示數字鐘 ========================
         g.drawImage(frame,x, y, 30, 30, this ); //顯示分鐘10位數
        }
  }
  

}
