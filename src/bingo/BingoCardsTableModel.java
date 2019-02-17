package bingo;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * A data model to show Bingo Cards
 */
public class BingoCardsTableModel implements TableModel {
	private final String[] colNames = new String[] {
		"Stampa", "Cartella", "Carnet", "Num1", "Num2", "Num3", "Num4", "Num5", "Num6", "Num7", "Num8", "Num9", "Num10" // TODO
	};
	// TODO add cards list

	@Override
	public int getRowCount() {
		// TODO The number of cards
		return 0;
	}

	@Override
	public int getColumnCount() {
		return this.colNames.length;
	}

	@Override
	public String getColumnName(final int col) {
		return this.colNames[col];
	}

	@Override
	public void addTableModelListener(final TableModelListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<?> getColumnClass(final int col) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValueAt(final int row, final int col) {
		// TODO Auto-generated method stub
		return null;
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
}
