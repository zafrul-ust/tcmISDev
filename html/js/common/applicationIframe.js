var minW = 800; /*Minimum width of the main window*/
var minH = 600; /*Minimum height of themain window*/
/*Need this to stop triggering an infinite loop of resize events in IE. I don't want to trigger resize events in the tabs when the main window is resized.
The tabs will be resied as they are clicked upon (made active). Only the current tab is resized when the main window is resized.*/
var applicationResizeFramecount=0;

var myApplicationWidth = 0;
var myApplicationHeight = 0;

/*Calculates the height and width. I only use the width though*/
function setApplicationWindowSizes() {
  if( typeof( window.innerWidth ) == 'number' ) {
    //Non-IE
    myApplicationWidth = window.innerWidth;
    myApplicationHeight = window.innerHeight;
  } else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
    //IE 6+ in 'standards compliant mode'
    myApplicationWidth = document.documentElement.clientWidth;
    myApplicationHeight = document.documentElement.clientHeight;
  } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
    //IE 4 compatible
    myApplicationWidth = document.body.clientWidth;
    myApplicationHeight = document.body.clientHeight;
  }
  //window.alert( 'Application Width = ' + myApplicationWidth );
  //window.alert( 'Application Height = ' + myApplicationHeight );
}

function verifySize(){
/*To set the window size to be atleast a minimum width*/
/* if (applicationResizeFramecount = 0)
 {
  //alert("verifySize");
  var resizeMe = false;
  setApplicationWindowSizes();
  var winW = myApplicationWidth;
  var winH = myApplicationHeight;

  if(minW>winW){winW=minW;resizeMe=true;}
  if(minH>winH){winH=minH;resizeMe=true;}

  if(resizeMe){
    window.resizeTo(winW,winH);
    setApplicationWindowSizes();
    /*var winW2 = winW - myApplicationWidth;
    var winH2 = winH - myApplicationHeight;
    window.resizeTo(winW + winW2,winH + winH2);*/
/*   alert("resizeMe");
  }
 }

 applicationResizeFramecount = 1;
 setTimeout('resetAppResizeFramecount()',20);*/

 if (selectedTabId.length > 0)
 {
  setTabFrameSize(selectedTabId);
 }
}

function resetAppResizeFramecount() {
 applicationResizeFramecount=0;
}

function logout() {
	try{
		var topDoc = window.top.document;
		topDoc.logoutForm.action.value = 'logout';
		topDoc.logoutForm.submit();
		return;
	}
	catch(ex) {
	}
}