package bingo.text;

import java.util.List;

public interface BingoCardParameters {
	List<StyledText> getMatrixSubtitle();

	List<StyledText> getMatrixFooter();

	List<StyledText> getAmount();

	List<StyledText> getMiddle();

	List<StyledText> getAuthorizations();
	
	List<StyledText> getPrices();

	String getSmallLogoName();

	String getBigLogoName();

	String getStemmaName();
	
	String getTitleLogoName();
}
