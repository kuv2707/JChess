import java.util.*;
import java.awt.*;
import java.awt.geom.*;
class MessageAnimator implements Runnable
{
    static String win="You won!😎";
    static String lose="You lost🙄🙄";
    static String draw="Game draw😶";
    static String starts="Game starts😄";
    static String discn="Disconnected !🤡";
    String instr;
    static String greet=win;
    static Font font=new Font("LEPIFONT",Font.PLAIN,0);
    final static int maxWidth=gui.scalefactor*8;
    boolean vanish=true;
    public MessageAnimator(String s,boolean b)
    {
        greet=s;
        instr=s;
        vanish=b;
    }
    /*
     * squareX,squareY,squareWidth,squareHeight,fontSize,Opacity
     */
    static double[] data=
    {
        maxWidth/2,
        0,
        0,
        0,
        0,
        0
    };
    public void run()//see dimensions of messages dont exceed space in drawing panel 
    {
        /**
         * start from top-center of the game area
         * enlarge till width is equal to game area's width
         */
        data=new double[]{maxWidth/2,0,0,0,0,0};
        
        
        double k=0;
        while(k<1)//while squares touches left boundary of chessboard
        {
            data[0]=(int)(maxWidth/2+(0-maxWidth/2)*k);//square moves in X direction from 320 to 0
            
            
            data[1]=(int)(0+(320-data[4])*k);//square moves in X direction from 320 to 0
            
            
            data[3]=2*data[4];
            data[5]=(int)(0+(220)*k);//opacity increases
            font=new Font("LEPIFONT",Font.PLAIN,(int)data[4]);
            FontMetrics fm=game.getChessBoard().pan.getFontMetrics(font);
            int width=fm.stringWidth(greet);
            data[2]=width;
            //data[0]+=(maxWidth-width)/2;
            if(width<maxWidth-20)
            data[4]+=1;
            
            //printData();
            k+=0.01;
            game.getChessBoard().refresh();
            try
            {
                Thread.sleep(3);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        
        
        
        if(!vanish)
        return;
        k=0;
        while(k<1)
        {
            data[5]=220*(1-k);
            k+=0.01;
            game.getChessBoard().refresh();
            try
            {
                Thread.sleep(3);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        
    }
    public static void drawContent(Graphics2D g)
    {
        //draw greeting
        /*
                Font f=;
                
                g.setFont(f);
                g.setColor(new Color(50,120,200,opacrect));
                int txtht=fm.getHeight();
                int txtwd=fm.stringWidth(greet);
                g.fill(new RoundRectangle2D.Double((int)grx-2-(fm.stringWidth(greet)/2),(int)gry-txtht,txtwd+6,(txtht*3/2),50,50));
                g.setColor(new Color(245, 203, 167,opactext));
                
                
                */
               Color bgc,tc;
               //maybe transition these colors
        if(Environment.isDarkMode())
        {
            bgc=new Color(32, 51, 51,(int)data[5]);
            tc=new Color(250,250,250,(int)data[5]);
            
        }
        else
        {
            bgc=new Color(255, 232, 224,(int)data[5]);
            tc=new Color(0,0,0,(int)data[5]);
            
        }
        
        g.setColor(bgc);
        g.fill(new RoundRectangle2D.Double(data[0],data[1],data[2],data[3],50,50)); 
        g.setColor(tc);
        g.setFont(font);
        g.drawString(greet,(int)data[0],(int)(data[1]+(data[3]+data[4])/2));
    }
    public static void printData()
    {
        System.out.println();
        for(double i: data)
        System.out.print(i+"\t");
        System.out.println();
    }
}