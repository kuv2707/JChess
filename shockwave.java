import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
class Shockwave implements Runnable
{
    int x,y,r;
    static int circlx, circly,circlr;
    static Color shockwavecolor=goti.nullcol;
    static Color white1=new Color(199, 179, 158);
    static Color black1=new Color(67, 69, 98);
    static Color white2=Color.WHITE;
    static Color black2=Color.BLACK;
    static Color white=white1,black=black1;
    public Shockwave(int x, int y)
    {
        this.x=x;
        this.y=y;
        
    }
    public void run()
    {
        circlx=x;
        circly=y;
        shockwavecolor=game.nowturnof.equals(goti.colWhit)?black:white;
        double spd=6;
        while(circlr<650)
        {
            circlr+=spd;
            spd-=0.01;
            try
            {
                Thread.sleep(2);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        circlx=0;
        circly=0;
        circlr=0;
    }
    public static void render(Graphics2D g)
    {
        g.setStroke(new BasicStroke(5));
        g.setColor(shockwavecolor);
        g.drawOval(circlx-circlr,circly-circlr,2*circlr,2*circlr);
    }
}