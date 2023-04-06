import java.awt.*;
class Shockwave implements Runnable
{
    int x,y,r;
    static Shockwave instance=null;
    static boolean executing=false;
    Color shockwavecolor=goti.nullcol;

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
        System.out.println("shock!");
        instance=this;
    }
    public void run()
    {
        if(executing==true|| instance==null)
        return;
        executing=true;
        double spd=6;
        while(instance.r<750)
        {
            instance.r+=spd;
            if(spd>10)spd-=0.03;
            try
            {
                Thread.sleep(5);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        executing=false;
        instance=null;
    }
    public void render(Graphics2D g)
    {
        g.setStroke(new BasicStroke(7));
        g.setColor(instance.shockwavecolor);
        int circlx=instance.x;
        int circly=instance.y;
        int circlr=instance.r;
        g.drawOval(circlx-circlr,circly-circlr,2*circlr,2*circlr);
    }
    public static void drawContent(Graphics2D g)
    {
        if(instance!=null)
        instance.render(g);
    }
}