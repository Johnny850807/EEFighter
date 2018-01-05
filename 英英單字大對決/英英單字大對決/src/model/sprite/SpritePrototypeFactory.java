package model.sprite;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import model.sprite.Sprite.Direction;


/**
 * @author Lin (ªL©v»õ)
 */
public class SpritePrototypeFactory {
	private static SpritePrototypeFactory instance = null;
	private Image[] player1LeftImages = new Image[4];
	private Image[] player1RightImages = new Image[4];
	private Image[] player2LeftImages = new Image[4];
	private Image[] player2RightImages = new Image[4];
	private Map<SpriteName, Sprite> spriteMap = new HashMap<>();
	private Map<Direction, ImageSequence> player1ImageMap = new HashMap<>();
	private Map<Direction, ImageSequence> player2ImageMap = new HashMap<>();

	private SpritePrototypeFactory() {
		try {
			preparePlayersImageMap();
			prepareSprites();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void preparePlayersImageMap() throws IOException{
		player1ImageMap.put(Direction.WEST, new ImageSequence("pic/boy/boy-left-", "png", 4));
		player1ImageMap.put(Direction.EAST, new ImageSequence("pic/boy/boy-right-", "png", 4));
		player2ImageMap.put(Direction.WEST, new ImageSequence("pic/girl/girl-left-", "png", 4));
		player2ImageMap.put(Direction.EAST, new ImageSequence("pic/girl/girl-right-", "png", 4));
	}

//	private void preparePlayer2LeftAndRightImage() throws IOException {
//		player2LeftImages[0] = ImageIO.read(new File("pic/girl/girl-left-0.png"));
//		player2LeftImages[1] = ImageIO.read(new File("pic/girl/girl-left-1.png"));
//		player2LeftImages[2] = ImageIO.read(new File("pic/girl/girl-left-2.png"));
//		player2LeftImages[3] = ImageIO.read(new File("pic/girl/girl-left-3.png"));
//		player2RightImages[0] = ImageIO.read(new File("pic/girl/girl-left-0.png"));
//		player2RightImages[1] = ImageIO.read(new File("pic/girl/girl-left-1.png"));
//		player2RightImages[2] = ImageIO.read(new File("pic/girl/girl-left-2.png"));
//		player2RightImages[3] = ImageIO.read(new File("pic/girl/girl-left-3.png"));
//	}
//
//	private void preparePlayer1LeftAndRightImage() throws IOException {
//		player1LeftImages[0] = ImageIO.read(new File("pic/boy/boy-left-0.png"));
//		player1LeftImages[1] = ImageIO.read(new File("pic/boy/boy-left-1.png"));
//		player1LeftImages[2] = ImageIO.read(new File("pic/boy/boy-left-2.png"));
//		player1LeftImages[3] = ImageIO.read(new File("pic/boy/boy-left-3.png"));
//		player1RightImages[0] = ImageIO.read(new File("pic/boy/boy-right-0.png"));
//		player1RightImages[1] = ImageIO.read(new File("pic/boy/boy-right-1.png"));
//		player1RightImages[2] = ImageIO.read(new File("pic/boy/boy-right-2.png"));
//		player1RightImages[3] = ImageIO.read(new File("pic/boy/boy-right-3.png"));
//
//	}

	private Map<Direction, ImageSequence> createImageMap(Image image) {
		Map<Direction, ImageSequence> imageMap = new HashMap<>();
		imageMap.put(Direction.WEST, images);
		return imageMap;
	}

	public static SpritePrototypeFactory getInstance() {
		if (instance == null)
			instance = new SpritePrototypeFactory();
		
		return instance;
	}

	public void prepareSprites() throws IOException {
		spriteMap.put(SpriteName.GRASS, new Sprite(64, 64, 0, 0, 64, 64, SpriteName.GRASS, createImageMap(ImageIO.read(new File("pic/grass.png")))));
		spriteMap.put(SpriteName.TERRAIN, new Sprite(64, 64, 0, 0, 64, 64, SpriteName.TERRAIN, createImageMap(ImageIO.read(new File("pic/block.png")))));
		spriteMap.put(SpriteName.A, new Sprite(64, 64, 10, 10, 54, 54, SpriteName.A, createImageMap(ImageIO.read(new File("pic/A.png")))));
		spriteMap.put(SpriteName.B, new Sprite(64, 64, 20, 20, 44, 44, SpriteName.B, createImageMap(ImageIO.read(new File("pic/B.png")))));
		spriteMap.put(SpriteName.C, new Sprite(64, 64, 22, 22, 42, 42, SpriteName.C, createImageMap(ImageIO.read(new File("pic/C.png")))));
		spriteMap.put(SpriteName.D, new Sprite(64, 64, 18, 14, 50, 47, SpriteName.D, createImageMap(ImageIO.read(new File("pic/D.png")))));
		spriteMap.put(SpriteName.E, new Sprite(64, 64, 19, 18, 42, 46, SpriteName.E, createImageMap(ImageIO.read(new File("pic/E.png")))));
		spriteMap.put(SpriteName.F, new Sprite(64, 64, 20, 20, 44, 44, SpriteName.F, createImageMap(ImageIO.read(new File("pic/F.png")))));
		spriteMap.put(SpriteName.G, new Sprite(64, 64, 22, 22, 42, 42, SpriteName.G, createImageMap(ImageIO.read(new File("pic/G.png")))));
		spriteMap.put(SpriteName.H, new Sprite(64, 64, 17, 10, 40, 49, SpriteName.H, createImageMap(ImageIO.read(new File("pic/H.png")))));
		spriteMap.put(SpriteName.I, new Sprite(64, 64, 10, 7, 3, 50, SpriteName.I, createImageMap(ImageIO.read(new File("pic/I.png")))));
		spriteMap.put(SpriteName.J, new Sprite(64, 64, 23, 23, 40, 40, SpriteName.J, createImageMap(ImageIO.read(new File("pic/J.png")))));
		spriteMap.put(SpriteName.K, new Sprite(64, 64, 15, 13, 50, 50, SpriteName.K, createImageMap(ImageIO.read(new File("pic/K.png")))));
		spriteMap.put(SpriteName.L, new Sprite(64, 64, 18, 10, 42, 50, SpriteName.L, createImageMap(ImageIO.read(new File("pic/L.png")))));
		spriteMap.put(SpriteName.M, new Sprite(64, 64, 15, 15, 49, 48, SpriteName.M, createImageMap(ImageIO.read(new File("pic/M.png")))));
		spriteMap.put(SpriteName.N, new Sprite(64, 64, 19, 19, 45, 45, SpriteName.N, createImageMap(ImageIO.read(new File("pic/N.png")))));
		spriteMap.put(SpriteName.O, new Sprite(64, 64, 19, 19, 45, 45, SpriteName.O, createImageMap(ImageIO.read(new File("pic/O.png")))));
		spriteMap.put(SpriteName.P, new Sprite(64, 64, 23, 23, 40, 40, SpriteName.P, createImageMap(ImageIO.read(new File("pic/P.png")))));
		spriteMap.put(SpriteName.Q, new Sprite(64, 64, 19, 18, 50, 45, SpriteName.Q, createImageMap(ImageIO.read(new File("pic/Q.png")))));
		spriteMap.put(SpriteName.R, new Sprite(64, 64, 19, 19, 40, 45, SpriteName.R, createImageMap(ImageIO.read(new File("pic/R.png")))));
		spriteMap.put(SpriteName.S, new Sprite(64, 64, 18, 13, 40, 50, SpriteName.S, createImageMap(ImageIO.read(new File("pic/S.png")))));
		spriteMap.put(SpriteName.T, new Sprite(64, 64, 15, 15, 50, 48, SpriteName.T, createImageMap(ImageIO.read(new File("pic/T.png")))));
		spriteMap.put(SpriteName.U, new Sprite(64, 64, 10, 9, 50, 50, SpriteName.U, createImageMap(ImageIO.read(new File("pic/U.png")))));
		spriteMap.put(SpriteName.V, new Sprite(64, 64, 10, 10, 50, 50, SpriteName.V, createImageMap(ImageIO.read(new File("pic/V.png")))));
		spriteMap.put(SpriteName.W, new Sprite(64, 64, 6, 12, 50, 52, SpriteName.W, createImageMap(ImageIO.read(new File("pic/W.png")))));
		spriteMap.put(SpriteName.X, new Sprite(64, 64, 10, 12, 50, 52, SpriteName.X, createImageMap(ImageIO.read(new File("pic/X.png")))));
		spriteMap.put(SpriteName.Y, new Sprite(64, 64, 11, 13, 50, 50, SpriteName.Y, createImageMap(ImageIO.read(new File("pic/Y.png")))));
		spriteMap.put(SpriteName.Z, new Sprite(64, 64, 13, 13, 50, 50, SpriteName.Z, createImageMap(ImageIO.read(new File("pic/Z.png")))));
		spriteMap.put(SpriteName.PLAYER1, new PlayerSprite(64, 64, 0, 0, 50, 50, SpriteName.PLAYER1, player1ImageMap));
		spriteMap.put(SpriteName.PLAYER2, new PlayerSprite(64, 64, 0, 0, 50, 50, SpriteName.PLAYER2, player1ImageMap));
	}

	public Sprite createSprite(SpriteName spriteName) {
		return spriteMap.get(spriteName).clone();
	}

}