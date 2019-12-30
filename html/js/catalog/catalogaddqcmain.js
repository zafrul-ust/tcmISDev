function autoSubmitSearchForm()
{

 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  if(validateSearchForm()) {
    $('uAction').value = 'search';
    document.genericForm.target='resultFrame';
    document.genericForm.submit();
    showPleaseWait();
   }
}

function submitSearchForm()
{
	
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  if(validateSearchForm()) { 
	  toggleLinks();
   $('uAction').value = 'search';
      document.genericForm.target='resultFrame';
   
   showPleaseWait();
   $("qcChecked").style.display = "none";
   return true;
  }
  else
  {
    return false;
  }
}

function toggleLinks() {
	var cataddStatus = $("status").value;
	var savePendingAssign = $("savePendingAssign");
	var approvePendingAssign = $("approvePendingAssign");
	var approveRetainLink = $("approveAndRetain");
	var rejectLink = $("rejectRequest");
	if ("Pending Assignment" == cataddStatus) {
		savePendingAssign.style.display = "";
		approvePendingAssign.style.display = "";
		approveRetainLink.style.display = "";
		rejectLink.style.display = "";
	}
	else {
		savePendingAssign.style.display = "none";
		approvePendingAssign.style.display = "none";
		approveRetainLink.style.display = "none";
		rejectLink.style.display = "none";
	}
}

function validateSearchForm() {
	var searchField = document.getElementById("searchField").value;
	var searchArgument = document.getElementById("searchArgument").value;
	if(searchArgument != ''){
		if(searchField == 'requestId')
			{
			 if (!isInteger(searchArgument.trim())) {
			    messagesData.validvalues;
			    return false;
			 }
		}
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


function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchMode');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if(selectedValue == 'requestId' && searchMyArr[i].value == 'contains')
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
		    if ( selectedValue == 'manufacturer' && searchMyArr[i].value == 'is')
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
}

function statusChanged(status) {
	if ( ! status) {
		status = $("status").value;
	}
	var nonStatusSearchFields = [
	        "companyId",
	        "catalogId",
	        "assignedTo",
	        "sortBy",
	        "taskStatus",
	        "supplier",
	        "searchField",
	        "searchMode",
	        "searchArgument",
	        "excludeMerckRequest",
	        "historicalSearch"
	];
	
	var pendAssignStatus = status == "Pending Assignment";
	
	var workQueueStatuses = status == "Pending SDS Sourcing"
			|| status == "Pending SDS Indexing"
			|| status == "Pending Item Creation";
	
	for (var i in nonStatusSearchFields) {
		var fieldId = nonStatusSearchFields[i];
		if ( ! workQueueStatuses && (fieldId == "taskStatus" || fieldId == "supplier"
				|| fieldId == "sortBy" || fieldId == "historicalSearch")) {
			$(fieldId).disabled = ! workQueueStatuses;
		}
		else if(pendAssignStatus && (fieldId == "assignedTo" || fieldId == "sortBy" || fieldId == "taskStatus"
				|| fieldId == "excludeMerckRequest" || fieldId == "supplier")) {
			$(fieldId).disabled = pendAssignStatus;
		}
		else {
			$(fieldId).disabled = false;
		}
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

