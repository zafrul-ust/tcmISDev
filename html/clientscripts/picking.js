var submitcount=0;
var shipupdcount=0;
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

var selectedrow=null;
var selectedRowId=null;

function selectRow(rowId)
{
   var selectedRow = document.getElementById("rowId"+rowId+"");

   var selectedRowClass = document.getElementById("colorClass"+rowId+"");

   selectedRow.className = "selected"+selectedRowClass.value+"";

   if (selectedRowId != null && rowId != selectedRowId)
   {
     var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
     var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
     previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
   }
   selectedRowId =rowId;
   /*TODO change to use materialRequestOrigin*/
   var inventoryGroup =  document.getElementById("inventoryGroup"+selectedRowId+"").value;
   if (inventoryGroup == 'Miami Distribution')
     {toggleContextMenu('showAddCharges');}
}  //end of selectRow

function getcurpath (){
	return encodeURIComponent('Ship Confirm');
}
function addLineCharges() {
    var companyId = document.getElementById("companyId"+selectedRowId+"").value;
    var prNumber = document.getElementById("prNumber"+selectedRowId+"").value;
    var lineItem = document.getElementById("lineItem"+selectedRowId+"").value;
    var currencyId = document.getElementById("currencyId"+selectedRowId+"").value;
    var opsEntityId = document.getElementById("opsEntityId"+selectedRowId+"").value;

      //parent.showTransitWin('<fmt:message key="label.linecharges"/>');
	  loc = "/tcmIS/distribution/addchargeline.do?orderType=MR"+"&status=&prNumber="+prNumber+
	  						"&companyId="+companyId+
	  						"&lineItem="+lineItem+
	  					    "&curpath="+getcurpath()+
                            "&opsEntityId="+opsEntityId+
                            "&currencyId="+currencyId;//+gg("currencyId");
	  var winId= 'addHeaderCharge'+prNumber;
      openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
      //parent.children[parent.children.length] = openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
}

function addHeaderCharges() {
    var companyId = document.getElementById("companyId"+selectedRowId+"").value;
    var prNumber = document.getElementById("prNumber"+selectedRowId+"").value;
    var lineItem = document.getElementById("lineItem"+selectedRowId+"").value;
    var currencyId = document.getElementById("currencyId"+selectedRowId+"").value;
    var opsEntityId = document.getElementById("opsEntityId"+selectedRowId+"").value;
      //parent.showTransitWin('<fmt:message key="label.headercharges"/>');
	  loc = "/tcmIS/distribution/addchargeheader.do?orderType=MR"+"&status=&prNumber="+prNumber+
	  						"&companyId="+companyId+
	  					    "&curpath="+getcurpath()+
                            "&opsEntityId="+opsEntityId+
                            "&currencyId="+currencyId;//+gg("currencyId");
	  var winId= 'addHeaderCharge'+prNumber;
      openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
      //parent.children[parent.children.length] = openWinGeneric(loc,winId.replace('.','a'),"820","400","yes","50","50","20","20","no");
}

function showProjectDocuments(receiptId,inventoryGroup)
{
 var loc = "/tcmIS/hub/receiptdocuments.do?receiptId="+receiptId+"&showDocuments=Yes&inventoryGroup="+inventoryGroup+"";
 openWinGeneric(loc,"showAllProjectDocuments","450","300","no","80","80");
}

function showLineNotes(rowid)
{
  var notesVisible = document.getElementById("notesVisible"+rowid+"");
  if (notesVisible.value == "No")
  {
  var lineNotesDiv = document.getElementById("lineNotes"+rowid+"");
  lineNotesDiv.style.display = "block";
  lineNoteslink

  var lineNoteslink = document.getElementById("lineNoteslink"+rowid+"");
  lineNoteslink.style.display = "none";

  notesVisible.value = "Yes";
  }
  else
  {
  	var lineNotesDiv = document.getElementById("lineNotes"+rowid+"");
	lineNotesDiv.style.display = "none";
 	lineNoteslink

	var lineNoteslink = document.getElementById("lineNoteslink"+rowid+"");
   lineNoteslink.style.display = "block";

   notesVisible.value = "No";
  }
}

