 import java.awt.Graphics;

public abstract class GraphicStrategy {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	public GraphicStrategy(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract void draw(Graphics g);
}
