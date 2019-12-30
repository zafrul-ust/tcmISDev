/** todo Internationalize* */
var submitcount = 0;
var updatecount = 0;
var selectedRowId = null;
var children = new Array();

function SubmitOnlyOnce() {
	try {
		var sourceHubName = null;
		sourceHubName = hubO.options[hubO.selectedIndex].text;

		var sourceHubNameObject = document.getElementById("sourceHubName");
		sourceHubNameObject.value = sourceHubName;
	} catch (ex) {
		// alert("Non-Pickable Status");
	}

	if (submitcount == 0) {
		submitcount++;
		try {
			var target = document.all.item("TRANSITPAGE");
			target.style["display"] = "";
			var target1 = document.all.item("MAINPAGE");
			target1.style["display"] = "none";
		} catch (ex) {
		}

		return true;
	} else {
		alert(messagesData.submitOnlyOnce);
		return false;
	}
}

String.prototype.trim = function() {
	// skip leading and trailing whitespace
	// and return everything in between
	return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function buildDropDown(arr, defaultObj, eleName) {
	var obj = $(eleName);
	for ( var i = obj.length; i > 0; i--)
		obj[i] = null;
	// if size = 1 or 2 show last one, first one is all, second one is the
	// only choice.
	if (arr == null || arr.length == 0)
		setOption(0, defaultObj.name, defaultObj.id, eleName);
	else if (arr.length == 1)
		setOption(0, arr[0].name, arr[0].id, eleName);
	else {
		var start = 0;
		if (defaultObj.nodefault)
			start = 0; // will do something??
		else {
			setOption(0, defaultObj.name, defaultObj.id, eleName);
			start = 1;
		}
		for ( var i = 0; i < arr.length; i++) {
			setOption(start++, arr[i].name, arr[i].id, eleName);
		}
	}
	obj.selectedIndex = 0;
}

function setOps() {
	buildDropDown(opshubig,defaults.ops,"opsEntityId");
 	$('opsEntityId').onchange = opsChanged;
    $('sourceHub').onchange = hubChanged;	
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
    	$('opsEntityId').value = defaultOpsEntityId;
    }
    opsChanged();
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0) {
    	$('sourceHub').value = defaultHub;
    	hubChanged();
    }
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0 && preferredInventoryGroup != null && preferredInventoryGroup.length > 0)
    	$('inventoryGroup').value = preferredInventoryGroup;
}

function opsChanged() {
	var opsO = $("opsEntityId");
	var arr = null;
	if (opsO.value != '') {
		for (i = 0; i < opshubig.length; i++)
			if (opshubig[i].id == opsO.value) {
				arr = opshubig[i].coll;
				break;
			}
	}

	buildDropDown(arr, defaults.hub, "sourceHub");
	hubChanged();
	if (defaults.ops.callback)
		defaults.ops.callback();
}

function hubChanged() {
	var opsO = $("opsEntityId");
	var hubO = $("sourceHub");
	var arr = null;
	if (opsO.value != '' && hubO.value != '') {
		for (i = 0; i < opshubig.length; i++)
			if (opshubig[i].id == opsO.value) {
				for (j = 0; j < opshubig[i].coll.length; j++)
					if (opshubig[i].coll[j].id == hubO.value) {
						document.getElementById("sourceHubName").value = hubO.options[hubO.selectedIndex].text;
						arr = opshubig[i].coll[j].coll;
						break;
					}
				break;
			}
	}
	buildDropDown(arr, defaults.inv, "inventoryGroup");
	if (defaults.hub.callback)
		defaults.hub.callback();
}

function setDefault() {
	if ($v("selectedHub").length > 0) {
		$("opsEntityId").value = $v("selectedOpsEntityId");
		opsChanged();
	}
	if ($v("selectedHub").length > 0) {
		$("sourceHub").value = $v("selectedHub");
		hubChanged();
	}
	if ($v("selectedInventoryGroup").length > 0)
		$("inventoryGroup").value = $v("selectedInventoryGroup");
}

function changeSearchCriterion() {
	if ($v("category") == "Non-Chemicals") {
		setSearchWhat(searchWhatNonChemArray);
	} else {
		setSearchWhat(searchWhatChemArray);
	}
}
var onloadSearchType = true;
function setSearchWhat(searchWhatArray) {
	var obj = $("searchWhat");
	for ( var i = obj.length; i >= 0; i--)
		obj[i] = null;

	for ( var j = 0; j < searchWhatArray.length; j++) {
		setOption(j, searchWhatArray[j].text, searchWhatArray[j].id,
				"searchWhat");
	}
	$("searchWhat").value = $v("selectedSearchWhat");

	if ($v("selectedSearchWhat") == ''
			|| ($v("category") == "Non-Chemicals" && ($v("selectedSearchWhat") == 'transferRequestId' || $v("selectedSearchWhat") == 'customerRmaId')))
			obj.selectedIndex = 0;
	else
		$("searchWhat").value = $v("selectedSearchWhat");
	// obj.selectedIndex = 0;
	
	changeSearchTypeOptions(obj.value);
}

function getSelectedSearchWhat(selectedValue) {
	if ($("searchWhat").selectedIndex < 4)
		$("selectedSearchWhat").value = $v("searchWhat");
	else
		$("selectedSearchWhat").value = '';
	
	changeSearchTypeOptions(selectedValue);
}

function changeSearchTypeOptions(selectedValue) 
{
	  var searchType = $('searchType');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;
	  
	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if((searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith') &&
		    	(selectedValue == 'PO' || selectedValue == 'Receipt Id' || selectedValue == 'Item ID' || selectedValue == 'transferRequestId' || selectedValue == 'customerRmaId'))
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchType")
		    if(onloadSearchType && searchMyArr[i].value == $v('searchTypeSelected'))
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
	  onloadSearchType = false;
}

function validateForm() {
	var result = true;
	var what = document.getElementById('searchWhat');
	if (what.value != "Item Desc"
			&& !isFloat(document.getElementById('search').value.trim(), true)) {
		alert(what.options[what.selectedIndex].text + " : "
				+ messagesData.mustbeanumberinthisfield);
		document.getElementById('search').focus();
		result = false;
	}
	return result;
}

function doexcelpopup() {
	var flag = validateForm();
	if (flag == false)
		return false;
	var userAction = document.getElementById("userAction");
	userAction.value = "createExcel";

	try {
		children[children.length] = openWinGenericViewFile(
				'/tcmIS/common/loadingfile.jsp', '_newGenerateExcel', '650',
				'600', 'yes');
	} catch (ex) {
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',
				'_newGenerateExcel', '650', '600', 'yes');
	}

	document.genericForm.target = '_newGenerateExcel';
	var a = window.setTimeout("document.genericForm.submit();", 500);
	return false;
}

