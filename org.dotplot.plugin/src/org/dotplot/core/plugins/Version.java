/**
 * 
 */
package org.dotplot.core.plugins;



/**
 * 
 * @author Christian Gerhardt <case42@gmx.net>
 */
public class Version implements Comparable{	
	
	/**
	 * 
	 */
	public static final String VERSION_PATTERN_MAIN_IDENTIFIER = "([0-9]*\\.)*[0-9]+";
	
	/**
	 * 
	 */
	public static final String VERSION_PATTERN_ALPHA_IDENTIFIER = "alpha";
	
	/**
	 * 
	 */
	public static final String VERSION_PATTERN_BETA_IDENTIFIER = "beta";
	
	/**
	 * 
	 */
	public static final String VERSION_PATTERN_REALEASE_CANDIDATE_IDENTIFIER = "rc";
	
	/**
	 * 
	 */
	public static final String VERSION_PATTERN_REALEASE_NUMBER_IDENTIFIER = "[0-9]+";
	
	/**
	 * 
	 */
	public static final VersionPattern DEFAULT_PATTERN = 
		new VersionPattern(
				VERSION_PATTERN_MAIN_IDENTIFIER,
				VERSION_PATTERN_ALPHA_IDENTIFIER,
				VERSION_PATTERN_BETA_IDENTIFIER,
				VERSION_PATTERN_REALEASE_CANDIDATE_IDENTIFIER,
				VERSION_PATTERN_REALEASE_NUMBER_IDENTIFIER);
	
	/**
	 * 
	 */
	public static final int ALPHA = 0;
	
	/**
	 * 
	 */
	public static final int BETA = 1;
	
	/**
	 * 
	 */
	public static final int RELEASE_CANDIDATE = 2;
	
	/**
	 * 
	 */
	public static final int FINAL = 3;
	
	/**
	 * 
	 */
	private String normalizedVersion;
	
	/**
	 * 
	 */
	private String completeVersionString;
	
	/**
	 * 
	 */
	private String releaseState;
	
	/**
	 * 
	 */
	private String releaseNumber;
	
	/**
	 * 
	 */
	private String mainVersionString;
	
	/**
	 * 
	 */
	private int status;
	
	/**
	 * 
	 */
	private VersionPattern pattern;	
	
	/**
	 * 
	 * @param version
	 * @throws MalformedVersionException
	 */
	public Version(String version) throws MalformedVersionException{
		this.pattern = DEFAULT_PATTERN;
		if(pattern.matches(version)){
			this.init(version);
		}
		else {
			throw new MalformedVersionException(version);
		}
	}
	
	/**
	 * 
	 * @param version
	 * @param pattern
	 * @throws MalformedVersionException
	 */
	public Version(String version, VersionPattern pattern) throws MalformedVersionException{
		this.pattern = pattern;
		if(this.pattern.matches(version)){
			this.pattern = pattern;
			this.init(version);
		}
		else {
			throw new MalformedVersionException(version);
		}
	}
	
	/**
	 * 
	 * @param version
	 */
	private void init(String version){
		this.completeVersionString = version;
		this.releaseState = version.replaceFirst(this.pattern.getMainIdentifier(),"").trim();		
		this.mainVersionString = this.completeVersionString.substring(0, this.completeVersionString.length() - this.releaseState.length()).trim();
		this.releaseNumber = this.releaseState.replaceFirst(this.pattern.getReleaseStateIdentifier(),"").trim();
		if("".equals(this.releaseNumber)) this.releaseNumber = "1";
		if(this.releaseState.split(this.pattern.getAlphaIdentifier()).length != 1){
			this.status = ALPHA;
		}
		else if(this.releaseState.split(this.pattern.getBetaIdentifier()).length != 1){
			this.status = BETA;
		}
		else if(this.releaseState.split(this.pattern.getReleaseCandidateIdentifier()).length != 1){
			this.status = RELEASE_CANDIDATE;
		}
		else {
			this.status = FINAL;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.normalize(this.mainVersionString));
		String status = null;
		switch(this.status){
		case ALPHA:
			status = " alpha ";
			break;
			
		case BETA:
			status = " beta ";
			break;
			
		case RELEASE_CANDIDATE:
			status = " rc ";
			break;
			
		case FINAL:
			break;
		}
		if(status != null){
			buffer.append(status);
			try {
				buffer.append(Integer.parseInt(this.releaseNumber));
			}
			catch(NumberFormatException e){
				buffer.append(this.releaseNumber);
			}
		}
		this.normalizedVersion = buffer.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public VersionPattern getPattern(){
		return this.pattern;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMainVersionString(){
		return this.mainVersionString;
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public boolean isSubVersion(Version v){
		return this.compareTo(v) >= 0;
	}

	/**
	 * 
	 * @return
	 */
	public String getReleaseNumber(){
		return this.releaseNumber;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getReleaseState(){
		return this.releaseState;
	}
		
	/**
	 * 
	 * @param v
	 * @return
	 */
	public boolean isSuperVersion(Version v){
		return this.compareTo(v) <= 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getReleaseStatus(){
		return this.status;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isFinal(){
		return this.status == FINAL;
	}

	/**
	 * Checks if the version is a alphaversion.
	 * @return <code>true</code> if it's an alphaversion, <code>false</code> otherwise.
	 */
	public boolean isAlpha(){
		return this.status == ALPHA;
	}

	/**
	 * Checks if the version is a betaversion.
	 * @return <code>true</code> if it's a betaversion, <code>false</code> otherwise.
	 */
	public boolean isBeta(){
		return this.status == BETA;
	}

	/**
	 * Checks if the version is a release candidate.
	 * @return <code>true</code> if it's a release candidate, <code>false</code> otherwise.
	 */
	public boolean isReleaseCandidate(){
		return this.status == RELEASE_CANDIDATE;
	}

	/*
	 *  (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		if(o instanceof Version){
			return this.equals((Version)o);
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public boolean equals(Version v){
		String version1, version2, number1, number2;
		version1 = normalize(this.getMainVersionString());
		version2 = normalize(v.getMainVersionString());
		if(version1.equals(version2)){
			if(this.getReleaseStatus() == v.getReleaseStatus()){
				number1 = normalize(this.getReleaseNumber());
				number2 = normalize(v.getReleaseNumber());
				return number1.equals(number2);
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public String normalize(){
		return normalizedVersion;
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	private String normalize(String string){
		try {
			string = string.trim();
			String[] parts = string.split("\\.");
			int[] ints = new int[parts.length];
			for(int i = 0; i < ints.length; i++){
				ints[i] = Integer.valueOf(parts[i]);
			}
			StringBuffer buffer = new StringBuffer();
			int length = ints.length;
			for(int i = ints.length-1; i > 0; i--){
				if(ints[i] == 0){
					length--;				
				}
				else {
					break;
				}
			}
			for(int i = 0; i < length; i++){
				buffer.append(ints[i]);
				buffer.append(".");
			}
			buffer.setLength(buffer.length()-1);
			return buffer.toString();
		}
		catch(Exception e){
			return "";
		}
	}
	
	/*
	 *  (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "Version " + completeVersionString;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(Object arg0) {
		return this.compareTo((Version)arg0);
	}

	/**
	 * 
	 * @param version
	 * @return
	 */
	public int compareTo(Version version) {
		String main = version.getMainVersionString();
		int i = 0; 
		i = this.normalize(this.mainVersionString).compareTo(this.normalize(main));
		if(i == 0){
			i = this.status - version.getReleaseStatus();
			if(i == 0){
				i = this.releaseNumber.compareTo(version.getReleaseNumber());
			}
		}
		return i;
	}
}
