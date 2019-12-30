windowCloseOnEsc = true;

var beangrid;
var resizeGridWithWindow = true;

function resultOnLoad()
{
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("msdsBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("msdsBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('msdsBean');

	initGridWithConfig(beangrid,config,false,false);
	if( typeof( jsonMainData ) != 'undefined' ) {		
		beangrid.parse(jsonMainData,"json");
	}	
	
	beangrid.attachEvent("onRowSelect",doOnRowSelected);
}

function doOnRowSelected(rowId,cellInd) {
	var selectedMSDS = parent.document.getElementById("selectedMSDS");

	selectedMSDS.style["display"] = "";
  
	selectedMSDS.innerHTML = '<a href="#" onclick="selectedMSDS(); return false;">'+messagesData.selectedmsds+":"+cellValue(rowId,'customerMsdsNumber');

	parent.returnCustomerMsdsNumber = cellValue(rowId,'customerMsdsNumber');
	parent.returnMaterialId = cellValue(rowId,'materialId');  
	parent.returnMaterialDesc = cellValue(rowId,'materialDesc');
	parent.returnManufacturer = cellValue(rowId,'manufacturer');

}


