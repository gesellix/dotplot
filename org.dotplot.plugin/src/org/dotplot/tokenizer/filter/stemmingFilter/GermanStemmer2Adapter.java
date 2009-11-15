package org.dotplot.tokenizer.filter.stemmingFilter;

public class GermanStemmer2Adapter extends Stemmer {
    private GermanStemmer2 germanStemmer2 = new GermanStemmer2();

    public GermanStemmer2Adapter(StemmerLanguage sl) {
	super(sl);
    }

    public GermanStemmer2Adapter(String text) {
	super(text);
    }

    public GermanStemmer2Adapter(String text, StemmerLanguage sl)
	    throws Exception {
	super(text, sl);
	if (sl != StemmerLanguage.DE) {
	    throw new Exception("Unsupported");
	}
    }

    @Override
    public String stemWord(String s) {
	String neu = germanStemmer2.stemWord(s);
	return neu;
    }

}
