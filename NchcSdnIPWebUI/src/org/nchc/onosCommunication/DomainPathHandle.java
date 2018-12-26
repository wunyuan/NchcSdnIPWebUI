package org.nchc.onosCommunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nchc.data.AttachedPoint;
import org.nchc.data.LinkPair;
//import org.nchc.data.LinkPair;
import org.nchc.data.LinkType;
import org.nchc.data.NodeInOutPort;
import org.nchc.data.PeerData;

public class DomainPathHandle {
	
	private ArrayList<PeerData> peerDataList;
	private OnosCommunicationService onosCom;
	private String localAsnNum;
	

	
	
	public DomainPathHandle(ArrayList<PeerData> peerDataList, OnosCommunicationService onosCom, String localAsnNum) {
		this.peerDataList = peerDataList;
		this.onosCom = onosCom;
		this.localAsnNum = localAsnNum;
	}
	

	public ArrayList<PeerData> getPeerDataList() {
		return peerDataList;
	}

	public void setPeerDataList(ArrayList<PeerData> peerDataList) {
		this.peerDataList = peerDataList;
	}
	

	protected HashMap<LinkPair, ArrayList<NodeInOutPort>> findFlowPath(JSONObject asnsJso) {
		String devicesJson = onosCom.getDevices();	
		
		HashMap<LinkPair, ArrayList<NodeInOutPort>> flowPath= new HashMap<LinkPair, ArrayList<NodeInOutPort>>();
		
		HashSet<String> deviceSet = new HashSet<String>();
		
		try {
			
			JSONObject devsJso = new JSONObject(devicesJson);
			JSONArray devicesJsa = devsJso.getJSONArray("devices");
			
			for(int i=0; i < devicesJsa.length(); i++) {
				
				JSONObject devJso = devicesJsa.getJSONObject(i);
				String swid = devJso.getString("id");
				deviceSet.add(swid);
			}
			
			
			HashMap<String,String> peerGatewayMap = new HashMap<String,String>();
			
			
			for(String sw : deviceSet) {
				String flowJson = this.onosCom.getFlowEntries();
				
				JSONObject flowsJso = new JSONObject(flowJson);
				JSONArray flowsJsa = flowsJso.getJSONArray("flows");
			
				for(int i = 0; i < flowsJsa.length(); i++) {
					
					JSONObject flowJso = flowsJsa.getJSONObject(i);
					
					String gatewayMac = "";
					String peerName = "";
					boolean matched = false;
					String deviceId = flowJso.getString("deviceId");
					
					if(deviceId.equals(sw)) {
						
						JSONArray instructionJsa = (flowJso.getJSONObject("treatment")).getJSONArray("instructions");
						
						for( int j = 0; j < instructionJsa.length(); j++) {
							
							JSONObject jo = instructionJsa.getJSONObject(j);
							if(jo.getString("type").equals("L2MODIFICATION") && jo.getString("subtype").equals("ETH_DST") ) {				
								gatewayMac = jo.getString("mac");
								matched = true;
								break;
							}									
						}
						
						if(matched) {
							JSONArray criJsa = (flowJso.getJSONObject("selector")).getJSONArray("criteria");
							for( int j = 0; j < criJsa.length(); j++) {
								
								JSONObject jo = criJsa.getJSONObject(j);

								if(jo.getString("type").equals("IPV4_DST")) {
									peerName = jo.getString("ip");
									break;
								}
							}
							peerGatewayMap.put(peerName, gatewayMac);
						}
						
					}								
				}
			}			
			
			HashSet<AttachedPoint> attachedSet = getSdnAttachedPoint();
			
			if(!peerDataList.isEmpty()) {
				for(PeerData peerdata : peerDataList) {
					String peer = peerdata.getPeerName();
					peerdata.setGatewayRouterMac(peerGatewayMap.get(peer));
					
					for(AttachedPoint attach : attachedSet) {
						if(attach.getHostMac().equals(peerdata.getGatewayRouterMac())) {
							peerdata.setNexHopConnectedSW(attach.getSwitchId());
							peerdata.setNexHopConnectedPort(attach.getAttachedPort());
						}
					}
				}
			}
			
			
			flowPath = flowPathGetter(peerDataList, attachedSet, asnsJso);
									
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return flowPath;
	}


	private HashSet<AttachedPoint> getSdnAttachedPoint() throws JSONException {
		// TODO Auto-generated method stub
		String jsonString = this.onosCom.getHosts();
		
		JSONObject hostsJso = new JSONObject(jsonString);
		JSONArray hostsJsa = hostsJso.getJSONArray("hosts");
		HashSet<AttachedPoint> attachedSet = new HashSet<AttachedPoint>();
		
		for(int i = 0; i < hostsJsa.length(); i++) {
			JSONObject hostJso = hostsJsa.getJSONObject(i);
			String hostMac = hostJso.getString("mac");
			String swId = "";
			String port = "";
			
			if(hostJso.isNull("location")) {
				JSONArray locationJa = hostJso.getJSONArray("locations");
				JSONObject locationJb =locationJa.getJSONObject(0);
				swId = locationJb.getString("elementId");
				port = locationJb.getString("port");
				
			} else {
				JSONObject locationJb =hostJso.getJSONObject("location");
				swId = locationJb.getString("elementId");
				port = locationJb.getString("port");
			}
			
			attachedSet.add(new AttachedPoint(hostMac, swId, port));			
		}
		
		return attachedSet;
	}


	private HashMap<LinkPair, ArrayList<NodeInOutPort>> flowPathGetter(ArrayList<PeerData> peerDataList, HashSet<AttachedPoint> attachedSet, JSONObject asnsJso) throws JSONException {
		
		HashMap<LinkPair, ArrayList<NodeInOutPort>> pathFlowMap = new HashMap<LinkPair, ArrayList<NodeInOutPort>>();
		
		String jsonString = this.onosCom.getFlowEntries();
		JSONObject flowsJso = new JSONObject(jsonString);
		JSONArray flowsJsa = flowsJso.getJSONArray("flows");
		
		jsonString = this.onosCom.getLinks();
		JSONObject linksJso = new JSONObject(jsonString);
		JSONArray linksJsa = linksJso.getJSONArray("links");
		
		
		for(PeerData srcPeer : peerDataList) {
			
			for(PeerData dstPeer : peerDataList) {
				
				if(!srcPeer.getPeerName().equals(dstPeer.getPeerName())) {
					HashMap<String, ArrayList<NodeInOutPort>> flowPath = srcPeer.getFlowPath();
					
					if(!flowPath.containsKey(dstPeer.getPeerName())) {
					
						ArrayList<NodeInOutPort> path  = flowEntryiesGetter(srcPeer.getPeerName(), dstPeer.getPeerName(), 
								srcPeer.getNexHopConnectedSW(), srcPeer.getNexHopConnectedPort(),
								new ArrayList<NodeInOutPort>(), attachedSet, flowsJsa, linksJsa);
						
					
						String srcAsnName = asnNameCheck(asnsJso, srcPeer.getAsn());
						String dstAsnName = asnNameCheck(asnsJso, dstPeer.getAsn());
						String linkIdPart1 = "";
						String linkIdPart2 = "";

						if(srcAsnName.equals("")){
							
							linkIdPart1 = srcPeer.getPeerName()+" ("+ srcPeer.getAsn()+ ")";
							
						} else {
							
							linkIdPart1 = srcPeer.getPeerName()+" ("+ srcAsnName+ ")";
							
						}
						
						if(dstAsnName.equals("")){
							
							linkIdPart2 = dstPeer.getPeerName()+ " ("+ dstPeer.getAsn()+ ")" ;
							
						} else {
							
							linkIdPart2 = dstPeer.getPeerName()+ " ("+ dstAsnName+ ")" ;
							
						}
						
						
						
						JSONArray srcAsnJsa = srcPeer.getAsPath();
						JSONArray dstAsnJsa = dstPeer.getAsPath();

						String globalAsnPath = createGlobalAsnPath(srcAsnJsa, dstAsnJsa, this.localAsnNum);
						
						String linkId = linkIdPart1 + "&nbsp;&nbsp;->&nbsp;&nbsp;" + linkIdPart2 ;
						LinkPair domainLink = new LinkPair(srcPeer.getPeerName(), dstPeer.getPeerName(), linkId, LinkType.DOMAINlINK, globalAsnPath);
						if(!(path.isEmpty())) {
							pathFlowMap.put(domainLink, path); 
						}
												
					} else {
						
					}

						//implement:maybe implement path update.			
					
				}// if end			
			} //for loop end		
			
		} // for loop end		
		
		return pathFlowMap;
		
	}
	
	
	private String createGlobalAsnPath(JSONArray srcAsnJsa, JSONArray dstAsnJsa, String localAsnNum) {
		// TODO Auto-generated method stub
		
		String golobalAsnPath = "";
		
		if(srcAsnJsa != null) {
			
			for(int i = (srcAsnJsa.length()-1); i >= 0; i--) {
				
				try {
					golobalAsnPath = golobalAsnPath+ "ASN:"+String.valueOf(srcAsnJsa.getInt(i))+ ",";
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			
		}
	/*	for(int i = (srcAsnJsa.length()-1); i >= 0; i--) {
			
			try {
				golobalAsnPath = golobalAsnPath+ "ASN:"+String.valueOf(srcAsnJsa.getInt(i))+ ",";
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}*/
		
		golobalAsnPath = golobalAsnPath + localAsnNum + ",";
		
		if(dstAsnJsa != null) {
			
			for(int j=0; j< dstAsnJsa.length(); j++) {
				
				try {
					golobalAsnPath = golobalAsnPath+ "ASN:"+String.valueOf(dstAsnJsa.getInt(j))+ ",";
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				
			}
			
		}
		
		/*
		for(int j=0; j< dstAsnJsa.length(); j++) {
			
			try {
				golobalAsnPath = golobalAsnPath+ "ASN:"+String.valueOf(dstAsnJsa.getInt(j))+ ",";
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
		*/
		
		return golobalAsnPath.substring(0, golobalAsnPath.length()-1);
	}


	private String asnNameCheck(JSONObject asnsJso, String asn) {
		
		String asnName = "";
		String [] asnStr = asn.split(":");
		JSONArray asnsJsa;
		try {
			asnsJsa = asnsJso.getJSONArray("asnmap");
			for(int i=0; i < asnsJsa.length(); i++) {
				
				JSONObject asnJso = asnsJsa.getJSONObject(i);
							
				if( asnStr[1].equals(asnJso.getString("asnid"))) {
					asnName = asnJso.getString("name");
					break;
				}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			asnName = "";
		}
		
		
		return asnName;
		
	}
	
	


	private ArrayList<NodeInOutPort> flowEntryiesGetter(String srcDomain, String dstDomain, 
			String sw, String inPort, ArrayList<NodeInOutPort> flowPath, HashSet<AttachedPoint> attachedSet, 
			JSONArray flowsJsa, JSONArray linksJsa) {
		// TODO Auto-generated method stub
		String nextHopSw = "";
		String nextHopInPort = "";
		String swOutPort = "";
		String dstMac = "";
		boolean jumpOut = false;

		if(flowPath.isEmpty()) {
			
			try {
				NodeInOutPort nip = null;
				jumpOut = false;
				for(int i = 0; i < flowsJsa.length(); i++) {
					
					String tempPort = "", tempIp = "";
					JSONObject flowJso = flowsJsa.getJSONObject(i);				
					String deviceId = flowJso.getString("deviceId");
					
					if(deviceId.equals(sw)) {
										
						JSONArray criJsa = (flowJso.getJSONObject("selector")).getJSONArray("criteria");
						
						for( int j = 0; j < criJsa.length(); j++) {
							
							JSONObject jo = criJsa.getJSONObject(j);
							
							if(jo.getString("type").equals("IN_PORT")) {
								tempPort = String.valueOf(jo.getInt("port"));
							}
							
							if(jo.getString("type").equals("IPV4_DST")) {
								tempIp = jo.getString("ip");
							}				
						}
						
						if(tempPort.equals(inPort) && tempIp.equals(dstDomain)) {
												
							JSONArray instructionJsa = (flowJso.getJSONObject("treatment")).getJSONArray("instructions");
							
							for( int j = 0; j < instructionJsa.length(); j++) {
								
								JSONObject jo = instructionJsa.getJSONObject(j);
								if(jo.getString("type").equals("L2MODIFICATION") && jo.getString("subtype").equals("ETH_DST") ) {
									
									dstMac = jo.getString("mac");
								}
								if(jo.getString("type").equals("OUTPUT")) {
									swOutPort = jo.getString("port");
									nip = new NodeInOutPort(sw, inPort, swOutPort);
									nip.setFlowEntryJson(flowJso);
									jumpOut = true;
									break;
								}				
							}			
						}
						
						if(jumpOut) {
							break;
						}
					}						
				}
				
				if(nip != null) {
					flowPath.add(nip);
				}
					
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String getResult = getNextHopSwPort(sw, swOutPort, attachedSet, linksJsa);
			
			if(!getResult.equals("NotMatched") && !getResult.equals(LinkType.HOSTLINK.toString())) {
				
				String [] swPort = getResult.split("#");
				nextHopSw = swPort[0];
				nextHopInPort = swPort[1];
				flowEntryiesGetter(srcDomain, dstMac, nextHopSw, nextHopInPort, flowPath, attachedSet, flowsJsa, linksJsa);
			}
		}
		else {

			try {
				
				NodeInOutPort nip = null;
				jumpOut = false;
				for(int i = 0; i < flowsJsa.length(); i++) {
					String tempPort = "", tempMac = "";
					JSONObject flowJso = flowsJsa.getJSONObject(i);
					String deviceId = flowJso.getString("deviceId");
					
					if(deviceId.equals(sw)) {
						
						JSONArray criJsa = (flowJso.getJSONObject("selector")).getJSONArray("criteria");
						for( int j = 0; j < criJsa.length(); j++) {
							
							JSONObject jo = criJsa.getJSONObject(j);
							if(jo.getString("type").equals("IN_PORT")) {
								tempPort = String.valueOf(jo.getInt("port"));
							}
							if(jo.getString("type").equals("ETH_DST")) {
								tempMac = jo.getString("mac");
							}				
						}
						
						if(tempPort.equals(inPort) && tempMac.equals(dstDomain)) {
							
							
							JSONArray instructionJsa = (flowJso.getJSONObject("treatment")).getJSONArray("instructions");
							
							for( int j = 0; j < instructionJsa.length(); j++) {
								
								JSONObject jo = instructionJsa.getJSONObject(j);
								
								if(jo.getString("type").equals("OUTPUT")) {
									swOutPort = jo.getString("port");
									nip = new NodeInOutPort(sw, inPort, swOutPort);
									nip.setFlowEntryJson(flowJso);
									jumpOut = true;
									break;
								}				
							}			
						}
						
						if(jumpOut) {
							break;
						}						
					}				
				}//end for
				
				if(nip != null) {
					flowPath.add(nip);
				}
				
							
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
			
			
			String getResult = getNextHopSwPort(sw, swOutPort, attachedSet, linksJsa);
			
			if(!getResult.equals("NotMatched") && !getResult.equals(LinkType.HOSTLINK.toString())) {
				
				String [] swPort = getResult.split("#");
				nextHopSw = swPort[0];
				nextHopInPort = swPort[1];
				flowEntryiesGetter(srcDomain, dstDomain, nextHopSw, nextHopInPort, flowPath, attachedSet, flowsJsa, linksJsa);
			}		
			
		}
		
		return flowPath;
		
	}


	private String getNextHopSwPort(String firstSw, String nextHopInPort, HashSet<AttachedPoint> attachedSet, JSONArray linksJsa) {
		// TODO Auto-generated method stub
		
        for(AttachedPoint attach : attachedSet) {
			
			if(attach.getSwitchId().equals(firstSw) && attach.getAttachedPort().equals(nextHopInPort)) {
				return LinkType.HOSTLINK.toString();
			}	
		}

        try{
        	           
            for(int i = 0; i < linksJsa.length(); i++) {
            	JSONObject linkJso = linksJsa.getJSONObject(i);
            	JSONObject srcJso = linkJso.getJSONObject("src");
            	if( srcJso.getString("port").equals(nextHopInPort) && srcJso.getString("device").equals(firstSw)) {
            			JSONObject dstJso = linkJso.getJSONObject("dst");
            			String result = dstJso.getString("device")+"#"+dstJso.getString("port");
            			return result;
            		
            	}
            }
        		
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return "NotMatched";
	}


	public String getLocalAsnNum() {
		return localAsnNum;
	}


	public void setLocalAsnNum(String localAsnNum) {
		this.localAsnNum = localAsnNum;
	}

	
	
	
}
