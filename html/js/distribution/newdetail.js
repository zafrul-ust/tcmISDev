windowCloseOnEsc = true;

function validateForm()
{
	// validates the search input field
	var errorMessage = messagesData.validvalues + "\n\n";
	var errorCount = 0;
	
	try
	{
	 
	 if ( $v("specId").length == 0  )
	 {
	    errorMessage +=  messagesData.spec + "\n";
	    errorCount = 1;	    
	 }
	 if (($v("specDetailType") == '') && ($v("specDetailClass") == '') && ($v("specDetailForm") == '')
			 && ($v("specDetailGroup") == '') && ($v("specDetailGrade") == '') && ($v("specDetailStyle") == '')
			 && ($v("specDetailFinish") == '') && ($v("specDetailSize") == '') && ($v("specDetailColor") == '')
			 && ($v("specDetailOther") == ''))
	 {
	    errorMessage +=  messagesData.detail + "\n";
	    errorCount = 2;	    
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


function addNewDetail()
{		
	document.genericForm.target='';
	var action = document.getElementById("uAction");	
	action.value="addDetail";	
	// var now = new Date();
    // var startSearchTime = document.getElementById("startSearchTime");    
	// startSearchTime.value = now.getTime();
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


function clearField() {
	$("specDetailType").selectedIndex = 0;
	$("specDetailClass").selectedIndex = 0;
	$("specDetailForm").selectedIndex = 0;
	$("specDetailGroup").selectedIndex = 0;
	$("specDetailGrade").selectedIndex = 0;
	$("specDetailStyle").selectedIndex = 0;
	$("specDetailFinish").selectedIndex = 0;
	$("specDetailSize").selectedIndex = 0;
	$("specDetailColor").selectedIndex = 0;
	$("specDetailOther").value="";
}


function newSpecWinClose()
{
	if(closeNewSpecWin && !showErrorMessage)
	{ 
		
		if(fromReceiptSpec)
		{
			opener.opener.addNewReceiptSpec($v("specId"),$v("specName"), "", $v("specDetailType"), $v("specDetailClass"),
					$v("specDetailForm"), $v("specDetailGroup"), $v("specDetailGrade"), $v("specDetailStyle"), $v("specDetailFinish"),
					$v("specDetailSize"), $v("specDetailColor"), $v("specDetailMethod"), $v("specDetailCondition"),
					$v("specDetailDash"), $v("specDetailNote"), $v("specDetailOther"), $v("specLibrary"), $v("specLibraryDesc"), "", "",$v("specVersion"),$v("specAmendment"),'',"Y");
		}
		else if(fromSpecQC){
			opener.updateSpecDetail("", $v("specDetailType"), $v("specDetailClass"),
					$v("specDetailForm"), $v("specDetailGroup"), $v("specDetailGrade"), $v("specDetailStyle"), $v("specDetailFinish"),
					$v("specDetailSize"), $v("specDetailColor"), $v("specDetailOther"));
		}
		else
		{
			opener.opener.addNewRow($v("specId"), $v("specName"), "", $v("specDetailType"), $v("specDetailClass"),
					$v("specDetailForm"), $v("specDetailGroup"), $v("specDetailGrade"), $v("specDetailStyle"), $v("specDetailFinish"),
					$v("specDetailSize"), $v("specDetailColor"), $v("specDetailMethod"), $v("specDetailCondition"),
					$v("specDetailDash"), $v("specDetailNote"), $v("specDetailOther"),
					$v("specLibraryDesc"), $v("specLibrary"), "", "", "", $v("specVersion"), $v("specAmendment"),"Y");
		}	
		
		opener.window.close();		
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




