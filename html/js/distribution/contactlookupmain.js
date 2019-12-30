var children = new Array();

windowCloseOnEsc = true;

var dhxFreezeWins = null;

function fixCustomerIdIfExisted()

{
  if($v('customerId').length > 0)
  {
    $("customerIdLabel").style.display="none";
  	$("customerIdSearch").style.display="none";
  	$("customerNameLabel").style.display="none";
  	$("customerNameSearch").style.display="none";
  	$("id1").style.display="none";
  	$("id2").style.display="none";
  	submitSearchForm();
  }
  else
  {
  	$("btnNewContactSpan").style.visibility = "hidden";
  }
}

function submitSearchForm()
{
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  document.getElementById("selectedContact").innerHTML = "";

  if(validateSearchForm()) { 
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   document.genericForm.submit();
  }
  else
  {
    return false;
  }
}

function validateSearchFormXSL()
{
  if( validateSearchForm() ) {
      document.getElementById("uAction").value = "createExcel"; 
		document.genericForm.target='_ContactLookUpExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ContactLookUpExcel','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	}
}

function validateSearchForm()
{
	return true;
}


var returnSelectedContactId=null;
var returnSelectedContactName=null;
var returnSelectedCustomerId=null;
var returnSelectedAddressLine1=null;
var returnSelectedAddressLine2=null;
var returnSelectedAddressLine3=null;
var returnSelectedAddressLine4=null;
var returnSelectedAddressLine5=null;
var returnSelectedBillToCompanyId=null;
var returnSelectedBillToLocationId=null;
var returnSelectedPhone=null;
var returnSelectedFax=null;
var returnSelectedEmail=null;
var valueElementId=null;
var displayArea=null;

function selectedContact()
{ 	
  try {
	  var openervalueElementId = opener.document.getElementById(valueElementId);
	  if (openervalueElementId)
		  openervalueElementId.value = returnSelectedContactId;
  } catch( ex ) {}
  try {
	  var openerdisplayElementId = opener.document.getElementById(displayElementId);
	  if (openerdisplayElementId) {
		  openerdisplayElementId.className = "inputBox";
		  openerdisplayElementId.value = returnSelectedContactName;
	  }
  } catch( ex ) {}
  try {
	  var openerdisplayArea = opener.document.getElementById($('displayArea').value); 
	  if (openerdisplayArea) {
		  openerdisplayArea.value = returnSelectedContactName;
	  }
  } catch( ex ) {}
   
  try {
  	if( window.opener.contactChanged ) {
    	var a = new contactinfo();
  		window.opener.contactChanged(a);
  	}
  } catch(exx) {}
 
  window.close();
}

function contactinfo() {
    this.customerId = returnSelectedCustomerId;
	this.billToAddressLine1 = returnSelectedAddressLine1;
	this.billToAddressLine2 = returnSelectedAddressLine2; 
	this.billToAddressLine3 = returnSelectedAddressLine3; 
	this.billToAddressLine4 = returnSelectedAddressLine4;
	this.billToAddressLine5 = returnSelectedAddressLine5;
	this.billToCompanyId = returnSelectedBillToCompanyId;
	this.billToLocationId = returnSelectedBillToLocationId;
	this.contactId = returnSelectedContactId; 
	this.contactName = returnSelectedContactName; 
	this.phone = returnSelectedPhone;
	this.fax = returnSelectedFax;
	this.email = returnSelectedEmail;
  } 

function newContact()
{  
	loc = "newcustomercontact.do?fromContactLookup=Yes&customerId=" + $v("customerId")+"&billToCompanyId="+ $v("billToCompanyId");
 	children[children.length] = openWinGeneric(loc,"NewCustomerContact","800","250","yes","50","50");
 	showTransitWin(messagesData.newcontact);
}

function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
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

}

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
