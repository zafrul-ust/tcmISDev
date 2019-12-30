var dhxWins = null;
var children = new Array();
resizeGridWithWindow=true;

function $(a) {
	return document.getElementById(a);
}

function callOnChangeFunction() {
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

        $('msdsDataDiv').style["display"]="";
		initializeMsdsGrid();

        if (hasHmrb) {
            $('hmrbDataDiv').style["display"]="";
		    initializeHmrbGrid();
        }

		//preselect the the first tab
		if (selectedTabId > 0) {
			togglePage(0);
		}

		setViewLevel();

		if (showErrorMessage) {
			showMessageDialog(messagesData.errors,errorMessage,false,"editOnLoad");
		}
		resizeWindowSizes();
	}
}

//start resubmit
function showResubmitCatalogAddRequestScreen() {
    var requestId  =  $v("requestId");
    if ( requestId != null &&  requestId != 0) {
        var loc = "catalogaddmsdsrequest.do?uAction=view&requestId="+requestId;
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

/*Calculates the height and width. I only use the width though*/
function resizeWindowSizes() {
	setWindowSizes();
    resetGridSize('msdsDataDiv', msdsBeanGrid);
    if (hasHmrb) {
        resetGridSize('hmrbDataDiv', hmrbBeanGrid);
        //this method will fit all columns on given screen for grid with few columns
        reSizeCoLumnWidths(hmrbBeanGrid);
    }
}

function resetGridSize(divName, theGrid, extraMargin) {
	var fudgeFactor= 10 + (extraMargin ? extraMargin : 0);
	
	try {
		$(divName).style["width"]= myWidth-35 + "px";
		$(divName).style["height"]= myHeight-230 + "px";
	}catch(ex) { 
	}

	try {
		var griDiv = $(theGrid.entBox.id);
		griDiv.style.height = myHeight-(230 + fudgeFactor) + "px";
	}catch(ex) {
		alert(ex);
	}
    //the reason that I commented out below is because it put the entire grid display on screen
    //and look looks off when the grid has many columns (i.e. msds)
    //reSizeCoLumnWidths(theGrid);
}


function closeThisWindow() {
	try {
	}catch(e){}
}

function cancel() {
	window.close();
}

function submitUpdate() {
  if (auditSaveData() && auditData()) {
	  showTransitWin();
      msdsBeanGrid.parentFormOnSubmit();
      enableFieldsForFormSubmit();
	  $("uAction").value = 'submit';
	  document.genericForm.submit();
  }else {
	  setViewLevel();
	  return false;
  }
}

function saveData() {
	if (auditSaveData()) {
		showTransitWin();
        msdsBeanGrid.parentFormOnSubmit();
        enableFieldsForFormSubmit();
		$("uAction").value = 'save';
		document.genericForm.submit();
	}
}

function deleteRequest() {
	showTransitWin();
	enableFieldsForFormSubmit();
	$("uAction").value = 'deleteRequest';
	document.genericForm.submit();
}

function submitApproveForm() {
	if (auditSaveData() && auditData()) {
        msdsBeanGrid.parentFormOnSubmit();
        enableFieldsForFormSubmit();
		showApprovalRoleWin();
	}else {
		setViewLevel();
	}
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

function requestHasAtLeastOneApprovalCode() {
	var result = false;
	//NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
	for(var i = 1; i <= hmrbBeanGrid.getRowsNum();i++) {
		if (hmrbBeanGrid.cells(i,hmrbBeanGrid.getColIndexById("dataSource")).getValue() == 'new') {
			result = true;
			break;
		}
	}
	return result;
}

function auditData() {
	var result = true;
	var missingFields = "";
    var missingData = "";
    var perVolWeightCheck = "";

    //make sure there is at least one MSDS record for request
    if (msdsBeanGrid.getRowsNum() == 0) {
        missingData += messagesData.requestMissingMsds+"\n";    
    }

    //if new MSDS
    if ($v("requestStartingView") == 6) {
        //if kit
        if (msdsBeanGrid.getRowsNum() > 1) {
            //NOTE THAT GRID STARTS WITH 1 AND NOT 0 (ZERO)
            var errorFound = false;
            var percentAmount = 0;

            for(var i = 1; i <= msdsBeanGrid.getRowsNum();i++) {
            	
                //if not mix ratio units, do vv table amount and size unit checks
                if(showPerVolWeight)
                {		              
		              var tmpPartSize = msdsBeanGrid.cells(i,msdsBeanGrid.getColIndexById("partSize")).getValue().trim();
		              if(!isNaN(tmpPartSize))
		            	  percentAmount += parseFloat(tmpPartSize);
                }
                else
                {
	                tmpPartSize = msdsBeanGrid.cells(i,msdsBeanGrid.getColIndexById("partSize")).getValue().trim();
	                if (tmpPartSize.length == 0 && $v("mixRatioRequired") == 'Y') {
	                    missingFields += "\t"+messagesData.size+"\n";
	                    errorFound = true;
	                }else {
	                    if (isNaN(tmpPartSize)) {
	                        missingFields += "\t"+messagesData.size+"\n";
                            errorFound = true;
                        }
	                }
            	}
                
                if (($v("requireCustomerMsds") == 'Y') || ($v("catAddRequestorEditMsdsId") == "MANDATORY")) {
                    if (msdsBeanGrid.cells(i,msdsBeanGrid.getColIndexById("customerMsdsNumber")).getValue().trim().length == 0) {
                        missingFields += "\t"+messagesData.customerMsdsNumber+"\n";
                        errorFound = true;    
                    }
                }

				//customer manufacturer ID
				if (msdsBeanGrid.cells(i, msdsBeanGrid.getColIndexById("customerMfgIdPermission")).getValue() == 'Y') {
					if (isWhitespace(msdsBeanGrid.cells(i,msdsBeanGrid.getColIndexById("customerMfgId")).getValue()) &&
						isWhitespace(msdsBeanGrid.cells(i,msdsBeanGrid.getColIndexById("customerMfgIdDisplay")).getValue())) {
						missingFields += "\t"+messagesData.customerMfgId+"\n";
						errorFound = true;
					}
				}

                //stop if found error in grid
                if (errorFound) {
                    break;
                }
            } //end of for each row loop

            if(showPerVolWeight) {
            	if(percentAmount != 100)
            		perVolWeightCheck += "\t"+messagesData.percentVolWeight+"\n\n";
            }
            if( msdsBeanGrid.cells(1,msdsBeanGrid.getColIndexById("sizeUnit")).getValue().trim() == '' && $v("mixRatioRequired") == 'Y')
            		perVolWeightCheck += "\t"+messagesData.percentVolWeightUnitCount+"\n\n";
        }else {
            //single component
        	if (($v("requireCustomerMsds") == 'Y') || ($v("catAddRequestorEditMsdsId") == "MANDATORY")) {
                if (msdsBeanGrid.cells(1,msdsBeanGrid.getColIndexById("customerMsdsNumber")).getValue().trim().length == 0) {
                    missingFields += "\t"+messagesData.customerMsdsNumber+"\n";
                }
            }
			//customer manufacturer ID
			if (msdsBeanGrid.cells(1, msdsBeanGrid.getColIndexById("customerMfgIdPermission")).getValue() == 'Y') {
				if (isWhitespace(msdsBeanGrid.cells(1,msdsBeanGrid.getColIndexById("customerMfgId")).getValue()) &&
					isWhitespace(msdsBeanGrid.cells(1,msdsBeanGrid.getColIndexById("customerMfgIdDisplay")).getValue())) {
					missingFields += "\t"+messagesData.customerMfgId+"\n";
				}
			}
        }
    }

    //make sure that there is at least one Approval code for this request
    if (hasHmrb && ($v("requestStartingView") == 6 || $v("requestStartingView") == 7)) {
        if (!requestHasAtLeastOneApprovalCode()) {
            missingData += messagesData.requestMissingApprovalCode+"\n";
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
    else if(perVolWeightCheck.length > 0)
    {
        alert("\n"+perVolWeightCheck);
		result = false;
    }
	return result;
}

function auditReplaceMsds() {
	var validMsds = true;
	for(var i = 1; i <= msdsBeanGrid.getRowsNum();i++) {
		var replaceMsds = msdsBeanGrid.cells(i,msdsBeanGrid.getColIndexById("replacesMsds")).getValue();
		var replaceMsdsValidator = msdsBeanGrid.cells(i,msdsBeanGrid.getColIndexById("replacesMsdsValidator")).getValue();
		
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

function auditSaveData() {
	var result = true;
	var missingFields = "";

    if ($v("poundsPerUnit").trim().length > 0) {
        if (isNaN($v("poundsPerUnit"))) {
            missingFields += "\t"+messagesData.poundsPerUnit+"\n";
        }
    }

    if ($v("partSize") != null) {
		if ($v("partSize").length > 0) {
			if (isNaN($v("partSize"))) {
				missingFields += "\t"+messagesData.size+"\n";
			}
		}
	}
	
	if (missingFields.length > 0) {
		alert(messagesData.validvalues+missingFields);
		result = false;
	}
	if (!auditReplaceMsds()) {
		alert(messagesData.invalidReplaceMsds);
		result = false;
	}
	return result;
}

function enableFieldsForFormSubmit() {
    setHeaderViewLevel('');
    setAdditionalCommentLevel('');
}

var viewLevel = "view";
function setViewLevel() {
	if ($("requestStatus").value == 7 || $("requestStatus").value == 9 || $("requestStatus").value == 12) {
		viewLevel = "view";
		setRequestActionLevel();
    }else if ($("requestStatus").value == 14 || $("requestStatus").value == 15 || $("requestStatus").value == 17) {
		//in draft status, only requestor can edit data
		if ($("personnelId").value == $("requestor").value || $("personnelId").value == $("resubmitRequestor").value) {
			viewLevel = "requestor";
			setRequestActionLevel();
        }
	}else {
		var userIsAnApprover = false;
		for (var i = 0; i < altApproversList.length; i++) {
			if (altApproversList[i] == $("personnelId").value) {
				userIsAnApprover = true;
				break;
			}
		}
		if (userIsAnApprover) {
			viewLevel = "approver";
		}else {
			viewLevel = "view";
		}
        setRequestActionLevel();
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

        //MSDS
        setMsdsActionViewLevel();
        setAdditionalCommentLevel('');
        //HMRB
        if (hasHmrb) {
            $("hmrbActionSpan").style["display"] = "";
		    setHmrbActionViewLevel();   
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

        //MSDS
        setMsdsActionViewLevel();
        setAdditionalCommentLevel('');
        //HMRB
        if (hasHmrb) {
            $("hmrbActionSpan").style["display"] = "";
		    setHmrbActionViewLevel();
        }
	}else {   //viewLevel == view
		if (($v("requestStatus")*1 > 4 && $v("requestStatus")*1 < 14) || $v("requestStatus")*1 == 16) {
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
        //kit MSDS
        setKitMsdsViewLevel();
        setAdditionalCommentLevel(true);
    }
}

function prepareForTransitWin() {
	$("closeTransitWinflag").value="Y";
	showTransitWin();
}

function setHeaderViewLevel(flag) {
    $("unitOfMeasure").disabled = flag;
    $("poundsPerUnit").disabled = flag;

}

function setAdditionalCommentLevel(flag) {
    $("messageToApprovers").disabled = flag;
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
	/*If the page being toggled is already closed, ignore the toggle.
  	  This can happen when they click the x on the taab.
 	for (var i=0; i<tabDataJson.length; i++) {
   	if (tabDataJson[i].tabId == tabId) {
    		if (tabDataJson[i].status == "closed") {
      		return;
    		}
   	}
 	}
   */

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

var hmrbSelectedRowId = null;
function initializeHmrbGrid(){
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
		if (viewLevel == 'requestor' || viewLevel == 'approver') {
			menuItems[menuItems.length] = 'text='+messagesData.editCopy+';url=javascript:copyHmrbData();';
		}
        /*
        if (setUpdateExpirationPermission) {
            menuItems[menuItems.length] = 'text='+messagesData.setUpdateExpiration+';url=javascript:setUpdateHmrbExpiration();';
        }
        */
    }
    //by this point if menu is empty then don't show anything
	if (menuItems.length == 0) {
		menuItems[menuItems.length] = '';
	}
	replaceMenu('hmrbRightClickMenu',menuItems);
	toggleContextMenu('hmrbRightClickMenu');
} //end of method

function addHmrb() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=addHmrb&requestId='+$v("requestId")+'&calledFrom=catalogAddMsdsRequest'+
			  '&catalogCompanyId='+$v("catalogCompanyId")+'&catalogId='+$v("catalogId")+
			  '&companyId='+$v("companyId")+'&facilityId='+$v("engEvalFacilityId");
	children[children.length] = openWinGeneric(url,"catalogAddHmrb",1000,700,'yes' );
}

function reloadHmrbData(tmpHmrbJsonMainData, closeTransitWinflag) {
	hmrbJsonMainData = tmpHmrbJsonMainData;
	hmrbSelectedRowId = null;
	initializeHmrbGrid();
    setHmrbActionViewLevel();
    resetGridSize('hmrbDataDiv', hmrbBeanGrid);
    closeTransitWin();
}

function editHmrbData() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=editHmrb&requestId='+$v("requestId")+
              '&hmrbLineItem='+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
              '&calledFrom=catalogAddMsdsRequest&catalogCompanyId='+$v("catalogCompanyId")+'&catalogId='+$v("catalogId")+
			  '&companyId='+$v("companyId")+'&facilityId='+$v("engEvalFacilityId");
	children[children.length] = openWinGeneric(url,"catalogAddHmrb",1000,700,'yes' );
}

function copyHmrbData() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=copyHmrb&requestId='+$v("requestId")+
              '&hmrbLineItem='+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
              '&calledFrom=catalogAddMsdsRequest'+'&catalogCompanyId='+$v("catalogCompanyId")+'&catalogId='+$v("catalogId")+
			  '&companyId='+$v("companyId")+'&facilityId='+$v("engEvalFacilityId");
	children[children.length] = openWinGeneric(url,"catalogAddHmrb",1000,700,'yes' );
}

function viewHmrbData() {
	showTransitWin();
	var url = 'catalogaddrequest.do?uAction=viewHmrb&requestId='+$v("requestId")+
              '&hmrbLineItem='+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
              '&calledFrom=viewHmrb&catalogCompanyId='+$v("catalogCompanyId")+'&catalogId='+$v("catalogId")+
			  '&companyId='+$v("companyId")+'&facilityId='+$v("engEvalFacilityId");
	children[children.length] = openWinGeneric(url,"catalogAddHmrb",1000,700,'yes' );
}

function removeHmrbSelectedLine() {
	showTransitWin();
    callToServer("catalogaddrequest.do?uAction=deleteHmrbLine&requestId="+$v("requestId")+
                 "&lineItem="+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
                 "&calledFrom=catalogAddMsdsRequest&setUpdateExpirationPermission="+$v("setUpdateExpirationPermission"));
}

function reloadHmrb() {
	showTransitWin();
	callToServer("catalogaddrequest.do?uAction=reloadHmrb&requestId="+$v("requestId")+"&engEvalFacilityId="+$v("engEvalFacilityId")+
                 "&companyId="+$v("companyId")+"&calledFrom=catalogAddMsdsRequest&setUpdateExpirationPermission="+$v("setUpdateExpirationPermission"));
}

function setHmrbActionViewLevel() {
    //limit user to just 1 new HRMB record per catalog add request
    if (!requestHasAtLeastOneApprovalCode() || $v("allowMultipleHmrb") == 'Y') {
        hmrbActionText = '<a href="#" onclick="addHmrb(); return false;">'+messagesData.addApprovalCode+'</a>';
        $("hmrbActionSpan").innerHTML = hmrbActionText+'<br>&nbsp;';
    }else {
        $("hmrbActionSpan").innerHTML = '<br>&nbsp;';
    }
}

//MSDS section
var msdsSelectedRowId = null;
var msdsPreClass = null;
var msdsPreRowId = null;
function initializeMsdsGrid(){
	msdsBeanGrid = new dhtmlXGridObject('msdsDataDiv');
	//initialize grid
	initGridWithConfig(msdsBeanGrid,msdsConfig,true,true);
	//setting smart rendering
	msdsBeanGrid.enableSmartRendering(true);
	msdsBeanGrid._haas_row_span = true;
	msdsBeanGrid._haas_row_span_map = lineMap;
	msdsBeanGrid._haas_row_span_class_map = lineMap3;
    if (showPerVolWeight)
        msdsBeanGrid._haas_row_span_cols = [0,1,2,3,4,5,7,9,11];
    else
        msdsBeanGrid._haas_row_span_cols = [0,1,2,3,4,5,7,11];
    msdsBeanGrid._haas_row_span_child_select = true;
    //parse data
	if( typeof( msdsJsonMainData ) != 'undefined' ) {
   	msdsBeanGrid.parse(msdsJsonMainData,"json");
 	}
	msdsBeanGrid.attachEvent("onRowSelect",msdsSelectRow);
	msdsBeanGrid.attachEvent("onRightClick",msdsSelectRow);
	msdsBeanGrid.attachEvent("onAfterHaasRenderRow", bindACkeys);
}

function bindACkeys(idx) {
	var replacesMsds = "replacesMsds";
	var replacesMsdsValidator = "replacesMsdsValidator";
	// check the flag set in qplBind so that the function is not called every time the row renders
	if (j$("#" + replacesMsds + idx).data("boundToKeystroke") !== true) {
		msdsBind(idx, replacesMsds, replacesMsdsValidator);
	}
}

function msdsBind(rowId, el1, el2) {
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
	
	j$("#" + el1 + rowId).bind('keyup',(function(e) {
		var keyCode = (e.keyCode ? e.keyCode : e.which);
		
		if (keyCode != 13 /* Enter */ && keyCode != 9 /* Tab */) {
			invalidateRequestor(rowId, el1, el2);
		}
	}));
	// set a flag so that this function does not get called every time the row renders
	j$("#" + el1 + rowId).data("boundToKeystroke", true);
	
	j$("#" + el1 + rowId).result(log).next().click(function() {
		j$(this).prev().search();
	});
		
	});
}

function invalidateRequestor(rowId, el1, el2) {
	var requestorName = document.getElementById(el1 + rowId);
	if (requestorName.value.length == 0) {
		requestorName.className = "inputBox";
	}
	else {
		requestorName.className = "inputBox red";
	}
	if (el2 != null) {
		msdsBeanGrid.cellById(rowId, msdsBeanGrid.getColIndexById(el2)).setValue("");
	}
}

function setMsdsActionViewLevel() {
    if ((viewLevel == 'requestor' || viewLevel == 'approver')) {
        //if new MSDS
        if ($v("requestStartingView") == 6) {
            $("msdsActionSpan").innerHTML = '<a href="#" onclick="addMaterial(); return false;">'+messagesData.addMaterial+'</a>'+
                                            ' | <a href="#" onclick="newMaterial(); return false;">'+messagesData.newMaterial+'</a>'+
                                            ' | <a href="#" onclick="uploadMsds(); return false;">'+messagesData.uploadMsdsImage+'</a>';
            if(msdsBeanGrid.getRowsNum() > 1)
    		{
    			requestStatus = $v("requestStatus");
    			if(requestStatus == 6 || viewLevel == 'requestor')
					$("msdsActionSpan").innerHTML += ' | <a href="#" onclick="uploadMsds(true); return false;">'+messagesData.uploadKitMsdsImage+'</a>';
				if(requestStatus == 5 || requestStatus== 8 || requestStatus == 11)
					$("msdsActionSpan").innerHTML += ' | <a href="#" onclick="viewKitSummaryDraft(); return false;">'+messagesData.viewKitSummary+'</a>';
			}
            $("msdsActionSpan").innerHTML += ' | <a href="#" onclick="showAttachedFiles(); return false;">'+messagesData.fileAttached+'</a>'+
                                            '<br>&nbsp;';
        }else { //else new Approval code
            $("msdsActionSpan").innerHTML = '<a href="#" onclick="uploadMsds(); return false;">'+messagesData.uploadMsdsImage+'</a>';
            if(msdsBeanGrid.getRowsNum() > 1)
			{
				requestStatus = $v("requestStatus");
    			if(requestStatus == 6 || viewLevel == 'requestor')
					$("msdsActionSpan").innerHTML += ' | <a href="#" onclick="uploadMsds(true); return false;">'+messagesData.uploadKitMsdsImage+'</a>';
				if(requestStatus == 5 || requestStatus== 8 || requestStatus == 11)
					$("msdsActionSpan").innerHTML += ' | <a href="#" onclick="viewKitSummaryDraft(); return false;">'+messagesData.viewKitSummary+'</a>';
			}
			$("msdsActionSpan").innerHTML += ' | <a href="#" onclick="showAttachedFiles(); return false;">'+messagesData.fileAttached+'</a>'+
                                            '<br>&nbsp;';
        }
        $('msdsActionSpan').style["display"]="";
    }else {
        $('msdsActionSpan').style["display"]="none";
    }
    //kit MSDS
    setKitMsdsViewLevel();
}

function showAttachedFiles() {
 var loc = "catalogaddrequest.do?uAction=findAttachedFiles&requestId="+$v("requestId");
 children[children.length] = openWinGeneric(loc,"showFilesList","650","500","yes","50","50","20","20","no");
}

function setKitMsdsViewLevel() {
    //show kit msds UOM for kit
    if (msdsBeanGrid.getRowsNum() > 1) {
        //we don't need this right now
        $('kitMsdsUomSpan').style["display"]="none";
        if ($v("requestStartingView") == 6) {
            setHeaderViewLevel('');
        }else {
            setHeaderViewLevel(true);
        }
    }else {
        $('kitMsdsUomSpan').style["display"]="none";
    }
}

function msdsSelectRow() {
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

	msdsPreRowId = rowId0;
	msdsSelectedRowId = rowId0;
	var dataSource = msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("dataSource")).getValue();

	//stop here if it's not a right mouse click
	if( !rightClick ) return;
	var menuItems = new Array();
	var msdsOnLine = msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("msdsOnLine")).getValue();
	if (colId0 > msdsBeanGrid.getColIndexById("mixtureDesc")) {
		//requestor and approvers is allow to do the following for lines on this request
		//starting_view = 0 - New Material
		//starting_view = 1 - New Size/Packaging
		//starting_view = 2 - New Part
		//starting_view = 3 - New Work Area Approval
		//starting_view = 4 - Modify QPL
		//starting_view = 5 - Fadeout from QPL
        //starting_view = 6 - New MSDS
        //starting_view = 7 - New Approval Code
        if (dataSource == 'new') {
			var startingView = msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("startingView")).getValue();
			if (viewLevel == 'requestor' || viewLevel == 'approver') {
                if ($v("requestStartingView") == '6') {
                    if (startingView == '6') {
                        menuItems[menuItems.length] = 'text='+messagesData.editMaterialInfo+';url=javascript:editMaterialData();';
                    }
                    if(viewLevel == 'requestor' || (viewLevel == 'approver' && $v('allowEditMixtureData') == 'Y'))
                    	menuItems[menuItems.length] = 'text='+messagesData.deleteLine+';url=javascript:deleteLineData();';
                }
                menuItems[menuItems.length] = 'text='+messagesData.uploadMsdsImage+';url=javascript:uploadMsds();';
			}

        }
		//always be able to view MSDS if it's online
		if (msdsOnLine == 'Y') {
			menuItems[menuItems.length] = 'text='+messagesData.viewComponentMsds+';url=javascript:viewMsds();';
		}

        //by this point if menu is empty then don't show anything
		if (menuItems.length == 0) {
			menuItems[menuItems.length] = '';
		}
		replaceMenu('msdsRightClickMenu',menuItems);
		toggleContextMenu('msdsRightClickMenu');
	}else {
        var customerMixtureNumber = msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("customerMixtureNumber")).getValue();
        if ((requestStatus == 5 || requestStatus== 8 || requestStatus == 11) && customerMixtureNumber != null && customerMixtureNumber.length > 0 && customerMixtureNumber != '&nbsp;') {
            menuItems[menuItems.length] = 'text='+messagesData.viewKitSummary+';url=javascript:viewKitSummaryDraft();';
            menuItems[menuItems.length] = 'text=;url=javascript:doNothing();';
        }else {
            menuItems[menuItems.length] = 'text=;url=javascript:doNothing();';
        }
        replaceMenu('msdsRightClickMenu',menuItems);
		toggleContextMenu('msdsRightClickMenu');    
    }
} //end of method

