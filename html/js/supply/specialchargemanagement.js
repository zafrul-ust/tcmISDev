/** **********************************NEW************************************************** */
var children = new Array();

var beanGrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

//Set this to be false if you don't want the grid width to resize based on
//window size.
var resizeGridWithWindow = true;

function viewPurchaseOrder(showSpecific) {
	var loc = "/tcmIS/supply/purchaseorder.do?";
	var tabName = messagesData.purchaseOrder;
	if (selectedRowId && showSpecific) {
		var radianPo = gridCellValue(beanGrid, selectedRowId, "radianPo");
		loc += "action=searchlike&radianPo=" + radianPo + "&po=" + radianPo;
		tabName += " " + radianPo;
	}
	
	//tabs with id as 'purchaseOrder' are processed separatedly, namely they will not overwrite each other
	try {
		parent.parent.openIFrame("purchaseOrder", loc, tabName, "", "N");
	} catch (e) {
		openWinGeneric(loc,"ViewPurchaseOrder","950","800","yes","150","150");
	}
}

function selectRow(rowId) {
	selectedRowId = rowId;
}

function buildRightClickOption(rowId) {
	selectedRowId = rowId;
	if (parent.readOnly)
		toggleContextMenu('contextMenu');
	else {
		var vitems = new Array();
		if (gridCellValue(beanGrid, selectedRowId, "status") == "I")
			vitems[0] = "text=" + messagesData.activate;
		else if (gridCellValue(beanGrid, selectedRowId, "status") == "A")
			vitems[0] = "text=" + messagesData.inactivate;
		vitems[0] += ";url=javascript:changePoStatus();";
		vitems[1] = "text=" + messagesData.viewPurchaseOrder + ";url=javascript:viewPurchaseOrder(true);";
		replaceMenu('modifyMenu', vitems);
		toggleContextMenu('modifyMenu');
	}
}

function replaceMenu(menuname, menus) {
	var i = mm_returnMenuItemCount(menuname);
	for (; i > 1; i--)
		mm_deleteItem(menuname, i);

	for (i = 0; i < menus.length;) {
		var str = menus[i];
		i++;
		mm_insertItem(menuname, i, str);
		// delete original first item.
		if (i == 1)
			mm_deleteItem(menuname, 1);
	}
}

function changePoStatus() {
	setGridCellValue(beanGrid, selectedRowId, "toModify", true);
	$('uAction').value = 'changeStatus';
	var now = new Date();
	var startSearchTime = parent.document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	parent.showPleaseWait(); // Show "please wait" while updating
	if (beanGrid != null) {
		// This function prepares the grid dat for submitting top the server
		beanGrid.parentFormOnSubmit();
	}
	document.genericForm.submit();
}