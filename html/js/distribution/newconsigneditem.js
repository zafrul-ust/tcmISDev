windowCloseOnEsc = true;
window.onresize= resizeFrames;
var showErrorMessage = false;

function validateForm() {
	errorMsg = messagesData.validvalues;
	errorCount = 0;
	
	if( !$v('itemId')) {
		  errorMsg += "\n" + messagesData.itemid;
		  errorCount = 1;
	} 
	
	if( !$v('specListId')) {
		  errorMsg += "\n" + messagesData.specs;
		  errorCount = 1;
	} 

	if( !isFloat($v('unitPrice'),false)) {
		  errorMsg += "\n" + messagesData.unitprice;
		  errorCount = 1;
	} 
	
	if(errorCount == 1) {
		alert(errorMsg);
		return false;
	}
	return true;
}

function addItem() {
	if( window['validateForm'] && !validateForm() ) return false;
		   
    $('uAction').value = 'addConsignedItem';
	document.genericForm.submit();
	
//	window.close();
}

function getItem() {
  loc = "/tcmIS/distribution/searchglobalitem.do?uAction=search&sourcePage=newConsignedItem&&inventoryGroup=Miami%20Distribution&&distribution=Y&searchText=";
  openWinGeneric(loc,"newConsignedItew","800","500","yes","50","50");
}

function clearItem()
{
    document.getElementById("itemDesc").innerHTML = "";
    document.getElementById("itemId").value = "";
    clearSpecList();
}

function itemChanged(itemId,itemDesc) {
	$('itemDesc').innerHTML = itemDesc;
	$('itemId').value = itemId;
	clearSpecList();
}

function getSpecList() {
	if( !$v('itemId')) {
		  alert(messagesData.validvalues + "\n" + messagesData.itemid);
		  return false;
	}
	
  	loc = "speclist.do?catPartNo=&specListConcat=&specVersionConcat=&specAmendmentConcat=&specDetailConcat=&specLibraryDescConcat=&specLibraryConcat=&specCocConcat=&specCoaConcat=&itemId="+$v('itemId'); 
    openWinGeneric(loc,"speclist","680","550","yes","50","50","20","20","no");
}

function clearSpecList()
{
    document.getElementById("specListDisplay").value = "";
    document.getElementById("specListId").value = "";
    document.getElementById("specDetail").value = "";
    document.getElementById("specCoc").value = "";
    document.getElementById("specCoa").value = "";
    document.getElementById("specLibrary").value = "";
}

function getList(specListDisplay, list, specVersionString, specAmendmentString, detail, library, coc, coa, libraryDesc) {
	document.getElementById("specListDisplay").value = specListDisplay;
	document.getElementById("specListId").value = list;  //list??
    document.getElementById("specDetail").value = detail;
    document.getElementById("specCoc").value = coc;
    document.getElementById("specCoa").value = coa;
    document.getElementById("specLibrary").value = library;
}

function showError() {
  if ($v("done") == 'Y') {
  	alert(messagesData.itemaddedsuccessfully);
    window.close();
  }
  if ($v("consign") == 'N') {
	  	alert(messagesData.srcignotfound);
	    window.close();
	  }
  	
  if (showErrorMessage)
  {
  	setTimeout('showUpdateErrorMessages()',50); /*Showing error messages if any*/
  }
}

function showUpdateErrorMessages()
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
  if($v("done") == 'Y')
  	errorWin.setText(messagesData.update);
  else
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
