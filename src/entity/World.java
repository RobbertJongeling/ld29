/*
 * The MIT License
 *
 * Copyright 2014 David.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package entity;

import entity.block.*;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.Point;

/**
 *
 * @author David
 */
public class World {
    private final int blockWidth = 50;
        
    private final List<Block> blocks;
    
    public World(){
        this.blocks = new ArrayList<>();
    }
    
    public List<Block> getBlocks(){
        return blocks;
    }
    

    public void addBlock(int gridX, int gridY, Blocktype type){
        for(Block b : blocks){
            if(b.getX() == gridX && b.getY() == gridY){// block is already on that spot
                return;
            }
        }
        
        switch(type) {
            case AIR: 
                blocks.add(new AirBlock(gridX, gridY, type));
                break;
            case DIRT:
                blocks.add(new DirtBlock(gridX, gridY, type));
                break;
            case STONE:
                blocks.add(new StoneBlock(gridX,gridY ,type));
                break;
        }
    }
    public int getBlockWidth(){        
        return blockWidth;
    }
    
    public Block getBlock(int gridX, int gridY){
        for(Block b : blocks){
            if(b.getX() == gridX && b.getY() == gridY){
                return b;
            }
        }
        return null;
    }
    
    public Point getPlayerLocationInGrid(int realX, int realY){        
        int retX = (int)Math.floor(realX/blockWidth);
        int retY = (int)Math.floor(realY/blockWidth);
        return new Point(retX, retY);
    } 
    
}
