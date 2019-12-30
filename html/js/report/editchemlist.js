// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;

function submitSearchForm() {
    document.adHocInventoryReportForm.target = 'resultFrame';
    $("submitValue").value = 'editChemListSearch';
    showPleaseWait();
    document.getElementById("startSearchTime").value = new Date().getTime();
    return true;
}

function createExcel() {
    document.adHocInventoryReportForm.target = 'resultFrame';
    $("submitValue").value = 'createChemListExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ChemListGenerateExcel','650','600','yes');
    var a = window.setTimeout("document.adHocInventoryReportForm.submit();",500);
}

function retSelected(close)
{
	var selected = new Array();
	var j = 0;
	var oneSelected = false;
	for(var i = 1; i <= mygrid.getRowsNum(); i++)
	{
		if(document.getElementById('select'+i).checked == true)
			{
				selected[j++]= {listName:cellValue(i,'listName'),
							listId:cellValue(i,'listId'),
							hasThreshold:cellValue(i,'hasThreshold'),
							hasAmountThreshold:cellValue(i,'hasAmountThreshold')};
				oneSelected = true;
			}
	}
	if(oneSelected)
	{
		if($v('callerId') == 'list')
			parent.opener.populateListGrid(selected);
		else
			parent.opener.retReportList(selected);
		if(close)
			parent.window.close();
	}
	else
		alert('Nothing was selected');
}

function checkPreSelect(rowId)
{
	var lSel = parent.opener.listSelected;
	if(lSel.length > 0)
	{
		var listId = cellValue(rowId,'listId');
		 for(var i = 0; i < lSel.length;i++)
		 {
			if(lSel[i] == listId)
				document.getElementById('select'+rowId).checked = true;
		 }
	}
}
