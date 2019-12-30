// ============= PageSetup.inc =============//
function addEvent(obj, evType, fn, useCapture) {
	// General function for adding an event listener
	if (obj.addEventListener) {
		obj.addEventListener(evType, fn, useCapture);
		return true;
	}
	else if (obj.attachEvent) {
		var r = obj.attachEvent("on" + evType, fn);
		return r;
	}
	else {
		alert(evType+" handler could not be attached");
	}
}

function addKeyEvent() {
	// Specific function for this particular browser
	var e = (document.addEventListener) ? 'keypress' : 'keydown';
	addEvent(document,e,keyEventHandler,false);
}

addKeyEvent();

//JIRA TCMISDEV-1336 Cancel the default backspace key's operation in Chrome, FF, and IE
function cancelBackSpace(e)
{
	if(window.event != null && (window.event.srcElement.tagName == 'BODY' || ((window.event.srcElement.tagName == 'INPUT' || window.event.srcElement.tagName == 'TEXTAREA') && window.event.srcElement.readOnly)) && window.event.keyCode==8)
	{
		window.event.returnValue = false;
		return false;
	}
	else if(e.target != null && (e.target.tagName == 'BODY' || ((e.target.tagName == 'INPUT' || e.target.tagName == 'TEXTAREA') && e.target.readOnly)) && e.keyCode==8)
	{
		try
		{
			e.preventDefault();
		}catch(e){}
			
		return false;
	}

}

function addKeyDownEvent() {
	addEvent(document,'keydown',cancelBackSpace,false);
}

addKeyDownEvent();
//JIRA TCMISDEV-1336 Cancel the default backspace key's operation in Chrome and IE

// ============= PageSetup.inc =============//


// ============= DisableKeys.js =============//
// Keys to be disabled can be added to the lists below.
// The number is the key code for the particular key
// and the text is the description displayed in the
// status window if the key [combination] is pressed.

var badKeys = new Object();
badKeys.single = new Object();
badKeys.single['8'] = 'Backspace outside text fields'; /*Does not allow you to backspace in a password field in mozilla.*/
//badKeys.single['13'] = 'Enter outside text fields';
badKeys.single['116'] = 'F5 (Refresh)'; /*blocks T in mozilla*/
//badKeys.single['122'] = 'F11 (Full Screen)';

badKeys.alt = new Object();
badKeys.alt['37'] = 'Alt+Left Cursor';
badKeys.alt['39'] = 'Alt+Right Cursor';
badKeys.alt['36'] = 'Alt+Home';

badKeys.ctrl = new Object();
badKeys.ctrl['78'] = 'Ctrl+N';
badKeys.ctrl['110'] = 'Ctrl+n'; /*In firefox when you press ctrl+n it is the lower case n that is passed, in IE it is N*/
badKeys.ctrl['79'] = 'Ctrl+O';
badKeys.ctrl['111'] = 'Ctrl+O'; /*In firefox when you press ctrl+o it is the lower case o that is passed, in IE it is O*/
badKeys.ctrl['69'] = 'Ctrl+E';
badKeys.ctrl['101'] = 'Ctrl+e'; /*In firefox when you press ctrl+e it is the lower case n that is passed, in IE it is E*/
badKeys.ctrl['73'] = 'Ctrl+I';
badKeys.ctrl['105'] = 'Ctrl+i'; /*In firefox when you press ctrl+i it is the lower case n that is passed, in IE it is I*/
badKeys.ctrl['72'] = 'Ctrl+H';
badKeys.ctrl['104'] = 'Ctrl+h'; /*In firefox when you press ctrl+h it is the lower case n that is passed, in IE it is H*/
badKeys.ctrl['66'] = 'Ctrl+B';
badKeys.ctrl['98'] = 'Ctrl+b'; /*In firefox when you press ctrl+b it is the lower case n that is passed, in IE it is B*/
badKeys.ctrl['76'] = 'Ctrl+L';
badKeys.ctrl['1088'] = 'Ctrl+l'; /*In firefox when you press ctrl+l it is the lower case n that is passed, in IE it is L*/
badKeys.ctrl['82'] = 'Ctrl+R';
badKeys.ctrl['114'] = 'Ctrl+r'; /*In firefox when you press ctrl+r it is the lower case n that is passed, in IE it is R*/
badKeys.ctrl['84'] = 'Ctrl+T';
badKeys.ctrl['116'] = 'Ctrl+t'; /*In firefox when you press ctrl+t it is the lower case n that is passed, in IE it is T*/

function checkKeyCode(type, code) {
	if (badKeys[type][code]) {
		return true;
	} else {
		return false;
	}
}

function getKeyText(type, code) {
	return badKeys[type][code];
}

var ie=document.all;
var w3c=document.getElementById&&!document.all;

var windowCloseOnEsc = false;

