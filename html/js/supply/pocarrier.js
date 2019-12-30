windowCloseOnEsc = true;

var beangrid;

var resizeGridWithWindow = true;

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("carrierInfoBean").style["display"] = "";

		initializeGrid();
	} else {
		document.getElementById("carrierInfoBean").style["display"] = "none";
	}

	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on or
	 * off.
	 */
	setResultFrameSize();
	
	//if exact match is found and auto return flag is Y, return the match
	if (totalLines == 1 && parent.$v("autoReturnIfMatch") == "Y" && parent.$v("searchArgument").toLowerCase() == cellValue(1, "carrierCode").toLowerCase()) {
		doOnRowSelected(1);
		parent.selectedCarrier();
	}
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('carrierInfoBean');

	initGridWithConfig(beangrid,config,false,false);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
	}	
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
}


function doOnRowSelected(rowId,cellInd) {

	var selectedCarrier = parent.document.getElementById("selectedCarrier");
	selectedCarrier.style["display"] = "";
	// Get the value of current user from selected grid row
	var carrierName = cellValue(rowId,'carrierName');
	selectedCarrier.innerHTML = '<a href="#" onclick="selectedCarrier(); return false;">'+messagesData.selectedcarrier+' : '+carrierName+'</a>';
	parent.returnSelectedCarrierCode = cellValue(rowId,'carrierCode');
	parent.returnSelectedCarrierName = cellValue(rowId,'carrierName');
	parent.returnSelectedAccount = cellValue(rowId,'account');
	parent.returnSelectedCompanyHub = cellValue(rowId,'companyHub');
	 parent.displayElementId = document.getElementById("displayElementId").value;
	 parent.valueElementId = document.getElementById("valueElementId").value;

    setResultFrameSize(); 
}

