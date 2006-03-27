package org.dotplot.tokenizer.service;

import org.dotplot.core.IConfiguration;

/**
 * Default implemtation of the <code>ITokenizerConfiguration</code>
 * interfacese.
 * <p>
 * The {@link TokenizerService#DEFAULT_TOKENIZER_ID} is used as default
 * tokenizer.
 * </p>
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public class DefaultTokenizerConfiguration implements ITokenizerConfiguration {

	/**
	 * The if of the assigned tokenizer.
	 */
	private String tokenizerID;

	/**
	 * Creates a new <code>DefaultTokenizerConfiguration</code>.
	 */
	public DefaultTokenizerConfiguration() {
		this.tokenizerID = TokenizerService.DEFAULT_TOKENIZER_ID;
	}

	/**
	 * Creates a new <code>DefaultTokenizerConfiguration</code>.
	 * 
	 * @param tokenizerID
	 *            The id of the tokenizer used as default value.
	 */
	public DefaultTokenizerConfiguration(String tokenizerID) {
		if (tokenizerID == null) throw new NullPointerException();
		this.tokenizerID = tokenizerID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.ITokenizerConfiguration#getTokenizerID()
	 */
	public String getTokenizerID() {
		return this.tokenizerID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.tokenizer.service.ITokenizerConfiguration#setTokenizerID(java.lang.String)
	 */
	public void setTokenizerID(String id) {
		if (id == null) {
			throw new NullPointerException();
		}
		else {
			this.tokenizerID = id;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#copy()
	 */
	public IConfiguration copy() {
		DefaultTokenizerConfiguration clone = new DefaultTokenizerConfiguration();
		clone.tokenizerID = this.tokenizerID;
		return clone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#serializedForm()
	 */
	public String serializedForm() throws UnsupportedOperationException {
		return this.tokenizerID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dotplot.core.IConfiguration#objectForm(java.lang.String)
	 */
	public IConfiguration objectForm(String serivalizedForm)
			throws UnsupportedOperationException {
		if(serivalizedForm == null) throw new NullPointerException();
		return new DefaultTokenizerConfiguration(serivalizedForm);
	}
}
