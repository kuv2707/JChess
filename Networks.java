import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

import java.awt.event.*;
class Networks implements Runnable
{
    Player player;
    ObjectInputStream instream;
    public static final Byte put=3;
    public static final Byte select=1;
    public static final Byte drag=2;
    public static ExecutorService senderSequence=Executors.newFixedThreadPool(1);
    
    public Networks(Online p,int port)
    {
        player=p;
        
        try {
            try (ServerSocket ss = new ServerSocket(port)) {
                Socket s=ss.accept();
                //ss.close();
                s.getOutputStream().flush();
                Environment.log("Accepted connection in network class "+port);
                instream=new ObjectInputStream(s.getInputStream());
            }
            //readMessages();
            /**
             * host computer's client object is linked to client computer's client object
             * start a thread receiving data
             */
            new Thread(this).start();
        }
        catch(Exception e){
            Environment.log(e.getMessage());
        }

    }
    
    public void run()
    {
        Object r=null;
            Object[] recu=null;
            while(true)
            {
                try
                {
                    Environment.log("Waiting for instructions");
                    r=instream.readObject();
                    if(r instanceof String)
                    {
                        
                        String inbound=(String)r;
                        String header=inbound.substring(0,5);
                        String content=inbound.substring(5);
                        Environment.log("string encountered "+inbound);
                        if(header.equals("name:"))
                        player.name=content;
                        if(header.equals("revv:"))
                        player.revival=content;
                                                
                        continue;
                    }
                    recu=(Object[])r;
                    
                    int recX=(int)recu[0];
                    int recY=(int)recu[1];
                    //rotate perspective
                    recX=8*gui.scalefactor-recX;
                    recY=8*gui.scalefactor-recY;
                    byte task=(byte)recu[2];
                    if(task==select)
                    {
                        MouseEvent cli=new MouseEvent(player,0,9999,0,recX,recY,1,true);
                        game.getChessBoard().mousePressed(cli);//these events eventually call back the server with the same data that it itself provided, and the server rejects it as that data is for a turn that already has been played so automaticallly bach gaye useless anomalies se
                        //System.out.println(toMove);
                    }
                    if(task==drag)
                    {
                        MouseEvent drag=new MouseEvent(player,0,9999,0,recX,recY,1,true);
                        game.getChessBoard().mouseDragged(drag);
                        
                    }
                    if(task==put)
                    {
                        MouseEvent rel=new MouseEvent(player,0,9999,0,recX,recY,1,true);
                        game.getChessBoard().mouseReleased(rel);
                    }
                    
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    gui.gameEvents.execute(new MessageAnimator(MessageAnimator.discn,false));
                    break;
                }
            }
    }
    
}