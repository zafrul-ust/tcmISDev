var beanGrid;
var selectedRowId = null;
// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;

var dhxWins;

function resultOnLoadWithGrid() {
	try {
		parent.stopPleaseWait();
	} catch (e) {}
	
	initGridWithGlobal(gridConfig);
	//variable so that only rows with value in this column being 'true' will be submitted to server
	if (typeof beanGrid != 'undefined') {
		beanGrid._haas_ok_column = "ok";
	}
	
	if ($(gridConfig.divName)) {
		$(gridConfig.divName).style["display"] = $v("totalLines") > 0 ? "" : "none";
	}
	
	try {
		parent.$("updateResultLink").style.visibility = "hidden";
	} catch (ex) {}
	
	displayGridSearchDuration();
	setResultFrameSize();

	toggleContextMenu('customerReturnRmcMenu');
}

function selectRow(rowId) {
	selectedRowId = rowId;
	
	//only show request creation hyperlink if there's available quantity
	try {
		parent.$("updateResultLink").style.visibility = getPrLineTotalAvailable() > 0 ? "visible" : "hidden";
	} catch (ex) {}
}

//assuming all receipts of the same prNumber-lineItem are next to each other
function getPrLineTotalAvailable() {
	var totalAvailable = 0;
	
	if (isWhitespace(cellValue(selectedRowId, 'prTotalAvailable'))) {
		var selectedPrNumberLineItem = cellValue(selectedRowId, "mrLine");
		
		//look forward
		var nextRowId = selectedRowId + 1;
		while (nextRowId <= $v("totalLines")) {
			if (cellValue(nextRowId, "mrLine") == selectedPrNumberLineItem) {
				totalAvailable += parseInt(cellValue(nextRowId, "totalAvailable"));
				nextRowId += 1;
			} else {
				break;
			}
		}
		
		//look backward
		var prevRowId = selectedRowId - 1;
		while (prevRowId > 0) {
			if (cellValue(prevRowId, "mrLine") == selectedPrNumberLineItem) {
				totalAvailable += parseInt(cellValue(prevRowId, "totalAvailable"));
				prevRowId -= 1;
			} else {
				break;
			}
		}
		
		//total everything including selected row
		totalAvailable += parseInt(cellValue(selectedRowId, "totalAvailable"));
		
		//set totalAvailables for all involved rows, so the calculation will only be done once for a prNumber-lineItem
		prevRowId = prevRowId <= 0 ? selectedRowId : prevRowId + 1;
		nextRowId = nextRowId > $v("totalLines") ? selectedRowId : nextRowId - 1;
		
		while (prevRowId <= nextRowId) {
			setCellValue(prevRowId, "prTotalAvailable", totalAvailable);
			prevRowId += 1;
		}
	} else {
		totalAvailable = parseInt(cellValue(selectedRowId, 'prTotalAvailable'));
	}
	
	return totalAvailable;
}

function openCmsCustomerReturnRequestPage() {
	openCustomerReturnRequest();
	
	closeInWindowPopup();
}

function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeInWindowPopup() {
	if (dhxWins != null) {
		var windowId = gridCellValue(beanGrid, selectedRowId, "prNumber")
						+ "-"
						+ gridCellValue(beanGrid, selectedRowId, "lineItem");
		if (dhxWins.window(windowId)) {
			dhxWins.window(windowId).setModal(false);
			dhxWins.window(windowId).hide();
		}
	}
}

function openCreateReturnPopup() {
	var inWindowPopup = $("inWindowPopupDiv");
	inWindowPopup.style.display = "";
	
	initializeDhxWins();
	var windowId = gridCellValue(beanGrid, selectedRowId, "prNumber")
					+ "-"
					+ gridCellValue(beanGrid, selectedRowId, "lineItem");
	if (!dhxWins.window(windowId)) {
		//create window first time
		var popupWin = dhxWins.createWindow(windowId, 0, 0, 320, 150);
		popupWin.setText(messagesData.createReturnRequest);
		popupWin.clearIcon();  // hide icon
		popupWin.denyPark();   // deny parking
		popupWin.attachObject("inWindowPopupDiv");
		popupWin.attachEvent("onClose", function(popupWin){closeInWindowPopup();});
		popupWin.center();
		popupWin.setModal(true);
	} else {
		//just show
		var popupWin = dhxWins.window(windowId);
		popupWin.show();
		popupWin.center();
		popupWin.setModal(true);
	}
}

function openCustomerReturnTracking() {
	var loc = "/tcmIS/distribution/customerreturntrackingmain.do?uAction=autoSearch"
			+ "&opsEntityId=" + URLEncode($v('opsEntityId'))
			+ "&hub=" + URLEncode(cellValue(selectedRowId, "sourceHub"))
			+ "&inventoryGroup=" + URLEncode(cellValue(selectedRowId, "inventoryGroup"))
			+ "&searchField=prNumber"
			+ "&searchMode=is"
			+ "&searchArgument=" + URLEncode(cellValue(selectedRowId, "prNumber"))
			+ "&rmaStatus="
			+ "&isHideSearchPanel=true";
	
    openWinGeneric(loc,'_showCustomerReturnTracking','950','600','yes');
}

function openCustomerReturnRequest() {
	var loc = "cmscustomerreturnrequest.do?companyId=" + URLEncode(cellValue(selectedRowId, "companyId"))
		+ "&prNumber=" + URLEncode(cellValue(selectedRowId, "prNumber"))
		+ "&lineItem=" + URLEncode(cellValue(selectedRowId, "lineItem"))
		+ "&returnType=" + URLEncode($("returnTypeCO").checked ? $v("returnTypeCO") : $v("returnTypeCR"));

	openWinGeneric(loc,'_showCustomerReturnRequest','1420','750','yes');
}