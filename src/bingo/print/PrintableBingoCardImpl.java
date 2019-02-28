package bingo.print;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import bingo.BingoCard;
import bingo.text.StyledText;
import bingo.text.Texts;

public class PrintableBingoCardImpl implements PrintableBingoCard {
	private final BingoCard card;
	private int printNumber = 0;
	
	final private String logoSmallName = "bobby.jpg";
	final private String logoBigName = "bobby.jpg";
	
	public PrintableBingoCardImpl(final BingoCard card) {
		this.card = card;
	}
	
	@Override
	public void addPDF(final PDDocument document, final PDPageContentStream stream, final Map<FontType, PDFont> fonts, final PDRectangle size) throws IOException {
		final float matrixSize = PrintableBingoCard.MATRIX_WIDTH * (size.getWidth() - PrintableBingoCard.LEFT_MARGIN);
		final float cardSize = size.getWidth() - matrixSize - PrintableBingoCard.LEFT_MARGIN;
		final PDFont titleFont = fonts.get(FontType.BOLD);
		final int topMargin = 15;
		
		stream.moveTo(0,0);
		stream.lineTo(size.getWidth(), 0);
		stream.closeAndStroke();
		
		stream.transform(Matrix.getTranslateInstance(PrintableBingoCard.LEFT_MARGIN, 0));
		
		// MATRIX
		stream.beginText();
		
		stream.newLineAtOffset(0, size.getHeight() - topMargin);
		
		// Title
		boolean first = true;
		for(final StyledText line : Texts.getTitle()) {
			stream.setLeading(line.getFontSize() + 5);
			stream.newLine();
			first = true;
			stream.setLeading(line.getFontSize());
			for(final String s : line.getText().split("\\n")) {
				if(!first) stream.newLine();
				stream.setFont(titleFont, line.getFontSize());
				stream.showText(s);
				first = false;
			}
			first = false;
		}
		stream.newLine();
		stream.setFont(titleFont, 7);
		stream.showText("Tombola di € 1.250,00");
		stream.newLine();
		stream.showText("Data xx/xx/xxxx");
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
		float squareMarginH = 4;
		float squareMarginV = 6;
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
		stream.newLineAtOffset(left + squareMarginH, bottom + squareSize + squareMarginV);
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
		stream.setFont(titleFont, 10);
		stream.setLeading(10);
		stream.showText("AVIS");
		stream.newLine();
		stream.showText("COMUNALE");
		stream.newLine();
		stream.showText("DI FORLIMPOPOLI");
		stream.endText();
		
		// CARD
		stream.transform(Matrix.getTranslateInstance(matrixSize, 0));
		
		// Title
		stream.beginText();
		stream.newLineAtOffset(0, size.getHeight() - topMargin);
		for(final StyledText line : Texts.getTitle()) {
			stream.setLeading(line.getFontSize() + 5);
			stream.newLine();
			stream.setFont(titleFont, line.getFontSize());
			stream.showText(line.getText().replace('\n', ' '));
		}
		stream.endText();
		
		
		// Images
		final float logoW = 80;
		final float logoH = logoW;
		final float logo2W = cardSize - 10;
		final float logo2H = 120;
		final float logo2Bottom = 200;
		final PDImageXObject logo = PDImageXObject.createFromFile(this.logoSmallName, document);
		final PDImageXObject logo2 = PDImageXObject.createFromFile(this.logoBigName, document);
		stream.drawImage(logo, cardSize - logoW - topMargin, size.getHeight() - topMargin - logoH, logoW, logoH);
		stream.drawImage(logo2, 0, logo2Bottom, logo2W, logo2H);
		stream.addRect(0, logo2Bottom, logo2W, logo2H);
		stream.closeAndStroke();
		
		// Middle
		final float amountBoxH = 50;
		final float amountBoxW = 155;
		stream.addRect(0, logo2Bottom - 10 - amountBoxH, amountBoxW, amountBoxH);
		stream.closeAndStroke();
		stream.beginText();
		stream.setFont(fonts.get(FontType.REGULAR), 12);
		stream.setLeading(20);
		stream.newLineAtOffset(10, logo2Bottom - 30);
		stream.showText("Cinquina Filata €250,00");
		stream.newLine();
		stream.showText("Tombola €1.000,00");
		stream.endText();
		
		stream.beginText();
		stream.newLineAtOffset(amountBoxW + 8, logo2Bottom - 25);
		stream.setFont(titleFont, 15);
		stream.setLeading(16);
		stream.showText("ESTRAZIONE DI DOMENICA");
		stream.newLine();
		stream.setFont(titleFont, 12);
		stream.showText("ore 20,00 in Piazza Garibaldi");
		stream.setLeading(10);
		stream.newLine();
		stream.setFont(titleFont, 7);
		stream.showText("in caso di maltempo l'estrazione rimarrà legata");
		stream.setLeading(7);
		stream.newLine();
		stream.showText("alla serata conclusiva dei festeggiamenti");
		stream.endText();
		
		// Numbers
		squareSize = 27;
		squareMarginH = 3;
		squareMarginV = 7;
		bottom = 75;
		left = 0;
		for(int c = 0; c < cols; c++) {
			for(int r = 0; r < rows; r++) {
				stream.addRect(left + c*squareSize, bottom + r*squareSize, squareSize, squareSize);
			}
		}
		stream.closeAndStroke();
		stream.beginText();
		stream.setFont(fonts.get(FontType.BOLD), 19);
		stream.newLineAtOffset(left + squareMarginH, bottom + squareSize + squareMarginV);
		for(int c = 0; c < cols; c++) {
			for(int r = 0; r < rows; r++) {
				stream.showText(String.format("%02d", this.getValues().get(c*rows + r)));
				stream.newLineAtOffset(0, -squareSize);
			}
			stream.newLineAtOffset(squareSize, 2*squareSize);
		}
		stream.endText();
		
		// Stemma
		final float stemmaSize = squareSize*rows;
		final float stemmaLeft = cols*squareSize + left + 10;
		final PDImageXObject stemma = PDImageXObject.createFromFile("stemma.jpg", document);
		stream.drawImage(stemma, stemmaLeft, bottom, stemmaSize, stemmaSize);
		
		// Authorizations
		final float authRectW = 195;
		final float authRectX = stemmaLeft + stemmaSize + 10;
		stream.addRect(authRectX, bottom, authRectW, stemmaSize);
		stream.closeAndStroke();
		stream.beginText();
		stream.newLineAtOffset(authRectX + 5, bottom + stemmaSize - 14);
		stream.setFont(fonts.get(FontType.REGULAR), 10);
		stream.setLeading(11);
		stream.showText("Autorizzazioni");
		stream.newLine();
		stream.setFont(fonts.get(FontType.BOLD), 8.2f);
		stream.showText("Comunicazione al Sindaco in data xx/xx/xxxx");
		stream.newLine();
		stream.showText("Comunicazione al prefetto in data xx/xx/xxxx");
		stream.newLine();
		stream.showText("Prezzo cartella €1,00");
		stream.endText();
		
		// Boxes
//		final float boxW = 100;
//		final float boxH = 30;
		boxX = 0;
		final float boxY = bottom - 10 - boxH;
//		final float boxMarginH = 5;
//		final float boxMarginV = 10;
		stream.addRect(boxX, boxY, boxW, boxH);
		stream.addRect(boxX + boxW + 15, boxY, boxW, boxH);
		stream.closeAndStroke();
		stream.beginText();
		stream.setFont(fonts.get(FontType.REGULAR), 10);
		stream.newLineAtOffset(boxX + boxMarginH, boxY + boxMarginV);
		stream.showText("Cartella N° " + String.format("%04d", this.getID()));
		stream.newLineAtOffset(boxW + 15, 0);
		stream.showText("Bollettario N° " + String.format("%04d", this.getCarnetID()));
		stream.endText();
		
		// Footer
		stream.beginText();
		stream.newLineAtOffset(5, 20);
		stream.setLeading(15);
		stream.setFont(fonts.get(FontType.BOLD), 10.5f);
		stream.showText("L'utile della manifestazione verrà investito per le attività forlimpopolesi di");
		stream.newLine();
		stream.newLineAtOffset(100, 0);
		stream.showText("AVIS COMUNALE DI FORLIMPOPOLI");
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
