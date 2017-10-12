import java.util.HashMap;
import java.util.Map;

public class NewPlayer {
	private Map<Integer, MediaPlayer> players;
	private MediaPlayersFactory mediaPlayersFactory;
	
	public void setMediaPlayersFactory(MediaPlayersFactory factory){
	    this.mediaPlayersFactory = factory;
	    players = mediaPlayersFactory.createMediaPlayers();
	}
	
	public void play(int type) {
		players.get(type).play();
	}
	
}
