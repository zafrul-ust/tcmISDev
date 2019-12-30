function init() {
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
	 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}
	
	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	I they are not initialized onLoad they tend to act weird*/
	
	showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
	showErrorMessagesWin.render();

	try {
	legendWin = new YAHOO.widget.Panel("showLegendArea", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
	legendWin.render();
	} catch(ex){}
	
	try {
	showNotesWin = new YAHOO.widget.Panel("showNotesArea", { width:"300px",height:"200px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
	showNotesWin.render();
	} catch(ex){}

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

function call(name) {
	if( eval('getResultFrame().'+name) != null )
		eval('getResultFrame().'+name+'()');
}

function resultPageSubmit() {    
    window.frames["resultFrame"].document.genericForm.submit();
}

function showLegend()
{
  document.getElementById("showLegendArea").style.display="";
  legendWin.show();
}

function showNotes(notes)
{
  document.getElementById("showNotesAreaBody").innerHTML = notes;
  document.getElementById("showNotesArea").style.display="";

  showNotesWin.show();
}
