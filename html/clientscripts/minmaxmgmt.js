var submitcount=0;
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


function checkBillingMethod(rownum)
{
var finalMsgt = "";
var allClear = 0;
var total  =  (document.getElementById("total").value)*1;
 var operatingMethod  =  document.getElementById("operatingMethod"+(rownum)+"");
 var stockmethod  =  document.getElementById("stockmethod"+(rownum)+"");
 var reorderpt  =  document.getElementById("reorderpt"+(rownum)+"");
 var stocklevel  =  document.getElementById("stocklevel"+(rownum)+"");
 var catpartno  =  document.getElementById("catpartno"+(rownum)+"");
 var reorderQuantity  =  document.getElementById("reorderQuantity"+(rownum)+"");

 //alert("Check if IOR then MIN MAX is 0/0 and stocking method can only be OOR");
 if (operatingMethod.value == "Issue On Receipt" && (stockmethod.value != "OOR"))
 {
	if (reorderpt.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Point cannot be > 0 for Issue On Receipt for part " +catpartno.value+ "\n\n";
   	//reorderpt.value = "0";
   	allClear = 1;
	}

	if (stocklevel.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for Issue On Receipt for part " +catpartno.value+ "\n\n";
   	//stocklevel.value = "0";
   	allClear = 1;
	}

    if (reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Quantity cannot be > 0 for Issue On Receipt for part " +catpartno.value+ "\n\n";
   	//reorderQuantity.value = "";
   	allClear = 1;
	}
 }

 /*if (stockmethod.value == "OOR")
 {
	if (operatingMethod.value == "Home Company Owned")
	{
		finalMsgt = finalMsgt + "Operating Method cannot be Home Company Owned for OOR part " +catpartno.value+ "\n\n";
   	allClear = 1;
   }
 }*/

 if (allClear > 0)
 {
	alert(finalMsgt);
   return false;
 }
 return true;
}

function checkOperatingMethod(rownum)
{
var finalMsgt = "";
var allClear = 0;
var total  =  (document.getElementById("total").value)*1;
 var operatingMethod  =  document.getElementById("operatingMethod"+(rownum)+"");
 var stockmethod  =  document.getElementById("stockmethod"+(rownum)+"");
 var reorderpt  =  document.getElementById("reorderpt"+(rownum)+"");
 var stocklevel  =  document.getElementById("stocklevel"+(rownum)+"");
 var catpartno  =  document.getElementById("catpartno"+(rownum)+"");
 var reorderQuantity  =  document.getElementById("reorderQuantity"+(rownum)+"");

 //alert("Check if IOR then MIN MAX is 0/0 and stocking method can only be OOR");
 if (operatingMethod.value == "Issue On Receipt" && (stockmethod.value != "OOR"))
 {
	if (reorderpt.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Point cannot be > 0 for Issue On Receipt for part " +catpartno.value+ "\n\n";
   	//reorderpt.value = "0";
   	allClear = 1;
	}

	if (stocklevel.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for Issue On Receipt for part " +catpartno.value+ "\n\n";
   	//stocklevel.value = "0";
   	allClear = 1;
	}

    if (reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Quantity cannot be > 0 for Issue On Receipt for part " +catpartno.value+ "\n\n";
   	//reorderQuantity.value = "";
   	allClear = 1;
	}
 }

 /*if (stockmethod.value == "OOR")
 {
	if (operatingMethod.value == "Home Company Owned")
	{
		finalMsgt = finalMsgt + "Operating Method cannot be Home Company Owned for OOR part " +catpartno.value+ "\n\n";
   	allClear = 1;
   }
 }*/


 if (allClear > 0)
 {
	alert(finalMsgt);
   return false;
 }
 return true;
}

function checkqtyvalue (controlname)
{
	var controlname  =  document.getElementById(controlname);
	if ( controlname.value.trim().length > 0 && (!(isInteger(controlname.value)) || controlname.value*1 == 0))
   {
   	alert(" Please enter a valid Non Zero Number.") ;
		controlname.value = "";
   }
}

