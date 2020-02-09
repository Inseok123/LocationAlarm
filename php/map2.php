


<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>현재 위치</title>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBH36RlmQgz5LI_WSmh_xEQnuDlyOK_gKc&callback=initMap"></script>

<script>




function initialize() {





  var LatLng = new google.maps.LatLng(37.339739, 126.73519897);
//  var LatLng = new google.maps.LatLng($la,$lo);


  var mapProp = {

    center: LatLng, // 위치

    zoom:18, // 어느정도까지 세세하게 볼 것인지.

    mapTypeId:google.maps.MapTypeId.ROADMAP

  };

  var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);

 
  var marker = new google.maps.Marker({

   position: LatLng,
   map: map,
   info: '착용자의 현재위치',
   title: '착용자의 현재위치'
   
  });

  var content = "착용자의 현재위치입니다.";
  var infowindow = new google.maps.InfoWindow({content:content});
  google.maps.event.addListener(marker,'click',function(){
     infowindow.open(map,marker);
  });
 
  
}

google.maps.event.addDomListener(window, 'load', initialize);

</script>

</head>

<body onload="initialize()">
 현재위치를 표시합니다.
 <div id="googleMap" style="width:100%;height:380px;"></div>





</body>
</html>


