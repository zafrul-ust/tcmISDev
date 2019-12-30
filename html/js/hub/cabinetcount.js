var beangrid;
var selectedRowId = null;
var windowCloseOnEsc = true;

var resizeGridWithWindow = true;

function $(a) {
	return document.getElementById(a);
}


/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultBodyOnload()
{
 	 	
 	totalLines = document.getElementById("totalLines").value; 
 	
 	
	if (totalLines > 0)
	{
		document.getElementById("cabinetBinItemBean").style["display"] = "";
			
		initializeGrid();
		
		if(updateSuccess){
	 		
	 		alert(messagesData.updateSuccess);
	 	}
		
		
		
		if (showUpdateLinks) /*Only show any update links if the user has permissions*/
	 	{
			 		
	  		parent.document.getElementById("updateResultLink").style["display"] = "";
	 	}
	 	else
	 	{
	 		
	  		parent.document.getElementById("updateResultLink").style["display"] = "none";
	 	}		
	}  
	else
	{
		
		document.getElementById("cabinetBinItemBean").style["display"] = "none";   
	}
  
	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
}

function initializeGrid(){
	  
	beangrid = new dhtmlXGridObject('cabinetBinItemBean');
	
	initGridWithConfig(beangrid,config,false,true,true);
	
	if( typeof( jsonMainData ) != 'undefined' ) {
		
			beangrid.parse(jsonMainData,"json");
						
    }
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
}

