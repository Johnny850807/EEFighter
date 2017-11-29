package model.sprite;


public class BasicRandomMapDirector extends TemplateRandomMapDirector{

	public BasicRandomMapDirector(MapBuilder builder) {
		super(builder);
	}

	@Override
	protected void startMove(Mouse mouse) {
		mouse.sculpture('0');
		for (int i = 0 ; i < 50 ; i ++)
		{
			mouse.successfullyRandomMove(true);
			mouse.sculpture('0');
		}
	}
	

}
