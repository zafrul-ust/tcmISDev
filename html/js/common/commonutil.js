// until later we take prototype, just use this for now.
function $(a) {
	return document.getElementById(a);
}

function $v(a) {
	var val = null;
	try {
		val = document.getElementById(a).value.trim();
	}catch(ex){}
	return val;
}

var submitCount=0;
//Makes sure the form is submitted only once.
function submitOnlyOnce()
{
    if (submitCount == 0)
    {
        submitCount++;
        try
        {
         //Turns off the main page and shows the transitions page
         var transitPage = document.getElementById("transitPage");
         transitPage.style["display"] = "";

         var mainPage = document.getElementById("mainPage");
         mainPage.style["display"] = "none";
         /*This is for IE, fomr some reason the table background is visible*/
         var resultsMaskTable = document.getElementById("resultsMaskTable");
         resultsMaskTable.style["display"] = "none";
        }
        catch (ex)
        {

        }
        try {
  	      document.getElementById("resultFrame").contentWindow.stopBackgroundRender();
        }
        catch (err) {
        }
        return true;
    }
    else
    {
        try
        {
          alert(messagesData.submitOnlyOnce);
        }
        catch (ex)
        {
          alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        }
        return false;
    }
}


function submitFrameOnlyOnce()
{
   try
   {
    searchMainPageHeight = document.getElementById("searchMainPage").offsetHeight;
    if (searchMainPageHeight !=0)
    {
    parent.searchFrameHeight=searchMainPageHeight; /*Need to keep track of search frame height to be used upon resize of main window*/
    }
   }
   catch (ex)
   {
   }

   if (parent.submitCount == 0)
   {
      parent.submitCount++;
      try {
	      stopBackgroundRender();
      }
      catch (err) {
      }
      return true;
   }
   else
   {
      try
      {
        alert(messagesData.submitOnlyOnce);
      }
      catch (ex)
      {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
      }
      return false;
   }
}

function submitSearchOnlyOnce()
{
   if (submitCount == 0)
   {
      submitCount++;
      try {
	      document.getElementById("resultFrame").contentWindow.stopBackgroundRender();
      }
      catch (err) {
      }
      return true;
   }
   else
   {
      alert(messagesData.submitOnlyOnce);
      return false;
   }
}

String.prototype.startsWith = function(str) {
	return (this.match("^" + str) == str);
};

String.prototype.endsWith = function(str) {
	return (this.match(str + "$") == str);
};

String.prototype.trim = function() {
	// skip leading and trailing whitespace
	// and return everything in between
	return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
};

String.prototype.replaceBr = function() {
	// replace <BR> with \n
	return this.replace(/&lt;BR&gt;/g, "\n");
};

// Support Array.indexOf in older javascript
if (!Array.indexOf) {
	Array.indexOf = [].indexOf ? function(arr, obj, from) {
		return arr.indexOf(obj, from);
	} : function(arr, obj, from) { // (for IE6)
		var l = arr.length, i = from ? parseInt((1 * from) + (from < 0 ? l : 0), 10) : 0;
		i = i < 0 ? 0 : i;
		for (; i < l; i++) {
			if (i in arr && arr[i] === obj) {
				return i;
			}
		}
		return -1;
	};
}

