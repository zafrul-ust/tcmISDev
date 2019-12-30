var dhxWins = null;
windowCloseOnEsc = true;

function onLoad() {
	if($v('uAction') == 'setChangeOwnership') {
        if(showErrorMessage) {
            $("errorMessagesArea").style.display="";
        }else {
            // update line in parent window
            opener.setOwnership($v('reorderPoint'),$v('stockingLevel'),$v('kanbanReorderQuantity'),$v('reorderQuantity'),$v('leadTimeDays'),$v('countType'),$v('ownershipName'),$v('hcoFlag'),$v('dropShipOverride'));
            window.close();
        }
    }else
        displayDropShipOverride();
}

function validateForm() {
	var message = "";
	
	if (!isInteger($v('reorderPoint'), true)) {
		message += formatMessage(messagesData.integerError, messagesData.reorderPoint) + "\n";
	}
	
	if (!isInteger($v('stockingLevel'), true)) {
		message += formatMessage(messagesData.integerError, messagesData.stockingLevel) + "\n";
	}
	
	if (!isInteger($v('kanbanReorderQuantity'), true)) {
		message += formatMessage(messagesData.integerError, messagesData.kanbanReorderQuantity) + "\n";
	}
	
	if (!isInteger($v('reorderQuantity'), true)) {
		message += formatMessage(messagesData.integerError, messagesData.reorderQuantity) + "\n";
	}
	if (!isInteger($v('leadTimeDays'), true)) {
		message += formatMessage(messagesData.integerError, messagesData.leadTimeInDays) + "\n";
	}
	
	if(parseInt($v('reorderPoint')) > parseInt($v('stockingLevel'))) {
	    message += messagesData.reorderPointLessThanStockingLevel + "\n";
	}
	
	if(parseInt($v('reorderPoint')) == 0 && parseInt($v('stockingLevel')) != 0) {
	    message += messagesData.reorderPointGreaterThanZero;
	}
	
	if (message.length > 1) {
		alert(message);
		return false;
	}
	
	return true;
}

function submitSave() {
	if (validateForm()) {
		var confirmMsg = messagesData.changeownership + " " + messagesData.For + " " + messagesData.part + " " + $v('catPartNo') + "?";
		
		if(confirm(confirmMsg)){
			$('uAction').value = "setChangeOwnership";
			document.clientCabinetManagementForm.submit();
		}
	}
}

function cancel() {
	$('uAction').value = "";
	window.close();
}

function displayDropShipOverride() {
    //don't display drop ship override if haas owned
    if ($v("hcoFlag") == 'N') {
        $('dropShipOverrideTr').style.display="none";
        $('dropShipOverride').checked = false;
    }else {
        $('dropShipOverrideTr').style.display="";  
    }
}