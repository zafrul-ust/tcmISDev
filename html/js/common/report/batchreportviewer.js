function $(a) {
	return document.getElementById(a);
}
function myOnload() {
	parent.document.getElementById('transitPage').style["display"]="none";
	parent.document.getElementById('resultFrameDiv').style["display"]="";
	setResultFrameSize();
	if( showUpdateLinks == true ) {
		parent.document.getElementById('updateResultLink').style["display"]="";
	}
	else {
		parent.document.getElementById('updateResultLink').style["display"]="none";
	}
}

function deleteReport() {
	getCheckedRow();
	document.getElementById('action').value="deleteReport";
	document.genericForm.target='resultFrame';
	parent.showPleaseWait();
	document.genericForm.submit();
}

function getCheckedRow() {
	var totalLines = $('totalLines').value;
	for(var i = 0; i < totalLines; i++) {
		var ok = $('deleteReport'+i).checked;
		if( ok ) {
			$('modified'+i).value = "delete";
		}else {
			$('modified'+i).value = "";
		}
	}
}

function checkAll(j) {
	var checked = $('ok'+j).checked;
	var totalLines = $('totalLines').value;
	for(var i = 0; i < totalLines; i++) {
		var ok = $('deleteReport'+i);
		if( checked ) {
			ok.checked = true;
			$('modified'+i).value = "delete";
			if ($('ok'+i) != null) {
				$('ok'+i).checked = true;
			}
		}else {
			ok.checked = false;
			$('modified'+i).value = "";
			if ($('ok'+i) != null) {
				$('ok'+i).checked = false;
			}
		}
	}
}

function createXSL() {
	document.getElementById('action').value="createExcel";
	document.genericForm.target='_excel_report_file';
	openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
	setTimeout("document.genericForm.submit()",300);
}

function showErrorMessages()
{
		parent.showErrorMessages();
}
