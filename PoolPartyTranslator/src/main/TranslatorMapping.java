package main;

public class TranslatorMapping {

	private String ppConceptTag;
	private String msdlName;
	private String msdlURI;
	private String msdlType;
	private String msdlAttribute = "";
	
	/**
	 * @param ppConceptTag the ppConceptTag to set
	 */
	public void setPpConceptTag(String ppConceptTag) {
		this.ppConceptTag = ppConceptTag;
	}
	
	/**
	 * @return the ppConceptTag
	 */
	public String getPpConceptTag() {
		return ppConceptTag;
	}

	/**
	 * @param msdlName the msdlName to set
	 */
	public void setMsdlName(String msdlName) {
		this.msdlName = msdlName;
	}

	/**
	 * @return the msdlName
	 */
	public String getMsdlName() {
		return msdlName;
	}

	/**
	 * @param msdlURI the msdlURI to set
	 */
	public void setMsdlURI(String msdlURI) {
		this.msdlURI = msdlURI;
	}

	/**
	 * @return the msdlURI
	 */
	public String getMsdlURI() {
		return msdlURI;
	}

	/**
	 * @param msdlType the msdlType to set
	 */
	public void setMsdlType(String msdlType) {
		this.msdlType = msdlType;
	}

	/**
	 * @return the msdlType
	 */
	public String getMsdlType() {
		return msdlType;
	}

	/**
	 * @param msdlAttribute the msdlAttribute to set
	 */
	public void setMsdlAttribute(String msdlAttribute) {
		this.msdlAttribute = msdlAttribute;
	}

	/**
	 * @return the msdlAttribute
	 */
	public String getMsdlAttribute() {
		return msdlAttribute;
	}
	
	public String toString() {
		return "Translator Mapping for PoolParty concept "+ppConceptTag
			+"\n\tMSDL Name: "+msdlName
			+"\n\tMSDL URI:  "+msdlURI
			+"\n\tMSDL Type: "+msdlType
			+"\n\tAttribute: "+msdlAttribute;
	}
}
