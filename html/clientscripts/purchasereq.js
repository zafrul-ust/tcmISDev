var submitcount=0;
var shipupdcount=0;

var noLinesChecked = 0;
var selectedItemId="";
var selectedInventoryGroup="";
var selectedCustomerPo="";
var selectedCompanyId="";

function SubmitOnlyOnce(userAction)
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

function submitUpdate(userActionValue)
{
    var userAction = document.getElementById("UserAction");
    userAction.value = "Update"+userActionValue;
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}


function editItemNotes(itemId)
{
    loc = "/tcmIS/supply/edititemnotes.do?itemId=";

    if (isInteger(itemId))
    {
	    loc = loc + itemId;
	    openWinGeneric(loc,"editItemNotes","800","600","yes","50","50")
	 }
	 else
	 {

	 }
}


function checkbuyer(buyeridnum)
{
	var buyerid = document.getElementById("buyerid"+buyeridnum+"");

	if (buyerid.value == "86405" || buyerid.value == "153" || buyerid.value == "12572")
	{
		alert("You cannot assign this Buy to this buyer.\n\nPlease pick a different buyer.");
		buyerid.value = "";
	}
}

function checkdbuystat (linenumber)
{
 var supplypath = document.getElementById("supplypath"+linenumber+"");
 var statusofpr = document.getElementById("statusofpr"+linenumber+"");

 if (supplypath.value == "DBuy")
 {
   if (statusofpr.value.search(/DBuy/) == -1)
   {
		alert("You are changing a DBuy Order to a Non DBuy status.\n\nPlease make sure you want to do this.");
   }
 }
}

function showopenbpos(prNumber)
{
    loc = "/tcmIS/supply/showblankets?prnumber=";

    if (isInteger(prNumber))
    {
	    loc = loc + prNumber;
	    loc = loc + "&Action=showblankets";
	    openWinGeneric(loc,"OpenBlankets","600","200","yes","50","50")
	 }
	 else
	 {

	 }
}

function uncheckopenbuyorders()
{
var showopenbuyorders = document.getElementById("showopenbuyorders");
showopenbuyorders.checked = false;
}

function checkopnbuyorders()
{
var showopenbuyorders = document.getElementById("showopenbuyorders");
if (showopenbuyorders.checked)
{
var showonlyconfpo = document.getElementById("showonlyconfpo");
showonlyconfpo.checked = false;

var showonlynopo = document.getElementById("showonlynopo");
showonlynopo.checked = false;

var showall = document.getElementById("showall");
showall.checked = false;

var status = document.getElementById("status");
status.value = 'All Except Closed';
status.selected = true;
}
}

function showbuypagelegend()
{
        openWinGeneric("/tcmIS/help/buypagelegend.html","buypagelegend","290","400","no","50","50")
}

function getsuggestedSupplier(partnum,requestid,catalogid)
{
    loc = "/tcmIS/purchase/posupplier?Action=suggestedsupp";
    loc = loc + "&catpartno=" + partnum + "&requestid=" +requestid+ "&catalogid=" +catalogid;
    openWinGeneric(loc,"suggestedsupplier","450","200","yes","200","200")
}

function showitemtcmBuys(itemId,shiptoloc)
{
    loc = "/tcmIS/purchase/showtcmbuys?Action=viewforpage&itemID=";
    loc = loc + itemId;
    loc = loc + "&shiptoLoc=" + shiptoloc;
    openWinGeneric(loc,"tcmbuyhistoryfrombuypage","800","450","yes","80","80")
}

function showBuyOrderTransfer(prNumber,shipTo,inventory,company,transferRoute,item,type,qty)
{
    loc = "/tcmIS/purchase/showtcmbuys?Action=buildBuyOrderTransferPage&prNumber=";
    loc = loc + prNumber;
    loc = loc + "&shipTo=" + shipTo;
	 loc = loc + "&inventory=" + inventory;
	 loc = loc + "&company=" + company;
	 loc = loc + "&transferRoute=" + transferRoute;
	 loc = loc + "&item=" + item;
	 loc = loc + "&type=" + type;
	 loc = loc + "&qty=" + qty;

    openWinGeneric(loc,"showBuyOrderTransferPage","650","300","yes","80","80")
}

