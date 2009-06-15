package org.dotplot.tokenizer.service;

import org.dotplot.core.IConfiguration;

/**
 * Interface for the configuration of the Tokenizer.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0
 */
public interface ITokenizerConfiguration extends IConfiguration {

    /**
     * 
     * @return
     */
    public String getTokenizerID();

    /**
     * 
     * @param id
     */
    public void setTokenizerID(String id);
}
