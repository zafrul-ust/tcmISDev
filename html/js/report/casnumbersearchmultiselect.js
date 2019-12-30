windowCloseOnEsc = true;

function retSelected(close)
{
	var selected = new Array();
	var j = 0;
	var oneSelected = false;
	for(var i = 1; i <= mygrid.getRowsNum(); i++)
	{
		if(document.getElementById('select'+i).checked == true)
			{
				selected[j++]= {casNum:cellValue(i,'casNum'),
						preferredName:cellValue(i,'preferredName')};
				oneSelected = true;
			}
	}
	if(oneSelected)
	{
		parent.opener.populateCasGrid(selected);
		if(close)
			parent.window.close();
	}
	else
		alert('Nothing was selected');
}

function checkPreSelect(rowId)
{
	var cSel = parent.opener.casSelected;
	var cas = cellValue(rowId,'casNum');
	 for(var i = 0; i < cSel.length;i++)
	 {
		if(cSel[i] == cas)
			document.getElementById('select'+rowId).checked = true;
	 }
}


