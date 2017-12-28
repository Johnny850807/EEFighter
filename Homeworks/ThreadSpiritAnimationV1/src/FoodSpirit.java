
/**
 * Write a description of class FoodSpirite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FoodSpirit extends Spirit
{
    FoodSpirit(Controller ctl) {
        super(ctl);        
        prototype = FoodSpiritImageSequencePrototype.getInstance();
        actionImgs[0][0]  = prototype.getImageSequence(0, 0);
        actionImgs[0][1]  = prototype.getImageSequence(0, 1);
        actionImgs[0][2]  = prototype.getImageSequence(0, 2);
        actionImgs[0][3]  = prototype.getImageSequence(0, 3);
    
        actionImgs[1][0]  = prototype.getImageSequence(1, 0);
        actionImgs[1][1]  = prototype.getImageSequence(1, 1);
        actionImgs[1][2]  = prototype.getImageSequence(1, 2);
        actionImgs[1][3]  = prototype.getImageSequence(1, 3);
    }
}
