var children = new Array(); 

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
		doQuoteInitGrid();
	}
	else {
		document.getElementById("quoteHistoryBean").style["display"] = "none";
	}

	var invoiceTotalLines = document.getElementById("invoiceTotalLines").value;
	if (invoiceTotalLines > 0) {
		// make result area visible if data exist
		document.getElementById("InvoiceSearchBean").style["display"] = "";
		// build the grid for display
		doinvoiceInitGrid();
	}
	else {
		document.getElementById("InvoiceSearchBean").style["display"] = "none";
	}
	
	var contactTotalLines = document.getElementById("contactTotalLines").value;
	if (contactTotalLines > 0) {
		// make result area visible if data exist
		document.getElementById("contactBean").style["display"] = "";
		// build the grid for display
		docontactInitGrid();
	}
	else {
		document.getElementById("contactBean").style["display"] = "none";
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
}

function setMrHistoryRowColor() {
	
	var mrHistoryGridSize = mrHistoryGrid.getRowsNum();

	for (var p = 1; p < (mrHistoryGridSize+1) ; p ++)
	  	  colorMrHistoryRedRow(p);

}

function colorMrHistoryRedRow(rowId) {
	var quantity = mrHistoryGrid.cellById(rowId, mrHistoryGrid.getColIndexById("quantity")).getValue();
	var quantityShipped = mrHistoryGrid.cellById(rowId, mrHistoryGrid.getColIndexById("quantityShipped")).getValue();
	var salesOrder = mrHistoryGrid.cellById(rowId, mrHistoryGrid.getColIndexById("salesOrder")).getValue();
	
	if (quantity == quantityShipped || (salesOrder != null && salesOrder.length > 0)) {
			mrHistoryGrid.haasSetColSpanStyle(rowId, 0, 12, "color: #8B0000;");
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

function doinvoiceInitGrid() {
	invoiceGrid = new dhtmlXGridObject('InvoiceSearchBean');

	initGridWithConfig(invoiceGrid, invoiceConfig, false, false, false);
	
	if (typeof (invoiceJsonMainData) != 'undefined') {
		invoiceGrid.parse(invoiceJsonMainData, "json");
	}
	
//	invoiceGrid.attachEvent("onRowSelect", invoiceSelectRow);
	invoiceGrid.attachEvent("onRightClick", invoiceRightclick);
}

function docontactInitGrid() {
	contactGrid = new dhtmlXGridObject('contactBean');

	initGridWithConfig(contactGrid, contactConfig, false, false, false);
	if (typeof (contactJsonMainData) != 'undefined') {
		contactGrid.parse(contactJsonMainData, "json");
	}
	
//	contactGrid.attachEvent("onRowSelect", contactSelectRow);
	contactGrid.attachEvent("onRightClick", contactRightclick);
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

function showMRSupplierItemNote() {
	var itemId = mrHistoryGrid.cellById(mrHistoryGrid.getSelectedRowId(), mrHistoryGrid.getColIndexById("itemId")).getValue();
	var opsEntityId = mrHistoryGrid.cellById(mrHistoryGrid.getSelectedRowId(), mrHistoryGrid.getColIndexById("opsEntityId")).getValue();
	
	if(itemId != null && itemId.trim().length >= 0) {
		loc = "/tcmIS/supply/showsupplieritemnotes.do?uAction=search&searchMode=is&searchField=itemId&searchArgument=" + itemId;
		loc = loc + "&itemId=" + itemId;
	//	loc = loc + "&itemDesc=" + encodeURIComponent(parent.$v('itemDesc'));
	    loc = loc + "&opsEntityId=" + opsEntityId;
	
		openWinGeneric(loc, "showSupplierItemNote", "1024", "600", "yes", "50", "50");
	}
}

function showQuoteSupplierItemNote() {
	var itemId = quoteHistoryGrid.cellById(quoteHistoryGrid.getSelectedRowId(), quoteHistoryGrid.getColIndexById("itemId")).getValue();
	var opsEntityId = quoteHistoryGrid.cellById(quoteHistoryGrid.getSelectedRowId(), quoteHistoryGrid.getColIndexById("opsEntityId")).getValue();
	
	if(itemId != null && itemId.trim().length >= 0) {
		loc = "/tcmIS/supply/showsupplieritemnotes.do?uAction=search&searchMode=is&searchField=itemId&searchArgument=" + itemId;
		loc = loc + "&itemId=" + itemId;
	//	loc = loc + "&itemDesc=" + encodeURIComponent(parent.$v('itemDesc'));
	    loc = loc + "&opsEntityId=" + opsEntityId;
	
		openWinGeneric(loc, "showSupplierItemNote", "1024", "600", "yes", "50", "50");
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

function invoiceRightclick(rowId, cellInd) {
	invoiceGrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('invoiceMenu');
}

function printInvoice() {
	var invoice = invoiceGrid.cellById(invoiceGrid.getSelectedRowId(), invoiceGrid.getColIndexById("invoice")).getValue();
	
	loc = "/HaasReports/common/loadingfile.jsp";
	openWinGeneric(loc, "printDeliveryDocument", "800", "600", "yes", "50", "50", "20", "20", "no");

	document.genericForm.action = "/HaasReports/report/printinvoice.do?personnelId=" + $v('personnelId')+"&InvoiceSearchBean[0].selected=true&&InvoiceSearchBean[0].invoice="+invoice;
	document.genericForm.target = "printDeliveryDocument";
	
// when submit again needs to clean up form since it didn't refresh the page.			
//	invoiceGrid.clearParentSubmitInput();
//	invoiceGrid.parentFormOnSubmit(); // prepare grid for data
	
	var a = window.setTimeout("document.genericForm.submit();", 1000);
}

function newContact() {

  var loc = "/tcmIS/distribution/newcustomercontact.do?fromQuickCustomerView=Yes&&customerId=" + parent.$v('customerId') + "&billToCompanyId=" + parent.$v('billToCompanyId');
  openWinGeneric(loc, "newContact", "700", "250", "yes", "50", "50");
}

function addContact(contact) {

   if(contactGrid == null) {
   		$("contactBean").style["display"] = "";
     	docontactInitGrid(); 
   }  
   
   var ind = contactGrid.getRowsNum();
   var rowid = ind*1+1;
   		
   var thisrow = contactGrid.addRow(rowid,"",ind);

   contactGrid.cells(rowid,contactGrid.getColIndexById("permission")).setValue("N");
   contactGrid.cells(rowid,contactGrid.getColIndexById("fullName")).setValue(contact.fullName);
   contactGrid.cells(rowid,contactGrid.getColIndexById("nickName")).setValue(contact.nickName);
   contactGrid.cells(rowid,contactGrid.getColIndexById("contactType")).setValue(contact.contactType);
   contactGrid.cells(rowid,contactGrid.getColIndexById("phone")).setValue(contact.phone);
   contactGrid.cells(rowid,contactGrid.getColIndexById("altPhone")).setValue(contact.altPhone);
   contactGrid.cells(rowid,contactGrid.getColIndexById("fax")).setValue(contact.fax);
   
   var emailDisplay = '<a href="mailto:'+contact.email+'">'+contact.email+'</a>';

   contactGrid.cells(rowid,contactGrid.getColIndexById("emailDisplay")).setValue(emailDisplay);
   
   contactGrid.cells(rowid,contactGrid.getColIndexById("email")).setValue(contact.email);
   contactGrid.cells(rowid,contactGrid.getColIndexById("otherJobFunctions")).setValue(contact.other);
   if(contact.status == 'ACTIVE')
   		contactGrid.cells(rowid,contactGrid.getColIndexById("status")).setValue(true);
   else
   		contactGrid.cells(rowid,contactGrid.getColIndexById("status")).setValue(false);
   
   if(contact.defaultContact == "Y")
   		contactGrid.cells(rowid,contactGrid.getColIndexById("defaultContact")).setValue(true);
   else
   		contactGrid.cells(rowid,contactGrid.getColIndexById("defaultContact")).setValue(false);
/*   
   if(contact.status == 'ACTIVE') {
   		$("status"+rowid).checked = true; 
		hchstatusA["status"+rowid] = true;
   }
   if(contact.defaultContact == "Y") {
	   	$("defaultContact"+rowid).checked = true; 
		hchstatusA["defaultContact"+rowid] = true;
   }
*/   
   contactGrid.cells(rowid,contactGrid.getColIndexById("contactPersonnelId")).setValue(contact.contactPersonnelId);
   contactGrid.cells(rowid,contactGrid.getColIndexById("customerId")).setValue(contact.customerId);
   
   contactGrid.selectRow(contactGrid.getRowIndex(rowid),null,false,false);

}


function contactRightclick(rowId, cellInd) {
	contactGrid.selectRowById(rowId, null, false, false);
	
	var email = contactGrid.cellById(rowId, contactGrid.getColIndexById("email")).getValue();
	if (email != null && email.length > 0)
		toggleContextMenu('contactMenu');
	else 
		toggleContextMenu('contactEmptyMenu');
}

function emailContact() {
	var email = contactGrid.cellById(contactGrid.getSelectedRowId(), contactGrid.getColIndexById("email")).getValue();
	location.href = "mailto:"+email;
}

function showCustomerDetails() {
	var tabId = "customerDetails"+parent.$v("customerId")+"";
    var loc = "/tcmIS/distribution/customerupdate.do?customerId="+parent.$v("customerId")+"&opsEntityId="+$v("opsEntityId");	
  	try
	{
		parent.parent.openIFrame(tabId,loc,""+messagesData.customerdetails+" "+parent.$v("customerId")+"","","N");
    }
	catch (ex){
		children[children.length] = openWinGeneric(loc,"customerDetails"+parent.$v("customerId"),"900","600","yes","80","80","yes");	
	}

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
  popupWindow3.setText(messagesData.invoice +'&nbsp;' + '<a href="#" onclick="createInvoiceXSL()">'+messagesData.createexcel+'</a>');
  popupWindow3.attachObject("resultFrameDiv3");
  
  popupWindow3.button("close").hide();
  popupWindow3.button("park").hide();
  popupWindow3._allowResizeGlobal = false;
  popupWindow3._allowMaxMin = true;
  
  popupWindow3.attachEvent("onMaximize",function(win){
 		maxInvoice();
  }); 
  
  popupWindow3.attachEvent("onMinimize",function(win){
  		minInvoice();
  }); 
  
  popupWindow4 = dhxLayout1.dhxWins.createWindow("popupWindow4", 0, 0, 400, 400);
  var contactText = messagesData.contact +'&nbsp;' + '<a href="#" onclick="createContactXSL()">'+messagesData.createexcel+'</a>' 
  				+ messagesData.blank + '<a href="#" onclick="newContact()">'+messagesData.newcontact+'</a>';
  popupWindow4.setText(contactText);
  popupWindow4.attachObject("resultFrameDiv4");
  
  popupWindow4.button("close").hide();
  popupWindow4.button("park").hide();
  popupWindow4._allowResizeGlobal = false;
  popupWindow4._allowMaxMin = true;
  
  popupWindow4.attachEvent("onMaximize",function(win){
 		maxContact();
  }); 
  
  popupWindow4.attachEvent("onMinimize",function(win){
  		minContact();
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
	   		reSizeCoLumnWithGridWidth(quoteHistoryGrid,dhxLayout1.cells("a").getWidth()+42); 
   		}
  	  }
  	  catch(ex) { }
}

function maxInvoice() {
	if (_isIE)
	  	popupWindow3.setDimension(myWidth, dhxLayout1.cells("b").getHeight()-5);
	else
		popupWindow3.setDimension(myWidth+30, dhxLayout1.cells("b").getHeight()-3);
		
 	  try
	  {
	  	var invoiceId=invoiceGrid.entBox.id;
   		var invoiceGriDiv = $(invoiceId);
	    if (_isIE)
	    	invoiceGriDiv.style.height = dhxLayout1.cells("b").getHeight()-25 + "px";
	    else
	    	invoiceGriDiv.style.height = dhxLayout1.cells("b").getHeight()-28 + "px";
	    invoiceGrid.setSizes(); 
	  }
	  catch(ex){ }
      
	  try
  	  {
  	  	if (_isIE) {
   			invoiceGriDiv.style.width = myWidth-2 + "px"; // myWidth - 10+"px"; 
   			reSizeCoLumnWithGridWidth(invoiceGrid,myWidth+22); 
   		}
   		else {
   			invoiceGriDiv.style.width = myWidth+25 + "px"; // myWidth - 10+"px"; 
   			reSizeCoLumnWithGridWidth(invoiceGrid,myWidth+65); 
   		}
  	  }
  	  catch(ex){ }
  	  
  	  popupWindow1.bringToBottom();
 	  popupWindow2.bringToBottom();
 	  popupWindow3.bringToTop();
 	  popupWindow4.bringToBottom();
}

function minInvoice() {

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
	  	var invoiceId=invoiceGrid.entBox.id;
   		var invoiceGriDiv = $(invoiceId);
	    if (_isIE)
	    	invoiceGriDiv.style.height = Layout1.cells("a").getHeight()-25 + "px";
	    else
	    	invoiceGriDiv.style.height = Layout1.cells("a").getHeight()-28 + "px";
	    invoiceGrid.setSizes(); 
	  }
	  catch(ex) { }
   
	  try
  	  {
  	  	if (_isIE) {
	   		invoiceGriDiv.style.width = myWidth - dhxLayout1.cells("a").getWidth()-7 + "px";
	   		reSizeCoLumnWithGridWidth(invoiceGrid,myWidth - dhxLayout1.cells("a").getWidth()+18); 
	   	}
	   	else {
	   		invoiceGriDiv.style.width = myWidth - dhxLayout1.cells("a").getWidth()+5 + "px";
	   		reSizeCoLumnWithGridWidth(invoiceGrid,myWidth - dhxLayout1.cells("a").getWidth()+42); 
	   	}
  	  }
  	  catch(ex) { }
}

function maxContact() {
	  if (_isIE) 
	      popupWindow4.setDimension(myWidth, dhxLayout1.cells("b").getHeight()-5);
	  else
	      popupWindow4.setDimension(myWidth+30, dhxLayout1.cells("b").getHeight()-5);
	    
 	  try
	  {
	  	var contactId=contactGrid.entBox.id;
   		var contactGriDiv = $(contactId);
	    if (_isIE) 
	    	contactGriDiv.style.height = dhxLayout1.cells("b").getHeight()-25 + "px";
	    else
	    	contactGriDiv.style.height = dhxLayout1.cells("b").getHeight()-30 + "px";
	    	
	    contactGrid.setSizes(); 
	  }
	  catch(ex){ }
    
	  try
  	  {
  	  	if (_isIE) {
	   		contactGriDiv.style.width = myWidth-2 + "px";
	   		reSizeCoLumnWithGridWidth(contactGrid,myWidth+25);
   		}
   		else {
   			contactGriDiv.style.width = myWidth + 25 + "px";
   			reSizeCoLumnWithGridWidth(contactGrid,myWidth+60);
   		} 
  	  }
  	  catch(ex){ }
  	  
  	  popupWindow1.bringToBottom();
 	  popupWindow2.bringToBottom();
 	  popupWindow3.bringToBottom();
 	  popupWindow4.bringToTop();
}

function minContact() {
	if (_isIE) {
		popupWindow4.setPosition(dhxLayout1.cells("a").getWidth()+3, Layout1.cells("a").getHeight()+12);
		popupWindow4.setDimension( myWidth - dhxLayout1.cells("a").getWidth()-3, Layout1.cells("b").getHeight()-5);  
	} else {
		popupWindow4.setPosition(dhxLayout1.cells("a").getWidth()+18, Layout1.cells("a").getHeight()+12);
		popupWindow4.setDimension( myWidth - dhxLayout1.cells("a").getWidth()+10, Layout1.cells("b").getHeight()-7); 
	}
	
	  try
	  {
	  	var contactId=contactGrid.entBox.id;
   		var contactGriDiv = $(contactId);
   		if (_isIE)
	    	contactGriDiv.style.height = Layout1.cells("b").getHeight()-30 + "px";
	    else
	    	contactGriDiv.style.height = Layout1.cells("b").getHeight()-32 + "px";
	    contactGrid.setSizes(); 
	  }
	  catch(ex) { }
   
	  try
  	  {
  	  	if (_isIE) {
	   		contactGriDiv.style.width = myWidth - dhxLayout1.cells("a").getWidth()-8 + "px";
	   		reSizeCoLumnWithGridWidth(contactGrid,myWidth - dhxLayout1.cells("a").getWidth()+15); 
   		}
   		else {
   			contactGriDiv.style.width = myWidth - dhxLayout1.cells("a").getWidth()+5 + "px";
   			reSizeCoLumnWithGridWidth(contactGrid,myWidth - dhxLayout1.cells("a").getWidth()+45); 
   		}
  	  }
  	  catch(ex) { }
}



function resizeGrids() {
	setTimeout('setWindowSizes();',0);
	setTimeout('minMRHistory();',0);
	setTimeout('minQuoteHistory();',0);
	setTimeout('minInvoice();',0);
	setTimeout('minContact();',0);
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
	document.genericForm.target='_QuickCustomerViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickCustomerViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}

function createInvoiceXSL() {
	$('uAction').value = 'createInvoiceExcel';
	document.genericForm.target='_QuickCustomerViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickCustomerViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}

function createPriceQuoteHistoryXSL() {
	$('uAction').value = 'createPriceQuoteHistoryExcel';
	document.genericForm.target='_QuickCustomerViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickCustomerViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}

function createContactXSL() {
	$('uAction').value = 'createContactExcel';
	document.genericForm.target='_QuickCustomerViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickCustomerViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}
