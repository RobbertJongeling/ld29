package launcher;

import entity.World;
import entity.block.*;

/**
 *
 * @author Robbert
 */
public class Worldgenerator {

    public Worldgenerator() {

    }
    
    public static World generateWorld() {
        World world = new World();
        
        for(int i = -100 ; i < 100 ; i ++){
            for(int j = 6 ; j < 15 ; j ++){                
                world.addBlock(i, j, new AirBlock());
            }
            for(int k = 5; k>-185; k--) {
                double r = Math.random();
                if(r < 0.01) {
                    world.addBlock(i, k, new AirBlock());
                }else if(r < 0.2) {
                    world.addBlock(i, k, new StoneBlock());
                } else {
                    world.addBlock(i, k, new DirtBlock());
                }
                
            }
        }        
        
        return world;
    }
}
