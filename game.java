import javax.imageio.ImageIO;
import java.awt.*;
import java.util.*;
import javax.swing.*;



import java.util.concurrent.*;
class game implements Runnable
{
    
    //game state variables
    
    static CopyOnWriteArrayList<goti> pisse=new CopyOnWriteArrayList<goti>();
    static goti[][] chessBoard=new goti[8][8];
    static volatile int turnNumber=0;//to be initialized to 1 the first time gui.turnmanager executes
    private static boolean isGameActive=true;
    static HashMap<Integer,String> FENmoveHist =new HashMap();
    static int gamescore=0; 
    
    
    
    private static Player herePlayer,therePlayer;
    
    static int whitPisseDie=0;
    static int blakPisseDie=0;
    
    //static boolean hasStarted=false;
    
    static final int profile_HIGH=1;
    
    static Player nowturnof;
    
    static Image blakKing;
    static Image blakPawn;
    static Image blakRook;
    static Image blakQueen;
    static Image blakBishop;
    static Image blakNait;
    static Image whitKing;
    static Image whitPawn;
    static Image whitRook;
    static Image whitQueen;
    static Image whitBishop;
    static Image whitNait;
    static Image board;
    static Image icon,cameraicon;
    static  gui gg;
    static String folder="/Fantasy";
    static ArrayList<Task> endTasks=new ArrayList();
    static ArrayList<Task> startTasks=new ArrayList();
    static TimeType timeGame;
    /**
     * the constructor should just accept the player objects
     * gamemode wont be required to be known
     * if the player is non-human, an function will be invoked to make it play
     * if it is human, that function will just enable mouse listeners on the gui
     * 
     * so the parameters should be:
     * Player[]
     * {
     *     whitePlayer,blackPlayer
     * };
     * Object[]
     * {
     *     boolean record;
     *     Temporizador timeLimit;
       }
       before calling the game constructor, the online player should be linked to the sockets meant for communication with
       the peer computer
       
       connections for chatting will be initialized after calling the constructor, upon creation of gui
       
       
       player objects will keep track of time, and when a player runs out of time, the object will request a game-end
     */
    public game(Player[] players,Object[] params)
    {
        Environment.__loadGame__();
        herePlayer=players[0];
        therePlayer=players[1];
        
        Integer head=(Integer)params[0];
        if(head==0)
        {
            //it is not replay game, so recording is allowed, if wanted
            Environment.recordGame((Boolean)params[1]);
            
        }
        try
        {
            loadimages(folder);
        }
        catch (Exception ioe)
        {
            ioe.printStackTrace();
        }
        creategoties(pisse);
        goti.subject=pisse;
        game.nowturnof=game.getPlayerOfColor(goti.colBlak);
        SwingUtilities.invokeLater(this);
    }

