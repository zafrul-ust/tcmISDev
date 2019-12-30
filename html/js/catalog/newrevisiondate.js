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

function validateForm(){
	var newDate = document.getElementById("revDate").value;
	var newAuthorId = document.getElementById("msdsAuthorId").value;
	var message = '';
	
	if(newDate == null || newDate.length < 1)
		message += messagesData.revDateRequired + '\n';
	
	if(newAuthorId == null || newAuthorId < 1)
		message += messagesData.authorRequired + '\n';
	
	if (message.length > 1) {
		alert(message);
		return false;
	}
	return true;
}

function doSubmit(ok) {
	if(ok){
		if (validateForm()) 
	  	{
	  		document.getElementById("uAction").value  =  'submit';
	    	//userAction.value = "new";
	  	
	   		//parent.showPleaseWait();
	    	document.genericForm.submit();
	  	}
	}
	else
		window.close();
}

function createNew() {
	document.getElementById("uAction").value = 'newmsdsauthor';
	document.genericForm.target = '';
	window.onunload=null;
	document.genericForm.submit();
}

function sameAsMfg() {
	/*document.getElementById("msdsAuthorId").value = document.getElementById("mfgId").value;
	document.getElementById("msdsAuthorDesc").value = document.getElementById("mfgDesc").value;
	document.getElementById("countryAbbrev").value = document.getElementById("mfgCountry").value;
	document.getElementById("notes").value = document.getElementById("mfgNotes").value;*/
	
	document.getElementById("msdsAuthorId").value = document.getElementById("mfgId").value;
	document.getElementById("msdsAuthorDesc").value = document.getElementById("dupDesc").value;
	document.getElementById("countryAbbrev").value = document.getElementById("dupCountry").value;
	document.getElementById("notes").value = document.getElementById("dupNotes").value;
}


