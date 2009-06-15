/**
 * 
 */
package org.dotplot.examples.FourGrammFilter;

import java.util.Map;

import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;
import org.dotplot.tokenizer.filter.BaseTokenFilter;
import org.dotplot.tokenizer.filter.ITokenFilter;
import org.dotplot.tokenizer.service.ITokenStream;
import org.dotplot.tokenizer.service.TextType;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class FourGrammFilter extends BaseTokenFilter {

    private StringBuffer buffer;

    private int tokenNumber;

    /**
     * Creates a new <code>FourGrammFilter</code>.
     */
    public FourGrammFilter() {
	super();
	this.tokenNumber = 0;
	this.buffer = new StringBuffer();
    }

    public FourGrammFilter(ITokenStream stream) {
	super(stream);
	this.tokenNumber = 0;
	this.buffer = new StringBuffer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.filter.ITokenFilter#applyParameter(java.util.Map)
     */
    @Override
    public void applyParameter(Map<String, ? extends Object> parameter) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(T)
     */
    public int compareTo(ITokenFilter arg0) {
	return 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.filter.ITokenFilter#filterToken(org.dotplot.tokenizer
     * .Token)
     */
    @Override
    public Token filterToken(Token token) throws TokenizerException {
	if (token == null) {
	    return null;
	}
	switch (token.getType()) {
	case Token.TYPE_EOF:
	case Token.TYPE_EOL:
	    break;

	case Token.TYPE_EOS:
	    if (buffer.length() > 0) {
		String buffer = this.buffer.toString();
		this.buffer.setLength(0);
		return new Token(buffer, Token.TYPE_STRING, token.getLine());
	    } else {
		return token;
	    }

	default:
	    this.tokenNumber++;
	    if (this.tokenNumber == 1) {
		this.buffer.append(token.getValue());
	    } else if (this.tokenNumber < 4) {
		this.buffer.append(" ");
		this.buffer.append(token.getValue());
	    } else {
		this.tokenNumber = 0;
		this.buffer.append(" ");
		this.buffer.append(token.getValue());
		String buffer = this.buffer.toString();
		this.buffer.setLength(0);
		return new Token(buffer, Token.TYPE_STRING, token.getLine());
	    }
	}

	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.filter.ITokenFilter#getNextToken()
     */
    @Override
    public Token getNextToken() throws TokenizerException {
	return this.getNextToken(this.getTokenStream());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.dotplot.tokenizer.filter.ITokenFilter#getNextToken(org.dotplot.tokenizer
     * .service.ITokenStream)
     */
    @Override
    public Token getNextToken(ITokenStream tokenStream)
	    throws TokenizerException {
	Token token = null;
	while (token == null) {
	    token = this.filterToken(tokenStream.getNextToken());
	}
	return token;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.service.ITokenStream#getStreamType()
     */
    public ISourceType getStreamType() {
	return TextType.type;
    }

}
