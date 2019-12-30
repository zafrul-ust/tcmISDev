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

function submitNew()
{
 	var flag = validateForm();
  	if (flag) 
  	{
    	//var userAction  =  document.getElementById("userAction");
    	//userAction.value = "new";
  	
   		//parent.showPleaseWait();
   		return true;
  	}
    	
  	return false;

}

function validateForm()
{
	return true;
}


