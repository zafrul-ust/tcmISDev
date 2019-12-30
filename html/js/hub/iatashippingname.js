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
		document.getElementById("IataViewBean").style["display"] = "";
		// build the grid for display
		doInitGrid();
	}
	else {
		document.getElementById("IataViewBean").style["display"] = "none";
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

    var currentRow =  cellValue (rowId,"properShippingNameDesc");
    parent.returnSelectedValue = currentRow;
    parent.iataDgId  = cellValue (rowId,"iataDgId");
    parent.packingGroup  = cellValue (rowId,"packingGroup");
    parent.identificationNumber  = cellValue (rowId,"identificationNumber");
    parent.properShippingNameDesc  = cellValue (rowId,"properShippingNameDesc");
    parent.hazmatId  = cellValue (rowId,"hazmatId");
    parent.classOrDivision  = cellValue (rowId,"classOrDivision");
    parent.technicalNameRequired  = cellValue (rowId,"technicalNameRequired");
    parent.shippingNameCount  = cellValue (rowId,"shippingNameCount");
    parent.cOnlyComment  = cellValue (rowId,"cOnlyComment");
    parent.cOnlyG  = cellValue (rowId,"cOnlyG");
    parent.cOnlyMaxNetQtyPerPkg  = cellValue (rowId,"cOnlyMaxNetQtyPerPkg");
    parent.cOnlyMaxNetUnitPerPkg  = cellValue (rowId,"cOnlyMaxNetUnitPerPkg");
    parent.cOnlyMaxNetValPerPkg  = cellValue (rowId,"cOnlyMaxNetValPerPkg");
    parent.cOnlyPkgInstr  = cellValue (rowId,"cOnlyPkgInstr");
    parent.ergCode  = cellValue (rowId,"ergCode");
    parent.hazardLabel  = cellValue (rowId,"hazardLabel");
    parent.pCComment  = cellValue (rowId,"pCComment");
    parent.pCG  = cellValue (rowId,"pCG");
    parent.pCLtdQtyG  = cellValue (rowId,"pCLtdQtyG");
    parent.pCLtdQtyMaxNetQtyPerPkg  = cellValue (rowId,"pCLtdQtyMaxNetQtyPerPkg");
    parent.pCLtdQtyMaxNetUntPerPkg  = cellValue (rowId,"pCLtdQtyMaxNetUntPerPkg");
    parent.pCLtdQtyMaxNetValPerPkg  = cellValue (rowId,"pCLtdQtyMaxNetValPerPkg");
    parent.pCLtdQtyPkgInstr  = cellValue (rowId,"pCLtdQtyPkgInstr");
    parent.pCMaxNetQtyPerPkg  = cellValue (rowId,"pCMaxNetQtyPerPkg");
    parent.pCMaxNetUnitPerPkg  = cellValue (rowId,"pCMaxNetUnitPerPkg");
    parent.pCMaxNetValPerPkg  = cellValue (rowId,"pCMaxNetValPerPkg");
    parent.pCPkgInstr  = cellValue (rowId,"pCPkgInstr");
    parent.pickable  = cellValue (rowId,"pickable");
    parent.specialProvision  = cellValue (rowId,"specialProvision");
    parent.subrisk  = cellValue (rowId,"subrisk");
    if(cellValue (rowId,"shippingNameCount") > 0)
    	selectedInfo.innerHTML = '| <a href="#" onclick="selectedRow(); return false;">'+messagesData.selectedRowMsg+' : '+currentRow+'</a>'; 
    else
    	selectedInfo.innerHTML = '';
}


function doInitGrid() {
	mygrid = new dhtmlXGridObject('IataViewBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)

	initGridWithConfig(mygrid, config, false, true, true);
	if (typeof (jsonMainData) != 'undefined') {
		mygrid.parse(jsonMainData, "json");
	}
	 mygrid.attachEvent("onRowSelect",selectRow);
}