function searchForm() {
	var flag = validateForm();
	if (flag == false)
		return false;
	document.genericForm.target = '';
	return true;
}

function searchTransferRequestId() {
	$("userAction").value = 'searchTransferRequestId';
	document.genericForm.target = '';
	document.genericForm.submit();
}

function selectRow(rowId) {
	var selectedRow = document.getElementById("rowId" + rowId + "");

	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	selectedRow.className = "selected" + selectedRowClass.value + "";

	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowId"
				+ selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass"
				+ selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value
				+ "";
	}
	selectedRowId = rowId;

	var dataCount = document.getElementById("selectedDataCount" + selectedRowId
			+ "").value;
	var docType = document.getElementById("docType" + dataCount + "").value;
	var transferRequestId = document.getElementById("transferRequestId"
			+ dataCount + "").value;
	var itemId = document.getElementById("itemId" + dataCount + "").value;
	var customerRmaId = $v("customerRmaId" + dataCount + "");
	var radianPo = document.getElementById("radianPo" + dataCount + "").value;
	var incomingTesting = document.getElementById("incomingTesting" + dataCount + "").value;
	// alert("docType:"+docType);
	vitems = new Array();

	if (docType == 'IT' && transferRequestId.trim().length > 0)
		vitems[vitems.length] = "text=" + messagesData.potitle + ";url=javascript:showrecforinvtransfrQc();";
	// toggleContextMenu('docTypeIT');
	/*
	 * else { if (itemId.trim().length > 0) toggleContextMenu('docTypeNotIT'); }
	 */
	vitems[vitems.length] = "text=" + messagesData.itemtitle + ";url=javascript:showPreviousReceivedQc();";
	vitems[vitems.length] = "text=" + messagesData.receiptspecs + ";url=javascript:receiptSpecs();";
	vitems[vitems.length] = "text=" + messagesData.viewaddreceipts + ";url=javascript:showProjectDocuments();";
	if (radianPo.trim().length > 0) {
		if (docType != 'IT')
			vitems[vitems.length] = "text=" + messagesData.viewpurchaseorder + ";url=javascript:showRadianPo();";
		vitems[vitems.length] = "text=" + messagesData.receivingchecklist + ";url=javascript:showReceivingJacket();";
      	var sourceHub = document.getElementById("sourceHub");
	    if (sourceHub.value == '2101') {
        vitems[vitems.length] = "text=" + messagesData.newreceivingchecklist + ";url=javascript:openReceivingQcDetailView();";
        }		
    }
	
	if (docType == 'IA' && customerRmaId != null)
		vitems[vitems.length] = "text=" + messagesData.viewrma + ";url=javascript:viewRMA();";

	if (itemId.trim().length > 0)
		vitems[vitems.length] = "text=" + messagesData.itemnotes + ";url=javascript:showItemNotes();";
	// TODO: Add the fuction for this right click
	/*
	 * if (customerRmaId.trim().length > 0) vitems[vitems.length ] =
	 * "text="+messagesData.viewcustomerreturnrequest+";url=javascript:showCustomerReturnRequest();";
	 */

	if (incomingTesting == 'Y')
		vitems[vitems.length] = "text=" + messagesData.startmarstest + ";url=javascript:startMARStest();";

	replaceMenu('receivingqcMenu', vitems);

	toggleContextMenu('receivingqcMenu');
}

function replaceMenu(menuname, menus) {
	var i = mm_returnMenuItemCount(menuname);

	for (; i > 1; i--)
		mm_deleteItem(menuname, i);

	for (i = 0; i < menus.length;) {
		var str = menus[i];

		i++;
		mm_insertItem(menuname, i, str);
		// delete original first item.
		if (i == 1)
			mm_deleteItem(menuname, 1);
	}
}

function startMARStest()
{
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId
			+ "").value;
	var receiptId = document.getElementById("receiptId" + dataCount + "").value;
	
	var loc = "/tcmIS/haas/testrequestform.do?uAction=create&receiptId="+receiptId;
	
	try {
		parent.openIFrame("testrequest" , loc, ""+messagesData.marsdetail+" "+ receiptId + "","","N");
	}
	catch (ex) {
		openWinGeneric(loc, "testrequest" , "900", "600", "yes", "80", "80", "yes");
	}
   
}

function selectRowNonChem(rowId) {
	var selectedRow = document.getElementById("rowId" + rowId + "");

	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	selectedRow.className = "selected" + selectedRowClass.value + "";

	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowId"
				+ selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass"
				+ selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value
				+ "";
	}
	selectedRowId = rowId;

	toggleContextMenu('nonChemQc');
}

function receiptSpecs() {
	var receiptId = $v("receiptId" + selectedRowId + "");
	var loc = "/tcmIS/distribution/receiptspec.do?receiptId=" + receiptId;
	children[children.length] = openWinGeneric(loc, "receiptSpecs", "800",
			"400", "yes", "50", "50", "20", "20", "no");
}

function checkClosePoLine(rowNumber) {

}

function addBintoMainPage() {
	var totallines = opener.document.getElementById("totallines").value * 1;
	var selectedRow = false;
	var vvHubBin = document.getElementById("vvHubBin").value;
	var itemId = document.getElementById("itemId");
	var selectedRowNumber = document.getElementById("rowNumber").value;

	for ( var rowNumber = 0; rowNumber < totallines; rowNumber++) {
		var mainItemId = opener.document.getElementById("itemId" + rowNumber
				+ "");
		if (mainItemId.value == itemId.value) {
			var mainBinO = opener.document.getElementById("bin" + rowNumber
					+ "");
			// alert("Found the line to add Bin "+vvHubBin+"");
			if (selectedRowNumber == rowNumber) {
				selectedRow = true;
			} else {
				selectedRow = false;
			}

			try {
				var binName = null;
				binName = mainBinO.value;
				if (mainBinO.value == "NONE" || mainBinO.value.length == 0) {
					mainBinO[0] = null;
					opener.addOptionItem(rowNumber, vvHubBin, vvHubBin,
							selectedRow);
				} else {
					opener.addOptionItem(rowNumber, vvHubBin, vvHubBin,
							selectedRow);
				}
			} catch (ex) {
				// alert("error");
			}

		}
	}
	cancel();
}