// this will decode the HTML Encode
function htmlDencode(s) {
	var str = new String(s);
	str = str.replace(/&amp;/g, "&");
	str = str.replace(/&lt;/g, "<");
	str = str.replace(/&gt;/g, ">");
	str = str.replace(/&quot;/g, "\"");
	str = str.replace(/&#034;/g, "\"");
	str = str.replace(/&#34;/g, "\"");
	str = str.replace(/&#035;/g, "#");
	str = str.replace(/&#038;/g, "&");
	str = str.replace(/&#38;/g, "&");
	str = str.replace(/&#039;/g, "\'");
	str = str.replace(/&#39;/g, "\'");
	str = str.replace(/&#044;/g, ",");
	str = str.replace(/&#043;/g, "+");
	str = str.replace(/%20/g, " ");
	str = str.replace(/%23/g, "#");
	str = str.replace(/%25/g, "%");
	str = str.replace(/%26/g, "&");
	str = str.replace(/%2b/g, "+");
	str = str.replace(/%7C/g, "|");
	return str;
}

// this function will encode the string for URL
function URLEncode(s) {
	var str = new String(s);
   str = str.replace(/#/gi, "%23");
   str = str.replace(/%/gi, "%25");
   str = str.replace(/&/gi, "%26");
   str = str.replace(/\+/gi, "%2b");
   //Tomcat doesn't process pipe (|) in URL
   str = str.replace(/\|/gi, "%7C")
   return str;
}


var IFrameObj; // our IFrame object to make AJAX calls
function callToServer(URL, targetFrameName) {
	var frameName;
	if(targetFrameName != null && targetFrameName != "") {
		frameName = targetFrameName;
	}
	else {
		frameName = 'RSIFrame';
	}
	//alert(URL);
	if (!document.createElement) {
		return true
	}
	;
	var IFrameDoc;
	if (!IFrameObj && document.createElement) {
		// create the IFrame and assign a reference to the
		// object to our global variable IFrameObj.
		// this will only happen the first time
		// callToServer() is called
		// the src is set to /blank.html for IE to not display a security message when used in https pages
		try {
			var tempIFrame = document.createElement('iframe');
			tempIFrame.setAttribute('id', frameName);
			tempIFrame.setAttribute('name', frameName);
			tempIFrame.setAttribute('src', '');
			tempIFrame.style.border = '0px';
			tempIFrame.style.width = '0px';
			tempIFrame.style.height = '0px';
			IFrameObj = document.body.appendChild(tempIFrame);

			if (document.frames) {
				// this is for IE5 Mac, because it will only
				// allow access to the document object
				// of the IFrame if we access it through
				// the document.frames array
				IFrameObj = document.frames[frameName];
			}
		}
		catch (exception) {
			// This is for IE5 PC, which does not allow dynamic creation
			// and manipulation of an iframe object. Instead, we'll fake
			// it up by creating our own objects.
			iframeHTML = '\<iframe id="RSIFrame" src="" name="' + frameName + '" style="';
			iframeHTML += 'border:0px;';
			iframeHTML += 'width:0px;';
			iframeHTML += 'height:0px;';
			iframeHTML += '"><\/iframe>';
			document.body.innerHTML += iframeHTML;
			IFrameObj = new Object();
			IFrameObj.document = new Object();
			IFrameObj.document.location = new Object();
			IFrameObj.document.location.iframe = document.getElementById(frameName);
			IFrameObj.document.location.replace = function(location) {
				this.iframe.src = location;
			}
		}
	}

	if (navigator.userAgent.indexOf('Gecko/') != -1 && !IFrameObj.contentDocument) {
		// we have to give NS6 a fraction of a second
		// to recognize the new IFrame
		setTimeout('callToServer()', 10);
		return false;
	}

	if (IFrameObj.contentDocument) {
		IFrameDoc = IFrameObj.contentDocument;
	}
	else if (IFrameObj.contentWindow) {
		IFrameDoc = IFrameObj.contentWindow.document;
	}
	else if (IFrameObj.document) {
		IFrameDoc = IFrameObj.document;
	}
	else {
		return true;
	}
	if (typeof (URL) == 'string') {
		IFrameDoc.location.replace(URL);
	}
	else {
		try {
			var oritarget = URL.target;
			URL.target = frameName;
			URL.submit();
			URL.target = oritarget;
		}
		catch (ex) {
			return true;
		}
	}
	setTimeout('window.status="";', 5000);
}



// This function replaces the src of the hidden Iframe to stop the progress bar in IE
function fixProgressBar()
{
//  try
//  {
//   if(_isIE)
//  {
//   var RSIFrame = document.getElementById("RSIFrame");
//   RSIFrame.src="/blank.html";
//   RSIFrame.blur();
//  }
//  }
//  catch (ex)
//  {
//  }
}

//this is for the help link on the client pages toolbar
function opentcmISHelp(module) {
	if (module == 'supplier') {
		openWinGeneric('/tcmIS/help/usgovshipping/index.html', 'tcmIS_Help', '1000', '600', 'yes');
	}
	else if (module == 'haas') {
		openWinGeneric('/tcmIS/help/Menu/help.html', 'tcmIS_Help', '1000', '600', 'yes');
	}
	else if (module == 'utc') {
		openWinGeneric('/tcmIS/help/utc/help/Menu/help.html', 'tcmIS_Help', '1000', '600', 'yes');
	}
	else if (module == 'lmco') {
		openWinGeneric('/tcmIS/help/lmco/help/Menu/help.html', 'tcmIS_Help', '1000', '600', 'yes');
	}
	else if (module == 'bae') {
		openWinGeneric('/tcmIS/help/bae/help/Menu/help.html', 'tcmIS_Help', '1000', '600', 'yes');
	}
	else if (module == 'ray') {
		openWinGeneric('/tcmIS/help/ray/robohelp/index.htm', 'tcmIS_Help', '1000', '600', 'yes');
	}
	else if (module == 'doe') {
		openWinGeneric('/tcmIS/help/doe/help/Menu/help.html', 'tcmIS_Help', '1000', '600', 'yes');
	}
	else if (module == 'customer') {
		openWinGeneric('https://apps.tcmis.com/tcmIS/help/custportal/help/Menu/help.html', 'tcmIS_Help', '1000', '600', 'yes');
	}
	else {
		openWinGeneric('/tcmIS/help/demo/help/Menu/help.html', 'tcmIS_Help', '1000', '600', 'yes');
	}
}

// this is for the MSDS viewer on the toolbar
//can be remove when everything is move to new application
function openwin ()
 {
   opentcmISHelp();
 }

//Use this funciton to open a URL from a link on a page. Example usage below
//openWinGeneric('receivinghistoryurl','show_receiving_history','610','600','yes');
function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable,topCoor,leftCoor,scrollbars )
{
  if (topCoor == null || topCoor.trim().length == 0)
  {
    topCoor = "200";
  }

  if (leftCoor == null || leftCoor.trim().length == 0)
  {
    leftCoor = "200";
  }

  if (scrollbars == null || scrollbars.trim().length == 0)
  {
    scrollbars = "yes";
  }

  windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars="+scrollbars+",status=no,top="+topCoor+",left="+leftCoor+",width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable+",scrollbars="+scrollbars;
  preview = window.open(destination, WinName,windowprops);
  if (preview != null) {
	  preview.focus();
  }

  window.setTimeout(function(){checkTitle(preview, 1)}, 500);
  return preview;
}

function checkTitle(popupWindow, cntr) {
	if (popupWindow && popupWindow.document) {
		if (popupWindow.document.title == null || popupWindow.document.title.trim().length == 0) {
			popupWindow.document.title = "tcmIS Popup";
		}
	}
	else if (cntr < 10) {
		window.setTimeout(function(){checkTitle( popupWindow, cntr + 1)}, 500);
	}
}


function openWinGenericDefault(destination,WinName,Resizable,topCoor,leftCoor,scrollbars )
{
	var width;
	var height;
	 if( typeof( window.innerWidth ) == 'number' ) {
		    //Non-IE
		 	width = parent.window.innerWidth-30;
		 	height = parent.window.innerHeight;
		  } else if( parent.parent.document.documentElement && ( parent.parent.document.documentElement.clientWidth || parent.parent.document.documentElement.clientHeight ) ) {
		    //IE 6+ in 'standards compliant mode'
			  width = parent.parent.document.documentElement.clientWidth;
			  height = parent.parent.document.documentElement.clientHeight;
		  } else if( parent.parent.document.body && ( parent.parent.document.body.clientWidth || parent.parent.document.body.clientHeight ) ) {
		    //IE 4 compatible
			  width = parent.parent.document.body.clientWidth;
			  height = parent.parent.document.body.clientHeight;
		  }
		  //window.alert( 'Width = ' + width );
		  //window.alert( 'Height = ' + height );
		  
	openWinGeneric(destination,WinName,width,height,Resizable,topCoor,leftCoor,scrollbars )
}

//Use this funciton to open a URL which results in a Excel file. This version gives
//the file toolbar to the user so that they can save the file if it
//opnes within the browser (for IE). Example usage below
//openWinGenericExcel('excelfileurl','show_excel_report_file','610','600','yes');
function openWinGenericExcel(destination,WinName,WinWidth, WinHeight, Resizable,topCoor,leftCoor,scrollbars )
{
  openWinGenericViewFile(destination,WinName,WinWidth, WinHeight, Resizable,topCoor,leftCoor,scrollbars );
}

function openWinGenericViewFile(destination,WinName,WinWidth, WinHeight, Resizable,topCoor,leftCoor,scrollbars )
{
  if (topCoor == null || topCoor.trim().length == 0)
  {
    topCoor = "200";
  }

  if (leftCoor == null || leftCoor.trim().length == 0)
  {
    leftCoor = "200";
  }

  if (scrollbars == null || scrollbars.trim().length == 0)
  {
    scrollbars = "yes";
  }

  windowprops = "toolbar=yes,location=no,directories=no,menubar=yes,scrollbars="+scrollbars+",status=yes,top="+topCoor+",left="+leftCoor+",width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
  preview = window.open(destination, WinName,windowprops);
  preview.focus();
  return preview;    
}

//Use this function to close a pop-up window
function closeWindow()
{
    window.close();
}

//passing element already. runs faster.
function setSelectOption(href, text, id, optionO, cssClassName, selected) {
	  var optionName = new Option(text, id, false, selected ? selected : false);
	  optionO[href] = optionName;
	  if( cssClassName ) optionO[href].className =cssClassName;
}
/*This is to add an option to a select dropdown eith the CSS class name specified*/

function setOption(href, text, id, target, cssClassName, selected) {
  var optionO = document.getElementById(target);
  setSelectOption(href, text, id, optionO, cssClassName, selected);
}

/*This is to change drop down to a certain option*/
function setSelect(target,value) {
  try {
    if( typeof( target ) == 'string' ) target = $(target);
  	var opt = target.options;
  	var i = opt.length;
  	for(;i--;) if( value == opt[i].value ) { target.selectedIndex = i; return; }
  } catch(ex){}
}
// check if a field is empty, this is different than the isEmpty in formcheck
function isEmptyV(target) {
  try {
    var value = null;
    if( typeof( target ) == 'string' ) value = $v(target); // already trimed.
	else value = target.value.trim();
	if( value ) return false;
  } catch(ex){}
  return true;
}

// Show/hide a given element
function toggleDiv(divId, show) {
   	var divElement = document.getElementById(divId);
   	if ( show ) {
	   	divElement.style.display="";
	} else {
		divElement.style.display="none";
	}
}

/*This is to be called on load of pages that a user can work on for hours, e.g. accounts payable, catalog group pages, buy page, msds*/
function resetApplicationTimer()
{
    try
    {
     parent.resetTimer();
    }
    catch(exap){
    }
}

/*This is to stop the transit page in the main Iframe*/
function resetTransitPage()
{
    try
    {
        parent.document.getElementById("transitPage").style["display"] = "none";
        parent.stopPleaseWait();
        var resultFrameDiv = parent.document.getElementById("resultFrameDiv");
        resultFrameDiv.style["display"] = "";
    }
    catch(exap){
    }
}

// Larry, to get result frame from the tree, return null if doesn't exist.
function getResultFrameWin(win){
	if( win.resultFrame != null ) return win.resultFrame;
	if( win.parent == null ) return null;
	return getResultFrameWin(win.parent);
}

function getResultFrame() {
	return getResultFrameWin(window);
}

function getSearchFrameWin(win){
	if( win.searchFrame != null ) return win.searchFrame;
	if( win.document.getElementById("searchFrameDiv") != null ) return win; // for main/result paradigm
	if( win.parent == null ) return null;
	return getSearchFrameWin(win.parent);
}

function getSearchFrame() {
	return getSearchFrameWin(self);
}

/*Use this function to submit the search for when a user hits enter in a text input field.
  Make sure to call your submit form method submitSearchForm().
  Currently Does not work in mozilla
*/
function onKeyPress(e) {
  var keyCode;
  if(window.event)
  {
    keyCode = window.event.keyCode;     //IE
  }else
  {
    try
    {
      keyCode = e.which;     //firefox
    }
    catch (ex){
      //return false;
    }
  }
  if (keyCode==13) {
    submitSearchForm();
  }
}

var _isFF=false;var _isIE=false;var _isOpera=false;var _isKHTML=false;var _isMacOS=false;

if(navigator.userAgent.indexOf('Macintosh')!= -1)_isMacOS=true;
if((navigator.userAgent.indexOf('Safari')!= -1)||(navigator.userAgent.indexOf('Konqueror')!= -1))
 _isKHTML=true;
else if(navigator.userAgent.indexOf('Opera')!= -1)
 _isOpera=true;
else if(navigator.appName.indexOf("Microsoft")!=-1)
 _isIE=true;
else{
 _isFF=true;
 var _FFrv=parseFloat(navigator.userAgent.split("rv:")[1])
}

var _isIE6 = false;
if (_isIE) this._isIE6 = (window.XMLHttpRequest==null?true:false);

function isIE(){
 if(navigator.appName.indexOf("Microsoft")!=-1)
 if(navigator.userAgent.indexOf('Opera')== -1)
 return true;
 return false;
}

/*
This is used to display a message on the footer section of the main page
*/
function displaySearchDuration(showEvenIfZero) {
   var totalLines = document.getElementById("totalLines");
   if (totalLines.value != null) {
     if (totalLines.value*1 > 0 || showEvenIfZero) {
       var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
       var now = new Date();
       var minutes = 0;
       var seconds = 0;
       //the duration is in milliseconds
       var searchDuration = now.getTime()-(startSearchTime.value*1);
       if (searchDuration > (1000*60)) {   //calculating minutes
         minutes = Math.round((searchDuration / (1000*60)));
         var remainder = searchDuration % (1000*60);
         if (remainder > 0) {   //calculating seconds
           seconds = Math.round(remainder / 1000);
         }
       }else {  //calculating seconds
         seconds = Math.round(searchDuration / 1000);
       }
       var footer = parent.document.getElementById("footer");
       if (minutes != 0) {
         footer.innerHTML = messagesData.recordFound+": "+totalLines.value+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
       }else {
         footer.innerHTML = messagesData.recordFound+": "+totalLines.value+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
       }
     }else {
       var footer = parent.document.getElementById("footer");
       footer.innerHTML ="";
     }
   }else {
     var footer = parent.document.getElementById("footer");
     footer.innerHTML ="&nbsp;";
   }
}

/*
This is used to display a message on the footer section of the main page
*/
function displayGridSearchDuration(showZeroIfOne) {
	if (showZeroIfOne == null) {
		showZeroIfOne = false;
	}
   var totalLines = document.getElementById("totalLines");
   if (totalLines.value != null) {
     if (totalLines.value*1 > 0) {
    	 var displayLines = $v("totalLines");
    	 if (displayLines ==1 && showZeroIfOne) {
    		 displayLines = "0";
    	 }
       var startSearchTime = parent.window.document.getElementById("startSearchTime");
       var now = new Date();
       var minutes = 0;
       var seconds = 0;
       //the duration is in milliseconds
       var searchDuration = now.getTime()-(startSearchTime.value*1);
       if (searchDuration > (1000*60)) {   //calculating minutes
         minutes = Math.round((searchDuration / (1000*60)));
         var remainder = searchDuration % (1000*60);
         if (remainder > 0) {   //calculating seconds
           seconds = Math.round(remainder / 1000);
         }
       }else {  //calculating seconds
         seconds = Math.round(searchDuration / 1000);
       }
       var footer = parent.document.getElementById("footer");
       if (minutes != 0) {
         footer.innerHTML = messagesData.recordFound+": "+displayLines+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
       }else {
         footer.innerHTML = messagesData.recordFound+": "+displayLines+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
       }
     }else {
       var footer = parent.document.getElementById("footer");
       footer.innerHTML ="";
     }
   }else {
     var footer = parent.document.getElementById("footer");
     footer.innerHTML ="&nbsp;";
   }
}

/*
This is used to display a message on the footer section of the main page
*/
function displayNoSearchSecDuration() {
   var totalLines = document.getElementById("totalLines");
   if (totalLines.value != null) {
     if (totalLines.value*1 > 0) {
       var startSearchTime = document.getElementById("startSearchTime");
       var endSearchTime = document.getElementById("endSearchTime");
       var minutes = 0;
       var seconds = 0;
       //the duration is in milliseconds
       var searchDuration = (endSearchTime.value*1)-(startSearchTime.value*1);
       if (searchDuration > (1000*60)) {   //calculating minutes
         minutes = Math.round((searchDuration / (1000*60)));
         var remainder = searchDuration % (1000*60);
         if (remainder > 0) {   //calculating seconds
           seconds = Math.round(remainder / 1000);
         }
       }else {  //calculating seconds
         seconds = Math.round(searchDuration / 1000);
       }
       var footer = document.getElementById("footer");
       if (minutes != 0) {
         footer.innerHTML = messagesData.recordFound+": "+totalLines.value+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
       }else {
         footer.innerHTML = messagesData.recordFound+": "+totalLines.value+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
       }
     }else {
       var footer = document.getElementById("footer");
       footer.innerHTML ="";
     }
   }else {
     var footer = document.getElementById("footer");
     footer.innerHTML ="&nbsp;";
   }
}

function getDisplaySearchDuration() {
	return parent.document.getElementById("footer").innerHTML;
}

function reDisplayGridSearchDuration(message) {
	if (message != null) {
		document.getElementById("footer").innerHTML = message.value;
	}else {
		document.getElementById("footer").innerHTML = "";
	}
}

/*Calculates the height and width. I only use the width though*/
function setWindowSizes() {
	if (typeof (window.innerWidth) == 'number') {
		// Non-IE
		myWidth = window.innerWidth - 30;
		myHeight = window.innerHeight;
	}
	else if (document.documentElement && (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
		// IE 6+ in 'standards compliant mode'
		myWidth = document.documentElement.clientWidth;
		myHeight = document.documentElement.clientHeight;
	}
	else if (document.body && (document.body.clientWidth || document.body.clientHeight)) {
		// IE 4 compatible
		myWidth = document.body.clientWidth;
		myHeight = document.body.clientHeight;
	}
	else if (parent.document.documentElement && (parent.document.documentElement.clientWidth || parent.document.documentElement.clientHeight)) {
		myWidth = parent.document.documentElement.clientWidth - 30;
		myHeight = parent.document.documentElement.clientHeight;
	}
	else if (parent.document.body && (parent.document.body.clientWidth || parent.document.body.clientHeight)) {
		myWidth = parent.document.body.clientWidth  - 30;
		myHeight = parent.document.body.clientHeight;
	}

//	window.alert('Width = ' + myWidth);
//	window.alert('Height = ' + myHeight);
}

/* Tells you if the page is currently visible to the user in the tabs. */
function isPageVisible(pageId)
{
var pageVisible = false;
  try
  {
     for (var i=0; i<parent.tabDataJson.length; i++)
     {
      if (parent.tabDataJson[i].tabId == pageId && parent.tabDataJson[i].status == "open")
      {
        var target = parent.window.document.getElementById(""+pageId+"div");
        if (target.style["display"] != 'none')
        {
         pageVisible = true;
        }
      }
     }
  }
  catch(ex)
  {
   //alert("Exception....265");
   pageVisible = true;
  }

 if (pageId == null || pageId.trim().length ==0)
 {
     pageVisible = false;
 }
 return pageVisible;
}

function getHiddenNameElementById( elementId ) {
	try {
		var len = elementId.length;
		var elementName = elementId + 'Name';
		if( ( elementId.substr( 0, len - 2) +"Id" ) == elementId )
			elementName = elementId.substr( 0, len - 2) + "Name";
		var ele = $(elementName);
		if( ele ) return ele;
		var hiddendiv = $('hiddenElements');
		var link = document.createElement("input");
		link.setAttribute('id', elementName );
		link.setAttribute('name', elementName );
		link.setAttribute('value', "" );
		hiddendiv.appendChild(link);
		return $(elementName);
	} catch(ex){}
}

function setDropDownNames(ids) {
	try {
	for(var i=0;i< ids.length; i++ ) {
		var des = getHiddenNameElementById(ids[i]);
		var src = $(ids[i]);
		des.value = src.options[src.selectedIndex].text;
		}
	}catch(ex){}
}

(function(original) {
	// a value of true implies the attachEvent function is defined
	if (!original) {
		HTMLBodyElement.prototype.attachEvent = function(arg0, arg1){
			if (typeof original == "undefined") {
				if (arguments[0].startsWith("on")) {
					arguments[0] = arguments[0].substr(2);
				}
				return HTMLBodyElement.prototype.addEventListener.apply(this, arguments);
			}
			else {
				arguments[0] = 'on' + arguments[0];
				return original.apply(this, arguments);
			}
		};
	}
})((typeof HTMLBodyElement != 'undefined')?HTMLBodyElement.prototype.attachEvent:true);

// Create a hidden input element, and append it to the form:
function addHiddenVariable(theForm, theName, theValue) {
    var hiddenFormField = document.createElement('input');
    hiddenFormField.type = 'hidden';
    hiddenFormField.name = theName;
    hiddenFormField.value = theValue;
    theForm.appendChild(hiddenFormField);
}

function isArray(testObject) {
	return testObject && !(testObject.propertyIsEnumerable('length'))
			&& typeof testObject === 'object'
			&& typeof testObject.length === 'number';
}

function changeElementToSpan(elementId, displayedValue, storedValue) {
	var parentNode = $(elementId).parentNode;
	parentNode.removeChild($(elementId));
	var spanElement = document.createElement("span");
	spanElement.innerHTML = displayedValue;
	spanElement.name = elementId + "Display";
	spanElement.id = elementId + "Display";
	var hiddenInputElement = document.createElement("input");
	hiddenInputElement.type = "hidden";
	hiddenInputElement.name = elementId;
	hiddenInputElement.id = elementId;
	hiddenInputElement.value = storedValue;
	parentNode.appendChild(spanElement);
	parentNode.appendChild(hiddenInputElement);
}