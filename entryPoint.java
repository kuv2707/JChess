import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.geom.*;

import javax.imageio.ImageIO;
import javax.swing.UIManager.*;
class entryPoint implements Runnable
{
    public static void test()
    {
        
    }
    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new entryPoint());
        
    }
    public static void main()
    {
        entryPoint.main(new String[]{});
    }
    public static void close(boolean ask)
    {
        if(ask==true)
        {
        modalYesNo ex=new modalYesNo("Do you really want to leave the game?","Continue game","Leave game",gui.cover);
        int result=ex.run();
        System.out.println(result);
        if(result==modalYesNo.NO)
        {
            return;
        }
      }
      //these throw exception when game hasnt started and tried to exit
      FileHandler.exitTask();
      if(game.getHerePlayer()!=null)
      game.getHerePlayer().destroy();
      if(game.getOppositePlayer()!=null)
      game.getOppositePlayer().destroy();
      if(game.getChessBoard()!=null)
      game.getChessBoard().removemouselisteners();
      
      Environment.log("almost about to end");
      Thread t=new utility.fadeout(gui.frame,true,true);
      t.start();
    }
    
    static JLabel p=new JLabel("Choose an option");
    static JTextField num=new JTextField();
    static String gametype="";
    static JFrame frame;
    public void run()
    {
        //System.out.println(.getClassName());
        try
        {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            game.icon=ImageIO.read(game.class.getResourceAsStream("/imgz/icon.png"));
            
        }
        catch (Exception cnfe)
        {
            cnfe.printStackTrace();
        }
               try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
            
        }
        frame=new JFrame("Login panel");
        frame.setIconImage(game.icon);
        frame.setUndecorated(true);
        //frame.setLayout(null);
        frame.setSize(400,400);
        frame.setShape(new RoundRectangle2D.Double(0,0,frame.getWidth(),frame.getHeight(),15,15));
        frame.setLocationRelativeTo(null);
        new Thread(new colortrans()).start();
        
        entryPane ep=new entryPane(frame);
        ep.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),"close");//0 specified no modifier: see https://docs.oracle.com/javase/7/docs/api/javax/swing/KeyStroke.html
        ep.getActionMap().put("close",new AbstractAction()
        {
            public void actionPerformed(ActionEvent ae)
            {
                close(false);
                Thread t=new utility.fadeout(frame,true,true);
                t.start();
                
            }
        });
        
        frame.add(ep);
        //frame.setOpacity(0);
        frame.setVisible(true);
        //new Thread(new utility.fadein(frame)).start();
    }
    static entryPane t;
    static class entryPane extends JTabbedPane
    {
        public entryPane(JFrame container)
        {
            add(Human.getEntryMenu(container),"2 player");
            add(AI.getEntryMenu(container),"AI");
            
            add(Online.getHostEntryMenu(container),"Host a game");
            add(Online.getClientEntryMenu(container),"Join a game");
            addTab("Others",SavedGameReader.getEntryMenu(container));//addTab(string,icon,component) for adding icon to tabs
            add(new utility.GradientPanel(),"Exit");
            setFocusable(false);
            t=this;
            addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent me)
                {
                    if(t.getSelectedIndex()==5)
                    {
                        close(false);
                        Thread t=new utility.fadeout(frame,true,true);
                        t.start();
                    }
                     offs=new Point(me.getX(),me.getY());
                }
                
            });
            addMouseMotionListener(new MouseAdapter()
            {
                public void mouseDragged(MouseEvent me)
                {
                    Point now=container.getLocation();
                    container.setLocation(new Point(now.x+me.getX()-offs.x,now.y+me.getY()-offs.y));
                }
            });
            new javax.swing.Timer(40,new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    repaint();
                }
            }).start();
        }
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
        }
    }
    public static void disableAllOptions()
    {
        for(int i=0;i<t.getTabCount()-1;i++)
        {
            if(i!=t.getSelectedIndex() )
            t.setEnabledAt(i,false);
                            
        }
    }
    static Point offs;
    
    
    static class loadLabel extends JLabel
    {
        double t=0;
        utility.rgbButton b;
        public loadLabel(utility.rgbButton b)
        {
            super(b.getText(),JLabel.CENTER);
            this.b=b;
            this.setBounds(b.getBounds());
            this.setFont(b.getFont());
            new javax.swing.Timer(16,new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    t+=.02;
                    repaint();
                }
            }).start();
            addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    System.out.println(t);
                }
            });
        }
        public void paintComponent(Graphics gg)
        {
            super.paintComponent(gg);
            Graphics2D g=(Graphics2D)gg;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(new Color((int)(Math.sin(t)*120+128),(int)(Math.cos(t)*120+128),(int)(Math.sin(t+1.3)*120+128)));
            g.drawString("🙄",-25+(int)((getWidth()/2)+( ((getWidth()/2)-50)*Math.sin(t))),getHeight()-5);
        }
    }
    static String ip=null;
    
    
}
