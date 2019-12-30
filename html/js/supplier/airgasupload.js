function init() {

this.cfg = new YAHOO.util.Config(this);
if (this.isSecure)
{
 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
}

/*Yui pop-ups need to be initialized onLoad to make them work correctly.
I they are not initialized onLoad they tend to act weird*/
showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,
draggable:true, modal:false } );
showErrorMessagesWin.render();
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showMessages()
{
  if (showErrorMessage)
  {
    /*var errorMessagesArea = document.getElementById("errorMessagesArea");
    errorMessagesArea.class="";*/

    var errorMessagesArea = document.getElementById("errorMessagesArea");
    errorMessagesArea.style.display="";
    if (showErrorMessage)
    {
     setTimeout('showErrorMessagesWin.show()',100); /*Showing error messages if any*/
    }
  }
}

function checkInput()
{
	var errorMessage = messagesData.validvalues+"\n\n";
	var errorCount = 0;
	
	var theFile = document.getElementById("theFile");
	if (theFile.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.file ;
	 errorCount = 1;
	}
	
	if (errorCount >0)
	{
	  alert(errorMessage);
	  return false;
	}
	else
	{
		var extension = theFile.value.trim().split('.').pop();
		if(extension == 'xlsx' || extension == 'xls') {
			return true;
		} else {
			alert(messagesData.filetypenotallowed);
			return false;
		}
	}

}

