/*
 * Created on 13.05.2004
 */
package org.dotplot.tokenizer.filter.tests;

import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.EOLToken;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.KeyWordToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenType;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.service.ITokenizer;
import org.dotplot.tokenizer.service.TextType;

/**
 * TestScanner Class
 * 
 * @author Sebastian Lorberg
 * @version 1.0
 */
public final class TestScanner implements ITokenizer {

	private int index = 0;

	private Token[] tokens = new Token[11];

	public TestScanner() {
		this.tokens[0] = new Token("bla", Token.TYPE_STRING, 1);
		this.tokens[1] = new Token("bla", Token.TYPE_STRING, 1);
		this.tokens[2] = new Token("bla", Token.TYPE_STRING, 1);
		this.tokens[3] = new EOLToken(1);
		this.tokens[4] = new Token("bla", Token.TYPE_STRING, 2);
		this.tokens[5] = new KeyWordToken("bla", Token.TYPE_STRING, 2);
		this.tokens[6] = new KeyWordToken("bla", Token.TYPE_STRING, 2);
		this.tokens[7] = new Token("bla", Token.TYPE_STRING, 2);
		this.tokens[8] = new EOLToken(1);
		this.tokens[9] = new EOLToken(1);
		this.tokens[10] = new Token("bla", Token.TYPE_STRING, 3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.service.ITokenizer#getName()
	 */
	public String getName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.ITokenStream#getNextToken()
	 */
	public Token getNextToken() throws TokenizerException {
		if (this.index < this.tokens.length) {
			return tokens[index++];
		}
		return new EOSToken();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.service.ITokenizer#getSourceType()
	 */
	public ISourceType getStreamType() {
		return TextType.type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.scanner.IScanner#getTokenTypes()
	 */
	public TokenType[] getTokenTypes() {
		return null;
	}

	public void reset() {
		this.index = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.service.ITokenizer#setName(java.lang.String)
	 */
	public void setName(String name) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.scanner.IScanner#setFile(java.io.File)
	 */
	public void setPlotSource(IPlotSource source) {
	}
}
