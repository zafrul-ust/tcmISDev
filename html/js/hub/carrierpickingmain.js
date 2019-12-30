
function init() {
this.cfg = new YAHOO.util.Config(this);
if (this.isSecure)
{
 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
}
deliveryCommentsWin = new YAHOO.widget.Panel("deliveryCommentsMessagesArea", { width:"300px",height:"200px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
deliveryCommentsWin.render();
}

function showDeliveryComments(notes)  
{  
  var deliveryCommentsMessagesAreaBody = document.getElementById("deliveryCommentsMessagesAreaBody");
  deliveryCommentsMessagesAreaBody.innerHTML = notes;

  var deliveryCommentsMessagesArea = document.getElementById("deliveryCommentsMessagesArea");
  deliveryCommentsMessagesArea.style.display="";

  deliveryCommentsWin.show();
}
