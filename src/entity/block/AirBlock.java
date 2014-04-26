package entity.block;

/**
 *
 * @author Robbert
 */
public class AirBlock extends Block {

    public AirBlock() {
        super();
    }

    @Override
    public int getDrillTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] getColor() {
        return new byte[] {(byte) 65, (byte) 105, (byte) 225};
    }
}
