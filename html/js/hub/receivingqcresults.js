var children = new Array();

/************************************NEW***************************************************/
var beanGrid;
var selectedRowId;
windowCloseOnEsc = true;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

var rowSpanCols = [0,1,2,3,10,11,12,22,23,25,26,27,28,29,30,31,32,33,
                   34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,
                   51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74];

var incomingTestOption = '';

function selectRow() {
    // to show menu directly
    rightClick = false;
    rowId0 = arguments[0];
    colId0 = arguments[1];
    ee     = arguments[2];

    selectedRowId = rowId0;
    if( ee != null ) {
        if( ee.button != null && ee.button == 2 ) rightClick = true;
        else if( ee.which == 3  ) rightClick = true;
    }
    columnId = beanGrid.getColumnId(colId0);

    if( columnId == 'bin' ) {
        if( cellValue(selectedRowId,"binPermission") == 'Y' ) {
          loadBins(cellValue(selectedRowId,"itemId"),cellValue(selectedRowId,"branchPlant"),selectedRowId);
        return;
       }
    }

    if( columnId == 'ok' ) {
       checkChemicalReceivingQcInput(rowId0);
    }

    //do right mouse click
    if (rightClick) {
        var incomingTesting = cellValue(selectedRowId,"incomingTesting");
        //incoming lab testing get data
        if (incomingTesting == 'Y') {
            loadRightClickData(selectedRowId);
        }else {
            incomingTestOption = '';
            buildRightClickOption();
        }
    } //end of right mouse click
}   //end of method

function buildRightClickOption() {
    var vitems = new Array();
    var docType = cellValue(selectedRowId,"docType");
    var transferRequestId = cellValue(selectedRowId,"transferRequestId");
    var itemId =  cellValue(selectedRowId,"itemId");
    var customerRmaId = cellValue(selectedRowId,"customerRmaId");
    var radianPo = cellValue(selectedRowId,"radianPo");

    if (docType == 'IT' && transferRequestId.trim().length > 0)
        vitems[vitems.length] = "text=" + messagesData.potitle + ";url=javascript:showrecforinvtransfrQc("+selectedRowId+");";

    vitems[vitems.length] = "text=" + messagesData.itemtitle + ";url=javascript:showPreviousReceivedQc("+selectedRowId+");";
    vitems[vitems.length] = "text=" + messagesData.receiptspecs + ";url=javascript:receiptSpecs("+selectedRowId+");";
    vitems[vitems.length] = "text=" + messagesData.viewaddreceipts + ";url=javascript:showProjectDocuments("+selectedRowId+");";
    if (radianPo.trim().length > 0) {
        if (docType != 'IT' && !disabledPoLink){
				vitems[vitems.length] = "text=" + messagesData.viewpurchaseorder + ";url=javascript:showRadianPo("+selectedRowId+");";
        }
        vitems[vitems.length] = "text=" + messagesData.receivingchecklist + ";url=javascript:showReceivingJacket("+selectedRowId+");";
    }

    vitems[vitems.length] = 'text=Add Bin;url=javascript:checkaddbins();';
    vitems[vitems.length] = 'text='+messagesData.receivingqcdchecklist+';url=javascript:openReceivingQcDetailView('+selectedRowId+');';

    if (docType == 'IA' && customerRmaId != null)
        vitems[vitems.length] = "text=" + messagesData.viewrma + ";url=javascript:viewRMA("+selectedRowId+");";

    if (itemId.trim().length > 0 && !disabledItemNotes)
        vitems[vitems.length] = "text=" + messagesData.itemnotes + ";url=javascript:showItemNotes("+selectedRowId+");";
    // TODO: Add the fuction for this right click
    /*
     * if (customerRmaId.trim().length > 0) vitems[vitems.length ] =
     * "text="+messagesData.viewcustomerreturnrequest+";url=javascript:showCustomerReturnRequest();";
     */

    //incoming lab testing add data to menu
    if (incomingTestOption.length > 0) {
        vitems[vitems.length] = incomingTestOption;
    }

    replaceMenu('receivingqcMenu', vitems);
    toggleContextMenu('receivingqcMenu');
} //end of method

function loadRightClickData(rowId) {
    var url = "rightmouseclickmenu.do?userAction=getIncomingTestRequired&receiptId="+cellValue(selectedRowId,"receiptId");
    callToServer(url+"&callback=processRightClickMenu");
}   //end of method

function processRightClickMenu(xmldoc) {
    incomingTestOption = '';
    if (xmldoc.incomingTestRequired == 'Y') {
        incomingTestOption = "text=" + messagesData.startmarstest + ";url=javascript:startMARStest("+selectedRowId+");";
    }else if (xmldoc.testRequestId.length > 0) {
        incomingTestOption = "text=" + messagesData.showMarsDetail + ";url=javascript:showMarsDetail("+xmldoc.testRequestId+");";
    }
    buildRightClickOption();
}   //end of method

