package model.factory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import model.sprite.Sprite;
import model.sprite.SpriteName;

public class SpritePrototypeFactory {
	private static SpritePrototypeFactory instance = null;
	private Map<SpriteName, Sprite> spriteMap = new HashMap<>();

	public SpritePrototypeFactory() {
		try {
			prepareSprites();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SpritePrototypeFactory getInstance() {
		if (instance == null)
			instance = new SpritePrototypeFactory();
		
		return instance;
	}

	public void prepareSprites() throws IOException {
		spriteMap.put(SpriteName.GRASS, new Sprite(64, 64, 0, 0, 64, 64, SpriteName.GRASS, ImageIO.read(new File("pic/grass.png"))));
		spriteMap.put(SpriteName.BARRIER, new Sprite(64, 64, 0, 0, 64, 64, SpriteName.BARRIER, ImageIO.read(new File("pic/block.png"))));

		spriteMap.put(SpriteName.A, new Sprite(64, 64, 10, 10, 54, 54, SpriteName.A, ImageIO.read(new File("pic/A.png"))));
		spriteMap.put(SpriteName.B, new Sprite(64, 64, 20, 20, 44, 44, SpriteName.B, ImageIO.read(new File("pic/B.png"))));
		spriteMap.put(SpriteName.C, new Sprite(64, 64, 17, 17, 47, 40, SpriteName.C, ImageIO.read(new File("pic/C.png"))));
		spriteMap.put(SpriteName.D, new Sprite(64, 64, 18, 18, 50, 50, SpriteName.D, ImageIO.read(new File("pic/D.png"))));
		spriteMap.put(SpriteName.E, new Sprite(64, 64, 22, 18, 50, 50, SpriteName.E, ImageIO.read(new File("pic/E.png"))));
		spriteMap.put(SpriteName.F, new Sprite(64, 64, 22, 18, 44, 44, SpriteName.F, ImageIO.read(new File("pic/F.png"))));
		spriteMap.put(SpriteName.G, new Sprite(64, 64, 12, 14, 42, 42, SpriteName.G, ImageIO.read(new File("pic/G.png"))));
		spriteMap.put(SpriteName.H, new Sprite(64, 64, 17, 10, 40, 49, SpriteName.H, ImageIO.read(new File("pic/H.png"))));
		spriteMap.put(SpriteName.I, new Sprite(64, 64, 29, 10, 35, 40, SpriteName.I, ImageIO.read(new File("pic/I.png"))));
		spriteMap.put(SpriteName.J, new Sprite(64, 64, 22, 14, 40, 40, SpriteName.J, ImageIO.read(new File("pic/J.png"))));
		spriteMap.put(SpriteName.K, new Sprite(64, 64, 15, 13, 50, 50, SpriteName.K, ImageIO.read(new File("pic/K.png"))));
		spriteMap.put(SpriteName.L, new Sprite(64, 64, 22, 10, 40, 50, SpriteName.L, ImageIO.read(new File("pic/L.png"))));
		spriteMap.put(SpriteName.M, new Sprite(64, 64, 10, 12, 50, 50, SpriteName.M, ImageIO.read(new File("pic/M.png"))));
		spriteMap.put(SpriteName.N, new Sprite(64, 64, 17, 19, 45,450, SpriteName.N, ImageIO.read(new File("pic/N.png"))));
		spriteMap.put(SpriteName.O, new Sprite(64, 64, 13, 17, 50, 45, SpriteName.O, ImageIO.read(new File("pic/O.png"))));
		spriteMap.put(SpriteName.P, new Sprite(64, 64, 21, 21, 40, 40, SpriteName.P, ImageIO.read(new File("pic/P.png"))));
		spriteMap.put(SpriteName.Q, new Sprite(64, 64, 13, 17, 50, 45, SpriteName.Q, ImageIO.read(new File("pic/Q.png"))));
		spriteMap.put(SpriteName.R, new Sprite(64, 64, 21, 17, 40, 45, SpriteName.R, ImageIO.read(new File("pic/R.png"))));
		spriteMap.put(SpriteName.S, new Sprite(64, 64, 21, 13, 40, 50, SpriteName.S, ImageIO.read(new File("pic/S.png"))));
		spriteMap.put(SpriteName.T, new Sprite(64, 64, 15, 12, 50, 50, SpriteName.T, ImageIO.read(new File("pic/T.png"))));
		spriteMap.put(SpriteName.U, new Sprite(64, 64, 10, 9, 50, 50, SpriteName.U, ImageIO.read(new File("pic/U.png"))));
		spriteMap.put(SpriteName.V, new Sprite(64, 64, 10, 10, 50, 50, SpriteName.V, ImageIO.read(new File("pic/V.png"))));
		spriteMap.put(SpriteName.W, new Sprite(64, 64, 6, 15, 50, 50, SpriteName.W, ImageIO.read(new File("pic/W.png"))));
		spriteMap.put(SpriteName.X, new Sprite(64, 64, 10, 12, 50, 50, SpriteName.X, ImageIO.read(new File("pic/X.png"))));
		spriteMap.put(SpriteName.Y, new Sprite(64, 64, 11, 13, 50, 50, SpriteName.Y, ImageIO.read(new File("pic/Y.png"))));
		spriteMap.put(SpriteName.Z, new Sprite(64, 64, 13, 15, 50, 50, SpriteName.Z, ImageIO.read(new File("pic/Z.png"))));
		
		spriteMap.put(SpriteName.PLAYER, new Sprite(64, 64, 0, 0, 50, 50, SpriteName.PLAYER, ImageIO.read(new File("pic/East_T0.png"))));
	}

	public Sprite createSprite(SpriteName spriteName) {
		return spriteMap.get(spriteName).clone();
	}

}