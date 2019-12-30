windowCloseOnEsc = true;
var beangrid;

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 
	if (totalLines > 0) {
		document.getElementById("openScheduleBean").style["width"] = "85%";
        document.getElementById("openScheduleBean").style["height"] = "250px";
		document.getElementById("openScheduleBean").style["display"] = "";
		initializeGrid();  
	} else {
		document.getElementById("openScheduleBean").style["display"] = "none";   
	}
	/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();
	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
}


function initializeGrid() 
{	 
	 beangrid = new dhtmlXGridObject('openScheduleBean');	 
	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
		 beangrid.parse(jsonMainData,"json");
	 }	 
	 beangrid.attachEvent("onBeforeSorting",sortValues);	 
	 
}

//Function to sort date  and other fields on the grid.
function sortValues(ind,type,direction)
{	
	var columnId = beangrid.getColumnId(ind);
	var colIndex;
	switch (columnId)
 	{
	 	case "dateToDeliverDiplay":
	 		colIndex = beangrid.getColIndexById("dateToDeliver");
	 		break;
	 	case "qtyDiplay":
	 		colIndex = beangrid.getColIndexById("qty"); 
	 		break; 	
	 	default:
	 		return true;   // Do not block normal sorting
 	};  
	beangrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	beangrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting
}
