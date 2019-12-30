windowCloseOnEsc = true;

function validateForm()
{
	if (!validateFileExtension()) return false;
	// validates the search input field
	var errorMessage = messagesData.validvalues + "\n\n";
	var errorCount = 0;
	
//	try
//	{
	 var searchInput = document.getElementById("specName");	 
	 if ( searchInput.value.trim().length == 0 )
	 {
	    errorMessage +=  messagesData.spec + "\n";
	    errorCount = 1;	    
	 }

	 if ( $v("specName").search(",") != -1 )
	 {
	    errorMessage =  messagesData.notusecommasinspec + "\n";
	    errorCount = 1;	    
	 }
	 
/*	 if (($v("specDetailType") == '') && ($v("specDetailClass") == '') && ($v("specDetailForm") == '')
			 && ($v("specDetailGroup") == '') && ($v("specDetailGrade") == '') && ($v("specDetailStyle") == '')
			 && ($v("specDetailFinish") == '') && ($v("specDetailSize") == '') && ($v("specDetailColor") == '')
			 && ($v("specDetailOther") == ''))
	 {
	    errorMessage +=  messagesData.detail + "\n";
	    errorCount = 2;	    
	 }*/

	 
//	} catch (ex) {		 
//	  errorCount++;
//	}
	if (errorCount >0)
	 {
		alert(errorMessage);
		$("specName").value = "";
	    return false;
	 }	
    return true;
}


function addNewSpec()
{		
	document.genericForm.target='';
	if($("specLibrary") == null || $v("specLibrary").length == 0) 
		$("uAction").value="addNewSpec";	
	else
		$("uAction").value="newSpecsfromOldSpec";	
		
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

function newSpecWinClose()
{	
	if($("specLibrary") != null && $v("specLibrary").length > 0) {
		$("specName").className="invGreyInputText";
      	$("specName").readOnly=true;
      	$("row3").style.display="none";
      	$("row1").style.display="none";
      	$("row2").style.display="none";
      	$("row4").style.display="";
	}
	else {
		$("specName").className="inputBox";
      	$("specName").readOnly=false;
      	$("row4").style.display="none";
	}
	
	if(closeNewSpecWin && !showErrorMessage)
	{ 	
		if(fromReceiptSpec)
		{
			if($v("uAction") == 'newSpecsfromOldSpec') {
				opener.addNewReceiptSpec($v("specId"),$v("specName"), "", $v("specDetailType"), $v("specDetailClass"),
					$v("specDetailForm"), $v("specDetailGroup"), $v("specDetailGrade"), $v("specDetailStyle"), $v("specDetailFinish"),
					$v("specDetailSize"), $v("specDetailColor"), $v("specDetailMethod"), $v("specDetailCondition"),
					$v("specDetailDash"), $v("specDetailNote"), $v("specDetailOther"), $v("specLibrary"), $v("specLibraryDesc"), "", "",$v("specVersion"),$v("specAmendment"),'');
			}
			else {
				opener.opener.addNewReceiptSpec($v("specId"),$v("specName"), "", $v("specDetailType"), $v("specDetailClass"),
					$v("specDetailForm"), $v("specDetailGroup"), $v("specDetailGrade"), $v("specDetailStyle"), $v("specDetailFinish"),
					$v("specDetailSize"), $v("specDetailColor"), $v("specDetailMethod"), $v("specDetailCondition"),
					$v("specDetailDash"), $v("specDetailNote"), $v("specDetailOther"), "temporary", "Temporary", "", "",$v("specVersion"),$v("specAmendment"),'');
				opener.window.close();
			}
		}                    
		else
		{
			if($v("uAction") == 'newSpecsfromOldSpec') {
				opener.addNewRow($v("specId"), $v("specName"), "", $v("specDetailType"), $v("specDetailClass"),
					$v("specDetailForm"), $v("specDetailGroup"), $v("specDetailGrade"), $v("specDetailStyle"), $v("specDetailFinish"),
					$v("specDetailSize"), $v("specDetailColor"), $v("specDetailMethod"), $v("specDetailCondition"),
					$v("specDetailDash"), $v("specDetailNote"), $v("specDetailOther"),
					$v("specLibraryDesc"), $v("specLibrary"), "", "", "", $v("specVersion"), $v("specAmendment"));
			}
			else {
				opener.opener.addNewRow($v("specId"), $v("specName"), "", $v("specDetailType"), $v("specDetailClass"),
					$v("specDetailForm"), $v("specDetailGroup"), $v("specDetailGrade"), $v("specDetailStyle"), $v("specDetailFinish"),
					$v("specDetailSize"), $v("specDetailColor"), $v("specDetailMethod"), $v("specDetailCondition"),
					$v("specDetailDash"), $v("specDetailNote"), $v("specDetailOther"),
					"Temporary", "temporary", "", "", "", $v("specVersion"), $v("specAmendment"));
				opener.window.close();	
			}
		}	
	
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




