var tabDataJson = new Array();
var selectedTabId = "";
var purchaseOrderCount = 0; /*
				 * This is to allow buyers to open more than one
				 * PO at a time
				 */
/*
 * This funtion build the tab and opens the Iframe that has the page associated
 * with the tab
 */
function openIFrame(tabId, tabURL, tabName, tabIcon, noScrolling, truncateTabName) {
	if (tabName.trim().length == 0) {
		try {
			tabName = pageData["" + tabId + ""];
		}
		catch (ex) {
			alert("No page Name defined");
			return;
		}
	}
	var tabNameDisplay = tabName;
	if (truncateTabName) {
		tabNameDisplay = tabName.split(" ")[0];
	}
	var foundExistingDiv = false;
	if (tabId != 'purchaseOrder') {
		for ( var i = 0; i < tabDataJson.length; i++) {
			if (tabDataJson[i].tabId == tabId) {
				foundExistingDiv = true;
				/*
				 * When a menu option is clicked I always reload
				 * the page even if it is already open in a tab.
				 */
				// if (tabDataJson[i].status == "closed")
				{
					tabDataJson[i].status = "open"
					var pageIframe = document.getElementById("" + tabId + "frame");
					// pageIframe.src = tabDataJson[i].url;
					/*
					 * See nothing wrong in using the URL
					 * that is passed to the procedure -
					 * Nawaz 11-13-07
					 */
					pageIframe.src = tabURL;
				}
				togglePage(tabId);
			}
		}
	}
	else {
		if (purchaseOrderCount > 0) {
			tabId = tabId + purchaseOrderCount;
		}
		purchaseOrderCount++;
	}

	if (!foundExistingDiv) {
		tabIndex = tabDataJson.length;
		/*
		 * Store the pages that are open in an array so that I don't
		 * open more than one frame for the same page
		 */
		tabDataJson[tabIndex] = {
		        tabId : "" + tabId + "",
		        status : "open",
		        url : "" + tabURL + ""
		};

		if (tabIcon.length == 0) {
			tabIcon = "/images/spacer14.gif"; // this is to
								// maintain
								// equal heights
								// for all tabs.
								// the heigt of
								// the icon
								// image has to
								// be 14 piexels
		}

		var list = document.getElementById("mainTabList");
		var newNode = document.createElement("li");
		newNode.id = tabId + "li";
		var newNodeInnerHTML = "<a href=\"#\" id=\"" + tabId + "Link\" class=\"selectedTab\" onmouseup=\"togglePage('" + tabId + "'); return false;\">";
		newNodeInnerHTML += "<span id=\"" + tabId + "LinkSpan\" class=\"selectedSpanTab\"><img src=\"" + tabIcon + "\" class=\"iconImage\"><span id=\"" + tabId + "frametabName\" title=\"" + tabName + "\">" + tabNameDisplay + "</span>&nbsp;&nbsp;";
		newNodeInnerHTML += "<img src=\"/images/closex.gif\" alt=\"Close Tab\" title=\"Close Tab\" onclick=\"closeTabx('" + tabId + "')\">";
		// newNodeInnerHTML +="<button type=\"button\" class=\"\"
		// onmouseover=\"this.className='closexOver'\"
		// onmouseout=\"this.className=''\"
		// onmouseup=\"closeTabx('"+tabId+"')\"><img
		// src=\"/images/closex031.gif\"></button>";
		newNodeInnerHTML += "<br class=\"brNoHeight\"><img src=\"/images/minwidth.gif\" width=\"" + (tabName.length * 2) + "\" height=\"0\"></span></a>";
		newNode.innerHTML = newNodeInnerHTML;
		list.appendChild(newNode);

		var maindivs = document.getElementById("maindivs");
		newDiv = document.createElement("div");
		newDiv.id = tabId + "div";

		var scrolling = "auto";
		if (noScrolling == "Y") {
			scrolling = "no";
		}

		var innHtmlline = "<iframe src=\"" + tabURL + "\" name=" + tabId + "frame id=" + tabId + "frame width=\"100%\" height=\"600\" scrolling=\"" + scrolling + "\" marginwidth=\"0\" frameborder=\"0\" style=\"\">";
		innHtmlline += "  [Your user agent does not support frames or is currently configured";
		innHtmlline += "  not to display frames. However, you may visit";
		innHtmlline += "  <A href=\"\balnk.html\">the related document.</A>]";
		innHtmlline += "</iframe>";
		newDiv.innerHTML = innHtmlline;
		maindivs.appendChild(newDiv);
		togglePage(tabId);
		
		try
		{ 
			/*
			 * This is to log page loading into Google Analytics if the user is logged in.			 
			 */
			//alert(hostEnv);
			if (hostEnv == 'Prod')
			{
				gaLoadTab(tabId);
			}
		}
		catch (ex) {
		}		
	}
}

