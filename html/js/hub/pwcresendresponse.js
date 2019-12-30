function changeSearchAction() {
	var lookupAction = $v("lookupAction");
	if (lookupAction == "poLookup") {
		$("orderSearchDiv").style.display = "none";
		$("poSearchDiv").style.display = "";
	}
	else {
		$("orderSearchDiv").style.display = "";
		$("poSearchDiv").style.display = "none";
		if (lookupAction == "orderLookup") {
			$("orderNumberSearchLabel").innerHTML = messagesData.customerpo+": ";
		}
		else {
			$("orderNumberSearchLabel").innerHTML = messagesData.mr + "/" + messagesData.customerpo+": ";
		}
	}
}

function changeSearchType() {
	var value = $v("lookupBy");
	if (value == "mr") {
		$("orderNumberSearchLabel").innerHTML = messagesData.mrnumber+": ";
	}
	else {
		$("orderNumberSearchLabel").innerHTML = messagesData.customerpo+": ";
	}
}

function submitSearchForm()
{
 	var flag = validateForm();
  	if(flag) 
  	{
  		document.genericForm.target = "resultFrame";
  		$("action").value = "search";
  		if ($v("lookupAction") == "poLookup") {
  			$("lookupClass").value = "poLookup";
  		}
  		else if ($v("lookupAction") == "notifyError") {
  			$("lookupClass").value = "notifyError";
  		}
  		else {
  			$("lookupClass").value = "orderLookup";
  		}
   		showPleaseWait();
   		return true;
  	}
  	else
  	{
    	return false;
  	}
}

function validateForm()
{
	if ($v("lookupAction") == "poLookup" && $v("poNumberSearch").trim().length == 0) {
		alert(messagesData.required.replace('{0}',messagesData.ponumber));
		return false;
	}
	else if ($v("lookupAction") == "orderLookup" && $v("orderNumberSearch").trim().length == 0) {
		alert(messagesData.required.replace('{0}',messagesData.customerpo));
		return false;
	}
	else if ($v("lookupAction") == "shipped" && $v("orderNumberSearch").trim().length == 0) {
		alert(messagesData.required.replace('{0}',messagesData.mr + "/" + messagesData.customerpo));
		return false;
	}
	else if ($v("lookupAction") == "billed" && $v("orderNumberSearch").trim().length == 0) {
		alert(messagesData.required.replace('{0}',messagesData.mr + "/" + messagesData.customerpo));
		return false;
	}
	return true;
}

function reSearchPoNumber() {
	var loc = "/tcmIS/supply/searchposmain.do?searchFromPopup=true&po="+$v("po");
    openWinGeneric(loc,"showPos","800","450","yes","50","50","no");
}

function getPo() {
	var ponum = $v("poNumberSearch");
	if (ponum.trim().length > 0) {
		if (isFloat(ponum.trim())) {
			var loc = "/tcmIS/supply/purchaseorder.do?po="+ponum.trim();
			loc = loc + "&Action=searchlike&subUserAction=po";
			openWinGeneric(loc,"radianPO","50","50","yes")
		}
	}
}

function showResendFOALink(show) {
	if ($v("lookupAction") == "poLookup") {
		show = false;
	}
	var disp = show?"block":"none";
	$("resendFullOrderAckLink").style.display = disp;
	$("resetAndSendResponseLink").style.display = disp;
}

function resendFullOrderAck() {
	document.genericForm.target = "resultFrame";
	$("action").value = "resendFOA";
	if ($v("lookupAction") == "poLookup") {
		$("lookupClass").value = "poLookup";
	}
	else if ($v("lookupAction") == "notifyError") {
		$("lookupClass").value = "notifyError";
	}
	else {
		$("lookupClass").value = "orderLookup";
	}
	showPleaseWait();
	document.genericForm.submit();
}

//Function for updating records.
function update(){
    
	/*Set any variables you want to send to the server*/
	document.genericForm.target='';
	document.getElementById('action').value = 'update';
	showPleaseWait();
    mygrid.parentFormOnSubmit(); //prepare grid for data sending	 
    document.genericForm.submit();
}

function showResend() {
	$("resendFullOrderAckLink").style.display = "inline";
	$("resetAndSendResponseLink").style.display = "inline";
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 64);
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
