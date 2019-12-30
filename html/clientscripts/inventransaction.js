function showMsg()
{
 alert("");
}
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
        alert("This form has already been submitted.  Thanks!");
        return false;
      }
}
function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
   windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function hubchanged(object)
{
	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.InvenTrans.invengrp.length; i > 0;i--)
   {
      document.InvenTrans.invengrp.options[i] = null;
   }
	showinvlinks(artist);
	window.document.InvenTrans.invengrp.selectedIndex = 0;
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
    document.InvenTrans.invengrp.options[href] = optionName;
}

function getReceiptnotes(receiptId)
{
    var loc = "/tcmIS/Hub/Logistics?session=Active&UserAction=addReceiptNotes";
    if (receiptId.length > 0)
    {
       loc = loc + "&receiptId=" + receiptId;
       openWinGeneric(loc,"ReceiptNotes","550","200","yes")
    }
}

function showBinHistory(receiptId)
{
    var testurl3 = "/tcmIS/Hub/Transactions?session=Active&UserAction=binHistory";
    testurl3 = testurl3 + "&receiptid=" + receiptId ;

    openWinGeneric(testurl3,"showBinHistory","640","400","yes")
}

function doPrintbol(prnumber,prline,prbatch)
{
    if (prnumber.length > 0 && prline.length > 0 && prbatch.length > 0)
    {
    var testurl3 = "/tcmIS/Hub/Transactions?session=Active&useraction=generatebol";
    var paperSize  =  "";
    testurl3 = testurl3 + "&prnumber=" + prnumber ;
    testurl3 = testurl3 + "&prlineitem=" + prline ;
    testurl3 = testurl3 + "&prbatch=" + prbatch ;

    openWinGeneric(testurl3,"Generate_rebols","640","600","yes")
    }
}

function doPrintrelabel(receiptId)
{
    var testurl3 = "/tcmIS/Hub/reprintnoinvenlabels?session=Active&generate_bols=yes";
    var paperSize  =  "";
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    testurl3 = testurl3 + "&receiptId=" + receiptId ;

	var testbin;
   eval( testbin =  "window.document.InvenTrans.HubName");
   var cur = null;
   eval( cur = (eval(testbin.toString())).selectedIndex );
   var curval = null;
   ( curval =  (eval(testbin.toString())).options[cur].value );
	testurl3 = testurl3 + "&HubNoforlabel=" + curval ;

    openWinGeneric(testurl3,"Generate_relabels","640","600","yes")
}


function ChecktransQtyValue(delname)
{
    var allClear = 0;
    finalMsgt = "Please enter valid values for: \n\n";

    testqty = document.getElementById("qty_picked"+ delname.toString() + "");
	 var v =testqty.value;

    if ( testqty.value*1 < 0 )
    {
        finalMsgt = finalMsgt + " Quantity Picked. \n";
        allClear = 1;
        testqty.value = "";
    }
    else if ( !(isInteger(v)) )
    {
        finalMsgt = finalMsgt + " Quantity Picked. \n";
        allClear = 1;
        testqty.value = "";
    }

    if (allClear > 0)
    {
		alert(finalMsgt);
    }
}


function checkContainerIdValue(lineNumber, mrItemNumber)
{
    var allClear = 0;
    finalMsgt = "Please enter valid values for: \n\n";

    testContainerId = document.getElementById("rePrintContainerId"+ mrItemNumber.toString() + "");
	 var v =testContainerId.value;

    if ( !(isInteger(v)) )
    {
        finalMsgt = finalMsgt + " Container ID. \n";
        allClear = 1;
        testContainerId.value = "";
    }

    if (allClear > 0)
    {
		alert(finalMsgt);
    }
    else
    {
        testqty = document.getElementById("qty_picked"+ lineNumber.toString() + "");
	    testqty.value = "1";
    }
}