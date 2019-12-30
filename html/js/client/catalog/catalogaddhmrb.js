var dhxWins = null;
var children = new Array();
var windowCloseOnEsc = true;

function $(a) {
	return document.getElementById(a);
}

function editOnLoad(action) {
	closeTransitWin();
	if (action == 'submitHmrb' && !showErrorMessage) {
		opener.reloadHmrb();
		window.close();
	}else {
		if (showErrorMessage) {
			showMessageDialog();
		}
		try{
			setOption($('dischargeSinkDrainId').length,messagesData.yes,theDischargeSinkDrainId, "dischargeSinkDrainId");
		}catch(ex){}
		
        loadUsageCategory();
        if (action == "editHmrb" || action == "copyHmrb" || action == "viewHmrb") {
            preSelectData();
        }
        setFacilitySpecificFields();
        matlThinnedChanged();
 
    }
}

function setAction() {
    if ($v("uAction") == "addHmrb") {
        $("actionSpan").innerHTML = '<a href="#" onclick="submitNew(); return false;">'+messagesData.saveNewAndReturn+'</a>'+
                                    ' | <a href="#" onclick="cancel(); return false;">'+messagesData.cancel+'</a>';
    }else if ($v("uAction") == "editHmrb") {
        if ($v("selectedUsageCategoryId") == $v("usageCategoryId") && $v("selectedUsageSubcategoryId") == $v("usageSubcategoryId")) {
            $("actionSpan").innerHTML = '<a href="#" onclick="submitUpdate(); return false;">'+messagesData.updateAndReturn+'</a>'+
                                        ' | <a href="#" onclick="cancel(); return false;">'+messagesData.cancel+'</a>';
        }else {
            $("actionSpan").innerHTML = /*'<a href="#" onclick="submitNew(); return false;">'+messagesData.saveNewAndReturn+'</a> | '+ */
                                        '<a href="#" onclick="submitUpdate(); return false;">'+messagesData.updateAndReturn+'</a>'+
                                        ' | <a href="#" onclick="cancel(); return false;">'+messagesData.cancel+'</a>';
        }
    }else if ($v("uAction") == "copyHmrb") {
        $("actionSpan").innerHTML = '<a href="#" onclick="submitUpdate(); return false;">'+messagesData.updateAndReturn+'</a>'+
                                    ' | <a href="#" onclick="cancel(); return false;">'+messagesData.cancel+'</a>';
    }else if ($v("uAction") == "viewHmrb") {
        $("actionSpan").innerHTML = '<a href="#" onclick="cancel(); return false;">'+messagesData.cancel+'</a>';
    }else {
        $("actionSpan").innerHTML = '<a href="#" onclick="cancel(); return false;">'+messagesData.cancel+'</a>';
    }
    $("actionSpan").style["display"] = "";
}

function closeThisWindow() {
	try {
		opener.closeTransitWin();
	}catch(e){}
}

function checkClose(action) {
	if( action != 'submitHmrb' ) {
		closeAllchildren();
		closeThisWindow();
	}
}

function cancel() {
	window.close();
}

function submitNew() {
    //clear this so the process will create new planned_id
    $("hmrbLineItem").value = '';
    submitData();
}

function submitUpdate() {
    submitData();
}

function submitData() {
    getSelectedUseLocationIds();
    getSelectedBuildingIds();
    getSelectedPurchasingMethodIds();
    getSelectedUseDescriptionIds();
    getSelectedSubstrateIds();
    if (auditData()) {
      try {
          if ($("useLocation"+$v("useLocationBoothIndex")*1).checked) {
            getSelectedBoothIds();
          }else {
            $("selectedBoothIds").value = "";
          }
      }catch(ex) {
        $("selectedBoothIds").value = "";    
      }
      
      $("uAction").value = 'submitHmrb';
	  document.genericForm.submit();
    }else {
	  return false;
    }
}

