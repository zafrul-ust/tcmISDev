var dhxWins = null;
var children = new Array();
resizeGridWithWindow=true;
var showMixtureColumnAudit = 'N';

function $(a) {
	return document.getElementById(a);
}

function editOnLoad(action) {
	closeTransitWin();
	if (action == 'deleteRequest' && !showErrorMessage) {
		parent.parent.closeTabx('cataddreq'+$v("requestId"));
    }else if (action == 'resubmitRequest' && !showErrorMessage) {
		parent.parent.closeTabx('cataddreq'+$v("originalRequestId"));
        showResubmitCatalogAddRequestScreen();
    }else {
		startOnload();
        if (hasHmrb) {
            document.getElementById('hmrbDataDiv').style["display"]="";
		    initializeHmrbGrid();
        }

        document.getElementById('qplDataDiv').style["display"]="";
		initializeQplGrid();

		document.getElementById('useApprovalDataDiv').style["display"]="";
		initializeUseApprovalGrid();

		document.getElementById('specDataDiv').style["display"]="";
		initializeSpecGrid();
		specGridAlreadyLoaded = true;

		document.getElementById('flowdownDataDiv').style["display"]="";
		initializeFlowdownGrid();

		setViewLevel();
		if($v('hasCatalogAddStorageTab') == 'Y')
			{

				document.getElementById('storageDataDiv').style["display"]="";
				for(var i = 0; i < storageJsonMainData.rows.length; i++)
					if(viewLevel == 'view')
						{
							storageJsonMainData.rows[i].data[2] = 'N';
							storageJsonMainData.rows[i].data[3] = 'N';

						}

				initializeStorageGrid();
			}

		//preselect the the first tab
		if (selectedTabId > 0) {
			togglePage(0);
		}

        if ($v('hasMaterialCategoryOption') == 'Y') {
            showMaterialCategoryOption();
        }
        showCatPartAttributeInfo();
		showRoomTempOutTimeInfo();

        if (showErrorMessage) {
			showMessageDialog(messagesData.errors,$v("userErrorMsg"),false,"editOnLoad");
		}
		resizeWindowSizes();

        //open catalog add edit popup if user requested new size packaging from the catalog screen
        if (action != 'submit' && $v("newPartFromExistingItemModifyPkg") == "Y") {
            //first select row
            qplSelectedRowId = 1;
            qplBeanGrid.selectRow(qplBeanGrid.getRowIndex(qplSelectedRowId),null,false,false);
            editItemData();
        }
  	  if (action == 'submit' && $v('seaGatePopUp').length > 0) {
  		   window.open($v('seaGatePopUp'), "SeaGatePopUp");
		}
    }
} //end of method

function showMaterialCategoryOption() {
    showMaterialCategory();
    //don't show material category if it has only 1 value
    //the reason for this is for Bell/Cessna we create 1 category and push
    //their data down to subcategory
    if (altMaterialCategory.length > 1)
        $("materialCategorySpan").style["display"] = "";
    else
        $("materialCategorySpan").style["display"] = "none";

    $("materialSubCategorySpan").style["display"] = "";
} //end of method

function showMaterialCategory() {
  var idArray = altMaterialCategory;
  var inv = $("materialCategoryId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }

  var selectedIndex = 0;
  if( idArray != null ) {
	  var startingIndex = 0;
      if (idArray.length == 0 || idArray.length > 1) {
	  	 setOption(0,messagesData.selectOne,"", "materialCategoryId");
		 startingIndex = 1;
	  }
      for (var i=0; i < idArray.length; i++) {
        setOption(startingIndex,idArray[i].materialCategoryName,idArray[i].materialCategoryId, "materialCategoryId");
        if (idArray[i].materialCategoryId == $v("selectedMaterialCategoryId")) {
            selectedIndex = startingIndex;
        }
        startingIndex++;
      }
  }else {
    setOption(0,messagesData.selectOne,"", "materialCategoryId");
  }
  $("materialCategoryId").selectedIndex = selectedIndex;
  materialCategoryChanged();
} //end of method

function materialCategoryChanged() {
  var selectedMaterialCategory = $("materialCategoryId").value;
  var idArray = altMaterialSubCategory[selectedMaterialCategory];
  var inv = $("materialSubcategoryId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }

  var selectedIndex = 0;
  if( idArray != null ) {
	  var startingIndex = 0;
      if (idArray.length == 0 || idArray.length > 1) {
	  	 setOption(0,messagesData.selectOne,"", "materialSubcategoryId");
		 startingIndex = 1;
	  }
      for (var i=0; i < idArray.length; i++) {
        setOption(startingIndex,idArray[i].materialSubcategoryName,idArray[i].materialSubcategoryId, "materialSubcategoryId");
        if (idArray[i].materialSubcategoryId == $v("selectedMaterialSubcategoryId")) {
            selectedIndex = startingIndex;
        }
        startingIndex++;
      }
  }else {
    setOption(0,messagesData.selectOne,"", "materialSubcategoryId");
  }
  $("materialSubcategoryId").selectedIndex = selectedIndex;
} //end of method

//start resubmit
function showResubmitCatalogAddRequestScreen() {
    var requestId  =  $v("requestId");
    if ( requestId != null &&  requestId != 0) {
        var loc = "catalogaddrequest.do?uAction=view&requestId="+requestId;
        try{
            parent.parent.openIFrame("cataddreq"+requestId,loc,""+messagesData.cataddreq+" "+requestId,"","N");
        }catch (ex) {
            openWinGeneric(loc,"cataddreq"+requestId+"","800","600","yes","50","50");
        }
    }
}

function canResubmitRequest() {
    var result = false;
    var facilityMaxResubmittal = $v("facilityMaxResubmittal");
    if (facilityMaxResubmittal != null && facilityMaxResubmittal != 0) {
        try {
            var checkRole = false;
            var numberOfResubmittal = $v("numberOfResubmittal");
            if (numberOfResubmittal != null) {
                if (facilityMaxResubmittal*1 > numberOfResubmittal*1) {
                    checkRole = true;
                }
            }else {
                checkRole = true;
            }

            if (checkRole) {
                for (var i = 0; i < altApproversList.length; i++) {
                    if (altApproversList[i] == $("personnelId").value) {
                        if ("Y" == altApproverRolesResubmitRequest[i]) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }catch (ex){}
    }
    return result;
}

function resubmitRequest() {
    if (confirm(messagesData.resubmitWarning)) {
        showTransitWin();
        enableFieldsForFormSubmit();
        $("uAction").value = 'resubmitRequest';
        document.genericForm.submit();
    }
}
//end of resubmit

function catPartAttribute1Clicked() {
	$("catPartAttribute2").checked = false;
    showCatPartAttributeInfo();
}

function catPartAttribute2Clicked() {
	$("catPartAttribute1").checked = false;;
    showCatPartAttributeInfo();
}

function showCatPartAttributeInfo() {
	if (altCatalogFacility[0].catPartAttributeHeader != null && altCatalogFacility[0].catPartAttributeHeader.length > 0) {
		$("catPartAttributeSpan").style["display"] = "";
		if ($("catPartAttribute1").checked || $("catPartAttribute2").checked) {
			if (altCatalogFacility[0].qualityIdLabel != null && altCatalogFacility[0].qualityIdLabel.length > 0) {
				$("qualityIdSpan").style["display"] = "";
			}else {
				$("qualityIdSpan").style["display"] = "none";
			}
		}else {
			$("qualityIdSpan").style["display"] = "none";
		}
	}else {
		$("catPartAttributeSpan").style["display"] = "none";
		$("qualityIdSpan").style["display"] = "none";
	}
}

/*Calculates the height and width. I only use the width though*/
function resizeWindowSizes() {
	setWindowSizes();
	if (hasHmrb) {
        resetGridSize('hmrbDataDiv', hmrbBeanGrid);
        //this method will fit all columns on given screen for grid with few columns
        reSizeCoLumnWidths(hmrbBeanGrid);
    }
    resetGridSize('qplDataDiv', qplBeanGrid);
	resetGridSize('useApprovalDataDiv', useApprovalBeanGrid, $v("startingView") == 3 ? 30 : 0);

	if($v('hasCatalogAddStorageTab') == 'Y')
	{
		resetGridSize('storageDataDiv', storageBeanGrid);
	}

	resetGridSize('specDataDiv', specBeanGrid);
	resetGridSize('flowdownDataDiv', flowdownBeanGrid);
    //this method will fit all columns on given screen for grid with few columns
    reSizeCoLumnWidths(flowdownBeanGrid);
}

function resetGridSize(divName, theGrid, extraMargin) {
	var fudgeFactor= 10 + (extraMargin ? extraMargin : 0);

	try {
		//8 came from manual test
		$(divName).style.width = myWidth - 8 + "px";
		$(divName).style.height = myHeight-(362 + fudgeFactor) + "px";
		theGrid.setSizes();
		updateColumnWidths(theGrid);
	}catch(ex) {
		alert(ex);
	}
    //the reason that I commented out below is because it put the entire grid display on screen
    //and look looks off when the grid has many columns (i.e. qpl)
    //reSizeCoLumnWidths(theGrid);
}


function closeThisWindow() {
	try {
		//opener.parent.closeTransitWin();
	}catch(e){}
}

function cancel() {
	window.close();
}

function submitUpdate() {
  if (auditSaveData() && auditData() && auditQplData()) {
	  showTransitWin();
      enableFieldsForFormSubmit();

      try {
			saveQplData();
	  } catch(ex) {}

	  try {
			saveUseApprovalData();
	  } catch(ex) {}

		if($v('hasCatalogAddStorageTab') == 'Y')
		{
		  try {
			  saveStorage();
		  } catch(ex) {}
		}
      //Add more save functions here if there are more tabs to save
      if ($v('showItarControl') == 'true' && $v('viewLevel') == 'approver' &&  $v("allowEditSpec") == 'Y') {
        try {
		    saveSpecData();
		} catch(ex) {}
      }
      
      $("uAction").value = 'submit';
	  setTimeout("document.genericForm.submit()",200);
  }else {
	  setViewLevel();
	  return false;
  }
}

function saveData() {
	if (auditSaveData()) {
		showTransitWin();
        enableFieldsForFormSubmit();
        try {
            partialQplAuditResults = saveQplData(true);
            if(!partialQplAuditResults)
            	return false;
        } catch(ex) {}

		try {
            saveUseApprovalData();
        } catch(ex) {}

		if($v('hasCatalogAddStorageTab') == 'Y')
		{
			try {
				setTimeout("saveStorage()",100);
			} catch(ex) {}
		}
		//Add more save functions here if there are more tabs to save
        if ($v('showItarControl') == 'true' && $v('viewLevel') == 'approver' && $v("allowEditSpec") == 'Y') {
            try {
		        saveSpecData();
		    } catch(ex) {}
        }

        $("uAction").value = 'save';
        setTimeout("document.genericForm.submit()",200);
    }
}

function deleteRequest() {
	showTransitWin();
	enableFieldsForFormSubmit();
	$("uAction").value = 'deleteRequest';
	$("maxRecertNumber").value = '';
	document.genericForm.submit();
}

function submitApproveForm() {
    if (auditSaveData() && auditData() && auditEmap() && auditQplData()) {
		enableFieldsForFormSubmit();
        try {
			saveQplData();
        } catch(ex) {}

        try {
            saveUseApprovalData();
        } catch(ex) {}
        //Add more save functions here if there are more tabs to save

		if($v('hasCatalogAddStorageTab') == 'Y')
		{
	      try {
	    	  setTimeout("saveStorage()",100);
	  	  } catch(ex) {}
		}

        if ($v('showItarControl') == 'true' && $v('viewLevel') == 'approver' && $v("allowEditSpec") == 'Y') {
            try {
		        saveSpecData();
		    } catch(ex) {}
        }

        showApprovalRoleWin();
	}else {
		setViewLevel();
	}
}

function auditEmap() {
	var result = true;
	for (var i = 0; i < altApproverRolesList.length; i++) {
		if (altApproverRolesList[i] == 'M&P' || altApproverRolesList[i] == 'MRP') {
			if ($("catPartAttribute1").checked) {
				if ($v("qualityId").trim().length == 0) {
					alert(messagesData.validvalues+"\n"+"\t"+$v("qualityIdLabel"));
					result = false;
				}
			}
		}
	}
	return result;
}


function approvalDetail() {
	showNewChemApprovalDetail($v("requestId"));
}

function showApprovalRulesResult() {
	loc = 'shownewchemlistdetail.do?requestId='+$v("requestId")+"&companyId="+$v("companyId")+"&showPassAndFailReviewRules=N";
	children[children.length] = openWinGeneric(loc,"showNewChemKeywordListDetail","500","500","yes","50","50","20","20","no");
}

function showAllApprovalRulesResult() {
	loc = 'shownewchemlistdetail.do?requestId='+$v("requestId")+"&companyId="+$v("companyId")+"&showPassAndFailReviewRules=Y";
	children[children.length] = openWinGeneric(loc,"showNewChemKeywordListDetail","500","500","yes","50","50","20","20","no");
}

function requestHasAtLeastOneItem() {
	var result = false;
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= qplBeanGrid.getRowsNum();i++) {
		if (qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("dataSource")).getValue() == 'new') {
			result = true;
			break;
		}
	}
	return result;
}

function requestHasOnlyItemFadeoutFromQpl() {
	var result = true;
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= qplBeanGrid.getRowsNum();i++) {
		if (qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("dataSource")).getValue() == 'new') {
			if (qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("startingView")).getValue() != '5') {
				result = false;
				break;
			}
		}
	}
	return result;
}

function requestHasAtLeastOneWorkArea() {
	var result = false;
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= useApprovalBeanGrid.getRowsNum();i++) {
	    if ($v("startingView") == 3) {
	        //if request is for new work area approval then user has to enter in at least 1 new work area record
	        if (useApprovalBeanGrid.cells(i,useApprovalBeanGrid.getColIndexById("dataSource")).getValue() == 'new'){
                result = true;
                break;
            }
	    }else {
            if (useApprovalBeanGrid.cells(i,useApprovalBeanGrid.getColIndexById("dataSource")).getValue() == 'new' ||
                 useApprovalBeanGrid.cells(i,useApprovalBeanGrid.getColIndexById("dataSource")).getValue() == 'catalog') {
                result = true;
                break;
            }
		}
	}
	return result;
}

function requestHasAtLeastOneApprovalCode() {
	var result = false;
    //NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
    for(var i = 1; i <= hmrbBeanGrid.getRowsNum();i++) {
        if ($v("startingView") == 7) {
            //if request is for new approval code then user has to enter in at least 1 approval code record
            if (hmrbBeanGrid.cells(i,hmrbBeanGrid.getColIndexById("dataSource")).getValue() == 'new' ) {
                result = true;
                break;
            }
        }else {
            if (hmrbBeanGrid.cells(i,hmrbBeanGrid.getColIndexById("dataSource")).getValue() == 'new' ||
                hmrbBeanGrid.cells(i,hmrbBeanGrid.getColIndexById("dataSource")).getValue() == 'catalog' ) {
                result = true;
                break;
            }
		}
	}
	return result;
}

