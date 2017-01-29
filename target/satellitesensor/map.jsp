<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Map</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jquery.datetimepicker.css">
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyD0qtnTz7yF7IbEdvpiSRUZj9e3-8XYNDA&v=3&amp;sensor=false"></script>
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script
	src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/infobox/src/infobox.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.datetimepicker.full.js"></script>

<style>
body {
	background: transparent
		url('${pageContext.request.contextPath}/images/hexellence.png');
}

#timeSelect {
	float: left;
	margin-left: 50px;
	margin-right: 20px;
	width: 260px;
}

#searchOptions {
	height: 165px;
	width: 800px;
	float: right;
	padding-right:60px;
}

#searchOptions>form>label, #imageOptions>label {
	margin-left: 10px;
	margin-right: 10px;
	vertical-align: top;
}

#searchOptions>form>input, select {
	margin-bottom: 15px;
}

#mapWrapper{
	position:relative;
	width: 1290px;
	height: 640px;
	display: block;
	margin-left: auto;
	margin-right: auto;
	margin-top: 20px;
}

#googleMap {
	width: 1290px;
	height: 640px;
	display: block;
	margin-left: auto;
	margin-right: auto;
	margin-top: 20px;
}
#infoBoxContainer {
	position: absolute;
	z-index: 99;
	left: 860px;
	top: 30px;
}

#infoBoxData {
	
	background-color: rgba(0, 109, 145, 0.7);
	height: 280px;
	width: 400px;
	border: 1px solid black;
	font-size: 14px
}

#infoBoxData2 {

	background-color: rgba(247, 255, 102, 0.7);
	height: 150px;
	width: 250px;
	margin-top:25px;
	border: 1px solid black;
	font-size: 14px;
	float:right;
}

.loader {
	display: none;
	background-color: rgba(171, 245, 141, 0.8);
	position: absolute;
	left: 550px;
	top: 250px;
	z-index: 99;
	border: 16px solid #f3f3f3;
	border-top: 16px solid #3498db;
	border-radius: 50%;
	width: 120px;
	height: 120px;
	animation: spin 2s linear infinite;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}



</style>

<script>


var latitude = ${late};
var longitude = ${longe};
var satLatitude = ${satLate};
var satLongitude = ${satLonge};
var satIds = ${satId};
var satNumbers = ${satNumbers};
var map;
var marker;
var markers = [];

var flightPath;
var flightPlanCoordinates = [];
var scanner;
var scannerPath = [];
var rectangle;

var longitudeJson = JSON.parse(JSON.stringify(longitude));
var latitudeJson = JSON.parse(JSON.stringify(latitude));
var satLongitudeJson = JSON.parse(JSON.stringify(satLongitude));
var satLatitudeJson = JSON.parse(JSON.stringify(satLatitude));
var satIdJson = JSON.parse(JSON.stringify(satIds));



var selectedID=5;
var sateliteChange=true;

var animationSpeed = 1000;
var animationSpeedTemp;