    public static Player getHerePlayer()
    {
        return herePlayer;
    }
    public static Player getOppositePlayer()
    {
        return therePlayer;
    }
    public static Player getOpponentOf(Player p)
    {
        if(p==null)
        return null;
        else if(p.equals(herePlayer))
        return therePlayer;
        else 
        return herePlayer;
        
    }
    public static Player getPlayerOfColor(Color c)
    {
        return (herePlayer.getColor().equals(c))?herePlayer:therePlayer;
        
    }
    public static gui getChessBoard()
    {
        return gg;
    }
    static goti.turnmanager successfulTurn=new goti.turnmanager(true);
    static goti.turnmanager unsuccessfulTurn=new goti.turnmanager(false);
    static boolean ai;
    public static void endGame(String endMessage,String onGUI)
    {
        //execute the exitTask() in FileHandler class
        System.out.println(endMessage);
        if(onGUI!=null)
        gui.checkstat.setText(onGUI);
        game.isGameActive=false;
        gg.removemouselisteners();
        //thisPlayer.
        //thatPlayer.lab.deHighlightName();
        //if(game.mode==game.mode_online)
        //gui.pool.execute(new networkhr.send("Timeout",networkhr.role));
        gui.gameEvents.execute(new MessageAnimator(endMessage,false));
        game.setGameEnd();
        
        
    }
    public static void onGameEnd(Task t)
    {
        endTasks.add(t);
    }
    public static void onGameStart(Task t)
    {
        startTasks.add(t);
    }
    public void run()
    {
         Environment.log("initiated gui");
        gg=new gui();
         
        gg.initGuiLoc();
        gg.makeFrame();
        
        
        
        
        gg.initThreads();
        for(Task t:startTasks)
        gui.gameEvents.execute(t);
        gui.turnChange.execute(successfulTurn);
        
    }
    public static boolean isGameActive()
    {
        return isGameActive;
    }
    public static void setGameEnd()
    {
        //assert(isGameActive==true);
        isGameActive=false;
        for(Task t:endTasks)
        gui.gameEvents.execute(t);
        
    }
    public static void loadimages(String folder)throws Exception
    {
            //System.setProperty("java.awt.headless", "true");
        blakPawn=ImageIO.read(game.class.getResourceAsStream(folder+"/bp.png"));
           // blakPawn=blakPawn.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
            
        blakRook=ImageIO.read(game.class.getResourceAsStream(folder+"/br.png"));
            //blakRook=blakRook.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        blakQueen=ImageIO.read(game.class.getResourceAsStream(folder+"/bq.png"));
           // blakQueen=blakQueen.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        blakKing=ImageIO.read(game.class.getResourceAsStream(folder+"/bk.png"));
           // blakKing=blakKing.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        blakBishop=ImageIO.read(game.class.getResourceAsStream(folder+"/bb.png"));
           // blakBishop=blakBishop.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        blakNait=ImageIO.read(game.class.getResourceAsStream(folder+"/bn.png"));
           // blakNait=blakNait.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        whitPawn=ImageIO.read(game.class.getResourceAsStream(folder+"/wp.png"));
           // whitPawn=whitPawn.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        whitRook=ImageIO.read(game.class.getResourceAsStream(folder+"/wr.png"));
           // whitRook=whitRook.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        whitQueen=ImageIO.read(gui.class.getResourceAsStream(folder+"/wq.png"));
          //  whitQueen=whitQueen.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        whitKing=ImageIO.read(game.class.getResourceAsStream(folder+"/wk.png"));
          //  whitKing=whitKing.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        whitBishop=ImageIO.read(game.class.getResourceAsStream(folder+"/wb.png"));
            //whitBishop=whitBishop.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
        whitNait=ImageIO.read(game.class.getResourceAsStream(folder+"/wn.png"));
           // whitNait=whitNait.getScaledInstance(gui.scalefactor,gui.scalefactor,Image.SCALE_SMOOTH);
            
            
        if(getHerePlayer().getColor().equals(goti.colWhit))
            board=ImageIO.read(game.class.getResourceAsStream("/imgz/boardWhite.png"));
        else
            board=ImageIO.read(game.class.getResourceAsStream("/imgz/boardBlack.png"));
        board=board.getScaledInstance((766*gui.scalefactor)/80,(767*gui.scalefactor)/80,Image.SCALE_SMOOTH);
            
        cameraicon=ImageIO.read(game.class.getResourceAsStream("/imgz/cameraicon.png"));
        cameraicon=cameraicon.getScaledInstance(100,70,Image.SCALE_SMOOTH);
    }
    public static void creategoties(CopyOnWriteArrayList<goti> cont)
    {
        //create pawns
        for(int i=0;i<8;i++)
        {
            cont.add(new pawn(new Point(i,1),goti.colWhit));            
        }
        for(int i=8;i<16;i++)
        {
            cont.add(new pawn(new Point(i-8,6),goti.colBlak));
        }
        
        //create queen
        cont.add(new queen(new Point(3,0),goti.colWhit));
        cont.add(new queen(new Point(3,7),goti.colBlak));
                
        //create raja
        cont.add(new king(new Point(4,0),goti.colWhit));
        cont.add(new king(new Point(4,7),goti.colBlak));
                
        //create rook  20 to 23
        cont.add(new rook(new Point(0,0),goti.colWhit));
        cont.add(new rook(new Point(7,0),goti.colWhit));
        cont.add(new rook(new Point(0,7),goti.colBlak));
        cont.add(new rook(new Point(7,7),goti.colBlak));
        
        //create horse  24 to 27
        cont.add(new knight(new Point(1,0),goti.colWhit));
        cont.add(new knight(new Point(6,0),goti.colWhit));
        cont.add(new knight(new Point(1,7),goti.colBlak));
        cont.add(new knight(new Point(6,7),goti.colBlak));
        
        //create bishop   28 to 31
        cont.add(new bishop(new Point(2,0),goti.colWhit));
        cont.add(new bishop(new Point(2,7),goti.colBlak));
        cont.add(new bishop(new Point(5,0),goti.colWhit));
        cont.add(new bishop(new Point(5,7),goti.colBlak));        
    }
    public static String generateFEN(CopyOnWriteArrayList<goti> g)
    {
        String s="";
        int vacio=0;
        char[][] boa=new char[8][8];
        for(int i=0;i<g.size();i++)
        {
            if(g.get(i).statOfAct==true)
            boa[g.get(i).getLocation().x][g.get(i).getLocation().y]=g.get(i).FENvalue;
        }
        for(int i=7;i>=0;i--)
        {
            for(int j=0;j<8;j++)
            {
                char c=boa[j][i];
                
                if(((int)c)!=0)
                {
                    if(vacio!=0)
                    s=s+vacio;
                    s=s+c;
                    vacio=0;
                }
                else
                {
                    vacio++;
                }
            }
            if(vacio!=0)
            s=s+vacio;
            vacio=0;
            if(i!=0)
            s=s+"/";
        }
        if(game.nowturnof.getColor()==goti.colWhit)//the color of ai is the color of next player turn
        {
            s=s+" w";
        }
        else
        {
            s=s+" b";
        }
        if(goti.whitecastlableK=='-'  &&  goti.whitecastlableQ=='-'  &&  goti.blackcastlablek=='-'  &&  goti.blackcastlableq=='-')
        {
            s=s+"--";
        }
        else
        {
            s=s+" ";
            if(goti.whitecastlableK!='-')
            {
                s=s+goti.whitecastlableK;
            }
            if(goti.whitecastlableQ!='-')
            {
                s=s+goti.whitecastlableQ;
            }
            if(goti.blackcastlablek!='-')
            {
                s=s+goti.blackcastlablek;
            }
            if(goti.blackcastlableq!='-')
            {
                s=s+goti.blackcastlableq;
            }
        }
        
        if(pawn.enpassantloc!=null)
        {
            s=s+' '+((char)(97+pawn.enpassantloc.x));
            s=s+((int)(pawn.enpassantloc.y+1))+' ';
        }
        else
        {
            s=s+" - ";
        }
        s=s+goti.halfmovecount+' ';
        s=s+(int)(game.turnNumber/2);
        return s;
    }
    
}