var searchSectionHeight = 0;
var resizeFramecount = 1; /*
				 * Need this to stop triggering an infinite loop of resize events in IE.I resize the frames only when this is 0 and applicationResizeFramecount is
				 * 0.
				 */
var showsearchresults = false; /* If this is true we show the result section of the page */
var resizeGridWithWindow = true; /* If this is set to false, I don't change the width of the result table upon resize. */
var layouInitialized = false; /* This gets set to true after the layout is initialized. This is to load the layout in tabs. */
var pageName = "";
var pageId = "";
var useLayout = true; /* This will either draw the layout or not */
var showSearchHeight = false;
var myResultFrameId = "resultFrame";

var myWidth = 0;
var myHeight = 0;

function resizeFrames() {

	if (!layouInitialized && useLayout) {
		loadLayoutWin(pageName, pageId);
	}

	if (parent.applicationResizeFramecount * 1 >= 0) {
		appResizeFramecount = parent.applicationResizeFramecount * 1;
	}
	else {
		appResizeFramecount = 0;
	}

	if (resizeFramecount == 0 && appResizeFramecount == 0) {
		// alert("resizeFrames");
		try {
			searchSectionHeight = 0;
			resultFrameHeight = 0;
			// searchSectionHeight = document.getElementById("searchFrameDiv").offsetHeight;
		}
		catch (ex) {
			searchSectionHeight = 0;
		}

		resizeFrame();
		setTimeout('resetResizeFramecount()', 5);
		resizeFramecount++;
		try {
			if (resizeGridWithWindow) {
				setTimeout('window.frames[myResultFrameId].reSizeGridCoLWidths();', 5);
			}
		}
		catch (exGrid) {
			// alert("Here 209");
		}
	}
}

function resetResizeFramecount() {
	resizeFramecount = 0;
}

function resizeFrame(extraReduction) {

	if (extraReduction == null) {
		extraReduction = 0;
	}

	var matchResultSectionDivs = false;
	try {
		setWindowSizes();
		if (isPageVisible(pageId)) {
			searchSectionHeight = document.getElementById("searchFrameDiv").offsetHeight;
		}
		else {
			try {
				searchSectionHeight = document.getElementById("searchHeight").value;
			}
			catch (ex1) {
				searchSectionHeight = 100;
			}
		}

		try {
			if (layouInitialized) {
				// var cellaHeight = dhxLayout.cells("a").getHeight();
				if (dhxLayout.cells("a").isCollapsed()) {
					searchSectionHeight = 10;
				}
				else {
					searchSectionHeight = searchSectionHeight * 1 + 10;
				}
				/* This is for when a user drags the seperator upwards. */
				var cellaHeight = dhxLayout.cells("a").getHeight();
				if (cellaHeight < searchSectionHeight)
					searchSectionHeight = cellaHeight;
			}
			else if (_isIE6) {
				try {
					searchSectionHeight = document.getElementById("searchHeight").value;
				}
				catch (ex1) {
					searchSectionHeight = 100;
				}
			}

			/* Just for IE */
			if (!isPageVisible(pageId) && _isIE) {
				searchSectionHeight = searchSectionHeight * 1 + 20;
			}
		}
		catch (ex) {
		}

		/* If the search section is wider than the window trying to give a scroll bar */
		/*
		 * var searchTableWidth=0; searchTableWidth = window.document.getElementById("searchTable").offsetWidth;
		 * 
		 * try { var cellaWidth = dhxLayout.cells("a").getWidth(); //alert("myWidth "+myWidth+" cellaWidth " +cellaWidth+" searchTableWidth "+searchTableWidth+""); if
		 * (searchTableWidth >cellaWidth) { window.document.getElementById("searchFrameDiv").style.overflowX=="scroll";
		 * window.document.getElementById("searchFrameDiv").style.overflow=="auto"; } } catch (ex) { alert("here 112"); }
		 */

		var topNavigationDivHeight = 0;
		try {
			var topNavigationDiv = document.getElementById("topNavigation");
			topNavigationDivHeight = topNavigationDiv.offsetHeight;
		}
		catch (ex) {
			topNavigationDivHeight = 0;
		}

		var footerDivHeight = 8;
		try {
			var footerDiv = document.getElementById("footer");
			footerDivHeight = footerDiv.offsetHeight + 8;
			if (footerDivHeight == 0) {
				footerDivHeight = 8;
			}
		}
		catch (ex) {
			footerDivHeight = 8;
		}

		var mainUpdateLinksHeight = 0;
		try {
			var mainUpdateLinksDiv = document.getElementById("mainUpdateLinks");
			mainUpdateLinksHeight = mainUpdateLinksDiv.offsetHeight;
			if (mainUpdateLinksHeight == 0) {
				mainUpdateLinksHeight = 8;
			}
			else {
				if (_isIE) {
					mainUpdateLinksHeight = mainUpdateLinksHeight + 5;
				}
				else {
					mainUpdateLinksHeight = mainUpdateLinksHeight + 8;
				}
			}
		}
		catch (ex) {
			mainUpdateLinksHeight = 8;
		}

		// bodyOffsetHeight = window.document.body.offsetHeight;
		frameName = document.getElementById("" + myResultFrameId + "");
		matchResultSectionDivs = true;
		var totalLines = 0;
		try {
			totalLines = window.frames[myResultFrameId].document.getElementById("totalLines").value;
		}
		catch (ex) {
			totalLines = 0;
		}

		// alert("myHeight: "+myHeight+" topNavigationDivHeight: "+topNavigationDivHeight+" searchSectionHeight: "+searchSectionHeight+" mainUpdateLinksHeight
		// "+mainUpdateLinksHeight+" footerDivHeight: "+footerDivHeight+"");
		if (_isIE) {
			resultFrameHeight = myHeight - 12 - topNavigationDivHeight - searchSectionHeight - 3 - 3 - mainUpdateLinksHeight - footerDivHeight - 35 - extraReduction;
		}
		else {
			resultFrameHeight = myHeight - 12 - topNavigationDivHeight - searchSectionHeight - 2 - 3 - mainUpdateLinksHeight - footerDivHeight - 10 - 40 - extraReduction;
		}

		if (totalLines == 0) {
			resultFrameHeight = 25;
		}

		/*
		 * If the result table is less than the minimum height we keep the result frame height at minimum height. When there are no records no need to show minimum height
		 */
		var minHeight = window.frames[myResultFrameId].document.getElementById("minHeight");
		var minHeightValue = 0;
		try {
			// alert("minHeight "+minHeight.value.trim()*1);
			minHeightValue = minHeight.value.trim() * 1;
		}
		catch (ex) {
			// alert("Here error 86");
			minHeightValue = 0;
		}

		if (totalLines != 0) {
			if (resultFrameHeight < minHeightValue) {
				resultFrameHeight = minHeightValue;
			}
		}

		if (resultFrameHeight > 150 || matchResultSectionDivs) {
			frameName.height = resultFrameHeight;

			if (totalLines != 0) {
				try {
					setTimeout('window.frames[myResultFrameId].setGridHeight(' + resultFrameHeight + ');', 2);
				}
				catch (exGrid) {
					// alert("Here 209");
				}

				try {
					setTimeout('window.frames[myResultFrameId].setGridSize();', 2);
				}
				catch (exGrid) {
					// alert("Here 209");
				}
			}
		}

		if (myWidth > 300 && resizeGridWithWindow && totalLines != 0) {
			/* Need to do this for IE, when the width of result table is > width of window to get the scrollbars correct */
			if (_isIE)
				frameName.width = myWidth - 30;

			try {
				setTimeout('window.frames[myResultFrameId].setGridWidth();', 2);
			}
			catch (exGrid) {
				// alert("Here 252");
			}
		}
	}
	catch (ex) {
		alert("here error 68 resizeFrame()");
	}
}

