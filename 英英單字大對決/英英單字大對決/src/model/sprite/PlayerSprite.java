package model.sprite;

import java.awt.Image;
import java.util.List;
import java.util.Map;

import java.util.Stack;

import model.Question;

public class PlayerSprite extends Sprite {
	private Stack<Sprite> letters = new Stack<Sprite>();
	private int score = 0;

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
				xy.move(0, -4);
				break;
			case SOUTH:
				xy.move(0, 4);
				break;
			case WEST:
				xy.move(-4, 0);
				break;
			case EAST:
				xy.move(4, 0);
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

	public void addLetter(Question question, Sprite sprite) {
		List<Character> tempAnswer = new Arr
		
//		String str = question.getWord().toUpperCase();
//		String[] questionLetters = str.split("");
//		String gottenLetter = sprite.getSpriteName().toString();
//		if (index == -1) {
//			letters.push(sprite);
//			System.out.println("push back" + sprite.getSpriteName().toString());
//		} else
//			for (int i = 0; i < questionLetters.length; i++)
//				if (gottenLetter.equals(questionLetters[i])) {
//					if (indexIsAlreadyChange(gottenLetter, questionLetters[i])
//							&& letters.size() > questionLetters.length) {
//						letters.add(i, sprite);
//						System.out.println("get" + sprite.getSpriteName().toString());
//					} else {
//						letters.push(sprite);
//						System.out.println("push" + sprite.getSpriteName().toString());
//					}
//				}

		// TODO ±Æ§Ç

		// letters.push(sprite);
	}

	private boolean indexIsAlreadyChange(String gottenLetter, String questionLetter) {
		return gottenLetter.equals(questionLetter) ? true : false;
	}

	public Sprite popLetter() {
		if (!letters.isEmpty())
			return letters.pop();
		return null;
	}

	public void removeAllLetters() {
		while (!letters.isEmpty())
			popLetter();
	}

	public List<Sprite> getLetters() {
		for (Sprite sprite : letters) {
			System.out.println(sprite.spriteName.toString());
		}
		return letters;
	}

}
