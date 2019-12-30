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

function submitSelection() 
{
    var userAction = window.frames["resultFrame"].document.getElementById("userAction");
    userAction.value = 'update';
    
    showPleaseWait();
    window.frames["resultFrame"].document.genericForm.submit();
}

var returnSelectedCasenumName=null;
var returnSelectedCasenumId=null;

function selectedCasenum()
{ 
  	try 
  	{
  		var openervalueElementId = opener.document.getElementById(valueElementId);
  		openervalueElementId.value = returnSelectedCasenumId;
  		var openerdisplayElementId = opener.document.getElementById(displayElementId);
  		openerdisplayElementId.value = returnSelectedCasenumName;
  		//reset valiable
  		returnSelectedCasenumName = null;
  		returnSelectedCasenumId=null;
  		valueElementId=null;
  		displayElementId=null;
  	} 
  	catch( ex ) 
  	{ 
  	//alert("caught exception in try block 1;");
  	}
  
  	try 
  	{
  		if( window.opener.personnelChanged ) 
  		{
  			window.opener.personnelChanged(returnSelectedCasenumId);
  		}
  	} 
  	catch(exx) 
  	{ 
  	//alert("caught exception in try block 2;");
  	}
 	window.close();
}
