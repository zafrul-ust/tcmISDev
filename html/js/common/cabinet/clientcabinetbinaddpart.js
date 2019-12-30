var dhxWins = null;
var windowCloseOnEsc = true;
var children = new Array();

function setDropdownViewLevel(tmpVal) {
	$("catalogId").disabled = tmpVal;
}

function enableFieldsForFormSubmit() {
	$("catalogId").disabled = '';
    $("reorderPoint").disabled = '';
    $("stockingLevel").disabled = '';
    $("leadTimeDays").disabled = '';
}

function submitAdd() {
	if (validateForm()) {
		enableFieldsForFormSubmit();
		$("uAction").value = 'add';
		$("catalogCompanyId").value = altCatalogCompanyId[($("catalogId").selectedIndex)];
		$("applicationDesc").value = $("application").options[$("application").selectedIndex].text;
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
  if( $v('countType') == 'NotCounted' ) {
	  if( !isFloat($v('avgAmount', false) ) || $v('avgAmount').indexOf('-') != -1 ){
			  alert(messagesData.avgneedbenumber);
			  return false;
	  }
	  if( !isFloat($v('maxAmount', false) ) || $v('maxAmount').indexOf('-') != -1 ){
			  alert(messagesData.maxneedbenumber);
			  return false;
      }
  	  if( $v('maxAmount')*1 < $v('avgAmount')*1) {
  	  	alert(messagesData.avgGreaterMax);
  	  	return false;
	  }
  }else if ($v('countType') == 'LEVEL') {
	  if ($v('levelUnit') == "") {
		  alert(messagesData.needLevelUnit);
		  return false;
	  }
  }else if ($v('countType') == 'AutomaticRefill') {
      if(parseInt(document.genericForm.stockingLevel.value.trim()) == 0) {
        alert(messagesData.stockingLevelRequired);
        return false;
      }
      if ($v('startDate') == "") {
		  alert(messagesData.needStartDate);
          return false;
      }
  }
  return true;
}

var closeParentTransitWin = true;
function closeThisWindow() {
	if (closeParentTransitWin) {
		opener.closeTransitWin();	
	}
}

function countTypeChanged(){
	if($v('countType') == "NotCounted") {
		$('NCid1').style.display="";
		$('NCid2').style.display="";
		$('avgAmount').value = '0';
		$('maxAmount').value = '0';
        $('reorderPoint').value = '0';
        $("reorderPoint").disabled = true;
        $('stockingLevel').value = '0';
        $("stockingLevel").disabled = true;
        $('reorderQuantity').value = '';
        $("reorderQuantity").disabled = true;
        $('kanbanReorderQuantity').value = '';
        $("kanbanReorderQuantity").disabled = true;
        $('leadTimeDays').value = '0';
        $("leadTimeDays").disabled = true;
        $('ownershipTr').style.display="none";
        $('homeCompanyOwned').checked = false;
        $('levelUnitTr').style.display="none";
        $('levelUnit').value="";
        $('startDateTr').style.display="none";
        $('startDate').value="";
    }else {
		$('NCid1').style.display="none";
		$('NCid2').style.display="none";
        $("reorderPoint").disabled = false;
        $("stockingLevel").disabled = false;
        $("reorderQuantity").disabled = false;
        $("kanbanReorderQuantity").disabled = false;
        $("leadTimeDays").disabled = false;
        if ($v('canChangeOwnership') == 'true')
            $('ownershipTr').style.display="";
        if ($v('countType') == "LEVEL"){
        	$('levelUnitTr').style.display="";
        }
        else {
        	$('levelUnitTr').style.display="none";
        	$('levelUnit').value="";
        }
        
        if ($v('countType') == "AutomaticRefill"){
        	$('startDateTr').style.display="";
        }
        else {
        	$('startDateTr').style.display="none";
        	$('startDate').value="";
        }
    }
	for(var i = 0; i < countType.rows.length; i++ ) {
		var cType = $v('countType');
        if(countType.rows[i].value == $v('countType')) {
        	document.getElementById("cabinetStartPredateBlock").value = countType.rows[i].predateBlock;
        	break;
        }
	}
    displayDropShipOverride();
}

function myOnLoad(uAction) {
	if (uAction == 'add' && !showErrorMessage) {
		closeParentTransitWin = false;
		opener.closeTransitWin();
        opener.search();
		closeWindow();		
	}else {
		closeParentTransitWin = true;
  		setDropdownViewLevel(false);
	}
	if (showErrorMessage) {
		alert($v("errorMsg"));
	}

	var start = 0;
	var workAreaArray = opener.addPartArray;
	if((workAreaArray.length == 1 && workAreaArray[0].id == 'All') || workAreaArray.length > 5)
		for (var m=0; m < cabinetId.length; m++)
			setOption(start++,cabinetId[m].text,cabinetId[m].application,"application");
	else {
		for (var m=0; m < cabinetId.length; m++)
			for(var n = 0; n < workAreaArray.length; n++)
				if(workAreaArray[n].value == cabinetId[m].value)
					setOption(start++,cabinetId[m].text,cabinetId[m].application,"application");
	}
	
	start = 0;
	if($('application').options.length == 0)
		for (var l=0; l < cabinetId.length; l++)
			setOption(start++,cabinetId[l].text,cabinetId[l].application,"application")
			
	createCountTypeDD();
	$('countType').onchange=countTypeChanged;
    displayDropShipOverride();
}  //end of method

//I was too short sighted so I named the window too narrow.
//replacememtpartsearchmain.do - is actually just a catalog search popup
function lookupPartNumber() {
    var tmpSearchPart = true;
    if($v("catalogId") != "Global") {
        if ($v("application").length == 0) {
            alert(messagesData.pleaseSelectaWorkarea);
            tmpSearchPart = false;
        }
    }
    if (tmpSearchPart) {
        var currentPart = ($v("facPartNo")).trim();
        showTransitWin(messagesData.waitFor);
        var loc = "replacementpartsearchmain.do?uAction=search&searchArgument="+encodeURIComponent(currentPart)+"&companyId="+altCompanyId[($("catalogId").selectedIndex)]+
                  "&catalogCompanyId="+altCatalogCompanyId[($("catalogId").selectedIndex)]+"&catalogId="+encodeURIComponent($v("catalogId"))+"&sourcePage="+$v("sourcePage")+
                  "&application="+encodeURIComponent($v("application"))+"&facilityId="+encodeURIComponent($v("facilityId"));
        children[children.length] = openWinGeneric(loc,"partNumberSearch"+children.length,"650","500","yes","50","50","20","20","no");
    }
}  //end of method

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
    setDropdownViewLevel(true);
}

