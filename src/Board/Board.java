package Board;
import Util.Util;
import java.util.*;
import javax.swing.JTable;

public class Board
{
	public HashMap<Piece, Long> pieces1, pieces2;
	Util.Player turn;
	Long[] rivers;
	static Random random=new Random();
	Board()
	{
		turn=Util.Player.BLUE;
		pieces1=new HashMap<Piece, Long>();
		pieces2=new HashMap<Piece, Long>();
	for(var p: Piece.values())
	{
		pieces1.put(p,1L<<((long)p.getValue()+1L));
		pieces2.put(p,1L<<(60L-(long)p.getValue()+1L));
		}
		rivers=new Long[1];
		rivers[0]=spawnRiver();
	}
		
			
	Util.Player getTurn() { return turn; }

	
	static int getPosition(long whatpiece) //Map the bitmap to the range 0-62
	{
		return String.format("%63s",Long.toBinaryString(whatpiece)).indexOf('1')+1; //Dirty move
	}	

	static long spawnRiver()
	{
		long result=0;
		long offset=random.nextLong(10,20);
		for(long i=0;i<2;i++)
		{
			for(long j=1;j<=2;j++)
			{
				result|= (1L<<(64-(i*7L +j+offset)));
			}
		}
		return result;
	}

int getDistance(long p1, long p2) //Calculate the position between two points
{
	int x1=(Long.numberOfTrailingZeros(p1)+1)%7, x2=(1+Long.numberOfTrailingZeros(p2))%7, y1=(1+Long.numberOfTrailingZeros(p1))/9, y2=(1+Long.numberOfTrailingZeros(p2))/9;
	return Math.abs(x1-x2) +Math.abs(y1-y2);

}

Piece getClosestFoe(Player pl, long which)
{
	Piece temppiece=null;
	int mindist=1000, tempdist=0;
	if(pl==Util.Player.BLUE)
	{
	for(var redpiece: pieces2.keySet())
		{
			tempdist=getDistance(pieces2.get(redpiece), which);
			if(tempdist<mindist)
			{
				mindist=tempdist;
temppiece=redpiece;
			}
		}
	}
	else if(pl==Util.Player.RED)
	{
	for(var bluepiece: pieces1.keySet())
		{
			tempdist=getDistance(pieces1.get(bluepiece), which);
			if(tempdist<mindist)
			{
				mindist=tempdist;
temppiece=bluepiece;
			}
		}
	}
	return temppiece;
}

int getScore(Player pl, HashMap<Direction, Integer> somemove)
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



HashMap<Direction, Integer> minimax(Player pl, Piece which,  long pos, int depth, Piece foe, long foepos)
{

	if(depth==0)
	{
var posmoves=new HashMap<Direction, Integer>(); 
	long postemp=0L;
	int scoretemp=-1;
	for(var dirtemp: Direction.values())
	{

			postemp = potentialMove(pos, dirtemp, pl);
			if(postemp==0) continue;
//	Piece foe=getClosestFoe(pl, postemp);
			scoretemp=score(pl, which, postemp,foe, foepos);

			posmoves.put(dirtemp, scoretemp);
}
	return posmoves;
	}
	var tempmoves=new HashMap<Board.Direction, Integer>();
		if(pl == Util.Player.BLUE)
	{
	for(var k: Direction.values())
{
			long postemp = potentialMove(pos, k, pl);
			if(postemp==0) continue;
				 var foescores= minimax(Util.Player.RED, foe,  foepos, depth-1, which, postemp);
			int scoretemp=score(pl, which, postemp, foe, foepos)*depth;


int foescore=getScore(Util.Player.RED, foescores);

	tempmoves.put(k, Math.min(scoretemp, foescore));
}
	}
	else if(pl == Util.Player.RED)
	{

//	int scoretemp=-1000;
	for(var k: Direction.values())
{
			long postemp = potentialMove(pos, k, pl);
			if(postemp==0) continue;
				 var foescores= minimax(Util.Player.BLUE, foe,  foepos, depth-1, which, postemp);

			int scoretemp=score(pl, which, postemp, foe, foepos)*depth;

int foescore=getScore(Util.Player.BLUE, foescores);
	tempmoves.put(k, Math.max(scoretemp, foescore));

}
	}
	return tempmoves;
}

/*
HashMap<Direction, Integer> minimax(Player pl, Piece which, long pos, int depth, HashMap<Direction, Integer> posmoves) {
    if (depth == 0) {
        for (var dirtemp : Direction.values()) {
            long postemp = potentialMove(pos, dirtemp, pl);
            if (postemp == 0) continue;
            int scoretemp = score(pl, which, postemp, foe);
            posmoves.put(dirtemp, scoretemp);
        }
        return posmoves;
    }

    HashMap<Direction, Integer> bestMoves = new HashMap<>();
    if (pl == Util.Player.BLUE) {
        int maxScore = Integer.MIN_VALUE;
        var foe = getClosestFoe(pl, pos);
        var foeScores = minimax(Util.Player.RED, foe, pieces2.get(foe), depth - 1, new HashMap<>());
        for (var dirtemp : Direction.values()) {
            int currentScore = posmoves.getOrDefault(dirtemp, Integer.MIN_VALUE);
            maxScore = Math.max(maxScore, foeScores.getOrDefault(dirtemp, currentScore));
            bestMoves.put(dirtemp, maxScore);
        }
    } else if (pl == Util.Player.RED) {
        int minScore = Integer.MAX_VALUE;
        var foe = getClosestFoe(pl, pos);
        var foeScores = minimax(Util.Player.BLUE, foe, pieces1.get(foe), depth - 1, new HashMap<>());
        for (var dirtemp : Direction.values()) {
            int currentScore = posmoves.getOrDefault(dirtemp, Integer.MAX_VALUE);
            minScore = Math.min(minScore, foeScores.getOrDefault(dirtemp, currentScore));
            bestMoves.put(dirtemp, minScore);
        }
    }
    return bestMoves;
}
*/
	long potentialMove(long which, Direction dir, Player pl)
{
	long temp=which;
	switch(dir)
		{
			case Direction.UP:
				temp= temp>>7;
				break;
			case Direction.DOWN:
				temp= temp<<7;
				break;
			case Direction.LEFT:
				temp= temp>>1;
				break;
			case Direction.RIGHT:
				temp= temp<<1;
				break;
		}
		for(int i=0;i<rivers.length;i++)
	{
		if((rivers[i]&temp)!=0) 
		{
			return 0;
		}
	}
if(getPosition(temp)<1 || getPosition(temp)>63)
	return 0;
if( (dir == Direction.RIGHT || dir==Direction.LEFT) && getPosition(temp)%7==0) return 0;
	return temp;
}

boolean move(Piece which, Direction dir)
	{
		long temp=-1;
		if(turn==Util.Player.BLUE)
		{
	if(pieces1.get(which)==null) return false;
	temp=pieces1.get(which);
			switch(dir)
			{
				case Direction.UP:
					temp=temp>>7;
					break;
				case Direction.DOWN:
					temp=temp<<7;
					break;
				case Direction.LEFT:
					temp>>=1;
					break;
				case Direction.RIGHT:
					temp<<=1;
					break;
			}
	for(var redpiece: pieces2.keySet())
			{
				if((pieces2.get(redpiece)^temp)==0)
				{
					break;
				}
			}
		}
		else if(turn==Util.Player.RED)
		{
		if(pieces2.get(which)==null) return false;
			temp=pieces2.get(which);
			switch(dir)
			{
				case Direction.UP:
					temp>>=7;
					break;
				case Direction.DOWN:
					temp<<=7;
					break;
				case Direction.LEFT:
					temp>>=1;
					break;
				case Direction.RIGHT:
					temp<<=1;
					break;
			}
	for(var bluepiece: pieces1.keySet())
{
						if((pieces1.get(bluepiece)^temp)==0)
						{
//							System.out.println("Red's "+getPieceName(which.getValue())+" tries to walk over Blue's "+getPieceName(i));
							break;
						}
			}
		}
		for(int i=0;i<rivers.length;i++)
		{
			if((rivers[i]&temp)!=0) 
			{
				return false;
			}
		}
	if(getPosition(temp)<1 || getPosition(temp)>63)
		return false;
	if( (dir == Direction.RIGHT || dir==Direction.LEFT) && getPosition(temp)%7==0) return false;

	if(turn==Util.Player.BLUE)
	{
		pieces1.put(which, temp);
		turn=Util.Player.RED;
	}
	else if(turn==Util.Player.RED)
	{
		pieces2.put(which,temp);
		turn=Util.Player.BLUE;
	}
		return true;
	}

