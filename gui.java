import javax.swing.*;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.util.concurrent.*;
import java.io.*;
import javax.swing.UIManager.*;

class gui implements MouseListener,MouseMotionListener 
{
    static JFrame frame;
    static JPanel pan,controlhost;
    static Settings config;
    static int natDragX,natDragY;
    static final int scalefactor=80;
    
    
    static MouseListener gameActions;
    static goti chaal;
    static PossibLox<Point,Color> highlight;
    static ExecutorService pool,seq,shockwavequeue,transition,smoooth;
    
    
    
    static final Color matar=new Color(231, 76, 60,240);
    
    static final Color selectcol=new Color(133, 193, 233,255);
    static final Color castlecol=new Color(248, 196, 113,200);
    static final Color goodcol=new Color(213, 216, 231);    
    
    
    static volatile Point selcol;
    static Point frloc;
    static Point loc=null;
    
    
    
    static goti lastgoti;//needs to be utilized for move history chart
    static gui este;
    
    
    static JLabel checkstat;
    static nameLabel thereturn,hereturn;
    static utility.rgbButton close, drag, minimize;
    static eyeProtector night;
    static JComponent hold;
    static chatboxui chatbox;
    static statusLabel sla;
    static JButton settings;
    static JToggleButton chat;
    static flash cover;
    static String lastmovealgebraic="";
    static JToggleButton bt;
    
    
    public void initGuiLoc()
    {
        for(int i=0;i<game.pisse.size();i++)
            {
                goti tem=game.pisse.get(i);
                
                tem.associate(new rendering(tem,new Point(660,660)));
            }
    }
    public void makeFrame()
    {
        
        try 
        {
             GraphicsEnvironment ge = 
             GraphicsEnvironment.getLocalGraphicsEnvironment();
             ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("LEIPFONT.ttf")));
            } 
            catch (IOException|FontFormatException e) {
                 //Handle exception
                 Environment.log("font failed "+e);
                 
            }
        
        
        
         

        //gui fields
        
        este=this;
        frame=new JFrame("Chess");
        frame.setSize(1280,718);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0,0,1280,718,25,25));
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        
        
         
        boardPicture boardPic=new boardPicture();
        boardPic.setBounds(0,0,frame.getWidth(),frame.getHeight());
        
        pan=new Ayush();
        pan.setLayout(null);
         
        pan.setBounds(   40,40 ,8*scalefactor,8*scalefactor);
        //pan.setBorder(BorderFactory.createTitledBorder("extend"));
        pan.setOpaque(false);
        
        hold=new JLayeredPane();
         hold.setLayout(null);
        hold.setOpaque(true);
        hold.setBounds(0,0,frame.getWidth(),frame.getHeight());
        hold.setIgnoreRepaint(true);
        
        
         controlhost=new Controls();
        controlhost.setBounds((((64-25)*scalefactor)/80 )+8*scalefactor+40,0,565,hold.getHeight());
        controlhost.setLayout(null);
        
        
        
         close=new utility.rgbButton("Close ");
        close.setForeground(Color.red);
        
        close.setOpaque(false);
        close.setFocusable(false);
        close.setFont(CONSTANTS.windowfont);
        close.setBounds(186+186,0,190,120);
        close.setTactileColor(new Color(240,23,45,220));
        
         minimize=new utility.rgbButton("Minimize ");
        minimize.setForeground(new Color(190, 114, 237));
        minimize.setTactileColor(new Color(190, 114, 237));
        minimize.setBounds(186,0,186,120);
        minimize.setOpaque(false);
        minimize.setFocusable(false);
        minimize.setFont(CONSTANTS.windowfont);
        
        drag=new utility.rgbButton("Drag");
        drag.setForeground(new Color(41, 128, 185));
        
        drag.setBounds(0,0,186,120);
        drag.setOpaque(false);
        drag.setFocusable(false);
        drag.setFont(CONSTANTS.windowfont);
        
        checkstat=new JLabel();
        checkstat.setHorizontalAlignment(JLabel.CENTER);
        checkstat.setFont(CONSTANTS.windowfont);
        checkstat.setForeground(new Color(231, 76, 60));
        checkstat.setBounds(0,380,controlhost.getWidth(),50);
        
        controlhost.add(checkstat);
        
        sla=new statusLabel();
        sla.setFont(new Font("LEIPFONT",Font.PLAIN,25));
        sla.setBounds(0,120,565,30);
        
        
        
        thereturn=game.getOppositePlayer().getGUIRepresentation();
        thereturn.setBounds(290,160,250,80);
        
        
        hereturn=game.getHerePlayer().getGUIRepresentation();
        hereturn.setBounds(20,160,250,80);
        
        
        
        
        
        
        
        JButton scrsh=new JButton();
        scrsh.setToolTipText("Take a screenshot of the entire screen");
        scrsh.setIcon(new ImageIcon(game.cameraicon));
        scrsh.setFocusable(false);
        scrsh.setOpaque(false);
        scrsh.setFont(new Font("LEIPFONT",Font.PLAIN,45));
        scrsh.setBounds(0,260,100,70);
        
        

        
        
        
        bt=new JToggleButton("Settings");
        bt.setFont(new Font("LEIPFONT",Font.PLAIN,22));
        bt.setFocusable(false);
        bt.setBounds(110,260+30,150,40);
        night=new eyeProtector();//used inside the Settings class constructor so needs to be instantiated before it
        config=new Settings();
        bt.addActionListener(e->
        {
            config.switchState();
        });
        
        
        if(game.nowturnof instanceof SavedGameReader)
        {
            JButton b=new JButton("  Pause  ");
            b.setFont(CONSTANTS.windowfont);
            b.setBounds(260,260,250,70);
            controlhost.add(b);
            game.onGameEnd(new Task()
            {
                public void perform()
                {
                    controlhost.remove(b);
                }
                
            });
            b.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent me)
                {
                    SavedGameReader.FILEREADpaused=!SavedGameReader.FILEREADpaused;
                    if(SavedGameReader.FILEREADpaused)
                    b.setText("  Resume  ");
                    else
                    b.setText("  Pause  ");
                }
            });
        }
        
        MouseAdapter windowbuttons=new MouseAdapter()
        {
            public void mousePressed(MouseEvent me)
            {
                if(me.getSource()==drag)
                {
                    loc=new Point(me.getX(),me.getY());
                    
                }
            }
            public void mouseClicked(MouseEvent me)
            {
                if(me.getSource()==scrsh)
                {
                    takescreenshot();
                    new Thread(new Runnable()
                    {
                        public void run()
                        {
                            gui.cover.screenshot();
                        }
                    }).start();
                            
                    

                }
                if(me.getSource()==drag)
                {
                    //dragmouseroutine(me);//hate it!
                    //seq.execute(new MessageAnimator(MessageAnimator.starts,true));
                }
                if(me.getSource()==close)
                {
                    try{entryPoint.close(game.isGameActive());}//custom dialog
                    catch(Exception e){}
                }
                
            }
            
            public void mouseDragged(MouseEvent me)
            {
                if(me.getSource()==drag)
                {
                    double x=frame.getLocation().getX()+me.getX()-loc.getX();
                    double y=frame.getLocation().getY()+(me.getY()-loc.getY());
                    frame.setLocation((int)x,(int)y);
                    game.getChessBoard().refresh();
                }
            }
            public void mouseEntered(MouseEvent me)
            {
                selcol=null;
                
                
                if(me.getSource()==close)
                {
                    close.setBackground(Color.red);
                }
                
                if(me.getSource()==minimize)
                {
                    minimize.setBackground(Color.black);
                }
                if(me.getSource()==drag)
                {
                    drag.setBackground(Color.cyan.darker());
                }
            }
            public void mouseExited(MouseEvent me)
            {
                selcol=null;
                if(me.getSource()==close)
                {
                    close.setBackground(new JButton().getBackground());
                }
                
                if(me.getSource()==minimize)
                {
                    minimize.setBackground(new JButton().getBackground());
                }
                if(me.getSource()==drag)
                {
                    drag.setBackground(new JButton().getBackground());
                }
            }
        };
        
        minimize.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                    Point start=frame.getLocation();
                    Point end=new Point(start.x,740);//screen  height get
                    for(double k=0;k<100;k+=0.5)
                    {
                        double x=(end.x*k+start.x)/(k+1);
                        double y=(end.y*k+start.y)/(k+1);
                        frame.setLocation(new Point((int)x,(int)y));
                        try
                        {
                            Thread.sleep(5);
                        }
                        catch (InterruptedException ie)
                        {
                            ie.printStackTrace();
                        }
                    }
                    frame.setState(JFrame.ICONIFIED);
                    
                
            }
        });
        close.addMouseListener(windowbuttons);
        minimize.addMouseListener(windowbuttons);
        drag.addMouseListener(windowbuttons);
            
        drag.addMouseMotionListener(windowbuttons);
        scrsh.addMouseListener(windowbuttons);
        controlhost.add(drag);
        controlhost.add(minimize);
        controlhost.add(close);
        controlhost.add(scrsh);
        controlhost.add(thereturn);
        controlhost.add(hereturn);
        controlhost.add(bt);
        controlhost.add(sla);
        if(game.getHerePlayer() instanceof Online  &&  false)//under construction
        {
            try
            {
                chatbox=new chatboxui(chat.getLocation());
                chatbox.fastSetVisible(false);
                //if(game.mode==game.mode_online)
                chat=new JToggleButton("  Show chat box   ");
                chat.setBounds(260,260,250,70);
                chat.setFocusable(false);
                chat.setFont(new Font("LEIPFONT",Font.PLAIN,22));
                chat.addMouseListener(new MouseAdapter()
                 {
                     public void mouseClicked(MouseEvent m)
                     {
                         if(chat.getModel().isSelected())
                         {
                             chatbox.setVisible(true);
                             chatbox.tf.requestFocus();
                             
                             chat.setText("   Hide chat box   ");
                         }
                         else
                         {
                             chatbox.setVisible(false);
                             chat.setText("   Show chat box   ");
                         }
                         chat.setForeground(Color.black);
                     }
                 });
                controlhost.add(chatbox);
                controlhost.add(chat);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }

        
        

        hold.add(boardPic,(Integer)1);
        hold.add(pan,(Integer)5);
        hold.add(controlhost,(Integer)2);
        
        
        cover=new flash();
        hold.add(cover,8);
        
        frame.add(hold);
        frame.setIconImage(game.icon);
        
        
        
        
        KeyListener exit=new KeyAdapter()
        {
            public void keyPressed(KeyEvent ke)
            {
                if(ke.getKeyCode()==KeyEvent.VK_ESCAPE)
                {
                    try
                    {
                        entryPoint.close(game.isGameActive());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                
                
            }
        };
        
             
        
        
        controlhost.addKeyListener(exit);
        pan.addKeyListener(exit);//maybe substitute with key bindings
        
        pan.setFocusable(true);
        //frame.setOpacity(0);
        frame.setVisible(true);
        
        
    }
    static ArrayList<Player> dontShowPossLocFor=new ArrayList();
    public void initThreads()
    {
        
        //actual game things
        pool=Executors.newFixedThreadPool(1);
        transition=Executors.newFixedThreadPool(1);
        seq=Executors.newFixedThreadPool(1);
        shockwavequeue=Executors.newFixedThreadPool(1);
        smoooth=Executors.newFixedThreadPool(1);
        seq.execute(new utility.fadein(frame));
        seq.execute(new Runnable()
        {
            public void run()
            {
                audio.play("start");
                for(int i=0;i<game.pisse.size();i++)
                {
                    game.pisse.get(i).getRendering().transit(gui.guidelegate(game.pisse.get(i).getLocation(),true),360);
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
        });
                
           
        if(!(game.getHerePlayer() instanceof SavedGameReader))
        {
            dontShowPossLocFor.add(game.getOppositePlayer());
        }
        
        
        
        
        seq.execute(new MessageAnimator(MessageAnimator.starts,true));
        
        
         //urge the online players to accept move inputs from sockets and play
           
        
        seq.execute(new addmou());
        gameActions=este;
        deadManager.initlox();
        
        Environment.setDarkMode(true);
    }
    static class addmou implements Runnable
    {
        public void run()
        {
            addmouselisteners();
        }
    }
    public static void addmouselisteners()
    {
        pan.addMouseListener(gameActions);
            pan.addMouseMotionListener((MouseMotionListener)gameActions);
            
            //gui.updatelabels();
    }
    public static void removemouselisteners()
    {
        pan.removeMouseListener(gameActions);
            pan.removeMouseMotionListener((MouseMotionListener)gameActions);
            //gui.updatelabels();
    }
    static Point prev=null;
    
    static class Smooth implements Runnable
    {
        Point start,end;
        static boolean inprogress=false;
        protected Smooth(Point s,Point e)
        {
            start=s;
            end=e;
            if(start==null)
            start=end;
        }
        public void run()
        {
            float k=0;
            inprogress=true;
            while(k<=1)
            {
                selcol=new Point( (int)(k*(end.x-start.x)+start.x),(int)(k*(end.y-start.y)+start.y));
                //selcol=new Point(  (int)((k*end.x+start.x)/(k+1)),(int)((k*end.y+start.y)/(k+1)));
                k+=0.025;
                gui.hold.repaint();
                try
                {
                    Thread.sleep(1);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            }
            selcol=end;
            inprogress=false;
        }
    }
    public void mouseMoved(MouseEvent me)
    {
        /**
         * if the current player doesnt require mouse
         * then source must be a JButton 
         * else it is the user trying to cheat
         */
        if(me.getSource() !=game.nowturnof.getMouseEventCauser())
        return;
                if(me.getX()>660)
                return;
        if(!game.isGameActive())
        {
            selcol=null;
            return;
        }
        goti g=goti.gotiAt(gui.guidelegate(new Point(me.getX(),me.getY()),false));
        if(g!=null)
        {
            if(g.teamCol.equals(game.nowturnof.getColor()))
            {
                Point p=gui.guidelegate(g.getLocation(),true);
                if(!p.equals(selcol))
                {
                    if(Smooth.inprogress==false)
                    smoooth.execute(new Smooth(selcol,p));
                    
                }
            }
            else
            {
                selcol=null;
            }
        }
        else
        {
            selcol=null;
        }
    }
    public void mouseClicked(MouseEvent me)
    {
        
    }
    public void mouseExited(MouseEvent me)
    {
        selcol=null;
        }
    public void mouseEntered(MouseEvent me)
    {
        
    }
    public void mouseDragged(MouseEvent me)
    {
        /**
         * if the current player doesnt require mouse
         * then source must be a JButton 
         * else it is the user trying to cheat
         */
        //CHECK IF THE CURRENT PLAYER EVEN REQUIRES MOUSE OR NOT
        
        if(chaal==null)
                return;
        if(me.getSource() !=game.nowturnof.getMouseEventCauser())
        return;  
                if(chaal.teamCol.equals(game.nowturnof.getColor())==false  )
                    {
                        chaal.guiloc=chaalPrevLoc;
                        chaal=null;
                        return;
                        
                    }
                int x=me.getX();
                int y=me.getY();
                //mousedragged(new Point((8*scalefactor)-x,(8*scalefactor)-y),(JComponent)me.getSource());
                
                chaal.guiloc.x=x-natDragX;
                chaal.guiloc.y=y-natDragY;

                hold.repaint();
    }
            Point chaalPrevLoc;
            static Point snapshotstart=null;
    public void mousePressed(MouseEvent me)
    {
        /**
         * if the current player doesnt require mouse
         * then source must be a JButton 
         * else it is the user trying to cheat
         */
        if(me.getSource() !=game.nowturnof.getMouseEventCauser())
        return;
                
               int x=me.getX();
                int y=me.getY();
                if(!isInBounds(x,y))
                return;
                    natDragX=((x)%scalefactor);
                    natDragY=((y)%scalefactor);
                  
                    
                    //int xInEngine=0,yInEngine=0;                    
                
                   Point onBoard=gui.guidelegate(new Point(x,y),false);
                
                    
                    chaal=goti.gotiAt(onBoard);
                    
                    if(chaal==null)
                    return;
                    chaalPrevLoc=chaal.guiloc;
                    if(chaal.teamCol.equals(game.nowturnof.getColor())==false  )
                    {
                        chaal=null;
                        return;
                        
                    }
                    
                    if(chaal.statOfAct==false)
                    {
                        chaal=null;
                        return;
                    }
                    
                    snapshotstart=new Point( x -natDragX , y -natDragY );
                    chaal.getRendering().selectedByUser();
                     highlight=chaal.whatLocationsPossible();
                     highlight.show();
                        // selectcol=new Color(255, 232, 159);
                      
                         highlight.put(chaal.getLocation(),Environment.currentloccol);
                //mousepressed(new Point((scalefactor*8)-x,(scalefactor*8)-y),(JComponent)me.getSource());
                pan.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    
                
    }
    public void mouseReleased(MouseEvent me)
    {
        /**
         * if the current player doesnt require mouse
         * then source must be a JButton 
         * else it is the user trying to cheat
         */
        if(me.getSource() !=game.nowturnof.getMouseEventCauser())
        return;
        
        
              
                try
                {
                if(chaal.teamCol.equals(game.nowturnof.getColor())==false  )
                    {
                        chaal=null;
                        return;
                        
                    }
                }
                catch(Exception e)
                {
                    //faltu reason ka exception h ye
                    //find a way to avoid cheating by quickly pressing mouse again to play in even opponent's turn in offline manual match
                }
                int x=me.getX();
                int y=me.getY();
                   
                
                   
                
                
                if(x<=0 ||  x>=pan.getWidth()  ||  y<=0  ||  y>=pan.getHeight())
                {
                    x=5678;
                    y=-2345;
                }
                Point land=gui.guidelegate(new Point(x,y),false);
                pan.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               selcol=null;
                    chaalPrevLoc=null;
             String send="";   
            if(chaal!=null )
            {
                 chaal.getRendering().releasedByUser();

                     highlight.remove(chaal.getLocation());
                   
                   stepstaken did= chaal.movedTo(land);
                   goti k;
                   if( (k=did.whomKilled())!=null)
                   {
                        shockwavequeue.execute(new shockwave(x,y));
                        audio.play("capture");
                        k.getRendering().transit(Controls.offset(deadManager.getFreeLocation(k)),180);
                        
                    }
                    
                    if(did.validTurn())
                    {
                        if(did.whomKilled()==null)
                        audio.play("put");
                        if(k!=null)
                        {
                            lastt=new Color(241, 148, 138,240);
                        }
                        else
                        {
                            lastt=new Color(243, 156, 18,220);
                        }
                         chaal.getRendering().transit(guidelegate(chaal.getLocation(),true),0);
                         new daemonOrderKeeper(chaal).start();
                        gui.lastfrom=gui.guidelegate(new Point(did.getPrevBoardLoc().x,did.getPrevBoardLoc().y),true);
                        gui.lastto=gui.guidelegate(new Point(chaal.getLocation().x,chaal.getLocation().y),true);
                        
                        lastmovealgebraic=did.getAlgebra();
                        if(chaal.teamCol.equals(goti.colBlak))
                        {
                            lastmovealgebraic.replace(goti.ghora,"♞");
                            lastmovealgebraic.replace(goti.rani,"♛");
                            lastmovealgebraic.replace(goti.mandir,"♝");
                            lastmovealgebraic.replace(goti.hathi,"♜");
                        }
                        new goti.turnmanager(true).start();
                    }
                    else
                    {
                        chaal.getRendering().transit(gui.guidelegate(new Point(did.getPrevBoardLoc().x,did.getPrevBoardLoc().y),true),360);
                        audio.play("invalid");
                        new goti.turnmanager(false).start();
                    }
                    if(did.castlecontent!=null)
                    {
                        goti g=(goti)did.castlecontent[0];//the rook to be displaced due to castling
                        Point p=(Point)did.castlecontent[1];//old location of that rook: no longer required
                        g.getRendering().transit(guidelegate(g.getLocation(),true),360);
                        audio.play("castle");
                    }
                    send=did.netSend();
                   highlight.fadeAway();
                        
                    chaal=null;
                    
            }
            
                  //mousereleased(new Point((8*scalefactor)-x,(8*scalefactor)-y),send,(JComponent)me.getSource());
               
    }
    
    static Color lastf=new Color(247, 220, 111,80);
    static Color lastt=new Color(243, 156, 18,220);
    
    static Point lastfrom=new Point(-90,-90),lastto=new Point(-90,-90);
    
    //static Color blacksquare=new Color(161, 69, 36,220);
    //static Color whitesquare=new Color(255,240,208,220);
    //static volatile Color controlBackground=new Color(colortrans.b,colortrans.r,colortrans.g,200);
    
    
    
    
    
    static class Ayush extends JPanel
    {
        public void paintComponent(Graphics gg)
        {
            
            
            Graphics2D g=(Graphics2D)gg;
            super.paintComponent(g);
            g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            
            int x=pan.getWidth();
            int y=pan.getHeight();
        
            
           
             boolean black=true;
             //g.rotate(Math.toRadians(180));
             ///g.translate(-(scalefactor*8),-(scalefactor*8));
             
            for(int i=0;i<(scalefactor*8);i+=scalefactor)
            {
                black=!black;
                for(int j=0;j<(scalefactor*8);j+=scalefactor)
                {
                    if(black==true)
                {
                    g.setColor(Environment.blacksquare);//previously 211, 84, 0
                    g.fillRect(j,i,scalefactor,scalefactor);
                    black=!black;
                }
                else//could not have used if    first case i have seen where if if is worse than if else
                {
                    g.setColor(Environment.whitesquare);
                    g.fillRect(j,i,scalefactor,scalefactor);
                    black=!black;
                }
                
                }
                
            }
            
            
                
                //LEARN HOW WORKS!           
           if(selcol!=null)
            {
                //g.setColor(selectcol);
                Point2D cent=new Point2D.Float(selcol.x+40,selcol.y+40);
                float[] dist={0.5f,1.0f};
                Color[] colors={selectcol,new Color(90,185,193,10)};
                //new Color(133, 193, 233,0)
                RadialGradientPaint p=new RadialGradientPaint(cent,40,dist,colors);
                g.setPaint(p);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
                g.fill(new RoundRectangle2D.Double(selcol.x,selcol.y,scalefactor,scalefactor,65,65));
            }
            if(lastf!=null)
            {
                Point2D cent=new Point2D.Float(lastfrom.x+40,lastfrom.y+40);
                float[] dist={0.5f,1.0f};
                Color[] colors={lastf,new Color(90,185,193,10)};
                //new Color(133, 193, 233,0)
                RadialGradientPaint p=new RadialGradientPaint(cent,40,dist,colors);
                g.setPaint(p);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
                g.fill(new RoundRectangle2D.Double(lastfrom.x,lastfrom.y,scalefactor,scalefactor,65,65));
            }
            if(lastt!=null)
            {
                Point2D cent=new Point2D.Float(lastto.x+40,lastto.y+40);
                float[] dist={0.5f,1.0f};
                Color[] colors={lastt,new Color(90,185,193,10)};
                //new Color(133, 193, 233,0)
                RadialGradientPaint p=new RadialGradientPaint(cent,40,dist,colors);
                g.setPaint(p);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
                g.fill(new RoundRectangle2D.Double(lastto.x,lastto.y,scalefactor,scalefactor,65,65));
            }
            
                
        if(highlight!=null  )
        {
              //highlight.show();
                try
                {
                for(Map.Entry m : highlight.entrySet())//what syntax is involved here?
                {
                    //if(gui.dontShowPossLocFor.contains(game.nowturnof))
                    //break;
                    if(highlight.isFor(game.nowturnof)==false)//is required?
                    break;
                    if(m.getValue()!=null  &&  m.getKey()!=null)
                    {
                        
                        
                            if( ((Color)m.getValue()).equals(gui.castlecol)==false)
                            {
                                 Color c=((Color)m.getValue());
                                 g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),(int)highlight.transparency));
                            }
                               
                                else
                                g.setColor(Environment.currentloccol);
                            Point temp=guidelegate(((Point)m.getKey()),true);
                                g.fill(new RoundRectangle2D.Double( temp.x,temp.y ,scalefactor,scalefactor,65,65));
                        
                        
                        
                    }
                }
              }
              catch(ConcurrentModificationException e)
              {
                  System.out.println(e);
              }
            
            
        }
        
            //g.setFont(new Font("Arial",Font.PLAIN,80));
            //g.setColor(new Color(245, 203, 167));
            g.setFont(new Font("LEIPFONT",Font.PLAIN,80));
            for(int i=0;i<game.pisse.size();i++)
            {
                goti plot=game.pisse.get(i);
                if(plot.equals(chaal))
                continue;
                if(plot.isInUse())
                continue;
                
                
                if((plot.guiloc.x>=0&&plot.guiloc.x<=pan.getWidth() && plot.guiloc.y>=0&&plot.guiloc.y<=pan.getHeight()) &&  plot.statOfAct ) 
                {
                    try
                    {
                        plot.getRendering().render(g);
                    }
                    catch(Exception e)
                    {
                        System.out.println(plot);
                    }
                }
                   
            }
            if(chaal!=null)
            chaal.getRendering().render(g);//drawn later so that it is shown above all
            for(int i=0;i<game.pisse.size();i++)
            {
                goti plot=game.pisse.get(i);
                if(plot.equals(chaal))
                continue;
                if(!plot.isInUse())
                continue;
                plot.getRendering().render(g);
                
            }
            
            //drawing game board done
         
        
        MessageAnimator.drawContent(g);
        
                // draw shockwave
        shockwave.render(g);
           
      }
      
    }
        
    
    
    
    static double gf=0;
    static class Controls extends JPanel
    {
        public static Point offset(Point p)
        {
            return new Point(p.x-(((64-25)*scalefactor)/80 )+80+10,p.y+ (((65-25)*scalefactor)/80)+10 );
            
        }
        @Override
        public void paintComponent(Graphics gg)
        {
            Graphics2D g=(Graphics2D)gg;
            super.paintComponent(g);
            g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            int x=getWidth();
            int y=getHeight();
        
            
          
            
            g.setPaint(new LinearGradientPaint(new Point2D.Float(0,0),new Point2D.Float(0,y),new float[]{0.56f,0.84f},new Color[]{Environment.controlBackground,Environment.controlBackGradient}));
            
            g.fillRect(0,0,x,y);
            g.setFont(new Font("LEIPFONT",Font.PLAIN,80));
            AffineTransform af=g.getTransform();
            
            for(int i=0;i<game.pisse.size();i++)
            {
                goti plot=game.pisse.get(i);
                if(plot==null)
                continue;
                if(plot.equals(chaal))
                continue;
                //if(plot.isInUse())
                //continue;
                g.setColor(plot.teamCol);
                if(plot.statOfAct==true)
                continue;
                try
                {
                      if(!((plot.guiloc.x>=0&&plot.guiloc.x<=pan.getWidth() && plot.guiloc.y>=0&&plot.guiloc.y<=pan.getHeight()) ) &&  !plot.statOfAct )
                {
                      plot.getRendering().render(g);
                    
                    
                }
                      
                    
                    
                }
                    catch(Exception e)
                      {
                          System.out.println(e+" "+plot.tipo);
                      }
                    
            }
         
            g.setTransform(af);
             //g.setFont(null);
        //g.setColor(Color.black);
            
            //g.drawLine(0,ggg,frame.getWidth(),ggg);
           
          
        
        }
    }
    private static int ggg=440;
    static int a=0,b=0;
    //APPARATUS TO SEND CURRENT USER ACTION TO SOCKET
    //TO BE DONE BY RESPECTIVE PLAYER OBJECTS
    /*
    public static void mousepressed(Point p,JComponent source)
    {
        Object[] mo;
        if(networkhr.role==1)
        mo=new Object[]{p,networkhr.select,null};
        else
        mo=new Object[]{p,networkhr.select,null};
        
        if(source instanceof JButton ==true )
        return;
        Runnable r=new networkhr.send(mo,networkhr.role);
        pool.execute(r);
    }
    public static void mousedragged(Point p,JComponent source)
    {
        Object[] mo;
        if(networkhr.role==1)
        mo=new Object[]{p,networkhr.drag,null};
        else
        mo=new Object[]{p,networkhr.drag,null};
        
        if(source instanceof JButton ==true )
        return;
        Runnable r=new networkhr.send(mo,networkhr.role);
        pool.execute(r);
    }
    public static void mousereleased(Point p,String s,JComponent source)//s is for revival type
    {
        Object[] mo;
        if(networkhr.role==1)
        mo=new Object[]{p,networkhr.put,s};
        else
        mo=new Object[]{p,networkhr.put,s};
        
        if(source instanceof JButton ==true )
        return;
        Runnable r=new networkhr.send(mo,networkhr.role);
        pool.execute(r);
    }

     */
    //true means logical board to gui coord conv
    //false means gui to logical board
    public static Point guidelegate(Point p,boolean convway)//takes in point and whether to convert it to board coordinates or vice versa , and does that
    {
        if(convway==true)
        {
            if(game.getHerePlayer().getColor().equals(goti.colWhit))
            {
                return new Point(scalefactor*p.x,(scalefactor*7)-scalefactor*p.y);
            }
            else
            {
                return new Point((scalefactor*7)-scalefactor*p.x,scalefactor*p.y);
            }
        } 
        else
        {
            
            if(game.getHerePlayer().getColor().equals(goti.colWhit))
            {
                return new Point(p.x/scalefactor,(7)-p.y/scalefactor);
            }
            else
            {
                return new Point((7)-p.x/scalefactor,p.y/scalefactor);
            }
        }

    }
    static class labedit implements Runnable
    {
        ArrayList<Runnable> l;
        public labedit(ArrayList<Runnable> l)
        {
            this.l=l;
        }
        public void run()
        {
            for(Runnable r: l)
            {
                gui.seq.execute(r);
            }
        }
    }
    static int h=0;
    public static void takescreenshot()
    {
        BufferedImage b=new BufferedImage(hold.getWidth(),hold.getHeight(),BufferedImage.TYPE_INT_RGB);
                Graphics2D gg=b.createGraphics();
                frame.paint(gg);
                String datea=new Date().toString();
                datea=datea.replace(" ","_");
                datea=datea.replace(":","_");
                try
                {
                    ImageIO.write(b,"png",new File("D:/chessAyush/chessAyush"+datea+".png"));
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
                h++;
    }
    
    static class boardPicture extends JPanel
    {
        int x,y;
        static int darkmode;
        public boardPicture()
        {
            super();
            if(game.getHerePlayer().equals(goti.colWhit))
            {
                x=-25;
                y=-25;
            }
            
            else
            {
                x=-30+6+1;
                y=x-1;
            }
            darkmode=0;
        }
        public void paintComponent(Graphics gg)
        {
            super.paintComponent(gg);
            
            Graphics2D g=(Graphics2D)gg;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(game.board,x,y,null);
            g.setColor(new Color(0,0,0,darkmode));
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }
    public void refresh()
    {
        hold.repaint();
    }
    public static boolean isInBounds(int x,int y)
    {
        
        return (  (x>0&&x<pan.getWidth() && y>0&&y<pan.getHeight()));
    }
}