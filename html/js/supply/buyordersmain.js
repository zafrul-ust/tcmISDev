function submitUpdate() {
	submitPage("update");
}

function submitConvert() {
	submitPage("convertPurchase");
}

function submitPage(submissionType) {
    /*Set any variables you want to send to the server*/
    var action = window.frames["resultFrame"].document.getElementById("action");
    action.value = submissionType;
    showPleaseWait();
    /*Submit the form in the result frame*/
    window.frames["resultFrame"].document.buyOrderForm.submit();
}

function createPo() {
    /*Set any variables you want to send to the server*/
	 if (window.frames["resultFrame"].validateCreatePo()) {
		 var action = window.frames["resultFrame"].document.getElementById("action");
		 action.value = 'createPo';
		 showPleaseWait();
		 /*Submit the form in the result frame*/
		 window.frames["resultFrame"].document.buyOrderForm.submit();
	 }
}

function showBuyPageLegend() {
//  openWinGeneric("/tcmIS/help/buypagelegend.html","buypagelegend","290","400","no","50","50")
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";
  legendWin.show();
}

function init() { /*This is to initialize the YUI*/
  this.cfg = new YAHOO.util.Config(this);
  if (this.isSecure) {
    this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
  }
	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	If they are not initialized onLoad they tend to act weird*/
  legendWin = new YAHOO.widget.Panel("showLegendArea", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
  legendWin.render();

  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,draggable:true, modal:false } );
  showErrorMessagesWin.render();
}

function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaTitle = document.getElementById("errorMessagesAreaTitle");
  errorMessagesAreaTitle.innerHTML = messagesData.errors;
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}