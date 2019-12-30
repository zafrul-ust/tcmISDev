var showErrorMessage = false;
var showUpdateLinks = false;

function setSearchFrameSize() {
parent.stopPleaseWait();
searchMainPageHeight = document.getElementById("searchMainPage").offsetHeight;
if (searchMainPageHeight !=0)
{
 parent.searchFrameHeight=searchMainPageHeight; /*Need to keep track of search frame height to be used upon resize of main window*/
 parent.resizeFramecount=1;
 parent.resizeFrame("searchFrame",searchMainPageHeight);
 setTimeout('parent.resetResizeFramecount()',10); /*Giving the browser some time to do the resize, helpfull for IE*/
}
}