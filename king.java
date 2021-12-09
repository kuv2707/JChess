import java.awt.*;
import java.util.*;
class king extends goti
{
    boolean castlable;
    CastleProperty cp;
    public king(Point ubi,Color teamCol)
    {
        super(goti.raja,ubi,teamCol);
        castlable=true;
        this.cp=null;
        if(teamCol.equals(goti.colWhit))
        FENvalue='K';
        else
        FENvalue='k';
        value=650;
    }
    
    @Override
    public stepstaken movedTo(Point p)
    {
        stepstaken steps=new stepstaken(this);
        steps.setPrevBoardLoc(this.getLocation());
        if(this.lop.containsKey(p))
        {
            goti already=gotiAt(p);
            CastleProperty  c;
            if((c=this.getCastleProperty())!=null  &&  c.isValid()==true)
            {
                Object[] cpset=c.getCastlePropertySet(p);
                if(cpset[0].equals(p))
                {
                    rook r=(rook)cpset[2];
                    steps.castlecontent=new Object[]{r,r.getLocation()};
                    r.setLocation((Point)cpset[1]);
                }
                this.removeCastleProperty();
            }
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
            if(this.teamCol.equals(goti.colWhit))
            {
                if(steps.prevloc.equals(new Point(4,0)))   
                {
                    whitecastlableQ='-';
                    whitecastlableK='-';
                }                
            }
            else
            {
                if(steps.prevloc.equals(new Point(4,7)))   
                {
                    blackcastlableq='-';
                    blackcastlablek='-';
                }
            }
            this.castlable=false;
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
    public void setCastleProperty(CastleProperty k)
    {
        this.cp=k;
    }
    public void removeCastleProperty()
    {
        this.cp=null;
    }
    public CastleProperty getCastleProperty()
    {
        return this.cp;
    }
    ArrayList<Point> possibleLocations()
    {
        Point currL=this.getLocation();
        int x=(int)currL.getX();
        int y=(int)currL.getY();
        ArrayList<Point> l=new ArrayList<Point>();
        l.add(new Point(x+1,y));
        l.add(new Point(x+1,y+1));
        l.add(new Point(x,y+1));
        l.add(new Point(x-1,y+1));
        l.add(new Point(x-1,y));
        l.add(new Point(x-1,y-1));
        l.add(new Point(x,y-1));
        l.add(new Point(x+1,y-1));
        return l;
    }
}
