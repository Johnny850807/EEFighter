
public abstract class Game
{
    public boolean run() {
        System.out.println("Welcome!");
        return start();
    }
     abstract boolean start() ; // an abstract method
     
}