var currentTime = new Date().getTime();

	$(document).ready(function() {
		$('#imageOptions').hide();
		setInterval("ajaxCall()",1000);
		 
			$( "#speedUp" ).on( "click", function() {
				  animationSpeed = animationSpeed*2;
				  $( "#speed" ).text(animationSpeed/1000+"x");
				  
				});
			$( "#speedDown" ).on( "click", function() {
				  animationSpeed = animationSpeed/2;
				  $( "#speed" ).text(animationSpeed/1000+"x");
				});
			$( "#play" ).on( "click", function() {
				if(animationSpeed!=0){
				  	animationSpeedTemp=animationSpeed;
					animationSpeed = 0;
					 $( "#speed" ).text("Pause");
				}
				else{
					animationSpeed=animationSpeedTemp;
					 $( "#speed" ).text(animationSpeed/1000+"x");
				}
				  
				});
			$('#type_select').click(function(){
				
				if($( "#type_select option[value='Earth Observation']:selected" ).length > 0){
					$('#imageOptions').show();
				}
				else{
					$('#imageOptions').hide();
				}
			});
			$('#recImg').click(function(){
				 var bounds = {
						    north: 20.599,
						    south: 44.490,
						    east: -78.443,
						    west: -98.649
						  };
				 
				 rectangle = new google.maps.Rectangle({
					    bounds: bounds,
					    editable: true,
					    draggable: true
					  });

					  rectangle.setMap(map);
			});
		
			$('#datetimepicker, #datetimepicker2').datetimepicker({
				dayOfWeekStart : 1,
				lang:'en',
				startDate:	new Date().getTime()
				});
			
			$("#analyze").click(function(){
				
			     
				   $( "#boxnelat" ).val(rectangle.getBounds().getNorthEast().lat());
				   $( "#boxnelng" ).val(rectangle.getBounds().getNorthEast().lng());
				   $( "#boxswlat" ).val(rectangle.getBounds().getSouthWest().lat());
				   $( "#boxswlng" ).val(rectangle.getBounds().getSouthWest().lng());
				   $('.loader').show();
					
				   $("#actionform").attr("action", "analyze");
				   $("#actionform").submit();
				});
				$("#query").click(function(){
					
				   $("#actionform").attr("action", "map");
				   $("#actionform").submit();
				});
			
			
				
			
	});

	function ajaxCall() {
		var newTime = new Date().getTime()
		currentTime = currentTime +animationSpeed;
		if(animationSpeed==250 && (currentTime>newTime-5000 || currentTime<newTime+5000)){
			currentTime=newTime;
		}
		$.ajax({
					url : 'map',
					data : {
						time : currentTime,
						ID : selectedID
					},
				
					success : function(allData) {
						
						var date = new Date(currentTime);
						$( "#time" ).text(date);
						
						
						uLatitude = JSON.parse(JSON.stringify(allData[0]));
						uLongitude = JSON.parse(JSON.stringify(allData[1]));
						usatLongitude = JSON.parse(JSON.stringify(allData[2]));
						usatLatitude = JSON.parse(JSON.stringify(allData[3]));
						usatData = JSON.parse(JSON.stringify(allData[4]));
						usatData2 =  JSON.parse(JSON.stringify(usatData));
						satSensor = JSON.parse(JSON.stringify(allData[5]));
						orbitData = JSON.parse(JSON.stringify(allData[6]));
					//	satSensor2 = JSON.parse(JSON.stringify(usatData));
						
					//	alert(satSensor[2].nazwa);
						
				
				
					if($('#selectionoptions').children('option').length==0&&satSensor!=""){
						
						$.each(satSensor, function (i, item) {
							    $('#selectionoptions').append($('<option>', { 
							        value: i,
							        text : item.nazwa, 
							        
							    }));
							});
					}
					
					if(satSensor!=""){
						$( "#sensor" ).text("Sensor: "+satSensor[$( "#selectionoptions" ).val()].nazwa);
						$( "#skanowanie" ).text("Scaning: "+satSensor[$( "#selectionoptions" ).val()].technikaSkanowania);
						$( "#obszar" ).text("Swath: "+satSensor[$( "#selectionoptions" ).val()].szerokoscPasma+" km");
					}
					else if(satSensor==""){
						$('#selectionoptions').empty();
						$( "#sensor" ).text("Sensor: No Data");
						$( "#skanowanie" ).text("Scaning: No Data");
						$( "#obszar" ).text("Swath: No Data");
					
					}
					if($( "#selectionoptions option:first").is(':selected')&&satSensor[$( "#selectionoptions" ).val()].nazwa!=$( "#selectionoptions option:first").text()){
						$('#selectionoptions').empty();
					}
					//	alert(JSON.stringify(usatLatitude));
					//	alert(JSON.stringify(usatData.noradId));
					//	alert(selectedID);
						
						flightPlanCoordinates = [];
						
						for (i = 0; i < 650; i = i + 1) {
							var point = new google.maps.LatLng(uLatitude.late[i],
									uLongitude.longe[i]);
							flightPlanCoordinates.push(point);
						}
						
						flightPath.setPath(flightPlanCoordinates);
						marker.setPosition(new google.maps.LatLng(uLatitude.late[0],uLongitude.longe[0]));
						
						// $( '#someerrors').text(usatLongitude.satLate[5]);
						//$( '#someerrors').text(markers.length);
						for (i = 0; i < markers.length; i++) {
							//	 $( '#someerrors').text(alldata[0].satLate[i]);

							var point = new google.maps.LatLng(
									usatLongitude.satLate[i],
									usatLatitude.satLonge[i]);
							markers[i].setPosition(point);
							
							
							
							$( "#noradID" ).text("Norad ID: "+usatData.noradId);
							$( "#Satelita" ).text("Satellite: "+usatData.nazwa);
							$( "#typ" ).text("Type: "+usatData.rodzaj);
							$( "#user" ).text("User: "+usatData.uzytkownicy);
							$( "#task" ).text("Task: "+usatData.cel);
						//	$( "#sensor" ).text("Sensor: "+usatData.noradId);
							$( "#start" ).text("Launch Date: "+usatData.dataStartu);
							$( "#pojazd" ).text("Starting Vehicle: "+usatData.pojazdStartowy);
							$( "#masa" ).text("Mass: "+usatData.masa+ " kg");
							$( "#kraj" ).text("Country: "+usatData.kraj);

							
							$( "#apoge" ).text("Apogeum: "+orbitData.apogeum);
							$( "#peri" ).text("Perigeum: "+orbitData.perygeum);
							$( "#mean" ).text("Eccentricity: "+orbitData.mimosrod);
							$( "#incl" ).text("Inclination: "+orbitData.inklinacja);
							$( "#orb" ).text("Orbit Type: "+orbitData.orbita);

							
							scannerPath = [];
							if(satSensor!=""){
								var pathkm = satSensor[$( "#selectionoptions" ).val()].szerokoscPasma/2;
								var addlat = pathkm/110.5;
								var rad = uLatitude.late[0]*0.0174532925;
								var addlon = pathkm/(111.320*Math.cos(rad));
								
								var point1 = new google.maps.LatLng(uLatitude.late[0]+addlat,
										uLongitude.longe[0]+addlon);
								var point2 = new google.maps.LatLng(uLatitude.late[0]+addlat,
										uLongitude.longe[0]-addlon);
								var point3 = new google.maps.LatLng(uLatitude.late[0]-addlat,
										uLongitude.longe[0]+addlon);
								var point4 = new google.maps.LatLng(uLatitude.late[0]-addlat,
										uLongitude.longe[0]-addlon);
								
								scannerPath.push(point2);
								scannerPath.push(point4);
								scannerPath.push(point3);
								scannerPath.push(point1);
								scanner.setPath(scannerPath);
							}
								else{
								
								var point1 = new google.maps.LatLng(uLatitude.late[0],
										uLongitude.longe[0]);
								var point2 = new google.maps.LatLng(uLatitude.late[0],
										uLongitude.longe[0]);
								var point3 = new google.maps.LatLng(uLatitude.late[0],
										uLongitude.longe[0]);
								var point4 = new google.maps.LatLng(uLatitude.late[0],
										uLongitude.longe[0]);
								scannerPath.push(point2);
								scannerPath.push(point4);
								scannerPath.push(point3);
								scannerPath.push(point1);
								scanner.setPath(scannerPath);
							}
							
						
							
						}
					},
					error : function(result) {
					//	alert("Error");
					//	$('#someerrors').text(JSON.stringify(result));
					}
				});
		//    alert(currentTime);

	}
	

	
	

	function initialize() {
	
	    
	  
		
		var mapProp = {
			center : new google.maps.LatLng(51.508742, -0.120850),
			zoom : 2,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById("googleMap"),
				mapProp);

		
		
		for (i = 0; i < 650; i = i + 1) {
			var point = new google.maps.LatLng(latitudeJson.late[i],
					longitudeJson.longe[i]);
			flightPlanCoordinates.push(point);
		}

		flightPath = new google.maps.Polyline({
			path : flightPlanCoordinates,
			geodesic : true,
			strokeColor : '#FF0000',
			strokeOpacity : 1.0,
			strokeWeight : 2
		});

		flightPath.setMap(map);

		var iconBase = '${pageContext.request.contextPath}/images/satellite.png';
		marker = new google.maps.Marker({
			position : new google.maps.LatLng(latitudeJson.late[0],
					longitudeJson.longe[0]),
			map : map,
			icon : iconBase
		});


		
		  
		for (i = 0; i < satNumbers; i = i + 1) {

			var point = new google.maps.LatLng(satLatitudeJson.satLate[i],
					satLongitudeJson.satLonge[i]);
			
			var icon = {
				    url: "${pageContext.request.contextPath}/images/red.png", // url
				    scaledSize: new google.maps.Size(6, 6), // scaled size
				    origin: new google.maps.Point(0,0), // origin
				    anchor: new google.maps.Point(3, 3) // anchor
				};
			
					var mark = new google.maps.Marker({
					position : point,
					map : map,
					icon : icon,
					
				
			});
					mark.set("ids", satIdJson.satId[i]);
					mark.addListener('click', function() {
						$('#selectionoptions').empty();
						selectedID = this.get("ids");
					  });
			
			markers.push(mark);
		}

		
		
		var point1 = new google.maps.LatLng(latitudeJson.late[0],
				longitudeJson.longe[0]);
		var point2 = new google.maps.LatLng(latitudeJson.late[0],
				longitudeJson.longe[0]);
		var point3 = new google.maps.LatLng(latitudeJson.late[0],
				longitudeJson.longe[0]);
		var point4 = new google.maps.LatLng(latitudeJson.late[0],
				longitudeJson.longe[0]);
		scannerPath.push(point2);
		scannerPath.push(point4);
		scannerPath.push(point3);
		scannerPath.push(point1);
		
		scanner = new google.maps.Polygon({
		    paths: scannerPath,
		    strokeColor: '#FFFF00',
		    strokeOpacity: 0.8,
		    strokeWeight: 2,
		    fillColor: '#DDFF00',
		    fillOpacity: 0.55
		  });
		scanner.setMap(map);

	}

	google.maps.event.addDomListener(window, 'load', initialize);
