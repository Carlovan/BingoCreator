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
		final List<BingoCardImpl> cards = Stream.generate(() -> new BingoCardImpl(0, 0, this.valuesCount)).distinct()
				.limit(this.cardsCount)
				.collect(Collectors.toList());
		for (int i = 0; i < cards.size(); i++) {
			cards.get(i).setID(DEFAULT_ID_START + i);
			cards.get(i).setCarnetID((i / this.cardsInCarnet) + DEFAULT_ID_START);
		}
		return cards.stream().map(x -> (BingoCard) x).collect(Collectors.toList());
	}

}
