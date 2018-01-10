package model.sprite;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import java.util.Stack;

import model.Question;

public class PlayerSprite extends Sprite{
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

	public void addLetter(String answer, Sprite sprite) {
		List<Character> tempAnswer = new ArrayList<>();
		List<Character> tempAnswerCopy = new ArrayList<>();
		for (int i = 0; i < answer.length(); i++) {
			tempAnswer.add(answer.charAt(i));
			tempAnswerCopy.add(answer.charAt(i));
		}
		List<Character> match = new ArrayList<>();
		List<Character> nonsenses = new ArrayList<>();
		
		for (int j = 0; j < letters.size(); j++) {
			Character letter = letters.get(j).getSpriteName().toString().charAt(0);
			if (tempAnswer.contains(letter)) {
				match.add(letter);
				tempAnswer.remove(letter);
			}
			else 
				nonsenses.add(letter);
		}
		
		Collections.sort(match, (letter1, letter2) -> {
			return tempAnswerCopy.indexOf(letter1) - tempAnswerCopy.indexOf(letter2);
		});
		
		StringBuilder stringBuilder = new StringBuilder();
		for (Character character : match)
			stringBuilder.append(character);
		for (Character character : nonsenses)
			stringBuilder.append(character);
		
		// TODO �Ƨ�

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
