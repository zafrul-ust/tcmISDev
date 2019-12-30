var submitcount=0;
var shipupdcount=0;
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

function unReceive(name,entered,prnumber,lineitem,picklistid)
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
	 testurl3 = testurl3 + "HubNoforlabel=" + HubNoforlabel ;

    openWinGeneric(testurl3,"Generate_Boxlabels","640","600","yes")
}

function doPrintrelabel()
{
    var testurl3 = "/tcmIS/Hub/reprintlabels?session=Active&generate_bols=yes";
    var paperSize  =  window.document.picking.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    openWinGeneric(testurl3,"Generate_relabels","640","600","yes")
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

function CheckQtyValue(name,entered,delname)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    finalMsgt = "Please enter valid values for: \n\n";

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
        finalMsgt = finalMsgt + " Quantity Picked. \n";
        allClear = 1;
        testqty12  =  eval("window.document.picking.qty_picked" + delname.toString() );
        testqty12.value = "";
    }

	 if (allClear == 0)
	 {
	 eval( pickqtyO = "window.document.picking.picklistqty" + delname.toString() )
    var pickqty = (eval(pickqtyO.toString())).value;

    if (pickqty != v)
    {
       warningmsg = "You entered a different quantity ("+v+") from what is on the Picklist ("+pickqty+") \n\nDo you want to continue?\n\n\nClick 'Ok' if you want to contiune with the different quantity.";
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