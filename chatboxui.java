import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.UIManager.*;
public class chatboxui extends JPanel
{
    Point bf;
    public  chatboxui(Point bornfrom)
    {
        super();
        bf=bornfrom;
        
        this.setLayout(new GridLayout(2,0));
        this.setLocation(0,440);
        this.setSize(565-80,640-440);
        this.setBorder(BorderFactory.createTitledBorder(("Chat with "+game.getOppositePlayer().getName()+"  ")));
        //this.setVisible(false);
        //jInternalFrame.setVisible(true);
        //jInternalFrame.setClosable(true);
        //this.setResizable(false);
        
        //jf.add(jInternalFrame);
        //jf.repaint();
        
         ATextArea tx=new ATextArea();
         tx.setBounds(0,0,2,2);
        //tx.setLineWrap(true);
        tx.setEditable(false);
        //tx.setWrapStyleWord(true);
        
        
         tf=new ATextField();
         tf.setBounds(0,250,getWidth(),getHeight()-tx.getHeight());
        Font font=new Font("Segoe UI Emoji",Font.PLAIN,22);
        tx.setFont(font);
        tf.setFont(font);
        
        JPanel input=new JPanel();
        input.setLayout(new GridLayout(2,0));
        
        
        JScrollPane fd=new JScrollPane(tx,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        fd.setBounds(0,0,500,250);
        //fs.add(fd);
        this.add(fd);
        input.add(tf);
        input.add(new JLabel("Options for chatting like buttons for instant emoji (reactions)"));
        this.add(input);
        
        tf.addKeyListener(new KeyAdapter()
         {
             public void keyReleased(KeyEvent ke)
             {
                 if(ke.getKeyCode()==KeyEvent.VK_ENTER)
                 {
                     String s=tf.getText();
                     tf.setText("");
                     tx.append("["+"You"+"] "+s+"\n");
                     //networkhr.chatout.println(s);
                     /**
                      * write the message to chatsocket
                      */
                 }
             }
         });
         
        //new Thread(new networkhr.chatinreader(tx)).start();
        
        
        
        
    }
    public JTextField tf;
    @Override
    public void setVisible(boolean b)
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                move(b);
            }
        }).start();
    }
    public void fastSetVisible(boolean b)
    {
        super.setVisible(b);
    }
    public void move(boolean b)
    {
        if(b)
        {
            double k=0;
            super.setVisible(b);
            while(k<=1)
            {
                this.setLocation((int)(600*(1-k)),440);
                k+=0.02;
                gui.frame.revalidate();
                try
                {
                    Thread.sleep(2);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
                
            }
            this.setLocation(0,440);
        }
        else
        {
            double k=1;
            
            while(k>=0)
            {
                this.setLocation((int)(600*(1-k)),440);
                k-=0.02;
                gui.frame.revalidate();
                try
                {
                    Thread.sleep(2);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
                
            }
            this.setLocation(600,440);
            super.setVisible(b);
        }
        
    }
    static class ATextArea extends JTextArea
    {
        
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(new Color(colortrans.r,colortrans.b,colortrans.g,80));
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }
    static class ATextField extends JTextField
    {
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(new Color(colortrans.g,colortrans.b,colortrans.r,80));
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }
}