	javax.swing.JTable getBoard()
	{
		String[]  columns= new String[]{"A","B","C","D","E","F","G"};
		char[] rows=new char[]{'a','b','c','d','e','f','g'};
		Object[][] data=new Object[9][7];
			StringBuilder res=new StringBuilder();
		for(int i=0;i<7;i++)
		{
			for(int j=1;j<=9;j++)
			{
				for(int k=0; k<rivers.length; k++)
				{
	long temp=1L<<(long)(64 -(9*i+j));
	if((rivers[k]&temp)!=0)
					{
						res.append(" water ");

						break;
					}
				}
				for(var bluepiece: pieces1.keySet())
					if(getPosition(pieces1.get(bluepiece))==(i*9+j))
					{
						res.append("blue's ");
						res.append(getPieceName(bluepiece));
						break;
				}
				for(var redpiece: pieces2.keySet())
					if(getPosition(pieces2.get(redpiece))==(i*9+j))
					{
						res.append("Red's ");
						res.append(getPieceName(redpiece));
						break;
				}

			res.append(" ");
res.append(rows[i]);
res.append(j);
data[j-1][i]=res.toString();				
}
res=new StringBuilder();
	}		
		JTable jt=new JTable(data,columns);
		return jt;

	}
	@Override
	public String toString()
	{
		StringBuilder res=new StringBuilder();
		char[] rows=new char[]{'a','b','c','d','e','f','g'};
				for(int j=0;j<9;j++)
				{
			for(int i=0;i<7;i++)
{

					for(int k=0; k<rivers.length; k++)
					{
	long temp=1L<<(63L-(7L*j+i+1L));

						if( (rivers[k]&temp)!=0 )
						{
							res.append(" water ");
							break;
						}
					}
				for(var bluepiece: pieces1.keySet())
					if(getPosition(pieces1.get(bluepiece))==(j*7+i+1))
					{
						res.append("blue's ");
						res.append(getPieceName(bluepiece));
						break;
				}
				for(var redpiece: pieces2.keySet())
					if(getPosition(pieces2.get(redpiece))==(j*7+i+1))
					{
						res.append("Red's ");
						res.append(getPieceName(redpiece));
						break;
				}
				res.append(" ");
	res.append(rows[i]);
	res.append((j+1));
}

	res.append("\n");
		}
		return res.toString();
	}
}
