package org.nchc.data;

import org.json.JSONObject;

public class NodeInOutPort {
	
	private String nodeName;
	private String inPort;
	private String outPort;
	private JSONObject flowEntryJson;
	
	public JSONObject getFlowEntryJson() {
		return flowEntryJson;
	}



	public void setFlowEntryJson(JSONObject flowEntryJson) {
		this.flowEntryJson = flowEntryJson;
	}



	public NodeInOutPort(String nodeName, String inPort, String outPort) {
		this.nodeName = nodeName;
		this.inPort = inPort;
		this.outPort = outPort;
	}
	
	
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getInPort() {
		return inPort;
	}
	public void setInPort(String inPort) {
		this.inPort = inPort;
	}
	public String getOutPort() {
		return outPort;
	}
	public void setOutPort(String outPort) {
		this.outPort = outPort;
	}
	
	
	


}