function addOptionItem(rowNumber, value, text, selectedRow) {
	obj = document.getElementById("bin" + rowNumber + "")
	var index = obj.length;
	obj.options[index] = new Option(text, value);
	if (selectedRow) {
		obj.options[index].selected = true;
	}
}

function showVvHubBins(rowNumber) {
	var itemId = document.getElementById("itemId" + rowNumber + "");
	var branchPlant = document.getElementById("branchPlant" + rowNumber + "");

	var loc = "/tcmIS/hub/showhubbin.do?branchPlant=" + branchPlant.value
			+ "&userAction=showBins&rowNumber=" + rowNumber + "&itemId=";
	loc = loc + itemId.value;
	try {
		children[children.length] = openWinGeneric(loc, "showVvHubBins", "300",
				"150", "no", "80", "80");
	} catch (ex) {
		openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
	}

}

function checkAllNonChemicalReceipts() {
	var result;
	var checkNonChemicalReceipts = document
			.getElementById("checkNonChemicalReceipts");
	if (checkNonChemicalReceipts.checked) {
		result = true;
	} else {
		result = false;
	}

	var totalLines = document.getElementById("totallines").value;
	for ( var p = 0; p < totalLines; p++) {
		try {
			var updateStatus = document.getElementById("updateStatus" + p + "").value;
		} catch (ex) {
			updateStatus = "";
		}

		if (updateStatus != 'readOnly') {
			try {
				var ok = document.getElementById("ok" + p + "");
				ok.checked = result;
			} catch (ex) {
			}
		}
	}
}

function showProjectDocuments(receiptId, rowNumber) {
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId
			+ "").value;
	var inventoryGroup = $v("inventoryGroup" + dataCount + "");
	var receiptId = $v("receiptId" + dataCount + "");
	var loc = "/tcmIS/hub/receiptdocuments.do?receiptId=" + receiptId
			+ "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "";
	try {
		children[children.length] = openWinGeneric(loc,
				"showAllProjectDocuments", "450", "300", "no", "80", "80");
	} catch (ex) {
		openWinGeneric(loc, "showAllProjectDocuments", "450", "300", "no",
				"80", "80");
	}
}

function showRadianPo(poNumber) {
  var dataCount = document.getElementById("selectedDataCount" + selectedRowId + "").value;
  var radianPo = document.getElementById("radianPo" + dataCount + "").value;

  loc = "/tcmIS/supply/purchaseorder.do?po=" + radianPo + "&Action=searchlike&subUserAction=po";
  try {
 	children[children.length] = openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
 } catch (ex){
 	openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
 }
}

function showReceivingJacket(rowNumber) {
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId + "").value;
	var radianPo = document.getElementById("radianPo" + dataCount + "");
	var lineItem = document.getElementById("poLine" + dataCount + "");
	var itemId = document.getElementById("itemId" + dataCount + "");
	var branchPlant = document.getElementById("branchPlant" + dataCount + "");

	loc = "/cgi-bin/radweb/old_sheet.cgi?po=";
	loc = loc + radianPo.value;
	loc = loc + "&item=" + itemId.value;
	loc = loc + "&line=" + lineItem.value;
	if (branchPlant.value == "2101") {
		loc = loc + "&tab=N";
	} else {
		loc = loc + "&tab=Y";
	}
	// alert(loc);
	try {
		children[children.length] = openWinGenericViewFile(loc,
				"showReceivingJacket11", "600", "600", "yes");
	} catch (ex) {
		openWinGenericViewFile(loc, "showReceivingJacket11", "800", "600",
				"yes");
	}
}

/*
 * function assignpaperSize(size) { var paperSize =
 * document.getElementById("paperSize"); paperSize.value =size; }
 */

function printReceivingQcLabels(hubNumber) {
	var loc = "/tcmIS/hub/receivingqclabels.do?";
	var paperSize = document.getElementById("paperSize").value;
	loc = loc + "paperSize=" + paperSize;
	loc = loc + "&hubNumber=" + hubNumber;
	if (hubNumber == "2106") {
		printNormandaleQCLabels();
	} else {
		loc = loc + "&hubNumber=" + hubNumber;
		printQCLabels(loc);
	}
}

function printReceivingQcDocLabels(hubNumber) {
	var loc = "/tcmIS/hub/receivingqclabels.do?";
	var paperSize = document.getElementById("paperSize").value;
	loc = loc + "paperSize=receiptDocument";
	loc = loc + "&hubNumber=" + hubNumber;
	
	if( $v('groupReceiptDoc') == "Y" ) {
			loc += "&groupReceiptDoc=Y";
			groupReceiptDoc = false;
			$('groupReceiptDoc').value = "";
	}
	
	if (hubNumber == "2106") {
		printNormandaleQCLabels();
	} else {
		loc = loc + "&hubNumber=" + hubNumber;
		printQCLabels(loc);
	}
}

function printReceivingQcReceiptLabels(hubNumber) {
	var loc = "/tcmIS/hub/receivingqclabels.do?";
	loc = loc + "paperSize=receiptDetail";
	loc = loc + "&hubNumber=" + hubNumber;
	if (hubNumber == "2106") {
		printNormandaleQCLabels();
	} else {
		loc = loc + "&hubNumber=" + hubNumber;
		printQCLabels(loc);
	}
}

/*
 * Function used to print the QC labels for hubs other than 2106 (Normandale).
 */
function printQCLabels(loc) {
	var skipKitLabels = document.getElementById("skipKitLabels");
	if (skipKitLabels.checked) {
		loc = loc + "&skipKitLabels=Yes";
	} else {
		loc = loc + "&skipKitLabels=No";
	}
	try {
		children[children.length] = openWinGeneric(loc,
				"printReceivingQcLabels", "800", "500", "yes", "80", "80");
	} catch (ex) {
		openWinGeneric(loc, "printReceivingQcLabels", "800", "500", "yes",
				"80", "80");
	}
}

