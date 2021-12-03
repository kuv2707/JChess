import java.util.*;
import java.awt.*;
import java.util.concurrent.*;
class PossibLox<P,C> extends HashMap<Point,Color>
{
    Color belongsTo;
    float transparency;
    boolean shown=false;
    ExecutorService order;
    public PossibLox(Color c)
    {
        super();
        belongsTo=c;
        order=Executors.newFixedThreadPool(1);
    }
    public boolean isFor(Player c)
    {
        return(c.getColor().equals(this.belongsTo));
    }
    public void show()
    {
        if(shown)
        return;
        shown=true;
        order.execute(new Runnable()
        {
            public void run()
            {
                while(transparency<230)
                {
                    transparency+=1;
                    game.getChessBoard().refresh();
                    try
                    {
                        Thread.sleep(1);
                    }
                    catch (InterruptedException ie)
                    {
                        ie.printStackTrace();
                    }
                }
            }
        });
    }
    public void fadeAway()
    {
        order.execute(new Runnable()
        {
            public void run()
            {
                while(transparency>0)
                {
                    transparency--;
                    game.getChessBoard().refresh();
                    try
                    {
                        Thread.sleep(1);
                    }
                    catch (InterruptedException ie)
                    {
                        ie.printStackTrace();
                    }
                }
            }
        });
    }
}