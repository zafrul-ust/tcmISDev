var myGrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

function resultOnLoad()
{
	//parent.closeTransitWin();
	totalLines = $("totalLines").value;
	if (totalLines > 0) {
	  if (showUpdate) {
	  	//parent.$("updateSpan").style["display"] = "";
	  }else {
	  	//parent.$("updateSpan").style["display"] = "none";  
	  }
	  $("customerPartManagementDiv").style["display"] = "";
	  initializeGrid();
	}else {
	   $("customerPartManagementDiv").style["display"] = "none";
	}
	
	displayGridSearchDuration();
	setResultFrameSize();
}

//grid black
var colorStyle = "background-color: #727272;color: #ffffff";
function setInactiveRowColor(rowId) {
	//alert("setInactiveRowColor");
	var changedIndex = 14; 	//original status column
	var rowIndex = myGrid.getRowIndex(rowId);
	// Check JSON data because grid cell may not have been rendered yet
	if (myGrid._haas_json_data.rows[rowIndex].data[changedIndex] == 'I') {
		// Set each cell's style to the desired CSS style
		myGrid.setCellTextStyle(rowId, 3, colorStyle);
		myGrid.setCellTextStyle(rowId, 4, colorStyle);
		myGrid.setCellTextStyle(rowId, 5, colorStyle);
		myGrid.setCellTextStyle(rowId, 6, colorStyle);
		myGrid.setCellTextStyle(rowId, 7, colorStyle);
	}
}


function initializeGrid(){
	//initialize grid
	myGrid = new dhtmlXGridObject('customerPartManagementDiv');

	initGridWithConfig(myGrid,config,true,true);
	//setting smart rendering
	myGrid.enableSmartRendering(true);
	myGrid._haas_row_span = true;
	myGrid._haas_row_span_map = lineMap;
	myGrid._haas_row_span_class_map = lineMap3;
	myGrid._haas_row_span_cols = [0,1,2];
	myGrid.attachEvent("onAfterHaasRenderRow", setInactiveRowColor);
	//parse data
	if( typeof( jsonMainData ) != 'undefined' ) {
		myGrid.parse(jsonMainData,"json");
 	}
	myGrid.attachEvent("onRowSelect",selectRow);
}


windowCloseOnEsc = true;
var currentPart = '';
var currentPartGroupNo = '1';
function selectRow()
{
	rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	currentPart = cellValue(rowId0,"catPartNo");
	//currentPartGroupNo = cellValue(rowId0,"partGroupNo");
	var selectedPart = parent.document.getElementById("updateResultLink");
	selectedPart.style["display"] = "";
	selectedPart.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">'+messagesData.selectedPart+' : '+ currentPart +'</a>';
}

function selectData() {
	try {
  		parent.opener.replacementPartChanged(currentPart,currentPartGroupNo);
  	} catch(exx) {}
  parent.close();
}

function auditData() {
	var result = true;
	var atLeastOneRowChecked = false;
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= myGrid.getRowsNum();i++) {
		if (gridCellValue(myGrid,i,myGrid.getColIndexById("ok")) == 'true') {
			atLeastOneRowChecked = true;
			if (myGrid.cells(i,myGrid.getColIndexById("customerPartNo")).getValue().length == 0) {
				alert(messagesData.missingCustomerPart+": "+myGrid.cells(i,myGrid.getColIndexById("itemId")).getValue());
				result = false;
				break;
			}
		}
	}
	if (!atLeastOneRowChecked) {
		alert(messagesData.noRowSelected);
		result = false;
	}

	return result;
}



