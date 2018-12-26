package org.nchc.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;


public class PeerData {
	
	String peerName;
	String nextHop;
	JSONArray asPath = new JSONArray();
	String nexHopConnectedSW;
	
	String gatewayRouterMac;
	String gatewayRouterIp;
	
	String asn;
	
	public String getAsn() {
		return asn;
	}

	public void setAsn(String asn) {
		this.asn = asn;
	}

	public String getGatewayRouterMac() {
		return gatewayRouterMac;
	}

	public void setGatewayRouterMac(String gatewayRouterMac) {
		this.gatewayRouterMac = gatewayRouterMac;
	}

	public String getGatewayRouterIp() {
		return gatewayRouterIp;
	}

	public void setGatewayRouterIp(String gatewayRouterIp) {
		this.gatewayRouterIp = gatewayRouterIp;
	}

	public JSONArray getAsPath() {
		return asPath;
	}

	public void setAsPath(JSONArray asPath) {
		this.asPath = asPath;
	}

	String nexHopConnectedPort;
	HashMap<String, ArrayList<NodeInOutPort>> flowPath = new HashMap<String, ArrayList<NodeInOutPort>>();
	
	public HashMap<String, ArrayList<NodeInOutPort>> getFlowPath() {
		return flowPath;
	}

	public void setFlowPath(HashMap<String, ArrayList<NodeInOutPort>> flowPath) {
		this.flowPath = flowPath;
	}

	public PeerData(String peerName, String nextHop, JSONArray asPath ) {
		this.peerName = peerName;
		this.nextHop = nextHop;
		this.asPath = asPath;
	}

	public String getPeerName() {
		return peerName;
	}

	public void setPeerName(String peerName) {
		this.peerName = peerName;
	}

	public String getNextHop() {
		return nextHop;
	}

	public void setNextHop(String nextHop) {
		this.nextHop = nextHop;
	}

	public String getNexHopConnectedSW() {
		return nexHopConnectedSW;
	}

	public void setNexHopConnectedSW(String nexHopConnectedSW) {
		this.nexHopConnectedSW = nexHopConnectedSW;
	}

	public String getNexHopConnectedPort() {
		return nexHopConnectedPort;
	}

	public void setNexHopConnectedPort(String nexHopConnectedPort) {
		this.nexHopConnectedPort = nexHopConnectedPort;
	}
	
	

}
