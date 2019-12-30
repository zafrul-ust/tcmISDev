var windowCloseOnEsc = true;
var selectedRow;
var beanGrid;
var children = new Array();

function submitSearchForm() {
	//find all checked checkboxes
	var statusCheckboxes = document.getElementsByName("statusCheckbox");
	var statuses = "";
	for (var i = 0; i < statusCheckboxes.length; i++)
		if (statusCheckboxes[i].checked)
			statuses += statusCheckboxes[i].value + ",";
	
	if (statuses.length < 1) {
		alert(messagesData.noCheckboxSelected.replace("{0}", messagesData.includeLocation));
		return false;
	}
	
	$("status").value = statuses;	
	_submitSearchForm();
}

function myResultOnLoadWithGrid(config) {
	//need this to clean up the link when page is loaded (first open and refresh)
	parent.document.getElementById("changeResultStatusLink").innerHTML = "&nbsp;";
	parent.document.getElementById("returnResultLink").innerHTML = "&nbsp;";
	try {
		if (!showUpdateLinks) /* Dont show any update links if the user does not have permissions */
		{
			parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
		}
		else {
			parent.document.getElementById("mainUpdateLinks").style["display"] = "";
		}
	}
	catch (ex) {}
	
	initGridWithGlobal(config);
	
	/* This displays our standard footer message */
	displayGridSearchDuration();

	/* It is important to call this after all the divs have been turned on or off. */
	setResultFrameSize();
}

function selectRow(rowid) {
	selectedRow = rowid;
	var curStatus = gridCellValue(beanGrid, selectedRow, "status");
	var selectedLocationText = gridCellValue(beanGrid, rowid, "billingLocationId");
	var changeStatusLink = parent.document.getElementById("changeResultStatusLink");
	if (curStatus == 'Inactive' || curStatus == '') {// only allow changing status if it is either Approved or Inactive or empty
		changeStatusLink.innerHTML = '<a href="#" onclick="call(\'changeLocationStatus\'); return false;">' 
			+ messagesData.activateLocation + ' ' + selectedLocationText + '</a>';
	} else if (curStatus == 'Approved') {
		changeStatusLink.innerHTML = '<a href="#" onclick="call(\'changeLocationStatus\'); return false;">' 
			+ messagesData.inactivateLocation + ' ' + selectedLocationText + '</a>';
	} else
		changeStatusLink.innerHTML = "&nbsp;";

	var returnLink = parent.document.getElementById("returnResultLink");
	if (parent.$v("callerName") == 'accountsPayable') { //only allow returning data for Accounts Payable page
		returnLink.innerHTML = '<a href="#" onclick="call(\'returnLocation\'); return false;">' 
			+ messagesData.returnSelectedLocation + ' ' + selectedLocationText + '</a>';
		if (changeStatusLink.innerHTML.length > 0)
			returnLink.innerHTML = "| " + returnLink.innerHTML;
	} else
		returnLink.innerHTML = "&nbsp;";
}

function returnLocation() {
	if (parent.$v("callerName") == 'accountsPayable') {
		if (isEmpty(gridCellValue(beanGrid, selectedRow, "sapVendorCode")) || gridCellValue(beanGrid, selectedRow, "status") == "Rejected") {
			alert(messagesData.unapprovedSelected);
			return;
		}
		parent.opener.sendSupplierWithUpdateNew(prepareReturnForm());
	}
	parent.window.close();
}

//create form to be used by Accounts Payable page's process
function prepareReturnForm() {
	var form = document.createElement("form");
	form.setAttribute("name", "SupplierLike");
	
	var childInput = document.createElement("input");
	childInput.setAttribute("type", "hidden");
	childInput.setAttribute("id", "billingLocationId");
	childInput.setAttribute("value", gridCellValue(beanGrid, selectedRow, "billingLocationId"));
	form.appendChild(childInput);
	
	childInput = document.createElement("input");
	childInput.setAttribute("type", "hidden");
	childInput.setAttribute("id", "locationDesc");
	childInput.setAttribute("value", gridCellValue(beanGrid, selectedRow, "locationDesc"));
	form.appendChild(childInput);
	
	childInput = document.createElement("input");
	childInput.setAttribute("type", "hidden");
	childInput.setAttribute("id", "addline1");
	childInput.setAttribute("value", gridCellValue(beanGrid, selectedRow, "addressLine1"));
	form.appendChild(childInput);
	
	childInput = document.createElement("input");
	childInput.setAttribute("type", "hidden");
	childInput.setAttribute("id", "addline2");
	if (!isEmpty(gridCellValue(beanGrid, selectedRow, "addressLine2")))
		childInput.setAttribute("value", gridCellValue(beanGrid, selectedRow, "addressLine2"));
	else
		childInput.setAttribute("value", "");
	form.appendChild(childInput);
	
	childInput = document.createElement("input");
	childInput.setAttribute("type", "hidden");
	childInput.setAttribute("id", "addline3");
	childInput.setAttribute("value", gridCellValue(beanGrid, selectedRow, "cityStateZipCountry"));
	form.appendChild(childInput);
	
	return form;
}

function closeAllchildren() {
	try {
		for (var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			} catch (ex) {}
			children[n].close();
		}
	} catch (ex) {}
	children = new Array();
}

function changeLocationStatus() {
	if (selectedRow) {
		setGridCellValue(beanGrid, selectedRow, "updated", "true");	
		submitResultForm('changestatus');
	} else
		alert(messagesData.noRowSelected);
}

function submitResultForm(action) {
	document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
	$("uAction").value = action;
	var now = new Date();
	var startSearchTime = parent.document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();	
	parent.showPleaseWait(); // Show "please wait" while updating

	if (beanGrid != null) {
		// This function prepares the grid dat for submitting top the server
		beanGrid.parentFormOnSubmit();
	}

	document.genericForm.submit();
}

function createNewRemitToAddress() {
	var loc = "newremittoaddress.do?supplier=" + $v("supplier") + "&supplierName=" + URLEncode($v("supplierName")) + "&callerName=remitToManagement";
	openWinGeneric(loc,"_NewRemitToAddress","800","300","yes","50","50");
}

function statusCheckboxChanged(checkbox) {
	if (checkbox.id == 'statusAll') { //if All checkbox is checked, uncheck all other
		var statusCheckboxes = document.getElementsByName("statusCheckbox");
		for (var i = 0; i < statusCheckboxes.length; i++)
			if (statusCheckboxes[i].id != 'statusAll')
				statusCheckboxes[i].checked = false;
	} else if ($("statusAll").checked) // if something is checked while All is checked, uncheck All
		$("statusAll").checked = false;
}

function isEmpty(s) {
	if (s == null || s.length < 1)
		return true;
	else
		return false;
}