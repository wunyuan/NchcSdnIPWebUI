$(".hopCount").on('click', function(){
		
		$("#sdnHop").css({"display":"none"});
		$(".swDiv").css({"display":"none"});
			
		var hopSelector = "#"+$(this).attr("flag");
		
		$("#sdnHop").css({"display":"block"});
		$(hopSelector).css({"display":"block"});
		if($("#sdnHop").height() > 300) {
			$("#sdnHop").css({'height': '300px'});
		}
	});