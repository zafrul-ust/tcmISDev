var selectedrow=null;

function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
 if (showErrorMessage)
 {
  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
  showErrorMessagesWin.render();
  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
 }
}

function myOnload()
{
 setResultFrameSize();
}

function submitSearchForm()
{
 var flag = validateForm();
  if(flag) {
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function cancel()
{
    parent.window.close();
}

function catchevent(rowid)
{
    //highlight selected row
    var selectedRow = document.getElementById("rowId"+rowid+"");
    var selectedRowClass = document.getElementById("colorClass"+rowid+"");
    selectedRow.className = "selected"+selectedRowClass.value+"";

    //update selected manifest display
    var selectedManifest = parent.document.getElementById("selectedManifest");
    var currentmanifest = document.getElementById("manifest"+rowid+"");
    selectedManifest.innerHTML = '<a href="#" onclick="selectedManifest(); return false;">'+messagesData.selectedManifest+' : '+currentmanifest.value+'</a>';
    parent.returnselectedmanifest = currentmanifest.value;

    //reset previous selected row back to it original color
    if (selectedrow != null && rowid != selectedrow)
    {
       var previouslySelectedRow = document.getElementById("rowId"+selectedrow+"");
       var previouslySelectedRowClass = document.getElementById("colorClass"+selectedrow+"");
       previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
    }
    selectedrow =rowid;
}

function getManifestSearchCalendar(dataFieldName)
{
    var notShip = document.getElementById("notShip");
    if (!notShip.checked) {
       getCalendar(dataFieldName);
    }
    return false;
}

function notShipClicked()
{
   var notShip = document.getElementById("notShip");
   var shipDateLabel = document.getElementById("shipDateLabel");
   var shippedToStartDate = document.getElementById("shippedToStartDate");
   var linkshippedToStartDate = document.getElementById("linkshippedToStartDate");
   var shipDateAndLabel = document.getElementById("shipDateAndLabel");
   var shippedToEndDate = document.getElementById("shippedToEndDate");
   var linkshippedToEndDate = document.getElementById("linkshippedToEndDate");
   if (notShip.checked) {
      shipDateLabel.disabled = true;
      shippedToStartDate.disabled = true;
      linkshippedToStartDate.disabled = true;
      shipDateAndLabel.disabled = true;
      shippedToEndDate.disabled = true;
      linkshippedToEndDate.disabled = true;
   }else {
      shipDateLabel.disabled = false;
      shippedToStartDate.disabled = false;
      linkshippedToStartDate.disabled = false;
      shipDateAndLabel.disabled = false;
      shippedToEndDate.disabled = false;
      linkshippedToEndDate.disabled = false;
   }
}

function validateForm() {
  return true;
} //end of validateForm

