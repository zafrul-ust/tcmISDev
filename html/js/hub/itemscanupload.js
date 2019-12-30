function checkInput()
{
var errorMessage = "Please enter valid values for: \n\n";
var errorCount = 0;

var theFile = document.getElementById("theFile");
if (theFile.value.trim().length == 0)
{
 errorMessage = errorMessage + " File. \n" ;
 errorCount = 1;
}

if (errorCount >0)
{
  alert(errorMessage);
  return false;
}
else
{
  return true;
}
}
