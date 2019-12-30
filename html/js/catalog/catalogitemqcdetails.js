var selectedItemTab = 0;

function myOnLoad() {
	if (($v("itemApproved") != null && $v("itemApproved").length > 0) || $v("itemRejected") == "true") {
		loadParent();
	}
	showErrors();
	var i = 0;
	var itemVerified = false;
	var leng = $v("totalComps");
	for (;i < leng;i++) {
		itemVerified = itemVerified||($("part["+i+"].itemVerified").value == "Y");
		var searchField = $("part["+i+"].pkgStyleSearch");
		var popupField = $("part["+i+"].pkgStylePopup");
		if (searchField.addEventListener) {
			searchField.addEventListener("focus",displayPkgStyleSelector);
			searchField.addEventListener("input",displayPkgStyleSelector);
			searchField.addEventListener("blur",selectPkgStyle);
		}
		else {
			searchField.attachEvent("onfocus",displayPkgStyleSelector);
			searchField.attachEvent("onkeyup",displayPkgStyleSelector);
			searchField.attachEvent("onblur",selectPkgStyle);
		}
		
		if (popupField.addEventListener) {
			popupField.addEventListener("blur",function() {
				if ($("part["+selectedItemTab+"].pkgStyleSearch") !== document.activeElement) {
					$("part["+selectedItemTab+"].pkgStylePopup").style.display = "none";
					$("part["+selectedItemTab+"].pkgStyleSearch").value = $v("part["+selectedItemTab+"].pkgStyle");
				}
			});
		}
		else {
			popupField.attachEvent("onblur", function() {
				if ($("part["+selectedItemTab+"].pkgStyleSearch") !== document.activeElement) {
					$("part["+selectedItemTab+"].pkgStylePopup").style.display = "none";
					$("part["+selectedItemTab+"].pkgStyleSearch").value = $v("part["+selectedItemTab+"].pkgStyle");
				}
			});
		}
	}
	var itemVerifiedSelect = $("itemVerifiedSelect");
	if (itemVerifiedSelect) {
		itemVerifiedSelect.value = itemVerified?"Y":"N";
	}
	$("itemVerified").value = itemVerified?"Y":"N";
	lockItem();
	var queueRowStatus = $v("catalogQueueRowStatus");
	var vendorTask = $v("vendorTask");
	var userIsAssignee = $v("qAssignee") == $v("currentUser");
	if (vendorTask == "Y") {
		if (! ((queueRowStatus == "Assigned" && userIsAssignee) ||
				(queueRowStatus == "Pending QC" && ! userIsAssignee))) {
			disablePage();
		}
	}
}

function lockItem() {
	if ($v("itemApproved") == "approved" || $v("itemApproved") == "submitted") {
		j$("input:not(.inputBtns):not(.lookupBtn):not(#itemVerified)").prop("disabled", true);
		j$("select").prop("disabled", true);
		if ($v("itemApproved") == "approved")
			alert(messagesData.itemapproved);
		else if ($v("itemApproved") == "submitted")
			alert(messagesData.itemsubmitted);
	}

	if ($v("itemVerified") == "Y") {
		j$(".roundcont:gt(0) input:not(.inputBtns):not(.lookupBtn):not(#itemVerified)").prop("disabled", true);
		j$("select").prop("disabled", true);
	}
}

function unlockItem() {
	j$(".roundcont:gt(0) input").prop("disabled", false);
	j$("select").prop("disabled", false);
	j$("textarea").prop("disabled", false);
}

function viewRequest() {
	var uAction = $v("uAction");
	var requestId = $v("requestId");
	var lineItem = $v("lineItem");
	var companyId = $v("companyId");
	var catalogId = $v("catalogId");
	var catPartNo = $v("catPartNo");
	loc = "catalogitemqcdetails.do?uAction=details";
	if ($("qId") != null && $v("qId").length > 0) {
		loc += "&qId="+$v("qId");
		loc += "&workItemStatus="+$v("catalogQueueRowStatus");
	}
	else {
		loc += "&companyId="+companyId;
		loc += "&requestId="+requestId;
		loc += "&lineItem="+lineItem;
		loc += "&catalogId="+catalogId;
		loc += "&catPartNo="+catPartNo;
	}
	children[children.length] = openWinGeneric(loc,"Original","650","500","yes");
}

