var mygrid;
var windowCloseOnEsc = true;

var resizeGridWithWindow = true;

var selectedRowId = null;
var alternateDb = null;

var approvalRoleMap = {
	"SDS Sourcing": "Material QC",
	"SDS Indexing": "MSDS Indexing",
	"Item Creation": "Item QC"
};

function onChangeGrab(rowId, col) {
	var selectedCatalogAddRequestId = cellValue(rowId, "catalogAddRequestId");
	var selectedGrabbedValue = cellValue(rowId, "grabbed");
	var selectionCount = 0;
	var caller = $v("calledFrom");
	var status = $v("status");
	var taskStatus = $v("taskStatus");
	if (caller == "vendorQueuePage") {
		taskStatus = status;
	}
	if ( ! ((status == "Pending SDS Sourcing" || status == "Pending SDS Indexing" || status == "Pending Item Creation") && 
			(taskStatus == "" || taskStatus == "Open" || taskStatus == "Assigned"))) {
	    for (var i = 1; i <= mygrid.getRowsNum(); i++) {
	        if ( ! (caller == 'vendorQueuePage' || i == rowId) && gridCellValue(mygrid, i, "catalogAddRequestId") == selectedCatalogAddRequestId) {
	            var cid = "grabbed" + i;
	            if (selectedGrabbedValue == "true" && ! $(cid).disabled)
	                $(cid).checked = true;
	            else
	                $(cid).checked = false;
	            updateHchStatusA(cid);
	        }
	        //getting number of records checked
	        if (gridCellValue(mygrid, i, "grabbed") == "true") {
	            selectionCount++;
	        }
	    }
	}
	changeSelectionCount(selectionCount);
}

function onChangeSupplier(rowId, col) {
	var cid = "grabbed" + rowId;
	$(cid).checked = true;
	updateHchStatusA(cid);
	//get reason for change
	var qId = cellValue(rowId, "qId");
	var task = cellValue(rowId, "task");
	var loc = "problemqueuereason.do?uAction=open&qId="+qId+"&task="+task+"&returnValueToOpener=Y";
    openWinGeneric(loc,"workQueueReason","400","300","yes","80","80","yes");
}

function callBackValue(problemType,comments) {
    mygrid.cells(selectedRowId,mygrid.getColIndexById("problemType")).setValue(problemType);
    mygrid.cells(selectedRowId,mygrid.getColIndexById("comments")).setValue(comments);
    mygrid.cells(selectedRowId,mygrid.getColIndexById("problemChanged")).setValue("Y");
}

function resultOnLoad() {
	resultOnLoadWithGrid(gridConfig);
	
	var queueStatus = $v("status");
	var taskStatus = $v("taskStatus");
	if ($v("calledFrom") == "vendorQueuePage") {
		taskStatus = queueStatus;
	}
	var hideLinks = false;
	if ($v("totalLines") == 0) {
		hideLinks = true;
	}
	if (taskStatus == "Open" || taskStatus == "Assigned" || $v("calledFrom") == 'workQueueDetails') {
		parent.document.getElementById("updateResultLink").style.display = "";
		//parent.document.getElementById("invoiceResultLink").style.display = "none";
		parent.document.getElementById("qcChecked").style.display = "none";
	}
	else if (taskStatus == "Approved" || taskStatus == "Closed") {
		parent.document.getElementById("updateResultLink").style.display = "none";
		//parent.document.getElementById("invoiceResultLink").style.display = "";
		parent.document.getElementById("qcChecked").style.display = "none";
	}
	else if (queueStatus == "Pending QC" && $v("calledFrom") == 'vendorQueuePage') {
		parent.document.getElementById("updateResultLink").style.display = "none";
		//parent.document.getElementById("invoiceResultLink").style.display = "none";
		parent.document.getElementById("qcChecked").style.display = "";
	}
	else if (queueStatus == "Pending SDS Sourcing"
			|| queueStatus == "Pending SDS Indexing"
			|| queueStatus == "Pending Item Creation") {
		if (taskStatus == "Pending Approval") {
			parent.document.getElementById("updateResultLink").style.display = "none";
			parent.document.getElementById("qcChecked").style.display = "";
		}
	}
	else {
		hideLinks = true;	
	}
	parent.document.getElementById("mainUpdateLinks").style.display = hideLinks?"none":"";
}

