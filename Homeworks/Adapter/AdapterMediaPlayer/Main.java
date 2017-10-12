
public class Main
{
    public static void main(String[] argv){
        NewPlayer player = new NewPlayer();
        player.setMediaPlayersFactory(new NewPlayerMediaPlayersFactory());
        
        player.play(NewPlayerMediaPlayersFactory.MP4);
        player.play(NewPlayerMediaPlayersFactory.VIC);
        player.play(NewPlayerMediaPlayersFactory.AUDIO);
    }
}
