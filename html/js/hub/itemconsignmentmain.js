// function submitSearchForm() {} Overridden in iframegridresizemain.js
// function createXSL() {} Overridden in iframegridresizemain.js
	
var dhxWins = null;

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function init() { /*This is to initialize the YUI*/
	this.cfg = new YAHOO.util.Config(this);
 	if (this.isSecure) {
		this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 	}

	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	If they are not initialized onLoad they tend to act weird*/
	legendWin = new YAHOO.widget.Panel("showLegendArea", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
	legendWin.render();

	//showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,draggable:true, modal:false } );
	//showErrorMessagesWin.render();
}

function showLegend(){
	var showLegendArea = document.getElementById("showLegendArea");
	showLegendArea.style.display="";
	legendWin.show();
}

function validateSearch() {
	var pulldown = document.getElementById('searchField');
	var searchType = pulldown.options[pulldown.selectedIndex].value;
	var search = $v('searchText');
	if (!isInteger(search,true)) {
		if (searchType == 'receiptId') {
			alert(messagesData.receiptInteger);
			return false;
		}
		else if (searchType == 'itemId') {
			alert(messagesData.itemInteger);
			return false;
		}
	}
	return true;
}

function showGrid()
{
 resizeFramecount=1;
 window.document.getElementById("resultGridDiv").style["display"] = "";
 window.document.getElementById("transitPage").style["display"] = "none";
}
