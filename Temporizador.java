import java.awt.*;
import java.awt.event.*;
/**
 * this class should work in two modes:
 * calculate the time 
 * cater the time to the client
 */
class Temporizador
{
    Player team;
    int timeSec;
    int timeMin;
    int timeHr;
    boolean status=false,ignoreme;//paused by default
    
    public Temporizador(Player c)
    {
        team=c;
        analyzeTimeType();
        if(!ignoreme)
        {
        new javax.swing.Timer(1000,new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(status)
                {
                    timeSec--;
                    if(timeSec<0)
                    {
                        timeSec+=60;
                        timeMin--;
                        if(timeMin<0)
                        {
                            timeMin+=60;
                            timeHr--;
                            if(timeHr<0)
                            {
                                //status=false;
                            }
                        }
                    }
                    if(timeHr<0  &&  game.isGameActive() )
                    {
                        game.endGame(team.getName()+" lost",team.getName()+" Ran out of time ");
                        
                        status=false;
                    }
                }
                
                
            }
        }).start();
       }
    }
    
    public Color getTeamColor()
    {
        return team.getColor();
    }
    public String getUCITime()
    {
        String s=null;
        if(team.getColor().equals(goti.colWhit))
        s="wtime ";
        else
        s="btime ";
        int msc;
        if(!ignoreme)
        msc=timeSec+(60*timeMin)+(3600*timeHr);
        else
        msc=2;
        msc*=1000;
        s+=msc;
        //System.out.println(timeSec+" "+timeMin);
        return s;
    }
    public void analyzeTimeType()
    {
        //TimeType t=game.timeGame;
        if(true)//if unlimited time then no point in analyzing
        {
            ignoreme=true;
            return;
        }
        //timeSec=t.getSeconds();
        //timeMin=t.getMinutes();
        //timeHr=t.getHours();
    }
    public String getTime()
    {
        if(!ignoreme)
        return String.format("%02d",timeHr)+":"+String.format("%02d",timeMin)+":"+String.format("%02d",timeSec);
        else
        return "";
    }
    public void start()
    {
        status=true;
    }
    public void pause()
    {
        status=false;
    }
    
}