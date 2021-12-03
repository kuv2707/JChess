import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
class InputTaker extends JDialog
{
    InputTaker y;
    flash cover;
    public InputTaker(String message,String enterWord,flash c)
    {
        super((JFrame)null,"",true);
        y=this;
        cover=c;
        setSize(600,150);
            setLocationRelativeTo(null);
            setUndecorated(true);
            setShape(new RoundRectangle2D.Double(0d,0d,getWidth()*1d,getHeight()*1d,60d,60d));
            utility.GradientPanel pan=new utility.GradientPanel();
            pan.setBackground(new Color(187, 143, 206));
            pan.setLayout(new GridLayout(2,1));
        
        JLabel a=new JLabel(message,JLabel.CENTER);
        a.setFont(CONSTANTS.windowfont);
        
        JTextField f=new JTextField();
        f.setFont(CONSTANTS.generalfont);
        
        
        utility.rgbButton b=new utility.rgbButton(enterWord);//tactile effect doesnt work
        b.setTactileColor(Color.cyan);
        
        utility.rgbButton can=new utility.rgbButton("Cancel");
        

        
        pan.add(a);
        pan.add(f);
        pan.add(b);
        pan.add(can);
        
        setContentPane(pan);
        can.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                
                closeAndReturn(null);
            }
        });
        b.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                closeAndReturn(f.getText());
            }
        });
        f.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                closeAndReturn(f.getText());
            }
        });
    }
    public void closeAndReturn(String s)
    {
        ret=s;
        utility.disappearDialog(y);
                    new Thread(new Runnable()
                    {
                        public void run()
                        {
                            cover.lighten(1);
                        }
                    }).start();
    }
    String ret="";
    public String run()
    {
        
            new Thread(new Runnable()
            {
                public void run()
                {
                    cover.darken(1);
                }
            }).start();
            utility.appearDialog(this);
            return ret;
    }
}