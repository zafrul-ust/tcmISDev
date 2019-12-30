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
		document.getElementById("Cfr49HazardousMaterialViewBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}
	else {
		document.getElementById("Cfr49HazardousMaterialViewBean").style["display"] = "none";
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

    var currentRow =  cellValue (rowId,"hazardousMaterialDescription");
    parent.returnSelectedValue = currentRow;
    parent.hazmatId  = cellValue (rowId,"hazmatId");
    parent.packingGroup  = cellValue (rowId,"packing");
    parent.unNumber  = cellValue (rowId,"identificationNumber");
    parent.symbol  = cellValue (rowId,"symbol");
    parent.properShippingName  = cellValue (rowId,"properShippingName");
    parent.hazardClass  = cellValue (rowId,"hazardClass");
    parent.shippingNameCount = cellValue (rowId,"shippingNameCount");
    if(cellValue (rowId,"shippingNameCount") > 0)
    	selectedInfo.innerHTML = '| <a href="#" onclick="selectedRow(); return false;">'+messagesData.selectedRowMsg+' : '+currentRow+'</a>'; 
    else
    	selectedInfo.innerHTML = '';
}


function doInitGrid() {
	mygrid = new dhtmlXGridObject('Cfr49HazardousMaterialViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)

	initGridWithConfig(mygrid, config, false, false, false);
	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}
	 mygrid.attachEvent("onRowSelect",selectRow);
}
