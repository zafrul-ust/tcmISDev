var resizeFramecount = 1; /*
				 * Need this to stop triggering an infinite loop of resize events in IE.I resize the frames only when this is 0 and applicationResizeFramecount is
				 * 0.
				 */
var layoutInitialized = false; /* This gets set to true after the layout is initialized. This is to load the layout in tabs. */
var pageName = "";
var pageId = "";
var useLayout = true; /* This will either draw the layout or not */

var myWidth = 0;
var myHeight = 0;

function doOnLoad() {
	loadSingleLayoutWin('', 'shippingSample' + $v("receiptId"));
	stopPleaseWait();
	if (showMessage) {
		showMessages();
	}
	
	initializeDropDown();
	try {
		var obj = document.getElementsByClassName("dhtmlxPolyInfoBar")[0];
		obj.style["display"] = "none";
	} catch (e) {
		
	}
}

function loadSingleLayoutWin(tmpPageName, tmpPageId) {
	stopPleaseWait();
	if (tmpPageName != null && tmpPageName.length > 0)
		pageName = tmpPageName;
	if (tmpPageId != null && tmpPageId.length > 0)
		pageId = tmpPageId;

	/* Testing to see if active X is enabled. If it is not, turn the layout off. */
	if (_isIE) {
		try {
			var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		} catch (noActiveX) {
			useLayout = false;
		}
	}

	/*
	 * This is to check if the page is open and the Tab is active in the application. Loading a window and setting it to fullscreen needs some hight taht can be obtained only
	 * when the window is visible.
	 * 
	 * It is also not a bad idea to know which pageId you are working on.
	 */
	var pageVisible = false;
	try {
		for ( var i = 0; i < parent.tabDataJson.length; i++) {
			if (parent.tabDataJson[i].tabId == pageId && parent.tabDataJson[i].status == "open") {
				var target = parent.window.document.getElementById("" + pageId + "div");
				if (target.style["display"] != 'none') {
					pageVisible = true;
				}
			}
		}
	} catch (ex) {
		pageVisible = true;
	}

	var searchSectionHeight = 0;
	setWindowSizes();
	if (isPageVisible(pageId)) {
		searchSectionHeight = document.getElementById("dataFrameDiv").offsetHeight;
	} else {
		try {
			searchSectionHeight = document.getElementById("searchHeight").value;
		} catch (ex1) {
			searchSectionHeight = 100;
		}
	}

	if (pageVisible && useLayout) {
		var dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
		var win = dhxWins.createWindow(pageId, 0, 0, 800, 600);
		win.setText(pageId);
		win.clearIcon(); // hide icon
		win.denyResize(); // deny resizing
		win.denyMove(); // deny moving
		win.denyPark(); // deny parking
		win.button("close").disable(); // disable "close" button
		win.setToFullScreen(true);
		win.hideHeader();

		dhxLayout = new dhtmlXLayoutObject(win, "1C");
		dhxLayout.setImagePath("/dhtmlxLayout/codebase/imgs/");
		dhxLayout.cells("a").attachObject("dataFrameDiv");

		if (_isIE) {
			dhxLayout.cells("a").setHeight(searchSectionHeight + 3);
		} else {
			dhxLayout.cells("a").setHeight(searchSectionHeight + 5);
		}
		dhxLayout.cells("a").setText("");

		// attaching onExpand event handler
		dhxLayout.attachEvent("onExpand", function(ietmId) {
			if (_isIE) {
				setTimeout('resizeFrame("0")', 50);
			} else {
				setTimeout('resizeFrame("0")', 50);
			}
		});
		// attaching onCollapse event handler
		dhxLayout.attachEvent("onCollapse", function(ietmId) {
			if (_isIE) {
				setTimeout('resizeFrame("0")', 50);
			} else {
				setTimeout('resizeFrame("0")', 50);
			}
		});
		// attaching onPanelResizeFinish event handler
		dhxLayout.attachEvent("onPanelResizeFinish", function() {
			if (_isIE) {
				setTimeout('resizeFrame("5")', 50);
			} else {
				setTimeout('resizeFrame("-2")', 50);
			}
		});

		layoutInitialized = true;
	}
	
	try {
		// These functions are not in same file, put in try loop to protect it.
		// since the main window mostly is for search might as well attach these fallback functions.
		if (window['submitSearchForm'] == null)
			window['submitSearchForm'] = _submitSearchForm;
		if (window['createXSL'] == null)
			window['createXSL'] = _createXSL;
		if (window['validateForm'] == null)
			window['validateForm'] = _validateForm;
	}
	catch (ex) {
	}
}

function resizeFrames() {
	if (!layoutInitialized && useLayout) {
		loadSingleLayoutWin(pageName, pageId);
	}

	if (parent.applicationResizeFramecount * 1 >= 0) {
		appResizeFramecount = parent.applicationResizeFramecount * 1;
	} else {
		appResizeFramecount = 0;
	}

	if (resizeFramecount == 0 && appResizeFramecount == 0) {
		resizeFrame();
		setTimeout('resetResizeFramecount()', 5);
		resizeFramecount++;
	}
}

