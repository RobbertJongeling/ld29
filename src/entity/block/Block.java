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

package entity.block;

import java.awt.Color;

/**
 *
 * @author David
 */
public abstract class Block {
    private final int x;
    private final int y;
    private Blocktype blockType;
    //damage lvl, between 0 and 100, <0 => destroy
    protected int damage;
    
    public Block(int x, int y, Blocktype type){
        this.x = x;
        this.y = y;
        this.blockType = type;
    }
    
    public void destroy(){
        this.blockType = Blocktype.AIR;
    }
    
    public void doDamage(int amount) {
        this.damage -= amount;
        if(damage < 0) {
            this.destroy();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public Blocktype getBlockType() {
        return blockType;
    }
    
    /**
     * 
     * @return time taken to drill trough block
     */
    public abstract int getDrillTime();
    
    /**
     * 
     * @return color of block in byte array of length three, first index red, second index green, third index blue
     */
    public abstract byte[] getColor();
}
