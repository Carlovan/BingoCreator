package bingocreator;

import java.io.IOException;
import java.util.List;

import bingo.print.PrintableBingoCard;

public interface GUILogics {
	List<PrintableBingoCard> generate(int cardsCount, int cardsInCarnet);
	List<PrintableBingoCard> getCards();
	void savePDF(String filename) throws IOException;
}
