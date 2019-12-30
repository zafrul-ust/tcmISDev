function showErrorMessages()
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
// this one should be in some common file. Since all involved here are standard vars...
function popupOnLoadWithGrid(gridConfig,extraReduction)
{
// stopPleaseWait();
 if( !gridConfig ) gridConfig = _gridConfig;
 try{
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  $("updateResultLink").style["display"] = "none";
 }
 else
 {
 
  $("updateResultLink").style["display"] = "";
//  $("mainUpdateLinks").style["display"] = "";
 }
 }
 catch(ex)
 {}

// totalLines = $("totalLines").value; 
 initGridWithGlobal(gridConfig);
 
 displayNoSearchSecDuration();
 if (typeof( extraReduction ) != 'undefined')
 	setResultSize(extraReduction);
 else
 	setResultSize();
}

function _simpleUpdate(act,val) { 
	  if( window['validateForm'] && !validateForm() ) return false;
	  if( !act ) act = 'uAction';
	  if( !val ) val = 'update';
      $(act).value = val;
	  showPleaseWait();
      if(haasGrid) haasGrid.parentFormOnSubmit(); //prepare grid for data sending
      document.genericForm.submit();
      return false;
}
