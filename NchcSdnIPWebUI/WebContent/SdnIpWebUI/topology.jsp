<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">

<title>Topology</title>

<link rel="stylesheet" type="text/css" href="SdnIpWebUI/css/topology_style.css">
<link rel="stylesheet" type="text/css" href="SdnIpWebUI/css/jquery.json-viewer.css">
<script type="text/javascript" src="SdnIpWebUI/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="SdnIpWebUI/js/cytoscape.min.js"></script>
<script type="text/javascript" src="SdnIpWebUI/js/jquery.json-viewer.js"></script>

</head>


<body>
	<div class="top">		
		<img class="logopic" src="SdnIpWebUI/img/nchc_logo_test.png">
		
	</div>

	<div id='cy'>
	</div>	

	<form  class="alias_form" style="display:none;">
		alias_name：<input class="aliasField" type="text" name="alias"><button id="applybutton" type="button">apply</button>		
	</form>


	<div id='flowinfo'>    
		<img id="crossLinePic" src="SdnIpWebUI/img/crossLine.png">
		<ul id='flows'>
		</ul>
	</div>
	<div id='networklist'>    
		<img id="networklistClosePic" src="SdnIpWebUI/img/crossLine.png">
		<ul class='networks'>
		</ul>
	</div>
	<div id="sdnHop"></div>

    <script type="text/javascript" src="SdnIpWebUI/js/pathFlow.js" data = '${peerPathJson}' asnmap = '${asnNetworkMap}' ></script>
	<script type="text/javascript" src="SdnIpWebUI/js/hopcountClickEvent.js"></script>
	<script>
	$(".logopic").click(function(){
		location.href = "/NchcSdnIPWebUI/";
	});
	
	var cy = cytoscape({
		  container: document.getElementById('cy'),
		  
		  boxSelectionEnabled: false,
		  autounselectify: true,

		  style: cytoscape.stylesheet()
		    .selector('node')
		      .css({
		        'content': 'data(name)',
		        'text-valign': 'center',
		        'font-size': 10,
		        'color': 'white',
		        'text-outline-width': 2,
		        'text-outline-color': 'black',
		        'width':'60',
		        'height':'44',
		        'background-color': 'white'
		      })
		     .selector('edge')
		      .css({
		    	  'line-color': 'gray',
		    	  'curve-style': 'bezier'
		    	 
		      })
		    .selector(':selected')
		      .css({
		        'background-color': 'black',
		        'line-color': 'black',
		        'target-arrow-color': 'black',
		        'source-arrow-color': 'black',
		        'text-outline-color': 'black'
		      }),
		      

		      
		  elements: {
			  ${topoJson}
		    //nodes: [
		    //  { data: { id: 'desktop', name: 'Cytoscape', href: 'http://cytoscape.org' } },
		     // { data: { id: 'js', name: 'Cytoscape.js', href: 'http://js.cytoscape.org' } }
		    //],
		    //edges: [
		    //  { data: { source: 'desktop', target: 'js' } }
		   // ]
		  },

		  layout: {			  
		
		    name: 'cose',
		    fit: true,
		    padding: 100,
		    nodeDimensionsIncludeLabels: true,
		    randomize: false,
		    componentSpacing: 40,
		    nodeOverlap: 50

		    
		  },
		  wheelSensitivity:0.1
		});
	



	    cy.ready( function(){
	    	cy.nodes("[type= 'normal']").css({'background-image': 'SdnIpWebUI/img/cloud1.png'});
	    	cy.nodes("[type= 'sdn']").css({'background-image': 'SdnIpWebUI/img/cloud2.png'});
	    	var asnName = cy.nodes("[type= 'sdn']").data('asnName');
	    	cy.nodes("[type= 'sdn']").css({'label': asnName});

	    
	    });
	    	    
	
		cy.on('grab', 'node', function(){	
			this.css({'background-image': 'SdnIpWebUI/img/cloud3.png'});	
		});
		
		cy.on('free', 'node', function(){	
			
			if(this.data("type") == 'normal') {
				this.css({'background-image': 'SdnIpWebUI/img/cloud1.png'});
			}
				
			if(this.data("type") == 'sdn') {
				this.css({'background-image': 'SdnIpWebUI/img/cloud2.png'});
			}			
		});
		
		
		cy.on('click', "node[type = 'sdn']", function(){
			
			$("#flowinfo").css({'display': 'block'});
			var asnName = this.data("id");
			var asnNameArr = asnName.split(":");
			var selectStr = "."+asnNameArr[0]+asnNameArr[1];
			$(".pathFlow").css({'display': 'none'});
			$(selectStr).css({'display': 'block'});
			if($("#flowinfo").height() > 0) {
				if($("#flowinfo").height() < 500 ) {
					$("#flowinfo").css({'height': $("#flowinfo").height()});
				}
				else {
					$("#flowinfo").css({'height': '500px'});
				}		
				$("#flowinfo").css({'overflow': 'scroll'});					
			}
		});
		
		
		$("#crossLinePic").hover( function () {
			$(this).attr("src", "SdnIpWebUI/img/crossLine_red.png");
			
		}, function () {
			
			$(this).attr("src", "SdnIpWebUI/img/crossLine.png");
		});
		
		
		$("#crossLinePic").click( function() {
			
			$("#flowinfo").css({'display': 'none'});
			
		});
		
		$("#networklistClosePic").hover( function () {
			$(this).attr("src", "SdnIpWebUI/img/crossLine_red.png");
			
		}, function () {
			
			$(this).attr("src", "SdnIpWebUI/img/crossLine.png");
		});
		
		
		$("#networklistClosePic").click( function() {
			
			$("#networklist").css({'display': 'none'});
			
		});
		
		
		cy.on("cxttap","node", function(event){	
			
			var newName = "";
			
			$(".alias_form").css({
				"position":"absolute",
				"display":"block",
				"top":this.renderedPosition('y'),
			　	"left":this.renderedPosition('x')
			});

			$("input.aliasField").unbind("change");
			$("input.aliasField").bind("keypress", function(e){
				if(e.keyCode == 13) {
					console.log("enter key detected!!")
					return false;
				}
			});
			replaceName(this);	
					
		});
		
		$("#applybutton").click(function(){
			$(".aliasField").val("");
			$(".alias_form").css({"display":"none"});
		});
		
		
		
		
		function replaceName(element){
			$("input.aliasField").change(function(){
				
				
				var sdnNameNoSame = false;
				
		    	var newName = $(this).val();	
		    	
		    	if(element.data("type") == "sdn") {
		    		
		    		if(element.data("asnName") != newName) {
		    			sdnNameCheck =true
		    		}
		    	}
		    	
		    	if( sdnNameNoSame || element.data("name") != newName) {
		    		//var nodes = cy.nodes("[type= \'"+element.data("type")+"\']");
		    		var nodes = cy.nodes();

		    		var matched = "false";
		    		
		    		for(var i=0; i < nodes.length; i++) {
		    			
		    			if(nodes[i].data("type") == "sdn") {		    				
		    				if( newName == nodes[i].data("asnName")) {
			    				matched = "true";
			    				break;
			    			}		    				
		    			}
		    			else {
		    				if( newName == nodes[i].data("name")) {
			    				matched = "true";
			    				break;
			    			}
		    			}	    			
		    		}
		    			
		    		if(matched == "false") {
		    			
		    			var repStr = '\\('+element.data("name")+'\\)';
		    			var replace = new RegExp(repStr, 'g');
				    	
				    	if(element.data("type") == 'normal') {		
				    		//cy.nodes("[id= \'"+element.data("id")+"\']").data("name", newName);
				    		element.data("name", newName);
				    		$("li.pathFlow").each(function(index){	    					    			
				    			var pathFlowText = "";			    			
				    			pathFlowText = $(this).html();			    	
				    			var replaceString = pathFlowText.replace(replace,'('+newName+')');
				    			var scripthtml = "<script type=\"text/javascript\" src=\"SdnIpWebUI/js/hopcountClickEvent.js\"><\/script>";
				    			$(this).html(replaceString+scripthtml);	
				    		});		    		
				    	}
				    	else {
				    //		cy.nodes("[id= \'"+element.data("id")+"\']").data("asnName", newName);
				            element.css({'label':newName});
				            element.data('asnName', newName);
				    		
				    	}
		    		}
		    	
			    }
		    	$("input.aliasField").unbind("change");
		    });   
		}
		
		
		var pathFlowJson = ${asnNetworkMap};


		cy.on('click', "node", function(){
			$(".alias_form").css({"display":"none"});
			if(this.data("type") == 'sdn') {
				var asn = this.data("id");
				
			} else {
				var asn = this.data("id");
				$("#flowinfo").css({'display': 'none'});
				$("#sdnHop").css({'display': 'none'});
			}
			
			var match = 0;
			$.each(pathFlowJson.asnNetwrokMapping, function(index, json){
				if(asn == json.asn) {
					$("#networklist .networks").html("");
					$.each(json.networks, function(index, data){
				
						$("#networklist .networks").last().append("<li class=\"networkdomain\">"+data);					
				
						match = 1;
						
						$("#networklist").css({'display': 'block'});
						if($("#networklist").height() > 0) {
				
							$("#networklist").css({'height': $("#networklist").height()});		
							$("#networklist").css({'overflow': 'scroll'});					
						}
					});			
				}
				if(match == 0) {
					$("#networklist .networks").html("");
					$("#networklist").css({'display': 'none'});
				}
			});		
		});	
		
</script>
	
	
</body>
</html>