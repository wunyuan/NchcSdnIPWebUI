//var size = $('script').length;
//var jsonData = $('script').get(size-1).getAttribute('data');
//var routesJson = JSON.parse(jsonData);

var routesJson = $('#sdnipscript').data('routes');
//addRouteTableEntry(routesJson.routes4);
var routeArray = routesJson.routeSet; 
autoCreateTabs(routeArray);
addRouteTableEntry(routeArray);


function autoCreateTabs(json) {
	
	var tabstring = "";
	for(i=0; i < json.length; i++) {
		
		if( i==0 ) {	
	//		tabstring =  '<li><a href= \"#tab'+ i+ '\">' + json[i].onosip + '</a></li>';
	//		$("div#iptabs").prepend("<p id=\"tab"+ i +"\"></p>");
			tabstring =  '<li><a href= \"#fortab\">' + json[i].onosip + '</a></li>';
			
		}
		else {
	//		tabstring =  tabstring+ '<li><a href= \"#tab'+ i+ '\">' + json[i].onosip + '</a></li>';
	//		$("div#iptabs").prepend("<p id=\"tab"+ i +"\"></p>");
			tabstring =  tabstring+ '<li><a href= \"#fortab\">' + json[i].onosip + '</a></li>';
		}
	}
	
	$(function() {
		
		$("#iptabs").tabs()
		
	});
	
	$("#iptabs").prepend(			
				'<ul>'+ 
					tabstring+
				'</ul>'		
	);	
}


function addRouteTableEntry(jsonarr){			
	
	for(z=0; z<jsonarr.length; z++) {
		
		var json = jsonarr[z].routes4;
			
		var count = 0;
		
		if(z==0) {
			
			var firstRowAppendString = '<tr class="tabui-id-'+(z+1)+'\" style=\"display: table-row\" rownumber= \"';
			
		} else {
			
			var firstRowAppendString = '<tr class="tabui-id-'+(z+1)+'\" style=\"display: none\" rownumber= \"';
			
		}	
		
		for(i=0; i<json.length; i++) {
			var route = json[i];
			var routeDatas = [ route.prefix, route.nextHop, 
	            route.bgpId, route.origin, route.asPath.pathSegments, 
	            route.localPref, route.multiExitDisc
				];
			
			for(j=0; j < routeDatas.length; j++) {		
			
				var tableEntries = routeDatas[j];
				if(tableEntries.constructor == Array) {
					
					if(tableEntries.length != 0) {

						var asPath = tableEntries[0].segmentAsNumbers
						$("table#bgpRoutesTable tbody tr").last().append('<td>'+ asPath.length+ ' '+ '<span class="spreadoutoff" style="color:blue; font-size:14px"><u>...</u></span>');
		
						
					} else {
						$("table#bgpRoutesTable tbody tr").last().append('<td>'+ tableEntries.length);
							
					}						
					
				} else {

					if(j == 0) {					
						var ident = "table#bgpRoutesTable tbody";
						var appendString = firstRowAppendString + count+'\"><td>'+tableEntries+ '</td>';
						$(ident).append(appendString);

					} else {

						if(j == routeDatas.length-1) {
							$("table#bgpRoutesTable tbody tr").last().append('<td>'+tableEntries+ '</td></tr>');
							count = count + 1;					

						} else {
							$("table#bgpRoutesTable tbody tr").last().append('<td>'+tableEntries+ '</td>');
						}				
					}		
				}							
			}
			
		}	
	}	

	$('#bgpRoutesTable td span.spreadoutoff').click(function(){
		
		var selectedTr= ($(this).parent()).parent();
		var rowIndex = selectedTr.attr("rownumber");
		var className = selectedTr.attr("class");
		var index = parseInt( className.charAt(className.length-1) );
		
		$("#innerSpreadDiv").last().jsonViewer(jsonarr[index-1].routes4[rowIndex].asPath, {collapsed: true, withQuotes: true});
		$("div#spreadDiv").css({"display":"block"});		
	});

}
		