function showPleaseWait() {
	resizeFramecount = 1;
	window.document.getElementById("resultGridDiv").style["display"] = "none";
	window.document.getElementById("transitPage").style["display"] = "";
}

function stopPleaseWait() {
	window.document.getElementById("transitPage").style["display"] = "none";
}

function loadLayoutWin(tmpPageName, tmpPageId) {
	stopPleaseWait();
	if (tmpPageName != null && tmpPageName.length > 0)
		pageName = tmpPageName;
	if (tmpPageId != null && tmpPageId.length > 0)
		pageId = tmpPageId;

	/* Testing to see if active X is enabeled if it is not turing the layout off. */
	if (_isIE) {
		try {
			var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		}
		catch (noActiveX) {
			// alert("Here no Active X");
			useLayout = false;
		}
	}

	/*
	 * if (_isIE6) { useLayout = false; }
	 */

	// Do not show the layout if it is not in the Application. This is to force people to use the application to get better features.
	// Nawaz - 08-04-09 - might be causing some problems in IE6. If active X is supported let them have it.
	/*
	 * try { if (!intcmIsApplication) { useLayout = false; } } catch (notInApplication) { }
	 */

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
	}
	catch (ex) {
		// alert("Exception....265");
		pageVisible = true;
	}

	if (pageVisible && showSearchHeight) {
		alert("Search Section height = " + window.document.getElementById("searchFrameDiv").offsetHeight);
	}

	var searchSectionHeight = 0;
	setWindowSizes();
	if (isPageVisible(pageId)) {
		searchSectionHeight = document.getElementById("searchFrameDiv").offsetHeight;
	}
	else {
		try {
			searchSectionHeight = document.getElementById("searchHeight").value;
		}
		catch (ex1) {
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

		dhxLayout = new dhtmlXLayoutObject(win, "2E");
		dhxLayout.setImagePath("/dhtmlxLayout/codebase/imgs/");
		dhxLayout.cells("a").attachObject("searchFrameDiv");
		dhxLayout.cells("b").attachObject("resultFrameDiv");
		dhxLayout.cells("b").hideHeader();

		if (_isIE) {
			dhxLayout.cells("a").setHeight(searchSectionHeight + 3);
			dhxLayout.cells("b").setHeight(myHeight - 12 - searchSectionHeight - 3 - 3);
		}
		else {
			dhxLayout.cells("a").setHeight(searchSectionHeight + 5);
			/* Need to subtract more for firefox, that is the 10 px extra */
			dhxLayout.cells("b").setHeight(myHeight - 12 - searchSectionHeight - 2 - 3 - 17);
		}
		dhxLayout.cells("a").setText("");

		// attaching onExpand event handler
		dhxLayout.attachEvent("onExpand", function(ietmId) {
			if (_isIE) {
				setTimeout('resizeFrame("0")', 50);
			}
			else {
				setTimeout('resizeFrame("0")', 50);
			}
		});
		// attaching onCollapse event handler
		dhxLayout.attachEvent("onCollapse", function(ietmId) {
			if (_isIE) {
				setTimeout('resizeFrame("0")', 50);
			}
			else {
				setTimeout('resizeFrame("0")', 50);
			}
		});

		// attaching onPanelResizeFinish event handler
		dhxLayout.attachEvent("onPanelResizeFinish", function() {
			if (_isIE) {
				setTimeout('resizeFrame("5")', 50);
			}
			else {
				setTimeout('resizeFrame("-2")', 50);
			}
		});

		layouInitialized = true;
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