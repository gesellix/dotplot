package org.dotplot.tokenizer.filter.stemmingFilter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Main {
    static public void main(String[] unused) throws Exception {
	Stemmer stemmer = new PorterStemmer(stringOfFile("maja.txt"));
	System.out.println(stemmer.stem());
	// xmlGen();
    }

    static public String stringOfFile(String filename) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(
		new FileInputStream(filename)));
	StringBuffer contentOfFile = new StringBuffer();
	String line;
	while ((line = br.readLine()) != null) {
	    contentOfFile.append(line + "\n");
	}
	return contentOfFile.toString();
    }

    static public void xmlGen() throws Exception {
	StaticStemmerWordList sswl = new StaticStemmerWordList();
	List<StaticStemmerWord> list = sswl.getSswl();
	list.add(new StaticStemmerWord("ich", "du", "de"));
	list.add(new StaticStemmerWord("mir", "dir", "de"));
	list.add(new StaticStemmerWord("uns", "mein", "de"));

	JAXBContext context = JAXBContext
		.newInstance(StaticStemmerWordList.class);
	Marshaller m = context.createMarshaller();
	m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	m.marshal(sswl, System.out);
    }
}