/*
 * Function used to print the QC labels for hub=2106 (Normandale), as the
 * printing is delegated to /HaasReports.
 */
function printNormandaleQCLabels() {
	var labelReceipts = getLabelReceipts();
	if (labelReceipts.trim().length > 0) {
		var loc = "/HaasReports/report/printSeagateLabel.do?pr_receipts_ids="
				+ labelReceipts;
		openWinGeneric(loc, "PrintSeagateLabel", "800", "600", "yes", "50",
				"50", "20", "20", "yes");
	}
	return;
}

/*
 * Function that returns the list of receipt ids for which the ok checkbox is
 * selected.
 */
function getLabelReceipts() {
	var totalLines = document.getElementById("totallines").value;
	var labelReceipts = "";
	var checkedCount = 0;
	for ( var p = 0; p < totalLines; p++) {
		try {
			var updateStatus = document.getElementById("updateStatus" + p + "").value;
		} catch (ex) {
			updateStatus = "";
		}
		if (updateStatus != 'readOnly') {
			try {
				var ok = document.getElementById("ok" + p + "");
				if (ok.checked) {
					var receiptId = document.getElementById("receiptId" + p
							+ "");
					if (checkedCount > 0) {
						labelReceipts += ','
					}
					labelReceipts += receiptId.value;
					checkedCount++
				}
			} catch (ex) {
			}
		}
	}
	return labelReceipts;
}

function printReceivingBoxLabels() {
	var labelReceipts = getLabelReceipts();
	if (labelReceipts.trim().length > 0) {
		var loc = "/tcmIS/hub/printreceiptboxlabels.do?labelReceipts="
				+ labelReceipts + "";
		loc = loc + "&paperSize=64";
		try {
			children[children.length] = openWinGeneric(loc, "printBinLabels11",
					"800", "500", "yes", "80", "80");
		} catch (ex) {
			openWinGeneric(loc, "printBinLabels11", "800", "500", "yes", "80",
					"80");
		}
	} else {
		alert(messagesData.norowselected);
	}
}

var groupReceiptDoc=false;

function isAtLeastOneRowSelected(checkPO) {
	groupReceiptDoc = false;
	var totalLines = document.getElementById("totallines").value;
	var count = 0;
	var po = null;
	var samePo = false;
	for ( var line = 0; line < totalLines; line++) {
		try {
			var ok = document.getElementById("ok" + line);
			if (ok.checked) {
				count++;
				thisPO = null; 
				try {
					// make sure it always get it.					
					thisPO = document.getElementById("poLabel" + line).innerHTML;
				} catch(exx) {}
				if( po == null ) {
					po = thisPO;
					samePo = true;
				}
				if( !thisPO )
					samePo = false;
				if( samePo && po != thisPO ) {
					samePo = false;
				}
			}
		} catch (ex) {
		}
	}
	if( count == 0 ) {
		alert(messagesData.norowselected);
		return false;
	}
	if( checkPO && count >1 && samePo ) {
//		if( confirm(messagesData.groupReceiptDoc) ) {
		if( confirm("Do you want to group these receipt certs together?\nClick 'OK' to proceed with them grouped.\nClick 'Cancel' to proceed without them grouped.") ) {
			document.getElementById("groupReceiptDoc").value = "Y";
			groupReceiptDoc = true;
			return true;
		}
	}
	document.getElementById("groupReceiptDoc").value = "";
	return true;
}

function reverseReceipt(rowNumber) {
	var found = false;
	var receiptId = document.getElementById("receiptId" + rowNumber + "");

	if (receiptId.value.trim().length > 0) {
		loc = "/tcmIS/hub/showreversereceipt.do?receiptId=";
		loc = loc + receiptId.value;
		try {
			children[children.length] = openWinGeneric(loc,
					"Reverse_Receiving", "300", "150", "no")
		} catch (ex) {
			openWinGeneric(loc, "Reverse_Receiving", "300", "150", "no")
		}
		return true;
	}
	return false;
}

function submitMainPage() {
	var submitSearch = opener.document.getElementById("submitSearch");
	submitSearch.click();
	window.close();
}

function checkReceiptStatus(rowNumber) {
	var lotStatus = document.getElementById("lotStatus" + rowNumber + "");
	var origlotStatus = document.getElementById("origlotStatus" + rowNumber + "");
	var qualityControlItem = $v("qualityControlItem" + rowNumber+ "");
	
	if (lotStatus.value.length == 0 && qualityControlItem != 'N') {
		var selecelemet = lotStatus.selectedIndex;
		var testelementtext = lotStatus.options[selecelemet].text;
		if (origlotStatus == null || origlotStatus.value != testelementtext) {
			alert(messagesData.nopermissiontochangestatus + " "
					+ testelementtext + ".")
		}

		i = 0;
		while (i < lotStatus.length) {
			var elementtext = lotStatus.options[i].text;

			if (elementtext == origlotStatus.value) {
				lotStatus.selectedIndex = i;
			}
			i += 1;
		}
	} else {
		if (lotStatus.value == "Customer Purchase"
				|| lotStatus.value == "Write Off Requested"
				|| lotStatus.value == "3PL Purchase") {
			loc = "/tcmIS/hub/terminalstatusrootcause.do?lotStatus=";
			loc = loc + lotStatus.value + "&rowNumber=" + rowNumber;
			try {
				children[children.length] = openWinGeneric(loc,
						"terminal_root_cause", "500", "300", "no");
			} catch (ex) {
				openWinGeneric(loc, "terminal_root_cause", "500", "300", "no");
			}
		} else {
			lotStatusRootCause = document.getElementById("lotStatusRootCause"
					+ rowNumber + "");
			lotStatusRootCause.value = "";

			responsibleCompanyId = document
					.getElementById("responsibleCompanyId" + rowNumber + "");
			responsibleCompanyId.value = "";

			lotStatusRootCauseNotes = document
					.getElementById("lotStatusRootCauseNotes" + rowNumber + "");
			lotStatusRootCauseNotes.value = "";
		}
	}
}

