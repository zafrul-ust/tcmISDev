var children = new Array(); 

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateSearchCriteriaInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(isValidSearchForm) {
   $("uAction").value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchCriteriaInput()
{
	var errorMessage = "";
	var errorCount = 0;
	
	var searchField = $v("searchField");
	var searchArgument = $v("searchArgument");
	if (searchField == 'facilityId' && !isFloat(searchArgument,true))
	{  
	   	   errorMessage = messagesData.validvalues + " " + messagesData.mr+"\n\n";
	   	   $("searchArgument").value = "";
		   errorCount = 1;
	}

	if (errorCount >0)
	{
	 alert(errorMessage);
	 return false;
	}
	
	return true;
}

function createXSL(){
	$('uAction').value = 'createExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_EdiOrderStatusExcel','650','600','yes');
	document.genericForm.target='_EdiOrderStatusExcel';
	var a = window.setTimeout("document.genericForm.submit();",500);
}

function closeAllchildren()
{
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
}

/*
function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 180);
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
}  */








