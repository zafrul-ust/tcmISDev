windowCloseOnEsc = true;
var currentMaterialId = '';
var currentMfgId = '';
var currentMaterialDesc = '';
var currentTradeName = '';
var selectedRow = 0;
var productCode = '';
var customerOnlyMsds = false;
function selectRow(rowid) {
	selectedRow = rowid;
	currentMaterial = cellValue(rowid, "id");
	currentMfgId = cellValue(rowid, "mfgId");
	currentMaterialDesc = cellValue(rowid, "description");
	currentTradeName = cellValue(rowid, "tradeName");
	productCode = cellValue(rowid, "productCode");
	customerOnlyMsds = cellValue(rowid, "customerOnlyMsds");
	
	var selectedMaterial = parent.document.getElementById("returnResultLink");
	if ($v("multiselect") == "Y") {
		selectedMaterial.innerHTML = '<a href="#" onclick="call(\'addData\'); return false;">' + messagesData.selectedRowMsg + ' (' + currentMaterial + ' - ' + currentMaterialDesc + ')</a>'
									+ ' | <a href="#" onclick="call(\'closeLookup\'); return false;">' + messagesData.finish + '</a>';
	}
	else {
		selectedMaterial.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">' + messagesData.selectedRowMsg + ' (' + currentMaterial + ' - ' + currentMaterialDesc + ')</a>';
	}
	
	hideNewMaterialLink();
}

function hideNewMaterialLink() {
	if ($v("allowNew") == "N") {
		parent.document.getElementById("updateResultLink").style.display = "none";
	}
}

function addData() {
	try {
		parent.opener.materialChanged(currentMaterial, currentMaterialDesc, currentTradeName,currentMfgId,productCode,customerOnlyMsds, true);
	}
	catch (exx) {
	}
}

function closeLookup() {
	parent.opener.finishMaterialLookup();
	parent.close();
}

function selectData() {
	try {
		parent.opener.materialChanged(currentMaterial, currentMaterialDesc, currentTradeName,currentMfgId,productCode,customerOnlyMsds, true);
	}
	catch (exx) {
	}
	parent.close();
}
