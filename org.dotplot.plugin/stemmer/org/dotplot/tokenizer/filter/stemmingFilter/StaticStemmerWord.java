package org.dotplot.tokenizer.filter.stemmingFilter;

import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorOrder
public class StaticStemmerWord {
    @XmlElement(name = "needle")
    private String needle;
    @XmlElement(name = "word")
    private String word;
    @XmlElement(name = "language")
    private String language;

    public StaticStemmerWord() {
	super();
    }

    public StaticStemmerWord(String needle, String word) {
	super();
	this.setNeedle(needle);
	this.setWord(word);
    }

    public StaticStemmerWord(String needle, String word, String language) {
	super();
	this.setNeedle(needle);
	this.setWord(word);
	this.setLanguage(language);
    }

    public String getLanguage() {
	return language;
    }

    public String getNeedle() {
	return needle;
    }

    public String getWord() {
	return word;
    }

    public void setLanguage(String language) {
	this.language = language;
    }

    public void setNeedle(String needle) {
	this.needle = needle;
    }

    public void setWord(String word) {
	this.word = word;
    }
}
