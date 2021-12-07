import java.util.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
public class utility
{
    static class fadein implements Runnable
    {
        JFrame sub;
        public fadein(JFrame fram)
        {
            sub=fram;
        }
        public void run()
        {
            float d=0;
            while(d<1  &&  Environment.animLevel[3])
            {
                sub.setOpacity(d);
                d+=0.03;
                try
                {
                    Thread.sleep(15);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            }
            sub.setOpacity(1.0f);
        }
    }
    public static void appearDialog(JDialog y)
    {
        y.setOpacity(0f);
        new Thread(new Runnable()
            {
                public void run()
                {
                    for(float f=0f;f<0.98 && ( Environment.animLevel[3]==true);f+=0.1)
                    {
                        y.setOpacity(f);
                        try
                        {
                            Thread.sleep(15);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                    }
                    y.setOpacity(0.98f);
                }
            }).start();
            y.setVisible(true);//this statement blocks the thread as the jdialogs are modal(i made them that way)
    }
    public static void disappearDialog(JDialog y)
    {
        new Thread(new Runnable()
                    {
                        public void run()
                        {
                            
                            for(float f=0.97f;f>0 && ( Environment.animLevel[3]==true);f-=0.1)
                            {
                                y.setOpacity(f);
                                try
                                {
                                    Thread.sleep(15);
                                }
                                catch(Exception e)
                                {
                                    
                                }
                            }
                            y.setOpacity(0f);
                            y.dispose();//how does it act on the dialog object without reference to it rather than actionlistener object or runnable object?!!!
                        }
                    }).start();
    }
    public static void appearJFrame(JFrame y)
    {
        y.setOpacity(0f);
        new Thread(new Runnable()
            {
                public void run()
                {
                    for(float f=0f;f<0.98 && ( Environment.animLevel[3]==true);f+=0.1)
                    {
                        y.setOpacity(f);
                        try
                        {
                            Thread.sleep(15);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                    }
                    y.setOpacity(0.98f);
                }
            }).start();
            y.setVisible(true);//this statement blocks the thread as the jdialogs are modal(i made them that way)
    }
    public static void disappearJFrame(JFrame y)
    {
        new Thread(new Runnable()
                    {
                        public void run()
                        {
                            
                            for(float f=0.97f;f>0 && ( Environment.animLevel[3]==true);f-=0.1)
                            {
                                y.setOpacity(f);
                                try
                                {
                                    Thread.sleep(15);
                                }
                                catch(Exception e)
                                {
                                    
                                }
                            }
                            y.setOpacity(0f);
                            y.dispose();//how does it act on the dialog object without reference to it rather than actionlistener object or runnable object?!!!
                        }
                    }).start();
    }
    static class fadeout extends Thread
    {
        /**
         * to be called on mainframe when application is terminated
         */
        boolean stream,doexit;
        JFrame sub;
        public fadeout(JFrame frame,boolean stream,boolean ex)
        {
            this.sub=frame;
            this.stream=stream;
            this.doexit=ex;
        }
        public void run()
        {
            
            float d=1;
            while(d>0  &&  Environment.animLevel[3])
            {
                sub.setOpacity(d);
                d-=0.03;
                try
                {
                    Thread.sleep(15);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            }
            sub.setOpacity(0f);
            sub.dispose();
            if(this.doexit==true)
            System.exit(0);
        }
    }
    
    
    
    
    static class GradientPanel extends JPanel
    {
        static Color in,out;
        static int x,y;
        static double param;
        public GradientPanel()
        {
            super();
            in=new Color(253, 180, 174);
            out=new Color(245, 235, 234);
            new javax.swing.Timer(150,new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    x=(int)((getWidth()/2)*(1.0+Math.sin(param)));
                    y=(int)((getHeight()/2)*(1.0+Math.cos(param)));
                    param+=0.01;
                    repaint();
                }
            }).start();
        }
        public void paintComponent(Graphics gg)
        {
            super.paintComponent(gg);
            Graphics2D g=(Graphics2D)gg;
            g.setPaint(new RadialGradientPaint(x,y,
                    Math.max(getWidth(),getHeight()),
                    new float[]{0.33f,0.9f},
                    new Color[]{in,out}));
            g.fillRect(0,0,this.getWidth(),this.getHeight());
        }
    }
    
    static class rgbButton extends JLabel
    {
        String text;
        Color foregroundColor;
        Color backupback,backupfore;
        Color background;
        ExecutorService animator=Executors.newFixedThreadPool(1);
        //=new Color(colortrans.g,colortrans.b,colortrans.r,alfa);
        volatile int alfa=70;
        Point epicenter=null;
        int radius=0;
        public rgbButton(String s)
        {
            super("",JLabel.CENTER);
            text=s;
            setFont(CONSTANTS.generalfont);
            foregroundColor=Color.black;
            setOpaque(true);
            background=new Color(93, 173, 226);
            
            addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent me)
                {
                    epicenter=new Point(me.getX(),me.getY());
                    
                    background=new Color(background.getRed(), background.getGreen(), background.getBlue());//as its alpha was 0
                    animator.execute(new Runnable()
                    {
                        public void run()
                        {
                            for(radius=0;radius<getWidth()+60;radius+=2)
                            {
                                try
                                {
                                    repaint();
                                    Thread.sleep(8);
                                }
                                catch (InterruptedException ie)
                                {
                                    ie.printStackTrace();
                                }
                                
                            }
                        }
                    });
                }
                public void mouseReleased(MouseEvent me)
                {
                    animator.execute(new Runnable()
                    {
                        public void run()
                        {
                            for(int a=255;a>=0;a-=2)
                            {
                                background=new Color(background.getRed(),background.getGreen(),background.getBlue(),a);
                                try
                                {
                                    repaint();
                                    Thread.sleep(8);
                                }
                                catch (InterruptedException ie)
                                {
                                    ie.printStackTrace();
                                }
                            }
                            epicenter=null;
                            radius=1;
                            repaint();
                        }
                    });
                    
                }
            });
            
            
            
        }
        
        public void setTactileColor(Color c)
        {
            backupback=c;
            background=c;
        }
        
        @Override
        public void setForeground(Color k)
        {
            backupfore=k;
            foregroundColor=k;
        }
        @Override
        public void setEnabled(boolean b)
        {
            if(b)
            {
                background=backupback;
                foregroundColor=backupfore;
            }
            else
            {
                background=new Color(10,20,30,20);
                foregroundColor=Color.gray;
            }
            super.setEnabled(b);
        }
        @Override
        public void setOpaque(boolean b)
        {
            super.setOpaque(false);//no option to set it to false!
        }
        @Override
        public void setText(String s)
        {
            text=s;
        }
        
        public void paintComponent(Graphics gG)
        {
            super.paintComponent(gG);
            Graphics2D g=(Graphics2D)gG;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(background);
            
            //g.fillRect(0,0,this.getWidth(),this.getHeight());
            if(epicenter!=null){
            g.setPaint(new RadialGradientPaint(epicenter.x,epicenter.y,
                    radius+1,
                    new float[]{0.33f,0.9f},
                    new Color[]{background,new Color(background.getRed(),background.getGreen(),background.getBlue(),120)}));
            g.fillOval(epicenter.x-radius,epicenter.y-radius,2*radius,2*radius);
          }
            FontMetrics fm=g.getFontMetrics(g.getFont());
            int xl=(getWidth()-fm.stringWidth(text))/2;
            int yl=(getHeight()+fm.getHeight()/2)/2;
            g.setColor(foregroundColor);
            g.drawString(text,xl,yl);
        }
        
    }
    
    
}