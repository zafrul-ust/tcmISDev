
var children = [];
var logHistoryGrid = null;

function resultOnLoad() {
	resultOnLoadWithGrid(logHistoryGridConfig);
}

function updateDocumentMenu() {
	
	var menus = new Array();
	menus[menus.length] = "showmenu=documentMenu;text=View Documents;image=";
	
	var docNameList = new Array();
	
	if (logHistoryGrid.getSelectedRowId() !== undefined &&
			logHistoryGrid.getSelectedRowId() != null) {
		var documentNames = gridCellValue(logHistoryGrid,logHistoryGrid.getSelectedRowId(),"documentNames");
		var documentUrls = gridCellValue(logHistoryGrid,logHistoryGrid.getSelectedRowId(),"documentUrls");
	
		var docNameList = documentNames.split(";");
		var docUrlList = documentUrls.split(";");
	}
	
	var docs = new Array();
	
	var docIndex = 0;
	
	for (x in docNameList) {
		if (docUrlList[x] !== undefined && docNameList[docIndex] !== "") {
			docs[docIndex] = "text="+docNameList[docIndex]+
				";url=javascript:viewDocument('"+docUrlList[docIndex]+"');";
			docIndex++;
		}
	}
	
	if (docs.length == 0) {
		docs[docIndex] = "text=<font color='gray'>No Documents</font>"+
			";url=javascript:doNothing();";
	}
	
	replaceMenu('documentMenu', docs);
	replaceMenu('viewDocumentMenu', menus);
}

function replaceMenu(menuname,menus){
	var i = mm_returnMenuItemCount(menuname);
	
	for(;i> 1; i-- )
		mm_deleteItem(menuname,i);
	
	for( i = 0; i < menus.length; ){
		var str = menus[i];
	
		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
		if( i == 1 ) mm_deleteItem(menuname,1);
	}
}

function viewDocument(doc) {
	if (doc.length > 1) {
	    children[children.length] = openWinGeneric(doc,"VIEW_FILE","800","650","yes");
	}
}

function showViewDocumentMenu(rowId, colId) {
	var contactStatus = gridCellValue(logHistoryGrid,logHistoryGrid.getSelectedRowId(),"contactStatus");
	if (logHistoryGrid.getSelectedRowId() && gridCellValue(logHistoryGrid,logHistoryGrid.getSelectedRowId(),"contactStatus")) {
		updateDocumentMenu();
		toggleContextMenu('viewDocumentMenu');
	}
}

function doNothing() {}