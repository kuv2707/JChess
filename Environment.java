import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class Environment
{
    /**
     * contains info about amount of animations
     * light/dark mode
     */
    static Color blacksquare=new Color(158, 74, 43,220);
    static Color whitesquare=new Color(252, 235, 203,220);
    static Color controlBackground=new Color(215, 214, 229,200);
    static Color activePlayer=new Color(189,242,174);
    static Color destacar=new Color(29, 231, 159,245);
    static Color currentloccol=new Color(93, 173, 226);
    static Color controlBackGradient=currentloccol;
    static boolean mode=true;//false means dark , true means light, by default starts with light
    //11, 98, 110
    /**
     * level0:  unused
     * level1:  animating computer's moves
     * level2:  moving gotis back to original place, dead-area etc
     * level3:  fading in and out of the windows
     * level4:  rotating dead gotis
     */
    static boolean[] animLevel={true,true,true,true,false};
    /**
     * make it more readable by creating objects having 3 fields
     * leftend, rightend, currentloc   representing colors
     * 
     */
    private static void setK(float k)
    {
        blacksquare=new Color((int)(k*(19-158)+158), (int)(k*(65-74)+74),(int)(k*(82-43)+43),220);
        whitesquare=new Color((int)(k*(81-252)+252), (int)(k*(102-235)+235), (int)(k*(110-203)+203),220);
        controlBackground=new Color((int)(k*(23-215)+215), (int)(k*(23-214)+214), (int)(k*(23-229)+229),200);
        controlBackGradient=new Color((int)(k*(2-93)+93),(int)(k*(2-173)+173),(int)(k*(2-226)+226));
        //27, 79, 114
        gui.minimize.setForeground(new Color((int)(k*(190-83)+83), (int)(k*(114-11)+11), (int)(k*(237-128)+128)));
        gui.sla.setForeground(new Color((int)((k)*255),(int)((k)*255),(int)((k)*255)));
        gui.boardPicture.darkmode=(int)(200*k);
        //activePlayer=new Color((int)(k*(17-189)+189),(int)(k*(122-242)+242),(int)(k*(101-174)+174));
        currentloccol=new Color((int)(k*(11-93)+93),(int)(k*(98-173)+173),(int)(k*(110-226)+226));
        gui.hold.repaint();
        gui.config.repaint();
    }
    public static boolean isDarkMode()
    {
        return !mode;
    }
    public static void setDarkMode(boolean b)
    {
        if(b)//make darker
        {
            mode=false;
            new Thread(new Runnable()
            {
                public void run()
                {
                    float k=0;
                    while(k<=1)
                    {
                        setK(k);
                        k+=0.01;
                        try
                        {
                            Thread.sleep(2);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                    }
                }
            }).start();
            //gui.minimize.setForeground(new Color(83, 11, 128));
            destacar=destacar.darker();
            //utility.GradientPanel.out=Color.black;
        }
        else
        {
            mode=true;
            new Thread(new Runnable()
            {
                public void run()
                {
                    float k=1;
                    while(k>=0)
                    {
                        setK(k);
                        k-=0.01;
                        
                        try
                        {
                            Thread.sleep(2);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                    }
                }
            }).start();
            destacar=destacar.brighter();
            //utility.GradientPanel.out=new Color(245, 235, 234);
        }
    }
    public static void log(String message)
    {
        System.out.println(System.nanoTime()+"  "+message);
    }
    public static void log2(String message)
    {
        //System.out.println(System.nanoTime()+"/2  "+message);
    }
    
    private static boolean record=false;
    static PrintWriter gameFileWriter;
    private static BufferedWriter bw;
    public static void recordGame(boolean yesno)
    {
        record=yesno;
        if(!record)
        return;
        /* 
         * format of information in the file
         * name of white player
         * name of black player
         * perspective
         * moves in algebraic notation
         * 
         * 
         * timer will not work in replay mode
         */
        try
        {
            FileWriter fw=new FileWriter("D:/chessAyush/chessgamerecording.txt");
             bw=new BufferedWriter(fw);
            gameFileWriter=new PrintWriter(bw);
            gameFileWriter.println(game.getPlayerOfColor(goti.colWhit).getName());
            gameFileWriter.println(game.getPlayerOfColor(goti.colBlak).getName());
            gameFileWriter.println(game.getHerePlayer().getTeamName());
        }
        catch (IOException ioe)
        {
            //ioe.printStackTrace();
        }
        
    }
    public static boolean canRecordGame()
    {
        return record;
    }
    public static Color getOppositeColor(Color c)
    {
        if(c==goti.colWhit)
        return goti.colBlak;
        else
        return goti.colWhit;
    }
    public static void __loadGame__()
    {
        audio.main();
        
    }
}