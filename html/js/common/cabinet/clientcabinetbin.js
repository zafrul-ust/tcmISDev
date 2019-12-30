var dhxWins = null;
var windowCloseOnEsc = true;
var children = new Array();

function setDropdownViewLevel() {
	$("catalogId").disabled = true;
}

function enableFieldsForFormSubmit() {
	$("catalogId").disabled = '';
}

function submitAdd() {
	if (validateForm()) {
		enableFieldsForFormSubmit();
		$("uAction").value = 'add';
		$("catalogCompanyId").value = altCatalogCompanyId[($("catalogId").selectedIndex)];
		document.genericForm.submit();
	}
}

function validateForm() {
  if(document.genericForm.facPartNo == null || document.genericForm.facPartNo.value.trim().length == 0) {
    alert(messagesData.facPartNoRequired);
    return false;
  }
  if(document.genericForm.stockingLevel == null || document.genericForm.stockingLevel.value.trim().length == 0) {
    alert(messagesData.stockingLevelRequired);
    return false;
  }
  if(document.genericForm.reorderPoint == null || document.genericForm.reorderPoint.value.trim().length == 0) {
    alert(messagesData.reorderPointRequired);
    return false;
  }
  if(document.genericForm.leadTimeDays == null || document.genericForm.leadTimeDays.value.trim().length == 0) {
    alert(messagesData.leadTimeDaysRequired);
    return false;
  }
  if(!isInteger(document.genericForm.reorderPoint.value.trim(), true)) {
    alert(messagesData.reorderPointInteger);
    return false;
  }
  if(!isInteger(document.genericForm.stockingLevel.value.trim(), true)) {
    alert(messagesData.stockingLevelInteger);
    return false;
  }
  if(document.genericForm.reorderQuantity != null && document.genericForm.reorderQuantity.value.trim().length > 0) {
	  if(!isInteger(document.genericForm.reorderQuantity.value.trim(), true)) {
		 alert(messagesData.reorderQuantityInteger);
		 return false;
	  }
  }
  if(document.genericForm.kanbanReorderQuantity != null && document.genericForm.kanbanReorderQuantity.value.trim().length > 0) {	
	  if(!isInteger(document.genericForm.kanbanReorderQuantity.value.trim(), true)) {
		 alert(messagesData.kanbanReorderQuantityInteger);
		 return false;
	  }
  }
  if(!isInteger(document.genericForm.leadTimeDays.value.trim(), true)) {
    alert(messagesData.leadTimeDaysInteger);
    return false;
  }
  if(parseInt(document.genericForm.reorderPoint.value.trim()) > parseInt(document.genericForm.stockingLevel.value.trim())) {
    alert(messagesData.reorderPointLessThanStockingLevel);
    return false;
  }
  return true;
}

var closeParentTransitWin = true;
function closeThisWindow() {
	if (closeParentTransitWin) {
		opener.closeTransitWin();	
	}
}

function myOnLoad(uAction) {
	if (uAction == 'add' && !showErrorMessage) {
		closeParentTransitWin = false;
		opener.closeTransitWin();
		opener.search();
		closeWindow();		
	}else {
		closeParentTransitWin = true;
  		setDropdownViewLevel();
	}
	if (showErrorMessage) {
		alert($v("errorMsg"));
	}
}

//I was too short sighted so I named the window too narrow.
//replacememtpartsearchmain.do - is actually just a catalog search popup
function lookupPartNumber() {
 var currentPart = ($v("facPartNo")).trim();
 showTransitWin(messagesData.waitFor);
 var loc = "replacementpartsearchmain.do?uAction=search&searchArgument="+currentPart+"&companyId="+altCompanyId[($("catalogId").selectedIndex)]+
	  "&catalogCompanyId="+altCatalogCompanyId[($("catalogId").selectedIndex)]+"&catalogId="+$v("catalogId")+"&sourcePage="+$v("sourcePage")+"&facilityId="+$v("facilityId");
 children[children.length] = openWinGeneric(loc,"partNumberSearch"+children.length,"650","500","yes","50","50","20","20","no");
 
}

function closeAllchildren()
{
// if (document.getElementById("uAction").value != 'new') {
 try {
  for(var n=0;n<children.length;n++) {
   try {
    children[n].closeAllchildren();
    } catch(ex){}
   children[n].close();
   }
  } catch(ex){}
  children = new Array();
// }
}

function replacementPartChanged(newPart,newPartGroupNo,customerTemperatureIdIndex) {
	$("facPartNo").value = newPart;
	$("partGroupNo").value = newPartGroupNo;
	$("tierIIStorageTemperature").selectedIndex = customerTemperatureIdIndex;
}

var dhxFreezeWins = null;
var resizeGridWithWindow = true;


function initializeDhxWins() {
if (dhxFreezeWins == null) {
  dhxFreezeWins = new dhtmlXWindows();
  dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
 }
}


function showTransitWin(messageType)
{
var waitingMsg = messagesData.waitingforinputfrom+"...";
 $("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

var transitDailogWin = document.getElementById("transitDailogWin");
 transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
 transitDailogWin.style.display="";

 initializeDhxWins();
if (!dhxFreezeWins.window("transitDailogWin")) {
 // create window first time
 transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
  transitWin.setText("");
  transitWin.clearIcon();  // hide icon
 transitWin.denyResize(); // deny resizing
 transitWin.denyPark();   // deny parking

  transitWin.attachObject("transitDailogWin");
 //transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
 transitWin.center();
 // setting window position as default x,y position doesn't seem to work, also hidding buttons.
 transitWin.setPosition(125, 75);
  transitWin.button("minmax1").hide();
  transitWin.button("park").hide();
  transitWin.button("close").hide();
  transitWin.setModal(true);
 }else{
 // just show
 transitWin.setModal(true);  // freeze the window here
 dhxFreezeWins.window("transitDailogWin").show();
 }
}

function closeTransitWin() {
if (dhxFreezeWins != null) {
 if (dhxFreezeWins.window("transitDailogWin")) {
   dhxFreezeWins.window("transitDailogWin").setModal(false);
   dhxFreezeWins.window("transitDailogWin").hide();
  }
 }
}