var mygrid;
/*
 * Set this to be false if you don't want the grid width to resize based on
 * window size.
 */
var resizeGridWithWindow = true;
var showNewOption = false;
var activateOption = false;

if (showUpdateLinks) {
	var inputSize = new Array();
	inputSize = {
		"defaultPaymentTerms" : 50,
		"status" : 2
	};

	var maxInputLength = new Array();
	maxInputLength = {
		"defaultPaymentTerms" : 50,
		"status" : 2
	};
}
/*
 * TODO- Allow copy and paste in the grid with ctrl+c and ctrl+v - Block
 * ctrl+mouse wheel on whole page -right click to print receipts,BOl,.etc
 */

/*
 * This is really the same as before. Except now there is a call to initialize
 * the grid.
 */
function resultOnLoad() {

	try {

		if (!showUpdateLinks) // Dont show any update links if the user does
								// not have permissions
		{
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		} else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}

	} catch (ex) {
	}

	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) {
		document.getElementById("supplierAddressViewBean").style["display"] = "";
		/* this is the new part. */
		doInitGrid();
	} else {
		document.getElementById("supplierAddressViewBean").style["display"] = "none";
	}

	/* This dislpays our standard footer message */
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on or
	 * off.
	 */
	setResultFrameSize();
}

function doInitGrid() {
	/*
	 * Give the div name that holds the grid the same name as your viewbean or
	 * dynabean you want for updates
	 */
	mygrid = new dhtmlXGridObject('supplierAddressViewBean');
	mygrid.setImagePath("/dhtmlxGrid/codebase/imgs/");

	/*
	 * To internationalize column headers, get the values from messagesData. To
	 * show tooltips in the header you can use spans to define the heading and
	 * tooltip. We would want to show tooltips for Columns that can be updated.
	 * Make sure to escape any , in your tool tip by //,
	 */
	/** **************************************************** */
	// The permission has beed set to N. This following codes do not match the
	// data. Need to be updated if the permission is reset.
	var okColHeader = messagesData.ok + "," + messagesData.status + ",";
	var okColWdith = ",4,8";
	var defaualtPaymentTermsWdith = ",8,";
	var okColAlign = ",left,left";
	var okColSorting = ",haasHch,haasCoro";
	var defaultPaymentTermsColSorting = ",haasStr,";
	var commentType = ",txt";
	var colOkType = ",hch,hcoro";
	var defaultPaymentTermType = ",ro";
	var colOkTT = ",false,false";
	var colOk = ",ok,status";
	var colOkVisibility = ",false,false";
	// The above codes do not match the data.
	if (!showUpdateLinks) {
		okColHeader = ""; // messagesData.status+",";
		okColWdith = ""; // ",6";
		// defaualtPaymentTermsWdith=",8,";
		defaualtPaymentTermsWdith = ",0,";
		okColAlign = ""; // ",left";
		okColSorting = ""; // ",haasStr";
		defaultPaymentTermsColSorting = ",haasStr,";
		commentType = ",ro";
		colOkType = ""; // ",ro";
		defaultPaymentTermType = ",ro";
		colOkTT = ""; // ",false";
		colOk = ""; // ",status";
		colOkVisibility = ""; // ",false";
	}

	var existingPriceListHeader = "";
	var existingPriceListWidth = "";
	var existingPriceListAlign = "";
	var existingPriceListSorting = "";
	var existingPriceListColTypes = "";
	var existingPriceListTooltips = "";
	var existingPriceListId = "";
	var showExistingPriceList = "";
	if ($v("fromSupplierPriceList") == "Y") {
		existingPriceListHeader = "," + messagesData.existingpricelist;
		existingPriceListWidth = ",5";
		existingPriceListAlign = ",left";
		existingPriceListSorting = ",haasStr";
		existingPriceListColTypes = ",ro";
		existingPriceListTooltips = ",false";
		existingPriceListId = ",existingPriceList";
		showExistingPriceList = ",false";
	}

	mygrid.setHeader("" + "permission" + "," + messagesData.supplierid + ","
			+ messagesData.suppliername + "," + messagesData.supplieraddress
			+ existingPriceListHeader + "," + messagesData.level + ","
			+ okColHeader + messagesData.comments + ","
			+ messagesData.defaultpaymentterms + "," + messagesData.phone + ","
			+ messagesData.email + "," + messagesData.newsupplier + ","
			+ messagesData.fedtaxid + "," + messagesData.vatregistration + ","
			+ messagesData.esupplierid + "," + messagesData.sapvendorcode + ","
			+ messagesData.accountnumber + "," + messagesData.status);

	/*
	 * Set initial widths for your columns, set the initial widths for Font Size
	 * smallest. We will proportionally increase the widths based on the user
	 * font size. Set hidden collums to size 0.
	 */

	mygrid.setInitWidths("0,6,11,16" + existingPriceListWidth + ",8"
			+ okColWdith + ",20" + defaualtPaymentTermsWdith
			+ "10,8,10,10,10,10,10,10,6");

	/*
	 * You can set column alingments, all string and date values will be left
	 * aligned and numbers will be right aligned.
	 */
	mygrid.setColAlign("left,left,left,left" + existingPriceListAlign + ",left"
			+ okColAlign
			+ ",left,left,left,left,left,left,left,left,left,left,left");

	/*
	 * Set the type of sort to do on this column.If the gird has rowspans >1 set
	 * all columns sotring to be na. Sorting for grid with rowspans>1 is not
	 * supported correctly yet. Date read-only sorting is not supported yet. For
	 * date columns set the sorting type to na
	 */

	mygrid.setColSorting("na,haasStr,haasStr,haasStr"
					+ existingPriceListSorting
					+ ",haasStr"
					+ okColSorting
					+ ",haasStr"
					+ defaultPaymentTermsColSorting
					+ "haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr,haasStr");
	// mygrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");

	/*
	 * Set column types, you can define and editable columns here. ro -read only
	 * ed -editable sinlge line text date -hcal select drop down - multiline
	 * text - link - hlink checkbox -
	 */

	mygrid.setColTypes("ro,ro,ro,ro" + existingPriceListColTypes + ",ro"
			+ colOkType + "" + commentType + "" + defaultPaymentTermType
			+ ",ro,ro,ro,ro,ro,ro,ro,ro,ro");

	/*
	 * This enables tooltips on a column and on mouseover displays the value of
	 * the cell in a tooltip. We will enable tooltips only for columns whose
	 * width might be less than the text data in that column. General rule >30
	 * characters give a tooltip. Most likely candidates are packaging, item
	 * desc, part desc, any user comments etc.
	 */

	mygrid.enableTooltips("false,false,true,true"
					+ existingPriceListTooltips
					+ ",false"
					+ colOkTT
					+ ",true,true,false,false,false,false,false,false,false,false,false");

	/*
	 * Set columIds. this will be the id you want your dynabean to be. this is
	 * the same as setting id attribute on an input element.
	 */
	mygrid.setColumnIds("permission,supplier,supplierName,addressLines"
					+ existingPriceListId
					+ ",level"
					+ colOk
					+ ",supplierNotes,defaultPaymentTerms,mainPhone,email,newSupplierId,federalTaxId,vatRegistrationNumber,eSupplierId,sapVendorCode,accountNumber,status");

	/*
	 * This tells the grid to send all data in the grid to the server in
	 * dynabean compatible format. You can comment this out if this is a
	 * read-only page not sending any data back to the server.
	 */
	mygrid.submitOnlyChanged(false);
	mygrid.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	/*
	 * Can also define which columns you want submitted to the server, avoid
	 * submitting data fields with date time in them this will cause problems on
	 * server side.
	 */
	// mygrid.submitColumns([false,false,false,false,false,false,false,false,false,false,false]);
	/***************************************************************************
	 * set setColumnsVisibility false shows the column -According to dhtmlx this
	 * is a feature Use this to hide columns which is data you need to do
	 * updates/send to server. I usually stick them at the end of the grid.
	 */

	mygrid.setColumnsVisibility("true,false,false,false"
			+ showExistingPriceList + ",false" + colOkVisibility
			+ ",false,true,false,false,false,false,false,false,false,false");

	/*
	 * This keeps the row height the same, true will wrap cell content and
	 * height of row will change.
	 */
	mygrid.enableMultiline(false);
	/*
	 * This is used to tell the grid that we have row height set based on the
	 * font size.
	 */
	mygrid.setAwaitedRowHeight(gridRowHeight);

	mygrid.attachEvent("onRowSelect", selectRow);
	mygrid.attachEvent("onRightClick", selectRightclick);

	/*
	 * This is to enable edit on click. If a cell is editiable it will show as
	 * soon as the row is selected.
	 */
	mygrid.enableEditEvents(true, false, false);
	/*
	 * enable/disable light mouse navigation mode (row selection with mouse
	 * over, editing with single click), mutual exclusive with enableEditEvents
	 * Causes some weired mouse behaviour, moves focus to the right most part of
	 * the gird for no reason.
	 */
	// mygrid.enableLightMouseNavigation(true);
	/*
	 * This allows doubleclick on any header (or use dropdown below the grid) to
	 * adjust column size according to cell value size. If sorting is enabled
	 * dblclick sorts also.
	 */
	// mygrid.enableColumnAutoSize(true);
	/*
	 * this enabels smart rendering, which buffers the loading to avoid any huge
	 * memory usages. this is not compatible with rowspans.
	 */
	mygrid.enableSmartRendering(true);

	mygrid.setSkin("haas");

	mygrid.init();

	/* loading from JSON object */
	mygrid.parse(jsonMainData, "json");

	/* This is to disable the mouse wheel ctrl page zoom problem in IE. */
	if (_isIE)
		mygrid.entBox.onmousewheel = stop_event;

	/*
	 * Allows to turn on or off columns in the grid. The menu that showsup needs
	 * some work.
	 */
	// mygrid.enableHeaderMenu(true);
	/* You can use this to set minwidth on a column in the grid. */
	/* mygrid.setColumnMinWidth(150, 8); */

	/* This will update the column headers widths according to font size. */
	updateColumnWidths(mygrid);

	/* This is to set the column headers fo editable cells to a different color */
	// changeUpdatableColumnHdr();
	/*
	 * This is to copy the ctrl+c to clipboard, and ctrl+v to paste to
	 * clipboard.
	 */
	if (_isIE)
		mygrid.entBox.onselectstart = function() {
			return true;
		};

	setHaasGrid(mygrid);
}

