windowCloseOnEsc = true;

function mySearchOnLoad() {

}

function activateTemplate() {
	$("action").value = 'activateTemplate';
	document.genericForm.target='resultFrame';
   showPleaseWait();
	document.genericForm.submit();
}

function inactivateTemplate() {
	$("action").value = 'inactivateTemplate';
	document.genericForm.target='resultFrame';
   showPleaseWait();
	document.genericForm.submit();
}

function deleteTemplate() {
	$("action").value = 'deleteTemplate';
	document.genericForm.target='resultFrame';
   showPleaseWait();
	document.genericForm.submit();
}

function submitSearchForm() {
  var isValidSearchForm = validateForm();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(isValidSearchForm) {
   var userAction = document.getElementById("action");
   userAction.value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }else {
    return false;
  }
}

function validateForm() {
   return true;
} //end of validateForm

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
}

function generateExcel() {
	var flag = validateForm();
	if(flag) {
		var action = document.getElementById("action");
		action.value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','report_template_excel','800','600','yes');
		document.genericForm.target='report_template_excel';
		var a = window.setTimeout("document.genericForm.submit();",500);

	}
}

function checkedAll() {
    for (var i = 0; i < $v("reportTypeCount"); i++) {
        $("reportType"+i).checked = $("allC").checked;
    }
}
