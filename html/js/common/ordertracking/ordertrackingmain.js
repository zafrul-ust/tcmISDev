var homeCompanyLogin;

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  errorMessagesAreaWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}

function showordertracklegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  legendWin.show();
}

function showMrLineNotes(notes)
{
  var mrLineNotesMessagesAreaBody = document.getElementById("mrLineNotesMessagesAreaBody");
  mrLineNotesMessagesAreaBody.innerHTML = notes;

  var mrLineNotesMessagesArea = document.getElementById("mrLineNotesMessagesArea");
  mrLineNotesMessagesArea.style.display="";

  showMrLineNotesWin.show();
}


function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
 //set start search time
 var now = new Date();
 var startSearchTime =document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var companyId = document.getElementById("companyId");
 var companyName = document.getElementById("companyName");
 companyName.value = companyId[companyId.selectedIndex].text;
 var facilityId = document.getElementById("facilityId");
 var facilityName = document.getElementById("facilityName");
 facilityName.value = facilityId[facilityId.selectedIndex].text;
 var applicationId = document.getElementById("applicationId");
 var applicationDesc = document.getElementById("applicationDesc");
 applicationDesc.value = applicationId[applicationId.selectedIndex].text;
 var searchType = document.getElementById("searchType");
 var searchTypeDesc = document.getElementById("searchTypeDesc");
 searchTypeDesc.value = searchType[searchType.selectedIndex].text;
 var searchWhat = document.getElementById("searchWhat");
 var searchWhatDesc = document.getElementById("searchWhatDesc");
 searchWhatDesc.value = searchWhat[searchWhat.selectedIndex].text;
 var needMyApproval = document.getElementById("needMyApproval");
 if (needMyApproval.checked) {
	 document.getElementById("searchNeedMyApproval").value = "Y";
 }else {
	document.getElementById("searchNeedMyApproval").value = "N";
 }
 // For IE10+, a check to ensure that the requestor ID will be blank if the name is, 
 // in case the clear button was used on the requestor name input field
 var requestorName = document.getElementById("requestorName").value;
 var requestorId = document.getElementById("requestorId");
 if (requestorName.length == 0) {
	 	requestorId.value = "";
 }
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

function validateForm() {
   var result = true;
   var statusAny = document.getElementById("statusAny");
   var statusReleased = document.getElementById("statusReleased");
   var statusDelivered = document.getElementById("statusDelivered");
   var statusCanceled = document.getElementById("statusCanceled");
  /* if (statusAny.checked) {
     var searchText = document.getElementById("searchText");
     if ((searchText.value == null || searchText.value.length == 0)) {
       alert(messagesData.anySearchError);
       result = false;
     }
   }else*/
   if (statusReleased.checked) {
     var releasedSinceDays = document.getElementById("releasedSinceDays");
     if (releasedSinceDays.value == null || releasedSinceDays.value.length == 0 || isNaN(releasedSinceDays.value)) {
       alert(messagesData.withinDayError);
       result = false;
     }
   }else if (statusDelivered.checked) {
     var deliveredSinceDays = document.getElementById("deliveredSinceDays");
     if (deliveredSinceDays.value == null || deliveredSinceDays.value.length == 0 || isNaN(deliveredSinceDays.value)) {
       alert(messagesData.withinDayError);
       result = false;
     }
   }else if (statusCanceled.checked) {
     var cancelledSinceDays = document.getElementById("cancelledSinceDays");
     if (cancelledSinceDays.value == null || cancelledSinceDays.value.length == 0 || isNaN(cancelledSinceDays.value)) {
       alert(messagesData.withinDayError);
       result = false;
     }
   }
   
   var companyId = document.getElementById("companyId").value;
   var searchText = document.getElementById("searchText").value.trim();
   var searchWhat = document.getElementById("searchWhat").value;
   if(homeCompanyLogin && 'My Companies' == companyId && 
		   (searchText.length == 0 || 'PO_NUMBER' == searchWhat || 'PO_NUMBER_LINE' == searchWhat || 'PART_DESC' == searchWhat)){
       alert(messagesData.selectcompany);
       result = false;
   }

  return result;
} //end of validateForm

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    var companyId = document.getElementById("companyId");
    var companyName = document.getElementById("companyName");
    companyName.value = companyId[companyId.selectedIndex].text;
    var facilityId = document.getElementById("facilityId");
    var facilityName = document.getElementById("facilityName");
    facilityName.value = facilityId[facilityId.selectedIndex].text;
    var applicationId = document.getElementById("applicationId");
    var applicationDesc = document.getElementById("applicationDesc");
    applicationDesc.value = applicationId[applicationId.selectedIndex].text;
    var searchType = document.getElementById("searchType");
    var searchTypeDesc = document.getElementById("searchTypeDesc");
    searchTypeDesc.value = searchType[searchType.selectedIndex].text;
    var searchWhat = document.getElementById("searchWhat");
    var searchWhatDesc = document.getElementById("searchWhatDesc");
    searchWhatDesc.value = searchWhat[searchWhat.selectedIndex].text;
    var needMyApproval = document.getElementById("needMyApproval");
    if (needMyApproval.checked) {
   	 document.getElementById("searchNeedMyApproval").value = "Y";
    }else {
   	document.getElementById("searchNeedMyApproval").value = "N";
    }
    // For IE10+, a check to ensure that the requestor ID will be blank if the name is, 
    // in case the clear button was used on the requestor name input field
    var requestorName = document.getElementById("requestorName").value;
    var requestorId = document.getElementById("requestorId");
    if (requestorName.length == 0) {
   	 	requestorId.value = "";
    }
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_OrderTrackingGenerateExcel','650','600','yes');
    document.genericForm.target='_OrderTrackingGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function selectRow(rowId)
{
   var selectedRow = document.getElementById("rowId"+rowId+"");
   var selectedRowClass = document.getElementById("colorClass"+rowId+"");
   selectedRow.className = "selected"+selectedRowClass.value+"";

   if (selectedRowId != null && rowId != selectedRowId)
   {
     var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
     var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
     previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
   }
   selectedRowId =rowId;

   /*Depending on the different conditions I set the right click menu to have different options*/
   //orderType = "SCH"
   //don't show allocation if line status is:
   //lineStatus = "Draft","Pending Finance Approval" or "Pending Use Approval"
   var orderType = document.getElementById("orderType"+rowId+"");
   var lineStatus = document.getElementById("lineStatus"+rowId+"");
   var showAllocation = false;
   var showSchedule = false;
   if (lineStatus.value != "Draft" && lineStatus.value != "Pending Finance Approval"
       && lineStatus.value != "Pending Use Approval" && lineStatus.value != "ERROR")
   {
      showAllocation = true;
   }
   if (orderType.value == "SCH")
   {
      showSchedule = true;
   }

   if (showAllocation && showSchedule) {
      toggleContextMenu('showAll');
   }else if (showAllocation) {
      toggleContextMenu('showAllocation');
   }else if (showSchedule) {
      toggleContextMenu('showSchedule');
   }else {
      toggleContextMenu('showEmpty');
   }
}  //end of selectRow

