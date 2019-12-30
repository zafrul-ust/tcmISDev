var chargeNumberGrid2Columns;
var chargeNumberGrid3Columns;
var chargeNumberGrid4Columns;
var chargeNumberGrid5Columns;
var inputSize = new Array();
var chargeNumberSelectedRowId = null;
/*Set this to be false if you don't want the grid width to resize based on window size.
* You will also need to set the resultsMaskTable width appropriately in the JSP.*/
var resizeGridWithWindow = true;


/*This function is called onload from the page*/
function myOnLoad()
{
	setUserView();
	loadDock();
	setChargeType('d');
    if (showErrorMessage) {
        showErrorMessages();
    }

} //end of method

function setUserView() {
	var viewType = $("viewType").value;

	if ("REQUEST" != viewType) {
        try
        {
	        $("dock").disabled = true;
			$("deliverTo").disabled = true;
        } catch(ex){}

    }

} //end of setUserView

function auditChargeNumberData(chargeData) {
	var result = true; 
	var prRulesIndexForLine = getPrRulesIndexForLine();
	if (prRulesColl[prRulesIndexForLine].prAccountRequired == "y") {
		var chargeNumberOfColumn = getChargeNumberOfColumn(prRulesIndexForLine);
		var totalPercent = 0.0*1;
		for (var i = 0; i < chargeData.length; i++) {
			var tmpPercent = chargeData[i].percentage;
			if (tmpPercent != null && tmpPercent.length > 0) {
				if (isFloat(tmpPercent)) {
					if (chargeNumberOfColumn == "2") {
						if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0) {
							alert(messagesData.missingchargenumber);
							return false;
						}else {
							totalPercent += tmpPercent*1;
						}
					}else if (chargeNumberOfColumn == "3") {
						hasChargeNumber = false;
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull1 == 'N') {
							if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull2 == 'N') {
							if (chargeData[i].accountNumber2 == null || chargeData[i].accountNumber2.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (hasChargeNumber) {
							totalPercent += tmpPercent*1;
						}
					}else if (chargeNumberOfColumn == "4") {
						hasChargeNumber = false;
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull1 == 'N') {
							if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull2 == 'N') {
							if (chargeData[i].accountNumber2 == null || chargeData[i].accountNumber2.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull3 == 'N') {
							if (chargeData[i].accountNumber3 == null || chargeData[i].accountNumber3.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (hasChargeNumber) {
							totalPercent += tmpPercent*1;
						}
					}else if (chargeNumberOfColumn == "5") {
						hasChargeNumber = false;
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull1 == 'N') {
							if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull2 == 'N') {
							if (chargeData[i].accountNumber2 == null || chargeData[i].accountNumber2.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull3 == 'N') {
							if (chargeData[i].accountNumber3 == null || chargeData[i].accountNumber3.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (prRulesColl[prRulesIndexForLine].chargeAllowNull4 == 'N') {
							if (chargeData[i].accountNumber4 == null || chargeData[i].accountNumber4.length == 0 ) {
								alert(messagesData.missingchargenumber);
								return false;
							}else {
								hasChargeNumber = true;
							}
						}
						if (hasChargeNumber) {
							totalPercent += tmpPercent*1;
						}
					}
				}else {
					alert(messagesData.missingpercent);
					return false;
				}
			}
		} //end of for each charge numbers
		//make sure the sum of percent is 100
		if (totalPercent != 100) {
			alert(messagesData.invalidpercent);
			return false;
		}
	} //end of if pr_account_required
	return result;
}

function directedCheck() {
	setChargeType('d');
}

function inDirectedCheck() {
	setChargeType('i');
}

function dockChanged() {
	loadDeliverTo();
}

function doChargeNumberInitGrid2ColumnsProofOfConcept(column1,typeStr){
	chargeNumberGrid2Columns = new dhtmlXGridObject('chargeNumber2ColumnsDivId');
	chargeNumberGrid2Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid2Columns.setHeader(
		"permission,"+
		"columnpermission,"+
		column1+","+
		"columnperm,"+
		"%"
	);

   chargeNumberGrid2Columns.setColumnIds("permission2,chargeNumber2Permission,chargeNumber2,percent2Permission,percent2");

	//_setPermColumn("permission2", false);
	//_setPermColumn("chargeNumberPermission2",false);
	_setPermColumn("chargeNumber2","chargeNumber2Permission");
	_setPermColumn("percent2", "percent2Permission");

	chargeNumberGrid2Columns.setInitWidths("0,0,330,0,100");
   chargeNumberGrid2Columns.setColAlign("left,left,left,left,left")
   chargeNumberGrid2Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber2"] = 59;
	inputSize["percent2"] = 15;

	chargeNumberGrid2Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid2Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid2Columns.enableTooltips("false,false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   // 5/11/2011 It seems to me these values were set up with the opposite values. In fact, this following statement is not necessary. Cindy
 //  chargeNumberGrid2Columns.enableEditEvents(true,true,false,true,false);
   chargeNumberGrid2Columns.setSkin("haas");
	chargeNumberGrid2Columns.submitOnlyChanged(false);
	chargeNumberGrid2Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid2Columns.submitColumns([false,false,true,false,true]);
   //chargeNumberGrid2Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid2Columns.setColSorting("na,na,haasStr,na,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid2Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid2Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid2Columns.enableSmartRendering(true);

	 chargeNumberGrid2Columns.init();
	/*allow copy and paste feature /
	chargeNumberGrid2Columns.entBox.onselectstart = function(){ return true; };
	/*loading from JSON object*/
	//chargeNumberGrid2Columns.parse(jsonMainData,"json");
	/*This will update the column headers widths according to font size.*/
	//updateColumnWidths(chargeNumberGrid2Columns);

    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function doChargeNumberInitGrid2Columns(column1,typeStr){
	chargeNumberGrid2Columns = new dhtmlXGridObject('chargeNumber2ColumnsDivId');
	chargeNumberGrid2Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid2Columns.setHeader(
		","+
		column1+","+
		"%"
	);

   chargeNumberGrid2Columns.setColumnIds("permission,chargeNumber2,percent2");

	chargeNumberGrid2Columns.setInitWidths("0,330,100");
   chargeNumberGrid2Columns.setColAlign("left,left,left")
   chargeNumberGrid2Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber2"] = 59;
	inputSize["percent2"] = 15;

	chargeNumberGrid2Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid2Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid2Columns.enableTooltips("false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   // 5/11/2011 It seems to me these values were set up with the opposite values. In fact, this following statement is not necessary. Cindy
//   chargeNumberGrid2Columns.enableEditEvents(true,false,false);
   chargeNumberGrid2Columns.setSkin("haas");
	chargeNumberGrid2Columns.submitOnlyChanged(false);
	chargeNumberGrid2Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid2Columns.submitColumns([false,true,true]);
   //chargeNumberGrid2Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid2Columns.setColSorting("na,haasStr,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid2Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid2Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid2Columns.enableSmartRendering(true);

	 chargeNumberGrid2Columns.init();
	/*allow copy and paste feature /
	chargeNumberGrid2Columns.entBox.onselectstart = function(){ return true; };
	/*loading from JSON object*/
	//chargeNumberGrid2Columns.parse(jsonMainData,"json");
	/*This will update the column headers widths according to font size.*/
	//updateColumnWidths(chargeNumberGrid2Columns);

    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function doChargeNumberInitGrid3Columns(column1,column2,typeStr){
	chargeNumberGrid3Columns = new dhtmlXGridObject('chargeNumber3ColumnsDivId');
   chargeNumberGrid3Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid3Columns.setHeader(
		","+
		column1+","+
		column2+","+
		"%"
	);

	chargeNumberGrid3Columns.setColumnIds("permission,chargeNumber,chargeNumber2,percent");
	chargeNumberGrid3Columns.setInitWidths("0,165,165,100");
   chargeNumberGrid3Columns.setColAlign("left,left,left,left")
   chargeNumberGrid3Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber"] = 27;
	inputSize["chargeNumber2"] = 27;
	inputSize["percent"] = 12;

	chargeNumberGrid3Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid3Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid3Columns.enableTooltips("false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   // 5/11/2011 It seems to me these values were set up with the opposite values. In fact, this following statement is not necessary. Cindy
//   chargeNumberGrid3Columns.enableEditEvents(true,false,false,false);
   chargeNumberGrid3Columns.setSkin("haas");
	chargeNumberGrid3Columns.submitOnlyChanged(false);
	chargeNumberGrid3Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid3Columns.submitColumns([false,true,true,true]);
    chargeNumberGrid3Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid3Columns.setColSorting("na,haasStr,haasStr,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid3Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid3Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid3Columns.enableSmartRendering(true);

    chargeNumberGrid3Columns.init();
	/*allow copy and paste feature */
	chargeNumberGrid3Columns.entBox.onselectstart = function(){ return true; };
	/*loading from JSON object*/
	//chargeNumberGrid3Columns.parse(jsonMainData,"json");
	/*This will update the column headers widths according to font size.*/
	//updateColumnWidths(chargeNumberGrid3Columns);
    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function doChargeNumberInitGrid4Columns(column1,column2,column3,typeStr){
	chargeNumberGrid4Columns = new dhtmlXGridObject('chargeNumber4ColumnsDivId');
   chargeNumberGrid4Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid4Columns.setHeader(
		","+
		column1+","+
		column2+","+
		column3+","+
		"%"
	);

	chargeNumberGrid4Columns.setColumnIds("permission,chargeNumber,chargeNumber2,chargeNumber3,percent");
	chargeNumberGrid4Columns.setInitWidths("0,120,120,120,70");
   chargeNumberGrid4Columns.setColAlign("left,left,left,left,left")
   chargeNumberGrid4Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber"] = 16;
	inputSize["chargeNumber2"] = 16;
	inputSize["chargeNumber3"] = 16;
	inputSize["percent"] = 6;

	chargeNumberGrid4Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid4Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid4Columns.enableTooltips("false,false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   // 5/11/2011 It seems to me these values were set up with the opposite values. In fact, this following statement is not necessary. Cindy
//   chargeNumberGrid4Columns.enableEditEvents(true,false,false,false,false);
   chargeNumberGrid4Columns.setSkin("haas");
	chargeNumberGrid4Columns.submitOnlyChanged(false);
	chargeNumberGrid4Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid4Columns.submitColumns([false,true,true,true,true]);
    chargeNumberGrid4Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid4Columns.setColSorting("na,haasStr,haasStr,haasStr,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid4Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid4Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid4Columns.enableSmartRendering(true);

    chargeNumberGrid4Columns.init();
	/*allow copy and paste feature */
	chargeNumberGrid4Columns.entBox.onselectstart = function(){ return true; };
    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function doChargeNumberInitGrid5Columns(column1,column2,column3,column4,typeStr){
	chargeNumberGrid5Columns = new dhtmlXGridObject('chargeNumber5ColumnsDivId');
   chargeNumberGrid5Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid5Columns.setHeader(
		","+
		column1+","+
		column2+","+
		column3+","+
		column4+","+
		"%"
	);

	chargeNumberGrid5Columns.setColumnIds("permission,chargeNumber,chargeNumber2,chargeNumber3,chargeNumber4,percent");
	chargeNumberGrid5Columns.setInitWidths("0,92,92,92,92,60");
   chargeNumberGrid5Columns.setColAlign("left,left,left,left,left,left")
   chargeNumberGrid5Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber"] = 11;
	inputSize["chargeNumber2"] = 11;
	inputSize["chargeNumber3"] = 11;
	inputSize["chargeNumber4"] = 11;
	inputSize["percent"] = 6;

	chargeNumberGrid5Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid5Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid5Columns.enableTooltips("false,false,false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   // 5/11/2011 It seems to me these values were set up with the opposite values. In fact, this following statement is not necessary. Cindy
//   chargeNumberGrid5Columns.enableEditEvents(true,false,false,false,false,false);
   chargeNumberGrid5Columns.setSkin("haas");
	chargeNumberGrid5Columns.submitOnlyChanged(false);
	chargeNumberGrid5Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid5Columns.submitColumns([false,true,true,true,true,true]);
    chargeNumberGrid5Columns.setColumnHidden(0,true); // permission
    /*Set the type of sort to do on this column.If the gird has rowspans >1 set all columns sotring to be na.
	sorting type str is case sensistive (X,Z come before a,b). haasStr is caseinsensitve sorting.
	For Date column types you need to write custom sorting funciton which will be triggered by onBeforeSorting event.
	For Editable Date column we will not allow sorting, set the sorting to be na.
	For hch you have to write a custom sorting function if needed on the page, other wise set sorting to na*/
	chargeNumberGrid5Columns.setColSorting("na,haasStr,haasStr,haasStr,haasStr,int");

    /*This keeps the row height the same, true will wrap cell content and height of row will change.*/
    chargeNumberGrid5Columns.enableMultiline(false);
    /*This is used to tell the grid that we have row height set based on the font size. */
    chargeNumberGrid5Columns.setAwaitedRowHeight(gridRowHeight);
    chargeNumberGrid5Columns.enableSmartRendering(true);

    chargeNumberGrid5Columns.init();
	/*allow copy and paste feature */
	chargeNumberGrid5Columns.entBox.onselectstart = function(){ return true; };
    try
    {
     var chargeNumberTable = document.getElementById("chargeNumberTable");
     chargeNumberTable.style.width = "450px";
    }
    catch(ex)
    {
      //alert("THis means the grid was not initialized");
    }
}

function chargeNumberSelectRow() {
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   if( ee != null ) {
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
   chargeNumberSelectedRowId = rowId0;
}

function doOnBeforeSelect(rowId,oldRowId) {
	setRowClass(oldRowId, saveRowClass);

	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;
}

function getCurrentChargeType() {
	if($("chargeTypeI").checked)
		return 'i';
	else
		return 'd';
}

function getPoRequired() {
	var result = "n";
	var currentChargeType = getCurrentChargeType();
	for(var i = 0; i < prRulesColl.length; i++) {
		if (currentChargeType == prRulesColl[i].chargeType) {
			result = prRulesColl[i].poRequired;
			break;
		}
	}
	return result;
}

function getPoSeqRequired() {
	var result = "n";
	var currentChargeType = getCurrentChargeType();
	for(var i = 0; i < prRulesColl.length; i++) {
		if (currentChargeType == prRulesColl[i].chargeType) {
			result = prRulesColl[i].poSeqRequired;
			break;
		}
	}
	return result;
}

function getChargeNumberOfColumn(prRulesIndexForLine) {
	var result = "0";
	var chargeLabel1 = prRulesColl[prRulesIndexForLine].chargeLabel1;
	var chargeLabel2 = prRulesColl[prRulesIndexForLine].chargeLabel2;
	var chargeLabel3 = prRulesColl[prRulesIndexForLine].chargeLabel3;
	var chargeLabel4 = prRulesColl[prRulesIndexForLine].chargeLabel4;
	if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
		 (chargeLabel3 != null && chargeLabel3.length > 0) && (chargeLabel4 != null && chargeLabel4.length > 0)) {
		result = "5";
	}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
		 (chargeLabel3 != null && chargeLabel3.length > 0)) {
		result = "4";
	}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
		result = "3";
	}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
		result = "2";
	}
	return result;
}

function getPrAccountRequired(currentRowId) {
	var result = "n";
	for(var i = 0; i < prRulesColl.length; i++) {
		if (cellValue(currentRowId,"chargeType") == prRulesColl[i].chargeType) {
			result = prRulesColl[i].prAccountRequired;
			break;
		}
	}
	return result;
}

function getPrRulesIndexForLine() {
	var result = 0;
	var currentChargeType = getCurrentChargeType();
	for(var i = 0; i < prRulesColl.length; i++) {
		if (currentChargeType == prRulesColl[i].chargeType) {
			result = i;
			break;
		}
	}
	return result;
}

function getNonPoPrice(currentRowId) {
	var result = -12345.6;
	var unitPrice = cellValue(currentRowId,"unitPrice");
	if (unitPrice != null) {
		result = cellValue(currentRowId,"qty")*(unitPrice*getConversionFactor(cellValue(currentRowId,"currencyId")));
	}
	return result;
}

function getConversionFactor(currencyId) {
	var result = 1;
	for (var i = 0; i < currencyColl.length; i++) {
		if (currencyId == currencyColl[i].currencyId) {
			result = currencyColl[i].exchangeRate;
			break;
		}
	}
	return result;
}

function setChargeType(currentChargeType) {

	if (prRulesColl.length == 2) {
		$("chargeTypeSpan").style["display"] = "";
	}else {
		$("chargeTypeSpan").style["display"] = "none";    
	}

	//determine what charge number or po to show
	var canEditMr = $("canEditMr").value;
	var key = 1; //cellValue(selectedRowId,"lineItem");
	var poRequired = "n";
	var poSeqRequired = "n";
	var unitPriceRequired = "N/A";
	var prAccountRequired = "n";
	var chargeDisplay1 = "n";
	var chargeDisplay2 = "n";
	var chargeDisplay3 = "n";
	var chargeDisplay4 = "n";
	var chargeLabel1 = "";
	var chargeLabel2 = "";
	var chargeLabel3 = "";
	var chargeLabel4 = "";
	var editDirectedCharge1 = "";
	var editDirectedCharge2 = "";
	var editDirectedCharge3 = "";
	var editDirectedCharge4 = "";
	for(var i = 0; i < prRulesColl.length; i++) {
		if (currentChargeType == prRulesColl[i].chargeType) {
			poRequired = prRulesColl[i].poRequired;
			poSeqRequired = prRulesColl[i].poSeqRequired;
			unitPriceRequired = prRulesColl[i].unitPriceRequired;
			prAccountRequired = prRulesColl[i].prAccountRequired;
			chargeDisplay1 = prRulesColl[i].chargeDisplay1;
			chargeDisplay2 = prRulesColl[i].chargeDisplay2;
			chargeDisplay3 = prRulesColl[i].chargeDisplay3;
			chargeDisplay4 = prRulesColl[i].chargeDisplay4;
			chargeLabel1 = prRulesColl[i].chargeLabel1;
			chargeLabel2 = prRulesColl[i].chargeLabel2;
			chargeLabel3 = prRulesColl[i].chargeLabel3;
			chargeLabel4 = prRulesColl[i].chargeLabel4;
			editDirectedCharge1 = prRulesColl[i].chargeAllowEdit1;
			editDirectedCharge2 = prRulesColl[i].chargeAllowEdit2;
			editDirectedCharge3 = prRulesColl[i].chargeAllowEdit3;
			editDirectedCharge4 = prRulesColl[i].chargeAllowEdit4;
			break;
		}
	}
	//charge number
	if (prAccountRequired == "y") {
		if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
			 (chargeLabel3 != null && chargeLabel3.length > 0) && (chargeLabel4 != null && chargeLabel4.length > 0)) {
			var typeStr = "ro,ro,ro,ro,ro,ro";
			if(currentChargeType == "d") {
				var directedCharge = $v("directedChargeForDirect"); 
				if (directedCharge == "Y") {
					$("chargeTypeD").disabled = "true";
					$("chargeTypeI").disabled = "true";
					typeStr = "ro,ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumber5Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
					if ($v("chargeNumbersFromDirectedChargeD") == 'Y') {
						typeStr = "ro";
						if (editDirectedCharge1 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge2 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge3 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge4 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						typeStr += ",hed";
					}else {
						typeStr = "ro,hed,hed,hed,hed,hed";
					}

					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumberEmpty5Columns(canEditMr,prAccountColl[key],currentChargeType);
				}
			}else {
				var directedCharge = $v("directedChargeForIndirect");
				if (directedCharge == "Y") {
					$("chargeTypeD").disabled = "true";
					$("chargeTypeI").disabled = "true";
					typeStr = "ro,ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumber5Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
					if ($v("chargeNumbersFromDirectedChargeI") == 'Y') {
						typeStr = "ro";
						if (editDirectedCharge1 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge2 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge3 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge4 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						typeStr += ",hed";
					}else {
						typeStr = "ro,hed,hed,hed,hed,hed";
					}
					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumberEmpty5Columns(canEditMr,prAccountColl[key],currentChargeType);
				}
			}
			$("chargeNumber2ColumnsDivId").style["display"] = "none";
			$("chargeNumber3ColumnsDivId").style["display"] = "none";
			$("chargeNumber4ColumnsDivId").style["display"] = "none";
			$("chargeNumber5ColumnsDivId").style["display"] = "";
		}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
			       (chargeLabel3 != null && chargeLabel3.length > 0)) {
			var typeStr = "ro,ro,ro,ro,ro";
			if(currentChargeType == "d") {
				var directedCharge = $v("directedChargeForDirect"); 
				if (directedCharge == "Y") {
					$("chargeTypeD").disabled = "true";
					$("chargeTypeI").disabled = "true";
					typeStr = "ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumber4Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
					if ($v("chargeNumbersFromDirectedChargeD") == 'Y') {
						typeStr = "ro";
						if (editDirectedCharge1 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge2 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge3 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						typeStr += ",hed";
					}else {
						typeStr = "ro,hed,hed,hed,hed";
					}
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumberEmpty4Columns(canEditMr,prAccountColl[key],currentChargeType);
				}
			}else {
				var directedCharge = $v("directedChargeForIndirect");
				if (directedCharge == "Y") {
					$("chargeTypeD").disabled = "true";
					$("chargeTypeI").disabled = "true";
					typeStr = "ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumber4Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
					if ($v("chargeNumbersFromDirectedChargeI") == 'Y') {
						typeStr = "ro";
						if (editDirectedCharge1 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge2 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						if (editDirectedCharge3 == "Y") {
							typeStr += ",hed";
						}else {
							typeStr += ",ro";
						}
						typeStr += ",hed";
					}else {
						typeStr = "ro,hed,hed,hed,hed";
					}
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumberEmpty4Columns(canEditMr,prAccountColl[key],currentChargeType);
				}
			}
			$("chargeNumber2ColumnsDivId").style["display"] = "none";
			$("chargeNumber3ColumnsDivId").style["display"] = "none";
			$("chargeNumber4ColumnsDivId").style["display"] = "";
			$("chargeNumber5ColumnsDivId").style["display"] = "none";
		}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
			var typeStr = "ro,ro,ro,ro";
			if(currentChargeType == "d") {
				var directedCharge = $v("directedChargeForDirect"); 
				if (directedCharge == "Y") {
					$("chargeTypeD").disabled = "true";
					$("chargeTypeI").disabled = "true";
					typeStr = "ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumber3Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
					if (chargeDisplay1 == "y" && chargeDisplay2 == "y") {
						typeStr = "ro,ro,ro,hed";
						//intialize grid
						chargeNumberGrid3Columns == null;
						doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
						displayChargeNumber3Columns(chargeNumberForDirectColl[key],canEditMr,prAccountColl[key],currentChargeType);
					}else {
						if ($v("chargeNumbersFromDirectedChargeD") == 'Y') {
							typeStr = "ro";
							if (editDirectedCharge1 == "Y") {
								typeStr += ",hed";
							}else {
								typeStr += ",ro";
							}
							if (editDirectedCharge2 == "Y") {
								typeStr += ",hed";
							}else {
								typeStr += ",ro";
							}
							typeStr += ",hed";
						}else {
							typeStr = "ro,hed,hed,hed";
						}
						//intialize grid
						chargeNumberGrid3Columns == null;
						doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
						displayChargeNumberEmpty3Columns(canEditMr,prAccountColl[key],currentChargeType);
					}
				}
			}else {
				var directedCharge = $v("directedChargeForIndirect");
				if (directedCharge == "Y") {
					$("chargeTypeD").disabled = "true";
					$("chargeTypeI").disabled = "true";
					typeStr = "ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumber3Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
					if (chargeDisplay1 == "y" && chargeDisplay2 == "y") {
						typeStr = "ro,ro,ro,hed";
						//intialize grid
						chargeNumberGrid3Columns == null;
						doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
						displayChargeNumber3Columns(chargeNumberForIndirectColl[key],canEditMr,prAccountColl[key],currentChargeType);
					}else {
						if ($v("chargeNumbersFromDirectedChargeI") == 'Y') {
							typeStr = "ro";
							if (editDirectedCharge1 == "Y") {
								typeStr += ",hed";
							}else {
								typeStr += ",ro";
							}
							if (editDirectedCharge2 == "Y") {
								typeStr += ",hed";
							}else {
								typeStr += ",ro";
							}
							typeStr += ",hed";
						}else {
							typeStr = "ro,hed,hed,hed";
						}
						//intialize grid
						chargeNumberGrid3Columns == null;
						doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
						displayChargeNumberEmpty3Columns(canEditMr,prAccountColl[key],currentChargeType);
					}
				}
			}
			$("chargeNumber2ColumnsDivId").style["display"] = "none";
			$("chargeNumber3ColumnsDivId").style["display"] = "";
			$("chargeNumber4ColumnsDivId").style["display"] = "none";
			$("chargeNumber5ColumnsDivId").style["display"] = "none";
		}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
			var typeStr = "ro,ro,ro";
			if(currentChargeType == "d") {
				var directedCharge = $v("directedChargeForDirect"); 
				if (directedCharge == "Y") {
					$("chargeTypeD").disabled = "true";
					$("chargeTypeI").disabled = "true";
					typeStr = "ro,ro,ro";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumber2Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
					if (chargeDisplay1 == "y") {
						typeStr = "ro,ro,hed";
						//intialize grid
						chargeNumberGrid2Columns == null;
						doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
						displayChargeNumber2Columns(chargeNumberForDirectColl[key],canEditMr,prAccountColl[key],currentChargeType);
					}else {
						typeStr = "ro,hed,hed";
						//intialize grid
						chargeNumberGrid2Columns == null;
						doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
						displayChargeNumberEmpty2Columns(canEditMr,prAccountColl[key],currentChargeType);
					}
				}
			}else {
				var directedCharge = $v("directedChargeForIndirect");
				if (directedCharge == "Y") {
					$("chargeTypeD").disabled = "true";
					$("chargeTypeI").disabled = "true";
					typeStr = "ro,ro,ro";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumber2Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
					if (chargeDisplay1 == "y") {
						typeStr = "ro,ro,hed";
						//intialize grid
						chargeNumberGrid2Columns == null;
						doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
						displayChargeNumber2Columns(chargeNumberForIndirectColl[key],canEditMr,prAccountColl[key],currentChargeType);
					}else {
						typeStr = "ro,hed,hed";
						//intialize grid
						chargeNumberGrid2Columns == null;
						doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
						displayChargeNumberEmpty2Columns(canEditMr,prAccountColl[key],currentChargeType);
					}
				}
			}
			$("chargeNumber2ColumnsDivId").style["display"] = "";
			$("chargeNumber3ColumnsDivId").style["display"] = "none";
			$("chargeNumber4ColumnsDivId").style["display"] = "none";
			$("chargeNumber5ColumnsDivId").style["display"] = "none";
		}
	}else {
		$("chargeNumber2ColumnsDivId").style["display"] = "none";
		$("chargeNumber3ColumnsDivId").style["display"] = "none";
		$("chargeNumber4ColumnsDivId").style["display"] = "none";
		$("chargeNumber5ColumnsDivId").style["display"] = "none";
	}
	//po
	if (poRequired == "p") {
		//po number
		$("poLabelSpan").style["display"] = "";
		if (currentChargeType == "d") {
			if (facAccountSysPoForDirectColl[key].length == 0) {
				$("poComboSpan").style["display"] = "none";
				$("poInputSpan").style["display"] = "";
				$("poInput").value = $v("poNumber");
			}else {
				//populate po dropdown
				loadPo(facAccountSysPoForDirectColl[key]);
				$("poComboSpan").style["display"] = "";
				$("poInputSpan").style["display"] = "none";
			}
		}else {
			if (facAccountSysPoForIndirectColl[key].length == 0) {
				$("poComboSpan").style["display"] = "none";
				$("poInputSpan").style["display"] = "";
				$("poInput").value = $v("poNumber");
			}else {
				//populate po dropdown
				loadPo(facAccountSysPoForIndirectColl[key]);
				$("poComboSpan").style["display"] = "";
				$("poInputSpan").style["display"] = "none";
			}
		}
		if (poSeqRequired == 'U') {
			$("poLineSpan1").style["display"] = "";
			$("poLineSpan2").style["display"] = "";
		}else {
			$("poLineSpan1").style["display"] = "none";
			$("poLineSpan2").style["display"] = "none";	
		}
		$("poLineInput").value = $v("releaseNumber");

	}else {
		$("poLabelSpan").style["display"] = "none";
		$("poComboSpan").style["display"] = "none";
		$("poInputSpan").style["display"] = "none";
		$("poLineSpan1").style["display"] = "none";
		$("poLineSpan2").style["display"] = "none";
	}
}

function saveCurrenData() {
	var canEditMr = $("canEditMr").value;
	if (canEditMr == "Y") {
		var key = $v("lineItem");
		var currentChargeType = getCurrentChargeType();

		$("shipToLocationId").value = $v("dock");
		$("deliveryPoint").value = $v("deliverTo");
		
        var poRequired = "n";
		var unitPriceRequired = "N/A";
		var prAccountRequired = "n";
		var chargeLabel1 = "";
		var chargeLabel2 = "";
		var chargeLabel3 = "";
		var chargeLabel4 = "";
		for(var i = 0; i < prRulesColl.length; i++) {
			if (currentChargeType == prRulesColl[i].chargeType) {
				poRequired = prRulesColl[i].poRequired;
				unitPriceRequired = prRulesColl[i].unitPriceRequired;
				prAccountRequired = prRulesColl[i].prAccountRequired;
				chargeLabel1 = prRulesColl[i].chargeLabel1;
				chargeLabel2 = prRulesColl[i].chargeLabel2;
				chargeLabel3 = prRulesColl[i].chargeLabel3;
				chargeLabel4 = prRulesColl[i].chargeLabel4;
				break;
			}
		}
		//PO number
		if (poRequired == "p") {
			savePoData();
		} //end of po required
		//charge numbers
		if (prAccountRequired == "y") {
			if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
				 (chargeLabel3 != null && chargeLabel3.length > 0) && (chargeLabel4 != null && chargeLabel4.length > 0)) {
				saveChargeNumber5Columns(key,currentChargeType);
			}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
				       (chargeLabel3 != null && chargeLabel3.length > 0)) {
				saveChargeNumber4Columns(key,currentChargeType);
			}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
				saveChargeNumber3Columns(key,currentChargeType);
			}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
			  	saveChargeNumber2Columns(key,currentChargeType);
			}
		}
	} //end of canEditMr
} //end of saveCurrentData

function saveChargeNumber2Columns(lineKeyId,currentChargeType) {
	//first delete previous data
	delete prAccountColl[lineKeyId];
	//save current data
	prAccountColl[lineKeyId] = new Array();
	var chargeNumbers = "";
	var j = 0;
	for (var i = 1; i <= chargeNumberGrid2Columns.getRowsNum(); i++) {
		var chargeNumber1 = chargeNumberGrid2Columns.cells(i,1).getValue();
		var percent = chargeNumberGrid2Columns.cells(i,2).getValue();

		if (chargeNumber1 != null && chargeNumber1.length > 0 && chargeNumber1 != "&nbsp;") {
			prAccountColl[lineKeyId][j++] = {
				accountNumber: 	chargeNumber1.trim(),
				accountNumber2:   '',
				percentage:			percent,
				chargeType:			currentChargeType
			};
			if (percent != null && percent.length > 0 && percent != "&nbsp;" ) {
				chargeNumbers += chargeNumber1.trim()+"!"+percent.trim()+"|";
			}
		}
	} //end of loop
	if (chargeNumbers.length > 0) {
		$("chargeNumbers").value = chargeNumbers;
		//mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeNumbers")).setValue(chargeNumbers);
	}
}

function saveChargeNumber3Columns(lineKeyId,currentChargeType) {
	//first delete previous data
	delete prAccountColl[lineKeyId];
	//save current data
	prAccountColl[lineKeyId] = new Array();
	var chargeNumbers = "";
	var j = 0;
	for (var i = 1; i <= chargeNumberGrid3Columns.getRowsNum(); i++) {
		var chargeNumber1 = chargeNumberGrid3Columns.cells(i,1).getValue();
		var chargeNumber2 = chargeNumberGrid3Columns.cells(i,2).getValue();
		var percent = chargeNumberGrid3Columns.cells(i,3).getValue();

		if ((chargeNumber1 != null && chargeNumber1.length > 0 && chargeNumber1 != "&nbsp;") ||
			 (chargeNumber2 != null && chargeNumber2.length > 0 && chargeNumber2 != "&nbsp;")) {
			prAccountColl[lineKeyId][j++] = {
				accountNumber: 	chargeNumber1.trim(),
				accountNumber2:   chargeNumber2.trim(),
				percentage:			percent,
				chargeType:			currentChargeType
			};
			if (percent != null && percent.length > 0 && percent != "&nbsp;" ) {
				chargeNumbers += chargeNumber1.trim()+"!"+chargeNumber2.trim()+"!"+percent.trim()+"|";
			}
		}
	}
	if (chargeNumbers.length > 0) {
		$("chargeNumbers").value = chargeNumbers;
		// mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeNumbers")).setValue(chargeNumbers);
	}
}

function saveChargeNumber4Columns(lineKeyId,currentChargeType) {
	//first delete previous data
	delete prAccountColl[lineKeyId];
	//save current data
	prAccountColl[lineKeyId] = new Array();
	var chargeNumbers = "";
	var j = 0;
	for (var i = 1; i <= chargeNumberGrid4Columns.getRowsNum(); i++) {
		var chargeNumber1 = chargeNumberGrid4Columns.cells(i,1).getValue();
		var chargeNumber2 = chargeNumberGrid4Columns.cells(i,2).getValue();
		var chargeNumber3 = chargeNumberGrid4Columns.cells(i,3).getValue();
		var percent = chargeNumberGrid4Columns.cells(i,4).getValue();

		if ((chargeNumber1 != null && chargeNumber1.length > 0 && chargeNumber1 != "&nbsp;") ||
			 (chargeNumber2 != null && chargeNumber2.length > 0 && chargeNumber2 != "&nbsp;") ||
			 (chargeNumber3 != null && chargeNumber3.length > 0 && chargeNumber3 != "&nbsp;")) {
			prAccountColl[lineKeyId][j++] = {
				accountNumber: 	chargeNumber1.trim(),
				accountNumber2:   chargeNumber2.trim(),
				accountNumber3:   chargeNumber3.trim(),
				percentage:			percent,
				chargeType:			currentChargeType
			};
			if (percent != null && percent.length > 0 && percent != "&nbsp;" ) {
				chargeNumbers += chargeNumber1.trim()+"!"+chargeNumber2.trim()+"!"+chargeNumber3.trim()+"!"+percent.trim()+"|";
			}
		}
	}
	if (chargeNumbers.length > 0) {
		$("chargeNumbers").value = chargeNumbers;
		//mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeNumbers")).setValue(chargeNumbers);
	}
}

function saveChargeNumber5Columns(lineKeyId,currentChargeType) {
	//first delete previous data
	delete prAccountColl[lineKeyId];
	//save current data
	prAccountColl[lineKeyId] = new Array();
	var chargeNumbers = "";
	var j = 0;
	for (var i = 1; i <= chargeNumberGrid5Columns.getRowsNum(); i++) {
		var chargeNumber1 = chargeNumberGrid5Columns.cells(i,1).getValue();
		var chargeNumber2 = chargeNumberGrid5Columns.cells(i,2).getValue();
		var chargeNumber3 = chargeNumberGrid5Columns.cells(i,3).getValue();
		var chargeNumber4 = chargeNumberGrid5Columns.cells(i,4).getValue();
		var percent = chargeNumberGrid5Columns.cells(i,5).getValue();

		if ((chargeNumber1 != null && chargeNumber1.length > 0 && chargeNumber1 != "&nbsp;") ||
			 (chargeNumber2 != null && chargeNumber2.length > 0 && chargeNumber2 != "&nbsp;") ||
			 (chargeNumber3 != null && chargeNumber3.length > 0 && chargeNumber3 != "&nbsp;") ||
			 (chargeNumber4 != null && chargeNumber4.length > 0 && chargeNumber4 != "&nbsp;")) {
			prAccountColl[lineKeyId][j++] = {
				accountNumber: 	chargeNumber1.trim(),
				accountNumber2:   chargeNumber2.trim(),
				accountNumber3:   chargeNumber3.trim(),
				accountNumber4:   chargeNumber4.trim(),
				percentage:			percent,
				chargeType:			currentChargeType
			};
			if (percent != null && percent.length > 0 && percent != "&nbsp;" ) {
				chargeNumbers += chargeNumber1.trim()+"!"+chargeNumber2.trim()+"!"+chargeNumber3.trim()+"!"+chargeNumber4.trim()+"!"+percent.trim()+"|";
			}
		}
	}
	if (chargeNumbers.length > 0) {
		$("chargeNumbers").value = chargeNumbers;
//		mygrid.cells(selectedRowId,mygrid.getColIndexById("chargeNumbers")).setValue(chargeNumbers);
	}
}

function savePoData() {
	//po number
	var tmpPoNumber = $("poCombo");
	if (tmpPoNumber == null || tmpPoNumber.value.length == 0) {
		tmpPoNumber = $("poInput");
	}
	if (tmpPoNumber != null && tmpPoNumber.value.length > 0) {
		$("poNumber").value = tmpPoNumber.value;
	}else {
		$("poNumber").value = "";
	}
	var tmpReleaseNumber = $("poLineInput");
	if (tmpReleaseNumber != null && tmpReleaseNumber.value.length > 0) {
		$("releaseNumber").value = tmpReleaseNumber.value;
	}else {
		$("releaseNumber").value = "";
	}

}

function displayChargeNumberEmpty2Columns(canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable2Columns();
	var rowCount = 0;
	for(var j = 0; j < savedDataArray.length; j++ ) {
		if (savedDataArray[j].accountNumber != null && savedDataArray[j].accountNumber.length > 0) {
			chargeNumberGrid2Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
			if (savedDataArray[j].percentage != 0) {
				chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
			}else {
				chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue('');
			}
			rowCount++;
		}
	}
	if (canEditChargeNumber == 'Y') {
		var emptyRow = 20-rowCount;
		for (var i = 0; i < emptyRow; i++) {
			chargeNumberGrid2Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue('');
			rowCount++;
		}
	}
}

function displayChargeNumber2Columns(dataArray,canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable2Columns();

	//looping thru master data
	for (var i = 0; i < dataArray.length; i++) {
		var percent = dataArray[i].percent;
		if (percent == null || percent.length == 0) {
			//looping thru saved data
			for (var j = 0; j < savedDataArray.length; j++) {
				if (currentChargeType == savedDataArray[j].chargeType) {
					if (dataArray[i].chargeNumber1 == savedDataArray[j].accountNumber) {
						var tmp = savedDataArray[j].percentage;
						if (tmp != null && tmp.length > 0) {
							percent = tmp;
							break;
						}
					}
				}
			}
		}
		chargeNumberGrid2Columns.addRow(i+1,"",i);
		var count = 0;
		chargeNumberGrid2Columns.cells(i+1,count++).setValue(canEditChargeNumber);
		chargeNumberGrid2Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber1);
		chargeNumberGrid2Columns.cells(i+1,count++).setValue(percent);
	}
}

function clearChargeTable2Columns() {
	for(var i = chargeNumberGrid2Columns.getRowsNum(); i > 0; i--) {
		chargeNumberGrid2Columns.deleteRow(i);
	}
}

function displayChargeNumberEmpty3Columns(canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable3Columns();
	var rowCount = 0;
	for(var j = 0; j < savedDataArray.length; j++ ) {
		if (currentChargeType == savedDataArray[j].chargeType) {
			chargeNumberGrid3Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber2);
			if (savedDataArray[j].percentage != 0) {
				chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
			}else {
				chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
			}
			rowCount++;
		}
	}
	if (canEditChargeNumber == 'Y') {
		var emptyRow = 20-rowCount;
		for (var i = 0; i < emptyRow; i++) {
			chargeNumberGrid3Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
			rowCount++;
		}
	}
}

function displayChargeNumber3Columns(dataArray,canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable3Columns();
	//looping thru master data
	for (var i = 0; i < dataArray.length; i++) {
		var percent = dataArray[i].percent;
		if (percent == null || percent.length == 0) {
			//looping thru saved data
			for (var j = 0; j < savedDataArray.length; j++) {
				if (currentChargeType == savedDataArray[j].chargeType) {
					if (dataArray[i].chargeNumber1 == savedDataArray[j].accountNumber &&
						 dataArray[i].chargeNumber2 == savedDataArray[j].accountNumber2) {
						var tmp = savedDataArray[j].percentage;
						if (tmp != null && tmp.length > 0) {
							percent = tmp;
							break;
						}
					}
				}
			}
		}
		chargeNumberGrid3Columns.addRow(i+1,"",i);
		var count = 0;
		chargeNumberGrid3Columns.cells(i+1,count++).setValue(canEditChargeNumber);
		chargeNumberGrid3Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber1);
		chargeNumberGrid3Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber2);
		chargeNumberGrid3Columns.cells(i+1,count++).setValue(percent);
	}
}

function clearChargeTable3Columns() {
	for(var i = chargeNumberGrid3Columns.getRowsNum(); i > 0; i--) {
		chargeNumberGrid3Columns.deleteRow(i);
	}
}

function displayChargeNumberEmpty4Columns(canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable4Columns();
	var rowCount = 0;
	for(var j = 0; j < savedDataArray.length; j++ ) {
		if (currentChargeType == savedDataArray[j].chargeType) {
			chargeNumberGrid4Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber2);
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber3);
			if (savedDataArray[j].percentage != 0) {
				chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
			}else {
				chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
			}
			rowCount++;
		}
	}
	if (canEditChargeNumber == 'Y') {
		var emptyRow = 20-rowCount;
		for (var i = 0; i < emptyRow; i++) {
			chargeNumberGrid4Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue('');
			rowCount++;
		}
	}
}

function displayChargeNumber4Columns(dataArray,canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable4Columns();
	//looping thru master data
	for (var i = 0; i < dataArray.length; i++) {
		var percent = dataArray[i].percent;
		if (percent == null || percent.length == 0) {
			//looping thru saved data
			for (var j = 0; j < savedDataArray.length; j++) {
				if (currentChargeType == savedDataArray[j].chargeType) {
					if (dataArray[i].chargeNumber1 == savedDataArray[j].accountNumber ||
						 dataArray[i].chargeNumber2 == savedDataArray[j].accountNumber2 ||
						 dataArray[i].chargeNumber3 == savedDataArray[j].accountNumber3) {
						var tmp = savedDataArray[j].percentage;
						if (tmp != null && tmp.length > 0) {
							percent = tmp;
							break;
						}
					}
				}
			}
		}
		chargeNumberGrid4Columns.addRow(i+1,"",i);
		var count = 0;
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(canEditChargeNumber);
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber1);
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber2);
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber3);
		chargeNumberGrid4Columns.cells(i+1,count++).setValue(percent);
	}
}

