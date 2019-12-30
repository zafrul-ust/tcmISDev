var chargeNumberGrid2Columns;
var chargeNumberGrid3Columns;
var chargeNumberGrid4Columns;
var chargeNumberGrid5Columns;
var chargeNumberSelectedRowId = null;
var dhxWins = null;
var children = new Array();
var windowCloseOnEsc = true;

function viewMsds(materialId) {
	if(newMsdsViewer)
		children[children.length] = openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+ materialId +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId')+
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
	else
		children[children.length] = openWinGeneric('ViewMsds?act=msds'+
			'&id='+ materialId +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId')+
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
}

var ssButton1 = false;
var ssButton2 = false;
function showSection1() {
  document.getElementById("section1").style["display"] = "";
  document.getElementById("newChemTabs").style["display"] = "";
  document.getElementById("section2").style["display"] = "none";
  ssButton1 = true;
  ssButton2 = false;
  $('ssB2').className='inputBtns';
}

function showSection2() {
  document.getElementById("section1").style["display"] = "none";
  document.getElementById("newChemTabs").style["display"] = "none";
  document.getElementById("section2").style["display"] = "";
  var count = 0;
  for (var i=0;i<tabDataJson.length;i++) {
   if(tabDataJson[i].status == "open") {
     if(count == 0) {
       document.getElementById("descPipeline").innerHTML = document.getElementById("materialDesc"+i).value;
     } else {
       document.getElementById("descPipeline").innerHTML += " || " + document.getElementById("materialDesc"+i).value;
     }
     count ++;
   } 
 }
  ssButton1 = false;
  ssButton2 = true;
  $('ssB1').className='inputBtns';
  
}

function $(a) {
	return document.getElementById(a);
}

function editOnLoad(action) {
	closeTransitWin();
	if (action == 'delete') {
		parent.parent.closeTabx('engeval');
	}else {
		initializeGrid();
		loadDock();
		setChargeType();
		startOnload();
		//preselect the the first tab
		if (selectedTabId > 0) {
			togglePage(0);
		}
		setViewLevel();
	}
}

function submitSubmitForm() {
  saveCurrenMrData();
  if (auditData()) {
	  showTransitWin();
	  $("uAction").value = 'submit';
	  document.genericForm.submit();
  }else {
	  setViewLevel();
  }
}

function submitDeleteForm() {
  showTransitWin();
  $("uAction").value = 'delete';
  return true;
}

function submitApproveForm() {
	saveCurrenMrData();
	if (auditData()) {
		showApprovalRoleWin();
	}else {
		setViewLevel();
	}
}
function submitSaveForm() {
  showTransitWin();
  saveCurrenMrData();
  $("uAction").value = 'save';
  return true;
}

function auditData() {
	var result = true;

	var missingFields = "";
	//material data
	if ($("evalType").value == 'new') {
		for (var i=0;i<tabDataJson.length;i++) {
			//don't need to audit closed tabs
			if (tabDataJson[i].status == "closed") continue;

			if (isEmptyV("manufacturer"+i)) {
				missingFields += "\t"+messagesData.manufacturer+"\n";
			}
			if (isEmptyV("materialDesc"+i)) {
				missingFields += "\t"+messagesData.materialdescription+"\n";
			}
			if (isEmptyV("mfgTradeName"+i)) {
				missingFields += "\t"+messagesData.manufacturertradename+"\n";
			}
			if ($("dimensionLabelSpan"+i).style.display.length == 0) {
				if (isEmptyV("dimension"+i)) {
					missingFields += "\t"+messagesData.dimension+"\n";
				}
			}
			if ($("netWtLabelSpan"+i).style.display.length == 0) {
				if (isEmptyV("netwt"+i)) {
					missingFields += "\t"+messagesData.netsize+"\n";
				}
			}
			if ($("netWtUnitLabelSpan"+i).style.display.length == 0) {
				if (isEmptyV("netwtUnit"+i)) {
					missingFields += "\t"+messagesData.netunit+"\n";
				}
			}
			if (isEmptyV("componentsPerItem"+i)) {
				missingFields += "\t"+messagesData.numperpart+"\n";
			}
			if (isEmptyV("partSize"+i)) {
				missingFields += "\t"+messagesData.size+"\n";
			}
			if (isEmptyV("sizeUnit"+i) || 'Select One' == $("sizeUnit"+i).value) {
				missingFields += "\t"+messagesData.unit+"\n";
			}
			if (isEmptyV("pkgStyle"+i) || 'Select One' == $("pkgStyle"+i).value) {
				missingFields += "\t"+messagesData.packagestyle+"\n";
			}
		}
	}

	//supplier data
	if ( isEmptyV("suggestedVendor") ) {
		missingFields += "\t"+messagesData.suppliername+"\n";
	}
	if (isEmptyV("vendorContactName")) {
  		missingFields += "\t"+messagesData.contactname+"\n";
	}
	if (isEmptyV("vendorContactPhone")) {
		missingFields += "\t"+messagesData.contactphone+"\n";
	}

	//MR data
	if (isEmptyV("qty")) {
		missingFields += "\t"+messagesData.qty+"\n";
	}
	if (isEmptyV("deliveryBy")) {
		missingFields += "\t"+messagesData.deliveryby+"\n";
	}
	if (isEmptyV("dock")) {
		missingFields += "\t"+messagesData.dock+"\n";
	}
	if (isEmptyV("deliverTo")) {
		missingFields += "\t"+messagesData.deliverto+"\n";
	}

	if ($("poInputSpan").style.display.length == 0) {
		if (isEmptyV("poInput")) {
			missingFields += "\t"+messagesData.po+"\n";
		}
	}
	if ($("poLineSpan").style.display.length == 0) {
		if (isEmptyV("poLineInput")) {
			missingFields += "\t"+messagesData.line+"\n";
		}
	}

	if (missingFields.length > 0) {
		alert(messagesData.validvalues+"\n"+missingFields);
		result = false;
	}else {
		result = auditChargeNumberData();
	}
	return result;
}


function enableFieldsForFormSubmit() {
	setMaterialViewLevel('');
	setSupplierViewLevel('');
	setMrViewLevel('');
}

function saveCurrenMrData() {
	enableFieldsForFormSubmit();
    //var canEditMr = $("canEditMr").value;
	//if (canEditMr == "Y") {
		var key = $("lineItem").value;
		var currentChargeType = $("currentChargeType").value;

        var poRequired = "n";
		var prAccountRequired = "n";
		var chargeLabel1 = "";
		var chargeLabel2 = "";
	   var chargeLabel3 = "";
	   var chargeLabel4 = "";
	   var chargeAllowNull1 = "";
		var chargeAllowNull2 = "";
	   var chargeAllowNull3 = "";
	   var chargeAllowNull4 = "";
		for(var i = 0; i < prRulesColl.length; i++) {
			if (currentChargeType == prRulesColl[i].chargeType) {
				poRequired = prRulesColl[i].poRequired;
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
	//} //end of canEditMr
} //end of saveCurrentData

function auditChargeNumberData() {
	var result = true;
	var canEditMr = $("canEditMr").value;
	if (canEditMr == "Y") {
		var key = $("lineItem").value;
		var currentChargeType = $("currentChargeType").value;

		var poRequired = "n";
		var prAccountRequired = "n";
		var chargeLabel1 = "";
		var chargeLabel2 = "";
		var chargeLabel3 = "";
	   var chargeLabel4 = "";
	   var chargeAllowNull1 = "";
		var chargeAllowNull2 = "";
	   var chargeAllowNull3 = "";
	   var chargeAllowNull4 = "";
		for(var i = 0; i < prRulesColl.length; i++) {
			if (currentChargeType == prRulesColl[i].chargeType) {
				poRequired = prRulesColl[i].poRequired;
				prAccountRequired = prRulesColl[i].prAccountRequired;
				chargeLabel1 = prRulesColl[i].chargeLabel1;
				chargeLabel2 = prRulesColl[i].chargeLabel2;
				chargeAllowNull1 = prRulesColl[i].chargeAllowNull1;
				chargeAllowNull2 = prRulesColl[i].chargeAllowNull2;
				chargeAllowNull3 = prRulesColl[i].chargeAllowNull3;
				chargeAllowNull4 = prRulesColl[i].chargeAllowNull4;
				break;
			}
		}
		if (prAccountRequired == "y") {
			if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
				 (chargeLabel3 != null && chargeLabel3.length > 0) && (chargeLabel4 != null && chargeLabel4.length > 0)) {
				result = auditChargeNumberColumns(prAccountColl[key],"5",chargeAllowNull1,chargeAllowNull2,chargeAllowNull3,chargeAllowNull4);
			}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
				 (chargeLabel3 != null && chargeLabel3.length > 0)) {
				result = auditChargeNumberColumns(prAccountColl[key],"4",chargeAllowNull1,chargeAllowNull2,chargeAllowNull3,chargeAllowNull4);
			}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
				result = auditChargeNumberColumns(prAccountColl[key],"3",chargeAllowNull1,chargeAllowNull2,chargeAllowNull3,chargeAllowNull4);
			}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
			  	result = auditChargeNumberColumns(prAccountColl[key],"2",chargeAllowNull1,chargeAllowNull2,chargeAllowNull3,chargeAllowNull4);
			}
		}
	} //end of canEditMr
	return result;
}