function showpickingpagelegend()
{
  openWinGeneric("/tcmIS/help/pickingpagelegend.html","pickingpagelegend","290","300","no","50","50")
}

function dolargelabelPopup()
{
   var testurl4 = "/tcmIS/hub/printlargelabel?session=Active";
   openWinGeneric(testurl4,"Generate_large_labels","600","600","yes")
}

function generatelargelabels()
{
    window.document.picking.UserAction.value = "printlargelabels";
    window.document.picking.SubUserAction.value = "printlargelabels";

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


function ChecklabelQtyValue()
{

}

function submitmainpage()
{
    opener.document.picking.UserAction.value = "RefreshSearch";
    opener.document.picking.SubUserAction.value = "DuplLine" ;

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
    opener.document.picking.submit();
}

function setprinterRes(printerRes)
{
  window.document.picking.printerRes.value = printerRes;
}

function enterdotinfo(item_id)
{
   loc = "/tcmIS/hub/shippinginfo.do?uAction=search&itemId=" + item_id;
   openWinGeneric(loc,"enterdotinfo","750","600","yes")
}


function deliveryticket(entered)
{
    window.document.picking.UserAction.value = "deliveryticket";
    window.document.picking.SubUserAction.value = "deliveryticket";

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


function dodeliveryticket()
{
    var testurl3 = "/tcmIS/Hub/PickingQC?session=Active&generate_bols=yes&UserAction=generatedelvtkt";
    var paperSize  =  window.document.picking.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    openWinGeneric(testurl3,"Generate_Deliveryticket","640","600","yes")
}


function checkall(checkboxname,sartingvalue)
{
var checkall = document.getElementById("chkall");
var totallines = document.getElementById("totallines").value;
totallines = totallines*1;
var valueq;
var result ;

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
	var linecheck = document.getElementById(""+checkboxname+""+(p+1)+"");
	linecheck.checked =result;
	linecheck.value = valueq;
	}
	catch (ex3)
	{

	}
}

}

//07-23-02
function Unreceive()
{
    opener.document.picking.UserAction.value = "RefreshSearch";
    opener.document.picking.SubUserAction.value = "DuplLine" ;

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
    opener.document.picking.submit();
    window.close();

}

function reversePick(name,entered,prnumber,lineitem,picklistid)
{
        loc = "/tcmIS/Hub/PickingQC?session=Active&UserAction=Unreceive&SubUserAction=Unreceive&remove_receipt_id=";
        loc = loc + prnumber;
	  loc = loc + "&removeline=" + lineitem;
	  loc = loc + "&removepickid=" + picklistid;
        openWinGeneric(loc,"Reverse_Picking","300","150","no");
}

function regenerate(Hub)
{
    var testbin;
    eval( testbin =  "window.document.picking.HubName");
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
    openWinGeneric(loc,"Show_Picklists","300","150","yes");
}


function cancel()
{
    window.close();
}

