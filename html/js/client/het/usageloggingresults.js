var mygrid;
var windowCloseOnEsc = true;
var validations = 0;
var resizeGridWithWindow = true;
var errorsExist = false;
var selectedRowId = 1;
var virtualKitId = -1;

/* The reason for this is to show the error messages from the main page */
function showErrorMessages() {
	parent.showErrorMessages();
}

function getVirtualKitId() {
	return virtualKitId--;
}

function myResultOnLoadWithGrid(gridConfig) {

	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("usageLogging").style["display"] = "";

		if (updateSuccess) {
			alert(messagesData.updateSuccess);
		}

		if (showUpdateLinks) {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
		else {
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
	}
	else {

		document.getElementById("usageLogging").style["display"] = "none";
	}

	initGridWithGlobal(gridConfig);

	/* Display the standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on or
	 * off.
	 */
	setResultFrameSize();
}

function submitMainUpdate() {
	validateForm();
}

function launchAddThinner() {
	var url = "alternatecontainersearch.do?uAction=solvent";
	url += "&facilityId=" + $v("facilityId");
	url += "&workArea=" + $v("workArea");
	var cartName = cellValue(selectedRowId, 'cartName');
	if (cartName != '')
		url += "&cartName=" + cartName;

	itemWindow = openWinGeneric(url, "ContainerSearchPage", "800", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = itemWindow;
	parent.showTransitWin(formatMessage(messagesData.waiting, messagesData.addThinner));
}

function launchCreateAdHocMixture() {
	var url = "alternatecontainersearch.do?uAction=adHocMixture";
	url += "&facilityId=" + $v("facilityId");
	url += "&workArea=" + $v("workArea");

	var start = mygrid.haasGetRowSpanStart(selectedRowId);
	var end = mygrid.haasGetRowSpanEndIndex(selectedRowId);
	var containerMap = {};
	var semiCount = 0;
	var cartName = cellValue(selectedRowId, 'cartName');
	if (cartName != '')
		url += "&cartName=" + cartName;
	url += "&containerIds=";
	for ( var i = start + 1; i < end + 1; i++) {
		if (cartName != '' && i == selectedRowId)
			continue;
		else {
			var containerId = cellValue(i, "containerId");
			if (containerMap[containerId] == null || containerMap[containerId] == undefined) {
				semiCount++;
				containerMap[containerId] = "in";
				if (semiCount > 1)
					url += ";" + containerId;
				else
					url += containerId;
			}
		}
	}

	itemWindow = openWinGeneric(url, "ContainerSearchPage", "800", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = itemWindow;
	parent.showTransitWin(formatMessage(messagesData.waiting, messagesData.addThinner));
}

function launchAddContainerToMixture() {
	var url = "alternatecontainersearch.do?uAction=addContainerMixture";
	url += "&facilityId=" + $v("facilityId");
	url += "&workArea=" + $v("workArea");
	url += "&mixtureId=" + cellValue(selectedRowId, "mixtureId");
	url += "&hetMixture=" + cellValue(selectedRowId, "hetMixture");

	var start = mygrid.haasGetRowSpanStartLvl2(selectedRowId);
	var end = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);
	var usedMSDSs = {};
	var semiCount = 0;
	var cartName = cellValue(selectedRowId, 'cartName');
	if (cartName != '')
		url += "&cartName=" + cartName;
	url += "&msdsPreviouslyUsed=";
	for ( var i = start + 1; i < end + 1; i++) {
		var currentMsds = cellValue(i, "msdsNo");
		if (usedMSDSs[currentMsds] == null || usedMSDSs[currentMsds] == undefined) {
			semiCount++;
			usedMSDSs[currentMsds] = "in";
			if (semiCount > 1)
				url += ";" + currentMsds;
			else
				url += currentMsds;
		}
	}

	itemWindow = openWinGeneric(url, "ContainerSearchPage", "800", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = itemWindow;
	parent.showTransitWin(formatMessage(messagesData.waiting, messagesData.addThinner));
}

function addContainer(containerId, partNo, itemId, msds, desc, size, amount, uom, containerType, mixtureCount, addType) {
	var cartIndex = mygrid.haasGetRowSpanStart(selectedRowId);
	var parentIndex = mygrid.haasGetRowSpanStartLvl2(selectedRowId);
	var parentId = mygrid.getRowId(parentIndex);
	var kitId = "" + cellValue(parentId, "kitId");
	var newLinePosition = mygrid.haasGetRowSpanEndIndexLvl2(selectedRowId);
	var permission = "N";
	var employee = (cellValue(selectedRowId, "employee") + "").trim();
	var selectedContainerId = (cellValue(selectedRowId, "containerId") + "").trim();
	if (mixtureCount <= (newLinePosition - parentIndex + 1)) {
		permission = "Y";
	}

	if (kitId == "") {
		kitId = getVirtualKitId();
		cell(parentId, "kitId").setValue(kitId);
	}

	var newRowData = [
					permission,
					cellValue(selectedRowId, "updated"),
					cellValue(selectedRowId, "cartName"),
					cellValue(selectedRowId, "cartId"),
					partNo,
					itemId,
					cellValue(selectedRowId, "mixtureName"),
					kitId,
					cellValue(selectedRowId, "materialId"),
					desc,
					msds,
					employee,
					containerId,
					amount,
					uom,
					false,
					cellValue(selectedRowId, "usageDate"),
					cellValue(selectedRowId, "partType"),
					cellValue(selectedRowId, "areaId"),
					cellValue(selectedRowId, "buildingId"),
					cellValue(selectedRowId, "permit"),
					cellValue(selectedRowId, "controlDevice"),
					cellValue(selectedRowId, "applicationMethod"),
					cellValue(selectedRowId, "painted"),
					cellValue(selectedRowId, "substrate"),
					cellValue(selectedRowId, "remarks"),
					cellValue(selectedRowId, "checkedOut"),
					cellValue(selectedRowId, "companyId"),
					cellValue(selectedRowId, "facilityId"),
					cellValue(selectedRowId, "reportingEntityId"),
					$v("workArea"),
					cellValue(selectedRowId, "mixtureId"),
					cellValue(selectedRowId, "inMixture"),
					cellValue(selectedRowId, "hetMixture"),
					cellValue(selectedRowId, "allowSplitKit"),
					cellValue(selectedRowId, "separable"),
					"" + containerType,
					"solvent" == addType,
					cellValue(selectedRowId, "msdsInMixture"),
					cellValue(selectedRowId, "mixtureMsdsCount"),
					amount
	];

	initializedRows[parentId] = false;
	mygrid.haasAddRowToRowSpan(newRowData, newLinePosition, cartIndex, parentIndex);
	mygrid.haasRenderRow(parentId);
	if (mixtureCount > 0) {
		for ( var index = parentIndex; index < newLinePosition; index++) {
			var curRowId = mygrid.getRowId(index);
			setCellValue(curRowId, "permission", permission);
			refreshCell(curRowId, "updated");
			setCellValue(curRowId, "employee", employee);
			refreshCell(curRowId, "amountRemaining");
			refreshCell(curRowId, "unitOfMeasure");
			refreshCell(curRowId, "discarded");
			refreshCell(curRowId, "usageDate");
			refreshCell(curRowId, "partType");
			refreshCell(curRowId, "permit");
			refreshCell(curRowId, "applicationMethod");
			refreshCell(curRowId, "painted");
			refreshCell(curRowId, "substrate");
			refreshCell(curRowId, "remarks");
		}
		mygrid.haasRenderRow(parentIndex);
	}

	// if ("solvent" != addType) {
	var newRowId = mygrid.getRowId(newLinePosition);
	var numRows = mygrid.getRowsNum();
	for ( var id = numRows; id > 0; id--) {
		var container = cellValue(id, "containerId");
		if (selectedRowId != id) {
			// Delete rows for the "added to" container that are from a
			// different mixture;
			if (selectedContainerId == container) {
				// alert("Row " + id + ", containerId " + container +",
				// matches added to containerId " + selectedContainerId);
				mygrid.haasDeleteRowFromRowSpan(id);
			}
		}
		if (newRowId != id) {
			// Delete rows for the "added from" container that are from a
			// different mixture;
			if (containerId == container) {
				mygrid.haasDeleteRowFromRowSpan(id);
			}
		}
	}
	// }

	toggleSubstrate(parentId);
	mygrid.selectRow(parentIndex, true);
}

function refreshCell(rowId, cellName) {
	setCellValue(rowId, cellName, cellJsonValue(rowId, cellName));
}

function launchOtherContainer() {
	var url = "alternatecontainersearch.do?uAction=othercontainer";
	url += "&facilityId=" + $v("facilityId");
	url += "&workArea=" + $v("workArea");
	url += "&materialId=" + cellValue(selectedRowId, "materialId");
	var cartName = cellValue(selectedRowId, 'cartName');
	if (cartName != '')
		url += "&cartName=" + cartName;
	itemWindow = openWinGeneric(url, "ContainerSearchPage", "800", "450", "yes", "50", "50", "no");
	parent.children[parent.children.length] = itemWindow;
	parent.showTransitWin(formatMessage(messagesData.waiting, messagesData.addThinner));
}

function changeContainer(containerId, amount, uom) {
	var index = mygrid.getRowIndex(selectedRowId);
	cell(selectedRowId, "containerId").setValue(containerId);
	cell(selectedRowId, "amountRemaining").setValue(amount);
	cell(selectedRowId, "unitOfMeasure").setValue(uom);
}

function onRightClick(rowId, cellInd) {
	selectedRowId = rowId; // set global variable, selectedRowId
	var index = mygrid.getRowIndex(rowId);
	var inMixture = 'true' == cellValue(selectedRowId, 'inMixture');
	var allowSplitKit = 'true' == cellValue(selectedRowId, 'allowSplitKit');
	var isKit = rowSpanLvl2Map[index] != 1;
	
	if (isKit && inMixture) {
		var mixtureCount = cellValue(selectedRowId, 'mixtureMsdsCount');
		if (mixtureCount >= rowSpanLvl2Map[index]) {
			inMixture = false;
		}
	}
	
	if (isKit) { // rowSpanLvl2Map != 1 for KITS
		if (inMixture && (!allowSplitKit || cartSearch)) {
			toggleContextMenu('rightClickMenuNoCreateAdHocMixture');
		}
		else if (!inMixture && allowSplitKit && !cartSearch) {
			toggleContextMenu('rightClickMenuNoAddContainerToMixture');
		}
		else if (inMixture && allowSplitKit) {
			toggleContextMenu('rightClickMenu');
		}
		else {
			toggleContextMenu('rightClickMenuNoAddContainerToMixtureNoCreateAdHocMixture');
		}
	}
	else {
		if (inMixture && (!allowSplitKit || cartSearch)) {
			toggleContextMenu('rightClickMenuNoOtherContainerNoCreateAdHocMixture');
		}
		else if (!inMixture && allowSplitKit && !cartSearch)  {
			toggleContextMenu('rightClickMenuNoOtherContainerNoAddContainerToMixture');
		}
		else if (inMixture && allowSplitKit) {
			toggleContextMenu('rightClickMenuNoOtherContainer');
		}
		else {
			toggleContextMenu('rightClickMenuNoOtherContainerNoAddContainerToMixtureNoCreateAdHocMixture');
		}
	}
}

function onRowSelect(rowId, cellInd) {
	selectedRowId = rowId; // set global variable, selectedRowId, here for
}

function validateLine(rowId) {
	var url = "containervalidation.do?itemId=" + cellValue(rowId, "itemId");
	url += "&containerId=" + cellValue(rowId, "containerId");
	url += "&amountRemaining=" + cellValue(rowId, "amountRemaining");
	url += "&unitOfMeasure=" + cellValue(rowId, "unitOfMeasure");
	url += "&cartId=" + cellValue(rowId, "cartId");
	url += "&checkedOut=" + cellValue(rowId, "checkedOut");
	url += "&usageDate=" + cellValue(rowId, "usageDate");
	if (("" + cellValue(rowId, "employee")).length > 0) {
		url += "&companyId=" + $v("companyId");
		url += "&facilityId=" + $v("facilityId");
		url += "&employee=" + cellValue(rowId, "employee");
	}
	url += "&rowId=" + rowId;
	validations++;
	callToServer(url, "usageLogging-" + rowId);
}

function invalidCheckInDate(rowId, checkedOut, today) {
	mygrid.selectRowById(rowId, true);
	alert(formatMessage(messagesData.rangeError, messagesData.usageDate, checkedOut, today));
}

function invalidQuantity(rowId, containerId, quantity, remaining, uom) {
	mygrid.selectRowById(rowId, true);
	if (remaining <= 0) {
		alert(formatMessage(messagesData.invalidQuantity2, containerId, quantity, uom));
	}
	else {
		alert(formatMessage(messagesData.invalidQuantity, containerId, quantity, uom));
	}
}

function invalidUOM(rowId, uom, containerId) {
	mygrid.selectRowById(rowId, true);
	alert(formatMessage(messagesData.invalidUOM, containerId, uom));
}

function invalidConversion(rowId, uom, containerId) {
	mygrid.selectRowById(rowId, true);
	alert(formatMessage(messagesData.invalidConversion, containerId, uom));
}

function invalidContainer(rowId, containerId, itemId) {
	mygrid.selectRowById(rowId, true);
	alert(formatMessage(messagesData.invalidContainer, containerId, itemId));
}

function invalidEmptyContainer(rowId, containerId) {
	mygrid.selectRowById(rowId, true);
	alert(formatMessage(messagesData.invalidEmptyContainer, containerId));
}

function invalidEmployee(rowId) {
	mygrid.selectRowById(rowId, true);
	alert(messagesData.invalidEmployee);
}

function finishSubmit() {
	validations--;
	if (validations < 1 && !errorsExist) {
		/* Set any variables you want to send to the server */
		$("uAction").value = 'update';
		parent.showPleaseWait();

		if (mygrid != null) {
			mygrid.parentFormOnSubmit();
		}
		/* Submit the form in the result frame */
		document.genericForm.submit();
	}
}

function validateForm() {
	errorsExist = false;
	validations = 0;
	var updates = 0;
	var errMsg = "";
	var numRows = mygrid.getRowsNum();
	var selectedIndex;
	for ( var rowIndex = 0; rowIndex < numRows; rowIndex++) {
		var rowId = mygrid.getRowId(rowIndex);
		if ("true" == cellValue(rowId, "updated")) {
			updates++;
			if ("" == cellValue(rowId, "employee")) {
				errMsg += messagesData.employeeRequired + "\n";
			}
			if ("" == cellValue(rowId, "containerId")) {
				errMsg += messagesData.containerRequired + "\n";
			}
			var quantity = cellValue(rowId, "amountRemaining");
			if (("" + quantity).length == 0) {
				errMsg += messagesData.quantityRequired + "\n";
			}
			else if (!isFloat(quantity, false)) {
				errMsg += messagesData.quantityNumber + "\n";
			}
			else {
				quantity = parseFloat(quantity);
				if (quantity < 0.0) {
					errMsg += messagesData.quantityNumber + "\n";
				}
				// else if (quantity > originalQuantity) {
				// errMsg += formatMessage(messagesData.quantityTooLarge,
				// cellValue(rowId, "containerId"), quantity , "") + "\n";
				// }
				// else if (quantity == originalQuantity) {
				// errMsg += messagesData.quantityNotChanged + "\n";
				// }
			}
			if (cellValue(rowId, "permit") == "SELECT") {
				errMsg += messagesData.permitRequired + "\n";
			}
			if (cellValue(rowId, "applicationMethod") == "SELECT") {
				errMsg += messagesData.methodRequired + "\n";
			}
			if (cellValue(rowId, "partType") == "SELECT") {
				errMsg += messagesData.partTypeRequired + "\n";
			}
			if (cellValue(rowId, "partType") == "N" && cellValue(rowId, "substrate") == "SELECT") {
				errMsg += messagesData.substrateRequired + "\n";
			}
			if (errMsg.length > 0) {
				mygrid.selectRow(rowIndex, true);
				alert(errMsg);
				errorsExist = true;
				return false;
			}
			validateLine(rowId);
		}
	}
	if (updates == 0) {
		alert(messagesData.nothingSelected);
		return false;
	}
	return true;
}

function toggleUpdated(rowId, colId) {
	var rowIndex = rowId - 1;
	if (rowSpanLvl2Map[rowIndex] > 1) {
		var endRow = rowIndex + rowSpanLvl2Map[rowIndex] - 1;
		var checkboxVal = $("updated" + rowId).checked;
		for ( var i = rowIndex + 1; i <= endRow; i++) {
			var childRowId = mygrid.getRowId(i);
			cell(childRowId, "updated").setValue(checkboxVal);
		}
	}
}

function toggleSubstrate(rowId) {
	var rowIndex = mygrid.getRowIndex(rowId);
	if (rowSpanLvl2Map[rowIndex] > 0) {
		var substrate = $("substrate" + rowId);
		if (substrate != null && substrate != undefined) {
			var partType = cellValue(rowId, "partType");
			$("substrate" + rowId);

			if (partType == "N") {
				substrate.disabled = false;
			}
			else {
				substrate.disabled = true;
			}
		}

	}

}

function setControlDevice(rowId) {
	var permit = cellValue(rowId, "permit");
	cell(rowId, "controlDevice").setValue(null != controlDevices[permit] ? controlDevices[permit] : "");
}

function toggleBuilding(rowId) {
	if (multiBuilding) {
		var buildingIdFieldName = "buildingId" + rowId;
		var buildingId = $(buildingIdFieldName);
		if (buildingId) {
			var areaId = cellValue(rowId, "areaId");
			var curVal = cellValue(rowId, "buildingId");
			var buildingIds = altBuildingId[areaId];
			var buildingDescs = altBuildingDesc[areaId];
			for ( var i = buildingId.length; i > 0; i--) {
				buildingId[i] = null;
			}
			for ( var i = 0; i < buildingIds.length; i++) {
				setSelectOption(i, buildingDescs[i], buildingIds[i], buildingId, null, curVal == buildingIds[i]);
			}
			togglePermits(rowId);
		}
	}
}

function togglePermits(rowId) {
	var permitFieldName = "permit" + rowId;
	var permit = $(permitFieldName);
	var buildingId = cellValue(rowId, "buildingId");
	var curVal = cellValue(rowId, "permit");
	var permits = altPermitId[buildingId];
	var permitDescs = altPermitDesc[buildingId];
	for ( var i = permit.length; i > 0; i--) {
		permit[i] = null;
	}
	var rowCntr = 0;
	setSelectOption(rowCntr++, messagesData.pleaseselect, 'SELECT', permit);
	if (null != permits) {
		for ( var i = 0; i < permits.length; i++) {
			setSelectOption(rowCntr++, permitDescs[i], permits[i], permit, null, curVal == permits[i]);
		}
	}
	setSelectOption(rowCntr, messagesData.none, 'NONE', permit);
}

function toggleMethods(rowId) {
	if ('true' == cellValue(rowId, "solvent")) {
		var methodFieldName = "applicationMethod" + rowId;
		var method = $(methodFieldName);

		for ( var i = method.length; i > 0; i--) {
			method[i] = null;
		}

		if (null != applicationMethodForSolvent) {
			for ( var i = 0; i < applicationMethodForSolvent.length; i++) {
				setSelectOption(i, applicationMethodForSolvent[i].text, applicationMethodForSolvent[i].value, method);
			}
		}
	}
}

var initializedRows = new Array();
function toggleSubstrateOnce(rowId) {
	if (!initializedRows[rowId]) {
		initializedRows[rowId] = true;
		toggleSubstrate();
		toggleMethods(rowId);
	}
}

function setupRow(rowId) {
	if (!initializedRows[rowId]) {
		initializedRows[rowId] = true;
		toggleSubstrate(rowId);
		toggleBuilding(rowId);
		toggleMethods(rowId);
	}
}

function callToServer(URL, targetFrameName) {
	var IFrameObj;
	var frameName;
	if(targetFrameName != null && targetFrameName != "") {
		frameName = targetFrameName;
	}
	else {
		frameName = 'RSIFrame';
	}
	//alert(URL);
	if (!document.createElement) {
		return true
	}
	;
	var IFrameDoc;
	if (!IFrameObj && document.createElement) {
		// create the IFrame and assign a reference to the
		// object to our global variable IFrameObj.
		// this will only happen the first time
		// callToServer() is called
		// the src is set to /blank.html for IE to not display a security message when used in https pages
		try {
			var tempIFrame = document.createElement('iframe');
			tempIFrame.setAttribute('id', frameName);
			tempIFrame.setAttribute('name', frameName);
			tempIFrame.setAttribute('src', '');
			tempIFrame.style.border = '0px';
			tempIFrame.style.width = '0px';
			tempIFrame.style.height = '0px';
			IFrameObj = document.body.appendChild(tempIFrame);

			if (document.frames) {
				// this is for IE5 Mac, because it will only
				// allow access to the document object
				// of the IFrame if we access it through
				// the document.frames array
				IFrameObj = document.frames[frameName];
			}
		}
		catch (exception) {
			// This is for IE5 PC, which does not allow dynamic creation
			// and manipulation of an iframe object. Instead, we'll fake
			// it up by creating our own objects.
			iframeHTML = '\<iframe id="RSIFrame" src="" name="' + frameName + '" style="';
			iframeHTML += 'border:0px;';
			iframeHTML += 'width:0px;';
			iframeHTML += 'height:0px;';
			iframeHTML += '"><\/iframe>';
			document.body.innerHTML += iframeHTML;
			IFrameObj = new Object();
			IFrameObj.document = new Object();
			IFrameObj.document.location = new Object();
			IFrameObj.document.location.iframe = document.getElementById(frameName);
			IFrameObj.document.location.replace = function(location) {
				this.iframe.src = location;
			}
		}
	}

	if (navigator.userAgent.indexOf('Gecko') != -1 && !IFrameObj.contentDocument) {
		// we have to give NS6 a fraction of a second
		// to recognize the new IFrame
		setTimeout('callToServer()', 10);
		return false;
	}

	if (IFrameObj.contentDocument) {
		IFrameDoc = IFrameObj.contentDocument;
	}
	else if (IFrameObj.contentWindow) {
		IFrameDoc = IFrameObj.contentWindow.document;
	}
	else if (IFrameObj.document) {
		IFrameDoc = IFrameObj.document;
	}
	else {
		return true;
	}
	if (typeof (URL) == 'string') {
		IFrameDoc.location.replace(URL);
	}
	else {
		try {
			var oritarget = URL.target;
			URL.target = frameName;
			URL.submit();
			URL.target = oritarget;
		}
		catch (ex) {
			return true;
		}
	}
	setTimeout('window.status="";', 5000);
}