function checkreorderQuantity(rownum)
{
var finalMsgt = "";
var allClear = 0;
var stockmethod  =  document.getElementById("stockmethod"+rownum+"");
var reorderpt  =  document.getElementById("reorderpt"+rownum+"");
var stocklevel  =  document.getElementById("stocklevel"+rownum+"");
var reorderQuantity  =  document.getElementById("reorderQuantity"+rownum+"");

if ( reorderQuantity.value.trim().length > 0 && !(isInteger(reorderQuantity.value)) )
{
	finalMsgt = finalMsgt + "Please enter a valid number for Reorder Quantity.\n\n";
	allClear = 1;
    reorderQuantity.value = "";
}
else if (reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 == 0)
{
 	finalMsgt = finalMsgt + "Reorder Quantity cannot be 0.\n\n";
    reorderQuantity.value = "";
 	allClear = 1;
}

if (!(reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0) && (reorderpt.value.trim()*1 > stocklevel.value.trim()*1))
{
 	finalMsgt = finalMsgt + "Reorder Point cannot be > Stocking Level.\n\n";
 	allClear = 1;
}

if (stockmethod.value == "OOR")
{
	if (reorderpt.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Point cannot be > 0 for OOR\n\n";
   	//reorderpt.value = "0";
   	allClear = 1;
	}

	if (stocklevel.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for OOR\n\n";
   	//stocklevel.value = "0";
   	allClear = 1;
	}

    if (reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Quantity cannot be > 0 for OOR\n\n";
   	//reorderQuantity.value = "";
   	allClear = 1;
	}
}


var operatingMethod  =  document.getElementById("operatingMethod"+(rownum)+"");
var changeOperatingMethod  =  document.getElementById("changeOperatingMethod");
if (changeOperatingMethod.value == "Yes")
{
 if (operatingMethod.value == "Issue On Receipt" && (stockmethod.value != "OOR"))
 {
	if (reorderpt.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Quantity cannot be > 0 for Issue On Receipt\n\n";
   	//reorderpt.value = "0";
   	allClear = 1;
	}

	if (stocklevel.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for Issue On Receipt\n\n";
   	//stocklevel.value = "0";
   	allClear = 1;
	}

    if (reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Quantity cannot be > 0 for Issue On Receipt\n\n";
   	//reorderQuantity.value = "";
   	allClear = 1;
	}
 }

 /*if (stockmethod.value == "OOR")
 {
	if (operatingMethod.value == "Home Company Owned")
	{
		finalMsgt = finalMsgt + "Operating Method cannot be Home Company Owned for OOR\n\n";
     	allClear = 1;
   }
 }*/
}

if (allClear > 0)
{
	alert(finalMsgt);
}
else if (reorderQuantity.value.trim().length > 0)
{
 stocklevel.value = "0";
}

}

