package bingo.text;

public class StyledTextImpl implements StyledText {
	private final String text;
	private final float size;

	public StyledTextImpl(final String text, final float size) {
		this.text = text;
		this.size = size;
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public float getFontSize() {
		return this.size;
	}

}
