windowCloseOnEsc = true;
var children = new Array();

function changeShowLateOrdersValue(element) {
	if (element.value == 'No')
		element.value = 'Yes';
	else
		element.value='No';
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateSearchCriteriaInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();  
  if(isValidSearchForm) {
   var userAction = document.getElementById("uAction");
   userAction.value = 'search';
   showPleaseWait();
   document.genericForm.target='resultFrame';
   document.genericForm.submit(); 
   
   return true;
  }
  else
  {
    return false;
  }
}

//***************************************************************************
//validateSearchCriteriaInput
// verify to and from dates if values set
//
function validateSearchCriteriaInput()
{
var errorMessage = messagesData.validValues + "\n\n";
var warningMessage = messagesData.alert + "\n\n";
var errorCount = 0;
var warnCount = 0;


if (errorCount >0)
{
 alert(errorMessage);
 return false;
}

return true;
}



function mainOnLoad() {

}


function openNewSpecWin() 
{		
	var loc = "/tcmIS/distribution/newspecs.do?fromReceiptSpec="+$v('fromReceiptSpec')+"&uAction=newspecs";
	
	children[children.length] = openWinGeneric(loc,"newspec","600","500","yes","50","50");
}

function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
}
