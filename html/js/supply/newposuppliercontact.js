windowCloseOnEsc = true;

function myOnload() {	
	if ( showErrorMessage ) 
 		showErrorMessages();
 
 
 	if ( done ) { 	
		window.opener.refreshPage( $v('supplier') );    	
		window.close();
	}
	
	if( modifyOpener ) {
		window.opener.modifyContact($v("contactId"), $v("lastName"),$v("firstName"),$v("nickname"),$v("contactType"),$v("phone"),$v("fax"),$v("email"));
		window.close();
	}
	
	if( addToOpener ) {
		window.opener.addContact($v("newContactId"),$v("lastName"),$v("firstName"),$v("nickname"),$("contactType").options[$("contactType").selectedIndex].text,$v("phone"),$v("fax"),$v("email"));
		window.close();
	}
}

function validateInput() {
  var errorMsg = "";
   if(isEmptyV("firstName")){
    errorMsg += "\n"+messagesData.firstnameRequired;
  }
  
  if(isEmptyV("lastName")){
    errorMsg += "\n"+messagesData.lastnameRequired;
  }
  if(isEmptyV("phone")) {
    errorMsg += "\n"+messagesData.phoneRequired;
  }
 
  if( errorMsg != '' ) {
   	alert(messagesData.validvalues+errorMsg);
   	return false;
  }
  return true;
}


function newContact() {	
	if(validateInput())
	{
		
	   showPleaseWait();	   
	 
	   
	  if($("actionType").value.trim().length >0 )
	  {	  
	    if($v("actionType")=="edit")
		 document.getElementById("action").value = "modify";
	    else if($v("actionType")=="new")
	     document.getElementById("action").value = "addAndShow";
	  
	  
	  }	  
  
	   document.genericForm.target='_self';
 	   window.setTimeout("document.genericForm.submit();",500);
	}
}