function clearChargeTable4Columns() {
	for(var i = chargeNumberGrid4Columns.getRowsNum(); i > 0; i--) {
		chargeNumberGrid4Columns.deleteRow(i);
	}
}

function displayChargeNumberEmpty5Columns(canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable5Columns();
	var rowCount = 0;
	for(var j = 0; j < savedDataArray.length; j++ ) {
		if (currentChargeType == savedDataArray[j].chargeType) {
			chargeNumberGrid5Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber2);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber3);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber4);
			if (savedDataArray[j].percentage != 0) {
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
			}else {
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			}
			rowCount++;
		}
	}
	if (canEditChargeNumber == 'Y') {
		var emptyRow = 20-rowCount;
		for (var i = 0; i < emptyRow; i++) {
			chargeNumberGrid5Columns.addRow(rowCount+1,"",rowCount);
			var count = 0;
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue('');
			rowCount++;
		}
	}
}

function displayChargeNumber5Columns(dataArray,canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable5Columns();
	//looping thru master data
	for (var i = 0; i < dataArray.length; i++) {
		var percent = dataArray[i].percent;
		if (percent == null || percent.length == 0) {
			//looping thru saved data
			for (var j = 0; j < savedDataArray.length; j++) {
				if (currentChargeType == savedDataArray[j].chargeType) {
					if (dataArray[i].chargeNumber1 == savedDataArray[j].accountNumber ||
						 dataArray[i].chargeNumber2 == savedDataArray[j].accountNumber2 ||
						 dataArray[i].chargeNumber3 == savedDataArray[j].accountNumber3 ||
						 dataArray[i].chargeNumber4 == savedDataArray[j].accountNumber4) {
						var tmp = savedDataArray[j].percentage;
						if (tmp != null && tmp.length > 0) {
							percent = tmp;
							break;
						}
					}
				}
			}
		}
		chargeNumberGrid5Columns.addRow(i+1,"",i);
		var count = 0;
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(canEditChargeNumber);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber1);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber2);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber3);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(dataArray[i].chargeNumber4);
		chargeNumberGrid5Columns.cells(i+1,count++).setValue(percent);
	}
}

