
var resizeGridWithWindow = true;

function update() {
	$("action").value = "assignPickingGroups";
	beanGrid.parentFormOnSubmit();
	parent.showPleaseWait();
	document.genericForm.submit();
}

function rowSelected() {
	
}

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("picklistData").style["display"] = "";
		parent.document.getElementById("updateResultLink").style["display"] = "";
		initGridWithGlobal(gridConfig); 
	}
	else {
		document.getElementById("picklistData").style["display"] = "none";   
		parent.document.getElementById("updateResultLink").style["display"] = "none";
	}
	
	if (parent.setStartTime) {
		parent.setStartTime();
	}
	displayGridSearchDuration();
	setResultFrameSize();
}

function doSelectAll(val) {
	var leng = 1*beanGrid.getRowsNum();
	for (var i = 1; i <= leng; i++) {
		$("pickingGroupId"+i).value = val;
	}
}

function checkAll() {
	var leng = 1*beanGrid.getRowsNum();
	for (var i = 1; i <= leng; i++) {
		if ($("update"+i)) {
			$("update"+i).checked = $("chkAllOk").checked;
		}
	}
}