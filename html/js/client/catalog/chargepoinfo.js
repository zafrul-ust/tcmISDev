var dhxWins = null;
var windowCloseOnEsc = true;
var currentChargeType = "d";
var currentPoRequired = "n";
var currentPoSeqRequired = "n";
var currentPrAccountRequired = "n";
var currentChargeNumberColumn = 2;
var currentDirectChargeNumbers = "";
var currentIndirectChargeNumbers = "";
var currentPoNumber = "";
var currentReleaseNumber = "";
var userEnteredChargeNumber = true;
var currentChargeAllowNull1 = "";
var currentChargeAllowNull2 = "";
var currentChargeAllowNull3 = "";
var currentChargeAllowNull4 = "";
var ignoreNullChargeNumber = "N";

function $(a) {
	return document.getElementById(a);
}

function editOnLoad() {
    if (showErrorMessage) {
		showMessageDialog();
	}
    if ($v("canEditData") == 'Y') {
        if ($v("sourcePage") == 'clientCabinetManagement') {
            $("removeSpan").style["display"] = "";
        }else {
            $("removeSpan").style["display"] = "none";
        }
    }
    setChargeType();
}

function closeThisWindow() {
	try {
		if ($v("sourcePage") == 'pointOfSale') {
			opener.setPointOfSaleChargeNumberPoData("","","","");
		}else if ($v("sourcePage") == 'clientCabinetManagement') {
			opener.setChargeNumberPoData("","","","","");
		}
	}catch(e){}
}

function checkClose() {
	if ($v("uAction") != 'submit' && $v("uAction") != 'delete') {
		closeThisWindow();
	}
}

function cancel() {
	$("uAction").value = "";
	window.close();
}

function submitDeleteDirectedCharge() {
	$("uAction").value = "delete";
	if ($v("sourcePage") == 'clientCabinetManagement') {
		opener.deleteDirectedCharge(currentChargeType);
	}
	window.close();
}

function submitUpdate() {
  if (auditData()) {
	  $("uAction").value = "submit";
	  if ($v("sourcePage") == 'pointOfSale') {
          var tmpChargeNumber = currentDirectChargeNumbers;
          if (currentChargeType == 'i' ) {
              tmpChargeNumber = currentIndirectChargeNumbers;
          }
          opener.setPointOfSaleChargeNumberPoData(currentChargeType,tmpChargeNumber,currentPoNumber,currentReleaseNumber,userEnteredChargeNumber);
	  }else if ($v("sourcePage") == 'clientCabinetManagement') {
          var tmpChargeNumber = currentDirectChargeNumbers;
          if ($v("allowBothChargeType") == 'Y') {
              if (currentDirectChargeNumbers.length > 0 && currentIndirectChargeNumbers.length > 0) {
                  currentChargeType = "d,i";
                  tmpChargeNumber += "!txtx!"+currentIndirectChargeNumbers;
              }else if (currentDirectChargeNumbers.length > 0) {
                  currentChargeType = "d";
                  tmpChargeNumber = currentDirectChargeNumbers;
              }else if (currentIndirectChargeNumbers.length > 0) {
                  currentChargeType = "i";
                  tmpChargeNumber = currentIndirectChargeNumbers;
              }
          }else {
            if (currentChargeType == 'i' ) {
              tmpChargeNumber = currentIndirectChargeNumbers;
            }
          }
          opener.setChargeNumberPoData(currentChargeType,tmpChargeNumber,currentPoNumber,currentReleaseNumber,userEnteredChargeNumber,ignoreNullChargeNumber);
	  }
	  window.close();
  }else {
	  $("uAction").value = "";
	  return false;
  }
}

function auditData() {
	var result = true;
	if (!auditPoData()) {
		result = false;
	}else {
		if (!auditChargeNumberData()) {
			result = false;
		}
    }
    return result;
}

