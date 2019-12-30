function lookupSupplier() {
	if(validateOps()) {
		loc = "/tcmIS/supply/posuppliermain.do?popUp=Y&displayElementId=supplierName&valueElementId=supplier&statusFlag=true&opsEntityId="+$v("opsEntityId");
		openWinGeneric(loc,"supplierlookup","800","500","yes","50","50","20","20","no"); 
	}  
}

function validateOps() {
	if($v("opsEntityId").length == 0) {
		alert(messagesData.pleaseinput.replace(/[{]0[}]/g,messagesData.operatingentity));
		return false;
	}  
	return true;
}

function supplierChanged(id) {
	document.getElementById("supplierName").className = "inputBox";
}

function submitSearch() {
	
	if (validateSearchCriteriaInput()) {
		$("uAction").value = 'search';
		document.genericForm.target = 'resultFrame';
		
		try {
			$('transitPage').style.display='';
		} catch(ex) {}
		
		document.genericForm.submit();
		return false;
	}
	else {
		return false;
	}
}

function validateSearchCriteriaInput() {

	if(!isInteger($v("days"), false)) {
		alert(messagesData.validvalues+" "+ messagesData.days);
		return false;
	}
	
	if($v("opsEntityId").length == 0) {
		alert(messagesData.pleaseinput.replace(/[{]0[}]/g,messagesData.operatingentity));
		return false;
	}

	if($v("supplier").length == 0) {
		alert(messagesData.pleaseinput.replace(/[{]0[}]/g,messagesData.supplier));
		return false;
	}
	
	return true;
}

function myResize() {
	setWindowSizes();
//alert($("searchFrameDiv").offsetHeight);	
	var iFrameHeight = myHeight - 75; //$("searchFrameDiv").offsetHeight;
	$("resultFrame").height = iFrameHeight + "px";
//	window.frames['resultFrame'].resizeGrids();
}

var dhxFreezeWins = null;


function initializeDhxWins() {
	if (dhxFreezeWins == null) {
	  dhxFreezeWins = new dhtmlXWindows();
	  dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}


function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);
	
	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";
	
	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		 // create window first time
		 transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
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
		 transitWin.setModal(true);  // freeze the window here
		 dhxFreezeWins.window("transitDailogWin").show();
	 }
}

function closeTransitWin() {
	if (dhxFreezeWins != null) {
		 if (dhxFreezeWins.window("transitDailogWin")) {
		   dhxFreezeWins.window("transitDailogWin").setModal(false);
		   dhxFreezeWins.window("transitDailogWin").hide();
		  }
	}
}



