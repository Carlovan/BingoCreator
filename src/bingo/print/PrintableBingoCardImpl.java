package bingo.print;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.util.Matrix;

import bingo.BingoCard;

public class PrintableBingoCardImpl implements PrintableBingoCard {
	private final BingoCard card;
	private int printNumber = 0;
	
	
	public PrintableBingoCardImpl(final BingoCard card) {
		this.card = card;
	}
	
	@Override
	public void addPDF(final PDPageContentStream stream, final Map<FontType, PDFont> fonts, final PDRectangle size) throws IOException {
		final float matrixSize = PrintableBingoCard.MATRIX_WIDTH * size.getWidth();
		
		stream.beginText();
		stream.setFont(fonts.get(FontType.REGULAR), 14);
		stream.newLineAtOffset(0, size.getHeight() - 15);
		stream.showText("MATRICE");
		stream.endText();
		
		stream.transform(Matrix.getTranslateInstance(matrixSize, 0));
		stream.beginText();
		stream.setFont(fonts.get(FontType.REGULAR), 14);
		stream.newLineAtOffset(0, size.getHeight() - 15);
		stream.setLeading(14.5f);
		stream.showText("Cartella n. " + this.getID() + "  ");
		stream.showText("Bollettario n. " + this.getCarnetID() + "  ");
		stream.newLine();
		stream.showText(this.getValues().toString());
		stream.endText();
		stream.transform(Matrix.getTranslateInstance(-matrixSize, 0));
	}

	@Override
	public int getValuesCount() {
		return this.card.getValuesCount();
	}

	@Override
	public List<Integer> getValues() {
		return this.card.getValues();
	}

	@Override
	public int getID() {
		return this.card.getID();
	}

	@Override
	public int getCarnetID() {
		return this.card.getCarnetID();
	}

	@Override
	public int getPrintNumber() {
		return this.printNumber;
	}
	
	public void setPrintNumber(final int val) {
		this.printNumber = val;
	}

}
