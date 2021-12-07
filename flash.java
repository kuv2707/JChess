import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
class flash extends JPanel
{
    
    public void screenshot()
    {
        Environment.log("flashing");
        for(fla=0;fla<150;fla+=2)
        {
            flashcol=new Color(213, 216, 231,(int)fla);
            repaint();
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        for(fla=150;fla>0;fla-=2)
        {
            flashcol=new Color(213, 216, 231,(int)fla);
            repaint();
            try
            {
                Thread.sleep(15);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        
    }
    static float fla=0;
    Color flashcol=new Color(213, 216, 231,(int)fla);
        public flash()
        {
            super();
            setOpaque(false);
            setBounds(0,0,gui.frame.getWidth(),gui.frame.getHeight());
            
        }
        float yellow;
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(new Color(  (232f/255), (187f/255), (39f/255) ,  yellow));
            g.fillRect(0,0,getWidth(),getHeight());
            
            g.setColor(flashcol);
            g.fillRect(0,0,gui.frame.getWidth(),gui.frame.getHeight());//flash
            
            
        }
        volatile boolean overDark=false,overLight=false;
        
        private float f=0;
        public void darken(int wait)
        {
            overLight=true;
            overDark=false;
            for(;(f<=1f)&&(overDark==false);f+=0.001)
            {
                fla=(int)(235*f);
                flashcol=new Color(     (int)(213-f*(213-30))   ,   (int)(216-f*(216-30))  ,   (int)(231-f*(231-30))  , (int) fla);
                repaint();
                try
                {
                    Thread.sleep(wait);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            }
            overDark=false;
        }
    public void lighten(int wait)
    {
        overDark=true;
        overLight=false;
        for(;(f>=0f)&&(overLight==false);f-=0.001)
            {
                fla=235-(int)(235-235*f);
                flashcol=new Color(     (int)(30+(100-30)*(1-f))   ,   (int)(30+(100-30)*(1-f)) ,   (int)(30+(100-30)*(1-f))  , (int) fla);
                repaint();
                try
                {
                    Thread.sleep(wait);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            }
            //fla=0;
            //flashcol=new Color(213, 216, 231,(int)fla);
            overLight=false;
    }
}
