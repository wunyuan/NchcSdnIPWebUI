<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>SDN-IP WEBUI</title>


<link rel="stylesheet" type="text/css" href="SdnIpWebUI/css/sdnip.css">
<link rel="stylesheet" type="text/css" href="SdnIpWebUI/css/jquery.json-viewer.css">
<script type="text/javascript" src="SdnIpWebUI/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="SdnIpWebUI/js/jquery.json-viewer.js"></script>
</head>


<body>

	<div class="top">		
		<img class="logopic" src="SdnIpWebUI/img/nchc_logo_test.png">
		<ul class="menulist">
			<li id="menu3Line">
			&#9776;
			<ul id="list1">
				<li class="content" id="option1">
					<a href= "topology.do" target="_blank">
					topology
					</a>
				</li>	
			</ul>

			</li>	
		</ul>
		<span id='onosipstring'>ONOS IP is : <span style="color:red">${onosip}</span></span>
		
	</div>
	

   <div id="spreadDiv">
   <img id="spreadDivCrossLinePic" src="SdnIpWebUI/img/crossLine.png"></img>
   <div id="innerSpreadDiv"></div>
   </div>


  	<div class="routeTableDiv">
    	<h1>SDN-IP BGP Routes</h1>
    	<h2>IPv4</h2>
    	<table class="table" id = "bgpRoutesTable">
    		<thead><tr><th>Prefix</th><th>NextHop</th><th>BgpId</th><th>Origin</th><th>AsPath</th><th>LocalPref</th><th>MultiExitDisc</th></tr></thead>
    		<tbody>
        	<!-- switches will be inserted here by SwitchListView:render -->
    		</tbody>
		</table>
		<br></br>
	</div>

	<div class="neighborTableDiv">
    	<h1>SDN-IP BGP Neighbors</h1>
    	<h2>Remote</h2>
    	<table class="table" id = "bgpNeighborTable1">
    		<thead><tr><th>Address</th><th>BgpVersion</th><th>As / As4</th><th>HoldTime</th><th>BgpId</th><th>Ipv4Unicast / Ipv6</th><th>Ipv4Multicast / Ipv6</th></tr>
    		</thead>
    		<tbody>
        	<!-- switches will be inserted here by SwitchListView:render -->
    		</tbody>
		</table>
		<h2>Local</h2>
		<table class="table" id = "bgpNeighborTable2">
    		<thead><tr><th>Address</th><th>eBgpVersion</th><th>As / As4</th><th>HoldTime</th><th>BgpId</th><th>Ipv4Unicast / Ipv6</th><th>Ipv4Multicast / Ipv6</th></tr>
    		</thead>
    		<tbody>
        	<!-- switches will be inserted here by SwitchListView:render -->
    		</tbody>
		</table>
		<br></br>
	</div>
    
    <div class="speakerTableDiv">
    	<h1>SDN-IP BGP Speakers</h1>
    	<table class="table" id = "bgpSpeakersTable">
    		<thead><tr><th>SpeakerName</th><th>ConnectPoint_deviceId</th><th>ConnectPoint_Port</th><th>Vlan</th><th>Peers</th></tr></thead>
    		<tbody>
        	<!-- switches will be inserted here by SwitchListView:render -->
    		</tbody>
		</table>
	</div>
	

  
  <!-- Scripts -->
  
  <script type="text/javascript" src="SdnIpWebUI/js/routes.js" data = '${bgpRoutes}' ></script>
  <script type="text/javascript" src="SdnIpWebUI/js/neighbors.js" data = '${bgpNeighbors}' ></script>  
  <script type="text/javascript" src="SdnIpWebUI/js/speakers.js" data = '${bgpSpeakers}' ></script>
  
  <script>
  
  $(document).ready(function() {
	  $("#menu3Line").click(function() {
		  $("#list1").toggle("slow");
	  });
  }
		  
  
  );
  
	$("#spreadDivCrossLinePic").hover( function () {
		$(this).attr("src", "SdnIpWebUI/img/crossLine_red.png");
		
	}, function () {
		
		$(this).attr("src", "SdnIpWebUI/img/crossLine.png");
	});
	
	
	$("#spreadDivCrossLinePic").click( function () {
		$("#spreadDiv").css({"display":"none"});
		
	});
	
	$(".logopic").click(function(){
		location.href = "/NchcSdnIPWebUI/";
	});
  
  </script>

</body>
</html>