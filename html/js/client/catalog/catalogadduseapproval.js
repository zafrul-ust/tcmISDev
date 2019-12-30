var dhxWins = null;
var children = new Array();
var windowCloseOnEsc = true;
//this is used to help multiselect popup distinguish empty-value option with dropdown id when identifying selected options
var emptyOptionValRepresentation = "!@#$%^&*()EmptyStr";

function $(a) {
	return document.getElementById(a);
}

function editOnLoad(action) {
	closeTransitWin();
	if (action == 'submitUseApproval' && !showErrorMessage) {
		opener.reloadUseApproval();
		window.close();
	}else {
		if (showErrorMessage) {
			showMessageDialog();
		}
        loadApplicationUseGroup();
        loadWasteWaterDischarge();
        setViewLevel();
	}
}

function enableFieldsForSubmit() {
	var hasCatalogAddStorageTab = opener.document.getElementById('hasCatalogAddStorageTab').value;
	if(hasCatalogAddStorageTab == 'Y' && $v("workArea") != 'All')
	{
		var existing = opener.existingStorageCheck($v("workArea"));
		if (existing == null)
			{
				$("applicationDesc").value = $("workArea").options[$("workArea").selectedIndex].text;
				$("application").value = $("workArea").value;
				$("storageAction").value = "add";
				opener.addStorage($v("applicationDesc"),$v("application"));
			}
		else
			{
				$("applicationDesc").value = "";
				$("application").value = "";
				$("storageAction").value = "";
			}
	}
	else
	{
		$("applicationDesc").value = "";
		$("application").value = "";
		$("storageAction").value = "";
	}
	$("applicationUseGroupId").disabled = '';
    $("workArea").disabled = '';
}

function setViewLevel() {
    if ($v("hasApplicationUseGroup") == 'Y') {
        $("applicationUseGroupSpan").style["display"] = "";    
    }else {
        $("applicationUseGroupSpan").style["display"] = "none";
    }

    if ($v("calledFrom") == 'editWorkArea') {
        $("applicationUseGroupId").disabled = true;
        $("workArea").disabled = true;
	}
}

function closeThisWindow() {
	try {
		opener.closeTransitWin();
	}catch(e){}
}

function checkClose(action) {
	if( action != 'submitUseApproval' ) {
		closeAllchildren();
		closeThisWindow();
	}
}

function cancel() {
	window.close();
}

function submitUpdate() {
  if (auditData()) {
	  enableFieldsForSubmit();
	  showTransitWin();
	  $("uAction").value = 'submitUseApproval';
	  document.genericForm.submit();
  }else {
	  return false;
  }
}

