package tests;

import model.GereScore;
import model.Score;

public class GereScoreTest {

	public boolean  test() {
		GereScore gereScore = new GereScore();
		boolean test1 = ajoutScoreTest(gereScore);
		boolean test2 =  displayScoresTest(gereScore);
		boolean test3 =  extractListeScoresTest(gereScore);

		return test1 && test2 && test3;
//		gereScore.writeFile();
//		gereScore.writeFileHTML();
//		gereScore.readFile();
	}
	
	public static boolean ajoutScoreTest(GereScore gereScore) {
		Score scoreTest = new Score("A", 1, 1);

		gereScore.ajoutScore("A", 1, 1);
		if(gereScore.getListScore().contains(scoreTest)) return true;
		return false;
	}
	
	public static boolean displayScoresTest(GereScore gereScore) {
		gereScore.displayScores();
	}
	
	public static boolean extractListeScoresTest(GereScore gereScore) {
		gereScore.extractListeScores();
	}
}
