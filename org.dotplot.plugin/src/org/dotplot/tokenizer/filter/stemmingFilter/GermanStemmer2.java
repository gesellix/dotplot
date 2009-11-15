package org.dotplot.tokenizer.filter.stemmingFilter;

/**
 * A stemmer for german words. The algorithm is based on the report
 * "A Fast and Simple Stemming Algorithm for German Words" by Jrg Caumanns
 * (joerg.caumanns@isst.fhg.de).
 * 
 * @author Gerhard Schwarz
 * @version $Id: GermanStemmer.java,v 1.1 2001/09/24 18:01:05 cutting Exp $
 */

public class GermanStemmer2 {

    /**
     * Buffer for the terms while stemming them.
     */
    private StringBuffer sb = new StringBuffer();
    /**
     * Indicates if a term is handled as a noun.
     */
    private boolean uppercase = false;
    /**
     * Amount of characters that are removed with <tt>substitute()</tt> while
     * stemming.
     */
    private int substCount = 0;

    /**
     * Checks a term if it can be processed correctly.
     * 
     * @return true if, and only if, the given term consists in letters.
     */
    private boolean isStemmable(String term) {
	boolean upper = false;
	int first = -1;
	for (int c = 0; c < term.length(); c++) {
	    // Discard terms that contain non-letter characters.
	    if (!Character.isLetter(term.charAt(c))) {
		return false;
	    }
	    // Discard terms that contain multiple uppercase letters.
	    if (Character.isUpperCase(term.charAt(c))) {
		if (upper) {
		    return false;
		}
		// First encountered uppercase letter, set flag and save
		// position.
		else {
		    first = c;
		    upper = true;
		}
	    }
	}
	// Discard the term if it contains a single uppercase letter that
	// is not starting the term.
	if (first > 0) {
	    return false;
	}
	return true;
    }

    /**
     * Removes a particle denotion ("ge") from a term, but only if at least 3
     * characters will remain.
     * 
     * @return The term without particle denotion, if there was one.
     */
    private StringBuffer removeParticleDenotion(StringBuffer buffer) {
	for (int c = 0; c < buffer.length(); c++) {
	    // Strip from the beginning of the string to the "ge" inclusive.
	    if (c < (sb.length() - 3) && buffer.charAt(c) == 'g'
		    && buffer.charAt(c + 1) == 'e') {
		buffer.delete(0, c + 2);
	    }
	}
	return sb;
    }

    /**
     * Undoes some changes made by substitute(). That are character pairs and
     * character combinations.
     * 
     * @return The term without the not human reaqdable substitutions.
     */
    private StringBuffer resubstitute(StringBuffer buffer) {
	for (int c = 0; c < buffer.length(); c++) {
	    if (buffer.charAt(c) == '*') {
		char x = buffer.charAt(c - 1);
		buffer.setCharAt(c, x);
	    } else if (buffer.charAt(c) == '$') {
		buffer.setCharAt(c, 's');
		buffer.insert(c + 1, new char[] { 'c', 'h' }, 0, 2);
	    } else if (buffer.charAt(c) == '§') {
		buffer.setCharAt(c, 'c');
		buffer.insert(c + 1, 'h');
	    } else if (buffer.charAt(c) == '%') {
		buffer.setCharAt(c, 'e');
		buffer.insert(c + 1, 'i');
	    } else if (buffer.charAt(c) == '&') {
		buffer.setCharAt(c, 'i');
		buffer.insert(c + 1, 'e');
	    } else if (buffer.charAt(c) == '#') {
		buffer.setCharAt(c, 'i');
		buffer.insert(c + 1, 'g');
	    } else if (buffer.charAt(c) == '!') {
		buffer.setCharAt(c, 's');
		buffer.insert(c + 1, 't');
	    }
	}
	return buffer;
    }

