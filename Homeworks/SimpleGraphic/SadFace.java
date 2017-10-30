 import java.awt.Color;
import java.awt.Graphics;

public class SadFace extends GraphicStrategy {

	public SadFace(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(x, y, width, height); 
		
		g.drawLine(x + (int)(width / 6), y + (int)(height / 3), x + (int)(width / 6) * 2, y + (int)(height / 3)); 
        
        //draw a filled oval with red color
        g.setColor(Color.RED);
        g.fillOval(x + (int)(width / 6) * 4, y + (int)(height / 3 - height / 24), (int)(width / 12), (int)(height / 12)); 
        
        //draw an arc at xpos, ypos of the top left corner, and width, height of the area
        // starting from starting degree s of 180 and sweeping 180 degree
        g.setColor(Color.BLUE);
        g.drawArc(x + (int)(width / 3), y + (int)(height / 5) * 3, (int)(width / 3), (int)(height / 6), 0, 180); 
	}

}
