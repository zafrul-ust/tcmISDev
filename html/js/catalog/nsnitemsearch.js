windowCloseOnEsc = true;

function selectRow(rowid) {
	selectedRow = rowid;
	var currentItem = cellValue(rowid, "itemId");
	$("selectedItem").value = currentItem;

	var returnResultLink = parent.document.getElementById("returnResultLink");
	returnResultLink.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">' + messagesData.selectedRowMsg + ' (' + currentItem + ')</a>';
}

function selectData() {
	try {
		parent.opener.itemSelected($v("selectedItem"));
	}
	catch (exx) {
	}
	parent.close();
}