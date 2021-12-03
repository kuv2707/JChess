import java.util.*;
import java.awt.event.*;
import java.awt.*;
class colortrans implements Runnable
{
    static double tr=0;
    static double tb=0;
    static double t=0;
    static double A=100;
    static int r=213;
    static int b=216;
    static int g=231;
    static Color backg=new Color(r,g,b,30);
    @Deprecated
    public void run()
    {
        
        new javax.swing.Timer(6,new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if(game.isGameActive()!=false)
                {
                r=(int)(138+A*Math.sin(tr));
                tr+=0.02+0.02*Math.sin(t);
                g=(int)(138+A*Math.cos(tr));
                b=(int)(138+A*Math.sin(Math.toRadians(45)+tb));
                tb+=0.02+0.02*Math.cos(t);
                t+=0.004;
              }
                r=213;
                b=216;
                g=231;
                
            }
        });
           
            
            
        
    }
    
}