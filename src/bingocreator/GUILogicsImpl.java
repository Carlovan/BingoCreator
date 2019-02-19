package bingocreator;

import java.util.List;

import bingo.BingoCard;
import bingo.BingoCardsFactory;
import bingo.BingoCardsFactoryImpl;

public class GUILogicsImpl implements GUILogics {

	@Override
	public List<BingoCard> generate(int cardsCount, int cardsInCarnet) {
		final BingoCardsFactory fact = new BingoCardsFactoryImpl();
		fact.setCardsCount(cardsCount);
		fact.setCardsInCarnet(cardsInCarnet);
		return fact.generateCards();
	}

}