function refreshMsdsData() {
	showTransitWin();
    callToServer("catalogaddmsdsrequest.do?uAction=reloadMsdsData&requestId="+$v("requestId")+"&addCont="+addCont);
}

function deleteLineData() {
	showTransitWin();
	callToServer("catalogaddmsdsrequest.do?uAction=deleteLine&requestId="+$v("requestId")+
				 "&lineItem="+msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("lineItem")).getValue()+
                 "&partId="+msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("partId")).getValue());
}

function rejectMsdsLine() {
	showMessageDialog(messagesData.comments,"",true,"rejectMsdsLine");
}

function rejectMsdsLineData() {
	showTransitWin();
	callToServer("catalogaddmsdsrequest.do?uAction=rejectLine&requestId="+$v("requestId")+
					 "&lineItem="+msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("lineItem")).getValue()+"&comments="+$("messageText").value+"&hasHmrbTab="+$v("hasHmrbTab"));
}

function reloadMsdsData(tmpMsdsConfig,tmpMsdsJsonMainData,tmpLineMap,tmpLineIdMap,tmpLineMap3,closeTransitWinflag) {
    msdsConfig = tmpMsdsConfig;
	msdsJsonMainData = tmpMsdsJsonMainData;
	lineMap = tmpLineMap;
	lineIdMap = tmpLineIdMap;
	lineMap3 = tmpLineMap3;
	msdsBeanGrid._haas_row_span_map = lineMap;
	msdsBeanGrid._haas_row_span_class_map = lineMap3;
	msdsSelectedRowId = null;
	msdsPreClass = null;
 	msdsPreRowId = null;

	initializeMsdsGrid();

	setMsdsActionViewLevel();
    setAdditionalCommentLevel('');

    if("Y" == closeTransitWinflag)
		closeTransitWin();
}