function auditChargeNumberColumns(chargeData,chargeNumberOfColumn,chargeAllowNull1,chargeAllowNull2,chargeAllowNull3,chargeAllowNull4) {
	var totalPercent = 0.0*1;
	for (var i = 0; i < chargeData.length; i++) {
        //the reason we need to check this is because chargeData array contains both charge types
        if ($("currentChargeType").value != chargeData[i].chargeType) continue;
        var tmpPercent = chargeData[i].percentage;
        if (tmpPercent != null && tmpPercent.length > 0) {
            if (isFloat(tmpPercent)) {
                if (tmpPercent != null && tmpPercent.length > 0 && tmpPercent != '&nbsp;') {
                    if (chargeNumberOfColumn == "2") {
                        if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0) {
                            alert(messagesData.missingchargenumber);
                            return false;
                        }else {
                            totalPercent += tmpPercent*1;
                        }
                    }else if (chargeNumberOfColumn == "3") {
                        hasChargeNumber = false;
                        if (chargeAllowNull1 == 'N') {
                            if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0 ) {
                                alert(messagesData.missingchargenumber);
                                return false;
                            }else {
                                hasChargeNumber = true;
                            }
                        }
                        if (chargeAllowNull2 == 'N') {
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
                        if (chargeAllowNull1 == 'N') {
                            if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0 ) {
                                alert(messagesData.missingchargenumber);
                                return false;
                            }else {
                                hasChargeNumber = true;
                            }
                        }
                        if (chargeAllowNull2 == 'N') {
                            if (chargeData[i].accountNumber2 == null || chargeData[i].accountNumber2.length == 0 ) {
                                alert(messagesData.missingchargenumber);
                                return false;
                            }else {
                                hasChargeNumber = true;
                            }
                        }
                        if (chargeAllowNull3 == 'N') {
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
                        if (chargeAllowNull1 == 'N') {
                            if (chargeData[i].accountNumber == null || chargeData[i].accountNumber.length == 0 ) {
                                alert(messagesData.missingchargenumber);
                                return false;
                            }else {
                                hasChargeNumber = true;
                            }
                        }
                        if (chargeAllowNull2 == 'N') {
                            if (chargeData[i].accountNumber2 == null || chargeData[i].accountNumber2.length == 0 ) {
                                alert(messagesData.missingchargenumber);
                                return false;
                            }else {
                                hasChargeNumber = true;
                            }
                        }
                        if (chargeAllowNull3 == 'N') {
                            if (chargeData[i].accountNumber3 == null || chargeData[i].accountNumber3.length == 0 ) {
                                alert(messagesData.missingchargenumber);
                                return false;
                            }else {
                                hasChargeNumber = true;
                            }
                        }
                        if (chargeAllowNull4 == 'N') {
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
	}else {
		return true;
	}
}

function removeChargeTypeFromData(lineKeyId,currentChargeType) {
    //remove the last element first since the splice method will skip the data up
    for (var i = prAccountColl[lineKeyId].length - 1; i >= 0; i-- ) {
        if (prAccountColl[lineKeyId][i].chargeType == currentChargeType) {
            prAccountColl[lineKeyId].splice(i,1);
        }
    }
}

function saveChargeNumber2Columns(lineKeyId,currentChargeType) {
	//first remove previous data
    removeChargeTypeFromData(lineKeyId,currentChargeType);

    var chargeNumbers = "";
	var j = prAccountColl[lineKeyId].length;
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
	}else {
		$("chargeNumbers").value = "";
	}
}

function saveChargeNumber3Columns(lineKeyId,currentChargeType) {
	//first remove previous data
    removeChargeTypeFromData(lineKeyId,currentChargeType);

    var chargeNumbers = "";
	var j = prAccountColl[lineKeyId].length;
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
	}else {
		$("chargeNumbers").value = "";
	}
}

function saveChargeNumber4Columns(lineKeyId,currentChargeType) {
	//first remove previous data
    removeChargeTypeFromData(lineKeyId,currentChargeType);

    var chargeNumbers = "";
	var j = prAccountColl[lineKeyId].length;
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
	}else {
		$("chargeNumbers").value = "";
	}
}

function saveChargeNumber5Columns(lineKeyId,currentChargeType) {
	//first remove previous data
    removeChargeTypeFromData(lineKeyId,currentChargeType);
    
    var chargeNumbers = "";
	var j = prAccountColl[lineKeyId].length;
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
	}else {
		$("chargeNumbers").value = "";
	}
}
var canEditNotes = false;
function setViewLevel() {
	canEditNotes = false;
	//can't change data if request is rejected or went alway thru
	if ($("requestStatus").value == 7 || $("requestStatus").value == 9 || $("requestStatus").value == 12) {
		setMaterialViewLevel(true);
		setSupplierViewLevel(true);

		//action buttons
		$("submitSubmit").disabled = true;
		$("submitSave").disabled = true;
		$("submitDelete").disabled = true;
		$("submitApprove").disabled = true;
		$("approvalDetail").disabled = '';
		$("submitSpan").style["display"] = "none";
		$("saveSpan").style["display"] = "none";
		$("deleteSpan").style["display"] = "none";
		$("approveSpan").style["display"] = "none";
		$("approvalDetailSpan").style["display"] = "";

		//add mr view here
		setMrViewLevel(true);
	}else if ($("requestStatus").value < 5) {
		//in draft status, only requestor can edit data
		if ($("personnelId").value == $("requestor").value) {
			//no one can edit material data if request start with existing item
			if ($("evalType").value == 'new') {
				setMaterialViewLevel('');
			}else {
				setMaterialViewLevel(true);
			}
			setSupplierViewLevel('');

			//action buttons
			$("submitSubmit").disabled = '';
			$("submitSave").disabled = '';
			$("submitDelete").disabled = '';
			$("submitApprove").disabled = true;
			$("approvalDetail").disabled = true;
			$("submitSpan").style["display"] = "";
			$("saveSpan").style["display"] = "";
			$("deleteSpan").style["display"] = "";
			$("approveSpan").style["display"] = "none";
			$("approvalDetailSpan").style["display"] = "none";
			
			//can edit notes
			canEditNotes = true;
			
			//add mr view here
			setMrViewLevel('');
		}else {
			setMaterialViewLevel(true);
			setSupplierViewLevel(true);

			//action buttons
			$("submitSubmit").disabled = true;
			$("submitSave").disabled = true;
			$("submitDelete").disabled = true;
			$("submitApprove").disabled = true;
			$("approvalDetail").disabled = true;
			$("submitSpan").style["display"] = "none";
			$("saveSpan").style["display"] = "none";
			$("deleteSpan").style["display"] = "none";
			$("approveSpan").style["display"] = "none";
			$("approvalDetailSpan").style["display"] = "none";
			
			//add mr view here
			setMrViewLevel(true);
		}
	}else {
		//in pending status
		var userIsAnApprover = false;
		for (var i = 0; i < altApproversList.length; i++) {
			if (altApproversList[i] == $("personnelId").value) {
				userIsAnApprover = true;
				break;
			}
		}
		if (userIsAnApprover) {
			//no one can edit material data if request start with existing item
			if ($("evalType").value == 'new') {
				setMaterialViewLevel('');
			}else {
				setMaterialViewLevel(true);
			}
			setSupplierViewLevel('');

			//action buttons
			$("submitSubmit").disabled = true;
			$("submitSave").disabled = '';
			$("submitDelete").disabled = true;
			$("submitApprove").disabled = '';
			$("approvalDetail").disabled = '';
			$("submitSpan").style["display"] = "none";
			$("saveSpan").style["display"] = "";
			$("deleteSpan").style["display"] = "none";
			$("approveSpan").style["display"] = "";
			$("approvalDetailSpan").style["display"] = "";
			
			//can edit notes
			canEditNotes = true;
			
			//add mr view  here
			setMrViewLevel(true);
		}else {
			setMaterialViewLevel(true);
			setSupplierViewLevel(true);

			//action buttons
			$("submitSubmit").disabled = true;
			$("submitSave").disabled = true;
			$("submitDelete").disabled = true;
			$("submitApprove").disabled = true;
			$("approvalDetail").disabled = '';
			$("submitSpan").style["display"] = "none";
			$("saveSpan").style["display"] = "none";
			$("deleteSpan").style["display"] = "none";
			$("approveSpan").style["display"] = "none";
			$("approvalDetailSpan").style["display"] = "";
			
			//add mr view here
			setMrViewLevel(true);
		}
	}
	
	if(canEditNotes && $v('notes').length == 0)
		$('showNotesLink').innerHTML = messagesData.add;
	else if(canEditNotes)
		$('showNotesLink').innerHTML = messagesData.viewEdit;
	else
		$('showNotesLink').innerHTML = messagesData.view;
}

