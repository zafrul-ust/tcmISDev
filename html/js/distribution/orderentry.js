var beangrid;
/*
 * Set this to be false if you don't want the grid width to resize based on
 * window size.
 */
var resizeGridWithWindow = true;

var children = new Array();

function resultOnLoad() {
	/*
	 * try{
	 * 
	 * if (!showUpdateLinks) //Dont show any update links if the user does
	 * not have permissions/ {
	 * document.getElementById("updateResultLink").style["display"] =
	 * "none"; } else {
	 * document.getElementById("updateResultLink").style["display"] = "";
	 *  } } catch(ex) {}
	 * 
	 */

	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("salesQuoteViewBean").style["display"] = "";

		initializeGrid();
	}
	else {
		document.getElementById("salesQuoteViewBean").style["display"] = "none";
	}

	/* This dislpays our standard footer message */
	displayNoSearchSecDuration();

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultSize();
	document.getElementById("mainUpdateLinks").style["display"] = "";

	try {
		parent.resetTimer();
	}
	catch (ex) {
	}
}

function initializeGrid() {
	beangrid = new dhtmlXGridObject('salesQuoteViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// -1 is for disable rowSpan and smart rendering, but sorting still
	// works; false will disable rowSpan and sorting but smart rendering is
	// enabled
	// set submitDefault to true: Send the data to the server
	// singleClickEdit: this is for type:"txt",
	initGridWithConfig(beangrid, config, false, true);
	if (typeof (jsonMainData) != 'undefined') {
		beangrid.parse(jsonMainData, "json");

	}
	// beangrid.attachEvent("onRowSelect",selectRow);
	beangrid.attachEvent("onRightClick", selectRightclick);
}

//function selectRow(rowId, cellInd) {
//
//	var columnId = beangrid.getColumnId(cellInd);
//	var okValue = cellValue(rowId, "ok");
//
//	switch (columnId) {
//		// check the revised promise date field value.
//		case "ok":
//			if (okValue == false)
//				return;
//
//			break;
//		// Disable text area for comments and other text fields if use
//		// doesn't have permission to edit them.
//		default:
//	}
//	;
//
//}

function selectRightclick(rowId, cellInd) {
	toggleContextMenu("openscratchpad");
}

// Function for updating records.
function deleteRecords() {
	/* Set any variables you want to send to the server */
	var selectedCounter = 0;
	for ( var i = 1; i <= beangrid.getRowsNum(); i++) {
		if (cellValue(i, "ok")) {
			var prNumber = cellValue(i, "prNumber");
			var tabId = 'scratchPad' + prNumber + '';
			try {
				parent.parent.closeTabx(tabId);
			}
			catch (ex) {
			}
			selectedCounter++;
		}
	}
	if (selectedCounter > 0) {
		document.genericForm.target = '';
		document.getElementById('action').value = 'delete';
		showPleaseWait();
		beangrid.parentFormOnSubmit(); // prepare grid for data sending
		document.genericForm.submit();
	}
	else {
		alert(messagesData.norow);
	}

}

function getScratchPadId() {
	callToServer("/tcmIS/distribution/scratchpadmain.do?uAction=newxxx");
}

function newScratchPad(blank, orderType, scratchPadId, personnelId, lastName, firstName, tcmISError) {
	if (tcmISError.length > 0) {
		alert(messagesData.scratchpadlimitexceeded);
		return;
	}

	var loc = "/tcmIS/distribution/scratchpadmain.do?blank=Y&scratchPadId=" + scratchPadId + "&tabId=" + encodeURIComponent('scratchPad' + scratchPadId + '') + "&personnelId=" + personnelId + "&lastName=" + lastName + "&firstName=" + firstName
	                + "&tcmISError=" + tcmISError;
	// alert(loc);
	try {
		parent.openIFrame("scratchPad" + scratchPadId + "", loc, "SP " + scratchPadId, "", "N");
	}
	catch (ex) {
		// alert(ex);
		openWinGeneric(loc, "scratchPad", "900", "600", "yes", "80", "80", "yes");
	}
}

var parNumber = null;
var curpr = 0;
var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&caller=" + encodeURIComponent(window.name) + "&callback=nextpr&scratchPadId=";
function nextpr() {
	i = curpr++;

	if (i < parNumber.length) {
		try {
			parent.openIFrame("scratchPad" + parNumber[i] + "", loc + parNumber[i] + "&tabId=" + encodeURIComponent("scratchPad" + parNumber[i] + ""), "SP " + parNumber[i], "", "N");
		}
		catch (ex) {
			openWinGeneric(loc + parNumber[i], "ScrathPad_New" + i, "900", "600", "yes", "80", "80", "yes");

		}
	}
}

function openScratchPads() {
	var prNumber;
	var selectedRowsCounter = 0;
	parNumber = new Array();

	for ( var i = 1; i <= beangrid.getRowsNum(); i++) {
		try {
			if (cellValue(i, "ok")) {
				selectedRowsCounter++;
				if (selectedRowsCounter > 0) {
					prNumber = cellValue(i, "prNumber");
					try {
						parNumber[parNumber.length] = prNumber;
					}
					catch (ex) {
					}
				}
			}
		}
		catch (ex) {
		}
	}

	if (selectedRowsCounter == 0) {
		alert(messagesData.norow);
		return false;
	}
	{
		try {
			parent.openIFrame("scratchPad" + parNumber[0] + "", loc + parNumber[0] + "&tabId=" + encodeURIComponent("scratchPad" + parNumber[0] + ""), "SP " + parNumber[0] + "", "", "N");
		}
		catch (ex) {
			openWinGeneric(loc + parNumber[0], "ScrathPad_New" + 0, "900", "600", "yes", "80", "80", "yes");
		}
	}
	curpr = 1;
}

function openScratchPadsOnRightClick() {
	var prNumber = cellValue(beangrid.getSelectedRowId(), "prNumber");

	var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId=" + prNumber + "&tabId=" + encodeURIComponent('scratchPad' + prNumber + '');
	try {
		parent.openIFrame("scratchPad" + prNumber + "", loc, "SP " + prNumber + "", "", "N");
	}
	catch (ex) {
		openWinGeneric(loc, "scratchPad" + prNumber, "900", "600", "yes", "80", "80", "yes");
	}
}

function refreshWin() {

	// window.location.reload( false );
	document.genericForm.target = '';
	showPleaseWait();
	document.genericForm.submit();

}

function openLookup() {
	var loc = "/tcmIS/distribution/orderentrylookupmain.do";
	try {
			parent.openIFrame("orderEntryLookup", loc, ""+messagesData.orderentrylookup, "", "N");
	}
	catch (ex) {
			children[children.length] = openWinGeneric(loc, "orderEntryLookup", "1024", "600", "yes", "80", "80", "yes");

	}
}

function closeAllchildren() {
	// You need to add all your submit button vlues here. If not, the window
	// will close by itself right after we hit submit button.
	// if (document.getElementById("uAction").value != 'new') {
	try {
		for ( var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			}
			catch (ex) {
			}
			children[n].close();
		}
	}
	catch (ex) {
	}
	children = new Array();
}
