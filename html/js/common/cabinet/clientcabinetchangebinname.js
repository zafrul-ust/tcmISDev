var dhxWins = null;
var showErrorMessage = false;
windowCloseOnEsc = true;

function onLoad() {
	if($v('uAction') == 'setChangeBinName') {
	    // update line in parent window
	    opener.setBinName($v('binId'), $v('newBinName'));
	    
	    if(showErrorMessage)
	    	$("errorMessagesArea").style.display="";
	    else
	    	window.close();
	}
}

function validateForm() {
	var message = "";
	
	return true;
}

function submitSave() {
	if (validateForm()) {
		$('uAction').value = "setChangeBinName";
		document.clientCabinetManagementForm.submit();
	}
}

function cancel() {
	$('uAction').value = "";
	window.close();
}