</script>
</head>
<body>
	
	<div id="ajaxResponse"></div>
	<div style="width: 1366px;margin:0px auto">
		<div id="timeSelect">
			<p style="font-size: 24px; margin-top: 0px; margin-bottom: 10px">SATELLITE
				SENSOR</p>

			<p id="time"></p>

			<button id="play">play</button>
			<button id="speedDown">back</button>
			<button id="speedUp">forward</button>
			<label id="speed">1x</label><br />
			<a href="http://ksg.eti.pg.gda.pl/satellitesensor/login">
<button style="float:left;margin-top:15px">Login</button> </a>
		</div>
		<img
			src="${pageContext.request.contextPath}/images/Satellite-icon.png"
			width="140" height="140" />

		<div id="searchOptions">
			<form id="actionform" action="map" method="GET">
				<label>Norad Id</label><input type="text" name="norad_id" size="6">
				<label>Satelite Name</label><input type="text" name="satelite_name"
					size="6"> <label>Satelite Count</label><input type="number"
					name="quantity" min="1" max="16000" size="6"> <label>Showing
					satelites ${satNo}</label> <br /> <label>Satelite Type</label><select
					multiple name="type" id="type_select">
					<option value="Communications">Communications</option>
					<option id="earth" value="Earth Observation">Earth
						Observation</option>
					<option value="Navigation">Navigation</option>
					<option value="Not defined">Not Defined</option>
					<option value="Debris">Debris</option>
					<option value="Rocket Body">Rocket Body</option>
				</select> <label>Orbit</label><select multiple name="height">
					<option value="LEO">LEO</option>
					<option value="MEO">MEO</option>
					<option value="GEO">GEO</option>
					<option value="HEO">HEO</option>
					<option value="UNC">UNCLASIEFIELD</option>
				</select> <span id="imageOptions"> <!--  <label>Image Type</label><select multiple name="imagetype">
 <option value="Weather">Metherolology</option>
  <option value="Image">Image</option>
