package bingocreator;

import java.util.List;

import bingo.BingoCard;

public interface GUILogics {
	List<BingoCard> generate(int cardsCount, int cardsInCarnet);
}
