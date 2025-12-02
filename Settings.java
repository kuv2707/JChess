import java.awt.*;
import javax.swing.*; 
import java.awt.geom.*;
import java.awt.event.*;
class Settings extends JPanel
    {
        JFrame fra;
        public Settings()
        {
            super();
            setSize(300,200);
            fra=new JFrame();
            fra.setSize(getWidth(),getHeight());
            fra.setUndecorated(true);
            fra.setType(JFrame.Type.UTILITY);
            fra.setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),30,30));
            fra.setLocationRelativeTo(gui.frame);
            fra.setIconImage(game.icon);
            fra.getContentPane().setLayout(null);
            String[] set=new String[]{"Fantasy ","Fantasy 2 ","Katz ","Eyes ","Classic ","Freak ","Skull "};
            JComboBox<String> change=new JComboBox<>(set);
            change.setOpaque(false);
            change.setToolTipText("Change the appearance of the pieces");
            change.setSelectedItem(game.folder);
            change.setBounds(10,50,150,40);
            change.setFont(new Font("LEIPFONT",Font.PLAIN,22));
            change.setFocusable(false);
            change.addItemListener(new ItemListener()
            {
                public void itemStateChanged(ItemEvent ee)
                {
                    game.folder="/"+change.getSelectedItem().toString().trim();
                    
                    //  if(change.getSelectedItem().toString().equals(set[4]))
                    //  {
                    //      Shockwave.white=Shockwave.white2;
                    //      Shockwave.black=Shockwave.black2;
                    //  }
                    //  else
                    //  {
                    //      Shockwave.white=Shockwave.white1;
                    //      Shockwave.black=Shockwave.black1;
                    //  }
                         new Thread(new Runnable()
                         {
                             public void run()
                             {
                                 try
                                 {
                                     game.loadimages(game.folder);
                                     
                                     for(float scal=1;scal>=0;scal-=0.01)
                                     {
                                        for(int i=0;i<game.pisse.size();i++)
                                        {
                                            game.pisse.get(i).getRendering().scaling=scal;
                                            
                                        }
                                        game.getChessBoard().refresh();
                                        Thread.sleep(10);
                                     }
                                     for(int i=0;i<game.pisse.size();i++)
                                     {
                                         game.pisse.get(i).setFace();  
                                     }
                                     for(float scal=0;scal<=1;scal+=0.01)
                                     {
                                        for(int i=0;i<game.pisse.size();i++)
                                        {
                                            game.pisse.get(i).getRendering().scaling=scal;
                                            
                                        }
                                        game.getChessBoard().refresh();
                                        Thread.sleep(10);
                                     }
                                 }
                                 catch (Exception e)
                                 {
                                     e.printStackTrace();
                                 }
                             }
                         }).start();
                         
                    
                     
                }
            });
            
            JLabel lab=new JLabel("  Theme  ");
            lab.setBounds(10,10,160,30);
            lab.setFont(CONSTANTS.generalfont);
            add(lab);
            add(change);
            fra.add(this);
            JToggleButton jtb=new JToggleButton(" Dark mode: Enabled ");
            jtb.setSelected(true);
                    jtb.setFocusable(false);
                    jtb.setBounds(10,100,180,60);
                    add(jtb);
                    jtb.setFont(CONSTANTS.generalfont);
                    
            jtb.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    Environment.setDarkMode(jtb.getModel().isSelected());
                    if(jtb.getModel().isSelected())
                    {
                        jtb.setText(" Dark mode:Enabled ");
                    }
                    else
                    {
                        jtb.setText(" Dark mode: Disabled ");
                    }
                }
            });
            addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent me)
                {
                    offset=new Point(me.getX(),me.getY());
                }
                public void mouseReleased(MouseEvent me)
                {
                    offset=null;
                }
            });
            addMouseMotionListener(new MouseAdapter()
            {
                public void mouseDragged(MouseEvent me)
                {
                    fra.setLocation(fra.getLocation().x+me.getX()-offset.x,fra.getLocation().y+me.getY()-offset.y);
                }
            });
            JLabel warm=new JLabel("Temperature",JLabel.CENTER);
            warm.setFont(CONSTANTS.generalfont);
            add(warm);
            add(gui.night);
            JButton hide=new JButton("Hide");//my custom button doesnt work here!!
            hide.setFont(CONSTANTS.generalfont);
            add(hide);
            hide.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent me)
                {
                    switchState();
                    gui.bt.getModel().setSelected(!gui.bt.getModel().isSelected());
                }
            });
        }
        Point offset;
        public void switchState()
        {
            if(fra.isVisible())
            {
                //fadeout
                utility.disappearJFrame(fra);
            }
            else
            {
                //fadein
                utility.appearJFrame(fra);
            }
            
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Environment.controlBackground);
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }