var beangrid;

var resizeGridWithWindow = true;

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("cabinetInventoryBean").style["display"] = "";

		initializeGrid();
	} else {
		document.getElementById("cabinetInventoryBean").style["display"] = "none";
	}

	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
}

function initializeGrid() {
	beangrid = new dhtmlXGridObject('cabinetInventoryBean');

	initGridWithConfig(beangrid, config, false, true);
	if (typeof (jsonMainData) != 'undefined') {
		beangrid.parse(jsonMainData, "json");
	}
	beangrid.attachEvent("onRightClick", selectRightclick);
}

function selectRightclick(rowId, cellInd) {
	beangrid.selectRowById(rowId, null, false, false);
	/*
	var hub = beangrid.cellById(rowId,beangrid.getColIndexById("hub")).getValue();
	var item = beangrid.cellById(rowId,beangrid.getColIndexById("hitemId")).getValue();
	if ((hub != '') && (item != '')) {
		toggleContextMenu('inventoryDetails');
	}else {
		toggleContextMenu("");
	}
	*/
	toggleContextMenu('inventoryDetails');
}

function viewInventoryDetail() {
	/*
	var hub = beangrid.cellById(beangrid.getSelectedRowId(),beangrid.getColIndexById("hub")).getValue();
	var item = beangrid.cellById(beangrid.getSelectedRowId(),beangrid.getColIndexById("hitemId")).getValue();
	if ((hub != '') && (item != '')) {
		loc = "/tcmIS/hub/inventorydetails.do?itemId=" + item + "&hub=" + hub;
		openWinGeneric(loc, "inventorydetailspage", "800", "450", "yes", "50","50", "no");
	}
   */
	openWinGeneric('inventorydetail.do?catPartNo='+ encodeURIComponent(beangrid.cellById(beangrid.getSelectedRowId(),beangrid.getColIndexById("catPartNo")).getValue())+
            '&inventoryGroup='+ beangrid.cellById(beangrid.getSelectedRowId(),beangrid.getColIndexById("inventoryGroup")).getValue() +
            '&catalogId='+beangrid.cellById(beangrid.getSelectedRowId(),beangrid.getColIndexById("catalogId")).getValue() +
            '&catalogCompanyId='+beangrid.cellById(beangrid.getSelectedRowId(),beangrid.getColIndexById("catalogCompanyId")).getValue() +
            '&partGroupNo='+beangrid.cellById(beangrid.getSelectedRowId(),beangrid.getColIndexById("partGroupNo")).getValue()+
            '&companyId='+beangrid.cellById(beangrid.getSelectedRowId(),beangrid.getColIndexById("companyId")).getValue()       
            ,"ItemInventory","600","600",'yes' );
}
