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
  document.getElementById("lessThanNWksInvBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("lessThanNWksInvBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	 beangrid = new dhtmlXGridObject('lessThanNWksInvBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 beangrid.attachEvent("onBeforeSorting",sortValues);
	 //beangrid.attachEvent("onRowSelect",selectRow);
	 beangrid.attachEvent("onRightClick",selectRightclick);
}

function selectRightclick(rowId, cellInd) {
	beangrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('showInventoryDetail');
}

function viewInventoryDetail() {
	var hub = beangrid.cellById(beangrid.getSelectedRowId(),
			beangrid.getColIndexById("hubId")).getValue();

	var itemId = beangrid.cellById(beangrid.getSelectedRowId(),
			beangrid.getColIndexById("itemId")).getValue();

	if ((hub != '') && (itemId != '')) {
		loc = "inventorydetails.do?itemId=" + itemId + "&hub=" + hub;
		openWinGeneric(loc, "inventorydetailspage", "800", "450", "yes", "50","50", "no");
	}
}


//Function to sort date  and other fields on the grid.
function sortValues(ind,type,direction)
{
	
	var columnId = beangrid.getColumnId(ind);
	var colIndex;
	switch (columnId)
 	{
 	case "averageCostDispaly":
 		colIndex=beangrid.getColIndexById("averageCost");
 		break; 	
 	default:
 		return true;   // Do not block normal sorting
 	};  
	beangrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	beangrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting	
	
}