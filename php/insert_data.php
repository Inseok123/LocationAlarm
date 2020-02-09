<?php
	$con = mysqli_connect("localhost", "root", "root", "GPS"); 

	if (mysqli_connect_errno())
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}

   $num  = $_GET["latitude"];
   $num1 = $_GET["longtitude"];


   $sql = "insert into gpsvalue(latitude,longtitude)values($num,$num1)";
   $ret = mysql_query($sql);
  
   mysqli_query($con, $sql);
 if($ret){
   	echo "Success Insert Query";
   }
   mysqli_close($con);
?>