function clearPartNumber() {
	$("facPartNo").value = '';
	$("partGroupNo").value = '';
	$("tierIIStorageTemperature").selectedIndex = 0;
    setDropdownViewLevel(false);
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

function applicationChanged(index) {
    if($v("catalogId") != "Global") {
        $("facPartNo").value = '';
	    $("partGroupNo").value = '';
	    $("tierIIStorageTemperature").selectedIndex = 0;
    }
    createCountTypeDD();
}  //end of method

function createCountTypeDD(isAllowStocking) {
    var isAllowStocking = 'N';
    var selectedApplication = $v('application');
    for (var m=0; m < cabinetId.length; m++) {
        if (cabinetId[m].application == selectedApplication) {
            isAllowStocking = cabinetId[m].allowStocking;
            break;
        }
    }

    var counTypeOptions = $('countType').options;
	for(var i = counTypeOptions.length - 1; i >= 0 ; i-- )
		counTypeOptions[i] = null;

    var homeCompanyOwnedChecked = $("homeCompanyOwned").checked;
    var optionsAddedCount = 0;
    var selectedIndex = 0;
    for(var i = 0; i < countType.rows.length; i++ ) {
        if(countType.rows[i].value == 'NotCounted') {
            if (countType.rows[i].value == $v("defaultCountType"))
                selectedIndex = optionsAddedCount
            setOption(optionsAddedCount++,countType.rows[i].text, countType.rows[i].value,"countType");
        }else if (isAllowStocking == 'Y') {
            if (countType.rows[i].value == "KanBan") {
                if (!homeCompanyOwnedChecked) {
                    if (countType.rows[i].value == $v("defaultCountType"))
                        selectedIndex = optionsAddedCount
                    setOption(optionsAddedCount++,countType.rows[i].text, countType.rows[i].value,"countType");
                }
            }else {
                if (countType.rows[i].value == $v("defaultCountType"))
                    selectedIndex = optionsAddedCount
                setOption(optionsAddedCount++,countType.rows[i].text, countType.rows[i].value,"countType");
            }
        }
    }

    $("countType").selectedIndex = selectedIndex;
	countTypeChanged();
}

function homeCompanyOwnedChanged() {
    createCountTypeDD();
    displayDropShipOverride();
}

function displayDropShipOverride() {
    //don't display drop ship override if haas owned
    if ($("homeCompanyOwned").checked) {
        $('dropShipOverrideTr').style.display="none";
        $('dropShipOverride').checked = false;    
    }else {
        $('dropShipOverrideTr').style.display="";
    }
}

function padInt(val, totalLength) {
	var valStr = val.toString();
	while (valStr.length < totalLength) {
		valStr = "0"+valStr;
	}
	return valStr;
}

function calendarBlock(el) {
	var lower = new Date();
	lower.setDate(lower.getDate() - document.getElementById("cabinetStartPredateBlock").value);
	
	var months = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
	document.getElementById("cabinetStartDate").value = padInt(lower.getDate(),2)+"-"+months[lower.getMonth()]+"-"+lower.getYear();
	
	return getCalendar(el,null,document.genericForm.cabinetEndDate,document.genericForm.cabinetStartDate);
}
