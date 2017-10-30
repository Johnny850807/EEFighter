

public class Test {

    public static void main(String[] argv) {
    	GraphicStrategy circle = new Circle(50, 50, 100);
    	GraphicStrategy smileFace = new SmileFace(200, 300, 300, 200);
    	GraphicStrategy sadFace = new SadFace(300, 100, 300, 200);
    	GraphicStrategy rectangle = new Rectangle(680, 160, 60, 90);
    	DrawGarphics drawGarphics = new DrawGarphics();
    	drawGarphics.addGarphic(rectangle);
    	drawGarphics.addGarphic(circle);
    	drawGarphics.addGarphic(smileFace);
    	drawGarphics.addGarphic(sadFace);
    	drawGarphics.setVisible(true);
    }
    
}
