var mygrid;

// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

/*
 * This is really the same as before. Except now there is a call to initialize
 * the grid.
 */

function selectRightclick(rowId, cellInd) {

	mygrid.selectRowById(rowId, null, false, false);
	selectRow(rowId, cellInd); // The right click event needs to call
					// selectRow function.
	// Show right-click menu
	if ($v("isWmsHub") == 'Y')
		toggleContextMenu('rightClickMenuWmsHub');
	else if(showUpdateLinks)
		toggleContextMenu('rightClickMenu');
	else
		toggleContextMenu('rightClickMenuSplit');
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId; // set global variable, selectedRowId, here for
				// later use, eg. right click, etc
	// Use onChange to do the validation
	// This is just an example what we can do here
	/*
	 * var okValue= cellValue(rowId,"okDoUpdate"); var columnId =
	 * mygrid.getColumnId(cellInd);
	 * 
	 * switch (columnId) { case "okDoUpdate": if( okValue != "true" ) // If
	 * a hchstatus checkbox is checked, its value equals to String "true".
	 * return; else validateLine(rowId); break; default: };
	 */
	 for(var i=0;i<lineMap4.length;i++)
			if(rowId == lineMap4[i])
				haasGrid.rowsAr[lineMap4[i]+1].className = "grid_yellow";

}

function reversePick()
{
    var revPickUrl = "reversepick.do?prNumber=" + cellValue(selectedRowId,"prNumber") + "&lineItem=" + cellValue(selectedRowId,"lineItem") + "&picklistId=" + cellValue(selectedRowId,"picklistId");
 	
    if ($v("personnelCompanyId") == 'Radian') 
    	revPickUrl = "/tcmIS/hub/" + revPickUrl;
    
    parent.children[parent.children.length] = openWinGeneric(revPickUrl,"Reverse_Picking_1111","300","150","yes","80","80","yes");
}

function showProjectDocuments()
{
    //var recptDocsUrl = "/tcmIS/hub/receiptdocuments.do?receiptId=" + cellValue(selectedRowId,"receiptId") + "&showDocuments=Yes&inventoryGroup=" + cellValue(selectedRowId,"inventoryGroup");
    var recptDocsUrl = "receiptdocuments.do?receiptId=" + cellValue(selectedRowId,"receiptId") + "&showDocuments=Yes&inventoryGroup=" + cellValue(selectedRowId,"inventoryGroup");
    
    if ($v("personnelCompanyId") == 'Radian') 
    	recptDocsUrl = "/tcmIS/hub/" + recptDocsUrl;
    
    parent.children[parent.children.length] = openWinGeneric(recptDocsUrl,"viewReceiptsTitle222","450","300","yes");
}

function doRecevingLabels()
{
	var printreceiptboxlabelsUrl = "printreceiptboxlabels.do?labelReceipts=" + cellValue(selectedRowId,"receiptId") + "&paperSize=64";
	
	if ($v("personnelCompanyId") == 'Radian') 
		printreceiptboxlabelsUrl = "/tcmIS/hub/" + printreceiptboxlabelsUrl;
	
	parent.children[parent.children.length] = openWinGeneric(printreceiptboxlabelsUrl,"printReceiptboxLabels","640","600","yes");
}

function showShippingInfo()
{
    //var shippingInfoUrl = "/tcmIS/hub/shippinginfo.do?uAction=search&itemId=" + cellValue(selectedRowId,"item");
    var shippingInfoUrl = "shippinginfo.do?uAction=search&itemId=" + cellValue(selectedRowId,"item");
    
    if ($v("personnelCompanyId") == 'Radian') 
    	shippingInfoUrl = "/tcmIS/hub/" + shippingInfoUrl;
    
    parent.children[parent.children.length] = openWinGeneric(shippingInfoUrl,"shippingInfo","640","600","yes");
}

function callClickOk(rowId) {
	var cid = "ok" + rowId;
	mygrid.cells(rowId,mygrid.getColIndexById("ok")).setValue(true);
	updateHchStatusA("ok" + rowId);
}

