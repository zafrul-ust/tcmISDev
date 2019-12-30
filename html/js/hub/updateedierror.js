var dhxWins = null;
var windowCloseOnEsc = true;
var children = new Array();

function submitUpdate() {
	if (validateForm()) {
		$("userAction").value = 'editEdiError';
        document.genericForm.submit();
	}
}

function validateForm() {

  if(!isInteger($v("quantity"), true)) {
    alert(messagesData.quantity);
    return false;
  }

  if( $v('shiptoCity') == '' ) {
	 alert(messagesData.shiptoCity);
     return false;
  }
  return true;
}

var closeParentTransitWin = true;
function closeThisWindow() {
	if (closeParentTransitWin) {
		opener.closeTransitWin();	
	}
}

function myOnLoad(userAction) {
	if (userAction == 'editEdiError' && !showErrorMessage) {
		closeParentTransitWin = false;
		opener.closeTransitWin();
        opener.parent.submitSearch();
		closeWindow();		
	}else {
		closeParentTransitWin = true;
	}
	if (showErrorMessage) {
		alert($v("errorMsg"));
	}
}  //end of method

//I was too short sighted so I named the window too narrow.
//replacememtpartsearchmain.do - is actually just a catalog search popup
function lookupPartNumber() {
    var currentPart = ($v("catPartNo")).trim();
    showTransitWin("");
    var loc = "replacementpartsearchmain.do?uAction=search&searchArgument="+encodeURIComponent(currentPart)+
              "&companyId="+encodeURIComponent($v("companyId"))+
              "&catalogCompanyId="+encodeURIComponent($v("catalogCompanyId"))+
              "&catalogId="+encodeURIComponent($v("catalogId"))+
              "&facilityId="+encodeURIComponent($v("facilityId"));
    children[children.length] = openWinGeneric(loc,"partNumberSearch"+children.length,"650","500","yes","50","50","20","20","no");
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

function replacementPartChanged(newPart,newPartGroupNo) {
	$("catPartNo").value = newPart;
	$("partGroupNo").value = newPartGroupNo;
}

function clearPartNumber() {
	$("catPartNo").value = '';
	$("partGroupNo").value = '';
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
