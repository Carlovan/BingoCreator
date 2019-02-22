package bingocreator;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class JPlaceholder extends JLabel {
	public JPlaceholder(final String s) {
		super(s);

		final Border line = BorderFactory.createDashedBorder(Color.BLACK, 20, 10);
		final Border padding = BorderFactory.createEmptyBorder(6, 10, 6, 10);
		final Border margin = BorderFactory.createEmptyBorder(5, 0, 5, 0);
		this.setBorder(BorderFactory.createCompoundBorder(margin, BorderFactory.createCompoundBorder(line, padding)));
	}

	public JPlaceholder() {
		this("");
	}
}