function showExistingItems() {
	loc = "/tcmIS/hub/existingcatalogitems.do?";
    var userpartnumber = $v("catPartNo");
    if (userpartnumber.length > 0) {
        loc = loc + "catPartNo=" + userpartnumber;
        var companyId  =  $v("companyId");
        loc = loc + "&companyId=" + companyId;
        var catalogId  =  $v("catalogId");
        loc = loc + "&catalogId=" + catalogId;

        openWinGeneric(loc,"showExistingItemsfor","700","450","yes","260","320");
    }
}

function getItemId() {
	showTransitDialog(messagesData.pleasewait);
	unlockItem();
	children[children.length] = openWinGeneric("","itemmatcher","1100","725","yes");
    document.genericForm.action = "itemmatcher.do";
	document.genericForm.target = "itemmatcher";
	$("uAction").value = "match";
	
	document.genericForm.submit();
	
	document.genericForm.action = "catalogitemqcdetails.do";
	document.genericForm.target = "";
	lockItem();
}

function showItemNotes() {
	var itemId = $v("itemId");
	var title = "showItemNotes_" + itemId.replace(/[.]/, "_");
	var loc = "/tcmIS/supply/edititemnotes.do?" +
		"&itemId=" + itemId;
   	
	children[children.length] = openWinGeneric(loc,title,"800","600","yes","50","50","20","20","no");
}

function showMsds() {
	var company = $v("companyId");
	if (company == null || company.length == 0) {
		company = "INTERNAL";
	}
	var material = $v("part["+selectedItemTab+"].materialId");
	if (material != null && material.length > 0) {
		var loc = "/tcmIS/all/ViewMsds?cl="+company+"&showspec=N&act=msds&id="+$v("part["+selectedItemTab+"].materialId");
		openWinGeneric(loc,"MSDS","800","650","yes");
	}
}

function saveItem() {
	var wqiStatus = $v("catalogQueueRowStatus");
	submitForm("save", "Pending QC" == wqiStatus);
}

function submitItem() {
	submitForm("submit", true);
}

function approveItem() {
	submitForm("approve", true);
}

function submitForm(uAction, performAudit) {
	unlockItem();
	showTransitDialog(messagesData.pleasewait);
	if (validate(performAudit)) {
		$("uAction").value = uAction;
		
		document.genericForm.submit();
	}
	else {
		hideTransitDialog();
	}
}

function selectAllNoFault(select) {
	var leng = $v("totalComps");
	for (var i = 0;i < leng;i++) {
		var compNoFaultCheckbox = $("part["+i+"].noFaultCheckbox");
		if (compNoFaultCheckbox) {
			compNoFaultCheckbox.checked = select;
		}
	}
}

function selectAllVendorError(select) {
	var leng = $v("totalComps");
	for (var i = 0;i < leng;i++) {
		var compVendorErrorCheckbox = $("part["+i+"].vendorErrorCheckbox");
		if (compVendorErrorCheckbox) {
			compVendorErrorCheckbox.checked = select;
		}
	}
}

function clearReverse(component) {
	var compNoFaultCheckbox = $("part["+component+"].noFaultCheckbox");
	var compVendorErrorCheckbox = $("part["+component+"].vendorErrorCheckbox");
	if (compNoFaultCheckbox) {
		compNoFaultCheckbox.checked = false;
	}
	if (compVendorErrorCheckbox) {
		compVendorErrorCheckbox.checked = false;
	}
}

function submitReverse() {
	var leng = $v("totalComps");
	var msg = "";
	var componentsReversed = false;
	for (var i = 0;i < leng;i++) {
		var compNoFaultCheckbox = $("part["+i+"].noFaultCheckbox");
		var compVendorErrorCheckbox = $("part["+i+"].vendorErrorCheckbox");
		if (compNoFaultCheckbox && compNoFaultCheckbox.checked) {
			componentsReversed = true;
			break;
		}
		if (compVendorErrorCheckbox && compVendorErrorCheckbox.checked) {
			componentsReversed = true;
			break;
		}
	}
		
	if ( ! componentsReversed) {
		msg += messagesData.reversecomperror+"\n";
	}
	var reversalComments = $("reverseComments");
	if (reversalComments) {
		if (reversalComments.value.length == 0) {
			msg += messagesData.pleaseentervaluefor.replace('{0}',messagesData.reversecomments)+"\n";
		}
	}
	if (msg.length > 0) {
		alert(msg);
		return false;
	}
	$("uAction").value = "reverse";
	
	showTransitDialog(messagesData.reversing);
	document.genericForm.submit();
}

