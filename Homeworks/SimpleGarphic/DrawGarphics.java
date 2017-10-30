 
import javax.swing.JFrame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class DrawGarphics extends JFrame {
	private List<GraphicStrategy> drawGarphicStrategys = new ArrayList<>();
    
    public DrawGarphics()
    {
        setSize(800, 600); //width, height of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set default close oeraton
        getContentPane().setBackground(Color.WHITE) ; //set background color
    }
    
    @Override
    public void paint(Graphics g) {
        draw(g);
    }
    
    public void addGarphic(GraphicStrategy graphicStrategy) {
    	drawGarphicStrategys.add(graphicStrategy);
    }
    
    public void draw(Graphics g)
    {
        for (GraphicStrategy graphicStrategy : drawGarphicStrategys) {
        	graphicStrategy.draw(g);
		}
    }
    
}
