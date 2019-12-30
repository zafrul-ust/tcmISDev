var mygrid;
var currentRow;
// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;
var children = new Array();

if (!Array.prototype.indexOf) {
	  Array.prototype.indexOf = function indexOf(member, startFrom) {
	    /*
	    In non-strict mode, if the `this` variable is null or undefined, then it is
	    set to the window object. Otherwise, `this` is automatically converted to an
	    object. In strict mode, if the `this` variable is null or undefined, a
	    `TypeError` is thrown.
	    */
	    if (this == null) {
	      throw new TypeError("Array.prototype.indexOf() - can't convert `" + this + "` to object");
	    }

	    var
	      index = isFinite(startFrom) ? Math.floor(startFrom) : 0,
	      that = this instanceof Object ? this : new Object(this),
	      length = isFinite(that.length) ? Math.floor(that.length) : 0;

	    if (index >= length) {
	      return -1;
	    }

	    if (index < 0) {
	      index = Math.max(length + index, 0);
	    }

	    if (member === undefined) {
	      /*
	        Since `member` is undefined, keys that don't exist will have the same
	        value as `member`, and thus do need to be checked.
	      */
	      do {
	        if (index in that && that[index] === undefined) {
	          return index;
	        }
	      } while (++index < length);
	    } else {
	      do {
	        if (that[index] === member) {
	          return index;
	        }
	      } while (++index < length);
	    }

	    return -1;
	  };
	}

function myResultOnLoadWithGrid(gridConfig) {
	var showZeroIfOne = false;
	var totalLines = $v("totalLines");
	if (totalLines < 1) {
		$("totalLines").value=1;
		showZeroIfOne = true;
	}
	
	try {
		if (!showUpdateLinks) {/* Dont show any update links if the user does not have permissions */
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		} else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	} catch (ex) {}
	
	try {
			document.document.getElementById(gridConfig.divName).style["display"] = "";
	} catch (ex) {}

	initGridWithGlobal(gridConfig);
	mygrid._haas_ok_column = "okDoUpdate";

	/* This displays our standard footer message */
	displayGridSearchDuration(showZeroIfOne);

	/* It is important to call this after all the divs have been turned on or off. */
	setResultFrameSize();
}

function doValidateLine(rowId) {
	// Validate the line here
	if (cellValue(rowId, "okDoUpdate") == "true") {
		validateLine(rowId);
	}
}

function updatePrices() {
	if (validationforUpdate()) {
		
		document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
		$('uAction').value = 'update';		// $('formVariableName') is a utility function that does a document.getElementById('variableName')
							// $v('formVariableName') does the same with document.getElementById('variableName').value
		
		// Reset search time for update
		var now = new Date();
		var startSearchTime = parent.document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();	

		parent.showPleaseWait(); // Show "please wait" while updating

		if (mygrid != null) {
			// This function prepares the grid dat for submitting top the server
			mygrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
	}
}

// validate the whole grid
function validationforUpdate() {
	var rowsNum = mygrid.getRowsNum();

	// This reflects the rowId we put in the JSON data 
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (!validateLine(rowId)) {
			//Select the failing line
			mygrid.selectRowById(rowId, null, false, false); 
			return false;
		}
	}

	return true;
}

