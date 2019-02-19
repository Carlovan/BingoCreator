package bingocreator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import bingo.BingoCard;

/**
 * A data model to show Bingo Cards
 */
public class BingoCardsTableModel implements TableModel {
	private final String[] colNames = new String[] { "Stampa", "Cartella", "Carnet" };
	private List<BingoCard> cards;
	private final Set<TableModelListener> listeners = new HashSet<>();

	public BingoCardsTableModel(final List<BingoCard> cards) {
		this.setData(cards);
	}

	public BingoCardsTableModel() {
		this(new ArrayList<BingoCard>());
	}

	public void setData(final List<BingoCard> cards) {
		this.cards = cards;

		final TableModelEvent eventRows = new TableModelEvent(this);
		final TableModelEvent eventCols = new TableModelEvent(this, TableModelEvent.HEADER_ROW);
		for (final TableModelListener l : this.listeners) {
			l.tableChanged(eventCols);
			l.tableChanged(eventRows);
		}
	}

	@Override
	public int getRowCount() {
		return this.cards.size();
	}

	@Override
	public int getColumnCount() {
		int count = this.colNames.length;
		if (this.cards.size() > 0) {
			count += this.cards.get(0).getValuesCount();
		}
		return count;
	}

	@Override
	public String getColumnName(final int col) {
		if (col < this.colNames.length) {
			return this.colNames[col];
		}
		return "Num:" + (col - this.colNames.length + 1);
	}

	@Override
	public void addTableModelListener(final TableModelListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public Object getValueAt(final int row, final int col) {
		final BingoCard current = this.cards.get(row);
		if (col == 0) {
			return 1;
		} else if (col == 1) {
			return current.getID();
		} else if (col == 2) {
			return current.getCarnetID();
		} else {
			return current.getValues().get(col - this.colNames.length);
		}
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		return false;
	}

	@Override
	public void removeTableModelListener(final TableModelListener listener) {
		this.listeners.remove(listener);
	}

	@Override
	public void setValueAt(Object val, int row, int col) {
		// Edit is not possible
	}

	@Override
	public Class<?> getColumnClass(final int col) {
		return Integer.class;
	}
}
