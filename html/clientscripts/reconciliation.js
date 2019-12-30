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

//12-17-02
function createxls(entered)
{
    loc = "/tcmIS/Hub/Reconciliation?session=Active&UserAction=generatexls&receiptid=";
    openWinGenericwbar(loc,"Generate_XLS","1100","600","yes");
}

//07-26-02
function donothing(entered)
{
    return false;
}

//07-11-02
function createpdf(entered)
{
  var skipreconhand = document.getElementById("skipreconhand");
  var skipreconhandValue = "";
  if (skipreconhand.checked)
  {
    skipreconhandValue = "Y"    
  }

    loc = "/tcmIS/Hub/Reconciliation?skipreconhand="+skipreconhandValue+"&session=Active&UserAction=generatepdf&receiptid=";    
    openWinGeneric(loc,"Generate_PDF","600","600","yes");
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}


function openWinGenericwbar(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function RefreshPage(entered)
{
    if ( eval(updatecount.toString()) > 0 )
    {
        alert("Please Update data Before Changing");
        return false;
    }
    with (entered)
    {
        ref=document.receiving.HubName.options[selectedIndex].value;
    }
    loc = "/tcmIS/Hub/Reconciliation?session=Active&HubName=";
    loc=loc + ref;
    document.location=loc;
}
function RefreshSearch(entered)
{
    if ( eval(updatecount.toString()) > 0 )
    {
        alert("Please Update data Before Changing");
        return false;
    }
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
    with (entered)
    {
        var testbin;var testbin;
        eval( testbin =  "window.document.receiving.HubName");
        var cur = null;
        eval( cur = (eval(testbin.toString())).selectedIndex );
        var curval = null;
        ( curval =  (eval(testbin.toString())).options[cur].value );
        loc = "/tcmIS/Hub/Reconciliation?session=Active&HubName=";
        loc=loc + curval;
        loc=loc+"&Category=";
        loc=loc+document.receiving.Category.value;
        loc=loc+"&SortBy=";
        loc=loc+document.receiving.SortBy.options[selectedIndex].value;
        loc=loc+"&UserAction=NEW";
    }
    if (submitcount == 0)
    {
        submitcount++;
        document.location=loc;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
    }
}
function OldSearch(entered)
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
    window.document.receiving.SubUserAction.value = "NA";
    window.document.receiving.CancelUserAction.value = "Yes";
    return true;
}
function updateIssue(entered)
{
    //if ( eval(updatecount.toString()) < 1 )
    //{
    //    alert("Please enter data and select Ok before Update");
    //    return false;
    //}

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
    window.document.receiving.SubUserAction.value = "IssueUpdPage";
    return true;
}
function UpdatenewPick(name,entered,linenum)
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
    window.document.receiving.SubUserAction.value = "updateNewPick";
    window.document.receiving.NewPickLine.value = linenum.toString();
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
function CheckBinValue(name,entered)
{
    eval( testqty = "window.document.receiving." + name.toString() )
    var v = (eval(testqty.toString())).value;
    if ( !(isInteger(v)) )
    {
        alert ( "Invalid Number\n Please Enter a Valid Number.");
        testqty12  =  eval("window.document.receiving." + name.toString() );
        testqty12.value = "";
    }
    else
    {
        updatecount++;
    }
}
function CheckValue(name ,entered,ItemIDLineNum)
{
    eval( testqty = "window.document.receiving." + name.toString() )
    var v = (eval(testqty.toString())).value;
    if ( !(isInteger(v)) )
    {
        alert ( "Invalid Number\n Please Enter a Valid Number.");
        testqty12  =  eval("window.document.receiving." + name.toString() );
        testqty12.value = "";
    }
    else
    {
        updatecount++;
    }
    eval( itemIdO = "window.document.receiving." + ItemIDLineNum.toString() )
    var itemId = (eval(itemIdO.toString())).value;
    eval( StartlineO = "window.document.receiving.Start" + itemId.toString() )
    var startline = (eval(StartlineO.toString())).value;
    eval( StoplineO = "window.document.receiving.Stop" + itemId.toString() )
    var stopline = (eval(StoplineO.toString())).value;
    var Total = 0*1;
    for ( var p = startline ; p <= stopline ; p ++)
    {
        eval( actOnHandO = "window.document.receiving.act_onhand" + p.toString() )
        var actOnHand = (eval(actOnHandO.toString())).value;
        actvalue = actOnHand*1;
        eval (Total = Total + actvalue);
    }
    row_name = eval("row" + itemId);
    row_name.cells(2).innerHTML = "<B>"+Total+"</B>";
}
function CheckIssueValue(name ,entered,number)
{
    eval( testqty = "window.document.receiving." + name.toString() )
    var v = (eval(testqty.toString())).value;
    eval( MrQtyO = "window.document.receiving.MrQuantity" + number.toString() )
    var MrQty = (eval(MrQtyO.toString())).value;
    MrQty = MrQty*1;
    var allCrear = 0 ;
    var MRRevITotal = 0*1;
    eval( receiptIdo = "window.document.receiving.receipt_id" + number.toString() )
    var receiptId = (eval(receiptIdo.toString())).value;
    RecMinConf = eval("RecMinConfQty.x" + receiptId.toString());
    RecMinConf = RecMinConf*1;
    recIssue = eval("RecIssue.x" + number.toString());
    recIssue = recIssue*1;
    if ( !(isInteger(v) ) )
    {
        alert ( "Invalid Number\n Please Enter a Valid Number.");
        testqty12  =  eval("window.document.receiving." + name.toString() );
        testqty12.value = recIssue;
        v=0;
        allCrear = 1;
    }
    if ( v*1 >  MrQty )
    {
        alert ( "Invalid Qty, Total Issued Greater than MR Quantity.");
        testqty12  =  eval("window.document.receiving." + name.toString() );
        testqty12.value = recIssue;
        if ( recIssue == MrQty )
        {
            disablePickO  =  eval("window.document.receiving.newPick" + number.toString() );
            disablePickO.disabled = true;
        }
        v=0;
        allCrear = 1;
    }
    else if ( v == MrQty )
    {
        disablePickO  =  eval("window.document.receiving.newPick" + number.toString() );
        disablePickO.disabled = true;
    }
    else
    {
        disablePickO  =  eval("window.document.receiving.newPick" + number.toString() );
        disablePickO.disabled = false;
    }
    eval( prnumberO = "window.document.receiving.prnumber" + number.toString() )
    var prnumber = (eval(prnumberO.toString())).value;
    v = v*1;
    var total1  =  window.document.receiving.Total_number.value;
    var Total = 0*1;
    var UnConfirmedRecTotal = 0*1;
    for ( var p = 0 ; p < total1 ; p ++)
    {
        eval ( line_num = p + 1 );
        eval( actOnHandO = "window.document.receiving.rev_issue" + line_num.toString() )
        var actOnHand = (eval(actOnHandO.toString())).value;
        actvalue = actOnHand*1;
        eval( AllreceiptIdo = "window.document.receiving.receipt_id" + line_num.toString() )
        var AllreceiptId = (eval(AllreceiptIdo.toString())).value;
        eval( ConfirmedO = "window.document.receiving.confirmed" + line_num.toString() )
        var Confirmed = (eval(ConfirmedO.toString())).value;
        if ( AllreceiptId == receiptId )
        {
            if ( Confirmed == "N" )
            {
                eval (UnConfirmedRecTotal = UnConfirmedRecTotal + actvalue);
            }
        }
    }
    if ( RecMinConf >= UnConfirmedRecTotal )
    {
    }
    else
    {
        alert ( "Invalid Qty\n Please Enter a Qty Less Than the received Qty Minus Confirmed Issues.");
        testqty12  =  eval("window.document.receiving." + name.toString() );
        testqty12.value = recIssue;
        v=0;
        allCrear = 1;
    }
    eval( StartlineO = "window.document.receiving.Start" + prnumber.toString() )
    var startline = (eval(StartlineO.toString())).value;
    eval( StoplineO = "window.document.receiving.Stop" + prnumber.toString() )
    var stopline = (eval(StoplineO.toString())).value;
    for ( var p = startline ; p <= stopline ; p ++)
    {
        eval( actOnHandO = "window.document.receiving.rev_issue" + p.toString() )
        var actOnHand = (eval(actOnHandO.toString())).value;
        actOnHand = actOnHand*1;
        eval (MRRevITotal = MRRevITotal + actOnHand);
    }
    if ( MrQty >= MRRevITotal)
    {
        updatecount++;
    }
    else
    {
        alert ( "Invalid Qty\n Please Enter a Qty Less Than the received Qty.");
        testqty12  =  eval("window.document.receiving." + name.toString() );
        testqty12.value = recIssue;
        disablePickO  =  eval("window.document.receiving.newPick" + number.toString() );
        disablePickO.disabled = true;
        v=0;
        allCrear = 1;
    }
    if ( ( v == 0) && !(allCrear == 0 ) )
    {
        mainFormvalue111  =  eval("window.document.receiving." + name.toString() );
        mainFormvalue111.value = recIssue;
    }
    v = v*1;
    revonHand = eval("RevOnhand.x" + receiptId.toString());
    revonHand = revonHand*1;
    var total1  =  window.document.receiving.Total_number.value;
    var Total = 0*1;
    var ReceiptTotal = 0*1;
    var OrigReceiptTotal = 0*1;
    var UnConfirmedRecTotal = 0*1;
    for ( var p = 0 ; p < total1 ; p ++)
    {
        eval ( line_num = p + 1 );
        eval( actOnHandO = "window.document.receiving.rev_issue" + line_num.toString() )
        var actOnHand = (eval(actOnHandO.toString())).value;
        actvalue = actOnHand*1;
        eval (Total = Total + actvalue);
        recIssue = eval("RecIssue.x" + line_num.toString());
        recIssue = recIssue*1;
        eval( AllreceiptIdo = "window.document.receiving.receipt_id" + line_num.toString() )
        var AllreceiptId = (eval(AllreceiptIdo.toString())).value;
        if ( AllreceiptId == receiptId )
        {
            eval (ReceiptTotal = ReceiptTotal + actvalue);
            eval (OrigReceiptTotal = OrigReceiptTotal + recIssue);
        }
    }
    row_name = eval("Totalsrow");
    row_name.cells(2).innerHTML = "<center><font face=Arial SIZE=2><B>"+Total+"</B></FONT><center>";
    revonHand = revonHand + (OrigReceiptTotal-ReceiptTotal);
    row_name = eval("x"+receiptId);
    row_name.cells(1).innerHTML = "<center><font face=Arial SIZE=2>"+revonHand+"</FONT><center>";
    var recOnTotal = 0*1;
    eval( revReceiptonHandO = "window.document.receiving.receipt_id" + receiptId.toString() )
    var revReceiptonHand = (eval(revReceiptonHandO.toString())).value;
    RevOnHandTotal = RevOnHandTotal*1;
    recOnTotal = RevOnHandTotal +(revonHand - revReceiptonHand*1);
    row_name = eval("ReceiptTotalsrow");
    row_name.cells(1).innerHTML = "<center><font face=Arial SIZE=2><B>"+recOnTotal+"</B></FONT><center>";
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
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "UpdPage";
    return true;
}
function updateItem(name,entered,ItemIDLineNum)
{
    eval( itemIdO = "window.document.receiving." + ItemIDLineNum.toString() )
    var itemId = (eval(itemIdO.toString())).value;
    eval( StartlineO = "window.document.receiving.Start" + itemId.toString() )
    var startline = (eval(StartlineO.toString())).value;
    eval( StoplineO = "window.document.receiving.Stop" + itemId.toString() )
    var stopline = (eval(StoplineO.toString())).value;
    var allClear = 0;
    for ( var p = startline ; p <= stopline ; p ++)
    {
        eval( actOnHandO = "window.document.receiving.act_onhand" + p.toString() )
        var actOnHand = (eval(actOnHandO.toString())).value;
        actOnHand = actOnHand*1;
        if ( (eval(actOnHandO.toString())).value.length > 0 )
        {
            if ( !(isInteger(actOnHand)) )
            {
                allClear = 1;
            }
        }
        else
        {
            allClear = 1;
        }
    }
    if (allClear < 1)
    {
    }
    else
    {
        alert("Please Enter All the values for Actual On Hand");
        return false;
    }
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
    var button_id = name.toString();
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "UpdPage";
    window.document.receiving.RedirectItemValue.value = button_id.toString() ;
    window.document.receiving.toRedirectItem.value = "Yes";
    return true;
}
function updateBinItem(name,entered)
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
    var button_id = name.toString();
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "UpdPage";
    window.document.receiving.RedirectItemValue.value = button_id.toString() ;
    window.document.receiving.toRedirectItem.value = "Yes";
    return true;
}
function search(entered)
{
    if ( eval(updatecount.toString()) > 0 )
    {
        alert("Please Update data Before Changing");
        return false;
    }
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
    window.document.receiving.SubUserAction.value = "NA";
    return true;
}
function StartNew(entered)
{
    window.document.receiving.UserAction.value = "NEW";
    window.document.receiving.SubUserAction.value = "STARTNEW";
    return true;
}