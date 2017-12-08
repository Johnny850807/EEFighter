package model.sprite;

public interface ILetterPool {
	Sprite requireSprite();
	void relaseSprite(Sprite sprite);
}
