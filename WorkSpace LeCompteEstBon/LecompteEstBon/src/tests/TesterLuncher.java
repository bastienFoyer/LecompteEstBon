package tests;

import model.Etape;

public class TesterLuncher {

	public void main (String[] args) {
		EtapeTest etapeTest = new EtapeTest();
		GereScoreTest gereScoreTest = new GereScoreTest();
		ModelJEUTest modelJeuTest = new ModelJEUTest();
		ScoreTest scoreTest = new ScoreTest();
		
		gereScoreTest.test();
	}
}
