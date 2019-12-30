// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;

function submitSearchForm() {
	/*Make sure to not set the target of the form to anything other than resultFrame*/
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

function validateSearchForm() {
	if ($v("facilityId").length == 0) {
		alert(messagesData.facilityRequired);
		return false;
	}
	if($v("searchArgument").length == 0 && $v("shipToDate").length == 0 && $v("shipFromDate").length == 0){
		alert(messagesData.idRequired);
		return false;
	}
	if($v("searchArgument").length > 0 && $v("searchField") == 'Shipment ID'  && !isInteger($v("searchArgument"),true)) {
		alert(messagesData.idInteger);
		return false;
	}
	return true;
}

function createXSL() {
	if (validateSearchForm()) {
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Catalog_Synonym_Excel','650','600','yes');
		document.genericForm.target = '_Catalog_Synonym_Excel';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
}

function buildDropDown(arr, eleName, selectedIndex) {
	var obj = $(eleName);
	for (var i = obj.length; i > 0; i--)
		obj[i] = null;

	var start = 0;
	for (var i = 0; i < arr.length; i++)
		setOption(start++, arr[i].name, arr[i].id, eleName);

	if (selectedIndex)
		obj.selectedIndex = selectedIndex;
	else
		obj.selectedIndex = 0;
}

//Initialize drop downs
function initializeDropDown(defaultFacilityId) {
	if (defaultFacilityId) {
		for (var i = 0; i < altFacility.length; i++)
			if (defaultFacilityId == altFacility[i].id) {
				buildDropDown(altFacility, "facilityId", i);
				break;
			}
		setWorkArea(defaultFacilityId);
	} else {
		buildDropDown(altFacility, "facilityId");
		setWorkArea(altFacility[0].id);
	}
}

function setWorkArea(facilityId) {
	buildDropDown(workAreaColl[facilityId], "application");
}

function facilityChanged() {
	setWorkArea($('facilityId').options[$('facilityId').selectedIndex].value);
}