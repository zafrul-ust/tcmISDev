var testResultGrid;
var labTestReceiptGrid;
var selectedReceiptRowId;

var createTestDefintionOnLoad = function () {
	var that = this;

	that.createNewTestDefinition = function(){
		var isValid = validateTestRequestDefinition(),
			action = document.getElementById("uAction");

		if(isValid){
			action.value = "create";
			document.genericForm.submit();
		}
	};

	that.validateTestRequestDefinition = function(){
		var company = document.getElementById("customerId"),
			facility = document.getElementById("facilityId"),
			description = document.getElementById("testDescription"),
			criteria = document.getElementById("testCriteria"),
			companyId,
			facilityId,
			descriptionText,
			criteriaText;

		companyId = company.value;
		facilityId = facility.value;
		descriptionText = description.value;
		criteriaText = criteria.value,
		errorMsg = "";

		if(isEmptyString(descriptionText)){
			errorMsg += description.name + ' ' + messagesData.errorEmptyValue + '\n';
		}

		if(isEmptyString(criteriaText)){
			errorMsg += criteria.name + ' ' + messagesData.errorEmptyValue + '\n';
		}

		return reportError(errorMsg);
	};
};

var searchTestRequestsOnLoad = function(){
	var that = this;	

	that.submitSearchForm = function(){
		var isValid = validateSearchValues(),
			now = new Date(), // Capture the current time
			action = document.getElementById("uAction");
		
		document.getElementById("startSearchTime").value = now.getTime();
		if(isValid) {	
			action.value = "search";			
			document.genericForm.target='resultFrame';	   
			showPleaseWait();	  
			document.genericForm.submit();
		}
		else{
			return false;
		}
	};
	
	that.validateSearchValues = function(){
		var errorMsg = "";

		// If a "days in lab" argument was provided it has to be a whole number of days.
		if(document.getElementById("labAge").value != "" && !isInteger(document.getElementById("labAge").value, true)) {
			errorMsg += messagesData.daysInLabMustBeInteger + "\n\n";
		}
		var searchArg = document.getElementById("searchArgument").value;
		if(searchArg != ""){
			var searchField =  document.getElementById("searchField").value;
			if(searchField == "testRequestId"  && !isFloat(searchArg, true)) {
				errorMsg += messagesData.mustBeANumber.replace(/[{]0[}]/g,messagesData.testRequest);
			}
			else if(searchField == "itemId"  && !isInteger(searchArg, true)) {
				errorMsg += messagesData.integerErr.replace(/[{]0[}]/g,messagesData.itemId);
			}
			else if(searchField == "receiptId"  && !isInteger(searchArg, true)) {
				errorMsg += messagesData.integerErr.replace(/[{]0[}]/g,messagesData.receiptId);
			}
		}
		return reportError(errorMsg);
	};
	
	that.generateExcel = function(windowTitle){
		if(validateSearchValues()) {	
		var action = document.getElementById("uAction");
		action.value = 'createExcel';
		openWinGenericExcel('/tcmIS/common/loadingfile.jsp',windowTitle,'650','600','yes');
		document.genericForm.target=windowTitle;
		var a = window.setTimeout("document.genericForm.submit();",500);
		}
		else{
			return false;
		}
	};
};

var reportError = function(errorMsg){
	if (errorMsg.length > 1) {
		alert(errorMsg);
		return false;
	}
	return true;
};

function doOnRowSelected(rowId,cellInd) {
	 var columnId = testResultGrid.getColumnId(cellInd);  
	 selectedRowId = rowId;
}

function receiptOnRowSelect() {
    rightClick = false;
    rowId0 = arguments[0];
    colId0 = arguments[1];
    ee     = arguments[2];
    selectedReceiptRowId = rowId0;
    if( ee != null ) {
        if( ee.button != null && ee.button == 2 ) rightClick = true;
        else if( ee.which == 3  ) rightClick = true;
    }
    columnId = labTestReceiptGrid.getColumnId(colId0);

    if (rightClick) {
        toggleContextMenu('receiptMenu');
    }
}


