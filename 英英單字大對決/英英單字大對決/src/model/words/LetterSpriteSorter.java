package model.words;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.sprite.Sprite;
import model.sprite.SpriteName;
import model.sprite.SpritePrototypeFactory;
import utils.SpriteLogHelper;

public class LetterSpriteSorter {
	
	public static List<Sprite> produceSortedLetters(String answer, List<Sprite> letters){
		answer = answer.toUpperCase();
		System.out.println("Answer: " + answer + ", Input: " + SpriteLogHelper.toString(letters));
		List<Sprite> output = new ArrayList<>();
		List<Character> answerCharList = new ArrayList<>();
		List<Character> answerCharListCopy = new ArrayList<>();
		List<Sprite> matchLetters = new ArrayList<>();
		List<Sprite> nonsensesLetters = new ArrayList<>();
		
		for (int i = 0 ; i < answer.length() ; i ++)
		{
			answerCharList.add(answer.charAt(i));
			answerCharListCopy.add(answer.charAt(i));
		}
		
		for (Sprite letter : letters)
		{
			Character letterChar = letterToChar(letter);
			if (answerCharList.contains(letterChar))
			{
				matchLetters.add(letter);
				answerCharList.remove(letterChar);
			}
			else
				nonsensesLetters.add(letter);
		}
		
		Map<Integer, Sprite> ordering = new HashMap<>();
		for (Sprite letter : letters)
		{
			for (int i = 0 ; i < answerCharListCopy.size() ; i ++)
			{
				if (letterToChar(letter) == answerCharListCopy.get(i) &&
						!ordering.containsKey(i))
				{
					ordering.put(i, letter);
					break;
				}
			}
		}
		
		output.addAll(new ArrayList<>(ordering.values()));
		output.addAll(nonsensesLetters);
		System.out.println("Output: " + SpriteLogHelper.toString(output));
		return output;
	}
	
	private static Character letterToChar(Sprite letter) {
		return letter.getSpriteName().toString().charAt(0);
	}
	
	public static void main(String[] argv) {
		SpritePrototypeFactory factory = SpritePrototypeFactory.getInstance();
		String answer = "apple".toUpperCase();
		List<Sprite> myLetters = new ArrayList<>();
		myLetters.add(factory.createSprite(SpriteName.P));
		myLetters.add(factory.createSprite(SpriteName.D));
		myLetters.add(factory.createSprite(SpriteName.C));
		myLetters.add(factory.createSprite(SpriteName.P));
		myLetters.add(factory.createSprite(SpriteName.A));
		myLetters.add(factory.createSprite(SpriteName.E));
		myLetters.add(factory.createSprite(SpriteName.K));
		
		StringBuilder strb = new StringBuilder();
		for (Sprite letter : produceSortedLetters(answer, myLetters))
			strb.append(letter.getSpriteName() + " ");
		System.out.println(strb.toString());
	}
}
