import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Human extends Player
{
    
    public Human(Color c, String s)
    {
        super(c,s);
    }
    public void destroy()
    {
                
    }
    @Override
    public String getRevivalCandidate()
    {
        {
            /**
             * all subclass override this method to return the variable revival
             * only human player will do the below thing
             *
             */
            Image goties[]=null;
            if(this.team.equals(goti.colWhit))
            {
                goties=new Image[]{game.whitQueen,game.whitRook,game.whitNait,game.whitBishop};
            }
            else
            {
                goties=new Image[]{game.blakQueen,game.blakRook,game.blakNait,game.blakBishop};
            }
            Promotion dialog=new Promotion(gui.frame,goties,MouseInfo.getPointerInfo().getLocation());//dialog appears where mouse pointer is
            
            
            return dialog.run();
        }
    }
    
        
    
    public JComponent getMouseEventCauser()
    {
        return game.getChessBoard().pan;
    }
    public static JPanel getEntryMenu(JFrame container)
    {
        JPanel local=new utility.GradientPanel();
        local.setOpaque(true);
            local.setLayout(null);
            Font font=new Font("",Font.PLAIN,22);
            Font font2=new Font("",Font.PLAIN,18);
            local.setBorder(BorderFactory.createTitledBorder("Two player"));
           
           
            
            JLabel lab=new JLabel("Name of the white team player");
            lab.setBounds(10,10,500,40);
            lab.setFont(font);
            
            JTextField whi=new JTextField("");
            whi.setFont(font2);
            whi.setBounds(10,46,250,40);
            
            
            
            
            
            JLabel n=new JLabel("Name of the black team player");
            n.setBounds(10,90,500,50);
            n.setFont(font);
            
            JTextField bla=new JTextField("");
            bla.setBounds(10,158-22,250,40);
            bla.setFont(font2);
            
            
            
            utility.rgbButton proc=new utility.rgbButton("Start game");
            proc.setForeground(Color.black.darker());
            proc.setBounds(2,250,container.getWidth()-5,119);
            proc.setFont(new Font("",Font.PLAIN,35));
            
            proc.setFocusable(false);
            proc.setOpaque(false);            
            
            JToggleButton grab=new JToggleButton("Record this game");
            grab.setBounds(10,200,150,30);
            grab.setOpaque(false);
            grab.setFocusable(false);
            
            JComboBox<String> jcb=new JComboBox<String>(TimeType.times);
            jcb.setBounds(190,200,120,30);
            
            local.add(grab);
            local.add(proc);
            local.add(jcb);
            local.add(n);
            local.add(bla);
            local.add(whi);
            local.add(lab);
            
           
            
            MouseAdapter mouse=new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    
                    
                    
                    
                        String w=whi.getText();
                        if(w.equals(""))
                        w="player1";
                        String b=bla.getText();
                        if(b.equals(""))
                        b="player2";
                        Thread gt=new utility.fadeout(container,false,false);
                        gt.start();
                       entryPoint.disableAllOptions();
                        proc.setEnabled(false);
                        
                        
                        Human white=new Human(goti.colWhit,w);
                        
                        Human black=new Human(goti.colBlak,b);
                        
                        
                        new game(new Player[]{white,black},new Object[]{0,grab.getModel().isSelected()});
                        
                }
            };
             
            proc.addMouseListener(mouse);
            return local;
    }
    @Override
    public void playMove() {
        
        
    }
    @Override
    public void endMove() {
        
        
    }
    
}