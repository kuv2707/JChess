
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
    public MessageAnimator(String s,boolean fade)
    {
        greet=s;
        instr=s;
        vanish=fade;
    }
    /*
     * squareX,squareY,squareWidth,squareHeight,fontSize,Opacity
     */
    static float squareX,squareY,squareWidth,squareHeight,fontSize,opacity;
    
    public void run()//see dimensions of messages dont exceed space in drawing panel 
    {
        /**
         * start from top-center of the game area
         * enlarge till width is equal to game area's width
         */
        squareX=maxWidth/2;
        squareY=0;
        squareWidth=0;
        squareHeight=0;
        fontSize=0;
        opacity=0;
        
        
        float k=0;
        while(k<1)//while squares touches left boundary of chessboard
        {
            //squareX=(int)(maxWidth/2+(0-maxWidth/2)*k);//square moves in X direction from 320 to 0
            
            
            squareY=(int)(0+(320-fontSize)*k);//square moves in X direction from 320 to 0
            
            
            squareHeight=2*fontSize;
            opacity=(int)(0+(220)*k);//opacity increases
            font=new Font("LEPIFONT",Font.PLAIN,(int)fontSize);
            FontMetrics fm=game.getChessBoard().pan.getFontMetrics(font);
            int width=fm.stringWidth(greet);
            squareWidth=width;
            squareX=(maxWidth-width)/2;
            if(width<maxWidth-20)
            fontSize+=1;
            
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
            opacity=220*(1-k);
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
        
        Color bgc,tc;
               //maybe transition these colors
        if(Environment.isDarkMode())
        {
            bgc=new Color(32, 51, 51,(int)opacity);
            tc=new Color(250,250,250,(int)opacity);
            
        }
        else
        {
            bgc=new Color(255, 232, 224,(int)opacity);
            tc=new Color(0,0,0,(int)opacity);
            
        }
        
        g.setColor(bgc);
        g.fill(new RoundRectangle2D.Double(squareX,squareY,squareWidth,squareHeight,50,50)); 
        g.setColor(tc);
        g.setFont(font);
        g.drawString(greet,(int)squareX,(int)(squareY+(squareHeight+fontSize)/2));
    }
    
}