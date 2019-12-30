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

function assignPaper(paper)
{
window.document.dropshipreceiving.Paper.value =paper ;
}

function doRecPopup(entered,ContainBin)
{
    var testurl3 = "/tcmIS/miller/Dropship?session=Active&generate_labels=yes&UserAction=GenerateLabels";
    var paperSize1 =  window.document.dropshipreceiving.Paper.value;
    testurl3 = testurl3 + "&paperSize=" + paperSize1 ;
    testurl3 = testurl3 + "&contBin=" + ContainBin ;

    var hubnamenum  =  window.document.dropshipreceiving.HubNoforlabel.value;
    testurl3 = testurl3 + "&HubNoforlabel=" + hubnamenum ;

    openWinGeneric(testurl3,"Generate_labels1","610","600","yes")
}

function showChemicalReceivedReceipts(receivedReceipts)
{
 receivedReceipts = receivedReceipts.replace(/,/gi, "%2c");
 var loc = "/tcmIS/hub/showdropshipreceivedreceipts.do?receivedReceipts="+receivedReceipts+"";
 /*var sourceHub = document.getElementById("sourceHub");
 var inventoryGroup = document.getElementById("inventoryGroup");
 loc = loc + "&sourceHub="+sourceHub.value+"&inventoryGroup="+inventoryGroup.value+"";*/
 openWinGeneric(loc,"showdropshipreceivedreceipts11","800","500","yes","80","80");
}

function showrecrecipts()
{
    var testurl3 = "/tcmIS/Hub/ShowReceivedReceipts?session=Active&generate_labels=yes&showissuedrcts=Yes&UserAction=GenerateLabels";
    var paperSize  =  "2";
    testurl3 = testurl3 + "&genLabels=" + paperSize ;

   /*var testbin;
   eval( testbin =  "window.document.receiving.HubName");
   var cur = null;
   eval( cur = (eval(testbin.toString())).selectedIndex );
   var curval = null;
   ( curval =  (eval(testbin.toString())).options[cur].value );
	testurl3 = testurl3 + "&HubNoforlabel=" + curval ;*/

    openWinGeneric(testurl3,"Generate_labels","640","600","yes")
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
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
    var total  =  window.document.dropshipreceiving.Total_number.value;
    for ( var p = 0 ; p < total ; p ++)
    {
        var line_num ;
        eval ( line_num = p + 1 );
        if ( button_id == ("Button"+line_num.toString()) )
        {
            window.document.dropshipreceiving.UserAction.value = "UPDATE";
            window.document.dropshipreceiving.SubUserAction.value = "DuplLine" ;
            window.document.dropshipreceiving.DuplLineNumber.value = line_num.toString() ;
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

function update(entered)
{
    window.document.dropshipreceiving.UserAction.value = "UPDATE";
    window.document.dropshipreceiving.SubUserAction.value = "UpdPage";
    window.document.dropshipreceiving.DuplLineNumber.value = "NA"  ;
    window.document.dropshipreceiving.AddBin_Item_Id.value = "NA" ;
    window.document.dropshipreceiving.AddBin_Bin_Id.value = "NA" ;
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
var result = true;
var searchlike = document.getElementById("searchlike").value;
var searchText = document.getElementById("SearchField").value;

if ( searchText.length > 0 && (searchlike == "a.RADIAN_PO" || searchlike == "a.MR_NUMBER" || searchlike == "a.ITEM_ID") )
{
	try
   {
     var searchTextNumber = searchText*1;
     if (searchTextNumber%1 != 0)
     {
      result = false;
     }
   }
   catch (ex)
   {
    result = false;
    alert("Please Enter a Valid Numeric Value.");
   }
}

	if (result)
	{
    window.document.dropshipreceiving.UserAction.value = "Search";
    window.document.dropshipreceiving.SubUserAction.value = "NA";
    window.document.dropshipreceiving.DuplLineNumber.value = "NA"  ;
    window.document.dropshipreceiving.AddBin_Item_Id.value = "NA" ;
    window.document.dropshipreceiving.AddBin_Bin_Id.value = "NA" ;
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
    alert("Please Enter a Valid Numeric Value.");
    return false;
   }
}

function checkValues(rowid)
{
    var result ;
    var yes = "yes";
    var no = "no";
    var NONE = "NONE";

    var testdate;
    var testqty;
    var testlot;
    var finalMsgt;
    var allClear = 0;

    var testcheckbox  =  document.getElementById("row_chk"+rowid+"");
    if ( testcheckbox.checked)
    {

    }
    else
    {
        updatecount--;
        testcheckbox.value = "no";
        return;
    }

    finalMsgt = "Please enter valid values for: \n\n";

    var testactualshipdate = document.getElementById("act_suppship_date"+rowid+"");
    if ( testactualshipdate.value.length == 10 )
    {
        if ( checkdate(testactualshipdate) == false )
        {
            finalMsgt = finalMsgt +   " Actual Supplier Ship Date. \n" ;
            allClear = 1;
        }
    }
    else if ( testactualshipdate.value.length > 0 )
    {
        finalMsgt = finalMsgt + " Actual Supplier Ship Date.\n" ;
        allClear = 1;
    }

    var testdatereceipt = document.getElementById("date_recieved"+rowid+"");
    if ( testdatereceipt.value.length == 10 )
    {
        if ( checkdate(testdatereceipt) == false )
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

    var testexpirydate = document.getElementById("expiry_date"+rowid+"");
    if (! (testexpirydate.value == "Indefinite" || testexpirydate.value == "INDEFINITE" || testexpirydate.value == "indefinite") )
    {
        if ( testexpirydate.value.length == 10 )
        {
            if ( checkdate(testexpirydate) == false )
            {
                finalMsgt = finalMsgt +   " Exp Date. \n" ;
                allClear = 1;
            }
        }
        else if ( testexpirydate.value.length > 0 )
        {
            finalMsgt = finalMsgt + " Exp Date. \n" ;
            allClear = 1;
        }
    }

    var testlot = document.getElementById("mfg_lot"+rowid+"");
    if ( testlot.value.length < 1 )
    {
        finalMsgt = finalMsgt + " Mfg Lot #. \n" ;
        allClear = 1;
    }

    var testqty = document.getElementById("qty_recd"+rowid+"");
    if ( !(isInteger(testqty.value)) )
    {
        finalMsgt = finalMsgt + " Quantity Received. \n";
        allClear = 1;
        testqty.value = "";
    }

    if (allClear < 1)
    {
        result = true;
        testcheckbox.value = "yes";
    }
    else
    {
        testcheckbox.checked = false;
        alert(finalMsgt);
        result = false;
    }

if (true == result )
{
    updatecount++;
}
return result;

}


function cancel()
{
window.close();
}