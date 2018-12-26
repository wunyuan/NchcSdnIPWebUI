package org.nchc;

import java.io.IOException;
import java.util.regex.Pattern;

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
@WebServlet( name="SdnIPHttpHandler" , urlPatterns={"/sdnip.do"})

public class SdnIPHttpHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SdnIPHttpHandler() {
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
		OnosCommunicationService onosComm = null;
		String routes = "";
		String neighbors = "";
		String speakers = "";
		String inputIpIdPwd = "";
		
		int count = 0;
		for(String ip : OnosCommunicationService.ipAccountPwdMap.keySet()) {
			
			String accountPwdSsl = OnosCommunicationService.ipAccountPwdMap.get(ip);
			String [] idPwdSsl = accountPwdSsl.split(":");
			onosComm = new OnosCommunicationService(idPwdSsl[0], idPwdSsl[1]);
		//	inputIpIdPwd = inputIpIdPwd + accountPwd + "@" + ip + ",";
			String port = OnosCommunicationService.ipPortMap.get(ip);
			String routedata = onosComm.getBgpRoutesWithIPPort(ip, port, idPwdSsl[2]);
			routes = routes + "{ \"onosip\":"+ "\""+ip+ "\"," +routedata.substring(1, routedata.length()) + ",";			
			neighbors = neighbors + onosComm.getBgpNeighborWithIPPort(ip, port, idPwdSsl[2]) + ",";
			speakers = speakers + onosComm.getBgpSpeakersWithIPPort(ip, port, idPwdSsl[2]) + ",";	
			OnosCommunicationService.onosIpSet[count] = ip;
			count++;			
		}
		
		routes = routes.substring(0, routes.length()-1);
		neighbors = neighbors.substring(0, neighbors.length()-1);
		speakers = speakers.substring(0, speakers.length()-1);
		String routeSet = "{\"routeSet\":["+ routes+ "]}";
		String neighborSet = "{\"neighborSet\":["+ neighbors+ "]}";
		String speakerSet = "{\"speakerSet\":["+ speakers+ "]}";
		
	//	request.setAttribute("inputIpIdPwd", inputIpIdPwd.substring(0, inputIpIdPwd.length()-1));
		request.setAttribute("bgpRoutes", routeSet);
		request.setAttribute("bgpNeighbors", neighborSet);
		request.setAttribute("bgpSpeakers", speakerSet);
		RequestDispatcher jspPage = request.getRequestDispatcher("/SdnIpWebUI/sdnip.jsp");
		jspPage.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		OnosCommunicationService.ipAccountPwdMap.clear();
		OnosCommunicationService.ipPortMap.clear();
		
		String sdnipnumber = request.getParameter("sdn-ip_number");
		
		
		if(sdnipnumber== null) {		
			ipStringParser(request, "onosip1");
		}
		else {
			
			int ipNumbers = Integer.valueOf(sdnipnumber);
			
			for(int i=1; i <= ipNumbers; i++) {
				String paraNmae = "onosip"+ i;
				ipStringParser(request, paraNmae);
			}
		}
		
		doGet(request, response);
	}
	
	private void ipStringParser( HttpServletRequest request, String paraName) {
		
		String inputString = request.getParameter(paraName);
		String []  inputSplit = inputString.split("@");
		String ssl = request.getParameter(paraName+"ssl");
		
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
			OnosCommunicationService.localOnosIP = request.getParameter(paraName);
			OnosCommunicationService.account = "onos";
			OnosCommunicationService.pwd = "rocks";
			
		}
		
		if(ssl == null) {		
			ssl = "off";			
		}
		
		String accountPwdSsl = OnosCommunicationService.account+ ":" + OnosCommunicationService.pwd + ":"+ ssl;
		OnosCommunicationService.ipAccountPwdMap.put(OnosCommunicationService.localOnosIP , accountPwdSsl);
		String port = "";
		
	    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
	     
		
		if(request.getParameter(paraName+"port") == null) {
			port = "";
		} else {
			
			if(pattern.matcher(request.getParameter(paraName+"port")).matches() ) {
			
				port = request.getParameter(paraName+"port");
			} else {
				port = "";
			}
			
			
		}
		OnosCommunicationService.ipPortMap.put(OnosCommunicationService.localOnosIP, port);
	}


}
