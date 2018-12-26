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


@WebServlet( name="SDNIPToplogyHandlerFixData" , urlPatterns={"/topologyDemo.do"}, 
			initParams={
					@WebInitParam(name="ASNMAP", value="/SdnIpWebUI/data/asnmap")
			})
@MultipartConfig
public class SDNIPToplogyHandlerFixData extends HttpServlet {



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
		/*OnosCommunicationService onosComm = new OnosCommunicationService(OnosCommunicationService.account, OnosCommunicationService.pwd);
		String jsonString = onosComm.getBgpRoutes();
		String neighborJsonString = onosComm.getBgpNeighbor();
		ArrayList<String> peerList = new ArrayList<String>();
		ArrayList<PeerData> peerDataList = new ArrayList<PeerData>();
		//DBconnectionService db = new DBconnectionService();

				
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
		String asnNetworkMap = createAsnNetworkMap(peerDataList);
		String topologyString = createTopologyString(peerDataList, OnosCommunicationService.localOnosIP, linkSet);
		
		DomainPathHandle dmPathHandle = new DomainPathHandle(peerDataList, onosComm);
		HashMap<LinkPair, ArrayList<NodeInOutPort>> pathFlowMap = dmPathHandle.findFlowPath(this.asnMapJsonString);
		
		
		JSONObject peerPathJso = createPeerPathFlowJson(pathFlowMap, asnNetworkMap);
		*/
		
