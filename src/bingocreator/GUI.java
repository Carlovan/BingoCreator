package bingocreator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1464069750731803934L;
	private final GUILogics logics = new GUILogicsImpl();

	JButton buttonCreate;
	JLabel labelCards, labelCardsInCarnet;
	JTextField textCards, textCardsInCarnet;
	BingoCardsTableModel cardsTableModel;

	// ACTION HANDLERS
	private void buttonCreateClick(ActionEvent event) {
		int cardsCount;
		int cardsInCarnet;

		try {
			cardsCount = Integer.parseInt(this.textCards.getText());
			cardsInCarnet = Integer.parseInt(this.textCardsInCarnet.getText());
			this.logics.generate(cardsCount, cardsInCarnet);
			this.cardsTableModel.setData(this.logics.getCards());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "I valori inseriti non sono validi", "Errore",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// BUILD METHODS

	/**
	 * Creates a bar that contains inputs to choose cards and carnet counts
	 *
	 * @return A component that contains inputs to choose cards and carnet counts
	 */
	private JComponent createCountBar() {
		final JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));

		this.labelCards = new JLabel("Numero schede:");
		this.textCards = new JTextField(3); // TODO set max size
		this.labelCards.setLabelFor(this.textCards);

		this.labelCardsInCarnet = new JLabel("Schede in un carnet:");
		this.textCardsInCarnet = new JTextField(3); // TODO set max size
		this.labelCardsInCarnet.setLabelFor(this.textCardsInCarnet);

		this.buttonCreate = new JButton("Genera cartelle");
		this.buttonCreate.addActionListener(this::buttonCreateClick);

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
		cardsTableModel = new BingoCardsTableModel();

		final JTable cardsTable = new JTable(cardsTableModel);
		final JScrollPane tablePane = new JScrollPane(cardsTable);
		return tablePane;
	}

	private JComponent createMatrixSettings() {
		final JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		container.add(new JPlaceholder("CITTÀ DI FORLIMPOPOLI"));
		container.add(new JPlaceholder("In collaborazione con l'ente flkloristico e culturale forlimpopolese"));
		container.add(new JPlaceholder("Festeggiamenti"));
		container.add(new JPlaceholder("SEGAVECCHIA"));

		return container;
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
		this.getContentPane().add(this.createMatrixSettings(), BorderLayout.CENTER);

		final JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener((e) -> {
			try {
				this.logics.savePDF("test.pdf");
			} catch (IOException e1) {
				System.err.println("Error saving file"); // TODO dialog
			}
		});
		this.getContentPane().add(buttonSave, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}

}
