
function submitSearchForm()
{
	 /*Make sure to not set the target of the form to anything other than resultFrame*/
	 document.genericForm.target='resultFrame';
	 document.getElementById("uAction").value = 'search';
	 showPleaseWait();
	 //set start search time
	 var now = new Date();
	 var startSearchTime = document.getElementById("startSearchTime");
	 startSearchTime.value = now.getTime();
   	return true;
}

function generateExcel()
{
	var flag = true;//validateForm();
	if(flag) 
	{
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_DisplayOnlyExcel','650','600','yes');
		document.genericForm.target='_DisplayOnlyExcel';
		var a = window.setTimeout("document.genericForm.submit();",50);
	}
}

function closeAllchildren()
{
// if (document.getElementById("uAction").value != 'new') {
 try {
  for(var n=0;n<children.length;n++) {
   try {
    children[n].closeAllchildren();
    } catch(ex){}
   children[n].close();
   }
  } catch(ex){}
  children = new Array();
// }
}

var dhxFreezeWins = null;


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

var transitDailogWin = document.getElementById("transitDailogWin");
 transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
 transitDailogWin.style.display="";

 initializeDhxWins();
if (!dhxFreezeWins.window("transitDailogWin")) {
 // create window first time
 transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
  transitWin.setText("");
  transitWin.clearIcon();  // hide icon
 transitWin.denyResize(); // deny resizing
 transitWin.denyPark();   // deny parking

  transitWin.attachObject("transitDailogWin");
 //transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
 transitWin.center();
 // setting window position as default x,y position doesn't seem to work, also hidding buttons.
 transitWin.setPosition(328, 131);
  transitWin.button("minmax1").hide();
  transitWin.button("park").hide();
  transitWin.button("close").hide();
  transitWin.setModal(true);
 }else{
 // just show
 transitWin.setModal(true);  // freeze the window here
 dhxFreezeWins.window("transitDailogWin").show();
 }
}

function closeTransitWin() {
if (dhxFreezeWins != null) {
 if (dhxFreezeWins.window("transitDailogWin")) {
   dhxFreezeWins.window("transitDailogWin").setModal(false);
   dhxFreezeWins.window("transitDailogWin").hide();
  }
 }
}
