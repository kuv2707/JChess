import java.awt.Point;
import java.util.*;
class CastleProperty
{
    ArrayList<Object[]> castles=new ArrayList<>();
    boolean isvalid=false;
    public boolean isValid()
    {
        return isvalid;
    }
    public void bind(Point kingloc, Point newrookloc,rook therook)
    {
        castles.add(new Object[]{kingloc,newrookloc,therook});
        isvalid=true;
    }
    public boolean isInPlace(Point p)
    {
        for(Object[] h:castles)
        {
            Point ps=(Point)h[0];
            if(p.equals(ps))
            return true;
        }
        return false;
    }
    public Object[] getCastlePropertySet(Point p)
    {
        for(Object[] h:castles)
        {
            Point ps=(Point)h[0];
            if(p.equals(ps))
            return h;
        }
        return null;
    }
    public rook getRookToCastleWith(Point kl)
    {
        for(Object[] h:castles)
        {
            Point ps=(Point)h[0];
            if(kl.equals(ps))
            {
                return((rook)h[2]);
            }
        }
        return null;//theoretically should never happen
    }
}