import java.awt.*;
import java.util.*;
class bishop extends goti
{
    public bishop(Point ubi,Color teamCol)
    {
        super(goti.mandir,ubi,teamCol);
        if(teamCol.equals(goti.colWhit))
        FENvalue='B';
        else
        FENvalue='b';
        value=300;
    }
    
    public bishop(pawn g)
    {
        super(goti.rani,g.getLocation(),g.teamCol);
        guiloc=g.guiloc;
        associate(g.getRendering());
        if(teamCol.equals(goti.colWhit))
        FENvalue='B';
        else
        FENvalue='b';
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
        return getSlantLocations(new ArrayList<Point>());
    }
}
