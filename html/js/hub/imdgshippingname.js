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
		document.getElementById("ImdgViewBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}
	else {
		document.getElementById("ImdgViewBean").style["display"] = "none";
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

    var currentRow =  cellValue (rowId,"description");
    parent.returnSelectedValue = currentRow;
	parent.imdgId = cellValue(rowId,"imdgId"); 
	parent.unNumber = cellValue(rowId,"unNumber"); 
	parent.description = cellValue(rowId,"description");
	parent.classOrDivision = cellValue(rowId,"classOrDivision");
	parent.subsidiaryRisk = cellValue(rowId,"subsidiaryRisk");
	parent.packingGroup = cellValue(rowId,"packingGroup");
	parent.specialProvision = cellValue(rowId,"specialProvision");
	parent.limitedQuantity = cellValue(rowId,"limitedQuantity");
	parent.packingInstruction = cellValue(rowId,"packingInstruction");
	parent.specialPackingProvision = cellValue(rowId,"specialPackingProvision");
	parent.ibcSpecialProvision = cellValue(rowId,"ibcSpecialProvision");
	parent.imoTankInstruction= cellValue(rowId,"imoTankInstruction");
	parent.unTankAndBulkContInstr = cellValue(rowId,"unTankAndBulkContInstr");
	parent.tankSpecialProvision = cellValue(rowId,"tankSpecialProvision");
	parent.ems = cellValue(rowId,"ems");
	parent.stowageAndSegregation = cellValue(rowId,"stowageAndSegregation");
	parent.property = cellValue(rowId,"property");
	parent.observation = cellValue(rowId,"observation");
	parent.star = cellValue(rowId,"star");
	parent.state = cellValue(rowId,"state");
	parent.shippingNameCount = cellValue(rowId,"shippingNameCount");
	parent.technicalNameRequired = cellValue(rowId,"technicalNameRequired");
    if(cellValue (rowId,"shippingNameCount") > 0)
    	selectedInfo.innerHTML = '| <a href="#" onclick="selectedRow(); return false;">'+messagesData.selectedRowMsg+' : '+currentRow+'</a>'; 
    else
    	selectedInfo.innerHTML = '';
}


function doInitGrid() {
	mygrid = new dhtmlXGridObject('ImdgViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)

	initGridWithConfig(mygrid, config, false, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}
	 mygrid.attachEvent("onRowSelect",selectRow);
}
