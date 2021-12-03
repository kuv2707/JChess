import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class statusLabel extends JLabel
{
    public statusLabel()
    {
        super();
        setHorizontalAlignment(JLabel.CENTER);
        
        
        
        
        new javax.swing.Timer(100,new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String recstat=""+Environment.canRecordGame();
                    int gn=game.turnNumber==0?1:game.turnNumber;
                if(game.isGameActive())
                {
                    String strtodraw="";
                    
                        if(game.turnNumber>1)
                        strtodraw="Last move:"+gui.lastmovealgebraic+"  "+"Moves : "+(gn-1)+recstat;
                        else
                        strtodraw="Moves : "+(gn-1)+recstat;
                        setText(strtodraw);
                }
                else
                {
                    setText("Game Over. Last move:"+gui.lastmovealgebraic+" "+"Moves: "+(gn-1)+recstat);
                }
            }
        }).start();
    }
    
}