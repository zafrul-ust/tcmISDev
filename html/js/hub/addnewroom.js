function validateForm()
{
	// validates the search input field
	var errorMessage = messagesData.validvalues + "\n\n";
	var errorCount = 0;
	
	try
	{
	 var searchInput = document.getElementById("room");	 
	 if ( searchInput.value.trim().length == 0  )
	 {
	    errorMessage +=  messagesData.room + "\n";
	    errorCount = 1;	    
	 }

	 
	} catch (ex) {		 
	  errorCount++;
	}
	if (errorCount >0)
	 {
		alert(errorMessage);
	    return false;
	 }	
    return true;
}


function newRoom()
{	
	document.genericForm.target='';
	var userAction = $('userAction');
	userAction.value="addNewRoom";
	
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

function newRoomWinClose()
{
	if(closeNewRoomWin)
	{ 
		var room =  $v('room');	
		var sourceHub=$v('sourceHub');
	    var sourceHubName=$v('sourceHubName');
		window.opener.refreshAddBinsPage(closeNewRoomWin,room, sourceHub,sourceHubName );    	   
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
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 380, 120);
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

