function autoSubmitSearchForm()
{
    /*Make sure to not set the target of the form to anything other than resultFrame*/
    var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
    $('uAction').value = 'search';
    document.genericForm.target='resultFrame';
    document.genericForm.submit();
    showPleaseWait();
}

function submitSearchForm()
{
	
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  if(validateSearchForm()) { 
   $('uAction').value = 'search';
      document.genericForm.target='resultFrame';
   
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchForm() {
	var searchField = document.getElementById("searchField").value;
	var searchText = document.getElementById("searchArgument").value;
	if(searchText != ''){
		if(searchField == 'requestId')
			{
			 if (!isInteger(searchText.trim())) {
			    messagesData.validvalues;
			    return false;
			 }
		}
	}
	else if ($v("task").length == 0 && $v("status").length == 0 && $v("assignedTo").length == 0) {
		alert("Please enter Search Text or select from Status, Task, and/or Assignee.");
		return false;
	}
	return true;
}

function createXSL(){
	  var flag = true;//validateForm();
	  if(flag) {
		$('uAction').value = 'createExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Catalog_AddQc_Excel','650','600','yes');
	    document.genericForm.target='_Catalog_AddQc_Excel';
	    var a = window.setTimeout("document.genericForm.submit();",50);
	  }
	}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 64);
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



