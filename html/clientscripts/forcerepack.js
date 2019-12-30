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

function submitmainpage()
{
  opener.search();
  opener.document.receiving.submit();
  window.close();
}

function closeingwindow()
{
	alert("Window is being cloesd");
}

function checkValues(name,delname)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

	 var pickchekbox = document.getElementById(""+name.toString()+"");
	 var originalcheckbox = pickchekbox.value;

    if ( pickchekbox.value == "no")
    {
	      pickchekbox.value = "yes";
         pickchekbox.checked = true;
         result = true;
    }
    else if ( pickchekbox.value == "yes")
    {
        pickchekbox.value = "no";
        pickchekbox.checked = false;
        result = true;
        updatecount--;
    }

    finalMsgt = "Please enter valid values for: \n\n";

    eval( testqty = "window.document.forcerepack.qty_picked" + delname.toString() )
    var v = (eval(testqty.toString())).value;
    if ( v*1 < 0 || v.length == 0 )
    {
        finalMsgt = finalMsgt + " Qty. \n";
        allClear = 1;
        testqty12  =  eval("window.document.forcerepack.qty_picked" + delname.toString() );
        testqty12.value = "";
    }
    else if ( !(isInteger(v)) )
    {
        finalMsgt = finalMsgt + " Qty. \n";
        allClear = 1;
        testqty12  =  eval("window.document.forcerepack.qty_picked" + delname.toString() );
        testqty12.value = "";
    }

	if (allClear < 1)
	{
        result = true;
        if(originalcheckbox == "no") {updatecount++;}
    }
    else
    {
        pickchekbox.value = "no";
        pickchekbox.checked = false;
        alert(finalMsgt);
        result = false;
    }
	 //alert("Update Count   "+updatecount+"");

	 var forceorderbutton = document.getElementById("forceorderbutton");
	 if (updatecount > 0)
	 {
	 	forceorderbutton.style.display="";
	 }
	 else
	 {
		forceorderbutton.style.display="none";
	 }

    return result;
}


function CheckQtyValue(name,entered,delname)
{
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    finalMsgt = "Please enter valid values for: \n\n";

    eval( testqty = "window.document.forcerepack.qty_picked" + delname.toString() )
    var v = (eval(testqty.toString())).value;
    if ( v*1 <= 0 || v.length == 0)
    {
        finalMsgt = finalMsgt + " Quantity Picked. \n";
        allClear = 1;
        testqty12  =  eval("window.document.forcerepack.qty_picked" + delname.toString() );
        testqty12.value = "";
    }
    else if ( !(isInteger(v)) )
    {
        finalMsgt = finalMsgt + " Quantity Picked. \n";
        allClear = 1;
        testqty12  =  eval("window.document.forcerepack.qty_picked" + delname.toString() );
        testqty12.value = "";
    }

	 if (allClear != 0)
	 {
		var pickchekbox;
      eval( pickchekbox  =  "window.document.forcerepack.row_chk" + delname.toString() );
	   eval((eval(pickchekbox.toString())).value  = no.toString() );;
      eval( (eval(pickchekbox.toString())).checked = false );;

		 alert(finalMsgt);
    }
}

function update(entered)
{
    window.document.forcerepack.UserAction.value = "UPDATE";
    window.document.forcerepack.SubUserAction.value = "UpdPage";
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
    window.document.forcerepack.UserAction.value = "Search";
    window.document.forcerepack.SubUserAction.value = "NA";
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