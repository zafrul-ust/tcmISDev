var resizeGridWithWindow = true;
var selectedRowId = null;
var saveRowClass = null;

function selectRow() {
	// to show menu directly
	rightClick = false;
	rowId0 = arguments[0];
	colId0 = arguments[1];
	ee = arguments[2];

	/*
	 * if (saveRowClass.search(/haas/) == -1 &&
	 * saveRowClass.search(/Selected/) == -1)
	 * setRowClass(rowId0,''+saveRowClass+'Selected');
	 */

	if (ee != null) {
		if (ee.button != null && ee.button == 2)
			rightClick = true;
		else if (ee.which == 3)
			rightClick = true;
	}
	selectedRowId = rowId0;
	// change to here for txt object.
	if (colId0 == haasGrid.getColIndexById("remarks") && cellValue(rowId0, "remarksPermission") != 'Y') {// &&
														// cellValue("remarks"))
														// {
		haasGrid.lockRow(rowId0, "true");// return
							// true;//alert('hh');
	}
	// else
	// haasGrid.lockRow(rowId0,"false");//return true;//alert('hh');

	if (!rightClick)
		return true;

	if (showUpdateLinks)
		toggleContextMenu('rcMenu');

}

function correctInvoice() {
	var inv = cellValue(selectedRowId, "invoice");
	var custinv = cellValue(selectedRowId, "customerInvoice");
	loc = "/tcmIS/distribution/invoicecorrection.do?uAction=search&invoice=" + inv
	       +"&customerInvoice="+custinv;
	var winId = 'correctInvoice' + inv;
	children[children.length] = openWinGeneric(loc, winId.replace('.', 'a'), "820", "400", "yes", "50", "50", "20", "20", "no");
}

function checkAll(checkboxname) {
	var rowsNum = haasGrid.getRowsNum();
	rowsNum = rowsNum * 1;

	// Make sure the background render of the page has rendered all rows
	renderAllRows();

	if ($("checkAllSelected").checked) {
		for ( var p = 1; p < (rowsNum + 1); p++) {
			var cid = "selected" + p;
			if (!$(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
	}
	else {
		for ( var p = 1; p < (rowsNum + 1); p++) {
			var cid = "selected" + p;
			if (!$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
	}
	return true;
}

function isAnyRowChecked() {
	var rowsNum = haasGrid.getRowsNum();
	var rowChecked = false;
	for ( var p = 1; p < (rowsNum + 1); p++) {
		var cid = "selected" + p;
		if ($(cid) && $(cid).checked) {
			rowChecked = true;
			break;
		}
	}
	return rowChecked;
}

function printSelectedInvoices(){
	if(parent.document.getElementById("active").checked)
	{
		printDeliveryDocument();
	}
	if(parent.document.getElementById("batch").checked)
	{
		printBatch();
	}
	
}

function printDeliveryDocument() {
	if (isAnyRowChecked()) {
		loc = "/HaasReports/common/loadingfile.jsp";
		openWinGeneric(loc, "printDeliveryDocument", "800", "600", "yes", "50", "50", "20", "20", "no");

		document.genericForm.action = "../../HaasReports/report/printinvoice.do?personnelId=" + $v('personnelId');
		document.genericForm.target = "printDeliveryDocument";
		// use your report server setting.
		// loc =
		// "/HaasReports/report/printdeliverydocument.do?documentType="+documentType+"&idField="+idField;
		if (haasGrid) {
			haasGrid.parentFormOnSubmit(); // prepare grid for data
		}
							// sending
		var a = window.setTimeout("document.genericForm.submit();", 1000);
	}
	else {
		alert(messagesData.noRowChecked);
		return false;
	}
}

function printBatch() {
	if (isAnyRowChecked())
	{
		if(parent.document.getElementById("reportName").value.trim().length > 0)
		  {
			 var reportName = parent.document.getElementById("reportName").value;
			 			
			loc = "/HaasReports/common/loadingfile.jsp";
			openWinGeneric(loc, "printBatch", "800", "600", "yes", "50", "50", "20", "20", "no");
			
			document.genericForm.action = "/HaasReports/report/printinvoice.do?personnelId=" + $v('personnelId')+"&reportType=batch"+"&reportName="+reportName;
			document.genericForm.target = "printBatch";
			
			if (haasGrid) {
				haasGrid.parentFormOnSubmit(); // prepare grid for data
			}
								// sending
			var a = window.setTimeout("document.genericForm.submit();", 1000);
		  }
		else
		{
			alert(messagesData.reportname); 
		}
	}
	else {
		alert(messagesData.noRowChecked);
		return false;
	}
}

function refreshme() {
	try {
		parent.submitSearchForm();
	}
	catch (ex) {
	}
}

var children = new Array();
function closeAllchildren() {
	// if (document.getElementById("uAction").value != 'new') {
	try {
		for ( var n = 0; n < children.length; n++) {
			try {
				children[n].closeAllchildren();
			}
			catch (ex) {
			}
			children[n].close();
		}
	}
	catch (ex) {
	}
	children = new Array();

	// }
}

function checkAllEInvoices()
{
	var rowsNum = haasGrid.getRowsNum();
	var rowChecked = false;
	for ( var p = 1; p <= (rowsNum + 1); p++) {
		var cid = "selected" + p;
		if ($(cid) && $(cid).checked) {
			rowChecked = true;
			autoEmailStatus = cellValue(p, "autoEmailStatus");
			if(autoEmailStatus == '')
				return 'notAuto';
			else if(autoEmailStatus == 'Ready')
				return 'notSent';
		}
	}
	
	if(!rowChecked)
		return 'noCheck';
	return null;
}

function regenEInvoice()
{
	var results = checkAllEInvoices();
	if(results == 'noCheck') 
	{
		alert(messagesData.noRowChecked);
		return false;
	}
	else if (results == 'notAuto')
	{
		alert(messagesData.noteinvoice);
		return false;
	}
	else if (results == 'notSent')
	{
		alert(messagesData.einvoicenotsent);
		return false;
	}
	else {
		parent.showPleaseWait();
		document.getElementById('uAction').value = 'regenEInvoice';
		var now = new Date();
		var startSearchTime = parent.document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();	

		if (haasGrid) {
			haasGrid.parentFormOnSubmit(); 
		}
		var a = window.setTimeout("document.genericForm.submit();", 1000);
	}
}

function showEInvoiceHistory()
{
	var inv = cellValue(selectedRowId, "invoice");
	loc = "printinvoiceehistory.do?uAction=showEInvoiceHistory&invoice=" + inv + "&startSearchTime=" + new Date().getTime();
	children[children.length] = openWinGeneric(loc, "eInvoiceHistory", "820", "400", "yes", "50", "50", "20", "20", "no");
}