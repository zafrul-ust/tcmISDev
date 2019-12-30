windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var beangrid;
var accessNotificationCol = "modified";
var permissionNotificationCol = "updated";

function isArray(testObject) {
	return testObject && !(testObject.propertyIsEnumerable('length'))
			&& typeof testObject === 'object'
			&& typeof testObject.length === 'number';
}

function showFacility(selectedCompany, selectedInv) {
	var idArray = altFacilityId[selectedCompany];
	var nameArray = altFacilityName[selectedCompany];
	var selectI = 0;
	var inserted = 0;

	var inv = $("facilityId");
	for (var i = inv.length; i > 0; i--) {
		inv[i] = null;
	}
	if (nameArray != null)
		for (var i = 0; i < nameArray.length; i++) {
			if (nameArray.length != 2 || i != 0) {
				setOption(inserted, nameArray[i], idArray[i], "facilityId");
				if (selectedInv == idArray[i])
					selectI = inserted;
				inserted++;
			}
		}
	if (inserted == 0)
		setOption(inserted, messagesData.myfacilities, "", "facilityId");
	$("facilityId").selectedIndex = selectI;
}

function showCompany(oldCompany) {
	var idArray = altCompanyId;
	var nameArray = altCompanyName;
	var selectI = 0;
	var inserted = 0;

	for (var i = 0; i < nameArray.length; i++) {
		if (nameArray.length != 2 || i == 1) {
			setOption(inserted, nameArray[i], idArray[i], "companyId");
			if (oldCompany == idArray[i])
				selectI = inserted;
			inserted++;
		}
	}
	$("companyId").selectedIndex = selectI;
}

function setCompany() {
	var oldCompany = $("oldcompanyId").value;
	var oldinv = $("oldfacilityId").value;
	try {
		showCompany(oldCompany);
		showFacility($("companyId").value, oldinv);
	} catch (ex) {
	}
}

function CompanyChanged() {

	var Company = $("companyId");
	var selectedCompany = Company.value;
	showFacility(selectedCompany, null);
}

function validateSearchForm() {
	return true;
}

function submitSearchForm() {
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	if (validateSearchForm()) {
		$('uAction').value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		return true;
	} else {
		return false;
	}
}

function showErrorMessages() {
	parent.showErrorMessages();
}

/**
 * check and modify checkboxes of the same column if given row is at the top of the group
 * @param topRowId row's id
 * @param colId column's id
 * @param notificationCol column to be set to 'Y' if check box in given column is changed
 */
function setCheckboxesBasedOnTopCheckbox(topRowId, colId, notificationCol) {
	if (cellValue(topRowId, "application") == 'All') {
		var preCompanyId = cellValue(topRowId, "companyId");
		var preFacilityId = cellValue(topRowId, "facilityId");
		for (var i = topRowId + 1; i <= $v("totalLines"); i++) {
			if (preCompanyId == cellValue(i, "companyId") && preFacilityId == cellValue(i, "facilityId")) { //check if the row is still within scope
				if (cell(topRowId, colId).isChecked()) { //only disable if the top checkbox is checked
					if (cell(i, colId).isChecked())
						if (colId == "newApplicationAccess")
							cell(i, colId).setChecked(false);
						else
							setPermission(i, colId, false);
					//if we are going to change the checkbox's value, need to signal the change at the row
					setCellValue(i, notificationCol, "Y");
					cell(i, colId).setDisabled(true);
				} else {//only undisable if the top checkbox is not checked
					cell(i, colId).setDisabled(false);
					if (colId == "newApplicationAccess" && Number(cellValue(i, "totalPermissionChecked")) > 0) {
						cell(i, colId).setChecked(true);
						setCellValue(i, notificationCol, "Y");
					}
				}
	
				preCompanyId = cellValue(i, "companyId");
				preFacilityId = cellValue(i, "facilityId");
			} else
				break;
		}
	}
}

function accessChanged(rowId, colId) {
	setCellValue(rowId, accessNotificationCol, "Y");	
	setCheckboxesBasedOnTopCheckbox(rowId, colId, accessNotificationCol);
	
	if (cell(rowId, colId).isChecked()) { //if Access is checked and Create MR is not, we want Display Application checked also
		if ($("GenerateOrders" + rowId) != null && $("GenerateOrders" + rowId).disabled == false && $("GenerateOrders" + rowId).checked == false)
			if ($("DisplayApplication" + rowId) != null	&& $("DisplayApplication" + rowId).disabled == false && $("DisplayApplication" + rowId).checked == false) {
				setPermission(rowId, "DisplayApplication", true);
			}
	} else
		//uncheck all permissions if Access checkbox of the row is unchecked
		for (var j = 1; j <= $v("headerCount"); j++) {
			//Note: 5 being the no. of columns before the Permission columns (starting from 0)
			// 2 * j is the index of the columns contain check boxes (skipping preceding hidden columns)
			if (cell(rowId, 5 + 2 * j).isChecked()) {
				setPermission(rowId, 5 + 2 * j, false);
			}
		}
}

