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
    public Shockwave(int x, int y,Color col)
    {
        this.x=x;
        this.y=y;
        if(col==goti.colWhit)
        shockwavecolor=white;
        else
        shockwavecolor=black;
    }
    public void run()
    {
        circlx=x;
        circly=y;
        
        double spd=6;
        while(circlr<650)
        {
            circlr+=spd;
            spd-=0.03;
            try
            {
                Thread.sleep(5);
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
        g.setStroke(new BasicStroke(7));
        g.setColor(shockwavecolor);
        g.drawOval(circlx-circlr,circly-circlr,2*circlr,2*circlr);
    }
}