function rightClickRow(rowId, colId) {
	var queueStatus = cellValue(rowId, "status");
	var requestId = cellValue(rowId, "catalogAddRequestId");
	var caller = $v("calledFrom");
    if ( ! (caller == "vendorQueuePage" && queueStatus == "Pending Approval")) {
        selectedRowId = rowId;
        var qId = cellValue(selectedRowId, "qId");
        var task = cellValue(selectedRowId, "task");
    	var vendor = cellValue(selectedRowId, "supplier");
    	var menuItem = 0;
    	
    	if (task == 'Item Creation') {
            mm_insertItem("rightClickMenu",menuItem,"text="+messagesData.open+" "+messagesData.queueid+" "+qId+" "+messagesData.item+";url=javascript:openItemQC();");
    	}
    	else {
    		mm_insertItem("rightClickMenu",menuItem,"text="+messagesData.open+" "+messagesData.queueid+" "+qId+" "+messagesData.msds+";url=javascript:openCatalogMSDSMaintenance();");
    	}
    	
   		if (caller=="wescoApproval" && vendor!=$v("userSupplier")
   				&& (queueStatus == "Open" || queueStatus == "Assigned" || queueStatus == "Pending QC")) {
   			mm_insertItem("rightClickMenu", ++menuItem, "text=Change to Wesco: "+ requestId +";url=javascript:changeRequestToWesco();");
   		}
   		
        var menuItemCount = mm_returnMenuItemCount("rightClickMenu");
    	for (; menuItemCount > (menuItem+1); menuItemCount--)
    		mm_deleteItem("rightClickMenu", menuItemCount);

        toggleContextMenu("rightClickMenu");
	}
}

function changeRequestToWesco() {
    var requestId = getCurrentRowVal("catalogAddRequestId");
    var vendorTask = getCurrentRowVal("task");
    var loc = "/tcmIS/catalog/problemqueuereason.do?uAction=open&reAssignTo=catalog&catalogAddRequestId="+requestId+"&task="+vendorTask;
    openWinGeneric(loc,"workQueueReason","400","300","yes","80","80","yes");
}

function selectRow(rowId) {
    selectedRowId = rowId;
	var selectionCount = 0;
	for (var i = 1; i <= mygrid.getRowsNum(); i++) {
		if (gridCellValue(mygrid, i, "grabbed") === "true") {
			selectionCount++;
		}
	}
	changeSelectionCount(selectionCount);
}

function getCurrentRowVal(name) {
	return encodeURIComponent(cellValue(selectedRowId, name));
}

function openMsdsQC() {
    openCatalogMSDSMaintenance("SDS QC");
}

function openCatalogMSDSMaintenance() {
	var qId = cellValue(selectedRowId, "qId");
	var qTask = cellValue(selectedRowId, "task");
	var approvalRole = approvalRoleMap[qTask];
    var loc="msdsindexingmain.do?uAction=getMaterialQc&qId="+qId+"&approvalRole="+approvalRole;
	try {
		if ($v("calledFrom") == 'workQueueDetails')
	        openWinGeneric(loc,"catalogMSDS","900","600","yes","80","80","yes");
	    else
			parent.parent.openIFrame("ChemTask"+qId,loc,messagesData.queueid+" "+qId,"","N");
	}
	catch (ex) {
		openWinGeneric(loc,"catalogMSDS","900","600","yes","80","80","yes");
	}
}

function openItemQC() {
	var qId = cellValue(selectedRowId, "qId");
	var qTask = cellValue(selectedRowId, "task");
	var approvalRole = approvalRoleMap[qTask];
	var loc="catalogitemqcdetails.do?uAction=search&qId="+qId+"&approvalRole="+approvalRole;
	try {
		if ($v("calledFrom") == 'workQueueDetails')
            openWinGeneric(loc,"catalogQC","900","600","yes","80","80","yes");
        else
			parent.parent.openIFrame("ChemTaskQC"+qId,loc,messagesData.task+" QC","","N");
	}
	catch (ex) {
		openWinGeneric(loc,"catalogQC","900","600","yes","80","80","yes");
	}
}

function update() {
	var msg = "";
	for (var i = 1; i <= mygrid.getRowsNum(); i++) {
		if (gridCellValue(mygrid, i, "supplier") != gridCellValue(mygrid, i, "originalSupplier")
				&& gridCellValue(mygrid, i, "problemChanged") != "Y") {
			if (msg.length > 0) {
				msg += ", ";
			}
			else {
				msg += "Please enter Problem Type and Comments for the following rows: ";
			}
			msg += i;
		}
	}
	
	if (msg.length == 0) {
		document.genericForm.target='';
		document.getElementById('uAction').value = 'update';
	
	    var now = new Date();
	    parent.document.getElementById("startSearchTime").value = now.getTime();
		parent.showPleaseWait();
	
		// Call this function to send the data from grid back to the server
		if (mygrid != null) {
			transformAssignee();
			mygrid.parentFormOnSubmit();
		}
	
		document.genericForm.submit();
	}
	else {
		alert(msg);
	}
}