function reshow(object)
{
    artist = object.options[object.selectedIndex].text;
    var indexselectec = object.options[object.selectedIndex]
        var indexofxompany = object.options[object.selectedIndex].value;
    for (var i = document.picking.FacName.length;i > 0;i--)
    {
        document.picking.FacName.options[i] = null;
    }
    reloading = true;
    showlinks(indexofxompany);
    window.document.picking.FacName.selectedIndex = 0;
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
    document.picking.FacName.options[href] = optionName;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function doPrintbol()
{
    var testurl3 = "/tcmIS/Hub/PickingQC?session=Active&generate_bols=yes&UserAction=generatebols";
    var paperSize  =  window.document.picking.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}

function doPrintbox()
{
	 var testurl3 = "/tcmIS/hub/reprintboxlbls?";

    HubNoforlabel = document.getElementById("HubName");
	 testurl3 = testurl3 + "HubNoforlabel=" + HubNoforlabel.value ;

    openWinGeneric(testurl3,"Generate_Boxlabels","640","600","yes")
}

function doPrintrelabel()
{
    var testurl3 = "/tcmIS/Hub/reprintlabels?session=Active&generate_bols=yes";
    var materilReqOriginCount = document.getElementById("materilReqOriginCount");
    var paperSize  =  window.document.picking.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    testurl3 = testurl3 + "&materilReqOriginCount=" + materilReqOriginCount.value;
    openWinGeneric(testurl3,"Generate_relabels","640","600","yes")
}

function doGenerateExitLabels()
{
    var testurl3 = "/tcmIS/Hub/reprintlabels?session=Active&generate_bols=yes";
    var materilReqOriginCount = document.getElementById("materilReqOriginCount");
    var paperSize  =  window.document.picking.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    testurl3 = testurl3 + "&labelType=exitLabels";
    testurl3 = testurl3 + "&materilReqOriginCount=" + materilReqOriginCount.value;
    openWinGeneric(testurl3,"generate_ExitLabels","640","600","yes")
}

function reprintpicks(entered)
{
    window.document.picking.UserAction.value = "printpicks";
    window.document.picking.SubUserAction.value = "reprintpicks";

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

function printpicks(entered)
{
    window.document.picking.UserAction.value = "printpicks";
    window.document.picking.SubUserAction.value = "updatepicks";

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

function search(entered)
{

    var testbin;
    eval( testbin =  "window.document.picking.HubName");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );
    if (curval == "All")
    {
        alert("Please Choose a Hub");
        return false;

    }

    window.document.picking.UserAction.value = "Search";
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

function pageconfirm(entered)
{
	if(!checkTotalPickQtyArray())
		return false;

    var testbin;
    eval( testbin =  "window.document.picking.HubName");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );
    if (curval == "All")
    {
        alert("Please Choose a Hub");
        return false;

    }

    window.document.picking.UserAction.value = "UpdateConfirm";
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

function isWmsHub() {
    var testbin;
    eval( testbin =  "window.document.picking.HubName");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );
    for ( var i=0; i < altAutomatedPutaway.length; i++) {
        if (altAutomatedPutaway[i].id == curval) {
            if (altAutomatedPutaway[i].automatedPutaway == 'Y')
                setButtonsView(true);
            else
                setButtonsView(false);
            break;
        }
    }
}

function setButtonsView(hideButton) {
    if (hideButton)
        window.document.picking.isWmsHub.value = "Y";
    else
        window.document.picking.isWmsHub.value = "N";
    //print BOL Short
    var tmpButton = window.document.picking.printBolShortButton;
    if (tmpButton) {
        if (hideButton) {
            tmpButton.style["display"] = "none";
            window.document.picking.isWmsHub.value = "Y";
        }else {
            tmpButton.style["display"] = "";
            window.document.picking.isWmsHub.value = "N";
        }
    }
    //print Delivery Label
    tmpButton = window.document.picking.deliveryLabelButton;
    if (tmpButton) {
        if (hideButton)
            tmpButton.style["display"] = "none";
        else
            tmpButton.style["display"] = "";
    }
    //ship confirm
    tmpButton = window.document.picking.shipConfirmButton;
    if (tmpButton) {
        if (hideButton)
            tmpButton.style["display"] = "none";
        else
            tmpButton.style["display"] = "";
    }
    //print BOL Long
    tmpButton = window.document.picking.printBolLongButton;
    if (tmpButton) {
        if (hideButton)
            tmpButton.style["display"] = "none";
        else
            tmpButton.style["display"] = "";
    }
    //re-print labels
    tmpButton = window.document.picking.rePrintLabelButton;
    if (tmpButton) {
        if (hideButton)
            tmpButton.style["display"] = "none";
        else
            tmpButton.style["display"] = "";
    }
    //exit labels
    tmpButton = window.document.picking.exitLabelButton;
    if (tmpButton) {
        if (hideButton)
            tmpButton.style["display"] = "none";
        else
            tmpButton.style["display"] = "";
    }
    //receiving labels
    tmpButton = window.document.picking.largeLabelButton;
    if (tmpButton) {
        if (hideButton)
            tmpButton.style["display"] = "none";
        else
            tmpButton.style["display"] = "";
    }
    //Delivery ticket
    tmpButton = window.document.picking.deliveryTicketButton;
    if (tmpButton) {
        if (hideButton)
            tmpButton.style["display"] = "none";
        else
            tmpButton.style["display"] = "";
    }
}

function getpicklistids(entered)
{

    var testbin;
    eval( testbin =  "window.document.picking.HubName");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );
    if (curval == "All")
    {
        alert("Please Choose a Hub");
        return false;

    }

    window.document.picking.UserAction.value = "New";
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

    window.document.picking.submit();
    return true;
}

function reprintlabels(entered)
{
    window.document.picking.UserAction.value = "reprintlabels";
    window.document.picking.SubUserAction.value = "reprintlabels";

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

function generateExitLabels(entered)
{
    window.document.picking.UserAction.value = "printexitlabels";
    window.document.picking.SubUserAction.value = "printexitlabels";

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

function boxlabels(entered)
{
    window.document.picking.UserAction.value = "printboxlabels";
    window.document.picking.SubUserAction.value = "printboxlabels";

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

function bolshort(entered)
{
    window.document.picking.UserAction.value = "printpicks";
    window.document.picking.SubUserAction.value = "PrintBOL";

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

function bollong(entered)
{
    window.document.picking.UserAction.value = "printpicks";
    window.document.picking.SubUserAction.value = "PrintBOLDetail";

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



// decimal point character differs by language and culture
var decimalPointDelimiter = "."

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
}


function CheckQtyValue(name,entered,delname)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    finalMsgt = "Please enter valid values for: \n\n";
    var nonintegerReceiving = document.getElementById("nonintegerReceiving"+delname+"");

    eval( testqty = "window.document.picking.qty_picked" + delname.toString() )
    var v = (eval(testqty.toString())).value;
    if ( v*1 < 0 )
    {
        finalMsgt = finalMsgt + " Quantity Picked. \n";
        allClear = 1;
        testqty12  =  eval("window.document.picking.qty_picked" + delname.toString() );
        testqty12.value = "";
    }
    else if ( !(isInteger(v)) )
    {
      if (!(nonintegerReceiving.value == "Y" && isFloat(v)) )
	   {
	     finalMsgt = finalMsgt + " Quantity Picked. \n";
        allClear = 1;
        testqty12  =  eval("window.document.picking.qty_picked" + delname.toString() );
        testqty12.value = "";
      }
    }

	 if (allClear == 0)
	 {
	 	var continueValidation = true;
	    eval( inventoryQuantityO = "window.document.picking.inventoryqty" + delname.toString() )
    	var inventoryQuantity = (eval(inventoryQuantityO.toString())).value;
		
		eval( pickqtyO = "window.document.picking.picklistqty" + delname.toString() )
    	var pickqty = (eval(pickqtyO.toString())).value;
    	
		eval( groupNumO = "window.document.picking.hgroupNumber" + delname.toString() )
    	var groupNum = (eval(groupNumO.toString())).value;
		eval("window.document.picking.groupCheck"+groupNum+".value='Y'");
						 
    	var availableQty = inventoryQuantity*1+pickqty*1;
		
    	if ( v > availableQty)
	    {
	       warningmsg = "The quantity available ("+availableQty+") is less than what You entered.\n\nDo you want to continue?\n\n\nClick 'Ok' if you want to continue with the quantity you entered.";
	       if (confirm(warningmsg))
	       {
	       }
	       else
	       {
				testqty12  =  eval("window.document.picking.qty_picked" + delname.toString() );
           		testqty12.value = "";
           		continueValidation = false;
	       }
	    }  
	    
	    if (continueValidation && pickqty != v)
	    {
	    	if (pickqty == "") {
	    		pickqty = "NONE";
	    	}
	       warningmsg = "You entered a different quantity ("+v+") from what is on the Picklist ("+pickqty+") \n\nDo you want to continue?\n\n\nClick 'Ok' if you want to continue with the different quantity.";
	       if (confirm(warningmsg))
	       {
	       }
	       else
	       {
				testqty12  =  eval("window.document.picking.qty_picked" + delname.toString() );
	         	testqty12.value = "";
	       }
	    }
    }
}

function checkpickvalue(name,entered)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    var pickchekbox;
    eval( pickchekbox  =  "window.document.picking." + name.toString() );

    if ( ((eval(pickchekbox.toString())).value )  == no.toString())
    {
            eval((eval(pickchekbox.toString())).value  = yes.toString() );;
            eval( (eval(pickchekbox.toString())).checked = true );;
            result = true;

    }
    else if ( ((eval(pickchekbox.toString())).value )  == yes.toString())
    {
        eval((eval(pickchekbox.toString())).value  = no.toString() );
        eval( (eval(pickchekbox.toString())).checked = false );
        result = true;

    }

    return result;
}

function checkTotalPickQtyArray() {
	
	var totallines = document.getElementById("totallines").value;
	totallines = totallines*1;
	var samepick = false;
	var validate = false;
	var totalpickqty = 0;
	var totalenteredpickqty = 0;
	var numOfLines = 0;
	var lastLine=false;
	var savePforPrompt=0;
	var qtypicktextbox = false;
	var pickqtyvalpresent = false;
	var groupNumberArr = new Array();
	var pickQtyArr = new Array();
	var enteredQtyArr = new Array();
	var receiptNumberArr = new Array();
	var savePforPromptArr = new Array();
	var index = 0;
	var indexTemp = 0;
	for ( var p=1 ; p<=totallines ; p++) {
		//get the group number and find the groupcheck flag
		
		eval( currGroupNumberO = "window.document.picking.hgroupNumber" + p.toString() )
    	var currGroupNumber = (eval(currGroupNumberO.toString())).value;
		
		eval( groupCheckO = "window.document.picking.groupCheck" + currGroupNumber.toString() )
    	var groupCheck = (eval(groupCheckO.toString())).value;
		
		//var currGroupNumber = document.getElementById("hgroupNumber"+p+"").value;
		//var groupCheck = document.getElementById("groupCheck"+currGroupNumber+"").value;
		
		if (groupCheck == 'Y') {			
			if (p<totallines) {
				eval( hgroupNumberO = "window.document.picking.hgroupNumber" + (p+1).toString() )
		    	var nextGroupNumber = (eval(hgroupNumberO.toString())).value;
				//var nextGroupNumber = document.getElementById("hgroupNumber"+(p+1)+"").value;
			}	
			else 
				lastLine = true;
			
			eval( pickqtyO = "window.document.picking.picklistqty" + p.toString() );
	    	var pickqty = (eval(pickqtyO.toString())).value;
	    	if (pickqty > 0 )
	    		pickqtyvalpresent = true;
			else
				pickqtyvalpresent = false;
	    	//store the pickqty in an array when the quantities dont match
	    	pickQtyArr[index]=pickqty;
	    	
			eval( enteredqty0 = "window.document.picking.qty_picked" + p.toString() );
			if (typeof eval(enteredqty0.toString()) == 'undefined') {
				var enteredqty =0;
				qtypicktextbox = false;
			} else {
				savePforPrompt = p;
				qtypicktextbox = true;
				var enteredqty = (eval(enteredqty0.toString())).value;
			}
	    	//store the entered qty and p number to focus when the quantities dont match in an array
			enteredQtyArr[index]=enteredqty;
			savePforPromptArr[index]=savePforPrompt;
			
			//var receiptNumber = document.getElementById("receiptId"+p+"").value;
			eval( receiptNumberO = "window.document.picking.receiptId" + p.toString() )
	    	var receiptNumber = (eval(receiptNumberO.toString())).value;
			//store the receipt number to display in case the quantities dont match
			receiptNumberArr[index]= receiptNumber
			
			//check for more than one receipt pick qty present - scenario where the user enters pick qty for more than one receipt in a picklist
			if (qtypicktextbox && pickqtyvalpresent)
				numOfLines++;
			
			//total qty and total qtypicked
			totalpickqty = totalpickqty*1 + pickqty*1;
			totalenteredpickqty = totalenteredpickqty*1 + enteredqty*1;			
			if (currGroupNumber == nextGroupNumber && !lastLine) {				
					samepick = true;
					validate = true;
					index++;
			} else {				
				samepick = false;
				index++;
				indexTemp = index; 
				if (numOfLines == 1 || totalenteredpickqty <= 0) {
					validate = false;
					totalpickqty = 0;
					totalenteredpickqty = 0;
				}
				numOfLines = 0;
				index = 0;
			}
			
			if(!samepick && validate && totalenteredpickqty > 0) {
				//Take user confirmation is the total qty entered is not same as the total pick qty (for all the receipts)
				if (totalpickqty != totalenteredpickqty) {
					var warningmsg = "The total quantity picked ("+totalenteredpickqty+") does not match the total pick quantity ("+totalpickqty+").\n\nDo you want to continue?\n\n\nClick 'Ok' if you want to continue with the quantity you entered.";
					totalpickqty = 0;
					totalenteredpickqty = 0;
					testqty12 = eval("window.document.picking.qty_picked" + savePforPrompt.toString());		           		
	           		testqty12.focus();
					if (!confirm(warningmsg)) {		       
						testqty12 = eval("window.document.picking.qty_picked" + savePforPrompt.toString());		           		
		           		testqty12.focus();
		           		continueValidation = false;
		           		return false;
					}
				} else {
					//Check if the user has entered the amount for all the receipts in scenarios where the total qty entered matches total pick qty (for all the receipts)
					for ( var ptemp=0 ; ptemp < indexTemp; ptemp++ ) {
						if (pickQtyArr[ptemp] != "" && pickQtyArr[ptemp] > 0) {
							if (enteredQtyArr[ptemp] == "" || enteredQtyArr[ptemp] == 0) {
								var warningmsg = "The quantity picked ("+enteredQtyArr[ptemp]+") does not match the pick quantity ("+pickQtyArr[ptemp]+") for receipt " +receiptNumberArr[ptemp]+ ".\n\nDo you want to continue?\n\n\nClick 'Ok' if you want to continue with the quantity you entered.";
								savePforPrompt = savePforPromptArr[ptemp];
								testqty = eval("window.document.picking.qty_picked" + savePforPrompt.toString());		           		
				           		testqty.focus();
								if (!confirm(warningmsg)) {		       
									testqty = eval("window.document.picking.qty_picked" + savePforPrompt.toString());		           		
					           		testqty.focus();
					           		continueValidation = false;
					           		return false;
								}
							}
						}
					}					
				}
				indexTemp=0;
				totalpickqty = 0;
				totalenteredpickqty = 0;
			}
		} else {
			validate = false;
		}
		
		if (!samepick) {
			indexTemp=0;
			pickQtyArr= new Array();
			receiptNumberArr= new Array();
			savePforPromptArr= new Array();
			enteredQtyArr= new Array();
		}
	}
	
	return true;
}