function clearChargeTable5Columns() {
	for(var i = chargeNumberGrid5Columns.getRowsNum(); i > 0; i--) {
		chargeNumberGrid5Columns.deleteRow(i);
	}
}

function loadPo(dataArray) {
	var poCombo = $("poCombo");
	//clear previous data
	for (var i = poCombo.length; i > 0; i--) {
    	poCombo[i] = null;
    }
	var selectedIndex = 0;
	var tmpVal = $v("poNumber");
	for (var i=0; i < dataArray.length; i++) {
		setOption(i,dataArray[i].poNumber,dataArray[i].poNumber, "poCombo");
		if (tmpVal == dataArray[i].poNumber) {
			selectedIndex = i;
		}
	}
	poCombo.selectedIndex = selectedIndex;	
}
/*
function displayPoChargeNumber() {

}
*/
function loadDock() {
//    try {
	    var dock = $("dock");
		//clear previous data
		for (var i = dock.length; i > 0; i--) {
	    	dock[i] = null;
	   	}
		var selectedIndex = 0;
		var tmpVal = $v("shipToLocationId");
		var key = 1;
		var dockColl = dockDeliveryPointColl[key].dockColl;
		for (var i=0; i < dockColl.length; i++) {
			setOption(i,dockColl[i].dockDesc,dockColl[i].dockLocationId, "dock");
			if (tmpVal == dockColl[i].dockLocationId) {
				selectedIndex = i;
			}
		}
		dock.selectedIndex = selectedIndex;
		loadDeliverTo();
//    } catch(ex) {}
}

