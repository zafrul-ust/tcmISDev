/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
var dhxWins = null;

function addItem(itemId, inventoryGroup) {
	var now = new Date();
	parent.document.getElementById("startSearchTime").value = now.getTime();
	$('itemId').value = itemId;
	$('addItemInventoryGroup').value = inventoryGroup;
	$('uAction').value = 'addItemUpdate';
	document.genericForm.target='resultFrame';
	parent.showPleaseWait();
	document.genericForm.submit(); 
}

function showAddItem() {
	
	if(confirm(messagesData.additemcountwarning))
	{
		showWait(messagesData.additem);
		var hub = $v('hub');
		var invGroups = document.getElementsByName("inventoryGroup");
		var loc = "additemtocountmain.do?";
		loc += "hub=" + hub;
		loc += "&uAction=addItem";
		for(var i = 0; i < invGroups.length; i++){
			loc += "&inventoryGroup=" + invGroups.item(i).value;
	        }
		openWinGeneric(loc,"additemtocount","700","500","yes","50","50","20","20","no");
	}
}

function checkAll(checkboxname) {
	var checkall = $("checkAllCounted");
	var rowsNum = mygrid.getRowsNum()*1;	
	var alertCount = 0;

	// Make sure the background render has rendered all rows
	renderAllRows();
	
	if( checkall.checked ) {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if($(cid) != null && !$(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
	}
	else {
		for(var p = 1 ; p < (rowsNum+1) ; p ++) {
			var cid = checkboxname+p;
			if( !$(cid) != null && !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
	}
	return true;  
}

function myResultOnLoadWithGrid(gridConfig) {
	if (!showUpdateLinks){
		parent.document.getElementById("multiUpdateLinks").style["display"] = "none";
		parent.document.getElementById("newCount").style["display"] = "none";
		parent.document.getElementById("multiNewCount").style["display"] = "none";
		parent.document.getElementById("singleUpdateLinks").style["display"] = "none";
	}
	else {
		var totalLines = document.getElementById("totalLines").value;
		if (totalLines < 1) {
			if (hubOnly) {
				parent.document.getElementById("multiNewCount").style["display"] = "";
				parent.document.getElementById("newCount").style["display"] = "none";
				parent.document.getElementById("multiUpdateLinks").style["display"] = "none";
				parent.document.getElementById("singleUpdateLinks").style["display"] = "none";
			}
			else {
				parent.document.getElementById("newCount").style["display"] = "";
				parent.document.getElementById("multiNewCount").style["display"] = "none";
				parent.document.getElementById("singleUpdateLinks").style["display"] = "none";
				parent.document.getElementById("multiUpdateLinks").style["display"] = "none";
			}
		}
		else {
			if (hubOnly) {
				parent.document.getElementById("multiUpdateLinks").style["display"] = "";
				if (showPoNumber) {
					parent.document.getElementById("poNumber").value = "";
					parent.document.getElementById("poNumberSpan").style["display"] = "";
				}
				else {
					parent.document.getElementById("poNumberSpan").style["display"] = "none";
				}
				parent.document.getElementById("newCount").style["display"] = "none";
				parent.document.getElementById("multiNewCount").style["display"] = "none";
				parent.document.getElementById("singleUpdateLinks").style["display"] = "none";
			}
			else {
				parent.document.getElementById("singleUpdateLinks").style["display"] = "";
				if (showPoNumber) {
					parent.document.getElementById("poNumber").value = "";
					parent.document.getElementById("poNumberSpan").style["display"] = "";
				}
				else {
					parent.document.getElementById("poNumberSpan").style["display"] = "none";
				}
				parent.document.getElementById("newCount").style["display"] = "none";
				parent.document.getElementById("multiNewCount").style["display"] = "none";
				parent.document.getElementById("multiUpdateLinks").style["display"] = "none";
			}
		}
	}

	initGridWithGlobal(gridConfig);
	
	/* Display the standard footer message */
	displayGridSearchDuration();
	
	/*
	 * It is important to call this after all the divs have been turned on
	 * or off.
	 */
	setResultFrameSize();
}

/*This is called from the main page to set the grid height.
*/
function setGridHeight(resultFrameHeight) {
	try {
		if (haasGrid !=null) {
			var id=haasGrid.entBox.id;
			var griDiv = document.getElementById(id);
			griDiv.style.height = resultFrameHeight-30 + "px";
		}
	}
	catch(ex) {
      //alert("THis means the grid was not initialized");
	}
}

function validateCountedQuantity(rowId,columnId) {
	if (isValidQuantity(rowId, columnId)) {
		// Check the other requirements
		var count = cellValue(rowId,columnId);
		var expected = cellValue(rowId,"expectedQuantity")*1;
		if (count != expected) {
			cell(rowId, "counted").setValue(true);
		}	
		
		// Was change greater than 25%
		
		if ((expected > 0  && ((count - expected) / expected) * 100 > 25) || expected == 0) {
			if (expected > 0) {
				alert(formatMessage(messagesData.quantitycounted, count, ((count - expected) / expected) * 100, expected) + "\n" + messagesData.doublecheckquantity);				
			}
			else {
				alert(formatMessage(messagesData.quantitycounted, count, "100", expected) + "\n" + messagesData.doublecheckquantity);				
			}
		}
		
		if ("Y" == cellValue(rowId,"issueOnReceipt")) {
			alert(messagesData.issueonreceiptmessage);
		}
	}
}

// When Actual Count is changed, verify it is float and check Counted if it is
function isValidQuantity(rowId, columnId) {
	var count = cellValue(rowId,columnId);
	if (count != null && (count + "").length > 0) {
		aCountedQuantity = document.getElementById("countedQuantity" + rowId);
		if(aCountedQuantity != null && (!isFloat(count,false) || (count.indexOf('.') != -1 && !isFixLenDegit(count.substring(count.indexOf('.')+1,count.length),$v('checkSigDigit'),false)))) {
			// Select the row with the error and grab the cursor
			// there as well
			mygrid.selectRowById(rowId, null, false, false);
			aCountedQuantity.focus();
			alert( messagesData.validvalues + "\n" + cellValue(rowId, "catPartNo") + " - " + messagesData.actualCount );
			return false;
		}
	}
	return true;
	
}

function countedChecked(rowId,columnId) {
	if (!isValidQuantity(rowId, "countedQuantity")) {
		cell(rowId, "counted").setValue(false);
		return false;
	}
	return true;
}

function processItemCount() {
	if(validationforUpdate()) {
  		document.genericForm.target='';
		document.getElementById('uAction').value = 'update';
	
		parent.showPleaseWait();
		
		if (mygrid != null) {
   			mygrid.parentFormOnSubmit(); 
   			// Call this function to send the
			// data from grid back to the server
   		} 
   	
   		document.genericForm.submit();
	}
}

// validate the whole grid
function validationforUpdate() {
	
	var totalRows = mygrid.getRowsNum();
	for (var rowNum = 1; rowNum <= totalRows ;) {
		var start = mygrid.haasGetRowSpanStart(rowNum) + 1;
		var end = mygrid.haasGetRowSpanEndIndex(rowNum);
		var spanDataCount = 0;
		for(var rowSpanNum = start;rowSpanNum <= end;++rowSpanNum)
		{
			var rowIndex = mygrid.getRowIndex(rowSpanNum)+1;
			if (!isValidQuantity(rowIndex, "countedQuantity")) {
	 			return false;
			}
			countedQuantity = $('countedQuantity'+rowIndex);
			if(countedQuantity != null && countedQuantity.value != '')
				spanDataCount++;
			else if(cellValue(rowIndex, "countedQuantity") !='' || cell(rowIndex, "countedQuantity").cell.innerText != '')
					spanDataCount++;

		}
		if(spanDataCount != 0 && spanDataCount != end - start + 1)
		{
			alert(messagesData.missingdisbursementvals);
			return false;
		}
			
		rowNum += end - start + 1;
    }
    
    return true;
}

function startItemCount () {
	var now = new Date();
	parent.document.getElementById("startSearchTime").value = now.getTime();
	$('uAction').value = 'startCount';
	document.genericForm.target='resultFrame';
	document.genericForm.submit(); 
}

function closeItemCount () {
	var now = new Date();
	parent.document.getElementById("startSearchTime").value = now.getTime();
	$('uAction').value = 'closeCount';
	document.genericForm.target='resultFrame';
	document.genericForm.submit(); 
}

function dateChangedValidation () {
	var countDate = Date.parse($('countDate').value.replace("-", " "));
	var rowsNum = mygrid.getRowsNum();
	var messages = "";
	var gridModified = false;

	for (var row = 1; row <= rowsNum ; row++) {
		var startDate = cellValue(row, "startDate");
		var lastDoR = cellValue(row, "lastDateOfReceipt");
		var lastRQD = cellValue(row, "lastReceiptQcDate");
		var lastDC  = cellValue(row, "lastDateCounted");
		var disableLine = false;
		var item;

		if (lastDoR != null && lastDoR.length > 6 && Date.parse(lastDoR) > countDate) {
			disableLine = true;
			item = cellValue(row, "issueOnReceipt") == "Y" ? cellValue(row, "itemId") : cellValue(row, "catPartNo");
			messages += formatMessage(messagesData.receivedaftercurrentcount, item, cellValue(row, "inventoryGroupName")) + "\n";
		}
		if (lastRQD != null && lastRQD.length > 6 && Date.parse(lastRQD) > Date.parse(startDate)) {
			disableLine = true;
			item = cellValue(row, "issueOnReceipt") == "Y" ? cellValue(row, "itemId") : cellValue(row, "catPartNo");
			messages += formatMessage(messagesData.receivedaftercountstart, item, cellValue(row, "inventoryGroupName")) + "\n";
		}
		if (lastDC != null && lastDC.length > 6 && Date.parse(lastDC) > countDate) {
			disableLine = true;
			item = cellValue(row, "issueOnReceipt") == "Y" ? cellValue(row, "itemId") : cellValue(row, "catPartNo");
			messages += formatMessage(messagesData.countedaftercurrentcount, item, cellValue(row, "inventoryGroupName"), lastDC) + "\n";
		}

		if (disableLine) {
			gridModified = true;
			jsonMainData.rows[row - 1].data[0] = 'N';
			jsonMainData.rows[row - 1]["class"] = "grid_black";
		}
		else if (mygrid.rowsAr[row].className.indexOf("grid_black") >= 0) {
			gridModified = true;
			var updatePerm = cellValue(row, "updatePerm");
			jsonMainData.rows[row - 1].data[0] = updatePerm;

			if (cellValue(row, "issueOnReceipt") == "Y") {
				jsonMainData.rows[row - 1]["class"] = "grid_red";
			}
			else {
				jsonMainData.rows[row - 1]["class"] ='';
			}
		}
	}
	
	if (gridModified) {
		for (var row = 1; row <= rowsNum ; row++) {
			jsonMainData.rows[row - 1].data[10] = cellValue(row, "countedQuantity");
			jsonMainData.rows[row - 1].data[11] = cellValue(row, "counted").indexOf("true") >= 0 ? true : false;
		}
		mygrid.clearAll();
		mygrid.parse( jsonMainData ,"json");
		setResultFrameSize();
	}
	if (messages.length > 0) {
		alert(messagesData.linesexcluded + "\n\n" + messages);
	}

	return true;
}

function showWait(message){
	$("transitLabel").innerHTML = message;
	
	var transitDailogWin = $("transitDailogWin");
	transitDailogWin.style.display="";

	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}

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

function stopShowingWait() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
	return true;
}

