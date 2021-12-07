

//(9,-10) is garbage coordinate: if any goti is there, it has no access to board whatsoever
//add, timer etc
//add provision for undo last move


import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;
abstract class goti
{
    static final String ghora="♘";
    static final  String hathi="♖";
    static final  String pyada="♙";
    static final  String mandir="♗";
    static final  String rani="♕";
    static final  String raja="♔";
    static final  Color colWhit=new Color(255,255,255);
    static final  Color colBlak=new Color(0,0,0);
    static final  Color nullcol=new Color(0,0,0,0);
    static CopyOnWriteArrayList<goti> subject;
    static char whitecastlableK='K',whitecastlableQ='Q',blackcastlablek='k',blackcastlableq='q';
    static int halfmovecount=0;
    
    private Point location;
    Point guiloc;
    int value;
    String tipo,attribute;
    boolean statOfAct;    //true=alive   false=dead
    
    Image face;
    Point size;
    char FENvalue;
    PossibLox<Point,Color> lop;
    Color teamCol;
    rendering rend;
    goti killer=null;
    public goti(goti g)
    {
        this.teamCol=g.teamCol;
        this.location=(Point)g.location.clone();
        this.tipo=new String(g.tipo);
        this.statOfAct=g.statOfAct;
        this.value=g.value;
        this.FENvalue=g.FENvalue;
        this.guiloc=(Point)g.guiloc.clone();
        this.face=g.face;
        this.size=g.size;
        
    }
    public goti(String typeG,Point ubi,Color teamCol)
    {
        this.location=ubi;
        game.chessBoard[ubi.x][ubi.y]=this;
        this.setTipo(typeG);
        this.teamCol=teamCol;
        this.statOfAct=true;
        this.size=new Point(gui.scalefactor,gui.scalefactor);
        this.setFace();
    }
    public void setFace()
    {
        if(this instanceof pawn  &&  this.teamCol.equals(goti.colBlak))
        {
            face=game.blakPawn.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);                
        }
                    
