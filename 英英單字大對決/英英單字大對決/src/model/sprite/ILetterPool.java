package model.sprite;

public interface ILetterPool {
	Sprite requireReusable();
	void relaseReusable(Sprite sprite);
}
