var windowCloseOnEsc = true;
var selectedRow;

function selectRow(rowid) {
	selectedRow = rowid;
	
	var mainUpdateLinks = parent.document.getElementById("mainUpdateLinks");
	mainUpdateLinks.innerHTML = 
		'<span><a href="#" onclick="call(\'selectData\'); return false;">' 
		+ messagesData.selectedRowMsg 
		+ ' ' + cellValue(rowid, "itemId") 
		+ ' (' 
		+ cellValue(rowid, "itemDesc") + ')</a></span> ' + 
		'| <span><a href="#" onclick="call(\'closePage\'); return false;">'
		+ messagesData.finish + '</span>';
}

function selectData() {
	try {
		parent.opener.itemChanged(cellValue(selectedRow, "itemId"));
	} catch (exx) {}
}

function closePage() {
	try {
		parent.opener.finishItemLookup();
	} catch (exx) {}
	parent.close();
}

function validateForm() {
	if ($v('mfgId') != '' && !isInteger($v('mfgId'))) {
		alert(messagesData.invalidValue.replace("{0}", $v('mfgId')) + " " + messagesData.enterValidValue);
		return false;
	}
	
	return true;
}