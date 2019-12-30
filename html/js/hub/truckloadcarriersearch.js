var resizeGridWithWindow = true;
var beanGrid;
var selectedRowId = "";

function initializeGrid(){
 	beanGrid = new dhtmlXGridObject('tlLtlCarrierIgViewBean');
 	initGridWithConfig(beanGrid,config,-1,true);
 	if( typeof( jsonMainData ) != 'undefined' ) {
   	beanGrid.parse(jsonMainData,"json");
 	}
	beanGrid.attachEvent("onRowSelect",selectRow);
}

function myOnLoad() {
	//close transit window
	parent.closeTransitWin();
	var totalLines = $("totalLines").value;
	if (totalLines > 0) {
		document.getElementById('tlLtlCarrierIgViewBean').style["display"]="";
		initializeGrid();
	}else {
		document.getElementById("tlLtlCarrierIgViewBean").style["display"] = "none";
		parent.document.getElementById("updateResultLink").style["display"] = "none";
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
} //end of method

function selectRow() {
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	if( ee != null ) {
   	if( ee.button != null && ee.button == 2 ) rightClick = true;
   	else if( ee.which == 3  ) rightClick = true;
   }

	selectedRowId = rowId0;
	parent.$("updateResultLink").style["display"] = "";
	parent.$("updateResultLink").innerHTML = '<a href="#" onclick=call("selectData"); return false;">'+messagesData.returnSelectedCarrier+' : '+ cellValue(selectedRowId,"carrierName") +'</a>';
}

function selectData() {
	try {
		parent.opener.carrierSelected(cellValue(selectedRowId,"carrierCode"),cellValue(selectedRowId,"carrierName"),cellValue(selectedRowId,"transportationMode"));
  	} catch(exx) {

	}
}