function keyEventHandler(evt) {
	this.target = evt.target || evt.srcElement;
	this.keyCode = evt.keyCode || evt.which;
	var targtype = this.target.type;

	if (w3c) {
		if (document.layers) {
	  	this.altKey = ((evt.modifiers & Event.ALT_MASK) > 0);
	  	this.ctrlKey = ((evt.modifiers & Event.CONTROL_MASK) > 0);
	  	this.shiftKey = ((evt.modifiers & Event.SHIFT_MASK) > 0);
	  	}
	  else {
		  this.altKey = evt.altKey;
	  	this.ctrlKey = evt.ctrlKey;
	  }
	}
	else
	{
		// Internet Explorer
		this.altKey = evt.altKey;
 		this.ctrlKey = evt.ctrlKey;
	}

	// Find out if we need to disable this key combination
	var badKeyType = "single";
    if (this.ctrlKey) {
		badKeyType = "ctrl";
	}
	else if (this.altKey) {
		badKeyType = "alt";
	}

    //alert("Here keyEventHandler "+this.keyCode+" evt.charCode  "+evt.charCode+" badKeyType "+badKeyType+"");
    //hmmm in mozilla the keycode would contain a function key ONLY IF the charcode IS 0
    //else key code and charcode read funny, the charcode for 't'
    //returns 116, which is the same as the ascii for F5
    //SOOO,... to check if a the keycode is truly a function key,
    //ONLY check when the charcode is null OR 0, IE returns null, mozilla returns 0
    if(badKeyType == "single" && evt.charCode == null || evt.charCode == 0){

    }
    else if (badKeyType == "single" && this.keyCode == 116)  /*This is to allow typing t in Mozilla*/
    {
        var ver = getInternetExplorerVersion();
        if (!isIE() || ver >= 9.0)
        {
          this.keyCode = 0;         
        }
    }
    
   if (windowCloseOnEsc == true && badKeyType == "single" && this.keyCode == 27) {
  	 if(top.close)
       top.close();
     else
       window.close();
   }
  
// alert("badKeyType"+badKeyType+"       keyCode:"+this.keyCode);   
   if ( typeof(doF6) != 'undefined' && badKeyType == "single" && this.keyCode == 117) {
       doF6();
   }
   
   if ( typeof(doF8) != 'undefined' && badKeyType == "single" && this.keyCode == 119) {
       doF8();
   }
   
   if ( typeof(doF10) != 'undefined' && badKeyType == "single" && this.keyCode == 121) {
       doF10();
   }
    

    //alert("Here keyEventHandler "+this.keyCode+" evt.charCode  "+evt.charCode+" badKeyType "+badKeyType+"");
    if (checkKeyCode(badKeyType, this.keyCode)) {
		return cancelKey(evt, this.keyCode, this.target, getKeyText(badKeyType, this.keyCode));
	}
}

function isIE(){
 if(navigator.appName.indexOf("Microsoft")!=-1)
 if(navigator.userAgent.indexOf('Opera')== -1)
 return true;
 return false;
}

function getInternetExplorerVersion()
// Returns the version of Internet Explorer or a -1
// (indicating the use of another browser).
{
  var rv = -1; // Return value assumes failure.
  if (navigator.appName == 'Microsoft Internet Explorer')
  {
    var ua = navigator.userAgent;
    var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
    if (re.exec(ua) != null)
      rv = parseFloat( RegExp.$1 );
  }
  return rv;
}

function cancelKey(evt, keyCode, target, keyText) {
	
	if (keyCode==8 || keyCode==13) {
		// Don't want to disable Backspace or Enter in text fields
      if (target.type == "password" || target.type == "text" || target.type == "textarea") {
	  	window.status = "";
	  	return true;
	  }
	}

	if (evt.preventDefault) {
		evt.preventDefault();
		evt.stopPropagation();
	}
	else {
		evt.keyCode = 0;
		evt.returnValue = false;
	}

	//alert("Here "+keyText+"");
	//window.status = keyText+" is disabled";
	return false;
}
// ============= DisableKeys.js =============//

/*Cuurently Not used - Nawaz 03-27-07*/
function disableCtrlKeyCombination(e)
{
// to use this add onKeyPress="return disableCtrlKeyCombination(event);" onKeyDown="return disableCtrlKeyCombination(event);"
//list all CTRL + key combinations you want to disable
var forbiddenKeys = new Array('a', 'n', 'c', 'x', 'v', 'j');
var key;
var isCtrl;

if(window.event)
{
  key = window.event.keyCode;     //IE
  if(window.event.ctrlKey)
  	isCtrl = true;
  else
  	isCtrl = false;
}
else
{
  key = e.which;     //firefox
  if(e.ctrlKey)
  	isCtrl = true;
  else
  	isCtrl = false;
}

//if ctrl is pressed check if other key is in forbidenKeys array
if(isCtrl)
{
  for(i=0; i<forbiddenKeys.length; i++)
  {
		//case-insensitive comparation
    if(forbiddenKeys[i].toLowerCase() == String.fromCharCode(key).toLowerCase())
    {
            alert('Key combination CTRL + '+String.fromCharCode(key)+' has been disabled.');
            return false;
    }
  }
}
return true;
}

function stop_event(e){
var key;
var isCtrl;

if(window.event)
{
  key = window.event.keyCode;     //IE
  if(window.event.ctrlKey)
  	isCtrl = true;
  else
  	isCtrl = false;
}
else
{
  key = e.which;     //firefox
  if(e.ctrlKey)
  	isCtrl = true;
  else
  	isCtrl = false;
}

//if ctrl is pressed check if other key is in forbidenKeys array
if(isCtrl)
{
  e=e||event;
  if (e.preventDefault) e.preventDefault();
  e.cancelBubble=true;
  return false;
}
}



