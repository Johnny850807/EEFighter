 import java.awt.Graphics;

public class Rectangle extends GraphicStrategy {

	public Rectangle(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(x, y, width, height);
	}

}
