resizeGridWithWindow = true;
var beangrid = null;

function resultOnLoad()
{
	
	totalLines = document.getElementById("totalLines").value; 
	
	if (totalLines > 0)
	{
		document.getElementById("scanScoreReportBean").style["display"] = "";
		
		initializeGrid();
	}  
	else
	{
		document.getElementById("scanScoreReportBean").style["display"] = "none";   
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
	
		  
}

function initializeGrid(){
   totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0)
	{
		document.getElementById("scanScoreReportBean").style["display"] = "";
		beangrid = new dhtmlXGridObject('scanScoreReportBean');
		initGridWithConfig(beangrid,config,false,true,true);
		if( typeof( jsonMainData ) != 'undefined' ) {
				beangrid.parse(jsonMainData,"json");
				
	   // beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
	   // beangrid.attachEvent("onRightClick",selectRightclick);
	   // beangrid.attachEvent("onRowSelect",doOnRowSelected);			
        }
	}  
	else
	{
		document.getElementById("scanScoreReportBean").style["display"] = "none";   
	}
}
