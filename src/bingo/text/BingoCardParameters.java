package bingo.text;

import java.util.List;

public interface BingoCardParameters {
	List<StyledText> getMatrixSubtitle();

	List<StyledText> getMatrixFooter();

	List<StyledText> getAmount();

	List<StyledText> getMiddle();

	List<StyledText> getAuthorizations();

	String getSmallLogoName();

	String getBigLogoName();

	String getStemmaName();
}