    /**
     * Stemms the given term to an unique <tt>discriminator</tt>.
     * 
     * @param term
     *            The term that should be stemmed.
     * @return Discriminator for <tt>term</tt>
     */
    public String stemWord(String term) {
	if (!isStemmable(term)) {
	    return term;
	}
	// Mark a possible noun.
	if (Character.isUpperCase(term.charAt(0))) {
	    uppercase = true;
	}
	// Use lowercase for medium stemming.
	term = term.toLowerCase();
	// Reset the StringBuffer.
	sb.delete(0, sb.length());
	sb.insert(0, term);
	sb = substitute(sb);
	// Nouns have only seven possible suffixes.
	if (uppercase && sb.length() > 3) {
	    if (sb.substring(sb.length() - 3, sb.length()).equals("ern")) {
		sb.delete(sb.length() - 3, sb.length());
	    } else if (sb.substring(sb.length() - 2, sb.length()).equals("en")) {
		sb.delete(sb.length() - 2, sb.length());
	    } else if (sb.substring(sb.length() - 2, sb.length()).equals("er")) {
		sb.delete(sb.length() - 2, sb.length());
	    } else if (sb.substring(sb.length() - 2, sb.length()).equals("es")) {
		sb.delete(sb.length() - 2, sb.length());
	    } else if (sb.charAt(sb.length() - 1) == 'e') {
		sb.deleteCharAt(sb.length() - 1);
	    } else if (sb.charAt(sb.length() - 1) == 'n') {
		sb.deleteCharAt(sb.length() - 1);
	    } else if (sb.charAt(sb.length() - 1) == 's') {
		sb.deleteCharAt(sb.length() - 1);
	    }
	    // Additional step for female plurals of professions and
	    // inhabitants.
	    if (sb.length() > 5
		    && sb.substring(sb.length() - 3, sb.length()).equals(
			    "erin*")) {
		sb.deleteCharAt(sb.length() - 1);
	    }
	    // Additional step for irregular plural nouns like
	    // "Matrizen -> Matrix".
	    if (sb.charAt(sb.length() - 1) == ('z')) {
		sb.setCharAt(sb.length() - 1, 'x');
	    }
	}
	// Check the 7 "base" suffixes: "e", "s", "n", "t", "em", "er", "nd" for
	// all
	// other terms. Adjectives, Verbs and Adverbs have a total of 52
	// different
	// possible suffixes.
	else {
	    // Strip base suffixes as long as enough characters remain.
	    boolean doMore = true;
	    while (sb.length() > 3 && doMore) {
		if ((sb.length() + substCount > 5)
			&& sb.substring(sb.length() - 2, sb.length()).equals(
				"nd")) {
		    sb.delete(sb.length() - 2, sb.length());
		} else if ((sb.length() + substCount > 4)
			&& sb.substring(sb.length() - 2, sb.length()).equals(
				"er")) {
		    sb.delete(sb.length() - 2, sb.length());
		} else if ((sb.length() + substCount > 4)
			&& sb.substring(sb.length() - 2, sb.length()).equals(
				"em")) {
		    sb.delete(sb.length() - 2, sb.length());
		} else if (sb.charAt(sb.length() - 1) == 't') {
		    sb.deleteCharAt(sb.length() - 1);
		} else if (sb.charAt(sb.length() - 1) == 'n') {
		    sb.deleteCharAt(sb.length() - 1);
		} else if (sb.charAt(sb.length() - 1) == 's') {
		    sb.deleteCharAt(sb.length() - 1);
		} else if (sb.charAt(sb.length() - 1) == 'e') {
		    sb.deleteCharAt(sb.length() - 1);
		} else {
		    doMore = false;
		}
	    }
	}
	if (!uppercase) {
	    sb = removeParticleDenotion(sb);
	}
	sb = resubstitute(sb);
	return sb.toString();
    }

    /**
     * Do some substitutions for the term to reduce overstemming:
     * 
     * - Substitute Umlauts with their corresponding vowel: -> aou, "" is
     * substituted by "ss" - Substitute an second char of an pair of equal
     * characters with an asterisk: ?? -> ?* - Substitute some common character
     * combinations with a token: sch/ch/ei/ie/ig/st -> $//%/&/#/!
     * 
     * @return The term with all needed substitutions.
     */
    private StringBuffer substitute(StringBuffer buffer) {
	for (int c = 0; c < buffer.length(); c++) {
	    // Replace the second char of a pair of the equal characters with an
	    // asterisk.
	    if (c > 0 && buffer.charAt(c) == buffer.charAt(c - 1)) {
		buffer.setCharAt(c, '*');
	    }
	    // Substitute Umlauts.
	    else if (buffer.charAt(c) == 'ä') {
		buffer.setCharAt(c, 'a');
	    } else if (buffer.charAt(c) == 'ö') {
		buffer.setCharAt(c, 'o');
	    } else if (buffer.charAt(c) == 'ü') {
		buffer.setCharAt(c, 'u');
	    }
	    // Take care that enough characters at left for search.
	    if (c < buffer.length() - 1) {
		if (buffer.charAt(c) == 'ß') {
		    buffer.setCharAt(c, 's');
		    buffer.insert(c + 1, 's');
		    substCount++;
		}
		// Masking several common character combinations with an token.
		else if ((c < buffer.length() - 2) && buffer.charAt(c) == 's'
			&& buffer.charAt(c + 1) == 'c'
			&& buffer.charAt(c + 2) == 'h') {
		    buffer.setCharAt(c, '$');
		    buffer.delete(c + 1, c + 3);
		    substCount = +2;
		} else if (buffer.charAt(c) == 'c'
			&& buffer.charAt(c + 1) == 'h') {
		    buffer.setCharAt(c, '§');
		    buffer.deleteCharAt(c + 1);
		    substCount++;
		} else if (buffer.charAt(c) == 'e'
			&& buffer.charAt(c + 1) == 'i') {
		    buffer.setCharAt(c, '%');
		    buffer.deleteCharAt(c + 1);
		    substCount++;
		} else if (buffer.charAt(c) == 'i'
			&& buffer.charAt(c + 1) == 'e') {
		    buffer.setCharAt(c, '&');
		    buffer.deleteCharAt(c + 1);
		    substCount++;
		} else if (buffer.charAt(c) == 'i'
			&& buffer.charAt(c + 1) == 'g') {
		    buffer.setCharAt(c, '#');
		    buffer.deleteCharAt(c + 1);
		    substCount++;
		} else if (buffer.charAt(c) == 's'
			&& buffer.charAt(c + 1) == 't') {
		    buffer.setCharAt(c, '!');
		    buffer.deleteCharAt(c + 1);
		    substCount++;
		}
	    }
	}
	return buffer;
    }
}