// validate one line here
function validateLine(rowId) {
	var errorMessage = messagesData.validvalues + "\n\n";
	var count = 0;

	if (cellValue(rowId, "okDoUpdate") != "true")
		return true; // If not checked, don't validate
	else {
		var startDate = cellValue(rowId, "startDate");
		if (startDate.trim() == "") {
			endDate
			errorMessage += "\n" +formatMessage(messagesData.mustBeADate,messagesData.startDate);
			count++;
		}

		var endDate = cellValue(rowId, "endDate");
		if (endDate.trim() == "") {
			errorMessage += "\n" +formatMessage(messagesData.mustBeADate,messagesData.endDate);
			count++;
		}
		
		if (startDate == endDate || convertDateStringToTimestamp(endDate) <= convertDateStringToTimestamp(startDate)) {
			errorMessage += "\n" + messagesData.daterange;
			count++;			
		}
		
		var catalogPrice = cellValue(rowId, "catalogPrice");
		if (!isFloat(catalogPrice, false)) { 
			// please refer to /js/common/formchek.js for more validation functions
			errorMessage += "\n" +formatMessage(messagesData.mustBeAFloat,messagesData.catalogPrice);
			count++;
		}

		var comments = cellValue(rowId, "loadingComments");
		if (comments.length > 2000) { // Limit the txt field to 2000 characters
			errorMessage += "\n" + messagesData.maximum2000;
			mygrid.cellById(rowId, mygrid.getColIndexById("loadingComments")).setValue(comments.substring(0, 2000));
			count++;
		}
		else if (comments.length <= 1) {
			errorMessage += "\n" + messagesData.noteRequired;			
			count++;
		}

		if (count > 0) {
			alert(errorMessage);
			return false;
		}
		
	}
	return true;
}

function onRowSelected(rowId,cellInd) {
	currentRow = rowId;
}

function onChange() {
	cell(currentRow, "okDoUpdate").setValue(true);
	$("okDoUpdate" + currentRow).checked = true;
	var startDateSort = convertDateStringToTimestamp(cellValue(currentRow, "startDate"));
	cell(currentRow, "startDateSort").setValue(startDateSort);
	var endDateSort = convertDateStringToTimestamp(cellValue(currentRow, "endDate"));
	cell(currentRow, "endDateSort").setValue(endDateSort);
}

function addRow() {
	 var loc = "CMSPricelist.do?uAction=showAddPart&companyId="+$v("companyId")+"&catalogId="+$v("catalogId");
	 children[children.length] = openWinGeneric(loc,"CMSPricelistAddPartSearch","650","500","yes","50","50","20","20","no");
	
};

function partAdded(catPartNo, itemId, itemDesc, catalogId, partGroupNo) {
	var rowsNum = mygrid.getRowsNum();

	// This reflects the rowId we put in the JSON data 
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (catPartNo == cellValue(rowId, "catPartNo")  && cellValue(rowId, "expired") != "true") {
			alert("Part " + catPartNo + " is already in the pricelist")
			mygrid.selectRowById(rowId, null, false, false); 
			return false;
		}
	}
	
	var id = mygrid.getRowsNum() + 1;	
	mygrid.addRow(id, ['Y', true, catPartNo, itemId, itemDesc, $v("blockBefore_startDate"), 0, $v("blockBefore_endDate"), 0,  $v("defaultCurrencyId"), '', '', catalogId, partGroupNo, '', '', '', '', 'false'], id - 1);
	mygrid.selectRowById(id);
	currentRow = id;
	onChange();
}


function importData(data) {
	var updates = 0;
	var errors = "";
	for (var i = 0; i < data.length; i++) {
		var result = importRow(data[i]);
		if ("SKIPPED" != result && "OK" != result && "EMPTYROW" != result) {
			errors += "Row #" + (i +1) + " error: " + result +"\n";
		}
		else if ("OK" == result) {
			updates++;
		}
//		else {
//			alert("Ignoring row[" + i + "]: " + result + " :: " + data[i]);
//		}
	}
	if (errors.length > 0) {
		alert(errors);
	}
	else if (updates > 0) {
		alert(updates + " rows updated from uploaded template.");
	}
	else {
		alert("No updates found in uploaded template.");
	}
}


