var children = new Array();
var dhxWins = null;
var myGrid;
var selectedRowId = '';
var inputSize= new Array();
inputSize={"quantityAllocated":5};

var maxInputLength = new Array();
maxInputLength={"quantityAllocated":5};
var totalOrderedPickedMsg = "";
var totalPrice = 0;
var userAction = "";

function closeAllchildren()
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
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

function closeWindow() {
	closeAllchildren();
	goSubmitCancellation();
}

function $(a) {
	return document.getElementById(a);
}

function myOnLoad(action) {
	userAction = action;
	if (action == 'cancelMr' && ($("tcmISError") == null || $("tcmISError").value.length == 0)) {
		parent.parent.closeTabx('pointofsalepickingscreen'+$v("prNumber"));
	}else {
		 var totalLines = $("totalLines").value;
		 if (totalLines > 0) {
			  document.getElementById('pointOfSaleInventoryViewBean').style["display"]="";
			  document.getElementById('resultGridDiv').style["display"]="";
			  initializeGrid();
			  if (action == 'submit') {
				 showTransitWin(); 
				 showReceipt();
				 document.getElementById('updateResultLink').style["display"]="none";
			  }else {
				 document.getElementById('updateResultLink').style["display"]="";
			  }
			  document.getElementById('headerDataSpan').style["display"]="";
		 }else {
			  document.getElementById("pointOfSaleInventoryViewBean").style["display"] = "none";
		 }
		 /*It is important to call this after all the divs have been turned on or off.
		 * This sets all sizes to be a good fit on the screen.*/
		 internalHeightIEOffset = 65;
		 internalHeightFFOffset = 75;       
		 internalWidthIEOffset = 40;
		 internalWidthFFOffset = 20;
		 setResultSize();
	}
}

function validateForm() {
	var result = true;
	return result;
}

function initializeGrid(){
	//initialize grid
	myGrid = new dhtmlXGridObject('pointOfSaleInventoryViewBean');

	initGridWithConfig(myGrid,config,true,true);
	//setting smart rendering
	myGrid.enableSmartRendering(true);
	myGrid._haas_row_span = true;
	myGrid._haas_row_span_map = lineMap;
	myGrid._haas_row_span_class_map = lineMap3;
	myGrid._haas_row_span_cols = [0,1,2,3,4,5,6,7,8];

	if (lineMap2) {
		myGrid._haas_row_span_lvl2 = true;
		myGrid._haas_row_span_lvl2_map = lineMap2;
		myGrid._haas_row_span_lvl2_cols = [9];
	}

	//parse data
	if( typeof( jsonMainData ) != 'undefined' ) {
		myGrid.parse(jsonMainData,"json");
 	}

	myGrid.attachEvent("onRowSelect",selectRow);
	calculatePickedTotal();
}

function selectRow() {
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	if( ee != null ) {
		if( (ee.button != null && ee.button == 2) || ee.which == 3) {
			rightClick = true;
		}
   }

	selectedRowId = rowId0;
	calculatePickedTotal();

} //end of method

function calculatePickedTotal() {
	//this variable keep track of the number of times calling lineMap
	var tmpCount = 0;
	totalPrice = 0;
	//reset message
	totalOrderedPickedMsg = "";
	//the reason i starts with 1 is because table row ID starts with 1 not 0 (zero)
	for(var i = 1;i <= myGrid.getRowsNum();){
		var currentRowId = i;
		var currentTotalPicked = 0;
		for(var j = 1; j <= lineMap[tmpCount]; j++) {
			currentTotalPicked += cellValue(currentRowId,"quantityAllocated")*1;
			currentRowId++;
		}

		var currentTotalOrdered = cellValue(i,"mrQuantity")*1;
		if (userAction == 'submit') {
			currentTotalPicked = currentTotalOrdered;		
		}
		//set total picked value
		myGrid.cells(i,myGrid.getColIndexById("sumOfQuantityPicked")).setValue(currentTotalPicked);
		//calculate total price
		var tmpCatalogPrice = cellValue(tmpCount+1,"catalogPrice");
		if (tmpCatalogPrice != null) {
			if (tmpCatalogPrice.length > 0 ) {
				totalPrice += (tmpCatalogPrice*1) * currentTotalPicked;
			}
		}

		//compare total ordered and total picked
		if (currentTotalOrdered > currentTotalPicked) {
			if (totalOrderedPickedMsg.length > 0) {
				totalOrderedPickedMsg +="\n";
			}
			totalOrderedPickedMsg += messagesData.forLine+" "+cellValue(tmpCount+1,"lineItem")+" : "+(currentTotalOrdered-currentTotalPicked)+" "+messagesData.notPicked;
		}else if (currentTotalOrdered < currentTotalPicked) {
			if (totalOrderedPickedMsg.length > 0) {
				totalOrderedPickedMsg +="\n";
			}
			totalOrderedPickedMsg += messagesData.forLine+" "+cellValue(tmpCount+1,"lineItem")+" : "+messagesData.youPicked+" "+(currentTotalPicked-currentTotalOrdered)+" "+messagesData.moreThanQtyOrdered;
		}
		//increase row count
		i = currentRowId;
		tmpCount += lineMap[tmpCount];
	}

	totalPrice = Math.round(totalPrice*100)/100;
	$("totalPriceSpan").innerHTML = totalPrice;
}

