var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function submitmainpage()
{
  opener.search();
  opener.document.receiving.submit();
  window.close();
}

function addtapid( tapid )
{
     window.document.undotap.undotapid.value=tapid;
}

function undoupdate(entered)
{
 var undotapid = document.getElementById("undotapid");
  if (undotapid.value.length > 0)
  {
    window.document.undotap.Action.value = "UPDATE";
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
	 alert("Please select a Tap ID to reverse.");
    return false;
  }
}

function undotapsearch(entered)
{
    window.document.undotap.Action.value = "Search";
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



function cancel()
{
    window.close();
}