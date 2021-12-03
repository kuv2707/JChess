import java.awt.*;
import java.util.*;
class rook extends goti
{
    boolean castlable;
    public rook(Point ubi,Color teamCol)
    {
        super(goti.hathi,ubi,teamCol);
        castlable=true;
        if(teamCol.equals(goti.colWhit))
        FENvalue='R';
        else
        FENvalue='r';
        value=500;
    }
    public rook(pawn g)
    {
        super(goti.hathi,g.getLocation(),g.teamCol);
        guiloc=g.guiloc;
        castlable=false;
        associate(g.getRendering());
        //gui.guidelegate(location,true);
        if(teamCol.equals(goti.colWhit))
        FENvalue='R';
        else
        FENvalue='r';
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
            this.castlable=false;
            if(this.teamCol.equals(goti.colWhit))
            {
                if(steps.prevloc.equals(new Point(0,0)))   
                whitecastlableQ='-';
                if(steps.prevloc.equals(new Point(7,0)))   
                whitecastlableK='-';
            }
            else
            {
                if(steps.prevloc.equals(new Point(0,7)))   
                blackcastlableq='-';
                if(steps.prevloc.equals(new Point(7,7)))   
                blackcastlablek='-';
            }
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
    public rook(goti g)
    {
        super(g);
        this.castlable=((rook)g).castlable;
        tipo=goti.hathi;
    }
    ArrayList<Point> possibleLocations()
    {
        return getRectLocations(new ArrayList<Point>());
    }
}