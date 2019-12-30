windowCloseOnEsc = true;
var currentManufacturerId = '';
var currentManufacturerDesc = '';
var selectedRow = 0;

function onMyLoad() {
	if (parent.$v("allowNew") == 'N') {
		parent.$("updateResultLink").style["display"] = "none";
		parent.$("returnResultLink").innerHTML = '';
	}else {
		parent.$("updateResultLink").style["display"] = "";
		parent.$("returnResultLink").innerHTML = '';
	}
}

function selectRow(rowid) {
	selectedRow = rowid;
	currentManufacturer = cellValue(rowid, "id");
	currentManufacturerDesc = cellValue(rowid, "description");
	
	var selectedManufacturer = parent.document.getElementById("returnResultLink");
	var tmpPipe = '';
	if (parent.$v("allowNew") == 'N') {
		tmpPipe = '';
	}else {
		tmpPipe = ' | ';
	}
	selectedManufacturer.innerHTML = tmpPipe+'<a href="#" onclick="call(\'selectData\'); return false;">' + messagesData.selectManufacturer + ' : ' + currentManufacturerDesc + '</a>';
}

function selectData() {
	try {
		parent.opener.manufacturerChanged(currentManufacturer, currentManufacturerDesc, cellValue(selectedRow,"mfgShortName"), cellValue(selectedRow,"phone"), cellValue(selectedRow,"email"), cellValue(selectedRow,"contact"), cellValue(selectedRow,"notes"), cellValue(selectedRow,"url"));
	}
	catch (exx) {
	}
	parent.close();
}
