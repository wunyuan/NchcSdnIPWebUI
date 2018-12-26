package org.nchc.data;

public class LinkPair {
	
	private String src;
	private String srcPort;
	private String dst;
	private String dstPort;
	private String linkId;
	private LinkType linkType;
	private String globalAsnPath;
	
	
	public LinkPair(String src, String dst, String linkId, LinkType linkType){
		
		this.src = src;
		this.dst = dst;
		this.linkId = linkId;
		this.linkType = linkType;

	}
	
	public LinkPair(String src, String dst, String linkId, LinkType linkType, String globalAsnPath){
		
		this.src = src;
		this.dst = dst;
		this.linkId = linkId;
		this.linkType = linkType;
		this.globalAsnPath = globalAsnPath;
	}
	
	public LinkPair(String src, String srcPort, String dst, String linkId, LinkType linkType){
		
		this.src = src;
		this.dst = dst;
		this.linkId = linkId;
		this.linkType = linkType;

	}
	
	
	public LinkPair(String src, String srcPort, 
		String dst, String dstPort, 
		String linkId, LinkType linkType){
		
		this.src = src;
		this.srcPort = srcPort;
		this.dst = dst;
		this.dstPort = dstPort;
		this.linkId = linkId;
		this.linkType = linkType;

	}
	
	public String getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}

	public String getDstPort() {
		return dstPort;
	}

	public void setDstPort(String dstPort) {
		this.dstPort = dstPort;
	}

	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getGlobalAsnPath() {
		return globalAsnPath;
	}

	public void setGlobalAsnPath(String globalAsnPath) {
		this.globalAsnPath = globalAsnPath;
	}
		
	

}