function auditHmrbCompatibility() {
    missingData = "";
    //NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
    
    tmpHmrbBeanArr = new Array();
    tmpUseApprovalBeanArr = new Array();
    
    for(var i = 1; i <= hmrbBeanGrid.getRowsNum();i++)
		tmpHmrbBeanArr.push(hmrbBeanGrid.cells(i,hmrbBeanGrid.getColIndexById("applicationUseGroupId")).getValue());
    
    for(var j = 1; j <= useApprovalBeanGrid.getRowsNum();j++)
        if (useApprovalBeanGrid.cells(j,useApprovalBeanGrid.getColIndexById("dataSource")).getValue() == 'new')
        	tmpUseApprovalBeanArr.push(useApprovalBeanGrid.cells(j,useApprovalBeanGrid.getColIndexById("applicationUseGroupId")).getValue());
    
    for(var i = 0; i < tmpHmrbBeanArr.length;i++) 
            for(var j = tmpUseApprovalBeanArr.length - 1; j >= 0 ;j--) 
            	if(tmpHmrbBeanArr[i] == tmpUseApprovalBeanArr[j])
            			tmpUseApprovalBeanArr.splice(j,1);
    
    if (tmpUseApprovalBeanArr.length != 0)
        missingData = messagesData.hmrbUseApprovalApprovalCodeMismatch+"\n";
    return missingData;
}

//don't call this method yet, since it will not handle all of the cases
//for example it can not handle approval roles that can be auto approved
function auditPartRevision() {
	var result = true;
	var missingFields = "";
    //if catAddPartRevisionRequired = Y and customer_part_revision is null and this is last approval role
    //then he/she is responsible for filling out data before approving request
    if (altCatalogFacility[0].catAddPartRevisionRequired == 'Y' &&
        $v("customerPartRevision").length == 0 &&
        $v("lastApprovalRole").length == 'Y') {
        missingFields += messagesData.partRevision;
    }

	if (missingFields.length > 0) {
        alert(messagesData.validvalues+"\n"+missingFields);
		result = false;
    }
	return result;
}

function auditData() {
	var result = true;
	var missingFields = "";
    var missingData = "";
    //header data
    if ($v("startingView") < 3) {
        if ($v("hasMaterialCategoryOption") == 'Y' && $v("allowEditMatlCategorySubcategory") == 'Y') {
            if (isEmptyV("materialCategoryId")) {
                missingFields += "\t"+messagesData.materialCategory+"\n";
            }
            if (isEmptyV("materialSubcategoryId")) {
               missingFields += "\t"+messagesData.materialSubcategory+"\n";
            }
        }
    }
    //if user select Incoming Testing
    if ($("incomingTesting").checked && $v("catalogHasDefaultTest") == 'N') {
        missingData += messagesData.incomingTestingRequired+"\n";            
    }

    //make sure that there is at least one Approval code for this request
    var catAddEmapAuditOption = $v('catAddEmapAuditOption').toLowerCase();
    if (hasHmrb) {
        if (!requestHasAtLeastOneApprovalCode()) {
            missingData += messagesData.requestMissingApprovalCode+"\n";
        }
 
        if(catAddEmapAuditOption == 'yes' && $v('qualityId').length == 0)
        	missingData += messagesData.hmrbEmapRequired+"\n";
        else if(catAddEmapAuditOption == 'checkhmrb' && $v('qualityId').length == 0)
        	missingData += hmrbEmapRequired();
    }
    else if(catAddEmapAuditOption == 'yes' && $v('qualityId').length == 0)
        	missingData += messagesData.hmrbEmapRequired+"\n";

    //make sure that there is at least one new item in the qpl for this request
	if (!requestHasAtLeastOneItem()) {
		missingData += messagesData.requestMissingItemInQpl+"\n";
	}
	//check to see if facility required at least one work area for this request
	if ($v("catAddApprovalDetailNeeded") == 'Y') {
		if (!requestHasAtLeastOneWorkArea()) {
			//if QPL has only fadeout items then no work area is needed
			if (!requestHasOnlyItemFadeoutFromQpl()) {
				missingData += messagesData.requestMissingWorkArea+"\n";
			}
		}
        //check compatibility between HMRB and Use approval tab
        if (hasHmrb) {
            missingData += auditHmrbCompatibility();
        }
		if($v('hasCatalogAddStorageTab') == 'Y')
		{
			missingData += auditStorageData();
		}
    }

    if (altCatalogFacility[0].catPartAttributeHeader != null && altCatalogFacility[0].catPartAttributeHeader.length > 0) {
        if ($("catPartAttribute1").disabled == false && $("catPartAttribute2").disabled == false) {
            if (!$("catPartAttribute1").checked && !$("catPartAttribute2").checked) {
                missingFields += "\t"+altCatalogFacility[0].catPartAttributeHeader+"\n";
            }
        }
    }

	if (missingData.length > 0 && missingFields.length > 0) {
        alert(missingData +"\n"+messagesData.validvalues+"\n"+missingFields);
		result = false;
	}else if (missingData.length > 0) {
        alert(missingData);
		result = false;
    }else if (missingFields.length > 0) {
        alert(messagesData.validvalues+"\n"+missingFields);
		result = false;
    }
    else if (!auditReplaceMsds()) {
		alert(messagesData.invalidReplaceMsds);
		result = false;
	}
	return result;
}

function auditSaveData() {
	var result = true;
	var missingFields = "";
	//
	if ($v("maxRecertNumber") != null) {
		if ($v("maxRecertNumber").length > 0) {
			if (isNaN($v("maxRecertNumber"))) {
				missingFields += "\n"+messagesData.numberOfRecertAllowed;
			}
		}
	}

	if($v('hasCatalogAddStorageTab') == 'Y')
	{
		var storageErr = auditStorageSaveData();
		if(storageErr.length > 0)
			missingFields += "\n" + storageErr;
	}

	if (missingFields.length > 0) {
		alert(messagesData.validvalues+missingFields);
		result = false;
	}
	return result;
}

function auditQplData() {
	var result = true;
	var missingFields = "";
    var missingMsds = false;
    var missingCustomerMfgId = false;
    
    var prevLneIt = '';
    var mixRatioAmount = 0;
    var kitSize = 0;
    
    var amtMxRatioErrorMsgAdd = false;
    var qplContainsKit = $v('qplContainsKit');
    var hasHmrbTab = $v("hasHmrbTab");

    var missingMixRatioAmount = false;
    var missingMixRatioUnit = false;
    var qplBeanGridSize = qplBeanGrid.getRowsNum();
    var mixRatioAmountPermission = 'N';
    
    //NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= qplBeanGridSize;i++) {
        if (qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("permission")).getValue() == 'Y') {
            mixRatioAmountPermission = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("mixRatioAmountPermission")).getValue();
            //packaging for request that user requested modify Pkg
            tmpVal = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("packaging")).getValue();
            if (tmpVal.length == 0) {
                missingFields += "\t"+messagesData.packaging+"\n";
            }

            //shelf life and storage temp
			if ($("timeTempSensitiveYes").checked) {
				if (qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("shelfLifeDaysPermission")).getValue() == 'Y') {
					var tmpVal = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("shelfLifeDays")).getValue();
					if (tmpVal != null && tmpVal.length > 0) {
						if (isNaN(tmpVal)) {
							missingFields += "\t"+messagesData.shelfLife+"\n";
						}
					}

					if (tmpVal.length == 0) {
						missingFields += "\t"+messagesData.shelfLife+"\n";
					}
				}
				if (qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("customerTemperatureId")).getValue().length == 0) {
					missingFields += "\t"+messagesData.storageTemp+"\n";
				}
                //label color
                if ($v("labelColorRequired") == 'Y') {
                    if (qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("labelColor")).getValue().length == 0) {
					    missingFields += "\t"+messagesData.labelColor+"\n";
				    }
                }
            }
			//room temp out time
			if ($("roomTempOutTimeYes").checked) {
				tmpVal = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("roomTempOutTime")).getValue();
				if (tmpVal != null && tmpVal.length > 0) {
					if (isNaN(tmpVal)) {
						missingFields += "\t"+messagesData.roomTempOutTime+"\n";
					}
				}
			}
            //customer msds
            if (qplBeanGrid.cells(i, qplBeanGrid.getColIndexById("customerMsdsNumberPermission")).getValue() == 'Y') {
                if (($v("requireCustomerMsds") == 'Y') || $v("catAddRequestorEditMsdsId") == "MANDATORY") {
                    tmpVal = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("customerMsdsNumber")).getValue();
                    if (tmpVal.length == 0  && !missingMsds) {
                        missingFields += "\t"+messagesData.customerMsdsNumber+"\n";
                        missingMsds = true;
                    }
                }
            }

            //customer manufacturer ID
            if (qplBeanGrid.cells(i, qplBeanGrid.getColIndexById("customerMfgIdPermission")).getValue() == 'Y') {
                tmpVal = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("customerMfgId")).getValue();
                customerMfgIdDisplay = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("customerMfgIdDisplay")).getValue();
                if (tmpVal.length == 0 && customerMfgIdDisplay.length == 0  && !missingCustomerMfgId) {
                    missingFields += "\t"+messagesData.customerMfgId+"\n";
                    missingCustomerMfgId = true;
                }
            }

            //CHECK MIX RATIO DATA
            //check for Mix Ratio Unit
            if(showMixtureColumnAudit == 'Y' && mixRatioAmountPermission == 'Y' &&
               qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("mixRatioSizeUnit")).getValue() == '') {
                missingMixRatioUnit = true;
            }
            //check for Mix Ratio amount
			if(showMixtureColumnAudit == 'Y' && mixRatioAmountPermission == 'Y') {
				var lineItem = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("lineItem")).getValue();
				if(prevLneIt == '')
					prevLneIt = lineItem;		
					
				if(mixRatioAmountPermission == 'Y' && prevLneIt == lineItem) {
                    ++kitSize;

                    if((qplContainsKit == false || qplContainsKit == '') && hasHmrbTab == 'Y')
                    {
                        qplContainsKit = true;
                        $('qplContainsKit').value = true;
                    }

                    rowMixRatioAmount = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("mixRatioAmount")).getValue();
                    if(rowMixRatioAmount != '')
                    {
                        if(!amtMxRatioErrorMsgAdd && (!isFloat(rowMixRatioAmount,false) || rowMixRatioAmount < 0))
                            amtMxRatioErrorMsgAdd = true;
                        else
                            mixRatioAmount +=  parseFloat(rowMixRatioAmount);
                    }else {
                        missingMixRatioAmount = true;
                    }
                }else {
                    //when go to new kit, this is to make sure that the previous kit has correct mix ratio amount
                    if(!amtMxRatioErrorMsgAdd && ((kitSize > 0 && mixRatioAmount == 0) || (mixRatioAmount > 0 && mixRatioAmount != 100 && showMixRatioUnitAsVolumeWeight)))
							amtMxRatioErrorMsgAdd = true;
					
					kitSize = 0;
				    mixRatioAmount=0;
				    prevLneIt = '';
					
					if(mixRatioAmountPermission == 'Y'){
                        prevLneIt = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("lineItem")).getValue();
                        ++kitSize;

                        rowMixRatioAmount = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("mixRatioAmount")).getValue();
                        if(rowMixRatioAmount != '')
                        {
                            if(!amtMxRatioErrorMsgAdd && (!isFloat(rowMixRatioAmount,false) || rowMixRatioAmount < 0))
                                amtMxRatioErrorMsgAdd = true;
                            else
                                mixRatioAmount +=  parseFloat(rowMixRatioAmount);
                        }else {
                            missingMixRatioAmount = true;
                        }
                    }
				} //end of else next line
			} //end of if showMixtureColumnAudit and has permission
		} //end of has permission for row
	} //end of loop for each row

    //has kit data
    if(showMixtureColumnAudit == 'Y' && mixRatioAmountPermission == 'Y') {
        //if mix ratio amount has error
        if (amtMxRatioErrorMsgAdd) {
            if (showMixRatioUnitAsVolumeWeight)
                missingFields += "\t"+messagesData.percentVolWeight+"\n";
            else
                missingFields += "\t"+messagesData.missingMixRatioAmount+"\n";
        }else {
            if ($v("mixRatioRequired") == 'Y') {
                //mix ratio amount
                if (showMixRatioUnitAsVolumeWeight) {
                    if (mixRatioAmount.toFixed(4) != 100)
                        missingFields += "\t"+messagesData.percentVolWeight+"\n";
                    else if (missingMixRatioAmount)
                        missingFields += "\t"+messagesData.missingMixRatioAmount+"\n";
                }else {
                    if (missingMixRatioAmount)
                        missingFields += "\t"+messagesData.missingMixRatioAmount+"\n";
                }
                //mix ratio unit
                if (missingMixRatioUnit)
                    missingFields += "\t"+messagesData.percentVolWeightUnitCount+"\n";
            } //end of if mix Ratio Required
        } //end of if mix ratio amounts do not has errors
    } //end of has mixture data

	if (missingFields.length > 0) {
		alert(messagesData.validvalues+"\n"+missingFields);
		closeTransitWin();
		result = false;
	}
	return result;
} //end of method

function auditMsdsData() {
	var result = true;
	
	if ($v("catAddRequestorEditMsdsId") == "MANDATORY") {
		for(var i = 1; i <= qplBeanGrid.getRowsNum();i++) {
			if ($v("customerMsdsNumber"+i) == "") {
				result = false;
			}
		}
	}
	
	return result;
}

function enableFieldsForFormSubmit() {
	setHeaderViewLevel('');
	setPartInfoViewLevel('');
}

var viewLevel = "view";
var approvalRoleBeforeTcmQc = "AFTER";
function setViewLevel() {
	if ($("requestStatus").value == 7 || $("requestStatus").value == 9 || $("requestStatus").value == 12) {
		viewLevel = "view";
		setRequestActionLevel();
		setHeaderViewLevel(true);
		setPartInfoViewLevel(true);
	}else if ($("requestStatus").value < 5 || $("requestStatus").value == 15 || $("requestStatus").value == 17) {
		//in draft status, only requestor can edit data
		if ($("personnelId").value == $("requestor").value || $("personnelId").value == $("resubmitRequestor").value) {
			viewLevel = "requestor";
			setRequestActionLevel();
			setHeaderViewLevel('');
			if ($v("startingView") < 3) {
				setPartInfoViewLevel('');
			}else {
				setPartInfoViewLevel(true);
			}
            approvalRoleBeforeTcmQc = "BEFORE";
        }else {
			setHeaderViewLevel(true);
			setPartInfoViewLevel(true);
		}
	}else {
		var userIsAnApprover = false;
		for (var i = 0; i < altApproversList.length; i++) {
			if (altApproversList[i] == $("personnelId").value) {
				userIsAnApprover = true;
                approvalRoleBeforeTcmQc = altApproverRoleBeforeTcmQc[i];
                break;
			}
		}
		if (userIsAnApprover) {
			viewLevel = "approver";
			setRequestActionLevel();
			setHeaderViewLevel('');
			if ($v("startingView") < 3) {
				setPartInfoViewLevel('');
			}else {
				setPartInfoViewLevel(true);
			}
		}else {
			viewLevel = "view";
			setRequestActionLevel();
			setHeaderViewLevel(true);
			setPartInfoViewLevel(true);
		}
	}
}

