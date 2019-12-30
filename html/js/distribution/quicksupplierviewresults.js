var children = new Array(); 

function resultOnLoad() {
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

	var priceListTotalLines = document.getElementById("priceListTotalLines").value;
	if (priceListTotalLines > 0) {
		// make result area visible if data exist
		document.getElementById("priceListBean").style["display"] = "";
		doPriceListInitGrid();
	}
	else {
		document.getElementById("priceListBean").style["display"] = "none";
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
			poGrid.haasSetColSpanStyle(rowId, 0, 13, "color: #8B0000;");
	}
}

function doPriceListInitGrid() {
	priceListGrid = new dhtmlXGridObject('priceListBean');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
	// Set submitDefault = false here because I copy the values to hidden inputs 
	initGridWithConfig(priceListGrid, priceListConfig, false, false, false);
	if (typeof (priceListJsonMainData) != 'undefined') {
		priceListGrid.parse(priceListJsonMainData, "json");
	}

//	priceListGrid.attachEvent("onRowSelect", priceListSelectRow);
	priceListGrid.attachEvent("onRightClick", priceListRightclick);
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

function showPOSupplierItemNote() {
	var itemId = poGrid.cellById(poGrid.getSelectedRowId(), poGrid.getColIndexById("itemId")).getValue();
	
	if(itemId != null && itemId.trim().length >= 0) {
		loc = "/tcmIS/supply/showsupplieritemnotes.do?uAction=search&searchMode=is&searchField=itemId&searchArgument=" + itemId;
		loc = loc + "&itemId=" + itemId;
		loc = loc + "&supplier=" + parent.$v("supplier");
		loc = loc + "&supplierName=" + parent.$v("supplierName");
	//	loc = loc + "&itemDesc=" + encodeURIComponent(parent.$v('itemDesc'));
	    loc = loc + "&opsEntityId=" + parent.$v("opsEntityId");
	
		openWinGeneric(loc, "showSupplierItemNote", "1024", "600", "yes", "50", "50");
	}
}

function showExpediteNotes() {
	var radianPo = poGrid.cellById(poGrid.getSelectedRowId(), poGrid.getColIndexById("hRadianPo")).getValue();
	var poLine = poGrid.cellById(poGrid.getSelectedRowId(), poGrid.getColIndexById("poLine")).getValue();
	
	if(radianPo != null && radianPo.trim().length >= 0) {
		loc = "/tcmIS/supply/newpoexpediting.do?action=searchnewpoexpedite&radianPo=" + radianPo;
		loc = loc + "&poLine=" + poLine;
	
		children[children.length] = openWinGeneric(loc, "showExpediteNote", "1024", "600", "yes", "50", "50");
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

function showPLSupplierItemNote() {
	var itemId = priceListGrid.cellById(priceListGrid.getSelectedRowId(), priceListGrid.getColIndexById("itemId")).getValue();
	
	if(itemId != null && itemId.trim().length >= 0) {
		loc = "/tcmIS/supply/showsupplieritemnotes.do?uAction=search&searchMode=is&searchField=itemId&searchArgument=" + itemId;
		loc = loc + "&itemId=" + itemId;
		loc = loc + "&supplier=" + parent.$v("supplier");
		loc = loc + "&supplierName=" + parent.$v("supplierName");
	//	loc = loc + "&itemDesc=" + encodeURIComponent(parent.$v('itemDesc'));
	    loc = loc + "&opsEntityId=" + parent.$v("opsEntityId");
	
		children[children.length] = openWinGeneric(loc, "showSupplierItemNote", "1024", "600", "yes", "50", "50");
	}
}

function editSourcingInfo() {
	var itemId = priceListGrid.cellById(priceListGrid.getSelectedRowId(), priceListGrid.getColIndexById("itemId")).getValue();
	var opsEntityId = priceListGrid.cellById(priceListGrid.getSelectedRowId(), priceListGrid.getColIndexById("opsEntityId")).getValue();
	var hub = priceListGrid.cellById(priceListGrid.getSelectedRowId(), priceListGrid.getColIndexById("inventoryGroupHub")).getValue();
	var inventoryGroup = priceListGrid.cellById(priceListGrid.getSelectedRowId(), priceListGrid.getColIndexById("inventoryGroup")).getValue();
	
//	loc = "/tcmIS/distribution/showsupplierpl.do?itemId=" + parent.$v('itemId');
	loc = "/tcmIS/distribution/editpricelist.do?uAction=search&searchField=itemId|number&searchMode=is&searchArgument=" + itemId;
	loc = loc + "&supplier=" + parent.$v("supplier");
	loc = loc + "&supplierName=" + parent.$v("supplierName");
	loc = loc + "&inventoryGroup=" + inventoryGroup;
	loc = loc + "&hub=" + hub;
    loc = loc + "&opsEntityId=" + opsEntityId;

	openWinGeneric(loc, "showSourcingInfo", "1024", "600", "yes", "50", "50");
}



function priceListRightclick(rowId, cellInd) {
	priceListGrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('priceListMenu');
}

function invoiceRightclick(rowId, cellInd) {
	invoiceGrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('invoiceMenu');
}

function showAccountsPayableforPO() {
	var radianPo = invoiceGrid.cellById(invoiceGrid.getSelectedRowId(), invoiceGrid.getColIndexById("radianPo")).getValue();
	
	loc = "/tcmIS/Invoice/AccountsPayable?Action=searchlike&subUserAction=po&po="+radianPo;
	openWinGeneric(loc, "showAccountsPayable"+radianPo, "800", "600", "yes", "50", "50", "20", "20", "no");
}

function showAccountsPayableforInvoice() {
	var voucherId = invoiceGrid.cellById(invoiceGrid.getSelectedRowId(), invoiceGrid.getColIndexById("voucherId")).getValue();
	var radianPo = invoiceGrid.cellById(invoiceGrid.getSelectedRowId(), invoiceGrid.getColIndexById("radianPo")).getValue();
	
	loc = "/tcmIS/Invoice/AccountsPayable?Action=searchlike&subUserAction=po&po="+radianPo+"&selectedInvoiceId="+voucherId;
	openWinGeneric(loc, "showAccountsPayable"+radianPo, "800", "600", "yes", "50", "50", "20", "20", "no");
}

/*
function newContact() {
  parent.showTransitWin(messagesData.suppliercontact);
  var loc = "/tcmIS/distribution/newcustomercontact.do?fromQuickCustomerView=Yes&&customerId=" + parent.$v('customerId') + "&billToCompanyId=" + parent.$v('billToCompanyId');
  openWinGeneric(loc, "newContact", "700", "250", "yes", "50", "50");
}
*/
function addContact(contact) {
   parent.closeTransitWin();
   
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
   contactGrid.cells(rowid,contactGrid.getColIndexById("status")).setValue(contact.status);
   contactGrid.cells(rowid,contactGrid.getColIndexById("defaultContact")).setValue(contact.defaultContact);
   
   if(contact.status == 'ACTIVE') {
	   	$("status"+rowid).checked = true; 
		hchstatusA["status"+rowid] = true;
   }
   if(contact.defaultContact == "Y") {
	   	$("defaultContact"+rowid).checked = true; 
		hchstatusA["defaultContact"+rowid] = true;
   }
   
   contactGrid.cells(rowid,contactGrid.getColIndexById("contactPersonnelId")).setValue(contact.contactPersonnelId);
   contactGrid.cells(rowid,contactGrid.getColIndexById("customerId")).setValue(contact.customerId);
   
   contactGrid.selectRow(contactGrid.getRowIndex(rowid),null,false,false);
}

function addNewSupplierContact()
{
   parent.showTransitWin(messagesData.suppliercontact);
   var supplier= parent.$v("supplier");
   var loc = "/tcmIS/supply/newposuppliercontact.do?action=showSupplierContact&fromQuickSupplierView=Y&actionType=new&supplier="+supplier+"&fromSupplierPriceList=";
   openWinGeneric(loc, "newPoSupplierContact", "800", "250", "yes", "50", "50");	
}

function modifyContact(contactId, LastName, FirstName, NickName, contactType, phone, fax, email) {
	parent.closeTransitWin();
	
	contactGrid.cells(contactSelectedRowId,contactGrid.getColIndexById("wholeName")).setValue(LastName+" "+FirstName);
	contactGrid.cells(contactSelectedRowId,contactGrid.getColIndexById("nickname")).setValue(NickName);
	contactGrid.cells(contactSelectedRowId,contactGrid.getColIndexById("contactType")).setValue(contactType);
	contactGrid.cells(contactSelectedRowId,contactGrid.getColIndexById("phone")).setValue(phone);
	contactGrid.cells(contactSelectedRowId,contactGrid.getColIndexById("fax")).setValue(fax);
	var emailDisplay = '<a href="mailto:'+email+'">'+email+'</a>';
	contactGrid.cells(contactSelectedRowId,contactGrid.getColIndexById("emailDisplay")).setValue(emailDisplay);	
	contactGrid.cells(contactSelectedRowId,contactGrid.getColIndexById("email")).setValue(email);
}

function addContact(contactId, LastName, FirstName, NickName, contactType, phone, fax, email) {
	if(contactGrid == null) {
   		$("contactBean").style["display"] = "";
     	docontactInitGrid(); 
    }  
   
    var ind = contactGrid.getRowsNum();
    var rowid = ind*1+1;
   		
    var thisrow = contactGrid.addRow(rowid,"",ind);

    contactGrid.cells(rowid,contactGrid.getColIndexById("permission")).setValue("N");
    contactGrid.cells(rowid,contactGrid.getColIndexById("wholeName")).setValue(LastName+" "+FirstName);
	contactGrid.cells(rowid,contactGrid.getColIndexById("nickname")).setValue(NickName);
	contactGrid.cells(rowid,contactGrid.getColIndexById("contactTypePermission")).setValue("N");
	contactGrid.cells(rowid,contactGrid.getColIndexById("contactType")).setValue(contactType);
	contactGrid.cells(rowid,contactGrid.getColIndexById("phone")).setValue(phone);
	contactGrid.cells(rowid,contactGrid.getColIndexById("fax")).setValue(fax);
	var emailDisplay = '<a href="mailto:'+email+'">'+email+'</a>';
	contactGrid.cells(rowid,contactGrid.getColIndexById("emailDisplay")).setValue(emailDisplay);	
	contactGrid.cells(rowid,contactGrid.getColIndexById("email")).setValue(email);
	contactGrid.cells(rowid,contactGrid.getColIndexById("contactId")).setValue(contactId);
	contactGrid.cells(rowid,contactGrid.getColIndexById("supplier")).setValue(parent.$v("supplier"));
}

var contactSelectedRowId = null;
function contactRightclick(rowId, cellInd) {
	contactGrid.selectRowById(rowId, null, false, false);
	contactSelectedRowId = rowId;
	
	var email = contactGrid.cellById(rowId, contactGrid.getColIndexById("email")).getValue();
	if (email != null && email.length > 0)
		toggleContextMenu('contactMenu');
	else 
		toggleContextMenu('contactEmptyMenu');
}

function editSupplierContact()
{	parent.showTransitWin(messagesData.suppliercontact);
	var supplier = contactGrid.cellById(contactGrid.getSelectedRowId(), contactGrid.getColIndexById("supplier")).getValue();
	var contactId = contactGrid.cellById(contactGrid.getSelectedRowId(), contactGrid.getColIndexById("contactId")).getValue();
	var loc = "/tcmIS/supply/newposuppliercontact.do?action=showSupplierContactData&fromQuickSupplierView=Y&actionType=edit&supplier="+supplier+"&contactId="+contactId;
    openWinGeneric(loc, "newPoSupplierContact", "800", "250", "yes", "50", "50");
}


function emailContact() {
	var email = contactGrid.cellById(contactGrid.getSelectedRowId(), contactGrid.getColIndexById("email")).getValue();
	location.href = "mailto:"+email;
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
  popupWindow1.setText(messagesData.pohistory +'&nbsp;' + '<a href="#" onclick="createSupplierPOHistoryXSL()">'+messagesData.createexcel+'</a>');
  popupWindow1.attachObject("resultFrameDiv1");
  
  popupWindow1.button("close").hide();
  popupWindow1.button("park").hide();
  popupWindow1._allowResizeGlobal = false;  // disable resizing
  popupWindow1._allowMaxMin = true;  // allow Max Min
  
  popupWindow1.attachEvent("onMaximize",function(win){
 		maxPOHistory();
  }); 
  
  popupWindow1.attachEvent("onMinimize",function(win){
  		minPOHistory();
  }); 
  
  popupWindow2 = dhxLayout1.dhxWins.createWindow("popupWindow2", 0, 0, 400, 400);
  popupWindow2.setText(messagesData.supplierpricelist +'&nbsp;' + '<a href="#" onclick="createSupplierPriceListXSL()">'+messagesData.createexcel+'</a>');
  popupWindow2.attachObject("resultFrameDiv2");
  
  popupWindow2.button("close").hide();
  popupWindow2.button("park").hide();
  popupWindow2._allowResizeGlobal = false;
  popupWindow2._allowMaxMin = true;
  
  popupWindow2.attachEvent("onMaximize",function(win){
 		maxPriceList();
  }); 
  
  popupWindow2.attachEvent("onMinimize",function(win){
  		minPriceList();
  }); 
  
  popupWindow3 = dhxLayout1.dhxWins.createWindow("popupWindow3", 0, 0, 400, 400);
  popupWindow3.setText(messagesData.supplierinvoicereport +'&nbsp;' + '<a href="#" onclick="createSupplierInvoiceXSL()">'+messagesData.createexcel+'</a>');
  popupWindow3.attachObject("resultFrameDiv3");
  
  popupWindow3.button("close").hide();
  popupWindow3.button("park").hide();
  popupWindow3._allowResizeGlobal = false;
  popupWindow3._allowMaxMin = true;
  
  popupWindow3.attachEvent("onMaximize",function(win){
 		maxInvoiceReport();
  }); 
  
  popupWindow3.attachEvent("onMinimize",function(win){
  		minInvoiceReport();
  }); 
  
  popupWindow4 = dhxLayout1.dhxWins.createWindow("popupWindow4", 0, 0, 400, 400);
  var contactText = messagesData.suppliercontact +'&nbsp;' + '<a href="#" onclick="createSupplierContactXSL()">'+messagesData.createexcel+'</a>' 
  				+ messagesData.blank + '<a href="#" onclick="addNewSupplierContact()">'+messagesData.newcontact+'</a>';
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

function maxPOHistory() {
	if (_isIE)
	  	popupWindow1.setDimension(myWidth, dhxLayout1.cells("b").getHeight()-5);
	else
		popupWindow1.setDimension(myWidth+30, dhxLayout1.cells("b").getHeight()-3);
		
 	  try
	  {
		   var poId=poGrid.entBox.id;
   		   var poGriDiv = $(poId);
		   if (_isIE)
		   		poGriDiv.style.height = dhxLayout1.cells("b").getHeight()-25 + "px";
		   else
	    		poGriDiv.style.height = dhxLayout1.cells("b").getHeight()-27 + "px";
		   poGrid.setSizes();  
	  }
	  catch(ex){ }
      
        
	  try
  	  {
   			if (_isIE){
      			poGriDiv.style.width = myWidth-2 + "px"; // myWidth - 10+"px"; 
   				reSizeCoLumnWithGridWidth(poGrid,myWidth+25); 
   			}
   			else {
      			poGriDiv.style.width = myWidth+25 + "px"; // myWidth - 10+"px"; 
   				reSizeCoLumnWithGridWidth(poGrid,myWidth+65);
   			} 
  	  }
  	  catch(ex){ }
  	  
  	  popupWindow1.bringToTop();
 	  popupWindow2.bringToBottom();
  	  popupWindow3.bringToBottom();
 	  popupWindow4.bringToBottom();
}

function minPOHistory() {
	popupWindow1.setDimension(dhxLayout1.cells("a").getWidth(), dhxLayout1.cells("a").getHeight()); 
	
	  try
	  {
		   var poId=poGrid.entBox.id;
   		   var poGriDiv = $(poId);
		   poGriDiv.style.height = dhxLayout1.cells("a").getHeight()-25 + "px";
		   poGrid.setSizes();  
	  }
	  catch(ex) { }
      
	  try
  	  {
  	  		if (_isIE) {
		   		poGriDiv.style.width = dhxLayout1.cells("a").getWidth()-2 + "px"; // myWidth - 10+"px"; 
   				reSizeCoLumnWithGridWidth(poGrid,dhxLayout1.cells("a").getWidth()+20); 
	   		}
	   		else {
	   			poGriDiv.style.width = dhxLayout1.cells("a").getWidth()-2 + "px"; // myWidth - 10+"px"; 
   				reSizeCoLumnWithGridWidth(poGrid,dhxLayout1.cells("a").getWidth()+45); 
	   		}
  	  }
  	  catch(ex) { }
}

function maxPriceList() {
	if (_isIE)
	  	popupWindow2.setDimension(myWidth, dhxLayout1.cells("b").getHeight()-5);
	else
		popupWindow2.setDimension(myWidth+30, dhxLayout1.cells("b").getHeight()-3);
		
 	  try
	  {
	  	var priceListId=priceListGrid.entBox.id;
   		var priceListGriDiv = $(priceListId);
	    if (_isIE)	
	    	priceListGriDiv.style.height = dhxLayout1.cells("b").getHeight()-25 + "px";
	    else
	    	priceListGriDiv.style.height = dhxLayout1.cells("b").getHeight()-27 + "px";
	    quoteHistoryGrid.setSizes();  
	  }
	  catch(ex){ }
      
	  try
  	  {
   		if (_isIE) {
	   		priceListGriDiv.style.width = myWidth-2 + "px"; // myWidth - 10+"px"; 
	   		reSizeCoLumnWithGridWidth(priceListGrid,myWidth+23); 
   		}
   		else {
   			priceListGriDiv.style.width = myWidth +25+ "px"; // myWidth - 10+"px"; 
	   		reSizeCoLumnWithGridWidth(priceListGrid,myWidth+68); 
   		}
  	  }
  	  catch(ex){ }
  	  
  	  popupWindow1.bringToBottom();
 	  popupWindow2.bringToTop();
 	  popupWindow3.bringToBottom();
 	  popupWindow4.bringToBottom();
}

function minPriceList() {
	popupWindow2.setPosition(0, dhxLayout1.cells("a").getHeight()+10);
	if (_isIE)
		popupWindow2.setDimension( dhxLayout1.cells("c").getWidth(), dhxLayout1.cells("c").getHeight()-5);
	else
		popupWindow2.setDimension( dhxLayout1.cells("c").getWidth(), dhxLayout1.cells("c").getHeight()-6);
		
	  try
	  {
	  	var priceListId=priceListGrid.entBox.id;
   		var priceListGriDiv = $(priceListId);
   		if (_isIE)
   			priceListGriDiv.style.height = dhxLayout1.cells("c").getHeight()-30 + "px";
   		else
   			priceListGriDiv.style.height = dhxLayout1.cells("c").getHeight()-30 + "px";
   		priceListGrid.setSizes();  
	  }
	  catch(ex) { }
      
	  try
  	  {
  	  	if (_isIE) {
	   		priceListGriDiv.style.width = dhxLayout1.cells("a").getWidth()-2 + "px";
	   		reSizeCoLumnWithGridWidth(priceListGrid,dhxLayout1.cells("a").getWidth()+20); 
   		}
   		else {
   			priceListGriDiv.style.width = dhxLayout1.cells("a").getWidth()-2 + "px";
	   		reSizeCoLumnWithGridWidth(priceListGrid,dhxLayout1.cells("a").getWidth()+46); 
   		}
  	  }
  	  catch(ex) { }
}

function maxInvoiceReport() {
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

function minInvoiceReport() {

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
	   		reSizeCoLumnWithGridWidth(invoiceGrid,myWidth - dhxLayout1.cells("a").getWidth()+48); 
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
   			reSizeCoLumnWithGridWidth(contactGrid,myWidth - dhxLayout1.cells("a").getWidth()+40); 
   		}
  	  }
  	  catch(ex) { }
}



function resizeGrids() {
	setTimeout('setWindowSizes();',0);
	setTimeout('minPOHistory();',0);
	setTimeout('minPriceList();',0);
	setTimeout('minInvoiceReport();',0);
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

function createSupplierPOHistoryXSL() {
	$('uAction').value = 'createSupplierPOHistoryExcel';
	document.genericForm.target='_QuickSupplierViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickSupplierViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}

function createSupplierPriceListXSL() {
	$('uAction').value = 'createSupplierPriceListExcel';
	document.genericForm.target='_QuickSupplierViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickSupplierViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}

function createSupplierInvoiceXSL() {
	$('uAction').value = 'createSupplierInvoiceExcel';
	document.genericForm.target='_QuickSupplierViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickSupplierViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}

function createSupplierContactXSL() {
	$('uAction').value = 'createSupplierContactExcel';
	document.genericForm.target='_QuickSupplierViewExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_QuickSupplierViewExcel', '650', '600', 'yes');
	setTimeout("document.genericForm.submit();", 300);
}
