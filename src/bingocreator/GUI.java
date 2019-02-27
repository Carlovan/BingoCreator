package bingocreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
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
import javax.swing.border.Border;

import bingo.text.StyledText;
import bingo.text.Texts;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1464069750731803934L;
	private final GUILogics logics = new GUILogicsImpl();

	JButton buttonCreate;
	JLabel labelCards, labelCardsInCarnet;
	JTextField textCards, textCardsInCarnet;
	List<StyledTextField> textMatrixInfo = new ArrayList<>();
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

	private JComponent createTitle() {
		final JPanel container = new JPanel(new GridBagLayout());
		final GridBagConstraints constraint = new GridBagConstraints();

		constraint.gridx = 0;
		constraint.anchor = GridBagConstraints.LINE_START;
		for (int i = 0; i < Texts.getTitle().size(); i++) {
			final StyledText line = Texts.getTitle().get(i);
			constraint.gridy = i;
			container.add(new JPlaceholder(line.getText(), line.getFontSize()), constraint);
		}
		return container;
	}

	private JComponent createNumPlaceholder() {
		final JPanel container = new JPanel(new GridLayout(2, 5));
		for (int i = 0; i < 10; i++) {
			final JLabel tmpLbl = new JPlaceholder("NUM", 9);
			tmpLbl.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
					BorderFactory.createEmptyBorder(4, 4, 4, 4)));
			tmpLbl.setPreferredSize(new Dimension(tmpLbl.getPreferredSize().width, tmpLbl.getPreferredSize().width));
			container.add(tmpLbl);
		}
		return container;
	}

	private JComponent createMatrixSettings() {
		StyledTextField tmpSTF;
		JLabel tmpLbl;
		final JPanel container = new JPanel(new GridBagLayout());
		final GridBagConstraints constraint = new GridBagConstraints();
		final Border blackSolidBorder = BorderFactory.createLineBorder(Color.BLACK);

		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.anchor = GridBagConstraints.PAGE_START;

		container.add(this.createTitle(), constraint);

		tmpSTF = new StyledTextField("Tombola di €", 6);
		this.textMatrixInfo.add(tmpSTF);
		constraint.gridy++;
		container.add(tmpSTF, constraint);

		tmpSTF = new StyledTextField("Data", 6);
		this.textMatrixInfo.add(tmpSTF);
		constraint.gridy++;
		container.add(tmpSTF, constraint);

		tmpLbl = new JPlaceholder("Cartella N°  ###", 10);
		tmpLbl.setBorder(blackSolidBorder);
		constraint.gridy++;
		constraint.insets = new Insets(10, 0, 0, 0);
		constraint.ipadx = 20;
		constraint.ipady = 15;
		container.add(tmpLbl, constraint);

		tmpLbl = new JPlaceholder("Bollettario N°  ###", 10);
		tmpLbl.setBorder(blackSolidBorder);
		constraint.gridy++;
		container.add(tmpLbl, constraint);

		constraint.gridy++;
		container.add(this.createNumPlaceholder(), constraint);

		tmpSTF = new StyledTextField("AVIS", 6);
		this.textMatrixInfo.add(tmpSTF);
		constraint.gridy++;
		constraint.ipadx = 0;
		constraint.ipady = 0;
		container.add(tmpSTF, constraint);

		tmpSTF = new StyledTextField("asd", 6);
		this.textMatrixInfo.add(tmpSTF);
		constraint.gridy++;
		constraint.insets = new Insets(0, 0, 0, 0);
		constraint.weighty = 1;
		container.add(tmpSTF, constraint);

		return container;
	}

	private JComponent createCardSettings() {
		final JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		final GridBagConstraints constraint = new GridBagConstraints();

		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.gridwidth = 2;
		constraint.anchor = GridBagConstraints.FIRST_LINE_START;
		container.add(this.createTitle());

		return container;
	}

	/**
	 * Builds the GUI
	 */
	public GUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		final JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
		north.add(this.createCountBar());
		north.add(this.createCardsTable());

		this.getContentPane().add(north, BorderLayout.NORTH);
		this.getContentPane().add(this.createMatrixSettings(), BorderLayout.WEST);
		this.getContentPane().add(this.createCardSettings(), BorderLayout.CENTER);

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
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}

}
