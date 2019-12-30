/*
 * testrequestsearchresults.jsp
 * 
 * 
 */
var selectedRowId = null;

// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;
var children = new Array(); 
var testRequestSearchResultsOnLoad = function(){
	var that = this,
		selectedRowId = null;

	// Make children private to the current context.
	var children = new Array();
	
	// from common/grid/resultiframegridresize.js
	// calls grid initialization, sets the footer messages, and resizes the iframe container.
	var totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0) 
	{
		document.getElementById("testRequestSearchResults").style["display"] = "";
		resultOnLoadWithGrid(testResultGridConfig);
	}
	else {
		document.getElementById("testRequestSearchResults").style["display"] = "none";
	}
	
	displayGridSearchDuration();

	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();
		

	
	// Add two functions to the current context for tracking child windows.
	that.addChild = function(child){
			children.push(child);
		};
	that.closeChildren = function(){
			try {
				for(var n = 0; n < children.length; n += 1) {
					try {
						children[n].closeAllchildren(); 
					} catch(ex){}
					children[n].close();
				}
			} catch(ex){}
			children = new Array(); 
		};	
	
};

// Functions for right click menus.
var showRightClick = function(rowId, colId){
	selectedRowId = rowId;
	toggleContextMenu("testRequestRightClick");
};

var showRequestDetail = function(){
	var requestId = gridCellValue(trSearchResultsGrid, selectedRowId, "testRequestId"),
    tabId = "testRequestDetail_" + requestId,
	queryString = "?uAction=search&testRequestId=" + requestId,
	destination = "testrequestform.do" + queryString;
	try
	{
        parent.parent.openIFrame(tabId,destination, messagesData.marsDetail + " " + requestId,'','N');
   
    }
	catch (ex)
	{
		windowName = "testRequestDetail_" + requestId;
		children[children.length] = openWinGeneric(destination, windowName.replace('.', 'a'), "1000", "600", "yes", "50", "50", "20", "20", "no"); 
	}
	//this.addChild(openWinGeneric(destination, windowName.replace('.', 'a'), "1000", "600", "yes", "50", "50", "20", "20", "no"));
};

function closeAllchildren()
{
// if (document.getElementById("uAction").value != 'new') {
 try {
  for(var n=0;n<children.length;n++) {
   try {
    children[n].closeAllchildren();
    } catch(ex){}
   children[n].close();
   }
  } catch(ex){}
  children = new Array();
// }
}
