windowCloseOnEsc = true;

function myOnload() {
 	
	if ( done ) { 
 		
 		//window.opener.refreshPage(); 
		window.close();
	}
}	

function validateInput() {
	  var errorMsg = "";
	   if(isEmptyV("partSynonym"))
		   {
		    errorMsg += "\n"+messagesData.synonym;
		   }
	   if(!isEmptyV("partSynonym") && document.getElementById("partSynonym").value.trim().length >= 50)
		   {
		   errorMsg += "\n"+messagesData.maximumallowedtext;
		   }
	   if(isEmptyV("itemId")){
		   errorMsg += "\n"+messagesData.itemId; 
	   }

	  if( errorMsg != '' ) {
	   	alert(messagesData.validvalues+errorMsg);
	   	return false;
	  }
	  return true;
	}

function getItem() {
	var loc = "/tcmIS/distribution/itemsynonymlookupmain.do?sourcePage=addItem&uAction=new";
	
	var winId= 'itemsynonymlookupmain';
	openWinGeneric(loc,winId.replace('.','a'),"900","590","yes","50","50","20","20","no");
	
}

function itemChanged(itemId,itemDesc) {
	$('itemDesc').value = itemDesc;
	$('itemId').value = itemId;
}

function clearItem()
{
    document.getElementById("itemDesc").value = "";
    document.getElementById("itemId").value = "";
}

function newSynonym() {	
	if(validateInput())
	{		
	   showPleaseWait();
	   document.getElementById("uAction").value = "submit";
 	   window.setTimeout("document.genericForm.submit();",500);
 	  
	}
}
