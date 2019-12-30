var resizeGridWithWindow = true;

function resultOnLoad() {
	/*try {
		if (!showUpdateLinks) 
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		else
			parent.document.getElementById("updateResultLink").style["display"] = "";
	}
	catch (ex) {
	}*/

	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		parent.document.getElementById("showlegendLink").style["display"] = "";
		// make result area visible if data exist
		document.getElementById("AdrViewBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}
	else {
		document.getElementById("AdrViewBean").style["display"] = "none";
	}

	parent.document.getElementById("selectedRow").innerHTML="";
	
	/* This displays our standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();
	
}

function selectRow(rowId,cellInd){

    var selectedInfo = parent.document.getElementById("selectedRow");
    
    var currentRow =  cellValue(rowId,"nameAndDescription");
    parent.returnSelectedValue = currentRow;
    parent.adrId  = cellValue (rowId,"adrId");
    parent.packingGroup  = cellValue (rowId,"packing");
    parent.unNumber  = cellValue (rowId,"unNumber");
    parent.adrClass  = cellValue (rowId,"adrClass");
    parent.nameAndDescription  = cellValue (rowId,"nameAndDescription");
    parent.shippingNameCount=cellValue (rowId,"shippingNameCount");
    parent.classificationCode  = cellValue (rowId,"classificationCode");
    parent.limitedQuantity  = cellValue (rowId,"limitedQuantity");
    parent.transportCategory  = cellValue (rowId,"transportCategory");
    parent.tunnelCode  = cellValue (rowId,"tunnelCode");
    parent.technicalNameRequired  = cellValue (rowId,"technicalNameRequired");
    if(cellValue (rowId,"shippingNameCount") > 0)
    	selectedInfo.innerHTML = '| <a href="#" onclick="selectedRow(); return false;">'+messagesData.selectedRowMsg+' : '+currentRow+'</a>'; 
    else
    	selectedInfo.innerHTML = '';
}


function doInitGrid() {
	mygrid = new dhtmlXGridObject('AdrViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)

	initGridWithConfig(mygrid, config, false, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}
	 mygrid.attachEvent("onRowSelect",selectRow);
}
