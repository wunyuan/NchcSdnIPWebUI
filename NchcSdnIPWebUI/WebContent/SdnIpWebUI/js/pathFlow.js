size = $('script').length;
jsonData = $('script').get(size-1).getAttribute('data');
var asnJsonStr = $('script').get(size-1).getAttribute('asnmap');
var pathFlowJson = JSON.parse(jsonData);
var asnJson = JSON.parse(asnJsonStr);

addPathFlow(pathFlowJson);


function addPathFlow(json){	
	
	var asns = json.asns;
		
	if(asns.length != 0) {		
		
		for(var x=0; x < asns.length; x++) {
			
			var peerSorted = [];
			var json = asns[x];
			
			if(asns[x].peers != null) {
				
				var tempPeers = json.peers;
				for( var i =0; i < tempPeers.length; i++) {
					
					var storedPeer;
					if( i==0 ){
						
						storedPeer = tempPeers[i];
						peerSorted.push(storedPeer);
						delete tempPeers[i];
						
					} else {
						
						if(tempPeers[i] != null) {
							storedPeer = tempPeers[i];
							peerSorted.push(storedPeer);
							delete tempPeers[i];		
						} 		
					}					
					
					//check reverse link
					
					for(var j =0; j < tempPeers.length; j++) {			
						var newPeer = tempPeers[j];
						if(newPeer != null) {
							if(storedPeer.srcPeer == newPeer.dstPeer && storedPeer.dstPeer == newPeer.srcPeer) {						
								peerSorted.push(newPeer);
								delete tempPeers[j];
								break;
							}
						}
					}
				}
			
				$.each(peerSorted, function(index, peer){

						addPathFlow(peer);
						
						function addPathFlow(peer) {	
							
													
							$("#flows").last().append('<li class = \'pathFlow '+ json.asnid+'\' srcasn='+ peer.srcAsn +' dstasn= '+ peer.dstAsn+ ' globalAsnPath = \''+ peer.globalAsnPath+'\'>'+peer.linkId+ 
									                  '&nbsp;&nbsp;&nbsp;SDN Path flow hop count: <u class= \'hopCount\' style=\"color:blue\" flag='+ json.asnid+peer.peerIndex+'>'+peer.SDNPath.length+ '</u>'+
									                  '</li>');										
						}					
						
						$.each(peer.SDNPath, function(index, sw){
							var divid = peer.peerIndex;
							if(index == 0) {
								
								$("#sdnHop").last().append('<div class = \'swDiv\' id='+ json.asnid+peer.peerIndex+ ' style= \" display: none\">'+
										'<img class=\"swDivcrossLinePic\" src=\"SdnIpWebUI/img/crossLine.png\">');					
							}
							var jsonString =  JSON.stringify(sw.flowEntry);
							var selector = "#"+json.asnid+peer.peerIndex;
							$(selector).append('<li class = \' sdnSwtich \'> <u class=\'flowEntry\' style=\"color:blue\" json='+ jsonString +'\>'+ sw.switchId+ '</u></p>'+
					                  '<p class = \'infoText\'> InPort: '+sw.inPort+ '</p>'+
					                  '<p class = \'infoText\'> OutPort: '+sw.outPort+ '</p>'+
					                  '</p></li>');					
						});
					
					});
				
			} else {

				if(pathFlowJson.error != null) {
					$("#flows").last().append('<li class = \' pathFlow \'> Error: '+ pathFlowJson.error+'</li>');
				}
				else {
					$("#flows").last().append('<li class = \' pathFlow \'> Error: no peer fectched</li>');
				}				
			}			
		}	
	}
	
	/*
	
	var peerSorted = [];
	
	if(pathFlowJson.peers != null){				
		
		var tempPeers = json.peers;
		for( var i =0; i < tempPeers.length; i++) {
			var storedPeer;
			if( i==0 ){
				storedPeer = tempPeers[i];
				peerSorted.push(storedPeer);
				delete tempPeers[i];
				
			} else {
				
				if(tempPeers[i] != null) {
					storedPeer = tempPeers[i];
					peerSorted.push(storedPeer);
					delete tempPeers[i];		
				} 		
			}
			
			
			//check reverse link
			
			for(var j =0; j < tempPeers.length; j++) {			
				var newPeer = tempPeers[j];
				if(newPeer != null) {
					if(storedPeer.srcPeer == newPeer.dstPeer && storedPeer.dstPeer == newPeer.srcPeer) {						
						peerSorted.push(newPeer);
						delete tempPeers[j];
						break;
					}
				}
			}
		}
	
		$.each(peerSorted, function(index, peer){

				addPathFlow(peer);
				
				function addPathFlow(peer) {	
					
											
					$("#flows").last().append('<li class = \'pathFlow '+ peer.asnid+'\' srcasn='+ peer.srcAsn +' dstasn= '+ peer.dstAsn+ ' globalAsnPath = \''+ peer.globalAsnPath+'\'>'+peer.linkId+ 
							                  '&nbsp;&nbsp;&nbsp;SDN Path flow hop count: <u class= \'hopCount\' style=\"color:blue\" flag='+ peer.peerIndex+'>'+peer.SDNPath.length+ '</u>'+
							                  '</li>');										
				}					
				
				$.each(peer.SDNPath, function(index, sw){
					var divid = peer.peerIndex;
					if(index == 0) {
						
						$("#sdnHop").last().append('<div class = \'swDiv\' id='+ peer.peerIndex+ ' style= \" display: none\">'+
								'<img class=\"swDivcrossLinePic\" src=\"SdnIpWebUI/img/crossLine.png\">');					
					}
					var jsonString =  JSON.stringify(sw.flowEntry);
					var selector = "#"+peer.peerIndex
					$(selector).append('<li class = \' sdnSwtich \'> <u class=\'flowEntry\' style=\"color:blue\" json='+ jsonString +'\>'+ sw.switchId+ '</u></p>'+
			                  '<p class = \'infoText\'> InPort: '+sw.inPort+ '</p>'+
			                  '<p class = \'infoText\'> OutPort: '+sw.outPort+ '</p>'+
			                  '</p></li>');					
				});
			
			});
		
		
	} else{
		
		if(pathFlowJson.error != null) {
			$("#flows").last().append('<li class = \' pathFlow \'> Error: '+ pathFlowJson.error+'</li>');
		}
		else {
			$("#flows").last().append('<li class = \' pathFlow \'> Error: no peer fectched</li>');
		}
		
	}

	*/
	
	$(".flowEntry").click( function(){
		
		$.post("flowentry.do",
			       {
			         json: $(this).attr("json"),
			       },
			       function(data) {
			    	  		    	   
			    	   var win=window.open('');
			    	    with(win.document)
			    	    {
			    	    
			    	    	var html = "";
			    	    	
			    	    	var htmlHead = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"+
			    	    	"<html>"+
			    	    	"<head>"+
			    	    	"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=BIG5\">"+
			    	    	"<title>Flow Json</title>"+
			    	    	"<link rel=\"stylesheet\" type=\"text/css\" href=\"SdnIpWebUI/css/jquery.json-viewer.css\">"+
			    	    	"<script type=\"text/javascript\" src=\"SdnIpWebUI/js/jquery-3.2.1.min.js\"></script>"+
			    	    	"<script type=\"text/javascript\" src=\"SdnIpWebUI/js/jquery.json-viewer.js\"></script>"+
			    	    	"</head>";

			    	    	var htmlBody = "<body>"+
			    	    	               "<div id=\"flowjson\"></div><script>";
			    	    	               
			    	    	var json = JSON.stringify(data);
			    	    	var js = "$(\"#flowjson\").last().jsonViewer("+json+", {collapsed: true, withQuotes: true});"
			    	    	var htmlEnd = "</script></body></html>"
			    	    	open();
			    	    	write(htmlHead+htmlBody+js+htmlEnd);
			    	    	close();
			    	    }
			       }
			    );

		
	});
	
	$(".hopCount").hover( function () {
		$(this).css({"color":"red"});
		
	}, function () {
		$(this).css({"color":"blue"});

	});
	
	$(".swDivcrossLinePic").hover( function () {
		$(this).attr("src", "SdnIpWebUI/img/crossLine_red.png");
		$(this).css({"z-index": "1"});
		
	}, function () {		
		$(this).attr("src", "SdnIpWebUI/img/crossLine.png");
		$(this).css({"z-index": "0"});
	});
	
	
	$(".swDivcrossLinePic").click( function() {
		
		$("#sdnHop").css({"display":"none"});
		$(".swDiv").css({"display":"none"});
		
	});
	
	$(".pathFlow").hover( function () {
		
		$(this).css({"background":"lightgray"});
		var asnGolbalPath = $(this).attr("globalAsnPath");		
		var pathArr = asnGolbalPath.split(",");

		asnGlobalPath = asnGlabalPathFinder(pathArr, cy.edges());
		if(asnGlobalPath.length !=0 ) {	
			
			$.each(asnGlobalPath , function(index, link){
				
				var src = pathArr[index];
					
				if( src == link.data("target")) {
						
					link.css({"line-color":"red", "source-arrow-shape": "triangle", "source-arrow-color": "red", "arrow-scale" : "2"});					
						
				}
				else {
					link.css({"line-color":"red", "target-arrow-shape": "triangle", "target-arrow-color": "red", "arrow-scale" : "2"});
				}			
			});		
		}	
	}, function () {
		
		$(this).css({"background":"white"});
		
		$.each(cy.edges(), function(index, edge){
			
			edge.css({"line-color":"gray", "source-arrow-shape": "none", "target-arrow-shape": "none"});
		});
				
	});
	
	$(".sdnSwtich").hover( function () {
		$(this).css({"background":"lightgray"});
		
	}, function () {
		
		$(this).css({"background":"white"});
	});
	
	function asnGlabalPathFinder(pathArr, edges) {
	
		var path = [];
		
		for(var i=0; i < pathArr.length-1; i++) {
			
			var src = pathArr[i];
			var dst = pathArr[i+1];
			
			for(var x=0; x < edges.size(); x++) {			
				if( ((src == edges[x].data("source")) || (src == edges[x].data("target"))) &&
					((dst == edges[x].data("source")) || (dst == edges[x].data("target")))) {
					path.push(edges[x]);														
				}		
			}    
			
		}
		  
		return path;
	}
	
			
}