function importRow(columns) {
	if ("Part No" == columns[0]) {
		return "SKIPPED";
	}
	if (columns[0].length <=1) {
		return "EMPTYROW";
	}
	var length = csvHeader.length;
	if (columns.length > length) {
		return "Too many columns";
	}
	if (columns.length < (length - 1)) { // notes might not show up if empty
		return "Too few columns";
	}
	if (!validCSVDate(columns[2])) {
		return "Start Date '" + columns[2] + "' is invalid, must be YYYY-MM-DD or MM/DD/YYYY";
	}
	if (!validCSVDate(columns[3])) {
		return "End Date '" + columns[3] + "' is invalid, must be YYYY-MM-DD or MM/DD/YYYY";
	}
	if (!validCurrencyId(columns[4])) {
		return "Currency Id '" + columns[4] + "' is invalid";
	}
	if (columns[5].length > 0 && !isFloat(columns[5])) {
		return "Catalog Price '" + columns[5] + "' must only be 0-9 and '.'";
	}
	var note = columns[6] || "";
	return updateRow(columns[0], columns[2], columns[3], columns[4], columns[5], note);
}

function updateRow(catPartNo, startDate, endDate, currencyId, catalogPrice, notes) {
	var rowsNum = mygrid.getRowsNum();

	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (cellValue(rowId, "catPartNo") == catPartNo  && cellValue(rowId, "expired") != "true") {
			var rowChanged = false;
			var newStartDate = convertDateFromCSV(startDate);
			var newEndDate = convertDateFromCSV(endDate);
			if (cellValue(rowId, "startDate") != newStartDate) {
				rowChanged = true;
				cell(rowId, "startDate").setValue(newStartDate);
			}
			if (cellValue(rowId, "endDate") != newEndDate) {
				rowChanged = true;
				cell(rowId, "endDate").setValue(newEndDate);
			}
			if (cellValue(rowId, "currencyId") != currencyId) {
				rowChanged = true;
				cell(rowId, "currencyId").setValue(currencyId);
			}
			if (catalogPrice.length > 0 ) {
				var oldCatalogPrice = parseFloat(cellValue(rowId, "catalogPrice"));
				var newCatalogPrice = parseFloat(catalogPrice);
				if (oldCatalogPrice != newCatalogPrice) {
					rowChanged = true;
					cell(rowId, "catalogPrice").setValue(catalogPrice);
				}
			}
			if (rowChanged) {
				cell(rowId, "loadingComments").setValue(notes);
				cell(rowId, "okDoUpdate").setValue(true);
				$("okDoUpdate" + rowId).checked = true;
				return "OK";
			}
			return "SKIPPED";
		}
	}
	return "Part No not on page, please add part manually.";
}

function validCurrencyId(id) {
	for (var i = 0; i < currencyId.length; i++) {
		if (id == currencyId[i].value) {
			return true; 
		}
	}
	return false;
}

// YYYY-MM-DD or MM/dd/YYYY
function validCSVDate(date) {
    var fields = (date +"").split("-");
    if (fields.length < 3) {
    	fields = (date +"").split("/");
    }
    var result = false;
    var month;
    var  day;
    var year;
    if (fields.length == 3) {
    	if (isInteger(fields[0])&& isInteger(fields[1])&& isInteger(fields[2])) {
			if(fields[0].length==4) { // YYY-MM-DD
				year = fields[0];
				month = fields[1];
				day = fields[2];
			}
			else { // MM/DD/YYY
				month = fields[0];
				day = fields[1];				
				year = fields[2];
			}
				
			if (month.length <= 2 && month < 13) {
				if (day.length <= 2 && day < 32) {
					if (year.length==4) {
							result = true;
					}
				} 
			}
    	} 
    }
    
	return result;
}

function convertToCSV(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = '';

    for (var i = 0; i < array.length; i++) {
        var line = '';
        for (var index in array[i]) {
            if (line != '') {
            	line += ','
            }
            line += '"' + array[i][index] +'"';
        }

        str += line + '\r\n';
    }

    return str;
}

var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
//Convert to YYYY-MM-DD
function convertDateForCSV (str){
	if ("Indefinite" == str) {
		return "3000-01-01";
	}
    var fields = str.split("-");
    var month = months.indexOf(fields[1])+1;
    if (month < 10) {
    	month = "0" + month;
    }
    return fields[2]+"-"+month+"-"+fields[0];
}

