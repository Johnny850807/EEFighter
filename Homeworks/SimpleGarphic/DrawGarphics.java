import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
public class DrawGarphics extends JFrame
{
    private DrawGarphicStrategy drawGarphic;
    
    public DrawGarphics(DrawGarphicStrategy drawGarphic)
    {
        this.drawGarphic = drawGarphic;
        setSize(500, 300); //width, height of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set default close oeraton
        getContentPane().setBackground(Color.WHITE) ; //set background color
    }
    
    @Override
    public void paint(Graphics g) {
        draw(g);
    }
    
    public void draw(Graphics g)
    {
        return drawGarphic.draw(g);
    }
}
