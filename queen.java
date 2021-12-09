import java.awt.*;
import java.util.*;
class queen extends goti
{
    
    public queen(Point ubi,Color teamCol)
    {
        super(goti.rani,ubi,teamCol);
        if(teamCol.equals(goti.colWhit))
        FENvalue='Q';
        else
        FENvalue='q';
        value=600;
    }
    
    public queen(pawn g)
    {
        super(goti.rani,g.getLocation(),g.teamCol);
        guiloc=g.guiloc;
        associate(g.getRendering());
        if(teamCol.equals(goti.colWhit))
        FENvalue='Q';
        else
        FENvalue='q';
    }
    @Override
    public stepstaken movedTo(Point p)
    {
        stepstaken steps=new stepstaken(this);
        steps.setPrevBoardLoc(this.getLocation());
        if(this.lop.containsKey(p))
        {
            goti already=gotiAt(p);
            if(already!=null)
            {
                already.killed(this);
                goti.halfmovecount=0;
                steps.killed(already);
            }
            else
            {
                goti.halfmovecount++;
            }
            this.setLocation(p);
            steps.movedTo(this.getLocation());
            callbackparent(steps);
        }
        else
        {
            //turn was flop
            steps.state(false);
        }
        this.lop=null;
        return steps;
    }
    ArrayList<Point> possibleLocations()
    {
        ArrayList<Point> l=new ArrayList<Point>();
        this.getSlantLocations(l);
        this.getRectLocations(l);
        return l;
    }
}