function selectRightclick(rowId, cellInd) {
	var stat = cellValue(mygrid.getSelectedRowId(), "status");
	if (!showNewOption) {
		toggleContextMenu("suppplierContact");
	} else {
		if (!activateOption) {
			toggleContextMenu("suppplierContactWithNewOption");
		} else {
			if (stat == 'Inactive')
				toggleContextMenu("suppplierContactWithNewOptionActivate");
			else
				toggleContextMenu("suppplierContactWithNewOptionModify");
		}
	}
}

function selectRow(rowId, cellInd) {
	if (parent.valueElementId.length > 0 && parent.displayElementId.length > 0) {

		var supplierid = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("supplier")).getValue();
		var suppliername = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("supplierName")).getValue();
		var selectedUser = parent.document.getElementById("selectedRow");
		var columnId = mygrid.getColumnId(cellInd);
		var stausCol = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("status")).getValue();
		if ((parent.statusFlag.length > 0) && (stausCol == 'Inactive')) {
			// nothing
			selectedUser.innerHTML = '';
		} else {
			selectedUser.innerHTML = '<a href="#" onclick="selectedRow(); return false;">'
					+ messagesData.selectedRowMsg
					+ ' : '
					+ suppliername
					+ '</a>';
			// set variable to main
			parent.returnSelectedValue = suppliername;
			parent.returnSelectedId = supplierid;
		}
	}
}

