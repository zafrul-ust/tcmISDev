
windowCloseOnEsc = true;


/************************************NEW***************************************************/
var mygrid;

/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;


// totalLines = document.getElementById("totalLines").value;
// 
// if (totalLines > 0)
// {
//  document.getElementById("customerAddressSearchViewBean").style["display"] = "";
//  
//  initializeGrid();  
// }  
// else
// {
//   document.getElementById("customerAddressSearchViewBean").style["display"] = "none";   
// }
//
// displayGridSearchDuration();
// 
// setResultFrameSize();
//}



//function initializeGrid(){
//	 mygrid = new dhtmlXGridObject('customerAddressSearchViewBean');
//
//	 initGridWithConfig(mygrid,config,false,true);
//	 haasGrid = mygrid;
////	 mygrid.enableSmartRendering(false);
//
//	 if( typeof( jsonMainData ) != 'undefined' ) {
//	 mygrid.parse(jsonMainData,"json");
//
//	 }
////	 mygrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
//	 mygrid.attachEvent("onRowSelect",selectRow);
//	 
//	 
//	 if (parent.valueElementId.length>0 && parent.displayElementId.length>0 )
//	 {
//		 mygrid.attachEvent("onRightClick",simplyRightClick);
//	 }
//	 else
//	 {	 
//	 mygrid.attachEvent("onRightClick",selectRightclick);
//	 }
//}





function printGrid()
{
 mygrid.printView();
}
function selectRightclick(rowId,cellInd){
	mygrid.selectRowById(rowId,null,false,false);	
	selectRow(rowId,cellInd);
	toggleContextMenu("opendetails");
}



function simplyRightClick(rowId,cellInd){
	mygrid.selectRowById(rowId,null,false,false);	
	selectRow(rowId,cellInd);	
}


//var saveRowClass = null;
//function doOnBeforeSelect(rowId,oldRowId) {	
//	if (oldRowId != 0) {
//		setRowClass(oldRowId, saveRowClass);		
//	}
//	saveRowClass = getRowClass(rowId);
//	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
//		overrideDefaultSelect(rowId,saveRowClass);
//	return true;	
//}


function selectRow(rowId,cellInd){
	
	  // to show menu directly
// if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
//  setRowClass(rowId,''+saveRowClass+'Selected');
	
	if (parent.valueElementId.length>0 && parent.displayElementId.length>0 )
	{ 
	  var cusName = cellValue (rowId,"customerName");	
	  var cusId = cellValue (rowId,"customerId");  
	  var selectedCus = parent.document.getElementById("selectedRow");	  
	  selectedCus.innerHTML = ' | <a href="#" onclick="selectedRow(); return false;">'+messagesData.selectedcustomer+' : '+cusName+'</a>';
	  //set variable to main
	  parent.returnSelectedValue = cusName;
	  parent.returnSelectedId    = cusId;
	  parent.returnSelectedABCClassification = cellValue(rowId,"abcClassification");
  	}	   

}


function showDetails() {

	var cusId = cellValue (mygrid.getSelectedId(),"customerId"); 
	var opsEntityId = cellValue (mygrid.getSelectedId(),"opsEntityId"); 
	var loc = "/tcmIS/distribution/customerupdate.do?customerId="+cusId+"&opsEntityId="+opsEntityId;	
  
  if (parent.valueElementId.length>0 && parent.displayElementId.length>0 )
  { 
  	openWinGeneric(loc,"customerDetails"+cusId,"900","600","yes","80","80","yes");	
  }
  else
  {
   try
   {
  	 parent.parent.openIFrame("customerDetails"+cusId+"",loc,""+messagesData.customerdetails+" "+cusId+"","","N");
   }	
   catch (ex)
   {
   }
  }
}


function showCreditReview() {

	var cusId = cellValue (mygrid.getSelectedId(),"customerId"); 
	var opsEntityId = cellValue (mygrid.getSelectedId(),"opsEntityId");
	var loc = "/tcmIS/distribution/creditreview.do?customerId="+cusId+"&opsEntityId="+opsEntityId;	
  
  	openWinGeneric(loc,"creditreview"+cusId,"400","420","yes","80","80","yes");	

}






