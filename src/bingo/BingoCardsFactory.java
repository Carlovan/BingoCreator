package bingo;

import java.util.List;

public interface BingoCardsFactory {
	final static int DEFAULT_VALUES_COUNT = 10;
	final static int DEFAULT_CARDS_IN_CARNET = 1;
	final static int DEFAULT_ID_START = 1;
	/**
	 * Sets the number of cards to generate
	 *
	 * @param count: number of cards
	 */
	void setCardsCount(int count);

	/**
	 * Sets the number of cards in a single carnet
	 *
	 * @param count: number of cards in a carnet
	 */
	void setCardsInCarnet(int count);

	/**
	 * Sets the number of values in each card
	 *
	 * @param count: number of values
	 */
	void setValuesCount(int count);

	/**
	 * Generate the cards using the specified parameters
	 *
	 * @return the generated cards
	 */
	List<BingoCard> generateCards();
}
