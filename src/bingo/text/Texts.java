package bingo.text;

public class Texts {
	private static final StyledText[] TITLE = new StyledText[] {
			new StyledTextImpl("CITTÀ DI FORLIMPOPOLI", 15),
			new StyledTextImpl("in collaborazione con l'Ente Folkloristico e Culturale Forlimpopolese", 11),
			new StyledTextImpl("Festeggiamenti", 15), new StyledTextImpl("SEGAVECCHIA", 15) };

	public static StyledText[] getTitle() {
		return Texts.TITLE;
	}
}