var testRequestEntryOnLoad = function(){
	var that = this;

    //create tabs
    openPageTab();
    //initialize test grid
    initGridWithGlobal(testResultGridConfig);
    //initialize receipt grid
    initGridWithGlobal(labTestReceiptGridConfig);

    if (selectedTabId > 0) {
		togglePage(0);
	}


	var commonSubmit = function(action){
		var uAction = document.getElementById("uAction");
		uAction.value = action;
		showPleaseWait();
		if(testResultGrid != null){
		testResultGrid.parentFormOnSubmit();}
		document.genericForm.submit();
	};

	that.submitUpdateTestRequest = function(){	
		var isValid = true;
		
		if (isValid){
			commonSubmit("update");
			
		}
	};

	that.signTestRequest = function(){
		var isValid = validateTestRequestFormValues();
		
		if(isValid){
			commonSubmit("sign");
		}
		
	};
	
	that.cancelTestRequest = function(){
		var isValid = true;
		
		if(isValid){
			commonSubmit("cancel");
		}
	};
	
	that.validateTestRequestFormValues = function() {
		// Validate sample size
		var sampleSize = document.getElementById("sampleSize"),
			requestStatus = document.getElementById("requestStatus"),
			statusValue = requestStatus.value,
			matlQualified = document.getElementById("materialQualified"),
			totalLines = document.getElementById("totalLines"),
			expiration = document.getElementById("expirationDate"),
			ddlPass, errorMsg = "";
		
		if(!isEmptyString(statusValue)){
			if("New" === statusValue){
				if(isEmptyString(sampleSize.value)){
					errorMsg +=  messagesData.sampleSizeLabel + ' - ' + messagesData.errorEmptyValue + '\n';
				}
			} else if ("In Test" === statusValue){
				if(isEmptyString(matlQualified.value)){
					errorMsg += messagesData.materialqualified + ' - ' + messagesData.errorEmptyValue + '\n';
				}
				
				if(!isEmptyString(errorMsg)){
				return reportError(errorMsg);}
				
				if(testResultGrid != null){
					var rowsNum = testResultGrid.getRowsNum();
	
				    for (var p = 1; p < (rowsNum+1) ; p ++)
				    {
				    	if (validateLine(p) == false) {
				    		testResultGrid.selectRowById(p,null,false,false);
							return false;
						}
				    }
				}

			    return true;
			}
		}
		
		return reportError(errorMsg);
	};

	//display error message to user if any
    if (showErrorMessage) {
        alert($v("errorMsg"));
    }
}

function validateLine(rowId) {
	var errorMessage = messagesData.validvalues+"\n\n";
	var count = 0;
	
	var passFail = testResultGrid.cells(rowId,testResultGrid.getColIndexById("passFail")).getValue()
  	if (passFail.trim() == "") {
  	   errorMessage += messagesData.testresults;
       count=1;
  	}
  	
  	if(count >0 ) {
	  	   alert(errorMessage);
		   return false;
	}
	return true;
}

var changeDisplay = function(divId, displayType){
	var node = document.getElementById(divId);
	node.style.display = displayType;
};

var toggleVisibility = function(visibilityConfig){
	var i, j;
	
	i = visibilityConfig.on.length;
	j = visibilityConfig.off.length;
	for(;i--;){
		changeDisplay(visibilityConfig.on[i], "");
	}
	for(;j--;){
		changeDisplay(visibilityConfig.off[j], "none");
	}
};

var reportError = function(errorMsg){
	if (errorMsg.length > 1) {
		alert(errorMsg);
		return false;
	}
	return true;
};

var isEmptyString = function(testValue){
	if (testValue === null || testValue === undefined){
		return true;
	}
	if (typeof(testValue) === 'string'){
		return testValue.trim() === '';
	}	
};

var limitText = function (limitField, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} 
};

