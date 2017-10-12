import java.util.*;

public class NewPlayerMediaPlayersFactory implements MediaPlayersFactory
{
    public static final int MP4 = 0;
	public static final int VIC = 1;
	public static final int AUDIO = 2;
	
    @Override
    public Map<Integer, MediaPlayer> createMediaPlayers(){
        HashMap<Integer, MediaPlayer> players = new HashMap<>();
        players.put(MP4, new MP4Adapter());
		players.put(VIC, new VICAdapter());
		players.put(AUDIO, new AudioPlayer());
		
		return players;
    }
}
