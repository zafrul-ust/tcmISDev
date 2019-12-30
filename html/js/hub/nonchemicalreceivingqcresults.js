var beanGrid;
var selectedRowId;
windowCloseOnEsc = true;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

var rowSpanCols = [0,1,2,11,12,13,15,16,17,18,19,20,21,22,23,25,26,27];

function checklinks()
{
  if(showUpdateLinks)
  {
     parent.document.getElementById("nonChemicalResultLink").style["display"] = "";
     parent.document.getElementById("chemicalResultLink").style["display"] = "none";   
  }
}

function selectRow()
{
// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   selectedRowId = rowId0;
   if( ee != null ) { 
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
   columnId = beanGrid.getColumnId(colId0);
   
   var docType = cellValue(selectedRowId,"docType");
   
   vitems = new Array();
   if (docType == 'IT')
   {
	 vitems[vitems.length] = "text=" + messagesData.potitle + ";url=javascript:javascript:showrecforinvtransfrQc("+rowId0+");";
   }
   if (docType != 'IT' && docType != 'IA')
   {
     vitems[vitems.length] = "text=" + messagesData.previouspotitle + ";url=javascript:showPreviousPoLineQc("+rowId0+");";
   }
   vitems[vitems.length] = "text=" + messagesData.viewaddreceipts + ";url=javascript:showProjectDocuments("+rowId0+");";
   
   replaceMenu('nonreceivingqcMenu', vitems);

	toggleContextMenu('nonreceivingqcMenu');
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

function submitNonConfirm()
{
	
	$('userAction').value = 'confirm';
	parent.showPleaseWait(); 

	if (beanGrid != null)
		beanGrid.parentFormOnSubmit(); 

	document.genericForm.submit();
}

function checkAllNonChemicalReceipts() {
	var result;
	var checkNonChemicalReceipts = document
			.getElementById("checkNonChemicalReceipts");
	if (checkNonChemicalReceipts.checked) {
		result = true;
	} else {
		result = false;
	}

	var totalLines = document.getElementById("totallines").value;
	for ( var p = 0; p < totalLines; p++) {
		try {
			var updateStatus = document.getElementById("updateStatus" + p + "").value;
		} catch (ex) {
			updateStatus = "";
		}

		if (updateStatus != 'readOnly') {
			try {
				var ok = document.getElementById("ok" + p + "");
				ok.checked = result;
			} catch (ex) {
			}
		}
	}
}

function showreceivingpagelegend() {
	try {
		children[children.length] = openWinGeneric(
				"/tcmIS/help/receivingpagelegend.html", "receivingpagelegend",
				"290", "300", "no", "50", "50");
	} catch (ex) {
		openWinGeneric("/tcmIS/help/receivingpagelegend.html",
				"receivingpagelegend", "290", "300", "no", "50", "50");
	}
}

function showrecforinvtransfrQc(rowNumber) {
	var transferRequestId = cellValue(selectedRowId,"transferRequestId");
	
	loc = "/tcmIS/hub/transfertransactions.do?transferRequestId=";
	loc = loc + transferRequestId.value;
	try {
		children[children.length] = openWinGeneric(loc,
				"Previous_Transfer_Transactions", "700", "400", "yes");
	} catch (ex) {
		openWinGeneric(loc, "Previous_Transfer_Transactions", "700", "400",
				"yes");
	}

}

function showPreviousPoLineQc(selectedRowId) {
	
	var radianPo = cellValue(selectedRowId,"radianPo");
	var poLine = cellValue(selectedRowId,"poLine");
	var itemId = cellValue(selectedRowId,"itemId");
	var branchPlant = cellValue(selectedRowId,"branchPlant");
	var inventoryGroup = cellValue(selectedRowId,"inventoryGroup");

	loc = "/tcmIS/hub/showreceivedreceipts.do?radianPo=" + radianPo
			+ "&poLine=" + poLine + "&hub=" + branchPlant
			+ "&inventoryGroup=" + inventoryGroup + "&approved=Y";


	try {
		children[children.length] = openWinGeneric(loc,
				"Previous_PO_Line_Receiving", "700", "450", "yes");
	} catch (ex) {
		openWinGeneric(loc, "Previous_PO_Line_Receiving", "700", "450", "yes");
	}
}

function showProjectDocuments(selectedRowId) {
	var receiptId = cellValue(selectedRowId,"receiptId");
	var inventoryGroup = cellValue(selectedRowId,"inventoryGroup");
	var loc = "/tcmIS/hub/receiptdocuments.do?receiptId=" + receiptId
			+ "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "";
	try {
		children[children.length] = openWinGeneric(loc,
				"showAllProjectDocuments", "450", "300", "no", "80", "80");
	} catch (ex) {
		openWinGeneric(loc, "showAllProjectDocuments", "450", "300", "no",
				"80", "80");
	}
}