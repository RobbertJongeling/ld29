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

    private final List<List<Block>> blocks;

    public World() {
        this.blocks = new ArrayList<>();
        for (int i = 0; i < 201; i++) {
            ArrayList<Block> subBlocks = new ArrayList<>();
            for (int j = 0; j < 201; j++) {
                subBlocks.add(new AirBlock());
            }
            this.blocks.add(subBlocks);
        }
    }

    public void addBlock(int gridX, int gridY, Block block) {
        Block b = blocks.get(gridX).get(gridY);
        if (b instanceof AirBlock) {
            blocks.get(gridX).set(gridY, block);
        }
    }

    public void changeBlock(int gridX, int gridY, Block block) {
        blocks.get(gridX).set(gridY, block);
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public Block getBlock(int gridX, int gridY) {
        if (gridX < 0 || gridY < 0) {
            return new AirBlock();
        }
        if (gridX >= getSizeX() || gridY >= getSizeY()){
            return new AirBlock();
        }
        return blocks.get(gridX).get(gridY);
    }

    public Block damageBlock(int gridX, int gridY, int damage) {
        Block b = blocks.get(gridX).get(gridY);
        b.doDamage(damage);
        if (b.getDamage() < 0) {
            blocks.get(gridX).set(gridY, new AirBlock());
            return b;
        }
        return null;
    }

    public Point getPlayerLocationInGrid(int realX, int realY) {
        int retX = (int) Math.floor(realX / blockWidth);
        int retY = (int) Math.floor(realY / blockWidth);
        return new Point(retX, retY);
    }
    
    public int getSizeX(){
        return blocks.size();
    }
    
    public int getSizeY(){
        return blocks.get(0).size();
    }

}
