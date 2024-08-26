package Board;
import java.util.Map;

public enum Piece
	{
		RAT(0),
		CAT(1),
		DOG(2),
		WOLF(3),
		LEOPARD(4),
		TIGER(5),
		LION(6),
		ELEPHANT(7);
		final int value;
		Piece(int v)
		{
			value=v;
		}
		public int getValue() { return value; }
	static Map<Piece,String> pieceNames=Map.of(
		Piece.RAT,"Rat",
		Piece.CAT, "Cat",
		Piece.DOG, "Dog",
		Piece.WOLF, "Wolf",
		Piece.TIGER, "Tiger",
		Piece.LEOPARD, "Leopard",
		Piece.LION, "Lion",
		Piece.ELEPHANT, "Elephant");
	static String getPieceName(Piece w)
	{
		return pieceNames.get(w);
	}
}
