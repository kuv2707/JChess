import java.awt.Color;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.*;
public class NetHuman extends Human
{
    ObjectOutputStream peer_send ;
    Socket peer;
    public NetHuman(Color c, String s) {
        super(c, s);
        
    }
    public void playMove()
    {
        
        game.getChessBoard().pan.addMouseListener(userMonitorForSync);
        game.getChessBoard().pan.addMouseMotionListener(userMonitorForSync);
    }
    public void endMove()
    {
        game.getChessBoard().pan.removeMouseListener(userMonitorForSync);
        game.getChessBoard().pan.removeMouseMotionListener(userMonitorForSync);
    }
    @Override
    public void setName(String s)
    {
        super.setName(s);
        try
        {
            peer_send.writeObject("renm:"+s);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    MouseAdapter userMonitorForSync=new MouseAdapter() {
        public void mousePressed(MouseEvent e)
        {
            if(e.getSource().equals(getMouseEventCauser()))
            {
                Networks.senderSequence.execute(new Runnable() {
                    public void run()
                    {
                        try
                        {
                            
                            peer_send.writeObject(new Object[]
                            {
                                e.getX(),e.getY(),
                                Networks.select
                               
                            });
                            peer_send.flush();
                        }
                        catch(Exception e)
                        {
        
                        }
                    }
                });
            }

        }
        public void mouseDragged(MouseEvent me)
        {
            if(me.getSource().equals(getMouseEventCauser()))
            {
                Networks.senderSequence.execute(new Runnable() {
                    public void run()
                    {
                        try
                        {
                            peer_send.writeObject(new Object[]
                            {
                                me.getX(),me.getY(),
                                Networks.drag
                                
                            });
                            peer_send.flush();
                        }
                        catch(Exception e)
                        {
        
                        }
                    }
                });
            }
        }
        public void mouseReleased(MouseEvent me)
        {
            if(me.getSource().equals(getMouseEventCauser()))
            {
                Networks.senderSequence.execute(new Runnable() {
                    public void run()
                    {
                        try
                        {
                            peer_send.writeObject(new Object[]
                            {
                                me.getX(),me.getY(),
                                Networks.put
                                
                            });
                            peer_send.flush();
                        }
                        catch(Exception e)
                        {
        
                        }
                    }
                });
            }
        }
    };
    @Override
    public void destroy()
    {
        if(peer!=null)
        {    try {
                peer_send.close();
                peer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * send the revival candidate selected to the peer on the other side
     */
    @Override
    public String getRevivalCandidate()
    {
        String resp=super.getRevivalCandidate();
        try
            {
                peer_send.writeObject("revv:"+resp);
                peer_send.flush();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return resp;
    }
    public void doHandShakeWith(String ip,int port)throws Exception
    {
        peer=new Socket(ip,port);
        Environment.log(" handshaked"+port);
        //Networks.writeToSocket("start the game i guess",peer);
        peer.getOutputStream().flush();
        peer_send=new ObjectOutputStream(peer.getOutputStream());
        Object nam=getName();
        peer_send.writeObject("name:"+nam);
        peer_send.flush();
        
    }
}
