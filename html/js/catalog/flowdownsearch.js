windowCloseOnEsc = true;
var currentFlowdown = '';
function selectRow(rowid)
{
	rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	currentFlowdown = cellValue(rowId0,"flowDown");
	currentFlowdownDesc = cellValue(rowId0,"flowDownDesc");
	var selectedFlowdown = parent.document.getElementById("updateResultLink");
	selectedFlowdown.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">'+messagesData.selectFlowdown+' : '+ currentFlowdownDesc +'</a>';
}

function selectData() {
	try {
		parent.closeTransitWin = false;
		parent.opener.newFlowdown(currentFlowdown);
  	} catch(exx) {}
  	parent.close();
}