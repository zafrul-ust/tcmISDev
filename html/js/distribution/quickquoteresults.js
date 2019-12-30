var children = new Array(); 
var mrHistoryGrid = null;
function resultOnLoad() {
	var mrTotalLines = document.getElementById("mrTotalLines").value;
	if (mrTotalLines > 0) {
		document.getElementById("mrBean").style["display"] = "";
			// build the grid for display
		doMrHistoryInitGrid();
	}
	else {
		document.getElementById("mrBean").style["display"] = "none";
	}

	var quoteTotalLines = document.getElementById("quoteTotalLines").value;
	if (quoteTotalLines > 0) {
		// make result area visible if data exist
		document.getElementById("quoteHistoryBean").style["display"] = "";
		// build the grid for display
		doQuoteInitGrid();
	}
	else {
		document.getElementById("quoteHistoryBean").style["display"] = "none";
	}

	var poHistoryTotalLines = document.getElementById("poHistoryTotalLines").value;
	if (poHistoryTotalLines > 0) {
		// make result area visible if data exist
		document.getElementById("poHistoryBean").style["display"] = "";
		// build the grid for display
		doPoHistoryInitGrid();
	}
	else {
		document.getElementById("poHistoryBean").style["display"] = "none";
	}
	
	var inventoryTotalLines = document.getElementById("inventoryTotalLines").value;
	if (inventoryTotalLines > 0) {
		// make result area visible if data exist
		document.getElementById("inventoryBean").style["display"] = "";
		// build the grid for display
		doInventoryInitGrid();
	}
	else {
		document.getElementById("inventoryBean").style["display"] = "none";
	}
	
	try {
		resizeGrids();
	}catch(ex){}
	
	setTimeout("parent.$('transitPage').style.display='none'",300);

}

