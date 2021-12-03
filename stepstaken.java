import java.awt.Point;
public class stepstaken
{
    goti killed=null,master;
    boolean turnstate=false;
    String sendnet="";
    Point prevloc,movedto;
    Object[] castlecontent=null;
    boolean readiness=false;
    String algeb;
    public stepstaken(goti mas)
    {
        master=mas;
    }
    public void killed(goti b)
    {
        killed=b;
    }
    public void movedTo(Point m)
    {
        this.movedto=m;
    }
    public boolean getReadyForNext()
    {
        return readiness;
    }
    public String getAlgebra()
    {
        return algeb;
    }
    public void makeAlgebraicNotation()
    {
        String alg=(char)(96+prevloc.x+1)+""+(prevloc.y+1)+""+(char)(96+movedto.x+1)+""+(movedto.y+1);
        if(killed!=null)
        {
            alg=(char)(96+prevloc.x+1)+""+(prevloc.y+1)+"x"+(char)(96+movedto.x+1)+""+(movedto.y+1);
        }
        if(!sendnet.equals(""))
        {
            alg=alg+sendnet;
        }
        if(castlecontent!=null  &&  master instanceof king)//obviously if castlecontent is not null, goti is king
        {
            Point p=(Point)castlecontent[1];
            if(p.x==7)
            {
                alg=alg+" 0-0";
            }
            if(p.x==0)
            {
                alg=alg+" 0-0-0";
            }
        }
        algeb=alg;
    }
    public void setReadyForNext()
    {
        readiness=true;
    }
    public void toNetSend(String s)
    {
        sendnet=s;
    }
    public void state(Boolean k)
    {
        turnstate=k;
    }
    public goti whomKilled()
    {
        return killed;
    }
    public boolean validTurn()
    {
        return turnstate;
    }
    public String netSend()
    {
        return sendnet;
    }
    public void setPrevBoardLoc(Point p)
    {
        prevloc=p;
    }
    public Point getPrevBoardLoc()
    {
        return prevloc;
    }
}