var showRighClickMenu=false;

function createExcel() {
	var loc = "inventorydetail.do?uAction=createExcel&catPartNo="+document.getElementById('catPartNo').value+
				 "&inventoryGroup="+document.getElementById('inventoryGroup').value+"&catalogId="+document.getElementById('catalogId').value+
				 "&partGroupNo="+document.getElementById('partGroupNo').value+"&catalogCompanyId="+document.getElementById('catalogCompanyId').value;
	openWinGenericExcel(loc,"showInventoryDetailExcel22","800","500","yes","80","80")
}

function showReceiptDocumentMenu(rowId){
	if (document.getElementById("receiptId"+rowId) != null) {
		if (document.getElementById("receiptId"+rowId).value.length != 0) {
			//first delete existing menu items
			//menu item starts with 0 (zero)
			var menuItemCount = mm_returnMenuItemCount('showReceiptDocument');
			if (menuItemCount > 0) {
				//NOTE: cannot remove all items, have to leave at least one item
				for (var i = 1; i < menuItemCount; i++) {
					mm_deleteItem('showReceiptDocument',1);    //always delete from top until no more items, BUT note delete top is 1 not 0 (zero)
				}
			}
			var documentIdArray = documentId[rowId];
			var documentNameArray = documentName[rowId];
			//edit the item left behind
			var jsUrl = "showDocument("+documentIdArray[0].receiptId+","+documentIdArray[0].documentId+")";
			var menuText = 'text='+documentNameArray[0]+';url=javascript:'+jsUrl+';';
			mm_editItem('showReceiptDocument',0,menuText);
			//now add items to menu
			//adding item start with 0 (zero)
			//but since i cannot delete all items, starting new menu will starts with 1
			for (var j = 1; j < documentIdArray.length; j++) {
				var jsUrl = "showDocument("+documentIdArray[j].receiptId+","+documentIdArray[j].documentId+")";
				var menuText = 'text='+documentNameArray[j]+';url=javascript:'+jsUrl+';';
				mm_insertItem('showReceiptDocument',j,menuText);
			}

			toggleContextMenu('showAll');
		}
	}
}

function showDocument(receiptId, documentId) {
	var tmpUrl = "";
    if ($v("secureDocViewer") == 'true')
        tmpUrl = "/DocViewer/client/";
	openWinGeneric(tmpUrl + "receiptdocviewer.do?uAction=viewReceiptDoc&documentId="+documentId+"&receiptId="+receiptId+"&companyId="+$v("companyId")
			,'_MrAllocationShowDocument','650','600','yes');
}