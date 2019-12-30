var showErrorMessage = false;
var showUpdateLinks = false;

function setResultFrameSize() {
 parent.submitCount=0;
 parent.resizeFramecount=1;

 try
 {
  parent.document.getElementById("transitPage").style["display"] = "none";
  resultTableheight = window.document.getElementById("resultsPageTable").offsetHeight;

  parent.showsearchresults=true;
  parent.resizeFrame("resultFrame",10);

  var resultFrameDiv = parent.document.getElementById("resultFrameDiv");
  resultFrameDiv.style["display"] = "";

  resultFrameDivHeight = resultFrameDiv.offsetHeight;

  /*If the height of the result table is less than the height of the window, want to resize the frame to match
  the height of the taable so that there is no white space above the gray border.*/
  if (resultTableheight < resultFrameDivHeight)
  {
   parent.resizeFrame("resultFrame",10);
  }
  setTimeout('parent.resetResizeFramecount()',20);

  totalLines = document.getElementById("totalLines").value;
  if (totalLines == 0) /*Dont show any links if no data found*/
  {
   parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
  }
  else
  {
   parent.document.getElementById("mainUpdateLinks").style["display"] = "";
  }
 }
 catch (exError){
 }

 if (showErrorMessage)
 {
  setTimeout('showErrorMessages()',100); /*Showing error messages if any*/
 }
}

function standardResultOnLoad()
{
 setResultFrameSize();
 try{ // don't show error please...
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 } catch(ex) {}
  try{ // don't show error please...
	 displaySearchDuration();
 } catch(ex) {}
}

// Larry Note: will be overwritten if it also exist in result js files, so its safe.
function showErrorMessages()
{       
		parent.showErrorMessages();
}

function showNotes(fieldid)
{
   try {
	var html = document.getElementById(fieldid);
	if( html.tagName.toLowerCase()  == 'div' )
		parent.showNotes( html.innerHTML );
	else
   		parent.showNotes( html.value );
   } catch(ex) {}
}
