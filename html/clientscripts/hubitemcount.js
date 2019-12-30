var submitcount=0;
var updatecount=0;
var checkCount=0;

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

function cancel()
{
window.close();
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}


function checkall(checkboxname)
{
 var checkall = document.getElementById("chkall");
 var totalLines = document.getElementById("totalNumber").value;
 totalLines = totalLines*1;
 var result ;
 if (checkall.checked)
 {
  result = true;
 }
 else
 {
  result = false;
 }

 for ( var p = 1 ; p <= totalLines ; p ++)
 {
	try
	{
	var countStatus = document.getElementById("countStatus"+(p)+"");
	countStatus.checked =result;
	}
	catch (ex1)
	{

	}
 }
}

function checkValue(rowNum)
{
    var act_on_hand  =  document.getElementById("act_on_hand"+rowNum+"");
    if (act_on_hand.value.trim().length > 0)
    {
    if ( !(isFloat(act_on_hand.value)) )
    {
        alert ( "Invalid Number\n Please Enter a Valid Number.");
        act_on_hand.value = "";
    }
    else
    {
        var expectedQuantity  =  document.getElementById("expectedQuantity"+rowNum+"");
        if (expectedQuantity.value*1 != act_on_hand.value)
        {
           var countStatus  =  document.getElementById("countStatus"+rowNum+"");
           countStatus.checked=true;
           //countStatus.value = "Counted";
        }

        if (((act_on_hand.value.trim()*1-expectedQuantity.value.trim()*1)/expectedQuantity.value.trim()*1)*100 > 25)
        {
         var qtyErrorMessage = "Alert:\n\nQuantity counted ("+act_on_hand.value.trim()+") is "+Math.round(10000*(act_on_hand.value.trim()*1-expectedQuantity.value.trim()*1)/expectedQuantity.value.trim()*1)/100+"% greater than quantity expected ("+expectedQuantity.value.trim()+").\n\nPlease double check the quantity.\n";
         alert(qtyErrorMessage);
        }

        updatecount++;
    }
    }

    var operatingMethod  =  document.getElementById("operatingMethod"+rowNum+"");
    if (operatingMethod.value == "Issue On Receipt")
    {
           alert("All of this material will be issued out");
    }
}

function checkCheckBox(rowNum)
{
 var countStatus  =  document.getElementById("countStatus"+rowNum+"");
 if (countStatus.checked)
 {
 var actualCountDate = document.getElementById("actualCountDate");
 var countStartDate = document.getElementById("countStartDate");
 var warningMessage = "Alert: \nBlack lines will be excluded from your count.\n\nReason:\n";
 var warnCount = 0;

 var lastDateOfReceipt = document.getElementById("lastDateOfReceipt"+rowNum+"").value;
 var lastReceiptQcDate = document.getElementById("lastReceiptQcDate"+rowNum+"").value;
 var lastDateCounted = document.getElementById("lastDateCounted"+rowNum+"").value;
 var countAllowed = document.getElementById("countAllowed"+rowNum+"").value;
 var currentRow = document.getElementById("rowId"+rowNum+"");
 var disabled = document.getElementById("disabled"+rowNum+"");
 var operatingMethod = document.getElementById("operatingMethod"+rowNum+"").value;
 var catPartNum = document.getElementById("catPartNum"+rowNum+"").value;
 var countType = document.getElementById("countType"+rowNum+"").value;
 var inventoryGroupName = document.getElementById("inventoryGroupName"+rowNum+"").value;
 var itemId = document.getElementById("itemId"+rowNum+"").value;
 var countStatus  =  document.getElementById("countStatus"+rowNum+"");

 var disableLine = false;
 var countedBeforeLastCount = false;

  if (countAllowed == "true")
  {
   try
   {
      if (lastDateOfReceipt.trim().length == 10)
      {
 		   if (Date.parse(lastDateOfReceipt) > Date.parse(actualCountDate.value)) {
	      disableLine = true;
	    }
      }

      if (lastReceiptQcDate.trim().length == 10)
      {
		  if (Date.parse(lastReceiptQcDate) > Date.parse(countStartDate.value)) {
			disableLine = true;
	    }
      }

      if (lastDateCounted.trim().length == 10)
      {
 		    if (Date.parse(lastDateCounted) > Date.parse(actualCountDate.value)) {
	      disableLine = true;
        countedBeforeLastCount = true;
	    }
      }

		if (disableLine)
		{
			currentRow.className = "black";
			if (countType == "PART")
			{
       if (countedBeforeLastCount)
       {
        warningMessage = warningMessage + catPartNum + " in " +inventoryGroupName+ " was Last Cotunted on "+lastDateCounted+" before Actual Count Date. \n";
       }
       else
       {
        warningMessage = warningMessage + catPartNum + " in " +inventoryGroupName+ " was Received after the count was started. \n";
       }
			}
      else
      {
       if (countedBeforeLastCount)
       {
        warningMessage = warningMessage + itemId + " in " +inventoryGroupName+ " was Last Cotunted on "+lastDateCounted+" before Actual Count Date. \n";
       }
       else
       {
        warningMessage = warningMessage + itemId + " in " +inventoryGroupName+ " was Received after the count was started. \n";
       }
      }
   	  warnCount = 1;
			countStatus.value = "Counted";
			disabled.value = "true";
		}
   }
   catch (ex)
   {

   }
  }

  if (warnCount >0)
  {
		alert(warningMessage);
  }
 }
 else
 {
  var expectedQuantity  =  document.getElementById("expectedQuantity"+rowNum+"");
  var act_on_hand  =  document.getElementById("act_on_hand"+rowNum+"");
  act_on_hand.value = expectedQuantity.value;

  var currentRow = document.getElementById("rowId"+rowNum+"");
  var disabled = document.getElementById("disabled"+rowNum+"");
  var operatingMethod = document.getElementById("operatingMethod"+rowNum+"").value;
  if ((rowNum+1)%2==0)
  {
    currentRow.className = "blue";
    disabled.value = "false";
  }
  else
  {
  currentRow.className = "white";
  disabled.value = "false";
  }

  if (operatingMethod == "Issue On Receipt")
  {
  currentRow.className = "red";
  disabled.value = "false";
  }
 }
}

