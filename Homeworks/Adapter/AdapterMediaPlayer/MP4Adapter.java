import somewhere.com.*;
public class MP4Adapter implements MediaPlayer{
    private MP4Player player = new MP4Player();
	@Override
	public void play() {

		player.playMP4();
	}

}