//input arguement for this method are:
//requestor - owner of request and request is still draft
//approver  - request is current pending this approver action
//view      - viewing request
function setRequestActionLevel() {
	if (viewLevel == 'requestor') {
		//request action
		$("requestActionSpan").style["display"] = "";
		$("requestActionSpan").innerHTML = '<a href="#" onclick="submitUpdate(); return false;">'+messagesData.submit+'</a>'+
			                               ' | <a href="#" onclick="saveData(); return false;">'+messagesData.save+'</a>'+
			 							   ' | <a href="#" onclick="deleteRequest(); return false;">'+messagesData.deleteRequest+'</a>';

        //HMRB
        if (hasHmrb) {
            $("hmrbActionSpan").style["display"] = "";
		    setHmrbActionViewLevel();
        }

        //qpl action
		$("qplActionSpan").style["display"] = "";
		setQplActionViewLevel();

		//use approval action
		$("useApprovalActionSpan").style["display"] = "";
		setUseApprovalAction();

		if($v('hasCatalogAddStorageTab') == 'Y')
		{
	     $("storageActionSpan").style["display"] = "";
		    setStorageActionViewLevel();
		}

		//spec
		$("specActionSpan").style["display"] = "";
		setSpecActionViewLevel();

		//flowdown
		if ($v("startingView") < 3) {
			$("flowdownActionSpan").style["display"] = "";
			$("flowdownActionSpan").innerHTML = '<a href="#" onclick="addFlowdown(); return false;">'+messagesData.addFlowdown+'</a>'+
											    '<br>&nbsp;';
		}

	}else if (viewLevel == 'approver') {
		$("requestActionSpan").style["display"] = "";
        var tmpAction = '<a href="#" onclick="submitApproveForm(); return false;">'+messagesData.approve+'/'+messagesData.reject+'</a>'+
                        ' | <a href="#" onclick="saveData(); return false;">'+messagesData.save+'</a>'+
                        ' | <a href="#" onclick="approvalDetail(); return false;">'+messagesData.approvaldetail+'</a>';
        if ($v("hasKeywordListApproval") == 'Y') {
            tmpAction += ' | '+messagesData.approvalRules+': <a href="#" onclick="showApprovalRulesResult(); return false;">'+messagesData.results+'</a>'+
                         ' , <a href="#" onclick="showAllApprovalRulesResult(); return false;">'+messagesData.allTests+'</a>';
        }
        if (canResubmitRequest()) {
            tmpAction += ' | <a href="#" onclick="resubmitRequest(); return false;">'+messagesData.editAndResubmit+'</a>';
        }
        $("requestActionSpan").innerHTML = tmpAction;

        //HMRB
        if (hasHmrb) {
            $("hmrbActionSpan").style["display"] = "";
		    setHmrbActionViewLevel();
        }

        //qpl action
		$("qplActionSpan").style["display"] = "";
		setQplActionViewLevel();

		//use approval action
		$("useApprovalActionSpan").style["display"] = "";
		setUseApprovalAction();

		if($v('hasCatalogAddStorageTab') == 'Y')
		{
	     $("storageActionSpan").style["display"] = "";
		    setStorageActionViewLevel();
		}

		//spec
		$("specActionSpan").style["display"] = "";
		setSpecActionViewLevel();

		//flowdown
		if ($v("startingView") < 3) {
			$("flowdownActionSpan").style["display"] = "";
			$("flowdownActionSpan").innerHTML = '<a href="#" onclick="addFlowdown(); return false;">'+messagesData.addFlowdown+'</a>'+
												'<br>&nbsp;';
		}
	}else {   //viewLevel == view
		if ($v("requestStatus")*1 > 4 && $("requestStatus").value != 15 && $("requestStatus").value != 17) {
			$("requestActionSpan").style["display"] = "";
            if ($v("hasKeywordListApproval") == 'Y') {
                $("requestActionSpan").innerHTML = '<a href="#" onclick="approvalDetail(); return false;">'+messagesData.approvaldetail+'</a>'+
                                                   ' | '+messagesData.approvalRules+': <a href="#" onclick="showApprovalRulesResult(); return false;">'+messagesData.results+'</a>'+
                                                   ' , <a href="#" onclick="showAllApprovalRulesResult(); return false;">'+messagesData.allTests+'</a>';
            }else {
                $("requestActionSpan").innerHTML = '<a href="#" onclick="approvalDetail(); return false;">'+messagesData.approvaldetail+'</a>';
            }
        }else {
			$("requestActionSpan").style["display"] = "none";
		}
        //HMRB
        if (hasHmrb) {
            $("hmrbActionSpan").style["display"] = "none";
        }
        $("qplActionSpan").style["display"] = "none";
		$("useApprovalActionSpan").style["display"] = "none";
		$("specActionSpan").style["display"] = "none";
		$("flowdownActionSpan").style["display"] = "none";
	}
}

var qplActionText = '';
function setQplActionViewLevel() {
	if ($v("startingView") == 3 || $v("startingView") == 7) {
		//don't allow users to edit anything if asking for new work area approval or new approval code approval
		$("qplActionSpan").innerHTML = '';
	}else {
        if (editableSql()) {
            //only allowing requestor to add new item to QPL
            if (viewLevel == 'requestor') {
                qplActionText = '<a href="#" onclick="addItem(); return false;">'+messagesData.addItem+'</a>'+
                                ' | <a href="#" onclick="showObsolete(); return false;">'+messagesData.showObsoleteItems+'</a>'+
							    ' | <a href="#" onclick="prepareForTransitWin();saveQplData(true); return false;">'+messagesData.save+'</a>'+
							    ' | <a href="#" onclick="showAttachedFiles(); return false;">'+messagesData.fileAttached+'</a>';
            }else {
                qplActionText = '<a href="#" onclick="showObsolete(); return false;">'+messagesData.showObsoleteItems+'</a>'+
							    ' | <a href="#" onclick="prepareForTransitWin();saveQplData(true); return false;">'+messagesData.save+'</a>'+
							    ' | <a href="#" onclick="showAttachedFiles(); return false;">'+messagesData.fileAttached+'</a>';
            }
        }else {
			qplActionText = '<a href="#" onclick="showObsolete(); return false;">'+messagesData.showObsoleteItems+'</a>'+
                            ' | <a href="#" onclick="showAttachedFiles(); return false;">'+messagesData.fileAttached+'</a>';
		}
		$("qplActionSpan").innerHTML = qplActionText+'<br>&nbsp;';
	}
}


function showAttachedFiles() {
 var loc = "catalogaddrequest.do?uAction=findAttachedFiles&requestId="+$v("requestId");
 children[children.length] = openWinGeneric(loc,"showFilesList","650","500","yes","50","50","20","20","no");
}

function prepareForTransitWin() {
	$("closeTransitWinflag").value="Y";
	showTransitWin();
}

function editableSql() {
	editableFields = false;
    //handle the case where user asking for new catalog add
    if (viewLevel == 'requestor') {
        editableFields = true;
    }else {
        //NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
        for(var i = 1; i <= qplBeanGrid.getRowsNum();i++) {
            if(gridCellValue(qplBeanGrid,i,"permission") == 'Y') {
                editableFields = true;
                break;
            }
        }
    }
    return editableFields;
}

function setUseApprovalAction() {
    //requestor and approver are NOT allow to edit anything if Modify QPL
    if (viewLevel == 'requestor' && $v("startingView") != 4) {
		$("useApprovalActionSpan").innerHTML = '<a href="#" onclick="addWorkArea(); return false;">'+messagesData.addWorkArea+'</a>'+
											   ' | <a href="#" onclick="prepareForTransitWin(); saveUseApprovalData(); return false;">'+messagesData.save+'</a>'+
											   '<br>&nbsp;';
	}else if (viewLevel == 'approver' && $v("startingView") != 4) {
		tmpHasPermission = false;
		tmpHasAllWorkAreas = false;
		//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
		for(var i = 1; i <= useApprovalBeanGrid.getRowsNum();i++) {
			if (useApprovalBeanGrid.cells(i,useApprovalBeanGrid.getColIndexById("permission")).getValue() == 'Y') {
				tmpHasPermission = true;
			}
		}

        if (tmpHasPermission) {
			$("useApprovalActionSpan").innerHTML = '<a href="#" onclick="addWorkArea(); return false;">'+messagesData.addWorkArea+'</a>'+
                                                    ' | <a href="#" onclick="prepareForTransitWin(); saveUseApprovalData(); return false;">'+messagesData.save+'</a>'+
                                                    '<br>&nbsp;';
		}else {
            $("useApprovalActionSpan").innerHTML = '';
        }
	}else {
        $("useApprovalActionSpan").innerHTML = '';
    }
}

function setSpecActionViewLevel() {
	if ($v("startingView") == 3 || $v("startingView") == 4 || $v("startingView") == 7) {
		//don't allow users to edit anything if asking for new work area approval or modify QPL or new approval code
		$("specActionSpan").innerHTML = '';
	}else {
        if (viewLevel == 'requestor' || (viewLevel == 'approver' &&  $v("allowEditSpec") == 'Y')) {
            tmpHasNoSpec = hasNoSpec();
            hasSmc = hasStandardMfgCert();
            tmpHasSpec = hasSpec();
            if (tmpHasNoSpec) {
                $("specActionSpan").innerHTML = '<br>&nbsp;';
            }else if (hasSmc) {
                $("specActionSpan").innerHTML = '<a href="#" onclick="addSpec(); return false;">'+messagesData.addSpec+'</a>'+
                                                          '<br>&nbsp;';
            }else if (tmpHasSpec) {
                $("specActionSpan").innerHTML = '<a href="#" onclick="addSpec(); return false;">'+messagesData.addSpec+'</a>'+
                                                ' | <a href="#" onclick="addStandardMfgCert(); return false;">'+messagesData.addStandardMfgCert+'</a>'+
                                                '<br>&nbsp;';
            }else {
                $("specActionSpan").innerHTML = '<a href="#" onclick="addSpec(); return false;">'+messagesData.addSpec+'</a>'+
                                                          ' | <a href="#" onclick="addNoSpec(); return false;">'+messagesData.addNoSpec+'</a>'+
                                                          ' | <a href="#" onclick="addStandardMfgCert(); return false;">'+messagesData.addStandardMfgCert+'</a>'+
                                                          '<br>&nbsp;';
            }
        }else {
            $("specActionSpan").innerHTML = '';
        }
    }
}

function hasSpec() {
	tmpHasSpec = false;
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= specBeanGrid.getRowsNum();i++) {
		if (specBeanGrid.cells(i,specBeanGrid.getColIndexById("specId")).getValue() != 'No Specification' &&
			 specBeanGrid.cells(i,specBeanGrid.getColIndexById("specId")).getValue() != 'Std Mfg Cert') {
			tmpHasSpec = true;
			break;
		}
	}
	return tmpHasSpec;
}

function hasNoSpec() {
	tmpHasNoSpec = false;
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= specBeanGrid.getRowsNum();i++) {
		if (specBeanGrid.cells(i,specBeanGrid.getColIndexById("specId")).getValue() == 'No Specification') {
			tmpHasNoSpec = true;
			break;
		}
	}
	return tmpHasNoSpec;
}

function hasStandardMfgCert() {
	hasSmc = false;
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= specBeanGrid.getRowsNum();i++) {
		if (specBeanGrid.cells(i,specBeanGrid.getColIndexById("specId")).getValue() == 'Std Mfg Cert') {
			hasSmc = true;
			break;
		}
	}
	return hasSmc;
}

function setHeaderViewLevel(flag) {
	/*
	$("catPartNo").disabled = flag;
	$("stocked").disabled = flag;
	$("messageToApprovers").disabled = flag;
	*/
}

function setPartInfoViewLevel(flag) {
	$("catPartNo").disabled = flag;
	$("stocked").disabled = flag;
	$("replacesPartNo").disabled = flag;
	$("selectedReplacesPartNo").disabled = flag;
	$("partDescription").disabled = flag;
	$("timeTempSensitiveYes").disabled = flag;
	$("timeTempSensitiveNo").disabled = flag;
	$("roomTempOutTimeYes").disabled = flag;
	$("roomTempOutTimeNo").disabled = flag;
	$("recertInstructions").disabled = flag;
	$("maxRecertNumber").disabled = flag;
    $("messageToApprovers").disabled = flag;
    $("incomingTesting").disabled = flag;
	$("catPartAttribute1").disabled = flag;
	$("catPartAttribute2").disabled = flag;
	$("qualityId").disabled = flag;
    $("customerPartRevision").disabled = flag;
    $("materialCategoryId").disabled = flag;
    $("materialSubcategoryId").disabled = flag;
}

//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
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
		newNodeInnerHTML +="<span id=\""+tabId+"LinkSpan\" class=\"selectedSpanTab\"><img src=\""+tabIcon+"\" class=\"iconImage\">"+tabName;
		newNodeInnerHTML +="<br class=\"brNoHeight\"><img src=\"/images/minwidth.gif\" width=\""+(tabName.length*2)+"\" height=\"0\"></span></a>";
		newNode.innerHTML = newNodeInnerHTML;
		list.appendChild(newNode);
		togglePage(tabId);
	}
}

function togglePage(tabId)
{
   	//if user click on Use Approval tab (tabId == 2) and this facility required HMRB
    //and it has not data then don't select Use Approval tab
   	var hmrbTabHasData = false;
   	if( typeof( hmrbBeanGrid ) != 'undefined' ) {
        hmrbTabHasData = requestHasAtLeastOneApprovalCode();
    }
    if($v("hasHmrbTab") == 'Y' && tabId == 2 && !hmrbTabHasData) {
		return;
    }
    //done with restricting Use Approval tab on HMRB tab

     //toggle page only if the page passed is not the selected tab
 	if (selectedTabId != tabId) {
        for (var i=0; i<tabDataJson.length; i++) {
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
			 }else {
				setVisible(tabDataJson[i].tabId, false);
				tabLink.className = "tabLeftSide";
				/*tabLink.onmouseover = "className='selectedTab'";
				tabLink.onmouseout = "className='tabLeftSide'";*/

				tabLinkSpan.className = "tabRightSide";
				/*tabLinkSpan.onmouseover = "className='selectedSpanTab'";
				tabLinkSpan.onmouseout = "className='tabRightSide'";*/
			 }
  		}
	}else {
		var tabLink =  document.getElementById(selectedTabId+"Link");
		tabLink.style["display"] = "";
		var tabLinkSpan =  document.getElementById(selectedTabId+"LinkSpan");
		tabLinkSpan.style["display"] = "";

		setVisible(tabId, true);
  	}
	/*
 	if (selectedTabId == 0) {
		toggleContextMenu('newCatalogItemContextMenuWithoutDelete');
  	}else {
	 	toggleContextMenu('newCatalogItemContextMenu');
  	}
	*/
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

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}

	$("closeTransitWinflag").value = "N";
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
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

