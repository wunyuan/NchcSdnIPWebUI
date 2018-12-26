<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SDN-IP WEBUI</title>


<link rel="stylesheet" type="text/css" href="SdnIpWebUI/css/index_style.css">
<script type="text/javascript" src="SdnIpWebUI/js/jquery-3.2.1.min.js"></script>

</head>



<body>
<div id='maindiv'>
	<div class="top">		
		<img class="logopic" src="SdnIpWebUI/img/nchc_logo_test.png">		
	</div>
	
	<div id = 'chooseradio'>
		<input type = 'radio' name = 'sdnip-number' value = 'single' checked = 'checked'> single SDN-IP
		<input type = 'radio' name = 'sdnip-number' value = 'multiple'> multiple SDN-IP 
	</div>

	<div id='inputdiv'>
		<form  id="onosip_form_single" action="sdnip.do" method = "POST">
			<table>
				<tr>
					<td>ONOS IP</td>
					<td>Port</td>
				</tr>
				<tr>			
					<td><input class="onosip_single" type="text" name="onosip1" required="required"></td>
					<td><input class="onosip_single" type="text" name="onosip1port"></td>
					<td>SSL:</td>
					<td><input class="onosip_single" type="checkbox" name="onosip1ssl"></td>
				</tr>		
			</table>
			<input type="submit" value="Submit">
		</form>
		<form  id="onosip_form_multi" action="sdnip.do" method = "POST" style = 'display : none'>
			    SDN-IP site numbers:<select id= 'select_number' name = 'sdn-ip_number'> 
				<option value = '2' selected = 'selected'>2</option>
				<option value = '3'>3</option>
				<option value = '4'>4</option>
				<option value = '5'>5</option>
			</select>	
		</form>
	</div>
</div>
</body>
<script>

	$('input[type=radio][name=sdnip-number]').change( function(){		
		if(this.value == 'multiple') {
			$('form#onosip_form_single').css({"display":"none"});
			$('form#onosip_form_multi').css({"display":"block"});
			var numbers = $('select#select_number').find(':selected').val();
			
			createInputBlank(numbers);
		}
		if(this.value == 'single') {
			$('form#onosip_form_single').css({"display":"block"});
			$('form#onosip_form_multi').css({"display":"none"});
		}
		
		
	});
	
	
	
	$('select#select_number').change( function(){
		
		console.log(this.value);
		createInputBlank(this.value)
		
	});
	
	function createInputBlank(index) {
		
		
		$('.onosip_multi').remove();
	//	$('p.onosip_multi').remove();
		for( var i=1 ; i <=index ; i++) {
			
			if(i==1) {
				var name = "onosip" + i; 
				var portname = "onosip" + i + "port"; 
				var checkboxname = "onosip" + i + "ssl"; 
				var appendStr = '<p class=\"onosip_multi\">SDN-IP Sites:<\/p>' +
					'<tr class=\"onosip_multi\"><td>ONOS IP</td><td>Port</td></tr>'+
					'<tr class=\"onosip_multi\"><td><input class=\"onosip_multi\" type=\"text\" name=' + name +' required=\"required\"></td>' +
					'<td><input class=\"onosip_multi\" type=\"text\" name=' + portname +'></td>'+
					'<td>SSL:</td>'+
					'<td><input class=\"onosip_multi\" type=\"checkbox\" name=' + checkboxname +' value ="on"></td></tr>';
				$('select#select_number').after(appendStr);			
			}
			else {
				var name = "onosip" + i; 
				var portname = "onosip" + i+ "port";
				var checkboxname = "onosip" + i + "ssl"; 
				var appendStr = '<tr class=\"onosip_multi\"><td><input class=\"onosip_multi\" type=\"text\" name=' + name +' required=\"required\"></td>' +
								'<td><input class=\"onosip_multi\" type=\"text\" name=' + portname +'></td>'+
								'<td>SSL:</td>'+
								'<td><input class=\"onosip_multi\" type=\"checkbox\" name=' + checkboxname +'></td></tr>';	
				$('input.onosip_multi').last().after(appendStr);
			}
			
		}
		$('tr.onosip_multi').wrapAll("<table class= \"onosip_multi\">");
		//$('input.onosip_multi').last().after( '<input class=\"onosip_multi\" type=\"submit\" value=\"Submit\">' );
		$('table').last().after( '<input class=\"onosip_multi\" type=\"submit\" value=\"Submit\">' );
		//$('table').last().prepend('<tr><td>ONOS IP</td><td>Port</td></tr>');
		
	}

</script>

</html>