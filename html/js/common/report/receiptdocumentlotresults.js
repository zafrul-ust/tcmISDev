var children = new Array();

/************************************NEW***************************************************/
var mygrid;
windowCloseOnEsc = true;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

function resultOnLoad()
{		  	 	
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0)
	{
		document.getElementById("receiptDocumentLotBean").style["display"] = "";
		initializeGrid();
	}
	else
		document.getElementById("receiptDocumentLotBean").style["display"] = "none";
	displayGridSearchDuration();
	setResultFrameSize();
	parent.$("deliveriesCostData").value = $v("deliveriesCostData");
	parent.$("deliveriesCustomerData").value = $v("deliveriesCustomerData");
}

var rowSpanCols = [0,1,2,3];

function initializeGrid(){
	initGridWithGlobal(gridConfig);
}  

function selectRow(rowId,cellInd)
{
	selectedRowId = rowId;
}

function selectRightclick(rowId,cellInd){
	vitems = new Array();
    if (cellValue(rowId,"quantityIssued") != null && cellValue(rowId,"quantityIssued") > 0*1 && cellValue(rowId,"mrLineQty") > 0*1 &&
        cellValue(rowId,"companyId") == $v("companyId")) {
        vitems[vitems.length] = "text="+messagesData.movetoanotherworkarea+";url=javascript:moveToAnotherWorkArea();";
    } 
    if(cellInd >= mygrid.getColIndexById("receiptId") ) {
		var Dpcoll = altDocuments[rowId].coll;
		vitems[vitems.length] = "text="+messagesData.receiptDocuments+";url=javascript:showReceiptDocuments();";
		vitems[vitems.length] = "text="+messagesData.pickingDocuments+";url=javascript:showPickingDocuments();";
		if (cellValue(rowId,"receiptId").length > 0 && cellValue(rowId,"companyId") == $v("companyId")) {
			vitems[vitems.length] = marsMenuLine;
		} 
	}
	  
	if (vitems.length > 0 ) {
		replaceMenu('rightClickMenu',vitems); 
		toggleContextMenu('rightClickMenu');
	}
 	else {
 		toggleContextMenu('contextMenu');
 	}
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

function showReceiptDocument(documentId, receiptId, documentUrl) {
    var openByUrl = true;
    if(documentUrl != '') {
        if (documentUrl.indexOf("receipt_documents") > 0)
            openByUrl = false;
    }

    if (openByUrl) {
        if(documentUrl != '')
            openWinGeneric(documentUrl,"RecevingDocuments","800","450","yes","50","50","no");
    }else {
        var tmpUrl = "";
        if ($v("secureDocViewer") == 'true')
            tmpUrl = "/DocViewer/client/";
    	openWinGeneric(tmpUrl+"receiptdocviewer.do?uAction=viewReceiptDoc&documentId="+documentId+"&receiptId="+receiptId+"&companyId="+$v("companyId")
    			,"RecevingDocuments","800","450","yes","50","50","no"); 
    }
}

function moveToAnotherWorkArea()
{
    var facilityId = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("facilityId")).getValue();
    var accountSysArray = altAccountSysId[facilityId];
    if(accountSysArray.length > 1)
        parent.showAccountInputDialog(accountSysArray);
    else {
        $("accountSysId").value = accountSysArray[0].value;
        popMovementofMaterial();
    }
}

function popMovementofMaterial()
{
    var facilityId = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("facilityId")).getValue();
	var applicationDesc = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("applicationDesc")).getValue();
	var application = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("application")).getValue();
	var facPartNo = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("facPartNo")).getValue();
	var itemId = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("itemId")).getValue();
	var receiptId = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("receiptId")).getValue();
	var quantityIssued = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("quantityIssued")).getValue();
	var prNumber = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("prNumber")).getValue();
	var lineItem = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("lineItem")).getValue();
	var loc = "movementofmaterial.do?uAction=new&accountSysId="+$v("accountSysId")+"&facilityId="+facilityId+
			"&fromPrNumber="+prNumber+"&fromLineItem="+lineItem+
			"&facPartNo="+facPartNo+"&itemId="+itemId+"&receiptId="+receiptId+"&deliveredQty="+quantityIssued+
			"&fromApplication="+application+"&applicationDesc="+encodeURIComponent(applicationDesc);
	openWinGeneric(loc,"movementOfMaterial","825","340","yes","50","50","no"); 
}

function showMarsRequests()
{
	var receiptId = mygrid.cellById(mygrid.getSelectedRowId(), mygrid.getColIndexById("receiptId")).getValue();
	
	var loc = "testrequestsearchmain.do?uAction=search&receiptId="+receiptId;
	openWinGeneric(loc,"TestRequestResults","825","500","yes"); 
}

function showReceiptDocuments() {
	var receiptId = cellValue(mygrid.getSelectedRowId(),"receiptId");
	var inventoryGroup = "VOID";
	var companyId = "VOID";
	var loc = "/tcmIS/hub/receiptdocuments.do?receiptId="+receiptId+"&showDocuments=Yes&inventoryGroup="+inventoryGroup+"&companyId="+companyId;
	openWinGeneric(loc,"showAllProjectDocuments","450","300","no","80","80");
}

function showPickingDocuments() {
	var shipmentId = cellValue(mygrid.getSelectedRowId(),"shipmentId");
	var receiptId = cellValue(mygrid.getSelectedRowId(),"receiptId");
	
	var loc = "/tcmIS/hub/pickingunitdocuments.do?shipmentId="+shipmentId+"&receiptId="+receiptId;
    openWinGeneric(loc,"Picking_Unit_Documents","800","400","yes")
}



