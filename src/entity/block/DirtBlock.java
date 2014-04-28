package entity.block;

/**
 *
 * @author Robbert
 */
public class DirtBlock extends Block {

    public DirtBlock() {
        super();
        //damage = 100;
    }

    @Override
    public int getResistance(){
        return 1;
    }
    
    @Override
    public int getDrillTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] getColor() {
        //TODO change color based on damage
        return new byte[] {(byte) 139, (byte) 69, (byte) 19};
    }
}
