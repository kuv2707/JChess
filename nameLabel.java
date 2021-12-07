 import java.awt.*;
 import javax.swing.*;
 import java.awt.event.*;
 import java.awt.geom.*;
class nameLabel extends JLabel
    {
        //Color key=null;
        volatile double[] key=new double[]{colortrans.r/4,colortrans.g,colortrans.b,70};
        Color forgnd=Color.black;
        int round=60;
        boolean highlighted=false;
        Player player;
        String teamIdentify;
        public nameLabel(String s,int i,Player play)
        {
            super(s,i);
            setOpaque(false);
            
            player=play;
            Environment.log("associated to player "+player.toString());
            if(player.team.equals(goti.colBlak))
            {
                teamIdentify="♚";
                
            }
            else
            {
                teamIdentify="♔";
                
            }

            setForeground(new Color(0,0,0,0));
            
            nameLabel este=this;
            //online players are not renamable 
            //dont forget to change when players are able to change type mid-game, like from manual to AI: at that time the renamable attribute will change
            
                addMouseListener(new MouseAdapter()
                {
                    public void mouseClicked(MouseEvent e)
                    {
                        if(SwingUtilities.isLeftMouseButton(e))
                        {
                            Environment.log(player.toString());
                            updateName();
                        }
                        if(!game.isGameActive()  ||  player.hasCheck  )
                        return;
                        if( !player.renamable)
                        {
                            removeMouseListener(this);
                            return;
                        }
                        if(SwingUtilities.isRightMouseButton(e))
                        {
                            JPopupMenu p=new JPopupMenu();
                            JMenuItem cnam=new JMenuItem("Rename");
                            p.add(cnam);
                            p.show(este,e.getX(),e.getY());
                            
                            cnam.addActionListener(new ActionListener()
                            {
                                public void actionPerformed(ActionEvent e)
                                {
                                    InputTaker it=new InputTaker("New name","Change",gui.cover);
                                    String newname=it.run();
                                    if(newname==null)
                                    return;
                                    if(newname.equals(""))
                                    return;
                                    Player p=game.getOpponentOf(player);
                                    if(p.getName().equals(newname))
                                    {
                                        //both players obviously cannot get the same name
                                        newname+="2";
                                    }
                                    player.setName(newname);
                                }
                            });
                        }
                    }
                });
            
                
        }
        public void refresh()
        {
            repaint();
        }
        @Override
        public JToolTip createToolTip()
        {
            return new JToolTip();
        }
        static class CoolToolTip extends JToolTip
        {
            public CoolToolTip(JComponent co)
            {
                super();
                setComponent(co);
                setBackground(Color.black);
                setForeground(Color.white);
            }
        }
        
        public void highlightName()
        {
            
            
            highlighted=true;
            new Thread(new Runnable()
            {
                public void run()
                {
                    float k=0;
                    while(k<1)
                    {
                        forgnd=new Color((int)(Environment.activePlayer.getRed()*k), (int)(Environment.activePlayer.getGreen()*k), (int)(Environment.activePlayer.getBlue()*k));
                        k+=0.02;
                        refresh();
                        try
                        {
                            Thread.sleep(2);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                    }
                }
            }).start();
            this.player.time.start();
        }
        public void deHighlightName()
        {
            
            if(!highlighted)
            return;
            highlighted=false;
            new Thread(new Runnable()
            {
                public void run()
                {
                    float k=1;
                    while(k>0)
                    {
                        forgnd=new Color((int)(Environment.activePlayer.getRed()*k), (int)(Environment.activePlayer.getGreen()*k), (int)(Environment.activePlayer.getBlue()*k));
                        k-=0.02;
                        refresh();
                        try
                        {
                            Thread.sleep(2);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                    }
                }
            }).start();
            this.player.time.pause();
        }
        int animOffset,textSize=35;
        Font paintFont=new Font("LEIPFONT",Font.BOLD,textSize);
        public void paintComponent(Graphics gg)
        {
            super.paintComponent(gg);
            Graphics2D g=(Graphics2D)gg;
            
            //setBackground(new Color(colortrans.b,colortrans.b,colortrans.g,70));
            g.setColor(new Color((int)key[0],(int)key[1],(int)key[2],(int)key[3]));
            g.fill(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),round,round));
            
            
            paintFont=new Font("LEIPFONT",Font.BOLD,textSize);
            FontMetrics fm=getFontMetrics(paintFont);
            g.setFont(paintFont);
            
            int j=fm.stringWidth(getText());
            int i=fm.getHeight();
            
            //g.setColor(forgnd);
            g.setPaint(new LinearGradientPaint(0,0,getWidth(),getHeight(),
            new float[]{0.35f,0.6f},
            new Color[]{forgnd,player.team}));
            g.drawString(getText(),animOffset+(getWidth()/2)-(j/2),(getHeight()/2)+(i/4)+3);
            
            
            Font f=new Font("LEIPFONT",Font.BOLD,15);
            fm=getFontMetrics(f);
            g.setFont(f);
            String sss=this.player.time.getTime();
            int k=fm.stringWidth(sss);
            g.drawString(sss,(getWidth()/2)-(k/2),(getHeight()/2)+(i/4)+3+21);
        }
        public void updateName()
        {
            /**
             * the existing name will become small and eventually disappear
             * then the new name will become larger and take its place
             */
            new Thread(new Runnable()
            {
                public void run()
                {
                    
                    while(textSize>=0)
                    {
                        textSize--;
                        refresh();
                        try
                        {
                            Thread.sleep(8);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                        
                    }
                   
                    setText(teamIdentify+player.getName());
                    
                    while(true)
                    {
                        textSize++;
                        refresh();
                        FontMetrics fm=getFontMetrics(paintFont);
                        int w=fm.stringWidth(getText());
                        if(w>getWidth()-30  ||  textSize>getHeight()/2)
                        break;
                        try
                        {
                            Thread.sleep(8);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                        
                    }
                    
                }
            }).start();
            
        }
        
        public void dangerOscillate(int con)//con is the turn number in which it has to oscillate 
        {
            /**
             * a call to this method means the player has got a check
             */
            player.hasCheck=true;
            double c=-0.5*Math.PI,forgr,forgg,forgb,forga=0;
            //sub.setOpaque(true);
            while(game.turnNumber<=con)
            {
                
                forgr=219;
                    forgg=77;
                    forgb=64;
                    forga=120+(100*Math.sin(c));
                try
                {
                        
                            key=new double[]{forgr,forgg,forgb,forga};
                       
                    Thread.sleep(6);
                }
                catch (Exception ie)
                {
                    ie.printStackTrace();
                }
                
                c+=0.03;
                refresh();
            }
            player.hasCheck=false;
            while(forga>70)
            {
                forga-=2;
                try
                {
                        
                            key[3]=forga;
                       refresh();
                    Thread.sleep(15);
                }
                catch (Exception ie)
                {
                    ie.printStackTrace();
                }
            }
            forgr=colortrans.r/4;
            forgg=colortrans.g;
            forgb=colortrans.b;
            forga=70;
            key=new double[]{forgr,forgg,forgb,forga};
            
        }
    }