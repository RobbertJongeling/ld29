package entity.block;

import java.awt.Color;

/**
 *
 * @author Robbert
 */
public class StoneBlock extends Block {

    public StoneBlock(int x, int y, Blocktype type) {
        super(x, y, type);
    }

    @Override
    public int getDrillTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] getColor() {
        //TODO change color based on damage
        return new byte[] {(byte) 119, (byte) 136, (byte) 153};
    }
}
