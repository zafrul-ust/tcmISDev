var resizeResultscount=1; /*Need this to stop triggering an infinite loop of resize events in IE.I resize the frames only when this is 0 and applicationResizeFramecount is 0.*/
var resizeGridWithWindow=true; /*If this is set to false, I don't change the width of the result table upon resize.*/
var addNewRowOK = false;
var showErrorMessage = false;
var showUpdateLinks = false;

var myWidth = 0;
var myHeight = 0;
var lastWindowWidth=0; /*This is to keep track of what the size of the window was before re-size*/
var resultGridHeight =0;

function resizeFrames(extraReduction) {
try
{
 if (parent.applicationResizeFramecount*1 >= 0)
 {
  appResizeFramecount = parent.applicationResizeFramecount*1;
 }
 else
 {
  appResizeFramecount = 0;
 }
}
catch(ex)
{
  appResizeFramecount = 0;	
}
	
 if (resizeResultscount ==0 && appResizeFramecount ==0 )
 {
  if (typeof( extraReduction ) == 'undefined' || extraReduction ==  null)
    resizeResults();
  else
    resizeResults(extraReduction);
  
  setTimeout('resizeResultsCount()',5);
  resizeResultscount++;
  try
  {
   if (resizeGridWithWindow)
   {
    setTimeout('reSizeGridCoLWidths();',5);
   }
  }
  catch(exGrid)
  {
   //alert("Here 209");
  }    
 }
}

function resizeResultsCount() {
 resizeResultscount=0;
}

var internalHeightIEOffset = 40;
var internalHeightFFOffset = 40;
function resizeResults(extraReduction) {

 if (typeof( extraReduction ) == 'undefined' || extraReduction ==  null)
 {
  extraReduction = 0;
 }

  var matchResultSectionDivs= false;
  try
  {
   setWindowSizes();
      
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

   var footerDivHeight=8;
   try
   {
    var footerDiv = document.getElementById("footer");
    footerDivHeight = footerDiv.offsetHeight+8;
    if(footerDivHeight ==0)
    {
     footerDivHeight = 8;
    }
   }
   catch (ex)
   {
    footerDivHeight	=8;
   }

   var mainUpdateLinksHeight=0;
   try
   {
    var mainUpdateLinksDiv = document.getElementById("mainUpdateLinks");
    mainUpdateLinksHeight = mainUpdateLinksDiv.offsetHeight;
    if(mainUpdateLinksHeight ==0)
    {
      mainUpdateLinksHeight = 8;
    }
    else
    {
        if (_isIE)
        {
            mainUpdateLinksHeight = mainUpdateLinksHeight +5;
        }
        else
        {
            mainUpdateLinksHeight = mainUpdateLinksHeight +8;
        }
    }
   }
   catch (ex)
   {
    mainUpdateLinksHeight=8;
   }

   //bodyOffsetHeight = window.document.body.offsetHeight;   
   matchResultSectionDivs = true;
   var totalLines =0;
   try
   {
    totalLines = document.getElementById("totalLines").value;
   }
   catch (ex)
   {
    totalLines=0;
   }

   //alert("myHeight: "+myHeight+" topNavigationDivHeight: "+topNavigationDivHeight+" searchSectionHeight: "+searchSectionHeight+" mainUpdateLinksHeight "+mainUpdateLinksHeight+" footerDivHeight: "+footerDivHeight+"");
   if (_isIE)
   {
    resultSectionHeight = myHeight-12-topNavigationDivHeight-mainUpdateLinksHeight-footerDivHeight-internalHeightIEOffset-extraReduction;
   }
   else
   {
    resultSectionHeight = myHeight-12-topNavigationDivHeight-mainUpdateLinksHeight-footerDivHeight-internalHeightFFOffset-extraReduction;
   }

   if (totalLines == 0 && addNewRowOK == false)
   {
     resultSectionHeight = 25;
   }

   /*If the result table is less than the minimum height we keep the result frame height at minimum height.
   When there are no records no need to show minimum height*/
   var minHeight = document.getElementById("minHeight");
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

  //if (totalLines != 0)
  {
   if (resultSectionHeight < minHeightValue)
   {
    resultSectionHeight = minHeightValue;
   }
  }

  if (resultSectionHeight > 150 || matchResultSectionDivs)
  {
   //frameName.height=resultSectionHeight;

   //if (totalLines != 0)
   {
     try
     {
       setTimeout('setGridHeight('+resultSectionHeight+');',2);
     }
     catch(exGrid)
     {
       //alert("Here 209");
     }

     try
     {
       setTimeout('setGridSize();',2);
     }
     catch(exGrid)
     {
       //alert("Here 209");
     }
   }
 }

  if (resizeGridWithWindow)
  {  
  try
  {
    setTimeout('setGridWidth();',50);
  }
  catch(exGrid)
  {
    //alert("Here 209");
  }    	 
  }
 }
 catch (ex)
 {
   alert("here error 68 resizeResults()");
 }
}

function setResultSize(extraReduction) {
 resizeResultscount=1;

 try
 {
  document.getElementById("transitPage").style["display"] = "none";

  var resultGridDiv = document.getElementById("resultGridDiv");
  resultGridDiv.style["display"] = "";
  
  //global variable totalLines raises no 'value' attribute error in IE7
  var totalLines = document.getElementById("totalLines").value;
  if (totalLines == 0) /*Dont show any links if no data found*/
  {
   document.getElementById("mainUpdateLinks").style["display"] = "none";
  }
  else
  {
   document.getElementById("mainUpdateLinks").style["display"] = "";
  }

  if (typeof( extraReduction ) != 'undefined') 
    setTimeout('resizeResults('+extraReduction+')',5);
  else
    setTimeout("resizeResults()",5);
  setTimeout('resizeResultsCount()',10);
 }
 catch (exError){
 }

 if (showErrorMessage)
 {
  setTimeout('showErrorMessages()',50); /*Showing error messages if any*/
 }
}

function showPleaseWait()
{
 resizeResultscount=1;
 document.getElementById("resultGridDiv").style["display"] = "none";
 document.getElementById("transitPage").style["display"] = "";
}

function stopPleaseWait()
{
 document.getElementById("transitPage").style["display"] = "none";
 document.getElementById("resultGridDiv").style["display"] = "";
}

/*This is to re-size column widths based on window size.*/
function reSizeGridCoLWidths()
{
  if (haasGrid !=null)
  {
    reSizeCoLumnWidths(haasGrid);
  }
}

/*This is called to set the grid height.*/
function setGridHeight(resultGridHeight)
{
  try
  {
   var id=haasGrid.entBox.id;
   var griDiv = document.getElementById(id);
   griDiv.style.height = resultGridHeight-3 + "px";
  }
  catch(ex)
  {
      //alert("THis means the grid was not initialized");
  }
}

var internalWidthIEOffset = 37;
var internalWidthFFOffset = 5;
/*This is called to set the grid width.*/
function setGridWidth()
{
 setWindowSizes(); 
 if (_isIE)
 {
   myWidth = myWidth - internalWidthIEOffset;
 }
 else
 {
   myWidth = myWidth - internalWidthFFOffset;
 }
    
  try
  {
   var id=haasGrid.entBox.id;
   var griDiv = document.getElementById(id);
   griDiv.style.width = myWidth + "px";
   lastWindowWidth = myWidth;
  }
  catch(ex)
  {
      //alert("THis means the grid was not initialized");
  }
}

function setGridSize()
{
  try
  {
    haasGrid.setSizes();
  }
  catch(exGrid)
  {
       //alert("Here 209");
  }  
}

