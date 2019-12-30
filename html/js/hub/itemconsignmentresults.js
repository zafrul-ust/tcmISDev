/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

var multiplePermissions = true;

//Build up the array for the columns which use different permissions
var permissionColumns = new Array();
permissionColumns = {
     "catalogPrice" : true,
     "currencyId" : true
};

function myResultOnLoadWithGrid(gridConfig) {
	try {
		/*
		 * Don't show any update links if the user does not have
		 * permissions
		 */
		if (!showUpdateLinks) {
			parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
		}
		else {
			parent.document.getElementById("mainUpdateLinks").style["display"] = "";
		}
	}
	catch (ex) {
		alert(ex);
	}
		
	initGridWithGlobal(gridConfig);

    try {
    if(parent.document.getElementById('dateDelivered').value != ''){
		document.getElementById('needdateAll').value = parent.document.getElementById('dateDelivered').value;
		setCalendarValue('dateDelivered');
		}
	}
	catch (ex) {
		//alert(ex);
	}
	/* Display the standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();
}

// When Actual Count is changed, verify it is float and check Counted if it is
function isValidQuantity(rowId, columnId) {
	var count = cellValue(rowId, columnId);
	var valid = true;
	var message = "";
	if (count != null && (count + "").length > 0) {
		if (!isFloat(count, false)) {
			message = messagesData.validvalues + "\n" + cellValue(rowId, "itemId") + " - " + messagesData.usedQuantity;
			valid = false;
		}
		else {
			count = count * 1;
			var onHand = cellValue(rowId, "inventoryQuantity") * 1;

			if (count > onHand) {
				message = formatMessage(messagesData.greaterThanOnHand, count, onHand);
				if (confirm(message)) {
					checkOKbox(rowId, true);
				}
				else {
					message = "";
					document.getElementById("usedQuantity" + rowId).value = '';
					checkOKbox(rowId,false);
					valid = false;
				}
			}
			else {
				checkOKbox(rowId,true);
			}
		}
	}
	else {
		message = messagesData.nonZeroCount;
		valid = false;
	}

	if (!valid) {
		// Select the row with the error and grab the cursor
		// there as well
		mygrid.selectRowById(rowId, null, false, false);
		document.getElementById("usedQuantity" + rowId).focus();

		// Uncheck the update box for this row
		checkOKbox(rowId,false);

		if (message.length > 0) {
			// Give the user the error message
			alert(message);
		}
	}

	return valid;

}

//When Actual price is changed, verify it is float and check priceed if it is
function isValidPrice(rowId, columnId) {
	var price = cellValue(rowId, columnId);
	var valid = true;
	var message = "";
		{
		if (price == null || parseFloat(price) == 0 || (price + "").length == 0 || !isFloat(price, false))
			{
				message = messagesData.validvalues + "\n" + messagesData.price + "\n" + messagesData.item + ": " + cellValue(rowId, "itemId"); 
				if(mygrid.getColIndexById("receiptId") != null)
					message += ", " + messagesData.receipt+": " + cellValue(rowId, "receiptId");
				checkOKbox(rowId,false);
				alert(message);
				valid = false;
			}
		}
	return valid;

}

function checkOKbox(rowId, checkedValue) {
	var okBox = $("rowUpdated" + rowId);
	if (okBox != null && !okBox.disabled) {
		cell(rowId, "rowUpdated").setValue(checkedValue);
	}
}


function updateChecked(rowId, columnId) {
	var okBox = $("rowUpdated" + rowId);
	if (okBox != null && !okBox.disabled && okBox.checked) {
		if(!isValidQuantity(rowId, "usedQuantity"))
			 return false;
		else if(!isValidPrice(rowId, "catalogPrice"))
			 return false;
	}
}

function process() {
			
		if(parent.document.getElementById('poNumber').value == '')
		{
			alert(messagesData.validvalues + "\n" + messagesData.poNumber);return;
		}
		if(validationforUpdate())
		{
			document.getElementById('poNumber').value = parent.document.getElementById('poNumber').value;
			parent.showPleaseWait();
			 if( $("poNumber") != null){
				 document.getElementById('dateDelivered').value = document.getElementById('needdateAll').value ;
			  }
			
			window.setTimeout("processUpdate()",20);	
		}
		 
		
}

function processUpdate()
	{
	  parent.window.document.getElementById("startSearchTime").value = new Date().getTime();
	  document.getElementById('poNumber').value = parent.document.getElementById('poNumber').value;
	  document.genericForm.target = '';
	  document.getElementById('uAction').value = 'update';

		if (mygrid != null) {
			mygrid.parentFormOnSubmit();
			// Call this function to send the
			// data from grid back to the server
		 }
		document.genericForm.submit();
		
	}


// validate the whole grid
function validationforUpdate() {
	
	var totalRows = mygrid.getRowsNum();
    var oneRowChecked = false;
	for ( var rowId = 1; rowId <= totalRows; rowId++) {
		var okBox = $("rowUpdated" + rowId);
		if (okBox != null && !okBox.disabled && okBox.checked) {
			oneRowChecked = true;
			 if(!isValidPrice(rowId, "catalogPrice"))
				 return false;
			 else if(!isValidQuantity(rowId, "usedQuantity"))
				 return false;
		}
	}

	if(oneRowChecked == false)
	{
		alert(messagesData.noUpdate);
		return false;
	}
	else
		return true;
	
	
}

function setCalendarValue(hcalName)
{
  var setHcalAll = null;
  switch (hcalName)
  {
  		case "dateDelivered":
 			setHcalAll = $("needdateAll");
 			break;
 	    
 		default:
 			break;
  }
  var rowsNum = mygrid.getRowsNum();
  var result;

  rowsNum = rowsNum*1;
	renderAllRows();
  if (setHcalAll.value.trim().length != 0)
  {
    result = setHcalAll.value.trim();
  }
  else
  {
    result = '';
  }
  
  for (var p = 1 ; p < (rowsNum+1) ; p ++)
  {
	try 
	{
	  mygrid.cells(p, mygrid.getColIndexById(hcalName)).setValue(result);
	}
	catch (ex1)
	{}
  }
  parent.document.getElementById('dateDelivered').value = document.getElementById('needdateAll').value ;
  return true;
}
