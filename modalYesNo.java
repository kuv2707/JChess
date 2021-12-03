 import java.awt.*;
 import javax.swing.*;
 import java.awt.geom.*;
 import java.awt.event.*;
 import java.util.*;
class modalYesNo extends JDialog
{
    public static int YES=1,NO=0;
    int dev;
    
    modalYesNo y;
    flash cover;
    public modalYesNo(String jlab,String yestxt,String notxt,flash c)
    {
        super((JFrame)null,"",true);
        cover=c;
        y=this;
        
        setSize(600,150);
            setLocationRelativeTo(null);
            setUndecorated(true);
            setShape(new RoundRectangle2D.Double(0d,0d,getWidth()*1d,getHeight()*1d,60d,60d));
            utility.GradientPanel pan=new utility.GradientPanel();
            pan.setBackground(new Color(187, 143, 206));
            pan.setLayout(null);
            
            Font font=new Font("",Font.BOLD,24);
            
            JLabel me=new JLabel(jlab,JLabel.CENTER);
            me.setBounds(0,5,getWidth(),60);
            me.setFocusable(false);
            me.setFont(new Font("",Font.PLAIN,31));
            
            
            utility.rgbButton yes=new utility.rgbButton(notxt);
            yes.setBounds(20,70,250,60);
            yes.setFocusable(false);
            yes.setFont(font);
            yes.setForeground(Color.red);
           
            //yes.setOpaque(false);
            /**
             * why is ripple effect not observed in these buttons!!!!!!!
             */
            utility.rgbButton no=new utility.rgbButton(yestxt);
            no.setBounds(getWidth()-20-250,70,250,60);
            no.setFocusable(false);
            no.setFont(font);
            no.setForeground(Color.green.darker());
            
           // no.setOpaque(false);
            
            pan.add(yes);
            pan.add(no);
            pan.add(me);
            setContentPane(pan);
            
            MouseAdapter l=new MouseAdapter()
            {
                public void mouseClicked(MouseEvent ae)
                {
                    if(ae.getSource()==yes)
                    {
                        dev=YES;
                    }
                    else
                    {
                        dev=NO;
                    }
                    utility.disappearDialog(y);
                    new Thread(new Runnable()
                    {
                        public void run()
                        {
                            cover.lighten(1);
                        }
                    }).start();
                }
            };
            
            yes.addMouseListener(l);
            no.addMouseListener(l);
            
    }
        public int run()
        {
            
            new Thread(new Runnable()
            {
                public void run()
                {
                    cover.darken(1);
                }
            }).start();
            utility.appearDialog(this);//it is done on a separate thread
            
            return dev;
        }
    }