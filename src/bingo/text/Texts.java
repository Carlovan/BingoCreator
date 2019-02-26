package bingo.text;

import java.util.Arrays;
import java.util.List;

public class Texts {
	private static final StyledText[] TITLE = new StyledText[] {
			new StyledTextImpl("CITTÀ DI FORLIMPOPOLI", 15),
			new StyledTextImpl("in collaborazione con l'Ente Folkloristico e Culturale Forlimpopolese", 11),
			new StyledTextImpl("Festeggiamenti", 15), new StyledTextImpl("SEGAVECCHIA", 15) };

	public static List<StyledText> getTitle() {
		return Arrays.asList(Texts.TITLE);
	}
}
