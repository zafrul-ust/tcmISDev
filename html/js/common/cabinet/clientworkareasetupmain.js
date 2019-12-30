var children = new Array();

function closeAllchildren() 
{ 
	if (document.getElementById("uAction").value != "search") {
		try {
			for(var n=0;n<children.length;n++) {
				children[n].closeAllchildren(); 
			}
		} catch(ex)
		{
		}
		if(!window.closed)
			window.close();
	} 
} 

function addWorkAreaGroupIfNew(companyId,facility, id, desc) {
	/*var applicationArray = altApplicationId[facility];
	var applicationDescArray = altApplicationDesc[facility];

	var isNew = true;
	for (var i = 0; i < applicationArray.length;i++) {
		if (applicationArray[i] == id) {
			isNew = false;
			break;
		}
	}*/
	
	var wagArray = wagColl[companyId+facility];	
	var isNew = true;
	for (var i = 0; i < wagArray.length;i++) {
		if (wagArray[i].wagId == id) {
			isNew = false;
			break;
		}
	}
	if(isNew)
	{
			wagArray[wagArray.length] = {wagName:desc,
					  wagId:id};
			showWagOptions(companyId+facility);
	}
}

function myOnLoad() {
	// load drop downs
	showCompanyOptions();
}

function search() {
	var now = new Date();
	$("startSearchTime").value = now.getTime();
	var app = document.getElementById('reportingEntityId');
	$("lastSearchWAG").value = app.options[app.selectedIndex].text;
	var fac = document.getElementById("facilityId");
	$("lastSearchFacility").value = fac.options[fac.selectedIndex].text;
	document.cabinetSetupForm.target='resultFrame';
	$("uAction").value = 'search';
	showPleaseWait();
	document.cabinetSetupForm.submit();
}

function generateExcel() {
    $("uAction").value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_cabinetDefinition','650','600','yes');
    document.cabinetSetupForm.target='_cabinetDefinition';
    window.setTimeout("document.cabinetSetupForm.submit();",500);
}

function showLegend() {
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  initializeDhxWins();
  
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
  else {
	    // just show
	    dhxWins.window("legendwin").show();
  }
}

//delay since onunload is called before page is actually closed
function newCloseTransitWin(callerId) {
	window.setTimeout(function() {closeMyTransitWin(callerId)}, 500);
}

function closeMyTransitWin(callerId) {
	if (dhxWins != null) {
		if (callerId != null) {
			var isOpened = false;
			//if page is closed, accessing it will raise error
			try {
				for (var i = 0; i < children.length; i++)
					if (children[i] && children[i].name == callerId) {
						isOpened = true;
						break;
					}
			} catch (e) {}
			
			if (isOpened)
				return;
		}
		
		if (dhxWins.window("transitDialogWin")) {
			dhxWins.window("transitDialogWin").setModal(false);
			dhxWins.window("transitDialogWin").hide();
		}
	}
}