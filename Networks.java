import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.awt.*;
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
            ServerSocket ss=new ServerSocket(port);
            Socket s=ss.accept();
            s.getOutputStream().flush();
            Environment.log("Accepted connection in network class "+port);
            instream=new ObjectInputStream(s.getInputStream());
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
                    
                    
                    Point rec=(Point)recu[0];
                    String s=(String)recu[2];
                    Environment.log(rec.toString());
                    if(s!=null &&  s.equals("")==false)
                    player.revival=s;
                    Byte task=(Byte)recu[1];
                    if(task.equals(select))
                    {
                        Point toMove=rec;
                        toMove=new Point(toMove.x,toMove.y);
                        MouseEvent cli=new MouseEvent(player,0,9999,0,toMove.x,toMove.y,1,true);
                        game.getChessBoard().mousePressed(cli);//these events eventually call back the server with the same data that it itself provided, and the server rejects it as that data is for a turn that already has been played so automaticallly bach gaye useless anomalies se
                        //System.out.println(toMove);
                    }
                    
                    if(task.equals(drag))
                    {
                        Point togo=rec;
                        MouseEvent drag=new MouseEvent(player,0,9999,0,togo.x,togo.y,1,true);
                         game.getChessBoard().mouseDragged(drag);
                        
                    }
                    
                    if(task.equals(put))
                    {
                        Point moverA=rec;
                        moverA=new Point(moverA.x,moverA.y);
                        MouseEvent rel=new MouseEvent(player,0,9999,0,moverA.x,moverA.y,1,true);
                        game.getChessBoard().mouseReleased(rel);
                        
                    }
                    
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    return;
                }
            }
    }
    
}