package bingocreator;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bingo.text.StyledText;
import bingo.text.StyledTextImpl;

public class StyledTextField extends JPanel {
	private static final long serialVersionUID = 5781981048445267271L;
	private final JTextField text;
	private final JComboBox<Integer> size;

	public StyledTextField(final String text, final int size) {
		super();
		this.text = new JTextField(10);
		this.size = new JComboBox<>();
		for (int i = 5; i < 30; i++) {
			this.size.addItem(i);
		}

		this.text.setPreferredSize(new Dimension(this.getPreferredSize().width, 25));
		this.setLayout(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.weightx = 1;
		this.add(this.text, c);
		c.gridx = 1;
		c.weightx = 0;
		this.add(this.size, c);

		this.setText(text);
		this.setFontSize(size);
	}

	public StyledText getStyledText() {
		return new StyledTextImpl(this.text.getText(), (int) this.size.getSelectedItem());
	}

	public void setText(final String val) {
		this.text.setText(val);
	}

	public void setFontSize(final int val) {
		this.size.setSelectedItem(val);
	}
}