function reportProblem(reAssignTo) {
	var url = 'problemqueuereason.do?uAction=open&qId='+$v("qId")
			+"&task="+$v("task")
			+"&status="+$v("catalogQueueRowStatus");
	if (reAssignTo)
		url += "&reAssignTo=" + reAssignTo;
    openWinGeneric(url,"reportProblem","400","300",'yes');
}

function problemReported() {
	loadParent();
}

function validate(approval) {
	var msg = "";
	if ( ! $("qId") && $v("catPartNo").length > 30) {
		msg += messagesData.maxlength.replace('{0}',messagesData.userpartnr).replace('{1}',"30");
		msg += "\n";
	}
	
	if ((approval || $v("itemId").length > 0) && isNaN(parseFloat($v("itemId")))) {
		msg += messagesData.mustbeanumber.replace('{0}',messagesData.itemid);
		msg += "\n";
	}
	
	if ($v("categoryId").length > 0 && isNaN(parseInt($v("categoryId")))) {
		msg += messagesData.pleaseselect.replace('{0}',messagesData.itemcategory);
		msg += "\n";
	}
	
	if ($v("sampleSize") !== "Y") {
		$("sampleSize").value = "N";
	}
	
	if ($v("itemType").length > 2) {
		msg += messagesData.maxlength.replace('{0}',messagesData.itemtype).replace('{1}',"2");
		msg += "\n";
	}
	
	if ($v("itemVerified") !== "Y") {
		$("itemVerified").value = "N";
	}
	
	for (var i = 0;i < $v("totalComps");i++) {
		var compNo = i+1;
		if ($v("part["+i+"].materialDesc").length > 1000) {
			msg += messagesData.maxlength.replace('{0}',compNo+": "+messagesData.materialdesc).replace('{1}',"1000");
			msg += "\n";
		}
		
		var shelfLifeDays = $v("part["+i+"].shelfLifeDays");
		if ((approval || shelfLifeDays.length > 0) && (isNaN(shelfLifeDays) || shelfLifeDays == 0 || shelfLifeDays < -1)) {
			msg += compNo+": "+messagesData.positive.replace('{0}',messagesData.shelflifedays);
			msg += "\n";
		}
		
		if ((approval || $v("part["+i+"].shelfLifeDays").length > 0) && $v("part["+i+"].shelfLifeBasis").length == 0) {
			msg += compNo+": "+messagesData.pleaseselect.replace('{0}',messagesData.shelflifebasis);
			msg += "\n";
		}
		
		if ($v("part["+i+"].shelfLifeBasis").length > 1) {
			msg += compNo+": "+messagesData.invalid.replace('{0}',messagesData.shelflifebasis);
			msg += "\n";
		}
		
		var minTemp = $v("part["+i+"].minTemp");
		if ((minTemp.length > 0) && isNaN(minTemp)) {
			msg += compNo+": "+messagesData.mustbeanumber.replace('{0}',messagesData.minstoragetemp);
			msg += "\n";
		}
		
		var maxTemp = $v("part["+i+"].maxTemp");
		if ((approval || maxTemp.length > 0) && isNaN(maxTemp)) {
			msg += compNo+": "+messagesData.mustbeanumber.replace('{0}',messagesData.maxstoragetemp);
			msg += "\n";
		}
		
		if (parseFloat(minTemp) > parseFloat(maxTemp)) {
			msg += compNo+": "+messagesData.maxlessthanmintemp;
			msg += "\n";
		}
		
		var tempUnits = $v("part["+i+"].tempUnits");
		if ((approval || minTemp.length > 0 || maxTemp.length > 0) && tempUnits.length == 0) {
			msg += compNo+": "+messagesData.pleaseselect.replace('{0}',messagesData.storagetempunit);
			msg += "\n";
		}
		
		if (tempUnits.length > 1) {
			msg += compNo+": "+messagesData.invalid.replace('{0}',messagesData.storagetempunit);
			msg += "\n";
		}
		
		if ($v("part["+i+"].grade").length > 200) {
			msg += compNo+": "+messagesData.maxlength.replace('{0}',messagesData.grade).replace('{1}',"200");
			msg += "\n";
		}
		
		if ($v("part["+i+"].mfgPartNo").length > 30) {
			msg += compNo+": "+messagesData.maxlength.replace('{0}',messagesData.mfgpartnr).replace('{1}',"30");
			msg += "\n";
		}
		
		if ((approval || $v("part["+i+"].componentsPerItem").length > 0) && isNaN(parseFloat($v("part["+i+"].componentsPerItem")))) {
			msg += compNo+": "+messagesData.mustbeanumber.replace('{0}',messagesData.nrcomp);
			msg += "\n";
		}
		
		if ((approval || $v("part["+i+"].partSize").length > 0) && isNaN(parseFloat($v("part["+i+"].partSize")))) {
			msg += compNo+": "+messagesData.mustbeanumber.replace('{0}',messagesData.partsize);
			msg += "\n";
		}
		
		var sizeUnit = $v("part["+i+"].sizeUnit");
		if (sizeUnit.length == 0) {
			msg += compNo+": "+messagesData.pleaseselect.replace('{0}',messagesData.sizeunit);
			msg += "\n";
		}
		
		if (sizeUnit.length > 30) {
			msg += compNo+": "+messagesData.invalid.replace('{0}',messagesData.sizeunit);
			msg += "\n";
		}
		
		var packaging = $v("part["+i+"].pkgStyle");
		if (packaging.length > 45 || ! isValidPackage(packaging, approval)) {
			msg += compNo+": "+messagesData.invalid.replace('{0}',messagesData.pkgstyle);
			msg += "\n";
		}
		
		if ($v("part["+i+"].dimension").length > 40) {
			msg += compNo+": "+messagesData.maxlength.replace('{0}',messagesData.dimension).replace('{1}',"40");
			msg += "\n";
		}
		
		var netwt = $v("part["+i+"].netwt");
		if (((approval && isNetWtRequired(sizeUnit)) || netwt.length > 0) && (isNaN(parseFloat(netwt)) || parseFloat(netwt) <= 0)) {
			msg += compNo+": "+messagesData.positive.replace('{0}',messagesData.netwt);
			msg += "\n";
		}
		
		if (((approval && isNetWtRequired(sizeUnit)) || netwt.length > 0) && $v("part["+i+"].netwtUnit").length == 0) {
			msg += compNo+": "+messagesData.pleaseselect.replace('{0}',messagesData.netwtunit);
			msg += "\n";
		}
		
		if ($v("part["+i+"].netwtUnit").length > 30) {
			msg += compNo+": "+messagesData.invalid.replace('{0}',messagesData.netwtunit);
			msg += "\n";
		}
		
		$("part["+i+"].itemVerified").value = $v("itemVerified");
	}
	
	if (msg.length > 0) {
		alert(msg);
		return false;
	}
	return true;
}

