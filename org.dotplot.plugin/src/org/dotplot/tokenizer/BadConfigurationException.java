/*
 * Created on 01.05.2004
 */
package org.dotplot.tokenizer;

/**
 * Diese Ausnahme wird geworfen, wenn etwas mit der Configuration nicht in
 * ordnung ist.
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 * @version 1.0 1.5.04
 */
public class BadConfigurationException extends TokenizerException {
    /**
     * for being Serializable
     */
    private static final long serialVersionUID = 9129331924470363972L;

    /**
     * Erzeigt eine BadConfigurationException.
     */
    public BadConfigurationException() {
	super("Die Konfiguration ist fehlerhaft!");
    }
}
