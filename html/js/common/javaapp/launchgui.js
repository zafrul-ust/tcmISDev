function launchGuiApp(hostUrl) {
  parent.showPleaseWait();
  parent.launchClientGui(hostUrl);
  return false;
}

/*Calling this from the result section*/
function myOnLoad()
{
 setResultFrameSize();
 setTimeout('hideResultFrame()',10000);
}

function hideResultFrame() {
 var resultFrameDiv = parent.document.getElementById("resultFrameDiv");
 resultFrameDiv.style["display"] = "none";
}


