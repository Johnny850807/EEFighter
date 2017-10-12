import java.util.*;
import java.util.function.*;

public class Test
{
    public static void main(String[] args) {
        List<Coin> coins = new ArrayList<>();
        Map<Integer,Supplier<Coin>> coinMap = new HashMap<>();
        coinMap.put(0, () -> new Coin1());
        coinMap.put(1, () -> new Coin2());
        coinMap.put(2, () -> new Coin3());
        coinMap.put(3, () -> new Coin4());
        Random random = new Random();
        
        for(int i = 0; i < 10; i++) {
            int randomInt = random.nextInt(4);
            coins.add(coinMap.get(randomInt).get());
        }
        
        for (Coin coin : coins)
            coin.runGame();
    }
}
