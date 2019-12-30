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

function enterdotinfo(item_id)
{
   loc = "/tcmIS/hub/shippinginfo.do?uAction=search&itemId=" + item_id;
   openWinGeneric(loc,"enterdotinfo","750","600","yes")
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

function checkall(checkboxname,sartingvalue)
{
var checkall = document.getElementById("chkall");
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
	var linecheck = document.getElementById(""+checkboxname+""+(p+1)+"");
	linecheck.checked =result;
	linecheck.value = valueq;
	}
	catch (ex2)
	{

	}
}

}

function regenerate(Hub)
{
	var hubName = document.getElementById("HubName");
	if (hubName.value == "All")
	{
		alert("Please Choose a Hub");
	   return false;
	}

    /*var testbin;
    eval( testbin =  "window.document.genPickList.HubName");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );
    if (curval == "All")
    {
        alert("Please Choose a Hub");
        return false;
    }

    var testfac;
    eval( testfac =  "window.document.genPickList.FacName");
    var fac = null;
    eval( fac = (eval(testfac.toString())).selectedIndex );
    var facval = null;
    ( facval =  (eval(testfac.toString())).options[fac].value );*/

	 var facName = document.getElementById("FacName");

    loc = "/tcmIS/Hub/reprintPicklist?";
    loc = loc + "&HubNo=" + hubName.value;
    loc = loc + "&FacName=" + facName.value;
    openWinGeneric(loc,"Show_Picklists","300","150","yes");
}

function sendpicklist(name,entered)
{
    var picklistbin;
    eval( picklistbin =  "window.document.picklistids.picklist");
    var cur = null;
    eval( cur = (eval(picklistbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(picklistbin.toString())).options[cur].value );

    opener.document.genPickList.UserAction.value = "printpicks";
    opener.document.genPickList.SubUserAction.value = "regeneratepicks";
    opener.document.genPickList.regenPickid.value = curval;

    opener.document.genPickList.submit();
    window.close();
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
    for (var i = document.genPickList.FacName.length;i > 0;i--)
    {
        document.genPickList.FacName.options[i] = null;
    }
    reloading = true;
    showlinks(indexofxompany);
    window.document.genPickList.FacName.selectedIndex = 0;
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
    document.genPickList.FacName.options[href] = optionName;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}


function doPrintpicks(sortby)
{
    var testurl3 = "/tcmIS/Hub/searchpicklist?session=Active&generate_picklist=yes&UserAction=generatepicks&SubUserAction=printpicklist&SortBy="+sortby+"";
    openWinGeneric(testurl3,"Generate_Picklist","640","600","yes")
}

function doPrintbox(HubNoforlabel)
{
	 var testurl3 = "/tcmIS/hub/reprintboxlbls?";
	 testurl3 = testurl3 + "HubNoforlabel=" + HubNoforlabel ;
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}


function doPrintbol()
{
    var testurl3 = "/tcmIS/Hub/searchpicklist?session=Active&generate_bols=yes&UserAction=generatebols";
    var paperSize  =  window.document.genPickList.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}

function doPrintconsolpicks()
{
	var consoldiurl = "/tcmIS/Hub/searchpicklist?session=Active&generate_picklist=yes&UserAction=genconsolidatedpicks";
   openWinGeneric(consoldiurl,"Cons_Picklist","640","600","yes")
}

function printconsolidatedpicks(entered)
{
    window.document.genPickList.UserAction.value = "printpicks";
    window.document.genPickList.SubUserAction.value = "consolidatedpicks";

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

function reprintpicks(entered)
{
    window.document.genPickList.UserAction.value = "printpicks";
    window.document.genPickList.SubUserAction.value = "reprintpicks";

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
    window.document.genPickList.UserAction.value = "printpicks";
    window.document.genPickList.SubUserAction.value = "updatepicks";

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



function generate(entered)
{
    window.document.genPickList.UserAction.value = "Generate";
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

function goBack(entered)
{
    window.document.genPickList.UserAction.value = "Back";
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



function update(entered)
{
    window.document.genPickList.UserAction.value = "Update";
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
    eval( testbin =  "window.document.genPickList.HubName");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );
    if (curval == "All")
    {
        alert("Please Choose a Hub");
        return false;

    }

window.document.genPickList.UserAction.value = "Search";
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
    window.document.genPickList.UserAction.value = "printpicks";
    window.document.genPickList.SubUserAction.value = "PrintBOXLabels";

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
    window.document.genPickList.UserAction.value = "printpicks";
    window.document.genPickList.SubUserAction.value = "PrintBOL";

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
    window.document.genPickList.UserAction.value = "printpicks";
    window.document.genPickList.SubUserAction.value = "PrintBOLDetail";

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


function checkpickvalue(name,entered)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    var pickchekbox;
    eval( pickchekbox  =  "window.document.genPickList." + name.toString() );

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