        if(this instanceof king  &&  this.teamCol.equals(goti.colBlak))
        {
            face=game.blakKing.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                        
        if(this instanceof rook  &&  this.teamCol.equals(goti.colBlak))
        {
            face=game.blakRook.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                    
        if(this instanceof knight  &&  this.teamCol.equals(goti.colBlak))
        {
            face=game.blakNait.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                    
        if(this instanceof bishop  &&  this.teamCol.equals(goti.colBlak))
        {
            face=game.blakBishop.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                    
        if(this instanceof queen  &&  this.teamCol.equals(goti.colBlak))
        {
            face=game.blakQueen.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                    
                  
                    
                    
        if(this instanceof pawn  &&  this.teamCol.equals(goti.colWhit))
        {
            face=game.whitPawn.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                    
        if(this instanceof king  &&  this.teamCol.equals(goti.colWhit))
        {
            face=game.whitKing.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                        
        if(this instanceof rook  &&  this.teamCol.equals(goti.colWhit))
        {
            face=game.whitRook.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                    
        if(this instanceof knight  &&  this.teamCol.equals(goti.colWhit))
        {
            face=game.whitNait.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                    
        if(this instanceof bishop  &&  this.teamCol.equals(goti.colWhit))
        {
            face=game.whitBishop.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
                    
        if(this instanceof queen  &&  this.teamCol.equals(goti.colWhit))
        {
            face=game.whitQueen.getScaledInstance(size.x,size.y,Image.SCALE_SMOOTH);
        }
    }
    //is opposite team piece in the passed point with respect to the caller object
    public void associate(rendering r)
    {
        r.master=this;
        this.rend=r;
    }
    public rendering getRendering()
    {
        return this.rend;
    }
    public static goti gotiAt(Point p)
    {
       if(p.x>7  ||  p.y>7 ||  p.x<0  ||  p.y<0)
       return null;
       
       return game.chessBoard[p.x][p.y];
    }
    
    protected static goti gotiAt(int x, int y)
    {
        return gotiAt(new Point(x,y));
    }
    public PossibLox<Point,Color> whatLocationsPossible()
    {
        if(this.statOfAct==false)
        return new PossibLox<Point,Color>(goti.nullcol);  //empty
        Point temploc=this.location;
        ArrayList<Point> l=this.possibleLocations();
        
        Iterator<Point> ll=l.iterator();
        
        while(ll.hasNext())
        {
            Point p=ll.next();
            if(!isInBounds(p))
            {
                ll.remove();
                continue;
            }
            goti who = goti.gotiAt(p);
            //agar koi goti h p par, to dekho ki wo same color ka h ya nahi
            //agar same color ka h to that point is not accessible
            //nahi to dekho ki waha jaa k kahi check to nahi mil rha h!
            if (who != null)
            {
                if (who.teamCol.equals(this.teamCol))//p pe jo goti h wo same team ka h matlab waha nahi jana h and no further checks for that location(p)
                {
                    ll.remove();//=who.location
                    continue;
                } else
                {
                    //who.location=new Point(10,3);
                    who.statOfAct = false;
                    game.chessBoard[who.location.x][who.location.y]=null;
                }

            }

            //this goti ko who ke location pe rakh k dekho ki isko check mil rha h ya nahi
            this.setLocation(p);
            if (isCheckTo(this.teamCol) != null)
            {
                ll.remove();
            }
            this.setLocation(temploc);//checking ho gya ab wapas le aao
            game.chessBoard[p.x][p.y]=who;
            if (who != null)
            {
                who.statOfAct = true;
            }
        }


        
        PossibLox<Point,Color> locAndCol=new PossibLox<Point,Color>(this.teamCol); 
        for(int i=0;i<l.size();i++)
        {
            if( isInLimits(l.get(i)))
            {
                if(gotiAt(l.get(i))==null)
                    locAndCol.put(l.get(i),Environment.destacar);
                if(gotiAt(l.get(i))!=null)
                    locAndCol.put(l.get(i),gui.matar);
                if(this instanceof pawn  &&  l.get(i).equals(pawn.enpassantloc))
                    locAndCol.replace(l.get(i),gui.matar);
            }    
        }
                    
        if(this instanceof king  &&  ((king)this).castlable==true)
        {
            king este=(king)this;
            este.setCastleProperty(new CastleProperty());
            
            //kingside castling
            rook rook1=null;
            goti g1=gotiAt(7,this.location.y);
            if(g1!=null)
            {
                if(g1 instanceof rook)
                {
                    rook1=(rook)g1;
                }
            }
            if(rook1!=null)
            {
                if(rook1.castlable==true)
                {
                    if(gotiAt(6,this.location.y)==null  &&  gotiAt(5,this.location.y)==null)
                    {
                        if(this.isCheckAt(6,this.location.y)==false  &&  this.isCheckAt(5,this.location.y)==false)
                        {
                            if(this.isCheckAt(this.location)==false)
                            {
                                locAndCol.put(new Point(6,this.location.y),gui.castlecol);
                                este.getCastleProperty().bind(new Point(6,this.location.y),new Point(5,this.location.y),rook1);
                            }
                        }
                    }
                }
            }
            //queenside castling
            rook rook2=null;
            goti g2=gotiAt(0,this.location.y);
            if(g2!=null)
            {
                if(g2 instanceof rook)
                {
                    rook2=(rook)g2;
                }
            }
            if(rook2!=null)
            {
                if(rook2.castlable==true)
                {
                    if(gotiAt(2,this.location.y)==null  &&  gotiAt(3,this.location.y)==null)
                    {
                        if(this.isCheckAt(2,this.location.y)==false  &&  this.isCheckAt(3,this.location.y)==false)
                        {
                            if(this.isCheckAt(this.location)==false)
                            {
                                locAndCol.put(new Point(2,this.location.y),gui.castlecol);
                                este.getCastleProperty().bind(new Point(2,this.location.y),new Point(3,this.location.y),rook2);
                            }
                        }
                    }
                }
            }
        }
        this.lop=locAndCol;
        return locAndCol;
    }

    private boolean isCheckAt(Point arg)
    {
        //OPPOSITE team ka har ZINDA goti k range me given x,y na aa jaye
        for (goti gots : subject)
        {
            if(gots.teamCol.equals(this.teamCol))
            continue;
            if(gots.statOfAct==false)
            continue;
            ArrayList<Point> Pospts=gots.possibleAttackLocations();

            for(int i=0;i<Pospts.size();i++)
            {
                if(Pospts.get(i).equals(arg))
                return true;
            }
        }
        return false;
    }
    private boolean isCheckAt(int x,int y)
    {
        return isCheckAt(new Point(x,y));
    }
    
    public static goti isCheckTo(Color col)//if gui has called, then return it true/false else return the goti which has given check
    {
        /*
         * first find if any team gets check
         * if that team is the same as that passed as argument, return true
         * else return false
         */
        goti king;
        if(col.equals(goti.colWhit))
        king=subject.get(18);
        else
        king=subject.get(19);
        goti checkYes=null;
        for(int i=0;i<subject.size();i++)
        {
            goti temp=subject.get(i);
            if(king.teamCol.equals(temp.teamCol)==false &&  temp.statOfAct==true  )//opposite team ka zinda goti encountered
            {
                ArrayList<Point> Pospts=temp.possibleAttackLocations();
                for(int k=0;k<Pospts.size();k++)
                {
                    if(  king.getLocation().equals(Pospts.get(k)))
                    {
                        checkYes=subject.get(i);
                    }
                }
            }
        }
        return checkYes;
    }
    abstract ArrayList<Point> possibleLocations();
    ArrayList<Point> possibleAttackLocations()
    {
        if(this instanceof pawn)
        {
            return ((pawn)this).killableLox(new ArrayList<Point>());
        }
        else
        {
            return possibleLocations();
        }
    }

    
    static int outX=8,outY=0;
    static int out2X=8,out2Y=2;
    
    public void killed(goti by)
    {
        if(this.tipo.equals(goti.raja)==true)
        {
            game.setGameEnd();    
        }
            
        if(this.teamCol.equals(goti.colWhit))
        {
            game.gamescore=game.gamescore-this.value;
        }
        if(this.teamCol.equals(goti.colBlak))
        {
            game.gamescore=game.gamescore+this.value;
        }
            
        if(this.teamCol.equals(goti.colWhit))
            game.whitPisseDie++;
        if(this.teamCol.equals(goti.colBlak))
            game.blakPisseDie++;
        this.statOfAct=false;
        game.chessBoard[this.location.x][this.location.y]=null;
        //this.face=this.face.getScaledInstance((int)( (gui.scalefactor*3)/4),(int)( (gui.scalefactor*3)/4),Image.SCALE_SMOOTH);
        this.size=new Point((int)( (gui.scalefactor*3)/4),(int)( (gui.scalefactor*3)/4));
        new Thread(new Runnable()
        {
            public void run()
            {
                for(float f=1;f>=0.5;f-=0.02)
                {
                    getRendering().scaling=f;
                    try
                    {
                        Thread.sleep(3);
                    }
                    catch (InterruptedException ie)
                    {
                        ie.printStackTrace();
                    }
                }
            }
        }).start();
        this.killer=by;
        
    }
    
    public static Color colorForm(String k)
    {
        if(k.equals("Black"))
            return goti.colBlak;
        else
            return goti.colWhit;
    }
    public static Color oppositeColor(Color in)
    {
        if(in.equals(goti.colWhit))
            return goti.colBlak;
        else
            return goti.colWhit;
    }
    public String getType()
    {
        return getTipo();  //tipo del objeto que llamo
    }
    public Point getLocation()
    {
        return location;   //ubication del objeto que llamo
    }
    public void setLocation(Point fsd)
    {
        game.chessBoard[fsd.x][fsd.y]=game.chessBoard[getLocation().x][getLocation().y];
        game.chessBoard[getLocation().x][getLocation().y]=null;
        this.location=fsd;
    }
    
    public static boolean isInLimits(Point f)
    {
        return (f.getX()<=7&&f.getX()>=0&&f.getY()<=7&&f.getY()>=0);
    }
    public String getTipo()
    {
        return tipo;
    }
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
    abstract public stepstaken movedTo(Point p);
    public void recordmove(stepstaken steps)
    {
        if(subject!=game.pisse)
        {
            return;
        }
        new Thread(new write(steps)).start();
    }
    static class write implements Runnable
    {
        stepstaken steps;
        public write(stepstaken steps)
        {
            this.steps=steps;
        }
        public void run()
        {
            FileHandler.writeMove(steps.getAlgebra());

        }
    }
    public void callbackparent(stepstaken steps)
    {
        steps.state(true);
        steps.makeAlgebraicNotation();
        recordmove(steps);
    }
    static class turnmanager implements Runnable
    {
        boolean valid;
        public turnmanager(boolean b)
        {
            valid=b;
        }
        public void run()
          {
              if(!game.isGameActive())             
              {
                  return;
              }
              if(valid)
              {
                  //game.FENmoveHist.put(game.turnNumber,game.generateFEN(subject));
                  
                  game.nowturnof=game.getOpponentOf(game.nowturnof);
                  game.turnNumber++;
                  
                  goti.checkforcheck(game.nowturnof);//checking is done for the next player
                  /**
                   * on releasing mouse by the player, the required things happen
                   * after that, turmanager is called 
                   * now if the player whose turn is to come has got checkmate etc, it will display the messages and call
                   * setGameEnd to end the game (isGameActive=false)
                   */
                  
              }
              else
              {
                  
              }
                 
               
              
              game.nowturnof.setActivatedTurn();
              game.getOpponentOf(game.nowturnof).endMove();
                  game.nowturnof.playMove();
               
        }
    }
    //NEEDS RENOVATION
    public static void checkforcheck(Player p)//invcol=jiske liye check karna h
    {
        int cb=0,cw=0;
        Color invcol=p.getColor();
        //System.out.println("Checking for player "+p);
        ArrayList<Runnable> tasklist=new ArrayList<>();
        goti checkgiver=goti.isCheckTo(invcol);
        //check for draw
        for(int i=0;i<subject.size();i++)
        {
            if(subject.get(i).statOfAct==true  &&  subject.get(i).teamCol.equals(goti.colWhit))
            cw++;
            if(subject.get(i).statOfAct==true  &&  subject.get(i).teamCol.equals(goti.colBlak))
            cb++;
        }
        /**
         * replace cw and cb with number of pieces which can force a checkmate
         */
        if(  (cw==cb  &&  cb==1)  ||  cw==1  ||  cb==1)//some team definitely ran short of gotis
        /**
         * if no team has 1 goti , this block wont execute
         */
        {
            
            game.setGameEnd();
            String aud=null;
            String vdo="";
            if( (cw==1  &&  game.getHerePlayer().getColor().equals(goti.colWhit))  || (cb==1  &&  game.getHerePlayer().getColor().equals(goti.colBlak))  )//getHerePlayer() ran short of gotis
            {
                //show the name of who won or lost: no pronouns
                vdo=game.getOppositePlayer().getName()+" won!😎";
                    tasklist.add(new Runnable()
                 {
                     public void run()
                     {
                         gui.checkstat.setText(game.getHerePlayer().getName()+" has insufficient material ");
                     }
                 }); 
                
            }
            /**
             * if i am white, and i have many gotis and black has only 1, then else block will be executed
             * same for when i am black and i have many gotis but white has only 1
             * both teams cannot simultaneously have one goti left, as one has to lose it before the other
             */
            else
            {
                aud="win";
                
                    vdo=game.getHerePlayer().getName()+" won!😎";
                
                tasklist.add(new Runnable()
                 {
                     public void run()
                     {
                         gui.checkstat.setText(game.getOppositePlayer().getName()+" has insufficient material ");
                     }
                 }); 
            }
            if(cw==cb  &&  cb==1)
            {
                aud="draw";
                vdo=MessageAnimator.draw;
            }
            final String temp=aud;
            final String te=vdo;
            //tasklist.add(new animresult(vdo));
            tasklist.add(new Runnable()
            {
                public void run()
                {
                    game.endGame(te,null);
                    audio.play(temp);
                }
            });
            SwingUtilities.invokeLater(new gui.labedit(tasklist));
            System.out.println("game was draw");
            return;
        }
       
        
        
        
        //check for stalemate, checkmate, simple check and 50movedrawrule
        //given team ke har goti ka possible locations dekho, agar kisi ka bhi nonZero h means stalemate nahi hua h
        boolean stale=true;
        for(int k=0;k<subject.size();k++)
        {
            goti g=subject.get(k);
            
            if(g.teamCol.equals(invcol)  &&  g.statOfAct==true)
            {
                PossibLox<Point,Color> h=(PossibLox<Point,Color>)g.whatLocationsPossible();
                if(h.size()!=0)
                {
                    stale=false;
                    break;
                }
                
            }
        }
        
        if(stale==false)//not a stalemate, i.e., simple check (and game can be ended by 50movedrawrule)
        {
            if(checkgiver!=null)//it is a check
            {
                 tasklist.add(new Runnable()
                 {
                     public void run()
                     {
                         gui.checkstat.setText(p.getName()+" got a check  ");
                         
                         p.getGUIRepresentation().dangerOscillate(game.turnNumber);
                     }
                 });           
                                
            }
            else
            {
                tasklist.add(new Runnable()
                 {
                     public void run()
                     {
                         gui.checkstat.setText("");
                         
                         
                     }
                 }); 
            }
            
            
            if(halfmovecount==100  )//100th move pe agar checkmate etc hua then good else agar kuch nahi hua or simply check diya then 50-move-draw ho jayega
            {
                game.setGameEnd();
                tasklist.add(new Runnable()
                {
                    public void run()
                    {
                        game.endGame(MessageAnimator.draw,null);
                        gui.checkstat.setText("50 move draw rule");
                        audio.play("draw");
                    }
                });
                
            }
            SwingUtilities.invokeLater(new gui.labedit(tasklist));
            return ;
        }
        else
        {
            /**
             * stalemate or possibly checkmate
             * game ends either way
             */
            game.setGameEnd();
            p.performActions(checkgiver!=null,tasklist);//fills the tasklist with win/lose tasks
            /**
             * not removing action listeners as it is fun to move gotis and see that there is no valid move
             * surely this will not be the case with 50movedrawrule as there further moves can be made, so remove listeners there
             */
            SwingUtilities.invokeLater(new gui.labedit(tasklist));      
            return;
        }
        
    }
    
    public ArrayList<Point> getSlantLocations(ArrayList<Point> l)
    {
        Point currL=this.getLocation();
        
        int x=(int)currL.getX();
        int y=(int)currL.getY();
        for(int i=1;i<8;i++)
        {
            if( x+i<8  &&  x+i>=0  &&  y+i<8  &&  y+i>=0)
            {
                l.add(new Point(x+i,y+i));
                if(gotiAt(new Point(x+i,y+i))!=null)
                break;            
            }
        }
        for(int i=-1;i>-8;i--)
        {
            if( x+i<8  &&  x+i>-1  &&  y+i<8  &&  y+i>=0)
            {
                l.add(new Point(x+i,y+i));
                if(gotiAt(new Point(x+i,y+i))!=null)
                break;            
            }
        }
        for(int i=1;i<8;i++)
        {
            if( x+i<8  &&  x+i>=0  &&  y-i<8  &&  y-i>=0)
            {
                l.add(new Point(x+i,y-i));
                if(gotiAt(new Point(x+i,y-i))!=null)
                break;        
            }
        }
        for(int i=-1;i>-8;i--)
        {
            if( x+i<8  &&  x+i>=0  &&  y-i<8  &&  y-i>=0)
            {
                l.add(new Point(x+i,y-i));
                if(gotiAt(new Point(x+i,y-i))!=null)
                break;        
            }
        }   
        return l;
    }
    public ArrayList<Point> getRectLocations(ArrayList<Point> l)
    {
        Point currL=this.getLocation();
        
        int x=(int)currL.getX();
        int y=(int)currL.getY();
        
         //horizontal scan
        for(int i=x-1;i>=0;i--)
        {
            l.add(new Point(i,y));
            if(gotiAt(new Point(i,y))!=null)
            {
                break;
            }
        }
        for(int i=x+1;i<8;i++)
        {
            l.add(new Point(i,y));
            if(gotiAt(new Point(i,y))!=null)
            {
                break;
            }
        }
        //vertical scan
        for(int i=y-1;i>-1;i--)
        {
            l.add(new Point(x,i));
            if(gotiAt(new Point(x,i))!=null)
            {
                break;
            }
        }
        for(int i=y+1;i<8;i++)
        {
            l.add(new Point(x,i));
            if(gotiAt(new Point(x,i))!=null)
            {
                break;
            }
        }
        return l;
    }
    @Override
    public String toString()
    {
        return getClass().getSimpleName()+" at "+getLocation().toString()+" of color "+teamCol;
    }
    public static boolean isInBounds(Point p)
    {
        return !(p.x<0  ||  p.y <0  ||  p.x>7  ||  p.y>7);
    }
    /**
     * a method which takes in a fen string and sets the current subject arraylist accordingly
     */
    public void setStateFEN(String s)
    {

    }
}