function checkLastDateReceived(showconfirmDialog)
{
 checkCount++;

 var totalLines = document.getElementById("totalNumber").value;
 var actualCountDate = document.getElementById("actualCountDate");
 var countStartDate = document.getElementById("countStartDate");

 var result = true;
 var warningMessage = "Alert: \nBlack lines will be excluded from your count.\n\nReason:\n";
 var warnCount = 0;
 var errorCount = 0;
 var errorMessage = "Please enter valid values for: \n\n";

 if (actualCountDate.value.trim().length == 10 && checkdate(actualCountDate))
 {
 for ( var p = 1 ; p <= totalLines ; p ++)
 {
  var lastDateOfReceipt = document.getElementById("lastDateOfReceipt"+p+"").value;
  var lastReceiptQcDate = document.getElementById("lastReceiptQcDate"+p+"").value;
  var lastDateCounted = document.getElementById("lastDateCounted"+p+"").value;
  var countAllowed = document.getElementById("countAllowed"+p+"").value;
  var currentRow = document.getElementById("rowId"+p+"");
  var disabled = document.getElementById("disabled"+p+"");
  var operatingMethod = document.getElementById("operatingMethod"+p+"").value;

  var catPartNum = document.getElementById("catPartNum"+p+"").value;
  var countType = document.getElementById("countType"+p+"").value;
  var inventoryGroupName = document.getElementById("inventoryGroupName"+p+"").value;

  var itemId = document.getElementById("itemId"+p+"").value;
  var countStatus  =  document.getElementById("countStatus"+p+"");

  var disableLine = false;
  var countedBeforeLastCount = false;

  if (countAllowed == "true" && countStatus.checked)
  {
   //alert("actualCountDate  "+actualCountDate.value+" "+actualCountDate.value.trim().length+"  "+checkdate(actualCountDate)+" lastDateOfReceipt "+lastDateOfReceipt+" lastReceiptQcDate "+lastReceiptQcDate+"");
   try
   {
      if (lastDateOfReceipt.trim().length == 10)
      {
 		   if (Date.parse(lastDateOfReceipt) > Date.parse(actualCountDate.value)) {
	      disableLine = true;
	    }
      }

      if (lastReceiptQcDate.trim().length == 10)
      {
		  if (Date.parse(lastReceiptQcDate) > Date.parse(countStartDate.value)) {
			disableLine = true;
	    }
      }

      if (lastDateCounted.trim().length == 10)
      {
 		    if (Date.parse(lastDateCounted) > Date.parse(actualCountDate.value)) {
	      disableLine = true;
        countedBeforeLastCount = true;
	    }
      }

		if (disableLine)
		{
			currentRow.className = "black";
			if (countType == "PART")
			{
       if (countedBeforeLastCount)
       {
        warningMessage = warningMessage + catPartNum + " in " +inventoryGroupName+ " was Last Cotunted on "+lastDateCounted+" before Actual Count Date. \n";
       }
       else
       {
        warningMessage = warningMessage + catPartNum + " in " +inventoryGroupName+ " was Received after the count was started. \n";
       }
			}
      else
      {
       if (countedBeforeLastCount)
       {
        warningMessage = warningMessage + itemId + " in " +inventoryGroupName+ " was Last Cotunted on "+lastDateCounted+" before Actual Count Date. \n";
       }
       else
       {
        warningMessage = warningMessage + itemId + " in " +inventoryGroupName+ " was Received after the count was started. \n";
       }
      }
   	  warnCount = 1;


      countStatus.checked=true;
			countStatus.value = "Counted";
			disabled.value = "true";
		}
		else if (disabled.value == "true")
		{
		  if (p%2==0)
        {
            currentRow.className = "blue";
            disabled.value = "false";
        }
        else
        {
            currentRow.className = "white";
            disabled.value = "false";
        }

        if (operatingMethod == "Issue On Receipt")
        {
				    currentRow.className = "red";
            disabled.value = "false";
        }

        checkValue(p);
		}
   }
   catch (ex)
   {

   }
  }
 }
}
else
{
 errorMessage = " Actual Count Date. \n";
 errorCount = 1;
}

 if (errorCount >0)
 {
   alert("Please enter valid values for: \n\n"+errorMessage);
   checkCount = 0;
	return false;
 }

 if (warnCount >0)
 {
   if (showconfirmDialog)
   {
   if (confirm(warningMessage+"\n\nContinue?"))
   {

   }
   else
   {
    checkCount = 0;
    return false;
   }
   }
   else
   {
		alert(warningMessage);
   }
 }
 return result;
}

