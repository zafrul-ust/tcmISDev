var children = new Array();

function attachHubChange() {
	defaults.hub.callback = refreshHubName;
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateSearchCriteriaInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if(isValidSearchForm) {
   var userAction = document.getElementById("uAction");
   userAction.value = 'searchSalesOrder';
   document.salesordersearch.target='resultFrame';
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
	var errorMessage = messagesData.validvalues + "\n\n";
	var errorCount = 0;
	
	var searchField = $v("searchField");
	var searchArgument = $v("searchArgument");
	if (searchField == 'prNumber' && !isFloat(searchArgument,true))
	{  
	   	   errorMessage = errorMessage +"\n"+ messagesData.mr;
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

function lookupCustomer() {
  loc = "../distribution/customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
  openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}



function lookupPerson()
{
	 loc = "/tcmIS/haas/searchpersonnelmain.do?fixedCompanyId=Y&displayArea=personnelName&valueElementId=personnelId";
	 openWinGeneric(loc,"PersonnelId","650","455","yes","50","50");
}


function clearCustomer()
{
    document.getElementById("customerName").value = "";
    document.getElementById("customerName").setAttribute("className", "inputBox");
    document.getElementById("customerId").value = "";
}


function clearRequestor()
{
    document.getElementById("personnelName").value = "";
    document.getElementById("personnelName").setAttribute("className", "inputBox");
    document.getElementById("personnelId").value = "";
}

function customerChanged(id, name) {
	document.getElementById("customerName").className = "inputBox";
}

function personnelChanged(id, name) {
	document.getElementById("personnelName").className = "inputBox";
}

function getOrderStatusList() {
	var orderStatusListString = "";
	for (i = 1; i < $("orderStatus").length; i++) {
		if ($("orderStatus").options[i].selected) {
			if (orderStatusListString == "")
				orderStatusListString = $("orderStatus").options[i].value;
			else 
				orderStatusListString += ", "+$("orderStatus").options[i].value;
		} 
	}
	$("orderStatusList").value = orderStatusListString;
}

function createXSL(){
	if( !validateSearchCriteriaInput() ) return;
	
	$('uAction').value = 'createExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_SalesOrdersReportExcel','650','600','yes');
	document.salesordersearch.target='_SalesOrdersReportExcel';
	var a = window.setTimeout("document.salesordersearch.submit();",500);
	
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