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

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function assignPaper(paper)
{
window.document.receiving.Paper.value =paper ;
}


function dotaplabelsPopup()
{
    var testurl5 = "/tcmIS/hub/repackaging?session=Active&generate_labels=yes&UserAction=GenerateLabels";
    var paperSize  =  window.document.receiving.Paper.value;
    testurl4 = testurl5 + "&paperSize=" + paperSize ;

    var testbin = document.getElementById("HubName");
    testurl4 = testurl4 + "&HubNoforlabel=" + testbin.value ;

    var printKitLabels  =  document.getElementById("printKitLabels");
	 if (printKitLabels.checked)
	 {
    testurl4 = testurl4 + "&printKitLabels=" +printKitLabels.value;
    }

    openWinGeneric(testurl4,"Generate_labels","600","600","yes")
}

function showEmtyBins(itemID,LineNo,Hub)
{
    loc = "/tcmIS/servlet/radian.web.servlets.internal.InternalShowEmptyBins?itemID=";
    loc = loc + itemID;
    loc = loc + "&HubNo=" + Hub;
    loc = loc + "&LineNo=" + LineNo;
    openWinGeneric(loc,"Show_Empty_Bins","300","150","no")
}


function CheckQtyValue(name,entered,delname)
{
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    finalMsgt = "Please enter valid values for: \n\n";

    eval( testqty = "window.document.receiving.qty_picked" + delname.toString() )
    var v = (eval(testqty.toString())).value;
    if ( v*1 < 0 )
    {
        finalMsgt = finalMsgt + " Qty Repackaged. \n";
        allClear = 1;
        testqty12  =  eval("window.document.receiving.qty_picked" + delname.toString() );
        testqty12.value = "";
    }
    else if ( !(isInteger(v)) )
    {
        finalMsgt = finalMsgt + " Qty Repackaged. \n";
        allClear = 1;
        testqty12  =  eval("window.document.receiving.qty_picked" + delname.toString() );
        testqty12.value = "";
    }

         if (allClear != 0)
         {
           alert(finalMsgt);
         }
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

function checkpickvalue(name,entered)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    var pickchekbox;
    eval( pickchekbox  =  "window.document.receiving." + name.toString() );

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

function startnewtap(itemid,invgroup)
{
   //var invengrp = document.getElementById("invengrp");
   var hubnum = document.getElementById("HubName");

   if (invgroup == "All")
   {
       alert("Please Choose an Inventory Group");
   }
   else
   {
   loc = "/tcmIS/hub/startnewtap?UserAction=Search&invengrp=" + invgroup + "&hubnum="+hubnum.value+ "&SearchField=" + itemid;
   openWinGeneric(loc,"startnewtap","750","420","yes")
   }
}

function undotap()
{
   var invengrp = document.getElementById("invengrp");
   var hubnum = document.getElementById("HubName");

   if (invengrp.value == "All")
   {
       alert("Please Choose an Inventory Group");
   }
   else
   {
   loc = "/tcmIS/hub/undotap?session=Active&invengrp=" + invengrp.value + "&hubnum="+hubnum.value;
   openWinGeneric(loc,"undotap","600","420","yes")
   }
}

function closetap(receiptId,invgroup)
{
   //var invengrp = document.getElementById("invengrp");
   var hubnum = document.getElementById("HubName");

   if (invgroup == "All")
   {
       alert("Please Choose an Inventory Group");
   }
   else
   {
   loc = "/tcmIS/hub/closetap?Action=closetap&invengrp=" + invgroup + "&hubnum="+hubnum.value + "&closetapid=" +receiptId;
   openWinGeneric(loc,"closetap","600","420","yes")
   }
}


function forcerepack()
{
   var invengrp = document.getElementById("invengrp");
   var hubnum = document.getElementById("HubName");

   if (invengrp.value == "All")
   {
       alert("Please Choose an Inventory Group");
   }
   else
   {
   loc = "/tcmIS/hub/forcerepack?session=Active&UserAction=New&invengrp=" + invengrp.value + "&hubnum="+hubnum.value;
   openWinGeneric(loc,"forcerepack","600","420","yes")
   }
}

function update(entered)
{
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "UpdPage";
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
    window.document.receiving.UserAction.value = "search";
    window.document.receiving.SubUserAction.value = "NA";
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

function newtapsearch(entered)
{
    window.document.receiving.UserAction.value = "Search";
    window.document.receiving.SubUserAction.value = "NA";
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



function cancel()
{
    window.close();
}