var children = [];
var dhxWins = null;

function myOnload(loadAction) {
	if (loadAction == "viewDocuments") {
		uploadDocuments();
	}
	/*else if (loadAction == "clearForm") {
		clearForm();
	}*/
	submit("search");
}

function uploadDocuments() {
	var contactLogId = $v("contactLogId");
	
	if (contactLogId == null || contactLogId == "") {
		submit("viewDocuments");
	}
	else {
		var loc = "contactlogdocumentview.do?uAction=view&contactLogId="+contactLogId;
		showWait(formatMessage(messagesData.waitingFor, messagesData.document));
		children[children.length] = openWinGeneric(loc,"contactLogDocumentViewer","700","320","yes","100","100","20","20","no");
	}
}

function closeAllchildren() {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren(); 
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = [];
}

function cancel() {
	window.close();
}

function submit(action) {
	if (action === undefined) {
		action = "search";
	}
	var msg = validate();
	
	if (action == "save" && msg != "") {
		alert(messagesData.fieldsRequired+"\n"+msg);
	}
	else {
		if (action == "viewDocuments" || action == "save") {
			document.genericForm.target = "_self";
			document.genericForm.action = "vendorcontactlogmain.do";
		}
		else {
			document.genericForm.target = "resultFrame";
		}
		var now = new Date();
	    document.getElementById("startSearchTime").value = now.getTime();
		document.getElementById("uAction").value = action;
		document.genericForm.submit();
	}
}

function validate() {
	var msg = "";
		
	if ($v("contactPurpose") == "") {
		msg += "\n"+messagesData.contactPurpose;
	}
	if ($v("contactStatus") == "") {
		msg += "\n"+messagesData.contactStatus;
	}
	if ($v("contactLogType") == "") {
		msg += "\n"+messagesData.contactType;
	}
	
	return msg;
}

function uploadDocuments() {
	var contactLogId = $v("contactLogId");
	
	if (contactLogId == null || contactLogId == "") {
		submit("viewDocuments");
	}
	else {
		var loc = "contactlogdocumentview.do?uAction=view&contactLogId="+contactLogId;
		showWait(formatMessage(messagesData.waitingFor, messagesData.document));
		children[children.length] = openWinGeneric(loc,"contactLogDocumentViewer","700","320","yes","100","100","20","20","no");
	}
}

function clearForm() {
	$("contactLogIdDisplay").value = messagesData.newcontactlog;
	$("contactLogId").value = "";
	$("contactName").value = "";
	$("contactReference").value = "";
	$("contactPurpose").selectedIndex = 0;
	$("contactStatus").selectedIndex = 0;
	$("contactLogType").selectedIndex = 0;
}

function showWait(message){
	$("transitLabel").innerHTML = message;
	
	var transitDailogWin = $("transitDailogWin");
	transitDailogWin.style.display="";

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
		transitWin.setPosition(200, 150);
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

function stopShowingWait() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
	return true;
}

function unloadWindow() {
	closeAllchildren();
	parent.opener.closeTransitWin();
}