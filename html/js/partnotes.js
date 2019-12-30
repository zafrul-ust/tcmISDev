/*Call this if you want to initialize something on load in the search frame.*/
function submitSearchForm() {
if( !validateForm() ) return false;

	var userAction = document.getElementById("uAction");
	userAction.value = 'search';

   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
   	return true;
   	
}

function validateForm() {
	var part = document.getElementById('catPartNo').value;
	if( part.trim() == '' ) {
		alert(messagesData.partNumRequired);
		return false;
	}
	return true;
}


function update() {
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var flag = true;//validateForm();
  if(flag) {
// for auto resubmit search..
   var action = document.getElementById("uAction");
   action.value = "update";
   document.genericForm.target='resultFrame';
   parent.showPleaseWait();
   document.genericForm.submit();
  }
}

function mydelete() {
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var flag = true;//validateForm();
  if(flag) {
// for auto resubmit search..
   var action = document.getElementById("uAction");
   action.value = "delete";
   document.genericForm.target='resultFrame';
   parent.showPleaseWait();
   document.genericForm.submit();
  }
}



function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultOnload()
{
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/
 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
}