function setTabName(tabId, newTabName) {
	var tabName = document.getElementById("" + tabId + "frametabName");
	tabName.innerHTML = newTabName;
}

function getTabName(tabId) {
	var tabName = document.getElementById("" + tabId + "frametabName");
	return tabName.innerHTML;
}

function setTabFrameSize(tabId) {
	setApplicationWindowSizes();
	var appTabs = document.getElementById("appTabs");
	tabsDivHeight = appTabs.offsetHeight;

	frameHeight = myApplicationHeight - 64 - tabsDivHeight - 8;
	// 64 is sum off header section and menu section
	// 8 is to count for the missing scroll bar at the bottom of the page
	frameName = document.getElementById("" + tabId + "frame");
	frameName.height = frameHeight;
}

function togglePage(tabId) {
	// alert("Here togglePage "+tabId+" "+selectedTabId+"");
	/*
	 * If the page being toggled is already closed, ignore the toggle. This
	 * can happen when they click the x on the taab.
	 */
	for ( var i = 0; i < tabDataJson.length; i++) {
		if (tabDataJson[i].tabId == tabId) {
			if (tabDataJson[i].status == "closed") {
				return;
			}
		}
	}

	// toggle page only if the page passed is not the selected tab
	if (selectedTabId != tabId) {
		for ( var i = 0; i < tabDataJson.length; i++) {
			var tabLink = document.getElementById(tabDataJson[i].tabId + "Link");
			var tabLinkSpan = document.getElementById(tabDataJson[i].tabId + "LinkSpan");
			if (tabDataJson[i].tabId == tabId && tabDataJson[i].status == "open") {
				setVisible(tabDataJson[i].tabId, true);
				tabLink.className = "selectedTab";
				tabLink.style["display"] = "";
				/*
				 * tabLink.onmouseover = ""; tabLink.onmouseout =
				 * "";
				 */

				tabLinkSpan.className = "selectedSpanTab";
				tabLinkSpan.style["display"] = "";
				/*
				 * tabLinkSpan.onmouseover = "";
				 * tabLinkSpan.onmouseout = "";
				 */
				selectedTabId = tabId;
			}
			else {
				setVisible(tabDataJson[i].tabId, false);
				tabLink.className = "tabLeftSide";
				/*
				 * tabLink.onmouseover =
				 * "className='selectedTab'"; tabLink.onmouseout =
				 * "className='tabLeftSide'";
				 */

				tabLinkSpan.className = "tabRightSide";
				/*
				 * tabLinkSpan.onmouseover =
				 * "className='selectedSpanTab'";
				 * tabLinkSpan.onmouseout =
				 * "className='tabRightSide'";
				 */
			}
		}
	}
	else {
		var tabLink = document.getElementById(selectedTabId + "Link");
		tabLink.style["display"] = "";
		var tabLinkSpan = document.getElementById(selectedTabId + "LinkSpan");
		tabLinkSpan.style["display"] = "";

		setVisible(tabId, true);
		verifySize();
	}

	toggleContextMenu('appcontextMenu');
	/*
	 * Doing this so that when the page first comes up and the first thing
	 * the user does is a right click out side of the tab area, the right
	 * click is correct (normal).
	 */
	setTimeout('toggleContextMenuToNormal()', 50);
	setTabFrameSize(tabId);
}

function setVisible(tabId, yesno) {
	try {
		var target = document.getElementById("" + tabId + "div");
		if (yesno) {
			// alert("Here setVisible true "+tabId+"");
			target.style["display"] = "";
		}
		else {
			// alert("Here setVisible false "+tabId+"");
			target.style["display"] = "none";
		}
	}
	catch (ex) {
		alert("Here exception in setVisible");
	}
}

