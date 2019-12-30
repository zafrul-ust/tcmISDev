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
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}


function showreceivingpagelegend()
{
  openWinGeneric("/tcmIS/help/receivingpagelegend.html","receivingpagelegend","290","300","no","50","50")
}

function showlogisticspagelegend()
{
  openWinGeneric("/tcmIS/help/logisticspagelegend.html","logisticspagelegend","290","300","no","50","50")
}

function showProjectDocuments(receiptId,inventoryGroup)
{
 var loc = "/tcmIS/hub/receiptdocuments.do?receiptId="+receiptId+"&showDocuments=Yes&inventoryGroup="+inventoryGroup+"";
 openWinGeneric(loc,"showAllProjectDocuments","450","300","no","80","80");
}

//Nawaz 03-23-05 to handle the generate large labels button
function generatelargelabels()
{
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "generatelargelabels";

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

function dolargelabelPopup()
{
   var testurl4 = "/tcmIS/hub/printlargelabel?session=Active";
   openWinGeneric(testurl4,"Generate_large_labels","600","600","yes")
}


function setprinterRes(printerRes)
{
  window.document.receiving.printerRes.value = printerRes;
}

function showreceivingqclegend()
{
	openWinGeneric("/tcmIS/help/recqclegend.html","recevingqclegend","250","325","no","50","50")
}

function generatealllabels(entered)
{
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "generatealllabels";
    window.document.receiving.DuplLineNumber.value = "NA"  ;
    window.document.receiving.AddBin_Item_Id.value = "NA" ;
    window.document.receiving.AddBin_Bin_Id.value = "NA" ;
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

function showrecforinvtransfr(trnsreqid)
{
    loc = "/tcmIS/Hub/PreviousTransactions?session=Active&UserAction=tranregeceiving&transreqid=";
    loc = loc + trnsreqid;
    openWinGeneric(loc,"Previous_Transfer_Transactions","700","400","yes")
}

function enterdotinfo(item_id)
{
   loc = "/tcmIS/hub/shippinginfo.do?uAction=search&itemId=" + item_id;
   openWinGeneric(loc,"enterdotinfo","750","600","yes")
}

function sendtervalues()
{
linenumberchoos = document.getElementById("linenumberchoos").value;

rootcause = document.getElementById("rootcause");
rootcausecompany = document.getElementById("rootcausecompany");
rootcausenotes = document.getElementById("rootcausenotes");

mainrootcause = opener.document.getElementById("rootcause"+linenumberchoos+"");
mainrootcause.value = rootcause.value;

mainrootcausecompany = opener.document.getElementById("rootcausecompany"+linenumberchoos+"");
mainrootcausecompany.value = rootcausecompany.value;

mainrootcausenotes = opener.document.getElementById("rootcausenotes"+linenumberchoos+"");
mainrootcausenotes.value = rootcausenotes.value;

window.close();
}

function checkterminalstatus(rownumberis)
{
 var lotstatus =  document.getElementById("selectElementStatus"+rownumberis+"");
 var origlotstatus =  document.getElementById("origlotstatus"+rownumberis+"");

 if (lotstatus.value.length == 0 )
 {
	var selecelemet = lotstatus.selectedIndex;
	var testelementtext = lotstatus.options[selecelemet].text;
	if (origlotstatus.value != testelementtext)
	{
	alert("You don't have permissions to change the status to "+testelementtext+".")
	}

	i = 0;
	while(i<lotstatus.length)
	{
	  var elementtext = lotstatus.options[i].text;

	  if (elementtext == origlotstatus.value)
	  {
		lotstatus.selectedIndex = i;
	  }
	   i+=1;
	}
 }
 else
 {
	if (lotstatus.value == "Customer Purchase" || lotstatus.value == "Write Off Requested" || lotstatus.value == "3PL Purchase")
	 {
	   loc = "/tcmIS/Hub/ReceivingQC?session=Active&UserAction=showterminalstatus&termlotstatus=";
	   loc = loc + lotstatus.value + "&terlinenumber=" + rownumberis;
	   openWinGeneric(loc,"terminal_root_cause","500","300","no");
	 }
	 else
	 {
	   mainrootcause = document.getElementById("rootcause"+rownumberis+"");
	   mainrootcause.value = "";

	   mainrootcausecompany = document.getElementById("rootcausecompany"+rownumberis+"");
	   mainrootcausecompany.value = "";

	   mainrootcausenotes = document.getElementById("rootcausenotes"+rownumberis+"");
	   mainrootcausenotes.value = "";
	 }
 }

}

//01-14-03
function createxls()
{
    var loc = "/tcmIS/Hub/Receiving?session=Active&UserAction=createxls";
    openWinGeneric(loc,"createXls","800","600","yes");
}

//Nawaz 12-27-02 to handle the generate xls button
function generatexls(entered)
{
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "generatexls";
    loc = "/tcmIS/Hub/Logistics?session=Active&UserAction=Update&SubUserAction=generatexls";
    openWinGeneric(loc,"ItemId","800","600","yes");
}

//11-14-02
function getReceiptnotes(receiptId)
{
    var loc = "/tcmIS/Hub/Logistics?session=Active&UserAction=addReceiptNotes";
    if (receiptId.length > 0)
    {
       loc = loc + "&receiptId=" + receiptId;
       openWinGeneric(loc,"ReceiptNotes","550","200","yes")
    }
}

//07-10-02
function splitQty(receiptid,Hub,Qtyava)
{
    loc = "/tcmIS/Hub/splityqty?session=Active&UserAction=splitqty&receiptid=";
    loc = loc + receiptid;
    loc = loc + "&HubNo=" + Hub;
    loc = loc + "&QtyAvailable=" + Qtyava;
    openWinGeneric(loc,"Previous_Transactions","350","300","no");
}
/*
//07-14-02
function sendsplitbin(name,entered)
{
    if (CheckSplitQtyValue(name,entered))
    {
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "Updatesplit";

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

//07-14-02
function CheckSplitQtyValue(name ,entered)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    finalMsgt = "Please enter valid values for: \n\n";

    var maxsplitqty  =  window.document.receiving.maxsplitqty.value;
    eval( testqty = "window.document.receiving.splitqty")
    var v = (eval(testqty.toString())).value;
    if ( v*1 < 0 )
    {
        finalMsgt = finalMsgt + " Quantity To Split. \n";
        allClear = 1;
        testqty12  =  eval("window.document.receiving.splitqty");
        testqty12.value = "";
    }
    else if ( !(isInteger(v)) )
    {
        finalMsgt = finalMsgt + " Quantity To Split. \n";
        allClear = 1;
        testqty12  =  eval("window.document.receiving.splitqty");
        testqty12.value = "";
    }
    else if ( v*1 > maxsplitqty*1 )
    {
        finalMsgt = finalMsgt + " Quantity To Split. \n";
        allClear = 1;
        testqty12  =  eval("window.document.receiving.splitqty");
        testqty12.value = "";
    }

   if (allClear >0)
   {
      alert(finalMsgt);
		return false;
   }
   else
   {
      return true;
   }
}
*/
//Nawaz 06-27-02 to handle the generate labels button
function generatelabels(entered)
{
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "generatelabels";
    window.document.receiving.DuplLineNumber.value = "NA"  ;
    window.document.receiving.AddBin_Item_Id.value = "NA" ;
    window.document.receiving.AddBin_Bin_Id.value = "NA" ;
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


//06-25-02
function addnewBin()
{
 var testbin;
  eval( testbin =  "window.document.receiving.HubName");
  var cur = null;
  eval( cur = (eval(testbin.toString())).selectedIndex );
  var curval = null;
  ( curval =  (eval(testbin.toString())).options[cur].value );
  //alert(curval);
  var newbinURL = "/tcmIS/Hub/AddNewBin?";
  newbinURL = newbinURL + "&HubName=" + curval;

  openWinGeneric(newbinURL,"add_newbin","500","225","Yes");

}

function Unreceive()
{
    opener.document.receiving.UserAction.value = "UPDATE";
    opener.document.receiving.SubUserAction.value = "DuplLine" ;
    opener.document.receiving.submit();window.close();
}
function Searchqc()
{
    opener.document.receiving.UserAction.value = "NEW";
    opener.document.receiving.SubUserAction.value = "NA";
    opener.document.receiving.DuplLineNumber.value = "NA"  ;
    opener.document.receiving.AddBin_Item_Id.value = "NA" ;
    opener.document.receiving.AddBin_Bin_Id.value = "NA" ;
    opener.document.receiving.submit();
    window.close();
}
function unReceive(name ,entered,receipt_id)
{
    var found = false;
    var button_id = name.toString();
    var total  =  window.document.receiving.Total_number.value;
    for ( var p = 0 ; p < total ; p ++)
    {
        var line_num ;
        eval ( line_num = p + 1 );
        if ( button_id == ("Button"+line_num.toString()) )
        {
            window.document.receiving.DuplLineNumber.value = line_num.toString() ;
            found = true;
            break;
        }
    }
    if ( found == true )
    {
        loc = "/tcmIS/Hub/ReceivingQC?session=Active&UserAction=Unreceive&SubUserAction=Unreceive&remove_receipt_id=";
        loc = loc + receipt_id;
        openWinGeneric(loc,"Reverse_Receiving","300","150","no")
        return true;
    }
    return false;
}


function showPreviousReceiptTransactions(receiptid,Hub)
{
    loc = "/tcmIS/Hub/PreviousTransactions?session=Active&UserAction=previousreceiving&receiptid=";
    loc = loc + receiptid;
    loc = loc + "&HubName=" + Hub;
    openWinGeneric(loc,"Previous_Transactions","700","400","yes")
}


function showPreviousTransactions(originallot,Hub)
{
    loc = "/tcmIS/Hub/PreviousTransactions?session=Active&UserAction=previousreceiving&mfglot=";
    loc = loc + originallot;
    loc = loc + "&HubName=" + Hub;
    openWinGeneric(loc,"Previous_Transactions","700","400","yes")
}



function showPreviousrecqc(poline,Hub)
{
    loc = "/tcmIS/Hub/ReceivingQC?session=Active&UserAction=previousreceiving&poline=";
    loc = loc + poline;
    loc = loc + "&HubNo=" + Hub;
    openWinGeneric(loc,"Previous_Receiving","600","300","yes")
}
function showPreviouspolineqc(poline,purchase_order,Hub)
{
    loc = "/tcmIS/Hub/ReceivingQC?session=Active&UserAction=previousreceiving&poline=";
    loc = loc + poline;
    loc = loc + "&purchorder=" + purchase_order;
    loc = loc + "&HubNo=" + Hub;
    openWinGeneric(loc,"Previous_Receiving","600","300","yes")
}


function showEmtyBins(itemID,LineNo,Hub)
{
    loc = "/tcmIS/servlet/radian.web.servlets.internal.InternalShowEmptyBins?itemID=";
    loc = loc + itemID;
    loc = loc + "&HubNo=" + Hub;
    loc = loc + "&LineNo=" + LineNo;
    openWinGeneric(loc,"Show_Empty_Bins","300","150","no")
}

function showPreviousrec(poline,Hub)
{
    loc = "/tcmIS/Hub/Receiving?session=Active&UserAction=previousreceiving&poline=";
    loc = loc + poline;
    loc = loc + "&HubNo=" + Hub;
    openWinGeneric(loc,"Previous_Receiving","600","300","yes")
}

function showPreviouspoline(poline,purchase_order,Hub)
{
    loc = "/tcmIS/Hub/Receiving?session=Active&UserAction=previousreceiving&poline=";
    loc = loc + poline;
    loc = loc + "&purchorder=" + purchase_order;
    loc = loc + "&HubNo=" + Hub;
    openWinGeneric(loc,"Previous_Receiving","600","300","yes")
}

function showReceivingJacket(po,itemID,poline,hub)
{
    loc = "/cgi-bin/radweb/rec_sheet.cgi?po=";
    loc = loc + po;
    loc = loc + "&item=" + itemID;
    loc = loc + "&line=" + poline;
    if (hub == "2101")
    {
       loc = loc + "&tab=N";
    }
    else
    {
       loc = loc + "&tab=Y";
    }
    openWinGeneric(loc,"Receiving_Jacket","600","600","yes")
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
function duplLine(name ,entered)
{
    var found = false;
    var button_id = name.toString();
    var total  =  window.document.receiving.Total_number.value;
    for ( var p = 0 ; p < total ; p ++)
    {
        var line_num ;
        eval ( line_num = p + 1 );
        if ( button_id == ("Button"+line_num.toString()) )
        {
            window.document.receiving.UserAction.value = "UPDATE";
            window.document.receiving.SubUserAction.value = "DuplLine" ;
            window.document.receiving.DuplLineNumber.value = line_num.toString() ;
            found = true;
            break;
        }
    }
    if ( found == true )
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
    return false;
}

function addbin(name ,entered)
{
    var found = false;
    var bin_id = prompt("Enter bin # " , "");
    if ( bin_id == null )
    {
        return false;
    }
    if ( bin_id.length < 1 )
    {
        alert ( "Please enter bin name");
        return false;
    }
    var button_id = name.toString();
    var total  =  window.document.receiving.Total_number.value;
    if (total == 1)
    {
        window.document.receiving.UserAction.value = "UPDATE";
        window.document.receiving.SubUserAction.value = "AddBin";
        var line_item = window.document.receiving.item_id.value;
        window.document.receiving.AddBin_Item_Id.value = line_item.toString() ;
        eval ( window.document.receiving.AddBin_Item_Id.value = line_item.toString() ) ;
        window.document.receiving.AddBin_Bin_Id.value = bin_id.toString() ;
        window.document.receiving.DuplLineNumber.value = "NA"  ;
        window.document.receiving.AddBin_Line_No.value = "1"  ;
        found = true;
    }
    else
    {
        for ( var p = 0 ; p < total ; p ++)
        {
            var line_num ;
            eval ( line_num = p + 1 );
            if ( button_id == ("ItemButton" + line_num.toString()) )
            {
                window.document.receiving.UserAction.value = "UPDATE";
                window.document.receiving.SubUserAction.value = "AddBin";
                var line_item = window.document.receiving.item_id[p].value;
                window.document.receiving.AddBin_Item_Id.value = line_item.toString() ;
                eval ( window.document.receiving.AddBin_Item_Id.value = line_item.toString() ) ;
                window.document.receiving.AddBin_Bin_Id.value = bin_id.toString() ;
                window.document.receiving.DuplLineNumber.value = "NA"  ;
                window.document.receiving.AddBin_Line_No.value = line_num.toString();
                found = true;
                break;
            }
        }
    }
    if ( found == true )
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
    return false;
}

function update(entered)
{
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "UpdPage";
    window.document.receiving.DuplLineNumber.value = "NA"  ;
    window.document.receiving.AddBin_Item_Id.value = "NA" ;
    window.document.receiving.AddBin_Bin_Id.value = "NA" ;
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
    window.document.receiving.UserAction.value = "NEW";
    window.document.receiving.SubUserAction.value = "NA";
    window.document.receiving.DuplLineNumber.value = "NA"  ;
    window.document.receiving.AddBin_Item_Id.value = "NA" ;
    window.document.receiving.AddBin_Bin_Id.value = "NA" ;
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

function checknonchemqc(name ,entered)
{
var yes = "yes";
var no = "no";

eval( testcheckbox  =  "window.document.receiving." + name.toString() );
if ( ((eval(testcheckbox.toString())).value )  == no.toString())
{
 eval((eval(testcheckbox.toString())).value  = yes.toString() );;
 eval( (eval(testcheckbox.toString())).checked = true );;
 updatecount++;
}
else if ( ((eval(testcheckbox.toString())).value )  == yes.toString())
{
 eval((eval(testcheckbox.toString())).value  = no.toString() );
 eval( (eval(testcheckbox.toString())).checked = false );
 updatecount--;
}
}

function checkValuesqc(name ,entered)
{
    var checkbox_id = name.toString();
    var total  =  window.document.receiving.Total_number.value;
    var HubFacCount = window.document.receiving.HubFacCount.value;

    //Remove this if you want to chek date of mfg and other stuff for more than one client
    HubFacCount = "1";

    var result ;
    var yes = "yes";
    var no = "no";
    var NONE = "NONE";
    var incom = "Incoming";
    for ( var p = 0 ; p < total ; p ++)
    {
        var line_num ;
        eval ( line_num = p + 1 );
        if ( checkbox_id == ("row_chk" + line_num.toString()) )
        {

            var testdate;
            var testqty;
            var testlot;
            var finalMsg;
            var allClear = 0;

            var testcheckbox;

		finalMsgt = "Please enter valid values for: \n\n";

            eval( testcheckbox  =  "window.document.receiving.row_chk" + line_num.toString() );
            if ( ((eval(testcheckbox.toString())).value )  == no.toString())
            {
            }
            if ( ((eval(testcheckbox.toString())).value )  == yes.toString())
            {
                eval((eval(testcheckbox.toString())).value  = no.toString() );
                eval( (eval(testcheckbox.toString())).checked = false );
                updatecount--;
                break;
            }

				var statusoveride = document.getElementById("statusoveride"+line_num.toString()+"");
            if (statusoveride.value == "N")
            {
					var testbin;
	            eval( testbin =  "window.document.receiving.selectElementStatus" + line_num.toString() );
	            var cur = null;
	            eval( cur = (eval(testbin.toString())).selectedIndex );
	            var curval = null;
	            ( curval =  (eval(testbin.toString())).options[cur].value );
	            if ( curval  == incom.toString())
	            {
	                finalMsgt = finalMsgt +   " Lot Status cannot be Incoming. \n" ;
	                allClear = 1;
	            }
            }

			   eval( testactualshipdate = "window.document.receiving.act_suppship_date" + line_num.toString() )
            if ( (eval(testactualshipdate.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testactualshipdate.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Actual Supplier Ship Date. \n" ;
                    allClear = 1;
                }
            }
            else if ( (eval(testactualshipdate.toString())).value.length > 0 )
            {
                finalMsgt = finalMsgt + " Actual Supplier Ship Date.\n" ;
                allClear = 1;
            }

            eval( testdate = "window.document.receiving.date_mfgd" + line_num.toString() )
            if ( (eval(testdate.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testdate.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Date Manufactured. \n" ;
                    allClear = 1;
                }
            }
            else if ( (eval(testdate.toString())).value.length > 0 )
            {
                finalMsgt = finalMsgt + " Date Manufactured. \n" ;
                allClear = 1;
            }
            else
            {
                if (HubFacCount*1 > 1)
                {
                    finalMsgt = finalMsgt + " Date Manufactured. \n" ;
                    allClear = 1;
                }
            }

            eval( testdatereceipt = "window.document.receiving.date_recieved" + line_num.toString() )
            if ( (eval(testdatereceipt.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testdatereceipt.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Date of Receipt. \n" ;
                    allClear = 1;
                }
            }
            else
            {
                finalMsgt = finalMsgt + " Date of Receipt.\n" ;
                allClear = 1;
            }

            /*eval( testexpirydate = "window.document.receiving.expiry_date" + line_num.toString() )
				var expdatevalue =(eval(testexpirydate.toString())).value;
            if (!( expdatevalue == "Indefinite" || expdatevalue == "indefinite" || expdatevalue == "INDEFINITE" ))
            {
                if ( (eval(testexpirydate.toString())).value.length == 10 )
                {
                    if ( checkexpirydate((eval(testexpirydate.toString()))) == false )
                    {
                        finalMsgt = finalMsgt +   " Exp Date. \n" ;
                        allClear = 1;
                    }
                }
                else
                {
                    finalMsgt = finalMsgt + " Exp Date. \n" ;
                    allClear = 1;
                }
            }*/

            try
            {
             var selectedElementStatus  =  document.getElementById("selectElementStatus"+line_num.toString()+"");
             var lotStatusaa = new Array();
             lotStatusaa = lotStatus[selectedElementStatus.value];
             if (lotStatusaa.length > 0)
             {
               //alert("Pickable Status");
               eval( testexpirydate = "window.document.receiving.expiry_date" + line_num.toString() )
               var expdatevalue =(eval(testexpirydate.toString())).value;
               if (!( expdatevalue == "Indefinite" || expdatevalue == "indefinite" || expdatevalue == "INDEFINITE" ))
               {
                if ( (eval(testexpirydate.toString())).value.length == 10 )
                {
                  if ( checkexpirydate((eval(testexpirydate.toString()))) == false )
                  {
                   finalMsgt = finalMsgt +   " Exp Date. \n" ;
                   allClear = 1;
                  }
                }
                else
                {
                  finalMsgt = finalMsgt + " Exp Date. \n" ;
                  allClear = 1;
                }
               }
             }
            }
            catch (ex)
            {
             //alert("Non-Pickable Status");
            }

            eval( testshipdate = "window.document.receiving.ship_date" + line_num.toString() )
            if ( (eval(testshipdate.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testshipdate.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Ship Date. \n" ;
                    allClear = 1;
                }
            }
            else if ( (eval(testshipdate.toString())).value.length > 0 )
            {
                finalMsgt = finalMsgt + " Ship Date. \n" ;
                allClear = 1;
            }
            else
            {
                if (HubFacCount*1 > 1)
                {
                    finalMsgt = finalMsgt + " Ship Date. \n" ;
                    allClear = 1;
                }
            }

				if (statusoveride.value == "N")
            {
					eval( testqty = "window.document.receiving.label_qty" + line_num.toString() )
	            var v = (eval(testqty.toString())).value;
	            if ( v*1 < 0 )
	            {
	                testqty12  =  eval("window.document.receiving.label_qty" + line_num.toString() );
	                testqty12.value = "";
	            }
	            else if (v.length == 0)
	            {

	            }
	            else if ( !(isInteger(v)) )
	            {
	                finalMsgt = finalMsgt + " Label Qty \n";
	                allClear = 1;
	                testqty12  =  eval("window.document.receiving.label_qty" + line_num.toString() );
	                testqty12.value = "";
	            }
				}
            if (allClear < 1)
            {
                eval((eval(testcheckbox.toString())).value  = yes.toString() );;
                eval( (eval(testcheckbox.toString())).checked = true );;
                result = true;
            }
            else
            {
                eval((eval(testcheckbox.toString())).value  = no.toString() );;
                eval( (eval(testcheckbox.toString())).checked = false );;
                alert(finalMsgt);
                result = false;
            }
            break;
        }
    }
    if (true == result )
    {
        updatecount++;
    }
    return result;
}

function checkBGValues(name ,entered)
{
    var checkbox_id = name.toString();
    var total  =  window.document.receiving.Total_number.value;
    var result ;
    var yes = "yes";
    var no = "no";
    var NONE = "NONE";
    for ( var p = 0 ; p < total ; p ++)
    {
        var line_num ;
        eval ( line_num = p + 1 );
        if ( checkbox_id == ("row_chk" + line_num.toString()) )
        {

            var testdate;
            var testqty;
            var testlot;
            var finalMsgt;
            var allClear = 0;

            var testcheckbox;;
            eval( testcheckbox  =  "window.document.receiving.row_chk" + line_num.toString() );
            if ( ((eval(testcheckbox.toString())).value )  == no.toString())
            {
            }
            if ( ((eval(testcheckbox.toString())).value )  == yes.toString())
            {
                eval((eval(testcheckbox.toString())).value  = no.toString() );
                eval( (eval(testcheckbox.toString())).checked = false );
                updatecount--;
                break;
            }
            finalMsgt = "Please enter valid values for: \n\n";

            eval( testdatereceipt = "window.document.receiving.date_recieved" + line_num.toString() )
            if ( (eval(testdatereceipt.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testdatereceipt.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Date of Receipt. \n" ;
                    allClear = 1;
                }
            }
            else
            {
                finalMsgt = finalMsgt + " Date of Receipt.\n" ;
                allClear = 1;
            }
            eval( testlot = "window.document.receiving.mfg_lot" + line_num.toString() )
            if ( (eval(testlot.toString())).value.length < 1 )
            {
                finalMsgt = finalMsgt + " Supplier Ref #. \n" ;
                allClear = 1;
            }
            eval( testqty = "window.document.receiving.qty_recd" + line_num.toString() )
            var v = parseFloat( (eval(testqty.toString())).value );
            if ( isNaN(v) )
            {
                finalMsgt = finalMsgt + " Quantity Received. \n";
                allClear = 1;
            }
            if (allClear < 1)
            {
                eval((eval(testcheckbox.toString())).value  = yes.toString() );;
                eval( (eval(testcheckbox.toString())).checked = true );;
                result = true;
            }
            else
            {
                eval((eval(testcheckbox.toString())).value  = no.toString() );;
                eval( (eval(testcheckbox.toString())).checked = false );;
                alert(finalMsgt);
                result = false;
            }
            break;
        }
    }
    if (true == result )
    {
        updatecount++;
    }
    return result;
}
function checkValues(name ,entered)
{
    var checkbox_id = name.toString();
    var total  =  window.document.receiving.Total_number.value;
    var HubFacCount = window.document.receiving.HubFacCount.value;

    var result ;
    var yes = "yes";
    var no = "no";
    var NONE = "NONE";
    var incom = "Incoming";
    for ( var p = 0 ; p < total ; p ++)
    {
        var line_num ;
        eval ( line_num = p + 1 );
        if ( checkbox_id == ("row_chk" + line_num.toString()) )
        {

            var testdate;
            var testqty;
            var testlot;
            var finalMsgt;
            var allClear = 0;

            var testcheckbox;;
            eval( testcheckbox  =  "window.document.receiving.row_chk" + line_num.toString() );
            if ( ((eval(testcheckbox.toString())).value )  == no.toString())
            {
            }
            if ( ((eval(testcheckbox.toString())).value )  == yes.toString())
            {
                eval((eval(testcheckbox.toString())).value  = no.toString() );
                eval( (eval(testcheckbox.toString())).checked = false );
                updatecount--;
                break;
            }
            finalMsgt = "Please enter valid values for: \n\n";

            var testbin;
            eval( testbin =  "window.document.receiving.selectElementBin" + line_num.toString() );
            var cur = null;
            eval( cur = (eval(testbin.toString())).selectedIndex );
            var curval = null;
            ( curval =  (eval(testbin.toString())).options[cur].value );
            if ( curval  == NONE.toString())
            {
                finalMsgt = finalMsgt +   " BIN. \n" ;
                allClear = 1;
            }

				eval( testactualshipdate = "window.document.receiving.act_suppship_date" + line_num.toString() )
            if ( (eval(testactualshipdate.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testactualshipdate.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Actual Supplier Ship Date. \n" ;
                    allClear = 1;
                }
            }
            else if ( (eval(testactualshipdate.toString())).value.length > 0 )
            {
                finalMsgt = finalMsgt + " Actual Supplier Ship Date.\n" ;
                allClear = 1;
            }

            eval( testdate = "window.document.receiving.date_mfgd" + line_num.toString() )
            if ( (eval(testdate.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testdate.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Date Manufactured. \n" ;
                    allClear = 1;
                }
            }
            else if ( (eval(testdate.toString())).value.length > 0 )
            {
                finalMsgt = finalMsgt + " Date Manufactured. \n" ;
                allClear = 1;
            }
            eval( testdatereceipt = "window.document.receiving.date_recieved" + line_num.toString() )
            if ( (eval(testdatereceipt.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testdatereceipt.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Date of Receipt. \n" ;
                    allClear = 1;
                }
            }
            else
            {
                finalMsgt = finalMsgt + " Date of Receipt.\n" ;
                allClear = 1;
            }
            eval( testexpirydate = "window.document.receiving.expiry_date" + line_num.toString() )
            var expdatevalue =(eval(testexpirydate.toString())).value;
            if (!( expdatevalue == "Indefinite" || expdatevalue == "indefinite" || expdatevalue == "INDEFINITE" ))
            {
                if ( (eval(testexpirydate.toString())).value.length == 10 )
                {
                    if ( checkexpirydate((eval(testexpirydate.toString()))) == false )
                    {
                        finalMsgt = finalMsgt +   " Exp Date. \n" ;
                        allClear = 1;
                    }
                }
                else if ( (eval(testexpirydate.toString())).value.length > 0 )
                {
                    finalMsgt = finalMsgt + " Exp Date. \n" ;
                    allClear = 1;
                }
                else
                {
                    if (HubFacCount*1 == 1)
                    {
                        finalMsgt = finalMsgt + " Exp Date. \n" ;
                        allClear = 1;
                    }
                }

            }
            eval( testshipdate = "window.document.receiving.ship_date" + line_num.toString() )
            if ( (eval(testshipdate.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testshipdate.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Ship Date. \n" ;
                    allClear = 1;
                }
            }
            else if ( (eval(testshipdate.toString())).value.length > 0 )
            {
                finalMsgt = finalMsgt + " Ship Date. \n" ;
                allClear = 1;
            }

            eval( testlot = "window.document.receiving.mfg_lot" + line_num.toString() )
            if ( (eval(testlot.toString())).value.length < 1 )
            {
                finalMsgt = finalMsgt + " Mfg Lot #. \n" ;
                allClear = 1;
            }
            eval( testrecipt = "window.document.receiving.receipt_id" + line_num.toString() )
            if ( (eval(testrecipt.toString())).value.length < 1 )
            {
                finalMsgt = finalMsgt + " Receipt ID #. \n" ;
                allClear = 1;
            }

            var qtyoveride = document.getElementById("qtyoveride"+line_num.toString()+"");
            if (qtyoveride.value == "N")
            {
	 				eval( testqty = "window.document.receiving.qty_recd" + line_num.toString() )
	            var v = (eval(testqty.toString())).value;
	            if ( !(isInteger(v)) || v*1 == 0 )
	            {
	                finalMsgt = finalMsgt + " Quantity Received. \n";
	                allClear = 1;
	                testqty12  =  eval("window.document.receiving.qty_recd" + line_num.toString() );
	                testqty12.value = "";
	            }
            }

				try
            {
            	var selectedStatus  =  document.getElementById("selectElementStatus"+ line_num.toString()+"").value;
	            if ( selectedStatus  == incom.toString())
	            {
	                finalMsgt = finalMsgt +   " Lot Status cannot be Incoming. \n" ;
	                allClear = 1;
	            }
            }
            catch (ex)
            {

            }

            if (allClear < 1)
            {
                eval((eval(testcheckbox.toString())).value  = yes.toString() );;
                eval( (eval(testcheckbox.toString())).checked = true );;
                result = true;
            }
            else
            {
                eval((eval(testcheckbox.toString())).value  = no.toString() );;
                eval( (eval(testcheckbox.toString())).checked = false );;
                alert(finalMsgt);
                result = false;
            }
            break;
        }
    }
    if (true == result )
    {
        updatecount++;
    }
    return result;
}
function checkdate(objName)
{
alert("Here checkdate");
    var datefield = objName;
    if (chkdate(objName) == false)
    {
        return false;
    }
    else
    {
        return true;
    }
}
function chkdate(objName)
{
alert("Here chkdate");
    var strDatestyle = "US";
    var strDate;
    var strDateArray;
    var strDay;
    var strMonth;
    var strYear;
    var intday;
    var intMonth;
    var intYear;
    var booFound = false;
    var datefield = objName;
    var strSeparatorArray = new Array("-"," ","/",".");
    var intElementNr;
    var err = 0;
    var strMonthArray = new Array(12);
    strMonthArray[0] = "Jan";
    strMonthArray[1] = "Feb";
    strMonthArray[2] = "Mar";
    strMonthArray[3] = "Apr";
    strMonthArray[4] = "May";
    strMonthArray[5] = "Jun";
    strMonthArray[6] = "Jul";
    strMonthArray[7] = "Aug";
    strMonthArray[8] = "Sep";
    strMonthArray[9] = "Oct";
    strMonthArray[10] = "Nov";
    strMonthArray[11] = "Dec";
    strDate = datefield.value;
    if (strDate.length < 1)
    {
        return true;
    }
    for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++)
    {
        if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1)
        {
            strDateArray = strDate.split(strSeparatorArray[intElementNr]);
            if (strDateArray.length != 3)
            {
                err = 1;
                return false;
            }
            else
            {
                strDay = strDateArray[0];
                strMonth = strDateArray[1];
                strYear = strDateArray[2];
            }
            booFound = true;
        }
    }
    if (booFound == false)
    {
        if (strDate.length>5)
        {
            strDay = strDate.substr(0, 2);
            strMonth = strDate.substr(2, 2);
            strYear = strDate.substr(4);
        }
    }
    if (strYear.length == 2)
    {
        strYear = '20' + strYear;
    }
    if (strDatestyle == "US")
    {
        strTemp = strDay;
        strDay = strMonth;
        strMonth = strTemp;
    }
    intday = parseInt(strDay, 10);
    if (isNaN(intday))
    {
        err = 2;
        return false;
    }
    intMonth = parseInt(strMonth, 10);
    if (isNaN(intMonth))
    {
        for (i = 0;i<12;i++)
        {
            if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase())
            {
                intMonth = i+1;
                strMonth = strMonthArray[i];
                i = 12;
            }
        }
        if (isNaN(intMonth))
        {
            err = 3;
            return false;
        }
    }
    intYear = parseInt(strYear, 10);
    if (isNaN(intYear))
    {
        err = 4;
        return false;
    }
    if (intMonth>12 || intMonth<1)
    {
        err = 5;
        return false;
    }
    if ((intMonth == 1
         || intMonth == 3
         || intMonth == 5
         || intMonth == 7
         || intMonth == 8
         || intMonth == 10
         || intMonth == 12) && (intday > 31 || intday < 1))
    {
        err = 6;
        return false;
    }
    if ((intMonth == 4
         || intMonth == 6
         || intMonth == 9
         || intMonth == 11) && (intday > 30 || intday < 1))
    {
        err = 7;
        return false;
    }
    if (intMonth == 2)
    {
        if (intday < 1)
        {
            err = 8;
            return false;
        }
        if (LeapYear(intYear) == true)
        {
            if (intday > 29)
            {
                err = 9;
                return false;
            }
        }
        else
        {
            if (intday > 28)
            {
                err = 10;
                return false;
            }
        }
    }
    var now = new Date();
    var year  = now.getYear()
    if ( year < 2000)
    {
        year = year + 1900;
    }
    var day  = now.getDate();
    var month  = now.getMonth() + 1;
    if (  strYear > year  )
    {
        return false;
    }
    if ( strYear == year )
    {
        if ( strMonth > month )
        {
            return false;
        }
        if ( strMonth == month )
        {
            if ( strDay > day )
            {
                return false;
            }
        }
    }
    return true;
}
function LeapYear(intYear)
{
    if (intYear % 100 == 0)
    {
        if (intYear % 400 == 0)
        {
            return true;
        }
    }
    else
    {
        if ((intYear % 4) == 0)
        {
            return true;
        }
    }
    return false;
}
function checkexpirydate(objName)
{
    var datefield1 = objName;
    if (chkexpdate(objName) == false)
    {
        return false;
    }
    else
    {
        return true;
    }
}
function chkexpdate(objName)
{
    var strDatestyle1 = "US";
    var strDate1;
    var strDateArray1;
    var strDay1;
    var strMonth1;
    var strYear1;
    var intday1;
    var intMonth1;
    var intYear1;
    var booFound1 = false;
    var datefield1 = objName;
    var strSeparatorArray1 = new Array("-"," ","/",".");
    var intElementNr1;
    var err1 = 0;
    var strMonthArray1 = new Array(12);
    strMonthArray1[0] = "Jan";
    strMonthArray1[1] = "Feb";
    strMonthArray1[2] = "Mar";
    strMonthArray1[3] = "Apr";
    strMonthArray1[4] = "May";
    strMonthArray1[5] = "Jun";
    strMonthArray1[6] = "Jul";
    strMonthArray1[7] = "Aug";
    strMonthArray1[8] = "Sep";
    strMonthArray1[9] = "Oct";
    strMonthArray1[10] = "Nov";
    strMonthArray1[11] = "Dec";
    strDate1 = datefield1.value;
    if (strDate1.length < 1)
    {
        return true;
    }
    for (intElementNr1 = 0; intElementNr1 < strSeparatorArray1.length; intElementNr1++)
    {
        if (strDate1.indexOf(strSeparatorArray1[intElementNr1]) != -1)
        {
            strDateArray1 = strDate1.split(strSeparatorArray1[intElementNr1]);
            if (strDateArray1.length != 3)
            {
                err1 = 1;
                return false;
            }
            else
            {
                strDay1 = strDateArray1[0];
                strMonth1 = strDateArray1[1];
                strYear1 = strDateArray1[2];
            }
            booFound1 = true;
        }
    }
    if (booFound1 == false)
    {
        if (strDate1.length>5)
        {
            strDay1 = strDate1.substr(0, 2);
            strMonth1 = strDate1.substr(2, 2);
            strYear1 = strDate1.substr(4);
        }
    }
    if (strYear1.length == 2)
    {
        strYear1 = '20' + strYear1;
    }
    if (strDatestyle1 == "US")
    {
        strTemp1 = strDay1;
        strDay1 = strMonth1;
        strMonth1 = strTemp1;
    }
    intday1 = parseInt(strDay1, 10);
    if (isNaN(intday1))
    {
        err = 2;
        return false;
    }
    intMonth1 = parseInt(strMonth1, 10);
    if (isNaN(intMonth1))
    {
        for (i = 0;i<12;i++)
        {
            if (strMonth1.toUpperCase() == strMonthArray1[i].toUpperCase())
            {
                intMonth1 = i+1;
                strMonth1 = strMonthArray1[i];
                i = 12;
            }
        }
        if (isNaN(intMonth1))
        {
            err1 = 3;
            return false;
        }
    }
    intYear1 = parseInt(strYear1, 10);
    if (isNaN(intYear1))
    {
        err1 = 4;
        return false;
    }
    if (intMonth1>12 || intMonth1<1)
    {
        err1 = 5;
        return false;
    }
    if ((intMonth1 == 1
         || intMonth1 == 3
         || intMonth1 == 5
         || intMonth1 == 7
         || intMonth1 == 8
         || intMonth1 == 10
         || intMonth1 == 12) && (intday1 > 31 || intday1 < 1))
    {
        err1 = 6;
        return false;
    }
    if ((intMonth1 == 4
         || intMonth1 == 6
         || intMonth1 == 9
         || intMonth1 == 11) && (intday1 > 30 || intday1 < 1))
    {
        err1 = 7;
        return false;
    }
    if (intMonth1 == 2)
    {
        if (intday1 < 1)
        {
            err1 = 8;
            return false;
        }
        if (LeapYear(intYear1) == true)
        {
            if (intday1 > 29)
            {
                err1 = 9;
                return false;
            }
        }
        else
        {
            if (intday1 > 28)
            {
                err1 = 10;
                return false;
            }
        }
    }
}

function assignPaper(paper)
{
window.document.receiving.Paper.value =paper ;
}

function doRecPopup(entered,ContainBin)
{
    var testurl3 = "/tcmIS/Hub/Receiving?session=Active&generate_labels=yes&UserAction=GenerateLabels";
    var paperSize1 =  window.document.receiving.Paper.value;
    testurl3 = testurl3 + "&paperSize=" + paperSize1 ;
    testurl3 = testurl3 + "&contBin=" + ContainBin ;

	 var hubnamenum  =  window.document.receiving.HubNoforlabel.value;
    testurl3 = testurl3 + "&HubNoforlabel=" + hubnamenum ;

    openWinGeneric(testurl3,"Generate_labels1","610","600","yes")
}

function doUnConfPopup()
{
  var testbin;
  eval( testbin =  "window.document.receiving.HubName");
  var cur = null;
  eval( cur = (eval(testbin.toString())).selectedIndex );
  var curval = null;
  ( curval =  (eval(testbin.toString())).options[cur].value );
  //alert(curval);
  var unconfURL = "/tcmIS/Hub/ShowUnconfirmedReceipts?session=Active";
  unconfURL = unconfURL + "&HubNo=" + curval;

  var invengrp  =  document.getElementById("invengrp");
  if (invengrp.value != "All")
  {
  unconfURL = unconfURL + "&inventoryGroup=" + invengrp.value;
  }

  var category = document.getElementById("Category");
  unconfURL = unconfURL + "&genLabels=" + category.value;

  openWinGeneric(unconfURL,"Generate_UNCONF_labels","640","600","yes")
}

function doPopup()
{
    var testurl3 = "/tcmIS/Hub/ShowReceivedReceipts?session=Active&generate_labels=yes&UserAction=GenerateLabels";
    var paperSize  =  window.document.receiving.GCategory.value;
    testurl3 = testurl3 + "&genLabels=" + paperSize ;

   var testbin;
   eval( testbin =  "window.document.receiving.HubName");
   var cur = null;
   eval( cur = (eval(testbin.toString())).selectedIndex );
   var curval = null;
   ( curval =  (eval(testbin.toString())).options[cur].value );
	testurl3 = testurl3 + "&HubNoforlabel=" + curval ;

    openWinGeneric(testurl3,"Generate_labels","640","600","yes")
}

function doQCPopup()
{
    var testurl4 = "/tcmIS/Hub/ReceivingQC?session=Active&generate_labels=yes&UserAction=GenerateLabels";
    var paperSize  =  window.document.receiving.paperSize1.value;
    //alert(paperSize);
    testurl4 = testurl4 + "&paperSize=" + paperSize ;

	var testbin;
   eval( testbin =  "window.document.receiving.HubName");
   var cur = null;
   eval( cur = (eval(testbin.toString())).selectedIndex );
   var curval = null;
   ( curval =  (eval(testbin.toString())).options[cur].value );
	testurl4 = testurl4 + "&HubNoforlabel=" + curval ;

   var printKitLabels  =  document.getElementById("printKitLabels");
   if (printKitLabels.checked)
   {
   testurl4 = testurl4 + "&printKitLabels=" +printKitLabels.value;
   }

   openWinGeneric(testurl4,"Generate_labels","600","600","yes")
}

function doInvenPopup()
{
    var testurl5 = "/tcmIS/Hub/Logistics?session=Active&generate_labels=yes&UserAction=GenerateLabels";
    var paperSize  =  window.document.receiving.Paper.value;
    //alert(paperSize);
    testurl4 = testurl5 + "&paperSize=" + paperSize ;

	var testbin;
   eval( testbin =  "window.document.receiving.HubName");
   var cur = null;
   eval( cur = (eval(testbin.toString())).selectedIndex );
   var curval = null;
   ( curval =  (eval(testbin.toString())).options[cur].value );
	testurl4 = testurl4 + "&HubNoforlabel=" + curval ;


	var printKitLabels  =  document.getElementById("printKitLabels");
	if (printKitLabels.checked)
	{
   testurl4 = testurl4 + "&printKitLabels=" +printKitLabels.value;
   }

   openWinGeneric(testurl4,"Generate_labels","600","600","yes")
}

function sendbin(name,entered)
{
    var itemID  =  window.document.EmptyBins.ItemID.value;
    var lineNum  =  window.document.EmptyBins.LineNum.value;

    var testbin;
    eval( testbin =  "window.document.EmptyBins.EmptyBin");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );

    opener.document.receiving.UserAction.value = "UPDATE";
    opener.document.receiving.SubUserAction.value = "AddBin";
    opener.document.receiving.AddBin_Item_Id.value = itemID.toString();
    opener.document.receiving.AddBin_Bin_Id.value = curval.toString();
    opener.document.receiving.DuplLineNumber.value = "NA"  ;
    opener.document.receiving.AddBin_Line_No.value = lineNum.toString();

    opener.document.receiving.submit();
    window.close();
}

function cancel()
{
    window.close();
}

function validateForm() {
   var result = true;
   var errmsg = "";
   
   if (document.getElementById("room").value.length == 0) {
	   errmsg += 'Room required.\n';
   }
   
   if (document.getElementById("binType").value.length == 0) {
	   errmsg += 'Bin Type required.\n';
   }
   
   if (errmsg.length > 0) {
	   alert(errmsg);
	   result = false;
   }
   return result;
}