function validateForm()
{
	
	if( $('itemIdRow').style.display == "")
	{		
		if( isEmpty($v('itemId')))
		{
		    	alert( messagesData.validvalues+"\n"+ messagesData.itemid);
		    	$("itemId").focus();
		    	return false;
		}
		
		if( !isNonnegativeInteger($v('itemId'),true))
		{
		    	alert( messagesData.itemInteger );
		    	$("itemId").focus();
		    	return false;
		}
		else
		{
			
			 return true;
		}
	}
	else
	{
    return true;
	}
}


function addDateCount()
{	
	document.genericForm.target='';
	var action = document.getElementById("uAction");	
	action.value="insertCountDate";
	
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");    
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	
	if(flag) 
	{
		showTransitWin();
		document.genericForm.submit();  	   
   		return true;
  	}
  	else
  	{
    	return false;
  	}
}

function newAddDateCountWin()
{
	if(closeAddDateCountWin)
	{ 
		 	   
        window.close();
		
	}

	
	if (showErrorMessage)
 {
  setTimeout('showCarrierErrorMessages()',50); /*Showing error messages if any*/
 }
}	

function showCarrierErrorMessages()
{
  var resulterrorMessages = document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window("errorWin")) {
  // create window first time
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 320, 150);
  errorWin.setText(messagesData.errors);
  errorWin.clearIcon();  // hide icon
  errorWin.denyResize(); // deny resizing
  errorWin.denyPark();   // deny parking
  errorWin.attachObject("errorMessagesArea");
  errorWin.attachEvent("onClose", function(errorWin){errorWin.hide();});
  errorWin.center();
  }
  else
  {
    // just show
    dhxWins.window("errorWin").show();
  }
}

function showTransitWin() {
	  resizeFramecount = 1;
	  $("searchFrameDiv").style["display"] = "none";
	  $("transitPage").style["display"] = "";
	}


function setItemId()
{
	var countType = $v('countType');
	if (countType =="Single Item") {
	   $('itemIdRow').style.display="";
	  }	 else {
		  $('itemIdRow').style.display="none";
	  }
}
