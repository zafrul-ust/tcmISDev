// This works only for popup windows and IE. Close the window after clicking Esc key
var windowCloseOnEsc = true;
var children = new Array();
var dhxWins = null;

function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	if (validateSearchCriteriaInput()) {
		$("uAction").value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		document.getElementById("startSearchTime").value = new Date().getTime();
		return true;
	}
	else {
		return false;
	}
}

function validateSearchCriteriaInput() {
	var errorMessage = messagesData.searchTextRequired;
	var errorCount = 0;

	var searchArgument = $v("searchArgument");
	if (searchArgument == null || searchArgument.length < 1) {
		$("searchArgument").value = "";
		errorCount = 1;
	}

	if (errorCount > 0) {
		alert(errorMessage);
		return false;
	}

	return true;
}

function createXSL() {
    if (validateSearchCriteriaInput()) {
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_DisplayOnlyExcel', '650', '600', 'yes');
		document.genericForm.target = '_DisplayOnlyExcel';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
}

function closeThisWindow() {
    opener.closeTransitWin();
}

function addNewSpec() {
    showTransitWin();
    var url = 'catalogaddrequest.do?uAction=addSpec&requestId='+$v("requestId")+'&calledFrom='+$v("calledFrom")+'&companyId='+$v("companyId");
	children[children.length] = openWinGeneric(url,"catalogAddSpec",425,235,'no' );
}

function returnAddNewSpec(specName,specTitle,specVersion,specAmendment,coc,coa) {
    opener.addNewSpec(specName,specTitle,specVersion,specAmendment,coc,coa);
    window.close();
}

function addSelectedSpec() {
    opener.addExistingSpec($v("specId"),$v("specLibrary"),$v("modifySpecId"));
    window.close();
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