function startMARStest(selectedRowId) {
    var receiptId = cellValue(selectedRowId,"receiptId");
    var loc = "testrequestform.do?uAction=create&receiptId="+receiptId;
    
    if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/haas/" + loc;
    
    try {
        parent.parent.openIFrame("testrequest" , loc, ""+messagesData.marsDetail+" "+ receiptId + "","","N");
    }catch (ex) {
        children[children.length] = openWinGeneric(loc, "testrequest" , "900", "600", "yes", "80", "80", "yes");
    }
} //end of method

function showMarsDetail (testRequestId) {
    tabId = "testRequestDetail_" + testRequestId,
	queryString = "?uAction=search&testRequestId=" + testRequestId,
	destination = "/tcmIS/haas/testrequestform.do" + queryString;
	try {
        parent.parent.openIFrame(tabId,destination, messagesData.marsDetail + " " + testRequestId,'','N');
    }catch (ex) {
		windowName = "testRequestDetail_" + requestId;
		children[children.length] = openWinGeneric(destination, windowName.replace('.', 'a'), "1000", "600", "yes", "50", "50", "20", "20", "no");
	}
} //end of method

function lotStatusChanged(rowId) {
    var selectedLotStatus = cellValue(rowId,"lotStatus");
    for (var i = 0; i < lotStatus.length; i++) {
        if (cellValue(rowId,"incomingTesting") == 'Y') {
            if (selectedLotStatus == lotStatus[i].value && lotStatus[i].pickable == 'Y' && cellValue(rowId,"labTestComplete") == 'N') {
                beanGrid.cellById(rowId, beanGrid.getColIndexById("lotStatus")).setValue(cellValue(rowId,"origLotStatus"));
                alert(messagesData.noCompletedIncomingTest);
                break;
            }
        }
    }
} //end of method

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
}   //end of method

    function checkAllChemicalLines() {
		var rowsNum = beanGrid.getRowsNum();
				
		for ( var p = 1; p < (rowsNum + 1); p++) {
			 
			  if(validateLine(p) == false)
			  {
				  return false;
			 }
			else
			{
			  return true;
			}
		}
	
	}
	
	function submitConfirm() {
		if(checkAllChemicalLines())
		{
			$('userAction').value = 'confirm';
			parent.showPleaseWait(); 

			if (beanGrid != null)
				beanGrid.parentFormOnSubmit(); 

			document.genericForm.submit(); // back to server
		}
	}
	
	
	function validateLine(rowId) {
		var receiptId = cellValue(rowId,"receiptId");
		var errorMessage = messagesData.forreceipt + " " + receiptId + " "
				+ messagesData.validvalues + " \n\n";
		var errorCount = 0;
		   var ok = cellValue(rowId,"ok");
		   //alert(ok);
		     if (ok)
	          {
	        	  var lotStatus = cellValue(rowId,"lotStatus");
	        	  
				 var qualityControlItem = cellValue(rowId,"qualityControlItem");
					if (lotStatus.trim() == "Incoming") {
						errorMessage = errorMessage + " " + messagesData.lotstatus
								+ " " + messagesData.cannotbe + " "
								+ messagesData.incoming + ". \n";
						errorCount = 1;
					} 
					else if (lotStatus.length == 0 && qualityControlItem != 'N') {
						var selecelemet = lotStatus.selectedIndex;
						var testelementtext = lotStatus.options[selecelemet].text;

						errorMessage = errorMessage + " "
								+ messagesData.nopermissionstoqcstatus + " "
								+ testelementtext + ".";
						errorCount = 1;
					}
					if (errorCount > 0) {
						alert(errorMessage);
						cell(rowId,"ok").setChecked(false);
						return false;
					}
	          }
			 else
			 {
				 cell(rowId,"ok").setChecked(false);
				 return true;
			}
			
	  }

	function checkChemicalReceivingQcInput(selectedRowId) {
		
			
		var receiptId = cellValue(selectedRowId,"receiptId");
		var errorMessage = messagesData.forreceipt + " " + receiptId + " "
				+ messagesData.validvalues + " \n\n";
		var errorCount = 0;
		var warnCount = 0;
		var expireError = false;

		var ok = cellValue(selectedRowId,"ok");
				
		if (ok) {
			var shelfLifeBasis = cellValue(selectedRowId,"receiptShelfLifeBasis");
			
			var mfgLot = cellValue(selectedRowId,"mfgLot");
			if (mfgLot.trim().length == 0) {
				errorMessage = errorMessage + " " + messagesData.mfglot + ". \n";
				errorCount = 1;
			}
			
			var supplierShipDate = dateToIntString(cellValue(selectedRowId,"vendorShipDate"));
			var dateOfReceipt = dateToIntString(cellValue(selectedRowId,"dateOfReceipt"));
			var dateOfManufacture = dateToIntString(cellValue(selectedRowId,"dateOfManufacture"));
			var dateOfShipment = dateToIntString(cellValue(selectedRowId,"dateOfShipment"));
			
						
			if (supplierShipDate.length > 0
					&& (supplierShipDate > dateOfReceipt || supplierShipDate < dateOfManufacture)) {
				errorMessage = errorMessage + " " + messagesData.actsupshpdate
						+ "\n";
				errorCount = 1;
			}

			 if (shelfLifeBasis == "M" && dateOfManufacture.length <= 0) {
				errorMessage = errorMessage + " " + messagesData.dom + "\n";
				errorCount = 1;
			}
			else if (shelfLifeBasis == "S" && dateOfShipment.length <= 0) {
				errorMessage = errorMessage + " " + messagesData.dos + "\n";
				errorCount = 1;
			}
			else if (shelfLifeBasis == "R" && dateOfReceipt.length <= 0) {
				errorMessage = errorMessage + " " + messagesData.dor + "\n";
				errorCount = 1;
			}
			 		
			if (dateOfManufacture.length > 0
					&& dateOfShipment.length > 0
					&& dateOfManufacture > dateOfShipment) {
				errorMessage = errorMessage + " " + messagesData.shipbeforemanufacture + "\n";
				errorCount = 1;
			}
           
			var expireDateString = cellValue(selectedRowId,"expireDateStr");
			if (expireDateString.trim().length == 0) {
				errorMessage = errorMessage + " " + messagesData.expiredate + "\n";
				errorCount = 1;
				//expireDateString.value = "";
				expireDateError = true;
			}
			 
			try {
				var lotStatusValue = cellValue(selectedRowId,"lotStatus");
			} catch (ex) {
				lotStatusValue = "";
			}
		 			
		}
		
		if (errorCount > 0) {
			alert(errorMessage);
			//cell(selectedRowId,"ok").setChecked(false);
			return false;
		}

		// This is just a warning
		var expireDate = cellValue(selectedRowId,"expireDate");
		var minimumExpireDate = cellValue(selectedRowId,"minimumExpireDate");
		var receiptId = cellValue(selectedRowId,"receiptId");
		
		/*if (action == 'warning' && ok.checked == true
				&& expireDate < minimumExpireDate) {
			alert(messagesData.expdatelessthanminexpdate.replace(/[{]0[}]/g,
					receiptId));
		}*/
		var updateStatus = cellValue(selectedRowId,"updateStatus");
		
		if(updateStatus == 'readOnly')
		{
			checkLabelQuantity(selectedRowId);
		}

		return true;
	}
	
	function checkaddbins(){
		{
			var loc = "showhubbin.do?callbackparam="+selectedRowId+"&branchPlant=" + $v('hub') + "&userAction=showBins";
			
			if ($v("personnelCompanyId") == 'Radian') 
				  loc = "/tcmIS/hub/" + loc;
			
			var winname = null;
			try {
				winname = openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
				children[children.length] = winname;
				} catch (ex) {
//				openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
			}
		}
	}

	function checkLabelQuantity(selectedRowId) {
		var errorMessage = " " + messagesData.validvalues + " \n\n";
		var warningMessage = messagesData.alert + " \n\n";
		var errorCount = 0;
		var warnCount = 0;

		var ok = cellValue(selectedRowId,"ok");
		if (ok) {
			
				var labelQuantity = cellValue(selectedRowId,"labelQuantity");
				if (!(isInteger(labelQuantity))
						|| labelQuantity * 1 == 0) {
					errorMessage = errorMessage + " " + messagesData.labelquantity
							+ ". \n";
					errorCount = 1;
					cell(selectedRowId,"labelQuantity").setValue("");
				}
				
				if (errorCount > 0) {
					alert(errorMessage);
					cell(selectedRowId,"ok").setChecked(false);
				}
			
		}
				
	}
	
	function showreceivingpagelegend() {
		try {
			children[children.length] = openWinGeneric(
					"/tcmIS/help/receivingpagelegend.html", "receivingpagelegend",
					"290", "300", "no", "50", "50");
		} catch (ex) {
			openWinGeneric("/tcmIS/help/receivingpagelegend.html",
					"receivingpagelegend", "290", "300", "no", "50", "50");
		}
	}
	
	function unitLabelPartNumber(rowNumber) {
		var unitLabelPrinted = document.getElementById("unitLabelPrinted"
				+ rowNumber + "");
		if (unitLabelPrinted.checked) {
			var itemId = document.getElementById("itemId" + rowNumber + "");
			var branchPlant = document.getElementById("branchPlant" + rowNumber
					+ "");
			var inventoryGroup = document.getElementById("inventoryGroup"
					+ rowNumber + "");

			var loc = "unitlabelpartnumber.do?&rowNumber=" + rowNumber;
			loc = loc + "&itemId=" + itemId.value;
			loc = loc + "&hub=" + branchPlant.value;
			loc = loc + "&inventoryGroup=" + inventoryGroup.value;
			
			if ($v("personnelCompanyId") == 'Radian') 
				  loc = "/tcmIS/hub/" + loc;
			
			try {
				children[children.length] = openWinGeneric(loc,
						"terminal_root_cause", "500", "300", "no");
			} catch (ex) {
				openWinGeneric(loc, "terminal_root_cause", "500", "300", "no");
			}
		}
	}
	
	
	function changeMlItem() {
		var checkedCount = 0;
		var selectedItem = $("selectedItem");
		if (checkMlItemInput())/* selectedItem.value.trim().length > 0 && ) */
		{
			if (selectedItem.value.trim().length > 0) {
				var receiptsList = "";
				var totalRecords = $("totalLines");
				for (j = 0; j < (totalRecords.value * 1); j++) {
					var receiptId = "";
					receiptId = $("receiptId" + (j) + "");
					currentcheckBox1 = $("ok" + (j) + "");
					itemType = $("itemType" + (j) + "");
					newChemRequestId = $("newChemRequestId" + (j) + "");

					if (currentcheckBox1 && itemType.value == "ML"
							&& newChemRequestId.value.trim().length == 0) {
						if (checkedCount > 0) {
							receiptsList += ','
						}
						receiptsList += receiptId.value;
						checkedCount++
					}
				}

				// var labelReceipts =
				// document.getElementById("labelReceipts").value;
				receiptsList = receiptsList.replace(/,/gi, "%2c");
				if (receiptsList.trim().length > 0) {
					var loc = "receivingitemsearchmain.do?receipts="
							+ receiptsList + "";
					var hubNumber = document.getElementById("sourceHub").value;
					loc = loc + "&hubNumber=" + hubNumber;
					loc = loc + "&listItemId=" + $("selectedItem").value;
					loc = loc + "&inventoryGroup="
							+ $("selectedInventoryGroup").value;
					loc = loc + "&catPartNo=" + $("selectedCatPartNo").value;
					loc = loc + "&catalog=" + $("selectedCatalogId").value;
					loc = loc + "&catalogCompanyId="
							+ $("selectedCatalogCompanyId").value;

					if ($v("personnelCompanyId") == 'Radian') 
						  loc = "/tcmIS/hub/" + loc;
					
					try {
						children[children.length] = openWinGeneric(loc,
								"changeItem", "800", "400", "yes", "80", "80");
					} catch (ex) {
						openWinGeneric(loc, "changeItem", "800", "400", "yes",
								"80", "80");
					}

				}
			}
		}
	}

	function checkMlItemInput() {
		var noLinesChecked = 0;
		var rowNumber = "";
		// var currentcheckBox = $("ok"+rowNumber+"");
		var totalRecords = document.getElementById("totalLines").value;
		
		var selectedItem = $("selectedItem");
		// var lineitemID = $("itemId"+rowNumber+"");

		var allClear = 0;
		var finalMsgt;
		finalMsgt = messagesData.cannotselectreceiptwith + " \n\n";

		for (j = 0; j < totalRecords; j++) {
			var lineitemID1 = "";
			lineitemID1 = $("itemId" + (j) + "");
			itemType = $("itemType" + (j) + "");
			currentcheckBox1 = $("ok" + (j) + "");
			newChemRequestId = $("newChemRequestId" + (j) + "");

			if (currentcheckBox1 && itemType.value == "ML") {
				if (noLinesChecked == 0) {
					lineitemID = $("itemId" + (j) + "");
					rowNumber = j;
				}

				noLinesChecked++;
				if (lineitemID.value != lineitemID1.value) {
					if (allClear == 0) {
						finalMsgt = finalMsgt + messagesData.differentmlitem + "\n";
					}
					allClear += 1;
				} else if (newChemRequestId.value.trim().length > 0) {
					if (allClear == 0) {
						finalMsgt = finalMsgt + " "
								+ messagesData.pendingnewchemrequest + " "
								+ newChemRequestId.value + "\n";
					}
					allClear += 1;
				}
			}

		}

		if (noLinesChecked == 0) {
			selectedItem.value = "";
		}

		if (allClear < 1) {
			if (noLinesChecked != 0) {
				selectedItem.value = $("itemId" + rowNumber + "").value;
				$("selectedInventoryGroup").value = $("inventoryGroup" + rowNumber
						+ "").value;
				$("selectedCatalogId").value = $("catalogId" + rowNumber + "").value;
				$("selectedCatPartNo").value = $("catPartNo" + rowNumber + "").value;
				$("selectedCatalogCompanyId").value = $("catalogCompanyId"
						+ rowNumber + "").value;
			}
			return true;
		} else {
			alert(finalMsgt);
			return false;
		}
	}
	
	function showreceivingpagelegend() {
		try {
			children[children.length] = openWinGeneric(
					"/tcmIS/help/receivingpagelegend.html", "receivingpagelegend",
					"290", "300", "no", "50", "50");
		} catch (ex) {
			openWinGeneric("/tcmIS/help/receivingpagelegend.html",
					"receivingpagelegend", "290", "300", "no", "50", "50");
		}
	}
	
	function showItemNotes() {
		
		var itemId = cellValue(selectedRowId,"itemId");
		var loc = "edititemnotes.do?itemId=" + itemId;
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/supply/" + loc;
		
		var winId = 'showItemNotes';

		children[children.length] = openWinGeneric(loc, winId.replace('.', 'a'),
				"800", "600", "yes", "50", "50", "20", "20", "no");
	}
	
	function viewRMA() {
		
		var customerRmaId = cellValue(selectedRowId,"customerRmaId");

		var loc = "customerreturnrequest.do?action=search&rmaId="
				+ customerRmaId;// +"&lineItem="+lineItem+"&prNumber="+prNumber;
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/distribution/" + loc;

		try {
			parent.parent.openIFrame("showcustomerreturnrequest" + customerRmaId
					+ "", loc, "" + messagesData.customerreturnrequest + " "
					+ customerRmaId + "", "", "N");
		} catch (ex) {
			openWinGeneric(loc, "showcustomerreturnrequest", "900", "600", "yes",
					"80", "80", "yes");
		}
	}
	
		
	function showPreviousReceivedQc(rowNumber) {
		var itemId = cellValue(selectedRowId,"itemId");
		var branchPlant = $('hub');
		var inventoryGroup = cellValue(selectedRowId,"inventoryGroup");
				
		var loc = "showreceivedreceipts.do?itemId=" + itemId + "&hub="
				+ branchPlant.value + "&inventoryGroup=" + inventoryGroup
				+ "&approved=Y";		
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc, "Previous_Receiving",
					"700", "450", "yes")
		} catch (ex) {
			openWinGeneric(loc, "Previous_Receiving", "700", "450", "yes");
		}
	}
	
	function showrecforinvtransfrQc(selectedRowId) {
		var transferRequestId = cellValue(selectedRowId,"transferRequestId");
		
		var loc = "transfertransactions.do?transferRequestId=" + transferRequestId;
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc,
					"Previous_Transfer_Transactions", "700", "400", "yes");
		} catch (ex) {
			openWinGeneric(loc, "Previous_Transfer_Transactions", "700", "400",
					"yes");
		}

	}
	
	function sendTerminalRootCauseValues() {
		rowNumber = document.getElementById("rowNumber").value;
		rootCause = document.getElementById("rootCause");
		rootCauseCompany = document.getElementById("rootCauseCompany");
		rootCauseNotes = document.getElementById("rootCauseNotes");

		lotStatusRootCause = opener.document.getElementById("lotStatusRootCause"
				+ rowNumber + "");
		lotStatusRootCause.value = rootCause.value;

		responsibleCompanyId = opener.document
				.getElementById("responsibleCompanyId" + rowNumber + "");
		responsibleCompanyId.value = rootCauseCompany.value;

		lotStatusRootCauseNotes = opener.document
				.getElementById("lotStatusRootCauseNotes" + rowNumber + "");
		lotStatusRootCauseNotes.value = rootCauseNotes.value;

		window.close();
	}
	
	function checkReceiptStatus(rowNumber) {
		var lotStatus = document.getElementById("lotStatus" + rowNumber + "");
		var origlotStatus = document.getElementById("origlotStatus" + rowNumber + "");
		var qualityControlItem = $v("qualityControlItem" + rowNumber+ "");
		
		if (lotStatus.value.length == 0 && qualityControlItem != 'N') {
			var selecelemet = lotStatus.selectedIndex;
			var testelementtext = lotStatus.options[selecelemet].text;
			if (origlotStatus == null || origlotStatus.value != testelementtext) {
				alert(messagesData.nopermissiontochangestatus + " "
						+ testelementtext + ".")
			}

			i = 0;
			while (i < lotStatus.length) {
				var elementtext = lotStatus.options[i].text;

				if (elementtext == origlotStatus.value) {
					lotStatus.selectedIndex = i;
				}
				i += 1;
			}
		} else {
			if (lotStatus.value == "Customer Purchase"
					|| lotStatus.value == "Write Off Requested"
					|| lotStatus.value == "3PL Purchase") {
				loc = "terminalstatusrootcause.do?lotStatus=";
				loc = loc + lotStatus.value + "&rowNumber=" + rowNumber;
				
				if ($v("personnelCompanyId") == 'Radian') 
					  loc = "/tcmIS/hub/" + loc;
				
				try {
					children[children.length] = openWinGeneric(loc,
							"terminal_root_cause", "500", "300", "no");
				} catch (ex) {
					openWinGeneric(loc, "terminal_root_cause", "500", "300", "no");
				}
			} else {
				lotStatusRootCause = document.getElementById("lotStatusRootCause"
						+ rowNumber + "");
				lotStatusRootCause.value = "";

				responsibleCompanyId = document
						.getElementById("responsibleCompanyId" + rowNumber + "");
				responsibleCompanyId.value = "";

				lotStatusRootCauseNotes = document
						.getElementById("lotStatusRootCauseNotes" + rowNumber + "");
				lotStatusRootCauseNotes.value = "";
			}
		}
	}
	
	function reverseReceipt(rowNumber) {
		var found = false;
		var receiptId = document.getElementById("receiptId" + rowNumber + "");

		if (receiptId.value.trim().length > 0) {
			var loc = "showreversereceipt.do?receiptId=" + receiptId.value;
			
			if ($v("personnelCompanyId") == 'Radian') 
				  loc = "/tcmIS/hub/" + loc;
			
			try {
				children[children.length] = openWinGeneric(loc,
						"Reverse_Receiving", "300", "150", "no")
			} catch (ex) {
				openWinGeneric(loc, "Reverse_Receiving", "300", "150", "no")
			}
			return true;
		}
		return false;
	}
	
	function submitMainPage() {
		opener.parent.document.getElementById("userAction").value = 'search'; 
		opener.parent.showPleaseWait();
		opener.parent.document.genericForm.submit();	
	    window.close();
	}
	
	function cancel() {
		window.close();
	}
	
	function isAtLeastOneRowSelected() {
		
		var totalLines = document.getElementById("totalLines").value;
		var count = 0;
		
		for ( var line = 1; line <= totalLines; line++) {
			try {
				var ok = $('ok' + line);
				
				if (ok) {
					count++;
					
				}
			} catch (ex) {
			}
		}
		if( count == 0 ) {
			alert(messagesData.norowselected);
			return false;
		}
		if(count >1) {
//			if( confirm(messagesData.groupReceiptDoc) ) {
			if( confirm("Do you want to group these receipt certs together?\nClick 'OK' to proceed with them grouped.\nClick 'Cancel' to proceed without them grouped.") ) {
				document.getElementById("groupReceiptDoc").value = "Y";
				groupReceiptDoc = true;
				return true;
			}
		}
		//document.getElementById("groupReceiptDoc").value = "";
		return true;
	}
	
	function printReceivingBoxLabels() {
		var labelReceipts = getLabelReceipts();
		if (labelReceipts.trim().length > 0) {
			var loc = "printreceiptboxlabels.do?labelReceipts="
					+ labelReceipts + "";
			loc = loc + "&paperSize=64";
			
			if ($v("personnelCompanyId") == 'Radian') 
				  loc = "/tcmIS/hub/" + loc;
			try {
				children[children.length] = openWinGeneric(loc, "printBinLabels11",
						"800", "500", "yes", "80", "80");
			} catch (ex) {
				openWinGeneric(loc, "printBinLabels11", "800", "500", "yes", "80",
						"80");
			}
		} else {
			alert(messagesData.norowselected);
		}
	}
	
	function getLabelReceipts() {
		var totalLines = document.getElementById("totalLines").value;
		var labelReceipts = "";
		var checkedCount = 0;
		for ( var p = 1; p <= totalLines; p++) {
			try {
					var ok = cellValue(p,"ok");
					if (ok) {
						var receiptId = cellValue(p,"receiptId");
						if (checkedCount > 0) {
							labelReceipts += ','
						}
						labelReceipts += receiptId;
						checkedCount++
					}
				} catch (ex) {
				}
			}
		
		return labelReceipts;
	}
	
	function printReceivingQcLabels() {
		if (isAtLeastOneRowSelected())
		{
			var hubNumber = document.getElementById("hub").value;
			var loc = "receivingqclabels.do?";
			var paperSize = document.getElementById("paperSize").value;
			loc = loc + "paperSize=" + paperSize;
			loc = loc + "&hubNumber=" + hubNumber;
			
			if ($v("personnelCompanyId") == 'Radian') 
				  loc = "/tcmIS/hub/" + loc;
			
			if (hubNumber == "2106") {
				printNormandaleQCLabels();
			} else {
				printQCLabels(loc);
			}
		}
	}
	
	
	function printNormandaleQCLabels() {
		var labelReceipts = getLabelReceipts();
		if (labelReceipts.trim().length > 0) {
			var loc = "/HaasReports/report/printSeagateLabel.do?pr_receipts_ids="
					+ labelReceipts;
			openWinGeneric(loc, "PrintSeagateLabel", "800", "600", "yes", "50",
					"50", "20", "20", "yes");
		}
		return;
	}
	
	function printQCLabels(loc) {
			var skipKitLabels = parent.document.getElementById("skipKitLabels");
			if (skipKitLabels.checked) {
				loc = loc + "&skipKitLabels=Yes";
			} else {
				loc = loc + "&skipKitLabels=No";
			}
			try {
				children[children.length] = openWinGeneric(loc,
						"printReceivingQcLabels", "800", "500", "yes", "80", "80");
			} catch (ex) {
				openWinGeneric(loc, "printReceivingQcLabels", "800", "500", "yes",
						"80", "80");
			}
		}
	
	function printReceivingQcReceiptLabels() {
		if(isAtLeastOneRowSelected)
		{
			var hubNumber = document.getElementById("hub").value;
			var loc = "receivingqclabels.do?";
			loc = loc + "paperSize=receiptDetail";
			
			if ($v("personnelCompanyId") == 'Radian') 
				  loc = "/tcmIS/hub/" + loc;
			
			loc = loc + "&hubNumber=" + hubNumber;
			if (hubNumber == "2106") {
				printNormandaleQCLabels();
			} else {
					printQCLabels(loc);
			}
		}
	}
	
	function printReceivingQcDocLabels() {
		if(isAtLeastOneRowSelected)
		{
			var hubNumber = document.getElementById("hub").value;
			var loc = "receivingqclabels.do?";
			var paperSize = document.getElementById("paperSize").value;
			loc = loc + "paperSize=receiptDocument";
			loc = loc + "&hubNumber=" + hubNumber;
			
			if( $v('groupReceiptDoc') == "Y" ) {
					loc += "&groupReceiptDoc=Y";
					groupReceiptDoc = false;
					$('groupReceiptDoc').value = "";
			}
			
			if ($v("personnelCompanyId") == 'Radian') 
				  loc = "/tcmIS/hub/" + loc;
			
			if (hubNumber == "2106") {
				printNormandaleQCLabels();
			} else {
				loc = loc + "&hubNumber=" + hubNumber;
				printQCLabels(loc);
			}
		}
	}
	
	
	
	function showReceivingJacket(selectedRowId) {
		
		var radianPo = cellValue(selectedRowId,"radianPo");
		var lineItem = cellValue(selectedRowId,"poLine");
		var itemId = cellValue(selectedRowId,"itemId");
		var branchPlant = $('hub');

		loc = "/cgi-bin/radweb/old_sheet.cgi?po=";
		loc = loc + radianPo;
		loc = loc + "&item=" + itemId;
		loc = loc + "&line=" + lineItem;
		if (branchPlant.value == "2101") {
			loc = loc + "&tab=N";
		} else {
			loc = loc + "&tab=Y";
		}
		// alert(loc);
		try {
			children[children.length] = openWinGenericViewFile(loc,
					"showReceivingJacket11", "600", "600", "yes");
		} catch (ex) {
			openWinGenericViewFile(loc, "showReceivingJacket11", "800", "600",
					"yes");
		}
	}
	
	function showRadianPo(selectedRowId) {
		  var radianPo = cellValue(selectedRowId,"radianPo");
		  
		  loc = "/tcmIS/supply/purchaseorder.do?po=" + radianPo + "&Action=searchlike&subUserAction=po";
		  try {
		 	children[children.length] = openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
		 } catch (ex){
		 	openWinGeneric(loc,"showradianPo","800","600","yes","50","50","yes");
		 }
		}
	
	function showProjectDocuments(selectedRowId) {
		var receiptId = cellValue(selectedRowId,"receiptId");
		var inventoryGroup = cellValue(selectedRowId,"inventoryGroup");
		var loc = "receiptdocuments.do?receiptId=" + receiptId + "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "";
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc,
					"showAllProjectDocuments", "450", "300", "no", "80", "80");
		} catch (ex) {
			openWinGeneric(loc, "showAllProjectDocuments", "450", "300", "no",
					"80", "80");
		}
	}
	
	function checkAllNonChemicalReceipts() {
		var result;
		var checkNonChemicalReceipts = document
				.getElementById("checkNonChemicalReceipts");
		if (checkNonChemicalReceipts.checked) {
			result = true;
		} else {
			result = false;
		}

		var totalLines = document.getElementById("totalLines").value;
		for ( var p = 0; p < totalLines; p++) {
			try {
				var updateStatus = document.getElementById("updateStatus" + p + "").value;
			} catch (ex) {
				updateStatus = "";
			}

			if (updateStatus != 'readOnly') {
				try {
					var ok = document.getElementById("ok" + p + "");
					ok.checked = result;
				} catch (ex) {
				}
			}
		}
	}
	
	function showVvHubBins(rowNumber) {
		var itemId = document.getElementById("itemId" + rowNumber + "");
		var branchPlant = document.getElementById("branchPlant" + rowNumber + "");

		var loc = "showhubbin.do?branchPlant=" + branchPlant.value
					+ "&userAction=showBins&rowNumber=" + rowNumber + "&itemId=" + itemId.value;
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc, "showVvHubBins", "300",
					"150", "no", "80", "80");
		} catch (ex) {
			openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
		}

	}
	
	function addBintoMainPage() {
		var totallines = opener.document.getElementById("totalLines").value * 1;
		var selectedRow = false;
		var vvHubBin = document.getElementById("vvHubBin").value;
		var itemId = document.getElementById("itemId");
		var selectedRowNumber = document.getElementById("rowNumber").value;

		for ( var rowNumber = 0; rowNumber < totallines; rowNumber++) {
			var mainItemId = opener.document.getElementById("itemId" + rowNumber
					+ "");
			if (mainItemId.value == itemId.value) {
				var mainBinO = opener.document.getElementById("bin" + rowNumber
						+ "");
				// alert("Found the line to add Bin "+vvHubBin+"");
				if (selectedRowNumber == rowNumber) {
					selectedRow = true;
				} else {
					selectedRow = false;
				}

				try {
					var binName = null;
					binName = mainBinO.value;
					if (mainBinO.value == "NONE" || mainBinO.value.length == 0) {
						mainBinO[0] = null;
						opener.addOptionItem(rowNumber, vvHubBin, vvHubBin,
								selectedRow);
					} else {
						opener.addOptionItem(rowNumber, vvHubBin, vvHubBin,
								selectedRow);
					}
				} catch (ex) {
					// alert("error");
				}

			}
		}
		cancel();
	}

	function addOptionItem(rowNumber, value, text, selectedRow) {
		obj = document.getElementById("bin" + rowNumber + "")
		var index = obj.length;
		obj.options[index] = new Option(text, value);
		if (selectedRow) {
			obj.options[index].selected = true;
		}
	}
	
	function receiptSpecs(selectedRowId) {
		var receiptId = cellValue(selectedRowId,"receiptId");
		var loc = "receiptspec.do?receiptId=" + receiptId;
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/distribution/" + loc;
		
		children[children.length] = openWinGeneric(loc, "receiptSpecs", "800",
				"400", "yes", "50", "50", "20", "20", "no");
	}
	
	function selectRowNonChem(rowId) {
		var selectedRow = document.getElementById("rowId" + rowId + "");

		var selectedRowClass = document.getElementById("colorClass" + rowId + "");
		selectedRow.className = "selected" + selectedRowClass.value + "";

		if (selectedRowId != null && rowId != selectedRowId) {
			var previouslySelectedRow = document.getElementById("rowId"
					+ selectedRowId + "");
			var previouslySelectedRowClass = document.getElementById("colorClass"
					+ selectedRowId + "");
			previouslySelectedRow.className = "" + previouslySelectedRowClass.value
					+ "";
		}
		selectedRowId = rowId;

		toggleContextMenu('nonChemQc');
	}
	

	function showProjectDocuments(selectedRowId) {
		var receiptId = cellValue(selectedRowId,"receiptId");
		var inventoryGroup = cellValue(selectedRowId,"inventoryGroup");
		var loc = "receiptdocuments.do?receiptId=" + receiptId + "&showDocuments=Yes&inventoryGroup=" + inventoryGroup + "";
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		openWinGeneric(loc, "showAllProjectDocuments", "450", "300", "no", "80", "80");
	}


	function openReceivingQcDetailView(selectedRowId)
	{		
		var now = new Date();
		var loc = "receivingqcchecklist.do?searchType=is&searchWhat=Receipt%20Id&search=" + cellValue(selectedRowId,"receiptId") 
		+ "&sourceHub=" + $v('hub') + "&sort=" + $v('sort') + "&opsEntityId=" + $v('opsEntityId') + "&packagedQty=" + cellValue(selectedRowId,"packagedQty") 
		+ "&startSearchTime=" + now.getTime();
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		openWinGeneric(loc, "receivingQcDetailView", "1000", "950", "yes", "80", "80");
	}










