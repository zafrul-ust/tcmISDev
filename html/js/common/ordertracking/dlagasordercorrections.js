function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	document.genericForm.target = 'resultFrame';
	document.getElementById("uAction").value = 'search';
	// set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	if (flag) {
		showPleaseWait();
		return true;
	}
	else {
		return false;
	}
}

function validateForm()
{
	if($v('releaseNum') == '')
		{
			alert('Please enter a release number');
			return false;
		}
	return true
}

function update() {
	if (validationforUpdate()) 
	{
		document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
		$('uAction').value = 'update';		// $('formVariableName') is a utility function that does a document.getElementById('variableName')
							// $v('formVariableName') does the same with document.getElementById('variableName').value
		
		// Reset search time for update
		var now = new Date();
		var startSearchTime = parent.document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();	

		parent.showPleaseWait(); // Show "please wait" while updating

		if (mygrid != null) {
			// This function prepares the grid dat for submitting top the server
			mygrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
	}
}

//validate the whole grid
function validationforUpdate() {
	var rowsNum = mygrid.getRowsNum();
	var err = "";
	var oneSelected = false;
	// This reflects the rowId we put in the JSON data 
	for (var rowId = 1; rowId <= rowsNum; rowId++) {
		var inspRequired = $('okDoUpdate'+rowId);
		if ( inspRequired != null && inspRequired != undefined && inspRequired.checked)
		{
			oneSelected = true;
			err += validateLine(rowId);
		}
	}
	
	if(!oneSelected)
	{
		alert(messagesData.norowselected);
		return false;
	}
	else if(err != "")
		{
			alert(messagesData.validvalues + "\n\n" + err);
			return false;
		}
	
	return true;
}


var monthAbbrev = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
//validate one line here
function validateLine(rowId) {
	var errorMessage = "";
	var count = 0;
	//validate the header asn data in this if and the detail data in the else
	if(cellValue(rowId, "isHeaderRecord") == 'true')
		{		
	
			var inspRequired = $('inspRequired'+rowId);
			if ( inspRequired != null && inspRequired != undefined && inspRequired.value.length > 0 && inspRequired.value != 'Y') {
				errorMessage += "Line " + rowId + " valid values for Inspection Required are only Y or no value\n";
			}
		
			var inspDodaac = $('inspDodaac'+rowId);
			if ( inspDodaac != null && inspDodaac != undefined && inspDodaac.value.length > 6) {
				errorMessage += "Line " + rowId + " Inspector Dodaac is too long\n";
			}
		
			var inspDodaac = $('inspDodaac'+rowId);
			if ( inspDodaac != null && inspDodaac != undefined && inspDodaac.value.length > 6) {
				errorMessage += "Line " + rowId + " Inspector Dodaac is too long\n";
			}
			
			var inspZip = $('inspZip'+rowId);
			if ( inspZip != null && inspZip != undefined && inspZip.value.length > 0 && !checkZIPCode(inspZip,false)) {
				errorMessage += "Line " + rowId + " Inspector Zip is not valid\n";
			}
			var inspEmail = $('inspEmail'+rowId);
			if (inspEmail != null && inspEmail != undefined &&  inspEmail.value.length > 0 && !checkEmail(inspEmail,false)) {
				errorMessage += "Line " + rowId + " Inspector Email is not valid\n";
			}
			var inspPhone = $('inspPhone'+rowId);
			if (inspPhone != null && inspPhone != undefined &&  inspPhone.value.length > 0 && !checkUSPhone(inspPhone,false)) {
				errorMessage += "Line " + rowId + " Inspector Phone is not valid\n";
			}

			var inspFaxNumber = $('inspFaxNumber'+rowId);
			if (inspFaxNumber != null && inspFaxNumber != undefined &&  inspFaxNumber.value.length > 0 && !checkUSPhone(inspFaxNumber,false)) {
				errorMessage += "Line " + rowId + " Inspector Fax is not valid\n";
			}

			var inspPhoneExtension = $('inspPhoneExtension'+rowId);
			if (inspPhoneExtension != null && inspPhoneExtension != undefined &&  inspPhoneExtension.value.length > 0 && !isPositiveInteger(inspPhoneExtension.value,false)) {
				errorMessage += "Line " + rowId + " Inspector Phone Ext is not valid\n";
			}					
			var inspState = $('inspState'+rowId);
			if (inspState != null && inspState != undefined &&  inspState.value.length > 0 && !checkStateCode(inspState,false)) {
				errorMessage += "Line " + rowId + " Inspector State is not valid\n";
			}		
			var estimateDeliveryDate = $('estimateDeliveryDate'+rowId);
			if ( estimateDeliveryDate != null && estimateDeliveryDate != undefined && estimateDeliveryDate.value.length == 0) {
				errorMessage += "Line " + rowId + " Estimate Delivery Date is blank\n";
			}
	
			var shipDate = $('shipDate'+rowId);
			if ( shipDate != null && shipDate != undefined && shipDate.value.length == 0) {
				errorMessage += "Line " + rowId + " Ship Date is blank\n";
			}
			var itemDescription = $('itemDescription'+rowId);
			if ( itemDescription != null && itemDescription != undefined && itemDescription.value.length == 0) {
				errorMessage += "Line " + rowId + " Item Description is blank\n";
			}

			var polineQtyShippedInShipment = $('polineQtyShippedInShipment'+rowId);
			if ( polineQtyShippedInShipment != null && polineQtyShippedInShipment != undefined && (polineQtyShippedInShipment.value.length == 0 || !isFloat(polineQtyShippedInShipment.value))) {
				errorMessage += "Line " + rowId + " Quantity Shipped is invalid\n";
			}
			
			var transactionRefNum = $('transactionRefNum'+rowId);
			if (transactionRefNum != null && transactionRefNum != undefined && transactionRefNum.value.length == 0) {
				errorMessage += "Line " + rowId + " Transaction Ref Num is not valid\n";
			}
			var transportationControlNum = $('transportationControlNum'+rowId);
			if (transportationControlNum != null && transportationControlNum != undefined && transportationControlNum.value.length == 0) {
				errorMessage += "Line " + rowId + " Transportation Control Num is not valid\n";
			}
			var contractOffice = $('contractOffice'+rowId);
			if (contractOffice != null && contractOffice != undefined && contractOffice.value.length == 0) {
				errorMessage += "Line " + rowId + " Contract Office is not valid\n";
			}
			var contractOfficeAlternateName = $('contractOfficeAlternateName'+rowId);
			if (contractOfficeAlternateName != null && contractOfficeAlternateName != undefined && contractOfficeAlternateName.value.length == 0) {
				errorMessage += "Line " + rowId + " Contract Office Alternate Name is not valid\n";
			}
			var payofficeId = $('payofficeId'+rowId);
			if (payofficeId != null && payofficeId != undefined && payofficeId.value.length == 0) {
				errorMessage += "Line " + rowId + " Pay Office Id is not valid\n";
			}
			var payofficeName = $('payofficeName'+rowId);
			if (payofficeName != null && payofficeName != undefined && payofficeName.value.length == 0) {
				errorMessage += "Line " + rowId + " Pay Office Name is not valid\n";
			}
			var payofficeAdditionalName = $('payofficeAdditionalName'+rowId);
			if (payofficeAdditionalName != null && payofficeAdditionalName != undefined && payofficeAdditionalName.value.length == 0) {
				errorMessage += "Line " + rowId + " Pay Office Additional Name is not valid\n";
			}
			var itemDescription = $('itemDescription'+rowId);
			if (itemDescription != null && itemDescription != undefined && itemDescription.value.length == 0) {
				errorMessage += "Line " + rowId + " Item Description is not valid\n";
			}
			var contractOfficeCode = $('contractOfficeCode'+rowId);
			if (contractOfficeCode != null && contractOfficeCode != undefined && contractOfficeCode.value.length == 0) {
				errorMessage += "Line " + rowId + " Contract Office Code is not valid\n";
			}
			
			var contractOfficeAlternateName = $('contractOfficeAlternateName'+rowId);
			if (contractOfficeAlternateName != null && contractOfficeAlternateName != undefined && contractOfficeAlternateName.value.length == 0) {
				errorMessage += "Line " + rowId + " Contract Office Alternate Name is not valid\n";
			}
			var contractOfficeCity = $('contractOfficeCity'+rowId);
			if (contractOfficeCity != null && contractOfficeCity != undefined && contractOfficeCity.value.length == 0) {
				errorMessage += "Line " + rowId + " Contract Office City is not valid\n";
			}
			var contractOfficeState = $('contractOfficeState'+rowId);
			if (contractOfficeState != null && contractOfficeState != undefined && contractOfficeState.value.length == 0) {
				errorMessage += "Line " + rowId + " Contract Office State is not valid\n";
			}
			var contractOfficeCountry = $('contractOfficeCountry'+rowId);
			if (contractOfficeCountry != null && contractOfficeCountry != undefined && contractOfficeCountry.value.length == 0) {
				errorMessage += "Line " + rowId + " Contract Office Country is not valid\n";
			}
			
			var contractOfficeState = $('contractOfficeState'+rowId);
			if (contractOfficeState != null && contractOfficeState != undefined &&  contractOfficeState.value.length > 0 && !checkStateCode(contractOfficeState,false)) {
				errorMessage += "Line " + rowId + " Contract Office State is not valid\n";
			}

			var contractOfficeZip = $('contractOfficeZip'+rowId);
			if ( contractOfficeZip != null && contractOfficeZip != undefined && contractOfficeZip.value.length > 0 && !checkZIPCode(contractOfficeZip,false)) {
				errorMessage += "Line " + rowId + " Contract Office Zip is not valid\n";
			}
			
			var payofficeCity = $('payofficeCity'+rowId);
			if (payofficeCity != null && payofficeCity != undefined && payofficeCity.value.length == 0) {
				errorMessage += "Line " + rowId + " Pay Office City is not valid\n";
			}
			var payofficeState = $('payofficeState'+rowId);
			if (payofficeState != null && payofficeState != undefined && (payofficeState.value.length == 0 || !checkStateCode(payofficeState,false))) {
				errorMessage += "Line " + rowId + " Pay Office State is not valid\n";
			}
			var payofficeZip = $('payofficeZip'+rowId);
			if (payofficeZip != null && payofficeZip != undefined && (payofficeZip.value.length == 0 || !checkZIPCode(payofficeZip,false))) {
				errorMessage += "Line " + rowId + " Pay Office Zip is not valid\n";
			}
			var payofficeCountry = $('payofficeCountry'+rowId);
			if (payofficeCountry != null && payofficeCountry != undefined && payofficeCountry.value.length == 0) {
				errorMessage += "Line " + rowId + " Pay Office Country is not valid\n";
			}
			
			var shiptoDodaac = $('shiptoDodaac'+rowId);
			if ( shiptoDodaac != null && shiptoDodaac != undefined && (shiptoDodaac.value.length == 0 || shiptoDodaac.value.length > 6)) {
				errorMessage += "Line " + rowId + " Ship to Dodaac is not valid\n";
			}
			
			var shiptoPartyName = $('shiptoPartyName'+rowId);
			if (shiptoPartyName != null && shiptoPartyName != undefined && shiptoPartyName.value.length == 0) {
				errorMessage += "Line " + rowId + " Ship to Party Name is not valid\n";
			}
			var shiptoCity = $('shiptoCity'+rowId);
			if (shiptoCity != null && shiptoCity != undefined && shiptoCity.value.length == 0) {
				errorMessage += "Line " + rowId + " Ship to City is not valid\n";
			}
			var shiptoState = $('shiptoState'+rowId);
			if (shiptoState != null && shiptoState != undefined && shiptoState.value.length == 0) {
				errorMessage += "Line " + rowId + " Ship to State is not valid\n";
			}
			var shiptoZip = $('shiptoZip'+rowId);
			if (shiptoZip != null && shiptoZip != undefined &&  !checkZIPCode(shiptoZip,false)) {
				errorMessage += "Line " + rowId + " Ship to Zip is not valid\n";
			}
			var shiptoCountry = $('shiptoCountry'+rowId);
			if (shiptoCountry != null && shiptoCountry != undefined && shiptoCountry.value.length == 0) {
				errorMessage += "Line " + rowId + " Ship to Country is not valid\n";
			}
			var usgovShipmentId = $('usgovShipmentId'+rowId);
			if (usgovShipmentId != null && usgovShipmentId != undefined && usgovShipmentId.value.length == 0) {
				errorMessage += "Line " + rowId + " US Gov Shipment Id is not valid\n";
			}	
			var shipmentId = $('shipmentId'+rowId);
			if (shipmentId != null && shipmentId != undefined && shipmentId.value.length > 0 && !isPositiveInteger(shipmentId.value)) {
				errorMessage += "Line " + rowId + " Shipment Id is not valid\n";
			}	
		}
	else
		{
			var boxId = $('boxId'+rowId);
			if (boxId != null && boxId != undefined && boxId.value.length == 0) {
				errorMessage += "Line " + rowId + " Box Id is not valid\n";
			}
			var shipFromLocationId = $('shipFromLocationId'+rowId);
			if (shipFromLocationId != null && shipFromLocationId != undefined && shipFromLocationId.value.length == 0) {
				errorMessage += "Line " + rowId + " Ship From Location Id is not valid\n";
			}
			var boxRfid = $('boxRfid'+rowId);
			if (boxRfid != null && boxRfid != undefined && boxRfid.value.length == 0) {
				errorMessage += "Line " + rowId + " Box Rf Id is not valid\n";
			}
			var polineQtyShippedInBox = $('polineQtyShippedInBox'+rowId);
			if (polineQtyShippedInBox != null && polineQtyShippedInBox != undefined && (polineQtyShippedInBox.value.length == 0|| !isFloat(polineQtyShippedInBox.value))) {
				errorMessage += "Line " + rowId + " Parts Per Box is not valid\n";
			}
			var unitPrice = $('unitPrice'+rowId);
			if (unitPrice != null && unitPrice != undefined && (unitPrice.value.length == 0 || !isFloat(unitPrice.value))) {
				errorMessage += "Line " + rowId + " Unit Price is not valid\n";
			}
			var uom = $('uom'+rowId);
			if (uom != null && uom != undefined && uom.value.length == 0) {
				errorMessage += "Line " + rowId + " UOM is not valid\n";
			}
		}


	return errorMessage;
}

function onRightClick(row)
{
	selectedRowId = row;
	if(cellValue(row, 'updateInspData') == 'N' && row == 1)
	{
		toggleContextMenu('rightClickMenu');
	}
	else
		toggleContextMenu('');
}

function selectRightClick(rowId, colId) {
	var invoicesStr = invoicesMenuData[cellValue(rowId, "prNumber") + ',' + cellValue(rowId, "shipmentId")];
	if (invoicesStr != null && invoicesStr.length > 0) {
		var invoices = invoicesStr.split(',');
		var vitems = new Array();
		for (var i = 0; i < invoices.length; i++)
			vitems[i] = "text=" + invoices[i] + ";url=javascript:resubmitInvoice(" + invoices[i] + ");";
		replaceMenu('resendInvoiceMenu', vitems);
		toggleContextMenu('rightClickMenu');
	} else
		toggleContextMenu('contextMenu');
}

function replaceMenu(menuname,menus){

    var i = mm_returnMenuItemCount(menuname);

    for(;i> 1; i-- )
          mm_deleteItem(menuname,i);

    for( i = 0; i < menus.length; ){
      var str = menus[i];

      i++;
      mm_insertItem(menuname,i,str);
      // delete original first item.
      if( i == 1 ) mm_deleteItem(menuname,1);
    }
}   //end of method

function resubmitInvoice(invoice) {
	document.genericForm.target = ''; 		// Form name "genericForm" comes from struts config file
	$('uAction').value = 'resubmit';		// $('formVariableName') is a utility function that does a document.getElementById('variableName')
	$('invoiceToResubmitId').value = invoice;	// $v('formVariableName') does the same with document.getElementById('variableName').value
	
	// Reset search time for update
	var now = new Date();
	var startSearchTime = parent.document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();	

	parent.showPleaseWait(); // Show "please wait" while updating

	if (mygrid != null) {
		// This function prepares the grid dat for submitting top the server
		mygrid.parentFormOnSubmit();
	}

	document.genericForm.submit(); // Submit the form
}

function editInspectionInfo()
{
	var rowId = selectedRowId;
	var oldRowPostion = mygrid.getRowIndex(rowId);
	var newData = new Array();
	var cntr = 0;
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'N'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	//newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = true; 
	newData[cntr++] = cellValue(rowId, "releaseNum");
	newData[cntr++] = cellValue(rowId, "prNumber");
	newData[cntr++] = cellValue(rowId, "estimateDeliveryDate");
	newData[cntr++] = cellValue(rowId, "shipDate");
	newData[cntr++] = cellValue(rowId, "boxId");
	newData[cntr++] = cellValue(rowId, "boxRfid");
	newData[cntr++] = cellValue(rowId, "caseRfid");
	newData[cntr++] = cellValue(rowId, "asnId");
	newData[cntr++] = cellValue(rowId, "releaseNum");
	newData[cntr++] = cellValue(rowId, "prNumber");
	newData[cntr++] = cellValue(rowId, "oldBoxId");
	newData[cntr++] = cellValue(rowId, "oldBoxRfid");
	newData[cntr++] = cellValue(rowId, "shipFromCageCode");
	newData[cntr++] = cellValue(rowId, "shipFromLocationId");
	newData[cntr++] = cellValue(rowId, "inspDodaac");
	newData[cntr++] = cellValue(rowId, "inspName");
	newData[cntr++] = cellValue(rowId, "inspEmail");
	newData[cntr++] = cellValue(rowId, "inspPhone");
	newData[cntr++] = cellValue(rowId, "inspAddressLine1");
	newData[cntr++] = cellValue(rowId, "inspAddressLine2");
	newData[cntr++] = cellValue(rowId, "inspAddressLine3");
	newData[cntr++] = cellValue(rowId, "inspCity");
	newData[cntr++] = cellValue(rowId, "inspState");
	newData[cntr++] = cellValue(rowId, "inspZip");
	newData[cntr++] = 'Y';
	
	mygrid.deleteRow(rowId);
	mygrid.addRow(rowId, newData, oldRowPostion);
	mygrid.selectRowById(rowId);	
}
