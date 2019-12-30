var beangrid;
var selectedRow;
var resizeGridWithWindow = true;
var windowCloseOnEsc = true;
var children = new Array();

function mySearchOnLoad() {
	if ($("fixedCompanyId") != null && $v("fixedCompanyId") == 'Y') {
		$("companyId").value = 'Radian';
		document.genericForm.companyId.disabled = true;
	}

	var searchText = document.getElementById("searchText");
	if (searchText != null && searchText.value.length != 0) {
		var now = new Date();
		document.getElementById("startSearchTime").value = now.getTime();
		var userAction = document.getElementById("action");
		userAction.value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		var a = window.setTimeout("document.genericForm.submit();", 500);
	}
}

function submitSearchForm() {
	// Clear selected user for new search
	document.getElementById("selectedUser").innerHTML = "";

	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	var isValidSearchForm = validateForm();
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();

	if (isValidSearchForm) {
		var userAction = document.getElementById("action");
		userAction.value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		return true;
	} else {
		return false;
	}
}

function validateForm() {
	return true;
}

function resultOnLoad() {
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0) {
		document.getElementById("searchPersonnelViewBean").style["display"] = "";

		initializeGrid();
	} else {
		document.getElementById("searchPersonnelViewBean").style["display"] = "none";
	}

	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on or
	 * off.
	 */
	setResultFrameSize();
}

function initializeGrid() {
	beangrid = new dhtmlXGridObject('searchPersonnelViewBean');

	initGridWithConfig(beangrid, config, false, false);
	if (typeof (jsonMainData) != 'undefined') {
		beangrid.parse(jsonMainData, "json");
	}
	beangrid.attachEvent("onRowSelect", doOnRowSelected);
}

/*
 * Grid event onRowSelect function Change the row class of selected row and
 * process selection.
 */
function doOnRowSelected(rowId, cellInd) {
	parent.selectedRow = rowId;
	// Get the parent selected user
	var selectedUser = parent.document.getElementById("selectedUser");
	selectedUser.innerHTML = '| <a href="#" onclick="selectedUser(); return false;">' + messagesData.selectedUser + ' : ' + cellValue(rowId, 'fullName') + '</a>';
}

function companyChanged() {
	var companyIdO = document.getElementById("companyId");
	var facilityIdO = document.getElementById("facilityId");
	var selectedCompany = companyIdO.value;

	for (var i = facilityIdO.length; i > 0; i--) {
		facilityIdO[i] = null;
	}

	showFacility(selectedCompany);
	facilityIdO.selectedIndex = 0;
}

function showFacility(selectedCompany) {
	var facilityIdArray = new Array();
	facilityIdArray = altFacilityId[selectedCompany];
	var facilityNameArray = new Array();
	facilityNameArray = altFacilityName[selectedCompany];

	for (var i = 0; i < facilityIdArray.length; i++) {
		setOption(i, facilityNameArray[i], facilityIdArray[i], "facilityId")
	}
}

//Return data to caller page through a function called personnelChanged, which exists within the scope of the caller
function selectedUser() {
	var returnSelectedUserId = window.frames['resultFrame'].cellValue(selectedRow, "hiddenUserId");
	var returnSelectedUserName = window.frames['resultFrame'].cellValue(selectedRow, "fullName");
	
	if ( $("fixedCompanyId") != null && $v("fixedCompanyId") =='Y' && window.opener.setBuyer != null ) {
		if(returnSelectedUserId.length > 0) 
			window.opener.setBuyer($v("callbackparam"), returnSelectedUserId, returnSelectedUserName);
	}
	
	displayOnCallerPage(returnSelectedUserId, returnSelectedUserName);

	try {
		if (window.opener.personnelChanged)
			window.opener.personnelChanged(returnSelectedUserId, returnSelectedUserName, $v("callbackparam"), window.frames['resultFrame'].cellValue(selectedRow, "email"));
	} catch (exx) {}
	
	window.close();
}

function displayOnCallerPage(userId, userName) {
	try {
		var openervalueElementId = opener.document.getElementById($v('valueElementId'));
		if (openervalueElementId)
			openervalueElementId.value = userId;
	} catch (ex) {}
	
	try {
		var openerdisplayElementId = opener.document.getElementById($v('displayElementId'));
		if (openerdisplayElementId) {
			if (window.opener.dontChangElemCss) {
				
			} else {
				openerdisplayElementId.className = "inputBox";
			}
			openerdisplayElementId.value = userName;
		}
	} catch (ex) {}
	
	try {
		var openerdisplayArea = opener.document.getElementById($('displayArea').value);
		if (openerdisplayArea)
			openerdisplayArea.value = userName;
	} catch (ex) {}
}

function showLegend() {
	var showLegendArea = document.getElementById("showLegendArea");
	showLegendArea.style.display = "";

	var dhxWins = new dhtmlXWindows()
	dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	if (!dhxWins.window(messagesData.showlegend)) {
		// create window first time
		var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 180);
		legendWin.setText(messagesData.showlegend);
		legendWin.clearIcon(); // hide icon
		legendWin.denyResize(); // deny resizing
		legendWin.denyPark(); // deny parking
		legendWin.attachObject("showLegendArea");
		legendWin.attachEvent("onClose", function(legendWin) {
			legendWin.hide();
		});
		legendWin.center();
	} else {
		// just show
		dhxWins.window("legendwin").show();
	}
}

function createNewUser() {
	var companyId = $v("companyId");
	if (companyId == 'My Companies') {
		alert(messagesData.selectA.replace("{0}", messagesData.company));
		return;
	}
	
	var loc = "createnewuser.do?companyId=" + companyId;
	children[children.length] = openWinGeneric(loc,"createnewuser","410","185","yes","80","80","no");
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