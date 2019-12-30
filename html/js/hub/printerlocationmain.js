function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 document.getElementById("action").value = 'search';
 //set start search time
 var now = new Date();
 var startSearchTime = document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var flag = validateForm();
 if(flag) {
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function refreshPage()
{	
	document.genericForm.target='';
	var action = document.getElementById("action");
	action.value="search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	document.genericForm.submit();
	showPleaseWait();
}

function validateForm(){
	var errorMessage = "";
	var errorCount = 0;

	try
	{
	 var searchInput = document.getElementById("searchArgument");

//	 if ((searchField.value.trim().length != 0 ) && (  (searchField.value == "itemId") || (searchField.value == "radianPo")      ) )
//	 {
//	    if((searchInput.value.trim().length != 0) && (!(isPositiveInteger(searchInput.value.trim())) || (searchInput.value*1 == 0 )  ))
//	    {
//	    errorMessage +=  messagesData.searchInput + "\n";
//	    errorCount = 1;
//	    searchInput.value = "";
//	    }
//    }

	} catch (ex) {
	  errorCount++;
	}
	if (errorCount >0)
	 {
	    alert(errorMessage);
	    return false;
	 }
    return true;
}

function submitCreateExcel()
{
  if( validateForm() ) {
      document.getElementById("action").value = "createExcel"; 
		document.genericForm.target='_PrinterLocationExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_PrinterLocationExcel','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	}
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 300, 120);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}

function addNewPrinterLoc()
{
	loc = "addnewprinterloc.do?uAction=new" ;
	 openWinGeneric(loc,"_addnewprinterloc","600","500","yes","50","50","no")
}




