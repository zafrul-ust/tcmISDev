function submitSearchForm() 
{
	/*Make sure to not set the target of the form to anything other than resultFrame*/
	document.catalogAddTracking.target='resultFrame';
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

function needingMyApprovalClicked() {
	setSearchViewLevel($('requestNeedingMyApproval').checked);
    if ($('requestNeedingMyApproval').checked) {
        setStatusesLevel(true);    
    }else {
        pendingApprovalRoleChanged();
    }
}

function setSearchViewLevel(flag) {
	$('requestorName').disabled = flag;
    $('activeOnly').disabled = flag;
    $('facilityId').disabled = flag;
	$('approverName').disabled = flag;
    $('activeOnly2').disabled = flag;
    $('applicationId').disabled = flag;
	//$('requestId').disabled = flag;
	$('searchField').disabled = flag;
	$('searchMode').disabled = flag;
	$('searchArgument').disabled = flag;
    $('dateType').disabled = flag;
    $('beginDateJsp').disabled = flag;
    $('endDateJsp').disabled = flag;
    $('pendingApprovalRole').disabled = flag;
}

function setStatusesLevel(flag) {
    var tmpArray = document.getElementsByName('requestStatusChkbxArray');
	for (var i = 0; i < tmpArray.length; i++) {
		tmpArray[i].disabled = flag;
	}
}

function invalidateRequestor()
{
 var requestorName  =  document.getElementById("requestorName");
 if (requestorName.value.length == 0) {
   requestorName.className = "inputBox";
 }else {
   requestorName.className = "inputBox red";
 }
 //set to empty
 document.getElementById("requestor").value = "";
}

function invalidateApprover()
{
 var approverName  =  document.getElementById("approverName");
 if (approverName.value.length == 0) {
   approverName.className = "inputBox";
 }else {
   approverName.className = "inputBox red";
 }
 //set to empty
 document.getElementById("approver").value = "";
}

function validateForm(){
	// validates the age input field
	var errorMessage = "";
	var errorCount = 0;
	
	try
	{
	 var searchInput = document.getElementById("searchArgument");
	 var searchField = document.getElementById("searchField");	 


	 if ((searchField.value.trim().length != 0 ) && (  (searchField.value == "itemId")   ) )			
	 {
	    if((searchInput.value.trim().length != 0) && (!(isPositiveInteger(searchInput.value.trim())) || (searchInput.value*1 == 0 )  ))
	    { 
	    errorMessage +=  messagesData.searchInput + "\n";
	    errorCount = 1;
	    searchInput.value = "";
	    }
	 }	
	 
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

function generateExcel() {
	var flag = validateForm();
	if(flag) {
		var action = document.getElementById("action");
		action.value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','New_Chem_Tracking_List','650','600','yes');
		document.catalogAddTracking.target='New_Chem_Tracking_List';
		var a = window.setTimeout("document.catalogAddTracking.submit();",500);

	}
}

function loadFacility() {
	var facilityO = document.getElementById("facilityId");
	for(var i = facilityO.length; i > 0; i--) {
		facilityO[i] = null;
	}
	//reload facility
	for (var j = 0; j < altFacilityId.length;j++) {
		setOption(j,altFacilityName[j],altFacilityId[j], "facilityId");
	}
	facilityO.selectedIndex = 0;
	facilityChanged();
}

function facilityChanged() {
	  var facilityO = document.getElementById("facilityId");
	  var applicationIdO = document.getElementById("applicationId");
	  var selectedFacility = facilityO.value;

	  for (var i = applicationIdO.length; i > 0;i--) {
	    applicationIdO[i] = null;
	  }

	  showApplicationLinks(selectedFacility);	  
	  applicationIdO.selectedIndex = 0;
      //approval roles
      var pendingApprovalRole = document.getElementById("pendingApprovalRole");
	  for (var i = pendingApprovalRole.length; i > 0;i--) {
	    pendingApprovalRole[i] = null;
	  }
      showApprovalRoleLinks(selectedFacility);
      pendingApprovalRole.selectedIndex = 0;
}  //end of method

function showApplicationLinks(selectedFacility) {
  var applicationIdArray = new Array();
  applicationIdArray = altApplication[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];

  //setApplication(0,"Please Select","All")
  for (var i=0; i < applicationIdArray.length; i++) {
    //setApplication(i,applicationDescArray[i],applicationIdArray[i])
	 setOption(i,applicationDescArray[i],applicationIdArray[i], "applicationId"); 
  }
}

function showApprovalRoleLinks(selectedFacility) {
  var approvalRoleArray = altUserFacilityApprovalRole[selectedFacility];
  if (approvalRoleArray != null) {
      var count = 0;
      if (approvalRoleArray.length == 0 || approvalRoleArray.length > 1) {
        setOption(count++,messagesData.allRoles,"", "pendingApprovalRole");
      }
      for (var i = 0; i < approvalRoleArray.length; i++) {
         setOption(i+count,approvalRoleArray[i],approvalRoleArray[i], "pendingApprovalRole");
      }
  }else {
    setOption(0,messagesData.allRoles,"", "pendingApprovalRole");
  }
  pendingApprovalRoleChanged();  
} //end of method

function pendingApprovalRoleChanged() {
    if ($v("pendingApprovalRole").length > 0) {
        setStatusesLevel(true);    
    }else {
        setStatusesLevel(false);
    }
}

function searchRequestor() {
   loc = "searchpersonnelmain.do?displayElementId=requestorName&valueElementId=requestor";
   openWinGeneric(loc,"searchRequestor","600","450","yes","50","50","20","20","no");
}

function searchApprover()
{
   loc = "searchpersonnelmain.do?displayElementId=approverName&valueElementId=approver";
   openWinGeneric(loc,"searchApprover","600","450","yes","50","50","20","20","no");
}


function clearRequestor()
{
    document.getElementById("requestorName").value = "";
    document.getElementById("requestor").value = "";
}


function clearApprover()
{
    document.getElementById("approverName").value = "";
    document.getElementById("approver").value = "";
}

function showApprovalDetail(requestId) {
	showNewChemApprovalDetail(requestId);
}