var callingShowMessageDialogFrom = "";
function showMessageDialog(winTitle,messageText,allowEdit,callingFrom)
{
	$("messageText").innerHTML = messageText.replace('<BR>',' ');
	if (allowEdit) {
		$("messageText").readOnly = "";
	}else {
		$("messageText").readOnly = "true";
	}
	callingShowMessageDialogFrom = callingFrom;

	var inputDailogWin = document.getElementById("messageDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showMessageDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showMessageDialog",0,0, 450, 150);
		inputWin.setText(winTitle);
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
		inputWin.setText(winTitle);
		inputWin.setModal(true);
		dhxWins.window("showMessageDialog").show();
	}
}

function closeMessageWin() {
  	dhxWins.window("showMessageDialog").setModal(false);
	dhxWins.window("showMessageDialog").hide();

  	if (callingShowMessageDialogFrom == 'rejectQplLine') {
	  rejectQplLineData();
  	}else {
  		closeTransitWin();
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

function lookupReplacementPart() {
 var currentPart = ($v("replacesPartNo")).trim();
 var loc = "replacementpartsearchmain.do?uAction=search&searchArgument="+currentPart+"&companyId="+$v("companyId")+
			  "&catalogCompanyId="+$v("catalogCompanyId")+"&catalogId="+$v("catalogId")+"&facilityId="+encodeURIComponent($v("facilityId"));
 children[children.length] = openWinGeneric(loc,"replacementpartsearch","650","500","yes","50","50","20","20","no");
}

function replacementPartChanged(newPart,newPartGroupNo) {
	document.getElementById("replacesPartNo").value = newPart;
}

//QPL section
var qplSelectedRowId = null;
var qplPreClass = null;
var qplPreRowId = null;
function initializeQplGrid(){
	
	if(typeof (qplBeanGrid) != 'undefined')
		qplBeanGrid.destructor();
	qplBeanGrid = new dhtmlXGridObject('qplDataDiv');

    //initialize grid
	initGridWithConfig(qplBeanGrid,qplConfig,true,true);
	//setting smart rendering
	qplBeanGrid.enableSmartRendering(true);
	qplBeanGrid._haas_row_span = true;
	qplBeanGrid._haas_row_span_map = lineMap;
	qplBeanGrid._haas_row_span_class_map = lineMap3;
    if (showMixRatioUnitAsVolumeWeight)
        qplBeanGrid._haas_row_span_cols = [0,2,3,4,5,6,7,8,9,10,12,14,21,22,23,24];
    else
        qplBeanGrid._haas_row_span_cols = [0,2,3,4,5,6,7,8,9,10,14,21,22,23,24];
    qplBeanGrid._haas_row_span_child_select = true;

    //parse data
	if( typeof( qplJsonMainData ) != 'undefined' ) {
   	qplBeanGrid.parse(qplJsonMainData,"json");
 	}
	qplBeanGrid.attachEvent("onRowSelect",qplSelectRow);
	qplBeanGrid.attachEvent("onRightClick",qplSelectRow);
	qplBeanGrid.attachEvent("onAfterHaasRenderRow", bindACkeys);
}

function bindACkeys(idx) {
	var replacesMsds = "replacesMsds";
	var replacesMsdsValidator = "replacesMsdsValidator";
	// check the flag set in qplBind so that the function is not called every time the row renders
	if (j$("#" + replacesMsds + idx).data("boundToKeystroke") !== true) {
		qplBind(idx, replacesMsds, replacesMsdsValidator);
	}
}

function qplBind(rowId, el1, el2) {
	j$().ready(function() {
		function log(event, data, formatted) {
			j$("#" + el2 + rowId).val(formatted.split(":")[0]);
			$(el1 + rowId).className = "inputBox";
		} 
		
		j$("#"+el1+rowId).result(log).next().click(function() {
			j$(this).prev().search();
		});
	
	j$("#" + el1 + rowId).autocomplete("catalogaddrequest.do?uAction=msdsRequest",{
		extraParams: {
			requestId: function() { return j$("#requestId").val(); }
		},
		width: 500,
		max: 20,  // default value is 10
		cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
		scrollHeight: 150,
		formatItem: function(data, i, n, value) {
			return  value;
		},
		formatResult: function(data, value) {
			return value;
		},
		parse: function(data) {
			var results = jQuery.parseJSON(data);
			var parsed = new Array();
			for (var row in results.CustomerMSDSNumberResults){
				parsed[parsed.length] = {
						data: results.CustomerMSDSNumberResults[row].customerMsdsNumber,
						value: results.CustomerMSDSNumberResults[row].customerMsdsNumber,
						result: results.CustomerMSDSNumberResults[row].customerMsdsNumber
				};
			}
			return parsed;
		} 
	});
	
	j$("#" + el1 + rowId).bind("keyup",(function(e) {
		var keyCode = (e.keyCode ? e.keyCode : e.which);
		
		if (keyCode != 13 /* Enter */ && keyCode != 9 /* Tab */) {
			invalidateQplRequestor(rowId, el1, el2);
		}
	}));
	// set a flag so that this function does not get called every time the row renders
	j$("#" + el1 + rowId).data("boundToKeystroke", true);
	
	j$("#" + el1 + rowId).result(log).next().click(function() {
		j$(this).prev().search();
	});
		
	});
}

function invalidateQplRequestor(rowId, el1, el2) {
	var requestorName = document.getElementById(el1 + rowId);
	if (requestorName.value.length == 0) {
		requestorName.className = "inputBox";
	}
	else {
		requestorName.className = "inputBox red";
	}
	if (el2 != null) {
		qplBeanGrid.cellById(rowId, qplBeanGrid.getColIndexById(el2)).setValue("");
	}
}

function qplSelectRow() {
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

	if( ee != null ) {
		if( (ee.button != null && ee.button == 2) || ee.which == 3) {
			rightClick = true;
		}
   }

	qplPreRowId = rowId0;
	qplSelectedRowId = rowId0;
	var dataSource = qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("dataSource")).getValue();
	if (dataSource == 'new') {
		var startingView = qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("startingView")).getValue();
		if ((startingView != '3' && startingView != '5') && (viewLevel == 'requestor' || viewLevel == 'approver')) {
			if($("qplActionSpan").innerHTML.length > 0) {
				$("qplActionSpan").innerHTML = qplActionText+
					' | <a href="#" onclick="uploadMsds(); return false;">'+messagesData.uploadAllMsdsForItem+'</a>';
					if(qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("isKit")).getValue() != 'N')
					{
						requestStatus = $v("requestStatus");
						if(requestStatus == 6 || $v('viewLevel') == 'requestor')
							$("qplActionSpan").innerHTML += ' | <a href="#" onclick="uploadMsds(true); return false;">'+messagesData.uploadKitMsdsForItem+'</a>';
						if(requestStatus == 5 || requestStatus== 8 || requestStatus == 11)
							$("qplActionSpan").innerHTML += ' | <a href="#" onclick="viewKitSummaryDraft(); return false;">'+messagesData.viewKitSummary+'</a>';										 
					}
					$("qplActionSpan").innerHTML += '<br>&nbsp;';
			}
		}
	}

	//stop here if it's not a right mouse click
	if( !rightClick ) return;
/*
	callToServer("catalogaddrequest.do?uAction=findFiles&requestId="+$v("requestId"));
}

function drawMenu( ) {
*/
	var menuItems = new Array();
	var msdsOnLine = qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("msdsOnLine")).getValue();
	if (colId0 > qplBeanGrid.getColIndexById("hetUsageRecording")) {
		//requestor and approvers is allow to do the following for lines on this request
		//starting_view = 0 - New Material
		//starting_view = 1 - New Size/Packaging
		//starting_view = 2 - New Part
		//starting_view = 3 - New Work Area Approval
		//starting_view = 4 - Modify QPL
		//starting_view = 5 - Fadeout from QPL
		if (dataSource == 'new') {
			var startingView = qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("startingView")).getValue();
			if ((startingView != '3' && startingView != '5') && (viewLevel == 'requestor' || viewLevel == 'approver')) {
                menuItems[menuItems.length] = 'text='+messagesData.addSuggestedSupplier+';url=javascript:addSuggestedSupplier();';
				if ((startingView == '0' || startingView == '1') && approvalRoleBeforeTcmQc == 'BEFORE') {
					menuItems[menuItems.length] = 'text='+messagesData.editItemInfo+';url=javascript:editItemData();';
					if(qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("itemId")).getValue() == ''
						&& qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("customerMixtureNumber")).getValue() == '')
						menuItems[menuItems.length] = 'text=Add Material;url=javascript:addMaterialToExisting();';
				}
                if (viewLevel == 'requestor') {
					isKit = qplBeanGrid.haasGetRowSpanEndIndex(qplSelectedRowId) - qplBeanGrid.haasGetRowSpanStart(qplSelectedRowId);
                	if(qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("itemId")).getValue() == '' && isKit != 1 
                	   && qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("customerMixtureNumber")).getValue() == '')
                		menuItems[menuItems.length] = 'text='+messagesData.deleteComponent+';url=javascript:deleteComponentData();';
                	else if(isKit != 1  && colId0 < qplBeanGrid.getColIndexById("mixRatioAmount"))
                		menuItems[menuItems.length] = 'text='+messagesData.deleteLine+';url=javascript:deleteLineData();';
                	else if(isKit == 1)
                		menuItems[menuItems.length] = 'text='+messagesData.deleteLine+';url=javascript:deleteLineData();';
				}
                menuItems[menuItems.length] = 'text='+messagesData.uploadAllMsdsForItem+';url=javascript:uploadMsds();';
			}
            if (viewLevel == 'requestor' || viewLevel == 'approver') {
                menuItems[menuItems.length] = 'text='+messagesData.uploadApprovedLetter+';url=javascript:uploadApprovedLetter();';
            }
        }
        //show letter is something is in content field
        tmpApprovalLetter = qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("approvalLetterContent")).getValue();
        if (tmpApprovalLetter != null && tmpApprovalLetter.length > 0) {
            menuItems[menuItems.length] = 'text='+messagesData.viewApprovedLetter+';url=javascript:viewApprovedLetter();';
        }
		//always be able to view MSDS if it's online
		if (msdsOnLine == 'Y') {
			menuItems[menuItems.length] = 'text='+messagesData.viewComponentMsds+';url=javascript:viewMsds();';
		}

        //by this point if menu is empty then don't show anything
		if (menuItems.length == 0) {
			toggleContextMenu('contextMenu');
		} else {
			replaceMenu('qplMsdsRightClickMenu',menuItems);
			toggleContextMenu('qplMsdsRightClickMenu');
		}
	}else {
		if (dataSource == 'catalog') {
			if (viewLevel == 'requestor' || viewLevel == 'approver') {
				menuItems[menuItems.length] = 'text='+messagesData.fadeOutFromQpl+';url=javascript:fadeOutFromQpl();';
			}
		}else if (dataSource == 'new') {
			var startingView = qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("startingView")).getValue();
            if (startingView == '5') {
				if (viewLevel == 'requestor') {
					menuItems[menuItems.length] = 'text='+messagesData.removeFadeoutRequest+';url=javascript:deleteLineData();';
				}else if (viewLevel == 'approver') {
					menuItems[menuItems.length] = 'text='+messagesData.rejectLine+';url=javascript:rejectQplLine();';
				}
			}else {
				if ((viewLevel == 'requestor' || viewLevel == 'approver') && startingView != '3') {
					
					var itId = qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("itemId")).getValue();
					if(itId == '')
						menuItems[menuItems.length] = 'text='+messagesData.addSuggestedSupplier+';url=javascript:addSuggestedSupplier();';
					
					if ((startingView == '0' || startingView == '1') && approvalRoleBeforeTcmQc == 'BEFORE') {
						menuItems[menuItems.length] = 'text='+messagesData.editItemInfo+';url=javascript:editItemData();';
						if( itId == ''
							&& qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("customerMixtureNumber")).getValue() == '')
							menuItems[menuItems.length] = 'text=Add Material;url=javascript:addMaterialToExisting();';
					}
					if (viewLevel == 'requestor') {
						isKit = qplBeanGrid.haasGetRowSpanEndIndex(qplSelectedRowId) - qplBeanGrid.haasGetRowSpanStart(qplSelectedRowId);
	                	if(qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("itemId")).getValue() == '' && isKit != 1 
	                		&& qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("customerMixtureNumber")).getValue() == ''
	                		&& colId0 > qplBeanGrid.getColIndexById("mixtureDesc") && colId0 < qplBeanGrid.getColIndexById("approvalCode"))
	                		menuItems[menuItems.length] = 'text='+messagesData.deleteComponent+';url=javascript:deleteComponentData();';
	                	else if(isKit != 1  && colId0 < qplBeanGrid.getColIndexById("mixRatioAmount"))
	                		menuItems[menuItems.length] = 'text='+messagesData.deleteLine+';url=javascript:deleteLineData();';
	                	else if(isKit == 1)
	                		menuItems[menuItems.length] = 'text='+messagesData.deleteLine+';url=javascript:deleteLineData();';
					}else if (viewLevel == 'approver') {
					    menuItems[menuItems.length] = 'text='+messagesData.rejectLine+';url=javascript:rejectQplLine();';
				    }
				}
			}
        	if(qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("isKit")).getValue() != 'N' && (requestStatus == 5 || requestStatus== 8 || requestStatus == 11))
        	    menuItems[menuItems.length] = 'text='+messagesData.viewKitSummary+';url=javascript:viewKitSummaryDraft();';
		}
		//by this point if menu is empty then don't show anything
		if (menuItems.length == 0) {
			toggleContextMenu('contextMenu');
		} else {
			replaceMenu('qplRightClickMenu',menuItems);
			toggleContextMenu('qplRightClickMenu');
		}
	}
} //end of method

// all same level, at least one item
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
}

function doNothing() {
}

