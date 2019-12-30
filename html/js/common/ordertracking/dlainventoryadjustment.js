var selectedRow;

function checkDocIdCode(rowId)
{
	if($v('docIdCode'+rowId) == 'DAC')  
		{
			if(cellValue(rowId,'supplyConditionCode') == 'A')
				mygrid.cellById(rowId, mygrid.getColIndexById("previousConditionCode")).setValue('F');
			else
				mygrid.cellById(rowId, mygrid.getColIndexById("previousConditionCode")).setValue('A');
		}
	else
		mygrid.cellById(rowId, mygrid.getColIndexById("previousConditionCode")).setValue(cellValue(rowId,'supplyConditionCode'));
}

function onRightClick(rowId)
{
	selectedRow = rowId;
	if(cellValue(rowId, 'lineStatus') != 'new')
		toggleContextMenu('rightClickMenu');
	else
		toggleContextMenu('');
}

function onRowSelect(rowId)
{
	selectedRow = rowId;
}

function update() {
	if (validationforUpdate()) {
		document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
		$('uAction').value = 'update';		// $('formVariableName') is a utility function that does a document.getElementById('variableName')
							// $v('formVariableName') does the same with document.getElementById('variableName').value

		parent.showPleaseWait(); // Show "please wait" while updating

		if (mygrid != null) {
			// This function prepares the grid dat for submitting top the server
			mygrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
	}
}

var oneEdit = false;

var errorMessage = '';
// validate the whole grid
function validationforUpdate() {
	var rowsNum = mygrid.getRowsNum();
	oneEdit = false;
	errorMessage = '';
	// This reflects the rowId we put in the JSON data 
	for ( var rowpos = 0; rowpos < rowsNum; rowpos++) {
		var rowId = mygrid.getRowId(rowpos);
		if (!validateLine(rowId, rowpos + 1)) 
		{
			//Select the failing line
			mygrid.selectRowById(rowId, null, false, false); 
	
		}
	}
	if(errorMessage != 0)
		{
			alert(errorMessage);
			return false;
		}

	if(oneEdit)
		return true;
	else
	{
		alert(messagesData.nothingchanged);
		return false;
	}
}


function validateLine(rowId, rowpos) {
	var count = 0;

	if (cellValue(rowId, "lineStatus") == 'new' && !isPositiveInteger(cellValue(rowId, "quantity")))
		{
			if(errorMessage.lenght == 0)
				errorMessage = messagesData.validvalues + "\n";
			errorMessage +=  "Row " + rowpos + ": " + messagesData.positiveInt.replace('{0}', messagesData.quantity) + "\n";
			return false;
		}
	else if (cellValue(rowId, "lineStatus") == 'new' || cellValue(rowId, "lineStatus") == 'delete')
		{
			oneEdit = true;
			return true;
		}

}

function removeRow()
{
	if(cellValue(selectedRow,'status') == '947 SENT')
		{
			mygrid.rowsAr[selectedRow].className = 'grid_black';
			mygrid.cellById(selectedRow, mygrid.getColIndexById("lineStatus")).setValue('delete');
		}
	else
		alert('Can not remove this record');
}

function insertRow()
{
	var rId = new Date();
	var newId = rId.getTime();
	var newData = new Array();
	var cntr = 0;
	newData[cntr++] = 'Y'; // Permission
	if($v('unitOfSale') == '')
		newData[cntr++] = 'Y'; // Permission
	else
		newData[cntr++] = 'N'; // Permission
	newData[cntr++] = $v('catPartNo');
	newData[cntr++] = '947 CREATED';
	newData[cntr++] = '';
	newData[cntr++] = 'A';
	newData[cntr++] = 'A';
	newData[cntr++] = 'D9A';
	newData[cntr++] = '1';
	newData[cntr++] = 'A';
	if($v('unitOfSale') == '')
		newData[cntr++] = 'EA';
	else
		newData[cntr++] = $v('unitOfSale');
	newData[cntr++] = 'new';
	newData[cntr++] = '';
	mygrid.addRow(newId,newData,0);
	mygrid.selectRowById(newId);	
}

function onOverride(rowId)
{
	if (cellValue(rowId, "override") == 'Y')
		mygrid.cellById(rowId, mygrid.getColIndexById("override")).setValue('N');
	else
		mygrid.cellById(rowId, mygrid.getColIndexById("override")).setValue('Y');
}
