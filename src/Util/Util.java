package Util;
import java.util.HashMap;

import Board.Piece;

public class Util
{
    public enum Player 
    {
        BLUE,
        RED 
    }
    enum Direction
    {
        LEFT(0),
        RIGHT(1),
        UP(2),
        DOWN(3);
        final int value;
        Direction(int v)
        {
            value=v;
        }
        public int getValue() { return value; }
    }
    static public int score(Player pl, Piece which, long pos)
    {
        int finalscore=0;
        if(pl == Util.Player.BLUE)
        {
                finalscore= getPosition(pos)==10 ? -1000 : -1000+Math.abs(10-getPosition(pos));
        for(var k: pieces2.keySet())
                if ( (pieces2.get(k)^p1pieces.get(which))==0 )
    {
        if(k.getValue()<which.getValue()) 
    finalscore-=500;
    else
    finalscore+=500;
    break;
    }
            return finalscore;
        }
        else if(pl == Util.Player.RED)
        {
            finalscore=getPosition(pos)==50 ? 1000 : 1000-Math.abs(50-getPosition(pos));
        for(var k: p1pieces.keySet())
                if ( (p1pieces.get(k) ^ pos)==0 ) 
    {
            if(k.getValue()<which.getValue()) finalscore+=500;
            else finalscore-=500;
    break;
    }
    
            return finalscore;
        }
        return 0;
    }
        
    
    int getAponentScore(Player pl, HashMap<Direction, Integer> somemove)
{
	if(somemove==null) return 0;
	int scoretemp=0;
	for(var k: somemove.values())
{
	if(pl==Util.Player.BLUE && scoretemp>k) scoretemp=k;
	else if(pl==Util.Player.RED && scoretemp<=k) scoretemp=k;
}
return scoretemp;
}


    private Util() { }
}