function fadeOutFromQpl() {
	showTransitWin();
	tmpTimeTempSensitive = "Y";
	if ($("timeTempSensitiveNo").checked) {
		tmpTimeTempSensitive = "N";
	}
	tmpRoomTempOutTime = "N";
	if ($("roomTempOutTimeYes").checked) {
		tmpRoomTempOutTime = "Y";
	}
	callToServer("catalogaddrequest.do?uAction=fadeOutFromQpl&requestId="+$v("requestId")+"&itemId="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("itemId")).getValue()+
		 	     "&timeTempSensitive="+tmpTimeTempSensitive+"&roomTempOutTime="+tmpRoomTempOutTime+"&hasHmrbTab="+$v("hasHmrbTab")+"&hasCatalogAddStorageTab="+$v("hasCatalogAddStorageTab")+
                 "&allowMixture="+$v("allowMixture")+"&showReplacesMsds="+$v("showReplacesMsds")+"&allowEditMixtureData="+$v("allowEditMixtureData")+"&viewLevel="+$v("viewLevel"));
}
refreshQplDataCloseTransWin = "Y";
function refreshQplData() {
	showTransitWin();
	tmpTimeTempSensitive = "Y";
	if ($("timeTempSensitiveNo").checked) {
		tmpTimeTempSensitive = "N";
	}
	tmpRoomTempOutTime = "N";
	if ($("roomTempOutTimeYes").checked) {
		tmpRoomTempOutTime = "Y";
	}
    callToServer("catalogaddrequest.do?uAction=reloadQplData&requestId="+$v("requestId")+"&timeTempSensitive="+tmpTimeTempSensitive+"&roomTempOutTime="+tmpRoomTempOutTime+
                 "&hasHmrbTab="+$v("hasHmrbTab")+"&hasCatalogAddStorageTab="+$v("hasCatalogAddStorageTab")+"&allowMixture="+$v("allowMixture")+"&showReplacesMsds="+
                 $v("showReplacesMsds")+"&refreshQplDataCloseTransWin="+refreshQplDataCloseTransWin+"&allowEditMixtureData="+$v("allowEditMixtureData")+"&viewLevel="+$v("viewLevel"));
}

function deleteLineData() {
	showTransitWin();
	tmpTimeTempSensitive = "Y";
	if ($("timeTempSensitiveNo").checked) {
		tmpTimeTempSensitive = "N";
	}
	tmpRoomTempOutTime = "N";
	if ($("roomTempOutTimeYes").checked) {
		tmpRoomTempOutTime = "Y";
	}
	callToServer("catalogaddrequest.do?uAction=deleteLine&requestId="+$v("requestId")+"&timeTempSensitive="+tmpTimeTempSensitive+"&roomTempOutTime="+tmpRoomTempOutTime+
		     "&lineItem="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("lineItem")).getValue()+"&hasHmrbTab="+$v("hasHmrbTab")+
            "&hasCatalogAddStorageTab="+$v("hasCatalogAddStorageTab")+"&allowMixture="+$v("allowMixture")+"&showReplacesMsds="+$v("showReplacesMsds")+"&allowEditMixtureData="+$v("allowEditMixtureData")+"&viewLevel="+$v("viewLevel"));
}

function deleteComponentData() {
	showTransitWin();
	tmpTimeTempSensitive = "Y";
	if ($("timeTempSensitiveNo").checked) {
		tmpTimeTempSensitive = "N";
	}
	tmpRoomTempOutTime = "N";
	if ($("roomTempOutTimeYes").checked) {
		tmpRoomTempOutTime = "Y";
	}
	callToServer("catalogaddrequest.do?uAction=deleteLine&requestId="+$v("requestId")+"&timeTempSensitive="+tmpTimeTempSensitive+"&roomTempOutTime="+tmpRoomTempOutTime+
		     "&lineItem="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("lineItem")).getValue()+"&hasHmrbTab="+$v("hasHmrbTab")+
            "&hasCatalogAddStorageTab="+$v("hasCatalogAddStorageTab")+"&allowMixture="+$v("allowMixture")+"&showReplacesMsds="+$v("showReplacesMsds")+
            "&partId="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("partId")).getValue()+"&allowEditMixtureData="+$v("allowEditMixtureData")+"&viewLevel="+$v("viewLevel"));
}

function rejectQplLine() {
	showMessageDialog(messagesData.comments,"",true,"rejectQplLine");
}

function rejectQplLineData() {
	showTransitWin();
	tmpTimeTempSensitive = "Y";
	if ($("timeTempSensitiveNo").checked) {
		tmpTimeTempSensitive = "N";
	}
	tmpRoomTempOutTime = "N";
	if ($("roomTempOutTimeYes").checked) {
		tmpRoomTempOutTime = "Y";
	}
	callToServer("catalogaddrequest.do?uAction=rejectLine&requestId="+$v("requestId")+"&timeTempSensitive="+tmpTimeTempSensitive+"&roomTempOutTime="+tmpRoomTempOutTime+
				 "&lineItem="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("lineItem")).getValue()+"&comments="+$("messageText").value+
                 "&hasHmrbTab="+$v("hasHmrbTab")+"&hasCatalogAddStorageTab="+$v("hasCatalogAddStorageTab")+"&allowMixture="+$v("allowMixture")+"&showReplacesMsds="+$v("showReplacesMsds")+"&allowEditMixtureData="+$v("allowEditMixtureData")+"&viewLevel="+$v("viewLevel"));
}

function reloadQplData(tmpQplConfig,tmpQplJsonMainData,tmpLineMap,tmpLineIdMap,tmpLineMap3,closeTransitWinflag,tmpShowMixtureColumnAudit) {
	qplConfig = tmpQplConfig;
	qplJsonMainData = tmpQplJsonMainData;
	lineMap = tmpLineMap;
	lineIdMap = tmpLineIdMap;
	lineMap3 = tmpLineMap3;
	qplBeanGrid._haas_row_span_map = lineMap;
	qplBeanGrid._haas_row_span_class_map = lineMap3;
	qplSelectedRowId = null;
	qplPreClass = null;
 	qplPreRowId = null;
    showMixtureColumnAudit = tmpShowMixtureColumnAudit;

    initializeQplGrid();

	setQplActionViewLevel();

	if(refreshHmrbAlso)
	{
		$('closeTransitWinflag').value = closeTransitWinflag;
		refreshHmrbAlso = false;
		reloadHmrb(closeTransitWinflag);
	}
	else if("Y" == closeTransitWinflag)
		closeTransitWin();
}

function addSuggestedSupplier() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=addSuggestedSupplier&requestId='+$v("requestId")+"&lineItem="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("lineItem")).getValue();
	children[children.length] = openWinGeneric(url,"catalogAddSuggestedSupplier",500,250,'yes' );
}

function editItemData() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=editNewItem&calledFrom=catalogAddRequest&requestId='+$v("requestId")+"&lineItem="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("lineItem")).getValue()+
              '&hasHmrbTab='+$v("hasHmrbTab")+"&hasCatalogAddStorageTab="+$v("hasCatalogAddStorageTab")+'&showReplacesMsds='+$v("showReplacesMsds");
	children[children.length] = openWinGeneric(url,"catalogAddEditNewItem",900,620,'yes' );
}

function uploadMsds(isKit) {
	showTransitWin();
	var msdsModule = '';
	var fileName = $v("requestId");
	if(typeof(isKit) != 'undefined')
		msdsModule = 'catalogAddKitMsds';
	else
		msdsModule = 'catalogAddMsds';
	var url = 'uploadfile.do?fileName='+fileName+"&modulePath="+msdsModule+"&allowMultiple=true&requestId="+fileName+"&lineItem="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("lineItem")).getValue();
	children[children.length] = openWinGeneric(url,"uploadfile",500,200,'yes' );
}

function viewKitSummaryDraft()
{
	var reportLoc = null;
	var isApprovedCustMixtureNumber = qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("approvedCustMixtureNumber")).getValue();
	
	if(isApprovedCustMixtureNumber == '')
	{
  		reportLoc =	"/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+$v('companyId')+
                    "&pr_request_id="+ $v("requestId")+
					"&pr_line_item="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("lineItem")).getValue()+
					"&rpt_ReportBeanId=CatAddMSDSKitReportDefinition";
	}
	else
	{
		reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+$v('companyId')+
                    "&pr_custMsdsDb="+encodeURIComponent($v("customerMsdsDb"))+
					"&pr_custMsdsNo="+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("customerMixtureNumber")).getValue()+
					"&rpt_ReportBeanId=MSDSKitReportDefinition";
	}
	try {
		opener.children[opener.children.length] = openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
	} catch(ex) {
		openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
	}
}

function viewMsds() {
	if(newMsdsViewer)
		children[children.length] = openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+ qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("materialId")).getValue() +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId')+
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
	else
		children[children.length] = openWinGeneric('ViewMsds?act=msds'+
			'&id='+ qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("materialId")).getValue() +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId')+
			'&facility=' + encodeURIComponent($v('facilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
}

function addItem() {
	prepareForTransitWin();
	var loc = "searchglobalitem.do?uAction=search&calledFrom=newCatalogAddProcess&searchText=&requestId="+$v("requestId")+
			  "&companyId="+$v("companyId")+"&facilityId="+encodeURIComponent($v("facilityId"));
	var winId= 'searchGlobalCatalog'+$v("requestId");
	children[children.length] = openWinGeneric(loc,winId,"900","590","yes","50","50","20","20","no");
}

function addMaterialToExisting()
{
	showTransitWin();
	var loc = "searchglobalitem.do?uAction=search&calledFrom=newCatalogAddProcess&searchText=&requestId="+$v("requestId")+
			  "&companyId="+$v("companyId")+"&facilityId="+encodeURIComponent($v("facilityId")) + "&lineItem=" + qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("lineItem")).getValue();
	var winId= 'searchGlobalCatalog'+$v("requestId");
	children[children.length] = openWinGeneric(loc,winId,"900","590","yes","50","50","20","20","no");
}

function newItem() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=newMaterial&calledFrom=catalogAddRequest&requestId='+$v("requestId");
	children[children.length] = openWinGeneric(url,"catalogAddEditNewItem",900,620,'yes' );
}

function showObsolete() {
	showTransitWin();
	tmpTimeTempSensitive = "Y";
	if ($("timeTempSensitiveNo").checked) {
		tmpTimeTempSensitive = "N";
	}
	tmpRoomTempOutTime = "N";
	if ($("roomTempOutTimeYes").checked) {
		tmpRoomTempOutTime = "Y";
	}
	callToServer("catalogaddrequest.do?uAction=showObsolete&requestId="+$v("requestId")+"&timeTempSensitive="+tmpTimeTempSensitive+"&roomTempOutTime="+tmpRoomTempOutTime+
                 "&hasHmrbTab="+$v("hasHmrbTab")+"&hasCatalogAddStorageTab="+$v("hasCatalogAddStorageTab")+"&allowMixture="+$v("allowMixture")+"&showReplacesMsds="+$v("showReplacesMsds")+"&allowEditMixtureData="+$v("allowEditMixtureData")+"&viewLevel="+$v("viewLevel"));
}


function partialQplAudit()
{
	var missingFields = '';
	var errShelfLifeDaysAdded = false;
	var errRoomTempOutTimeAdded = false;
	var amtMxRatioErrorMsgAdd = false;
	
	for(var i = 1; i <= qplBeanGrid.getRowsNum();i++) {
	    //shelf life and storage temp
		if (qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("shelfLifeDaysPermission")).getValue() == 'Y') {
			if ($("timeTempSensitiveYes").checked) {
				
					var tmpVal = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("shelfLifeDays")).getValue();
					if (tmpVal != null && tmpVal.length > 0) {
						if (!errShelfLifeDaysAdded && isNaN(tmpVal)) {
							missingFields += "\t"+messagesData.shelfLife+"\n";
							errShelfLifeDaysAdded = true;
						}
					}
				
		    }
			else if($("timeTempSensitiveNo").checked)
				qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("shelfLifeDays")).setValue("");
		}
		
		//room temp out time
		if ($("roomTempOutTimeYes").checked) {
			tmpVal = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("roomTempOutTime")).getValue();
			if (tmpVal != null && tmpVal.length > 0) {
				if (!errRoomTempOutTimeAdded && isNaN(tmpVal)) {
					missingFields += "\t"+messagesData.roomTempOutTime+"\n";
					errRoomTempOutTimeAdded = true;
				}
			}
		}
		else if($("roomTempOutTimeNo").checked)
			qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("roomTempOutTime")).setValue("");
		
		mixRatioAmount = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("mixRatioAmount")).getValue();
		if(!amtMxRatioErrorMsgAdd && mixRatioAmount != '' && !isFloat(mixRatioAmount,false))
			{
				missingFields += "\t"+messagesData.mixRatioAmount+"\n";
				amtMxRatioErrorMsgAdd = true;
			}
	}
	
	if(missingFields.length > 0)
		{
			alert(messagesData.validvalues+"\n"+missingFields);
			closeTransitWin();
			return false;		
		}
	
	return true;
	
}

function auditReplaceMsds() {
	var validMsds = true;
	for(var i = 1; i <= qplBeanGrid.getRowsNum();i++) {
		var replaceMsds = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("replacesMsds")).getValue();
		var replaceMsdsValidator = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("replacesMsdsValidator")).getValue();
		
		validMsds = (replaceMsds == replaceMsdsValidator);
		if (!validMsds) {
			break;
		}
	}
	
	if (validMsds) {
		return true;
	}
	return false;
}

function saveQplData(partialAudit) {
    //prepare grid for data sending
	partialAuditResults = true;
	if(partialAudit) {
		partialAuditResults = partialQplAudit();
		if (!auditReplaceMsds()) {
			alert(messagesData.invalidReplaceMsds);
			closeTransitWin();
			partialAuditResults = false;
		}
	}
	
	if(!partialAuditResults)
		return false;
				
    qplBeanGrid.parentFormOnSubmit();
    $("uAction").value = 'saveQplData';
    var tmpTarget = document.genericForm.target;
    document.genericForm.target = 'catalogAddRequestQplFrame';
    document.genericForm.submit();
    document.genericForm.target = tmpTarget;
    return true;
}


var dataArray = new Array();
var calledFrom = "";
var itemAlreadyInQpl = "";
var itemIdInQpl = "";
searchGlobalItemMsdsAddAndContinue = false;
refreshHmrbAlso = false;
function addNewItemToQpl() {
    //reload HMRB tab
    if ($v("hasHmrbTab") == 'Y')
    	refreshHmrbAlso = true;

	if (calledFrom == 'catalogAddRequest') {
		//updateSelectedRow();
		//closeTransitWin();
        refreshQplData();
    }else {
		if (itemAlreadyInQpl == 'Y') {
			showMessageDialog(messagesData.information,messagesData.itemAlreadyInQpl.replace(/[{]0[}]/g,itemIdInQpl),false,"addNewItemToQpl");
		}else {
			if(calledFrom == 'searchGlobalItemMsds' && searchGlobalItemMsdsAddAndContinue)
				refreshQplDataCloseTransWin = "N";
			else
				refreshQplDataCloseTransWin = "Y";
			refreshQplData();
		}
	}


} //end of method

function updateSelectedRow() {
	for (var i = 1; i <= qplBeanGrid.getRowsNum(); i++) {
		var tmpLineItem = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("lineItem")).getValue();
		var tmpPartId = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("partId")).getValue();
		var tmpDataSource = qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("dataSource")).getValue();
		if (tmpLineItem == null || tmpLineItem.length == 0) {
			continue;
		}else {
			for (var j = 0; j < dataArray.length;j++) {
				if (tmpLineItem == dataArray[j].lineItem && tmpPartId == dataArray[j].partId && tmpDataSource == 'new') {
					qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("materialDesc")).setValue(dataArray[j].materialDesc);
					qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("packaging")).setValue(dataArray[j].packaging);
					qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("mfgDesc")).setValue(dataArray[j].mfgDesc);
					qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("customerMsdsNumber")).setValue(dataArray[j].customerMsdsNumber);
				}
			}
		}

	}
} //end of method

