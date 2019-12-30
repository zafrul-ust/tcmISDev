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

function doexcelpopup()
{
 excelfileurl = "/tcmIS/common/viewexcel.jsp";

 openWinGeneric(excelfileurl,"show_excel_report_file","610","600","yes");
}

function hubchanged()
{
	var hubO = document.getElementById("hub");
	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
	removeAllInvocieDates();
}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,"Please Select","All")
    }

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = document.getElementById("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}

function removeAllInvocieDates()
{
	var invoiceDateO = document.getElementById("invoiceDate");
	 if(invoiceDateO.length <= 0)
          return;

	for (var i = invoiceDateO.length; i > 0;i--)
   {
      invoiceDateO[i] = null;
   }
}

function customerSearch(entered)
{
 var invoiceDate  =  document.getElementById("invoiceDate");
    if (invoiceDate.value == "All")
    {
     alert("Please Select a Invoice Period");
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

    var UserAction  =  document.getElementById("UserAction");
    UserAction.value = "search";
    return true;
    }
}

function search(entered)
{
 var invoiceDate  =  document.getElementById("invoiceDate");
    var inventoryGroup  =  document.getElementById("inventoryGroup");

	 if (inventoryGroup.value == "All")
	 {
	  alert("Please Select a Inventory Group");
	  return false
	 }
    else if (invoiceDate.value == "All")
    {
     alert("Please Select a Invoice Period");
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

    var UserAction  =  document.getElementById("UserAction");
    UserAction.value = "search";
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

	 var UserAction  =  document.getElementById("UserAction");
    UserAction.value = "createxlsreport";
    return true;
}

function getinvociedates(entered)
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

	 var UserAction  =  document.getElementById("UserAction");
    UserAction.value = "getinvociedates";

    window.document.monthylInvenDetailForm.submit();
}