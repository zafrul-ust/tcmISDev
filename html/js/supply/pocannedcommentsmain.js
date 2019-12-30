function returnData() {	
  if ( window.opener.addSelCannedComments) {
	  	var c = new cannedComments();	  	
	  	window.opener.addSelCannedComments(c);
	  	window.close();
	  	return;
  }
}

var commentId = '';
var comment = '';
var commentName = '';

function cannedComments() {	
    this.commentId = commentId;
	this.comment = comment;
	this.commentName = commentName; 
}

//overwrite default loadLayoutWin to get single layout
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

		dhxLayout = new dhtmlXLayoutObject(win, "1C");
		dhxLayout.setImagePath("/dhtmlxLayout/codebase/imgs/");
		dhxLayout.cells("a").attachObject("resultFrameDiv");
		dhxLayout.cells("a").hideHeader();

		if (_isIE) {
			dhxLayout.cells("a").setHeight(myHeight - 12 - searchSectionHeight - 3 - 3);
		}
		else {
			/* Need to subtract more for firefox, that is the 10 px extra */
			dhxLayout.cells("a").setHeight(myHeight - 12 - searchSectionHeight - 2 - 3 - 17);
		}
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