package model.sprite;

import java.util.Arrays;

public enum SpriteName {
	TERRAIN("Terrain"), GRASS("Grass"), A("A"), B("B"), C("C"), D("D"), E("E"), F("F"), G("G"), H("H"), I("I"), 
	J("J"), K("K"), L("L"), M("M"), N("N"), O("O"), P("P"), Q("Q"), R("R"), S("S"), T("T"), U("U"), V("V"), W("W"), X("X"), Y("Y"), Z("Z"), PLAYER1("Player1"), PLAYER2("Player2");
	
	private String name;
	
	private SpriteName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static SpriteName[] getLetterNames(){
		SpriteName[] allNames = values();
		return Arrays.copyOfRange(allNames, 2, 2 + 26);
	}
	
	public boolean isLetter(){
		assert values()[2+25] == SpriteName.Z;
		return this.ordinal() >= 2 && this.ordinal() < 2 + 26;
	}
}
