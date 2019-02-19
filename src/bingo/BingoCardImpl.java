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

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("BingoCard[");
		b.append(this.getID());
		b.append(", ");
		b.append(this.getCarnetID());
		b.append(", ");
		b.append(this.getValues().toString());
		return b.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 7;
		hash = prime * hash + this.ID;
		hash = prime * hash + this.carnetID;
		hash = prime * hash + this.values.hashCode();
		return hash;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (!this.getClass().equals(o.getClass())) {
			return false;
		}
		final BingoCardImpl other = (BingoCardImpl) o;
		if (this.ID != other.ID || this.carnetID != other.carnetID || !this.values.equals(other.values)) {
			return false;
		}
		return true;
	}

}