function showErrorMessages() {
	parent.showErrorMessages();
}

function submitSearchForm() {
	var flag = validateForm();
	if (flag) {
		parent.showPleaseWait();
		return true;
	} else {
		return false;
	}
}

function validateForm() {
	return true;
}

function showSupplierContacts() {
	var supplierid = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("supplier")).getValue();
	loc = "posuppliercontact.do?action=search&supplier=" + supplierid;// +"&displayElementId=supplierContactName&valueElementId=contactId";
	openWinGeneric(loc, "shoSupplierContacts", "800", "450", "yes", "50", "50", "no")
}
function showPurchaseHistory() {
	var supplier = cellValue(mygrid.getSelectedRowId(), "supplier");
	var loc = "supplierposhistory.do?supplier=" + supplier;
	openWinGeneric(loc, "SupplierPosHistory", "800", "600", "yes", "50", "50");
}

function updatePoSupplier() {
	/* Set any variables you want to send to the server */
	var flag = validateForm();
	if (flag) {
		var action = document.getElementById("uAction");
		action.value = 'update';
		parent.showPleaseWait();
		mygrid.parentFormOnSubmit(); // prepare grid for data sending
		document.genericForm.submit();
	}
}

function modifySupplier() {
	var supplier = cellValue(mygrid.getSelectedRowId(), "supplier");
	var pay = cellValue(mygrid.getSelectedRowId(), "defaultPaymentTerms");
	var loc = "supplierrequest.do?uAction=modify&supplierRequestType=Modify&supplier="
			+ encodeURIComponent(supplier)
			+ "&oriDefaultPaymentTerms="
			+ encodeURIComponent(pay);
	openWinGeneric(loc, "_ModifySupplier", "800", "600", "yes", "50", "50");
}

