package bingocreator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class JPlaceholder extends JLabel {
	private static final long serialVersionUID = 3222067926395246516L;

	public JPlaceholder(final String s, final float fontSize) {
		// HTML to make text wrap
		super("<html><div style='text-align: center;'>" + s + "</div></html>", SwingConstants.CENTER);

		// final Border line = BorderFactory.createDashedBorder(Color.BLACK, 20, 10);
		final Border padding = BorderFactory.createEmptyBorder(3, 10, 3, 10);
		// final Border margin = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		// this.setBorder(BorderFactory.createCompoundBorder(margin,
		// BorderFactory.createCompoundBorder(line, padding)));
		this.setBorder(padding);

		this.setFont(this.getFont().deriveFont(fontSize));
	}

	public JPlaceholder() {
		this("", 10);
	}
}
