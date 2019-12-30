var dhxWins = null;

function searchFieldChanged() {
	/* remove the contains option from search mode
	 * if search field is load id as that parameter must be an exact match */
	if ($("searchField").value == 'loadId')
		$("searchMode").remove(1);
	else {
		setOption(1,messagesData.contains,"contains","searchMode");
	}
}

function submitSearchForm() {
	if(validateForm()) {
		/*Make sure to not set the target of the form to anything other than resultFrame*/
		document.genericForm.target='resultFrame';
		$("uAction").value = 'search';

		//set start search time
		var now = new Date();
		var startSearchTime = $("startSearchTime");
		startSearchTime.value = now.getTime();
		
		showPleaseWait();
		return true;
	}
	
	return false;
}

function validateForm(){
	var result = true;
	var errmsg = "";
	
	var searchFieldVal = $("searchField").value;
	var searchArgumentVal = $("searchArgument").value;
	   
	if (isWhitespace(searchArgumentVal)) {
		// validate search argument isn't empty
		errmsg += messagesData.searchargumentrequired + '\n';
	}
	else if (searchFieldVal == 'loadId' && !(isInteger(searchArgumentVal))) {
		// validate load id is a number
		errmsg += messagesData.mustBeANumber.replace(/[{]0[}]/g,messagesData.loadid);
	}
    	   
	if (errmsg.length > 0) {
		alert(errmsg);
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
	$("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = $("transitDailogWin");
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
		transitWin.center();
		
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		dhxWins.window("transitDailogWin").show();
	}
}

function generateExcel() {
	if(validateForm()) {
		$("uAction").value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_FreightInvoicingGenerateExcel','650','600','yes');
		document.genericForm.target='_FreightInvoicingGenerateExcel';
		window.setTimeout("document.genericForm.submit();",500);
	}
}
