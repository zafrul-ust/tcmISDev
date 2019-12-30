windowCloseOnEsc = true;

var selectedRowId = null;

function myOnload()
{
	doInitGrid();
	 
	var riskString = $v("riskString").split(","); 
	if(riskString.length > 0*1 && riskString[0].length > 0*1) {
		for(var i = 0; i < riskString.length; i++){
			addNewLine(riskString[i]);
		}
	}
	 
	setResultSize();
}

function doInitGrid() {
	mygrid = new dhtmlXGridObject('hazardClassBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	initGridWithConfig(mygrid, config, false, false, false);
	if (typeof (jsonData) != 'undefined') {
		mygrid.parse(jsonData, "json");
	}

	mygrid.attachEvent("onRowSelect", selectRow);
//	mygrid.attachEvent("onRightClick", selectRightclick);
}

var rowids = new Array();
function addNewLine(classId) {

	var ind = haasGrid.getRowsNum();
	var rowid = ind + 1;
		  
	count = 0 ;

	var thisrow = haasGrid.addRow(rowid,"",ind);

	haasGrid.cells(rowid, count++).setValue('Y');
	haasGrid.cells(rowid, count++).setValue(classId);
	rowids[""+rowid] = rowid;

	$('removeLineSpan').style.display="";	
}

function removeLine() {
	if(!selectedRowId){
		alert(messagesData.norowselected);return false;
	}
	else {
		haasGrid.deleteRow(selectedRowId);
		delete( rowids[selectedRowId] ) ;
		if( haasGrid.getRowsNum() == 0 ) 
			$('removeLineSpan').style["display"] = "none";
	}
	if(haasGrid.getRowsNum() > 0*1) {
		selectedRowId = haasGrid.getRowId(0);
		haasGrid.selectRowById(haasGrid.getRowId(0), null, false, false);
	}
	
}

function selectRow(rowId0, colId0) {

	selectedRowId = rowId0;
//	haasGrid.selectRowById(rowId0, null, false, false);

} //end of method

function returnSelected() {
	var returnString = "";
	var count = 0;
	for( rowId in rowids ) {
		if($v("hazardClass"+rowId) == "") {
			alert(messagesData.pleaseselect);
			return false;
		}
		if(count == 0)
			returnString = $v("hazardClass"+rowId);
		else
			returnString += ","+$v("hazardClass"+rowId);
		
		count++;
	}
	
	if(opener.setSubRisk)
		opener.setSubRisk(returnString);
		
	window.close();
}

