import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.geom.AffineTransform;
class rendering
{
    goti master;
    double angle=0,paramangle;
    Point start,end;
    double scaling=1;
    ExecutorService animator=Executors.newFixedThreadPool(1);
    public rendering(goti g,Point p)
    {
        this.master=g;
        master.guiloc=p;
    }
    public void existAt(Point p)
    {
        master.guiloc.x=p.x;
        master.guiloc.y=p.y;
    }
    public void render(Graphics2D g)
    {
        g.setColor(master.teamCol);
        AffineTransform pre=g.getTransform();
        g.setTransform(new AffineTransform());
        g.translate(master.guiloc.x*1.25+47,master.guiloc.y*1.25+40);//why add 40?
        /**
         * an offset of 40 needs to be added when repainting the holder of all jpanels
         * it is not needed for repainting the actual panel of pieces
         * but,, why!!!
         */
        g.scale(scaling*1.3,scaling*1.3);
        g.rotate(Math.toRadians(paramangle));
        if(master.face!=null)
        g.drawImage(master.face,0,0,null);
        else
        g.drawString(master.tipo,0,80);
        g.setTransform(pre);
    }
    boolean zooming=false;
    public void selectedByUser()
    {
        
        animator.execute(new Runnable()
        {
            public void run()
            {
                scale(true);
            }
        });
    }
    public synchronized void scale(boolean b)
    {
        if(b)
        {
            for(;scaling<1.125;scaling+=0.016)
                {
                    try
                    {
                        Thread.sleep(16);
                    }
                    catch (InterruptedException ie)
                    {
                        ie.printStackTrace();
                    }
                    game.getChessBoard().refresh();
                }
        }
        else
        {
            for(;scaling>=1;scaling-=0.016)
                {
                    try
                    {
                        Thread.sleep(16);
                    }
                    catch (InterruptedException ie)
                    {
                        ie.printStackTrace();
                    }
                    game.getChessBoard().refresh();
                }
        }
    }
    public void releasedByUser()
    {
        animator.execute(new Runnable()
        {
            public void run()
            {
                scale(false);
            }
        });
    }
    public void transit(Point end, double angleby)
    {
        rendering ref=this;
        Point start=master.guiloc;
        if(Math.abs(start.x-end.x)<80  &&  Math.abs(start.y-end.y)<80)
        angleby=0;
        if((start.y>=560  &&  end.y>=560)  ||  (start.y<=80  &&  end.y<=80))
        angleby=0;
        final double ss=angleby;
        
        Runnable cr=new Runnable()
        {
            public void run()
            {
                ref.move(start,end,ref.angle+ss,this);
            }
        };
        
        animator.execute(cr);
    }
    public synchronized void move(Point start,Point end,double rotate,Runnable este)//adapt it for many scenarios of goti's location
    {
       //System.out.println("         move method called "+master);
        double k=0;
       int lim=50,pause=10;
        goti m=master;
       
        double s=Math.sqrt(Math.pow(end.x-start.x,2)+Math.pow(end.y-start.y,2));
        if(s<=100 )
        {
            pause=1;
        }
        if(s<10)
        lim=2;
        
        double kf=-10;
        
         do
         {
            if(!Environment.animLevel[2])
            break;
            
            
            k=Math.pow(2,kf);
            m.guiloc.x=(int)  ((end.x*k+start.x)/(k+1));
            m.guiloc.y=(int)  ((end.y*k+start.y)/(k+1));
                                                  
            paramangle=((rotate*2*k+angle)/(2*k+1));
            
            
            kf+=0.2;
             
            game.getChessBoard().refresh();
            try
            {
                if(kf>7)
                Thread.sleep(1);
                else
                Thread.sleep(pause);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
            
        }while(kf<lim &&  gui.chaal!=m  );
        
       
       
        if(m.statOfAct==true)
        {
            m.guiloc.x=((m.guiloc.x+20)/gui.scalefactor)*gui.scalefactor;
            m.guiloc.y=((m.guiloc.y+20)/gui.scalefactor)*gui.scalefactor;
            angle=rotate;
            paramangle=angle;
                                            
        }
        
                                     
    }
    
    
    
     
}