function auditData() {
	var result = true;
	var missingFields = "";
    if (isEmptyV("applicationUseGroupId")) {
			missingFields += "\t"+messagesData.workAreaGroup+"\n";
	}
    if (isEmptyV("workArea")) {
			missingFields += "\t"+messagesData.workarea+"\n";
	}
	if (isEmptyV("processDesc")) {
			missingFields += "\t"+messagesData.useProcessDesc+"\n";
	}
	if (!isEmptyV("estimatedAnnualUsage")) {
		if (isNaN($v("estimatedAnnualUsage"))) {
			missingFields += "\t"+messagesData.estimateAnnualUsage+"\n";
		}
	}
	if (!isEmptyV("quantity1")) {
		if (isNaN($v("quantity1"))) {
			missingFields += "\t"+messagesData.restriction1+"\n";
		}
	}
	if (!isEmptyV("per1")) {
		if (isNaN($v("per1"))) {
			missingFields += "\t"+messagesData.restriction1+"\n";
		}
	}
	if (!isEmptyV("quantity2")) {
		if (isNaN($v("quantity2"))) {
			missingFields += "\t"+messagesData.restriction2+"\n";
		}
	}
	if (!isEmptyV("per2")) {
		if (isNaN($v("per2"))) {
			missingFields += "\t"+messagesData.restriction2+"\n";
		}
	}
	if (showWasteWaterDischarge)
		if (isEmptyV("wasteWaterDischarge")) {
			missingFields += "\t"+messagesData.wasteWaterDischarge+"\n";
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

function setDefaultInventoryGroup() {
    tmpIg = "";
	if ($v("selectedInventoryGroup") == null || $v("selectedInventoryGroup").length == 0) {
        var selectedApplicationUseGroup = $("applicationUseGroupId").value;
        if ($v("workArea") != 'All' && $v("workArea").length > 0 && selectedApplicationUseGroup.length > 0) {
            var applicationArray = altApplication[selectedApplicationUseGroup];
            for (i=0; i < applicationArray.length; i++) {
				if (applicationArray[i].application = $v("workArea")) {
					tmpIg = applicationArray[i].inventoryGroup;
					break;
				}
			}
			$("inventoryGroup").value = tmpIg;
		}else {
			$("inventoryGroup").selectedIndex = 0;
		}
	}else {
		$("inventoryGroup").value = $v("selectedInventoryGroup");
	}
    loadEmissionPoints();
}

function loadApplicationUseGroup() {
    var applicationUseGroupArray = altApplicationUseGroup;
    //clear current data
    var inv = $("applicationUseGroupId");
    for (var i = inv.length; i > 0; i--) {
        inv[i] = null;
    }
    //load new data
    var selectedIndex = 0;
    if( applicationUseGroupArray != null ) {
        var startingIndex = 0;
        if (applicationUseGroupArray.length == 0 || applicationUseGroupArray.length > 1) {
            setOption(0,messagesData.selectOne,"", "applicationUseGroupId");
            startingIndex = 1;
        }
        for (var i=0; i < applicationUseGroupArray.length; i++) {
            setOption(i+startingIndex,applicationUseGroupArray[i].applicationUseGroupName,applicationUseGroupArray[i].applicationUseGroupId, "applicationUseGroupId");
            if (applicationUseGroupArray[i].applicationUseGroupId == $v("selectedApplicationUseGroupId")) {
                selectedIndex = i+startingIndex;
            }
        }
    }else {
        setOption(0,messagesData.allGroups,"All", "applicationUseGroupId");
    }

    inv.selectedIndex = selectedIndex;
    applicationUseGroupChanged();
}

function applicationUseGroupChanged() {
    var selectedApplicationUseGroup = $("applicationUseGroupId").value;
    var applicationArray = altApplication[selectedApplicationUseGroup];

    var inv = $("workArea");
    for (var i = inv.length; i > 0; i--) {
        inv[i] = null;
    }

    var selectedIndex = 0;
    if( applicationArray != null ) {
        var startingIndex = 0;
        //if facility allow all work areas then add it the drop down option even though it has only one work area
        if ($v("allowAllApps") == 'Y') {
            if ($v("specificUseApprovalRequired") == 'Y') {
                setOption(0,messagesData.allExceptControlled,"All", "workArea");
            }else {
                setOption(0,messagesData.allWorkAreas,"All", "workArea");
            }
            startingIndex = 1;
        }else {
            setOption(0,messagesData.selectOne,"", "workArea");
            startingIndex = 1;
        }
        for (var i=0; i < applicationArray.length; i++) {
            setOption(i+startingIndex,applicationArray[i].applicationDesc,applicationArray[i].application, "workArea");
            if (applicationArray[i].application == $v("selectedWorkArea")) {
                selectedIndex = i+startingIndex;
            }
        }
    }else {
        if ($v("specificUseApprovalRequired") == 'Y') {
            setOption(0,messagesData.allExceptControlled,"All", "workArea");
        }else {
            setOption(0,messagesData.allWorkAreas,"All", "workArea");
        }
    }
    inv.selectedIndex = selectedIndex;
    setDefaultInventoryGroup();
}

function buildDropDown(arr, eleName, selectedId) {
	var obj = $(eleName);
	for (var i = obj.length; i > 0; i--)
		obj[i] = null;
	// if arr has only 1 value or less excluding default values, don't show
	// defaults
	var selectedPos = 0;
	var start = 0;
	var offset = 0;
	if (eleName == 'emissionPointsSel') {
		setOption(start++, '', emptyOptionValRepresentation, eleName);
		offset = $(eleName).options.length;
	}
	
	if (arr == null || arr.length == 0) {
		if (eleName != 'emissionPointsSel')
			setOption(start, messagesData.selectOne, '', eleName);
	} else {
		for (var i = 0; i < arr.length; i++) {
			setOption(start++, arr[i].name, arr[i].id, eleName);
			if (selectedId != null && arr[i].id == selectedId)
				selectedPos = i + offset;
		}
	}
	obj.selectedIndex = selectedPos;
}

function loadWasteWaterDischarge() {
	if (showWasteWaterDischarge)
		buildDropDown(wasteWaterDischargeColl, "wasteWaterDischarge", $v("selectedWasteWaterDischarge"));
}

function loadEmissionPoints() {
	if (showEmissionPoints) {
		var availableEmissionPoints = emissionPointsColl[$("workArea").options[$("workArea").selectedIndex].value];
		$("emissionPointsMultiSel").style['display'] = '';
		
		//since the multiselect popup use the dropdown to build its grid, this needs to be setup beforehand
		buildDropDown(availableEmissionPoints, "emissionPointsSel", $v("selectedEmissionPoints"));
		//if adding new work area or the stored value for emission point is just one
		if (isEmptyV("selectedEmissionPoints") 
				|| $v("selectedEmissionPoints").indexOf($v("emissionPointIdSeparator") + $v("emissionPointIdSeparator")) == -1
				|| availableEmissionPoints == null) {
			$("emissionPointsSel").style['display'] = '';
			$('emissionPointsMultiSelTxt').style['display'] = 'none';
			if (availableEmissionPoints == null || availableEmissionPoints.length == 0)
				$('emissionPointsMultiSel').style['display'] = 'none';
			
			emissionPointsChanged();
		} else {
			$("emissionPointsSel").style['display'] = 'none';
			var multiSelTxt = $('emissionPointsMultiSelTxt');
			multiSelTxt.style['display'] = '';
			
			var displayStr = '';
			var storedVals = '';
			var emissionPointsIdSeparator = $v("emissionPointIdSeparator") + $v("emissionPointIdSeparator");
			var selectedEmissionPoints = $v("selectedEmissionPoints").split(emissionPointsIdSeparator);
			clearElements("emissionPoints");
			for (var i = 0; i < selectedEmissionPoints.length; i++) {
				for (var j = 0; j < availableEmissionPoints.length; j++) 
					if (selectedEmissionPoints[i] == availableEmissionPoints[j].id) {
						createElement("emissionPoints", availableEmissionPoints[j].id);
						displayStr += availableEmissionPoints[j].name;
						storedVals += availableEmissionPoints[j].id;
						if (i != availableEmissionPoints.length - 1) {
							displayStr += "; ";
							storedVals += emissionPointsIdSeparator;
						}
						break;
					}
			}
			
			if (displayStr.trim().length == 0) {
				alert(messagesData.genericError);
				return;
			}
			
			multiSelTxt.value = displayStr;
			multiSelTxt.title = displayStr.replace(/; /g, "\n");
			$("emissionPoints").value = storedVals;
		}
	}
}

/* called when emissionPoints dropdown is changed */
function emissionPointsChanged() {
	var emissionPointsSel = $("emissionPointsSel");
	//if the function is somehow called while the dropdown is not displayed, do nothing
	if (emissionPointsSel.style['display'] == 'none') {
		return;
	} else {
		clearElements("emissionPoints");
		createElement("emissionPoints", emissionPointsSel.value);
		$("emissionPoints").value = emissionPointsSel.value;
	}
}

function popMultiSel(dropDownId) {
	children[children.length] = openWinGeneric("adhocinventoryreport.do?uAction=multiSelect&dropDownId=" + dropDownId, "edit_field_list", "900", "500", "yes");
	showTransitWin();
}

/*used by multi select popup to know which option is selected*/
function createElement(which, id) {
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("id", which + id);
	var span = document.getElementById(which + 'Span');
	span.appendChild(input);
}

/*used by multi select popup*/
function clearElements(which) {
	var span = document.getElementById(which + 'Span');
	span.innerHTML = '';
}

/*used by multi select popup to return data*/
function multiSelRet(retArr,which) {
	//either users choose only one or one of the 2 selected options is empty string
	if(retArr.length < 2 || (retArr.length == 2 && retArr[0].split('#^%&*!$@')[1] == emptyOptionValRepresentation)) {
		$(which + 'MultiSelTxt').style['display'] = 'none';
		var selectBox = $(which + 'Sel');
		selectBox.style['display'] = '';
		if (retArr.length > 0) {
			var selected = retArr.length == 2 && retArr[0].split('#^%&*!$@')[1] == emptyOptionValRepresentation ? retArr[1].split('#^%&*!$@')[1] : retArr[0].split('#^%&*!$@')[1];
			var options = selectBox.options;
			for (var i = 0; i < options.length; i++)
				if (options[i].value == selected) {
					selectBox.selectedIndex = i;
					break;
				}
		} else
			selectBox.selectedIndex = 0;
		if(which == 'emissionPoints') {
			emissionPointsChanged();
		}
	} else {
		$(which + 'Sel').style['display'] = 'none';
		var multiSelTxt = $(which + 'MultiSelTxt');
		multiSelTxt.style['display'] = '';
		
		var displayStr = '';
		var storedVals = '';
		for (var i = 0; i < retArr.length; i++) {
			var selectedArr = retArr[i].split('#^%&*!$@');
			if (selectedArr[1] != emptyOptionValRepresentation) {
				displayStr += selectedArr[0];
				storedVals += selectedArr[1];
				if (i != retArr.length - 1) {
					displayStr += "; ";
					if (which == 'emissionPoints')
						storedVals += $v("emissionPointIdSeparator") + $v("emissionPointIdSeparator");
					else
						storedVals += "|";
				}
			} else if (which == "emissionPoints") //remove reference to empty-value option even if it is checked, since there are other options selected
				document.getElementById("emissionPointsSpan").removeChild(document.getElementById("emissionPoints" + emptyOptionValRepresentation));
		}
		multiSelTxt.value = displayStr;
		multiSelTxt.title = displayStr.replace(/; /g, "\n");
		$(which).value = storedVals;
	}
}