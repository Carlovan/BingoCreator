package bingo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BingoCardsFactoryImpl implements BingoCardsFactory {
	private int cardsCount = 0;
	private int cardsInCarnet = DEFAULT_CARDS_IN_CARNET;
	private int valuesCount = DEFAULT_VALUES_COUNT;

	@Override
	public void setCardsCount(final int count) {
		this.cardsCount = count;
	}

	@Override
	public void setCardsInCarnet(final int count) {
		this.cardsInCarnet = count;
	}

	@Override
	public void setValuesCount(final int count) {
		this.valuesCount = count;
	}

	@Override
	public List<BingoCard> generateCards() {
		return Stream.iterate(DEFAULT_ID_START, id -> id + 1).limit(this.cardsCount)
				.map(id -> new BingoCardImpl(id, (id - DEFAULT_ID_START) / this.cardsInCarnet + DEFAULT_ID_START,
						this.valuesCount))
				.collect(Collectors.toList());
	}

}
