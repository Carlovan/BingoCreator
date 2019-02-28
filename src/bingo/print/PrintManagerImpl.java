package bingo.print;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.util.Matrix;

import bingo.BingoCard;
import bingo.BingoCardsFactory;
import bingo.text.BingoCardParameters;
import bingo.text.StyledText;

public class PrintManagerImpl implements PrintManager {
	private List<PrintableBingoCard> cards = new ArrayList<>();
	private final int cardsInCarnet;
	private BingoCardParameters parameters;

	public PrintManagerImpl(final int cardsInCarnet) {
		this(cardsInCarnet, new BingoCardParameters() {
			public List<StyledText> getMatrixSubtitle() {
				return List.of();
			}

			public List<StyledText> getMatrixFooter() {
				return List.of();
			}

			public List<StyledText> getAmount() {
				return List.of();
			}

			public List<StyledText> getMiddle() {
				return List.of();
			}

			public List<StyledText> getAuthorizations() {
				return List.of();
			}

			public String getSmallLogoName() {
				return "";
			}

			public String getBigLogoName() {
				return "";
			}

			public String getStemmaName() {
				return "";
			}
		});
	}

	public PrintManagerImpl(final int cardsInCarnet, final BingoCardParameters parameters) {
		this.cardsInCarnet = cardsInCarnet;
		this.parameters = parameters;
	}

	@Override
	public PDDocument getPDF() throws IOException {
		final PDDocument doc = new PDDocument();
		final Map<Integer, PDPage> pages = new HashMap<>();

		final Map<FontType, PDFont> fonts = new HashMap<>();
		fonts.put(FontType.REGULAR, PDType0Font.load(doc, new File(PrintManager.FONT_NAME + "-Regular.ttf")));
		fonts.put(FontType.BOLD, PDType0Font.load(doc, new File(PrintManager.FONT_NAME + "-Bold.ttf")));

		final float halfPageHeight = PrintManager.PAGE_SIZE.getHeight() / 2.0f;

		final PDRectangle cardSize = new PDRectangle(PrintManager.PAGE_SIZE.getWidth(), halfPageHeight);

		for(final PrintableBingoCard card : this.cards) {
			pages.putIfAbsent(card.getPrintNumber(), new PDPage(PrintManager.PAGE_SIZE));
			final PDPageContentStream content = new PDPageContentStream(doc, pages.get(card.getPrintNumber()), PDPageContentStream.AppendMode.APPEND, false);

			final boolean shouldTranslate = (card.getID() - BingoCardsFactory.DEFAULT_ID_START) % this.cardsInCarnet < this.cardsInCarnet / 2;
			if(shouldTranslate) {
				content.transform(Matrix.getTranslateInstance(0.0f, halfPageHeight));
			}
			card.addPDF(doc, content, fonts, cardSize, this.parameters);
			if(shouldTranslate) {
				content.transform(Matrix.getTranslateInstance(0.0f, -halfPageHeight));
			}
			content.close();
		}

		pages.entrySet().stream().sorted((a,b) -> Integer.compare(a.getKey(), b.getKey())).forEach(p -> doc.addPage(p.getValue()));
		return doc;
	}

	@Override
	public void setCards(List<BingoCard> cards) {
		this.cards = cards.stream().map(PrintableBingoCardImpl::new)
				.peek(c -> c.setPrintNumber((c.getID() - BingoCardsFactory.DEFAULT_ID_START) % (this.cardsInCarnet / 2) + (c.getCarnetID() - BingoCardsFactory.DEFAULT_ID_START)*(this.cardsInCarnet / 2) + 1))
				.collect(Collectors.toList());
	}

	@Override
	public List<PrintableBingoCard> getPrintableCards() {
		return this.cards;
	}

	@Override
	public void setParameters(BingoCardParameters parameters) {
		this.parameters = parameters;
	}
}
