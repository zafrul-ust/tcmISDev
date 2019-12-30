// Global variable: specially useful for right-click
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

// This works only for popup windows and IE. Close the window after clicking Esc
// key
var windowCloseOnEsc = true;

var application;

// Allow different permissions for different columns
var multiplePermissions = true;

var permissionColumns = new Array();
permissionColumns = {
	"solvent" : true,
	"diluent" : true,
	"defaultApplicationMethodCod" : true,
	"defaultPartType" : true,
	"defaultPermitId" : true,
	"defaultSubstrateCode" : true
};

var dupRowClicked = false;
var reValidate = false;
var currentValidatingRow = 1;
/*
 * Grid event OnBeforeSelect function Save the row class of currently selected
 * row, before class changes.
 */
function selectRightclick(rowId, oldRowId) {
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId;
	if (dupRowClicked)
		rowDup(rowId);
	dupRowClicked = false;
}

var callBack = true;
function validateContainerId(rowId) {
	var v = cellValue(rowId, 'containerId');

	if (v != '') {
		var pat = new RegExp("^[0-9]+\\-[0-9]+$");
		if (!pat.test(v)) {
			alert(messagesData.containerIdFormat.replace('{0}', v));
			mygrid.cellById(rowId, mygrid.getColIndexById("validContainerId")).setValue(false);
			return;
		}
		for ( var i = 1; i <= mygrid.getRowsNum(); i++)
			if (i == parseInt(rowId))
				continue;
			else if (cellValue(i, 'containerId') == v) {
				alert(messagesData.uniqueContainerId.replace('{0}', messagesData.containerId).replace('{1}', v));
				mygrid.cellById(rowId, mygrid.getColIndexById("validContainerId")).setValue(false);
				callBack = false;
			}
		if (callBack) {
			callToServer("containervalidation.do?uAction=containerInentoriedDupCheck&containerId=" + v + "&itemId=" + cellValue(rowId, 'itemId') + "&callback=returnValidateContainerId");
		}
	}
}

function returnValidateContainerId(val, containerId, receiptId, itemId) {
	if (val == "true") {
		if (receiptId != '')
			alert(formatMessage(messagesData.containerValid, receiptId, itemId));
		else
			alert(formatMessage(messagesData.alreadyExists, messagesData.containerId, containerId));

		if (reValidate) {
			mygrid.cellById(currentValidatingRow, mygrid.getColIndexById("validContainerId")).setValue(false);
			reValidate = false;
			currentValidatingRow = 1;
		}
		else
			mygrid.cellById(selectedRowId, mygrid.getColIndexById("validContainerId")).setValue(false);
	}
	else {

		if (reValidate) {
			mygrid.cellById(currentValidatingRow, mygrid.getColIndexById("validContainerId")).setValue(true);
			reValidate = false;
			currentValidatingRow = 1;
			update();
		}
		else
			mygrid.cellById(selectedRowId, mygrid.getColIndexById("validContainerId")).setValue(true);
	}

}

function myLoad() {
	if ($('nonHaasPurchased').value == 'Y') {
		config[1].columnName = numberOfContainers;
		config[1].columnId = 'quantity';
		config[1].type = 'hed';
		config[1].size = '1';

		config[3].columnName = customerMsdsNo;
		config[3].columnId = 'custMsdsNo';

		config[4].attachHeader = containerId;
		config[4].columnName = container;

		config[5].columnName = '#cspan';
		config[5].type = 'hed';
		config[5].size = '5';
		config[5].align = "center";
		config[5].width = '6';

		config[6].columnName = '#cspan';
		config[6].type = "hcoro";
		config[6].align = "center";
		config[6].width = '6';

	}
	else {
		config[1].columnName = dupRow;
		config[1].columnId = 'dupRow';

		config[3].columnName = itemId;
		config[3].columnId = 'itemId';

		config[4].columnName = containerId;
		config[4].type = 'hed';
		config[4].onChange = 'validateContainerId';
	}
}

function nhpUpdate() {
	if (updateSuccess == "Y") {
		loc = "hetnhpprintlabels.do";
		parent.children[parent.children.length] = openWinGeneric(loc, "_PrintNhpLabels", "900", "450", "yes", "50", "50", "no");
		parent.showTransitWin(messagesData.waitFor);
		updateSuccess = "N";
	}
}

