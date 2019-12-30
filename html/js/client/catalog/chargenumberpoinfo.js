var chargeNumberGrid2Columns;
var chargeNumberGrid3Columns;
var chargeNumberGrid4Columns;
var chargeNumberGrid5Columns;
var inputSize = new Array();

function doChargeNumberInitGrid2Columns(column1,typeStr){
	chargeNumberGrid2Columns = new dhtmlXGridObject('chargeNumber2ColumnsDivId');
   chargeNumberGrid2Columns.setImagePath("../../dhtmlxGrid/codebase/imgs/");

	chargeNumberGrid2Columns.setHeader(
		","+
		column1+","+
		"%"
	);

   chargeNumberGrid2Columns.setColumnIds("permission,chargeNumber,percent");
   chargeNumberGrid2Columns.setInitWidths("0,330,100");
   chargeNumberGrid2Columns.setColAlign("left,left,left")
   chargeNumberGrid2Columns.setColTypes(typeStr);
	//set size for all 'hed' here
	inputSize["chargeNumber"] = 59;
	inputSize["percent"] = 15;

	//chargeNumberGrid2Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   //chargeNumberGrid2Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid2Columns.enableTooltips("false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid2Columns.enableEditEvents(true,false,false);
   chargeNumberGrid2Columns.setSkin("haas");
	chargeNumberGrid2Columns.submitOnlyChanged(false);
	chargeNumberGrid2Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid2Columns.submitColumns([false,true,true]);
   chargeNumberGrid2Columns.setColumnHidden(0,true); // permission
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
	/*allow copy and paste feature */
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

	//chargeNumberGrid3Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   //chargeNumberGrid3Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid3Columns.enableTooltips("false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid3Columns.enableEditEvents(true,false,false,false);
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

	//chargeNumberGrid4Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   //chargeNumberGrid4Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid4Columns.enableTooltips("false,false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid4Columns.enableEditEvents(true,false,false,false,false);
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

	//chargeNumberGrid5Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   //chargeNumberGrid5Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid5Columns.enableTooltips("false,false,false,false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid5Columns.enableEditEvents(true,false,false,false,false,false);
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

function displayChargeNumberEmpty2Columns(canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable2Columns();
	var rowCount = 0;
	for(var j = 0; j < savedDataArray.length; j++ ) {
		if (currentChargeType == savedDataArray[j].chargeType) {
			if (savedDataArray[j].accountNumber != null && savedDataArray[j].accountNumber.length > 0) {
				chargeNumberGrid2Columns.addRow(rowCount+1,"",rowCount);
				var count = 0;
				chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
				chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
				chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
				rowCount++;
			}
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
		if (savedDataArray[j].percentage != null && savedDataArray[j].percentage.length > 0) {
			if (currentChargeType == savedDataArray[j].chargeType) {
				chargeNumberGrid3Columns.addRow(rowCount+1,"",rowCount);
				var count = 0;
				chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
				chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
				chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber2);
				chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
				rowCount++;
			}
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
		if (savedDataArray[j].percentage != null && savedDataArray[j].percentage.length > 0) {
			if (currentChargeType == savedDataArray[j].chargeType) {
				chargeNumberGrid4Columns.addRow(rowCount+1,"",rowCount);
				var count = 0;
				chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
				chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
				chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber2);
				chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber3);
				chargeNumberGrid4Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
				rowCount++;
			}
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
		if (savedDataArray[j].percentage != null && savedDataArray[j].percentage.length > 0) {
			if (currentChargeType == savedDataArray[j].chargeType) {
				chargeNumberGrid5Columns.addRow(rowCount+1,"",rowCount);
				var count = 0;
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(canEditChargeNumber);
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber);
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber2);
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber3);
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].accountNumber4);
				chargeNumberGrid5Columns.cells(rowCount+1,count++).setValue(savedDataArray[j].percentage);
				rowCount++;
			}
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

function loadPo(dataArray,selectedPo) {
	var poCombo = $("poCombo");
	//clear previous data
	for (var i = poCombo.length; i > 0; i--) {
    poCombo[i] = null;
   }
	var selectedIndex = 0;
	for (var i=0; i < dataArray.length; i++) {
		setOption(i,dataArray[i].poNumber,dataArray[i].poNumber, "poCombo");
		if (selectedPo == dataArray[i].poNumber) {
			selectedIndex = i;
		}
	}
	poCombo.selectedIndex = selectedIndex;
}