function auditRequestTransfer()
{

	//make sure user pick an inventory group if he/she enters a consolidating qty
	var consolidatingQty = document.getElementById("consolidatingQty");
	var consolidatingInventory = document.getElementById("consolidatingInventory");
	if (consolidatingQty.value.length == 0) {
		cancel();
	}else {
		//user has to pick a consolidating inventory group
		if (consolidatingInventory.value == "None") {
			alert("Please select a consolidating inventory group.");
			return false;
		}
		//original inventory qty and consolidating qty has to equal to buy qty
		var buyQty = document.getElementById("buyQty");
		var originalQty = document.getElementById("originalQty");

      var origQtyNum = 0;
      var consQtyNum = 0;
      var buyyQtyNum = 0;

		try {
      	if (! isNaN(parseInt(originalQty.value))) {
            origQtyNum = parseInt(originalQty.value);
         } else {
            origQtyNum = 0;
         }
      } catch (e) {
      	origQtyNum = 0;
      }
		try {
         if (! isNaN(parseInt(consolidatingQty.value))) {
        	   consQtyNum = parseInt(consolidatingQty.value);
         } else {
            consQtyNum = 0;
         }
      } catch (e) {
      	consQtyNum = 0;
      }
		try {
         if (! isNaN(parseInt(buyQty.value))) {
      	   buyyQtyNum = parseInt(buyQty.value);
         } else {
            buyyQtyNum = 0;
         }
     } catch (e) {
      	buyyQtyNum= 0;
     }

		if (buyyQtyNum != origQtyNum + consQtyNum) {
			alert("The sum of Original Inventory Group qty and Consolidating Inventory Group qty must equal to Buy Quantity.");
			return false;
		}
	}
	return true;
}


function showschedulde(mrnumber,lineitem)
{
    loc = "/tcmIS/purchase/showtcmbuys?Action=showschedulde&itemID=";
    loc = loc + mrnumber;
    loc = loc + "&lineID="+lineitem;

    openWinGeneric(loc,"tcmbuyhistory","400","460","yes","80","80")
}

//07-23-02
function Unreceive()
{
    opener.document.purchasereq.UserAction.value = "RefreshSearch";
    opener.document.purchasereq.SubUserAction.value = "DuplLine" ;

    try
    {
        var target = opener.document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = opener.document.all.item("MAINPAGE");
        target1.style["display"] = "none";
    }
    catch (ex)
    {
    }
    opener.document.purchasereq.submit();
    window.close();

}

function regenerate(Hub)
{
    var testbin;
    eval( testbin =  "window.document.purchasereq.HubName");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );
    if (curval == "All")
    {
        alert("Please Choose a Hub");
        return false;
    }

    loc = "/tcmIS/Hub/reprintPicklist?";
    loc = loc + "&HubNo=" + curval;
    openWinGeneric(loc,"Show_Picklists","300","150","no");
}