function doOnRowSelected(rowId,cellInd) {
	selectedRowId = rowId;
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
   if(validateSearchForm()) { 
   $('action').value = 'search';
   document.cabinetCountForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}
function validateSearchForm() 
{
  	  	  	
	var errorMessage = messagesData.validvalues +"\n\n";
	var errorCount = 0;

	var selectedCabinets  =  document.getElementById("cabinetIdArray");
		 
	if (selectedCabinets[0].selected == true)

	//if (selectedCabinets[0].value == "" || selectedCabinets[0].value.length==0)
	{
		//alert("cant choose All cabinets");
  		errorMessage += messagesData.notAllCabinets + "\n\n" ;
  		errorCount = 1;
	}
	if($v("searchArgument").length > 0 && $v("searchField") == "itemId" && !isInteger($v("searchArgument"),true))
	{
		errorMessage += messagesData.itemInteger;
		errorCount = 1;
	}
	if (errorCount >0)
	{
  		alert(errorMessage);
  		return false;
	}
	else
	{
  		return true;
	}
}

function hubChanged() {
  var hubO = document.getElementById("branchPlant");
  var facilityO = document.getElementById("facilityId");
  var selectedHub = hubO.value;

  for (var i = facilityO.length; i > 0; i--) {
    facilityO[i] = null;
  }
  showFacilityOptions(selectedHub);
  facilityO.selectedIndex = 0;
  facilityChanged();
  //if user does not have permission to add bin hide the button
//  checkBinPermission(selectedHub);
  setHubName();

 var facilityName = getFacilityName(hubO.value, facilityO.value);
 var facilityNameO = document.getElementById("facilityName");
 facilityNameO.value = facilityName;
}

function showFacilityOptions(selectedHub) {
  var facilityArray = new Array();
  facilityArray = altFacilityId[selectedHub];

  var facilityNameArray = new Array();
  facilityNameArray = altFacilityName[selectedHub];

  if(facilityArray.length == 0) {
    setOption(0,"All","", "facilityId")
  }

  for (var i=0; i < facilityArray.length; i++) {
    setOption(i,facilityNameArray[i],facilityArray[i], "facilityId")
  }
}

function facilityChanged() {
  var hubO = document.getElementById("branchPlant");
  var facilityO = document.getElementById("facilityId");
  var applicationO = document.getElementById("application");
  var cabinetO = document.getElementById("cabinetIdArray");
  var selectedHub = hubO.value;
  var selectedFacility = facilityO.value;

  for (var i = applicationO.length; i > 0; i--) {
    applicationO[i] = null;
  }
  for (var i = cabinetO.length; i > 0; i--) {
    cabinetO[i] = null;
  }
  showApplicationOptions(selectedHub + "-" + selectedFacility);
  applicationO.selectedIndex = 0;
  applicationChanged();

 var facilityName = getFacilityName(hubO.value, facilityO.value);
 var facilityNameO = document.getElementById("facilityName");
 facilityNameO.value = facilityName;
}

function showApplicationOptions(selectedFacility) {
  var applicationArray = new Array();
  applicationArray = altApplicationId[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];

  if(applicationArray.length == 0) {
    setOption(0,"All","", "application")
  }

  for (var i=0; i < applicationArray.length; i++) {
    setOption(i,applicationDescArray[i],applicationArray[i], "application")
  }
}

function applicationChanged() {
  var hub0 = document.getElementById("branchPlant");
  var facilityO = document.getElementById("facilityId");
  var applicationO = document.getElementById("application");
  var cabinetO = document.getElementById("cabinetIdArray");

  var selectedHub = hub0.value;
  var selectedFacility = facilityO.value;
  var selectedApplication = applicationO.value;

  if(cabinetO != null) {
    for (var i = cabinetO.length; i > 0; i--) {
      cabinetO[i] = null;
    }
  }
  showCabinetOptions(selectedHub + "-" + selectedFacility + "-" + selectedApplication);
  cabinetO.selectedIndex = 0;
}

function showCabinetOptions(selectedApplication) {
  var cabinetArray = new Array();
  var hub = document.getElementById("branchPlant");
  var fac = document.getElementById("facilityId");

  var currApp = document.getElementById("application");
  if(currApp.value == '') {
    selectedApplication = hub.value + "-" + fac.value + "-All";
  }

  cabinetArray = altCabinetIdArray[selectedApplication];

  var cabinetNameArray = new Array();
  cabinetNameArray = altCabinetNameArray[selectedApplication];

  if(cabinetArray == null || cabinetArray.length == 0) {
    setOption(0,"All","", "cabinetIdArray")
  }
  else {
    for (var i=0; i < cabinetArray.length; i++) {
      setOption(i,cabinetNameArray[i],cabinetArray[i], "cabinetIdArray")
    }
  }
}

function submitMainUpdate() 
{
	var flag = validateForm();
	if(flag)
	{
    /*Set any variables you want to send to the server*/
    var action = $("action");
    	action.value = 'update';
	   	parent.showPleaseWait();
	   	
	   	if (beangrid != null) {
	    	beangrid.parentFormOnSubmit();
	    }
    /*Submit the form in the result frame*/
    	document.cabinetCountForm.submit();
	}
}

function validateForm()
{
	  for(var i=0;i<beangrid.getRowsNum();i++)
	  {
		  rowid = beangrid.getRowId(i);
		  var pri = cellValue(rowid,"countQuantity");
		  if(pri != '' && !isInteger(pri,false)) 
		  {
			  alert(messagesData.validvalues + messagesData.quantity);
			  return false;
		  }
	  }
		return true;
}

//?? don't understand the code..
function submitUpdate() 
{
  var flag = validateUpdateForm();
  if(flag) 
  {
    var action = document.getElementById("action");
    action.value = 'update';
    document.cabinetCountForm.submit();
  }
}

function validateUpdateForm() 
{	 
  	document.cabinetCountForm.target='resultFrame';
  	var action = document.getElementById("action");
  	action.value = '';
  	
 }

function generateExcel() {
  var flag = validateSearchForm();
  if(flag) 
  {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    document.cabinetCountForm.target='_newGenerateExcel';
    var a = window.setTimeout("document.cabinetCountForm.submit();",500);
  }
}

function changeMinMaxCabinet(branchPlant, facilityId, itemId, cabinetId, binId, cabinetName, useApplication, partNumber, companyId, catalogId, partGroupNumber, status) {
  //var facilityName = getFacilityName(branchPlant, facilityId);
   var facilityNameO = document.getElementById("facilityName");
   var facilityName = facilityNameO.value;
   var hubNameO = document.getElementById("hubName");
   var hubName = hubNameO.value;
   useApplication = useApplication.replace(/&/gi, "%26");
   useApplication = useApplication.replace(/#/gi, "%23");
   facilityId = facilityId.replace(/&/gi, "%26");
   facilityId = facilityId.replace(/#/gi, "%23");
   facilityName = facilityName.replace(/&/gi, "%26");
   facilityName = facilityName.replace(/#/gi, "%23");
   var levelChangeUrl = "/tcmIS/hub/cabinetlevel.do?action=viewMinMax&branchPlant=" + branchPlant + "&hubName=" + hubName + "&facilityId=" + facilityId + "&facilityName=" + facilityName + "&application=" + useApplication + "&itemId=" +itemId+"&cabinetName="+ cabinetName + "&cabinetId="+cabinetId + "&catPartNo=" + partNumber + "&companyId=" + companyId + "&catalogId=" + catalogId + "&partGroupNo=" + partGroupNumber + "&status=" + status;
   openWinGeneric(levelChangeUrl,"getchang_elevelscreen","650","600","yes","50","50");
}

function getFacilityName(branchPlant, facilityId) {
  var facName = null;
  var facIdArray = new Array();
  var facNameArray = new Array();
  facIdArray = altFacilityId[branchPlant];
  facNameArray = altFacilityName[branchPlant];
  for (var i = 0; i < facIdArray.length; i++) {
    if(facIdArray[i] == facilityId) {
      facName = facNameArray[i];
    }
  }
  return facName;
}

function myOnLoad() {
  //setSearchFrameSize();
	
  var hubO = document.getElementById("branchPlant");
  var selectedHub = hubO.value;
  
  setHubName();
}

function setHubName() {
  var hubO = document.getElementById("branchPlant");
  var selectedHub = hubO.value;
  var hubName = document.getElementById("hubName");
  hubName.value = altHubName[selectedHub];
}


function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchMode');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if(selectedValue == 'itemId' && searchMyArr[i].value == 'contains' )
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
		    actuallyAddedCount++;
	  }
}
