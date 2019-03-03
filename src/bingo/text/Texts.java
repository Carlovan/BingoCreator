package bingo.text;

import java.util.Arrays;
import java.util.List;

public class Texts {
	private static final StyledText[] TITLE = new StyledText[] {
			new StyledTextImpl("in collaborazione con\nl'Ente Folkloristico e\nCulturale Forlimpopolese", 8),
			new StyledTextImpl("col patrocinio del\ncomune di Forlimpopoli", 9f),
			new StyledTextImpl("TOMBOLA DELLA\nSEGAVECCHIA", 14f) };

	private static final StyledText[] FOOTER = new StyledText[] {
			new StyledTextImpl("L'utile della manifestazione verrà investito per le attività forlimpopolesi di", 10),
			new StyledTextImpl("AVIS COMUNALE DI FORLIMPOPOLI", 10) };

	private static final StyledText AUTH_TITLE = new StyledTextImpl("Comunicazioni", 10);

	public static List<StyledText> getTitle() {
		return Arrays.asList(Texts.TITLE);
	}

	public static List<StyledText> getFooter() {
		return Arrays.asList(Texts.FOOTER);
	}

	public static StyledText getAuthorizationTitle() {
		return Texts.AUTH_TITLE;
	}
}
