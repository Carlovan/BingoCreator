package bingo;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BingoCardImpl implements BingoCard {
	public final static int DEFAULT_MAX_VALUE = 90;
	public final static int DEFAULT_MIN_VALUE = 1;
	private final int ID;
	private final int carnetID;
	private final List<Integer> values;

	BingoCardImpl(final int ID, final int carnetID, final int valuesCount) {
		this.ID = ID;
		this.carnetID = carnetID;

		final Random rand = new Random();
		this.values = Stream.generate(() -> rand.nextInt(DEFAULT_MAX_VALUE) + DEFAULT_MIN_VALUE)
				.distinct()
				.limit(valuesCount)
				.sorted()
				.collect(Collectors.toList());
	}

	@Override
	public int getValuesCount() {
		return this.values.size();
	}

	@Override
	public List<Integer> getValues() {
		return Collections.unmodifiableList(this.values);
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public int getCarnetID() {
		return this.carnetID;
	}

}
