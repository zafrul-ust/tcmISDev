var beangrid;

var resizeGridWithWindow = true;

function resultOnLoad()
{
/*try{
	
 if (!showUpdateLinks) Dont show any update links if the user does not have permissions
 {
  document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  document.getElementById("updateResultLink").style["display"] = "";
  
 }
 }
 catch(ex)
 {}*/  

 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("openPickList").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("openPickList").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	 beangrid = new dhtmlXGridObject('openPickList');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 	beangrid.parse(jsonMainData,"json");
	 }
	 
	 beangrid.attachEvent("onBeforeSorting",sortValues);
}

function sortValues(ind,type,direction)
{
	
	var columnId = beangrid.getColumnId(ind);
	var colIndex;
	switch (columnId)
 	{
 		case "unitPrice":
 			colIndex=beangrid.getColIndexById("hunitPrice");
 			break; 
 			
 		case "value":
 			colIndex=beangrid.getColIndexById("hvalue");
 			break; 	
 		
 		case "picklistPrintDate":
 			colIndex=beangrid.getColIndexById("hpicklistPrintDate");
 			break; 	
 		
 		default:
 			return true;   // Do not block normal sorting
 	};  
	beangrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	beangrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting	
		
}

