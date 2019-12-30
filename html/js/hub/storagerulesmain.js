
function setBranchPlant() {
	$("branchPlant").value = $v("hub");
}

function setDefault() {
	if ($v("selectedOpsEntityId").length > 0) {
		$("opsEntityId").value = $v("selectedOpsEntityId");
		opsChanged();
	}

	if ($v("selectedHub").length > 0) {
		$("hub").value = $v("selectedHub");
		hubChanged();
	}
}

function validateSearchForm() {
	var valid = true;
	if ($v("opsEntityId").length == 0 || $v("branchPlant").length == 0) {
		valid = false;
	}
	return valid;
}

function submitSearchForm() {
	$("userAction").value = "search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    if (validateSearchForm()) {
	    showPleaseWait();
	    return true;
    }
    else {
    	alert(messagesData.pleaseselecthub);
    	return false;
    }
}

function createXLS() {
	if (validateSearchForm()) {
		document.getElementById("userAction").value = "createXLS";
        document.genericForm.target = '_StorageRulesExcel';
        openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_StorageRulesExcel', '650', '600', 'yes');
        setTimeout("document.genericForm.submit()", 300);
        return true;
	}
	else {
		alert(messagesData.pleaseselecthub);
		return false;
	}
}