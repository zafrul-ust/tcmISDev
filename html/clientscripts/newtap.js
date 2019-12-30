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

function printtaplabelandclose()
{
  loc = "/tcmIS/hub/startnewtap?session=Active&generate_labels=yes&UserAction=GenerateLabels";
  openWinGeneric(loc,"printstartnewtap","600","520","yes")

	submitmainpage();
}

function submitmainpage()
{
  opener.search();
  opener.document.receiving.submit();
  window.close();
}

function showEmtyBins(itemID,LineNo,Hub)
{
    loc = "/tcmIS/servlet/radian.web.servlets.internal.InternalShowEmptyBins?itemID=";
    loc = loc + itemID;
    loc = loc + "&HubNo=" + Hub;
    loc = loc + "&LineNo=" + LineNo;
    openWinGeneric(loc,"Show_Empty_Bins","300","150","no")
}

function checkValues(name ,delname)
{
	 var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

	 var pickchekbox = document.getElementById("row_chk"+delname.toString()+"");
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
        window.document.receiving.updatecount.value = updatecount;
    }


		finalMsgt ="Please enter valid values for: \n\n";

      var testbin = document.getElementById("selectElementBin"+delname.toString()+"");
      if ( testbin.value.length == 0)
      {
          finalMsgt = finalMsgt +   " BIN. \n" ;
          allClear = 1;
      }

	if (allClear < 1)
	{
        result = true;
        if(originalcheckbox == "no")
        {
   	    updatecount++;
   	    window.document.receiving.updatecount.value = updatecount;
   	    if (updatecount > 1)
   	    {
				alert("Please start one tap at a time.");
				updatecount--;
				window.document.receiving.updatecount.value = updatecount;
				pickchekbox.value = "no";
	         pickchekbox.checked = false;
				result = false;
   	    }
        }
    }
    else
    {
        pickchekbox.value = "no";
        pickchekbox.checked = false;
        alert(finalMsgt);
        result = false;
    }
	 //alert("Update Count   "+updatecount+"");

	 var startnewtap = document.getElementById("startnewtap");
	 if (updatecount > 0)
	 {
	 	startnewtap.style.display="";
	 }
	 else
	 {
		startnewtap.style.display="none";
	 }

    return result;
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