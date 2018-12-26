package org.nchc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nchc.onosCommunication.OnosCommunicationService;

/**
 * Servlet implementation class SdnIPHttpHandler
 */
@WebServlet( name="SdnIPFixDataHttpHandler" , urlPatterns={"/sdnipDemo.do"})

public class SdnIPFixDataHttpHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SdnIPFixDataHttpHandler() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//OnosCommunicationService onosComm = new OnosCommunicationService("onos", "rocks");
		//System.out.println("account:"+OnosCommunicationService.account+ " pwd:"+OnosCommunicationService.pwd);
		//OnosCommunicationService onosComm = new OnosCommunicationService(OnosCommunicationService.account, OnosCommunicationService.pwd);
		String routes = "{\"routes4\":[{\"prefix\":\"192.168.200.0/24\",\"nextHop\":\"10.140.110.41\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[{\"type\":\"AS_SEQUENCE\",\"segmentAsNumbers\":[65211,65500]}]},\"localPref\":100,\"multiExitDisc\":0},{\"prefix\":\"10.113.20.0/24\",\"nextHop\":\"10.115.1.5\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[{\"type\":\"AS_SEQUENCE\",\"segmentAsNumbers\":[65113,65120]}]},\"localPref\":100,\"multiExitDisc\":0},{\"prefix\":\"10.209.0.0/24\",\"nextHop\":\"10.140.110.41\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[{\"type\":\"AS_SEQUENCE\",\"segmentAsNumbers\":[65211,65209]}]},\"localPref\":100,\"multiExitDisc\":0},{\"prefix\":\"192.168.4.0/24\",\"nextHop\":\"10.140.110.41\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[{\"type\":\"AS_SEQUENCE\",\"segmentAsNumbers\":[65211,65300]}]},\"localPref\":100,\"multiExitDisc\":0},{\"prefix\":\"10.211.211.0/24\",\"nextHop\":\"10.140.110.41\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[{\"type\":\"AS_SEQUENCE\",\"segmentAsNumbers\":[65211]}]},\"localPref\":100,\"multiExitDisc\":0},{\"prefix\":\"192.168.228.0/24\",\"nextHop\":\"10.140.110.41\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[{\"type\":\"AS_SEQUENCE\",\"segmentAsNumbers\":[65211,65432]}]},\"localPref\":100,\"multiExitDisc\":0},{\"prefix\":\"211.73.95.32/27\",\"nextHop\":\"211.73.95.164\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[]},\"localPref\":100,\"multiExitDisc\":0},{\"prefix\":\"192.168.50.0/24\",\"nextHop\":\"10.250.91.52\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[{\"type\":\"AS_SEQUENCE\",\"segmentAsNumbers\":[65165]}]},\"localPref\":100,\"multiExitDisc\":0},{\"prefix\":\"10.113.10.0/24\",\"nextHop\":\"10.115.1.5\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[{\"type\":\"AS_SEQUENCE\",\"segmentAsNumbers\":[65113,65110]}]},\"localPref\":100,\"multiExitDisc\":0},{\"prefix\":\"140.116.158.224/27\",\"nextHop\":\"10.140.116.1\",\"bgpId\":\"192.168.115.8\",\"origin\":\"IGP\",\"asPath\":{\"pathSegments\":[{\"type\":\"AS_SEQUENCE\",\"segmentAsNumbers\":[65332]}]},\"localPref\":100,\"multiExitDisc\":0}],\"routes6\":[]}";
		String neighbors = "{\"bgpneighbor\":[{\"remoteAddress\":\"/211.73.95.164:36221\",\"remoteBgpVersion\":4,\"remoteAs\":65115,\"remoteAs4\":65115,\"remoteHoldtime\":10,\"remoteBgpId\":\"192.168.115.8\",\"remoteIpv4Unicast\":true,\"remoteIpv4Multicast\":false,\"remoteIpv6Unicast\":false,\"remoteIpv6Multicast\":false,\"localAddress\":\"/211.73.75.9:2000\",\"localBgpVersion\":4,\"localAs\":65115,\"localAs4\":65115,\"localHoldtime\":10,\"localBgpId\":\"211.73.75.9\",\"localIpv4Unicast\":true,\"localIpv4Multicast\":false,\"localIpv6Unicast\":false,\"localIpv6Multicast\":false},{\"remoteAddress\":\"/211.73.75.8:48508\",\"remoteBgpVersion\":4,\"remoteAs\":65115,\"remoteAs4\":65115,\"remoteHoldtime\":10,\"remoteBgpId\":\"192.168.115.8\",\"remoteIpv4Unicast\":true,\"remoteIpv4Multicast\":false,\"remoteIpv6Unicast\":false,\"remoteIpv6Multicast\":false,\"localAddress\":\"/211.73.75.9:2000\",\"localBgpVersion\":4,\"localAs\":65115,\"localAs4\":65115,\"localHoldtime\":10,\"localBgpId\":\"211.73.75.9\",\"localIpv4Unicast\":true,\"localIpv4Multicast\":false,\"localIpv6Unicast\":false,\"localIpv6Multicast\":false}]}";
		String speakers = "{\"bgpspeakers\":[{\"speakerName\":\"nchc-quagga\",\"connectPoint_deviceId\":\"of:81a97072cfb5dde1\",\"connectPoint_port\":\"6\",\"vlan\":\"None\",\"peers\":[{\"ip\":\"10.115.1.7\"},{\"ip\":\"10.115.1.6\"},{\"ip\":\"10.140.110.41\"},{\"ip\":\"10.115.1.5\"},{\"ip\":\"10.140.116.1\"},{\"ip\":\"10.115.2.9\"},{\"ip\":\"10.250.91.252\"},{\"ip\":\"10.73.95.36\"},{\"ip\":\"10.250.91.51\"},{\"ip\":\"10.250.91.52\"}]}]}";
		
		request.setAttribute("bgpRoutes", routes);
		request.setAttribute("bgpNeighbors", neighbors);
		request.setAttribute("bgpSpeakers", speakers);
		request.setAttribute("onosip", "211.73.75.9");
		RequestDispatcher jspPage = request.getRequestDispatcher("/SdnIpWebUI/sdnipDemo.jsp");
		jspPage.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String inputString = request.getParameter("onosip");
		String []  inputSplit = inputString.split("@");
		
		if(inputSplit.length > 1) {
			String [] accPwd = inputSplit[0].split(":");
			OnosCommunicationService.localOnosIP = inputSplit[1];
			if(accPwd.length > 1) {
				OnosCommunicationService.account = accPwd[0];
				OnosCommunicationService.pwd = accPwd[1];
			}
			else {
				OnosCommunicationService.account = accPwd[0];
				OnosCommunicationService.pwd = "rocks";
			}
		}
		else {
			OnosCommunicationService.localOnosIP = request.getParameter("onosip");
			OnosCommunicationService.account = "onos";
			OnosCommunicationService.pwd = "rocks";
		}
		doGet(request, response);
	}


}
