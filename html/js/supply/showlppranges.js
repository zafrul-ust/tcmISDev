windowCloseOnEsc = true;
var beangrid;

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 
	if (totalLines > 0) {
		document.getElementById("lppRangeBean").style["width"] = "85%";
        document.getElementById("lppRangeBean").style["height"] = "250px";
		document.getElementById("lppRangeBean").style["display"] = "";
		initializeGrid();  
	} else {
		document.getElementById("lppRangeBean").style["display"] = "none";   
	}
	/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();
	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
}


function initializeGrid() 
{	 
	 beangrid = new dhtmlXGridObject('lppRangeBean');	 
	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
		 beangrid.parse(jsonMainData,"json");
	 }
}
