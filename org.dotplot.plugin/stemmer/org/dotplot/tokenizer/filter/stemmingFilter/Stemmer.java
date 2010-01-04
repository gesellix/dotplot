package org.dotplot.tokenizer.filter.stemmingFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public abstract class Stemmer {
    public static String reduceToLowerWords(String s) {
	return s.toLowerCase().replaceAll("\n", " ").replaceAll("[^a-zäöüß ]",
		"");
    }

    public static String runChain(String text, Iterator<Stemmer> it) {
	if (!it.hasNext()) {
	    return text;
	}

	Stemmer stemmer = it.next();
	stemmer.setText(text);

	return Stemmer.runChain(stemmer.stem(), it);
    }

    protected String text;

    protected StemmerLanguage sl;

    public Stemmer(StemmerLanguage sl) {
	super();
	this.sl = sl;
    }

    public Stemmer(String text) {
	super();
	this.sl = StemmerLanguage.DE;
	this.text = text.trim().toLowerCase();
    }

    public Stemmer(String text, StemmerLanguage sl) {
	super();
	this.sl = sl;
	this.text = text.trim().toLowerCase();
    }

    public String getText() {
	return this.text;
    }

    public void setText(String text) {
	this.text = text.trim().toLowerCase();
    }

    public String stem() {
	List<StaticStemmerWord> wordList = new ArrayList<StaticStemmerWord>();
	StringTokenizer st = new StringTokenizer(reduceToLowerWords(this.text));
	String s = this.text.toLowerCase();
	String old, neu;

	while (st.hasMoreTokens()) {
	    old = st.nextToken();
	    neu = this.stemWord(old);

	    if (!old.equals(neu)) {
		if (!stringInList(wordList, old)) {
		    wordList.add(new StaticStemmerWord(old, neu));
		    // System.out.println(old + "->" + neu);
		}
	    }
	}

	for (StaticStemmerWord ss : wordList) {
	    s = s.replace(ss.getNeedle(), ss.getWord());
	}

	return s;
    }

    public abstract String stemWord(String s);

    public boolean stringInList(List<StaticStemmerWord> list, String s) {
	for (StaticStemmerWord ss : list) {
	    if (ss.getNeedle().equals(s)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public String toString() {
	return this.text;
    }
}