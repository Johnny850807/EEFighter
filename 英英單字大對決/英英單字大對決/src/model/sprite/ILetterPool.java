package model.sprite;

public interface ILetterPool {
	Sprite requireSprite();
	void releaseSprite(Sprite sprite);
}