function timeTempClickedYes () {
	timeTempSensitiveNo = $("timeTempSensitiveNo");
    if (timeTempSensitiveNo.checked) {
    	timeTempSensitiveNo.checked = false;
    	timeTempSensitiveYes = $("timeTempSensitiveYes");
    	timeTempSensitiveYes.checked = true;
        $("timeTempSensitive").value = "Y";
        prepareForTransitWin()
        partialQplAudtiResults = saveQplData(true);
        if(!partialQplAudtiResults)
        	{
	        	timeTempSensitiveNo.checked = true;
	        	timeTempSensitiveYes.checked = false;
        	}
        
    }
}

function timeTempClickedNo () {
	timeTempSensitiveYes = $("timeTempSensitiveYes");
    if (timeTempSensitiveYes.checked) {
        timeTempSensitiveYes.checked = false;
        timeTempSensitiveNo = $("timeTempSensitiveNo");
        timeTempSensitiveNo.checked = true;
        $("timeTempSensitive").value = "N";
        prepareForTransitWin();
        partialQplAudtiResults = saveQplData(true);
        if(!partialQplAudtiResults)
        	{
	        	timeTempSensitiveNo.checked = false;
	        	timeTempSensitiveYes.checked = true;
        	}
    }
}

function outTimeClickedYes() {
	roomTempOutTimeNo = $("roomTempOutTimeNo");
    if (roomTempOutTimeNo.checked) {
    	roomTempOutTimeNo.checked = false;
    	roomTempOutTimeYes = $("roomTempOutTimeYes");
    	roomTempOutTimeYes.checked = true;
    	prepareForTransitWin();
        partialQplAudtiResults = saveQplData(true);
        if(!partialQplAudtiResults)
        	{
	        	roomTempOutTimeNo.checked = true;
	        	roomTempOutTimeYes.checked = false;
        	}
    }
}

function outTimeClickedNo() {
	roomTempOutTimeYes = $("roomTempOutTimeYes");
    if (roomTempOutTimeYes.checked) {
    	roomTempOutTimeYes.checked = false;
    	roomTempOutTimeNo = $("roomTempOutTimeNo");
    	roomTempOutTimeNo.checked = true;
        prepareForTransitWin();
        partialQplAudtiResults = saveQplData(true);
        if(!partialQplAudtiResults)
    	{
        	roomTempOutTimeNo.checked = false;
        	roomTempOutTimeYes.checked = true;
    	}
    }
}

function showRoomTempOutTimeInfo() {
	if ($v("allowRoomTempOutTime") == 'Y') {
		$("roomTempOutTimeSpan").style["display"] = "";
	}else {
		$("roomTempOutTimeSpan").style["display"] = "none";
	}
}

//use approval section
var useApprovalSelectedRowId = null;
function initializeUseApprovalGrid(){
	if(typeof (useApprovalBeanGrid) != 'undefined')
		useApprovalBeanGrid.destructor();
 	useApprovalBeanGrid = new dhtmlXGridObject('useApprovalDataDiv');

	//this is needed because qpl grid is a row span and it the alternate color
	//and this grid need to exist at the same time as qpl grid
	useApprovalBeanGrid.setEvenoddmap(null);
	//by setting this to null this grid will use it own color scheme.

	//-1 to turn off smart rendering but allows sorting
	initGridWithConfig(useApprovalBeanGrid,useApprovalConfig,-1,true);
 	if( typeof( useApprovalJsonMainData ) != 'undefined' ) {
   	useApprovalBeanGrid.parse(useApprovalJsonMainData,"json");
 	}

	/*This is to override color for rows*/
	//setTimeout('overrideUseApprovalRowColor()',100);

	useApprovalBeanGrid.attachEvent("onRowSelect",useApprovalSelectRow);
	useApprovalBeanGrid.attachEvent("onRightClick",useApprovalSelectRow);
}

function overrideUseApprovalRowColor() {
	//setting different colors for each data source
	for(var i=0;i<useApprovalBeanGrid.getRowsNum();i++) {
		var color = '';
		try {
			var dataSource = cellValue(i+1,'dataSource');
			if (dataSource == 'catalog') {
				color = 'green';
			}else if (dataSource == 'otherRequest') {
				color = 'yellow';
			}else {
				color = '';
			}
			if (color.length > 0) {
				for (var j = 0; j < useApprovalBeanGrid.getColumnCount(); j++) {
					useApprovalBeanGrid.rowsAr[i+1].cells[j].style.background = color;
				}
			}
		}catch(ex) {
			alert("error row:"+i+"-"+color+"="+color.length+"*");
		}
	}
} //end of method

function useApprovalSelectRow() {
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
   if( ee != null ) {
   	if( ee.button != null && ee.button == 2 ) rightClick = true;
   	else if( ee.which == 3  ) rightClick = true;
   }
	useApprovalSelectedRowId = rowId0;

	//stop here if it's not a right mouse click
	if( !rightClick ) return;

	var menuItems = new Array();
	if (useApprovalBeanGrid.cells(useApprovalSelectedRowId,useApprovalBeanGrid.getColIndexById("dataSource")).getValue() == 'new') {
		if ((viewLevel == 'requestor' || viewLevel == 'approver') &&
             useApprovalBeanGrid.cells(useApprovalSelectedRowId,useApprovalBeanGrid.getColIndexById("permission")).getValue() == 'Y') {
			menuItems[menuItems.length] = 'text='+messagesData.edit+';url=javascript:editUseApprovalData();';
			menuItems[menuItems.length] = 'text='+messagesData.deleteLine+';url=javascript:removeSelectedLine();';
		}
	}
	//by this point if menu is empty then don't show anything
	if (menuItems.length == 0) {
		toggleContextMenu('contextMenu');
	} else {
		replaceMenu('useApprovalRightClickMenu',menuItems);
		toggleContextMenu('useApprovalRightClickMenu');
	}
} //end of method

function addWorkArea() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=addUseApproval'
			+ '&requestId=' + $v("requestId")
			+ '&calledFrom=newWorkArea'
			+ '&engEvalWorkArea='
			+ '&facilityName=' + $v("facilityName")
			+ '&engEvalFacilityId=' + $v("facilityId")
			+ '&catalogCompanyId=' + $v("catalogCompanyId")
			+ '&catalogId=' + $v("catalogId")
			+ '&catPartNo=' + encodeURIComponent($v("catPartNo"))
			+ '&partGroupNo=' + $v("partGroupNo")
			+ '&companyId=' + $v("companyId")
			+ '&allowAllApps=' + altCatalogFacility[0].catAddAllowAllForApps
			+ '&hasApplicationUseGroup=' + $v("hasApplicationUseGroup");
    children[children.length] = openWinGeneric(url,"catalogAddUseApproval",610,400,'yes' );
}

function reloadUseApprovalData(tmpUseApprovalJsonMainData, closeTransitWinflag) {
	useApprovalJsonMainData = tmpUseApprovalJsonMainData;
	useApprovalSelectedRowId = null;
	
	initializeUseApprovalGrid();

	if("Y" == closeTransitWinflag)
		closeTransitWin();

}

function editUseApprovalData() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=addUseApproval'
		+ '&requestId=' + $v("requestId")
		+ '&calledFrom=editWorkArea'
		+ '&engEvalWorkArea=' + useApprovalBeanGrid.cells(useApprovalSelectedRowId,useApprovalBeanGrid.getColIndexById("workArea")).getValue()
		+ '&facilityName=' + $v("facilityName")
		+ '&engEvalFacilityId=' + $v("facilityId")
		+ '&companyId=' + $v("companyId")
		+ '&allowAllApps=' + altCatalogFacility[0].catAddAllowAllForApps
		+ '&hasApplicationUseGroup=' + $v("hasApplicationUseGroup");	
	if (showEmissionPoints) {
		url += '&emissionPoints=' + gridCellValue(useApprovalBeanGrid, useApprovalSelectedRowId, 'emissionPoints')
		+ '&emissionPointIdSeparator=' + gridCellValue(useApprovalBeanGrid, useApprovalSelectedRowId, 'emissionPointIdSeparator');
	}
	if (showWasteWaterDischarge)
		url += '&wasteWaterDischarge=' + gridCellValue(useApprovalBeanGrid, useApprovalSelectedRowId, 'wasteWaterDischarge');
	
	children[children.length] = openWinGeneric(url,"catalogAddUseApproval",610,400,'yes' );
}

function removeSelectedLine() {
	showTransitWin();
	var app = useApprovalBeanGrid.cells(useApprovalSelectedRowId,useApprovalBeanGrid.getColIndexById("workArea")).getValue();
	if($v('hasCatalogAddStorageTab') == 'Y' && app != "All")
		{
			var existing = existingStorageCheck(app);
			if (existing != null)
				{
					storageBeanGrid.deleteRow(existing);
					callToServer("catalogaddrequest.do?uAction=deleteUseApprovalLine&requestId="+$v("requestId")+
							'&storageAction=delete&application=' + app +
							'&engEvalWorkArea='+useApprovalBeanGrid.cells(useApprovalSelectedRowId,useApprovalBeanGrid.getColIndexById("workArea")).getValue());

				}
			else
				{
					callToServer("catalogaddrequest.do?uAction=deleteUseApprovalLine&requestId="+$v("requestId")+
							 '&engEvalWorkArea='+useApprovalBeanGrid.cells(useApprovalSelectedRowId,useApprovalBeanGrid.getColIndexById("workArea")).getValue());
				}
		}
	else
		{
			callToServer("catalogaddrequest.do?uAction=deleteUseApprovalLine&requestId="+$v("requestId")+
					 '&engEvalWorkArea='+useApprovalBeanGrid.cells(useApprovalSelectedRowId,useApprovalBeanGrid.getColIndexById("workArea")).getValue());
		}

	/*callToServer("catalogaddrequest.do?uAction=deleteUseApprovalLine&requestId="+$v("requestId")+
					 '&engEvalWorkArea='+useApprovalBeanGrid.cells(useApprovalSelectedRowId,useApprovalBeanGrid.getColIndexById("workArea")).getValue());*/
}

function reloadUseApproval() {
	showTransitWin();
	callToServer("catalogaddrequest.do?uAction=reloadUseApproval&requestId="+$v("requestId")+'&engEvalFacilityId='+$v("facilityId")+'&companyId='+$v("companyId")+
                 "&viewLevel="+$v("viewLevel")+"&allowEditUseApproval="+$v("allowEditUseApproval"));
}

function saveUseApprovalData() {

	//prepare grid for data sending
	useApprovalBeanGrid.parentFormOnSubmit();
	
	$("uAction").value = 'saveUseApprovalData';
	var tmpTarget = document.genericForm.target;
	document.genericForm.target = 'catalogAddRequestUseApprovalFrame';
	document.genericForm.submit();
	document.genericForm.target = tmpTarget;
}

//spec
var specSelectedRowId = null;
function initializeSpecGrid(){
 	specBeanGrid = new dhtmlXGridObject('specDataDiv');

	//this is needed because qpl grid is a row span and it the alternate color
	//and this grid need to exist at the same time as qpl grid
	specBeanGrid.setEvenoddmap(null);
	//by setting this to null this grid will use it own color scheme.

	//-1 to turn off smart rendering but allows sorting
	initGridWithConfig(specBeanGrid,specConfig,-1,true);
 	if( typeof( specJsonMainData ) != 'undefined' ) {
   	specBeanGrid.parse(specJsonMainData,"json");
 	}

	specBeanGrid.attachEvent("onRowSelect",specSelectRow);
	specBeanGrid.attachEvent("onRightClick",specSelectRow);
}

function specSelectRow() {
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
   if( ee != null ) {
   	if( ee.button != null && ee.button == 2 ) rightClick = true;
   	else if( ee.which == 3  ) rightClick = true;
   }
	specSelectedRowId = rowId0;

	//stop here if it's not a right mouse click
	if( !rightClick ) return;

	var menuItems = new Array();
	tmpDataSource = specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("dataSource")).getValue();
	tmpOnline = specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("onLine")).getValue();
	if (tmpDataSource == 'new') {
		if (viewLevel == 'requestor' || (viewLevel == 'approver' && $v("allowEditSpec") == 'Y')) {
			if (specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("specId")).getValue() != 'No Specification') {
				menuItems[menuItems.length] = 'text='+messagesData.edit+';url=javascript:editSpecData();';
			}
			menuItems[menuItems.length] = 'text='+messagesData.deleteLine+';url=javascript:removeSpecSelectedLine();';
			tmpSpecId = specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("specId")).getValue();
			if (tmpSpecId != 'No Specification' && tmpSpecId != 'Std Mfg Cert') {
                //for now Approval Role ability to edit spec superceded feature release disabledSpecUpload
                if ($v('viewLevel') == 'approver' &&  $v("allowEditSpec") == 'Y') {
                    menuItems[menuItems.length] = 'text='+messagesData.uploadSpec+';url=javascript:uploadSpec();';
                }else if ($v("disabledSpecUpload") != 'true') {
                    menuItems[menuItems.length] = 'text='+messagesData.uploadSpec+';url=javascript:uploadSpec();';    
                }
            }
		}
	}
	//always allow users to view spec if it's online
	if (tmpOnline == 'Y') {
		menuItems[menuItems.length] = 'text='+messagesData.viewSpec+';url=javascript:viewSpec();';
	}
	//by this point if menu is empty then don't show anything
	if (menuItems.length == 0) {
		toggleContextMenu('contextMenu');
	} else {
		replaceMenu('specRightClickMenu',menuItems);
		toggleContextMenu('specRightClickMenu');
	}
} //end of method

function reloadSpecData(tmpSpecJsonMainData) {
	specJsonMainData = tmpSpecJsonMainData;
	specSelectedRowId = null;
	initializeSpecGrid();
	setSpecActionViewLevel();
	closeTransitWin();
}

function viewSpec() {
	var specStatus = specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("status")).getValue();
    var tmpUrl = '';
    if ($v("secureDocViewer") == 'true')
        tmpUrl = '/DocViewer/client/';

    if (specStatus == "Approved") {
		tmpUrl += 'docViewer.do?uAction=viewSpec'
				+ '&spec=' + encodeURIComponent(specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("specId")).getValue())
				+ '&hasSpecificFacility=Y'
				+ '&companyId=' + $v('companyId')
				+ '&facility=' + encodeURIComponent($v('facilityId')) 
				+ '&catalogId=' + encodeURIComponent($v('catalogId'))
				+ '&catpartno=' + encodeURIComponent($v('catPartNo'));
	}
	else {
		var reqId = $v("requestId");
		if (specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("dataSource")).getValue() == "otherRequest") {
			reqId = specStatus.substr(specStatus.search("[0-9]+"));
		} 
		tmpUrl += 'docViewer.do?uAction=viewCatalogAddSpec'
                 +'&spec=' + encodeURIComponent(specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("specId")).getValue())
                 +'&catalogAddRequestId='+reqId
                 +'&companyId='+$v("companyId");
	}
    openWinGeneric(tmpUrl,"ViewSpec","800","800",'yes' );
}

