package org.dotplot.tokenizer.filter.stemmingFilter;

import java.util.ArrayList;
import java.util.List;

public class StaticStemmer extends Stemmer {
    private List<StaticStemmerWord> wordList = new ArrayList<StaticStemmerWord>();

    public StaticStemmer(StemmerLanguage sl) {
	super(sl);
	buildWordList();
    }

    public StaticStemmer(String text) {
	super(text);
	buildWordList();
    }

    public StaticStemmer(String text, StemmerLanguage sl) {
	super(text, sl);
	buildWordList();
    }

    private void buildWordList() {
	// TODO:
	wordList.add(new StaticStemmerWord("in", "aus"));
	wordList.add(new StaticStemmerWord("einem", "ein"));
	wordList.add(new StaticStemmerWord("spieler", "spiel"));
    }

    @Override
    public String stemWord(String s) {
	for (StaticStemmerWord ssw : wordList) {
	    if (ssw.getNeedle().equals(s)) {
		return ssw.getWord();
	    }
	}

	return s;
    }
}
