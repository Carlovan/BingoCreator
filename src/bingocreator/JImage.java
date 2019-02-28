package bingocreator;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class JImage extends JLabel {
	private static final long serialVersionUID = 6698633937558097971L;
	private BufferedImage image;

	public JImage(final BufferedImage img) {
		super();
		this.image = img;
	}

	public JImage(final String filename) throws IOException {
		this(ImageIO.read(new File(filename)));
	}

	public BufferedImage getImage() {
		return this.image;
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		Rectangle innerArea = new Rectangle();
		SwingUtilities.calculateInnerArea(this, innerArea);

		g.drawImage(image, innerArea.x, innerArea.y, innerArea.width, innerArea.height, this);
	}
}
