function init() 
{
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
	 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}

	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	If they are not initialized onLoad they tend to act weird*/
	showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,
	draggable:true, modal:false } );
	showErrorMessagesWin.render();
}

/*The reason this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}
/*
function submitUpdate() 
{
    / *Set any variables you want to send to the server* /
    var action = window.frames["resultFrame"].document.getElementById("action");
    action.value = 'update';
    showPleaseWait();
    / *Submit the form in the result frame* /
    window.frames["resultFrame"].document.genericForm.submit();
}
*/

function selectedRow()
{ 
  try {
  var openervalueElementId = opener.document.getElementById(valueElementId);
  openervalueElementId.value = returnSelectedId;
  var openerdisplayElementId = opener.document.getElementById(displayElementId);
  openerdisplayElementId.className = "inputBox";
  openerdisplayElementId.value = returnSelectedValue;
  } catch( ex ) {}
  
  try {
  if( window.opener.itemChanged ) {
  	window.opener.itemChanged(returnSelectedId);
  }
  } catch(exx) {}
  window.close();
  //reset valiable
  returnSelectedValue = null;
  returnSelectedId=null;
  valueElementId=null;
  displayElementId=null;
}
