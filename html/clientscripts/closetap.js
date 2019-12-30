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
     window.document.closetap.closetapid.value=tapid;
}

function closeupdate(entered)
{
  var closetapid = document.getElementById("closetapid");
  if (closetapid.value.length > 0)
  {
    window.document.closetap.Action.value = "UPDATE";
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
	 alert("Please select a Tap ID to close.");
    return false;
  }
}

function closetapsearch(entered)
{
    window.document.closetap.Action.value = "Search";
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