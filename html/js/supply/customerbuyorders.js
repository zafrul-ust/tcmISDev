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

function showCreatedPurchaseOrder(createdPos)
{
 alert(createdPos);
 createdPos = createdPos.replace(/,/gi, "%2c");
 var loc = "/tcmIS/hub/showpurchaseorders.do?submitSearch=Yes,searchWhat=RADIAN_PO&searchType=IN&searchText="+createdPos+"";
 openWinGeneric(loc,"showCreatedPurchaseOrder11","800","600","yes","80","80");
}

function doexcelpopup()
{
 excelfileurl = "/tcmIS/common/viewexcel.jsp";

 openWinGenericViewFile(excelfileurl,"show_excel_report_file","610","600","yes");
}

function cancel()
{
    window.close();
}

function showpurchaserequestspagelegend()
{
        openWinGeneric("/tcmIS/help/buypagelegend.html","buypagelegend","290","400","no","50","50")
}

function checkBuyorder(rowNumber)
{
var errorMessage = "Please enter valid values for: \n\n";
var warningMessage = "Alert: \n\n";
var errorCount = 0;
var warnCount = 0;

var ok = document.getElementById("ok"+rowNumber+"");
if (ok.checked)
{
var raytheonPo = document.getElementById("raytheonPo"+rowNumber+"");
if (raytheonPo.value.trim().length == 0)
{
 errorMessage = errorMessage + " PO #. \n" ;
 errorCount = 1;
}

try
{
 var orderQuantity = document.getElementById("orderQuantity"+rowNumber+"");
 if ( !(isInteger(orderQuantity.value)) || orderQuantity.value*1 == 0 )
 {
   errorMessage = errorMessage + " Quantity. \n";
	errorCount = 1;
   orderQuantity.value = "";
 }
}
catch (ex)
{

}

}

if (errorCount >0)
{
 alert(errorMessage);
 ok.checked = false;
 return false;
}

if (warnCount >0)
{
 alert(warningMessage);
}
return true;
}

function hubchanged()
{
	var hubO = document.getElementById("branchPlant");
	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setInventoryGroup(0,"All","")
    }

    for (var i=0; i < invgrpid.length; i++)
    {
        setInventoryGroup(i,invgrpName[i],invgrpid[i])
    }
}

function setInventoryGroup(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = document.getElementById("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}