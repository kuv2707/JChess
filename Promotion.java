import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
class Promotion extends JDialog implements MouseMotionListener,MouseListener
{
    JButton rani, hathi, ghora, mandir;
    String devuelve="";
    Point highlightloc;
    static int xl=-555,yl=-555;
    static Image[] res;
    static optionPanel pan;
    final String[] names=new String[]{goti.rani,goti.hathi,goti.ghora,goti.mandir};
    static int scale=gui.scalefactor;
    public Promotion(JFrame parent,Image[] g,Point location)
    {
        super(parent,"",true);
        res=g;
        for(int i=0;i<=3;i++)
            {
                res[i]=res[i].getScaledInstance(scale,scale,Image.SCALE_SMOOTH);
            }
        
        setUndecorated(true);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0,0,4*scale,scale-1,20,20));
        setLocation(new Point(location.x,location.y));
        setSize(4*scale,scale);
        setOpacity(0.90f);
        
        pan=this.new optionPanel();
        pan.addMouseListener(this);
        pan.addMouseMotionListener(this);
        new Thread(new refresh()).start();
        getContentPane().add(pan);
    }
    static class refresh implements Runnable
    {
        public void run()
        {
            new javax.swing.Timer(3,new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            pan.repaint();
                        }
                    });
                }
            }).start();
        }
    }
    public void mouseMoved(MouseEvent me)
    {
        xl=me.getX()/scale;
        yl=me.getY()/scale;
    }
    public void mouseDragged(MouseEvent me)
    {
        
    }
    public void mouseEntered(MouseEvent me)
    {
        
    }
    public void mouseExited(MouseEvent me)
    {
        xl=-555;
    }
    boolean prefl=false;
    public void mousePressed(MouseEvent me)
    {
        prefl=true;
    }
    public void mouseReleased(MouseEvent me)
    {
        prefl=false;
    }
    public void mouseClicked(MouseEvent me)
    {
        int loc=(int)(me.getX()/scale);
        if(loc>=0 &&  loc<names.length)
        devuelve=names[loc];
        else
        devuelve=goti.rani;
        utility.disappearDialog(this);
    }
    public String run()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                audio.play("choose");
            }
        }).start();
        utility.appearDialog(this);
        return devuelve;
    }
     class optionPanel extends JPanel
    {
        
        public void paint(Graphics G)
        {
            Graphics2D g=(Graphics2D)G;
            super.paintComponent(G);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Environment.currentloccol);
            g.fillRect(0,0,getWidth(),getHeight());
            if(prefl==true)
            {
                g.setColor(Environment.destacar);
            }
            
            else
            {
                g.setColor(new Color(colortrans.g/2,colortrans.b,colortrans.r));
                Point2D cent=new Point2D.Float(xl*scale+40,yl*scale+40);
                float[] dist={0.5f,1.0f};
                Color[] colors={new Color(colortrans.g/2,colortrans.b,colortrans.r),new Color(90,185,193,10)};
                //new Color(133, 193, 233,0)
                RadialGradientPaint p=new RadialGradientPaint(cent,60,dist,colors);
                g.setPaint(p);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
                
            }
            g.fill(new RoundRectangle2D.Double(xl*scale,yl*scale,scale,scale,65,65));
            for(int i=0;i<=3;i++)
            {
                g.drawImage(res[i],scale*i,0,null);
            }
        }
    }
}