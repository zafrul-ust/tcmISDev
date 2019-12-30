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

function quantityChanged(rowNumber)
{
  poLineStatus = document.getElementById("poLineStatus"+rowNumber+"");
  if ( (poLineStatus.value == "Confirmed") || (poLineStatus.value == "Closed") )
  {
    poLineStatus.value = "Unconfirmed";

    amendment = document.getElementById("amendment"+rowNumber+"");
    amendment.value = (amendment.value*1)+1;
  }

  lineChangeStatus = document.getElementById("lineChangeStatus"+rowNumber+"");
  lineChangeStatus.value = "Yes";
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