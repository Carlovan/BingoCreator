package bingo.print;

import java.io.IOException;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import bingo.BingoCard;
import bingo.text.BingoCardParameters;

public interface PrintableBingoCard extends BingoCard {
	final float MATRIX_WIDTH = 0.25f;
	final float LEFT_MARGIN = 50;

	int getPrintNumber();

	void addPDF(PDDocument document, PDPageContentStream stream, Map<FontType, PDFont> fonts, PDRectangle size,
			BingoCardParameters parameters) throws IOException;
}
