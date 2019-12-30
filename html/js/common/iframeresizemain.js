var searchFrameHeight=0;
var resizeFramecount=1; /*Need this to stop triggering an infinite loop of resize events in IE.I resize the frames only when this is 0 and applicationResizeFramecount is 0.*/
var showsearchresults=false; /*If this is true we show the result section of the page*/
var resultWidthResize=true; /*If this is set to false, I don't change the width of the result table upon resize.*/

var myWidth = 0;
var myHeight = 0;

function resizeFrames() {
 if (parent.applicationResizeFramecount*1 >= 0)
 {
  appResizeFramecount = parent.applicationResizeFramecount*1;
 }
 else
 {
  appResizeFramecount = 0;
 }

 if (resizeFramecount ==0 && appResizeFramecount ==0 )
 {
  //alert("resizeFrames");
  try
  {
  //searchFrameHeight = window.frames["searchFrame"].document.getElementById("searchMainPage").offsetHeight;
  }
  catch (ex)
  {
    searchFrameHeight=5;
  }

  //if (_isIE) /*Need to hide the result section to get the height of window content correclty for IE*/
  {
   window.document.getElementById("resultFrameDiv").style["display"] = "none";
  }
  /*Need to trigger resize of search frame and then the result frame to avoid running into each other*/
  /*setTimeout('resizeSearchFrame()',10);*/
  setTimeout('resizeResultFrame()',10);
  resizeFramecount++;
 }
}

/*The reason to resize the search frame is to not worry about declaring the height of the iframe in the main JSP.
The search resize automatically adjusts the height of the iframe to fit the contents.
This avoids you the trouble of figuring out what is the optimum height of the search frame.
Just figuring out the optimum width of the search table is work enough.*/
function resizeSearchFrame() {
 resizeFrame("searchFrame",searchFrameHeight);
 /*Triggering the resize of result frame after the browser resizes the search frame*/
 setTimeout('resizeResultFrame()',10);
}

function resizeResultFrame() {
 /*Show the results section only if there is something to show.
 Not showing the result section when the user first gets to the page*/
 if (showsearchresults)
 {
  resizeFrame("resultFrame",10);
  window.document.getElementById("resultFrameDiv").style["display"] = "";

  var resultFrameDiv = document.getElementById("resultFrameDiv");
  resultFrameDivHeight = resultFrameDiv.offsetHeight;
  resultTableheight = window.frames["resultFrame"].document.getElementById("resultsPageTable").offsetHeight;
  /*If the height of the result table is less than the height of the window, want to resize the frame to match
  the height of the taable so that there is no white space above the gray border.*/
  if (resultTableheight < resultFrameDivHeight)
  {
   resizeFrame("resultFrame",10);
  }
 }
 setTimeout('resetResizeFramecount()',20);
}

function resetResizeFramecount() {
 resizeFramecount=0;
}

