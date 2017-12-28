
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
        actionImgs[0][0]  =  new ImageSequence("pics/food/halt0/halt","png", 1);
        actionImgs[0][1]  = new ImageSequence("pics/food/halt1/halt","png", 1) ;
        actionImgs[0][2]  = new ImageSequence("pics/food/halt2/halt","png", 1);
        actionImgs[0][3]  = new ImageSequence("pics/food/halt3/halt","png", 1);
    
        actionImgs[1][0]  =  new ImageSequence("pics/food/walk0/walk","png", 3);
        actionImgs[1][1]  =  new ImageSequence("pics/food/walk1/walk","png", 3) ;
        actionImgs[1][2]  = new ImageSequence("pics/food/walk2/walk","png", 3);
        actionImgs[1][3]  = new ImageSequence("pics/food/walk3/walk","png", 3);
    }
}