function doMrHistoryInitGrid() {
	mrHistoryGrid = new dhtmlXGridObject('mrBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// Set submitDefault = false here because I copy the values to hidden inputs 
	initGridWithConfig(mrHistoryGrid, mrHistoryConfig, false, false, false);
	//make sure this is set for onAfterHaasGridRendered to work correctly
	mrHistoryGrid._haas_bg_render_enabled = true;
	mrHistoryGrid.enableSmartRendering(true);
	
	if (typeof (mrHistoryJsonMainData) != 'undefined') {
		mrHistoryGrid.parse(mrHistoryJsonMainData, "json");
	}

	mrHistoryGrid.attachEvent("onAfterHaasGridRendered", setMrHistoryRowColor);
//	mrHistoryGrid.attachEvent("onRowSelect", mrHistorySelectRow);
	mrHistoryGrid.attachEvent("onRightClick", mrHistoryRightclick);

	//hideIntercompanyRow();
}

function setMrHistoryRowColor() {
	
	var mrHistoryGridSize = mrHistoryGrid.getRowsNum();

	for (var p = 1; p < (mrHistoryGridSize+1) ; p ++)
	  	  colorMrHistoryRedRow(p);

}

function hideIntercompanyRow() {	
	displayInterCoRow = true;
	var mrHistoryGridSize = mrHistoryGrid.getRowsNum();
	if (!$("hideintercompany").checked)
    {
		displayInterCoRow = false;
    }
		
	for (var p = 1; p < (mrHistoryGridSize+1) ; p ++)
	{
			var intercompanyCustomer = mrHistoryGrid.cellById(p, mrHistoryGrid.getColIndexById("intercompanyCustomer")).getValue();			
			if (intercompanyCustomer == 'Y' && displayInterCoRow)
			{
				mrHistoryGrid.setRowHidden(p,true);
			}
			else
			{
				mrHistoryGrid.setRowHidden(p,false);
			}
	}	  	 
}


function colorMrHistoryRedRow(rowId) {
	var quantity = mrHistoryGrid.cellById(rowId, mrHistoryGrid.getColIndexById("quantity")).getValue();
	var quantityShipped = mrHistoryGrid.cellById(rowId, mrHistoryGrid.getColIndexById("quantityShipped")).getValue();
	var salesOrder = mrHistoryGrid.cellById(rowId, mrHistoryGrid.getColIndexById("salesOrder")).getValue();

	if (quantity == quantityShipped || (salesOrder != null && salesOrder.length > 0)) {
			mrHistoryGrid.haasSetColSpanStyle(rowId, 0, 13, "color: #8B0000;");
	}
}

function doQuoteInitGrid() {
	quoteHistoryGrid = new dhtmlXGridObject('quoteHistoryBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// Set submitDefault = false here because I copy the values to hidden inputs 
	initGridWithConfig(quoteHistoryGrid, quoteHistoryConfig, false, false, false);
	if (typeof (quoteHistoryJsonMainData) != 'undefined') {
		quoteHistoryGrid.parse(quoteHistoryJsonMainData, "json");
	}

//	quoteHistoryGrid.attachEvent("onRowSelect", quoteHistorySelectRow);
	quoteHistoryGrid.attachEvent("onRightClick", quoteHistoryRightclick);
}

function doPoHistoryInitGrid() {
	poGrid = new dhtmlXGridObject('poHistoryBean');

	initGridWithConfig(poGrid, poHistoryConfig, false, false, false);
	
	//make sure this is set for onAfterHaasGridRendered to work correctly
	poGrid._haas_bg_render_enabled = true;
	poGrid.enableSmartRendering(true);
	
	if (typeof (poHistoryJsonMainData) != 'undefined') {
		poGrid.parse(poHistoryJsonMainData, "json");
	}
	
	poGrid.attachEvent("onAfterHaasGridRendered", setPoRowColor);
//	poGrid.attachEvent("onRowSelect", poSelectRow);
	poGrid.attachEvent("onRightClick", poHistoryRightclick);
}

function setPoRowColor() {
	
	var poGridSize = poGrid.getRowsNum();
	
	for (var p = 1; p < (poGridSize+1) ; p ++)
	  	  colorPoRedRow(p);
  
}

function colorPoRedRow(rowId) {
	var quantity = poGrid.cellById(rowId, poGrid.getColIndexById("quantity")).getValue();
	var quantityReceived = poGrid.cellById(rowId, poGrid.getColIndexById("totalQuantityReceived")).getValue();
	if (quantity == quantityReceived) {
			poGrid.haasSetColSpanStyle(rowId, 0, 9, "color: #8B0000;");
	}
}

function doInventoryInitGrid() {
	inventoryGrid = new dhtmlXGridObject('inventoryBean');

	initGridWithConfig(inventoryGrid, inventoryConfig, false, false, false);
	if (typeof (inventoryJsonMainData) != 'undefined') {
		inventoryGrid.parse(inventoryJsonMainData, "json");
	}
	
//	inventoryGrid.attachEvent("onRowSelect", inventorySelectRow);
	inventoryGrid.attachEvent("onRightClick", inventoryRightclick);
}

function mrHistoryRightclick(rowId, cellInd) {
	mrHistoryGrid.selectRowById(rowId, null, false, false);
	
	var vitems = new Array();
	vitems[vitems.length] = "text="+messagesData.open+";url=javascript:openMrHistory();";
	vitems[vitems.length] = "text="+messagesData.mrlineallocation+";url=javascript:showMrLineAllocationReport();";
	vitems[vitems.length] = "text="+messagesData.createnewquote+";url=javascript:createScratchPad('createQuoteFromMR', mrHistoryGrid,'openNewTab');";
	vitems[vitems.length] = "text="+messagesData.createnewmr+";url=javascript:createScratchPad('createMRFromMR', mrHistoryGrid,'openNewTab');";
	vitems[vitems.length] = "text="+messagesData.createnewquotefornewcustomer+";url=javascript:searchCustomer('Q', mrHistoryGrid, 'MR');";
	vitems[vitems.length] = "text="+messagesData.createnewmrfornewcustomer+";url=javascript:searchCustomer('MR', mrHistoryGrid, 'MR');";
	
	if(parent.$v("openedPr").length > 0 && top.eval('scratchPad'+parent.$v("openedPr")+'frame').addLinefromQuickPage) 
		vitems[vitems.length] = "text="+messagesData.addline+" "+parent.$v("openedQuoteType")+" "+parent.$v("openedPr")+";url=javascript:addLine(mrHistoryGrid, 'MR');";
		
	replaceMenu('mrHistoryMenu',vitems);
	toggleContextMenu('mrHistoryMenu');
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


function openMrHistory(newPrNumber) {
	if(newPrNumber == null)
		var prNumber = mrHistoryGrid.cellById(mrHistoryGrid.getSelectedRowId(), mrHistoryGrid.getColIndexById("hiddenPrNumber")).getValue();
	else
		var prNumber = newPrNumber;
	var lineItem = mrHistoryGrid.cellById(mrHistoryGrid.getSelectedRowId(), mrHistoryGrid.getColIndexById("lineItem")).getValue();
	
    var tabId = 'scratchPad'+prNumber+'';
    var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent(tabId)+"&selectedRowId="+lineItem;
 	
 	try
	{
		parent.parent.openIFrame(tabId,loc,'MR '+prNumber+'','','N');
    }
	catch (ex){
		children[children.length] = openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");
	}  
} 

function showMrLineAllocationReport()
{
	var companyId = mrHistoryGrid.cellById(mrHistoryGrid.getSelectedRowId(), mrHistoryGrid.getColIndexById("companyId")).getValue();
	var mrNumber  = mrHistoryGrid.cellById(mrHistoryGrid.getSelectedRowId(), mrHistoryGrid.getColIndexById("hiddenPrNumber")).getValue();
	var lineItem  = mrHistoryGrid.cellById(mrHistoryGrid.getSelectedRowId(), mrHistoryGrid.getColIndexById("lineItem")).getValue();
	if (companyId.length>0 )
	{
		var loc = "mrallocationreportmain.do?fromCustomerOrdertracking=Y&companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem="+lineItem+"";
		children[children.length] = openWinGeneric(loc,"showMrAllocationReport","800","550","yes","80","80","no");
	}
}

function createScratchPad(action, grid) {
    var url="quickquoteresults.do?uAction="+action+
	        "&oldPrNumber="+grid.cellById(grid.getSelectedRowId(), grid.getColIndexById("hiddenPrNumber")).getValue()+
	        "&lineItem="+grid.cellById(grid.getSelectedRowId(), grid.getColIndexById("lineItem")).getValue()+
	        "&callback=openNewTab";

	callToServer(url);
}

function searchCustomer(quoteType, grid, fromQuoteType) {
	parent.showTransitWin("");
	
	parent.$("openedQuoteType").value = quoteType;
	$("fromMRorQuote").value = fromQuoteType;
	
	$("oldPrNumber").value = grid.cellById(grid.getSelectedRowId(), grid.getColIndexById("hiddenPrNumber")).getValue();
	$("lineItem").value = grid.cellById(grid.getSelectedRowId(), grid.getColIndexById("lineItem")).getValue();
	var loc = "customersearchmain.do?fromQuickPage=Y&popup=Y&quoteType="+quoteType;
	
	children[children.length] = openWinGeneric(loc,"CustomerSearch","900","600","yes","80","80","yes");
}

function createScratchPadforNewCustomer(newCustomerId, newCompanyId, newShipToLocId) {

	if($v("fromMRorQuote") == "MR")
		var url="quickquoteresults.do?uAction=createNewScratchPadforNewCustomerfromMR"+
				"&newCustomerId="+newCustomerId+"&newCompanyId="+newCompanyId+"&newShipToLocId="+newShipToLocId+
		        "&oldPrNumber="+$v("oldPrNumber")+
		        "&lineItem="+$v("lineItem")+
		        "&quoteType="+parent.$v("openedQuoteType");
	else
		var url="quickquoteresults.do?uAction=createNewScratchPadforNewCustomerfromQuote"+
				"&newCustomerId="+newCustomerId+"&newCompanyId="+newCompanyId+"&newShipToLocId="+newShipToLocId+
		        "&oldPrNumber="+$v("oldPrNumber")+
		        "&lineItem="+$v("lineItem")+
		        "&quoteType="+parent.$v("openedQuoteType");

	callToServer(url);
}


function openNewTab(quoteType, prNumber, errorFromQuickPages) {
	var tabId = 'scratchPad'+prNumber+'';
	
	parent.$("openedPr").value = prNumber;
	parent.$("openedQuoteType").value = quoteType;
	
    var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+
    			"&tabId="+encodeURIComponent(tabId)+"&errorFromQuickPages="+errorFromQuickPages;	
    			
    parent.closeTransitWin();
  	try
	{
		parent.parent.openIFrame(tabId,loc,quoteType+' '+prNumber+'','','N');
    }
	catch (ex){
		children[children.length] = openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");
	}
}

function addLine(targetedGrid, fromQuoteType) {
	parent.showTransitWin("");
	
	var prNumber = targetedGrid.cellById(targetedGrid.getSelectedRowId(), targetedGrid.getColIndexById("hiddenPrNumber")).getValue();
	var lineItem = targetedGrid.cellById(targetedGrid.getSelectedRowId(), targetedGrid.getColIndexById("lineItem")).getValue();

	if(top.eval('scratchPad'+parent.$v("openedPr")+'frame').addLinefromQuickPage)
	{ 
		if (parent.$v("openedQuoteType") == 'Q') {
			callToServer("quickcustomerviewresults.do?uAction=createNewQuoteLine&oldPrNumber="+prNumber+"&lineItem="+lineItem+"&openedPr="+parent.$v("openedPr"));
		}
		else if(parent.$v("openedQuoteType") == 'MR' && fromQuoteType == 'MR'){
			callToServer("quickcustomerviewresults.do?uAction=createNewMRLineFromMR&oldPrNumber="+prNumber+"&lineItem="+lineItem+"&openedPr="+parent.$v("openedPr"));
		}
		else if(parent.$v("openedQuoteType") == 'MR' && fromQuoteType == 'Q'){
			callToServer("quickcustomerviewresults.do?uAction=createNewMRLineFromQuote&oldPrNumber="+prNumber+"&lineItem="+lineItem+"&openedPr="+parent.$v("openedPr"));
		}
	}  

}

function quoteHistoryRightclick(rowId, cellInd) {
	quoteHistoryGrid.selectRowById(rowId, null, false, false);
	
	var vitems = new Array();
	vitems[vitems.length] = "text="+messagesData.open+";url=javascript:openQuoteHistory();";
	vitems[vitems.length] = "text="+messagesData.createnewquote+";url=javascript:createScratchPad('createQuoteFromQuote', quoteHistoryGrid,'openNewTab');";
	vitems[vitems.length] = "text="+messagesData.createnewmr+";url=javascript:createScratchPad('createMRFromQuote', quoteHistoryGrid,'openNewTab');";
	vitems[vitems.length] = "text="+messagesData.createnewquotefornewcustomer+";url=javascript:searchCustomer('Q', quoteHistoryGrid, 'Q');";
	vitems[vitems.length] = "text="+messagesData.createnewmrfornewcustomer+";url=javascript:searchCustomer('MR', quoteHistoryGrid, 'Q');";
		
	if(parent.$v("openedPr").length > 0 && top.eval('scratchPad'+parent.$v("openedPr")+'frame').addLinefromQuickPage) 
		vitems[vitems.length] = "text="+messagesData.addline+" "+parent.$v("openedQuoteType")+" "+parent.$v("openedPr")+";url=javascript:addLine(quoteHistoryGrid, 'Q');";
		
	replaceMenu('quoteHistoryMenu',vitems);
	toggleContextMenu('quoteHistoryMenu');
}

function openQuoteHistory(newPrNumber) {
	if (newPrNumber == null)
		var prNumber = quoteHistoryGrid.cellById(quoteHistoryGrid.getSelectedRowId(), quoteHistoryGrid.getColIndexById("hiddenPrNumber")).getValue();
	else
		var prNumber = newPrNumber;
		
//    var type = cellValue(beangrid.getSelectedRowId(),"type");
    var tabId = 'scratchPad'+prNumber+'';
    var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent(tabId);
 	
 	try
	{
		parent.parent.openIFrame(tabId,loc,'Q '+prNumber+'','','N');
    }
	catch (ex){
		children[children.length] = openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");
	}
} 

function editSourcingInfo() {
//	loc = "/tcmIS/distribution/showsupplierpl.do?itemId=" + parent.$v('itemId');
	loc = "/tcmIS/distribution/editpricelist.do?uAction=search&searchField=itemId|number&searchMode=is&searchArgument=" + parent.$v('itemId');
	loc = loc + "&itemId=" + parent.$v('itemId');
	loc = loc + "&inventoryGroup=" + parent.$v('inventoryGroup');
	loc = loc + "&hub=" + parent.$v('hub');
    loc = loc + "&opsEntityId=" + parent.$v('opsEntityId');

	openWinGeneric(loc, "showSourcingInfo", "1024", "600", "yes", "50", "50");
}

function showSupplierItemNote() {
	loc = "/tcmIS/supply/showsupplieritemnotes.do?uAction=search&searchMode=is&searchField=itemId&searchArgument=" + parent.$v('itemId');
	loc = loc + "&itemId=" + parent.$v('itemId');
	loc = loc + "&itemDesc=" + encodeURIComponent(parent.$v('itemDesc'));
    loc = loc + "&opsEntityId=" + parent.$v('opsEntityId');

	openWinGeneric(loc, "showSupplierItemNote", "1024", "600", "yes", "50", "50");
}

function showItemNote() {
	loc = "/tcmIS/supply/edititemnotes.do?fromJDE=Y";
	loc = loc + "&itemId=" + parent.$v('itemId');

	openWinGeneric(loc, "showItemNote", "1024", "600", "yes", "50", "50");
}

function showShippingInfo() {
	loc = "/tcmIS/hub/shippinginfo.do?uAction=search";
	loc = loc + "&itemId=" + parent.$v('itemId');

	openWinGeneric(loc, "showShippingInfo", "1024", "600", "yes", "50", "50");
}

function poHistoryRightclick(rowId, cellInd) {
	poGrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('poHistoryMenu');
}

function openPoHistory() {

	var HaasPO = poGrid.cellById(poGrid.getSelectedRowId(), poGrid.getColIndexById("hRadianPo")).getValue();
    var loc = "/tcmIS/supply/purchaseorder.do?po="+HaasPO+"&Action=searchlike&subUserAction=po";
    
 	try
	{
		parent.parent.openIFrame("purchaseOrder"+HaasPO+"",loc,""+messagesData.purchaseorder+" "+HaasPO+"","","N");
    }
	catch (ex){
		children[children.length] = openWinGeneric(loc,"showradianPo","800","600","yes","50","50");
	}
}

function showExpediteNotes() {
	var radianPo = poGrid.cellById(poGrid.getSelectedRowId(), poGrid.getColIndexById("hRadianPo")).getValue();
	var poLine = poGrid.cellById(poGrid.getSelectedRowId(), poGrid.getColIndexById("poLine")).getValue();
	
	if(radianPo != null && radianPo.trim().length >= 0) {
		loc = "/tcmIS/supply/newpoexpediting.do?action=searchnewpoexpedite&radianPo=" + radianPo;
		loc = loc + "&poLine=" + poLine;
	
		openWinGeneric(loc, "showExpediteNote", "1024", "600", "yes", "50", "50");
	}
}

function showReceiptDocuments() {
	var HaasPO = poGrid.cellById(poGrid.getSelectedRowId(), poGrid.getColIndexById("hRadianPo")).getValue();
	var poLine = poGrid.cellById(poGrid.getSelectedRowId(), poGrid.getColIndexById("poLine")).getValue();
	var inventoryGroup = poGrid.cellById(poGrid.getSelectedRowId(), poGrid.getColIndexById("inventoryGroup")).getValue();
	
    var loc = "/tcmIS/hub/poreceiptdocuments.do?showDocuments=Yes&radianPo="+HaasPO+
    			"&poLine="+poLine+"&inventoryGroup="+inventoryGroup;
    
 	children[children.length] = openWinGeneric(loc, "receiptDocuments", "450", "600", "yes", "50", "50");
} 

function inventoryRightclick(rowId, cellInd) {
	inventoryGrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('inventoryMenu');
}

function showDetails() {

	var title = "showAllocatable_" + parent.$v('itemId').replace(/[.]/, "_");
	var loc = "/tcmIS/distribution/allocation.do?" +
		"itemId=" + parent.$v('itemId') +
		"&curpath=" + encodeURIComponent("Item Lookup") +
		"&opsEntityId=" + parent.$v('opsEntityId') +
		"&inventoryGroup=" + inventoryGrid.cellById(inventoryGrid.getSelectedRowId(), inventoryGrid.getColIndexById("inventoryGroup")).getValue()+
		"&specList=No+Specification" +
		"&searchKey=IG" +
		"&currencyId=" + parent.$v('currencyId') +
		"&partDesc=" + encodeURIComponent(parent.$v('itemDesc'));

	children[children.length] = openWinGeneric(loc, title, "1100", "500", "yes", "50", "50");	
} 

var resizeGridWithWindow=true; /*If this is set to false, I don't change the width of the result table upon resize.*/
var layouInitialized=false; /*This gets set to true after the layout is initialized. This is to load the layout in tabs.*/
var myresultHeight = 0;

function loadLayoutWin3E(pageName)
{
  
  setWindowSizes();
  myresultHeight = myHeight - parent.$("searchFrameDiv").offsetHeight;
//  var searchFrameHeight=0;
//  searchFrameHeight = window.document.getElementById("resultFrameDiv1").offsetHeight;

  var dhxWins = new dhtmlXWindows();
  var win = dhxWins.createWindow(pageName, 0, 0, 800, 600);
  win.setText(pageName);
  win.clearIcon();  // hide icon
  win.denyResize(); // deny resizing
  win.denyMove();   // deny moving
  win.denyPark();   // deny parking
  win.button("close").disable(); // disable "close" button
  win.setToFullScreen(true);
  win.hideHeader();

  dhxLayout1 = new dhtmlXLayoutObject(win, "3J");
  dhxLayout1.cells("a").attachObject("resultFrameDiv1");
 // dhxLayout.cells("b").attachObject("resultFrameDiv1");
  dhxLayout1.cells("c").attachObject("resultFrameDiv2");
  dhxLayout1.cells("a").hideHeader();
  dhxLayout1.cells("b").hideHeader();
  dhxLayout1.cells("c").hideHeader();

  dhxLayout1.cells("a").setHeight(myresultHeight/2);
 // dhxLayout1.cells("a").setText("dfsdfsdf");
     
  dhxLayout1.cells("b").setHeight(myresultHeight);
  dhxLayout1.cells("c").setHeight(myresultHeight/2);
  dhxLayout1.cells("a").setWidth(myWidth/2);
  dhxLayout1.cells("b").setWidth(myWidth/2);
  dhxLayout1.cells("c").setWidth(myWidth/2);
  
  Layout1 = new dhtmlXLayoutObject(dhxLayout1.cells("b"), "2E");
  Layout1.cells("a").attachObject("resultFrameDiv3");
  Layout1.cells("b").attachObject("resultFrameDiv4");
  Layout1.cells("a").hideHeader();
  Layout1.cells("b").hideHeader();

  Layout1.cells("a").setText("");
     
  Layout1.cells("a").setHeight(myresultHeight/2);
  Layout1.cells("b").setHeight(myresultHeight/2);

  Layout1.cells("a").setWidth(myWidth/2);
  Layout1.cells("b").setWidth(myWidth/2);
  
  // attaching onPanelResizeFinish event handler
  dhxLayout1.attachEvent("onPanelResizeFinish", function(){
        resizeGrids();
  }); 

  // attaching onPanelResizeFinish event handler
  Layout1.attachEvent("onPanelResizeFinish", function(){
        resizeGrids();
  }); 
  
  popupWindow1 = dhxLayout1.dhxWins.createWindow("popupWindow1", 0, 0, 400, 400);  
  popupWindow1.setText(messagesData.mrHistory +'&nbsp;');
  popupWindow1.setText(messagesData.mrHistory +'&nbsp;' + '<a href="#" onclick="createMRHistoryXSL()">'+messagesData.createexcel+'</a>');
  
  popupWindow1.attachObject("resultFrameDiv1");
  
  popupWindow1.button("close").hide();
  popupWindow1.button("park").hide();
  popupWindow1._allowResizeGlobal = false;  // disable resizing
  popupWindow1._allowMaxMin = true;  // allow Max Min
  
  popupWindow1.attachEvent("onMaximize",function(win){
 		maxMRHistory();
  }); 
  
  popupWindow1.attachEvent("onMinimize",function(win){
  		minMRHistory();
  }); 
  
  popupWindow2 = dhxLayout1.dhxWins.createWindow("popupWindow2", 0, 0, 400, 400);
  popupWindow2.setText(messagesData.priceQuoteHistory +'&nbsp;' + '<a href="#" onclick="createPriceQuoteHistoryXSL()">'+messagesData.createexcel+'</a>');
  popupWindow2.attachObject("resultFrameDiv2");
  
  popupWindow2.button("close").hide();
  popupWindow2.button("park").hide();
  popupWindow2._allowResizeGlobal = false;
  popupWindow2._allowMaxMin = true;
  
  popupWindow2.attachEvent("onMaximize",function(win){
 		maxQuoteHistory();
  }); 
  
  popupWindow2.attachEvent("onMinimize",function(win){
  		minQuoteHistory();
  }); 
  
  popupWindow3 = dhxLayout1.dhxWins.createWindow("popupWindow3", 0, 0, 400, 400);
  var poText = messagesData.pohistory +'&nbsp;' + '<a href="#" onclick="createPOHistoryXSL()">'+messagesData.createexcel+'</a>' 
  				+ messagesData.blank + '<a href="#" onclick="editSourcingInfo()">'+messagesData.editsourcinginfo+'</a>' 
  				+ messagesData.blank + '<a href="#" onclick="showSupplierItemNote()">'+messagesData.supplieritemnotes+'</a>'
  				+ messagesData.blank + '<a href="#" onclick="showItemNote()">'+messagesData.itemnotes+'</a>'
  				+ messagesData.blank + '<a href="#" onclick="showShippingInfo()">'+messagesData.shippinginfo+'</a>';
  popupWindow3.setText(poText);
  popupWindow3.attachObject("resultFrameDiv3");
  
  popupWindow3.button("close").hide();
  popupWindow3.button("park").hide();
  popupWindow3._allowResizeGlobal = false;
  popupWindow3._allowMaxMin = true;
  
  popupWindow3.attachEvent("onMaximize",function(win){
 		maxPOHistory();
  }); 
  
  popupWindow3.attachEvent("onMinimize",function(win){
  		minPOHistory();
  }); 
  
  popupWindow4 = dhxLayout1.dhxWins.createWindow("popupWindow4", 0, 0, 400, 400);
  popupWindow4.setText(messagesData.inventory +'&nbsp;' + '<a href="#" onclick="createInventoryXSL()">'+messagesData.createexcel+'</a>');
  popupWindow4.attachObject("resultFrameDiv4");
  
  popupWindow4.button("close").hide();
  popupWindow4.button("park").hide();
  popupWindow4._allowResizeGlobal = false;
  popupWindow4._allowMaxMin = true;
  
  popupWindow4.attachEvent("onMaximize",function(win){
 		maxInventory();
  }); 
  
  popupWindow4.attachEvent("onMinimize",function(win){
  		minInventory();
  }); 
  
  popupWindow1.denyMove();
  popupWindow2.denyMove();
  popupWindow3.denyMove();
  popupWindow4.denyMove();

}

function maxMRHistory() {
	if (_isIE)
	  	popupWindow1.setDimension(myWidth, dhxLayout1.cells("b").getHeight()-5);
	else
		popupWindow1.setDimension(myWidth+30, dhxLayout1.cells("b").getHeight()-3);
		
 	  try
	  {
		   var mrHistoryId=mrHistoryGrid.entBox.id;
		   var mrHistoryGriDiv = $(mrHistoryId);
		   if (_isIE)
		   		mrHistoryGriDiv.style.height = dhxLayout1.cells("b").getHeight()-25 + "px";
		   else
	    		mrHistoryGriDiv.style.height = dhxLayout1.cells("b").getHeight()-27 + "px";
		   mrHistoryGrid.setSizes();  
	  }
	  catch(ex){ }
      
        
	  try
  	  {
   			if (_isIE){
  // 				document.getElementById('resultsMaskTable1').width =  myWidth-2 + "px";
  //    			$('mrBean').style.width = myWidth-2 + "px";
      			mrHistoryGriDiv.style.width = myWidth-2 + "px"; // myWidth - 10+"px"; 
   				reSizeCoLumnWithGridWidth(mrHistoryGrid,myWidth+25); 
   			}
   			else {
  // 				document.getElementById('resultsMaskTable1').width =  myWidth+25 + "px";
   //   			$('mrBean').style.width = myWidth+25 + "px";
      			mrHistoryGriDiv.style.width = myWidth+25 + "px"; // myWidth - 10+"px"; 
   				reSizeCoLumnWithGridWidth(mrHistoryGrid,myWidth+78);
   			} 
  	  }
  	  catch(ex){ }
  	  
  	  popupWindow1.bringToTop();
 	  popupWindow2.bringToBottom();
  	  popupWindow3.bringToBottom();
 	  popupWindow4.bringToBottom();
}

function minMRHistory() {
	popupWindow1.setDimension(dhxLayout1.cells("a").getWidth(), dhxLayout1.cells("a").getHeight()); 
	
	  try
	  {
		   var mrHistoryId=mrHistoryGrid.entBox.id;
		   var mrHistoryGriDiv = $(mrHistoryId);
		   mrHistoryGriDiv.style.height = dhxLayout1.cells("a").getHeight()-25 + "px";
		   mrHistoryGrid.setSizes();  
	  }
	  catch(ex) { }
      
 //     document.getElementById('resultsMaskTable1').width =  dhxLayout1.cells("a").getWidth()-2 + "px";
 //     $('mrBean').style.width = dhxLayout1.cells("a").getWidth()-2 + "px";
        
	  try
  	  {
   			if (_isIE) {
		   		mrHistoryGriDiv.style.width = dhxLayout1.cells("a").getWidth()-2 + "px"; // myWidth - 10+"px"; 
   				reSizeCoLumnWithGridWidth(mrHistoryGrid,dhxLayout1.cells("a").getWidth()+20); 
	   		}
	   		else {
	   			mrHistoryGriDiv.style.width = dhxLayout1.cells("a").getWidth()-2 + "px"; // myWidth - 10+"px"; 
   				reSizeCoLumnWithGridWidth(mrHistoryGrid,dhxLayout1.cells("a").getWidth()+50); 
	   		}
  	  }
  	  catch(ex) { }
}

function maxQuoteHistory() {
	if (_isIE)
	  	popupWindow2.setDimension(myWidth, dhxLayout1.cells("b").getHeight()-5);
	else
		popupWindow2.setDimension(myWidth+30, dhxLayout1.cells("b").getHeight()-3);
		
 	  try
	  {
	  	var quoteHistoryId=quoteHistoryGrid.entBox.id;
	    var quoteHistoryGriDiv = $(quoteHistoryId);
	    if (_isIE)	
	    	quoteHistoryGriDiv.style.height = dhxLayout1.cells("b").getHeight()-25 + "px";
	    else
	    	quoteHistoryGriDiv.style.height = dhxLayout1.cells("b").getHeight()-27 + "px";
	    quoteHistoryGrid.setSizes();  
	  }
	  catch(ex){ }
      
	  try
  	  {
   		if (_isIE) {
 //  			document.getElementById('resultsMaskTable2').width =  myWidth-2 + "px";
 //     		$('quoteHistoryBean').style.width =  myWidth-2 + "px";
	   		quoteHistoryGriDiv.style.width = myWidth-2 + "px"; // myWidth - 10+"px"; 
	   		reSizeCoLumnWithGridWidth(quoteHistoryGrid,myWidth+23); 
   		}
   		else {
  // 			document.getElementById('resultsMaskTable2').width =  myWidth +25 + "px";
  //    		$('quoteHistoryBean').style.width =  myWidth +25+ "px";
   			quoteHistoryGriDiv.style.width = myWidth +25+ "px"; // myWidth - 10+"px"; 
	   		reSizeCoLumnWithGridWidth(quoteHistoryGrid,myWidth+68); 
   		}
  	  }
  	  catch(ex){ }
  	  
  	  popupWindow1.bringToBottom();
 	  popupWindow2.bringToTop();
 	  popupWindow3.bringToBottom();
 	  popupWindow4.bringToBottom();
}

function minQuoteHistory() {
	popupWindow2.setPosition(0, dhxLayout1.cells("a").getHeight()+10);
	if (_isIE)
		popupWindow2.setDimension( dhxLayout1.cells("c").getWidth(), dhxLayout1.cells("c").getHeight()-5);
	else
		popupWindow2.setDimension( dhxLayout1.cells("c").getWidth(), dhxLayout1.cells("c").getHeight()-6);
		
	  try
	  {
	  	var quoteHistoryId=quoteHistoryGrid.entBox.id;
   		var quoteHistoryGriDiv = $(quoteHistoryId);
   		if (_isIE)
   			quoteHistoryGriDiv.style.height = dhxLayout1.cells("c").getHeight()-30 + "px";
   		else
   			quoteHistoryGriDiv.style.height = dhxLayout1.cells("c").getHeight()-30 + "px";
   		quoteHistoryGrid.setSizes();  
	  }
	  catch(ex) { }
      
 //     document.getElementById('resultsMaskTable1').width =  dhxLayout1.cells("a").getWidth()-2 + "px";
 //     $('mrBean').style.width = dhxLayout1.cells("a").getWidth()-2 + "px";
        
	  try
  	  {
  	  	if (_isIE) {
	   		quoteHistoryGriDiv.style.width = dhxLayout1.cells("a").getWidth()-2 + "px";
	   		reSizeCoLumnWithGridWidth(quoteHistoryGrid,dhxLayout1.cells("a").getWidth()+20); 
   		}
   		else {
   			quoteHistoryGriDiv.style.width = dhxLayout1.cells("a").getWidth()-2 + "px";
	   		reSizeCoLumnWithGridWidth(quoteHistoryGrid,dhxLayout1.cells("a").getWidth()+43); 
   		}
  	  }
  	  catch(ex) { }
}

function maxPOHistory() {
	if (_isIE)
	  	popupWindow3.setDimension(myWidth, dhxLayout1.cells("b").getHeight()-5);
	else
		popupWindow3.setDimension(myWidth+30, dhxLayout1.cells("b").getHeight()-3);
		
 	  try
	  {
	  	var poId=poGrid.entBox.id;
	    var poGriDiv = $(poId);
	    if (_isIE)
	    	poGriDiv.style.height = dhxLayout1.cells("b").getHeight()-25 + "px";
	    else
	    	poGriDiv.style.height = dhxLayout1.cells("b").getHeight()-28 + "px";
	    poGrid.setSizes(); 
	  }
	  catch(ex){ }
      
	  try
  	  {
  	  	if (_isIE) {
 // 	  		document.getElementById('resultsMaskTable3').width =  myWidth-2 + "px";
 //     		$('poHistoryBean').style.width =  myWidth-2 + "px";
   			poGriDiv.style.width = myWidth-2 + "px"; // myWidth - 10+"px"; 
   			reSizeCoLumnWithGridWidth(poGrid,myWidth+22); 
   		}
   		else {
 //  			document.getElementById('resultsMaskTable3').width =  myWidth+25 + "px";
  //    		$('poHistoryBean').style.width =  myWidth+25 + "px";
   			poGriDiv.style.width = myWidth+25 + "px"; // myWidth - 10+"px"; 
   			reSizeCoLumnWithGridWidth(poGrid,myWidth+65); 
   		}
  	  }
  	  catch(ex){ }
  	  
  	  popupWindow1.bringToBottom();
 	  popupWindow2.bringToBottom();
 	  popupWindow3.bringToTop();
 	  popupWindow4.bringToBottom();
}

function minPOHistory() {

	if (_isIE) {
		popupWindow3.setPosition(dhxLayout1.cells("a").getWidth()+3, 0);
		popupWindow3.setDimension( myWidth - dhxLayout1.cells("a").getWidth()-3, Layout1.cells("a").getHeight());  
	}
	else {
		popupWindow3.setPosition(dhxLayout1.cells("a").getWidth()+18, 0);
		popupWindow3.setDimension( myWidth - dhxLayout1.cells("a").getWidth()+10, Layout1.cells("a").getHeight()-3); 
	}
	
	  try
	  {
	  	var poId=poGrid.entBox.id;
	    var poGriDiv = $(poId);
	    if (_isIE)
	    	poGriDiv.style.height = Layout1.cells("a").getHeight()-25 + "px";
	    else
	    	poGriDiv.style.height = Layout1.cells("a").getHeight()-28 + "px";
	    poGrid.setSizes(); 
	  }
	  catch(ex) { }
   
 //  	  document.getElementById('resultsMaskTable3').width =  myWidth - dhxLayout1.cells("a").getWidth()-10 + "px";
//	  $('poHistoryBean').style.width = myWidth - dhxLayout1.cells("a").getWidth()-10 + "px";
	  try
  	  {
  	  	if (_isIE) {
	   		poGriDiv.style.width = myWidth - dhxLayout1.cells("a").getWidth()-7 + "px";
	   		reSizeCoLumnWithGridWidth(poGrid,myWidth - dhxLayout1.cells("a").getWidth()+18); 
	   	}
	   	else {
	   		poGriDiv.style.width = myWidth - dhxLayout1.cells("a").getWidth()+5 + "px";
	   		reSizeCoLumnWithGridWidth(poGrid,myWidth - dhxLayout1.cells("a").getWidth()+40); 
	   	}
  	  }
  	  catch(ex) { }
}

function maxInventory() {
	  if (_isIE) 
	      popupWindow4.setDimension(myWidth, dhxLayout1.cells("b").getHeight()-5);
	  else
	      popupWindow4.setDimension(myWidth+30, dhxLayout1.cells("b").getHeight()-5);
	    
 	  try
	  {
	  	var inventoryId=inventoryGrid.entBox.id;
	    var inventoryGriDiv = $(inventoryId);
	    if (_isIE) 
	    	inventoryGriDiv.style.height = dhxLayout1.cells("b").getHeight()-25 + "px";
	    else
	    	inventoryGriDiv.style.height = dhxLayout1.cells("b").getHeight()-30 + "px";
	    	
	    inventoryGrid.setSizes(); 
	  }
	  catch(ex){ }
    
	  try
  	  {
 // 	  	document.getElementById('resultsMaskTable3').width =  myWidth-8 + "px";
 //     	$('inventoryBean').style.width =  myWidth-8 + "px";
  	  	if (_isIE) {
	   		inventoryGriDiv.style.width = myWidth-2 + "px";
	   		reSizeCoLumnWithGridWidth(inventoryGrid,myWidth+25);
   		}
   		else {
 //  			document.getElementById('resultsMaskTable3').width =  myWidth + 25 + "px";
 //     		$('inventoryBean').style.width =  myWidth + 25 + "px";
   			inventoryGriDiv.style.width = myWidth + 25 + "px";
   			reSizeCoLumnWithGridWidth(inventoryGrid,myWidth+60);
   		} 
  	  }
  	  catch(ex){ }
  	  
  	  popupWindow1.bringToBottom();
 	  popupWindow2.bringToBottom();
 	  popupWindow3.bringToBottom();
 	  popupWindow4.bringToTop();
}

function minInventory() {
	if (_isIE) {
		popupWindow4.setPosition(dhxLayout1.cells("a").getWidth()+3, Layout1.cells("a").getHeight()+12);
		popupWindow4.setDimension( myWidth - dhxLayout1.cells("a").getWidth()-3, Layout1.cells("b").getHeight()-5);  
	} else {
		popupWindow4.setPosition(dhxLayout1.cells("a").getWidth()+18, Layout1.cells("a").getHeight()+12);
		popupWindow4.setDimension( myWidth - dhxLayout1.cells("a").getWidth()+10, Layout1.cells("b").getHeight()-7); 
	}
	
	  try
	  {
	  	var inventoryId=inventoryGrid.entBox.id;
   		var inventoryGriDiv = $(inventoryId);
   		if (_isIE)
	    	inventoryGriDiv.style.height = Layout1.cells("b").getHeight()-30 + "px";
	    else
	    	inventoryGriDiv.style.height = Layout1.cells("b").getHeight()-32 + "px";
	    inventoryGrid.setSizes(); 
	  }
	  catch(ex) { }
   
  //    document.getElementById('resultsMaskTable4').width =  myWidth - dhxLayout1.cells("a").getWidth()-10 + "px";
  //    $('inventoryBean').style.width = myWidth - dhxLayout1.cells("a").getWidth()-10 + "px";
   
	  try
  	  {
  	  	if (_isIE) {
	   		inventoryGriDiv.style.width = myWidth - dhxLayout1.cells("a").getWidth()-8 + "px";
	   		reSizeCoLumnWithGridWidth(inventoryGrid,myWidth - dhxLayout1.cells("a").getWidth()+15); 
   		}
   		else {
   			inventoryGriDiv.style.width = myWidth - dhxLayout1.cells("a").getWidth()+5 + "px";
   			reSizeCoLumnWithGridWidth(inventoryGrid,myWidth - dhxLayout1.cells("a").getWidth()+40); 
   		}
  	  }
  	  catch(ex) { }
}

function resizeGrids() {
	setTimeout('setWindowSizes();',0);
	setTimeout('minMRHistory();',0);
	setTimeout('minQuoteHistory();',0);
	setTimeout('minPOHistory();',0);
	setTimeout('minInventory();',0);
}

function closeAllchildren()
{
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren(); 
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
		
	children = new Array(); 
}  

function createMRHistoryXSL() {
	$('uAction').value = 'createMRHistoryExcel';
	document.genericForm.target='_QuickItemViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickItemViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}

function createPOHistoryXSL() {
	$('uAction').value = 'createPOHistoryExcel';
	document.genericForm.target='_QuickItemViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickItemViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}

function createPriceQuoteHistoryXSL() {
	$('uAction').value = 'createPriceQuoteHistoryExcel';
	document.genericForm.target='_QuickItemViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickItemViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}

function createInventoryXSL() {
	$('uAction').value = 'createInventoryExcel';
	document.genericForm.target='_QuickItemViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickItemViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}