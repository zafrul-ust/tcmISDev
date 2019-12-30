var myGrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
var dhxWins = null;

function resultOnLoad()
{
	closeTransitWin();
	totalLines = $("totalLines").value;
	if (totalLines > 0) {
		/*
	  if (showUpdate) {
	  	parent.$("updateSpan").style["display"] = "";
	  }else {
	  	parent.$("updateSpan").style["display"] = "none";  
	  }
	  */
	  $("customerPartManagementPopupDiv").style["display"] = "";
	  $("mainUpdateLinks").style["display"] = "";
	  if (showUpdate) {
	  	  $("updateSpan").style["display"] = "";
	  }else {
		  $("updateSpan").style["display"] = "none";
	  }
	  initializeGrid();
	}else {
	   $("customerPartManagementPopupDiv").style["display"] = "none";
		$("mainUpdateLinks").style["display"] = "none";
	}
	
	//displayGridSearchDuration();
	setResultFrameSize();
}

//grid black
var colorStyle = "background-color: #727272;color: #ffffff";
function setInactiveRowColor(rowId) {
	//alert("setInactiveRowColor");
	var changedIndex = 14; 	//original status column
	var rowIndex = myGrid.getRowIndex(rowId);
	// Check JSON data because grid cell may not have been rendered yet
	if (myGrid._haas_json_data.rows[rowIndex].data[changedIndex] == 'I') {
		// Set each cell's style to the desired CSS style
		myGrid.setCellTextStyle(rowId, 3, colorStyle);
		myGrid.setCellTextStyle(rowId, 4, colorStyle);
		myGrid.setCellTextStyle(rowId, 5, colorStyle);
		myGrid.setCellTextStyle(rowId, 6, colorStyle);
		myGrid.setCellTextStyle(rowId, 7, colorStyle);
	}
}


function initializeGrid(){
	//initialize grid
	myGrid = new dhtmlXGridObject('customerPartManagementPopupDiv');

	initGridWithConfig(myGrid,config,true,true);
	//setting smart rendering
	myGrid.enableSmartRendering(true);
	myGrid._haas_row_span = true;
	myGrid._haas_row_span_map = lineMap;
	myGrid._haas_row_span_class_map = lineMap3;
	myGrid._haas_row_span_cols = [0,1,2];
	myGrid.attachEvent("onAfterHaasRenderRow", setInactiveRowColor);
	//parse data
	if( typeof( jsonMainData ) != 'undefined' ) {
		myGrid.parse(jsonMainData,"json");
 	}
}


function submitUpdate() {
	if (auditData()) {
		showTransitWin();
		//prepare grid for data sending
		myGrid.parentFormOnSubmit();
		$("uAction").value = 'update';
		document.genericForm.submit();
	}
}

function createExcel() {
	$("uAction").value = "createExcel";
	document.genericForm.target='_CustomerPartManagementPopExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_CustomerPartManagementPopExcel','650','600','yes');
	setTimeout("document.genericForm.submit()",300);
}

function auditData() {
	var result = true;
	var atLeastOneRowChecked = false;
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= myGrid.getRowsNum();i++) {
		if (gridCellValue(myGrid,i,myGrid.getColIndexById("ok")) == 'true') {
			atLeastOneRowChecked = true;
			if (myGrid.cells(i,myGrid.getColIndexById("customerPartNo")).getValue().length == 0) {
				alert(messagesData.missingCustomerPart+": "+myGrid.cells(i,myGrid.getColIndexById("itemId")).getValue());
				result = false;
				break;
			}
		}
	}
	if (!atLeastOneRowChecked) {
		alert(messagesData.noRowSelected);
		result = false;
	}

	return result;
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
		dhxWins.window("transitDailogWin").show();
	}
}


