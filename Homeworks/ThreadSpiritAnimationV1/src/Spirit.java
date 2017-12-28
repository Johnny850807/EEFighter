import java.util.Random;

/**
 * Write a description of class Spirit here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Spirit {
	protected final int MIN_X = 0;
	protected final int MIN_Y = 0;
	protected final int MAX_X = 900;
	protected final int MAX_Y = 600;
	protected ImageSequencePrototype prototype = SpiritImageSequencePrototype.getInstance();
	protected ImageSequence[][] actionImgs = 
		{{prototype.getImageSequence(0, 0), prototype.getImageSequence(0, 1), prototype.getImageSequence(0, 2), prototype.getImageSequence(0, 3)},
		 {prototype.getImageSequence(1, 0), prototype.getImageSequence(1, 1), prototype.getImageSequence(1, 2), prototype.getImageSequence(1, 3)}};
	protected Controller ctl;
	protected int curX;
	protected int curY;
	protected ActionType curAct;
	protected DIR4 curDir;
	protected final int deltaDist = 5; // 5 px at each move
	protected State state;

	Spirit(Controller ctl) {
		this.ctl = ctl;
		curAct = ActionType.HALT;
		curDir = null;
		curX = (int) (MIN_X + MAX_X) / 2;
		curY = (int) (MIN_Y + MAX_Y) / 2;
		curAct = ActionType.HALT;
		curDir = DIR4.EAST;			
		state = new State(ctl, this, curX, curY, actionImgs[0][1]);
	}

	public void move(ActionType act, DIR4 dir) {// will change its state of location
		switch (curAct) {
		case HALT:
			switch (act) {
			case HALT:
				break; // nothing changed
			case WALK:
				if ((dir == DIR4.NORTH) && ((curY - deltaDist) >= MIN_Y))
					curY -= deltaDist;
				if ((dir == DIR4.SOUTH) && ((curY + deltaDist) <= MAX_Y))
					curY += deltaDist;
				if ((dir == DIR4.EAST) && ((curX + deltaDist) <= MAX_X))
					curX += deltaDist;
				if ((dir == DIR4.WEST) && ((curX - deltaDist) >= MIN_X))
					curX -= deltaDist;
				System.out.println(act.ordinal() + "" + dir.ordinal());
				ImageSequence seq = actionImgs[ActionType.WALK.ordinal()][dir.ordinal()]; // get the action frames
				seq.reset();
				state.setState(curX, curY, seq); // will notify the conntroller
				break;
			}
			break;
		case WALK:
			switch (act) {
			case HALT:
				break; // nothing changed
			case WALK:
				if ((dir == DIR4.NORTH) && ((curY - deltaDist) >= MIN_Y))
					curY -= deltaDist;
				if ((dir == DIR4.SOUTH) && ((curY + deltaDist) <= MAX_Y))
					curY += deltaDist;
				if ((dir == DIR4.EAST) && ((curX + deltaDist) <= MAX_X))
					curX += deltaDist;
				if ((dir == DIR4.WEST) && ((curX - deltaDist) >= MIN_X))
					curX -= deltaDist;

				ImageSequence seq = actionImgs[ActionType.WALK.ordinal()][dir.ordinal()]; // get the action frames
				if (dir != curDir)
					seq.reset();
				state.setState(curX, curY, seq); // will notify the conntroller
			}
			break;
		}
		curAct = act;
		curDir = dir;
	}

}
