
function myOnload()
{   
	//it's import to put this before setResultFrameSize(), otherwise it will not resize correctly
	displaySearchDuration();
	setResultFrameSize();
}

function hubChanged() {

  var hubO = document.getElementById("branchPlant");
  var roomO = document.getElementById("room");
  var selectedHub = hubO.value;

  for (var i = roomO.length; i > 0; i--) {
    roomO[i] = null;
  }
  showRoomOptions(selectedHub);
  roomO.selectedIndex = 0;
}

function showRoomOptions(selectedHub) {
  var roomArray = new Array();
  roomArray = altRoomId[selectedHub];

  var roomNameArray = new Array();
  roomNameArray = altRoomName[selectedHub];

  if(roomArray.length == 0) {
    setOption(0,messagesData.all,"", "room")
  }

  for (var i=0; i < roomArray.length; i++) {
    setOption(i,roomNameArray[i],roomArray[i], "room")
  }
}

function validateForm() {
  if(!isInteger(document.genericForm.itemId.value.trim(), true)) {
    alert(messagesData.itemInteger);
    return false;
  }
  if(!isInteger(document.genericForm.unitCostMin.value.trim(), true)) {
    alert(messagesData.moneyInteger);
    return false;
  }
  if(!isInteger(document.genericForm.unitCostMax.value.trim(), true)) {
    alert(messagesData.moneyInteger);
    return false;
  }
  if(!isInteger(document.genericForm.receiptDaySpan.value.trim(), true)) {
    alert(messagesData.daysInteger);
    return false;
  }
  if(!isInteger(document.genericForm.inventoryValueMin.value.trim(), true)) {
    alert(messagesData.moneyInteger);
    return false;
  }
  if(!isInteger(document.genericForm.inventoryValueMax.value.trim(), true)) {
    alert(messagesData.moneyInteger);
    return false;
  }
  if(!isInteger(document.genericForm.binCountDays.value.trim(), true)) {
    alert(messagesData.daysInteger);
    return false;
  }
  return true;
}
function search() {
  var action = document.getElementById("userAction");
  action.value = 'search';
    
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
    
  var flag = validateForm();
  if(!flag)  {
	alert(errorMessage);
	return false;
	}
	else
	{
   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
  	return true;
	}
}

function generateExcel() {
    var action = document.getElementById("userAction");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_BinsToScanGenerateExcel','650','600','yes');
    document.genericForm.target='_BinsToScanGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function showErrorMessages()
{
		parent.showErrorMessages();
}
