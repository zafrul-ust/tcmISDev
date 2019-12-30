function validateForm()
{
	// validates the search input field
	var errorMessage = messagesData.validvalues + "\n\n";
	var errorCount = 0;
	
	try
	{
	 var searchInput = document.getElementById("carrierName");	 
	 if ( searchInput.value.trim().length == 0  )
	 {
	    errorMessage +=  messagesData.carriername + "\n";
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


function addNewCarrier()
{	
	document.genericForm.target='';
	var action = document.getElementById("action");	
	action.value="addNewCarrier";

	/*if(fromCustomerDetail)
	{	
	var fromCustomerDetail = $v('fromCustomerDetail');
	action.value="addNewCarrier?fromCustomerDetail="+fromCustomerDetail;
	}
	else
	{
	}	
	*/
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

function newCarrierWinClose()
{
	if(closeNewCarrierWin)
	{ 
		var currentCarrier =  document.getElementById("hCarrier").value;	
		window.opener.refreshPage(closeNewCarrierWin,currentCarrier );    	   
        window.close();
		/*if(fromCustomerDetail)
		{
			refreshPageWithCustId(closeNewCarrierWin,currentCarrier,$v('customerId'), $v('fromCustomerDetail'));  
		}
		else
		{	
			
		
		}*/
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