function reshow(object)
{
    var artist;
    artist = object.options[object.selectedIndex].text;

    var indexselectec = object.options[object.selectedIndex]
        var indexofxompany = object.options[object.selectedIndex].value;
    for (var i = document.purchasereq.FacName.length;i > 0;i--)
    {
        document.purchasereq.FacName.options[i] = null;
    }
    reloading = true;
    showlinks(indexofxompany);
    window.document.purchasereq.FacName.selectedIndex = 0;
}
function showlinks(selectedIndex)
{
    var indextoshow = hubnumbers[selectedIndex];
    var companytoshow = hubnames[indextoshow];
    var nickNameValue = new Array();
    var nickNameValueid = new Array();
    nickNameValue = altName[companytoshow];
    nickNameValueid = altNameid[companytoshow];
    for (var i=0; i < nickNameValue.length; i++)
    {
        opt(i,nickNameValue[i],nickNameValueid[i])
    }
}
function opt(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.purchasereq.FacName.options[href] = optionName;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function search(entered)
{
 var result = true;
 try {
 var HubName = document.getElementById("HubName"); //All
 var buyer = document.getElementById("buyer");
 var companyID = document.getElementById("companyID");
 var category = document.getElementById("category");
 var FacName = document.getElementById("FacName"); //blank
 var searchthis = document.getElementById("searchthis");
 var status = document.getElementById("status"); //<>New
 var headsupplypath = document.getElementById("headsupplypath");
 var showall = document.getElementById("showall");
 var showonlynopo = document.getElementById("showonlynopo");
 var showonlyconfpo = document.getElementById("showonlyconfpo");
 var showopenbuyorders = document.getElementById("showopenbuyorders");

if (!(HubName.value == "2150" || HubName.value == "2205" || HubName.value == "2151" || HubName.value == "2154" ))
{
 if (!showall.checked && !showonlynopo.checked && !showonlyconfpo.checked && !showopenbuyorders.checked)
 {
   if (searchthis.value.trim().length == 0)
   {
    if ((buyer.value == "All" || buyer.value*1 > 0) && (FacName.value.trim().length == 0 || FacName.value == "All")
        && (status.value != "New"))
    {
      alert("Please pick at least one specific value for  \nBuyer or\nFacility.\nOr search for status New and No Buyer Assigned.");
      result = false;
    }
    else if (buyer.value == "All" && (FacName.value.trim().length == 0 || FacName.value == "All")
        && (status.value != "New"))
    {
      alert("Please pick at least one specific value for  \nBuyer or\nFacility.\nOr search for status New and No Buyer Assigned.");
      result = false;
    }
   }
 }
 else if (!showopenbuyorders.checked )
 {
   if (searchthis.value.trim().length == 0)
   {
     if ( showall.checked && showonlynopo.checked && companyID.value == "All" && !showonlyconfpo.checked )
     {
      if ( buyer.value == "All" && (FacName.value.trim().length == 0 || FacName.value == "All")
        && (status.value != "New"))
      {
      alert("Please pick at least one specific value for  \nBuyer or\nFacility or\nCompany.\nOr search for status New.");
      result = false;
      }
      else if (buyer.value == "All" && (FacName.value.trim().length == 0 || FacName.value == "All")
        && (status.value != "New"))
      {
      alert("Please pick at least one specific value for  \nBuyer or\nFacility\nCompany.\nOr search for status New.");
      result = false;
      }
     }
     else if (showonlyconfpo.checked )
     {
      if ( buyer.value == "All" || buyer.value == "No Buyers")
      {
      alert("If you need this search, contact the tech center.");
      result = false;
      }
     }
   }
 }
}
}
catch (ex)
{
}

	if (result)
	{
    window.document.purchasereq.UserAction.value = "Search";
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
	 return false;
   }
}

function createpo(entered)
{
    window.document.purchasereq.UserAction.value = "createpo";
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

function confirm(entered)
{
    window.document.purchasereq.UserAction.value = "UpdateConfirm";
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


function editPRsearch(entered,userAction)
{
	var numofHubs  =  document.getElementById("numofHubs");
	if (numofHubs.value != 1)
   {
       var HubName  =  document.getElementById("HubName");
       HubName.value  =  "None";
   }

    window.document.purchasereq.UserAction.value = userAction;
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

function checkEditPrsValues(name, entered)
{
var lineNum = name.toString();
//alert(noLinesChecked);
var currentcheckBox = document.getElementById(""+lineNum+"");

var lineitemID  =  document.getElementById("itemid"+lineNum+"");
var invengrp  =  document.getElementById("invengrp"+lineNum+"");
var selectedCustomerPoObj  =  document.getElementById("customerPo"+lineNum+"");
var companyId  =  document.getElementById("companyid"+lineNum*1+"");

if ( noLinesChecked == 0)
{
	selectedItemId= lineitemID.value;
	selectedInventoryGroup=invengrp.value;
	selectedCustomerPo=selectedCustomerPoObj.value;
	selectedCompanyId=companyId.value;
}

if (currentcheckBox.checked)
{
 noLinesChecked ++;

var openbalnket  =  document.getElementById("openbalnket"+lineNum+"");
if (openbalnket.value.trim() == "Y" && currentcheckBox.checked)
{
	alert("FYI: There is a blanket order available for this Item");
}

if ( lineitemID.value.trim().length > 0 && (lineitemID.value != selectedItemId) )
{
	alert("You Cannot Choose a Different Item");
	currentcheckBox.checked = false;
	noLinesChecked --;
	return false;
}

if ( invengrp.value != selectedInventoryGroup )
{
	alert("You Cannot Choose a Different Inventory Group");
	currentcheckBox.checked = false;
	noLinesChecked --;
	return false;
}

if ( (selectedCompanyId == "SWA") || (companyId.value.trim() == "SWA") )
{
	if ( selectedCustomerPo != selectedCustomerPoObj.value )
	{
		alert("You Cannot Choose a Different Customer PO for Drop Ship");
		currentcheckBox.checked = false;
		noLinesChecked --;
		return false;
	}
}
}
else
{
 noLinesChecked --;
 /*if ( noLinesChecked == 0)
 {
	selectedItemId="";
	selectedInventoryGroup="";
	selectedCustomerPo="";
	selectedCompanyId="";
 }*/
 return false;
}
/*
if ( noLinesChecked == 0)
{
	selectedItemId="";
	selectedInventoryGroup="";
	selectedCustomerPo="";
	selectedCompanyId="";
}*/
}

function cancel()
{
    window.close();
}

function resetApplicationTimer()
{
    try
    {
     parent.resetTimer();
    }
    catch(exap){
    }
}

/*
function checkEditPrsValues(name, entered)
{
var lineNum = name.toString();
alert(noLinesChecked);
var currentcheckBox = document.getElementById(""+lineNum+"");

var totalRecords = document.getElementById("totalRecords");

var lineshipTo  =  document.getElementById("facility"+lineNum+"");
var lineHubName  =  document.getElementById("branchplant"+lineNum+"");
var lineitemID  =  document.getElementById("itemid"+lineNum+"");

var itemfromLine  =  document.getElementById("itemfromLine");
var shipTofromLine  =  document.getElementById("shipTofromLine");
var hubfromLine  =  document.getElementById("hubfromLine");
var invengrp  =  document.getElementById("invengrp"+lineNum+"");
var selectedCustomerPoObj  =  document.getElementById("customerPo"+lineNum+"");
var companyId  =  document.getElementById("companyid"+lineNum*1+"");

if (itemfromLine.value.trim().length == 0)
{
itemfromLine.value = lineitemID.value;
}
if (shipTofromLine.value.trim().length == 0)
{
shipTofromLine.value = lineshipTo.value;
}
if (hubfromLine.value.trim().length == 0)
{
hubfromLine.value = lineHubName.value;
}

 if ( noLinesChecked == 0)
 {
	selectedItemId= lineitemID.value;
	selectedInventoryGroup=invengrp.value;
	selectedCustomerPo=selectedCustomerPoObj.value;
	selectedShipTo=lineshipTo.value;
	selectedCompanyId=companyId.value;
 }

if (currentcheckBox.checked)
{
noLinesChecked ++;
}
else
{
 noLinesChecked --;
 if ( noLinesChecked == 0)
 {
	itemfromLine.value = "";
	shipTofromLine.value = "";
	hubfromLine.value = "";

	selectedItemId="";
	selectedInventoryGroup="";
	selectedCustomerPo="";
	selectedShipTo="";
	selectedCompanyId="";
 }
 return false;
}

var openbalnket  =  document.getElementById("openbalnket"+lineNum+"");
if (openbalnket.value.trim() == "Y" && currentcheckBox.checked)
{
	alert("FYI: There is a blanket order available for this Item");
}

	if ( itemfromLine.value.trim().length > 0 && (lineitemID.value != itemfromLine.value) )
	{
	  alert("You Cannot Choose a Different Item");
	  currentcheckBox.checked = false;
	  noLinesChecked --;
	  return false;
	}

  if ( invengrp.value != selectedInventoryGroup )
	{
	  alert("You Cannot Choose a Different Inventory Group");
	  currentcheckBox.checked = false;
	  noLinesChecked --;
	  return false;
	}

	if ( (selectedCompanyId == "SWA") || (companyId.value.trim() == "SWA") )
	{
	  if ( selectedCustomerPo != selectedCustomerPoObj.value )
		{
		  alert("You Cannot Choose a Different Customer PO for Drop Ship");
		  currentcheckBox.checked = false;
		  noLinesChecked --;
		  return false;
		}
	}

	/*if ( shipTofromLine.value.trim().length > 0 && (lineshipTo.value != shipTofromLine.value) )
	{
	  alert("You Cannot Choose a Different Ship TO");
	  currentcheckBox.checked = false;
	  return false;
	}
	//alert(HubName.value);
	if ( hubfromLine.value.trim().length > 0 && (hubfromLine.value != "None") && (lineHubName.value != hubfromLine.value) )
	{
	  alert("You Cannot Choose a Different Hub");
	  currentcheckBox.checked = false;
	  return false;
	}*/

/*
	   var allClear = 0;
	   var finalMsgt;
	   finalMsgt = "You cannot Select PRs with Different: \n\n";

		if (noLinesChecked > 1)
		{
	   for(j=0;j<(totalRecords.value*1);j++)
	   {
	      var lineitemID1  =  "";
	      var lineHubName1 =  "";
	      var lineshipTo1  =  "";
	      var lineinvengrp  =  "";
	      var currentcheckBox1;
        var currentCustomerPo  =  "";

	      currentcheckBox1 = document.getElementById(""+(j+1)+"");

	      if ( (j+1)!=(lineNum*1) && currentcheckBox1.checked)
	      {
         lineitemID1  =  document.getElementById("itemid"+(j+1)+"");
	       lineHubName1 =  document.getElementById("branchplant"+(j+1)+"");
	       lineshipTo1  =  document.getElementById("facility"+(j+1)+"");
         companyid = document.getElementById("companyid"+(j+1)+"");
	       lineinvengrp  =  document.getElementById("invengrp"+(j+1)+"");
	       currentCustomerPo =  document.getElementById("customerPo"+(j+1)+"");
				 //noLinesChecked ++;

         if (lineitemID.value != lineitemID1.value)
         {
            if (allClear == 0)
            {
	            finalMsgt = finalMsgt + "  Item\n";
	          }
	          allClear += 1;
	       }

	      if (lineinvengrp.value != invengrp.value)
			  {
              if (allClear == 0)
              {
              finalMsgt = finalMsgt + "  Inventory Group\n";
              }
              allClear += 1;
         }

        var companyid2  =  document.getElementById("companyid"+lineNum*1+"");
		    if (lineHubName.value == "2202" || lineHubName1.value == "2202")
		    {
				  //alert(lineHubName.value);
	        if (currentCustomerPo.value == selectedCustomerPoObj.value)
				  {

				  }
				  else if ( (companyid2.value.trim() == "SWA") || (companyid.value.trim() == "SWA") )
	        {
	         finalMsgt = finalMsgt + "  Customer PO for Drop Ship\n";
	         allClear += 1;
	        }
		     }
	     }
	   }
		}

	    if (allClear < 1)
	    {

	    }
	    else
	    {
	        alert(finalMsgt);
	        currentcheckBox.checked = false;
          noLinesChecked --;
	    }*/
/*
		 if ( noLinesChecked == 0)
		 {
	     itemfromLine.value = "";
	     shipTofromLine.value = "";
	     hubfromLine.value = "";

   	 	 selectedItemId="";
 	 	 	 selectedInventoryGroup="";
 	 	 	 selectedCustomerPo="";
 	 	 	 selectedShipTo="";
 	 	 	 selectedCompanyId="";
		 }
}*/