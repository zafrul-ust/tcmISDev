function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }

  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
  showErrorMessagesWin.render();
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}
// .................................
var selectedrow = null;

function mySearchOnLoad()
{
  	//setSearchFrameSize(); 	
}



function myResultsBodyOnload()
{
 setResultFrameSize();
}

function addComment() {
   //	parent.showPleaseWait();
   	return true;
}

