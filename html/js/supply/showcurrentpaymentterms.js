var cptGrid;
var cptExpGrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;
var resizeResultscount=1;
var windowCloseOnEsc = true;
var  appResizeFramecount = 0;

var myWidth = 0;
var myHeight = 0;
var lastWindowWidth=0; /*This is to keep track of what the size of the window was before re-size*/
var resultGridHeight =0;
function resultOnLoad()
{
 //try{
	//showTransitWin();
	
	var pTermsTotalLines = $v('paymentTermsTotalLines');
	var pTermsExcepTotalLines = $v('paymentTermsExcepTotalLines');
	
	if(pTermsTotalLines >0)
	{	
	
	
	   $("currentPaymentTermsViewBean").style["display"] = "";
	   initializeCPTGrid();
	}
	else
	{
		$("currentPaymentTermsViewBean").style["display"] =  "none";
	}
	
	if(pTermsExcepTotalLines>0)
	{	
		$("paymentTermIgExceptionViewBean").style["display"] = "";
		initializeCPTExceptionsGrid();
	}
	else
	{
		$("paymentTermIgExceptionViewBean").style["display"] =  "none";
	
	}
	
	
	
	//stopTransitWin();
	//$('resultGridDiv').style.display="";
	
	 setResultSize();
	 resizeResultscount =0;
/*This dislpays our standard footer message*/
//displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
// setResultSize();  
//}



}

function initializeCPTGrid(){
	 $('currentPaymentTermsViewBean').style.height = "200px";
	cptGrid = new dhtmlXGridObject('currentPaymentTermsViewBean');

	 initGridWithConfigForPopUp(cptGrid,cptConfig,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
		 cptGrid.parse(jsonMainData,"json");
		 
		$('currentPaymentTermsViewBean').style.display="";
		 $('paymentTermsFooter').innerHTML = "&nbsp;&nbsp;"+messagesData.recordFound+": "+$('paymentTermsTotalLines').value;
		 
		  
	 }
	}


function initializeCPTExceptionsGrid(){
	 $('paymentTermIgExceptionViewBean').style.height = "200px";
	cptExpGrid = new dhtmlXGridObject('paymentTermIgExceptionViewBean');

	 initGridWithConfigForPopUp(cptExpGrid,cptExpConfig,false,true);
	 if( typeof( jsonCPTExpMainData ) != 'undefined' ) {
		 cptExpGrid.parse(jsonCPTExpMainData,"json");
		 $('paymentTermIgExceptionViewBean').style.display="";
		 $('paymentTermsExcepFooter').innerHTML = "&nbsp;&nbsp;"+messagesData.recordFound+": "+$('paymentTermsExcepTotalLines').value;
	 }
	}


function showTransitWin() {
	 // resizeFramecount = 1;
	 $("resultGridDiv").style["display"] = "none";
	 $("currentPaymentTermsExpFrameDiv").style["display"] = "none";
	 $("resultFrameDiv").style["display"] = "none";
	 $("transitPage").style["display"] = "";		 
	
	}


function stopTransitWin() {
	 // resizeFramecount = 1;
	 $("transitPage").style["display"] = "none";
	 $("resultGridDiv").style["display"] = "";
	 $("currentPaymentTermsExpFrameDiv").style["display"] = "";	
	 $("currentPaymentTermsViewBean").style["display"] = "";	
	 $("resultFrameDiv").style["display"] = "";	 
	
	// $("customerCarrierFrameDiv").style["display"] = "none";	 	 
	
	}


function resizeWin() {
			
	 if (resizeResultscount ==0 && appResizeFramecount ==0 )
	 {
		 
	  resizeThisWinResults();
	  resizeResultscount=0;
	  resizeResultscount++;
	  try
	  {
	   if (resizeGridWithWindow)
	   {
	    setTimeout('reSizeNoSearchGridWidths();',5);
	    
	   }
	  }
	  catch(exGrid)
	  {
		  
	   //alert("Here 209");
	  }    
	 }
	 
	 resizeResultscount =0;
	}


function reSizeNoSearchGridWidths()
{
  if (cptGrid !=null)  { 
    reSizeCoLumnWidths(cptGrid);
  }
  
  if (cptExpGrid !=null)
  { 
    reSizeCoLumnWidths(cptExpGrid);
  }
}


