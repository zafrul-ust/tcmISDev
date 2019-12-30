var submitcount=0;
var selectedrow=null;

function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function mySearchOnload()
{
	setSearchFrameSize();
	//showInventoryLinks(document.getElementById("facilityId").value);
	showHub();
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
 var hubId = document.getElementById("hub");
 document.getElementById("hubName").value = hubId[hubId.selectedIndex].text;
 var inventoryGroup = document.getElementById("inventoryGroup");
 document.getElementById("inventoryGroupName").value = inventoryGroup[inventoryGroup.selectedIndex].text;
 var searchWhat = document.getElementById("searchWhat");
 document.getElementById("searchWhatDesc").value = searchWhat[searchWhat.selectedIndex].text;
 var searchType = document.getElementById("searchType");
 document.getElementById("searchTypeDesc").value = searchType[searchType.selectedIndex].text;
 var sortBy = document.getElementById("sortBy");
 document.getElementById("sortByDesc").value = sortBy[sortBy.selectedIndex].text;
 var uom = document.getElementById("unitOfMessure");
 document.getElementById("unitOfMessureDesc").value = uom[uom.selectedIndex].text;

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
  if ((document.getElementById("searchText").value.length == 0) &&
		(document.getElementById("beginDate").value.length == 0) &&
		(document.getElementById("endDate").value.length == 0)) {
	  alert(messagesData.missingData);
	  result = false;
  }
  return result;
} //end of validateForm

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
	 var hubId = document.getElementById("hub");
	 document.getElementById("hubName").value = hubId[hubId.selectedIndex].text;
	 var inventoryGroup = document.getElementById("inventoryGroup");
	 document.getElementById("inventoryGroupName").value = inventoryGroup[inventoryGroup.selectedIndex].text;
	 var searchWhat = document.getElementById("searchWhat");
	 document.getElementById("searchWhatDesc").value = searchWhat[searchWhat.selectedIndex].text;
	 var searchType = document.getElementById("searchType");
	 document.getElementById("searchTypeDesc").value = searchType[searchType.selectedIndex].text;
	 var sortBy = document.getElementById("sortBy");
	 document.getElementById("sortByDesc").value = sortBy[sortBy.selectedIndex].text;
	 var uom = document.getElementById("unitOfMessure");
	 document.getElementById("unitOfMessureDesc").value = uom[uom.selectedIndex].text;

	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReceivingReportGenerateExcel','650','600','yes');
    document.genericForm.target='_ReceivingReportGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function showHub() {
  for (var i=0; i < altHubId.length; i++) {
    setOption(i,altHubName[i],altHubId[i], "hub");
  }
  document.getElementById("hub").selectedIndex = 0;
  hubChanged();
}

function hubChanged() {
  var hub0 = document.getElementById("hub");
  var inventoryGroup0 = document.getElementById("inventoryGroup");
  //var facility0 = document.getElementById("facilityId");
  var selectedHub = hub0.value;
  for (var i = inventoryGroup0.length; i > 0; i--) {
    inventoryGroup0[i] = null;
  }

  showInventoryGroupOptions(selectedHub);
  inventoryGroup0.selectedIndex = 0;
 // inventoryGroupChanged(inventoryGroup0.value);
}

function showInventoryGroupOptions(selectedHub) {
  var inventoryGroupArray = new Array();
  inventoryGroupArray = altInventoryGroup[selectedHub];

  var inventoryGroupNameArray = new Array();
  inventoryGroupNameArray = altInventoryGroupName[selectedHub];

  if(inventoryGroupArray == null || inventoryGroupArray.length == 0) {
    setOption(0,"All","ALL", "inventoryGroup")
  }
  else {
    for (var i=0; i < inventoryGroupArray.length; i++) {
      setOption(i,inventoryGroupNameArray[i],inventoryGroupArray[i], "inventoryGroup")
    }
  }
}

function inventoryGroupChanged() {
  var inventoryGroup0 = document.getElementById("inventoryGroup");
  var facilityId0 = document.getElementById("facilityId");
  var selectedInventoryGroup = inventoryGroup0.value;
  if(facilityId0 != null) {
    for (var i = facilityId0.length; i > 0; i--) {
      facilityId0[i] = null;
    }
  }
  showFacilityIdOptions(selectedInventoryGroup);
  facilityId0.selectedIndex = 0;
}


function showFacilityIdOptions(selectedInventoryGroup) {
  var facilityIdArray = new Array();
  facilityIdArray = altFacilityId[selectedInventoryGroup];
  var facilityNameArray = new Array();
  facilityNameArray = altFacilityName[selectedInventoryGroup];

  if(facilityIdArray == null || facilityIdArray.length == 0) {
    setOption(0,"All","ALL", "facilityId")
  }
  else {
    for (var i=0; i < facilityIdArray.length; i++) {
      setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId")
    }
  }
}

/*
function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryO = document.getElementById("inventory");
    inventoryO[href] = optionName;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function search(entered)
{
    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
        var target2 = document.all.item("RESULTSPAGE");
        target2.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
}

function cancel()
{
    window.close();
}
*/
