package bingo;

import java.util.List;

public interface BingoCard {
	/**
	 * @return the number of values in this card
	 */
	int getValuesCount();

	/**
	 * @return the list of values in this card
	 */
	List<Integer> getValues();

	/**
	 * @return this card's ID
	 */
	int getID();

	/**
	 * @return the ID of the carnet this card belongs to
	 */
	int getCarnetID();

}
