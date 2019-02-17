package bingocreator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import bingo.BingoCardsFactory;
import bingo.BingoCardsFactoryImpl;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1464069750731803934L;

	/**
	 * Creates a bar that contains inputs to choose cards and carnet counts
	 *
	 * @return A component that contains inputs to choose cards and carnet counts
	 */
	private JComponent createCountBar() {
		final JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));

		final JLabel labelCards = new JLabel("Numero schede:");
		final JTextField textCards = new JTextField(3); // TODO set max size
		labelCards.setLabelFor(textCards);

		final JLabel labelCardsInCarnet = new JLabel("Schede in un carnet:");
		final JTextField textCardsInCarnet = new JTextField(3); // TODO set max size
		labelCardsInCarnet.setLabelFor(textCardsInCarnet);

		final JButton buttonCreate = new JButton("Genera cartelle");

		container.add(labelCards);
		container.add(textCards);
		container.add(labelCardsInCarnet);
		container.add(textCardsInCarnet);
		container.add(buttonCreate);

		return container;
	}

	/**
	 * Creates the data table to show the cards
	 *
	 * @return A component suitable to show cards' data
	 */
	private JComponent createCardsTable() {
		// TODO this is only a test
		final BingoCardsFactory fact = new BingoCardsFactoryImpl();
		fact.setCardsCount(10);
		fact.setCardsInCarnet(5);
		final TableModel cardsModel = new BingoCardsTableModel(fact.generateCards());

		final JTable cardsTable = new JTable(cardsModel);
		final JScrollPane tablePane = new JScrollPane(cardsTable);
		return tablePane;
	}

	/**
	 * Builds the GUI
	 */
	public GUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		final JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
		north.add(this.createCountBar(), BorderLayout.NORTH);
		north.add(this.createCardsTable(), BorderLayout.CENTER);
		this.getContentPane().add(north, BorderLayout.NORTH);

		this.setVisible(true);
	}

}
