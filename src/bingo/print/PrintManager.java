package bingo.print;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import bingo.BingoCard;
import bingo.text.BingoCardParameters;

public interface PrintManager {
	final String FONT_NAME = "OpenSans";
	final PDRectangle PAGE_SIZE = PDRectangle.A4;

	PDDocument getPDF() throws IOException;
	void setCards(final List<BingoCard> cards);
	List<PrintableBingoCard> getPrintableCards();
	void setParameters(BingoCardParameters parameters);
}
