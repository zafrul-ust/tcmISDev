var unPaidCashGrid;
var openOrdersGrid;
var openInvoiceGrid;

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
	
	var unpaidCashTotalLines = $v('unpaidCashTotalLines');
	var openInvoicesTotalLines = $v('openInvoicesTotalLines');
	var openOrdersTotalLines = $v('openOrdersTotalLines');
	
	
	if(unpaidCashTotalLines >0)
	{	
	
	
	   $("CustomerUnappliedCashViewBean").style["display"] = "";
	   initializeuUnPaidCashGrid();
	}
	else
	{
		$("CustomerUnappliedCashViewBean").style["display"] =  "none";
	}
	
	if(openInvoicesTotalLines>0)
	{	
		$("CustomerOpenInvoiceViewBean").style["display"] = "";
		initializeOpenInvoiceGrid();
	}
	else
	{
		$("CustomerOpenInvoiceViewBean").style["display"] =  "none";
	
	}
	
	if(openOrdersTotalLines>0)
	{	
		$("CustomerOpenOrdersViewBean").style["display"] = "";
		initializeOpenOrdersGrid();
	}
	else
	{
		$("CustomerOpenOrdersViewBean").style["display"] =  "none";
	
	}
	
	
	
	
	stopTransitWin();
	//$('resultGridDiv').style.display="";
	
	// setResultSize();
	 resizeResultscount =0;
/*This dislpays our standard footer message*/
 //displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
// setResultSize();  
//}



}



function showTransitWin() {
	 // resizeFramecount = 1;
	 
	 $("transitPage").style["display"] = "";
	 $("resultGridDiv").style["display"] = "none";	 	
	 $("openInvoiceFrameDiv").style["display"] = "none";	
	 $("openOrdersFrameDiv").style["display"] = "none";	
	 $("resultFrameDiv").style["display"] = "none";	 
	
	}


function stopTransitWin() {
	 // resizeFramecount = 1;
	 $("transitPage").style["display"] = "none";
	 $("resultGridDiv").style["display"] = "";	 	
	 $("openInvoiceFrameDiv").style["display"] = "";	
	 $("openOrdersFrameDiv").style["display"] = "";	
	 $("resultFrameDiv").style["display"] = "";	 
	
	// $("customerCarrierFrameDiv").style["display"] = "none";	 	 
	
	}


/*function resizeWin() {
			
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
	}*/

/*
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
*/

/*function resizeThisWinResults(extraReduction) {

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

	   If the result table is less than the minimum height we keep the result frame height at minimum height.
	   When there are no records no need to show minimum height
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
*/

function showUnPaidCashDiv() {
	 document.getElementById("unPaidCashDiv").style["display"] = "";
	 document.getElementById("showUnPaidCashDivRow").style["display"] = "none";
	 document.getElementById("hideUnPaidCashDivRow").style["display"] = "";
	 
}

function hideUnPaidCashDiv() {
	 document.getElementById("unPaidCashDiv").style["display"] = "none";
	 document.getElementById("showUnPaidCashDivRow").style["display"] = "";
	 document.getElementById("hideUnPaidCashDivRow").style["display"] = "none";
}


function showOpenInvoicesDiv() {
	 document.getElementById("openInvoicesDiv").style["display"] = "";
	 document.getElementById("showOpenInvoicesDivRow").style["display"] = "none";
	 document.getElementById("hideOpenInvoicesDivRow").style["display"] = "";
	 
}

function hideOpenInvoicesDiv() {
	 document.getElementById("openInvoicesDiv").style["display"] = "none";
	 document.getElementById("showOpenInvoicesDivRow").style["display"] = "";
	 document.getElementById("hideOpenInvoicesDivRow").style["display"] = "none";
}


function showOpenOrdersDiv() {
	 document.getElementById("openOrdersDiv").style["display"] = "";
	 document.getElementById("showOpenOrdersDivRow").style["display"] = "none";
	 document.getElementById("hideOpenOrdersDivRow").style["display"] = "";
	 
}

function hideOpenOrdersDiv() {
	 document.getElementById("openOrdersDiv").style["display"] = "none";
	 document.getElementById("showOpenOrdersDivRow").style["display"] = "";
	 document.getElementById("hideOpenOrdersDivRow").style["display"] = "none";
}


function initializeuUnPaidCashGrid(){
	unPaidCashGrid = new dhtmlXGridObject('CustomerUnappliedCashViewBean');

	 initGridWithConfig(unPaidCashGrid,unPaidCashConfig,false,true);
	 if( typeof( unPaidCashJsonMainData ) != 'undefined' ) {
	 unPaidCashGrid.parse(unPaidCashJsonMainData,"json");
	 
	 
	 $('CustomerUnappliedCashViewBean').style.display="";
	 $('unpaidCashFooter').innerHTML = "&nbsp;&nbsp;<b>"+messagesData.recordFound+":</b> "+$('unpaidCashTotalLines').value+" &nbsp;&nbsp;<b>"+messagesData.total+":</b> "+$('unPaidCurrencyId').value+" "+$('unPaidAmountTotal').value;

	 }
	}



function initializeOpenInvoiceGrid(){
	openInvoiceGrid = new dhtmlXGridObject('CustomerOpenInvoiceViewBean');

	 initGridWithConfig(openInvoiceGrid,openInvoiceConfig,false,true);
	 if( typeof( openInvoiceJsonMainData ) != 'undefined' ) {
		 openInvoiceGrid.parse(openInvoiceJsonMainData,"json");
		 
		 $('CustomerOpenInvoiceViewBean').style.display="";
		 $('openInvoiceFooter').innerHTML = "&nbsp;&nbsp;<b>"+messagesData.recordFound+":</b> "+$('openInvoicesTotalLines').value+" &nbsp;&nbsp;<b>"+messagesData.total+":</b> "+$('openInvoicesCurrencyId').value+" "+$('openInvoicesTotal').value;

	 }
}


function initializeOpenOrdersGrid(){
	openOrdersGrid = new dhtmlXGridObject('CustomerOpenOrdersViewBean');

	 initGridWithConfig(openOrdersGrid,openOrdersConfig,false,true);
	 if( typeof( openOrdersJsonMainData ) != 'undefined' ) {
	 openOrdersGrid.parse(openOrdersJsonMainData,"json");
	 
	 $('CustomerOpenOrdersViewBean').style.display="";
	 $('openOrdersFooter').innerHTML = "&nbsp;&nbsp;<b>"+messagesData.recordFound+":</b> "+$('openOrdersTotalLines').value+" &nbsp;&nbsp;<b>"+messagesData.total+":</b> "+$('openOrderCurrencyId').value+" "+$('openOrderTotal').value;

	 }
}

function createExcel(uAction) {
	$('uAction').value = uAction; 
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_CreditReviewExcel','650','600','yes');
    document.genericForm.target='_CreditReviewExcel';
    
    var a = window.setTimeout("document.genericForm.submit();",50);
}




