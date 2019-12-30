
function init() {
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
	 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}

	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	I they are not initialized onLoad they tend to act weird*/
	showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,
	draggable:true, modal:false } );
	showErrorMessagesWin.render();
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}

function submitUpdate() {
    //var auditResult = window.frames["resultFrame"].validateInputData();
	    var now = new Date(); 
      window.frames["searchFrame"].document.getElementById("startSearchTime").value = now.getTime();
      /*Set any variables you want to send to the server*/
      var action = window.frames["resultFrame"].document.getElementById("action");
      action.value = 'update';
      showPleaseWait();
      /*Submit the form in the result frame*/
      window.frames["resultFrame"].document.genericForm.submit();
}

function confirmShipment() {
	  var transportationMode = window.frames["resultFrame"].document.getElementById("transportationMode");
		var sourceHub = window.frames["resultFrame"].document.getElementById("sourceHub");
	  var loc = "packconfirmshipment.do?action=packConfirmShipment&transportationMode="+transportationMode.value+"&sourceHub="+sourceHub.value;
    openWinGeneric(loc,"showPackConfirmShipment22","800","500","yes","80","80")
}

function reprintPalletMsl() {
    /*Set any variables you want to send to the server*/
    var action = window.frames["resultFrame"].document.getElementById("action");
    action.value = 'reprintPalletMsl';
    showPleaseWait();
    /*Submit the form in the result frame*/
    window.frames["resultFrame"].document.genericForm.submit();
}

function requestWAWFInsRequest() {
    var auditData = true;
    if(window.frames["resultFrame"].wawfInsRequestAudit) {
        auditData = window.frames["resultFrame"].wawfInsRequestAudit();
    }

    if (auditData) {
        /*Set any variables you want to send to the server*/
        var action = window.frames["resultFrame"].document.getElementById("action");
        action.value = 'requestWAWFInsRequest';
        showPleaseWait();
        /*Submit the form in the result frame*/
        window.frames["resultFrame"].document.genericForm.submit();
    }
}

function printHazardousGoodsManifest() {
    /*Set any variables you want to send to the server*/
    var action = window.frames["resultFrame"].document.getElementById("action");
    action.value = 'printHazardousGoodsManifest';
    showPleaseWait();
    /*Submit the form in the result frame*/
    window.frames["resultFrame"].document.genericForm.submit();
}