function rowDupClicked() {
	dupRowClicked = true;
}

function rowDup(rId) {
	var rowId = rId * 1;
	// var rowIndex = mygrid.getRowsNum();
	var rowIndex = mygrid.haasGetRowSpanEndIndex(rowId);
	var parentIndex = rowIndex;
	var rowData = [
					'Y',// permission
					'<input type="button" class="smallBtns" onmouseover="this.className=\'smallBtns smallBtnsOver\'" onmouseout="this.className=\'smallBtns\'" id="dupRow' + (rowIndex) + '" name="dupRow' + (rowIndex)
							+ '" value="+" onclick="rowDupClicked()" >',// dup
					// row
					cellValue(rowId, 'catPartNo'),// catPartNo
					cellValue(rowId, 'itemId'), // item
					cellValue(rowId, 'containerId'), // containerId
					'', // amountRemaining
					'', // unitOfMeasure
					cellValue(rowId, 'componentMsdsNo'), // componentMsdsNo
					cellValue(rowId, 'materialDesc'),// materialDesc
					cellValue(rowId, 'containerSize'),// containerSize
					cellValue(rowId, 'containerType'),// containerType
					++kitStart,
					cellValue(rowId, 'materialId'),
					cellValue(rowId, 'applicationId'),
					cellValue(rowId, 'partId'),
					cellValue(rowId, 'validContainerId'),
					cellValue(rowId, 'catalogId'),
					cellValue(rowId, 'catalogCompanyId'),
					cellValue(rowId, 'customerMsdsDb'),
					cellValue(rowId, 'hetUsageRecording'),
					'N',
					cellJsonValue(rowId, 'solvent'),
					'N',
					cellJsonValue(rowId, 'diluent'),
					cellValue(rowId, 'defaultApplicationMethodCodPermission'),
					cellValue(rowId, 'defaultApplicationMethodCod'),
					cellValue(rowId, 'defaultPartTypePermission'),
					cellValue(rowId, 'defaultPartType'),
					cellValue(rowId, 'defaultPermitIdPermission'),
					cellValue(rowId, 'defaultPermitId'),
					cellValue(rowId, 'defaultSubstrateCodePermission'),
					cellValue(rowId, 'defaultSubstrateCode'),
					cellValue(rowId, 'itemId'),
					cellValue(rowId, 'buildingId')
	];
	
	initToggled = new Array();

	mygrid.haasAddRowToRowSpan(rowData, rowIndex);

	var start = mygrid.haasGetRowSpanStart(rowId);
	var end = mygrid.haasGetRowSpanEndIndex(rowId);
	var diff = parseInt(end) - parseInt(start);
	if (diff > 1) {
		if ($v("totalLines") == 0) {
			$("totalLines").value = 1;
			setResultFrameSize();
		}
		for ( var i = 1; i < diff; i++) {
			// rowIndex = mygrid.getRowsNum(); // data array[]
			// index, 0 starting point
			rowId++; // actual ID: value (STRING) we put in
			// the JSON data
			rowIndex++; // data array[] index, 0 starting point
			rowData = [
							'Y',
							'',
							cellValue(rowId, 'catPartNo'),
							cellValue(rowId, 'itemId'),
							cellValue(rowId, 'containerId'),
							'',
							'',
							cellValue(rowId, 'componentMsdsNo'),
							cellValue(rowId, 'materialDesc'),
							cellValue(rowId, 'containerSize'),
							cellValue(rowId, 'containerType'),
							kitStart,
							cellValue(rowId, 'materialId'),
							cellValue(rowId, 'applicationId'),
							cellValue(rowId, 'partId'),
							cellValue(rowId, 'validContainerId'),
							cellValue(rowId, 'catalogId'),
							cellValue(rowId, 'catalogCompanyId'),
							cellValue(rowId, 'customerMsdsDb'),
							cellValue(rowId, 'hetUsageRecording'),
							'N',
							cellJsonValue(rowId, 'solvent'),
							'N',
							cellJsonValue(rowId, 'diluent'),
							cellValue(rowId, 'defaultApplicationMethodCodPermission'),
							cellValue(rowId, 'defaultApplicationMethodCod'),
							cellValue(rowId, 'defaultPartTypePermission'),
							cellValue(rowId, 'defaultPartType'),
							cellValue(rowId, 'defaultPermitIdPermission'),
							cellValue(rowId, 'defaultPermitId'),
							cellValue(rowId, 'defaultSubstrateCodePermission'),
							cellValue(rowId, 'defaultSubstrateCode'),
							cellValue(rowId, 'itemId')
			];

			mygrid.haasAddRowToRowSpan(rowData, rowIndex, parentIndex);


		}
		// mygrid.selectRow(rowIndex);
	}
	mygrid.haasRenderRow(parentIndex + 1);
	// mygrid.haasSetRowSpanSelected(parentIndex + 2);
}

