function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window("errorWin")) {
  // create window first time
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 450, 300);
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

function call(name) {
	try { // whenever call something for result frame, also should update startSearchTime.
		var now = new Date();
	    document.getElementById("startSearchTime").value = now.getTime();
    } catch(ex){}
	if( eval('getResultFrame().'+name) != null )
		eval('getResultFrame().'+name+'()');
}

function resultPageSubmit() {    
    window.frames["resultFrame"].document.genericForm.submit();
}


function _submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = validateForm();

  if(isValidSearchForm) {
	var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();

   	$('uAction').value = 'search';
   	document.genericForm.target='resultFrame';
   	showPleaseWait();
    document.genericForm.submit(); 
  }
  return false;
}

function _createXSL(popname){
  var flag = validateForm();
  if(flag) {
	$('uAction').value = 'createExcel';
	if( popname == null ) popname = '_ExcelReport' + window.name;
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',popname,'650','600','yes');
    document.genericForm.target= popname;
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function _validateForm() {
	return true;
}
