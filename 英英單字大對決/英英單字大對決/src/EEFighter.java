import model.factory.SpritePrototypeFactory;
import model.sprite.BasicMapBuilder;
import model.sprite.BasicMapDirector;
import model.sprite.GameMap;
import model.sprite.Sprite;
import ui.GameView;

public class EEFighter {

	private GameView gameView;
	
	public void startGame() {
		GameMap gameMap = new BasicMapDirector(new BasicMapBuilder()).buildMap();
		SpritePrototypeFactory spritePrototypeFactory = SpritePrototypeFactory.getInstance();
		Sprite[] letters = null;
		Sprite player1 = null;
		Sprite player2 = null;
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(17);
						//sprite update
						gameView.onDraw(gameMap, letters, player1, player2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	public boolean isOver() {
		return false;
	}
}