function editMaterialData() {
	showTransitWin();
	var url = 'catalogaddmsdsrequest.do?uAction=editNewMaterial&calledFrom=catalogAddMsdsRequest&requestId='+$v("requestId")+
              "&lineItem="+msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("lineItem")).getValue()+
              "&partId="+msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("partId")).getValue();
	children[children.length] = openWinGeneric(url,"catalogAddEditNewMaterial",800,350,'yes' );
}

function uploadMsds(isKit) {
	//make sure there is at least one MSDS record for request
	if (msdsBeanGrid.getRowsNum() == 0) {
		alert(messagesData.uploadSdsImageRequiredData);
	}else {
		showTransitWin();
		var fileName = $v("requestId");
		var catalogAddMsds = 'catalogAddMsds';
		if (typeof (isKit) != 'undefined')
			catalogAddMsds = 'catalogAddKitMsds';
		var url = 'uploadfile.do?fileName=' + fileName + "&modulePath=" + catalogAddMsds + "&allowMultiple=true&requestId=" + fileName + "&lineItem=" + msdsBeanGrid.cells((msdsSelectedRowId == null ? 1 : msdsSelectedRowId), msdsBeanGrid.getColIndexById("lineItem")).getValue();
		children[children.length] = openWinGeneric(url, "uploadfile", 500, 200, 'yes');
	}
}

