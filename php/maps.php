<?php
error_reporting(E_ALL);
ini_set('display_errors',1);
 
$link=mysqli_connect("localhost","root","root","GPS");// localhost, root, 개인 root 비밀번호, db 순으로 입력하세요.
if (!$link)
{
   echo "MySQL 접속 에러 : ";
   echo mysqli_connect_error();
   exit();
 
}
 
mysqli_set_charset($link,"utf8");
//POST 방식으로 데이터를 받아온다.
 
$name=isset($_POST['name']) ? $_POST['name'] : '';
 
if ($name !=""){
 
    $sql="insert into fighting(name) values('$name')";
    $result=mysqli_query($link,$sql);
 
    if($result){
 
       echo "SQL문 처리 성공";
    }
    else{
       echo "SQL문 처리중 에러 발생 : ";
       echo mysqli_error($link);
    }
} else {
 
    echo "데이터를 입력하세요 ";
 
}
 
mysqli_close($link);
?>
 
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=euc-kr"/>
<meta name="description" content="지도에 주소표시 위도 경도 찾기 " />
<meta name="keywords" content="위도, 경도, 구글맵API, 구글맵 주소표시"/>
<meta name="author" content="webmaster@iegate.net">
<meta name="robots" content="ALL">
<meta name="revisit-after" content="1 week">
<meta name="rating" content="general">
 
 
<style>
body, p, input, button { font-family:Tahoma,굴림; font-size:9pt; color:#222222; }
form { margin:0px; }
</style>
</head>
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAeU8PXsv-CcInm201SSTsdBRtLhwu-T2v9jUWMzzaOm0MN4E4txR7oQsv4whFmTZ49vjxhZ64Khjzqg" type="text/javascript"></script>
    <script type="text/javascript">
 
    var map;
    var geocoder;
 
    function initialize() {
      map = new GMap2(document.getElementById('map_canvas'));
      map.setCenter(new GLatLng(37.566535,126.9779692), 15);
      geocoder = new GClientGeocoder();
      map.disableDoubleClickZoom();
          map.addControl(new GNavLabelControl());
          map.addControl(new GSmallMapControl());
    }
 
// 맵정보
    function addAddressToMap(response) {
      map.clearOverlays();
      if (!response || response.Status.code != 200) {
        alert("Sorry, 주소를 확인해 주세요!!");
      } else {
        place = response.Placemark[0];
        point = new GLatLng(place.Point.coordinates[1],
                            place.Point.coordinates[0]);
        marker = new GMarker(point);
        map.addOverlay(marker);
        marker.openInfoWindowHtml(
//                      place.address + '<br>' +
        '<b>위도,경도:</b>' + place.Point.coordinates[1] + "," + place.Point.coordinates[0] + '<br>' +
        '<b>주소:</b>' + place.address);
//        '<b>Status Code:</b>' + response.Status.code + '<br>' +
//       '<b>Status Request:</b>' + response.Status.request + '<br>' +
//        '<b>Country code:</b> ' + place.AddressDetails.Country.CountryNameCode);
 
      }
    }
 
    function showLocation() {
      var address = document.forms[0].q.value;
      geocoder.getLocations(address, addAddressToMap);
    }
 
    function findLocation(address) {
      document.forms[0].q.value = address;
      showLocation();
    }
    </script>
  </head>
 
<body onload="initialize()">
<form action="#" onsubmit="showLocation(); return false;">
      <p>
        <b>싸움이 일어난 곳의 위도와 경도를 입력하세요 :</b>
        <input type="text" name="q" value="" class="address_input" size="40"  method=post />
        <input type="submit" name="find" value="Search" />
      </p>
    </form>
<form action="<?php $_PHP_SELF ?>" method="POST">
         한번 더 입력하세요 : <input type = "text" name = "name" />
         <input type = "submit" value="위치 저장"/>
    </form>
                <div id="map_canvas" style="width: 680px; height: 380px">
                        <div class="" style='overflow:hidden;text-align:center;border:1px solid #e1e1e1;width:680px;height:380px;margin-top:10px;padding-top:10px;margin-bottom:10px; '>
                        <img src='http://maps.google.com/maps/api/staticmap?center=&zoom=5&size=640x360&markers=icon:http://www.iegate.net/maps/images/ruby_point.png|,&sensor=false'></div>
                </div>
 
 <!--[if lt IE 7]>
<div style='border: 1px solid #F7941D; background: #FEEFDA; text-align: center; clear: both; height: 50px; position: relative;'>
<div style='font-size: 12px; font-weight: bold; margin-top: 12px;'>최신 브라우저로 지금 업그레이드 해주세요.<br/> IE6 이하 버젼에서는 지원되지 않는 기능이 있습니다.
</div>
</div>
<![endif]-->
        </div>
 </body>
</html>