function companyChanged() {
  var companyIdO = document.getElementById("companyId");
  var facilityIdO = document.getElementById("facilityId");
  var selectedCompany = companyIdO.value;

  for (var i = facilityIdO.length; i >= 0;i--) {
    facilityIdO[i] = null;
  }
  showFacility();
}

function showFacility() {
    var facilityIdArray = altFacilityId[$v("companyId")];
    var facilityNameArray = altFacilityName[$v("companyId")];

    var selectedIndex = 0;
    if (typeof(facilityIdArray) == 'undefined' || facilityIdArray.length == 0) {
		 setOption(0, messagesData.myFacilities,"My Facilities", "facilityId");
    }else {
        var dataCount = 0;
        if (allowAllFacilities) {
            setOption(dataCount++, messagesData.allFacilities,"All", "facilityId");
            if (facilityIdArray.length > 1)
                selectedIndex = 1;
        }
        //load rest of data
        var defaultFound = false;
        for (var i=0; i < facilityIdArray.length; i++) {
            setOption(dataCount++,facilityNameArray[i].replace("&nbsp;&nbsp;&nbsp;&nbsp;","    "),facilityIdArray[i], "facilityId");
            if (!defaultFound) {
                var tmpFacilityId = facilityIdArray[i].split(".");
                var splitIndex = 0;
                if (tmpFacilityId.length == 2)
                    splitIndex = 1
                if (tmpFacilityId[splitIndex] == $v("myDefaultFacilityId")) {
                    selectedIndex = dataCount-1;            //reason for -1 is because the setOption did +1
                    defaultFound = true;
                }
            }
        }
    }
    $("facilityId").selectedIndex = selectedIndex;
    facilityChanged();
}

function facilityChanged() {
  var facilityO = document.getElementById("facilityId");
  var applicationIdO = document.getElementById("applicationId");
  var selectedFacility = facilityO.value;

  for (var i = applicationIdO.length; i >= 0;i--) {
    applicationIdO[i] = null;
  }

  showApplicationLinks(selectedFacility);
  applicationIdO.selectedIndex = 0;
}

function showApplicationLinks(selectedFacility)
{
  var applicationIdArray = new Array();
  applicationIdArray = altApplication[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];

	if (typeof(applicationIdArray) == 'undefined')
		 setOption(0, messagesData.myworkareas,"My Work Areas", "applicationId");
	else
	{
		if(applicationIdArray.length == 0) {
            if (selectedFacility == 'All') {
                applicationIdArray[0] = "All";
			    applicationDescArray[0] = messagesData.allWorkAreas;
            }else {
                applicationIdArray[0] = "My Work Areas";
			    applicationDescArray[0] = messagesData.myworkareas;
            }
        }
	
		for (var i=0; i < applicationIdArray.length; i++) {
		    setApplication(i,htmlDencode(applicationDescArray[i]),applicationIdArray[i])
		}
	}
}

