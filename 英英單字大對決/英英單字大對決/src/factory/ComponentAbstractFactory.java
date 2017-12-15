package factory;

import controller.EEFighter;
import model.sprite.GameMap;
import model.sprite.MapDirector;
import ui.GameView;
import ui.IGameStartView;

public class ComponentAbstractFactory {
	private IGameStartView gameStartView;
	private GameView gameView;
	private EEFighter eeFighter;
	private MapDirector mapDirector;
	private GameMap gameMap;
}
