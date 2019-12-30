var dhxWins = null;
var children = new Array();
var accountSysId = new Array();
var canChangeOwnership = false;

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
	uploadPartLevelPermission = true;
	showCompanyOptions();
}

var	addPartArray;
var calledFromPage;
var workAreaGroup;
function addPart(calledFrom) {
	calledFromPage = calledFrom;
	$("uAction").value = 'clientCabinetBinAddPart';
	var fac = document.getElementById('facilityId');
	$('facilityName').value = fac.options[fac.selectedIndex].text;
	addPartArray = new Array();
	if (calledFrom == 'addPartFromLink') {
		numberSelected = 0;
		tmpWorkAreaId = '';
		tmpWorkAreaDesc = '';
		var applicationId = $("applicationIdSel");
		if(applicationId.style['display'] == '')
			addPartArray[0] = {value:applicationId.value, id: applicationId.options[applicationId.selectedIndex].text};
		else
			{
				var text = $("applicationIdMultiSelTxt").value.split(';');
				var val = $("applicationId").value.split('|');
				for(var j=0;j<text.length;j++) {
					addPartArray[j] = {value: val[j],id:text[j]};
				}
				
			}
	}else {
        addPartArray[0] = {value:$("applicationId").value,id:$("applicationDesc").value};
	}
    
    showTransitWin();
    children[children.length] = openWinGeneric('clientcabinetbinaddpart.do?facilityId='+encodeURIComponent($v("facilityId"))+'&facilityName='+encodeURIComponent($v("facilityName"))+
                                               '&companyId='+encodeURIComponent($v("companyId"))+"&canChangeOwnership="+canChangeOwnership, '_newAddPart', '650','440', 'yes');
}

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

function createTemplateData() {
    if (validateForm()) {
        $('uAction').value = 'createTemplateData';
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
		if (dhxWins.window("transitDialogWin")) {
			dhxWins.window("transitDialogWin").setModal(false);
			dhxWins.window("transitDialogWin").hide();
		}
	}
}

function showTransitWin(message) {
	if (message != null && message.length > 0) {
		$("transitLabel").innerHTML = message;
	}else {
		$("transitLabel").innerHTML = messagesData.pleasewait;
	}

	var transitDialogWin = document.getElementById("transitDialogWin");
	transitDialogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDialogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDialogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDialogWin");
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
		dhxWins.window("transitDialogWin").show();
	}
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 100);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}

function showAccountInputDialog()
{
	var inputDailogWin = document.getElementById("accountSysDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showAccountInputDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showAccountInputDialog",0,0, 400, 118);
		inputWin.setText(messagesData.accountsysteminputdialog);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking
		setOption(0,messagesData.pleaseselect,"", "accountSystemSelectBox");
		for ( var i=0; i < accountSysId.length; i++)
		{
			setOption(i+1,accountSysId[i].id,accountSysId[i].id, "accountSystemSelectBox");

		}
		inputWin.attachObject("accountSysDailogWin");
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
		dhxWins.window("showAccountInputDialog").show();
	}
}

function accountSysOkClose()
{
	var selectedAccountSysId = document.getElementById("accountSystemSelectBox");
	if((selectedAccountSysId.length>0) && (selectedAccountSysId.value!=""))
	{
	  $("accountSysId").value = selectedAccountSysId.value;
	  dhxWins.window("showAccountInputDialog").setModal(false);
	  dhxWins.window("showAccountInputDialog").hide();
	  window.frames["resultFrame"].editDirectedCharge();
	}
} //end of method

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

function uploadList() {
    openWinGeneric("uploadpartlevel.do","uploadList","450","170","yes","80","80");
    showTransitWin(messagesData.pleasewait);
}

function checkCompanyPermission(selectedCompany) {
    var found = false;
	for (var i = 0; i < companyPermArr.length; i++ ) {
		if(companyPermArr[i] == selectedCompany) {
    		found = true; 
		}
    }
    if(found) {
      try {
    	  document.getElementById("showHideBtn").style.display="";
      } catch(ex) {}    
    }
    else {
      try {
    	  document.getElementById("showHideBtn").style.display="none";
      } catch(ex) {}
    }
  }   