function setApplication(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var applicationIdO = document.getElementById("applicationId");
  applicationIdO[href] = optionName;
}

function checkDisabled()
{
var needMyApproval  =  document.getElementById("needMyApproval");
if (needMyApproval.checked)
{
 var lookupButton  =  document.getElementById("lookupButton");
 if(lookupButton != null)
	 lookupButton.disabled=true;

 var companyId  =  document.getElementById("companyId");
 companyId.disabled=true;

 var facilityId  =  document.getElementById("facilityId");
 facilityId.disabled=true;

 var statusAny  =  document.getElementById("statusAny");
 statusAny.disabled=true;
 var statusDraft  =  document.getElementById("statusDraft");
 statusDraft.disabled=true;
 var statusPending  =  document.getElementById("statusPending");
 statusPending.checked=true;
 statusPending.disabled=true;
 var statusOpen  =  document.getElementById("statusOpen");
 statusOpen.disabled=true;
 var statusReleased  =  document.getElementById("statusReleased");
 statusReleased.disabled=true;
 var statusDelivered  =  document.getElementById("statusDelivered");
 statusDelivered.disabled=true;
 var statusCanceled  =  document.getElementById("statusCanceled");
 statusCanceled.disabled=true;

 var deliveredSinceDays  =  document.getElementById("deliveredSinceDays");
 deliveredSinceDays.disabled=true;

 var applicationId  =  document.getElementById("applicationId");
 applicationId.disabled=true;

 var searchText  =  document.getElementById("searchText");
 searchText.disabled=true;

 var searchWhat  =  document.getElementById("searchWhat");
 searchWhat.disabled=true;

 var searchType  =  document.getElementById("searchType");
 searchType.disabled=true;

 var critical  =  document.getElementById("critical");
 critical.disabled=true;

 var requestorName  =  document.getElementById("requestorName");
 requestorName.disabled=true;
 
 $("activeOnly").disabled=true;
}
else
{
 var lookupButton  =  document.getElementById("lookupButton");
 if(lookupButton != null)
	 lookupButton.disabled=false;

 var companyId  =  document.getElementById("companyId");
 companyId.disabled=false;

 var facilityId  =  document.getElementById("facilityId");
 facilityId.disabled=false;

 var statusAny  =  document.getElementById("statusAny");
 statusAny.disabled=false;
 var statusDraft  =  document.getElementById("statusDraft");
 statusDraft.disabled=false;
 var statusPending  =  document.getElementById("statusPending");
 statusPending.disabled=false;
 var statusOpen  =  document.getElementById("statusOpen");
 statusOpen.disabled=false;
 var statusReleased  =  document.getElementById("statusReleased");
 statusReleased.disabled=false;
 var statusDelivered  =  document.getElementById("statusDelivered");
 statusDelivered.disabled=false;
 var statusCanceled  =  document.getElementById("statusCanceled");
 statusCanceled.disabled=false;

 var deliveredSinceDays  =  document.getElementById("deliveredSinceDays");
 deliveredSinceDays.disabled=false;

 var applicationId  =  document.getElementById("applicationId");
 applicationId.disabled=false;

 var searchText  =  document.getElementById("searchText");
 searchText.disabled=false;

 var searchWhat  =  document.getElementById("searchWhat");
 searchWhat.disabled=false;

 var searchType  =  document.getElementById("searchType");
 searchType.disabled=false;

 var critical  =  document.getElementById("critical");
 critical.disabled=false;

 var requestorName  =  document.getElementById("requestorName");
 requestorName.disabled=false;
 
 $("activeOnly").disabled=false;
}
}

function invalidateRequestor()
{
 var requestorName  =  document.getElementById("requestorName");
 var requestorId  =  document.getElementById("requestorId");
 if (requestorName.value.length == 0) {
   requestorName.className = "inputBox";
 }else {
   requestorName.className = "inputBox red";
 }
 //set to empty
 requestorId.value = "";
}

function getPersonnel()
{
   var searchText = "";
   var requestorName  =  document.getElementById("requestorName");
   if (requestorName != null) {
     var requestorId  =  document.getElementById("requestorId");
     if(requestorName.value.length > 0 && requestorId != null) {
       if(requestorId.value.length == 0) {
         searchText = requestorName.value;
       }
     }
   }
   loc = "searchpersonnelmain.do?displayElementId=requestorName&valueElementId=requestorId&searchText="+searchText;
   openWinGeneric(loc,"searchpersonnel","600","450","yes","50","50","no")
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

function showEvalApprovalDetail(requestId) {
	showNewChemApprovalDetail(requestId);
}

function mrApprovalDetail(companyId,prNumber,lineItem) {
	showMrApprovalDetail(companyId,prNumber,lineItem);
}

function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchType');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if((selectedValue == 'PR_NUMBER' || selectedValue == 'ITEM_ID') && searchMyArr[i].value == 'LIKE')
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchType")
		    if ((selectedValue == 'PO_NUMBER_LINE' || selectedValue == 'PO_NUMBER') && searchMyArr[i].value == 'IS')
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
}