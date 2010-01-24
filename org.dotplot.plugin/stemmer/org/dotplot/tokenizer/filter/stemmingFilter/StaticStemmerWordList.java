package org.dotplot.tokenizer.filter.stemmingFilter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StaticStemmerWordList {
	private List<StaticStemmerWord> sswl = new ArrayList<StaticStemmerWord>();

	public List<StaticStemmerWord> getSswl() {
		return sswl;
	}

	public void setSswl(List<StaticStemmerWord> sswl) {
		this.sswl = sswl;
	}
}