function loadDeliverTo() {
    try {
    var selectedDock = $("dock").value;
	var deliverTo = $("deliverTo");
	//clear previous data
	for (var i = deliverTo.length; i > 0; i--) {
    deliverTo[i] = null;
   }
	var selectedIndex = 0;
	var tmpVal = $v("deliveryPoint");
	var key = 1;
	var dockColl = dockDeliveryPointColl[key].dockColl;
	for (var i=0; i < dockColl.length; i++) {
		if (dockColl[i].dockLocationId == selectedDock) {
			var deliveryPointColl = dockColl[i].deliveryPointColl;
			for (var j=0; j < deliveryPointColl.length; j++) {
				setOption(j,deliveryPointColl[j].deliveryPointDesc,deliveryPointColl[j].deliveryPoint, "deliverTo");
				if (tmpVal == deliveryPointColl[j].deliveryPoint) {
					selectedIndex = j;
				}
			}
			break;
		}
	}
	deliverTo.selectedIndex = selectedIndex;
    } catch(ex) {}
}

function toApplicationChanged() {
	$("uAction").value = 'workAreaChanged';
	showPleaseWait();
	document.genericForm.submit();  
}

function validateQty() {
	if(!isPositiveInteger($v("qty"), false) || $v("qty")*1 > $v("deliveredQty")*1) {
		alert(messagesData.validvalues+"\n"+messagesData.qty);
		$("qty").value = "";
		return false;
	}  
	return true;
}

function processMovement() {
	saveCurrenData();
	
	if (!validateQty())
		return false;
	
	if (getPoRequired() == "p") {
		//po number
		var poNumber = $v("poNumber");
		if (poNumber == null || poNumber.length == 0) {
			alert(messagesData.missingpo);
			return false;
		}
		
		if (getPoSeqRequired() == 'U') {
			releaseNumber = $v("releaseNumber");
			if (releaseNumber == null || releaseNumber.length == 0) {
				alert(messagesData.validvalues+" "+messagesData.poline);
				return false;
			}
		}
	}
	
	toApplication = $v("toApplication");
	if (toApplication == null || toApplication.length == 0) {
		alert(messagesData.pleasechooseworkarea);
		return false;
	}
	
	var key = $v("lineItem");
	if(!auditChargeNumberData(prAccountColl[key])) {
		return false;
	} 
		
	$("uAction").value = 'update';
	
	showPleaseWait();
	document.genericForm.submit();  
}

function cancelMovement() {
	$("uAction").value = 'delete';
	showPleaseWait();
	document.genericForm.submit();  
}

function doCancelMovement() {
	callToServer("movementofmaterial.do?uAction=delete&prNumber="+$v("prNumber"));
}