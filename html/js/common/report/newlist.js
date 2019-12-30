var children = new Array(); 

function closeAllchildren() 
{ 
	//if (document.getElementById("uAction").value != "search") 
	{
		try {
			for(var n=0;n<children.length;n++) {
				children[n].closeAllchildren(); 
			}
		} catch(ex)
		{
		}
		if(!window.closed)
			window.close();
	} 
} 
	
function newList() {
	document.genericForm.target='';
	document.getElementById("uAction").value = "addnewlist";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");    
	startSearchTime.value = now.getTime();
   var flag = validateForm();
	
	if(flag) 
	{
		showPleaseWait();
		document.genericForm.submit();  	   
   		return true;
  	}
  	else
  	{
    	return false;
  	}
		
}

function newListWinClose()
{
	if(closeNewListWin)
	{ 
		opener.showUpdateMessages($('errorMessagesAreaBody').innerHTML);
		window.close();	
				
	}
	else
	{
		if ( showErrorMessage ) 
	 		showErrorMessages();
	}
	
}


function validateForm() {
	
	var errorMsg = '';
	if( !$v("listName"))
		errorMsg += "\n"+messagesData.name;
	if( !$v('listDescription'))
		errorMsg += "\n"+messagesData.desc;
	if( errorMsg != '' ) {
		  alert(messagesData.validvalues+errorMsg);
		  return false;
	}
	return true;
}