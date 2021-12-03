/**
 * this class encapsulates all properties of a player-
 * its type(human,ai, lan)
 * its name
 * its color
 * time it has (through the temporizador object)
 * its label in the gui
 * message to be displayed for it in various occasions in the gui
 * 
 * by default represents the human player
 * and its derived classes represent AI,savedgame etc
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
abstract class Player extends JComponent//for easy use in gui events
{
    Color team;
    String name;
    
    Temporizador time;
    nameLabel lab;
    boolean isActivatedTurn=false;
    boolean renamable=true;
    volatile String revival="";
    boolean hasCheck=false;
    public Player(Color c, String nam)
    {
        team=c;
        name=nam;
        
        time=new Temporizador(this);
        lab=new nameLabel("",JLabel.CENTER,this);
        lab.updateName();
        lab.setToolTipText(getTeamName());
        lab.setFont(CONSTANTS.windowfont);
        game.onGameEnd(new Task()
        {
            public void perform()
            {
                lab.deHighlightName();
            }
        });
        lab.setOpaque(false);
        lab.setFocusable(false);
        
    }
    
    /***
     * this method will be used by filereader and ai to move gotis
     */
    public void actualAction(Point toMove,Point moverA)//coordinates in terms of board not gui
    {
        goti g=game.chessBoard[toMove.x][toMove.y];
        //System.out.println(toMove+"  "+moverA);
        toMove=gui.guidelegate(toMove,true);
        Environment.log("goti selected for motion is"+g);
        MouseEvent cli=new MouseEvent(this,0,9999,0,toMove.x+5,toMove.y+5,1,true);
        game.getChessBoard().mousePressed(cli);
        try
        {
            Thread.sleep(180);
        }
        catch (Exception ie)
        {
            ie.printStackTrace();
        }
        double k=0;
        double accn=0,accn2=0;
        
        
        moverA=gui.guidelegate(moverA,true);
        
        
        double kf=-10;
        do
         {
            
            k=Math.pow(2,kf);
            g.guiloc.x=(int)  ((moverA.x*k+toMove.x)/(k+1));
            g.guiloc.y=(int)  ((moverA.y*k+toMove.y)/(k+1));
                                                  
            
            kf+=0.2;
             
            gui.hold.repaint();
            try
            {
                if(kf>7)
                Thread.sleep(1);
                else
                Thread.sleep(9);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
            
        }while(kf<50);
        
        
        
        
        MouseEvent rel=new MouseEvent(this,0,9999,0,moverA.x+5,moverA.y+5,1,true);
        try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        game.getChessBoard().mouseReleased(rel);
         
    }
    public Temporizador getTime()
    {
        return time;
    }
    public String getName()
    {
        return name;
    }
    public String getStringColor()
    {
        return "("+team.getRed()+","+team.getGreen()+","+team.getBlue()+")";
    }
    public Color getColor()
    {
        return team;
    }
    public nameLabel getGUIRepresentation()
    {
        return lab;
    }
    public String getRevivalCandidate()
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
    public void setName(String s)
    {
        Environment.log(toString()+" ka name changed to "+s);
        name=s;
        lab.updateName();
        
    }
    abstract public void playMove();
    abstract public void endMove();
    
    /**
         * will be overriden by
         * AI class to end stockfish process
         * SavedGameReader class to close file resources
         */
    abstract public void destroy();
    /**
     * except Human, all return themselves
     * as human's mouse event is caused by the game panel itself
     * while others manually trigger them, and pass themselves in the constructor of MouseEvent
     */
    abstract public JComponent getMouseEventCauser();
    @Override
    public String toString()
    {
        return getColor()+" "+getName()+"  "+getClass().getSimpleName();
    }
    
    
    public void performActions(boolean checktrue,ArrayList<Runnable> tasklist)
    {
        MessageAnimator exec=null;
        Runnable aud=null;
        exec=new MessageAnimator(game.getOpponentOf(this).getName()+" won!😎",false);
                aud=(new Runnable()
                    {
                        public void run()
                        {
                            audio.play("win");
                        }
                    });
                       
            tasklist.add(exec);
            tasklist.add(aud);
            

            tasklist.add(new Runnable()
            {
                public void run()
                {
                    if(checktrue)
                    gui.checkstat.setText(getName()+" has got a checkmate!  ");//arrange for pronoun here too
                    else
                        gui.checkstat.setText(getName()+" has got a stalemate!  ");//arrange for pronoun here too
                    game.setGameEnd();
                }
            });
        }
        
    public void setActivatedTurn()
    {
        if(isActivatedTurn)
        return;
        isActivatedTurn=true;
        lab.highlightName();
        game.getOpponentOf(this).lab.deHighlightName();
        game.getOpponentOf(this).isActivatedTurn=false;
        //System.out.println(this.toString()+" got activated");
    }
    public String getTeamName()//stored in the saved game file
    {
        if(team.equals(goti.colWhit))
        return "white";
        else
        return "black";
    }
    }
