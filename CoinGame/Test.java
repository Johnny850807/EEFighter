import java.util.*;
/**
 * Write a description of class Test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Test
{
    public static void main(String[] args) {
        List<Coin> coins = new ArrayList<>();
        Random random = new Random();
        
        for(int i = 0; i < 10; i++) {
            int randomInt = random.nextInt(4);
            switch (randomInt) {
                case 0:
                    coins.add(new Coin1());
                    break;
                case 1:
                    coins.add(new Coin2());
                    break;
                case 2:
                    coins.add(new Coin3());
                    break;
                case 3:
                    coins.add(new Coin4());
                    break;
            }
        }
        
    }
}
