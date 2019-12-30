var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce()
{
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

        var userAction  =  document.getElementById("userAction");
        userAction.value = "search";
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

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function facilityChanged()
{
	var facilityIdO = document.getElementById("facilityId");
	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selfacility = facilityIdO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
   if (selfacility == 'All')
   {
     setCab(0,"Please Select","All");
   }
   else
   {
	showinvlinks(selfacility);
   }
	inventoryGroupO.selectedIndex = 0;
}

function showinvlinks(selfacility)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selfacility];

	 var invgrpName = new Array();
    invgrpName = altinvName[selfacility];

    var invenGroupIn = "";
    var invgrpinCount = 0;

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])

        if (invgrpid[i] != 'All')
        {
        if (invgrpinCount > 0)
        {
        invenGroupIn =  invenGroupIn + ",'" + invgrpid[i] + "'"
        }
        else
        {
        invenGroupIn =  invenGroupIn + "'" + invgrpid[i] + "'"
        }
        invgrpinCount++;
        }
    }

   var invenGroupInO  =  document.getElementById("invenGroupIn");
   invenGroupInO.value = invenGroupIn;
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = document.getElementById("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}

function readonlysearch()
{
 var facilityId  =  document.getElementById("facilityId");
 var inventoryGroup  =  document.getElementById("inventoryGroup");

	 if (facilityId.value == "All")
	 {
	  alert("Please Select a Facility.");
	  return false
	 }
	 else
    {
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

    var userAction  =  document.getElementById("userAction");
    userAction.value = "search";
    return true;
    }
}

function createxlsreport(entered)
{
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

	 var userAction  =  document.getElementById("userAction");
    userAction.value = "createxlsreport";
    return true;
}