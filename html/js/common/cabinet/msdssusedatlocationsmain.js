var dhxWins = null;
var children = new Array();
var accountSysId = new Array();

function closeAllchildren()
{
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


function myOnLoad() {
	showCompanyOptions();
}

var	addPartArray;
var calledFromPage;
var workAreaGroup;

function validateForm() {
	// validate that item id is numberic
	if (document.clientCabinetManagementForm.itemOrPart.value == 'item') {
		if (!isInteger(document.clientCabinetManagementForm.criteria.value.trim(),true)) {
			alert(messagesData.itemInteger);
			return false;
		}
	}
	return true;
}

function search() {
	if (validateForm()) {
		var now = new Date();
		$("startSearchTime").value = now.getTime();
		document.clientCabinetManagementForm.target='resultFrame';
		$("uAction").value = 'search';
		getWASelectCount();
		showPleaseWait();	
		document.clientCabinetManagementForm.submit();
	}
}

function getWASelectCount()
{
	if($('applicationIdMultiSelTxt').style['display'] == '')
		$('workAreaSelectCount').value = 'Multiple';
	else
		{
			var applicationIdSel = $('applicationIdSel');
			$('workAreaSelectCount').value = applicationIdSel.options[applicationIdSel.selectedIndex].text;	
		}
}

function generateExcel() {
	if (validateForm()) {
		$("uAction").value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_cabinetManagement','650','600','yes');
		document.clientCabinetManagementForm.target='_cabinetManagement';
		window.setTimeout("document.clientCabinetManagementForm.submit();",500);
	}
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

function showTransitWin(message) {
	if (message != null && message.length > 0) {
		$("transitLabel").innerHTML = message;
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

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


function countTypeArrayChanged()
{
	var countTypeArrayO = document.getElementById('countTypeArraySel');

	if($('countTypeArrayMultiSelTxt').style['display'] == 'none')
	{
		$('countTypeArray').value = countTypeArrayO.value;
		$('countTypeArrayMultiSelTxt').value = '';
		$('countTypeArrayMultiSelTxt').style['display']='none';
		$('countTypeArraySel').style['display']='';
	}
}