function resetResizeFramecount() {
	resizeFramecount = 0;
}

function resizeFrame(extraReduction) {
	try {
		setWindowSizes();
	} catch (ex) {
		alert("here error 68 resizeFrame()");
	}
}

function showPleaseWait() {
	resizeFramecount = 1;
	window.document.getElementById("transitPage").style["display"] = "";
	window.document.getElementById("dataFrameDiv").style["display"] = "none";
}

function stopPleaseWait() {
	window.document.getElementById("transitPage").style["display"] = "none";
	window.document.getElementById("dataFrameDiv").style["display"] = "";
}

function showMessages() {
	document.getElementById("messagesArea").style.display = "";

	var dhxWins = new dhtmlXWindows();
	dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	if (!dhxWins.window("messageWin")) {
		// create window first time
		var messageWin = dhxWins.createWindow("messageWin", 0, 0, 450, 150);
		messageWin.setText(messagesData.messageWinHeader);
		messageWin.clearIcon(); // hide icon
		messageWin.denyResize(); // deny resizing
		messageWin.denyPark(); // deny parking
		messageWin.attachObject("messagesArea");
		messageWin.attachEvent("onClose", function(messageWin) {
			messageWin.hide();
		});
		messageWin.center();
	} else {
		// just show
		dhxWins.window("messageWin").show();
	}
}

//Initialize drop downs
function initializeDropDown() {
	buildDropDown(altCompany, "companyIdSelect", $v("companyId"));
	companyChanged();
}

function buildDropDown(arr, eleName, selectedValue) {
	var obj = $(eleName);
	for (var i = obj.length; i > 0; i--)
		obj[i] = null;
	// if arr has only 1 value or less excluding default values, don't show
	// defaults
	var selectedIndx = 0;
	if (arr != null && arr.length > 0) {
		var start = 0;
		for (var i = 0; i < arr.length; i++) {
			if (arr[i].id === selectedValue)
				selectedIndx = start;
			setOption(start++, arr[i].name, arr[i].id, eleName);
		}
	}
	obj.selectedIndex = selectedIndx;
}

function companyChanged() {
	$("companyId").value = $("companyIdSelect").options[$("companyIdSelect").selectedIndex].value;
	setFacility(altCompany[$('companyIdSelect').selectedIndex].id);
}

function setFacility(companyId) {
	buildDropDown(facilityColl[companyId], "facilityIdSelect");
	facilityChanged();
}

function facilityChanged() {
	$("facilityId").value = $("facilityIdSelect").options[$("facilityIdSelect").selectedIndex].value;
}

function getShipTo() {
	if (isWhitespace($v("companyId")))
		alert(messagesData.xNotSelected.replace("{0}", messagesData.company));
	else
		openWinGeneric("/tcmIS/supply/poshiptomain.do?companyId=" + $v("companyId"), "ShippingSampleShipTo", "600", "500", "yes", "50", "50");
}

function shipToChanged(s) {
	var shipToAddressDisplay = "";
  	if( s.addressLine1.length > 0)
  		shipToAddressDisplay += s.addressLine1+"<br/>";
  	if( s.addressLine2.length > 0)
  		shipToAddressDisplay += s.addressLine2+"<br/>";
  	if( s.addressLine3.length > 0)
  		shipToAddressDisplay += s.addressLine3+"<br/>";
  	shipToAddressDisplay += s.city + "," + s.stateAbbrev + " " + s.zip + " " + s.countryAbbrev;
  	$("shipToLocationAddressSpan").innerHTML = shipToAddressDisplay;
  	$("shipToLocationAddressDisplay").value = shipToAddressDisplay;
  	$("shipToLocationIdDisplay").value = s.locationId;
  	$("shipToLocationId").value = s.locationId;
}

function printSampleDeliveryLabel() {
	if (validateDataForm()) {
		$("labelType").value = "samplebox";
		$("uAction").value = "storeAndPrintSampleData";
		selectPrintQty();
	}
}

function selectPrintQty() {
	openWinGeneric("selectprintqty.do","selectPrintQty","300","200","yes","100","100");
}

function printQtyChanged(printQty) {
	$("labelQuantity").value = printQty;
	
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_GenerateLabels','650','600','yes');
	document.genericForm.target = "_GenerateLabels";
	document.genericForm.submit();
	
	setTimeout('window.status="";',5000);
}

function validateDataForm() {
	if (!isPositiveInteger($v("quantity"), false)) {
		alert(messagesData.positiveIntegerError.replace("{0}", messagesData.quantity));
		return false;
	} else if (isWhitespace($v("shipToLocationId"))) {
		alert(messagesData.xNotSelected.replace("{0}", messagesData.shipTo));
		return false;
	} else if (isWhitespace($v("companyId"))) {
		alert(messagesData.xNotSelected.replace("{0}", messagesData.company));
		return false;
	} else if (isWhitespace($v("facilityId"))) {
		alert(messagesData.xNotSelected.replace("{0}", messagesData.facility));
		return false;
	} else
		return true;
}