function isNetWtRequired(sizeUnit) {
	var reqd = true;
	var option = j$("#part\\[0\\]\\.netwtUnit option[value='"+sizeUnit+"']");
	if (option.val() == sizeUnit) {
		reqd = false;
	}
	return reqd;
}

function selectItemVerified() {
	$("itemVerified").value = $v("itemVerifiedSelect");
}

function setPkgStyle(pkgStyle) {
	j$("#part\\["+selectedItemTab+"\\]\\.pkgStyle").data("store", pkgStyle);
	$("part["+selectedItemTab+"].pkgStyle").value = pkgStyle;
	$("part["+selectedItemTab+"].pkgStyleSearch").value = pkgStyle;
	$("part["+selectedItemTab+"].pkgStylePopup").innerHTML="";
	$("part["+selectedItemTab+"].pkgStylePopup").style.display="none";
}

function isValidPackage(packaging, approval) {
	var valid = false;
	if ( ! approval && packaging.length == 0) {
		valid = true;
	}
	else {
		for (x in pkgStyleList) {
			var style = pkgStyleList[x];
			if (packaging === style) {
				valid = true;
				break;
			}
		}
	}
	return valid;
}

function buildPkgStyleDiv(style) {
	return "<div style=\"cursor:default\" onmouseover=\"highlight(this, true)\" onmouseout=\"highlight(this, false)\" onmousedown=\"setPkgStyle(this.innerHTML);\">"+style+"</div>";
}