function submitInvoice() {
	document.genericForm.target='';
	document.getElementById('uAction').value = 'invoice';

	var now = new Date();
    parent.document.getElementById("startSearchTime").value = now.getTime();
	parent.showPleaseWait();

	// Call this function to send the data from grid back to the server
	if (mygrid != null) {
		transformAssignee();
		mygrid.parentFormOnSubmit();
	}

	document.genericForm.submit();
}

function changeSelectionCount(grabCount) {
	var selectionCount = parent.document.getElementById("selectionCount");
	if (selectionCount == null) {
		selectionCount = document.createElement("span");
		selectionCount.id = "selectionCount";
		selectionCount.style.marginLeft = "200px";
		parent.document.getElementById("footer").appendChild(selectionCount);
	}
	
	if (grabCount == 0) {
		selectionCount.style.display = "none";
	}
	else {
		selectionCount.style.display = "inline";
		selectionCount.innerHTML = grabCount+" item(s) selected";
	}
}

function onChangeAssignedTo(rowId, col) {
	var cid = "grabbed" + rowId;
	$(cid).checked = true;
	updateHchStatusA(cid);
}

function checkAll(colId) {
	var rowCount = mygrid.getRowsNum();
	var chkAllStatus = $("chkAllGrab").checked;
	var actualCount = 0;
	for (var i = 1; i <= rowCount; i++) {
		var checkbox = $(colId+i);
		if (checkbox && ! checkbox.disabled) {
			$(colId+i).checked = chkAllStatus;
			actualCount++;
		}
	}
	changeSelectionCount(chkAllStatus?actualCount:0);
}

function approve() {
	var selectionCount = 0;
	for (var i = 1; i <= mygrid.getRowsNum(); i++) {
		if (gridCellValue(mygrid, i, "grabbed") === "true") {
			selectionCount++;
			break;
		}
	}
	if (selectionCount == 0) {
		alert(messagesData.pleaseselect);
		return false;
	}
	
	document.genericForm.target='';
	document.getElementById('uAction').value = 'approve';

    var now = new Date();
    parent.document.getElementById("startSearchTime").value = now.getTime();
	parent.showPleaseWait();

	// Call this function to send the data from grid back to the server
	if (mygrid != null) {
		transformAssignee();
		mygrid.parentFormOnSubmit();
	}

	document.genericForm.submit();
}

function transformAssignee() {
	for (var i = 1; i <= mygrid.getRowsNum(); i++) {
		var assignee = gridCellValue(mygrid, i, "assignedTo");
		if (isNaN(assignee)) {
			var assigneeId = gridCellValue(mygrid, i, "assignedToHidden");
			if (assigneeId) {
				setGridCellValue(mygrid, i, "assignedTo", assigneeId);
			}
			else {
				setGridCellValue(mygrid, i, "assignedTo", "");
			}
		}
	}
}

function doRefresh(closeThisTab) {
	window.setTimeout('finishRefresh();', 10);
	//CLose current tab
	var caller = $v("calledFrom");
	if (caller != 'workQueueDetails' || closeThisTab == 'true') {
		parent.parent.closeTab();
	}
}

function finishRefresh() {
	// Select this tab
	var caller = $v("calledFrom");
    if (caller == 'wescoApproval') {
    	parent.parent.togglePage("catalogAddProcess");
    	document.genericForm.action="/tcmIS/catalog/catalogaddqcresults.do";
    }
    else if (caller == 'workQueueDetails') {
    	document.genericForm.action="/tcmIS/catalog/catalogaddvendorqueuedetails.do";
    }
    else {
    	parent.parent.togglePage("catAddVendorQueue");
    }

	// redo the search
	var now = new Date();
	parent.document.getElementById("startSearchTime").value = now.getTime();
	document.genericForm.target='';
	document.getElementById('uAction').value = 'search';
	parent.showPleaseWait();
	document.genericForm.submit();
}

function setRowStatusColors(rowId) {
	if (rowId) {
		var timeRemaining = cellValue(rowId, "timeRemaining");
		var colored = cellValue(rowId, "colored");
		if ("N" == colored && timeRemaining <= 0) {
		    mygrid.haasSetColSpanStyle(rowId, 0, columnConfig.length, "background-color: #ff9999;");
		}
		setCellValue(rowId, "colored", "Y");
	}
}
