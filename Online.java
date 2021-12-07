import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.*;
import java.io.*;

public class Online extends Player
{
    Socket peer;
    public Online(Color c, String nam) {
        super(c, nam);
    }
    @Override
    public String getRevivalCandidate()
    {
        while(revival.equals(""))
        {
            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
        String s=revival;
        revival="";
        return s;
    }
    @Override
    public void playMove() {
        game.getOpponentOf(this).endMove();
    }
    @Override
    public void destroy()
    {
        try
        {
            
            peer.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        
    }

    
    @Override
    public JComponent getMouseEventCauser() {
        return this;
    }
    
    /**
     * maybe online k liye 2 classes are not needed
     * just one class representing the online player
     * and the other class be Human
     * will added functionality of sending user movement to peer
     * by overriding its playMove method
     */
    public static JPanel getClientEntryMenu(JFrame container)
    {
        JPanel client=new utility.GradientPanel();
        client.setBorder(BorderFactory.createTitledBorder("Join a game, you will get black color"));
         client.setLayout(null);
         
            Font font=new Font("",Font.PLAIN,28);
            Font font2=new Font("",Font.PLAIN,25);
            
            JLabel lab=new JLabel("Enter ipv4 address of the host");
            lab.setBounds(10,10,420,50);
            lab.setFont(font);
            
            JTextField tf=new JTextField();
            tf.setBounds(10,60,190,40);
            tf.setFont(font2);
           
            
            
            
            JLabel nam=new JLabel("Enter your name");
            nam.setFont(font);
            nam.setBounds(10,112,250,50);
            
            JTextField name=new JTextField("");
            name.setFont(font2);
            name.setBounds(10,158,250,40);
            
            JLabel status=new JLabel("");
            status.setForeground(Color.yellow);
            status.setBounds(145,199,300,50);
            status.setFont(font);
            
            
            
            utility.rgbButton procc=new utility.rgbButton("Join game");
            procc.setForeground(Color.black.darker());
            procc.setBounds(2,250,container.getWidth()-5,119);
            procc.setFont(new Font("",Font.PLAIN,35));
            
            procc.setFocusable(false);
            
            try
            {
                InetAddress b=InetAddress.getLocalHost();
                String ip=b.getHostAddress().trim();
                tf.setText(ip);
            }
            catch (UnknownHostException uhe)
            {
                status.setForeground(Color.red);
                status.setText("Unknown host error");
                procc.setEnabled(false);
            }
            
            
            JToggleButton grab=new JToggleButton("Record this game");
            grab.setBounds(10,200,150,30);
            grab.setOpaque(false);
            grab.setFocusable(false);
            
                
            client.add(grab);
            
            client.add(procc);
            client.add(status);
            client.add(name);
            client.add(nam);
            client.add(tf);
            client.add(lab);
            
            
            
            MouseListener mouse=new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    
                    if(e.getSource()==procc  &&  procc.isEnabled()==true)
                    {
                        String nom=name.getText();
                        if(nom.equals(""))
                            nom="player2";
                            Human player=new Human(goti.colBlak,nom);
                            Online host=new Online(goti.colWhit,"<none>");

                            
                        //CLIENT
                        new Thread(new Runnable()
                        {
                            public void run()
                            {
                                try {
                                    Environment.log("about to send ip");
                                    sendIPToOpponent(tf.getText());
                                    Environment.log("sent ip");
                                    new Thread(new Runnable() {
                                        public void run()
                                        {

                                            new Networks(host,2222);
                                        }
                                    }).start();

                                    player.doHandShakeWith(tf.getText(),1111);
                                    Thread gt=new utility.fadeout(container,false,false);
                                    gt.start();
                                    new game(new Player[]{player,host},new Object[]{0,grab.getModel().isSelected()});


                                    
                                } catch (Exception ioException) {
                                    ioException.printStackTrace();
                                }


                            }
                        }).start();
                        
                        name.setEnabled(false);
                        procc.setEnabled(false);
                        grab.setEnabled(false);
                        entryPoint.disableAllOptions();
                    }
                }
            };
             
            procc.addMouseListener(mouse);
            
        return client;
    }
    public static JPanel getHostEntryMenu(JFrame container)
    {
        utility.GradientPanel host=new utility.GradientPanel();
        host.setBorder(BorderFactory.createTitledBorder("Host a game, you will get white color"));
        host.setLayout(null);

        Font font=new Font("",Font.PLAIN,28);
        Font font2=new Font("",Font.PLAIN,25);

        JLabel lab=new JLabel("Your ipv4 address is-");
        lab.setBounds(10,10,280,50);
        lab.setFont(font);

        JTextField tf=new JTextField();
        tf.setBounds(10,60,190,40);
        tf.setFont(font2);
        tf.setOpaque(false);

        tf.setEditable(false);

        JLabel nam=new JLabel("Enter your name");
        nam.setFont(font);
        nam.setBounds(10,112,250,50);

        JTextField name=new JTextField("");
        name.setFont(font2);
        name.setBounds(10,158,250,40);

        JLabel status=new JLabel("");
        status.setForeground(Color.yellow);
        status.setBounds(125,199,350,50);
        status.setFont(font);



        utility.rgbButton proch=new utility.rgbButton("Host game");
        proch.setForeground(Color.black);
        proch.setBounds(2,250,container.getWidth()-5,119);
        proch.setFont(new Font("",Font.PLAIN,35));

        proch.setFocusable(false);

        JToggleButton grab=new JToggleButton("Record this game");
        grab.setBounds(10,200,150,30);
        grab.setOpaque(false);
        grab.setFocusable(false);

        JComboBox<String> jcb=new JComboBox<>(TimeType.times);
        jcb.setBounds(190,200,120,30);

        host.add(proch);
        host.add(status);
        host.add(name);
        host.add(nam);
        host.add(tf);
        host.add(lab);
        host.add(grab);
        host.add(jcb);
        try
        {
            InetAddress b=InetAddress.getLocalHost();
            tf.setText(b.getHostAddress().trim());
        }
        catch (UnknownHostException uhe)
        {
            status.setForeground(Color.red);
            status.setText("Unknown host exception");
        }

        MouseListener mouse=new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {

                if(e.getSource()==proch  &&  proch.isEnabled()==true)
                {
                    String nom=name.getText();
                    if(nom.equals(""))
                        nom="player1";
                    
                    Human player=new Human(goti.colWhit,nom);
                    Online client=new Online(goti.colBlak,"<client>");
                    
                    
                    //HOST
                    new Thread(new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                Environment.log("about to get ip");
                                String ipadd= getOpponentIP();
                                Environment.log("The client's ip address is "+ipadd);
                                new Thread(new Runnable()
                                {
                                    public void run()
                                    {

                                        new Networks(client,1111);
                                    }
                                }).start();
                                player.doHandShakeWith(ipadd,2222);
                                Thread gt=new utility.fadeout(container,false,false);
                                gt.start();
                                new game(new Player[]{player,client},new Object[]{0,grab.getModel().isSelected()});



                                
                            } catch (Exception ioException) {
                                ioException.printStackTrace();
                            }

                        }
                    }).start();
                    name.setEnabled(false);
                    proch.setEnabled(false);
                    grab.setEnabled(false);
                    entryPoint.disableAllOptions();
                }
            }
        };

        proch.addMouseListener(mouse);

        return host;
    }
    public static String getOpponentIP() throws IOException {
        
        ServerSocket ss=new ServerSocket(2707);
        Socket s=ss.accept();
        
        String ip=(((InetSocketAddress) s.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
        s.close();
        ss.close();
        return ip;
        
    }
    public static void sendIPToOpponent(String ip) throws IOException {
        //connect to the server so that it gets the ip address of the client, then connects its own client object with this client.
        //meanwhile this machine will connect its host player with the host machines player
        Socket s=new Socket(ip,2707);
        s.close();
    }
    public void endMove()
    {
        
    }
    
}