		String peerPath = "{\"peers\":[{\"dstPeer\":\"192.168.200.0/24\",\"linkId\":\"140.116.158.224/27 (NCKU)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.200.0/24 (NICT)\",\"srcPeer\":\"140.116.158.224/27\",\"srcAsn\":\"ASN:65332\",\"dstAsn\":\"ASN:65500\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo1\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":477440,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026443,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":9,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.200.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949997534562\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"9\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.113.10.0/24\",\"linkId\":\"192.168.228.0/24 (ASTI)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.10.0/24 (NCTU-1)\",\"srcPeer\":\"192.168.228.0/24\",\"srcAsn\":\"ASN:65432\",\"dstAsn\":\"ASN:65110\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo2\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026415,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.10.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950799626279\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"10.209.0.0/24\",\"linkId\":\"192.168.50.0/24 (iCAIR)&nbsp;&nbsp;->&nbsp;&nbsp;10.209.0.0/24 (NCHC-HC_LAB)\",\"srcPeer\":\"192.168.50.0/24\",\"srcAsn\":\"ASN:65165\",\"dstAsn\":\"ASN:65209\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo3\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":500298,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026444,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":10,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.209.0.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948461269627\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"10\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.113.20.0/24\",\"linkId\":\"192.168.50.0/24 (iCAIR)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.20.0/24 (NCTU_LAB)\",\"srcPeer\":\"192.168.50.0/24\",\"srcAsn\":\"ASN:65165\",\"dstAsn\":\"ASN:65120\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo4\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026451,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":10,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.20.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339947173098781\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"10\",\"outPort\":\"7\"}]},{\"dstPeer\":\"192.168.50.0/24\",\"linkId\":\"10.113.10.0/24 (NCTU-1)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.50.0/24 (iCAIR)\",\"srcPeer\":\"10.113.10.0/24\",\"srcAsn\":\"ASN:65110\",\"dstAsn\":\"ASN:65165\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo5\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"52:54:00:12:34:56\"},{\"port\":\"10\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":68825,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026419,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.50.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949329819405\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"10\"}]},{\"dstPeer\":\"10.209.0.0/24\",\"linkId\":\"140.116.158.224/27 (NCKU)&nbsp;&nbsp;->&nbsp;&nbsp;10.209.0.0/24 (NCHC-HC_LAB)\",\"srcPeer\":\"140.116.158.224/27\",\"srcAsn\":\"ASN:65332\",\"dstAsn\":\"ASN:65209\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo6\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":500298,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026424,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":9,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.209.0.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949447244772\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"9\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.209.0.0/24\",\"linkId\":\"10.113.10.0/24 (NCTU-1)&nbsp;&nbsp;->&nbsp;&nbsp;10.209.0.0/24 (NCHC-HC_LAB)\",\"srcPeer\":\"10.113.10.0/24\",\"srcAsn\":\"ASN:65110\",\"dstAsn\":\"ASN:65209\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo7\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":500298,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026421,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.209.0.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339947704507325\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.211.211.0/24\",\"linkId\":\"10.113.10.0/24 (NCTU-1)&nbsp;&nbsp;->&nbsp;&nbsp;10.211.211.0/24 (NCHC-HC)\",\"srcPeer\":\"10.113.10.0/24\",\"srcAsn\":\"ASN:65110\",\"dstAsn\":\"ASN:65211\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo8\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":500298,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026422,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.211.211.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339951019892698\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"140.116.158.224/27\",\"linkId\":\"10.113.10.0/24 (NCTU-1)&nbsp;&nbsp;->&nbsp;&nbsp;140.116.158.224/27 (NCKU)\",\"srcPeer\":\"10.113.10.0/24\",\"srcAsn\":\"ASN:65110\",\"dstAsn\":\"ASN:65332\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo9\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"9C:8E:99:1A:50:EA\"},{\"port\":\"9\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":235,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":1819718,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026471,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"140.116.158.224/27\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949535998944\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"9\"}]},{\"dstPeer\":\"192.168.50.0/24\",\"linkId\":\"10.113.20.0/24 (NCTU_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.50.0/24 (iCAIR)\",\"srcPeer\":\"10.113.20.0/24\",\"srcAsn\":\"ASN:65120\",\"dstAsn\":\"ASN:65165\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo10\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"52:54:00:12:34:56\"},{\"port\":\"10\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":68825,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026419,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.50.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949329819405\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"10\"}]},{\"dstPeer\":\"192.168.50.0/24\",\"linkId\":\"10.209.0.0/24 (NCHC-HC_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.50.0/24 (iCAIR)\",\"srcPeer\":\"10.209.0.0/24\",\"srcAsn\":\"ASN:65209\",\"dstAsn\":\"ASN:65165\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo11\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"52:54:00:12:34:56\"},{\"port\":\"10\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":68825,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026452,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.50.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950479339399\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"10\"}]},{\"dstPeer\":\"192.168.228.0/24\",\"linkId\":\"10.113.10.0/24 (NCTU-1)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.228.0/24 (ASTI)\",\"srcPeer\":\"10.113.10.0/24\",\"srcAsn\":\"ASN:65110\",\"dstAsn\":\"ASN:65432\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo12\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":477410,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026428,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.228.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949308463976\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"192.168.228.0/24\",\"linkId\":\"140.116.158.224/27 (NCKU)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.228.0/24 (ASTI)\",\"srcPeer\":\"140.116.158.224/27\",\"srcAsn\":\"ASN:65332\",\"dstAsn\":\"ASN:65432\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo13\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":477410,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026426,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":9,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.228.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339947948280286\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"9\",\"outPort\":\"8\"}]},{\"dstPeer\":\"192.168.50.0/24\",\"linkId\":\"10.211.211.0/24 (NCHC-HC)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.50.0/24 (iCAIR)\",\"srcPeer\":\"10.211.211.0/24\",\"srcAsn\":\"ASN:65211\",\"dstAsn\":\"ASN:65165\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo14\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"52:54:00:12:34:56\"},{\"port\":\"10\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":68825,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026452,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.50.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950479339399\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"10\"}]},{\"dstPeer\":\"10.113.20.0/24\",\"linkId\":\"10.209.0.0/24 (NCHC-HC_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.20.0/24 (NCTU_LAB)\",\"srcPeer\":\"10.209.0.0/24\",\"srcAsn\":\"ASN:65209\",\"dstAsn\":\"ASN:65120\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo15\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026429,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.20.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949121311692\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"192.168.200.0/24\",\"linkId\":\"192.168.50.0/24 (iCAIR)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.200.0/24 (NICT)\",\"srcPeer\":\"192.168.50.0/24\",\"srcAsn\":\"ASN:65165\",\"dstAsn\":\"ASN:65500\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo16\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":477440,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026412,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":10,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.200.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339947325304740\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"10\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.113.10.0/24\",\"linkId\":\"10.211.211.0/24 (NCHC-HC)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.10.0/24 (NCTU-1)\",\"srcPeer\":\"10.211.211.0/24\",\"srcAsn\":\"ASN:65211\",\"dstAsn\":\"ASN:65110\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo17\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026415,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.10.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950799626279\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"10.113.10.0/24\",\"linkId\":\"140.116.158.224/27 (NCKU)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.10.0/24 (NCTU-1)\",\"srcPeer\":\"140.116.158.224/27\",\"srcAsn\":\"ASN:65332\",\"dstAsn\":\"ASN:65110\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo18\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026418,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":9,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.10.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948760787088\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"9\",\"outPort\":\"7\"}]},{\"dstPeer\":\"140.116.158.224/27\",\"linkId\":\"10.211.211.0/24 (NCHC-HC)&nbsp;&nbsp;->&nbsp;&nbsp;140.116.158.224/27 (NCKU)\",\"srcPeer\":\"10.211.211.0/24\",\"srcAsn\":\"ASN:65211\",\"dstAsn\":\"ASN:65332\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo19\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"9C:8E:99:1A:50:EA\"},{\"port\":\"9\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":235,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":1819718,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026471,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"140.116.158.224/27\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948925481293\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"9\"}]},{\"dstPeer\":\"140.116.158.224/27\",\"linkId\":\"192.168.50.0/24 (iCAIR)&nbsp;&nbsp;->&nbsp;&nbsp;140.116.158.224/27 (NCKU)\",\"srcPeer\":\"192.168.50.0/24\",\"srcAsn\":\"ASN:65165\",\"dstAsn\":\"ASN:65332\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo20\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"9C:8E:99:1A:50:EA\"},{\"port\":\"9\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":235,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":1819718,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026470,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":10,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"140.116.158.224/27\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948301178851\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"10\",\"outPort\":\"9\"}]},{\"dstPeer\":\"10.113.20.0/24\",\"linkId\":\"192.168.228.0/24 (ASTI)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.20.0/24 (NCTU_LAB)\",\"srcPeer\":\"192.168.228.0/24\",\"srcAsn\":\"ASN:65432\",\"dstAsn\":\"ASN:65120\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo21\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026429,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.20.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949121311692\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"192.168.4.0/24\",\"linkId\":\"10.113.10.0/24 (NCTU-1)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.4.0/24 (HUST)\",\"srcPeer\":\"10.113.10.0/24\",\"srcAsn\":\"ASN:65110\",\"dstAsn\":\"ASN:65300\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo22\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":436695,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026435,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.4.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339951132747880\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.113.20.0/24\",\"linkId\":\"192.168.200.0/24 (NICT)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.20.0/24 (NCTU_LAB)\",\"srcPeer\":\"192.168.200.0/24\",\"srcAsn\":\"ASN:65500\",\"dstAsn\":\"ASN:65120\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo23\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026429,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.20.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949121311692\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"192.168.4.0/24\",\"linkId\":\"140.116.158.224/27 (NCKU)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.4.0/24 (HUST)\",\"srcPeer\":\"140.116.158.224/27\",\"srcAsn\":\"ASN:65332\",\"dstAsn\":\"ASN:65300\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo24\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":436695,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026423,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":9,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.4.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950227749878\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"9\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.211.211.0/24\",\"linkId\":\"192.168.50.0/24 (iCAIR)&nbsp;&nbsp;->&nbsp;&nbsp;10.211.211.0/24 (NCHC-HC)\",\"srcPeer\":\"192.168.50.0/24\",\"srcAsn\":\"ASN:65165\",\"dstAsn\":\"ASN:65211\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo25\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":500298,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026441,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":10,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.211.211.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948286854342\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"10\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.113.20.0/24\",\"linkId\":\"192.168.4.0/24 (HUST)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.20.0/24 (NCTU_LAB)\",\"srcPeer\":\"192.168.4.0/24\",\"srcAsn\":\"ASN:65300\",\"dstAsn\":\"ASN:65120\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo26\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026429,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.20.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949121311692\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"10.113.10.0/24\",\"linkId\":\"192.168.4.0/24 (HUST)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.10.0/24 (NCTU-1)\",\"srcPeer\":\"192.168.4.0/24\",\"srcAsn\":\"ASN:65300\",\"dstAsn\":\"ASN:65110\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo27\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026415,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.10.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950799626279\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"140.116.158.224/27\",\"linkId\":\"192.168.4.0/24 (HUST)&nbsp;&nbsp;->&nbsp;&nbsp;140.116.158.224/27 (NCKU)\",\"srcPeer\":\"192.168.4.0/24\",\"srcAsn\":\"ASN:65300\",\"dstAsn\":\"ASN:65332\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo28\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"9C:8E:99:1A:50:EA\"},{\"port\":\"9\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":235,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":1819718,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026471,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"140.116.158.224/27\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948925481293\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"9\"}]},{\"dstPeer\":\"192.168.50.0/24\",\"linkId\":\"140.116.158.224/27 (NCKU)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.50.0/24 (iCAIR)\",\"srcPeer\":\"140.116.158.224/27\",\"srcAsn\":\"ASN:65332\",\"dstAsn\":\"ASN:65165\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo29\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"52:54:00:12:34:56\"},{\"port\":\"10\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":68825,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026447,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":9,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.50.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339947924137956\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"9\",\"outPort\":\"10\"}]},{\"dstPeer\":\"192.168.50.0/24\",\"linkId\":\"192.168.228.0/24 (ASTI)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.50.0/24 (iCAIR)\",\"srcPeer\":\"192.168.228.0/24\",\"srcAsn\":\"ASN:65432\",\"dstAsn\":\"ASN:65165\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo30\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"52:54:00:12:34:56\"},{\"port\":\"10\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":68825,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026452,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.50.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950479339399\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"10\"}]},{\"dstPeer\":\"192.168.50.0/24\",\"linkId\":\"192.168.200.0/24 (NICT)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.50.0/24 (iCAIR)\",\"srcPeer\":\"192.168.200.0/24\",\"srcAsn\":\"ASN:65500\",\"dstAsn\":\"ASN:65165\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo31\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"52:54:00:12:34:56\"},{\"port\":\"10\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":68825,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026452,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.50.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950479339399\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"10\"}]},{\"dstPeer\":\"10.113.10.0/24\",\"linkId\":\"10.209.0.0/24 (NCHC-HC_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.10.0/24 (NCTU-1)\",\"srcPeer\":\"10.209.0.0/24\",\"srcAsn\":\"ASN:65209\",\"dstAsn\":\"ASN:65110\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo32\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026415,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.10.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950799626279\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"192.168.200.0/24\",\"linkId\":\"10.113.20.0/24 (NCTU_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.200.0/24 (NICT)\",\"srcPeer\":\"10.113.20.0/24\",\"srcAsn\":\"ASN:65120\",\"dstAsn\":\"ASN:65500\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo33\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":477440,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026437,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.200.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948065603482\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.209.0.0/24\",\"linkId\":\"10.113.20.0/24 (NCTU_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;10.209.0.0/24 (NCHC-HC_LAB)\",\"srcPeer\":\"10.113.20.0/24\",\"srcAsn\":\"ASN:65120\",\"dstAsn\":\"ASN:65209\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo34\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":500298,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026421,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.209.0.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339947704507325\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"140.116.158.224/27\",\"linkId\":\"192.168.228.0/24 (ASTI)&nbsp;&nbsp;->&nbsp;&nbsp;140.116.158.224/27 (NCKU)\",\"srcPeer\":\"192.168.228.0/24\",\"srcAsn\":\"ASN:65432\",\"dstAsn\":\"ASN:65332\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo35\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"9C:8E:99:1A:50:EA\"},{\"port\":\"9\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":235,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":1819718,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026471,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"140.116.158.224/27\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948925481293\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"9\"}]},{\"dstPeer\":\"10.113.10.0/24\",\"linkId\":\"192.168.50.0/24 (iCAIR)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.10.0/24 (NCTU-1)\",\"srcPeer\":\"192.168.50.0/24\",\"srcAsn\":\"ASN:65165\",\"dstAsn\":\"ASN:65110\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo36\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026433,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":10,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.10.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339947194858868\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"10\",\"outPort\":\"7\"}]},{\"dstPeer\":\"192.168.200.0/24\",\"linkId\":\"10.113.10.0/24 (NCTU-1)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.200.0/24 (NICT)\",\"srcPeer\":\"10.113.10.0/24\",\"srcAsn\":\"ASN:65110\",\"dstAsn\":\"ASN:65500\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo37\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":477440,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026437,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.200.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948065603482\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"140.116.158.224/27\",\"linkId\":\"10.113.20.0/24 (NCTU_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;140.116.158.224/27 (NCKU)\",\"srcPeer\":\"10.113.20.0/24\",\"srcAsn\":\"ASN:65120\",\"dstAsn\":\"ASN:65332\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo38\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"9C:8E:99:1A:50:EA\"},{\"port\":\"9\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":235,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":1819718,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026471,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"140.116.158.224/27\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949535998944\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"9\"}]},{\"dstPeer\":\"192.168.4.0/24\",\"linkId\":\"10.113.20.0/24 (NCTU_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.4.0/24 (HUST)\",\"srcPeer\":\"10.113.20.0/24\",\"srcAsn\":\"ASN:65120\",\"dstAsn\":\"ASN:65300\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo39\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":436695,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026435,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.4.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339951132747880\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"10.113.20.0/24\",\"linkId\":\"140.116.158.224/27 (NCKU)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.20.0/24 (NCTU_LAB)\",\"srcPeer\":\"140.116.158.224/27\",\"srcAsn\":\"ASN:65332\",\"dstAsn\":\"ASN:65120\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo40\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026427,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":9,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.20.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948299624882\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"9\",\"outPort\":\"7\"}]},{\"dstPeer\":\"140.116.158.224/27\",\"linkId\":\"192.168.200.0/24 (NICT)&nbsp;&nbsp;->&nbsp;&nbsp;140.116.158.224/27 (NCKU)\",\"srcPeer\":\"192.168.200.0/24\",\"srcAsn\":\"ASN:65500\",\"dstAsn\":\"ASN:65332\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo41\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"9C:8E:99:1A:50:EA\"},{\"port\":\"9\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":235,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":1819718,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026471,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"140.116.158.224/27\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948925481293\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"9\"}]},{\"dstPeer\":\"10.211.211.0/24\",\"linkId\":\"140.116.158.224/27 (NCKU)&nbsp;&nbsp;->&nbsp;&nbsp;10.211.211.0/24 (NCHC-HC)\",\"srcPeer\":\"140.116.158.224/27\",\"srcAsn\":\"ASN:65332\",\"dstAsn\":\"ASN:65211\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo42\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":500298,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026432,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":9,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.211.211.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950260431636\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"9\",\"outPort\":\"8\"}]},{\"dstPeer\":\"140.116.158.224/27\",\"linkId\":\"10.209.0.0/24 (NCHC-HC_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;140.116.158.224/27 (NCKU)\",\"srcPeer\":\"10.209.0.0/24\",\"srcAsn\":\"ASN:65209\",\"dstAsn\":\"ASN:65332\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo43\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"9C:8E:99:1A:50:EA\"},{\"port\":\"9\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":235,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":1819718,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026471,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"140.116.158.224/27\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948925481293\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"9\"}]},{\"dstPeer\":\"10.113.10.0/24\",\"linkId\":\"192.168.200.0/24 (NICT)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.10.0/24 (NCTU-1)\",\"srcPeer\":\"192.168.200.0/24\",\"srcAsn\":\"ASN:65500\",\"dstAsn\":\"ASN:65110\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo44\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026415,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.10.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950799626279\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"10.113.20.0/24\",\"linkId\":\"10.211.211.0/24 (NCHC-HC)&nbsp;&nbsp;->&nbsp;&nbsp;10.113.20.0/24 (NCTU_LAB)\",\"srcPeer\":\"10.211.211.0/24\",\"srcAsn\":\"ASN:65211\",\"dstAsn\":\"ASN:65120\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo45\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:50:56:B7:F1:87\"},{\"port\":\"7\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":2816,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026429,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.113.20.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949121311692\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"7\"}]},{\"dstPeer\":\"10.211.211.0/24\",\"linkId\":\"10.113.20.0/24 (NCTU_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;10.211.211.0/24 (NCHC-HC)\",\"srcPeer\":\"10.113.20.0/24\",\"srcAsn\":\"ASN:65120\",\"dstAsn\":\"ASN:65211\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo46\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":500298,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026422,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"10.211.211.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339951019892698\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"192.168.228.0/24\",\"linkId\":\"10.113.20.0/24 (NCTU_LAB)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.228.0/24 (ASTI)\",\"srcPeer\":\"10.113.20.0/24\",\"srcAsn\":\"ASN:65120\",\"dstAsn\":\"ASN:65432\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo47\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":477410,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026428,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":7,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.228.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949308463976\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"7\",\"outPort\":\"8\"}]},{\"dstPeer\":\"192.168.50.0/24\",\"linkId\":\"192.168.4.0/24 (HUST)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.50.0/24 (iCAIR)\",\"srcPeer\":\"192.168.4.0/24\",\"srcAsn\":\"ASN:65300\",\"dstAsn\":\"ASN:65165\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo48\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"52:54:00:12:34:56\"},{\"port\":\"10\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":68825,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026452,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":8,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.50.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339950479339399\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"8\",\"outPort\":\"10\"}]},{\"dstPeer\":\"192.168.4.0/24\",\"linkId\":\"192.168.50.0/24 (iCAIR)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.4.0/24 (HUST)\",\"srcPeer\":\"192.168.50.0/24\",\"srcAsn\":\"ASN:65165\",\"dstAsn\":\"ASN:65300\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo49\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":436695,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026449,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":10,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.4.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339949391285013\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"10\",\"outPort\":\"8\"}]},{\"dstPeer\":\"192.168.228.0/24\",\"linkId\":\"192.168.50.0/24 (iCAIR)&nbsp;&nbsp;->&nbsp;&nbsp;192.168.228.0/24 (ASTI)\",\"srcPeer\":\"192.168.50.0/24\",\"srcAsn\":\"ASN:65165\",\"dstAsn\":\"ASN:65432\",\"linkType\":\"DOMAINlINK\",\"peerIndex\":\"peerNo50\",\"SDNPath\":[{\"flowEntry\":{\"treatment\":{\"instructions\":[{\"subtype\":\"ETH_DST\",\"type\":\"L2MODIFICATION\",\"mac\":\"00:0A:F7:82:8A:FF\"},{\"port\":\"8\",\"type\":\"OUTPUT\"}],\"deferred\":[]},\"liveType\":\"UNKNOWN\",\"groupId\":0,\"priority\":220,\"deviceId\":\"of:81a97072cfb5dde1\",\"timeout\":0,\"life\":477410,\"packets\":-1,\"isPermanent\":true,\"lastSeen\":1525836026439,\"bytes\":0,\"appId\":\"org.onosproject.net.intent\",\"tableId\":0,\"selector\":{\"criteria\":[{\"port\":10,\"type\":\"IN_PORT\"},{\"ethType\":\"0x800\",\"type\":\"ETH_TYPE\"},{\"ip\":\"192.168.228.0/24\",\"type\":\"IPV4_DST\"}]},\"id\":\"34339948566038825\",\"state\":\"ADDED\"},\"switchId\":\"of:81a97072cfb5dde1\",\"inPort\":\"10\",\"outPort\":\"8\"}]}]}";
		String topologyString = "nodes: [{ data: { id: \'211.73.75.9\', name: \'ASN:65115\', type: \'sdn\', asnName: \'NCHC-TN\' }, classes: \'nodes\' },{ data: { id: \'ASN:65120\', name: \'NCTU_LAB\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65165\', name: \'iCAIR\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65211\', name: \'NCHC-HC\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65211\', name: \'NCHC-HC\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65211\', name: \'NCHC-HC\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65209\', name: \'NCHC-HC_LAB\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65113\', name: \'NCTU-0\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65113\', name: \'NCTU-0\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65432\', name: \'ASTI\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65211\', name: \'NCHC-HC\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65211\', name: \'NCHC-HC\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65500\', name: \'NICT\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65300\', name: \'HUST\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65332\', name: \'NCKU\', type: \'normal\' }, classes: \'nodes\' },{ data: { id: \'ASN:65110\', name: \'NCTU-1\', type: \'normal\' }, classes: \'nodes\' }], edges: [{ data: { id: \'ASN:65113-ASN:65120\', source: \'ASN:65113\', target: \'ASN:65120\' }, classes: \'edges\' },{ data: { id: \'211.73.75.9-ASN:65165\', source: \'211.73.75.9\', target: \'ASN:65165\' }, classes: \'edges\' },{ data: { id: \'211.73.75.9-ASN:65211\', source: \'211.73.75.9\', target: \'ASN:65211\' }, classes: \'edges\' },{ data: { id: \'211.73.75.9-ASN:65211\', source: \'211.73.75.9\', target: \'ASN:65211\' }, classes: \'edges\' },{ data: { id: \'211.73.75.9-ASN:65211\', source: \'211.73.75.9\', target: \'ASN:65211\' }, classes: \'edges\' },{ data: { id: \'ASN:65211-ASN:65209\', source: \'ASN:65211\', target: \'ASN:65209\' }, classes: \'edges\' },{ data: { id: \'211.73.75.9-ASN:65113\', source: \'211.73.75.9\', target: \'ASN:65113\' }, classes: \'edges\' },{ data: { id: \'211.73.75.9-ASN:65113\', source: \'211.73.75.9\', target: \'ASN:65113\' }, classes: \'edges\' },{ data: { id: \'ASN:65211-ASN:65432\', source: \'ASN:65211\', target: \'ASN:65432\' }, classes: \'edges\' },{ data: { id: \'211.73.75.9-ASN:65211\', source: \'211.73.75.9\', target: \'ASN:65211\' }, classes: \'edges\' },{ data: { id: \'211.73.75.9-ASN:65211\', source: \'211.73.75.9\', target: \'ASN:65211\' }, classes: \'edges\' },{ data: { id: \'ASN:65211-ASN:65500\', source: \'ASN:65211\', target: \'ASN:65500\' }, classes: \'edges\' },{ data: { id: \'ASN:65211-ASN:65300\', source: \'ASN:65211\', target: \'ASN:65300\' }, classes: \'edges\' },{ data: { id: \'211.73.75.9-ASN:65332\', source: \'211.73.75.9\', target: \'ASN:65332\' }, classes: \'edges\' },{ data: { id: \'ASN:65113-ASN:65110\', source: \'ASN:65113\', target: \'ASN:65110\' }, classes: \'edges\' }]";
		String asnNetworkMap = "{\"asnNetwrokMapping\":[{\"networks\":[\"10.113.20.0/24\"],\"asn\":\"ASN:65120\"},{\"networks\":[\"192.168.50.0/24\"],\"asn\":\"ASN:65165\"},{\"networks\":[\"10.113.10.0/24\"],\"asn\":\"ASN:65110\"},{\"networks\":[\"192.168.4.0/24\"],\"asn\":\"ASN:65300\"},{\"networks\":[\"192.168.228.0/24\"],\"asn\":\"ASN:65432\"},{\"networks\":[\"10.211.211.0/24\"],\"asn\":\"ASN:65211\"},{\"networks\":[\"140.116.158.224/27\"],\"asn\":\"ASN:65332\"},{\"networks\":[\"192.168.200.0/24\"],\"asn\":\"ASN:65500\"},{\"networks\":[\"211.73.95.32/27\"],\"asn\":\"ASN:65115\"},{\"networks\":[\"10.209.0.0/24\"],\"asn\":\"ASN:65209\"}]}";
		
