windowCloseOnEsc = true;
var beangrid;

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
  document.getElementById("poViewBean").style["display"] = "";
  initializeGrid();  
 }  
 else
 {
   document.getElementById("poViewBean").style["display"] = "none";   
 }

/*This dislpays our standard footer message*/
displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}


function initializeGrid(){
	 beangrid = new dhtmlXGridObject('poViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 
	 beangrid.attachEvent("onBeforeSorting",sortValues);
	 beangrid.attachEvent("onRightClick",selectRightclick);
	 //beangrid.attachEvent("onRowSelect",selectRow);
	}


function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);	
	var  contextMenuName ="purchaseorder";	
	toggleContextMenu(""+contextMenuName+"");
}

//Function to sort date  and other fields on the grid.
function sortValues(ind,type,direction)
{
	
	var columnId = beangrid.getColumnId(ind);
	var colIndex;
	switch (columnId)
 	{
 	case "dateConfirmedDiplay":
 		colIndex=beangrid.getColIndexById("dateConfirmed");
 		break;
 	case "originalDateConfirmedDiplay":
 		colIndex=beangrid.getColIndexById("originalDateConfirmed"); 
 		break;
 	case "firstTimeReceivedDiplay":
 		colIndex=beangrid.getColIndexById("firstTimeReceived"); 
 		break;
 	case "unitPriceDisplay":
 		colIndex=beangrid.getColIndexById("unitPrice"); 
 		break;		
 	default:
 		return true;   // Do not block normal sorting
 	};  
	beangrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	beangrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting	
	
	
}





function submitSearchForm()
{
 	document.genericForm.target='resultFrame';
 	var userAction = document.getElementById("userAction");
 	userAction.value = 'search';
 	//set start search time
 	var now = new Date();
 	var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
 	startSearchTime.value = now.getTime();
 	var flag = validateForm();
  	if (flag) 
  	{
    	parent.showPleaseWait();
    	return true;
  	}
  	else
  	{
    	return false;
  	}
}

function validateForm() 
{
   return true;
} //end of validateForm



function selectRow(rowid)
{
    //highlight selected row if the manufacturer is active
    //var status = document.getElementById("status"+rowid+"");
    //alert (" rowid = [" + rowid + "]; ");
   	if (true) // (status.value != "I")
   	{
    	var selectedRow = document.getElementById("rowId"+rowid+"");
    	var selectedRowClass = document.getElementById("colorClass"+rowid+"");
    	selectedRow.className = "selected"+selectedRowClass.value+"";

    	//update selected user display
    	var viewPurchaseOrderLink = parent.document.getElementById("viewPurchaseOrderLink");
    	var currentRadianPo = document.getElementById("radianPo"+rowid+"");
    	
    	var selectedRadianPo = document.getElementById("selectedRadianPo");
    	selectedRadianPo.value = currentRadianPo.value;
    	//alert (" selectedItemId = [" + selectedItemId.value + "]; ");
    	
  		var requestId = document.getElementById("requestId");
    	
    	viewPurchaseOrderLink.innerHTML = '<a href="#" onclick="onclickViewPurchaseOrderLink(); return false;">' 
    						     + messagesData.viewPurchaseOrder + ': ' + currentRadianPo.value +  '</a>';
    	//set variable to main
    	parent.returnSelectedRadianPo = document.getElementById("radianPo"+rowid+"").value;
    	//parent.displayElementId = document.getElementById("displayElementId").value;
    	//parent.valueElementId = document.getElementById("valueElementId").value;

    	//reset previous selected row back to it original color
    	if (selectedrow != null && rowid != selectedrow)
    	{
       		var previouslySelectedRow = document.getElementById("rowId"+selectedrow+"");
       		var previouslySelectedRowClass = document.getElementById("colorClass"+selectedrow+"");
       		previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
    	}
    	selectedrow =rowid;
   	}
}

function sendRadianPo()
{
  	var displayElementId = document.getElementById("displayElementId");
  	var valueElementId = document.getElementById("valueElementId");

  	var openervalueElementId = opener.document.getElementById("" + valueElementId.value + "");
  	var requestorID = document.getElementById("requestorID");
  	if (requestorID.value.trim().length > 0 )
  	{
    	openervalueElementId.value = requestorID.value;

    	var openerdisplayElementId = opener.document.getElementById(""+displayElementId.value+"");
    	var requestorName = document.getElementById("requestorName");
    	openerdisplayElementId.value = requestorName.value;
    	window.close();
  	}
}

function showPurchOrder()
{
   var poNumber = cellValue(beangrid.getSelectedRowId(),"radianPo");
   //var loc = "/tcmIS/supply/purchorder?po="+HaasPO+"&Action=searchlike&subUserAction=po";
   var loc = "/tcmIS/supply/purchaseorder.do?po=" + poNumber + "&Action=searchlike&subUserAction=po";

   //openWinGeneric(loc,"showradianPo","800","600","yes","50","50");

  try
  {
    parent.parent.openIFrame("purchaseOrder"+poNumber+"",loc,""+messagesData.purchaseorder+" "+poNumber+"","","N");
  }
  catch (ex)
  {
    openWinGeneric(loc,"showradianPo"+poNumber+"","800","600","yes","50","50");
  }
}