function showitemcountpagelegend()
{
  openWinGeneric("/tcmIS/help/itemcountpagelegend.html","showitemcountpagelegend","290","300","no","50","50")
}

function additemID( itemID )
{
     document.additem2count.itemId.value=itemID;
}

function additem2count ()
{
    /*var countId  =  document.getElementById("countId");


    if ( (countId.value.trim().length == 0 || invengrp.value.trim().length == 0 ) )
    {
		alert("Please Choose a Valid Open Count and Valid Inventory Group.");
    }
	 else*/
    {
	    loc = "/tcmIS/hub/additemtocount?countId=";
       //loc = loc + countId.value;
       var invengrp  =  document.getElementById("invengrp");
       loc = loc + "&invengrp="+invengrp.value;
       loc = loc + "&Action=new";
       var HubName  =  document.getElementById("HubName");
		 loc = loc + "&HubName="+HubName.value;
       openWinGeneric(loc,"AddItem2hubcount","700","400","yes")
    }
}

function ShowSearch(name,entered)
{
var invengrp  =  document.getElementById("invengrp");
var itemId  =  document.getElementById("itemId");

//alert(""+itemId.value+"       "+invengrp.value+"");
if (name.toString() == "okupdate")
{
	if ( itemId.value.trim().length == 0 || (invengrp.value == "All")  )
	{
		alert("Please select a Item and Inventory Group");
	}
	else
	{
	 with (entered)
	 {
  		 loc = "/tcmIS/hub/additemtocount?&Action=" + name.toString() + "&SearchString=";
 		 loc = loc + window.document.additem2count.SearchString.value;
  		 loc = loc + window.document.additem2count.itemId.value;
       loc = loc + "&itemId="+itemId.value;
       loc = loc + "&invengrp="+invengrp.value;
       var HubName  =  document.getElementById("HubName");
		 loc = loc + "&HubName="+HubName.value;
 	}
 	 window.location.replace(loc);
	}
}
else
{
	 with (entered)
	 {
  		 loc = "/tcmIS/hub/additemtocount?&Action=" + name.toString() + "&SearchString=";
  		 loc = loc + window.document.additem2count.SearchString.value;
  		 loc = loc + window.document.additem2count.itemId.value;

       loc = loc + "&itemId="+itemId.value;
       loc = loc + "&invengrp="+invengrp.value;
       var HubName  =  document.getElementById("HubName");
		 loc = loc + "&HubName="+HubName.value;
 	}
 	window.location.replace(loc);
}
}

function hubchanged(object)
{
	 try
    {
        var target1 = document.all.item("COUNTDETAILS");
        target1.style["display"] = "none";

		  processButton = document.getElementById("processButton");
	     processButton.style.display = "none";

		  createXlsButton = document.getElementById("createXlsButton");
	     createXlsButton.style.display = "none";

        additemtocount = document.getElementById("additemtocount");
	     additemtocount.style.display = "none";

        closecount = document.getElementById("closecount");
	     closecount.style.display = "none";

		  StartNewCount = document.getElementById("StartNewCount");
	     StartNewCount.style.display = "none";
    }
    catch (ex)
    {
     //alert("No COUNTDETAILS Div");
    }

	 try
    {
		  StartNewCount = document.getElementById("StartNewCount");
	     StartNewCount.style.display = "none";
    }
    catch (ex)
    {
     //alert("No COUNTDETAILS Div");
    }

	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.receiving.invengrp.length; i > 0;i--)
   {
      document.receiving.invengrp.options[i] = null;
   }
  	showinvlinks(artist);
   window.document.receiving.invengrp.selectedIndex = 0;
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
    document.receiving.invengrp.options[href] = optionName;
}