function sendTerminalRootCauseValues() {
	rowNumber = document.getElementById("rowNumber").value;
	rootCause = document.getElementById("rootCause");
	rootCauseCompany = document.getElementById("rootCauseCompany");
	rootCauseNotes = document.getElementById("rootCauseNotes");

	try {
	lotStatusRootCause = opener.document.getElementById("lotStatusRootCause"
			+ rowNumber + "");
	lotStatusRootCause.value = rootCause.value;

	responsibleCompanyId = opener.document
			.getElementById("responsibleCompanyId" + rowNumber + "");
	responsibleCompanyId.value = rootCauseCompany.value;

	lotStatusRootCauseNotes = opener.document
			.getElementById("lotStatusRootCauseNotes" + rowNumber + "");
	lotStatusRootCauseNotes.value = rootCauseNotes.value;
	}
	catch(ex) {}

	try {
		opener.setRootCauseCallback( 
				rowNumber,
				rootCause.value,
				rootCauseCompany.value,
				rootCauseNotes.value );
		valueSent = true;
	}
	catch(ex) {}

	window.close();
}

function showrecforinvtransfrQc(rowNumber) {
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId
			+ "").value;

	var transferRequestId = document.getElementById("transferRequestId"
			+ dataCount + "");
	loc = "/tcmIS/hub/transfertransactions.do?transferRequestId=";
	loc = loc + transferRequestId.value;
	try {
		children[children.length] = openWinGeneric(loc,
				"Previous_Transfer_Transactions", "700", "400", "yes");
	} catch (ex) {
		openWinGeneric(loc, "Previous_Transfer_Transactions", "700", "400",
				"yes");
	}

}

function showPreviousReceivedQc(rowNumber) {
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId
			+ "").value;
	var itemId = document.getElementById("itemId" + dataCount + "");
	var branchPlant = document.getElementById("branchPlant" + dataCount + "");
	var inventoryGroup = document.getElementById("inventoryGroup" + dataCount
			+ "");

	loc = "/tcmIS/hub/showreceivedreceipts.do?itemId=" + itemId.value + "&hub="
			+ branchPlant.value + "&inventoryGroup=" + inventoryGroup.value
			+ "&approved=Y";

	/*
	 * loc =
	 * "/tcmIS/Hub/ReceivingQC?session=Active&UserAction=previousreceiving&poline=";
	 * loc = loc + itemId.value; loc = loc + "&HubNo=" + branchPlant.value;
	 */
	try {
		children[children.length] = openWinGeneric(loc, "Previous_Receiving",
				"700", "450", "yes")
	} catch (ex) {
		openWinGeneric(loc, "Previous_Receiving", "700", "450", "yes")
	}
}

function showPreviousPoLineQc(rowNumber) {
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId
			+ "").value;
	var radianPo = document.getElementById("radianPo" + dataCount + "");
	var poLine = document.getElementById("poLine" + dataCount + "");
	var itemId = document.getElementById("itemId" + dataCount + "");
	var branchPlant = document.getElementById("branchPlant" + dataCount + "");
	var inventoryGroup = document.getElementById("inventoryGroup" + dataCount
			+ "");

	loc = "/tcmIS/hub/showreceivedreceipts.do?radianPo=" + radianPo.value
			+ "&poLine=" + poLine.value + "&hub=" + branchPlant.value
			+ "&inventoryGroup=" + inventoryGroup.value + "&approved=Y";

	/*
	 * loc =
	 * "/tcmIS/Hub/ReceivingQC?session=Active&UserAction=previousreceiving&poline=";
	 * loc = loc + poLine.value; loc = loc + "&purchorder=" + radianPo.value;
	 * loc = loc + "&HubNo=" + branchPlant.value;
	 */

	try {
		children[children.length] = openWinGeneric(loc,
				"Previous_PO_Line_Receiving", "700", "450", "yes");
	} catch (ex) {
		openWinGeneric(loc, "Previous_PO_Line_Receiving", "700", "450", "yes");
	}
}

function viewRMA() {
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId
			+ "").value;
	var customerRmaId = document.getElementById("customerRmaId" + dataCount
			+ "").value;

	var loc = "/tcmIS/distribution/customerreturnrequest.do?action=search&rmaId="
			+ customerRmaId;// +"&lineItem="+lineItem+"&prNumber="+prNumber;

	try {
		parent.parent.openIFrame("showcustomerreturnrequest" + customerRmaId
				+ "", loc, "" + messagesData.customerreturnrequest + " "
				+ customerRmaId + "", "", "N");
	} catch (ex) {
		openWinGeneric(loc, "showcustomerreturnrequest", "900", "600", "yes",
				"80", "80", "yes");
	}
}

function showItemNotes() {
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId
			+ "").value;
	var itemId = document.getElementById("itemId" + dataCount + "");
	var loc = "/tcmIS/supply/edititemnotes.do?itemId=" + itemId.value;
	var winId = 'showItemNotes';

	children[children.length] = openWinGeneric(loc, winId.replace('.', 'a'),
			"800", "600", "yes", "50", "50", "20", "20", "no");
}

function addnewBin() {
	var sourceHub = document.getElementById("sourceHub");
	var sourceHubName = document.getElementById("sourceHubName");

	if (sourceHubName.value.length > 0 && sourceHub.value.length > 0) {
		var loc = "/tcmIS/hub/addhubbin.do?branchPlant=" + sourceHub.value
				+ "&sourceHubName=" + sourceHubName.value
				+ "&userAction=addNewBin";
		try {
			children[children.length] = openWinGeneric(loc, "addnewBin", "600",
					"200", "no", "80", "80");
		} catch (ex) {
			openWinGeneric(loc, "addnewBin", "600", "200", "no", "80", "80");
		}
	}
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 180);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
} 

function checkNonChemicalReceivingQcInput(rowNumber) {

}

function checkLabelQuantity(rowNumber) {
	var errorMessage = " " + messagesData.validvalues + " \n\n";
	var warningMessage = messagesData.alert + " \n\n";
	var errorCount = 0;
	var warnCount = 0;

	var ok = document.getElementById("ok" + rowNumber + "");
	if (ok.checked) {
		try {
			var labelQuantity = document.getElementById("labelQuantity"
					+ rowNumber + "");
			if (!(isInteger(labelQuantity.value))
					|| labelQuantity.value * 1 == 0) {
				errorMessage = errorMessage + " " + messagesData.labelquantity
						+ ". \n";
				errorCount = 1;
				labelQuantity.value = "";
			}
		} catch (ex) {

		}
	}

	if (errorCount > 0) {
		alert(errorMessage);
		ok.checked = false;
	}

	if (warnCount > 0) {
		alert(warningMessage);
	}
}

