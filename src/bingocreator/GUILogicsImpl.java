package bingocreator;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import bingo.BingoCardsFactory;
import bingo.BingoCardsFactoryImpl;
import bingo.print.PrintManager;
import bingo.print.PrintManagerImpl;
import bingo.print.PrintableBingoCard;
import bingo.text.BingoCardParameters;

public class GUILogicsImpl implements GUILogics {
	PrintManager cards = new PrintManagerImpl(1);


	@Override
	public List<PrintableBingoCard> generate(int cardsCount, int cardsInCarnet) {
		this.cards = new PrintManagerImpl(cardsInCarnet);
		final BingoCardsFactory fact = new BingoCardsFactoryImpl();
		fact.setCardsCount(cardsCount);
		fact.setCardsInCarnet(cardsInCarnet);
		this.cards.setCards(fact.generateCards());
		return this.getCards();
	}

	@Override
	public List<PrintableBingoCard> getCards() {
		return this.cards.getPrintableCards();
	}

	@Override
	public void savePDF(final String filename, final BingoCardParameters parameters) throws IOException {
		this.cards.setParameters(parameters);
		final PDDocument doc = this.cards.getPDF();
		doc.save(filename);
		doc.close();
	}

}