function checkreorderpt (rownum)
{
var finalMsgt = "";
var allClear = 0;
var stockmethod  =  document.getElementById("stockmethod"+rownum+"");
var reorderpt  =  document.getElementById("reorderpt"+rownum+"");
var stocklevel  =  document.getElementById("stocklevel"+rownum+"");
var reorderQuantity  =  document.getElementById("reorderQuantity"+rownum+"");

if ( !(isInteger(reorderpt.value)) )
{
	finalMsgt = finalMsgt + "Please enter a valid number for Reorder point.\n\n";
	allClear = 1;
   reorderpt.value = "";
}
else if (!(reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0) && (reorderpt.value.trim()*1 > stocklevel.value.trim()*1))
{
 	finalMsgt = finalMsgt + "Reorder Point cannot be > Stocking Level.\n\n";
 	allClear = 1;
}

if (stockmethod.value == "OOR")
{
	if (reorderpt.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Point cannot be > 0 for OOR\n\n";
   	//reorderpt.value = "0";
   	allClear = 1;
	}

	if (stocklevel.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for OOR\n\n";
   	//stocklevel.value = "0";
   	allClear = 1;
	}

    if (reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Quantity cannot be > 0 for OOR\n\n";
   	//reorderQuantity.value = "";
   	allClear = 1;
	}
}

var operatingMethod  =  document.getElementById("operatingMethod"+(rownum)+"");
var changeOperatingMethod  =  document.getElementById("changeOperatingMethod");
if (changeOperatingMethod.value == "Yes")
{
 if (operatingMethod.value == "Issue On Receipt" && (stockmethod.value != "OOR"))
 {
	if (reorderpt.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Point cannot be > 0 for Issue On Receipt\n\n";
   	//reorderpt.value = "0";
   	allClear = 1;
	}

	if (stocklevel.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for Issue On Receipt\n\n";
   	//stocklevel.value = "0";
   	allClear = 1;
	}

    if (reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Quantity cannot be > 0 for OOR\n\n";
   	//reorderQuantity.value = "";
   	allClear = 1;
	}
 }

 /*if (stockmethod.value == "OOR")
 {
	if (operatingMethod.value == "Home Company Owned")
	{
		finalMsgt = finalMsgt + "Operating Method cannot be Home Company Owned for OOR\n\n";
   	allClear = 1;
   }
 }*/
}

if (allClear > 0)
{
	alert(finalMsgt);
}

}

function checkstocklevel (rownum)
{
var finalMsgt = "";
var allClear = 0;
var stockmethod  =  document.getElementById("stockmethod"+rownum+"");
var reorderpt  =  document.getElementById("reorderpt"+rownum+"");
var stocklevel  =  document.getElementById("stocklevel"+rownum+"");
var reorderQuantity  =  document.getElementById("reorderQuantity"+rownum+"");

if ( !(isInteger(stocklevel.value)) )
{
	finalMsgt = finalMsgt + "Please enter a valid number for Stocking Level.\n\n";
	allClear = 1;
  stocklevel.value = "";
}
else if (!(reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0) && (reorderpt.value.trim()*1 > stocklevel.value.trim()*1))
{
 	finalMsgt = finalMsgt + "Reorder Point cannot be > Stocking Level.\n\n";
 	allClear = 1;
}

if (stockmethod.value == "OOR")
{
	if (reorderpt.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Point cannot be > 0 for OOR\n\n";
   	//reorderpt.value = "0";
   	allClear = 1;
	}

	if (stocklevel.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for OOR\n\n";
   	//stocklevel.value = "0";
   	allClear = 1;
	}

    if (reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Quantity cannot be > 0 for OOR\n\n";
   	//reorderQuantity.value = "";
   	allClear = 1;
	}
}

var operatingMethod  =  document.getElementById("operatingMethod"+(rownum)+"");
var changeOperatingMethod  =  document.getElementById("changeOperatingMethod");
if (changeOperatingMethod.value == "Yes")
{
 if (operatingMethod.value == "Issue On Receipt" && (stockmethod.value != "OOR"))
 {
	if (reorderpt.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Point cannot be > 0 for Issue On Receipt\n\n";
   	//reorderpt.value = "0";
   	allClear = 1;
	}

	if (stocklevel.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for Issue On Receipt\n\n";
   	//stocklevel.value = "0";
   	allClear = 1;
	}

    if (reorderQuantity.value.trim().length > 0 && reorderQuantity.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Quantity cannot be > 0 for OOR\n\n";
   	//reorderQuantity.value = "";
   	allClear = 1;
	}
 }

 /*if (stockmethod.value == "OOR")
 {
	if (operatingMethod.value == "Home Company Owned")
	{
		finalMsgt = finalMsgt + "Operating Method cannot be Home Company Owned for OOR\n\n";
   	    allClear = 1;
   }
 }*/
}

if (allClear > 0)
{
	alert(finalMsgt);
}
else if (stocklevel.value.trim().length > 0)
{
 reorderQuantity.value = "";
}

}

