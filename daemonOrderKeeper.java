import java.util.*;
import java.awt.*;

class daemonOrderKeeper extends Thread
{
    goti sub;
    public daemonOrderKeeper(goti s)
    {
        sub=s;
    }
    public void run()
    {
        
            try
            {
                this.sleep(1500);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        sub.guiloc=gui.guidelegate(sub.getLocation(),true);
        sub.getRendering().releasedByUser();
    }
}