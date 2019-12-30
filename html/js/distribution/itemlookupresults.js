var children = new Array();

/*
 * Set this to be false if you don't want the grid width to resize based on
 * window size.
 */
var resizeGridWithWindow = true;

var lineMap = new Array();
var lineMap3 = new Array();
var selectedRowId;

function rightClickRow(rowId, ColId) {
	selectedRowId = rowId;
	toggleContextMenu("rightClickMenu");
}

function getCurrentRowVal(name) {
	var value = null;
	value = cellValue(selectedRowId, name);
	return encodeURIComponent(value);
}

function getParentVal(name) {
	var value = parent.$v(name);
	alert(value);
	return encodeURIComponent(value);
}

function getCurPath() {
	return encodeURIComponent("Item Lookup");
}

function showSourcingInfo() {
	var itemId = getCurrentRowVal('itemId');
	var title = "showSourcingInfo_" + itemId.replace(/[.]/, "_");
//	var loc = "/tcmIS/distribution/showsupplierpl.do?" +

	var loc = "/tcmIS/distribution/editpricelist.do?uAction=search&searchField=itemId|number&searchMode=is&searchArgument=" + itemId+
		"&inventoryGroup=" + $v('inventoryGroup') +
		"&opsEntityId=" + $v('opsEntityId') +
		"&hub=" + $v('hub') +
		"&itemId=" + itemId +
		"&showExpired=Y";

	children[children.length] = openWinGeneric(loc, title, "1024", "600", "yes", "50", "50");	
}

function showItemActivity(searchKey) {
	var itemId = getCurrentRowVal('itemId');
	var title = "showAllocatable_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/distribution/allocation.do?" +
		"itemId=" + itemId +
		"&curpath=" + getCurPath() +
		"&opsEntityId=" + $v('opsEntityId') +
		"&inventoryGroup=" + $v('inventoryGroup') +
		"&specList=No+Specification" +
		"&searchKey=" + searchKey +
		"&currencyId=" + currencyId +
		"&partDesc=" + getCurrentRowVal('itemDesc');

	children[children.length] = openWinGeneric(loc, title, "1100", "500", "yes", "50", "50");	
}

function showPreviousOrders(searchKey) {
	var itemId = getCurrentRowVal('itemId');
	var title = "showPreviousOrders_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/distribution/showallpreviousorders.do?" +
		"&region=" +
		"&inventoryGroup=" + $v('inventoryGroup') +
		"&curpath=" + getCurPath() +
		"&searchKey=" + searchKey +
		"&itemId=" + itemId;
	children[children.length] = openWinGeneric(loc, title, "1024", "500", "yes", "50", "50");	
}

function showQuotationHistory(searchKey) {
	var itemId = getCurrentRowVal('itemId');
	var title = "showQuotationHistory_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/distribution/quotationhistory.do?" +
		"region=" +
		"&curpath=" + getCurPath() +
		"&inventoryGroup=" + $v('inventoryGroup') +
		"&searchKey=" + searchKey +
		"&itemId=" + itemId;
	children[children.length] = openWinGeneric(loc, title, "1024", "500", "yes", "50", "50");

}

function showPoHistory(searchKey) {
	var itemId = getCurrentRowVal('itemId');
	var title = "showPoHistory_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/distribution/showpohistory.do?" +
		"region=" +
		"&curpath=" + getCurPath() +
		"&inventoryGroup=" + $v('inventoryGroup') +
		"&searchKey=" + searchKey +
		"&itemId=" + itemId;
	children[children.length] = openWinGeneric(loc, title, "1024", "500", "yes", "50", "50");	
}

function showItemNotes() {
	var itemId = getCurrentRowVal('itemId');
	var title = "showItemNotes_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/supply/edititemnotes.do?" +
		"&itemId=" + itemId;
   	
	children[children.length] = openWinGeneric(loc,title,"800","600","yes","50","50","20","20","no");
}

function showSupplierItemNotes() {
	var itemId = getCurrentRowVal('itemId');
	var title = "showSupplierItemNotes_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/supply/showsupplieritemnotes.do?" +
		"uAction=search" +
		"&opsEntityId=" + $v('opsEntityId') +
		"&searchMode=is" +
		"&searchField=itemId" +
		"&searchArgument=" + itemId;
   	
	children[children.length] = openWinGeneric(loc,title,"800","600","yes","50","50","20","20","no");
}

function showCatalogItemNotes() {
	var itemId = getCurrentRowVal('itemId');
	var title = "showCatalogItemNotes_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/distribution/catalogitemnotesmain.do?" +
		"&itemId=" + itemId;
   	
	children[children.length] = openWinGeneric(loc,title,"900","600","yes","50","50","20","20","no");
}

function showMinMax() {
	var itemId = getCurrentRowVal('itemId');
	var title = "showMinMaxLevels_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/hub/minmaxlevelmain.do?" +
		"uAction=showlevels" +
		"&opsEntityId=" + $v('opsEntityId') +
		"&hub=" + $v('hub') +
		"&inventoryGroup=" + $v('inventoryGroup') +
		"&criterion=itemId" +
		"&criteria=" + itemId;
   	
	children[children.length] = openWinGeneric(loc,title,"1024","600","yes","50","50");
}

function showDOT() {
	var itemId = getCurrentRowVal('itemId');
	var loc = "/tcmIS/hub/shippinginfo.do?uAction=search&itemId=" + itemId;
   	
	parent.parent.openIFrame("dotShippingInfo"+itemId,loc,""+messagesData.shipinfo+" "+itemId,"","N");
}

var currencyId;
function setCurrency()
{
	try{
		currencyId = parent.opsCurrMap[parent.document.getElementById('opsEntityId').value];
	}catch(err){}
}