function createxls(entered)
{
    loc = "/tcmIS/Hub/itemcount?session=Active&UserAction=generatexls&receiptid=";
    openWinGenericExcel(loc,"Generatexlscount","600","600","yes");
}

//Use this funciton to open a URL which results in a Excel file. This version gives
//the file toolbar to the user so that they can save the file if it
//opnes within the browser (for IE). Example usage below
//openWinGenericExcel('excelfileurl','show_excel_report_file','610','600','yes');
function openWinGenericExcel(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function donothing(entered)
{
    return false;
}

function createpdf(entered)
{
    loc = "/tcmIS/Hub/itemcount?session=Active&UserAction=generatepdf&receiptid=";
    openWinGeneric(loc,"Generate_PDF","600","600","yes");
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}


function RefreshPage(entered)
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

	var HubName  =  document.getElementById("HubName");
	var catagory  =  document.getElementById("Category");
	var invengrp  =  document.getElementById("invengrp").value;

    invengrp = invengrp.replace(/#/gi, "%23");
    invengrp = invengrp.replace(/&/gi, "%26");

    loc = "/tcmIS/Hub/itemcount?session=Active&UserAction=New&HubName=";
    loc=loc + HubName.value;
    loc=loc +"&Category="+ catagory.value;
    loc=loc +"&invengrp="+ invengrp;

    //alert(loc);
    document.location=loc;
}

/*var defaultEmptyOK = false
// decimal point character differs by language and culture
var decimalPointDelimiter = "."

function isEmpty(s)
{
    return ((s == null) || (s.length == 0))
}
function isDigit (c)
{
    return ((c >= "0") && (c <= "9"))
}
function isInteger (s)
{
    var i;
    if (isEmpty(s))
        if (isInteger.arguments.length == 1) return defaultEmptyOK;
        else return (isInteger.arguments[1] == true);
    for (i = 0; i < s.length; i++)
    {
        // Check that current character is number.
        var c = s.charAt(i);
        if (!isDigit(c)) return false;
    }
    return true;
}

// isFloat (STRING s [, BOOLEAN emptyOK])
//
// True if string s is an unsigned floating point (real) number.
//
// Also returns true for unsigned integers. If you wish
// to distinguish between integers and floating point numbers,
// first call isInteger, then call isFloat.
//
// Does not accept exponential notation.
//
// For explanation of optional argument emptyOK,
// see comments of function isInteger.

function isFloat (s)

{   var i;
    var seenDecimalPoint = false;

    if (isEmpty(s))
       if (isFloat.arguments.length == 1) return defaultEmptyOK;
       else return (isFloat.arguments[1] == true);

    if (s == decimalPointDelimiter) return false;

    // Search through string's characters one by one
    // until we find a non-numeric character.
    // When we do, return false; if we don't, return true.

    for (i = 0; i < s.length; i++)
    {
        // Check that current character is number.
        var c = s.charAt(i);

        if ((c == decimalPointDelimiter) && !seenDecimalPoint) seenDecimalPoint = true;
        else if (!isDigit(c)) return false;
    }

    // All characters are numbers.
    return true;
}*/

function update(entered)
{
    //if ( eval(updatecount.toString()) < 1 )
    //{
    //    alert("Please enter data and select Ok before Update");
    //    return false;
    // }
    if (checkCount == 0)
    {
    if (checkLastDateReceived(true))
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
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "UpdPage";
    return true;
    }
    else
    {
		return false;
    }
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
     window.document.receiving.UserAction.value = "UPDATE";
     window.document.receiving.SubUserAction.value = "UpdPage";
     return true;
    }
}


function closecount(entered)
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
    window.document.receiving.UserAction.value = "Close";
    window.document.receiving.SubUserAction.value = "NA";
    return true;
}

function searchcount(entered)
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
    window.document.receiving.UserAction.value = "Search";
    window.document.receiving.SubUserAction.value = "NA";
    return true;
}

function StartNew(entered)
{
   var invengrp  =  document.getElementById("invengrp");
	//if (invengrp.value.length > 0)
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
    window.document.receiving.UserAction.value = "NEW";
    window.document.receiving.SubUserAction.value = "STARTNEW";
    return true;
	}
   /*else
   {
		alert("Please select a Inventory Group to start a count.");
		return false;
   }*/
}