var children = new Array();

function checkAll(checkboxname) {
	var checkall = $("checkAllPrint");
	var rowsNum = mygrid.getRowsNum();	
	var alertCount = 0;

	// Make sure the background render has rendered all rows
	renderAllRows();
	
	if( checkall.checked ) {
		for(var p = 1 ; p <= rowsNum; p ++) {
			var cid = checkboxname+p;
			if($(cid) != null && !$(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
	}
	else {
		for(var p = 1 ; p <= rowsNum; p ++) {
			var cid = checkboxname+p;
			if($(cid) != null && !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
	}
	return true;  
}

function isAtLeastOneRowChecked(checkboxname) {
	var rowsNum = mygrid.getRowsNum();	
	
	for(var p = 1 ; p <= rowsNum; p ++) {
		var cid = checkboxname+p;
		if( $(cid) != null && !$(cid).disabled && $(cid).checked) {
			return true;
		}
	}
	return false;
}

function generateLabels(labelType) {
	if (isAtLeastOneRowChecked('print')) {
		createLabels(labelType);
	}
	else {
		alert(messagesData.selectCabinet);
	}
}

function createLabels(labelType) {
	if (labelType != "generatebaseline") {
		showGeneratingWin();
	}

	var labelForm = document.getElementById("LabelPrintForm");

	// Remove any previous values in the form
	if ( labelForm.hasChildNodes() ) {
		while ( labelForm.childNodes.length >= 1 ) {
			labelForm.removeChild( labelForm.firstChild );       
		} 
	}

	addNewFormElement(labelForm, "UserAction", labelType);
	addNewFormElement(labelForm, "facilityName", $v("facilityId"));
	addNewFormElement(labelForm, "generate_labels", "yes");
	addNewFormElement(labelForm, "paperSize", "31");
	addNewFormElement(labelForm, "fromWebApp", "Y");

	var rowsNum = mygrid.getRowsNum();	
	for(var p = 1 ; p <= rowsNum ; p++) {
		var cid = "print"+p;
		if($(cid) != null && !$(cid).disabled && $(cid).checked) {
			addNewFormElement(labelForm, "cabId", cellValue(p, "cabinetId"));
		}
	}
	
	labelForm.submit();
	
	setTimeout('window.status="";',5000);
}

//helper function to add elements to the form
function addNewFormElement(inputForm, elementName, elementValue){
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", elementName);
	input.setAttribute("value", elementValue);
	inputForm.appendChild(input);
}

function rightClickRow(rowId, ColId) {
	toggleContextMenu("binDetailMenu");
}

function showBinDetails() {
	var loc = "clientcabinetbinlabel.do?uAction=searchBin" +
		"&cabinetId=" + cellValue(mygrid.getSelectedRowId(), "cabinetId") +
		"&facilityId=" + $v("facilityId");
	children[children.length] = openWinGeneric(loc,"GenerateBinLabels","900","600","yes","50","50", "no");
}

var dhxWinObject = null;

function getDhxWindow() {
	if (dhxWinObject == null) {
		dhxWinObject = new dhtmlXWindows();
		dhxWinObject.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
	var displayWin = dhxWinObject.window("Generating"); 
	if (!displayWin) {
		// create window first time
		displayWin = dhxWinObject.createWindow("Generating",0,0, 400, 200);
		displayWin.setText("");
		displayWin.clearIcon();  // hide icon
		displayWin.denyResize(); // deny resizing
		displayWin.denyPark();   // deny parking

		displayWin.attachObject("generatingWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		displayWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		displayWin.setPosition(328, 131);
		displayWin.button("minmax1").hide();
		displayWin.button("park").hide();
		displayWin.button("close").hide();
	}
	return displayWin;
}

function showGeneratingWin(messageType) {
	var displayWin = getDhxWindow();
	displayWin.setModal(true);
	displayWin.show();
}

function closeGeneratingWin() {
	var displayWin = getDhxWindow();
	displayWin.setModal(false);
	displayWin.hide();
}

