var beangrid;

var resizeGridWithWindow = true;

var selectedRowId = null;
var showMsds = false;

function rightClickRow(rowId, cellInd) {
	selectedRowId = rowId;
	if (cellInd >= beangrid.getColIndexById("materialIdDisplay")) {
		toggleContextMenu('rightClickMenu');
	}
	else {
		toggleContextMenu('contextMenu');
	}
}

function getCurrentRowVal(name) {
	return encodeURIComponent(cellValue(selectedRowId, name));
}

function openCatalogCompanyMSDS() {
    openCatalogMSDSMaintenance("Custom Indexing");
}

function openCatalogMSDSIndexing() {
    openCatalogMSDSMaintenance("MSDS Indexing");
}

function openCatalogMSDS() {
    openCatalogMSDSMaintenance("Material QC");
}

function update() {
	document.genericForm.target='';
	document.getElementById('uAction').value = 'update';

	parent.showPleaseWait();

	// Call this function to send the data from grid back to the server
	if (beangrid != null) {
		beangrid.parentFormOnSubmit();
	}

	document.genericForm.submit();
}

function resultOnLoad()
{	
	 totalLines = document.getElementById("totalLines").value;
	 if (totalLines > 0)
	 {
	  	document.getElementById("KitIndexingQueueViewBean").style["display"] = "";
	  	initializeGrid();  
	 }
	 else
	 {
	   document.getElementById("KitIndexingQueueViewBean").style["display"] = "none";   
	 }
	 displayGridSearchDuration(); 
	 setResultFrameSize(); 
}

function doOnRowSelected(rowId, cellInd) {
	selectedRowId = rowId;
	
	if(showMsds) {
		viewMsds();
		showMsds = false;
	}
}

function initializeGrid(){
	initGridWithGlobal(gridConfig);
}

function viewMsds() {
	newMsdsViewer = true;
	if(newMsdsViewer)
		openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+ beangrid.cells(selectedRowId,beangrid.getColIndexById("materialId")).getValue() +
			'&spec=' + // do we need to know spec id?
			'&facility=' +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
	else
    	openWinGeneric('ViewMsds?act=msds'+
			'&id='+ beangrid.cells(selectedRowId,beangrid.getColIndexById("materialId")).getValue() +
			'&spec=' + // do we need to know spec id?
			'&facility=' +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
}
