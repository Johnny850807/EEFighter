
public abstract class Coin
{
   protected Game gm;
    public boolean runGame() {
        gm = genGame() ; // this is the factory method
        return gm.run();
    }
    abstract Game genGame(); // the abstract method
}
