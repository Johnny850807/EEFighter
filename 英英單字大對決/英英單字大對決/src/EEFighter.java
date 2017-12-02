import model.sprite.GameMap;
import ui.GameView;

public class EEFighter {

	private GameView gameView;
	
	public void startGame() {
		GameMap gameMap;
		new Thread() {
			public void run() {
				while (!isOver()) {
					//gameView.onDraw(gameMap, letters, player1, player2);
				}
			}
		};
	}
	
	public boolean isOver() {
		return false;
	}
}
