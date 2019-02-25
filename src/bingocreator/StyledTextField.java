package bingocreator;

import java.awt.Dimension;
import java.awt.FlowLayout;

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
		this.text = new JTextField(10);
		this.size = new JComboBox<>();
		for (int i = 5; i < 30; i++) {
			this.size.addItem(i);
		}

		this.text.setPreferredSize(new Dimension(this.getPreferredSize().width, 25));
		this.setLayout(new FlowLayout());
		this.add(this.text);
		this.add(this.size);

		this.setText(text);
		this.setSize(size);
	}

	public StyledText getStyledText() {
		return new StyledTextImpl(this.text.getText(), (int) this.size.getSelectedItem());
	}

	public void setText(final String val) {
		this.text.setText(val);
	}

	public void setSize(final int val) {
		this.size.setSelectedItem(val);
	}
}
