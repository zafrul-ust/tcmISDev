function lookupCustomer() {    
	  loc = "../distribution/customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
	  openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}
function clearCustomer() {    

	$('customerName').value = '';
	$('customerName').className = 'inputBox';
	$('customerId').value = '';

}

function customerChanged(id,value){
 $('customerName').className = 'inputBox';
 if ($('shipToBtnDisplay') != null) {
    if( $v('customerId') )
    	$('shipToBtnDisplay').style.display = "";
    else
    	$('shipToBtnDisplay').style.display = "none";
  }
}

function submitValidateSearchForm() {
  if (validateForm())
    submitSearchForm();
  else 
    return false;
}

function validateForm() {
  var errormessage = messagesData.validvalues + "\n\n";
  if ($("idField").value.trim().length > 0 && ($("documentType").value == "shipmentId" || $("documentType").value == "mr"))
  {
	  if(!isFloat($("idField").value.trim(), true)) {
	    alert(errormessage+$("documentType").options[$("documentType").selectedIndex].text);
	    return false;
	  }
  }
  $("idField").value = $("idField").value.trim();
  return true;
}

defaults.ops.nodefault = true;
//defaults.hub.nodefault = true;
//defaults.ops.callback = setCsr;

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

var children= new Array();
function closeAllchildren()
{
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
		
//	} 
}

function addressChanged(s) {
	closeTransitWin();
//	$("locationType").value = s.locationType;
//	$("billToAddress").value = s.billToAddress;
	$("shipToLocationId").value = s.shipToLocationId; 
//	$("shipToCompanyId").value = s.shipToCompanyId; 
	$("shipToName").value = s.locationDesc; 
	
//	shipToLocationId
	
}

function changeShipTo() {
	  var customerId = $v("customerId");
	  var companyId = $v("companyId");
	  
	  if(customerId.length == 0) {
	    alert(messagesData.pickcustomerid);
	    return false;
	  } else {
//	    getTabName();
//	    $("shipToUpdatable").value = 'N';
	    showTransitWin(messagesData.shiptoaddress);
	    tabName = "PrintInvoice";
	    loc = "shiptoaddress.do?uAction=search&customerId="+customerId+
//	    	"&companyId="+companyId+
	    	"&locationType="+encodeURIComponent("Ship To")+
	    	"&tabName="+encodeURIComponent(tabName);
	  } 
	  var winId= 'shipToAddress_forInvoice';
	  children[children.length] = openWinGeneric(loc,winId.replace('.','a'),"700","350","yes","50","50","no"); 
	}

function clearShipTo() {
	$('shipToLocationId').value = '';
	$('shipToName').value = '';
}

function showName(){
	$('printBatch').style.display = "";
	
}

function uncheckOtherCheckBox(id,checked)
{
	if(id == 'showEInvoices' && checked)
		$('showEInvoicesNotSent').checked = false;
	else if(id == 'showEInvoicesNotSent' && checked)
		$('showEInvoices').checked = false;
}


