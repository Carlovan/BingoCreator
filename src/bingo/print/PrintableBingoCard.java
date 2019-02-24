package bingo.print;

import java.io.IOException;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import bingo.BingoCard;

public interface PrintableBingoCard extends BingoCard {
	final float MATRIX_WIDTH = 0.4f;
	
	void addPDF(final PDPageContentStream stream, final Map<FontType, PDFont> fonts, final PDRectangle size) throws IOException;
	int getPrintNumber();
}
