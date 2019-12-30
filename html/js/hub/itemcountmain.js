// function submitSearchForm() {} Overridden in iframegridresizemain.js
// function createXSL() {} Overridden in iframegridresizemain.js

var dhxWins = null;

// this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function init() { /* This is to initialize the YUI */
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure) {
		this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}

	/*
	 * Yui pop-ups need to be initialized onLoad to make them work
	 * correctly. If they are not initialized onLoad they tend to act weird
	 */
	legendWin = new YAHOO.widget.Panel("showLegendArea", {
	        width : "300px",
	        fixedcenter : true,
	        constraintoviewport : true,
	        underlay : "none",
	        close : true,
	        visible : false,
	        draggable : true,
	        modal : false
	});
	legendWin.render();

	// showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", {
	// width:"300px", height:"300px", fixedcenter: true,
	// constraintoviewport: true, underlay:"none", close:true,
	// visible:false,draggable:true, modal:false } );
	// showErrorMessagesWin.render();
}

function showLegend() {
	var showLegendArea = document.getElementById("showLegendArea");
	showLegendArea.style.display = "";
	legendWin.show();
}

var org_opsChanged = window.opsChanged;
window.opsChanged = function() {
	document.getElementById('consignedBSpan').style.display = 'none';
	org_opsChanged();
};

var org_hubChanged = window.hubChanged;
window.hubChanged = function() {
	document.getElementById('consignedBSpan').style.display = 'none';
	org_hubChanged();
	if(hubIsConsignment[$v('hub')]) {
		document.getElementById('addItemSpan1').style.display = 'none';
		document.getElementById('addItemSpan2').style.display = 'none';
	}
	else {
		document.getElementById('addItemSpan1').style.display = "";
		document.getElementById('addItemSpan2').style.display = "";
	}
};

function showConsignedB() {
	var s = invGrpType[$v('inventoryGroup')];
	if (s != null && s == "true") {
		document.getElementById("consignedBSpan").style.display = "";
	}
	else {
		document.getElementById("consignedBSpan").style.display = "none";
	}
}

function newConsignedItem() {

	if ($v('inventoryGroup').trim().length == 0) {
		alert(messagesData.selectinventorygroup);
		return false;
	}

	var loc = "/tcmIS/distribution/newconsigneditem.do?new&shipToId=&hub=" + encodeURIComponent($v('hub')) + "&opsEntityId=" + encodeURIComponent($v('opsEntityId')) + "&supplier=&supplierName=&companyId=Radian&destInventoryGroup="
	                + encodeURIComponent($v('inventoryGroup')) + "&sourceInventoryGroup=&fromItemCount=Y";

	openWinGeneric(loc, 'newConsignedItem', "800", "228", "yes", "50", "50", "20", "20", "no");

}

function selectCatPartNo()
{
	var loc = "/tcmIS/hub/selectcatpartno.do?uAction=selectCatPartNo&hub=" + $v('hub') + "&inventoryGroup=" + $v('inventoryGroup')+"&sortBy=" + $v('sortBy');
	openWinGeneric(loc,'_selectCatPartNo',"600", "600","yes", "50", "50");
}

function printInvGroupBinLabels(catPartno, itemId) {
	var loc = "/tcmIS/hub/printinvgroupbinlabel.do?labelType=bin&paperSize=31" + "&hub=" + $v('hub') + "&inventoryGroup=" + $v('inventoryGroup')+"&catPartNo=" + catPartno+"&itemId=" + itemId;
	try {
		openWinGeneric(loc, "showAllMrDocuments", "450", "300", "no", "80", "80");
	}
	catch (ex) {
		openWinGeneric(loc, "showAllMrDocuments", "450", "300", "no", "80", "80");
	}
}