function viewKitSummaryDraft()
{
	var reportLoc = null;
		
	if(msdsBeanGrid.cells((msdsSelectedRowId==null?1:msdsSelectedRowId),msdsBeanGrid.getColIndexById("approvedCustMixtureNumber")).getValue() == '')
	{
		reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+$v('companyId')+
                    "&pr_request_id="+ $v("requestId")+
					"&pr_line_item="+msdsBeanGrid.cells((msdsSelectedRowId==null?1:msdsSelectedRowId),msdsBeanGrid.getColIndexById("lineItem")).getValue()+
					"&rpt_ReportBeanId=CatAddMSDSKitReportDefinition";
	}
	else
	{
		reportLoc = "/HaasReports/report/printConfigurableReport.do"+
                    "?pr_companyId="+$v('companyId')+
                    "&pr_custMsdsDb="+encodeURIComponent($v("customerMsdsDb"))+
					"&pr_custMsdsNo="+msdsBeanGrid.cells((msdsSelectedRowId==null?1:msdsSelectedRowId),msdsBeanGrid.getColIndexById("customerMixtureNumber")).getValue()+
					
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
			'&materialId='+ msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("materialId")).getValue() +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId')+
			'&facility=' + encodeURIComponent($v('engEvalFacilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
	else
		children[children.length] = openWinGeneric('ViewMsds?act=msds'+
			'&id='+ msdsBeanGrid.cells(msdsSelectedRowId,msdsBeanGrid.getColIndexById("materialId")).getValue() +
			'&showspec=N' +
			'&spec=' + // do we need to know spec id?
			'&cl='+$v('companyId')+
			'&facility=' + encodeURIComponent($v('engEvalFacilityId')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
}

var addCont = "N"; 
function addNewMaterialToList(contAdd) {
	if(contAdd)
		addCont = "Y";
	else
		addCont = "N";
	refreshMsdsData();
} //end of method

function addMaterial() {
	showTransitWin();		
	var loc = "searchmsdsmain.do?uAction=search&calledFrom=newCatalogAddMsdsProcess&searchText=&requestId="+$v("requestId")+
              "&facilityId="+$v("engEvalFacilityId")+"&companyId="+$v("companyId");
	if(msdsBeanGrid.getRowsNum() > 0)
		loc += "&msdsCatAddCont=true";
	else
		loc += "&msdsCatAddCont=false";
	var winId= 'searchForMsds'+$v("requestId");
	children[children.length] = openWinGeneric(loc,winId,"900","590","yes","50","50","20","20","no");
}

function newMaterial() {
	showTransitWin();
	var url = 'catalogaddmsdsrequest.do?uAction=newMaterial&calledFrom=catalogAddMsdsRequest&requestId='+$v("requestId");
	children[children.length] = openWinGeneric(url,"catalogAddEditNewMaterial",800,350,'yes' );
}

function setUpdateHmrbExpiration() {
    showTransitWin();
    callToServer("catalogaddrequest.do?uAction=setUpdateUseCodeExpiration&requestId="+$v("requestId")+"&calledFrom=msds"+
                 "&lineItem="+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("hmrbLineItem")).getValue()+
                 "&startDate="+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("beginDateJsp")).getValue()+
                 "&endDate="+hmrbBeanGrid.cells(hmrbSelectedRowId,hmrbBeanGrid.getColIndexById("endDateJsp")).getValue());
}

function callToServerCallback() {
    closeTransitWin();
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
};


function deleteNewMaterialLine(requestId,lineItem,partId,delCont)
{
	callToServer("catalogaddmsdsrequest.do?uAction=deleteLine&requestId="+requestId+
			 "&lineItem="+lineItem+
            "&partId="+partId+"&delCont="+delCont);
}