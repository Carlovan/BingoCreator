package bingocreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import bingo.text.BingoCardParameters;
import bingo.text.StyledText;
import bingo.text.Texts;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1464069750731803934L;
	private final GUILogics logics = new GUILogicsImpl();

	private final String smallLogoName = "bobby.jpg"; // TODO settable
	private final String bigLogoName = "bobby.jpg"; // TODO settable
	private final String stemmaName = "stemma.jpg"; // TODO settable

	JButton buttonCreate;
	JLabel labelCards, labelCardsInCarnet;
	JTextField textCards, textCardsInCarnet;
	BingoCardsTableModel cardsTableModel;
	List<StyledTextField> textMatrixSubtitle = new ArrayList<>();
	List<StyledTextField> textMatrixFooter = new ArrayList<>();
	List<StyledTextField> textAmount = new ArrayList<>();
	List<StyledTextField> textMiddle = new ArrayList<>();
	List<StyledTextField> textAuthorizations = new ArrayList<>();

	private BingoCardParameters getParameters() {
		return new BingoCardParameters() {
			private List<StyledText> getText(final List<StyledTextField> fields) {
				return fields.stream().map(StyledTextField::getStyledText).collect(Collectors.toList());
			}

			public List<StyledText> getMatrixSubtitle() {
				return this.getText(textMatrixSubtitle);
			}

			public List<StyledText> getMatrixFooter() {
				return this.getText(textMatrixFooter);
			}

			public List<StyledText> getAmount() {
				return this.getText(textAmount);
			}

			public List<StyledText> getMiddle() {
				return this.getText(textMiddle);
			}

			public List<StyledText> getAuthorizations() {
				return this.getText(textAuthorizations);
			}

			public String getSmallLogoName() {
				return smallLogoName;
			}

			public String getBigLogoName() {
				return bigLogoName;
			}

			public String getStemmaName() {
				return stemmaName;
			}
		};
	}

	// ACTION HANDLERS
	private void buttonCreateClick(ActionEvent event) {
		int cardsCount;
		int cardsInCarnet;

		try {
			cardsCount = Integer.parseInt(this.textCards.getText());
			cardsInCarnet = Integer.parseInt(this.textCardsInCarnet.getText());
			if (cardsCount % cardsInCarnet != 0) {
				JOptionPane.showMessageDialog(null, String.format("L'ultimo carnet avrà solo %d cartelle invece che %d",
						cardsCount % cardsInCarnet, cardsInCarnet), "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
			this.logics.generate(cardsCount, cardsInCarnet);
			this.cardsTableModel.setData(this.logics.getCards());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "I valori inseriti non sono validi", "Errore",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void buttonSaveClick(ActionEvent event) {
		try {
			final JFileChooser fileChooser = new JFileChooser();
			final FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF", "pdf");
			fileChooser.addChoosableFileFilter(filter);
			fileChooser.setAcceptAllFileFilterUsed(false);
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				final File file = fileChooser.getSelectedFile();
				String filename = file.getAbsolutePath();
				if (!filename.endsWith(".pdf")) {
					filename += ".pdf";
				}
				this.logics.savePDF(filename, this.getParameters());
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Impossibile salvare il file", "Errore",
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
		tablePane.setPreferredSize(new Dimension(tablePane.getPreferredSize().width, 200));
		return tablePane;
	}

	private JComponent createTitle() {
		final JPanel container = new JPanel(new GridBagLayout());
		final GridBagConstraints constraint = new GridBagConstraints();

		constraint.weightx = 1;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.gridx = 0;
		constraint.anchor = GridBagConstraints.LINE_START;
		for (int i = 0; i < Texts.getTitle().size(); i++) {
			final StyledText line = Texts.getTitle().get(i);
			constraint.gridy = i;
			container.add(new JPlaceholder(line), constraint);
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
		container.setPreferredSize(new Dimension(250, container.getPreferredSize().height));
		container.setMinimumSize(new Dimension(250, 1));
		final GridBagConstraints constraint = new GridBagConstraints();
		final Border blackSolidBorder = BorderFactory.createLineBorder(Color.BLACK);

		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.anchor = GridBagConstraints.FIRST_LINE_START;
		constraint.weightx = 1;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.insets = new Insets(0, 10, 0, 0);

		container.add(this.createTitle(), constraint);

		tmpSTF = new StyledTextField("Tombola di €", 7); // TODO settable
		this.textMatrixSubtitle.add(tmpSTF);
		constraint.gridy++;
		container.add(tmpSTF, constraint);

		tmpSTF = new StyledTextField("Data", 7); // TODO settable
		this.textMatrixSubtitle.add(tmpSTF);
		constraint.gridy++;
		container.add(tmpSTF, constraint);

		tmpLbl = new JPlaceholder("Cartella N°  ###", 10);
		tmpLbl.setBorder(blackSolidBorder);
		constraint.gridy++;
		constraint.insets.top = 10;
		constraint.ipadx = 20;
		constraint.ipady = 15;
		container.add(tmpLbl, constraint);

		tmpLbl = new JPlaceholder("Bollettario N°  ###", 10);
		tmpLbl.setBorder(blackSolidBorder);
		constraint.gridy++;
		container.add(tmpLbl, constraint);

		constraint.gridy++;
		constraint.insets.bottom = 10;
		container.add(this.createNumPlaceholder(), constraint);

		constraint.insets.top = 0;
		constraint.insets.bottom = 0;
		constraint.ipadx = 0;
		constraint.ipady = 0;
		for (final String s : new String[] { "AVIS", "", "", "" }) { // TODO settable
			tmpSTF = new StyledTextField(s, 8);
			this.textMatrixFooter.add(tmpSTF);
			constraint.gridy++;
			container.add(tmpSTF, constraint);
		}

		constraint.gridy++;
		constraint.weighty = 1;
		container.add(new JLabel(), constraint); // Filler

		return container;
	}

	private JComponent createCardSettings() {
		final JPanel container = new JPanel();
		StyledTextField tmpSTF;
		container.setLayout(new GridBagLayout());
		container.setPreferredSize(new Dimension(800, container.getPreferredSize().height));
		container.setMinimumSize(new Dimension(1000, 1));
		final GridBagConstraints constraint = new GridBagConstraints();

		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.anchor = GridBagConstraints.FIRST_LINE_START;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.insets = new Insets(10, 10, 10, 10);

		// Title
		constraint.gridwidth = 4;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		container.add(this.createTitle(), constraint);


		// Logo small
		constraint.gridx = 4;
		constraint.gridwidth = 1;
		constraint.fill = GridBagConstraints.BOTH;
		try {
			final JImage img = new JImage(this.smallLogoName);
			container.add(img, constraint);
		} catch (IOException e) {
			System.err.println(e.toString());
		}

		// Logo big
		constraint.gridx = 0;
		constraint.gridy++;
		constraint.gridwidth = 5;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		try {
			final JImage img = new JImage(this.bigLogoName);
			img.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			img.setPreferredSize(new Dimension(1, 200));
			img.setMinimumSize(new Dimension(1, 200));
			container.add(img, constraint);
		} catch (IOException e) {
			System.err.println(e.toString());
		}

		constraint.gridy++;
		constraint.gridx = 0;

		// Amount box
		constraint.fill = GridBagConstraints.BOTH;
		constraint.gridwidth = 1;
		final JPanel amountBox = new JPanel(new GridLayout(2, 1));
		amountBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		tmpSTF = new StyledTextField("CINQUINA FILATA €", 11); // TODO settable
		this.textAmount.add(tmpSTF);
		amountBox.add(tmpSTF);
		tmpSTF = new StyledTextField("TOMBOLA €", 11); // TODO settable
		this.textAmount.add(tmpSTF);
		amountBox.add(tmpSTF);
		container.add(amountBox, constraint);

		// Middle
		final JPanel middlePanel = new JPanel(new GridLayout(4, 1));
		tmpSTF = new StyledTextField("ESTRAZIONE DI DOMENICA", 15); // TODO settable
		this.textMiddle.add(tmpSTF);
		middlePanel.add(tmpSTF);
		tmpSTF = new StyledTextField("ore 20,00 in Piazza Garibaldi", 12); // TODO settable
		this.textMiddle.add(tmpSTF);
		middlePanel.add(tmpSTF);
		tmpSTF = new StyledTextField("in caso di maltempo l'estrazione rimarrà legata", 7); // TODO settable
		this.textMiddle.add(tmpSTF);
		middlePanel.add(tmpSTF);
		tmpSTF = new StyledTextField("alla serata conclusiva dei festeggiamenti", 7); // TODO settable
		this.textMiddle.add(tmpSTF);
		middlePanel.add(tmpSTF);
		constraint.gridx = 1;
		constraint.gridwidth = 4;
		container.add(middlePanel, constraint);

		constraint.gridy++;
		constraint.gridx = 0;

		// Numbers
		constraint.gridwidth = 1;
		constraint.fill = GridBagConstraints.BOTH;
		constraint.anchor = GridBagConstraints.CENTER;
		container.add(this.createNumPlaceholder(), constraint);

		// Stemma
		constraint.gridx = 1;
		constraint.gridwidth = 1;
		constraint.fill = GridBagConstraints.NONE;
		try {
			final JImage img = new JImage(this.stemmaName);
			img.setPreferredSize(new Dimension(img.getImage().getWidth(), img.getImage().getHeight()));
			img.setMinimumSize(
					new Dimension((int) (img.getImage().getWidth() * 0.7), (int) (img.getImage().getHeight() * 0.7)));
			container.add(img, constraint);
		} catch (IOException e) {
			System.err.println(e.toString());
		}

		// Authorizations
		constraint.gridx = 2;
		constraint.gridwidth = 3;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		final JPanel authBox = new JPanel(new GridLayout(4, 1));
		authBox.add(new JPlaceholder(Texts.getAuthorizationTitle()));
		tmpSTF = new StyledTextField("Sindaco", 8); // TODO settable
		this.textAuthorizations.add(tmpSTF);
		authBox.add(tmpSTF);
		tmpSTF = new StyledTextField("Prefetto", 8); // TODO settable
		this.textAuthorizations.add(tmpSTF);
		authBox.add(tmpSTF);
		tmpSTF = new StyledTextField("Prezzo € 1,00", 8); // TODO settable
		this.textAuthorizations.add(tmpSTF);
		authBox.add(tmpSTF);
		container.add(authBox, constraint);
		constraint.fill = GridBagConstraints.NONE;

		constraint.gridy++;
		constraint.gridx = 0;

		// IDs
		constraint.gridwidth = 1;
		constraint.ipadx = constraint.ipady = 10;
		final JLabel labelID = new JLabel("Cartella N° ###");
		labelID.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		container.add(labelID, constraint);
		constraint.gridx = 2;
		final JLabel labelCarnetID = new JLabel("Bollettario N° ###");
		labelCarnetID.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		container.add(labelCarnetID, constraint);

		// Footer
		constraint.gridy++;
		constraint.gridx = 0;
		constraint.gridwidth = 5;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		final JPanel footerPane = new JPanel();
		footerPane.setLayout(new BoxLayout(footerPane, BoxLayout.Y_AXIS));
		for (final StyledText t : Texts.getFooter()) {
			footerPane.add(new JPlaceholder(t));
		}
		container.add(footerPane, constraint);

		return container;
	}

	/**
	 * Builds the GUI
	 */
	public GUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(new JPanel(new GridBagLayout()));
		final GridBagConstraints constraint = new GridBagConstraints();
		constraint.weightx = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.anchor = GridBagConstraints.LINE_START;

		final JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
		north.add(this.createCountBar());
		north.add(this.createCardsTable());
		north.setMinimumSize(new Dimension(north.getPreferredSize().width, 200));

		constraint.fill = GridBagConstraints.BOTH;
		constraint.gridwidth = 2;
		constraint.weighty = 0;
		this.getContentPane().add(north, constraint);

		constraint.gridwidth = 1;
		constraint.gridy++;
		constraint.weightx = 0.4;
		constraint.weighty = 1;
		constraint.fill = GridBagConstraints.VERTICAL;
		this.getContentPane().add(this.createMatrixSettings(), constraint);

		constraint.gridx = 1;
		constraint.weightx = 1;
		this.getContentPane().add(this.createCardSettings(), constraint);

		constraint.gridy++;
		constraint.gridx = 0;
		constraint.gridwidth = 2;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weighty = 0;
		final JButton buttonSave = new JButton("Save");
		buttonSave.addActionListener(this::buttonSaveClick);
		this.getContentPane().add(buttonSave, constraint);


		this.setVisible(true);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
}
