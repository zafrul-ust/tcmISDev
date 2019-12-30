var children = new Array(); 

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateSearchCriteriaInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(isValidSearchForm) {
   var userAction = document.getElementById("uAction");
   userAction.value = 'search';
   document.customerordertracking.target='resultFrame';
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
	if (searchField == 'pr_number' && !isFloat(searchArgument,true))
	{  
	   	   errorMessage = messagesData.validvalues + " " + messagesData.mr+"\n\n";
	   	   $("searchArgument").value = "";
		   errorCount = 1;
	}
	
	var customerIdList = $v("customerIdList");
	if (customerIdList.length == 0)
	{  
	   	   errorMessage = errorMessage + messagesData.selectatleastonecustomer;
		   errorCount = 1;
	}

	if (errorCount >0)
	{
	 alert(errorMessage);
	 return false;
	}
	
	return true;
}

function getCustomerIdList() {
	var customerIdListString = "";
	for (i = 0; i < $("customerId").length; i++) {
		if ($("customerId").options[i].selected) {
			if (customerIdListString == "")
				customerIdListString = $("customerId").options[i].value;
			else 
				customerIdListString += "|"+$("customerId").options[i].value;
		} 
	}
	$("customerIdList").value = customerIdListString;
}

function getOrderStatusListString() {
	var orderStatusListString = "";
	for (i = 1; i < $("orderStatus").length; i++) {
		if ($("orderStatus").options[i].selected) {
			if (orderStatusListString == "")
				orderStatusListString = $("orderStatus").options[i].value;
			else 
				orderStatusListString += "|"+$("orderStatus").options[i].value;
		} 
	}
	$("orderStatusList").value = orderStatusListString;
}

function createXSL(){
	$('uAction').value = 'createExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_SalesOrdersReportExcel','650','600','yes');
	document.customerordertracking.target='_SalesOrdersReportExcel';
	var a = window.setTimeout("document.customerordertracking.submit();",500);
}


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

function closeAllchildren()
{
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
//	} 
}