function genPkgList(enteredText) {
	var prefixList = "";
	var wordStartList = "";
	var otherMatchList = "";
	if (enteredText.length == 0) {
		for (x in pkgStyleList) {
			otherMatchList += buildPkgStyleDiv(pkgStyleList[x]);
		}
	}
	else {
		var exactMatch = "";
		for (x in pkgStyleList) {
			var style = pkgStyleList[x];
			var matchIndex = style.toLowerCase().indexOf(enteredText.toLowerCase());
			var match = enteredText.length >= 2?matchIndex >= 0:matchIndex == 0;
			
			if (match) {
				var div = buildPkgStyleDiv(style);
				if (matchIndex == 0) {
					if (enteredText === style) {
						exactMatch = enteredText;
					}
					prefixList += div;
				}
				else if (style.charAt(matchIndex-1).search(/\W/) == 0) {
					wordStartList += div;
				}
				else {
					otherMatchList += div;
				}
			}
		}
		if (exactMatch.length > 0) {
			j$("#part\\["+selectedItemTab+"\\]\\.pkgStyle").data("store", $v("part["+selectedItemTab+"].pkgStyle"));
			$("part["+selectedItemTab+"].pkgStyle").value = exactMatch;
		}
		else {
			$("part["+selectedItemTab+"].pkgStyle").value = j$("#part\\["+selectedItemTab+"\\]\\.pkgStyle").data("store");
		}
	}
	return prefixList+wordStartList+otherMatchList;
}

function displayPkgStyleSelector() {
	var enteredText = $v("part["+selectedItemTab+"].pkgStyleSearch");
	var pkgList = genPkgList(enteredText);
	
	if (pkgList.length > 0) {
		$("part["+selectedItemTab+"].pkgStylePopup").innerHTML=pkgList;
		$("part["+selectedItemTab+"].pkgStylePopup").style.display="";
	}
	else {
		$("part["+selectedItemTab+"].pkgStylePopup").innerHTML="";
		$("part["+selectedItemTab+"].pkgStylePopup").style.display="none";
	}
}

function selectPkgStyle() {
	if ($("part["+selectedItemTab+"].pkgStylePopup") !== document.activeElement) {
		$("part["+selectedItemTab+"].pkgStylePopup").style.display = "none";
		$("part["+selectedItemTab+"].pkgStyleSearch").value = $v("part["+selectedItemTab+"].pkgStyle");
	}
}

function highlight(component, on) {
	if (on) {
		component.style.color = "white";
		component.style.backgroundColor = "blue";
	}
	else {
		component.style.color = "black";
		component.style.backgroundColor = "transparent";
	}
}

function setItemId(itemId, itemVerified) {
	if (itemId.length > 0) {
		if (itemVerified == "Y") {
			var qId = $("qId")?$v("qId"):"";
			var requestId = $v("requestId");
			var lineItem = $v("lineItem");
			var companyId = $v("companyId");
			
			var loc="catalogitemqcdetails.do?uAction=reload&qId="+qId+"&companyId="+companyId+"&requestId="+requestId+"&lineItem="+lineItem+"&itemId="+itemId;
			location.assign(loc);
		}
		else {
			unlockItem();
			$("itemId").value = itemId;
			if ($v("qId") == null || $v("qId").length == 0) {
				var itemVerifiedSelect = $("itemVerifiedSelect");
				if (itemVerifiedSelect) {
					itemVerifiedSelect.value = "N";
				}
			}
			$("itemVerified").value = "N";
		}
	}
	hideTransitDialog();
}

function toggleItem(itemNum) {
	if (itemNum != selectedItemTab) {
		hideItem(selectedItemTab);
		showItem(itemNum);
		selectedItemTab = itemNum;
	}
}

function hideItem(itemNum) {
	var itemLink =  $("itemLink" + itemNum);
	var itemLinkSpan =  $("itemLinkSpan" + itemNum);
	var itemDiv =  $("itemDiv" + itemNum);

	itemLink.className = "tabLeftSide";
	itemLinkSpan.className = "tabRightSide";
	itemDiv.style["display"] = "none";

}

function showItem(itemNum) {
	var itemLink =  $("itemLink" + itemNum);
	var itemLinkSpan =  $("itemLinkSpan" + itemNum);
	var itemDiv =  $("itemDiv" + itemNum);

	itemLink.className = "selectedTab";
	itemLinkSpan.className = "selectedSpanTab";
	itemDiv.style["display"] = "block";
}

