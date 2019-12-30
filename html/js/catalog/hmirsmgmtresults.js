
var selectedRow;
var resizeGridWithWindow = true;
var children = [];

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("hmirsRoadMapData").style["display"] = "";
		initGridWithGlobal(gridConfig);
	}
	else {
		document.getElementById("hmirsRoadMapData").style["display"] = "none";
	}
	
	displayGridSearchDuration();
	setResultFrameSize();
}

function updateRoadMap() {
	parent.showPleaseWait();
	$("uAction").value = "update";
	beangrid.parentFormOnSubmit();
	document.genericForm.submit();
	return true;
}

function lookupItem(nsn) {
	var loc = "nsnitemsearchmain.do?uAction=search&nsn="+nsn;
	children[children.length] = openWinGeneric(loc,"nsnItemSearch","600","500","yes","50","50","20","20","no");
}

function selectRow(rowId) {
	selectedRow = rowId;
}

function clearItem(rowId) {
	setCellValue(rowId, "itemId", "");
}

function itemSelected(itemId) {
	setCellValue(selectedRow, "itemId", itemId);
}

function closeAllchildren() {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren();
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = [];
}