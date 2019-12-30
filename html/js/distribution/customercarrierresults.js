var beangrid;

var resizeGridWithWindow = true;

function resultOnLoad()
{	
	
 /*try
 {
 if (!showUpdateLinks) Dont show any update links if the user does not have permissions
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}*/
	
 totalLines = document.getElementById("totalLines").value;
 
 
 if (totalLines > 0)
 {
  document.getElementById("carrierInfoViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("carrierInfoViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	 beangrid = new dhtmlXGridObject('carrierInfoViewBean');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 beangrid.attachEvent("onRowSelect",selectRow);
	 beangrid.attachEvent("onRightClick",selectRightclick);
	}

function validateForm()
{
	return true;
}


function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);	
	  
}


function selectRow(rowId,cellInd){
	
if (parent.valueElementId.length>0 && parent.displayElementId.length>0 )
  { 
  var carrirName = cellValue (rowId,"carrierName");	
  var carrierCode = cellValue (rowId,"carrierCode");
  var account = cellValue (rowId,"account");
  var selectedCarrier = parent.document.getElementById("selectedRow");	  
  selectedCarrier.innerHTML = '<a href="#" onclick="selectedRow(); return false;">'+messagesData.selectedRowMsg+' : '+carrirName+'</a>';
  //set variable to main
  parent.returnSelectedValue = carrirName;
  parent.returnSelectedId    = carrierCode;
  parent.carrierAcctId    = account;
  }	   

}
