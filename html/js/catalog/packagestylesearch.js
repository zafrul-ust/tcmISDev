windowCloseOnEsc = true;
var currentPkgStyle = '';
function selectRow(rowid)
{
	rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	currentPkgStyle = cellValue(rowId0,"pkgStyle");
	var selectedPkgStyle = parent.document.getElementById("updateResultLink");
	selectedPkgStyle.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">'+messagesData.selectedPackageStyle+' : '+ currentPkgStyle +'</a>';
}

function selectData() {
	try {
  		parent.opener.pkgStyleChanged(currentPkgStyle);
  	} catch(exx) {}
  parent.close();
}