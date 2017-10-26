import java.awt.Graphics;
public class Circle extends DrawGarphicStrategy
{
    public void draw(Graphics g) {
        g.drawOval(50, 50, 300, 200);
    }
}
