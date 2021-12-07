import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
class SavedGameReader extends Player
{
    Runnable Turn;
    static BufferedReader br;
    static boolean FILEREADpaused=false;
    public SavedGameReader(Color c)
    {
        
        super(c,"");
        try
        {
            setName(br.readLine());
        }
        catch(Exception e){}
        renamable=false;
        Turn=new Runnable()
        {
            
            public void run()
            {
                while(FILEREADpaused==true)
                {
                    try
                    {
                        Thread.sleep(400);
                    }
                    catch (InterruptedException ie)
                    {
                        ie.printStackTrace();
                    }
                }
                
                String flag=FileHandler.not_end;
                try
                {
                    
                        String data=br.readLine();
                        if(data.equals(FileHandler.not_end) || data.equals(FileHandler.yes_end))
                        {
                            flag=data;
                            throw new Exception();
                        }
                        
                        char c1=data.charAt(0);
                        int fx=(int)(c1-'a');
                        
                        int fy=Integer.parseInt(data.charAt(1)+"")-1;
                        int c=2;
                        if(data.charAt(2)=='x')
                        c=3;
                        
                        char c2=data.charAt(c);
                        int tx=(int)(c2-'a');
                        c++;
                        int ty=Integer.parseInt(data.charAt(c)+"")-1;
                        c++;
                            //System.out.println(data);
                        if(data.length()-1 ==c)
                        {    
                            revival.setLength(0);
                            revival.append(""+data.charAt(c));

                        }
                            
                        Point toMove=new Point(fx,fy);
                        Point moverA=new Point(tx,ty);
                           
                        actualAction(toMove,moverA);
                }
                catch(Exception e)
                {
                        
                        e.printStackTrace();
                        if(flag.equals(FileHandler.not_end))
                        {
                            
                            game.endGame("File ended","");
                            
                        }
                        else
                        {
                            
                        }
                        Environment.log("ending recorded play");
                        return;
                }
            }
            
        };
    }
    @Override
    public void playMove()
    {
        
        gui.gameEvents.execute(Turn);
    }
    public void endMove()
    {
        //no use here
    }
    public static JPanel getEntryMenu(JFrame container)
    {
        new Thread(()->{
            connectFile();
        }).start();
        utility.GradientPanel f=new utility.GradientPanel();
        JButton b=new JButton("Play last saved game");
        b.setFont(new Font("",Font.PLAIN,28));
        b.setFocusable(false);
        b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Thread t=new utility.fadeout(container,false,false);
                        t.start();
                //create 2 savedgamereader objects
                SavedGameReader p1=new SavedGameReader(goti.colWhit);
                SavedGameReader p2=new SavedGameReader(goti.colBlak);
                //2 lines from the file have been read: the 3rd line is the perspective
                Player[] players=new Player[2];
                try {
                    String persp=br.readLine();
                    if(persp.equals("white"))
                    {
                        players[0]=p1;
                        players[1]=p2;
                    }
                    else
                    {
                        players[1]=p1;
                        players[0]=p2;
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                new game(players,new Object[]{0,false});
            }
        });
        
        f.add(b);
        return f;
    }
    public JComponent getMouseEventCauser()
    {
        return this;
    }
    public static void connectFile() 
    {
        try
        {
            FileReader fr=new FileReader("D:/chessAyush/chessgamerecording.txt");
            br=new BufferedReader(fr);
        
        }
        catch (Exception fnfe)
        {
            Environment.log("File not found show a dialog box for it!!");
        }
    }
    public void destroy()
    {
        try
                {
                    br.close();
                }
                catch(Exception e)
                {

                }
    }
    @Override
    public String getRevivalCandidate()
    {
        return revival.toString();
    }
}