function resizeFrame(frameName,frameHeight) {
 var matchResultSectionDivs= false;
 if (frameName == "resultFrame")
 {
  try
  {
   setWindowSizes();
   resultTableheight = window.frames["resultFrame"].document.getElementById("resultsPageTable").offsetHeight;
   resultTableWidth = window.frames["resultFrame"].document.getElementById("resultsPageTable").offsetWidth;

	 var topNavigationDivHeight	=0;
   try
   {
    var topNavigationDiv = document.getElementById("topNavigation");
    topNavigationDivHeight = topNavigationDiv.offsetHeight;
   }
   catch (ex)
   {
    topNavigationDivHeight	=0;
   }

   var footerDivHeight	=0;
   try
   {
    var footerDiv = document.getElementById("footer");
    footerDivHeight = footerDiv.offsetHeight+10;
   }
   catch (ex)
   {
    footerDivHeight	=0;
   }

   if (searchFrameHeight == 0)
   {
     try
     {
      searchFrameHeight = window.frames["searchFrame"].document.getElementById("searchMainPage").offsetHeight;
     }
     catch (ex)
     {
       searchFrameHeight=5;
     }
   }

   bodyOffsetHeight = window.document.body.offsetHeight;
   if (bodyOffsetHeight == 0 && intcmIsApplication)
   {
      parentFrameHeight = parent.window.document.body.offsetHeight;
     try
     {
      appTabsHeight =parent.window.document.getElementById("appTabs").offsetHeight;
         //alert("appTabsHeight  "+appTabsHeight);
      headerHeight =parent.window.document.getElementById("appHeaderSection").offsetHeight;
         //alert("headerHeight  "+headerHeight);
      menuContainerHeight =parent.window.document.getElementById("menuSection").offsetHeight;
         //alert("menuContainerHeight  "+menuContainerHeight);
        bodyOffsetHeight = parentFrameHeight - appTabsHeight -menuContainerHeight -headerHeight-10;
      }
     catch (ex)
     {
         
     }
   }     
   frameName = document.getElementById(""+frameName+"");
   if (_isIE)
   {
    resultFrameHeight = bodyOffsetHeight-topNavigationDivHeight-searchFrameHeight-footerDivHeight-75;
    //75 is to accomodate the bottom scrollbars
    //alert("resultTableheight: "+resultTableheight+" resultFrameHeight: "+resultFrameHeight+"");
    //alert("bodyOffsetHeight: "+bodyOffsetHeight+" topNavigationDivHeight: "+topNavigationDivHeight+" searchFrameHeight: "+searchFrameHeight+" footerDivHeight: "+footerDivHeight+"");        
    if (resultTableheight != 0 && resultTableheight < resultFrameHeight)
    {
     resultFrameHeight = resultTableheight;
     if (resultTableWidth > frameName.width)
     {
       resultFrameHeight = resultFrameHeight +20;
     }
     matchResultSectionDivs = true;
    }
   }
   else
   {
    resultFrameHeight = bodyOffsetHeight-topNavigationDivHeight-searchFrameHeight-footerDivHeight-65;
    //65 is to accomodate the bottom scrollbars
    //alert("resultTableheight: "+resultTableheight+" resultFrameHeight: "+resultFrameHeight+"");
    if (resultTableheight != 0 && resultTableheight < resultFrameHeight)
    {
     resultFrameHeight = resultTableheight;
     if (resultTableWidth > frameName.width)
     {
       resultFrameHeight = resultFrameHeight +20;
     }
     matchResultSectionDivs = true;
    }
   }

   /*If the result table is less than the minimum height we keep the result frame height at minimum height.
   When there are no records no need to show minimum height*/
   var minHeight = window.frames["resultFrame"].document.getElementById("minHeight");
   var minHeightValue = 0;
   try
   {
     //alert("minHeight  "+minHeight.value.trim()*1);
     minHeightValue = minHeight.value.trim()*1;
   }
   catch(ex)
   {
     //alert("Here error 86");
     minHeightValue = 0;
   }

  var totalLines = window.frames["resultFrame"].document.getElementById("totalLines").value;    
  if (totalLines != 0)
  {
    if (resultFrameHeight < minHeightValue)
   {
    resultFrameHeight = minHeightValue;
   }
  }
   if (resultFrameHeight > 150 || matchResultSectionDivs)
   {
   frameName.height=resultFrameHeight;
   }

   if (myWidth >300 && resultWidthResize)
   {
   /*Need to do this for IE, when the width of result table is > width of window to get the scrollbars correct*/
   frameName.width=myWidth-37;
   }
  }
  catch (ex)
  {
   alert("here error 68 resizeFrame()");
  }
 }
 else /*For search frame*/
 {
  frameName = document.getElementById(""+frameName+"");
  frameName.height=frameHeight;
 }
}

function showPleaseWait()
{
resizeFramecount=1;
window.document.getElementById("resultFrameDiv").style["display"] = "none";
window.document.getElementById("transitPage").style["display"] = "";
}

function stopPleaseWait()
{
 window.document.getElementById("transitPage").style["display"] = "none";
}