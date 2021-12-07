
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
                sleep(1500);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        sub.guiloc=gui.guidelegate(sub.getLocation(),true);
        sub.getRendering().releasedByUser();
    }
}