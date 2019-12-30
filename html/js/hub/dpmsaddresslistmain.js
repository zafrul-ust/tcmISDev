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

function selectAddress(type) {
    if('nododaac2' == type) {
        var selectedDodaac = window.frames["resultFrame"].document.getElementById("selectedDodaac");
        var selectedDodaacType = window.frames["resultFrame"].document.getElementById("selectedDodaacType");
        var selectedLocationId = window.frames["resultFrame"].document.getElementById("selectedLocationId");
        var selectedAddress = window.frames["resultFrame"].document.getElementById("selectedAddress");
        var openerDodaac = opener.document.getElementById("mfDodaac");
        openerDodaac.value = selectedDodaac.value;
        var openerDodaacType = opener.document.getElementById("mfDodaacType");
        openerDodaacType.value = selectedDodaacType.value;
        var openerLocationId = opener.document.getElementById("mfLocationId");
        openerLocationId.value = selectedLocationId.value;
        var openerAddress = opener.document.getElementById("mfAddress");
        openerAddress.value = selectedAddress.value;
    }
    else {
        var selectedDodaac = window.frames["resultFrame"].document.getElementById("selectedDodaac");
        var selectedDodaacType = window.frames["resultFrame"].document.getElementById("selectedDodaacType");
        var selectedLocationId = window.frames["resultFrame"].document.getElementById("selectedLocationId");
        var selectedAddress = window.frames["resultFrame"].document.getElementById("selectedAddress");
        var openerDodaac = opener.document.getElementById("dodaac");        
        openerDodaac.value = selectedDodaac.value;
        var openerDodaacType = opener.document.getElementById("dodaacType");
        openerDodaacType.value = selectedDodaacType.value;
        var openerLocationId = opener.document.getElementById("locationId");
        openerLocationId.value = selectedLocationId.value;
        var openerAddress = opener.document.getElementById("address");
        openerAddress.value = selectedAddress.value;
    }
    this.close();
}

