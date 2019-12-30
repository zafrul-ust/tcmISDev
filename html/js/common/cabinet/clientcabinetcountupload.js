var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce()
{
   try
   {
     var sourceHubName = null;
     sourceHubName =  hubO.options[hubO.selectedIndex].text;

     var sourceHubNameObject = document.getElementById("sourceHubName");
     sourceHubNameObject.value = sourceHubName;
   }
   catch (ex)
   {
     //alert("Non-Pickable Status");
   }

    if (submitcount == 0)
    {
        submitcount++;
        try
        {
         var target = document.all.item("TRANSITPAGE");
         target.style["display"] = "";
         var target1 = document.all.item("MAINPAGE");
         target1.style["display"] = "none";
        }
        catch (ex)
        {
        }

        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}


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
