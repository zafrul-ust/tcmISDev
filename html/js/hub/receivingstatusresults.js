var beangrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

// This works only for popup windows and IE. Close the window after clicking Esc
// key
var windowCloseOnEsc = true;

function onRowSelect(rowId, cellId) {
	selectedRowId = rowId;
}

function onRightclick(rowId, cellId) {
		selectedRowId = rowId;
		// Show right-click menu
		toggleContextMenu('rightClickMenu');
}

function touchUpdated(rowId, cellId) {
	setCellValue(rowId, "updated", true);
}


function openChecklist() {
	var receiptId = cellValue(selectedRowId, "receiptId");
	var now = new Date();
	var loc = "/tcmIS/hub/receivingqcchecklist.do?openerPage=rStatus&searchType=is&sort=Bin&searchWhat=Receipt%20Id&search=" + receiptId;
	loc += "&sourceHub=" + $v('hub');
	loc += "&opsEntityId=" + $v('opsEntityId');
	loc += "&startSearchTime=" + now.getTime();
	openWinGeneric(loc, "receivingQcCheckList" + receiptId, "1000", "950", "yes", "80", "80");

}

function myResultOnLoadWithGrid(gridConfig) {
	try {
		if (!showUpdateLinks) /* Dont show any update links if the user does not have permissions */
		{
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
		else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	}
	catch (ex) {
	}

	if ($v("totalLines") > 0) {
		initGridWithGlobal(gridConfig);
	}
	else {
		document.getElementById("receivingStatusDisplay").style["display"] = "none";
	}

	displayGridSearchDuration();

	/* It is important to call this after all the divs have been turned on or off. */
	setResultFrameSize();

}

function doUpdate() {
		document.genericForm.target = ''; 
		$('uAction').value = 'update';		
		parent.showPleaseWait(); 
		
		// Reset search time for update
		var now = new Date();
		var startSearchTime = parent.document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();	

		if (mygrid != null) {
			mygrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
}
