windowCloseOnEsc = true;
var resizeGridWithWindow = true;
var beangrid;
var returnSelectedValue = null;
var returnSelectedId=null;


function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		document.getElementById("UserGroupAccessInputBean").style["display"] = "";
			initializeGrid(); 
		 parent.document.getElementById("mainUpdateLinks").style["display"] = "";
	}		
			
	else
	{
		document.getElementById("UserGroupAccessInputBean").style["display"] = "none"; 
		parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
	}
	
		/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
		
    try{
    parent.resetTimer();
   }
   catch (ex)
   {
   }
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('UserGroupAccessInputBean');
	initGridWithConfig(beangrid,config,-1,true,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
			beangrid.parse(jsonMainData,"json");
		  }
	//beangrid.attachEvent("onRowSelect",selectRow);
}



function selectRow(rowId,cellInd){
	  var facilityid = beanGrid.cellById(beanGrid.getSelectedRowId(), beanGrid.getColIndexById("facilityId")).getValue();
	  var facilityname = beanGrid.cellById(beanGrid.getSelectedRowId(), beanGrid.getColIndexById("facilityName")).getValue();
	  var selectedUser = parent.document.getElementById("selectedRow");
	  var columnId = beanGrid.getColumnId(cellInd);
	  	  
	  selectedUser.innerHTML = '<a href="#" onclick="selectedRow(); return false;">'+messagesData.selectedRowMsg+' : '+facilityname+'</a>';
	  //set variable to main
	  parent.returnSelectedValue = facilityname;
	  parent.returnSelectedId    = facilityid;
	  
	  
}






