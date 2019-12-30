windowCloseOnEsc = true;
var currentItemId = '';
var currentInventoryGroup = '';
var currentItemDesc = '';
var selectedRow = 0;

function selectRow(rowid) {
	selectedRow = rowid;
	currentItemId = cellValue(rowid, "itemId");
	currentInventoryGroup = cellValue(rowid, "inventoryGroup");
	currentItemDesc = cellValue(rowid, "description");
	
	var selectedMaterial = parent.document.getElementById("returnResultLink");
	selectedMaterial.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">' + messagesData.selectedRowMsg + ' (' + currentInventoryGroup + ' - ' + currentItemId + ')</a>';
}

function selectData() {
	try {
		parent.opener.addItem(currentItemId, currentInventoryGroup);
	}
	catch (exx) {
	}
	parent.close();
}
