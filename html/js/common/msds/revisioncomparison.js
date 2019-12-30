windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var children = new Array();


function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 
	if (totalLines > 0)
	{
		
		document.getElementById("RevisionComparisionView").style["display"] = "";

		initializeGrid();  
	}  
	else
	{
		document.getElementById("RevisionComparisionView").style["display"] = "none";   
	}
	
		/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
	
   try{
    parent.resetTimer();
   }
   catch (ex)
   {
   }
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('RevisionComparisionView');

	// initGridWithConfig(inputGrid,config,rowSpan,submitDefault,singleClickEdit)
    // -1 is for disable rowSpan and smart rendering, but sorting still works; false will disable rowSpan and sorting but smart rendering is enabled
    // set submitDefault to true: Send the data to the server
    // singleClickEdit: this is for type:"txt",
	initGridWithConfig(beangrid,config,false,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");

	}
	//beangrid.attachEvent("onRightClick",selectRightclick);
}