function auditData() {
	var result = true;

    var missingFields = "";

    if (isEmptyV("usageCategoryId")) {
         missingFields += "\t"+messagesData.usageCategory+"\n";
    }
    if (isEmptyV("usageSubcategoryId")) {
         missingFields += "\t"+messagesData.usageSubcategory+"\n";
    }
    if (isEmptyV("materialCategoryId")) {
         missingFields += "\t"+messagesData.materialCategory+"\n";
    }
    if (isEmptyV("materialSubcategoryId")) {
         missingFields += "\t"+messagesData.materialSubcategory+"\n";
    }

    if (isEmptyV("selectedPurchasingMethodIds")) {
         missingFields += "\t"+messagesData.purchasing+"\n";
    }

    if (showGreaterThan54Gal) {
        if (isEmptyV("gt54Gal")) {
            missingFields += "\t"+messagesData.gt54Gal+"\n";
        }
    }
    if($('processSpan').style.display == '')
    {
	    if($v('dischargeSinkDrainId') == '-9229068743')
	    	missingFields += "\t"+messagesData.dischargeSinkDrain+"\n";
	    else if (isEmptyV("selectedUseDescriptionIds")) {
	         missingFields += "\t"+messagesData.useProcesses+"\n";
	    }
	    if (isEmptyV("selectedSubstrateIds")) {
	        missingFields += "\t"+messagesData.substrateAppliedOn+"\n";
	   }
	    var estimatedAnnualUsage = $v('estimatedAnnualUsage');
	    if (isEmptyV("estimatedAnnualUsage") || !isFloat(estimatedAnnualUsage) || estimatedAnnualUsage <= 0) {
	        missingFields += "\t"+messagesData.estimatedAnnualUsage+"\n";
	    }
	    if (isEmptyV("estimatedAnnualUsageUnit")) {
	        missingFields += "\t"+messagesData.estimatedAnnualUsageUnit+"\n";
	    }
    }
    selectedUseDescriptionIds = $v('selectedUseDescriptionIds');
    if(selectedUseDescriptionIds.indexOf(otherUseDescriptionId) != -1 && isEmptyV('additionalDescription'))
    	   missingFields += "\t"+messagesData.missingAdditionalDescription+"\n";
    
 /*
    if (showIntendedProductFormulation) {
        if (isEmptyV("intendedProductFormulation")) {
            missingFields += "\t"+messagesData.intendedProductFormulation+"\n";
        }
    }
 */
    if (showMaxQtyPerShift) {
        if (maxQtyPerShiftRequired) {
            if (isEmptyV("maxQtyUsePerShift") || isEmptyV("maxQtyUsePerShiftUnit")) {
                missingFields += "\t"+messagesData.maxQtyUsePerShift+"\n";
            }
        }
    }

    /*
    if (showProcessInfo) {
        if (isEmptyV("matlFlyAwayWithAircraft")) {
             missingFields += "\t"+messagesData.matlFlyAwayWithAircraft+"\n";
        }
    }
    */

    if (showFtwSpecific) {
        if (isEmptyV("selectedBuildingIds")) {
             missingFields += "\t"+messagesData.building+"\n";
        }

        if (isEmptyV("selectedUseLocationIds")) {
             missingFields += "\t"+messagesData.processLocation+"\n";
        }
        if($v('selectedUseLocationIds').indexOf(otherProcessLocationId) != -1 && $v('processLocationOtherText') == '')
        	 missingFields += "\t"+messagesData.otherProccessLocation+"\n";
    }
   
    //thinned material
    if($v("matlThinnedWhenUsed") == 'Y') {
        //msds number
        if (isEmptyV("thinnedCustomerMsdsNumber")) {
             missingFields += "\t"+messagesData.withMsds+"\n";
        }
        //material thinned
        if (isEmptyV("thinnedMatlAmountInRatio")) {
             missingFields += "\t"+messagesData.thinnerRatioNumber+"\n";
        }else {
            if (isNaN($v("thinnedMatlAmountInRatio"))) {
				missingFields += "\n"+messagesData.thinnerRatioNumber+"\n";
		    }
        }
        //thinning
        if (isEmptyV("thinnerAmountInRatio")) {
             missingFields += "\t"+messagesData.materialRatioNumber+"\n";
        }else {
            if (isNaN($v("thinnerAmountInRatio"))) {
				missingFields += "\n"+messagesData.materialRatioNumber+"\n";
		    }
        }
        //unit
        if (isEmptyV("thinningUnit")) {
             missingFields += "\t"+messagesData.thinningRatioUnit+"\n";
        }
    }
    
    if (isEmptyV("importFlag")) {
        missingFields += "\t"+messagesData.foreignSupplier+"\n";
    }

    if ($v('showProgram') == 'Y' && isEmptyV("programId")) {
        missingFields += "\t"+messagesData.program+"\n";
    }

    if (missingFields.length > 0) {
        alert(messagesData.validvalues+"\n"+missingFields);
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

function regExcape(str) {
// if more special cases, need more lines.
return str.replace(new RegExp("[([]","g"),"[$&]");
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
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
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

function loadUsageCategory() {
  var idArray = altUsageCategory;
  //clear current data
  var inv = $("usageCategoryId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }
  //load new data
  selectedIndex = 0;
  if( idArray != null ) {
      var startingIndex = 0;
      if (idArray.length == 0 || idArray.length > 1) {
	  	 setOption(0,messagesData.selectOne,"", "usageCategoryId");
		 startingIndex = 1;
	  }
      for (var i=0; i < idArray.length; i++) {
	    setOption(i+startingIndex,idArray[i].usageCategoryName,idArray[i].usageCategoryId, "usageCategoryId");
        if (idArray[i].usageCategoryId == $v("selectedUsageCategoryId")) {
            selectedIndex = i+startingIndex;
        }
      }
  }else {
    setOption(0,messagesData.selectOne,"", "usageCategoryId");
  }
  $("usageCategoryId").selectedIndex = selectedIndex;
  usageCategoryChanged();
} //end of method

function usageCategoryChanged() {
  var usageCategoryIdIndex = $("usageCategoryId").selectedIndex;
  if(usageCategoryIdIndex > 0)
  {
	  var showProgram = altUsageCategory[$("usageCategoryId").selectedIndex - 1].showProgram;
	  if(showProgram && showProgram == 'Y')
		  {
		  	$('programDiv').style.visibility = '';
		  	$('showProgram').value = 'Y';
		  }
	  else
		  {
		  	$('programDiv').style.visibility="hidden";
		 	$('showProgram').value = 'N';
		  }
  }
  else
	  {
	  	$('programDiv').style.visibility = 'hidden';
	 	$('showProgram').value = 'N';
	  }
	
  var selectedUsageCategory = $("usageCategoryId").value;
  var idArray = altUsageSubCategory[selectedUsageCategory];

  var inv = $("usageSubcategoryId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }

  selectedIndex = 0;
  if( idArray != null ) {
	  var startingIndex = 0;
      if (idArray.length == 0 || idArray.length > 1) {
	  	 setOption(0,messagesData.selectOne,"", "usageSubcategoryId");
		 startingIndex = 1;
	  }
      for (var i=0; i < idArray.length; i++) {
	    setOption(i+startingIndex,idArray[i].usageSubcategoryName,idArray[i].usageSubcategoryId, "usageSubcategoryId");
        if (idArray[i].usageSubcategoryId == $v("selectedUsageSubcategoryId")) {
            selectedIndex = i+startingIndex;
        }
      }
  }else {
    setOption(0,messagesData.selectOne,"", "usageSubcategoryId");
  }

  $("usageSubcategoryId").selectedIndex = selectedIndex;
  usageSubcategoryChanged();
} //end of method

var showForProd = false;
function usageSubcategoryIsProd() {
  //determine whether usage subcategory is Production
  var selectedUsageCategory = $("usageCategoryId").value;
  var idArray = altUsageSubCategory[selectedUsageCategory];
  if (idArray != null) {
      var selectedUsageSubCategory = $("usageSubcategoryId").value;
      for (var i = 0; i < idArray.length; i++) {
          if (selectedUsageSubCategory == idArray[i].usageSubcategoryId) {
              if (idArray[i].production == 'Y') {
                  showForProd = true;
              }else {
                  showForProd = false;
              }
              break;
          }
      }
  }else {
      showForProd = false;
  }
}

function usageSubcategoryChanged() {
  //reset drop down
  var inv = $("materialCategoryId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }

  selectedIndex = 0;
  //material category is semi related to usage subcategory
  //depending on which usage subcategory is selected then display the material category
  if (isEmptyV("usageSubcategoryId")) {
    setOption(0,messagesData.selectOne,"", "materialCategoryId");
  }else {
      usageSubcategoryIsProd();
      var idArray = altMaterialCategory;
      if( idArray != null ) {
          var startingIndex = 0;
          if (idArray.length == 0 || getProdorNonProdCount(idArray,showForProd) > 1) {
             setOption(0,messagesData.selectOne,"", "materialCategoryId");
             startingIndex = 1;
          }
          for (var i=0; i < idArray.length; i++) {
            if (showForProd) {
                //show production material category
                if (idArray[i].showForProd == 'Y') {
                    setOption(startingIndex,idArray[i].materialCategoryName,idArray[i].materialCategoryId, "materialCategoryId");
                    if (idArray[i].materialCategoryId == $v("selectedMaterialCategoryId")) {
                        selectedIndex = startingIndex;
                    }
                    startingIndex++;
                }
            }else {
                //show non-production material category
                if (idArray[i].showForNonProd == 'Y') {
                    setOption(startingIndex,idArray[i].materialCategoryName,idArray[i].materialCategoryId, "materialCategoryId");
                    if (idArray[i].materialCategoryId == $v("selectedMaterialCategoryId")) {
                        selectedIndex = startingIndex;
                    }
                    startingIndex++;
                }
            }
          }
      }else {
        setOption(0,messagesData.selectOne,"", "materialCategoryId");
      }
  }
  setAction();
  $("materialCategoryId").selectedIndex = selectedIndex;
  materialCategoryChanged();
}

function materialCategoryChanged() {
  var selectedMaterialCategory = $("materialCategoryId").value;
  var idArray = altMaterialSubCategory[selectedMaterialCategory];
  var inv = $("materialSubcategoryId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }

  selectedIndex = 0;
  if( idArray != null ) {
	  var startingIndex = 0;
      if (idArray.length == 0 ||  getProdorNonProdCount(idArray,showForProd)  > 1)  {
	  	 setOption(0,messagesData.selectOne,"", "materialSubcategoryId");
		 startingIndex = 1;
	  }
      for (var i=0; i < idArray.length; i++) {
	    if (showForProd) {
            //show production material subcategory
            if (idArray[i].showForProd == 'Y') {
                setOption(startingIndex,idArray[i].materialSubcategoryName,idArray[i].materialSubcategoryId, "materialSubcategoryId");
                if (idArray[i].materialSubcategoryId == $v("selectedMaterialSubcategoryId")) {
                    selectedIndex = startingIndex;
                }
                startingIndex++;
            }
        }else {
            //show non-production material category
            if (idArray[i].showForNonProd == 'Y') {
                setOption(startingIndex,idArray[i].materialSubcategoryName,idArray[i].materialSubcategoryId, "materialSubcategoryId");
                if (idArray[i].materialSubcategoryId == $v("selectedMaterialSubcategoryId")) {
                    selectedIndex = startingIndex;
                }
                startingIndex++;
            }
        }
      }
  }else {
    setOption(0,messagesData.selectOne,"", "materialSubcategoryId");
  }
  $("materialSubcategoryId").selectedIndex = selectedIndex;
  materialSubcategoryChanged();
}

var showProcessInfo = false;
var showMaxQtyPerShift = false;
var maxQtyPerShiftRequired = false;
var showFtwSpecific = false;
function materialSubcategoryChanged() {
  //determine whether to show specific sections/fields
  var selectedMaterialCategory = $("materialCategoryId").value;
  var idArray = altMaterialSubCategory[selectedMaterialCategory];
  if (idArray != null) {
      var selectedMaterialSubCategory = $("materialSubcategoryId").value;
      for (var i = 0; i < idArray.length; i++) {
          if (selectedMaterialSubCategory == idArray[i].materialSubcategoryId) {

              //process info
              if (idArray[i].hideProcessInfo == 'Y') {
                  showProcessInfo = false;
              }else {
                  showProcessInfo = true;
              }
              //FACILITY SPECIFIC FIELDS
              if ($v("facilityId") == 'Fort Worth') {
                  //max qty per shift
                  if (idArray[i].hideQtyPerShift == 'Y') {
                      showMaxQtyPerShift = false;
                  }else {
                      showMaxQtyPerShift = true;
                  }
                  //max qty per shift
                  if (idArray[i].qtyPerShiftOptional == 'Y') {
                      maxQtyPerShiftRequired = false;
                  }else {
                      maxQtyPerShiftRequired = true;
                  }

                  //ftw specific
                  if (idArray[i].hideFtwSpecific == 'Y') {
                      showFtwSpecific = false;
                  }else {
                      showFtwSpecific = true;
                  }
              }

              break;
          }
      }
  }else {
    showProcessInfo = false;
    showMaxQtyPerShift = false;
    maxQtyPerShiftRequired = false;
    showFtwSpecific = false;
  }
  setViewLevel();
} //end of method

var showGreaterThan54Gal = false;
var showIntendedProductFormulation = false;
function setFacilitySpecificFields() {
    //palmdale specific
    if ($v("facilityId") == 'Palmdale') {
        showGreaterThan54Gal = true;
        $("maxQtyOnHandSpan").style["display"] = "";
        showIntendedProductFormulation = true;
    //    $("intendedProductionFormulationSpan").style["display"] = "";
    }
} //end of method

function setViewLevel() {
    $("headerSpan").style["display"] = "";

    if (showMaxQtyPerShift) {
        $("maxShiftUsageSpan").style["display"] = "";
    }else {
        $("maxShiftUsageSpan").style["display"] = "none";
    }
    if (maxQtyPerShiftRequired) {
        $("maxShiftUsageRequiredSpan").style["display"] = "";
    }else {
        $("maxShiftUsageRequiredSpan").style["display"] = "none";
    }

    if (showProcessInfo) {
        $("processSpan").style["display"] = "";
    }else {
        $("processSpan").style["display"] = "none";
    }


    //FACILITY SPECIFIC FIELDS
    $("ftwSpecificSpan").style["display"] = "none";
    $("eshContactInfoSpan").style["display"] = "";
    if (showFtwSpecific) {
        $("ftwSpecificSpan").style["display"] = "";
        boothClicked($v("useLocationBoothIndex")*1);
    }

} //end of method

function getSelectedUseDescriptionIds() {
    tempVal = "";
    for (i = 0; i < $v("useDescriptionCount")*1; i++) {
        if ($("useDescription"+i).checked) {
            tempVal += $v("useDescription"+i)+";";
        }
    }
    if($('processSpan').style.display == '')
    {
    	selectedDischargeSinkDrainId = $v('dischargeSinkDrainId');
    	if(selectedDischargeSinkDrainId != '')
    		tempVal += selectedDischargeSinkDrainId+";";
    }
    $("selectedUseDescriptionIds").value = tempVal;
}
function getSelectedSubstrateIds() {
    tempVal = "";
    for (i = 0; i < $v("substrateCount")*1; i++) {
        if ($("substrate"+i).checked) {
            tempVal += $v("substrate"+i)+";";
        }
    }
    $("selectedSubstrateIds").value = tempVal;
}
function getSelectedUseLocationIds() {
    tempVal = "";
    for (i = 0; i < $v("useLocationCount")*1; i++) {
        if ($("useLocation"+i).checked) {
            tempVal += $v("useLocation"+i)+";";
        }
    }
    $("selectedUseLocationIds").value = tempVal;
}

function getSelectedBuildingIds() {
    tempVal = "";
    for (i = 0; i < $v("buildingCount")*1; i++) {
        if ($("building"+i).checked) {
            tempVal += $v("building"+i)+";";
        }
    }
    $("selectedBuildingIds").value = tempVal;
}

function boothClicked(clickedIndex) {
    if ($("useLocation"+clickedIndex).checked) {
        $("boothLabelDiv").style["display"] = "";
        if (showForProd) {
            $("boothProductionDiv").style["display"] = "";
            $("boothNonProductionDiv").style["display"] = "none";
        }else {
            $("boothProductionDiv").style["display"] = "none";
            $("boothNonProductionDiv").style["display"] = "";   
        }
    }else {
        $("boothLabelDiv").style["display"] = "none";
        $("boothProductionDiv").style["display"] = "none";
        $("boothNonProductionDiv").style["display"] = "none";
    }
}

function useLocationOtherClicked(clickedIndex) {
    if (!$("useLocation"+clickedIndex).checked) {
        $("processLocationOtherText").value = "";
    }
}


function getSelectedBoothIds() {
    tempVal = "";
    boothCount = 0;
    if($v('usageCategoryId') == 54462)
    	boothCount = $v('boothCountProd');
    else 	
    	boothCount = $v('boothCountNonProd');
    for (i = 0; i < boothCount*1; i++) {

        if (showForProd) {
            if ($("boothProduction"+i).checked) {
                tempVal += $v("boothProduction"+i)+";";
            }
        }else {
            if ($("boothNonProduction"+i).checked) {
                tempVal += $v("boothNonProduction"+i)+";";
            }
        }
    }

    $("selectedBoothIds").value = tempVal;
}

function preSelectData() {
    preSelectUseDescription();
    preSelectSubstrate();
    preSelectUseLocation();
    preSelectBuilding();
    preSelectBooth();
    preSelectPurchasingMethod();
}

function preSelectUseDescription() {
    tmpArray = $v("selectedUseDescriptionIds").split(";");
    for (i = 0; i < tmpArray.length; i++) {
       for (j = 0 ; j < $v("useDescriptionCount")*1; j++) {
    	   if(tmpArray[i] == theDischargeSinkDrainId)
    		   $('dischargeSinkDrainId').value = theDischargeSinkDrainId;
    	   else if ($v("useDescription"+j) == tmpArray[i]) {
               $("useDescription"+j).checked = true;
               break;
           }
       }
    }    	
}

function preSelectSubstrate() {
    tmpArray = $v("selectedSubstrateIds").split(";");
    for (i = 0; i < tmpArray.length; i++) {
       for (j = 0 ; j < $v("substrateCount")*1; j++) {
           if ($v("substrate"+j) == tmpArray[i]) {
               $("substrate"+j).checked = true;
               break;
           }
       }
    }
}

function preSelectUseLocation() {
    tmpArray = $v("selectedUseLocationIds").split(";");
    for (i = 0; i < tmpArray.length; i++) {
       for (j = 0 ; j < $v("useLocationCount")*1; j++) {
           if ($v("useLocation"+j) == tmpArray[i]) {
               $("useLocation"+j).checked = true;
               //if booth is checked
               if ($v("useLocationBoothIndex")*1 == j) {
                    boothClicked(j);
               }
               break;
           }
       }
    }
}

function preSelectBuilding() {
    tmpArray = $v("selectedBuildingIds").split(";");
    for (i = 0; i < tmpArray.length; i++) {
       for (j = 0 ; j < $v("buildingCount")*1; j++) {
           if ($v("building"+j) == tmpArray[i]) {
               $("building"+j).checked = true;
               break;
           }
       }
    }
}

function preSelectBooth() {
    tmpArray = $v("selectedBoothIds").split(";");
    boothCount = 0;
    if($v('usageCategoryId') == 54462)
    	boothCount = $v('boothCountProd');
    else 	
    	boothCount = $v('boothCountNonProd');
    for (i = 0; i < tmpArray.length; i++) {
       for (j = 0 ; j < boothCount*1; j++) {
           if (showForProd) {
               if ($v("boothProduction"+j) == tmpArray[i]) {
                   $("boothProduction"+j).checked = true;
                   break;
               }
           }else {
              if ($v("boothNonProduction"+j) == tmpArray[i]) {
                $("boothNonProduction"+j).checked = true;
                break;
              }
           }
       }
    }
}

function getSelectedPurchasingMethodIds() {
    tempVal = "";
    for (i = 0; i < $v("purchasingMethodCount")*1; i++) {
        if ($("purchasingMethod"+i).checked) {
            tempVal += $v("purchasingMethod"+i)+";";
        }
    }
    $("selectedPurchasingMethodIds").value = tempVal;
}

function preSelectPurchasingMethod() {
    tmpArray = $v("selectedPurchasingMethodIds").split(";");
    for (i = 0; i < tmpArray.length; i++) {
       for (j = 0 ; j < $v("purchasingMethodCount")*1; j++) {
           if ($v("purchasingMethod"+j) == tmpArray[i]) {
               $("purchasingMethod"+j).checked = true;
               break;
           }
       }
    }
}

function matlThinnedChanged() {
    if($v("matlThinnedWhenUsed") == 'Y') {
        $("thinnedSpan").style["display"] = "";
    }else{
        $("thinnedSpan").style["display"] = "none";    
    }
}

function lookupCustomerMsdsNumber() {
    //not working correctly showTransitWin();
	approvalUseGroupId = '';
	localUsageSubcategoryIdVar = $v('usageSubcategoryId'); 
	
	localUsageSubCategory = altUsageSubCategory[$v('usageCategoryId')];
	for(var i = 0; i < localUsageSubCategory.length;i++)
		if(localUsageSubCategory[i].usageSubcategoryId == localUsageSubcategoryIdVar)
		{
			approvalUseGroupId = localUsageSubCategory[i].approvalUseGroupId;
			break;
		}
	
	var loc = "searchmsdsmain.do?uAction=search&calledFrom=catalogAddHmrb&searchText="+
              "&facilityId="+$v("facilityId")+"&companyId="+$v("companyId") +"&approvalUseGroupId="+approvalUseGroupId;
	var winId= 'searchForMsds'+$v("requestId");
	children[children.length] = openWinGeneric(loc,winId,"900","590","yes","50","50","20","20","no");
}

function customerMsdsNumberCallback(customerMsdsNumber) {
    $("thinnedCustomerMsdsNumber").value = customerMsdsNumber;
    //closeTransitWin();
}

function getProdorNonProdCount(idArray,showForProd) 
{
	var count = 0;
    	for (var i=0; i < idArray.length; i++) 
    		if(idArray.showForProd == showForProd)
    			++count;
    		else if (idArray.showForNonProd == showForProd)
    			++count;
    return count;
 
}