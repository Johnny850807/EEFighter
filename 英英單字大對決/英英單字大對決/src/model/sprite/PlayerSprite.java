package model.sprite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.words.LetterSpriteSorter;

public class PlayerSprite extends Sprite{
	private List<Sprite> letters = new ArrayList<Sprite>();
	private int score = 0;
	private static final int MOVE_SPEED = 10;

	public PlayerSprite(int w, int h, int biasWithX, int biasWithY, int bodyHeight, int bodyLength,
			SpriteName spriteName, Map<Movement, ImageSequence> imageMap) {
		super(w, h, biasWithX, biasWithY, bodyHeight, bodyLength, spriteName, imageMap);
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	@Override
	public synchronized void update() {
		if (status == Status.MOVE) {
			switch (direction) {
			case NORTH:
				xy.move(0, MOVE_SPEED * -1);
				break;
			case SOUTH:
				xy.move(0, MOVE_SPEED);
				break;
			case WEST:
				xy.move(MOVE_SPEED * -1, 0);
				break;
			case EAST:
				xy.move(MOVE_SPEED, 0);
				break;
			default:
				break;
			}
		}
		if (moveFailed(gameMap)) {
			xy.rollback();
			gameView.onHitWall(this);
		}
	}

	private boolean moveFailed(GameMap gameMap) {
		if (gameMap.outOfMap(this))
			return true;
		for (Sprite terrain : gameMap.getAllTerrains())
			if (terrain.isCollisions(this))
				return true;
		return false;
	}

	public void addLetter(Sprite sprite) {
		 letters.add(sprite);
	}

	public Sprite popLetter(String answer) {
		LinkedList<Sprite> sortedLetters = new LinkedList<>(
				LetterSpriteSorter.productSortedLetters(answer, letters));
		Sprite lastLetter = sortedLetters.peekLast();
		for (int i = letters.size() - 1 ; i >= 0 ; i --)
			if (letters.get(i).getSpriteName() == lastLetter.getSpriteName())
			{
				Sprite poppedLetter = letters.get(i);
				System.out.println("Popped letter: " + poppedLetter.getSpriteName());
				letters.remove(i);
				return poppedLetter;
			}
		return null;
	}

	public void removeAllLetters() {
		letters.clear();
	}

	public List<Sprite> getLetters() {
		return letters;
	}

}
