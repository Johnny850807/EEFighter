import somewhere.com.*;
public class VICAdapter implements MediaPlayer{
    private VICPlayer player = new VICPlayer();
	@Override
	public void play() {

		player.playVIC();
	}

}