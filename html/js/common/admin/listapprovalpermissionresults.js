windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var beangrid = null;

function resultOnLoad() {
	if (!showUpdateLinks) {
		parent.document.getElementById("updateResultLink").style["display"] = "none";
		//document.getElementById("checkAll").style["display"] = "none";
	} else {
		parent.document.getElementById("updateResultLink").style["display"] = "";
		//document.getElementById("checkAll").style["display"] = "";
	}
	
	parent.document.getElementById("updateResultTreeLink").style["display"] = "none";
	
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		// make result area visible if data exist
		document.getElementById("facilityListApproverViewBean").style["display"] = "";
		// build the grid for display
		initializeGrid();
		showChecked();
	} else {
		document.getElementById("facilityListApproverViewBean").style["display"] = "none";
	}
	
	/* This displays our standard footer message */
	displayGridSearchDuration();
	
	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('facilityListApproverViewBean');
	initGridWithConfig(beangrid,config,-1,true,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");
	}
	beangrid.attachEvent("onRowSelect",selectRow);
}

function getCell(rowId,colId) {
	return gridCell(selectedGrid,rowId,colId);
}

function selectRow(rowId, cellInd) {
	var columnId = beangrid.getColumnId(cellInd);
	switch (columnId) {
		case "displayView":
			rowChanged(rowId);
			default:
	}	
}


function rowChanged(rowId) {
	beangrid.cellById(rowId, beangrid.getColIndexById("modified")).setValue("Y");
	
}


function showChecked() {
	var numRows = beangrid.getRowsNum();
	var j=0;
	for (var rowId = 1; rowId <= numRows; rowId++) {
		if(beangrid.cells(rowId,beangrid.getColIndexById("approver")).getValue() == 'Y') {
			beangrid.cells(rowId,beangrid.getColIndexById("displayView")).setChecked(true);
			j++;
		}
	}
	if(j == numRows)
		document.getElementById("chkAllSelected").checked=true;
}


function checkAll(checkboxname) {
	var checkall = $("chkAllSelected");
	var rowsNum = beangrid.getRowsNum();
	
	rowsNum = rowsNum*1;
	
	renderAllRows();
	
	if( checkall.checked ) {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
				rowChanged(p);
			}
		}
	} else {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
				rowChanged(p);
			}
		}
	}
	
	return true;  
}

function createXSL() {
	document.getElementById('uAction').value="createXSL";
	document.genericForm.target='_excel_report_file';
	openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
	setTimeout("document.genericForm.submit()",300);
}
