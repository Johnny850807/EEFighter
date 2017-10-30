import java.awt.Graphics;

public class Circle extends GraphicStrategy{

	public Circle(int x, int y, int sideLength) {
		super(x, y, sideLength, sideLength);
	}

	@Override
	public void draw(Graphics g) {
		g.drawOval(x, y, width, height);
	}

}
