var resizeGridWithWindow = true;
var beanGrid;
//Global variable : used to track the selected row
var selectedRowId = null;

var dhxWins = null;
//the default radian carrier, this is the only active carrier for now
var defaultFreightForwarder = "Intelligent Audit"

function myOnLoad() {
	//close transit window
	parent.closeTransitWin();
	var totalLines = $("totalLines").value;
	if (totalLines > 0) {
		$('freightInvoiceStageBean').style["display"]="";
		initGridWithGlobal(gridConfig);
		
		if (showUpdateLinks) {
			parent.$("updateResultLink").style["display"] = "";
		}else {
			parent.$("updateResultLink").style["display"] = "none";
		}
		if (reprocessPermission) {
			parent.$("reprocessInvoiceLink").style["display"] = "";
		}

	}else {
		  $("freightInvoiceStageBean").style["display"] = "none";
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
	
}

function submitUpdate() {
  if (validateForm()) {
	  parent.showTransitWin();
	  
	  $("uAction").value = 'update';
	  
	  beanGrid.parentFormOnSubmit();
	  document.genericForm.submit();
  }else {
	  return false;
  }
}

//repocess the invoice lines start from here
function doReprocessInvoiceLines() {
	//validate the changes and alert user for update, if there is any changes on the result frame
	if(validateChanges() && 
			!confirm(messagesData.unSavedChanges)) {
			return false;
	}
	
	showTransitWin();
}

//repocess the invoice lines by selected Load ID, Carrier and Currency
function submitReprocessInvoiceLines() {
	var loadId = document.getElementById("loadId");
	var radianCarrier = document.getElementById("radianCarrier");
	
	$("uAction").value = 'reprocess';
	$("reprocessLoadId").value = loadId.options[loadId.selectedIndex].text;
	//take currency ID from the first row by default as it's going to be same for all
	$("reprocessCurrencyId").value = cellValue(1,"currencyId");
	$("reprocessCarrier").value = radianCarrier.options[radianCarrier.selectedIndex].text;
	transitWin.setModal(false);
	transitWin.hide();
	parent.showTransitWin();
	beanGrid.parentFormOnSubmit();
	document.genericForm.submit();
}

//close the reprocess window
function doReprocessCancel() {
	var showLoadIdDiv = document.getElementById("loadId");
	var radianCarrier = document.getElementById("radianCarrier");
	transitWin.setModal(false);
	transitWin.hide();
}

function validateForm(){
	var hasSelectedRow = false;	
	var errorMsg = "";  // for future use, no values to validate for now
	
	for(var i = 1;i <= beanGrid.getRowsNum();i++){
		if(cellValue(i,"ok")+'' == 'true') {		
			hasSelectedRow = true;
		}
	}

	if (errorMsg.length > 1) {
		alert(errorMsg);
		return false;
	}else if (!hasSelectedRow) {
		alert(messagesData.pleaseSelectRowForUpdate);
		return false;
	}else {
		return true;
	}
}

function limitCommentLength(rowId){
	var maxLength = 100; // characters
	var comments = cellValue(rowId,"comments")
	
	if (comments.length > maxLength) {
		setCellValue(rowId, "comments", comments.substr(0, maxLength));
		
		alert(messagesData.maxlength.replace("{0}", messagesData.comments).replace("{1}", maxLength));
	}
}

//display load id and carrier in popup window
function showTransitWin() {
	
	var reprocessDailogWin = document.getElementById("reprocessDailogWin");
	var loadId = document.getElementById("loadId");
	loadId.selectedIndex = 0;
	var radianCarrier = document.getElementById("radianCarrier");
	//select default freight forwarder
	radianCarrier.value = defaultFreightForwarder;	
	reprocessDailogWin.style.display="";
	initializeDhxWins();
	if (!dhxWins.window("reprocessInvoiceLines")) {
		// create window first time
		transitWin = dhxWins.createWindow("reprocessInvoiceLines",0,0,400,200);
		transitWin.setText('');
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking
		transitWin.attachObject("reprocessDailogWin");
		transitWin.setPosition(328, 131);
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}
	else
	{
		// just show
		transitWin.setModal(true);
		dhxWins.window("reprocessInvoiceLines").show();
	}
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
		if (dhxWins.window("reprocessDailogWin")) {
			dhxWins.window("reprocessDailogWin").setModal(false);
			dhxWins.window("reprocessDailogWin").hide();
		}
	}
}

function validateChanges() {
	
	for(var i = 1;i <= beanGrid.getRowsNum();i++) {
		//first level of validation to find any row is selected
		if(cellValue(i,"ok")+'' == 'true') {
			//second level of validation to validate any changes occurs
			if(!compareValue(i,"invoiceNumber") ||	!compareValue(i,"orderNumber") ||
					!compareValue(i,"glCode") || !compareValue(i,"orderType") || 
					!compareValue(i,"status") || !compareValue(i,"comments") 
					) {
				return true;
			}
		}
	}
	return false;
}

function compareValue(rowId,columnName) {
	var columnId = beanGrid.getColIndexById(columnName)
	
	if(jsonMainData.rows[rowId - 1].data[columnId] == 
		cellValue(rowId,columnName)) {
		return true;
	}
	return false;
}