function showTransitDialog(message){
	if (message) {
		$("transitLabel").innerHTML = message;
	}

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
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function hideTransitDialog() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
			transitReload = false;
		}
	}
	return true;
}

function closeTransitWin() {
	hideTransitDialog();
}

function loadParent() {
	//var qId = $v("qId");
	//var qStatus = $v("catalogQueueRowStatus");
	if ($v("requestIdDisp") != null && $v("requestIdDisp").length > 0)
		parent.catalogAddProcessframe.resultFrame.doRefresh('true');
	else
		parent.catAddVendorQueueframe.resultFrame.doRefresh('false');
}

function showErrors() {
	if (showErrorMessage) {
		var errorMessagesArea = $("errorMessagesArea");
		errorMessagesArea.style.display="";

		if (dhxWins == null) {
			dhxWins = new dhtmlXWindows();
			dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
		}

		if (!dhxWins.window("errorMessagesArea")) {
			// create window first time
			errorWin = dhxWins.createWindow("errorMessagesArea",0,0, 400, 200);
			errorWin.setText("");
			errorWin.clearIcon();  // hide icon
			errorWin.denyResize(); // deny resizing
			errorWin.denyPark();   // deny parking

			errorWin.attachObject("errorMessagesArea");
			// errorWin.attachEvent("onClose",
			// function(inputWin){inputWin.hide();});
			errorWin.center();
			// setting window position as default x,y position doesn't seem
			// to work, also hidding buttons.
			errorWin.setPosition(328, 131);
			errorWin.button("minmax1").hide();
			errorWin.button("park").hide();
			// errorWin.button("close").hide();
			errorWin.setModal(true);
		}else{
			// just show
			errorWin.setModal(true);
			dhxWins.window("errorMessagesArea").show();
		}
	}
}

function disablePage() {
	j$("input:button:not([id$=msdsViewer])").attr("disabled","disabled");
	j$("input:checkbox").attr("readonly","readonly");
	j$("input:checkbox,input:radio").click(function() {
		return false;
	});
	j$("input:radio:not(:checked)").attr("readonly","readonly");
	var textInputs = j$("input:text,textarea");
	textInputs.attr("readonly","readonly");
	textInputs.addClass("msdsReadOnlyField optionTitleBold");
	j$("select").attr("disabled", "disabled");
	//j$("select option:not(:selected)").attr("disabled","disabled");
	
	// special handling for pkgStyle input
	var i = 0;
	var leng = $v("totalComps");
	for (;i < leng;i++) {
		var searchField = $("part["+i+"].pkgStyleSearch");
		if (searchField.removeEventListener) {
			searchField.removeEventListener("focus",displayPkgStyleSelector);
			searchField.removeEventListener("input",displayPkgStyleSelector);
			searchField.removeEventListener("blur",selectPkgStyle);
		}
		else {
			searchField.detachEvent("onfocus",displayPkgStyleSelector);
			searchField.detachEvent("onkeyup",displayPkgStyleSelector);
			searchField.detachEvent("onblur",selectPkgStyle);
		}
	}
}

function submitRejectCannotFulfill() {
	if ($v("rejectionComments").length == 0) {
		alert(messagesData.pleaseentervaluefor.replace('{0}',messagesData.rejectioncomments));
		return false;
	}
	
	$("uAction").value = "reject";
	showTransitDialog(messagesData.rejecting);
	
	document.genericForm.submit();
	
	return true;
}

function toggleRejectionPopup() {
	j$("#rejectCommentPopup").toggle();
}

function toggleReversalPopup() {
	j$("#reverseCommentPopup").toggle();
}

function viewContactLog() {
	var qId = $v("qId");
	var loc = "vendorcontactlogmain.do?uAction=search&qId="+qId+"&qItemStatus="+$v("catalogQueueRowStatus");
	showTransitDialog(messagesData.pleasewait);
	children[children.length] = openWinGeneric(loc,"contactlog","800","700","yes","50","50","20","20","no");
}

function viewProblemHistory() {
	var qId = $v("qId");
	var loc = "problemqueuereason.do?uAction=showHistory&qId="+qId;
	children[children.length] = openWinGeneric(loc,"problemHistory","800","700","yes","50","50","20","20","no");	
}