function manageRemitToAddress() {
	var supplier = cellValue(mygrid.getSelectedRowId(), "supplier");
	var supplierName = URLEncode(cellValue(mygrid.getSelectedRowId(), "supplierName"));
	var loc = "remittomanagementmain.do?supplier=" + supplier + "&supplierName=" + supplierName;
	openWinGeneric(loc, "RemitToManagement", "" + parent.window.innerWidth, "400", "yes", "50", "50");
}

function requestChangePaymentTerms() {

	var supplier = cellValue(mygrid.getSelectedRowId(), "supplier");
	var loc = "/tcmIS/supply/supplierrequestupdate.do?uAction=modify&supplierRequestType="
			+ encodeURIComponent('Change Payment Terms')
			+ "&supplier="
			+ encodeURIComponent(supplier);
	try {
		parent.parent.openIFrame("newSupplierRequestNew", loc, "" + messagesData.newsupplierrequest + "", "", "N");
	} catch (ex) {
		openWinGeneric(loc, "newSupplierRequestDetail" + "", "800", "600", "yes", "50", "50");
	}
}

function requestActivation() {

	var supplier = cellValue(mygrid.getSelectedRowId(), "supplier");
	var loc = "/tcmIS/supply/supplierrequestupdate.do?uAction=modify&supplierRequestType=Activate" + "&supplier=" + encodeURIComponent(supplier);
	try {
		parent.parent.openIFrame("newSupplierRequestNew", loc, "" + messagesData.newsupplierrequest + "", "", "N");
	} catch (ex) {
		openWinGeneric(loc, "newSupplierRequestDetail" + "", "800", "600", "yes", "50", "50");
	}
}

function requestModify() {

	var supplier = cellValue(mygrid.getSelectedRowId(), "supplier");
	var pay = cellValue(mygrid.getSelectedRowId(), "defaultPaymentTerms");

	// var loc =
	// "/tcmIS/supply/supplierrequestupdate.do?uAction=modify&supplierRequestType="+
	// encodeURIComponent('Modify')+"&supplierRequestId="+supplier;
	var loc = "/tcmIS/supply/supplierrequestupdate.do?uAction=modify&supplierRequestType=Modify&supplier="
			+ encodeURIComponent(supplier)
			+ "&oriDefaultPaymentTerms="
			+ encodeURIComponent(pay);

	try {
		parent.parent.openIFrame("newSupplierRequestNew", loc, "" + messagesData.newsupplierrequest + "", "", "N");
	} catch (ex) {
		openWinGeneric(loc, "newSupplierRequestDetail" + "", "800", "600", "yes", "50", "50");
	}
}

function showCurrentPaymentTerms() {
	var supplierid = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("supplier")).getValue();
	var supplierName = URLEncode(mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("supplierName")).getValue());
	loc = "showcurrentpaymentterms.do?uAction=showcurrentpaymentterms&supplierId=" + supplierid + "&supplierName=" + supplierName;
	openWinGeneric(loc, "ShowCurrentPaymentTerms", "800", "600", "yes", "50", "50", "yes");
}