function hubchanged()
{
	var hubO = document.getElementById("sourceHub");
	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
        igchanged();

}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,"All","")
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

function igchanged()
{
	var igO = document.getElementById("inventoryGroup");
	var shipToO = document.getElementById("shipTo");
	var selig = igO.value;

	for (var i = shipToO.length; i > 0;i--)
   {
      shipToO[i] = null;
   }
        if(selig != null && selig.length>0) {
	  for (var i = shipToO.length; i > 0;i--) {
            shipToO[i] = null;
          }
	  showshiptolinks(selig);
        }
        else {
          showshiptolinks("");
        }
        shipToO.selectedIndex = 0;
}

function showshiptolinks(selectedig)
{
    var shiptoid = new Array();
    shiptoid = altigid[selectedig];

    var shiptoName = new Array();
    shiptoName = altigName[selectedig];

    if(shiptoid != null && shiptoid.length > 0) {
      for (var i=0; i < shiptoid.length; i++) {
        setCab2(i,shiptoName[i],shiptoid[i])
      }
    }
    else {
      setCab2(0,"All","")
    }
}

function setCab2(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var shiptoO = document.getElementById("shipto");
	 shiptoO[href] = optionName;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function searchCarrierInfo(parentSelectedRow,branchPlant) {
   var inventoryGroup = document.getElementById("inventoryGroup"+parentSelectedRow);
   var tmp = escape(inventoryGroup.value);
   var loc = "/tcmIS/Hub/ShipConfirm?UserAction=BuildCarrierInfoPage&parentSelectedRow="+parentSelectedRow+"&inventoryGroup="+tmp;
   openWinGeneric(loc,"searchCarrierInfo","800","500","yes","80","80")
}

function consolidate() {
   var totallines = document.getElementById("totallines").value;
   totallines = totallines*1;
   if(totallines == 0) {
     alert("You must select at least one shipment to print.");
   }
   else {
     var checked = "no";
     var tmp = "";
     var shippingId;
     for (var p = 0 ; p < totallines ; p ++) {
        var linecheck = document.getElementById("rowChk"+(p*1)+"");
	if(linecheck.checked) {
	  checked="yes";
          shippingId = document.getElementById("shipmentId"+(p*1)+"");
          if(tmp == "") {
            tmp = "?shipmentIds=" + "" + shippingId.value;
          }
          else {
            tmp = tmp + "," + shippingId.value;
          }
	}
     }
     if(checked == "no") {
       alert("You must select at least one shipment to print.");
     }
     else {
       var loc = "/tcmIS/hub/printpackinglist.do" + tmp;
       openWinGeneric(loc,"consolidate","800","500","yes","80","80")
    }
  }
}

function consolidateBol() {
   var totallines = document.getElementById("totallines").value;
   totallines = totallines*1;
   if(totallines == 0) {
     alert("Please select a shipment id that you want to print.");
   }else {
     var checked = "no";
     var tmp = "";
     var shippingId;
     var count = 0;
     for (var p = 0 ; p < totallines ; p ++) {
        var linecheck = document.getElementById("rowChk"+(p*1)+"");
	if(linecheck.checked) {
	  checked="yes";
          count++;
          shippingId = document.getElementById("shipmentId"+(p*1)+"");
          if(tmp == "") {
            tmp = "?shipmentIds=" + "" + shippingId.value;
          }else {
            tmp = tmp + "," + shippingId.value;
          }
	}
     }
     if(checked == "no") {
       alert("Please select a shipment id that you want to print.");
     }else if (count > 1) {
       alert("Only one shipment id can be print at a time.");
     }else {
       var loc = "/tcmIS/hub/printconsolidatedbol.do" + tmp;
       openWinGeneric(loc,"printconsolidatebol","800","500","yes","80","80")
    }
  }
}


function checkall(checkboxname)
{
var checkall = document.getElementById("chkAll");
var totallines = document.getElementById("totallines").value;
totallines = totallines*1;

var result ;
var valueq;
if (checkall.checked)
{
  result = true;
  valueq = "yes";
}
else
{
  result = false;
  valueq = "no";
}

for ( var p = 0 ; p < totallines ; p ++)
{
	try
	{
	var linecheck = document.getElementById(""+checkboxname+""+(p*1)+"");
	linecheck.checked =result;
	linecheck.value = valueq;
	}
	catch (ex1)
	{

	}
}

}

function update(entered)
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
    return true;
}

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

function search(entered)
{
    var fromDate  =  document.getElementById("fromDate");
    if ( fromDate.value.length == 10 )
    {
      if ( checkdate(fromDate) == false )
      {
	     alert("Please Select a valid from Date.");
        return false;
	   }
	 }
    else if ( fromDate.value.length > 0 )
    {
		alert("Please Select a valid from Date.");
      return false;
    }

    var toDate  =  document.getElementById("toDate");

    if ( toDate.value.length == 10 )
    {
      if ( checkdate(toDate) == false )
      {
	     alert("Please Select a valid to Date.");
        return false;
	   }
	 }
    else if ( fromDate.value.length > 0 )
    {
		alert("Please Select a valid to Date.");
      return false;
    }

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