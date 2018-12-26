package org.nchc.onosCommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nchc.data.LinkPair;
import org.nchc.data.LinkType;
import org.nchc.data.NodeInOutPort;
import org.nchc.data.PeerData;
//import org.nchc.db.DBconnectionService;
import org.nchc.data.TopologyNodeData;


@WebServlet( name="SDNIPToplogyHandler" , urlPatterns={"/topology.do"}, 
			initParams={
					@WebInitParam(name="ASNMAP", value="/SdnIpWebUI/data/asnmap")
			})
@MultipartConfig
public class SDNIPToplogyHandler extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3493933904259138446L;

	private String ASNMAP;
	
	private JSONObject asnMapJsonString;
	
	private String localAsn = "0";
	
	public void init() throws ServletException {
		super.init();
		ASNMAP = getInitParameter("ASNMAP");
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	
		HashSet<TopologyNodeData> nodeDataSet = new HashSet<TopologyNodeData>();
		HashSet<LinkPair> linkSet = new HashSet<LinkPair>();
		JSONObject peerPathJso = new JSONObject();
		JSONArray asnPeerPathJsa = new JSONArray();
		String asnNetworkMap = "";
		String jsonStr = asnMapFileLoad(this.getServletContext());
		
		try {
			this.asnMapJsonString = new JSONObject(jsonStr);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		for(String ip : OnosCommunicationService.ipAccountPwdMap.keySet()) { 
			
			String accountPwdSsl = OnosCommunicationService.ipAccountPwdMap.get(ip);
			String [] idPwdSsl = accountPwdSsl.split(":");
			
			OnosCommunicationService onosComm = new OnosCommunicationService(idPwdSsl[0], idPwdSsl[1]);
			String port = OnosCommunicationService.ipPortMap.get(ip);
			
			OnosCommunicationService.localOnosIP = ip;
			OnosCommunicationService.localOnosPort = port;
			OnosCommunicationService.localOnosSsl = idPwdSsl[2];

			String jsonString = onosComm.getBgpRoutesWithIPPort(ip, port, idPwdSsl[2]);
			String neighborJsonString = onosComm.getBgpNeighborWithIPPort(ip, port, idPwdSsl[2]);
			ArrayList<String> peerList = new ArrayList<String>();
			ArrayList<PeerData> peerDataList = new ArrayList<PeerData>();
			
			try {
				JSONObject jso = new JSONObject(jsonString);
				JSONObject neighborJso = new JSONObject(neighborJsonString);
				JSONArray jsa = jso.getJSONArray("routes4");
				JSONArray neighborJsa = neighborJso.getJSONArray("bgpneighbor");
				JSONObject neighborFirstJso = neighborJsa.getJSONObject(0);
				this.localAsn = Integer.toString(neighborFirstJso.getInt("localAs"));
				
				for(int i = 0; i < jsa.length(); i++) {
					JSONObject jo = jsa.getJSONObject(i);
					String prefix =jo.getString("prefix");
					JSONArray psJsa = (jo.getJSONObject("asPath")).getJSONArray("pathSegments");
					JSONArray asPath = null;
					if(psJsa.length() != 0) {
						asPath = (psJsa.getJSONObject(0)).getJSONArray("segmentAsNumbers");
					}

					String nextHop =jo.getString("nextHop");
					peerList.add(prefix);
					peerDataList.add(new PeerData(prefix, nextHop, asPath));
				}		
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			HashSet<LinkPair> links = createLinks(peerDataList, OnosCommunicationService.localOnosIP);
			HashSet<TopologyNodeData> nodeDatas = createNodes(links);
			

			linkSet = addLinkToSet(linkSet, links);
			nodeDataSet = addNodeToSet(nodeDataSet, nodeDatas);
			nodeDataSet = nodeTypeChange(nodeDataSet, "ASN:"+localAsn);

			
			asnNetworkMap = createAsnNetworkMap(peerDataList);
		//	String topologyString = createTopologyString(peerDataList, OnosCommunicationService.localOnosIP, links);
			
			DomainPathHandle dmPathHandle = new DomainPathHandle(peerDataList, onosComm, "ASN:"+localAsn);
			HashMap<LinkPair, ArrayList<NodeInOutPort>> pathFlowMap = dmPathHandle.findFlowPath(this.asnMapJsonString);
			
			
			JSONObject asnPeerJso = createPeerPathFlowJson(pathFlowMap, asnNetworkMap, "ASN"+localAsn);
			asnPeerPathJsa.put(asnPeerJso);
					
		}

		try {
			peerPathJso.put("asns", asnPeerPathJsa);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String topologyString = createTopologyString(nodeDataSet, linkSet);
		
		System.out.println(peerPathJso.toString());
		System.out.println(topologyString);
		System.out.println(asnNetworkMap);
		

		req.setAttribute("peerPathJson", peerPathJso.toString());
		req.setAttribute("topoJson", topologyString);
		req.setAttribute("asnNetworkMap", asnNetworkMap);
		
		
		RequestDispatcher reqDispatcher = req.getRequestDispatcher("/SdnIpWebUI/topology.jsp");
		reqDispatcher.forward(req, resp);
		
	/*	OnosCommunicationService onosComm = new OnosCommunicationService(OnosCommunicationService.account, OnosCommunicationService.pwd);
		String jsonString = onosComm.getBgpRoutes();
		String neighborJsonString = onosComm.getBgpNeighbor();
		ArrayList<String> peerList = new ArrayList<String>();
		ArrayList<PeerData> peerDataList = new ArrayList<PeerData>();
			
		try {
			JSONObject jso = new JSONObject(jsonString);
			JSONObject neighborJso = new JSONObject(neighborJsonString);
			JSONArray jsa = jso.getJSONArray("routes4");
			JSONArray neighborJsa = neighborJso.getJSONArray("bgpneighbor");
			JSONObject neighborFirstJso = neighborJsa.getJSONObject(0);
			this.localAsn = Integer.toString(neighborFirstJso.getInt("localAs"));
			
			for(int i = 0; i < jsa.length(); i++) {
				JSONObject jo = jsa.getJSONObject(i);
				String prefix =jo.getString("prefix");
				JSONArray psJsa = (jo.getJSONObject("asPath")).getJSONArray("pathSegments");
				JSONArray asPath = null;
				if(psJsa.length() != 0) {
					asPath = (psJsa.getJSONObject(0)).getJSONArray("segmentAsNumbers");
				}

				String nextHop =jo.getString("nextHop");
				peerList.add(prefix);
				peerDataList.add(new PeerData(prefix, nextHop, asPath));
			}		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HashSet<LinkPair> linkSet = createLinks(peerDataList, OnosCommunicationService.localOnosIP);
	//	HashSet<TopologyNodeData> nodeDataSet = new HashSet<TopologyNodeData>();
	//	nodeDataSet = createNodeSet(nodeDataSet, linkSet);
		
		String asnNetworkMap = createAsnNetworkMap(peerDataList);
		String topologyString = createTopologyString(peerDataList, OnosCommunicationService.localOnosIP, linkSet);
		
		DomainPathHandle dmPathHandle = new DomainPathHandle(peerDataList, onosComm);
		HashMap<LinkPair, ArrayList<NodeInOutPort>> pathFlowMap = dmPathHandle.findFlowPath(this.asnMapJsonString);
		
		
		JSONObject peerPathJso = createPeerPathFlowJson(pathFlowMap, asnNetworkMap);
		

		System.out.println(peerPathJso.toString());
		System.out.println(topologyString);
		System.out.println(asnNetworkMap);
		
		req.setAttribute("peerPathJson", peerPathJso.toString());
		req.setAttribute("topoJson", topologyString);
		req.setAttribute("asnNetworkMap", asnNetworkMap);
		//db.addPeerData(peerList, onosComm.getLocalOnosIP());		
		RequestDispatcher reqDispatcher = req.getRequestDispatcher("/SdnIpWebUI/topology.jsp");
		reqDispatcher.forward(req, resp);
		*/
	}




	private HashSet<TopologyNodeData> nodeTypeChange(HashSet<TopologyNodeData> nodeDataSet, String localAsn) {
		// TODO Auto-generated method stub
		
		for(TopologyNodeData node : nodeDataSet) {
			
			if(node.getId().equals(localAsn)) {
				nodeDataSet.remove(node);
				node.setType("sdn");
				nodeDataSet.add(node);
				break;
			}		
		}		
		return nodeDataSet;
	}


	private HashSet<LinkPair> addLinkToSet(HashSet<LinkPair> linkSet, HashSet<LinkPair> links) {
		// TODO Auto-generated method stub
		
		boolean add = true;
		HashSet<LinkPair> needAddLinkSet = new HashSet<LinkPair>();
		
		if(linkSet.isEmpty()) {
			linkSet = links;
		} else {

			for(LinkPair newlink : links) {
				
				add = true;
				
				for(LinkPair link : linkSet ) { 
					
					if( ( (newlink.getSrc().equals( link.getSrc() )) || (newlink.getSrc().equals( link.getDst() ) ) ) && 
						( (newlink.getDst().equals( link.getSrc() )) || (newlink.getDst().equals( link.getDst() ) ) ) ){
						
						add = false;
						break;
					}
					
				}				
				if(add) {
					needAddLinkSet.add(newlink);
				}				
			}			
		}

		
		if(!needAddLinkSet.isEmpty()) {			
			for(LinkPair link : needAddLinkSet) {
				linkSet.add(link);
			}			
		}
		
		return linkSet;
	}


	private HashSet<TopologyNodeData> addNodeToSet(HashSet<TopologyNodeData> nodeDataSet,
			HashSet<TopologyNodeData> nodeDatas) {
		// TODO Auto-generated method stub
		
		boolean add = true;
		
		HashSet<TopologyNodeData> needAddNodeSet = new HashSet<TopologyNodeData>();
		
		if(nodeDataSet.isEmpty()) {		
			nodeDataSet = nodeDatas; 						
		} else {
			
			for(TopologyNodeData newNode : nodeDatas) {
				
				add = true;
			
				for(TopologyNodeData origNode : nodeDataSet) {
					
					if(newNode.getId().equals(origNode.getId())) {
						
						add = false;
						break;
						
					}					
				}
				
				if(add) {
					needAddNodeSet.add(newNode);
				}
				
			}	
		}
		
		if(!needAddNodeSet.isEmpty()) {
		
			for(TopologyNodeData node : needAddNodeSet) {
				nodeDataSet.add(node);
			}
			
		}
		
		return nodeDataSet;
	}


	private String createAsnNetworkMap(ArrayList<PeerData> peerDataList) {
		// TODO Auto-generated method stub
		
		HashMap<String, HashSet<String>> asnNetworkMap = new HashMap<String, HashSet<String>>();
		
		String asn = "";
		String ipPrefix = "";
		for(PeerData peer: peerDataList) {
			HashSet<String> ipPrefixSet = new HashSet<String>();
			JSONArray asPath = peer.getAsPath();
			if( asPath != null) {
				
				try {

					asn = "ASN:"+String.valueOf(asPath.getInt(asPath.length()-1));
					peer.setAsn(asn);
					ipPrefix = peer.getPeerName();
						
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else {
				
				asn = "ASN:"+ this.localAsn;
				peer.setAsn(asn);
				ipPrefix = peer.getPeerName();
				
			}
			
			
			
			if(asnNetworkMap.containsKey(asn)) {				
				ipPrefixSet = asnNetworkMap.get(asn);			
			} 
			
			ipPrefixSet.add(ipPrefix);
			asnNetworkMap.put(asn, ipPrefixSet);		
		}
		String asnNetworkMappingStr = createANMJson(asnNetworkMap);
		return asnNetworkMappingStr;
	}






	private String createANMJson(HashMap<String, HashSet<String>> asnNetworkMap) {
		// TODO Auto-generated method stub
		JSONObject jso = new JSONObject();
		JSONArray jsa = new JSONArray();
		
		try {
		
			for(String asn : asnNetworkMap.keySet()) {
			
				JSONObject jsasn = new JSONObject();
				JSONArray jsNetwork = new JSONArray();
				for(String network : asnNetworkMap.get(asn)) {
					jsNetwork.put(network);
									
				}
				
				jsasn.put("asn", asn);
				jsasn.put("networks", jsNetwork);
				jsa.put(jsasn);

			}
			jso.put("asnNetwrokMapping", jsa);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
				
		return jso.toString();
	}






	private JSONObject createPeerPathFlowJson(HashMap<LinkPair, ArrayList<NodeInOutPort>> pathFlowMap, String asnNetworkMap,
			String asnId) {
		// TODO Auto-generated method stub
		JSONObject peerListJso = new JSONObject();
		
		try {
					
			JSONArray peerJsa = new JSONArray();
					
			if(!pathFlowMap.isEmpty()) {
				
				int index = 1;
				for(LinkPair linkPair : pathFlowMap.keySet()) {
					ArrayList<NodeInOutPort> niopl = pathFlowMap.get(linkPair);
							
					JSONArray jsa = new JSONArray();

					
					for(NodeInOutPort np : niopl ) {
						
						JSONObject npJso = new JSONObject();
						npJso.put("switchId", np.getNodeName());
						npJso.put("inPort", np.getInPort());
						npJso.put("outPort", np.getOutPort());
						npJso.put("flowEntry", np.getFlowEntryJson());
						jsa.put(npJso);
						
					}
					JSONObject peerJso = new JSONObject();
					peerJso.put("srcPeer", linkPair.getSrc());
					peerJso.put("dstPeer", linkPair.getDst());
					
					JSONObject asnNetworkMapJso = new JSONObject(asnNetworkMap);
					JSONArray asnNetworksJsa = asnNetworkMapJso.getJSONArray("asnNetwrokMapping");
					String srcAsn = "";
					String dstAsn = "";
					for(int x=0; x < asnNetworksJsa.length(); x++) {
						JSONObject asnJso = asnNetworksJsa.getJSONObject(x);
						JSONArray networksJsa = asnJso.getJSONArray("networks");
						for(int y=0; y < networksJsa.length(); y++) {
													
							if(networksJsa.getString(y).equals(linkPair.getSrc())) {
								srcAsn = asnJso.getString("asn");
							}
							
							if(networksJsa.getString(y).equals(linkPair.getDst())) {
							dstAsn = asnJso.getString("asn");
							}
						}
					}
					
					//peerJso.put("asnid", asnId);
					peerJso.put("srcAsn", srcAsn);
					peerJso.put("dstAsn", dstAsn);
					peerJso.put("linkId", linkPair.getLinkId());
					peerJso.put("linkType", linkPair.getLinkType().toString());
					peerJso.put("globalAsnPath", linkPair.getGlobalAsnPath());
					peerJso.put("peerIndex", "peerNo"+index);
					index++;
					peerJso.put("SDNPath", jsa);	
					
					peerJsa.put(peerJso);
				}
				
				peerListJso.put("peers", peerJsa);
				peerListJso.put("asnid", asnId);
				
			} else {
				
				peerListJso.put("error", "pathFlowMap is empty");
			}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return peerListJso;			
	}






	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	
	
	private HashSet<LinkPair> createLinks(ArrayList<PeerData>  peerDataList, String OnosIP) {
		
		HashSet<LinkPair> linkSet = new HashSet<LinkPair>();
		try {
			
			for(PeerData peer : peerDataList) {
		
				JSONArray asPath = peer.getAsPath();
				if( asPath != null){
					
					if(asPath.length() == 1) {
						String dst = "ASN:" + String.valueOf(asPath.getInt(0));
						String linkId = OnosIP + "-" + dst;
						linkSet.add(new LinkPair("ASN:"+this.localAsn, dst, linkId, LinkType.DOMAINlINK));
					} else {
						int count = asPath.length()-1;
						for( int i = count ; i >= 0; i--) {
											
							if( i== 0) {						
								String dst = "ASN:" + String.valueOf(asPath.getInt(i));
								String linkId = OnosIP + "-" + dst;
								linkSet.add(new LinkPair("ASN:"+this.localAsn, dst, linkId, LinkType.DOMAINlINK));
								
							} else {
								String src = "ASN:" + String.valueOf(asPath.getInt(i-1));
								String dst = "ASN:" + String.valueOf(asPath.getInt(i));
								String linkId =  src+ "-" + dst;
								linkSet.add(new LinkPair(src, dst, linkId, LinkType.DOMAINlINK));
							}				
						}			
					}
					
				}
					
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return linkSet;
	}
	
	
	
	private HashSet<TopologyNodeData> createNodes(HashSet<LinkPair> linkSet) {
		// TODO Auto-generated method stub
		

		HashSet<TopologyNodeData> nodeDataSet = new HashSet<TopologyNodeData>();
		
		
		String asn = "ASN:"+ this.localAsn;
		
		if( nodeDataSet.isEmpty()) {
			nodeDataSet.add(new TopologyNodeData(asn, asn, "sdn"));
		}
		
		
		if(!linkSet.isEmpty()) {
					
			for( LinkPair link : linkSet ) {
				TopologyNodeData newNode1 = new TopologyNodeData(link.getSrc(), link.getSrc(), "normal");
				TopologyNodeData newNode2 = new TopologyNodeData(link.getDst(), link.getDst(), "normal");				
				nodeDataSet = addNodeToNodeSet(nodeDataSet, newNode1);
				nodeDataSet = addNodeToNodeSet(nodeDataSet, newNode2);
			}			
		}
		
		return nodeDataSet;
	}
	
	
	private HashSet<TopologyNodeData> addNodeToNodeSet(HashSet<TopologyNodeData> nodeDataSet, TopologyNodeData newNode) {
		
		boolean same = false;
		
		for(TopologyNodeData node : nodeDataSet) {
			
			if( node.getId().equals(newNode.getId())) {
				
				same = true;
				break;
			}
		}
		
		if(!same) {
			nodeDataSet.add(newNode);
		}
		
		return nodeDataSet;
	};
	
	
	
	
	private String createTopologyString(HashSet<TopologyNodeData> nodeDataSet, HashSet<LinkPair> linkSet) {
		// TODO Auto-generated method stub
		
		String result="";
		String asn = "ASN:"+ this.localAsn;
		//String nodeStrFirst = "{ data: { id: \'"+ asn+ "\', name: \'"+ asn+ "\', type: \'sdn\', ";
		//String nodeStrLast = "}, classes: \'nodes\' }";
		String linkStr = "";			
		String jsonStr = asnMapFileLoad(this.getServletContext());
		String [] onosAsnStr = asn.split(":");
		//String nodeStr = nodeStrFirst+nodeStrLast;
		String nodeStr = "";
		
		
		if(!nodeDataSet.isEmpty()) {
			
			for(TopologyNodeData node : nodeDataSet) {
				
				String asnName = "";
				String asnStr = node.getName();				
				
				asnName = asnNameFinderForAsnMap(jsonStr, asnStr, true);				
				
				if( asnName.equals("")) {
					
					nodeStr = nodeStr+ "{ data: { id: \'"+ node.getId() + "\', name: \'"+ node.getName()+ "\', type: \'"+ node.getType() +"\' }, classes: \'nodes\' },";
					
				} else {
					
					nodeStr = nodeStr+ "{ data: { id: \'"+ node.getId() + "\', name: \'"+ asnName+ "\', type: \'"+ node.getType() + "\' }, classes: \'nodes\' },";
					
				}				
			}			
		}
					
		if(!linkSet.isEmpty()) {		
			
			for(LinkPair linkPair : linkSet) {
					
				if(linkStr == "" ) {
					linkStr = "{ data: { id: \'"+ linkPair.getLinkId()+ 
							"\', source: \'"+ linkPair.getSrc()+ 
							"\', target: \'"+ linkPair.getDst()+ 
							"\' }, classes: \'edges\' }";
				}
				else {
					linkStr = linkStr+ ",{ data: { id: \'"+ linkPair.getLinkId()+ 
							"\', source: \'"+ linkPair.getSrc()+ 
							"\', target: \'"+ linkPair.getDst()+ 
							"\' }, classes: \'edges\' }";
				}
			}
			
			result = "nodes: ["+ nodeStr+ "], edges: ["+linkStr +"]" ;
			
		}
		else {
			result = "nodes: ["+ nodeStr+ "]" ;
		}
	
		return result;
	}
	
	
	
	/*
	private String createTopologyString(ArrayList<PeerData> peerDataList, String localOnosIP, HashSet<LinkPair> linkSet) {
		// TODO Auto-generated method stub
		
		String result="";
		String asn = "ASN:"+ this.localAsn;
		String nodeStrFirst = "{ data: { id: \'"+ asn+ "\', name: \'"+ asn+ "\', type: \'sdn\', ";
		String nodeStrLast = "}, classes: \'nodes\' }";
		String linkStr = "";		
		
		String jsonStr = asnMapFileLoad(this.getServletContext());
		
		
	
		String [] onosAsnStr = asn.split(":");
		//String onosAsnName = asnNameFinderForAsnMap(jsonStr, onosAsnStr, false);
		
		//String nodeStr = nodeStrFirst+ "asnName: \'"+ onosAsnName+ "\' "+nodeStrLast;
		String nodeStr = nodeStrFirst+nodeStrLast;
		
		

		
		if(!linkSet.isEmpty()) {
			
			
			for(LinkPair linkPair : linkSet) {
				String asnName = "";
				String [] asnStr = linkPair.getDst().split(":");
				
				
				asnName = asnNameFinderForAsnMap(jsonStr, asnStr, true);
				
				/*
				 
				try {
					JSONObject asnsJso = new JSONObject(jsonStr);
					this.asnMapJsonString = asnsJso;
					JSONArray asnsJsa = asnsJso.getJSONArray("asnmap");
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
				*/
				
				/*
				if( asnName.equals("")) {
					
					nodeStr = nodeStr+ ",{ data: { id: \'"+ linkPair.getDst()+ "\', name: \'"+ linkPair.getDst()+ "\', type: \'normal\' }, classes: \'nodes\' }";
					
				} else {
					
					nodeStr = nodeStr+ ",{ data: { id: \'"+ linkPair.getDst()+ "\', name: \'"+ asnName+ "\', type: \'normal\' }, classes: \'nodes\' }";
					
				}
				
				
				if(linkStr == "" ) {
					linkStr = "{ data: { id: \'"+ linkPair.getLinkId()+ 
							"\', source: \'"+ linkPair.getSrc()+ 
							"\', target: \'"+ linkPair.getDst()+ 
							"\' }, classes: \'edges\' }";
				}
				else {
					linkStr = linkStr+ ",{ data: { id: \'"+ linkPair.getLinkId()+ 
							"\', source: \'"+ linkPair.getSrc()+ 
							"\', target: \'"+ linkPair.getDst()+ 
							"\' }, classes: \'edges\' }";
				}
			}
			
			result = "nodes: ["+ nodeStr+ "], edges: ["+linkStr +"]" ;
			
		}
		else {
			result = "nodes: ["+ nodeStr+ "]" ;
		}
		
		/*
		 nodes: [
			      { data: { id: 'desktop', name: 'Cytoscape' } },
			      { data: { id: 'js', name: 'Cytoscape.js' } }
			    ],
			    edges: [
			      { data: { source: 'desktop', target: 'js' } }
			    ]
		*/
		/*
		return result;
	}*/



	private String asnNameFinderForAsnMap(String jsonStr, String asnStr, boolean setasnMapJsonString) {
		
		String asnName = "";
		asnStr = asnStr.substring(4,asnStr.length());
		try {
			JSONObject asnsJso = new JSONObject(jsonStr);
			
			if(setasnMapJsonString) {
				this.asnMapJsonString = asnsJso;
			}
			
			JSONArray asnsJsa = asnsJso.getJSONArray("asnmap");
			for(int i=0; i < asnsJsa.length(); i++) {
				
				JSONObject asnJso = asnsJsa.getJSONObject(i);
							
				if( asnStr.equals(asnJso.getString("asnid"))) {

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



	private String asnMapFileLoad(ServletContext servletContext) {
		// TODO Auto-generated method stub
		String json = "";
		
		try {
			
			InputStream in = servletContext.getResourceAsStream("/SdnIpWebUI/data/asnmap");
			
			InputStreamReader reader = new InputStreamReader(in);
		
			BufferedReader br = new BufferedReader(reader);
			
			
			
			while(br.ready()) {

				json = json + br.readLine();
				
			}
			
			
			if(json != null) {
				
				Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
				json = pattern.matcher(json).replaceAll("");
			}
			
			
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
		
		return json;
	}
	
}
