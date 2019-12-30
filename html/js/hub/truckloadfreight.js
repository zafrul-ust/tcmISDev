var resizeGridWithWindow = true;
var beanGrid;
var selectedRowId = "";
var consolidationWithMultiple = new Array();
var children = new Array();

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

function myOnLoad() {
	//close transit window
	parent.closeTransitWin();
	
	var totalLines = $("totalLines").value;
	if (totalLines > 0) {
		document.getElementById('freightTlTrackingViewBean').style["display"]="";
		initializeGrid();
		//looping thru table to see if any row is "Multi"
		//the reason i starts with 1 is because table row ID starts with 1 not 0 (zero)
		for(var i = 1;i <= beanGrid.getRowsNum();i++){
			if (cellValue(i,"orderCount") != 'Single') {
				consolidationWithMultiple[consolidationWithMultiple.length] = cellValue(i,"consolidationNumber");
			}
		}

		if(parent.$("hasEditPermission").value == 'true') {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}

	}else {
		  document.getElementById("freightTlTrackingViewBean").style["display"] = "none";
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
} //end of method

function submitUpdate() {
  if (validateForm()) {
	  parent.showTransitWin();
	  $("uAction").value = 'update';
	  beanGrid.parentFormOnSubmit();
	  document.genericForm.submit();
  }else {
	  return false;
  }
} //end of method

function submitCancel() {
  if (validateFormForCancel()) {
	  parent.showTransitWin();
	  $("uAction").value = 'cancel';
	  beanGrid.parentFormOnSubmit();
	  document.genericForm.submit();
  }else {
	  return false;
  }
} //end of method

function validateFormForCancel() {
	var count = 0;

	for(var i = 1;i <= beanGrid.getRowsNum();i++){
		if(cellValue(i,"ok")+'' == 'true')	count++;
	}

	if (count == 0) {
		alert(messagesData.noRowSelected);
		return false;
	}else 
		return true;
}

function validateForm(){
	var hasSelectedRow = false;
	var qtyNotInteger = false;
	var scheduledShipDateNotDate = false;
	var trackingNumberNotValid = false;
	var totalQtyColl = new Array();
	var errorMsg = "";

	for(var i = 1;i <= beanGrid.getRowsNum();i++){
		if(cellValue(i,"ok")+'' == 'true') {
			//if the transportation mode = TL then user has to enter a tracking number
			if (cellValue(i,"transportationMode") == 'TL') {
				if(cellValue(i,"carrierCode").length == 0) {
					if (errorMsg.length > 1) {
						errorMsg += "\n";
					}
					errorMsg += messagesData.selectCarrier;
				}
				if(cellValue(i,"trackingNumber").length == 0) {
					trackingNumberNotValid = true;
					if (errorMsg.length > 1) {
						errorMsg += "\n";
					}
					errorMsg += messagesData.validValues+" "+messagesData.trackingNumber;
				}
			}
			if (cellValue(i,"scheduledShipDate").length == 0) {
				scheduledShipDateNotDate = true;
				if (errorMsg.length > 1) {
					errorMsg += "\n";
				}
				errorMsg += messagesData.mustBeADate.replace(/[{]0[}]/g,messagesData.scheduledShipDate);
			}
			//check to make sure user entered an integer for qty
			if(!isInteger(cellValue(i,"quantity"))) {
				qtyNotInteger = true;
				if (errorMsg.length > 1) {
					errorMsg += "\n";
				}
				errorMsg += messagesData.mustBeAnInteger.replace(/[{]0[}]/g,messagesData.qty);
			}
			
			if (qtyNotInteger || trackingNumberNotValid || scheduledShipDateNotDate) {
				break;
			}

			//need to deal with splitted row qty has to sum up to original qty
			var dataKey = cellValue(i,"consolidationNumber");
			if (dataKey == null || dataKey.length == 0) {
				dataKey = cellValue(i,"originalConsolidationNumber");
			}
			if (totalQtyColl[dataKey] == null) {
				totalQtyColl[dataKey] = {
					originalQty: 	cellValue(i,"originalQuantity"),
					sumOfLineQty:  cellValue(i,"quantity")
				};
			}else {
				var totalQty = totalQtyColl[dataKey].sumOfLineQty*1 + cellValue(i,"quantity")*1;
				totalQtyColl[dataKey].sumOfLineQty = totalQty;
			}

			hasSelectedRow = true;
			//make sure to calculate shipping weight before submitting data to server
			calculateShippingWeight(i);
		}
	}

	//checking total qty for each consolidation number
	for (key in totalQtyColl) {
		if (totalQtyColl[key].originalQty == null || totalQtyColl[key].originalQty.length == 0) {
			if (errorMsg.length > 1) {
				errorMsg += "\n";
			}
			errorMsg += messagesData.selectConsolidation.replace(/[{]0[}]/g,key);
			continue;	//the reason I put a continue here is that if the original consolidation is not picked then the original qty is empty.  Need no further auditting.
		}
		if (totalQtyColl[key].originalQty*1 != totalQtyColl[key].sumOfLineQty*1) {
			if (errorMsg.length > 1) {
				errorMsg += "\n";
			}
			errorMsg += messagesData.totalQtyNotEqual.replace(/[{]0[}]/g,messagesData.consolidationNumber).replace(/[{]1[}]/g,key).replace(/[{]2[}]/g,totalQtyColl[key].originalQty);
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
}  //end of method

function initializeGrid(){
 	beanGrid = new dhtmlXGridObject('freightTlTrackingViewBean');
 	initGridWithConfig(beanGrid,config,-1,true);
 	if( typeof( jsonMainData ) != 'undefined' ) {
   	beanGrid.parse(jsonMainData,"json");
 	}
	beanGrid.attachEvent("onRowSelect",selectRow);
	beanGrid.attachEvent("onRightClick",selectRow);
}

function selectRow() {
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	if( ee != null ) {
   	if( ee.button != null && ee.button == 2 ) rightClick = true;
   	else if( ee.which == 3  ) rightClick = true;
   }
	beanGrid.selectRowById(rowId0,null,false,false);
	
	if(parent.$("hasEditPermission").value != 'true') return;
	if (selectedRowId.length != 0 && selectedRowId != rowId0) {
		calculateShippingWeight(selectedRowId);
	}
	selectedRowId = rowId0;
	calculateShippingWeight(selectedRowId);

	if( !rightClick ) return;
	//if right mouse clicked
	var splitOk = true;
	var currentConsolidationNumber = cellValue(selectedRowId,"consolidationNumber");
	for(var i=0; i < consolidationWithMultiple.length;i++) {
		if (consolidationWithMultiple[i] == currentConsolidationNumber) {
			splitOk = false;
			break;
		}
	}
	//check haz mat
	var showHazMat = (cellValue(selectedRowId,"hazardousFlag") == 'Y');
	if (splitOk && showHazMat) {
		toggleContextMenu("splitLineHazmatInfo");
	}else if (splitOk) {
		toggleContextMenu("splitLine");
	}else if (showHazMat) {
		toggleContextMenu("hazmatInfo");
	}else {
		toggleContextMenu("empty");
	}
} //end of method

function showHazmatInfo() {
	var tmpConsolidationNumber = cellValue(selectedRowId,"consolidationNumber");
	if (tmpConsolidationNumber == null) {
		tmpConsolidationNumber = cellValue(selectedRowId,"originalConsolidationNumber");
	}
	parent.showHazmatInfo(tmpConsolidationNumber);
}

function calculateShippingWeight(currentRowId) {
	//calculate ONLY if Single
	if (cellValue(currentRowId,"orderCount") == 'Single') {
		var qty = cellValue(currentRowId,"quantity");
		if (qty == null || qty.length == 0 || !isInteger(qty)) {
			beanGrid.cells(currentRowId,beanGrid.getColIndexById("shippingWeight")).setValue("");
			beanGrid.cells(currentRowId,beanGrid.getColIndexById("palletCount")).setValue("");
			return;
		}else {
			var itemWeight = cellValue(currentRowId,"itemWeight");
			if (itemWeight == null || itemWeight.length == 0 ) {
				itemWeight = 0;
			}
			var weightUnit =cellValue(currentRowId,"weightUnit");
			beanGrid.cells(currentRowId,beanGrid.getColIndexById("shippingWeight")).setValue(((qty*1)*(itemWeight*1)).toFixed(2)+" "+weightUnit);
			var itemsPerPallet =cellValue(currentRowId,"itemsPerPallet");
			beanGrid.cells(currentRowId,beanGrid.getColIndexById("palletCount")).setValue(((qty*1)/(itemsPerPallet*1)).toFixed(2));
		}
	}
}

function searchCarrier(inventoryGroup) {
 	var loc = "truckloadcarriersearchmain.do?uAction=&inventoryGroup="+inventoryGroup;
 	children[children.length] = openWinGeneric(loc,"truckloadCarrierSearch","510","500","yes","50","50","20","20","no");
}

function carrierSelected(carrierCode,carrierName,transMode) {
	closeAllchildren();
	beanGrid.cells(selectedRowId,beanGrid.getColIndexById("carrierNameDisplay")).setValue(carrierName+" / "+carrierCode+" / "+transMode);
	beanGrid.cells(selectedRowId,beanGrid.getColIndexById("carrierName")).setValue(carrierName);
	beanGrid.cells(selectedRowId,beanGrid.getColIndexById("carrierCode")).setValue(carrierCode);
	beanGrid.cells(selectedRowId,beanGrid.getColIndexById("transportationMode")).setValue(transMode);
}

function splitLine() {
	if(beanGrid == null) {
		return false;
	}

	if( selectedRowId == null || selectedRowId == "") {
		alert(messagesData.noRowSelected);
		return false;
	}

	var ind = beanGrid.getRowsNum();
	var rowid = ind*1+1;
	var selectedIndex = beanGrid.getRowIndex(selectedRowId)*1 + 1;
	var thisrow = beanGrid.addRow(rowid,"",selectedIndex);
	beanGrid.selectRow(beanGrid.getRowIndex(rowid),false,false,true);
	var count = 0;
	beanGrid.cells(rowid,count++).setValue('Y');
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("carrierNameDisplay")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("carrierName")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("carrierSearch")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("carrierCode")).getValue());
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("scheduledShipDate")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("requiredDatetime")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("shipFrom")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("shipTo")).getValue());
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("export")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("transportationMode")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("transportationPriority")).getValue());
	//beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("hazardousFlag")).getValue());
	beanGrid.cells(rowid,count++).setValue('Y');
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("itemsPerPallet")).getValue());
	//edit qty
	beanGrid.cells(rowid,count++).setValue('Y');
	beanGrid.cells(rowid,count++).setValue('');
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("orderCount")).getValue());
	//original consolidation number
	var consolidationNumber = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("consolidationNumber")).getValue();
	//the reason for this is users can split line from splitted line
	if (consolidationNumber == null || consolidationNumber.length == 0) {
		consolidationNumber = beanGrid.cells(selectedRowId,beanGrid.getColIndexById("originalConsolidationNumber")).getValue();
	}
	beanGrid.cells(rowid,count++).setValue(consolidationNumber);
	//original qty
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("originalQuantity")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("unitPalletWeight")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("weightUnit")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("itemWeight")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("inventoryGroup")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("originalShippingWeight")).getValue());
	beanGrid.cells(rowid,count++).setValue('Y');
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("originalPalletCount")).getValue());
	beanGrid.cells(rowid,count++).setValue(beanGrid.cells(selectedRowId,beanGrid.getColIndexById("shippingUnits")).getValue());
	//setting split line flag for line that is being split
	beanGrid.cells(selectedRowId,beanGrid.getColIndexById("splitLine")).setValue('Y');

	parent.document.getElementById("updateResultLink").style["display"] = "";

	//check new row
	$("ok"+rowid).checked = true;
	updateHchA("ok"+rowid);
	//check selected row as well
	if (!$("ok"+selectedRowId).checked) {
		$("ok"+selectedRowId).checked = true;
		updateHchA("ok"+selectedRowId);
	}
	//set selectedRowId to current row
	selectedRowId = rowid;

} //end of method