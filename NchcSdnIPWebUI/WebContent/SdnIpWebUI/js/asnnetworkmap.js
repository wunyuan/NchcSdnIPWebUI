size = $('script').length;
jsonData = $('script').get(size-1).getAttribute('data');
var pathFlowJson = JSON.parse(jsonData);


console.log(pathFlowJson);


cy.on('click', "node[type = 'normal']", function(){
	var asn = this.data("id");
	$.each(pathFlowJson, function(index, json){
		console.log("json.asn");
		console.log(json.asn);
		console.log("asn");
		console.log(asn);
	});

});
