function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("expertReviewItemBean").style["display"] = "";
		initGridWithGlobal(gridConfig); 
	}
	else {
		document.getElementById("expertReviewItemBean").style["display"] = "none";
	}
	
	//setResultFrameSize();
	if (showUpdateLinks) {
		$("mainUpdateLinks").style.display = "";
	}
	
	if (showErrors) {
		$("errorMessagesArea").style.display = "";
	}
}

function cancel() {
	var totallines = $v("totalLines");
	for ( var i = 1; i <= totallines; i++) {
		try {
			var checkbox = $("ok"+i);
			var status = gridCellValue(beanGrid, i, "status");
			if (status == 'A') {
				if (checkbox != null) {
					checkbox.checked = true;
				}
			}
			else {
				if (checkbox != null) {
					checkbox.checked = false;
				}
			}
			var modifiedCheckbox = $("modified"+i);
			if (modifiedCheckbox != null) {
				modifiedCheckbox.checked = false;
			}
		}
		catch (ex) {
		}
	}
}

function setModified(rowId, colId) {
	var ok = $("ok"+rowId);
	var modified = $("modified"+rowId);
	var status = gridCellValue(beanGrid, rowId, "status");
	try {
		if ((status == 'A' && ok.checked) ||
				(status == 'I' && ! ok.checked)) {
			modified.checked = false;
		}
		else {
			modified.checked = true;
		}
	}
	catch(e) {}
}

function updateLists() {
	beanGrid.parentFormOnSubmit();
	$('uAction').value = "update";
	document.genericForm.submit();
}

var selectedRow = null;
function selectRightClick(rowId, cellInd) {
	selectedRow = rowId;

	toggleContextMenu('rightClickMenu');
}

function showEntityList() {
	var loc = "expertreviewentitylist.do?uAction=search&expertReviewId="+gridCellValue(beanGrid, selectedRow, "expertReviewId");
	openWinGeneric(loc, "expertReviewEntity", "800", "450", "yes", "80", "80");
	//alert(loc);
}