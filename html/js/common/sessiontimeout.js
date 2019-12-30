var warningMessageShown=false;

function init() {
this.cfg = new YAHOO.util.Config(this);
if (this.isSecure)
{
 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
}

/*Yui pop-ups need to be initialized onLoad to make them work correctly.
I they are not initialized onLoad they tend to act weird*/
warnMessageWin = new YAHOO.widget.Panel("warnMessageArea", { width:"300px",height:"205px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:false, visible:false,
draggable:true, modal:false,underlay: 'shadow' } );
warnMessageWin.render();
}

function showWarningMessage() {
 //alert("showWarningMessage");
 if (!warningMessageShown)
 {
  var warnMessageArea = document.getElementById("warnMessageArea");
  warnMessageArea.style.display="";
  displayCountdown(timeOutWait/1000,"timer");
  clearTimeout(timerWarnID);
  warnMessageWin.show();
  warningMessageShown = true;
  self.focus();
 }
}

// Timeout functions
var defaultTimeOutMilliseconds = 5400000; // 90 Minutes
var defaultTimeOutWaitMilliseconds = 600000; // 10 minutes
//var defaultTimeOutMilliseconds = 10000; // 10 Seconds
//var defaultTimeOutWaitMilliseconds = 10000; // 10 seconds
var timeOutWait = defaultTimeOutWaitMilliseconds;
var curTimeOut = defaultTimeOutMilliseconds; 
var timerID, timerWarnID, timeoutUrl, resetTimeoutURL="";
var timeoutMsg;

function loadPage(timeout, timeoutWait)
{
 if(timeout != null && timeout > 0)
	 curTimeOut = timeout;
 else
	 curTimeOut=defaultTimeOutMilliseconds;
 
 if(timeoutWait != null && timeoutWait > 0)
	 timeOutWait = timeoutWait;
 else
	 timeOutWait=defaultTimeOutWaitMilliseconds;
 
 setupTimeout(curTimeOut);
}

function clearGoToTimeout()
{
  warnMessageWin.hide();
  warningMessageShown = false;

  //alert("here clearGoToTimeout");
  //clearTimeout(timerID);
  clearTimeout(timerWarnID);
  clearTimeout(timerCountDown);

  var loc = "resetsession.do?";
  callToServer(loc);
  fixProgressBar();

  //setupTimeout(curTimeOut);
  setTimeout('setupTimeout(curTimeOut)',20);
}

function resetTimeoutValues(timeoutValue)
{
// For clearing the timeout for timeout window AND timeout-warning window
  //clearTimeout(timerID);
  clearTimeout(timerWarnID);
  curTimeOut=timeoutValue;
  //alert("resetTimeoutValues- " +timeoutValue);
  setupTimeout(timeoutValue);
}

function goToTimeout(curTimeOut)
{
 warnMessageWin.hide();
 sessionLogout();
 return;
}

function resetTimer()
{
 resetTimeoutValues(curTimeOut);
}

function setupTimeout(curTimeOut)
{
  if (!warningMessageShown)
  {
   timerWarnID=window.setTimeout('showWarningMessage()', curTimeOut);
  }
}

function displayCountdown(countdn,cd)
{
    if (countdn < 0)
    {
     goToTimeout(curTimeOut);
    }
    else
    {
      var secs = countdn % 60;
	if (secs < 10) secs = '0'+secs;var countdn1 = (countdn - secs) / 60;
	var mins = countdn1 % 60;
	if (mins < 10) mins = '0'+mins;
	countdn1 = (countdn1 - mins) / 60;
	var hours = countdn1 % 24;
	var days = (countdn1 - hours) / 24;
	document.getElementById(cd).innerHTML = ""+mins+" min : "+secs+" secs";

      timerCountDown=window.setTimeout('displayCountdown('+(countdn-1)+',\''+cd+'\');',995);
    }
}

/*This function is called when to avoid people loggin into the wrong frame.
When there is a log out in the search or result frame it needs to go to main.
If the timeout is in the application page, the user needs to login to the application.*/
function sessionLogout() {

	try{
		var topDoc = window.top.document;
		topDoc.logoutForm.action.value = 'logout';
		topDoc.logoutForm.lostSession.value = 'timeout';
		topDoc.logoutForm.submit();
		return;
	}
	catch(ex) {
		//alert(ex);
	}

     /*If the timeout is from a page in the application*/
     try {
      var action = parent.document.getElementById("action");
      action.value = 'logout';
      var lostSession = parent.document.getElementById("lostSession");
      lostSession.value = 'timeout';

      parent.window.document.logoutForm.submit();
      return;
     }
     catch (ex){}

     /*If the timeout is from another framed page in the application*/
	try {
		var action = parent.parent.document.getElementById("action");
		action.value = 'logout';
		var lostSession = parent.parent.document.getElementById("lostSession");
		lostSession.value = 'timeout';

		parent.parent.window.document.logoutForm.submit();
		return;
	}
	catch (ex){}

}