function update() {
	if (validationforUpdate()) {
		{
			document.genericForm.target = '';
			document.getElementById('uAction').value = 'update';

			parent.showPleaseWait();

			if (mygrid != null)
				mygrid.parentFormOnSubmit();

			document.genericForm.submit(); // back to server
		}
	}
}

function validationforUpdate() {
	var oneRowFilled = false;
	reValidate = false;
	// renderAllRows();
	var errMsg = "";
	var l = mygrid.getRowsNum();

	if ($('nonHaasPurchased').value == 'Y') {
		for ( var j = 1; j <= l; j++) {
			var q = cellValue(j, 'quantity');
			var catPartNo = cellValue(j, 'catPartNo');
			var containerType = cellValue(j, 'containerType');
			if (q != null && q != '' && catPartNo != null && catPartNo.length != 0 && containerType != null && containerType.length != 0) {
				oneRowFilled = true;
				break;
			}

		}

		if (!oneRowFilled) {
			alert(messagesData.enterValues.replace('{0}', messagesData.numberOfContainers + " & " + messagesData.partnumber + " & " + messagesData.containertype));
			return false;
		}
		for ( var i = 1; i <= l; i++) {
			var amountRemaining = cellValue(i, 'amountRemaining');
			var numContainers = cellValue(i, 'quantity');
			var containerType = cellValue(j, 'containerType');
			var catPartNo = cellValue(j, 'catPartNo');

			if (numContainers != "" && (!isPositiveInteger(numContainers) || parseInt(numContainers) < 1)) {
				alert(messagesData.quantityValRow.replace('{0}', messagesData.nom));
				return false;
			}
			else if (numContainers != "" && (amountRemaining == '' || !isFloat(amountRemaining) || parseFloat(amountRemaining) < 0.0)) {
				alert(messagesData.quantityValRow.replace('{0}', messagesData.quantity));
				return false;
			}
			else if (numContainers != "" && containerType == '') {
				alert(messagesData.enterValues.replace('{0}', messagesData.containertype));
				return false;
			}
			else if (numContainers != "" && catPartNo == '') {
				alert(messagesData.enterValues.replace('{0}', messagesData.partnumber));
				return false;
			}

			if (numContainers != "" && $("defaultPermitId" + i) != null) {
				if (cellValue(i, "defaultPermitId") == "SELECT") {
					errMsg += messagesData.permitRequired + "\n";
				}
				if (cellValue(i, "defaultApplicationMethodCod") == "SELECT") {
					errMsg += messagesData.methodRequired + "\n";
				}
				if (cellValue(i, "defaultPartType") == "N" && cellValue(i, "defaultSubstrateCode") == "SELECT") {
					errMsg += messagesData.substrateRequired + "\n";
				}
				if (errMsg.length > 0) {
					mygrid.selectRow(i - 1, true);
					alert(errMsg);
					errorsExist = true;
					return false;
				}
			}
		}
	}
	else {
		while (currentValidatingRow <= l) {
			var containerId = cellValue(currentValidatingRow, 'containerId');
			if (containerId != '')
				oneRowFilled = true;
			if (containerId != '' && cellValue(currentValidatingRow, 'validContainerId') == false) {
				reValidate = true;
				validateContainerId(currentValidatingRow);
				return;
			}
			else
				currentValidatingRow++;
		}
		if (!oneRowFilled) {
			alert(messagesData.enterValues.replace('{0}', messagesData.containerId));
			return false;
		}
		var length = mygrid.getRowsNum();
		for ( var index = 0; index < length; index++) {
			if (rowSpanMap[index] > 1) {
				var endRowSpan = index + rowSpanMap[index];
				var containerCount = 0;
				for ( var rowSpanIndex = index; rowSpanIndex < endRowSpan; rowSpanIndex++) {
					if (("" + cellValue(mygrid.getRowId(rowSpanIndex), 'containerId')).length > 0)
						containerCount++;
				}
				if (containerCount > 0 && containerCount != rowSpanMap[index]) {
					alert(messagesData.selectForEach.replace('{0}', messagesData.containerId).replace('{1}', messagesData.kit).replace('{2}', index + 1));
					currentValidatingRow = 1;
					return false;
				}

			}
			if ($("defaultPermitId" + (index + 1)) != null) {
				if (cellValue(index + 1, 'containerId') != '' && cellValue(index + 1, "defaultPermitId") == "SELECT") {
					errMsg += messagesData.permitRequired + "\n";
				}
				if (cellValue(index + 1, 'containerId') != '' && cellValue(index + 1, "defaultApplicationMethodCod") == "SELECT") {
					errMsg += messagesData.methodRequired + "\n";
				}
				if (cellValue(index + 1, 'containerId') != '' && cellValue(index + 1, "defaultPartType") == "N" && cellValue(index + 1, "defaultSubstrateCode") == "SELECT") {
					errMsg += messagesData.substrateRequired + "\n";
				}
				if (errMsg.length > 0) {
					mygrid.selectRow(index, true);
					alert(errMsg);
					errorsExist = true;
					currentValidatingRow = 1;
					return false;
				}
			}
		}
	}

	return true;
}