function specOnlineUpdate() {
    specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("onLine")).setValue("Y");
}

function uploadSpec() {
	showTransitWin();
	tmpSpecId = specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("specId")).getValue();
    //for file name remove any space
    var fileName = $v("requestId")+"-"+tmpSpecId.replace(/\s/g, "");

    var url = "uploadfile.do?fileName="+fileName+"&modulePath=catalogAddSpec&calledFrom=catalogAddSpec&companyId="+$v("companyId")+"&requestId="+$v("requestId")+"&specId="+tmpSpecId;
	children[children.length] = openWinGeneric(url,"uploadfile",500,200,'yes' );
}

function addSpec() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=addSpec&requestId='+$v("requestId")+'&calledFrom=newSpec&companyId='+$v("companyId");
	children[children.length] = openWinGeneric(url,"catalogAddSpec",425,235,'no' );
}

function addNoSpec() {
	showTransitWin();
	callToServer('catalogaddrequest.do?uAction=addNoSpec&requestId='+$v("requestId")+'&calledFrom=newSpec&companyId='+$v("companyId"));
}

function addStandardMfgCert() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=addStandardMfgCert&requestId='+$v("requestId")+'&calledFrom=newSpec&companyId='+$v("companyId");
	children[children.length] = openWinGeneric(url,"catalogAddSpec",425,235,'no' );
}

function editSpecData() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=addSpec&requestId='+$v("requestId")+'&calledFrom=editSpec'+'&companyId='+$v("companyId")+
		 		 '&specId='+specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("specId")).getValue();
	children[children.length] = openWinGeneric(url,"catalogAddSpec",425,235,'no' );
}

function removeSpecSelectedLine() {
	showTransitWin();
	callToServer("catalogaddrequest.do?uAction=deleteSpec&requestId="+$v("requestId")+'&companyId='+$v("companyId")+
					 '&specId='+specBeanGrid.cells(specSelectedRowId,specBeanGrid.getColIndexById("specId")).getValue());
}

function reloadSpec() {
	showTransitWin();
	callToServer("catalogaddrequest.do?uAction=reloadSpec&requestId="+$v("requestId"));
}

function saveSpecData() {
	//prepare grid for data sending
	specBeanGrid.parentFormOnSubmit();
	$("uAction").value = 'saveSpecData';
	var tmpTarget = document.genericForm.target;
	document.genericForm.target = 'catalogAddRequestSpecFrame';
	document.genericForm.submit();
	document.genericForm.target = tmpTarget;
}

function isApprovalRoleInList(approvalRole) {
	var result = false;
	for (var i = 0; i < altApproverRolesList.length; i++) {
		if (altApproverRolesList[i] == approvalRole) {
		    result = true;
            break;
        }
	}
	return result;
}

//flowdown
var flowdownSelectedRowId = null;
function initializeFlowdownGrid(){
 	flowdownBeanGrid = new dhtmlXGridObject('flowdownDataDiv');

	//this is needed because qpl grid is a row span and it the alternate color
	//and this grid need to exist at the same time as qpl grid
	flowdownBeanGrid.setEvenoddmap(null);
	//by setting this to null this grid will use it own color scheme.

	//-1 to turn off smart rendering but allows sorting
	initGridWithConfig(flowdownBeanGrid,flowdownConfig,-1,true);
 	if( typeof( flowdownJsonMainData ) != 'undefined' ) {
   	flowdownBeanGrid.parse(flowdownJsonMainData,"json");
 	}

	flowdownBeanGrid.attachEvent("onRowSelect",flowdownSelectRow);
	flowdownBeanGrid.attachEvent("onRightClick",flowdownSelectRow);
}

function flowdownSelectRow() {
	// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];
   if( ee != null ) {
   	if( ee.button != null && ee.button == 2 ) rightClick = true;
   	else if( ee.which == 3  ) rightClick = true;
   }
	flowdownSelectedRowId = rowId0;

	//stop here if it's not a right mouse click
	if( !rightClick ) return;

	var menuItems = new Array();
	tmpDataSource = flowdownBeanGrid.cells(flowdownSelectedRowId,flowdownBeanGrid.getColIndexById("dataSource")).getValue();
	tmpContent = flowdownBeanGrid.cells(flowdownSelectedRowId,flowdownBeanGrid.getColIndexById("content")).getValue();
	if (tmpDataSource == 'new') {
		if (viewLevel == 'requestor' || viewLevel == 'approver') {
			menuItems[menuItems.length] = 'text='+messagesData.deleteLine+';url=javascript:removeFlowdownSelectedLine();';
			menuItems[menuItems.length] = 'text='+messagesData.uploadFlowdown+';url=javascript:uploadFlowdown();';
		}
	}
	//allow user to view flowdown if it's online and catalog_company.display_flow_down = 'Y'
	if (tmpContent.length > 0 && altCatalogFacility[0].displayFlowDown == 'Y') {
		menuItems[menuItems.length] = 'text='+messagesData.viewFlowdown+';url=javascript:viewFlowdown();';
	}
	//by this point if menu is empty then don't show anything
	if (menuItems.length == 0) {
		toggleContextMenu('contextMenu');
	} else {
		replaceMenu('flowdownRightClickMenu',menuItems);
		toggleContextMenu('flowdownRightClickMenu');
	}
} //end of method

function viewFlowdown() {
	var url = flowdownBeanGrid.cells(flowdownSelectedRowId,flowdownBeanGrid.getColIndexById("content")).getValue();
	children[children.length] = openWinGeneric(url,"viewflowdown",800,800,'yes' );
}

function uploadFlowdown() {
	showTransitWin();
	tmpFlowdown = flowdownBeanGrid.cells(flowdownSelectedRowId,flowdownBeanGrid.getColIndexById("flowDown")).getValue();
	tmpFlowdown = tmpFlowdown.replace(/\s/g, "");
	var fileName = $v("requestId")+"-"+tmpFlowdown;
	var url = "uploadfile.do?fileName="+fileName+"&modulePath=catalogAddFlowdown";
	children[children.length] = openWinGeneric(url,"uploadfile",500,200,'yes' );
}

function reloadFlowdownData(tmpFlowdownJsonMainData) {
	flowdownJsonMainData = tmpFlowdownJsonMainData;
	flowdownSelectedRowId = null;
	initializeFlowdownGrid();
	closeTransitWin();
}

function addFlowdown() {
	showTransitWin();
 	var loc = "flowdownsearchmain.do?userAction=search"+"&catalogId="+$v("catalogId")+"&companyId="+$v("companyId");
 	children[children.length] = openWinGeneric(loc,"flowdownsearch","500","500","yes","50","50","20","20","no");
}

/*
function editFlowdownData() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=addFlowdown&requestId='+$v("requestId")+'&calledFrom=editFlowdown'+
		 		 '&facilityName='+$v("facilityName")+'&engEvalFacilityId='+$v("facilityId")+'&companyId='+$v("companyId");
	children[children.length] = openWinGeneric(url,"catalogAddFlowdown",500,350,'yes' );
}
*/

function removeFlowdownSelectedLine() {
	showTransitWin();
	callToServer('catalogaddrequest.do?uAction=deleteFlowdown&requestId='+$v("requestId")+'&companyId='+$v("companyId")+
					 '&flowDown='+flowdownBeanGrid.cells(flowdownSelectedRowId,flowdownBeanGrid.getColIndexById("flowDown")).getValue());
}

function reloadFlowdown() {
	showTransitWin();
	callToServer("catalogaddrequest.do?uAction=reloadFlowdown&requestId="+$v("requestId"));
}

function newFlowdown(newFlowdown) {
	showTransitWin();
	callToServer("catalogaddrequest.do?uAction=addFlowdown&requestId="+$v("requestId")+"&companyId="+$v("companyId")+"&catalogId="+$v("catalogId")+
					 "&flowDown="+newFlowdown);
}

var useTypeOtherSelected = false;
function toggleTypeOfUseOther(value) {
	if (value == "OTHER") {
		useTypeOtherSelected = true;
		document.getElementById("useTypeOther").style["display"] = "";
	}
	else {
		useTypeOtherSelected = false;
		document.getElementById("useTypeOther").style["display"] = "none";
	}
}

function toggleSubstrate(value) {
	if (value != "FLYING") {
		document.getElementById("substrate").style["display"] = "";
	}
	else {
		document.getElementById("substrate").style["display"] = "none";
	}
}

//helper function to add elements to the form
function addNewFormElement(inputForm, elementName, elementValue){
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", elementName);
	input.setAttribute("value", elementValue);
	inputForm.appendChild(input);
}

function shelfLifeBasisChanged(rowId) {
	var tmpShelfLifeB = qplBeanGrid.cells(rowId,qplBeanGrid.getColIndexById("shelfLifeBasis")).getValue();
	if (tmpShelfLifeB == 'M' || tmpShelfLifeB == 'R' || tmpShelfLifeB == 'S' || tmpShelfLifeB == 'P' || tmpShelfLifeB == 'T') {
		var tmpShelfLifeDays = qplBeanGrid.cells(rowId,qplBeanGrid.getColIndexById("shelfLifeDays")).getValue();
		if (tmpShelfLifeDays == null || tmpShelfLifeDays == '&nbsp;') {
			tmpShelfLifeDays = "";
		}else {
			tmpShelfLifeDays = tmpShelfLifeDays.trim();
		}
		qplBeanGrid.cells(rowId,qplBeanGrid.getColIndexById("shelfLifeDaysPermission")).setValue("Y");
		qplBeanGrid.cells(rowId,qplBeanGrid.getColIndexById("shelfLifeDays")).setValue(tmpShelfLifeDays);
	}else {
		qplBeanGrid.cells(rowId,qplBeanGrid.getColIndexById("shelfLifeDaysPermission")).setValue("N");
		qplBeanGrid.cells(rowId,qplBeanGrid.getColIndexById("shelfLifeDays")).setValue("");
	}
}

//use approval section
var hmrbSelectedRowId = null;
function initializeHmrbGrid(){

	if(typeof (hmrbBeanGrid) != 'undefined')
		hmrbBeanGrid.destructor();
 	hmrbBeanGrid = new dhtmlXGridObject('hmrbDataDiv');

	//this is needed because qpl grid is a row span and it the alternate color
	//and this grid need to exist at the same time as qpl grid
	hmrbBeanGrid.setEvenoddmap(null);
	//by setting this to null this grid will use it own color scheme.

	//-1 to turn off smart rendering but allows sorting
	initGridWithConfig(hmrbBeanGrid,hmrbConfig,-1,true);
 	if( typeof( hmrbJsonMainData ) != 'undefined' ) {
   	    hmrbBeanGrid.parse(hmrbJsonMainData,"json");
 	}

	hmrbBeanGrid.attachEvent("onRowSelect",hmrbSelectRow);
	hmrbBeanGrid.attachEvent("onRightClick",hmrbSelectRow);
}

function calendarDateChangedCallBack(fieldId) {
    setUseCodeExpiration();
    setTimeout('setUpdateHmrbExpiration()',50);
}

function setUseCodeExpiration() {
    var blockBefore = hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("beginDateJsp")).getValue();
    var blockAfter = hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("endDateJsp")).getValue();
    if (blockBefore != null && blockBefore.length > 0) {
        $("blockBefore_endDateJsp").value = blockBefore;
    }else {
        $("blockBefore_endDateJsp").value = "";
    }
    if (blockAfter != null && blockAfter.length > 0) {
        $("blockAfter_beginDateJsp").value = blockAfter;
    }else {
         $("blockAfter_beginDateJsp").value = "";
    }
}

function hmrbSelectRow() {
	// to show menu directly
    rightClick = false;
    rowId0 = arguments[0];
    colId0 = arguments[1];
    ee     = arguments[2];
    if( ee != null ) {
        if( ee.button != null && ee.button == 2 ) rightClick = true;
        else if( ee.which == 3  ) rightClick = true;
    }
    hmrbSelectedRowId = rowId0;

    //setting variable for calendar for Begin and End Date
    setUseCodeExpiration();

    //stop here if it's not a right mouse click
	if( !rightClick ) return;

	var menuItems = new Array();
	if (hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("dataSource")).getValue() == 'new') {
		if (viewLevel == 'requestor' || viewLevel == 'approver') {
			menuItems[menuItems.length] = 'text='+messagesData.editCopy+';url=javascript:editHmrbData();';
			menuItems[menuItems.length] = 'text='+messagesData.deleteLine+';url=javascript:removeHmrbSelectedLine();';
        }else {
            menuItems[menuItems.length] = 'text='+messagesData.view+';url=javascript:viewHmrbData();';
        }
	}else if (hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("dataSource")).getValue() == 'catalog') {
	    //don't allow users to edit anything if asking for new work area approval
		if ($v("startingView") != 3 && (viewLevel == 'requestor' || viewLevel == 'approver')) {
            if (hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("approvalCodeName")).getValue() != 'AG') {
                menuItems[menuItems.length] = 'text='+messagesData.editCopy+';url=javascript:copyHmrbData();';
            }
        }
        /*
        if (setUpdateExpirationPermission) {
            menuItems[menuItems.length] = 'text='+messagesData.setUpdateExpiration+';url=javascript:setUpdateHmrbExpiration();';
        }
        */
    }
    //by this point if menu is empty then don't show anything
	if (menuItems.length == 0) {
		toggleContextMenu('contextMenu');
	} else {
		replaceMenu('hmrbRightClickMenu',menuItems);
		toggleContextMenu('hmrbRightClickMenu');
	}
} //end of method

function addHmrb() {
    prepareForTransitWin();
    isKit = qplContainsKit();
	var url = 'catalogaddrequest.do?uAction=addHmrb&requestId='+$v("requestId")+'&calledFrom=newHmrb'+
			  '&catalogCompanyId='+$v("catalogCompanyId")+'&catalogId='+$v("catalogId")+
			  '&companyId='+$v("companyId")+'&facilityId='+$v("facilityId") + '&isKit=' + isKit;
	children[children.length] = openWinGeneric(url,"catalogAddHmrb",1000,700,'yes' );
}

function reloadHmrbData(tmpHmrbJsonMainData, closeTransitWinflag) {
	hmrbJsonMainData = tmpHmrbJsonMainData;
	hmrbSelectedRowId = null;
	initializeHmrbGrid();
    setHmrbActionViewLevel();
    resetGridSize('hmrbDataDiv', hmrbBeanGrid);
    if("Y" == $v('closeTransitWinflag'))
    	closeTransitWin();

}

