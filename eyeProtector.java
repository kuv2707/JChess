import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
class eyeProtector extends JSlider
{
    public eyeProtector()
    {
        super(JSlider.HORIZONTAL,0,100,0);
        setMinorTickSpacing(10);
        setMajorTickSpacing(20);
        setPaintTicks(true);
        setFocusable(false);
        setOpaque(false);
        addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent ce)
            {
                gui.cover.yellow=getValue()*0.004f;
                game.getChessBoard().refresh();
                System.out.println(gui.cover.yellow);
            }
        });
    }
    
    
}