function $(a) {
	return document.getElementById(a);
}

function submitSearchForm() {
	var receiptboxlabels = document.getElementById("receiptboxlabels");
	receiptboxlabels.value = 'receiptboxlabels';
	document.genericForm.submit();
}

function checkInteger(elementName) {
	if (!(isInteger($("" + elementName + "").value))) {
		alert(messagesData.quantityIntegerError);
		$("" + elementName).value = "";
	}
}


function checkQuantityReceived(elementId, received) {
	var max = received;
	var startingId = 1;
	if (!(isInteger($("quantityReceived" + elementId).value))) {
		alert(messagesData.quantityIntegerError);
		$("quantityReceived" + elementId ).value = "";
	}
}

/** TODO Tinternationalize* */
