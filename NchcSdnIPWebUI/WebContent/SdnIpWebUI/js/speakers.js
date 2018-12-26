var speakersJson = $('#sdnipscript').data('speakers');

addSpeakersTableEntry(speakersJson.speakerSet);

$("div#iptabs ul li").click( function(){
	var selectStr = "tbody tr.tab" + $(this).children().attr("id");
	$("tbody tr").css({"display":"none"});
	$(selectStr).css({"display":"table-row"});	
});

function addSpeakersTableEntry(jsonArr){
	
	for(i=0; i < jsonArr.length; i++) {
		
		var json = jsonArr[i].bgpspeakers;
		var count = 0;
				
		if(i==0) {			
			var firstRowAppendString = '<tr class="tabui-id-'+(i+1)+'\" style=\"display: table-row\" rownumber= \"';		
		} else {			
			var firstRowAppendString = '<tr class="tabui-id-'+(i+1)+'\" style=\"display: none\" rownumber= \"';		
		}		
		
		for(j=0; j < json.length; j++) {
			
			var speaker = json[j];
			
			
			var speakerDatas = [ speaker.speakerName, speaker.connectPoint_deviceId, 
	               speaker.connectPoint_port, speaker.vlan, speaker.peers 
				   ];


			for(z=0; z < speakerDatas.length; z++) {
			
				var tableEntries = speakerDatas[z];
				
				if(tableEntries.constructor == Array) {

					
					$("table#bgpSpeakersTable tbody tr").last().append('<td>'+ tableEntries.length+ ' '+ ' <span class="spreadoutoff" style="color:blue; font-size:14px"><u>...</u></span></td>');
		
					$('#bgpSpeakersTable td a').click(function(){
						$('#bgpSpeakersTable td a').html('<br> yes');
					});
		
				} else {

					if(z == 0) {	
			
						$("table#bgpSpeakersTable tbody").append(firstRowAppendString + count+ '\"><td>'+tableEntries+ '</td>');
			
					} else {
			
						if(z == (speakerDatas.length-1)) {
							$("table#bgpSpeakersTable tbody tr").last().append('<td>'+tableEntries+ '</td></tr>');
				
						} else {
				$("table#bgpSpeakersTable tbody tr").last().append('<td>'+tableEntries+ '</td>');
						}				
					}		
				}
	
				if(z == (speakerDatas.length-1)) {
					count = count + 1;
				}				
			}			
		}		
	}
	
	$('#bgpSpeakersTable td span.spreadoutoff').click(function(){
		
		var selectedTr= ($(this).parent()).parent();
		var rowIndex = selectedTr.attr("rownumber");
		var className = selectedTr.attr("class");
		var index = parseInt( className.charAt(className.length-1) );	
		
		$("#innerSpreadDiv").jsonViewer(jsonArr[index-1].bgpspeakers[rowIndex].peers, {collapsed: true, withQuotes: true});
		$("div#spreadDiv").css({"display":"block"});
		
	});
			
}
