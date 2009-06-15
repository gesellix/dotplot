/**
 * 
 */
package org.dotplot.core.plugins;

/**
 * @author Christian Gerhardt <case42@gmx.net>
 * 
 */
public class VersionPattern {

    /**
	 * 
	 */
    private String mainIdentifier;

    /**
	 * 
	 */
    private String alphaIdentifier;

    /**
	 * 
	 */
    private String betaIdentifier;

    /**
	 * 
	 */
    private String releaseCandidateIdentifier;

    /**
	 * 
	 */
    private String releaseNumberIdentifier;

    /**
	 * 
	 */
    private String pattern;

    /**
     * Creates a new <code>VersionPattern</code>.
     * 
     * @param main
     * @param alpha
     * @param beta
     * @param releaseCandidate
     * @param releaseNumber
     */
    public VersionPattern(String main, String alpha, String beta,
	    String releaseCandidate, String releaseNumber) {
	this.setMainIdentifier(main);
	this.setAlphaIdentifier(alpha);
	this.setBetaIdentifier(beta);
	this.setReleaseCandidateIdentifier(releaseCandidate);
	this.setReleaseNumberIdentifier(releaseNumber);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
	if (o instanceof VersionPattern) {
	    return this.equals((VersionPattern) o);
	} else {
	    return false;
	}
    }

    public boolean equals(VersionPattern pattern) {
	return this.toString().equals(pattern.toString());
    }

    /**
     * @return Returns the alphaIdentifier.
     */
    public String getAlphaIdentifier() {
	return alphaIdentifier;
    }

    /**
     * @return Returns the betaIdentifier.
     */
    public String getBetaIdentifier() {
	return betaIdentifier;
    }

    /**
     * @return Returns the mainIdentifier.
     */
    public String getMainIdentifier() {
	return mainIdentifier;
    }

    /**
     * @return Returns the releaseCandidateIdentifier.
     */
    public String getReleaseCandidateIdentifier() {
	return releaseCandidateIdentifier;
    }

    /**
     * @return Returns the releaseNumberIdentifier.
     */
    public String getReleaseNumberIdentifier() {
	return releaseNumberIdentifier;
    }

    /**
     * 
     * @return
     */
    public String getReleaseStateIdentifier() {
	StringBuffer buffer = new StringBuffer();
	boolean first = false;

	if (!"".equals(this.alphaIdentifier)) {
	    buffer.append("(");
	    buffer.append(this.alphaIdentifier);
	    first = true;
	}
	if (!"".equals(this.betaIdentifier)) {
	    if (first) {
		buffer.append("|");
	    } else {
		buffer.append("(");
		first = true;
	    }
	    buffer.append(this.betaIdentifier);
	}
	if (!"".equals(this.releaseCandidateIdentifier)) {
	    if (first) {
		buffer.append("|");
	    } else {
		buffer.append("(");
	    }
	    buffer.append(this.releaseCandidateIdentifier);
	    buffer.append(")");
	} else {
	    if (first) {
		buffer.append(")");
	    }
	}
	return buffer.toString();
    }

    /**
     * 
     * @param string
     * @return
     */
    public boolean matches(String string) {
	return string.matches(this.toString());
    }

    /**
     * @param alphaIdentifier
     *            The alphaIdentifier to set.
     */
    private void setAlphaIdentifier(String alphaIdentifier) {
	if (alphaIdentifier == null) {
	    this.alphaIdentifier = "";
	} else {
	    this.alphaIdentifier = alphaIdentifier;
	}
    }

    /**
     * @param betaIdentifier
     *            The betaIdentifier to set.
     */
    private void setBetaIdentifier(String betaIdentifier) {
	if (betaIdentifier == null) {
	    this.betaIdentifier = "";
	} else {
	    this.betaIdentifier = betaIdentifier;
	}
    }

    /**
     * @param mainIdentifier
     *            The mainIdentifier to set.
     */
    private void setMainIdentifier(String mainIdentifier) {
	if (mainIdentifier == null) {
	    this.mainIdentifier = "";
	} else {
	    this.mainIdentifier = mainIdentifier;
	}
    }

    /**
     * @param releaseCandidateIdentifier
     *            The releaseCandidateIdentifier to set.
     */
    private void setReleaseCandidateIdentifier(String releaseCandidateIdentifier) {
	if (releaseCandidateIdentifier == null) {
	    this.releaseCandidateIdentifier = "";
	} else {
	    this.releaseCandidateIdentifier = releaseCandidateIdentifier;
	}
    }

    /**
     * @param releaseNumberIdentifier
     *            The releaseNumberIdentifier to set.
     */
    private void setReleaseNumberIdentifier(String releaseNumberIdentifier) {
	if (releaseNumberIdentifier == null) {
	    this.releaseNumberIdentifier = "";
	} else {
	    this.releaseNumberIdentifier = releaseNumberIdentifier;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuffer buffer;
	if (this.pattern == null) {
	    buffer = new StringBuffer();
	    buffer.append(this.mainIdentifier);
	    String rsident = this.getReleaseStateIdentifier();
	    if (!"".equals(rsident)) {
		buffer.append("( ");
		buffer.append(rsident);
		if (!"".equals(this.releaseNumberIdentifier)) {
		    buffer.append("( ");
		    buffer.append(this.releaseNumberIdentifier);
		    buffer.append(")?");
		}
		buffer.append(")?");
	    }
	    this.pattern = buffer.toString();
	}
	return this.pattern;
    }

}
