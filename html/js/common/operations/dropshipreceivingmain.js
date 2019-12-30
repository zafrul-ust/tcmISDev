var children = new Array();

function init() { /*This is to initialize the YUI*/
  this.cfg = new YAHOO.util.Config(this);
  if (this.isSecure)
  {
    this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
  }

  /*Yui pop-ups need to be initialized onLoad to make them work correctly.
  If they are not initialized onLoad they tend to act weird*/
  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"200px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,draggable:true, modal:false } );
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
    //set start search time
	if(validate())
	{
	    var now = new Date();
	    var startSearchTime = window.frames["searchFrame"].document.getElementById("startSearchTime");
	    startSearchTime.value = now.getTime();
	
	    var action = window.frames["resultFrame"].document.getElementById("action");
	    action.value = 'receive';
	
	    var duplicateLine = window.frames["resultFrame"].document.getElementById("duplicateLine");
	    duplicateLine.value = "";
	
	    var duplicatePkgLine = window.frames["resultFrame"].document.getElementById("duplicatePkgLine");
	    duplicatePkgLine.value = "";
	
	    var duplicateKitLine = window.frames["resultFrame"].document.getElementById("duplicateKitLine");
	    duplicateKitLine.value = "";
	
	    showPleaseWait();
	    window.frames["resultFrame"].document.genericForm.submit();
	}
} //end of method

function validate()
{
	var lines = window.frames["resultFrame"].document.getElementById("totalLines").value;
	var isOneSelected = false;
	for(var i = 0; i < lines; i++)
	{
		var ok = window.frames["resultFrame"].document.getElementById("ok"+i);
		if(ok != null && typeof(ok) != 'undefined' && ok.checked)
			isOneSelected = true;
	}
	
	if(!isOneSelected)
		alert(messagesData.novalidlineselected);
	else
		return true;
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


