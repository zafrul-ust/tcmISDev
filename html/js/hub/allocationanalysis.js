// Global variable: specially useful for right-click
var selectedRowId = null;

var beanGrid;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

/******************Functions for Page Setup*********************/

function myResultOnLoad() {
	try{
		if (!showUpdateLinks) {//Dont show any update links if the user does not have permissions
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		} else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	} catch(ex) {
	}	
	
	initGridWithGlobal(gridConfig);
	
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById(gridConfig.divName).style["display"] = "";
	} else {
		document.getElementById(gridConfig.divName).style["display"] = "none";   
	}

	if (showErrorMessage) 
		parent.showErrorMessages();
	
	displayGridSearchDuration();
 
	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
}

function inventoryGroupChanged() {
	var defaultObj = 
		{
			id:"",
			name: messagesData.all,
			nodefault:false
		}
	;
	buildMyDropDown(altFacility[document.getElementById("inventoryGroup").value], defaultObj, "facilityId");
}

//buildDropDown() function in opshubig.js will not show default value if arr size is 1, so we use our own
function buildMyDropDown(arr, defaultObj, eleName) {
	var obj = $(eleName);
	for (var i = obj.length; i > 0; i--)
		obj[i] = null;
	
	var start = 0;
	if (!defaultObj.nodefault)
		setOption(start++,defaultObj.name,defaultObj.id, eleName);
	if (arr != null && arr.length != 0) {
		for (var i = 0; i < arr.length; i++) {
			setOption(start++, arr[i].name, arr[i].id, eleName);
		}
	}
	obj.selectedIndex = 0;
}

/******************Functions for Buttons*********************/

function validateForm() {
	document.genericForm.target='';
	if(!isSignedInteger(document.genericForm.daySpan.value.trim(), true)) {
		alert(messagesData.daySpanInteger);
		return false;
	}
	if(!isSignedFloat(document.genericForm.itemOrMr.value.trim(), true)) {
		alert(messagesData.itemMrInteger);
		return false;
	}
	return true;
}

function generateExcel() {
	if(validateForm()) {
		$("uAction").value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_AllocationGenerateExcel','650','600','yes');
		document.genericForm.target='_AllocationGenerateExcel';
		window.setTimeout("document.genericForm.submit();",500);
	}
}

function search() {
	if(validateForm()) {
		var now = new Date();
		document.getElementById("startSearchTime").value = now.getTime();
		document.genericForm.target='resultFrame';
		$("uAction").value = 'search';
		showPleaseWait();
		document.genericForm.submit();
	}
}

function showAllocationLegend(){
	var showLegendArea = document.getElementById("showLegendArea");
	showLegendArea.style.display="";

	var dhxWins = new dhtmlXWindows()
	dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	if (!dhxWins.window(messagesData.showlegend)) {
		//create window first time
		var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 210);
		legendWin.setText(messagesData.showlegend);
		legendWin.clearIcon();  // hide icon
		legendWin.denyResize(); // deny resizing
		legendWin.denyPark();   // deny parking
		legendWin.attachObject("showLegendArea");
		legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
		legendWin.center();
	} else {
		//just show
		dhxWins.window("legendwin").show();
	}
}

function lookupCustomer() {    
	loc = "../distribution/customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
	openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}

function lookupPersonnel() {
	loc = "/tcmIS/haas/searchpersonnelmain.do?fixedCompanyId=Y&displayArea=personnelName&valueElementId=csrPersonnelId";
	openWinGeneric(loc,"PersonnelId","650","455","yes","50","50");
}

/******************Functions for Right-click Menus*********************/

function isExistInArray(arr, val) {
	for (var i = 0; i < arr.length; i++)
		if (arr[i] === val)
			return true;
	
	return false;
}

function selectRow(rowId) {
	selectedRowId = rowId;
	var mrNotes = gridCellValue(beanGrid, selectedRowId, "mrNotes");
	var distribution = gridCellValue(beanGrid, selectedRowId, "distribution");
	var opsEntityId = gridCellValue(beanGrid, selectedRowId, "opsEntityId");
	var selectedLineItem = gridCellValue(beanGrid, selectedRowId, "lineItem");

	var opsEntityIdPermissions = ['HAASTCMSIN', 'HAASTCMAUS', 'HAASPSUSA', 'WESCOTCMUK', 'WESCOSCMUK', 'HAASSCMEIR', 'HAASSCMDEU'];
	
	if((mrNotes.length*1 > 0 && selectedLineItem != '0') && (distribution == 'Y' || isExistInArray(opsEntityIdPermissions, opsEntityId)) )
		toggleContextMenu('allMenu');
	else if(mrNotes.length > 0 && selectedLineItem != '0')
		toggleContextMenu('showNotes');
	else if(distribution == 'Y' || isExistInArray(opsEntityIdPermissions, opsEntityId)) {
		if (selectedLineItem == '0') {
			//alert("selectedLineItem " +selectedLineItem);
			if(mrNotes.length*1 > 0) {
				toggleContextMenu('transferReqMenuWNotes');
			} else {
				toggleContextMenu('transferReqMenu');
			}
		} else {
			toggleContextMenu('openMRMenu');
		}
	} else
		toggleContextMenu('contextMenu');	
}