function loadRowSpans()
{
 for(var i=0;i<myGrid.getRowsNum();i++){
   try
   {   
     var rowSpan = lineMap[i];
     if( rowSpan == null || rowSpan == 1 ) continue;
     myGrid.setRowspan(i+1,0,rowSpan*1);
     myGrid.setRowspan(i+1,1,rowSpan*1);
     myGrid.setRowspan(i+1,2,rowSpan*1);
     myGrid.setRowspan(i+1,3,rowSpan*1);
     myGrid.setRowspan(i+1,4,rowSpan*1);
     myGrid.setRowspan(i+1,5,rowSpan*1);
     myGrid.setRowspan(i+1,6,rowSpan*1);
     myGrid.setRowspan(i+1,7,rowSpan*1);
     myGrid.setRowspan(i+1,8,rowSpan*1);
     myGrid.setRowspan(i+1,9,rowSpan*1);
   }
   catch (ex)
   {
     //alert("here 269");
   }
 }
 /*Need to call this only if the grid has rowspans > 1*/
 myGrid._fixAlterCss();
}

function validateSubmitData() {
	calculatePickedTotal();
	if (totalOrderedPickedMsg.length > 0) {
		showMessageDialog(messagesData.warning,totalOrderedPickedMsg);
	}else {
		return true;
	}
}

function validateOrderingLimit() {
	var result = true;
	//audit ordering limit
	if ($v("orderingLimit") != null && $v("orderingLimit").length > 0) {
		if ($v("orderingLimit") != 'Unlimited') {
		 	if (($v("orderingLimit")*1 - totalPrice) < 0) {
				alert(messagesData.mrExceededFinancialLimit);
			 	result = false;
		 	}
		}
	}
	return result;
}

function submitSearchForm() {
	if(validateOrderingLimit()) {
  		if (validateSubmitData()) {
	  		goSubmitData();
  		}
	}
}

function goSubmitData() {
	showTransitWin();
	$("action").value = 'submit';
	//prepare grid for data sending
	myGrid.parentFormOnSubmit();
	document.genericForm.submit();
}

function goSubmitCancellation() {
	showTransitWin();
	$("action").value = 'cancelMr';
	document.genericForm.submit();
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function showReceipt() {
 	var loc = "pointofsale.do?action=showPointOfSaleReceipt&prNumber="+$v("prNumber");
 	children[children.length] = openWinGeneric(loc,"showPointOfSaleReceipt","520","500","yes","50","50","20","20","no");
}

function closeBin(receiptId) {
	showTransitWin();
  	$("action").value = 'closeTap';
	$("receiptId").value = receiptId;
	document.genericForm.submit();
}

function tapItem(itemId,inventoryGroup) {
 	var loc = "tapsource.do?uAction=getTapData&itemId="+itemId+"&inventoryGroup="+inventoryGroup;
 	children[children.length] = openWinGeneric(loc,"tapSource","520","500","yes","50","50","20","20","no");
}

function receiptTapped(receiptId,bin,itemId,inventoryGroup) {
	closeAllchildren();
	showTransitWin();
  	$("action").value = 'tapReceipt';
	$("receiptId").value = receiptId;
	$("bin").value = bin;
	$("itemId").value = itemId;
	$("inventoryGroup").value = inventoryGroup;
	document.genericForm.submit();
}

function showMessageDialog(winTitle,message)
{
	$("messageText").value = message;

	var inputDailogWin = document.getElementById("messageDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showMessageDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 150);
		inputWin.setText(winTitle);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		inputWin.attachObject("messageDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131);
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}
	else
	{
		// just show
		inputWin.setText(winTitle);
		inputWin.setModal(true);
		dhxWins.window("showMessageDialog").show();
	}
}

function closeMessageWin() {
	if (dhxWins != null) {
		if (dhxWins.window("showMessageDialog")) {
  			dhxWins.window("showMessageDialog").setModal(false);
  			dhxWins.window("showMessageDialog").hide();
		}
	}
}

function continueMessageWin() {
	closeMessageWin();
	goSubmitData();
}