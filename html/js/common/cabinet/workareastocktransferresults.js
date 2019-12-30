var windowCloseOnEsc = true;

/* The reason for this is to show the error messages from the main page */
function showErrorMessages() {
	parent.showErrorMessages();
}

function checkUpdateSuccess() {
	if (updateSuccess) {
		alert(messagesData.updatesucess);
	}
	
	if (showUpdateLinks){
		parent.document.getElementById("updateResultLink").style["display"] = "";
	}
	else {
		parent.document.getElementById("updateResultLink").style["display"] = "none";
	}
}
function submitUpdate() {
	if(validateForm()){
        parent.$("startSearchTime").value = new Date().getTime();
        /* Set any variables you want to send to the server */
		var action = $("uAction");
		action.value = 'update';
		parent.showPleaseWait();
	
		if (beanGrid != null) {
			beanGrid.parentFormOnSubmit();
		}
		/* Submit the form in the result frame */
		document.genericForm.submit();
	}
}

function validateForm(){
for (var rowId = 1; rowId <= $v('totalLines') ; rowId++) { 
		if(cellValue(rowId,"okDoUpdate") == "Y"){
			if (!isInteger(cellValue(rowId,"transferQuantity")) || parseInt(cellValue(rowId,"transferQuantity")) < 1){
				alert(messagesData.transferQuantity + " " + messagesData.positiveInteger);
				return false;
			}
			else if(parseInt(cellValue(rowId,"transferQuantity")) > parseInt(cellValue(rowId,"quantity")) ){
				alert(messagesData.transferQuantityLessThanQuantity);
				return false;
			}
		}
	}
	
	return true;
}

function setUpdate(rowId){
	if(cellValue(rowId,"transferQuantity") == '')
		setCellValue(rowId, "okDoUpdate", "");
	else
		setCellValue(rowId, "okDoUpdate", "Y");
}