function setMrViewLevel(flag) {
	$("endUser").disabled = flag;
	$("department").disabled = flag;
	$("critical").disabled = flag;
	$("chargeTypeD").disabled = flag;
	$("chargeTypeI").disabled = flag;
	$("qty").disabled = flag;
	$("deliveryBy").disabled = flag;
	$("dock").disabled = flag;
	$("deliverTo").disabled = flag;
	$("poCombo").disabled = flag;
	$("poInput").disabled = flag;
	$("poLineInput").disabled = flag;
}

function getApprovalDetail() {
	showNewChemApprovalDetail($('requestId').value);
}

function setSupplierViewLevel(flag) {
	$("suggestedVendor").disabled = flag;
	$("vendorContactName").disabled = flag;
	$("freeSample").disabled = flag;
	$("vendorContactEmail").disabled = flag;
	$("vendorContactPhone").disabled = flag;
	$("vendorContactFax").disabled = flag;
}

function setMaterialViewLevel(flag) {
	$("selectedManufacturer").disabled = flag;
	for (var i=0;i<tabDataJson.length;i++) {
		$("manufacturer"+i).disabled = flag;
		$("materialDesc"+i).disabled = flag;
		$("grade"+i).disabled = flag;
		$("mfgTradeName"+i).disabled = flag;
		$("mfgCatalogId"+i).disabled = flag;
		$("dimension"+i).disabled = flag;
		$("netwt"+i).disabled = flag;
		$("netwtUnit"+i).disabled = flag;
		$("componentsPerItem"+i).disabled = flag;
		$("partSize"+i).disabled = flag;
		$("sizeUnit"+i).disabled = flag;
		$("pkgStyle"+i).disabled = flag;
		$("sampleOnly"+i).disabled = flag;
	}
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showNotes() {
  var notes = document.getElementById("notesAreaBody");
  notes.style.display="";
  //test
  if(canEditNotes)
	  $("mrNotes").readOnly = false;
  else
	  $("mrNotes").readOnly = true;
  initializeDhxWins();
  if (!dhxWins.window("notesAreaWin")) {
    // create window first time
    var mrNotesWin = dhxWins.createWindow("notesAreaWin", 0, 0, 380, 170);
    mrNotesWin.setText(messagesData.notes);
    mrNotesWin.clearIcon();  // hide icon
    mrNotesWin.denyResize(); // deny resizing
    mrNotesWin.denyPark();   // deny parking
    mrNotesWin.attachObject("notesAreaBody");
	 mrNotesWin.button("minmax1").hide();
	 mrNotesWin.button("park").hide();
	 mrNotesWin.button("close").hide();
	 mrNotesWin.setModal(true); 
	 mrNotesWin.center();
  }
  else
  {
    // just show
	 dhxWins.window("notesAreaWin").setModal(true);
	 dhxWins.window("notesAreaWin").show();
  }
}

function closeNoteWin() {
	var mrNotes = $v("mrNotes");
	$("notes").value = $v("mrNotes");
	if(canEditNotes && mrNotes != '')
		$('showNotesLink').innerHTML=messagesData.viewEdit;
	dhxWins.window("notesAreaWin").setModal(false);
	dhxWins.window("notesAreaWin").hide();
}

function unitChanged() {
	var tmp = altSizeUnitRequiredNetWt[$("sizeUnit"+selectedTabId).selectedIndex];
	if (tmp == 'Y') {
		$("netWtRequired"+selectedTabId).value = "Yes";
		$("dimensionLabelSpan"+selectedTabId).style["display"] = "";
		$("netWtLabelSpan"+selectedTabId).style["display"] = "";
		$("netWtUnitLabelSpan"+selectedTabId).style["display"] = "";
		$("dimensionSpan"+selectedTabId).style["display"] = "";
		$("netWtSpan"+selectedTabId).style["display"] = "";
		$("netWtUnitSpan"+selectedTabId).style["display"] = "";
	}else {
		$("netWtRequired"+selectedTabId).value = "No";
		$("dimensionLabelSpan"+selectedTabId).style["display"] = "none";
		$("netWtLabelSpan"+selectedTabId).style["display"] = "none";
		$("netWtUnitLabelSpan"+selectedTabId).style["display"] = "none";
		$("dimensionSpan"+selectedTabId).style["display"] = "none";
		$("netWtSpan"+selectedTabId).style["display"] = "none";
		$("netWtUnitSpan"+selectedTabId).style["display"] = "none";
	} 
}

function isArray(testObject) {
      return testObject &&
             !( testObject.propertyIsEnumerable('length')) &&
             typeof testObject === 'object' &&
             typeof testObject.length === 'number';
}

function regExcape(str) {
// if more special cases, need more lines.
return str.replace(new RegExp("[([]","g"),"[$&]");
}

// Assuming standard template names. and a
// totalRow variable, which is the current total number of rows.
function mydup() {
// init hard code part, page dependent..
	var universalReplace = "catalogAddItemBean["; // for those inputs
	var dupStr = new Array();
	var partCount = $('partCount').value;
	var newDivHTML = $('newItem0').innerHTML;
	
// put the column that needs change the rownum...
	dupStr[0] = new Array(  "manufacturer","manufacturerId",
							"materialDesc",
							"grade",
							"mfgTradeName",
							"newTabComponent",
							"netWtRequired",
							"mfgCatalogId",
							"dimension",
							"netwt",
							"netwtUnit",
							"componentsPerItem",
							"partSize",
							"sizeUnit",
							"pkgStyle",
							"sampleOnly",
		 					"dimensionLabelSpan",
		 					"netWtLabelSpan",
		 					"netWtUnitLabelSpan",
		 					"dimensionSpan",
		 					"netWtSpan",
		 					"netWtUnitSpan"
							);
    var replHTML = '<div id="newItem'+partCount+'">' + newDivHTML + '</div>';
	for( jj = 0 ;jj < dupStr[0].length; jj++ )
			replHTML = replHTML.replace(new RegExp( regExcape(dupStr[0][jj])+"0","g"),dupStr[0][jj]+partCount);
	if(	universalReplace != null && universalReplace != "" )
			replHTML = replHTML.replace(new RegExp( regExcape(universalReplace)+"0","g"),universalReplace+partCount);

	var newDiv = document.createElement('div');
	newDiv.id = "newItem"+partCount;
	$("tabsdiv").appendChild(newDiv);
	$("newItem"+partCount).innerHTML = replHTML;

//	$("tabsdiv").innerHTML = $("tabsdiv").innerHTML + replHTML;
    $("manufacturerId"+partCount).value = "";
	$("manufacturer"+partCount).value = "";  
	$("materialDesc"+partCount).value = "";
	$("grade"+partCount).value = "";
	$("mfgTradeName"+partCount).value = "";
	$("mfgCatalogId"+partCount).value = "" ;
	$("dimension"+partCount).value = "";
	$("netwt"+partCount).value = "";
	$("netwtUnit"+partCount).selectedIndex = 0;
	$("componentsPerItem"+partCount).value = 0;
	$("partSize"+partCount).value = "";
	$("sizeUnit"+partCount).selectedIndex = 0 ;
	$("sizeUnit"+partCount).onchange = unitChanged;
	$("pkgStyle"+partCount).selectedIndex = 0 ;
	$("sampleOnly"+partCount).value = "";
    $("newTabComponent"+partCount).value = "New";
    $("netWtRequired"+partCount).value = "";
	$('partCount').value = 1+ parseInt(partCount);
//	setResultFrameSize();
	return;
}

var tabDataJson = new Array();
var selectedTabId="";

/*This funtion build the tab and opens the Iframe that has the page associated with the tab*/
function openTab(tabId,tabURL,tabName,tabIcon,noScrolling)
{
	var foundExistingDiv = false;
	var tabNum = 1;
	for (var i=0; i<tabDataJson.length; i++)
	{
		if (tabDataJson[i].status == "open")
	 	tabNum = tabNum + 1;

		if (tabDataJson[i].tabId == tabId) {
			foundExistingDiv = true;
			togglePage(tabId);
		}
	}

	if (!foundExistingDiv) {
	tabIndex = tabDataJson.length;
	/*Store the pages that are open in an array so that I don't open more than one frame for the same page*/
	tabDataJson[tabIndex]={tabId:""+tabId+"",status:"open"};

	if (tabIcon.length ==0)
	{
	  tabIcon = "/images/spacer14.gif"; //this is to maintain equal heights for all tabs. the heigt of the icon image has to be 14 piexels
	}

	var list = document.getElementById("mainTabList");
	var newNode = document.createElement("li");
	newNode.id = tabId +"li";
	var newNodeInnerHTML ="<a href=\"#\" id=\""+tabId+"Link\" class=\"selectedTab\" onmouseup=\"togglePage('"+tabId+"'); return false;\">";
	newNodeInnerHTML +="<span id=\""+tabId+"LinkSpan\" class=\"selectedSpanTab\"><img src=\""+tabIcon+"\" class=\"iconImage\">"+tabName+"&nbsp;"+tabNum+"&nbsp;";
	newNodeInnerHTML +="<br class=\"brNoHeight\"><img src=\"/images/minwidth.gif\" width=\""+(tabName.length*2)+"\" height=\"0\"></span></a>";
	newNode.innerHTML = newNodeInnerHTML;
	list.appendChild(newNode);
	togglePage(tabId);
	}
 	unitChanged();
}

function togglePage(tabId)
{
 /*If the page being toggled is already closed, ignore the toggle.
  This can happen when they click the x on the taab.
 */
 for (var i=0; i<tabDataJson.length; i++)
 {
   if (tabDataJson[i].tabId == tabId)
   {
    if (tabDataJson[i].status == "closed")
    {
      return;
    }
   }
 }

 //toggle page only if the page passed is not the selected tab
 if (selectedTabId != tabId)
 {
  for (var i=0; i<tabDataJson.length; i++)
  {
    var tabLink =  document.getElementById(tabDataJson[i].tabId+"Link");
    var tabLinkSpan =  document.getElementById(tabDataJson[i].tabId+"LinkSpan");
    if (tabDataJson[i].tabId == tabId && tabDataJson[i].status == "open")
    {
      setVisible(tabDataJson[i].tabId, true);
      tabLink.className = "selectedTab";
      tabLink.style["display"] = "";
      /*tabLink.onmouseover = "";
      tabLink.onmouseout = "";*/

      tabLinkSpan.className = "selectedSpanTab";
      tabLinkSpan.style["display"] = "";
      /*tabLinkSpan.onmouseover = "";
      tabLinkSpan.onmouseout = "";*/
      selectedTabId = tabId;
    }
    else
    {
      setVisible(tabDataJson[i].tabId, false);
      tabLink.className = "tabLeftSide";
      /*tabLink.onmouseover = "className='selectedTab'";
      tabLink.onmouseout = "className='tabLeftSide'";*/

      tabLinkSpan.className = "tabRightSide";
      /*tabLinkSpan.onmouseover = "className='selectedSpanTab'";
      tabLinkSpan.onmouseout = "className='tabRightSide'";*/
    }
  }
  }
  else
  {
   var tabLink =  document.getElementById(selectedTabId+"Link");
   tabLink.style["display"] = "";
   var tabLinkSpan =  document.getElementById(selectedTabId+"LinkSpan");
   tabLinkSpan.style["display"] = "";

   setVisible(tabId, true);
  }

  //only allow requestor to add more component if it's in draft status and
  //not coming from an existing item
  if ($("requestStatus").value < 5) {
	//in draft status, only requestor can edit data
	if ($("personnelId").value == $("requestor").value) {
		//even requestor cannot edit material data if request start with existing item
		if ($("evalType").value == 'new') {
			if (selectedTabId == 0) {
			toggleContextMenu('nchemcontextMenuWithoutDelete');
		 }else {
			toggleContextMenu('nchemcontextMenu');
		 }
		}
	}
  }

  /*Doing this so that when the page first comes up and the first thing the
   user does is a right click out side of the tab area, the right click is correct (normal).*/
  setTimeout('toggleContextMenuToNormal()',50);
}

function setVisible(tabId, yesno)
{
    try
    {
        var target =  document.getElementById("newItem"+tabId+"");
        if (yesno)
        {
         //alert("Here setVisible true  "+tabId+"");
         target.style["display"] = "";

        }
        else
        {
          //alert("Here setVisible false  "+tabId+"");
          target.style["display"] = "none";
        }
    }
    catch (ex)
    {
      alert("Here exception in setVisible");
    }
}

function addNewTab()
{
  mydup(); 
  openTab(''+($('partCount').value*1-1)+'','',messagesData.component,'','');      
}

function removeTab()
{
 // if (selectedTabId.length > 0 && selectedTabId !='home')
 var selectedTabIndex=0;
 if (selectedTabId.length > 0 && selectedTabId != "0")
 {
  var tabLink =  document.getElementById(selectedTabId+"Link");
  tabLink.style["display"] = "none";
  var tabLinkSpan =  document.getElementById(selectedTabId+"LinkSpan");
  tabLinkSpan.style["display"] = "none";

  var target =  document.getElementById("newItem"+selectedTabId+"");
  target.style["display"] = "none";

/*  var pageIframe =  document.getElementById(""+selectedTabId+"frame");
  pageIframe.src = "/blank.html";*/

  for (var i=0; i<tabDataJson.length; i++)
  {
     if (tabDataJson[i].tabId == selectedTabId)
     {
      //selectedTabId = tempSelectedTab;
      tabDataJson[i].status = "closed";       
      $("newTabComponent"+selectedTabId+"").value = "closed";
      selectedTabIndex=i;
     }
  }
  findNextTab(selectedTabIndex);
 } 

// Reassign an ordered number for tab  
 var list = document.getElementById("mainTabList");
 var a = list.getElementsByTagName('span');
 var tabNum = 1;
 for (var i=0;i<a.length;i++) {
   if(tabDataJson[i].status == "open") {
     a[i].innerText = a[i].textContent = messagesData.component+" "+tabNum+" ";
     tabNum = tabNum +1;
   } 
 }

}

function findNextTab(closedTabIndex)
{
 //alert("Here findNextTab "+closedTabIndex+"");
 var foundNextTab = false;
 //alert(closedTabIndex);
 for (var i=closedTabIndex-1; i>=0; i--)
 {
   //alert(""+tabDataJson[i].tabId+"  "+tabDataJson[i].status+" "+foundNextTab+"");
   if (tabDataJson[i].status == "open" && !foundNextTab)
   {
    togglePage (tabDataJson[i].tabId);
    foundNextTab = true;
    break;
   }
 }

 //alert("Between Tabs"+foundNextTab+"");
 if (!foundNextTab)
 {
  for (var i=tabDataJson.length-1; i>closedTabIndex; i--)
  {
   //alert(""+tabDataJson[i].tabId+"  "+tabDataJson[i].status+" "+foundNextTab+"");
   if (tabDataJson[i].status == "open" && !foundNextTab)
   {
    togglePage (tabDataJson[i].tabId);
    foundNextTab = true;
    break;
   }
  }
 }
}

function lookupManufacturer() {
// alert("selectedTabId"+selectedTabId);
 var manufacturer = document.getElementById("manufacturer"+selectedTabId).value.trim();
 var loc = "manufacturersearchmain.do?userAction=search&searchArgument="+manufacturer+"&allowNew=N";
 children[children.length] = openWinGeneric(loc,"manufacturersearch","500","500","yes","50","50","20","20","no");
}

function stopShowingWait() {
	//do nothing
}

function manufacturerChanged(newId,newDesc) {
	document.getElementById("manufacturerId"+selectedTabId).value = newId;
	document.getElementById("manufacturer"+selectedTabId).value = newDesc;
}

function initializeGrid(){
 beangrid = new dhtmlXGridObject('engEvalHistoryBean');

 initGridWithConfig(beangrid,config,false,true);
 if( typeof( jsonMainData ) != 'undefined' ) {
   beangrid.parse(jsonMainData,"json");
 }
// beangrid.attachEvent("onRightClick",selectRightclick);
}

function loadDock() {
	var dock = $("dock");
	//clear previous data
	for (var i = dock.length; i > 0; i--) {
    dock[i] = null;
   }
	var dockSelectedIndex = 0;
	var tmpVal = $("shipToLocationId").value;
	var key = $("lineItem").value;
	var dockColl = dockDeliveryPointColl[key].dockColl;
	for (var i=0; i < dockColl.length; i++) {
		setOption(i,dockColl[i].dockDesc,dockColl[i].dockLocationId, "dock");
		if (tmpVal == dockColl[i].dockLocationId) {
			dockSelectedIndex = i;
		}
	}
	dock.selectedIndex = dockSelectedIndex;
	loadDeliverTo();
}

function dockChanged() {
	loadDeliverTo();
}

function loadDeliverTo() {
	var selectedDock = $("dock").value;
	var deliverTo = $("deliverTo");
	//clear previous data
	for (var i = deliverTo.length; i > 0; i--) {
    deliverTo[i] = null;
   }
	var deliverSelectedIndex = 0;
	var tmpVal = $("deliveryPoint").value;
	var key = $("lineItem").value;
	var dockColl = dockDeliveryPointColl[key].dockColl;
	for (var i=0; i < dockColl.length; i++) {
		if (dockColl[i].dockLocationId == selectedDock) {
			var deliveryPointColl = dockColl[i].deliveryPointColl;
			for (var j=0; j < deliveryPointColl.length; j++) {
				setOption(j,deliveryPointColl[j].deliveryPointDesc,deliveryPointColl[j].deliveryPoint, "deliverTo");
				if (tmpVal == deliveryPointColl[j].deliveryPoint) {
					deliverSelectedIndex = j;
				}
			}
			break;
		}
	}
	deliverTo.selectedIndex = deliverSelectedIndex;
}


function directedCheck() {
    if ($v("currentChargeType") == "i") {
        saveCurrenMrData();
        $("currentChargeType").value = "d";
        setChargeType();
    }
}

function inDirectedCheck() {
    if ($v("currentChargeType") == "d") {
        saveCurrenMrData();
        $("currentChargeType").value = "i";
        setChargeType();
    }
}

function setChargeType() {
	var currentChargeType = $("currentChargeType").value;
    if (currentChargeType == "d") {
		$("chargeTypeD").checked = "checked";
		$("chargeTypeI").checked = "";
	}else {
		$("chargeTypeD").checked = "";
		$("chargeTypeI").checked = "checked";
	}       

	//don't show charge type if there is only one
	if (prRulesColl.length == 2) {
		$("chargeTypeSpan").style["display"] = "";
	}else {
		$("chargeTypeSpan").style["display"] = "none"; 
	}
	//determine what charge number or po to show
	var canEditMr = $("canEditMr").value;
	var key = $("lineItem").value;
	var poRequired = "n";
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
		if (currentChargeType.charAt(0) == prRulesColl[i].chargeType.charAt(0)) {
			poRequired = prRulesColl[i].poRequired;
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
				var directedCharge = $("directedChargeForDirect").value;
				if (directedCharge == "Y") {
                    if ($v("chargeNumbersFromDirectedChargeD") == 'Y' &&
                        $v("chargeNumbersFromDirectedChargeI") == 'Y' &&
                        canEditMr == "Y"    ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumber5Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditMr == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
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
				var directedCharge = $("directedChargeForIndirect").value;
				if (directedCharge == "Y") {
					if ($v("chargeNumbersFromDirectedChargeD") == 'Y' &&
                        $v("chargeNumbersFromDirectedChargeI") == 'Y' &&
                        canEditMr == "Y"    ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid5Columns == null;
					doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
					displayChargeNumber5Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditMr == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
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
			if(chargeNumberGrid5Columns != null && chargeNumberGrid5Columns != undefined && chargeNumberGrid5Columns.getRowsNum() < 6)
				$("chargeNumber5ColumnsDivId").style.width = '431px';
			else
				$("chargeNumber5ColumnsDivId").style.width = '450.1px';
		}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
			       (chargeLabel3 != null && chargeLabel3.length > 0)) {
			var typeStr = "ro,ro,ro,ro,ro";
			if(currentChargeType == "d") {
				var directedCharge = $("directedChargeForDirect").value;
				if (directedCharge == "Y") {
					if ($v("chargeNumbersFromDirectedChargeD") == 'Y' &&
                        $v("chargeNumbersFromDirectedChargeI") == 'Y' &&
                        canEditMr == "Y"    ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumber4Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditMr == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
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
				var directedCharge = $("directedChargeForIndirect").value;
				if (directedCharge == "Y") {
					if ($v("chargeNumbersFromDirectedChargeD") == 'Y' &&
                        $v("chargeNumbersFromDirectedChargeI") == 'Y' &&
                        canEditMr == "Y"    ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid4Columns == null;
					doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
					displayChargeNumber4Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditMr == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
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
			if(chargeNumberGrid4Columns != null && chargeNumberGrid4Columns != undefined && chargeNumberGrid4Columns.getRowsNum() < 6)
				$("chargeNumber4ColumnsDivId").style.width = '431px';
			else
				$("chargeNumber4ColumnsDivId").style.width = '450.1px';
		
		}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
			var typeStr = "ro,ro,ro,ro";
			if(currentChargeType.charAt(0) == "d" ) {
				var directedCharge = $("directedChargeForDirect").value;
				if (directedCharge == "Y") {
					if ($v("chargeNumbersFromDirectedChargeD") == 'Y' &&
                        $v("chargeNumbersFromDirectedChargeI") == 'Y' &&
                        canEditMr == "Y"    ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumber3Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditMr == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
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
				var directedCharge = $("directedChargeForIndirect").value;
				if (directedCharge == "Y") {
					if ($v("chargeNumbersFromDirectedChargeD") == 'Y' &&
                        $v("chargeNumbersFromDirectedChargeI") == 'Y' &&
                        canEditMr == "Y"    ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro,ro";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumber3Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditMr == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
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
			
			if(chargeNumberGrid3Columns != null && chargeNumberGrid3Columns != undefined && chargeNumberGrid3Columns.getRowsNum() < 6)
				$("chargeNumber3ColumnsDivId").style.width = '431px';
			else
				$("chargeNumber3ColumnsDivId").style.width = '450.1px';
			
		}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
			var typeStr = "ro,ro,ro";
			if(currentChargeType == "d") {
				var directedCharge = $("directedChargeForDirect").value;
				if (directedCharge == "Y") {
					if ($v("chargeNumbersFromDirectedChargeD") == 'Y' &&
                        $v("chargeNumbersFromDirectedChargeI") == 'Y' &&
                        canEditMr == "Y"    ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumber2Columns(chargeNumberForDirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditMr == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
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
				var directedCharge = $("directedChargeForIndirect").value;
				if (directedCharge == "Y") {
					if ($v("chargeNumbersFromDirectedChargeD") == 'Y' &&
                        $v("chargeNumbersFromDirectedChargeI") == 'Y' &&
                        canEditMr == "Y"    ) {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
					typeStr = "ro,ro,ro";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumber2Columns(chargeNumberForIndirectColl[key],"N",prAccountColl[key],currentChargeType);
				}else {
                    if(canEditMr == "Y") {
                        $("chargeTypeD").disabled = false;
					    $("chargeTypeI").disabled = false;
                    }else {
                        $("chargeTypeD").disabled = true;
					    $("chargeTypeI").disabled = true;
                    }
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
			
			if(chargeNumberGrid2Columns != null && chargeNumberGrid2Columns != undefined && chargeNumberGrid2Columns.getRowsNum < 6)
				$("chargeNumber2ColumnsDivId").style.width = '431px';
			else
				$("chargeNumber2ColumnsDivId").style.width = '450.1px';
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
				$("poInput").value = $("poNumber").value;
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
				$("poInput").value = $("poNumber").value;
			}else {
				//populate po dropdown
				loadPo(facAccountSysPoForIndirectColl[key]);
				$("poComboSpan").style["display"] = "";
				$("poInputSpan").style["display"] = "none";
			}
		}
		$("poLineLabelSpan").style["display"] = "";
		$("poLineSpan").style["display"] = "";
		$("poLineInput").value = $("releaseNumber").value;
	}else {
		$("poLabelSpan").style["display"] = "none";
		$("poComboSpan").style["display"] = "none";
		$("poInputSpan").style["display"] = "none";
		$("poLineLabelSpan").style["display"] = "none";
		$("poLineSpan").style["display"] = "none";
	}
} //end of method

function loadPo(dataArray) {
	var poCombo = $("poCombo");
	//clear previous data
	for (var i = poCombo.length; i > 0; i--) {
    poCombo[i] = null;
   }
	var poSelectedIndex = 0;
	var tmpVal = $("poNumber").value;
	for (var i=0; i < dataArray.length; i++) {
		setOption(i,dataArray[i].poNumber,dataArray[i].poNumber, "poCombo");
		if (tmpVal == dataArray[i].poNumber) {
			poSelectedIndex = i;
		}
	}
	poCombo.selectedIndex = poSelectedIndex;
}


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

	chargeNumberGrid2Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid2Columns.attachEvent("onRightClick",chargeNumberSelectRow);

   chargeNumberGrid2Columns.enableTooltips("false,false,false");
   /*This is to enable edit on click. If a cell is editiable it will show as soon as the row is selected.*/
   chargeNumberGrid2Columns.enableEditEvents(true,false,false);
   chargeNumberGrid2Columns.setSkin("haas");
	chargeNumberGrid2Columns.submitOnlyChanged(false);
	chargeNumberGrid2Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid2Columns.submitColumns([false,true,true]);
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
    /*This is to copy the ctrl+c to clipboard, and ctrl+v to paste to clipboard.*/
	//mygrid.entBox.onselectstart = function(){ return true; };
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
   chargeNumberGrid3Columns.enableEditEvents(true,false,false,false);
   chargeNumberGrid3Columns.setSkin("haas");
	chargeNumberGrid3Columns.submitOnlyChanged(false);
	chargeNumberGrid3Columns.setFieldName("{GRID_ID}[{ROW_INDEX}].{COLUMN_ID}");
	chargeNumberGrid3Columns.submitColumns([false,true,true,true]);
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

    /*This is to copy the ctrl+c to clipboard, and ctrl+v to paste to clipboard.*/
	//mygrid.entBox.onselectstart = function(){ return true; };
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

	chargeNumberGrid5Columns.attachEvent("onRowSelect",chargeNumberSelectRow);
   chargeNumberGrid5Columns.attachEvent("onRightClick",chargeNumberSelectRow);

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

function displayChargeNumberEmpty2Columns(canEditChargeNumber,savedDataArray,currentChargeType) {
	clearChargeTable2Columns();
	var rowCount = 0;
	for(var j = 0; j < savedDataArray.length; j++ ) {
		if (currentChargeType == savedDataArray[j].chargeType) {
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
			chargeNumberGrid2Columns.cells(i+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid2Columns.cells(rowCount+1,count++).setValue('');
			rowCount++;
		}
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
			chargeNumberGrid3Columns.cells(i+1,count++).setValue(canEditChargeNumber);
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
			chargeNumberGrid3Columns.cells(rowCount+1,count++).setValue('');
			rowCount++;
		}
	}
}

function clearChargeTable2Columns() {
	for(var i = chargeNumberGrid2Columns.getRowsNum(); i > 0; i--) {
		chargeNumberGrid2Columns.deleteRow(i);
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

function manageSDS() {
	children[children.length] = openWinGeneric("managecataddsds.do?requestId=" + $v('requestId') + "&editable=Y", "manageSDS", "500", "300", "yes");	
}