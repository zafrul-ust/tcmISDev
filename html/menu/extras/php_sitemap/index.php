<html>	
<body>

This file needs to be executed under PHP from a webserver and will take the <br>
contents of any Milonic menu data file and output the results as either a <br>
NoScript menu or as a Sitemap


<?

//$menuDataFile[]="http://www.milonic.com/menu_data.php"; // Can execute a WWW File

$menuDataFile[]="../../menu_data.js"; // Or can execute a local file

//$menuDataFile[]="/main_menu_data.php"; // You can also add more than one file
//$menuDataFile[]="/menu_data.php";      // For main and sub menus if you need to


$useMenuStyles=false;
include("mm_dataminer.php");
echo "<PRE>".outputAsSiteMap($menuDataFile)."</PRE>"; // Can output as a Sitemap
//echo "<PRE>".outputAsNoScript($menuDataFile)."</PRE>"; // Or output as a NoScript Sitemap

?>

</body>
</html>