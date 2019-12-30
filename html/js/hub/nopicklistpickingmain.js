function init() {
this.cfg = new YAHOO.util.Config(this);
if (this.isSecure)
{
 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
}

/*Yui pop-ups need to be initialized onLoad to make them work correctly.
I they are not initialized onLoad they tend to act weird*/
legendWin = new YAHOO.widget.Panel("showLegendArea", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
legendWin.render();

showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"200px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
showErrorMessagesWin.render();
}

function showPickingLegend()
{
var showLegendArea = document.getElementById("showLegendArea");
showLegendArea.style.display="";

legendWin.show();
}

function doConfirm()
{
    /*Set any variables you want to send to the server*/
    var action = window.frames["resultFrame"].document.getElementById("confirm");
    action.value = 'confirm';
    showPleaseWait();
    /*Submit the form in the result frame*/
    window.frames["resultFrame"].document.genericForm.submit();
//    document.genericForm.submit();
    return true;
}

function printBols()
{
    var testurl3 = "bolpage?UserAction=generatebols";
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}

function printboxLabels()
{
    var testurl3 = "bolpage?UserAction=generateboxlabels";
    openWinGeneric(testurl3,"Generate_Boxlabels","640","600","yes")
}

function printPackingList()
{
    var batchNumber = window.frames["resultFrame"].document.getElementById("batchNumber");
    var launchUrl = "nopicklistpackinglist.do?userAction=nopicklistpackinglist&batchNumber="+batchNumber.value;
    openWinGeneric(launchUrl,"Generate_Boxlabels","800","600","yes")
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