</select>--> <label>Sensor Type</label><select multiple
					name="sensortype">
						<option value="Radar">Radar</option>
						<option value="Image">Optical</option>
				</select></span><br /><span id="selectSensor"> <label>Sensor </label><select
					id="selectionoptions">

				</select> <img id="recImg"
					style="margin-left: 30px; border: 1px solid black; vertical-align: middle; margin-bottom: 5px;"
					src="${pageContext.request.contextPath}/images/draw.svg" width="28"
					height="28"> <input type="text" value="" id="datetimepicker"
					name="datetimepicker" /> <input type="text" value=""
					id="datetimepicker2" name="datetimepicker2" /> <input type="hidden"
					id="boxnelat" name="boxnelat" value="0" /> <input type="hidden"
					id="boxnelng" name="boxnelng" value="0" /> <input type="hidden"
					id="boxswlat" name="boxswlat" value="0" /> <input type="hidden"
					id="boxswlng" name="boxswlng" value="0" /> <input type="button"
					value="Analyze" id="analyze" />

				</span>

				<div style="float: right">
					<input type="button" value="Query" id="query" />


				</div>

			</form>
		</div>
	</div>
	

	<div id="mapWrapper">
		<div id="googleMap"></div>
		
	<div class="loader"></div>
	<div id="infoBoxContainer">
			<div id="infoBoxData">
				<div style="float: left; width: 59%; margin-left: 5px">
					<p id="noradID"></p>
					<p id="Satelita"></p>
					<p id="typ"></p>
					<p id="kraj"></p>
					<p id="user"></p>
					<p id="task"></p>
					<p id="masa"></p>
				</div>
				<div style="float: right; width: 39%">
					
					<p id="sensory"></p>
					<p id="start"></p>
					<p id="pojazd"></p>
					<p id="orb"></p>
					<p id="apoge"></p>
					<p id="peri"></p>
					<p id="mean"></p>
					<p id="incl"></p>
					
	
				</div>
	
			</div>
			<div id="infoBoxData2">
				<p id="sensor"></p>
				<p id="skanowanie"></p>
				<p id="obszar"></p>
			</div>
		</div>
	</div>
	<p id="someerrors"></p>


</body>

</html>