/**
 * check/uncheck checkbox corresponding to the given column and handle the action's consequences
 * @param rowId
 * @param colId
 * @param val
 */
function setPermission(rowId, colId, val) {
	cell(rowId, colId).setChecked(val);
	setCellValue(rowId, permissionNotificationCol, "Y");
	
	//change number of checked permissions of the row
	if (val)
		setCellValue(rowId, "totalPermissionChecked", Number(cellValue(rowId, "totalPermissionChecked")) + 1);
	else
		setCellValue(rowId, "totalPermissionChecked", Number(cellValue(rowId, "totalPermissionChecked")) - 1);
	
	setCheckboxesBasedOnTopCheckbox(rowId, colId, permissionNotificationCol);
}

function permissionChanged(rowId, colId) {
	setPermission(rowId, colId, cellValue(rowId, colId) == 'true');
	
	if (cell(rowId, colId).isChecked()) { //Access checkbox must be checked if a Permission checkbox is checked
		if ($("newApplicationAccess" + rowId).disabled == false && $("newApplicationAccess" + rowId).checked == false) {
			cell(rowId, "newApplicationAccess").setChecked(true);
			accessChanged(rowId, "newApplicationAccess");
		}
	}
}

function createXSL() {
	document.getElementById('uAction').value = "createXSL";
	document.genericForm.target = '_excel_report_file';
	openWinGenericExcel('/tcmIS/common/loadingfile.jsp', '_excel_report_file',
			'650', '600', 'yes');
	setTimeout("document.genericForm.submit()", 300);
}

function validateForm() {
	//prepare access
	for (var i = 0; i < beangrid.getRowsNum(); i++) {
		var uacc = '';
		var rowid = i + 1;
		for (var j = 0; j < $v("headerCount"); j++)
			uacc += beangrid.cells(rowid, 7 + 2 * j).getValue() + "=";
		cell(rowid, "userGroupAccess").setValue(uacc);
	}
	
	return true;
}

function _simpleUpdate(act, val) {
	if (window['validateForm'] && !validateForm())
		return false;

	if (!act)
		act = 'uAction';
	if (!val)
		val = 'update';

	$(act).value = val;
	parent.showPleaseWait();

	if (beangrid)
		beangrid.parentFormOnSubmit(); //prepare grid for data sending
	document.genericForm.submit();

	return false;
}

function myResultOnLoad() {
	try {
		if (!showUpdateLinks) {/*Dont show any update links if the user does not have permissions*/
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		} else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	} catch (ex) {
	}

	initGridWithGlobal(gridConfig);

	if ($v('totalLines') > 0) {
		document.getElementById(gridConfig.divName).style["display"] = "";
	} else {
		document.getElementById(gridConfig.divName).style["display"] = "none";
	}

	if (showErrorMessage)
		showErrorMessages();

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
	
	var interval = setInterval(function() {
	    if(document.readyState === 'complete') {
	        clearInterval(interval);
	        prepareInitialCheckboxes();
	    }    
	}, 100);
}

/**
 * Handle checkboxes' disable state after grid has been rendered
 */
function prepareInitialCheckboxes() {
	for (var i = 1; i <= $v("totalLines");) {
		if (cellValue(i, "application") == 'All') {
			var topRowId = i;
			var preCompanyId = cellValue(topRowId, "companyId");
			var preFacilityId = cellValue(topRowId, "facilityId");
			for (i++; i <= $v("totalLines"); i++) {
				if (preCompanyId == cellValue(i, "companyId") && preFacilityId == cellValue(i, "facilityId")) { //check if the row is still within scope
					for (var j = 0; j <= $v("headerCount"); j++) {
						var colName = config[5 + 2 * j].columnId;
						//Note: 5 being the no. of columns before the Permission columns (starting from 0)
						// 2 * j is the index of the columns contain check boxes (skipping preceding hidden columns)
						if (cell(topRowId, colName).isChecked()) {
							if (cell(i, colName).isChecked())
								cell(i, colName).setChecked(false);
							cell(i, colName).setDisabled(true);
						}
					}
		
					preCompanyId = cellValue(i, "companyId");
					preFacilityId = cellValue(i, "facilityId");
				} else
					break;
			}
		} else
			i++;
	}
}