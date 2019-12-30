windowCloseOnEsc = true;
var casNum = '';
var selectedRow = 0;

function selectRow(rowid) {
	selectedRow = rowid;
	casNum = cellValue(rowid, "casNum");
	
	var selectedMaterial = parent.document.getElementById("returnResultLink");
	if(parent.document.getElementById("fromListManagement") != null)
	{
	  selectedMaterial.innerHTML = '<a href="#" onclick="call(\'selectData\'); return false;">' + messagesData.selectedRowMsg + ' : ' + casNum + '</a>';
	  	
	}
	else{
	   selectedMaterial.innerHTML = '|&nbsp;<a href="#" onclick="call(\'selectData\'); return false;">' + messagesData.selectedRowMsg + ' : ' + casNum + '</a>';
	}
}

function selectData() {
	try {	
		
		parent.opener.addNewListchemical(casNum, cellValue(selectedRow,"preferredName"));
		
	}
	catch (exx) {
	}
	try {
		parent.opener.casNumberChanged(casNum, cellValue(selectedRow,"preferredName"));
	}
	catch (exx) {
	}
	parent.close();
}

function showLink(){
		
	if(parent.document.getElementById("fromListManagement").value != "")
	{
		parent.document.getElementById("updateResultLink").style["display"] = "none";
	}
	else
	{
		parent.document.getElementById("updateResultLink").style["display"] = "";
	}
}