var initToggled = new Array();
function initToggleSubstrate(rowId) {
	if (!initToggled[rowId]) {
		initToggled[rowId] = true;
		toggleSubstrate(rowId);
		toggleMethods(rowId);
	}
}

function toggleMethods(rowId) {
	if (cellValue(rowId, "solvent").endsWith('true')) {
		var method = $("defaultApplicationMethodCod" + rowId);

		if (method != null) {
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
}

function toggleSubstrate(rowId) {
	var rowIndex = rowId;
	try {
		if ($("defaultSubstrateCode" + rowIndex) != null) {
			if (rowSpanMap[rowId] > 1) {
				var endRowSpan = rowIndex + rowSpanMap[rowId];
				for ( var i = rowIndex; i < endRowSpan; i++) {
					if ($("defaultSubstrateCode" + i) != null) {
						if (cellValue(i, "defaultPartType") != "N") {
							$("defaultSubstrateCode" + i).disabled = true;
						}
						else {
							$("defaultSubstrateCode" + i).disabled = false;
						}
					}
				}
			}
			else {
				if (cellValue(rowId, "defaultPartType") != "N") {
					$("defaultSubstrateCode" + rowId).disabled = true;
				}
				else {
					$("defaultSubstrateCode" + rowId).disabled = false;
				}
			}
		}

	}
	catch (ex) {
	}
}

function determineOTR(rowId) {
	if (!overrideUsageLogging) {
		var amount = cellValue(rowId, "amountRemaining");
		var unit = cellValue(rowId, "unitOfMeasure");

		if ("" != amount) {
			var url = "containerentryotr.do?materialId=" + cellValue(rowId, "materialId");
			url += "&amountRemaining=" + amount;
			url += "&unitOfMeasure=" + unit;
			url += "&rowId=" + rowId;
			callToServer(url);
		}
	}
}

function setOTRforRow(OTR, rowId) {
	if (OTR != cellValue(rowId, "defaultApplicationMethodCodPermission")) {
		setCellValue(rowId, "hetUsageRecording", "Y" == OTR ? "N" : "Y");
		setCellValue(rowId, "defaultApplicationMethodCodPermission", OTR);
		setCellValue(rowId, "defaultPartTypePermission", OTR);
		setCellValue(rowId, "defaultPermitIdPermission", OTR);
		setCellValue(rowId, "defaultSubstrateCodePermission", OTR);
		refreshCell(rowId, "defaultApplicationMethodCod");
		refreshCell(rowId, "defaultPartType");
		refreshCell(rowId, "defaultPermitId");
		refreshCell(rowId, "defaultSubstrateCode");
		toggleSubstrate(rowId);
		toggleMethods(rowId);
	}
}

function refreshCell(rowId, cellName) {
	setCellValue(rowId, cellName, cellJsonValue(rowId, cellName));
}
