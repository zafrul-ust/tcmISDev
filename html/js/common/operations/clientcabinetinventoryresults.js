var beangrid;

var resizeGridWithWindow = true;

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("clientCabinetInvViewBean").style["display"] = "";

		initializeGrid();
	} else {
		document.getElementById("clientCabinetInvViewBean").style["display"] = "none";
	}

	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
}

function initializeGrid() {
	beangrid = new dhtmlXGridObject('clientCabinetInvViewBean');

	initGridWithConfig(beangrid, config, false, true);
	if (typeof (jsonMainData) != 'undefined') {
		beangrid.parse(jsonMainData, "json");
	}
	beangrid.attachEvent("onRowSelect", selectRow);
	beangrid.attachEvent("onRightClick", selectRightclick);
}

function selectRightclick(rowId, cellInd) {
	beangrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('freightAdvice');
}



function selectRow(rowId, cellInd) {
	var columnId = beangrid.getColumnId(cellInd);
	 /*
	if (columnId == "itemId") {
		viewInventoryDetail();
	}
   */
}

function viewInventoryDetail() {
	var hub = parent.window.$("branchPlant").value;

	var item = beangrid.cellById(beangrid.getSelectedRowId(),
			beangrid.getColIndexById("hitemId")).getValue();

	if ((hub != '') && (item != '')) {
		
		loc = "/tcmIS/hub/inventorydetails.do?itemId=" + item + "&hub=" + hub;
		openWinGeneric(loc, "inventorydetailspage", "800", "450", "yes", "50","50", "no");

	}

}