/* this function is called when the x on the tab is clicked. */
function closeTabx(closeTabId) {
	// alert("Here closeTabx "+closeTabId+" "+selectedTabId+"");
	var selectedTabIndex = 0;
	var tabLink = document.getElementById(closeTabId + "Link");
	tabLink.style["display"] = "none";
	var tabLinkSpan = document.getElementById(closeTabId + "LinkSpan");
	tabLinkSpan.style["display"] = "none";

	var target = document.getElementById("" + closeTabId + "div");
	target.style["display"] = "none";

	var pageIframe = document.getElementById("" + closeTabId + "frame");
	pageIframe.src = "/blank.html";

	for ( var i = 0; i < tabDataJson.length; i++) {
		if (tabDataJson[i].tabId == closeTabId) {
			// selectedTabId = tempSelectedTab;
			tabDataJson[i].status = "closed";
			selectedTabIndex = i;
		}
	}

	var openTabCount = 0;
	/* If there are no more tabs open, no need to find the next tab. */
	for ( var i = 0; i < tabDataJson.length; i++) {
		if (tabDataJson[i].status != "closed") {
			openTabCount++;
		}
	}

	if (selectedTabId == closeTabId && openTabCount > 0) {
		findNextTab(selectedTabIndex);
	}
}

function closeTab() {
	// if (selectedTabId.length > 0 && selectedTabId !='home')
	var selectedTabIndex = 0;
	if (selectedTabId.length > 0) {
		var tabLink = document.getElementById(selectedTabId + "Link");
		tabLink.style["display"] = "none";
		var tabLinkSpan = document.getElementById(selectedTabId + "LinkSpan");
		tabLinkSpan.style["display"] = "none";

		var target = document.getElementById("" + selectedTabId + "div");
		target.style["display"] = "none";

		var pageIframe = document.getElementById("" + selectedTabId + "frame");
		pageIframe.src = "/blank.html";

		for ( var i = 0; i < tabDataJson.length; i++) {
			if (tabDataJson[i].tabId == selectedTabId) {
				// selectedTabId = tempSelectedTab;
				tabDataJson[i].status = "closed";
				selectedTabIndex = i;
			}
		}
		findNextTab(selectedTabIndex);
	}
}

function findNextTab(closedTabIndex) {
	// alert("Here findNextTab "+closedTabIndex+"");
	var foundNextTab = false;
	// alert(closedTabIndex);
	for ( var i = closedTabIndex - 1; i >= 0; i--) {
		// alert(""+tabDataJson[i].tabId+" "+tabDataJson[i].status+"
		// "+foundNextTab+"");
		if (tabDataJson[i].status == "open" && !foundNextTab) {
			togglePage(tabDataJson[i].tabId);
			foundNextTab = true;
			break;
		}
	}

	// alert("Between Tabs"+foundNextTab+"");
	if (!foundNextTab) {
		for ( var i = tabDataJson.length - 1; i > closedTabIndex; i--) {
			// alert(""+tabDataJson[i].tabId+"
			// "+tabDataJson[i].status+" "+foundNextTab+"");
			if (tabDataJson[i].status == "open" && !foundNextTab) {
				togglePage(tabDataJson[i].tabId);
				foundNextTab = true;
				break;
			}
		}
	}
}

function openStartingPages(timeout, timeoutWait) {
	try { /*
		 * This is to start the timeout clock in the application. there
		 * is no timeout on the home/launch page
		 */
		loadPage(timeout, timeoutWait);
	}
	catch (ex) {

	}

	startOnload();
	verifySize();
}

function addPageToStartup() {
	if (selectedTabId.length > 0) {
		loc = "userpage.do?action=addPageToStartup";
		if (selectedTabId.indexOf('purchaseOrder') == 0) {
			loc += "&pageId=purchaseOrder";
		}
		else {
			loc += "&pageId=" + selectedTabId + "";
		}

		if (selectedTabId == "home") {
			loc += "&startPageOrder=0";
		}
		callToServer(loc);
		fixProgressBar();
	}
}

function removePageFromStartup() {
	if (selectedTabId.length > 0) {
		loc = "userpage.do?action=removePageFromStartup";
		if (selectedTabId.indexOf('purchaseOrder') == 0) {
			loc += "&pageId=purchaseOrder";
		}
		else {
			loc += "&pageId=" + selectedTabId + "";
		}
		callToServer(loc);
		fixProgressBar();
	}
}

function closeTabstotheRight() {
	for ( var i = tabDataJson.length - 1; i > 0; i--) {
		if (tabDataJson[i].tabId == selectedTabId) {
			break;
		}
		else {
			closeTabx(tabDataJson[i].tabId);
		}
	}
}

function closeOtherTabs() {
	for ( var i = tabDataJson.length - 1; i >= 0; i--) {
		if (tabDataJson[i].tabId == selectedTabId) {
			continue;
		}
		else {
			closeTabx(tabDataJson[i].tabId);
		}
	}
}
