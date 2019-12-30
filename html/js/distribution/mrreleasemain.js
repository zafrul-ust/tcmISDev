
function changeShowLateOrdersValue(element) {
	if (element.value == 'No')
		element.value = 'Yes';
	else
		element.value='No';
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateSearchCriteriaInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(isValidSearchForm) {
   var userAction = document.getElementById("action");
   userAction.value = 'searchOrders';
   document.mrrelease.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

//***************************************************************************
//validateSearchCriteriaInput
// verify to and from dates if values set
//
function validateSearchCriteriaInput()
{
var errorMessage = messagesData.validvalues + "\n\n";
var warningMessage = messagesData.alert + "\n\n";
var errorCount = 0;
var warnCount = 0;

if( isEmptyV("opsEntityId") ){
    //errorMsg += "\n"+messagesData.paymentTermsRequired;
	errorMessage = messagesData.pleaseselect+" "+messagesData.operatingentity + "\n\n";
	errorCount =1;
}

var searchField = $v("searchField");
var searchArgument = $v("searchArgument");
if (searchField == 'prNumber' && !isFloat(searchArgument,true))
{  
	errorMessage = errorMessage + messagesData.mr;
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
	 loc = "/tcmIS/haas/searchpersonnelmain.do?displayArea=personnelName&valueElementId=personnelId";
	 openWinGeneric(loc,"PersonnelId","650","455","yes","50","50");
}

function clearCustomer()
{
    document.getElementById("customerName").value = "";
    document.getElementById("customerName").setAttribute("className", "inputBox");
    document.getElementById("customerId").value = "";
}

function clearPersonnel()
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

function clearRequestor()
{
    document.getElementById("personnelName").value = "";
    document.getElementById("personnelName").setAttribute("className", "inputBox");
    document.getElementById("personnelId").value = "";
}

function mainOnLoad() {
	document.getElementById("selectedRow").innerHTML="";	
}

function createXSL(){
  if(validateSearchCriteriaInput()) {
	$('action').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_MRReleaseExcel','650','600','yes');
    document.mrrelease.target='_MRReleaseExcel';
    var a = window.setTimeout("document.mrrelease.submit();",50);
  }
}

var dhxWins = null;
//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function changeSearchMode(selectedValue)
{
	  var searchType = $('searchMode');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;
	  
	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if((searchMyArr[i].value == 'like' || searchMyArr[i].value == 'endsWith') && selectedValue == 'prNumber' )
		    	continue;
		    setOption(actuallyAddedCount++,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
	  }

}

