import java.awt.*;
import java.util.*;
class knight extends goti
{
    public knight(Point ubi,Color teamCol)
    {
        super(goti.ghora,ubi,teamCol);
        if(teamCol.equals(goti.colWhit))
        FENvalue='N';
        else
        FENvalue='n';
        value=400;
    }
    public knight(goti g)
    {
        super(g);
        tipo=goti.ghora;
    }
    public knight(pawn g)
    {
        super(goti.ghora,g.getLocation(),g.teamCol);
        guiloc=g.guiloc;
        associate(g.getRendering());
        if(teamCol.equals(goti.colWhit))
        FENvalue='N';
        else
        FENvalue='n';
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
        Point currL=this.getLocation();
        int x=(int)currL.getX();
        int y=(int)currL.getY();
        int counter=0;
        ArrayList<Point> l=new ArrayList<Point>();
        l.add(new Point(x+2,y+1));
        l.add(new Point(x+2,y-1));
        l.add(new Point(x-2,y-1));
        l.add(new Point(x-2,y+1));
        l.add(new Point(x-1,y+2));
        l.add(new Point(x-1,y-2));
        l.add(new Point(x+1,y-2));
        l.add(new Point(x+1,y+2));
        return l;
    }
}