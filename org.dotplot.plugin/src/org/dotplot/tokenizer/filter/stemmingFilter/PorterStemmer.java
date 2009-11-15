/**
 * Eine Java-Implementierung des Porter-Stemmers, entstanden im Rahmen des DopPlot-Projekts (http://www.dotplot.org)
 * @author: Nils Braden
 * 
 * -- Beginn ursprünglicher Kommentar --
 * Eine Pythonimplementation des Porter-Stemmers für Deutsch (Orginal unter http://snowball.tartarus.org/texts/germanic.html)
 * Ersteller dieser Version: (c) by kristall 'ät' c-base.org       http://kristall.crew.c-base.org/porter_de.py
 * Der Algorithmus in (englischem) Prosa unter http://snowball.tartarus.org/algorithms/german/stemmer.html
 * Wikipedia zum Porter-Stemmer: http://de.wikipedia.org/wiki/Porter-Stemmer-Algorithmus
 * Lizenz: Diese Software steht unter der BSD License (siehe http://www.opensource.org/licenses/bsd-license.html).
 * Ursprünglicher Autor: (c) by Dr. Martin Porter
 * -- Ende ursprünglicher Kommentar --
 */

package org.dotplot.tokenizer.filter.stemmingFilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PorterStemmer extends Stemmer {

    public static void main(String[] args) {
	try {
	    FileWriter fw = new FileWriter("output.txt");
	    BufferedReader in = new BufferedReader(new FileReader(
		    "sampleVocab.txt"));
	    String zeile = null;
	    while ((zeile = in.readLine()) != null) {
		fw.write(new PorterStemmer(zeile).toString() + "\n");
	    }
	    fw.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public PorterStemmer(String text) {
	super(text);
    }

    public PorterStemmer(String text, StemmerLanguage sl) {
	super(text, sl);
    }

    /**
     * Helper class: Returns true if the array containts the given character,
     * else false.
     * 
     * @param array
     * @param character
     * @return true if char is in array
     */
    private Boolean contains(char[] array, char character) {
	for (char c : array) {
	    if (c == character) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Helper class: Returns true if the array containts the given String, else
     * false.
     * 
     * @param array
     * @param text
     * @return true if String is in array
     */
    private Boolean contains(String[] array, String text) {
	for (String s : array) {
	    if (s.equals(text)) {
		return true;
	    }
	}
	return false;
    }

    /**
     * endStemming replaces 'ä', 'ö', 'ü' by the 'Grundvokal' and switches U and
     * Y to lowercase.
     */
    private String endStemming(String text) {
	text = text.replace('ä', 'a');
	text = text.replace('ö', 'o');
	text = text.replace('ü', 'u');
	text = text.replace('U', 'u');
	text = text.replace('Y', 'y');
	return text;
    }

    /**
     * Stems a given word and returns the result as String
     * 
     * @param text
     *            - the given word
     * @return String - the stemmed word
     */
    @Override
    public String stemWord(String text) {
	char[] vokale = { 'a', 'e', 'i', 'o', 'u', 'y', 'ä', 'ö', 'ü' };
	char[] konsonanten = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
		'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z', 'ß',
		'U', 'Y' };

	String r1 = "";
	Integer p1 = 0;
	String r2 = "";
	Integer p2 = 0;

	Boolean finished = false;
	/*
	 * Words in this list are not stemmed if 'useStoplist = true'.
	 */
	Boolean useStoplist = false;
	String[] stoplist = { "aber", "alle", "allem", "allen", "aller",
		"alles", "als", "also", "am", "an", "ander", "andere",
		"anderem", "anderen", "anderer", "anderes", "anderm", "andern",
		"anders", "auch", "auf", "aus", "bei", "bin", "bis", "bist",
		"da", "damit", "dann", "der", "den", "des", "dem", "die",
		"das", "dass", "daß", "derselbe", "derselben", "denselben",
		"desselben", "demselben", "dieselbe", "dieselben", "dasselbe",
		"dazu", "dein", "deine", "deinem", "deinen", "deiner",
		"deines", "denn", "derer", "dessen", "dich", "dir", "du",
		"dies", "diese", "diesem", "diesen", "dieser", "dieses",
		"doch", "dort", "durch", "ein", "eine", "einem", "einen",
		"einer", "eines", "einig", "einige", "einigem", "einigen",
		"einiger", "einiges", "einmal", "er", "ihn", "ihm", "es",
		"etwas", "euer", "eure", "eurem", "euren", "eurer", "eures",
		"für", "gegen", "gewesen", "hab", "habe", "haben", "hat",
		"hatte", "hatten", "hier", "hin", "hinter", "ich", "mich",
		"mir", "ihr", "ihre", "ihrem", "ihren", "ihrer", "ihres",
		"euch", "im", "in", "indem", "ins", "ist", "jede", "jedem",
		"jeden", "jeder", "jedes", "jene", "jenem", "jenen", "jener",
		"jenes", "jetzt", "kann", "kein", "keine", "keinem", "keinen",
		"keiner", "keines", "können", "könnte", "machen", "man",
		"manche", "manchem", "manchen", "mancher", "manches", "mein",
		"meine", "meinem", "meinen", "meiner", "meines", "mit", "muss",
		"musste", "muß", "mußte", "nach", "nicht", "nichts", "noch",
		"nun", "nur", "ob", "oder", "ohne", "sehr", "sein", "seine",
		"seinem", "seinen", "seiner", "seines", "selbst", "sich",
		"sie", "ihnen", "sind", "so", "solche", "solchem", "solchen",
		"solcher", "solches", "soll", "sollte", "sondern", "sonst",
		"über", "um", "und", "uns", "unse", "unsem", "unsen", "unser",
		"unses", "unter", "viel", "vom", "von", "vor", "während",
		"war", "waren", "warst", "was", "weg", "weil", "weiter",
		"welche", "welchem", "welchen", "welcher", "welches", "wenn",
		"werde", "werden", "wie", "wieder", "will", "wir", "wird",
		"wirst", "wo", "wollem", "wollte", "würde", "würden", "zu",
		"zum", "zur", "zwar", "zwischen" };

	text = text.toLowerCase().replaceAll(
		"[.:,;#\"\'+~*^°!\"§$%&/()=}][{0-9]", "");

	// If 'useStoplist = true' and word is in 'stoplist' we are finished
	// here.
	if (useStoplist) {
	    for (String s : stoplist) {
		if (s.equals(text)) {
		    finished = true;
		}
	    }
	}

	if (!finished) {
	    text = text.replaceAll("ß", "ss");

	    /*
	     * Replaces the characters to be saved by be saved 'u'/'y'.
	     * 
	     * Make array one field longer and insert fill character to avoid
	     * IndexOutOfBoundException.
	     */
	    char[] array = new char[text.length() + 1];
	    text.getChars(0, text.length(), array, 0);
	    array[text.length()] = 'x';

	    for (int i = 1; i < array.length - 1; i++) {
		if (array[i] == 'u') {
		    if (contains(vokale, array[i - 1])
			    && contains(vokale, array[i + 1])) {
			array[i] = 'U';
		    }
		} else if (array[i] == 'y') {
		    if (contains(vokale, array[i - 1])
			    && contains(vokale, array[i + 1])) {
			array[i] = 'Y';
		    }
		}
	    }
	    /*
	     * Writing array into String, delete fill character, re-initialize
	     * array without fill character.
	     */
	    text = new String(array);
	    text = text.replaceAll("x$", "");
	    array = new char[text.length()];
	    text.getChars(0, text.length(), array, 0);

	    // r1, r2, p1 & p2 are initialized
	    try {
		Boolean containsVokale = false;
		for (int i = 0; i < array.length; i++) {
		    if (contains(vokale, array[i])) {
			containsVokale = true;
		    }
		    if (contains(konsonanten, array[i]) && containsVokale) {
			p1 = i + 1;
			r1 = text.substring(p1);
			break;
		    }
		}
		containsVokale = false;
		char[] r1_array = r1.toCharArray();
		for (int i = 0; i < r1.length(); i++) {
		    if (contains(vokale, r1_array[i])) {
			containsVokale = true;
		    }
		    if (contains(konsonanten, r1_array[i]) && containsVokale) {
			p2 = i + 1;
			r2 = r1.substring(p2);
			break;
		    }
		}
		if (p1 < 3 && p1 > 0) {
		    p1 = 3;
		    r1 = text.substring(p1);
		} else if (p1 == 0) {
		    finished = true;
		}
	    } catch (Exception e) {
	    }
	}

	if (!finished) {
	    try { /* Step 1 */
		String[] eSuffixe_1 = { "e", "em", "en", "er", "es", "ern" };
		char[] sEndung = { 'b', 'd', 'f', 'g', 'h', 'k', 'l', 'm', 'n',
			'r', 't' };
		Boolean didBreak_1 = false;
		for (String e : eSuffixe_1) {
		    String temp = "";
		    if (r1.length() >= e.length()) {
			temp = r1.substring(r1.length() - e.length());
		    }
		    if (e.equals(temp)) {
			text = text.substring(0, text.length() - e.length());
			r1 = r1.substring(0, r1.length() - e.length());
			r2 = r2.substring(0, r2.length() - e.length());
			didBreak_1 = true;
			break;
		    }
		}
		if (!didBreak_1) {
		    if ("s".equals(r1.substring(r1.length() - 1))) {
			if (contains(sEndung, text.charAt(text.length() - 2))) {
			    text = text.substring(0, text.length() - 1);
			    r1 = r1.substring(0, r1.length() - 1);
			    r2 = r2.substring(0, r2.length() - 1);
			}
		    }
		}
	    } catch (Exception e) {
	    }

	    try { /* Step 2 */
		String[] eSuffixe_2 = { "est", "er", "en" };
		String[] eSonst_2 = { "st" };
		char[] stEndung = { 'b', 'd', 'f', 'g', 'h', 'k', 'l', 'm',
			'n', 't' };
		Boolean didBreak_2 = false;
		for (String e : eSuffixe_2) {
		    String temp = "";
		    if (r1.length() >= e.length()) {
			temp = r1.substring(r1.length() - e.length());
		    }
		    if (e.equals(temp)) {
			text = text.substring(0, text.length() - e.length());
			r1 = r1.substring(0, r1.length() - e.length());
			r2 = r2.substring(0, r2.length() - e.length());
			didBreak_2 = true;
			break;
		    }
		}
		if (!didBreak_2) {
		    if (contains(eSonst_2, r1.substring(r1.length() - 2))) {
			if (contains(stEndung, text.charAt(text.length() - 3))) {
			    if (text.length() > 5) {
				text = text.substring(0, text.length() - 2);
				r1 = r1.substring(0, r1.length() - 2);
				r2 = r2.substring(0, r2.length() - 2);
			    }
			}
		    }
		}
	    } catch (Exception e) {
	    }

	    try { /* Step 3a */
		String[] dSuffixe_1 = { "end", "ung" };
		for (String e : dSuffixe_1) {
		    if (e.equals(r2.substring(r2.length() - 3))) {
			finished = true;
			String temp = "";
			if (r2.length() > 4) {
			    temp = r2.substring(r2.length() - 5,
				    r2.length() - 3);
			}
			if ("ig".equals(temp)) {
			    if (text.charAt(text.length() - 6) == 'e') {
				text = text.substring(0, text.length() - 3);
				r1 = r1.substring(0, r1.length() - 3);
				r2 = r2.substring(0, r2.length() - 3);
				break;
			    } else {
				text = text.substring(0, text.length() - 5);
				r1 = r1.substring(0, r1.length() - 5);
				r2 = r2.substring(0, r2.length() - 5);
				break;
			    }
			} else {
			    text = text.substring(0, text.length() - 3);
			    r1 = r1.substring(0, r1.length() - 3);
			    r2 = r2.substring(0, r2.length() - 3);
			}
		    }
		}
	    } catch (Exception e) {
	    }
	}

	if (!finished) {
	    try { /* Step 3b */
		String[] dSuffixe_2 = { "ig", "ik", "isch" };
		for (String e : dSuffixe_2) {
		    if (e.equals(r2.substring(r2.length() - e.length()))) {
			if (text.charAt(text.length() - e.length() - 1) != 'e') {
			    text = text
				    .substring(0, text.length() - e.length());
			    r1 = r1.substring(0, r1.length() - e.length());
			    r2 = r2.substring(0, r2.length() - e.length());
			    break;
			}
		    }
		}
	    } catch (Exception e) {
	    }

	    try { /* Step 3c */
		String[] dSuffixe_3 = { "lich", "heit" };
		String[] sonder_1 = { "er", "en" };
		Boolean didBreak_3c = false;
		for (String e : dSuffixe_3) {
		    if (e.equals(r2.substring(r2.length() - e.length()))) {
			for (String i : sonder_1) {
			    String temp = "";
			    if (r1.length() > 5) {
				temp = r1.substring(r1.length() - 6, r1
					.length() - 4);
			    }
			    if (i.equals(temp)) {
				text = text.substring(0, text.length() - 6);
				r1 = r1.substring(0, r1.length() - 6);
				r2 = r2.substring(0, r2.length() - 6);
				didBreak_3c = true;
				break;
			    }
			}
			if (!didBreak_3c) {
			    text = text.substring(0, text.length() - 4);
			    r1 = r1.substring(0, r1.length() - 4);
			    r2 = r2.substring(0, r2.length() - 4);
			    break;
			}
		    }
		}
	    } catch (Exception e) {
	    }

	    try { /* Step 3d */
		String e_3d = "keit";
		String[] sonder_2 = { "lich", "ig" };
		if (e_3d.equals(r2.substring(r2.length() - 4))) {
		    Boolean didBreak_3d = false;
		    for (String i : sonder_2) {
			String temp = "";
			if (r2.length() > 4 + i.length() - 1) {
			    temp = r2.substring(r2.length() - 4 - i.length(),
				    r2.length() - 4);
			}
			if (i.equals(temp)) {
			    text = text.substring(0, text.length() - 4
				    - i.length());
			    didBreak_3d = true;
			    break;
			}
		    }
		    if (!didBreak_3d) {
			text = text.substring(0, text.length() - 4);
		    }
		}
	    } catch (Exception e) {
	    }
	}

	text = endStemming(text);
	finished = true;
	return text;
    }
}