function convertDateStringToTimestamp (str) {
	var date;
	if (str == "Indefinite") {
		date = new Date(3000, 1, 1);
	}
	else {
		var fields = str.split("-");
		var month = months.indexOf(fields[1])+1;
		date = new Date(fields[2], month, fields[0]);
	}
    return Math.round(date.getTime()/1000);
}

function convertDateFromCSV(date) {
	var fields = (date + "").split("-");
	if (fields.length < 3) {
		fields = (date + "").split("/");
	}
	var month;
	var day;
	var year;

	if (fields[0].length == 4) { // YYYY-MM-DD
		year = fields[0];
		month = fields[1];
		day = fields[2];
	} else { // MM/DD/YYY
		month = fields[0];
		day = fields[1];
		year = fields[2];
	}
	if (day < 10) {
		day = "0" + day;
	}
	if (month < 10) {
		month = "0" + month;
	}
	if (year == "3000") {
		return "Indefinite";
	}
	return day + "-" + months[month - 1] + "-" + year;
}

var csvHeader = {
	    partNo: "Part No",
	    desc: "Description",
	    startDate: "Start Date",
	    endDate: "End Date",
	    currencyId: "Currency ID",
	    catalogPrice: "Catalog Price",
	    loadingComments: "Audit Note (REQUIRED)"
	};

function cleanUpColumnForCSV(data) {
	if (data == null || data.length < 1) {
		return "";
	}
	//data = ("" + data ).replace(/,/g, ';');
	data = data.replace(/\n/g, ' ');
	return data
}

function downloadTemplate() {
	if (typeof Blob != "undefined") { // Setup the CSV headers
		var csvPrices = [ csvHeader ];

		// Add prices
		var length = jsonMainData.rows.length;
		var curRow = 1;
		for (var rowIndex = 0; rowIndex < length; rowIndex++) {
			var columns = jsonMainData.rows[rowIndex].data;
			if (columns[18] != "true") { // Don't download expired rows
				csvPrices[curRow++] = {
					partNo : columns[2],
					desc : cleanUpColumnForCSV(columns[4]),
					startDate : convertDateForCSV(columns[5]),
					endDate : convertDateForCSV(columns[7]),
					currencyId : columns[9],
					catalogPrice : columns[10],
					loadingComments : ""
				};
			}
		}
		// Convert Object to JSON
		var jsonObject = JSON.stringify(csvPrices);

		var csv = this.convertToCSV(jsonObject);

		var fileName = $v("companyId") + "-" + $v("priceGroupId");
		if ($v("searchTerm")) {
			fileName += "-" + $v("searchTerm");
		}
		fileName += '.csv';

		var blob = new Blob([ csv ], {
			type : 'text/csv;charset=utf-8;'
		});
		if (navigator.msSaveBlob) { // IE 10+
			navigator.msSaveBlob(blob, fileName);
		} else {
			var link = document.createElement("a");
			if (link.download !== undefined) { // feature detection
				// Browsers that support HTML5 download attribute
				var url = URL.createObjectURL(blob);
				link.setAttribute("href", url);
				link.setAttribute("download", fileName);
				link.style.visibility = 'hidden';
				document.body.appendChild(link);
				link.click();
				document.body.removeChild(link);
			}
		}
	} else {
		// saveTextAs(csv, fileName);
	    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_CMSPricelistCSV','650','600','yes');
		document.getCSVform.target='_CMSPricelistCSV';
		document.getCSVform.submit();
	}
}

function onRightclick(rowId, cellId) {
	// Show right-click menu
	toggleContextMenu('rightClickMenu');
}

function showHistory() {
	var loc = "CMSPricelist.do?uAction=showHistory&searchArgument=" + encodeURIComponent(cellValue(currentRow, "catPartNo"));
	loc += "&companyId=" + $v("companyId");
	loc += "&priceGroupId=" + encodeURIComponent($v("priceGroupId"));
	openWinGeneric(loc, "CMSPricelistHistory", "900", "600", "yes", "80", "80", "yes");
}
