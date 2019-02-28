package bingocreator;

import java.io.IOException;
import java.util.List;

import bingo.print.PrintableBingoCard;
import bingo.text.BingoCardParameters;

public interface GUILogics {
	List<PrintableBingoCard> generate(int cardsCount, int cardsInCarnet);
	List<PrintableBingoCard> getCards();
	void savePDF(String filename, BingoCardParameters parameters) throws IOException;
}
