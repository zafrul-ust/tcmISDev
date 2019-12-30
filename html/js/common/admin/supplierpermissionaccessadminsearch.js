/*Call this if you want to initialize something on load in the search frame.*/
function mySearchOnload()
{
 setSearchFrameSize();
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 var now = new Date();
 var startSearchTime = document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var action = document.getElementById("action"); 
 action.value = 'search'; 
 var flag = validateForm();
  if(flag) {
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {

	return true;
 
}

function closeWindow() {
	window.parent.close();
}

