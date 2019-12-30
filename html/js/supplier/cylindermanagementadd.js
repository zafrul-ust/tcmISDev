var dhxWins = null;
var windowCloseOnEsc = true;
var children = new Array();

function submitUpdate() {
	if (validateForm()) {
		$("userAction").value = 'addNewEditCylinder';
        document.genericForm.submit();
	}
}

function validateForm() {
  var errorMsg = '';
  if($v("locationId") == '')
    errorMsg += '\t'+messagesData.location+'\n';

  if( $v('serialNumber') == '' )
    errorMsg += '\t'+messagesData.serialNumber+'\n';

  if( $v('manufacturerIdNo') == '' || $v("manufacturerName") == '')
    errorMsg += '\t'+messagesData.manufacturer+'\n';

  if( $v('vendorPartNo') == '' || $v("vendorPartNoDisplay") == '' )
    errorMsg += '\t'+messagesData.nsn8120+'\n';

  if( $v('correspondingNsn') == '' || $v("correspondingNsnDisplay") == '' )
    errorMsg += '\t'+messagesData.nsn6830+'\n';

  if( $v('cylinderStatus') == '' )
    errorMsg += '\t'+messagesData.cylinderStatus+'\n';

  if( $v('cylinderConditionCode') == '' )
    errorMsg += '\t'+messagesData.conditionCode+'\n';

  if(errorMsg.length > 0) {
    alert(messagesData.validValues+'\n'+errorMsg);
    return false;
  }
  return true;
}

function cylinderStatusChanged() {
    if( $v('cylinderStatus') == 'Out' )
        alert(messagesData.outStatusMsg);
}

var closeParentTransitWin = true;
function closeThisWindow() {
	if (closeParentTransitWin) {
		//opener.closeTransitWin();
	}
}

function myOnLoad(userAction) {
	if (userAction == 'addNewEditCylinder' && !showErrorMessage) {
		closeParentTransitWin = false;
		//opener.closeTransitWin();
		if ($v("calledFrom") == 'main')
            opener.submitSearch();
        else
            opener.parent.submitSearch();
		closeWindow();		
	}else {
		closeParentTransitWin = true;
	}
	if (showErrorMessage) {
	    if ($v("errorMsg") == 'Existed')
	        alert(messagesData.cylinderExisted);
	    else
		    alert($v("errorMsg"));
	}
}  //end of method

function closeAllchildren()
{
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

var dhxFreezeWins = null;
var resizeGridWithWindow = true;

function initializeDhxWins() {
if (dhxFreezeWins == null) {
  dhxFreezeWins = new dhtmlXWindows();
  dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
 }
}


function showTransitWin(messageType)
{
var waitingMsg = messagesData.waitingforinputfrom+"...";
 $("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

var transitDailogWin = document.getElementById("transitDialogWin");
 transitDailogWin.innerHTML = document.getElementById("transitDialogWinBody").innerHTML;
 transitDailogWin.style.display="";

 initializeDhxWins();
if (!dhxFreezeWins.window("transitDialogWin")) {
 // create window first time
 transitWin = dhxFreezeWins.createWindow("transitDialogWin",0,0, 400, 200);
  transitWin.setText("");
  transitWin.clearIcon();  // hide icon
 transitWin.denyResize(); // deny resizing
 transitWin.denyPark();   // deny parking

  transitWin.attachObject("transitDialogWin");
 //transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
 transitWin.center();
 // setting window position as default x,y position doesn't seem to work, also hidding buttons.
 transitWin.setPosition(125, 75);
  transitWin.button("minmax1").hide();
  transitWin.button("park").hide();
  transitWin.button("close").hide();
  transitWin.setModal(true);
 }else{
 // just show
 transitWin.setModal(true);  // freeze the window here
 dhxFreezeWins.window("transitDialogWin").show();
 }
}

function closeTransitWin() {
if (dhxFreezeWins != null) {
 if (dhxFreezeWins.window("transitDialogWin")) {
   dhxFreezeWins.window("transitDialogWin").setModal(false);
   dhxFreezeWins.window("transitDialogWin").hide();
  }
 }
}

function invalidateVendorPartNo()
{
    var vendorPartNoDisplay  =  document.getElementById("vendorPartNoDisplay");
    if (vendorPartNoDisplay.value.length == 0) {
        vendorPartNoDisplay.className = "inputBox";
    }else {
        vendorPartNoDisplay.className = "inputBox red";
    }
    //set to empty
    $("vendorPartNo").value = "";
    $("correspondingNsn").value = "";
    $("correspondingNsnDisplay").value = "";
    $("correspondingNsnDisplay").disabled="disabled";
    $("correspondingNsnDisplay").className = "inputBox";
    $("refurbCategoryDisplay").innerHTML = "";
    $("refurbCategory").value = "";
    $("conversionGroupDisplay").innerHTML = "";
    $("conversionGroup").value = "";
}

function invalidateCorrespondingNsn()
{
    var vendorPartNoDisplay  =  document.getElementById("correspondingNsnDisplay");
    if (vendorPartNoDisplay.value.length == 0) {
        vendorPartNoDisplay.className = "inputBox";
    }else {
        vendorPartNoDisplay.className = "inputBox red";
    }
    //set to empty
    $("correspondingNsn").value = "";
    $("refurbCategoryDisplay").innerHTML = "";
    $("refurbCategory").value = "";
    $("conversionGroupDisplay").innerHTML = "";
    $("conversionGroup").value = "";
}

function invalidateManufacturer()
{
    var manufacturerName  =  document.getElementById("manufacturerName");
    if (manufacturerName.value.length == 0) {
        manufacturerName.className = "inputBox";
    }else {
        manufacturerName.className = "inputBox red";
    }
    //set to empty
    $("manufacturerIdNo").value = "";
}