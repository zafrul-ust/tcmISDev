var beangrid;

var resizeGridWithWindow = true;

var selectedRowId = null;

function rightClickRow(rowId, cellInd) {
	selectedRowId = rowId;
	toggleContextMenu('rightClickMenu');
}

function resultOnLoad()
{	
	 totalLines = document.getElementById("totalLines").value;
	 if (totalLines > 0)
	 {
	  	document.getElementById("deliveryScheduleViewBean").style["display"] = "";
	  	initializeGrid();  
	 }
	 else
	 {
	   document.getElementById("deliveryScheduleViewBean").style["display"] = "none";   
	 }
	 displayGridSearchDuration(); 
	 setResultFrameSize(); 
}

function doOnRowSelected(rowId, cellInd) {
	selectedRowId = rowId;
}

function initializeGrid(){
	initGridWithGlobal(gridConfig);
}

function viewDelivery() {
	if (selectedRowId != null) {
		parent.parent.openIFrame('deliveryScheduleChange','deliveryschedulechange.do?uAction=viewDelivery&prNumber='+
			beangrid.cells(selectedRowId,beangrid.getColIndexById("prNumber")).getValue()+
			'&lineItem='+beangrid.cells(selectedRowId,beangrid.getColIndexById("lineItem")).getValue()+
			'&isBuyer='+$v("isBuyer")+'&isCsr='+$v("isCsr")
			,messagesData.deliveryScheduleChange,"","N" );
	}
}