function editHmrbData() {
	prepareForTransitWin();
	isKit = qplContainsKit();
	var url = 'catalogaddrequest.do?uAction=editHmrb&requestId='+$v("requestId")+
              '&hmrbLineItem='+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
              '&calledFrom=editHmrb&catalogCompanyId='+$v("catalogCompanyId")+'&catalogId='+$v("catalogId")+
			  '&companyId='+$v("companyId")+'&facilityId='+$v("facilityId") + '&isKit=' + isKit  + '&allowEditUseApproval=' + $v('allowEditUseApproval');
	children[children.length] = openWinGeneric(url,"catalogAddHmrb",1000,700,'yes' );
}

function qplContainsKit()
{
	isKit = false;
	qplBeanGridSize = qplBeanGrid.getRowsNum();
	for(var i = 1; i <= qplBeanGridSize;i++) {
		if(qplBeanGrid.cells(i,qplBeanGrid.getColIndexById("isKit")).getValue() == 'Y')
			{
				isKit = true;
				break;
			}
	}
	return isKit;
}

function copyHmrbData() {
	prepareForTransitWin();
	var url = 'catalogaddrequest.do?uAction=copyHmrb&requestId='+$v("requestId")+
              '&hmrbLineItem='+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
              '&calledFrom=copyHmrb'+'&catalogCompanyId='+$v("catalogCompanyId")+'&catalogId='+$v("catalogId")+
			  '&companyId='+$v("companyId")+'&facilityId='+$v("facilityId");
	children[children.length] = openWinGeneric(url,"catalogAddHmrb",1000,700,'yes' );
}

function viewHmrbData() {
	prepareForTransitWin();
	var url = 'catalogaddrequest.do?uAction=viewHmrb&requestId='+$v("requestId")+
              '&hmrbLineItem='+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
              '&calledFrom=viewHmrb&catalogCompanyId='+$v("catalogCompanyId")+'&catalogId='+$v("catalogId")+
			  '&companyId='+$v("companyId")+'&facilityId='+$v("facilityId");
	children[children.length] = openWinGeneric(url,"catalogAddHmrb",1000,700,'yes' );
}

function removeHmrbSelectedLine() {
	prepareForTransitWin();
    callToServer("catalogaddrequest.do?uAction=deleteHmrbLine&requestId="+$v("requestId")+
                 "&lineItem="+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
                 "&setUpdateExpirationPermission="+$v("setUpdateExpirationPermission"));
}

function reloadHmrb(closeTransitWinflag) {
	if(typeof(closeTransitWinflag) == 'undefined')
		prepareForTransitWin();
	callToServer("catalogaddrequest.do?uAction=reloadHmrb&requestId="+$v("requestId")+'&engEvalFacilityId='+$v("facilityId")+'&companyId='+$v("companyId")+
                 "&setUpdateExpirationPermission="+$v("setUpdateExpirationPermission"));
}

function saveHmrbData() {

	//prepare grid for data sending
	hmrbBeanGrid.parentFormOnSubmit();
	$("uAction").value = 'saveHmrbData';
	var tmpTarget = document.genericForm.target;
	document.genericForm.target = 'catalogAddRequestFrame';
	document.genericForm.submit();
	document.genericForm.target = tmpTarget;
}

function setHmrbActionViewLevel() {
    if ($v("startingView") == 3) {
		//don't allow users to edit anything if asking for new work area approval
		$("hmrbActionSpan").innerHTML = '';
	}else {
        //limit user to just 1 new HRMB record per catalog add request
        var recordFound = false;
        for(var i = 1; i <= hmrbBeanGrid.getRowsNum();i++) {
            if (hmrbBeanGrid.cells(i,hmrbBeanGrid.getColIndexById("dataSource")).getValue() == 'new')
                recordFound = true;
        }
        if (!recordFound || $v("allowMultipleHmrb") == 'Y') {
            hmrbActionText = '<a href="#" onclick="addHmrb(); return false;">'+messagesData.addApprovalCode+'</a>';
            $("hmrbActionSpan").innerHTML = hmrbActionText+'<br>&nbsp;';
        }else {
            $("hmrbActionSpan").innerHTML = '<br>&nbsp;';
        }
    }
}

function uploadApprovedLetter() {
	showTransitWin();
    var tmpLineItem = qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("lineItem")).getValue();
    var tmpFileName = $v("requestId")+"-"+tmpLineItem;
    var url = 'uploadfile.do?fileName='+tmpFileName+'&modulePath=catalogAddLetter&allowMultiple='+
              '&companyId='+$v("companyId")+'&requestId='+$v("requestId")+'&lineItem='+tmpLineItem;
	children[children.length] = openWinGeneric(url,"uploadfile",500,200,'yes' );
}

function viewApprovedLetter() {
    url = "/catalogAddLetter/"+qplBeanGrid.cells(qplSelectedRowId,qplBeanGrid.getColIndexById("approvalLetterContent")).getValue();
    children[children.length] = openWinGeneric(url,"viewLetter",800,800,'yes' );
}

function setUpdateHmrbExpiration() {
    showTransitWin();
    callToServer("catalogaddrequest.do?uAction=setUpdateUseCodeExpiration&requestId="+$v("requestId")+"&calledFrom=part"+
                 "&lineItem="+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
                 "&startDate="+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("beginDateJsp")).getValue()+
                 "&endDate="+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("endDateJsp")).getValue());
}

function callToServerCallback() {
    closeTransitWin();
}

function setStorageActionViewLevel() {
	if(viewLevel != 'view')
	{
	    storageActionText = '<a href="#" onclick="addStorage(null,null); return false;">'+messagesData.add+'</a> | <a href="#" onclick="prepareForTransitWin();auditLocalSaveStorageData(); return false;">'+messagesData.save+'</a> ';
	    $("storageActionSpan").innerHTML = storageActionText+'<br>&nbsp;';
	}
}

//use approval section
var storageSelectedRowId = null;
var storageBeanGrid;
var loadUseApprovalBeanGrid
function initializeStorageGrid(){

	if(typeof (storageBeanGrid) != 'undefined')
		storageBeanGrid.destructor();
		
 	storageBeanGrid = new dhtmlXGridObject('storageDataDiv');

	//this is needed because qpl grid is a row span and it the alternate color
	//and this grid need to exist at the same time as qpl grid
 	storageBeanGrid.setEvenoddmap(null);
	//by setting this to null this grid will use it own color scheme.

	//-1 to turn off smart rendering but allows sorting
 	if(useApprovalBeanGrid)
	initGridWithConfig(storageBeanGrid,storageConfig,-1,true);
 	if( typeof( storageJsonMainData ) != 'undefined' ) {
 		storageBeanGrid.parse(storageJsonMainData,"json");
 	}

 	storageBeanGrid.attachEvent("onRowSelect",  storageSelect);
 	storageBeanGrid.attachEvent("onRightClick", storageSelectRightClick);
}

function storageSelect(rowId)
{
	storageSelectedRowId = rowId;
}

function storageSelectRightClick(rowId)
{
	storageSelectedRowId = rowId;

	if(viewLevel != 'view')
	{
		toggleContextMenu('storageRightClickMenu');
	}
}

function addStorage(appDesc,app)
{
	var rId = new Date();
	var newId = rId.getTime();
	var newData = new Array();
	var cntr = 0;
	newData[cntr++] = 'N';
	if(appDesc == null || appDesc == undefined)
		newData[cntr++] = 'Y';
	else
		newData[cntr++] = 'N';
	if(viewLevel == 'approver' || viewLevel == 'requestor')
		{
			newData[cntr++] = 'Y';
			newData[cntr++] = 'Y';
		}
	else
		{
			newData[cntr++] = 'N';
			newData[cntr++] = 'N';
		}
	if(appDesc == null || appDesc == undefined)
		newData[cntr++] = '';
	else
		newData[cntr++] = appDesc;
	newData[cntr++] = '';
	newData[cntr++] = '';
	if(appDesc == null || appDesc == undefined)
		newData[cntr++] = '';
	else
		newData[cntr++] = app;
	newData[cntr++] = $v("companyId");
	newData[cntr++] = $v("requestId");
	if(appDesc == null || appDesc == undefined)
		newData[cntr++] = 'Y';
	else
		newData[cntr++] = 'N';
	newData[cntr++] = 'N';
	storageBeanGrid.addRow(newId,newData,0);
	storageBeanGrid.selectRowById(newId);
	waBind(newId,'applicationDesc','application');
}


function waBind(rowId, el1, el2)
{
	j$().ready(function() {
		function log(event, data, formatted) {
			 setGridCellValue(storageBeanGrid,rowId,"application",formatted.split("]>^~<[")[0]);
			$(el1 + rowId).className = "inputBox";
		}

		j$("#" + el1 + rowId).autocomplete("catalogaddrequest.do",{
			extraParams: {existingWorkAreas: function() {return existingWA();},
				uAction: function() {return "storageCheckExistingWorkAreas";},
				applicationDesc: function() {return  $v(el1 + rowId);},
				requestId: function() {return  $v('requestId');}
				},
				width: 200,
				max: 10,  // default value is 10
				cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
				scrollHeight: 200,
				formatItem: function(data, i, n, value) {
					return  value.split("]>^~<[")[1].substring(0,40);
				},
				formatResult: function(data, value) {
					return value.split("]>^~<[")[1];
				}
		});

		j$("#" + el1 + rowId).bind('keyup',(function(e) {
			  var keyCode = (e.keyCode ? e.keyCode : e.which);

			  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
			  	invalidateRequestor(rowId, el1, el2);
		}));
		j$("#" + el1 + rowId).result(log).next().click(function() {
			j$(this).prev().search();
		});

	});
}

function existingWA()
{
	var existing = '';
	var rowsNum = storageBeanGrid.getRowsNum();
	for(var i = 0; i < rowsNum; i++)
	{
		var rowId = storageBeanGrid.getRowId(i);
		if(rowId != storageSelectedRowId
				&& $('applicationDesc' + rowId) != null
				&& $('applicationDesc' + rowId) != undefined
				&& $('applicationDesc' + rowId).className != "inputBox red"
				&& $v('applicationDesc' + rowId) != "")
				existing +=  "'" + 		gridCellValue(storageBeanGrid,rowId,"application") + "',";
		else if($('applicationDesc' + rowId) == null
				|| $('applicationDesc' + rowId) == undefined )
				existing +=  "'" + 	gridCellValue(storageBeanGrid,rowId,"application") + "',";
	}
	return existing;
}

function invalidateRequestor(rowId, el1, el2)
{
 var requestorName  =  document.getElementById(el1 + rowId);
 if (requestorName.value.length == 0) {
   requestorName.className = "inputBox";
 }else {
   requestorName.className = "inputBox red";
 }
 //set to empty
 setGridCellValue(storageBeanGrid,rowId,"application","");
}

function saveStorage()
{
	//prepare grid for data sending
	storageBeanGrid.parentFormOnSubmit();
	$("uAction").value = 'saveStorageData';
	var tmpTarget = document.genericForm.target;
	document.genericForm.target = 'catalogAddRequestFrame';
	document.genericForm.submit();
	document.genericForm.target = tmpTarget;
}

function existingStorageCheck(app)
{
	for(var i = 0; i < storageBeanGrid.getRowsNum(); i++)
	{
		var rowId = storageBeanGrid.getRowId(i);
		if(gridCellValue(storageBeanGrid,rowId,"application") == app)
			{
			    return rowId;
			    break;
			}
	}
	return null;
}

function deleteStorage(rowId)
{
		var id = rowId;
		if(id == null)
			id = storageSelectedRowId;

		if(gridCellValue(storageBeanGrid,id,"newRow") != 'Y' )
		{
		    showTransitWin();
		    callToServer("catalogaddrequest.do?uAction=deleteStorageData&requestId="+$v("requestId")+"&application="+ gridCellValue(storageBeanGrid,id,"application"));
		}
		else
			closeTransitWin();

		storageBeanGrid.deleteRow(id);
}


function reloadStorageData(tmpStorageJsonMainData, closeTransitWinflag)
{
storageJsonMainData = tmpStorageJsonMainData;
storageSelectedRowId = null;

for(var i = 0; i < storageJsonMainData.rows.length; i++)
	if(viewLevel == 'view')
		{
			storageJsonMainData.rows[i].data[2] = 'N';
			storageJsonMainData.rows[i].data[3] = 'N';

		}

initializeStorageGrid();

if("Y" == closeTransitWinflag)
	closeTransitWin();
}

function auditStorageData()
{
	var err = "";
	for(var i = 0; i < storageBeanGrid.getRowsNum(); i++)
		{
			var rowId = storageBeanGrid.getRowId(i);
			err += validWa(rowId,i);
			if(gridCellValue(storageBeanGrid,rowId,"maximumQuantityStored") != '' && !isInteger(gridCellValue(storageBeanGrid,rowId,"maximumQuantityStored")))
				err += messagesData.maximumQuantityStored + " row " + (i + 1) + "\n";

			if(gridCellValue(storageBeanGrid,rowId,"averageQuantityStored") != '' && !isInteger(gridCellValue(storageBeanGrid,rowId,"averageQuantityStored")))
				err += messagesData.averageQuantityStored + " row " + (i + 1) + "\n";
		}
	return err;
}

function validWa(rowId,index)
{
	var err = "";
	if($('applicationDesc' + rowId) != null && $('applicationDesc' + rowId) != undefined && $('applicationDesc' + rowId).className == "inputBox red")
		err += messagesData.storageWorkArea + " row " + (index + 1) + "\n";
	return err;
}

function auditStorageSaveData()
{
	var err = "";
	for(var i = 0; i < storageBeanGrid.getRowsNum(); i++)
		{
			var rowId = storageBeanGrid.getRowId(i);
			err += validWa(rowId,i);
			if(gridCellValue(storageBeanGrid,rowId,"maximumQuantityStored") != '' && !isInteger(gridCellValue(storageBeanGrid,rowId,"maximumQuantityStored")))
				err += messagesData.maximumQuantityStored + " row " + (i + 1) + "\n";

			if(gridCellValue(storageBeanGrid,rowId,"averageQuantityStored") != '' && !isInteger(gridCellValue(storageBeanGrid,rowId,"averageQuantityStored")))
				err += messagesData.averageQuantityStored + " row " + (i + 1) + "\n";
		}
	return err;
}

function auditLocalSaveStorageData()
{
	var err = auditStorageSaveData();

	if(err.length > 0)
		{
			alert(messagesData.validvalues+"\n"+err);
			closeTransitWin();
		}
	else
		saveStorage();
}

function hmrbEmapRequired()
{
    //NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
    for(var i = 1; i <= hmrbBeanGrid.getRowsNum();i++)
		if ((hmrbBeanGrid.cells(i,hmrbBeanGrid.getColIndexById("dataSource")).getValue() == 'new' ||
            hmrbBeanGrid.cells(i,hmrbBeanGrid.getColIndexById("dataSource")).getValue() == 'catalog') &&
            hmrbBeanGrid.cells(i,hmrbBeanGrid.getColIndexById("emapRequired")).getValue() == 'Y')
				return messagesData.hmrbEmapRequired+"\n";

    return '';
}




