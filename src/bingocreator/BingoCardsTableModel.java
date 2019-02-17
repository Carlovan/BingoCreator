package bingocreator;

import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import bingo.BingoCard;

/**
 * A data model to show Bingo Cards
 */
public class BingoCardsTableModel implements TableModel {
	private final String[] colNames = new String[] { "Stampa", "Cartella", "Carnet" };
	private final List<BingoCard> cards;

	public BingoCardsTableModel(final List<BingoCard> cards) {
		this.cards = cards;
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
		return "Num" + (col - this.colNames.length);
	}

	@Override
	public void addTableModelListener(final TableModelListener listener) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTableModelListener(final TableModelListener listener) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setValueAt(Object val, int row, int col) {
		// TODO Auto-generated method stub
	}

	@Override
	public Class<?> getColumnClass(final int col) {
		if (col < this.colNames.length) {
			return String.class;
		}
		return Integer.class;
	}
}
