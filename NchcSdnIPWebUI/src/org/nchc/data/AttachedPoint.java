package org.nchc.data;

public class AttachedPoint {
	
	private String hostMac = "";
	private String switchId = "";
	private String attachedPort = "";
	
	
	public AttachedPoint(String hostMac, String switchId, String attachedPort) {
		super();
		this.hostMac = hostMac;
		this.switchId = switchId;
		this.attachedPort = attachedPort;
	}
	
	public String getHostMac() {
		return hostMac;
	}
	public void setHostMac(String hostMac) {
		this.hostMac = hostMac;
	}
	public String getSwitchId() {
		return switchId;
	}
	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}
	public String getAttachedPort() {
		return attachedPort;
	}
	public void setAttachedPort(String attachedPort) {
		this.attachedPort = attachedPort;
	}
	
	

}
