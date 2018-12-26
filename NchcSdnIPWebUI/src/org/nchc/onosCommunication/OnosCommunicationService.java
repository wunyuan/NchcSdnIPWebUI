package org.nchc.onosCommunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class OnosCommunicationService {
	
	private String authString;
	public static String localOnosIP = "";
	public static String localOnosPort = "";
	public static String localOnosSsl = "";
	public static String account = "onos";
	public static String pwd = "rocks";
	public static HashMap<String,String> ipAccountPwdMap = new HashMap<String,String>() ;
	public static HashMap<String,String> ipPortMap = new HashMap<String,String>() ;
	public static String [] onosIpSet = new String[10] ;
	
	
	
	
	public OnosCommunicationService(String account, String passwd) {
		this.authString = account + ":" + passwd;	
	}	
	
	private String openOnosRestConnection(String requestUrlString) {
		
		String result = "no data";

		try {
			URL RequestUrl = new URL(requestUrlString);
			HttpURLConnection onosConn = (HttpURLConnection) RequestUrl.openConnection();
			onosConn.setRequestMethod("GET");
			onosConn.setRequestProperty("Accept", "application/json");
			String encodeString = "Basic " + Base64.encode(authString.getBytes());
			onosConn.setRequestProperty("Authorization", encodeString);		

			if (onosConn.getResponseCode() != 200) {
				throw new RuntimeException("Connection Failed : HTTP error code : " + onosConn.getResponseCode());
			}			
			InputStreamReader inputStreamReader = new InputStreamReader(onosConn.getInputStream());
			BufferedReader bufferReader = new BufferedReader(inputStreamReader);		
			String rd;
			if ((rd = bufferReader.readLine()) != null) {		
				result = rd;
			}			 		 
			onosConn.disconnect();			
			} catch (MalformedURLException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();	
			}  
		
		return result;
	}
	
	public String getBgpRoutes() {
		
		String urlString = "http://" + localOnosIP + ":8181/onos/bgpRest/nchc/sdnip/bgproutes";
		return openOnosRestConnection(urlString);
		
	}
	
	public String getBgpRoutesWithIPPort(String ip, String port, String ssl) {
		
		String urlString = "";
		
		String firstString = "http://";
		
		if(ssl.equals("on")) {
			firstString = "https://";
		}
		
		if(port.equals("")) {
			urlString = ip +"/onos/bgpRest/nchc/sdnip/bgproutes";
		} else {
			urlString = ip + ":"+ port+"/onos/bgpRest/nchc/sdnip/bgproutes";
		}
		
		urlString = firstString + urlString;
		
		return openOnosRestConnection(urlString);
		
	}
	
	public String getBgpNeighbor() {
		
		String urlString = "http://" + localOnosIP + ":8181/onos/bgpRest/nchc/sdnip/bgpneighbor";
		return openOnosRestConnection(urlString);
		
	}
	
	public String getBgpNeighborWithIPPort(String ip, String port, String ssl) {
		
		String urlString = "";
		
		String firstString = "http://";
		
		if(ssl.equals("on")) {
			firstString = "https://";
		}
		
		if(port.equals("")) {
			urlString = ip +"/onos/bgpRest/nchc/sdnip/bgpneighbor";
		} else {
			urlString = ip + ":"+ port+"/onos/bgpRest/nchc/sdnip/bgpneighbor";
		}
		
		urlString = firstString + urlString;
		return openOnosRestConnection(urlString);
		
	}
	
	public String getBgpSpeakers() {
		
		String urlString = "http://" + localOnosIP + ":8181/onos/bgpRest/nchc/sdnip/bgpspeakers";
		return openOnosRestConnection(urlString);
		
	}
	
	public String getBgpSpeakersWithIPPort(String ip, String port, String ssl) {
		

		String urlString = "";
		
		String firstString = "http://";
		
		if(ssl.equals("on")) {
			firstString = "https://";
		}
		
		if(port.equals("")) {
			urlString = ip +"/onos/bgpRest/nchc/sdnip/bgpspeakers";
		} else {
			urlString = ip + ":"+ port+"/onos/bgpRest/nchc/sdnip/bgpspeakers";
		}
		
		urlString = firstString + urlString;
		return openOnosRestConnection(urlString);
		
	}
	
	public String getHosts() {
		
		String urlString = "";
		
		String firstString = "http://";
		
		if(localOnosSsl.equals("on")) {
			firstString = "https://";
		}
		
		if(localOnosPort.equals("")) {
			urlString = localOnosIP +"/onos/v1/hosts";
		} else {
			urlString = localOnosIP + ":"+ localOnosPort+"/onos/v1/hosts";
		}
		
		urlString = firstString + urlString;
		
		
		//String urlString = "http://" + localOnosIP + ":8181/onos/v1/hosts";
		return openOnosRestConnection(urlString);
		
	}
	
    public String getDevices() {
		
		String urlString = "";
		
		String firstString = "http://";
		
		if(localOnosSsl.equals("on")) {
			firstString = "https://";
		}
		
		if(localOnosPort.equals("")) {
			urlString = localOnosIP +"/onos/v1/devices";
		} else {
			urlString = localOnosIP + ":"+ localOnosPort+"/onos/v1/devices";
		}
		
		urlString = firstString + urlString;
    	
		//String urlString = "http://" + localOnosIP + ":8181/onos/v1/devices";
		return openOnosRestConnection(urlString);
		
	}
	
	public String getFlowEntries() {
		
		String urlString = "";
		
		String firstString = "http://";
		
		if(localOnosSsl.equals("on")) {
			firstString = "https://";
		}
		
		if(localOnosPort.equals("")) {
			urlString = localOnosIP +"/onos/v1/flows";
		} else {
			urlString = localOnosIP + ":"+ localOnosPort+"/onos/v1/flows";
		}
		
		urlString = firstString + urlString;
		
		//String urlString = "http://" + localOnosIP + ":8181/onos/v1/flows";
		return openOnosRestConnection(urlString);
		
	}
	
	
	public String getLinks() {
		
		String urlString = "";
		
		String firstString = "http://";
		
		if(localOnosSsl.equals("on")) {
			firstString = "https://";
		}
		
		if(localOnosPort.equals("")) {
			urlString = localOnosIP +"/onos/v1/links";
		} else {
			urlString = localOnosIP + ":"+ localOnosPort+"/onos/v1/links";
		}
		
		urlString = firstString + urlString;
		
	//	String urlString = "http://" + localOnosIP + ":8181/onos/v1/links";
		return openOnosRestConnection(urlString);
		
	}


	
}
