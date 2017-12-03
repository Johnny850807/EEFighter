package model.sprite;

import java.util.Arrays;

public enum SpriteName {
	BARRIER, GRASS, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, PLAYER1, PLAYER2;
	
	public static SpriteName[] getLetterNames(){
		SpriteName[] allNames = values();
		return Arrays.copyOfRange(allNames, 2, 2 + 26);
	}
}
