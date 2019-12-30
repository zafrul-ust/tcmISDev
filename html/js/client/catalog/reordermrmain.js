var dhxWins;
// Initialize drop downs
function initializeDropDowns() {
	buildDropDown(facilityArr, "facilityId");
	facilityChanged('', true);
}

function facilityChanged(newApplicationId, isSubmitSearch) {
	buildDropDown(accountSysArr[$v("facilityId")], "accountSysId");
	accountSysChanged();
	newApplicationId = typeof newApplicationId === "undefined" && applicationArr[$v("facilityId")].length == 1 ? applicationArr[$v("facilityId")][0].id : newApplicationId;
	buildDropDown(applicationArr[$v("facilityId")], "applicationId", newApplicationId);
	applicationChanged(isSubmitSearch);
}

function applicationChanged(isSubmitSearch) {
	if (isSubmitSearch || isSubmitSearch == null)
		_submitSearchForm();
}

function accountSysChanged() {
	if (isResultFrameLoaded())
		window.frames["resultFrame"].$("accountSysId").value = $v("accountSysId");
}

function buildDropDown(arr, elementName, selectedId) {
	var obj = $(elementName);
	for (var i = obj.length; i > 0; i--)
		obj[i] = null;

	var optionIndex = 0;
	if (!arr || arr.length > 1)
		setOption(optionIndex++, messagesData.pleaseSelect, "", elementName);
	
	if (arr) {
		var selectedIndex = 0;
		for (var i = 0; i < arr.length; i++) {
			if (arr[i].id == selectedId)
				selectedIndex = optionIndex;
			setOption(optionIndex++, arr[i].name, arr[i].id, elementName);
		}
	}
	obj.selectedIndex = selectedIndex;
}

//after resizing main parts, center in-window popup to the new layout
function resizePage() {
	var doRecenter = resizeFramecount == 1 ? false : true;
	resizeFrames();
	if (doRecenter) {
		if (dhxWins && dhxWins.getTopmostWindow(true))
			dhxWins.getTopmostWindow(true).center();
		else if (window.frames["resultFrame"].dhxWins && window.frames["resultFrame"].dhxWins.getTopmostWindow(true))
			window.frames["resultFrame"].dhxWins.getTopmostWindow(true).center();
	}
}

function validateForm() {
	if ("Y" == $v("isFirstLoad")) {
		if (isWhitespace($v("prNumber")))
			return false;
		else
			return true;
	} else 
		return true;
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin() {
	$("transitDialogWin").style.display = "";

	initializeDhxWins();
	var transitWin;
	if (!dhxWins.window("transitDialogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDialogWin", 0, 0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking
		transitWin.attachObject("transitDialogWin");
		transitWin.center();
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	} else {
		transitWin = dhxWins.window("transitDialogWin");
		transitWin.center();
		transitWin.setModal(true);
		transitWin.show();
	}
}

function closeTransitWin() {
	if (dhxWins && dhxWins.window("transitDialogWin")) {
		$("transitDialogWin").style.display = "none";
		dhxWins.window("transitDialogWin").setModal(false);
		dhxWins.window("transitDialogWin").hide();
	}
}

function showLegends() {
	$("showLegendArea").style.display = "";

	initializeDhxWins();
	var legendWin;
	if (!dhxWins.window("legendWin")) {
		// create window first time
		legendWin = dhxWins.createWindow("legendWin", 0, 0, 450, 65);
		legendWin.setText(messagesData.legend);
		legendWin.clearIcon(); // hide icon
		legendWin.denyResize(); // deny resizing
		legendWin.denyPark(); // deny parking
		legendWin.attachObject("showLegendArea");
		legendWin.attachEvent("onClose", function(legendWin) {
			$("showLegendArea").style.display = "none";
			legendWin.hide();
		});
		legendWin.center();
	} else {
		// just show
		legendWin = dhxWins.window("legendWin");
		legendWin.show();
		legendWin.center();
	}
}

function multiplyQuantities() {
	if (isResultFrameLoaded()) {
		if (isPositiveInteger($v("multiplier")))
			window.frames["resultFrame"].multiplyQuantities($v("multiplier"));
		else
			alert(messagesData.xxPositiveInteger.replace("{0}", messagesData.multiplier));
	}
}

function resetQuantityMultiplication() {
	if (isResultFrameLoaded())
		window.frames["resultFrame"].resetQuantityMultiplication();
}

function doNothing() {}

function isResultFrameLoaded() {
	var resultFrame = window.frames["resultFrame"];
	if (resultFrame && resultFrame.$)
		return true;
	else
		return false;
}