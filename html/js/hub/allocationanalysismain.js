
function init() {
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
	 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}
	mrLineNotesWin = new YAHOO.widget.Panel("mrLineNotesArea", { width:"300px",height:"200px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
	mrLineNotesWin.render();
	
	legendWin = new YAHOO.widget.Panel("showLegendArea", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
	legendWin.render();
	
	showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
	showErrorMessagesWin.render();

}

function showAllNotes(comments, mrLine)  
{ 
  var mrLineNotesAreaTitle = document.getElementById("mrLineNotesAreaTitle");
  var mrLineNotesAreaBody = document.getElementById("mrLineNotesAreaBody");
  mrLineNotesAreaTitle.innerHTML = messagesData.notes + " " + mrLine; 	         
  mrLineNotesAreaBody.innerHTML = comments; 
  
  var mrLineNotesArea = document.getElementById("mrLineNotesArea");
  mrLineNotesArea.style.display="";

  mrLineNotesWin.show();
}

function showAllocationLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";
  legendWin.show();
}