function checkoorvalues(rownum)
{
	var finalMsgt = "";
   var allClear = 0;

	var stockmethod  =  document.getElementById("stockmethod"+rownum+"");
	var reorderpt  =  document.getElementById("reorderpt"+rownum+"");
	var stocklevel  =  document.getElementById("stocklevel"+rownum+"");
   var reorderQuantity  =  document.getElementById("reorderQuantity"+rownum+"");

	if (stockmethod.value == "OOR")
	{
   	 stocklevel.style.display = "none";
   	 reorderpt.style.display = "none";
   	 reorderQuantity.style.display = "none";

		/*if (reorderpt.value.trim()*1 > 0)
	   {
	      finalMsgt = finalMsgt + "Reorder Point cannot be > 0 for OOR\n\n";
	      allClear = 1;
	   }

	   if (stocklevel.value.trim()*1 > 0)
	   {
	      finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for OOR\n\n";
	      allClear = 1;
	   }

	   if (allClear > 0)
	   {
	      alert(finalMsgt);
	      stockmethod.value = "MM";
	   }*/

	}
	else if (stockmethod.value == "MM")
	{
		stocklevel.style.display = "block";
	   reorderpt.style.display = "block";
	   reorderQuantity.style.display = "block";
		/*if (reorderpt.value.trim()*1 > stocklevel.value.trim()*1)
		{
			finalMsgt = finalMsgt + "Reorder Point cannot be > Stocking Level.\n\n";
     		allClear = 1;
		}

		if (allClear > 0)
		{
	  		alert(finalMsgt);
	  		reorderpt.value = "";
		}*/
	}


var operatingMethod  =  document.getElementById("operatingMethod"+(rownum)+"");
var changeOperatingMethod  =  document.getElementById("changeOperatingMethod");
if (changeOperatingMethod.value == "Yes")
{
 /*if (operatingMethod.value == "Issue On Receipt")
 {
	if (reorderpt.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Reorder Point cannot be > 0 for Issue On Receipt\n\n";
   	//reorderpt.value = "0";
   	allClear = 1;
	}

	if (stocklevel.value.trim()*1 > 0)
	{
   	finalMsgt = finalMsgt + "Stocking Level cannot be > 0 for Issue On Receipt\n\n";
   	//stocklevel.value = "0";
   	allClear = 1;
	}
 }*/

 /*if (stockmethod.value == "OOR")
 {
	if (operatingMethod.value == "Home Company Owned")
	{
		finalMsgt = finalMsgt + "Operating Method cannot be Home Company Owned for OOR\n\n";
   	allClear = 1;
   }
 }*/
}

if (allClear > 0)
{
	alert(finalMsgt);
}

}

/*
if ( !(isSignedInteger(quantity.value)) )
                {
                    LineMsg = LineMsg + "       Valid Quantity.\n" ;
                    allClearforline = 1;
                }
*/

function hubchanged(object)
{
 var HubName  =  document.getElementById("HubName");
 artist = HubName.value;

 //var artist;
 //artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.minmaxlvls.invengrp.length; i > 0;i--)
   {
      document.minmaxlvls.invengrp.options[i] = null;
   }
	showinvlinks(artist);
	window.document.minmaxlvls.invengrp.selectedIndex = 0;
}

function showinvlinks(selectedIndex)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedIndex];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedIndex];

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.minmaxlvls.invengrp.options[href] = optionName;
}

function actionValue(name, entered)
{
    var actvalue = name.toString();
    window.document.minmaxlvls.UserAction.value = actvalue;

    var changeOperatingMethod  =  document.getElementById("changeOperatingMethod");
    var result = true;
    /*if (changeOperatingMethod.value == "Yes")
    {
     result = checkOperatingMethod();
    }*/
    if (result)
    {
	 var searchtext  =  document.getElementById("searchtext");
	 if (searchtext.value.trim().length > 0)
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
    else
    {
		alert("Please enter a item ID to search");
		return false;
    }
    }
    else
    {
     return false;
    }
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function cancel()
{
    window.close();
}