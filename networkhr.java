import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
class networkhr
{
   
    /*
     static int role=7;//client(0) or server(1);
     public static void closestreams()
     {
         for(int i=0;i<out.length;i++)
         {
             try
             {
                 out[i].close();
                 in[i].close();
             }
             catch (IOException ioe)
             {
                 //ioe.printStackTrace();
                 continue;
             }
             catch(NullPointerException ne)
             {
                 return;
             }
         }
         chatout.close();
         try
         {
             if(game.record  &&  game.isGameActive())
             pw.println(CONSTANTS.not_end);
             if(game.record  &&  !game.isGameActive())
             pw.println(CONSTANTS.yes_end);
             
             bw.close();
             pw.close();
         }
         catch (Exception ioe)
         {
             //ioe.printStackTrace();
         }
         
         game.getHerePlayer().destroy();
         game.getOppositePlayer().destroy();
         try
         {
             chatin.close();
         }
         catch (Exception ioe)
         {
             //ioe.printStackTrace();
         }
         for(int i=0;i<audio.aiss.length;i++)
         {
             try
             {
                 audio.aiss[i].close();
             }
             catch (IOException e)
             {
                 System.out.println(e);
                 continue;
             }
             
         }
     }
     /*
    static class server implements Runnable
    {
        String name,time;
        
        utility.rgbButton status;
        boolean re;
        public server(String n,utility.rgbButton status,boolean reco,String te)
        {
            this.status=status;
            this.name=n;
            re=reco;
            time=te;
        }
        public void run()
        {
            
              entryPoint.loadLabel a=entryPoint.loadAnim(entryPoint.proch);
              a.setText("Waiting for connection..");
              status=null;
            String opponame="nameless";
            
                try
                    {
                         ServerSocket ss=new ServerSocket(port);
                        
                         role=1;
                         //System.out.println("hosting");
                        Socket s=ss.accept();
                        Socket s2=ss.accept();
                        Socket chatser = ss.accept();
                        Socket timeServer=ss.accept();
                        
                        out[0]=new ObjectOutputStream(s.getOutputStream());
                        in[0]=new ObjectInputStream(s.getInputStream());
                            
                        
                           out[1]=new ObjectOutputStream(s2.getOutputStream());
                        in[1]=new ObjectInputStream(s2.getInputStream());
                        
                        out[2]=new ObjectOutputStream(timeServer.getOutputStream());
                        in[2]=new ObjectInputStream(timeServer.getInputStream());
                        
                        chatout=new PrintStream(chatser.getOutputStream());
                        
                        chatin=new BufferedReader(new InputStreamReader((chatser.getInputStream())));
                        try
                    {
                        opponame=chatin.readLine();
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                    chatout.println(name);
                    
                    }
                    catch (IOException uhe)
                    {
                         //uhe.printStackTrace();
                        a.setForeground(Color.red);
                        a.setText("Network error");
                         return;
                    }
                
                    a.setText("Connection established!");
                    
                    
                    Thread t=new utility.fadeout(entryPoint.frame,false,false);
                    t.start();
                    SwingUtilities.invokeLater(new game(game.mode_online,goti.colWhit,name,opponame,re,new TimeType(time)));
        }
    }
    
    static class client implements Runnable
    {
        static String ip,name,time;
        utility.rgbButton status;
        boolean re;
        public client(String nam,String ipp,utility.rgbButton status,boolean rec)
        {
            name=nam;
            ip=ipp.trim();
            this.status=status;
            re=rec;
            
        }
        public void run()
        {
            //System.out.println(ip);
            
            entryPoint.loadLabel a=entryPoint.loadAnim(entryPoint.procc);
            a.setText("Connecting...");
            try
                    {
                        
                        Socket s=new Socket(ip,port);
                        Socket s2=new Socket(ip,port);
                        Socket chatser =new Socket(ip,port);
                        Socket timeServer=new Socket(ip,port);
                        //System.out.println("Connected to server");
                        role=0;
                        
                        
                        out[0]=new ObjectOutputStream(s.getOutputStream());
                        in[0]=new ObjectInputStream(s.getInputStream());
                        
                            
                        
                           out[1]=new ObjectOutputStream(s2.getOutputStream());
                        in[1]=new ObjectInputStream(s2.getInputStream());
                        
                        out[2]=new ObjectOutputStream(timeServer.getOutputStream());
                        in[2]=new ObjectInputStream(timeServer.getInputStream());
                        
                        chatout=new PrintStream(chatser.getOutputStream());
                        chatout.println(name);
                        chatin=new BufferedReader(new InputStreamReader((chatser.getInputStream())));
                        
                    
                    
                    String opponame="nameless";
                    try
                    {
                        opponame=chatin.readLine();
                        if(opponame.equals("{"))
                        {
                            s.close();
                            s2.close();
                            chatser.close();
                            System.exit(0);//do inform the user though
                        }
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                    //toother.println("El cliente quiere empezar");
                    //SwingUtilities.invokeLater(new guiclient());
                    a.setText("Connected !..");
                   Thread t=new utility.fadeout(entryPoint.frame,false,false);
                    t.start();
                     SwingUtilities.invokeLater(new game(game.mode_online,goti.colBlak,name,opponame,re,new TimeType(time)));
                     }
                    catch (IOException uhe)
                    {
                        a.setForeground(Color.red);
                        a.setText("Network error");
                         return;
                    }
        }
    }
    
    static class chatinreader implements Runnable
    {
        static chatboxui.ATextArea t;
        String s;
        public chatinreader(chatboxui.ATextArea t)
        {
            this.t=t;
        }
        public void run()
        {
            try
            {
                        while((s=chatin.readLine())!=null)
                        {
                            SwingUtilities.invokeAndWait(new Runnable()
                            {
                                public void run()
                                {
                                    //if(s.contains(game.thisname)==false)
                                    t.append("["+game.getOppositePlayer().getSimpleName()+"] "+s+"\n");
                                    if(gui.chat.getModel().isSelected()==false)
                                    {
                                        gui.chat.setText(gui.chat.getText()+"* ");
                                        gui.chat.setForeground(new Color(17, 122, 101));
                                    }
                                }
                            });
                            //System.out.println(s);
                        }
            }
            catch(Exception e)
            {
                
            }
            
        }
    }
    static gui board;
     static class send implements Runnable
    {
        Object p;
        int channel;
        
        public send(Object p,int channel)
        {
            this.p=p;
            this.channel=channel;
                        
        }
        public void run()
        {
            if(channel>1)
            return;
            try
            {
                out[channel].writeObject(p);
                
            }
            catch (Exception ioe)
            {
               
                    gui.removemouselisteners();
                    chatinreader.t.setForeground(Color.red.darker());
                    new Thread(new animresult(animresult.discn)).start();
                    
                    return;
            }
        }
    }
    */
}