function resizeThisWinResults(extraReduction) {

	 if (extraReduction ==  null)
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

	   var footer1DivHeight=8;
	   var footer2DivHeight=8;
	   try
	   {
	    var footer1Div = document.getElementById("paymentTermsFooter");
	    var footer2Div = document.getElementById("paymentTermsExcepFooter");
	    footer1DivHeight = footer1Div.offsetHeight+8;
	    footer2DivHeight = footer2Div.offsetHeight+8;
	    if(footer1DivHeight ==0)
	    {
	    	footer1DivHeight = 8;
	    }
	    if(footer2DivHeight ==0)
	    {
	    	footer2DivHeight = 8;
	    }
	   }
	   catch (ex)
	   {
		   footer1DivHeight	=8;
		   footer2DivHeight	=8;
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
	   var paymentTermsTotalLines =0;
	   var paymentTermsExcepTotalLines =0;
	   try
	   {
		   paymentTermsTotalLines = document.getElementById("paymentTermsTotalLines").value;
		   paymentTermsExcepTotalLines = document.getElementById("paymentTermsExcepTotalLines").value;
	   }
	   catch (ex)
	   {
	    
	    paymentTermsTotalLines = 0;
		paymentTermsExcepTotalLines = 0;
	   }

	   //alert("myHeight: "+myHeight+" topNavigationDivHeight: "+topNavigationDivHeight+" searchSectionHeight: "+searchSectionHeight+" mainUpdateLinksHeight "+mainUpdateLinksHeight+" footerDivHeight: "+footerDivHeight+"");
	   if (_isIE)
	   {
	    resultSectionHeight = myHeight-12-topNavigationDivHeight-mainUpdateLinksHeight-footer1DivHeight-footer2DivHeight-internalHeightIEOffset-extraReduction;
	   }
	   else
	   {
	    resultSectionHeight = myHeight-12-topNavigationDivHeight-mainUpdateLinksHeight-footer1DivHeight-footer2DivHeigh-internalHeightFFOffset-extraReduction;
	   }

	   if (paymentTermsTotalLines == 0 && paymentTermsExcepTotalLines==0)
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

	  if ((paymentTermsTotalLines != 0) && (paymentTermsExcepTotalLines!=0))
	  {
	   if (resultSectionHeight < minHeightValue)
	   {
	    resultSectionHeight = minHeightValue;
	   }
	  }

	  if (resultSectionHeight > 150 || matchResultSectionDivs)
	  {
	   //frameName.height=resultSectionHeight;

	   if ((paymentTermsTotalLines != 0) && (paymentTermsExcepTotalLines!=0))
	   {
	     try
	     {
	    	 //setTimeout('setThisGrid1Height('+resultSectionHeight+');',2);
	    	 //setTimeout('setThisGrid2Height('+resultSectionHeight+');',2);
	     }
	     catch(exGrid)
	     {
	       //alert("Here 209");
	     }

	     try
	     {
	       setTimeout('setGrid1Size();',2);
	       setTimeout('setGrid2Size();',2);
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
	    setTimeout('setGrid1Width();',50);
	    setTimeout('setGrid2Width();',50);
	    
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

function setThisGrid1Height(resultGridHeight)
{
  try
  {
   var id=cptGrid.entBox.id;
   var griDiv = document.getElementById(id);
   griDiv.style.height = resultGridHeight-3 + "px";
  }
  catch(ex)
  {
      //alert("THis means the grid was not initialized");
  }
}

function setThisGrid2Height(resultGridHeight)
{
  try
  {
   var id=cptExpGrid.entBox.id;
   var griDiv = document.getElementById(id);
   griDiv.style.height = resultGridHeight-3 + "px";
  }
  catch(ex)
  {
      //alert("THis means the grid was not initialized");
  }
}


function setGrid1Size()
{
	cptGrid.setSizes();
}


function setGrid2Size()
{
	cptExpGrid.setSizes();
}

function setGrid1Width()
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
   var id=cptGrid.entBox.id;
   var griDiv = document.getElementById(id);
   griDiv.style.width = myWidth + "px";
   lastWindowWidth = myWidth;
  }
  catch(ex)
  {
      //alert("THis means the grid was not initialized");
  }
}

function setGrid2Width()
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
   var id=cptExpGrid.entBox.id;
   var griDiv = document.getElementById(id);
   griDiv.style.width = myWidth + "px";
   lastWindowWidth = myWidth;
  }
  catch(ex)
  {
      //alert("THis means the grid was not initialized");
  }
}


function showCPTDiv() {
	 document.getElementById("currentPaymentTermsDiv").style["display"] = "";
	 document.getElementById("showCPTDivRow").style["display"] = "none";
	 document.getElementById("hideCPTDivRow").style["display"] = "";
	 
}

function hideCPTDiv() {
	 document.getElementById("currentPaymentTermsDiv").style["display"] = "none";
	 document.getElementById("showCPTDivRow").style["display"] = "";
	 document.getElementById("hideCPTDivRow").style["display"] = "none";
}


function showCPTExpDiv() {
	 document.getElementById("currentPaymentTermsExceptionDiv").style["display"] = "";
	 document.getElementById("showCPTExpDivRow").style["display"] = "none";
	 document.getElementById("hideCPTExpDivRow").style["display"] = "";
	 
}

function hideCPTExpDiv() {
	 document.getElementById("currentPaymentTermsExceptionDiv").style["display"] = "none";
	 document.getElementById("showCPTExpDivRow").style["display"] = "";
	 document.getElementById("hideCPTExpDivRow").style["display"] = "none";
}
