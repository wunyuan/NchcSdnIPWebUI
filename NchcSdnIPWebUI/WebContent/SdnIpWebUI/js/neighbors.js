var neighborJson = $('#sdnipscript').data('neighbors');
addNeighborTableEntry(neighborJson.neighborSet);


function addNeighborTableEntry(jsonArr){
	
	for(i=0; i< jsonArr.length; i++) {
		
		var json = jsonArr[i].bgpneighbor;
		
		for(j=0; j < json.length; j++) {
			
			var neighbor = json[j];
			
			var remoteDatas = [ neighbor.remoteAddress, neighbor.remoteBgpVersion, neighbor.remoteAs+' / '+neighbor.remoteAs4, neighbor.remoteHoldtime, neighbor.remoteBgpId,
	               neighbor.remoteIpv4Unicast+' / '+neighbor.remoteIpv6Unicast, neighbor.remoteIpv4Multicast+' / '+neighbor.remoteIpv6Multicast,
	             ];  
			
			var localDatas = [ neighbor.localAddress, neighbor.localBgpVersion, neighbor.localAs+' / '+neighbor.localAs4, neighbor.localHoldtime, neighbor.localBgpId,
	               neighbor.localIpv4Unicast+' / '+neighbor.localIpv6Unicast, neighbor.localIpv4Multicast+' / '+neighbor.localIpv6Multicast,
				 ];
			
			if( i==0 ) {
				
				var firstRowAppendString = '<tr class="tabui-id-'+(i+1)+'\" style=\"display: table-row\"><td>';
				
			} else {
				
				var firstRowAppendString = '<tr class="tabui-id-'+(i+1)+'\" style=\"display: none\"><td>';
				
			}
			
			
			addTableEntries("#bgpNeighborTable1", remoteDatas, firstRowAppendString);
			
			addTableEntries("#bgpNeighborTable2", localDatas, firstRowAppendString)
			
			function addTableEntries (id, data, index) {				
							
				for(z=0; z < data.length; z++) {	
									
					if(z == 0) {
						
						$("table" + id + " tbody").append(firstRowAppendString +data[z]+ '</td>');
					} else {
						
						if(z == data.length-1) {
							$("table" + id + " tbody tr").last().append('<td>'+data[z]+ '</td></tr>');
						} else {
							$("table" + id + " tbody tr").last().append('<td>'+data[z]+ '</td>');
						}				
					}
				}
			};
			
					
		}
			
	}
			
}
