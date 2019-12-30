var beangrid;
var resizeGridWithWindow = true;
//var rId ;
//var cId;
function resultOnLoad()
{
	/*  try{	 
		
		 if (!showUpdateLinks) //Dont show any update links if the user does not have permissions
		 {
		  document.getElementById("updateResultLink").style["display"] = "none";
		 }
		 else
		 {
		  document.getElementById("updateResultLink").style["display"] = "";
		  
		 }
		 }
		 catch(ex)
		 {}
		 */
	
	 totalLines = document.getElementById("totalLines").value;
	 
	 if (totalLines > 0)
	 {
	  document.getElementById("freightAdviceViewBean").style["display"] = "";
	  
	  initializeGrid();  
	 }  
	 else
	 {
	   document.getElementById("freightAdviceViewBean").style["display"] = "none";   
	 }

	 displayGridSearchDuration();
	 
	 /*It is important to call this after all the divs have been turned on or off.*/
	 setResultFrameSize();
}


function initializeGrid(){
	 beangrid = new dhtmlXGridObject('freightAdviceViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 beangrid.attachEvent("onRowSelect",selectRow);
	 //beangrid.attachEvent("onRightClick",selectRightclick);
	}


function selectRow(rowId,cellInd) {
	beangrid.selectRowById(rowId,null,false,false);	 
    var selRowShipConfirmDate = cellValue(beangrid.getSelectedRowId()   ,"shipConfirmDate");
    var statusVal = cellValue(beangrid.getSelectedRowId()   ,"status");
	if ( (selRowShipConfirmDate.length>0) || (!showUpdateLinks)  || (statusVal =='Cancelled') )
	{
		toggleContextMenu('contextMenu');
	}	
	else
	{    	
	    toggleContextMenu('freightAdvice');	
	}
	 
}


function freightAdvice()
{
  var selPackingGroupId = cellValue(beangrid.getSelectedRowId()   ,"packingGroupId");	
  var action = document.getElementById("action");
  action.value = 'changefreightadvice';
 
 
  if (selPackingGroupId.length > 0)
  {
	var packingGroupId = document.getElementById("packingGroupId");
	packingGroupId.value = selPackingGroupId;	
   openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_ChangeFreightAdvice','400','220','yes',"50","50","no");
   document.genericForm.target='_ChangeFreightAdvice';
    var a = window.setTimeout("document.genericForm.submit();",500);   
  }
}
