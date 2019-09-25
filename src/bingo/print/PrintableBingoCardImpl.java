package bingo.print;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import bingo.BingoCard;
import bingo.text.BingoCardParameters;
import bingo.text.StyledText;
import bingo.text.Texts;

public class PrintableBingoCardImpl implements PrintableBingoCard {
	private final BingoCard card;
	private int printNumber = 0;

	public PrintableBingoCardImpl(final BingoCard card) {
		this.card = card;
	}

	@Override
	public void addPDF(final PDDocument document, final PDPageContentStream stream, final Map<FontType, PDFont> fonts,
			final PDRectangle size, final BingoCardParameters parameters) throws IOException {
		final float matrixSize = PrintableBingoCard.MATRIX_WIDTH * (size.getWidth() - PrintableBingoCard.LEFT_MARGIN);
		final PDFont titleFont = fonts.get(FontType.BOLD);
		final int topMargin = 15;

		stream.moveTo(0,0);
		stream.lineTo(size.getWidth(), 0);
		stream.closeAndStroke();

		stream.transform(Matrix.getTranslateInstance(PrintableBingoCard.LEFT_MARGIN, 0));

		// MATRIX
		// Title
		final PDImageXObject titleLogo = PDImageXObject.createFromFile(parameters.getTitleLogoName(), document);
		final float titleLogoH = 20;
		final float titleLogoW = titleLogo.getImage().getWidth() * titleLogoH / titleLogo.getImage().getHeight();
		final float titleLogoY = size.getHeight() - topMargin - titleLogoH;
		stream.drawImage(titleLogo, 0, titleLogoY, titleLogoW, titleLogoH);
		boolean first = true;
		stream.beginText();
		stream.newLineAtOffset(0, titleLogoY);
		for(final StyledText line : Texts.getTitle()) {
			stream.setLeading(line.getFontSize() + 5);
			stream.newLine();
			first = true;
			stream.setLeading(line.getFontSize());
			for(final String s : line.getText().split("\\n")) {
				if(!first) stream.newLine();
				stream.setFont(titleFont, line.getFontSize() - 1);
				stream.showText(s);
				first = false;
			}
			first = false;
		}
		for (final StyledText t : parameters.getMatrixSubtitle()) {
			stream.newLine();
			stream.setFont(titleFont, t.getFontSize());
			stream.showText(t.getText());
		}
		stream.endText();

		// Boxes
		final int mid = 200;
		final float boxW = 100;
		final float boxH = 30;
		float boxX = 0;
		final float boxMarginH = 5;
		final float boxMarginV = 10;
		stream.addRect(boxX, mid, boxW, boxH);
		stream.addRect(boxX, mid - boxH - boxMarginV, boxW, boxH);
		stream.closeAndStroke();
		stream.beginText();
		stream.setFont(fonts.get(FontType.REGULAR), 10);
		stream.newLineAtOffset(boxX + boxMarginH, mid + boxMarginV);
		stream.showText("Cartella N° " + String.format("%04d", this.getID()));
		stream.newLineAtOffset(0, -(boxH + boxMarginV));
		stream.showText("Bollettario N° " + String.format("%04d", this.getCarnetID()));
		stream.endText();

		// Numbers
		final int cols = 5;
		final int rows = 2;
		float squareSize = 20;
		float squarePaddingH = 4;
		float squarePadingV = 6;
		float bottom = 100;
		float left = 0;
		for(int c = 0; c < cols; c++) {
			for(int r = 0; r < rows; r++) {
				stream.addRect(left + c*squareSize, bottom + r*squareSize, squareSize, squareSize);
			}
		}
		stream.closeAndStroke();
		stream.beginText();
		stream.setFont(fonts.get(FontType.BOLD), 10);
		stream.newLineAtOffset(left + squarePaddingH, bottom + squareSize + squarePadingV);
		for(int c = 0; c < cols; c++) {
			for(int r = 0; r < rows; r++) {
				stream.showText(String.format("%02d", this.getValues().get(c*rows + r)));
				stream.newLineAtOffset(0, -squareSize);
			}
			stream.newLineAtOffset(squareSize, 2*squareSize);
		}
		stream.endText();

		// Footer
		stream.beginText();
		stream.newLineAtOffset(0, 40);
		stream.setLeading(10);
		for (final StyledText t : parameters.getMatrixFooter()) {
			stream.setFont(titleFont, t.getFontSize());
			stream.showText(t.getText());
			stream.newLine();
		}
		stream.endText();

		// CARD
		stream.transform(Matrix.getTranslateInstance(matrixSize, 0));

		// Title
		stream.drawImage(titleLogo, 0, titleLogoY, titleLogoW, titleLogoH);
		stream.beginText();
		stream.newLineAtOffset(titleLogoW + 3, titleLogoY + 3);
		stream.setFont(titleFont, 14);
		stream.showText("COMUNALE DI FORLIMPOPOLI");
		stream.endText();
		stream.beginText();
		stream.newLineAtOffset(0, titleLogoY);
		for(final StyledText line : Texts.getTitle()) {
			stream.setLeading(line.getFontSize() + 5);
			stream.newLine();
			stream.setFont(titleFont, line.getFontSize());
			stream.showText(line.getText().replace('\n', ' '));
		}
		stream.endText();


		// Images
		final float smallLogoW = 80;
		final float smallLogoH = smallLogoW; 
		final float bigLogoW = 400;
		final float bigLogoH = 120;
		final float smallLogoX = bigLogoW - smallLogoW;
		final float smallLogoY = size.getHeight() - topMargin - smallLogoH;
		final float bigLogoBottom = 200;
		final float stemmaSize = smallLogoH / 3;
		final PDImageXObject smallLogo = PDImageXObject.createFromFile(parameters.getSmallLogoName(), document);
		final PDImageXObject bigLogo = PDImageXObject.createFromFile(parameters.getBigLogoName(), document);
		final PDImageXObject stemma = PDImageXObject.createFromFile(parameters.getStemmaName(), document);
		stream.drawImage(smallLogo, smallLogoX, smallLogoY, smallLogoW, smallLogoH);
		stream.drawImage(bigLogo, 0, bigLogoBottom, bigLogoW, bigLogoH);
		stream.drawImage(stemma, smallLogoX - stemmaSize - 2, smallLogoY, stemmaSize, stemmaSize);
		stream.addRect(0, bigLogoBottom, bigLogoW, bigLogoH);
		stream.closeAndStroke();

		// Middle
		final float amountBoxH = 50;
		final float amountBoxW = 155;
		final float amountBoxMarginT = 5;
		final float amountBoxY = bigLogoBottom - amountBoxMarginT - amountBoxH; 
		stream.addRect(0, amountBoxY, amountBoxW, amountBoxH);
		stream.closeAndStroke();
		stream.beginText();
		stream.setLeading(20);
		stream.newLineAtOffset(10, bigLogoBottom - amountBoxMarginT - 20);
		for (final StyledText t : parameters.getAmount()) {
			stream.setFont(fonts.get(FontType.REGULAR), t.getFontSize());
			stream.showText(t.getText());
			stream.newLine();
		}
		stream.endText();

		stream.beginText();
		stream.newLineAtOffset(amountBoxW + 8, bigLogoBottom - 25);
		stream.setLeading(16);
		for (final StyledText t : parameters.getMiddle()) {
			stream.setFont(titleFont, t.getFontSize());
			stream.showText(t.getText());
			stream.setLeading(t.getFontSize() - 1);
			stream.newLine();
		}
		stream.endText();

		// Numbers
		final float numbersMarginT = 5;
		squareSize = 27;
		squarePaddingH = 3;
		squarePadingV = 7;
		bottom = amountBoxY - numbersMarginT - rows*squareSize;
		left = 0;
		for(int c = 0; c < cols; c++) {
			for(int r = 0; r < rows; r++) {
				stream.addRect(left + c*squareSize, bottom + r*squareSize, squareSize, squareSize);
			}
		}
		stream.closeAndStroke();
		stream.beginText();
		stream.setFont(fonts.get(FontType.BOLD), 19);
		stream.newLineAtOffset(left + squarePaddingH, bottom + squareSize + squarePadingV);
		for(int c = 0; c < cols; c++) {
			for(int r = 0; r < rows; r++) {
				stream.showText(String.format("%02d", this.getValues().get(c*rows + r)));
				stream.newLineAtOffset(0, -squareSize);
			}
			stream.newLineAtOffset(squareSize, 2*squareSize);
		}
		stream.endText();

		// Prices
		stream.beginText();
		stream.newLineAtOffset(cols*squareSize + left + 10, bottom + rows * squareSize);
		for(final StyledText t : parameters.getPrices()) {
			stream.setFont(fonts.get(FontType.REGULAR), t.getFontSize());
			stream.setLeading(t.getFontSize() + 1);
			stream.newLine();
			stream.showText(t.getText());
		}
		stream.endText();

		// Boxes
		boxX = 0;
		final float boxMarginR = 5;
		final float boxMarginT = 5;
		final float boxY = bottom - boxMarginT - boxH;
		stream.addRect(boxX, boxY, boxW, boxH);
		stream.addRect(boxX + boxW + boxMarginR, boxY, boxW, boxH);
		stream.closeAndStroke();
		stream.beginText();
		stream.setFont(fonts.get(FontType.REGULAR), 10);
		stream.newLineAtOffset(boxX + boxMarginH, boxY + boxMarginV);
		stream.showText("Cartella N° " + String.format("%04d", this.getID()));
		stream.newLineAtOffset(boxW + boxMarginR, 0);
		stream.showText("Bollettario N° " + String.format("%04d", this.getCarnetID()));
		stream.endText();
		
		// Authorizations
		final float authRectW = 195;
		final float authRectH = 45; 
		final float authRectX = boxX + 2*(boxW + boxMarginR);
		final float authRectY = bottom - boxMarginT - authRectH;
		stream.addRect(authRectX, authRectY, authRectW, authRectH);
		stream.closeAndStroke();
		stream.beginText();
		stream.newLineAtOffset(authRectX + 5, authRectY + 4);
		final StyledText authTitle = Texts.getAuthorizationTitle();
		stream.setLeading(-(authTitle.getFontSize()));
		final List<StyledText> authorizations = parameters.getAuthorizations();
		// Printed backwards
		for (int i = authorizations.size() - 1; i >= 0; i--) {
			final StyledText t = authorizations.get(i);
			stream.setFont(fonts.get(FontType.BOLD), t.getFontSize());
			stream.showText(t.getText());
			stream.newLine();
		}
		stream.setFont(fonts.get(FontType.REGULAR), authTitle.getFontSize());
		stream.showText(authTitle.getText());
		stream.endText();

		// Footer
		stream.beginText();
		stream.newLineAtOffset(5, 20);
		stream.setLeading(15);
		for (final StyledText t : Texts.getFooter()) {
			stream.setFont(fonts.get(FontType.BOLD), t.getFontSize());
			stream.showText(t.getText());
			stream.newLine();
		}
		stream.endText();

		stream.transform(Matrix.getTranslateInstance(-(PrintableBingoCard.LEFT_MARGIN + matrixSize), 0));
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
