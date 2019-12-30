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

function linehubchanged(object,rownum)
{
	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;
	var hubdrpbox  =  document.getElementById("lineinvengrp"+rownum+"");

	for (var i = hubdrpbox.length; i > 0;i--)
   {
        hubdrpbox.options[i] = null;
   }
	showlineinvlinks(artist,rownum);
	hubdrpbox.selectedIndex = 0;
}

function showlineinvlinks(selectedIndex,rownum)
{
    var nickNameValue = new Array();
    nickNameValue = altinvid[selectedIndex];

    for (var i=0; i < nickNameValue.length; i++)
    {
        setlineCab((i+1),nickNameValue[i],nickNameValue[i],rownum)
    }

	 setlineCab(0,"Please Select","",rownum);
}

function setlineCab(href,text,id,rownum)
{
    var optionName = new Option(text, id, false, false)
    var invgrpdrpbox  =  document.getElementById("lineinvengrp"+rownum+"");
    invgrpdrpbox.options[href] = optionName;

    //document.invtransreq.invengrp""+rownum+"".options[href] = optionName;
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
    }
    catch (ex)
    {
     //alert("No COUNTDETAILS Div");
    }

	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.invtransreq.invengrp.length; i > 0;i--)
   {
      document.invtransreq.invengrp.options[i] = null;
   }
	showinvlinks(artist);
	window.document.invtransreq.invengrp.selectedIndex = 0;
}

function showinvlinks(selectedIndex)
{
    var nickNameValue = new Array();
    nickNameValue = altinvid[selectedIndex];

    for (var i=0; i < nickNameValue.length; i++)
    {
        setCab((i+1),nickNameValue[i],nickNameValue[i])
    }

	 setCab(0,"Please Select","");
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.invtransreq.invengrp.options[href] = optionName;
}

function createxls(entered)
{
    loc = "/tcmIS/hub/transferrequest?session=Active&UserAction=generatexls&receiptid=";
    openWinGeneric(loc,"Generatexlscount","600","600","yes");
}


function donothing(entered)
{
    return false;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

var defaultEmptyOK = false
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

function checkValue(rownum)
{
   var availableqty  =  document.getElementById("avlqty"+rownum+"");
   var neededqty  =  document.getElementById("qty_sent"+rownum+"");

    if ( !(isInteger(neededqty.value)) )
    {
        alert ( "Invalid Number\n Please Enter a Valid Number.");
        neededqty.value = "";
    }
    else if (neededqty.value*1 > availableqty*1)
    {
		  alert ( "Cannot transfer more than available quantity.\n Please Enter a Valid Number.");
        neededqty.value = "";
    }
}

function update(entered)
{
    //if ( eval(updatecount.toString()) < 1 )
    //{
    //    alert("Please enter data and select Ok before Update");
    //    return false;
    // }
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
    window.document.invtransreq.UserAction.value = "UPDATE";
    window.document.invtransreq.SubUserAction.value = "UpdPage";
    return true;
}


function search(entered)
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
    window.document.invtransreq.UserAction.value = "Search";
    window.document.invtransreq.SubUserAction.value = "NA";
    return true;
}

function StartNew(entered)
{
	var invengrp  =  document.getElementById("invengrp");
	if (invengrp.value.length > 0)
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
    window.document.invtransreq.UserAction.value = "NEW";
    window.document.invtransreq.SubUserAction.value = "STARTNEW";
    return true;
   }
   else
   {
		alert("Please select a Inventory Group to start a count.");
		return false;
   }
}