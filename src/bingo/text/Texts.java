package bingo.text;

import java.util.Arrays;
import java.util.List;

public class Texts {
	private static final StyledText[] TITLE = new StyledText[] {
			new StyledTextImpl("CITTÀ DI\nFORLIMPOPOLI", 15),
			new StyledTextImpl("in collaborazione con\nl'Ente Folkloristico e\nCulturale Forlimpopolese", 8),
			new StyledTextImpl("Festeggiamenti", 15), new StyledTextImpl("SEGAVECCHIA", 15) };

	private static final StyledText FOOTER = new StyledTextImpl(
			"L'utile della manifestazione verrà investito per le attività forlimpopolesi di\n"
					+ "AVIS COMUNALE DI FORLIMPOPOLI",
			10);

	public static List<StyledText> getTitle() {
		return Arrays.asList(Texts.TITLE);
	}

	public static List<StyledText> getFooter() {
		return List.of(Texts.FOOTER);
	}
}
