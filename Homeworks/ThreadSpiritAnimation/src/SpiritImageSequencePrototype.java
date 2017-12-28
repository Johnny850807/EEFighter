public class SpiritImageSequencePrototype implements Cloneable, ImageSequencePrototype
{
    private static ImageSequencePrototype instance;

     protected ImageSequence[][] actionImgs = {
     { new ImageSequence("pics/halt0/halt","png", 1), new ImageSequence("pics/halt1/halt","png", 1),
     new ImageSequence("pics/halt2/halt","png", 1), new ImageSequence("pics/halt3/halt","png", 1)},
     { new ImageSequence("pics/walk0/walk","png", 3), new ImageSequence("pics/walk1/walk","png", 3),
     new ImageSequence("pics/walk2/walk","png", 3), new ImageSequence("pics/walk3/walk","png", 3)}};
     
     private SpiritImageSequencePrototype() { } //讓別人無法呼叫來產生SpiritImageSequencePrototype物件
    
     public ImageSequence getImageSequence(int act, int dir) {
         return actionImgs[act][dir];
     }
     public static ImageSequencePrototype getInstance() {
    
         if (instance==null) instance = new SpiritImageSequencePrototype(); //只會產生一次(Singleton)
         Object cp =null;
         try { cp = instance.clone();} catch(Exception e){ System.out.println(e);} //prototyping by cloning instance
         if (cp!=null) {
         return (ImageSequencePrototype) cp;
         } else
         return null;
     }
     
     @Override
     public Object clone() {
         SpiritImageSequencePrototype retObj = null;
         try {
             retObj = (SpiritImageSequencePrototype) super.clone(); //first copy the two-dim array shallowly
        
             for (int i=0; i< retObj.actionImgs.length ; i++) {
                 for (int j=0;j <retObj.actionImgs[i].length; j++) {
                     //copy the ImageSequence object at [i][j],
                     retObj.actionImgs[i][j]= (ImageSequence) actionImgs[i][j].clone();
                     // note the img array files will not be copied, saving memory
                 }
             }
         } catch (Exception e) {}
         return retObj;
    }
}
