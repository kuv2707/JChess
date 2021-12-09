import java.util.*;
import java.awt.*;
public class deadManager
{
    static ArrayList<Point> lox=new ArrayList<>();
    static int stackbottom,stacktop;
    public static void initlox()
    {
        for(int i=420;i<620;i+=40)
        {
            for(int j=680;j<=game.getChessBoard().pan.getWidth()+540;j+=40)
            {
                lox.add(new Point(j,i));
            }
        }
        stackbottom=0;
        stacktop=lox.size()-1;
        if(lox.size()-1<32)
        System.out.println("Logical error");
    }
    public static Point getFreeLocation(goti g)
    {
        if(stacktop<stackbottom)
        Environment.log("deadManager error");
        Point ret=null;
        if(g.teamCol.equals(game.getHerePlayer().getColor()))
        {
            ret=lox.get(stacktop);
            stacktop--;
        }
        else
        {
            ret=lox.get(stackbottom);
            stackbottom++;
        }
        return ret;
    }
}