package entity.block;

/**
 *
 * @author Robbert
 */
public class StoneBlock extends Block {

    public StoneBlock() {
        super();
        damage = 100;
    }

    @Override
    public int getDrillTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] getColor() {
        //TODO change color based on damage
        return new byte[]{(byte) 119, (byte) 136, (byte) 153};
    }
}
