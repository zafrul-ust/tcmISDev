function init() {
this.cfg = new YAHOO.util.Config(this);
if (this.isSecure)
{
 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
}

/*Yui pop-ups need to be initialized onLoad to make them work correctly.
I they are not initialized onLoad they tend to act weird*/
legendWin = new YAHOO.widget.Panel("showLegendArea", { width:"450px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
legendWin.render();

deliveryCommentsWin = new YAHOO.widget.Panel("deliveryCommentsMessagesArea", { width:"300px",height:"200px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
deliveryCommentsWin.render();

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

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  legendWin.show();
}

function showDeliveryComments(notes)
{  
  var deliveryCommentsMessagesAreaBody = document.getElementById("deliveryCommentsMessagesAreaBody");
  deliveryCommentsMessagesAreaBody.innerHTML = notes;

  var deliveryCommentsMessagesArea = document.getElementById("deliveryCommentsMessagesArea");
  deliveryCommentsMessagesArea.style.display="";

  deliveryCommentsWin.show();
}