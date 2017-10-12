import java.util.*;
import java.util.function.*;

public class Test
{
    public static void main(String[] args) throws Exception{
        List<Coin> coins = new ArrayList<>();
        Random random = new Random();
        
        for(int i = 0; i < 10; i++) {
            int randomInt = random.nextInt(4);
            coins.add((Coin)Class.forName("Coin" + (randomInt+1)).newInstance());  // reflection
        }
        
        for (Coin coin : coins)
            coin.runGame();
    }
}