function auditChargeNumberData() {
    var result = true;
    if (currentPrAccountRequired == "y") {
		if (currentChargeNumberColumn == 5) {
			saveChargeNumber5Columns(currentChargeType);
		}else if (currentChargeNumberColumn == 4) {
			saveChargeNumber4Columns(currentChargeType);
		}else if (currentChargeNumberColumn == 3) {
			saveChargeNumber3Columns(currentChargeType);
		}else if (currentChargeNumberColumn == 2) {
		  	saveChargeNumber2Columns(currentChargeType);
		}

        if ($v("allowBothChargeType") == 'Y') {
            if (prRulesColl.length == 2) {
                result = validateChargeNumberData(currentChargeType);
                if (result) {
                    if (currentChargeType == 'd') {
                        result = validateChargeNumberData("i");
                    }else {
                        result = validateChargeNumberData("d");
                    }
                }
            }else {
                result = validateChargeNumberData(currentChargeType);
            }
        }else {
            result = validateChargeNumberData(currentChargeType);
        }
    } //end of if pr_account_required
    return result;
}  //end of method

function validateChargeNumberData(chargeType) {
	var result = true;
    var totalPercent = 0.0*1;
    for (var i = 0; i < chargeNumbersData.length; i++) {
        if (chargeNumbersData[i].chargeType != chargeType) continue;
        var numberOfColumns = chargeNumbersData[i].numberOfColumns;
        var chargeAllowNull1 = chargeNumbersData[i].chargeAllowNull1;
        var chargeAllowNull2 = chargeNumbersData[i].chargeAllowNull2;
        var chargeAllowNull3 = chargeNumbersData[i].chargeAllowNull3;
        var chargeAllowNull4 = chargeNumbersData[i].chargeAllowNull4;
        var tmpPercent = chargeNumbersData[i].percentage;
        if (isFloat(tmpPercent)) {
            if (tmpPercent != null && tmpPercent.length > 0) {
                if (numberOfColumns == "2") {
                    if (chargeNumbersData[i].accountNumber == null || chargeNumbersData[i].accountNumber.trim().length == 0) {
                        alert(messagesData.missingchargenumber);
                        return false;
                    }else {
                        totalPercent += tmpPercent*1;
                    }
                }else if (numberOfColumns == "3") {
                    hasChargeNumber = false;
                    if (chargeAllowNull1 == 'N') {
                        if (chargeNumbersData[i].accountNumber == null || chargeNumbersData[i].accountNumber.trim().length == 0 ) {
                            alert(messagesData.missingchargenumber+"*"+chargeType+"*");
                            return false;
                        }else {
                            hasChargeNumber = true;
                        }
                    }
                    if (chargeAllowNull2 == 'N') {
                        if (chargeNumbersData[i].accountNumber2 == null || chargeNumbersData[i].accountNumber2.trim().length == 0 ) {
                            alert(messagesData.missingchargenumber+"HERE");
                            return false;
                        }else {
                            hasChargeNumber = true;
                        }
                    }
                    if (hasChargeNumber) {
                        totalPercent += tmpPercent*1;
                    }
                }else if (numberOfColumns == "4") {
                    hasChargeNumber = false;
                    if (chargeAllowNull1 == 'N') {
                        if (chargeNumbersData[i].accountNumber == null || chargeNumbersData[i].accountNumber.trim().length == 0 ) {
                            alert(messagesData.missingchargenumber);
                            return false;
                        }else {
                            hasChargeNumber = true;
                        }
                    }
                    if (chargeAllowNull2 == 'N') {
                        if (chargeNumbersData[i].accountNumber2 == null || chargeNumbersData[i].accountNumber2.trim().length == 0 ) {
                            alert(messagesData.missingchargenumber);
                            return false;
                        }else {
                            hasChargeNumber = true;
                        }
                    }
                    if (chargeAllowNull3 == 'N') {
                        if (chargeNumbersData[i].accountNumber3 == null || chargeNumbersData[i].accountNumber3.trim().length == 0 ) {
                            alert(messagesData.missingchargenumber);
                            return false;
                        }else {
                            hasChargeNumber = true;
                        }
                    }
                    if (hasChargeNumber) {
                        totalPercent += tmpPercent*1;
                    }
                }else if (numberOfColumns == "5") {
                    hasChargeNumber = false;
                    if (chargeAllowNull1 == 'N') {
                        if (chargeNumbersData[i].accountNumber == null || chargeNumbersData[i].accountNumber.trim().length == 0 ) {
                            alert(messagesData.missingchargenumber);
                            return false;
                        }else {
                            hasChargeNumber = true;
                        }
                    }
                    if (chargeAllowNull2 == 'N') {
                        if (chargeNumbersData[i].accountNumber2 == null || chargeNumbersData[i].accountNumber2.trim().length == 0 ) {
                            alert(messagesData.missingchargenumber);
                            return false;
                        }else {
                            hasChargeNumber = true;
                        }
                    }
                    if (chargeAllowNull3 == 'N') {
                        if (chargeNumbersData[i].accountNumber3 == null || chargeNumbersData[i].accountNumber3.trim().length == 0 ) {
                            alert(messagesData.missingchargenumber);
                            return false;
                        }else {
                            hasChargeNumber = true;
                        }
                    }
                    if (chargeAllowNull4 == 'N') {
                        if (chargeNumbersData[i].accountNumber4 == null || chargeNumbersData[i].accountNumber4.trim().length == 0 ) {
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
    } //end of for each charge numbers
    //if user enter some percent make sure the sum of percent is 100
    if (totalPercent != 0 && totalPercent != 100) {
        alert(messagesData.invalidpercent);
        return false;
    }
    //
    if ($v("allowBothChargeType") == 'Y') {
        //user has to enter some charge number and percentage to one of the charge type
        if (currentDirectChargeNumbers.length == 0 && currentIndirectChargeNumbers.length == 0) {
            alert(messagesData.missingchargenumber);
            return false;
        }
    }else {
        //user has to enter some charge number and percentage
        if (totalPercent == 0) {
            alert(messagesData.missingchargenumber);
            return false;
        }
    }



    return result;
} //end of method

function auditPoData() {
	var result = true;
	if (currentPoRequired == "p") {
		//po number
		savePoNumber();
		if (currentPoNumber == null || currentPoNumber.length == 0) {
			alert(messagesData.missingpo);
			return false;
		}

		if (currentPoSeqRequired == 'U') {
			saveReleaseNumber();
			if (currentReleaseNumber == null || currentReleaseNumber.length == 0) {
				alert(messagesData.validvalues+" "+messagesData.poline);
				return false;
			}
		}
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

function regExcape(str) {
// if more special cases, need more lines.
return str.replace(new RegExp("[([]","g"),"[$&]");
}

function showMessageDialog()
{
	var inputDailogWin = document.getElementById("messageDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showMessageDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 150);
		inputWin.setText(messagesData.errors);
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
		inputWin.setModal(true);
		dhxWins.window("showMessageDialog").show();
	}
}

function closeMessageWin() {
  dhxWins.window("showMessageDialog").setModal(false);
  dhxWins.window("showMessageDialog").hide();
}

function directedCheck() {
    if (currentChargeType != 'd') {
        if (currentPrAccountRequired == "y") {
            if (currentChargeNumberColumn == 5) {
                saveChargeNumber5Columns("i");
            }else if (currentChargeNumberColumn == 4) {
                saveChargeNumber4Columns("i");
            }else if (currentChargeNumberColumn == 3) {
                saveChargeNumber3Columns("i");
            }else if (currentChargeNumberColumn == 2) {
                saveChargeNumber2Columns("i");
            }
        }
        if ($v("allowBothChargeType") == 'Y') {
            setChargeType();
        }else {
            if (currentIndirectChargeNumbers.length > 0 && $v("canEditData") == 'Y') {
                alert(messagesData.onlyOneChargeTypeData);
                $("chargeTypeI").checked = true;
            }else {
                setChargeType();
            }
        }
    }
}  //end of method

function inDirectedCheck() {
    if (currentChargeType != 'i') {
        if (currentPrAccountRequired == "y") {
            if (currentChargeNumberColumn == 5) {
                saveChargeNumber5Columns("d");
            }else if (currentChargeNumberColumn == 4) {
                saveChargeNumber4Columns("d");
            }else if (currentChargeNumberColumn == 3) {
                saveChargeNumber3Columns("d");
            }else if (currentChargeNumberColumn == 2) {
                saveChargeNumber2Columns("d");
            }
        }
        if ($v("allowBothChargeType") == 'Y') {
            setChargeType();
        }else {
            if (currentDirectChargeNumbers.length > 0 && $v("canEditData") == 'Y') {
                alert(messagesData.onlyOneChargeTypeData);
                $("chargeTypeD").checked = true;
            }else {
                setChargeType();
            }
        }
    }
} //end of method

function setChargeType() {
	if ($("chargeTypeI").checked) {
		currentChargeType = 'i';
	}else {
		currentChargeType = 'd';
	}

    //don't show charge type if there is only one
	if (prRulesColl.length == 2) {
		$("chargeTypeSpan").style["display"] = "";
	}else {
		$("chargeTypeSpan").style["display"] = "none";
	}

	//determine what charge number or po to show
	var canEditMr = 'Y';
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
	var chargeAllowNull1 = "";
	var chargeAllowNull2 = "";
	var chargeAllowNull3 = "";
	var chargeAllowNull4 = "";
    var directedChargeAllowNull1 = "";
	var directedChargeAllowNull2 = "";
	var directedChargeAllowNull3 = "";
	var directedChargeAllowNull4 = "";
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
			chargeAllowNull1 = prRulesColl[i].chargeAllowNull1;
			chargeAllowNull2 = prRulesColl[i].chargeAllowNull2;
			chargeAllowNull3 = prRulesColl[i].chargeAllowNull3;
			chargeAllowNull4 = prRulesColl[i].chargeAllowNull4;
            directedChargeAllowNull1 = prRulesColl[i].directedChargeAllowNull1;
			directedChargeAllowNull2 = prRulesColl[i].directedChargeAllowNull2;
			directedChargeAllowNull3 = prRulesColl[i].directedChargeAllowNull3;
			directedChargeAllowNull4 = prRulesColl[i].directedChargeAllowNull4;
            break;
		}
	}

	currentPoRequired = poRequired;
	currentPoSeqRequired = poSeqRequired;
	currentPrAccountRequired = prAccountRequired;
    if ($v("sourcePage") == 'clientCabinetManagement') {
        if ($v("facLocAppDirectedChargeAllowNull") == 'Y') {
            currentChargeAllowNull1 = directedChargeAllowNull1;
            currentChargeAllowNull2 = directedChargeAllowNull2;
            currentChargeAllowNull3 = directedChargeAllowNull3;
            currentChargeAllowNull4 = directedChargeAllowNull4;
        }else {
            currentChargeAllowNull1 = "N";
            currentChargeAllowNull2 = "N";
            currentChargeAllowNull3 = "N";
            currentChargeAllowNull4 = "N";
        }
        //charge number allow null
        if (currentChargeAllowNull1 == 'Y' || currentChargeAllowNull2 == 'Y' ||
            currentChargeAllowNull3 == 'Y' || currentChargeAllowNull4 == 'Y' )
            ignoreNullChargeNumber = 'Y';
    }else {
        currentChargeAllowNull1 = chargeAllowNull1;
        currentChargeAllowNull2 = chargeAllowNull2;
        currentChargeAllowNull3 = chargeAllowNull3;
        currentChargeAllowNull4 = chargeAllowNull4;
    }

    //charge number
	if (prAccountRequired == "y") {
		if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
			 (chargeLabel3 != null && chargeLabel3.length > 0) && (chargeLabel4 != null && chargeLabel4.length > 0)) {
			var typeStr = "ro,ro,ro,ro,ro,ro";
			currentChargeNumberColumn = 5;
            if ($v("canEditData") == 'Y')
                typeStr = "ro,hed,hed,hed,hed,hed";
            //intialize grid
            chargeNumberGrid5Columns == null;
            doChargeNumberInitGrid5Columns(chargeLabel1,chargeLabel2,chargeLabel3,chargeLabel4,typeStr);
            displayChargeNumberEmpty5Columns(canEditMr,chargeNumbersData,currentChargeType);
            userEnteredChargeNumber = true;

			$("chargeNumber2ColumnsDivId").style["display"] = "none";
			$("chargeNumber3ColumnsDivId").style["display"] = "none";
			$("chargeNumber4ColumnsDivId").style["display"] = "none";
			$("chargeNumber5ColumnsDivId").style["display"] = "";
		}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0) &&
			       (chargeLabel3 != null && chargeLabel3.length > 0)) {
			var typeStr = "ro,ro,ro,ro,ro";
			currentChargeNumberColumn = 4;
            if ($v("canEditData") == 'Y')
                typeStr = "ro,hed,hed,hed,hed";
            //intialize grid
            chargeNumberGrid4Columns == null;
            doChargeNumberInitGrid4Columns(chargeLabel1,chargeLabel2,chargeLabel3,typeStr);
            displayChargeNumberEmpty4Columns(canEditMr,chargeNumbersData,currentChargeType);
            userEnteredChargeNumber = true;

			$("chargeNumber2ColumnsDivId").style["display"] = "none";
			$("chargeNumber3ColumnsDivId").style["display"] = "none";
			$("chargeNumber4ColumnsDivId").style["display"] = "";
			$("chargeNumber5ColumnsDivId").style["display"] = "none";
		}else if ((chargeLabel1 != null && chargeLabel1.length > 0) && (chargeLabel2 != null && chargeLabel2.length > 0)) {
			var typeStr = "ro,ro,ro,ro";
			currentChargeNumberColumn = 3;
			if(currentChargeType == "d") {
				if (chargeDisplay1 == "y" && chargeDisplay2 == 'y') {
                    if ($v("canEditData") == 'Y')
                        typeStr = "ro,ro,ro,hed";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumber3Columns(chargeNumberForDirectColl,canEditMr,chargeNumbersData,currentChargeType);
					userEnteredChargeNumber = false;
				}else {
                    if ($v("canEditData") == 'Y')
                        typeStr = "ro,hed,hed,hed";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumberEmpty3Columns(canEditMr,chargeNumbersData,currentChargeType);
					userEnteredChargeNumber = true;
				}
			}else {
				if (chargeDisplay1 == "y" && chargeDisplay2 == 'y') {
                    if ($v("canEditData") == 'Y')
                        typeStr = "ro,ro,ro,hed";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumber3Columns(chargeNumberForIndirectColl,canEditMr,chargeNumbersData,currentChargeType);
					userEnteredChargeNumber = false;
				}else {
                    if ($v("canEditData") == 'Y')
                        typeStr = "ro,hed,hed,hed";
					//intialize grid
					chargeNumberGrid3Columns == null;
					doChargeNumberInitGrid3Columns(chargeLabel1,chargeLabel2,typeStr);
					displayChargeNumberEmpty3Columns(canEditMr,chargeNumbersData,currentChargeType);
					userEnteredChargeNumber = true;
				}
			}
			$("chargeNumber2ColumnsDivId").style["display"] = "none";
			$("chargeNumber3ColumnsDivId").style["display"] = "";
			$("chargeNumber4ColumnsDivId").style["display"] = "none";
			$("chargeNumber5ColumnsDivId").style["display"] = "none";
		}else if (chargeLabel1 != null && chargeLabel1.length > 0) {
			var typeStr = "ro,ro,ro";
			currentChargeNumberColumn = 2;
			if(currentChargeType == "d") {
				if (chargeDisplay1 == "y") {
                    if ($v("canEditData") == 'Y')
                        typeStr = "ro,ro,hed";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumber2Columns(chargeNumberForDirectColl,canEditMr,chargeNumbersData,currentChargeType);
					userEnteredChargeNumber = false;
				}else {
                    if ($v("canEditData") == 'Y')
                        typeStr = "ro,hed,hed";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumberEmpty2Columns(canEditMr,chargeNumbersData,currentChargeType);
					userEnteredChargeNumber = true;
				}
			}else {
				if (chargeDisplay1 == "y") {
                    if ($v("canEditData") == 'Y')
                        typeStr = "ro,ro,hed";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumber2Columns(chargeNumberForIndirectColl,canEditMr,chargeNumbersData,currentChargeType);
					userEnteredChargeNumber = false;
				}else {
                    if ($v("canEditData") == 'Y')
                        typeStr = "ro,hed,hed";
					//intialize grid
					chargeNumberGrid2Columns == null;
					doChargeNumberInitGrid2Columns(chargeLabel1,typeStr);
					displayChargeNumberEmpty2Columns(canEditMr,chargeNumbersData,currentChargeType);
					userEnteredChargeNumber = true;
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
			if (facAccountSysPoForDirectColl.length == 0) {
				$("poComboSpan").style["display"] = "none";
				$("poInputSpan").style["display"] = "";
				$("poInput").value = $v("poNumberForDirect");
			}else {
				//populate po dropdown
				loadPo(facAccountSysPoForDirectColl,$v("poNumberForDirect"));
				$("poComboSpan").style["display"] = "";
				$("poInputSpan").style["display"] = "none";
			}
		}else {
			if (facAccountSysPoForIndirectColl.length == 0) {
				$("poComboSpan").style["display"] = "none";
				$("poInputSpan").style["display"] = "";
				$("poInput").value = $v("poNumberForIndirect");
			}else {
				//populate po dropdown
				loadPo(facAccountSysPoForIndirectColl,$v("poNumberForIndirect"));
				$("poComboSpan").style["display"] = "";
				$("poInputSpan").style["display"] = "none";
			}
		}
		if (poSeqRequired == 'U') {
			$("poLineSpan").style["display"] = "";
		}else {
			$("poLineSpan").style["display"] = "none";
		}
		if (currentChargeType == "d") {
			$("poLineInput").value = $v("poLineForDirect");
		}else {
			$("poLineInput").value = $v("poLineForIndirect");
		}
	}else {
		$("poLabelSpan").style["display"] = "none";
		$("poComboSpan").style["display"] = "none";
		$("poInputSpan").style["display"] = "none";
		$("poLineSpan").style["display"] = "none";
	}
}

function removeChargeTypeFromData(chargeType) {
    //remove the last element first since the splice method will skip the data up
    for (var i = chargeNumbersData.length - 1; i >= 0; i-- ) {
        if (chargeNumbersData[i].chargeType == chargeType) {
            chargeNumbersData.splice(i,1);
        }
    }
} //end of method

function saveChargeNumber2Columns(chargeType) {
	//first remove previous data
    removeChargeTypeFromData(chargeType);
    var tmpChargeNumber = "";
    var j = chargeNumbersData.length;
	for (var i = 1; i <= chargeNumberGrid2Columns.getRowsNum(); i++) {
		var saveChargeNumber = false;
		var chargeNumber1 = chargeNumberGrid2Columns.cells(i,1).getValue();
		var percent = chargeNumberGrid2Columns.cells(i,2).getValue();
		if (chargeNumber1 != null && percent != null) {
			if (chargeNumber1.length > 0 && percent.length > 0) {
				if (chargeNumber1 != "&nbsp;" && percent != "&nbsp;") {
					saveChargeNumber = true;
				}else {
					saveChargeNumber = false;
				}
			}else {
				saveChargeNumber = false;
			}
		}else {
			saveChargeNumber = false;
		}
		if (saveChargeNumber) {
			//keep this array so it can be easily audit later
			chargeNumbersData[j++] = {
				accountNumber: 	    chargeNumber1.trim(),
				accountNumber2:     '',
				percentage:			percent.trim(),
				chargeType:			currentChargeType,
                numberOfColumns:    currentChargeNumberColumn,
                chargeAllowNull1:   currentChargeAllowNull1,
                chargeAllowNull2:   currentChargeAllowNull2,
                chargeAllowNull3:   currentChargeAllowNull3,
                chargeAllowNull4:   currentChargeAllowNull4
            };
			tmpChargeNumber += chargeNumber1.trim()+"!"+percent.trim()+"|";
		}
	} //end of loop
    if (chargeType == 'd') {
        currentDirectChargeNumbers = tmpChargeNumber;
    }else {
        currentIndirectChargeNumbers = tmpChargeNumber;
    }
} //end of method

function saveChargeNumber3Columns(chargeType) {
	//first remove previous data
    removeChargeTypeFromData(chargeType);
    var tmpChargeNumber = "";
    var j = chargeNumbersData.length;
	for (var i = 1; i <= chargeNumberGrid3Columns.getRowsNum(); i++) {
		var saveChargeNumber = false;
		var chargeNumber1 = chargeNumberGrid3Columns.cells(i,1).getValue();
		var chargeNumber2 = chargeNumberGrid3Columns.cells(i,2).getValue();
		var percent = chargeNumberGrid3Columns.cells(i,3).getValue();
        if (percent != null) {
            if (percent.length > 0) {
				if (percent != "&nbsp;") {
					saveChargeNumber = true;
				}else {
					saveChargeNumber = false;
				}
			}else {
				saveChargeNumber = false;
			}
		}else {
			saveChargeNumber = false;
		}
		if (saveChargeNumber) {
			chargeNumbersData[j++] = {
				accountNumber: 	    chargeNumber1.trim(),
				accountNumber2:     chargeNumber2.trim(),
				percentage:			percent.trim(),
				chargeType:			currentChargeType,
                numberOfColumns:    currentChargeNumberColumn,
                chargeAllowNull1:   currentChargeAllowNull1,
                chargeAllowNull2:   currentChargeAllowNull2,
                chargeAllowNull3:   currentChargeAllowNull3,
                chargeAllowNull4:   currentChargeAllowNull4
			};
			tmpChargeNumber += chargeNumber1.trim()+"!"+chargeNumber2.trim()+"!"+percent.trim()+"|";
		}
	} //end of loop
    if (chargeType == 'd') {
        currentDirectChargeNumbers = tmpChargeNumber;
    }else {
        currentIndirectChargeNumbers = tmpChargeNumber;
    }
} //end of method

function saveChargeNumber4Columns(chargeType) {
    //first remove previous data
    removeChargeTypeFromData(chargeType);
    var tmpChargeNumber = "";
    var j = chargeNumbersData.length;
    for (var i = 1; i <= chargeNumberGrid4Columns.getRowsNum(); i++) {
		var saveChargeNumber = false;
		var chargeNumber1 = chargeNumberGrid4Columns.cells(i,1).getValue();
		var chargeNumber2 = chargeNumberGrid4Columns.cells(i,2).getValue();
		var chargeNumber3 = chargeNumberGrid4Columns.cells(i,3).getValue();
		var percent = chargeNumberGrid4Columns.cells(i,4).getValue();
		if (percent != null) {
			if (percent.length > 0) {
				if (percent != "&nbsp;") {
					saveChargeNumber = true;
				}else {
					saveChargeNumber = false;
				}
			}else {
				saveChargeNumber = false;
			}
		}else {
			saveChargeNumber = false;
		}
		if (saveChargeNumber) {
			chargeNumbersData[j++] = {
				accountNumber: 	    chargeNumber1.trim(),
				accountNumber2:     chargeNumber2.trim(),
				accountNumber3:     chargeNumber3.trim(),
				percentage:			percent,
				chargeType:			chargeType,
                numberOfColumns:    currentChargeNumberColumn,
                chargeAllowNull1:   currentChargeAllowNull1,
                chargeAllowNull2:   currentChargeAllowNull2,
                chargeAllowNull3:   currentChargeAllowNull3,
                chargeAllowNull4:   currentChargeAllowNull4
			};
			tmpChargeNumber += chargeNumber1.trim()+"!"+chargeNumber2.trim()+"!"+chargeNumber3.trim()+"!"+percent.trim()+"|";
        }
	}
    if (chargeType == 'd') {
        currentDirectChargeNumbers = tmpChargeNumber;
    }else {
        currentIndirectChargeNumbers = tmpChargeNumber;
    }
} //end of method

function saveChargeNumber5Columns(chargeType) {
	//first remove previous data
    removeChargeTypeFromData(chargeType);
    var tmpChargeNumber = "";
    var j = chargeNumbersData.length;
	for (var i = 1; i <= chargeNumberGrid5Columns.getRowsNum(); i++) {
		var saveChargeNumber = false;
		var chargeNumber1 = chargeNumberGrid5Columns.cells(i,1).getValue();
		var chargeNumber2 = chargeNumberGrid5Columns.cells(i,2).getValue();
		var chargeNumber3 = chargeNumberGrid5Columns.cells(i,3).getValue();
		var chargeNumber4 = chargeNumberGrid5Columns.cells(i,4).getValue();
		var percent = chargeNumberGrid5Columns.cells(i,5).getValue();
		if (percent != null) {
			if (percent.length > 0) {
				if (percent != "&nbsp;") {
					saveChargeNumber = true;
				}else {
					saveChargeNumber = false;
				}
			}else {
				saveChargeNumber = false;
			}
		}else {
			saveChargeNumber = false;
		}
		if (saveChargeNumber) {
			chargeNumbersData[j++] = {
				accountNumber: 	    chargeNumber1.trim(),
				accountNumber2:     chargeNumber2.trim(),
				accountNumber3:     chargeNumber3.trim(),
				accountNumber4:     chargeNumber4.trim(),
				percentage:			percent.trim(),
				chargeType:			currentChargeType,
                numberOfColumns:    currentChargeNumberColumn,
                chargeAllowNull1:   currentChargeAllowNull1,
                chargeAllowNull2:   currentChargeAllowNull2,
                chargeAllowNull3:   currentChargeAllowNull3,
                chargeAllowNull4:   currentChargeAllowNull4
			};
			tmpChargeNumber += chargeNumber1.trim()+"!"+chargeNumber2.trim()+"!"+chargeNumber3.trim()+"!"+chargeNumber4.trim()+"!"+percent.trim()+"|";
		}
	}
    if (chargeType == 'd') {
        currentDirectChargeNumbers = tmpChargeNumber;
    }else {
        currentIndirectChargeNumbers = tmpChargeNumber;
    }
}

function savePoNumber() {
	//po number
	var tmpPoNumber = $("poCombo");
	if (tmpPoNumber == null || tmpPoNumber.value.length == 0) {
		tmpPoNumber = $("poInput");
	}

	if (tmpPoNumber != null && tmpPoNumber.value.length > 0) {
		currentPoNumber = tmpPoNumber.value;
	}else {
		currentPoNumber = "";
	}
}

function saveReleaseNumber() {
	//release number
	var tmpReleaseNumber = $("poLineInput");
	if (tmpReleaseNumber != null && tmpReleaseNumber.value.length > 0) {
		currentReleaseNumber = tmpReleaseNumber.value;
	}else {
		currentReleaseNumber = "";
	}
}
