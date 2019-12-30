var selectedRowId=null;

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myOnload()
{
	//it's import to put this before setResultFrameSize(), otherwise it will not resize correctly
	displaySearchDuration();
	setResultFrameSize();
}

function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
 //set start search time
 var now = new Date();
 var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
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
 var flag = validateForm();
  if(flag) {
    parent.showPleaseWait();
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
   if (statusAny.checked) {
     var searchText = document.getElementById("searchText");
     if (searchText.value == null || searchText.value.length == 0) {
       alert(messagesData.anySearchError);
       result = false;
     }
   }else if (statusReleased.checked) {
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

  return result;
} //end of validateForm

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
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

  showFacility(selectedCompany);
  facilityIdO.selectedIndex = 0;
  facilityChanged();
}

function showFacility(selectedCompany) {
  var facilityIdArray = new Array();
  facilityIdArray = altFacilityId[selectedCompany];
  var facilityNameArray = new Array();
  facilityNameArray = altFacilityName[selectedCompany];

  for (var i=0; i < facilityIdArray.length; i++) {
    setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId")
  }
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

	if (applicationIdArray.length == 0) {
		applicationIdArray[0] = "My Work Areas";
		applicationDescArray[0] = messagesData.myworkareas;
	}

  for (var i=0; i < applicationIdArray.length; i++) {
    setApplication(i,htmlDencode(applicationDescArray[i]),applicationIdArray[i])
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
}
else
{
 var lookupButton  =  document.getElementById("lookupButton");
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

function showMrLineAllocationReport()
{
  if (selectedRowId != null)
  {
    var companyId  =  document.getElementById("companyId"+selectedRowId+"");
    var mrNumber  =  document.getElementById("prNumber"+selectedRowId+"");
    var lineItem  =  document.getElementById("lineItem"+selectedRowId+"");

    if ( (mrNumber.value != null && lineItem.value != null && mrNumber.value != 0) && (mrNumber.value.length > 0 && lineItem.value.length > 0) )
    {
      var loc = "mrallocationreportmain.do?companyId="+companyId.value+"&mrNumber="+mrNumber.value+"&lineItem="+lineItem.value+"";
      openWinGenericViewFile(loc,"showMrAllocationReport22","800","550","yes","80","80","no")
    }
  }
}

function showMrAllocationReport()
{
  if (selectedRowId != null)
  {
   var companyId  =  document.getElementById("companyId"+selectedRowId+"");
   var mrNumber  =  document.getElementById("prNumber"+selectedRowId+"");
   var lineItem  =  document.getElementById("lineItem"+selectedRowId+"");

   if ( mrNumber.value != null && mrNumber.value.length > 0 && mrNumber.value != 0)
   {
     var loc = "mrallocationreportmain.do?companyId="+companyId.value+"&mrNumber="+mrNumber.value+"&lineItem=";
     openWinGenericViewFile(loc,"showMrAllocationReport22","800","550","yes","80","80","no")
   }
  }
}

function showDeliverySchedule()
{
  if (selectedRowId != null)
  {
   var orderType  =  document.getElementById("orderType"+selectedRowId+"");
   if (orderType.value == "SCH")
   {
     var companyId  =  document.getElementById("companyId"+selectedRowId+"");
     var prNumber  =  document.getElementById("prNumber"+selectedRowId+"");
     var lineItem  =  document.getElementById("lineItem"+selectedRowId+"");
     var loc = "deliveryschedulemain.do?source=orderTracking&companyId="+companyId.value+"&prNumber="+prNumber.value+"&lineItem="+lineItem.value+"";
     openWinGeneric(loc,"showDeliverySchedule22","850","550","yes","100","100","no")
   }
  }
}