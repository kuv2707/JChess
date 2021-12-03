import java.awt.*;
import java.util.*;
class pawn extends goti
{
    static Point enpassantloc=null;
    static int validenpassantturn=-1;
    static pawn inEnPassant=null;
    static int valueOfIt=100;
    public pawn(Point ubi,Color teamCol)
    {
        super(goti.pyada,ubi,teamCol);
        if(teamCol.equals(goti.colWhit))
        FENvalue='P';
        else
        FENvalue='p';
        value=100;
    }
    public pawn(goti g)
    {
        super(g);
        tipo=goti.pyada;
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
                steps.killed(already);
            }
            Point i=this.getLocation();
            if(enpassantloc!=null)
            {                           
                if(enpassantloc.equals(p))
                {
                    if(validenpassantturn==game.turnNumber)
                    {
                        inEnPassant.killed(this);
                        steps.killed(inEnPassant);                        
                    }
                }
            }
            if(subject==game.pisse)
            {
                if(Math.abs(i.y-p.y)==2)
                {
                    enpassantloc=new Point(i.x,(i.y+p.y)/2);
                    validenpassantturn=game.turnNumber+1;
                    inEnPassant=this;
                }
                else
                {
                    enpassantloc=null;
                    validenpassantturn=-1;
                    inEnPassant=null;
                }
            }
            goti.halfmovecount=0;
            this.setLocation(p);
            steps.movedTo(this.getLocation());
            String send=this.promotionCheck();
            steps.toNetSend(send);
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
    public String promotionCheck()// finally returns type of goti chosen to revive and its color
    {
        Point showloc=this.guiloc;
        int y=this.getLocation().y;
        String got="";

        if(subject!=game.pisse)
            return "";
        if( ((y==7 && this.teamCol.equals(goti.colWhit))  ||  (y==0 && this.teamCol.equals(goti.colBlak)) )  &&  this.statOfAct==true)
        {

            got=game.nowturnof.getRevivalCandidate();

            if(got.equals(goti.rani))
            {
                subject.set(subject.indexOf(this),new queen((pawn)this));
            }
            if(got.equals(goti.hathi))
            {
                subject.set(subject.indexOf(this),new rook((pawn)this));
            }
            if(got.equals(goti.ghora))
            {
                subject.set(subject.indexOf(this),new knight((pawn)this));
            }
            if(got.equals(goti.mandir))
            {
                subject.set(subject.indexOf(this),new bishop((pawn)this));
            }
        }
        return got;
    }
    ArrayList<Point> possibleLocations()
    {
        Point currL=this.getLocation();
        int x=(int)currL.getX();
        int y=(int)currL.getY();
        int counter=0;
        ArrayList<Point> l=new ArrayList<Point>();
        if(this.teamCol.equals(goti.colWhit))
        {
            if(gotiAt(new Point(x,y+1))==null)
            {
                l.add(new Point(x,y+1));
                if(  y==1  &&   gotiAt(new Point(x,y+2))==null )
                    l.add(new Point(x,y+2));
            }
        }
        if(this.teamCol.equals(goti.colBlak))
        {
            if(gotiAt(new Point(x,y-1))==null)
            {
                l.add(new Point(x,y-1));
                if(  y==6   && gotiAt(new Point(x,y-2))==null )
                    l.add(new Point(x,y-2));
            }   
        }
        killableLox(l);
        Point p=getEnPassant();
        if(p!=null)
            l.add(p);
        return l;
    }
    ArrayList<Point> killableLox(ArrayList<Point> l)
    {
        Point currL=this.getLocation();
        int x=(int)currL.getX();
        int y=(int)currL.getY();
        if(this.teamCol.equals(goti.colWhit))
        {
            goti dummy1=gotiAt(new Point(x+1,y+1));
            if(dummy1!=null)
                l.add(new Point(x+1,y+1));
            goti dummy2=gotiAt(new Point(x-1,y+1));
            if(dummy2!=null)
                l.add(new Point(x-1,y+1));
        }
        if(this.teamCol.equals(goti.colBlak))
        {
            goti dummy1=gotiAt(new Point(x+1,y-1));
            if(dummy1!=null)
                l.add(new Point(x+1,y-1));
            goti dummy2=gotiAt(new Point(x-1,y-1));
            if(dummy2!=null)
                l.add(new Point(x-1,y-1));
        }
        return l;
    }
    public Point getEnPassant()
    {
        if(validenpassantturn==game.turnNumber)
        {
            if(this.teamCol.equals(inEnPassant.teamCol)==false)
            {
                if(Math.abs(this.getLocation().x-inEnPassant.getLocation().x)==1  &&  this.getLocation().y==inEnPassant.getLocation().y)
                {
                    return enpassantloc;
                }
            }
        }
        return null;
    }
    @Deprecated
    public int getRank()
    {
        if(this.teamCol.equals(goti.colWhit))
        return (this.getLocation().y+1);
        else
        return (8-this.getLocation().y);
    }
}