function validationForConfirm(rowId)
{
		if(rowId != null)
		{
		   var qtyPicked = cellValue(rowId,"quantityPicked");
		   if(qtyPicked != "")
			{
			  oneRowChecked = true;
			  var pickQuantity = cellValue(rowId,"picklistQuantity");
	   		  if(pickQuantity == "")
		   			pickQuantity = "0";
		   	  if(qtyPicked == "")
		   			qtyPicked = "0";
		   	  if(!isInteger(qtyPicked))
		   	  {
		   		  	alert(messagesData.mustBeAnInteger.replace(/[{]0[}]/g,messagesData.qPick));
		   		  	mygrid.cellById(rowId,mygrid.getColIndexById("quantityPicked")).setValue("");
		   			return false;
		   	  }
		   	  else if(parseInt(qtyPicked) > parseInt(pickQuantity))
		   		  {
					    var message = messagesData.qtypickedlessthanavailableqty.replace(/[{]0[}]/g,qtyPicked);
					    if (!confirm(message))
					    {
				   		  	mygrid.cellById(rowId,mygrid.getColIndexById("quantityPicked")).setValue("");
					    	return false;
					    }
		   		  }
		   	  else if (pickQuantity != qtyPicked)
			  {
				    var message = messagesData.diffQty1 + qtyPicked  + messagesData.diffQty2 + pickQuantity + messagesData.diffQty3;
				    if (! confirm(message))
				    {
			   		  	mygrid.cellById(rowId,mygrid.getColIndexById("quantityPicked")).setValue("");
				    	return false;
				    }
			  }
		   	  callClickOk(rowId);
			}
		}
		return true;
}
function validate()
{
  var oneRowChecked = false;
  var passVal = false;
  var size = mygrid.getRowsNum();
   for(var i=0;i<size;i++)
   {
	var qtyPicked = cellValue(i+1,"quantityPicked");
	   if( cellValue(i+1,"quantityPickedPermission") == 'Y' && qtyPicked != "")
		   {
		   		var rowId = mygrid.getRowId(i);
		   		passVal = validationForConfirm(rowId)
		   		oneRowChecked = true;
		   }
   }
   if(!oneRowChecked)
	   {
		   	alert(messagesData.pleasemakeselection);
			return false;
	   }
   else if(!passVal)
	   return false;
   else
	   return true;
}
	   

function doConfirm() {
	if (validate()) 
	{
		document.genericForm.target = '';
		document.getElementById('userAction').value = 'confirm';

		parent.showPleaseWait(); // Show "please wait" while
						// updating

		if (mygrid != null)
			mygrid.parentFormOnSubmit(); // Got to call this
							// function to send the
							// data from grid back
							// to the server

		document.genericForm.submit(); // back to server
	}
}


//to perform the check all function in the header.
function checkAll(checkboxname) {
	var checkall = $("chkAllOkDoUpdate");
	var rowsNum = mygrid.getRowsNum(); // Get the total rows of the grid
	var columnId = mygrid.getColIndexById(checkboxname);

	if (checkall.checked) {
		for ( var rowId = 1; rowId <= rowsNum; rowId++) {
			if(cellValue(rowId, "permission") == 'Y') {
				var curCheckBox = checkboxname + rowId;
				if ($(curCheckBox)) { // Make sure the row has been rendered and the element exists
					$(curCheckBox).checked = true;
					updateHchStatusA(curCheckBox);
				}
				else { // The HTML element hasn't been drawn yet, update the JSON data directly
					mygrid._haas_json_data.rows[mygrid.getRowIndex(rowId)].data[columnId] = true;
				}
			}
		}
	}
	else {
		for ( var rowId = 1; rowId <= rowsNum; rowId++) {
			if(cellValue(rowId, "permission") == 'Y') {
				var curCheckBox = checkboxname + rowId;
				if ($(curCheckBox)) {  // Make sure the row has been rendered and the element exists
					$(curCheckBox).checked = false;
					updateHchStatusA(curCheckBox);
				}
				else { // The HTML element hasn't been drawn yet, update the JSON data directly
					mygrid._haas_json_data.rows[mygrid.getRowIndex(rowId)].data[columnId] = false;
				}
			}
		}
	}
	return true;
}

function setMrClose(checkBox,index)
{
	var currMrLine = null;
	var rowsNum = mygrid.getRowsNum(); 
	for(var i = 1; i <= rowsNum;i++)
	{
		if(checkBox.checked)
		{
			if(currMrLine == null)
				{
					mygrid.cells(index+i,mygrid.getColIndexById("closeMr")).setValue("Y");
					currMrLine = mygrid.cells(index+i,mygrid.getColIndexById("mrLine")).getValue();
				}
			else
			{
				var closeMr = mygrid.cells(index+i,mygrid.getColIndexById("mrLine")).getValue();
				if(currMrLine == closeMr)
					mygrid.cells(index+i,mygrid.getColIndexById("closeMr")).setValue("Y");
				else
					break;
			}
		}
		else
		{
			if(currMrLine == null)
			{
				mygrid.cells(index+i,mygrid.getColIndexById("closeMr")).setValue("N");
				currMrLine = mygrid.cells(index+i,mygrid.getColIndexById("mrLine")).getValue();
			}
			else
			{
				var closeMr = mygrid.cells(index+i,mygrid.getColIndexById("mrLine")).getValue();
				if(currMrLine == closeMr)
					mygrid.cells(index+i,mygrid.getColIndexById("closeMr")).setValue("N");
				else
					break;
			}
		}
	}
	updateHchStatusA(checkBox.id);
	

	//example MR is 3893595
	
	return true;
}


