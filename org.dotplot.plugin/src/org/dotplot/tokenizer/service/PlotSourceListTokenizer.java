/**
 * 
 */
package org.dotplot.tokenizer.service;

import java.util.Iterator;

import org.dotplot.core.BaseType;
import org.dotplot.core.DefaultSourceList;
import org.dotplot.core.IPlotSource;
import org.dotplot.core.ISourceList;
import org.dotplot.core.ISourceType;
import org.dotplot.tokenizer.EOFToken;
import org.dotplot.tokenizer.EOSToken;
import org.dotplot.tokenizer.Token;
import org.dotplot.tokenizer.TokenizerException;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class PlotSourceListTokenizer implements ITokenStream {

    private ISourceType currentType;

    private ISourceList sourceList;

    private ITokenizer tokenizer;

    private Iterator<IPlotSource> iterator;

    IPlotSource currentSource;

    /**
	 * 
	 */
    public PlotSourceListTokenizer(ITokenizer tokenizer) {
	if (tokenizer == null) {
	    throw new NullPointerException();
	}
	this.sourceList = new DefaultSourceList();
	this.tokenizer = tokenizer;
	this.currentType = BaseType.type;
    }

    public void addPlotSource(IPlotSource source) {
	if (source == null) {
	    throw new NullPointerException();
	}
	if (iterator != null) {
	    throw new UnsupportedOperationException();
	}
	this.sourceList.add(source);
	this.currentType = this.sourceList.getCombinedSourceType();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.ITokenStream#getNextToken()
     */
    public Token getNextToken() throws TokenizerException {
	Token token;
	if (iterator == null) {
	    this.iterator = this.sourceList.iterator();
	    if (iterator.hasNext()) {
		this.currentSource = this.iterator.next();
		this.tokenizer.setPlotSource(this.currentSource);
	    }
	}
	if (this.currentSource != null) {
	    token = this.tokenizer.getNextToken();
	    if (token instanceof EOSToken) {
		token = new EOFToken(this.currentSource);
		if (iterator.hasNext()) {
		    this.currentSource = this.iterator.next();
		    this.tokenizer.setPlotSource(this.currentSource);
		} else {
		    this.currentSource = null;
		}
	    }
	    return token;
	} else {
	    return new EOSToken();
	}
    }

    public ISourceList getSourceList() {
	return new DefaultSourceList(this.sourceList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dotplot.tokenizer.service.ITokenStream#getStreamType()
     */
    public ISourceType getStreamType() {
	return this.currentType;
    }

    public void removePlotSource(IPlotSource source) {
	if (source == null) {
	    throw new NullPointerException();
	}
	if (iterator != null) {
	    throw new UnsupportedOperationException();
	}
	this.sourceList.remove(source);
	this.currentType = this.sourceList.getCombinedSourceType();
    }
}