function showAllNotes() {
	var mrLineNotesArea = document.getElementById("mrLineNotesArea");
	mrLineNotesArea.style.display="";
	mrLineNotesArea.innerHTML = gridCellValue(beanGrid, selectedRowId, "mrNotes");
	
	var dhxWins = new dhtmlXWindows();
	var windowId = messagesData.notes + " " + gridCellValue(beanGrid, selectedRowId, "prNumber") + "-" + gridCellValue(beanGrid, selectedRowId, "lineItem");
	dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	if (!dhxWins.window(windowId)) {
		//create window first time
		var legendWin = dhxWins.createWindow(windowId, 0, 0, 400, 150);
		legendWin.setText(windowId);
		legendWin.clearIcon();  // hide icon
		legendWin.denyPark();   // deny parking
		legendWin.attachObject("mrLineNotesArea");
		legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
		legendWin.center();
	} else {
		//just show
		dhxWins.window("legendwin").show();
	}
}

function openMR() {
	var prNumber = gridCellValue(beanGrid, selectedRowId, "prNumber");
	var tabId = 'scratchPad'+prNumber+'';
	var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent(tabId);
	
	try {
		parent.parent.openIFrame(tabId,loc,'MR '+prNumber+'','','N');
	} catch (ex) {
		parent.children[parent.children.length] = openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");
	}
}

function pp(name) {
	var value = gridCellValue(beanGrid, selectedRowId, name);
	return encodeURIComponent(value);
}

function allocationPopup(searchKey) {
	popdown(); // This prevents menu to popup again unnecessarily

	var loc = 
		"/tcmIS/distribution/allocation.do"+
		"?companyId=" +pp('companyId')+
		"&facilityId="+pp('facilityId')+
		"&itemId="+ pp('itemId')+
		"&inventoryGroup="+pp('ownerInventoryGroup')+
		"&specList="+ pp('specListConcat')+
		"&specification="+ pp('specList')+
		"&orderPrNumber="+pp('prNumber')+
		"&shipToCompanyId="+pp('shipToCompanyId')+
		"&shipToLocationId="+pp('shipToLocationId')+
		"&billToCompanyId="+pp('billToCompanyId')+
		"&billToLocationId="+pp('billToLocationId')+
		"&curpath="+"MR "+ pp('prNumber')+
		"&opsEntityId="+pp('opsEntityId')+
		"&priceGroupId="+pp('priceGroupId')+
		"&promisedDate="+pp('promisedDate')+
		"&needDate="+pp('requiredDatetime')+
		"&labelSpec="+pp('labelSpec')+
		"&incoTerms="+pp('incoTerms')+
		"&searchKey="+searchKey+
		"&opsCompanyId="+pp('opsCompanyId');

	loc = loc+ "&remainingShelfLifePercent="+gridCellValue(beanGrid, selectedRowId, "remainingShelfLifePercent");

	loc = loc+"&shipComplete="+ gridCellValue(beanGrid, selectedRowId, "shipComplete") +
		"&consolidateShipment="+ gridCellValue(beanGrid, selectedRowId, "consolidateShipment")+
		"&specDetailList="+ pp('specDetailConcat')+
		"&specLibraryList="+ pp('specLibraryConcat')+
		"&specCocList="+ pp('specCocConcat')+
		"&specCoaList="+ pp('specCoaConcat')+
		"&currencyId="+pp('currencyId')+
		"&scratchPadLineItem="+gridCellValue(beanGrid, selectedRowId, "lineItem")+
		"&orderQuantity="+pp('orderQuantity')+
		"&orderType="+"MR"; //pp('orderType');  ---> get value 

	if ($v("itemType"+selectedRowId) == "MV")
		loc = loc+"&itemType=MV";
	else
		loc = loc+ "&itemType=";

	var shippedOrPickList = (gridCellValue(beanGrid, selectedRowId, "orderQuantity")*1) - (gridCellValue(beanGrid, selectedRowId, "openQuantity")*1);        
	loc = loc+"&unitOfMeasure="+ pp('unitOfSale')+
		"&shipped="+ shippedOrPickList +
		"&pickList=0"+  //shippedOrPickList +
		"&previousPage="+encodeURIComponent("AllocationAnalysis")+
		"&callbackparam="+encodeURIComponent(""+selectedRowId)+
		"&partDesc="+pp('partDescription');

	openWinGeneric(loc, "AllocationDetail_"+pp('prNumber').replace(/[.]/,"_")+"_"+pp('lineItem'), "1024", "500", "yes", "50", "50");
}

/******************Functions for Auto-complete*********************/

function invalidateCustomer() {
	var customerName  =  document.getElementById("customerName");
	var customerId  =  document.getElementById("customerId");
	if (customerName.value.length == 0) {
		customerName.className = "inputBox";
	}else {
		customerName.className = "inputBox red";
	}
	
	//set to empty
	customerId.value = "";
}

function invalidatePersonnel() {
	var personnelName  =  document.getElementById("personnelName");
	var personnelId  =  document.getElementById("csrPersonnelId");
	if (personnelName.value.length == 0) {
		personnelName.className = "inputBox";
	} else {
		personnelName.className = "inputBox red";
	}
	//set to empty
	personnelId.value = "";
}