function checkAllChemicalLines() {
	var totalLines = document.getElementById("totallines").value;
	var result = true;

	for ( var p = 0; p < totalLines; p++) {
		try {
			var updateStatus = document.getElementById("updateStatus" + p + "").value;
		} catch (ex) {
			updateStatus = "";
		}

		if (updateStatus != 'readOnly') {
			try {
				var ok = document.getElementById("ok" + p + "");
				if (ok.checked) {
					var lineResult = checkChemicalReceivingQcInput(p, 'confirm');
					if (lineResult == false) {
						result = false;
						break;
					}
				}
			} catch (ex) {
			}
		}
	}

	return result;
}

function checkChemicalReceivingQcInput(rowNumber, action) {
	var receiptId = document.getElementById("receiptId" + rowNumber + "");
	var errorMessage = messagesData.forreceipt + " " + receiptId.value + " "
			+ messagesData.validvalues + " \n\n";
	var errorCount = 0;
	var warnCount = 0;
	var expireError = false;

	var ok = document.getElementById("ok" + rowNumber);
	if (ok.checked) {
		var shelfLifeBasis = $v("receiptShelfLifeBasis" + rowNumber );
		var mfgLot = document.getElementById("mfgLot" + rowNumber + "");
		if (mfgLot.value.trim().length == 0) {
			errorMessage = errorMessage + " " + messagesData.mfglot + ". \n";
			errorCount = 1;
		}

		var supplierShipDate = dateToIntString($v("vendorShipDate" + rowNumber));
		var dateOfReceipt = dateToIntString($v("dateOfReceipt" + rowNumber));
		var dateOfManufacture = dateToIntString($v("dateOfManufacture" + rowNumber));
		if ($v("vendorShipDate" + rowNumber + "").length > 0
				&& (supplierShipDate > dateOfReceipt || supplierShipDate < dateOfManufacture)) {
			errorMessage = errorMessage + " " + messagesData.actsupshpdate
					+ "\n";
			errorCount = 1;
		}

		 if (shelfLifeBasis == "M" && $v("dateOfManufacture" + rowNumber).length <= 0) {
			errorMessage = errorMessage + " " + messagesData.dom + "\n";
			errorCount = 1;
		}
		else if (shelfLifeBasis == "S" && $v("dateOfShipment" + rowNumber).length <= 0) {
			errorMessage = errorMessage + " " + messagesData.dos + "\n";
			errorCount = 1;
		}
		else if (shelfLifeBasis == "R" && $v("dateOfReceipt" + rowNumber).length <= 0) {
			errorMessage = errorMessage + " " + messagesData.dor + "\n";
			errorCount = 1;
		}
		
		
		var dateOfShipment = dateToIntString($v("dateOfShipment" + rowNumber));
		if ($v("dateOfManufacture" + rowNumber).length > 0
				&& $v("dateOfShipment" + rowNumber + "").length > 0
				&& dateOfManufacture > dateOfShipment) {
			errorMessage = errorMessage + " " + messagesData.shipbeforemanufacture + "\n";
			errorCount = 1;
		}

		var expireDateString = document.getElementById("expireDateString"
				+ rowNumber + "");
		if (expireDateString.value.trim().length == 0) {
			errorMessage = errorMessage + " " + messagesData.expiredate + "\n";
			errorCount = 1;
			expireDateString.value = "";
			expireDateError = true;
		}

		var qaStatement = $v("qaStatement" + rowNumber + "");
		if (qaStatement != null && qaStatement != "" && qaStatement != "1"
				&& qaStatement != "2") {
			errorMessage = errorMessage + " " + messagesData.qastatement + "\n";
			$("qaStatement" + rowNumber + "").value = "";
			errorCount = 1;
		}

		try {
			var lotStatusValue = document.getElementById("lotStatus"
					+ rowNumber + "").value;
		} catch (ex) {
			lotStatusValue = "";
		}
		/*
		 * if (lotStatusValue.trim().length > 0 && action=="confirm") { var
		 * dateOfReceipt =
		 * document.getElementById("dateOfReceipt"+rowNumber+""); if
		 * (dateOfReceipt.value.trim().length == 0) { errorMessage =
		 * errorMessage + " "+messagesData.dor+"\n" ; errorCount = 1;
		 * dateOfReceipt.value = document.getElementById("todayDate").value; }
		 * 
		 * 
		 * var expireDateString =
		 * document.getElementById("expireDateString"+rowNumber+""); // if (!(
		 * expireDate.value.trim() == "Indefinite" || expireDate.value.trim() ==
		 * "indefinite" || expireDate.value.trim() == "INDEFINITE" )) // { if
		 * (expireDateString.value.trim().length == 0) { errorMessage =
		 * errorMessage + " "+messagesData.expiredate+"\n" ; errorCount = 1;
		 * expireDate.value = ""; } //} }
		 */
		if (action == "confirm") {
			try {
				var lotStatus = document.getElementById("lotStatus" + rowNumber+ "");
			 	var qualityControlItem = $v("qualityControlItem" + rowNumber+ "");
				if (lotStatus.value.trim() == "Incoming") {
					errorMessage = errorMessage + " " + messagesData.lotstatus
							+ " " + messagesData.cannotbe + " "
							+ messagesData.incoming + ". \n";
					errorCount = 1;
				} else if (lotStatus.value.length == 0 && qualityControlItem != 'N') {
					var selecelemet = lotStatus.selectedIndex;
					var testelementtext = lotStatus.options[selecelemet].text;

					errorMessage = errorMessage + " "
							+ messagesData.nopermissionstoqcstatus + " "
							+ testelementtext + ".";
					errorCount = 1;
				}
			} catch (ex) {
			}
		}
		
	}

	if (errorCount > 0) {
		alert(errorMessage);
		ok.checked = false;
		return false;
	}

	// This is just a warning
	var expireDate = dateToIntString($v("expireDate" + rowNumber + ""));
	var minimumExpireDate = dateToIntString($v("minimumExpireDate" + rowNumber
			+ ""));
	var receiptId = $v("receiptId" + rowNumber + "");

	if (action == 'warning' && ok.checked == true
			&& expireDate < minimumExpireDate) {
		alert(messagesData.expdatelessthanminexpdate.replace(/[{]0[}]/g,
				receiptId));
	}

	return true;
}

