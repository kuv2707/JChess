import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

import java.awt.*;
import java.io.*;

class AI extends Player
{
    PrintStream ps;
    BufferedReader br;
    Process p;
    Runnable Turn;
    static int aidiff=10;

    @Override
    public void playMove()
    {
        game.getOpponentOf(this).endMove();
        gui.gameEvents.execute(Turn);
    }
    public AI(Color c,int difficulty)
    {
        super(c,"");
        renamable=false;
        Turn=new Runnable()
        {
            public void run()
            {
                String fen=game.generateFEN(game.pisse);
                ps.println("position fen "+fen);
                ps.println("go "+time.getUCITime());
                ps.flush();
            
                String buf=null;
                try
                {
                    while((buf=br.readLine())!=null)
                    {
                        //Environment.log(buf);
                        if(buf.contains("bestmove"))
                        break;
                        
                    }
                }
                catch(Exception e)
                {
                
                }
                
                String p=buf.substring(buf.indexOf(' ')+1);
                char f1=p.charAt(0);
                int f2=Integer.parseInt(""+p.charAt(1))-1;
                char t1=p.charAt(2);
                int t2=Integer.parseInt(""+p.charAt(3))-1;
                Point fr=new Point((int)f1-97,f2);
                Point to=new Point((int)t1-97,t2);
            
                if( (to.y==7  ||  to.y==0) &&  (goti.gotiAt(fr) instanceof pawn))//
                {
                    char rev=p.charAt(4);
                    if(rev=='q')
                    revival=goti.rani;
                    if(rev=='k')
                    revival=goti.ghora;
                    if(rev=='r')
                    revival=goti.hathi;
                    if(rev=='b')
                    revival=goti.mandir;
                
                }
            
                actualAction(fr,to);
            }
            
        };
        try
        {
            
            p=new ProcessBuilder("D:\\chessAyush\\stockfishWin\\stockfish_14_x64_popcnt.exe").start();
            InputStream engI=p.getInputStream();
            OutputStream engO=p.getOutputStream();
            
            ps=new PrintStream(engO);
            br=new BufferedReader(new InputStreamReader(engI));
            ps.println("ucinewgame");
            ps.println("isready");
            ps.flush();
            br.readLine();
            String s=br.readLine();
            if(!s.equals("readyok"))
            {
                throw new IOException(s);
                 
            }
             
            ps.println("setoption name Skill Level value "+difficulty);
            setName("Stockfish "+difficulty);
             
        }
        catch (java.io.IOException e)
        {
            Environment.log("exception thrown in loading stockfish" +e.getMessage());
            
            //request game exit or whatever
            
        }
    }
    @Override
    public void destroy()
    {
        ps.println("quit");
        ps.flush();
        try
        {
            br.close();
            ps.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        
        p.destroy();
    }
    public static JPanel getEntryMenu(JFrame container)
    {
        JPanel local=new utility.GradientPanel();
        local.setOpaque(true);
            local.setLayout(null);
            Font font=new Font("",Font.PLAIN,28);
            Font font2=new Font("",Font.PLAIN,25);
            local.setBorder(BorderFactory.createTitledBorder("Stockfish engine will be used if supported, else a basic engine"));
            
            JLabel lab=new JLabel("Your team");
            lab.setBounds(10,15,150,40);
            lab.setFont(font);
            
            
            JRadioButton whi=new JRadioButton("White");
            whi.setFont(font2);
            whi.setBounds(160,15,100,40);
            whi.setFocusable(false);
            whi.setOpaque(false);
            JRadioButton bla=new JRadioButton("Black");
            bla.setFont(font2);
            bla.setBounds(290,15,140,40);
            bla.setFocusable(false);
            bla.setOpaque(false);
            
            JLabel n=new JLabel("Your name");
            n.setBounds(10,70,150,50);
            n.setFont(font);
            JTextField name=new JTextField("");
            name.setBounds(160,70,220,50);
            name.setFont(font2);
            
            JLabel dif=new JLabel("Difficulty: "+(int)aidiff);
            dif.setBounds(10,130,160+10,50);
            dif.setOpaque(false);
            dif.setFocusable(false);
            dif.setFont(font);
            
            JToggleButton grab=new JToggleButton("Record this game");
            grab.setBounds(10,200,150,30);
            grab.setOpaque(false);
            grab.setFocusable(false);
            
            JComboBox jcb=new JComboBox(TimeType.times);
            jcb.setBounds(190,200,120,30);
        
            
            JSlider difcho=new JSlider(JSlider.HORIZONTAL,0,20,10);
            difcho.setBounds(170+10,130,200,50);
            difcho.setMinorTickSpacing(1);
            difcho.setMajorTickSpacing(5);
            difcho.setPaintTicks(true);
            difcho.setFocusable(false);
            difcho.setPaintLabels(true);
            difcho.setOpaque(false);
            difcho.setFocusable(false);
             //change listener is in javax.swing.event not awt.event
            difcho.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent ce)
                {
                    aidiff=(int)difcho.getValue();
                    //System.out.println(game.aidiff);
                    dif.setText("Difficulty: "+(int)aidiff);
                    Environment.log(""+aidiff);
                }
            });
            
            utility.rgbButton proc=new utility.rgbButton("Start game");
            proc.setTactileColor(Color.green);
            proc.setForeground(Color.black);
            //proc.setForeground(Color.black.darker());
            proc.setBounds(2,250,container.getWidth()-5,119);
            proc.setFont(new Font("",Font.PLAIN,35));
            proc.setEnabled(false);
            proc.setFocusable(false);
            proc.setOpaque(false);   
            
            
            local.add(proc);
            local.add(name);
            local.add(n);
            local.add(bla);
            local.add(whi);
            local.add(lab);
            local.add(dif);
            local.add(jcb);
            local.add(difcho);           
            local.add(grab);
            MouseAdapter mouse=new MouseAdapter()
            {
                public void mouseClicked(MouseEvent e)
                {
                    if(e.getSource()==whi)
                    {
                        bla.getModel().setSelected(false);
                        
                    }
                    if(e.getSource()==bla)
                    {
                        whi.getModel().setSelected(false);
                        
                    }
                    if(bla.getModel().isSelected()==false  &&  whi.getModel().isSelected()==false)
                    {
                        proc.setEnabled(false);
                    }
                    else
                    {
                        proc.setEnabled(true);
                    }
                    if(e.getSource()==proc  &&  proc.isEnabled()==true)
                    {
                        Color kol=!whi.getModel().isSelected()?goti.colWhit:goti.colBlak;
                        String nom=name.getText();
                        if(nom.equals(""))
                        nom="Noobie";
                        Thread gt=new utility.fadeout(container,false,false);
                        gt.start();
                        entryPoint.disableAllOptions();
                        proc.setEnabled(false);
                        whi.setEnabled(false);
                        bla.setEnabled(false);
                        //create the AI player object with difficulty accepted
                        AI aiplayer=new AI(kol,aidiff);
                        Human hooman=new Human(Environment.getOppositeColor(kol),nom);
                        Environment.log(hooman.toString());
                        Environment.log(aiplayer.toString());
                        new game(new Player[]{hooman,aiplayer},new Object[]{0,grab.getModel().isSelected()});
                    }
                }
            };
            
            whi.addMouseListener(mouse);
            bla.addMouseListener(mouse);
            proc.addMouseListener(mouse);
            return local;
    }
    public JComponent getMouseEventCauser()
    {
        return this;
    }
    @Override
    public String getRevivalCandidate()
    {
        return revival;
    }
    public void endMove()
    {
        
    }
}