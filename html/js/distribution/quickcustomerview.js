function lookupCustomer() {    
  loc = "../distribution/customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
  openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}

function customerChanged(id, name) {
	document.getElementById("customerName").className = "inputBox";
}

function clearCustomer() {
    document.getElementById("customerName").value = "";
    document.getElementById("customerId").value = "";
    
    return false;
}

function submitSearch() {
	
	if (validateSearchCriteriaInput()) {
		$("uAction").value = 'search';
		document.genericForm.target = 'resultFrame';
		
		try {
			$('transitPage').style.display='';
		} catch(ex) {}
		
		document.genericForm.submit();
/*		
		var loc = "quickcustomerviewresults.do?uAction=search&searchKey="+$v("searchKey")+"&region=&itemId="+$v("itemId")+
				  "&inventoryGroup="+$v("inventoryGroup")+"&hub="+$v("hub")+"&opsEntityId="+$v("opsEntityId")+
				  "&customerId="+$v("customerId")+"&shipConfirmDate="+$v("shipConfirmDate")+"&deliveredDate="+$v("deliveredDate");
		window.frames["resultFrame"].location = loc;
*/		
		return false;
	}
	else {
		return false;
	}
}

function lookupItem() {
	if($v("inventoryGroup").length == 0) {
		alert(messagesData.selectinventorygroup);
		return false;
	} else {
		var loc = "quickitemsearchmain.do?inventoryGroup=" + encodeURIComponent($v("inventoryGroup"))+"&opsEntityId="+ encodeURIComponent($v("opsEntityId"));
		
		openWinGeneric(loc,'itemsearch',"900","600","yes","50","50","20","20","no"); 
	}
}

function setItem(itemId, desc) {
	$("itemId").value = itemId;
		
	submitSearch();
}

function validateSearchCriteriaInput() {

	if(!isInteger($v("days"), true)) {
		alert(messagesData.validvalues+" "+ messagesData.days);
		return false;
	}
	
	if($v("customerId").length == 0) {
		alert(messagesData.pleaseinput.replace('{0}',messagesData.customer));
		return false;
	}
	
	return true;
}

function myResize() {
	setWindowSizes();
// alert($("searchFrameDiv").offsetHeight);	
	var iFrameHeight = myHeight - 75; // $("searchFrameDiv").offsetHeight 
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
		transitWin.setModal(true);
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