function cancel() {
	window.close();
}

function hubchanged() {
	var hubO = document.getElementById("sourceHub");

	try {
		var sourceHubName = null;
		sourceHubName = hubO.options[hubO.selectedIndex].text;

		var sourceHubNameObject = document.getElementById("sourceHubName");
		sourceHubNameObject.value = sourceHubName;
	} catch (ex) {
		// alert("Non-Pickable Status");
	}

	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for ( var i = inventoryGroupO.length; i > 0; i--) {
		inventoryGroupO[i] = null;
	}
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
}

function showinvlinks(selectedhub) {
	var invgrpid = new Array();
	invgrpid = altinvid[selectedhub];

	var invgrpName = new Array();
	invgrpName = altinvName[selectedhub];

	if (invgrpid.length == 0) {
		setCab(0, messagesData.all, "")
	}

	for ( var i = 0; i < invgrpid.length; i++) {
		setCab(i, invgrpName[i], invgrpid[i])
	}
}

function setCab(href, text, id) {
	var optionName = new Option(text, id, false, false)
	var inventoryGroupO = document.getElementById("inventoryGroup");
	inventoryGroupO[href] = optionName;
}

function checkOriginalLot(rowNumber) {
	try {
		var mfgLot = document.getElementById("mfgLot" + rowNumber + "");
		var origMfgLot = document.getElementById("origMfgLot" + rowNumber + "");

		if (origMfgLot.value.trim().length > 0) {
			var ok = document.getElementById("ok" + rowNumber + "");
			if (origMfgLot.value.trim() == mfgLot.value.trim()) {
				if (ok.disabled) {
					ok.disabled = false;
				}
			} else {
				if (!ok.disabled) {
					ok.disabled = true;
				}
			}
		}
	} catch (ex) {
	}
}

function submitGenLabel(actionElementName) {
	if (isAtLeastOneRowSelected()) {
		var actionElement = document
				.getElementById("" + actionElementName + "");
		actionElement.value = "" + actionElement + "";

		var submitReceive = document.getElementById("submitReceive");
		submitReceive.value = "";

		document.genericForm.submit();
	}
}

function submitGenDocLabel(actionElementName) {
	if (isAtLeastOneRowSelected(true)) { // checking PO && if at least one
		var actionElement = document
				.getElementById("" + actionElementName + "");
		actionElement.value = "" + actionElement + "";

		var submitDocumentLabelsPrint = document
				.getElementById("submitDocumentLabelsPrint");
		submitDocumentLabelsPrint.value = "" + actionElement + "";

		var submitReceive = document.getElementById("submitReceive");
		submitReceive.value = "";

		document.genericForm.submit();
	}
}

function submitGenReceiptLabel(actionElementName) {
	if (isAtLeastOneRowSelected()) {
		var actionElement = document
				.getElementById("" + actionElementName + "");
		actionElement.value = "" + actionElement + "";

		var submitReceiptLabelsPrint = document
				.getElementById("submitReceiptLabelsPrint");
		submitReceiptLabelsPrint.value = "" + actionElement + "";

		var submitReceive = document.getElementById("submitReceive");
		submitReceive.value = "";

		document.genericForm.submit();
	}
}

function submitNonChemConfirm(actionElementName) {
	var actionElement = document.getElementById("" + actionElementName + "");
	actionElement.value = "" + actionElement + "";

	var submitPrint = document.getElementById("submitPrint");
	submitPrint.value = "";
	var submitSearch = parent.document.getElementById("submitSearch");
	// submitSearch.click();
	$("transitPage").style["display"] = "";
	$("mainPage").style["display"] = "none";
	document.genericForm.submit();
}

function submitConfirm(actionElementName) {
	var actionElement = document.getElementById("" + actionElementName + "");
	actionElement.value = "" + actionElement + "";

	var submitPrint = document.getElementById("submitPrint");
	submitPrint.value = "";
	
	var showConfirm = false;
	if(actionElementName == 'submitReceive') {
		var totalLines = document.getElementById("totallines").value;
		for ( var p = 0; p < totalLines*1; p++) {
			var qcOk = $v("qcOk" + p + "");
			var ok = $("ok" + p + "");
			if(ok!=null && ok.checked && qcOk == 'N') {
		//		selectRow(p);
				showConfirm = true;
	        }
	    }
	    if (showConfirm) {
		    var text = messagesData.sendinghubwillbealtered;
			var answer = confirm (text);
			if (!answer) {
			    return false;
			}
		}
	}
	

	if (checkAllChemicalLines()) {
		// var submitSearch =
		// parent.document.getElementById("submitSearch");
		// submitSearch.click();
		$("transitPage").style["display"] = "";
		$("mainPage").style["display"] = "none";

		document.genericForm.target = '';
		document.genericForm.submit();
	}
}

function changeMlItem() {
	var checkedCount = 0;
	var selectedItem = $("selectedItem");
	if (checkMlItemInput())/* selectedItem.value.trim().length > 0 && ) */
	{
		if (selectedItem.value.trim().length > 0) {
			var receiptsList = "";
			var totalRecords = $("totallines");
			for (j = 0; j < (totalRecords.value * 1); j++) {
				var receiptId = "";
				receiptId = $("receiptId" + (j) + "");
				currentcheckBox1 = $("ok" + (j) + "");
				itemType = $("itemType" + (j) + "");
				newChemRequestId = $("newChemRequestId" + (j) + "");

				if (currentcheckBox1.checked && itemType.value == "ML"
						&& newChemRequestId.value.trim().length == 0) {
					if (checkedCount > 0) {
						receiptsList += ','
					}
					receiptsList += receiptId.value;
					checkedCount++
				}
			}

			// var labelReceipts =
			// document.getElementById("labelReceipts").value;
			receiptsList = receiptsList.replace(/,/gi, "%2c");
			if (receiptsList.trim().length > 0) {
				var loc = "/tcmIS/hub/receivingitemsearchmain.do?receipts="
						+ receiptsList + "";
				var hubNumber = document.getElementById("sourceHub").value;
				loc = loc + "&hubNumber=" + hubNumber;
				loc = loc + "&listItemId=" + $("selectedItem").value;
				loc = loc + "&inventoryGroup="
						+ $("selectedInventoryGroup").value;
				loc = loc + "&catPartNo=" + $("selectedCatPartNo").value;
				loc = loc + "&catalog=" + $("selectedCatalogId").value;
				loc = loc + "&catalogCompanyId="
						+ $("selectedCatalogCompanyId").value;

				try {
					children[children.length] = openWinGeneric(loc,
							"changeItem", "800", "400", "yes", "80", "80");
				} catch (ex) {
					openWinGeneric(loc, "changeItem", "800", "400", "yes",
							"80", "80");
				}

			}
		}
	}
}

