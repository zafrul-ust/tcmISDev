// Set this to be false if you don't want the grid width to resize based on window size.
var resizeGridWithWindow = true;

//Global variable: specially useful for right-click
var selectedRowId = null;

/*
 * Grid event OnBeforeSelect function
 * Save the row class of currently 
 * selected row, before class changes.
 */
var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {
	// set the color for previous row
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);		
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
   		
	return true;	
}

function doOnRowSelected(rowId,cellInd) {
	 var columnId = beanGrid.getColumnId(cellInd);  
	 selectedRowId = rowId;
}	 

function selectRightclick(rowId,cellInd){
	beanGrid.selectRowById(rowId,null,false,false);
	// The right click event needs to call selectRow function.
	doOnRowSelected(rowId,cellInd);
	// Show right-click menu
    toggleContextMenu('rightClickMenu');
}

function openMr() {
	var prNumber = cellValue(beanGrid.getSelectedRowId(),"prNumber");
	var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent('scratchPad'+prNumber+'');

	try	{
		parent.parent.openIFrame("scratchPad"+prNumber+"",loc,"MR "+prNumber+"","","N");
	}
	catch (ex)
	{
		openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");	
	}
}