function expiredDateChanged() {
    $("indefiniteDate").value = "01-" + month_abbrev_Locale_Java[pageLocale][0]
		       + "-3000";

    if ($v("expireDate") == messagesData.indefinite) {
	   $("expirationDate").value = $v("indefiniteDate");
     } else {
	$("expirationDate").value = $v("expireDate");
    }
    
}   

function showReceiptDocuments(receiptId, inventoryGroup) {
    var loc = "/tcmIS/hub/receiptdocuments.do?receiptId="+labTestReceiptGrid.cells(selectedReceiptRowId,labTestReceiptGrid.getColIndexById("receiptId")).getValue()+
              "&showDocuments=Yes&inventoryGroup="+labTestReceiptGrid.cells(selectedReceiptRowId,labTestReceiptGrid.getColIndexById("inventoryGroup")).getValue()+"";
    try {
        children[children.length] = openWinGeneric(loc,"showReceiptDocuments","800","600","no","80","80");
    } catch (ex){
        openWinGeneric(loc,"showReceiptDocuments","800","600","no","80","80");
    }
}
 

var tabDataJson = new Array();
var selectedTabId = 0;

function openPageTab() {
    var tabCount = 0;
    openTab(tabCount,'',messagesData.testresults,'','');
	tabCount += 1;
    openTab(tabCount,'',messagesData.receipt,'','');
}

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

function togglePage(tabId) {
 	if (selectedTabId != tabId) {
        for (var i=0; i<tabDataJson.length; i++) {
			 var tabLink =  document.getElementById(tabDataJson[i].tabId+"Link");
			 var tabLinkSpan =  document.getElementById(tabDataJson[i].tabId+"LinkSpan");
			 if (tabDataJson[i].tabId == tabId && tabDataJson[i].status == "open")
			 {
				setVisible(tabDataJson[i].tabId, true);
				tabLink.className = "selectedTab";
				tabLink.style["display"] = "";

				tabLinkSpan.className = "selectedSpanTab";
				tabLinkSpan.style["display"] = "";
				selectedTabId = tabId;
			 }else {
				setVisible(tabDataJson[i].tabId, false);
				tabLink.className = "tabLeftSide";
				tabLinkSpan.className = "tabRightSide";
			 }
  		}
	}else {
		var tabLink =  document.getElementById(selectedTabId+"Link");
		tabLink.style["display"] = "";
		var tabLinkSpan =  document.getElementById(selectedTabId+"LinkSpan");
		tabLinkSpan.style["display"] = "";

		setVisible(tabId, true);
  	}
  	setTimeout('toggleContextMenuToNormal()',50);
}

function setVisible(tabId, yesno)
{
    try
    {
        var target =  document.getElementById("newTab"+tabId+"");
        if (yesno)
        {
         target.style["display"] = "";

        }
        else
        {
          target.style["display"] = "none";
        }
    }
    catch (ex)
    {
      alert("Here exception in setVisible");
    }
}

function showFacility(oldFacility) {
	var idArray = altFacilityId;
	var nameArray = altFacilityName;
	var selectI = 0;
	var inserted = 0;

	if( nameArray != null && nameArray.length == 1 ) 
	  	$("facilityId").value = idArray[0];
	else
		for (var i=1; i < nameArray.length; i++) 
		{
			if( nameArray.length != 2 || i == 1 ) {
				setOption(inserted,nameArray[i],idArray[i], "facilityId");
			    if( oldFacility == idArray[i] ) 
			    	selectI = inserted;
			    inserted++;
			}
		}
	if( inserted == 0 )
    	setOption(inserted,messagesData.myfacilities,"", "facilityId");
  	$("facilityId").selectedIndex = selectI;
  	$("facility").value = $("facilityId")[$("facilityId").selectedIndex].text;
}

function setFacility() {	
	 var oldinv =  $("oldfacilityId").value;
	 try {
		 showFacility(oldinv);		 
	 } catch (ex) {}
}

function facilityChanged() {
	$("facility").value = $("facilityId")[$("facilityId").selectedIndex].text;
}