var dhxWins = null;

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 document.getElementById("uAction").value = 'search';
 var hub = document.getElementById("hub");
	document.getElementById("hubName").value = hub[hub.selectedIndex].text;
	var carrier = document.getElementById("carrierAccountId");
	document.getElementById("carrierName").value = carrier[carrier.selectedIndex].text; 
 //set start search time
 var now = new Date();
 var startSearchTime = document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var flag = validateForm();
 if(flag) {
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateForm(){
    return true;
}

function createXls() {
 	document.getElementById("uAction").value = 'createExcel';
 	var flag = validateForm();
 	if(flag) {
		var hub = document.getElementById("hub");
	 	document.getElementById("hubName").value = hub[hub.selectedIndex].text;
		var carrier = document.getElementById("carrierAccountId");
	 	document.getElementById("carrierName").value = carrier[carrier.selectedIndex].text; 
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_FreightTrackingGenerateExcel','650','600','yes');
    	document.genericForm.target='_FreightTrackingGenerateExcel';
    	var a = window.setTimeout("document.genericForm.submit();",500);
	}
} //end of method

function showHub() {
	 var hasAll = false;
	 if(altHubId.length == 0 || altHubId.length > 1)
    {
       setOption(0,messagesData.myHubs,"","hub");
		 hasAll = true;
	 }

	 if (hasAll) {
	 	for (var i=1; i <= altHubId.length; i++){
        setOption(i,altHubName[i-1],altHubId[i-1],"hub");
    	}
	 }else {
		for (var i=0; i < altHubId.length; i++){
        setOption(i,altHubName[i],altHubId[i],"hub");
    	}
	 }
	 var hubO = document.getElementById("hub");
	 hubO.selectedIndex = 0;
	 hubChanged();
}

function hubChanged()
{
   var hubO = document.getElementById("hub");
   var carrierNameO = document.getElementById("carrierAccountId");
   var selHub = hubO.value;
   for (var i = carrierNameO.length; i > 0;i--)
   {
     carrierNameO[i] = null;
   }

   if (selHub.length > 0 ) {
     showCarrierOptions(selHub);
   } else {
     setOption(0,messagesData.all,"","carrierAccountId");
   }
   carrierNameO.selectedIndex = 0;
}

function showCarrierOptions(selectedHub)
{
    var carrierAccountIdArray = new Array();
    carrierAccountIdArray = altCarrierAccountId[selectedHub];
	 var carrierNameArray = new Array();
    carrierNameArray = altCarrierName[selectedHub];
	 var hasAll = false;
	 if(carrierAccountIdArray.length == 0 || carrierAccountIdArray.length > 1) {
       setOption(0,messagesData.all,"","carrierAccountId");
		 hasAll = true;
	 }
	 if(hasAll) {
    	for (var i=1; i <= carrierAccountIdArray.length; i++) {
       setOption(i,carrierNameArray[i-1],carrierAccountIdArray[i-1],"carrierAccountId");
    	}
	 }else {
		for (var i=0; i < carrierAccountIdArray.length; i++) {
       setOption(i,carrierNameArray[i],carrierAccountIdArray[i],"carrierAccountId");
    	}
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
		dhxWins.window("transitDailogWin").show();
	}
}

function showLegend(){
	  var showLegendArea = document.getElementById("showLegendArea");
	  showLegendArea.style.display="";

	  initializeDhxWins();
	  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	  if (!dhxWins.window(messagesData.showlegend)) {
	  // create window first time
	  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 180);
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