		//req.setAttribute("peerPathJson", peerPathJso.toString());
		req.setAttribute("peerPathJson", peerPath);
		req.setAttribute("topoJson", topologyString);
		req.setAttribute("asnNetworkMap", asnNetworkMap);
		//db.addPeerData(peerList, onosComm.getLocalOnosIP());		
		RequestDispatcher reqDispatcher = req.getRequestDispatcher("/SdnIpWebUI/topology.jsp");
		reqDispatcher.forward(req, resp);
		
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






	private JSONObject createPeerPathFlowJson(HashMap<LinkPair, ArrayList<NodeInOutPort>> pathFlowMap, String asnNetworkMap) {
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
					
					peerJso.put("srcAsn", srcAsn);
					peerJso.put("dstAsn", dstAsn);
					peerJso.put("linkId", linkPair.getLinkId());
					peerJso.put("linkType", linkPair.getLinkType().toString());
					peerJso.put("peerIndex", "peerNo"+index);
					index++;
					peerJso.put("SDNPath", jsa);	
					
					peerJsa.put(peerJso);
				}
				
				peerListJso.put("peers", peerJsa);
				
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
						linkSet.add(new LinkPair(OnosIP, dst, linkId, LinkType.DOMAINlINK));
					} else {
						int count = asPath.length()-1;
						for( int i = count ; i >= 0; i--) {
											
							if( i== 0) {						
								String dst = "ASN:" + String.valueOf(asPath.getInt(i));
								String linkId = OnosIP + "-" + dst;
								linkSet.add(new LinkPair(OnosIP, dst, linkId, LinkType.DOMAINlINK));
								
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
	
	
	private String createTopologyString(ArrayList<PeerData> peerDataList, String localOnosIP, HashSet<LinkPair> linkSet) {
		// TODO Auto-generated method stub
		
		String result="";
		String asn = "ASN:"+ this.localAsn;
		String nodeStrFirst = "{ data: { id: \'"+ localOnosIP+ "\', name: \'"+ asn+ "\', type: \'sdn\', ";
		String nodeStrLast = "}, classes: \'nodes\' }";
		String linkStr = "";		
		
		String jsonStr = asnMapFileLoad(this.getServletContext());
		
		
	
		String [] onosAsnStr = asn.split(":");
		String onosAsnName = asnNameFinderForAsnMap(jsonStr, onosAsnStr, false);
		
		String nodeStr = nodeStrFirst+ "asnName: \'"+ onosAsnName+ "\' "+nodeStrLast;
		
		

		
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
		
		return result;
	}



	private String asnNameFinderForAsnMap(String jsonStr, String [] asnStr, boolean setasnMapJsonString) {
		
		String asnName = "";
		
		try {
			JSONObject asnsJso = new JSONObject(jsonStr);
			
			if(setasnMapJsonString) {
				this.asnMapJsonString = asnsJso;
			}
			
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