function checkMlItemInput() {
	var noLinesChecked = 0;
	var rowNumber = "";
	// var currentcheckBox = $("ok"+rowNumber+"");
	var totalRecords = $("totallines");
	var selectedItem = $("selectedItem");
	// var lineitemID = $("itemId"+rowNumber+"");

	/*
	 * if (currentcheckBox.checked) { noLinesChecked ++; }
	 */

	/*
	 * if ( selectedItem.value.trim().length > 0 && (lineitemID.value !=
	 * selectedItem.value) ) { alert("You cannot choose a receipt with Different
	 * ML Item"); currentcheckBox.checked = false; return false; }
	 */

	var allClear = 0;
	var finalMsgt;
	finalMsgt = messagesData.cannotselectreceiptwith + " \n\n";

	for (j = 0; j < (totalRecords.value * 1); j++) {
		var lineitemID1 = "";
		lineitemID1 = $("itemId" + (j) + "");
		itemType = $("itemType" + (j) + "");
		currentcheckBox1 = $("ok" + (j) + "");
		newChemRequestId = $("newChemRequestId" + (j) + "");

		if (currentcheckBox1.checked && itemType.value == "ML") {
			if (noLinesChecked == 0) {
				lineitemID = $("itemId" + (j) + "");
				rowNumber = j;
			}

			noLinesChecked++;
			if (lineitemID.value != lineitemID1.value) {
				if (allClear == 0) {
					finalMsgt = finalMsgt + messagesData.differentmlitem + "\n";
				}
				allClear += 1;
			} else if (newChemRequestId.value.trim().length > 0) {
				if (allClear == 0) {
					finalMsgt = finalMsgt + " "
							+ messagesData.pendingnewchemrequest + " "
							+ newChemRequestId.value + "\n";
				}
				allClear += 1;
			}
		}

	}

	if (noLinesChecked == 0) {
		selectedItem.value = "";
	}

	if (allClear < 1) {
		if (noLinesChecked != 0) {
			selectedItem.value = $("itemId" + rowNumber + "").value;
			$("selectedInventoryGroup").value = $("inventoryGroup" + rowNumber
					+ "").value;
			$("selectedCatalogId").value = $("catalogId" + rowNumber + "").value;
			$("selectedCatPartNo").value = $("catPartNo" + rowNumber + "").value;
			$("selectedCatalogCompanyId").value = $("catalogCompanyId"
					+ rowNumber + "").value;
		}
		return true;
	} else {
		alert(finalMsgt);
		return false;
	}
}

function $(a) {
	return document.getElementById(a);
}

function submitSearchForm() {
	var submitSearch = document.getElementById("submitSearch");
	submitSearch.click();
	// document.genericForm.submit();
}

function unitLabelPartNumber(rowNumber) {
	var unitLabelPrinted = document.getElementById("unitLabelPrinted"
			+ rowNumber + "");
	if (unitLabelPrinted.checked) {
		var itemId = document.getElementById("itemId" + rowNumber + "");
		var branchPlant = document.getElementById("branchPlant" + rowNumber
				+ "");
		var inventoryGroup = document.getElementById("inventoryGroup"
				+ rowNumber + "");

		loc = "/tcmIS/hub/unitlabelpartnumber.do?&rowNumber=" + rowNumber;
		loc = loc + "&itemId=" + itemId.value;
		loc = loc + "&hub=" + branchPlant.value;
		loc = loc + "&inventoryGroup=" + inventoryGroup.value;
		try {
			children[children.length] = openWinGeneric(loc,
					"terminal_root_cause", "500", "300", "no");
		} catch (ex) {
			openWinGeneric(loc, "terminal_root_cause", "500", "300", "no");
		}
	}
}

function sendUnitLabelPartNumber() {
	rowNumber = document.getElementById("rowNumber").value;
	catPartNo = document.getElementById("catPartNo");

	openerunitLabelCatPartNo = opener.document
			.getElementById("unitLabelCatPartNo" + rowNumber + "");
	try {
		if( openerunitLabelCatPartNo ) // non-grid case.
			openerunitLabelCatPartNo.value = catPartNo.value;
		else {
			opener.cell(rowNumber,"unitLabelCatPartNo").setValue(catPartNo.value);
		}
	} catch(ex){} // don't break;
	window.close();
}

function expiredDateChanged(rowid) {
	$("indefiniteDate").value = "01-" + month_abbrev_Locale_Java[pageLocale][0]
			+ "-3000";

	if ($v("expireDateString" + rowid) == messagesData.indefinite) {
		$("expireDate" + rowid).value = $v("indefiniteDate");
	} else {
		$("expireDate" + rowid).value = $v("expireDateString" + rowid);
	}
	// alert("expireDate"+$v("expireDate"+rowid));
}

function closeAllchildren() {
	// You need to add all your submit button vlues here. If not, the window
	// will close by itself right after we hit submit button.
	// if (document.getElementById("uAction").value != 'new') {
	try {
		for ( var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			} catch (ex) {
			}
			children[n].close();
		}
	} catch (ex) {
	}
	children = new Array();
}

function openReceivingQcDetailView()
{		
	var dataCount = document.getElementById("selectedDataCount" + selectedRowId + "").value;
	var receiptId = $v("receiptId" + dataCount + "");
	var now = new Date();
	var loc = "/tcmIS/hub/receivingqcchecklist.do?search=" + receiptId 
	+ "&sourceHub=" + $v('selectedHub') + "&sort=" + $v('sort') + "&opsEntityId=" + $v('opsEntityId') + "&startSearchTime=" + now.getTime() + "&sourceHubName=" + encodeURIComponent($v('sourceHubName'));;
	
	openWinGeneric(loc, "receivingQcCheckList" + receiptId, "1000", "950", "yes", "80", "80");
}