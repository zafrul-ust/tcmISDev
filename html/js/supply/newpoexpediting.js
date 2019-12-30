var beangrid;
/*Set this to be false if you don't want the grid width to resize based on window size.*/
var resizeGridWithWindow = true;

function resultOnLoad()
{
 try{

 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
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

 totalLines = document.getElementById("totalLines").value; 
 if (totalLines > 0)
 {
  document.getElementById("newPoExpediteBean").style["display"] = "";
  initializeGrid();
 }  
 else 
 {
   document.getElementById("newPoExpediteBean").style["display"] = "none";   
 }
/*This dislpays our standard footer message*/
displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.*/
 setResultSize();
}




function initializeGrid(){
	 beangrid = new dhtmlXGridObject('newPoExpediteBean');	 
	 initGridWithConfig(beangrid,config,false,false,true)
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 beangrid.attachEvent("onRowSelect",selectRow);
	 beangrid.attachEvent("onRightClick",selectRightclick);
	}

function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);	
	var  contextMenuName ="history";	
	toggleContextMenu(""+contextMenuName+"");
}

function selectRow(rowId,cellInd) {	
	var errorMessage = messagesData.validvalues+"\n\n";	
	var okValue=  cellValue (rowId,"ok");	
	var columnId = beangrid.getColumnId(cellInd);
	var revisedPromisedDate;	
	var commentval;
 	switch (columnId)
 	{
 	// check the revised promise date field value.
 	case "ok": 
 		if( okValue == false ) 
			return;
	else
	{
		revisedPromisedDate =  cellValue(rowId,"revisedPromisedDate");		
		commentval = cellValue(rowId,"comments");
	  if (revisedPromisedDate.trim() == "")
	  {    alert(errorMessage + messagesData.revisedPromisedDate );
	       beangrid.cellById(rowId,cellInd).setValue('false');
	  }
	  
	  if(commentval.length >2000)
	  {
		  alert( messagesData.maximum2000);
		  beangrid.cellById(rowId,cellInd).setValue('false');
	  }	  
	 
	}  
 		
 		break;
 	
 	default:   
 	};  	  
}

function showHistory(){
 
   var HaasPO = cellValue(beangrid.getSelectedRowId(),"radianPo");	
   var POLine = cellValue(beangrid.getSelectedRowId(), "poLine");	  
   var now = new Date();   
   loc = "polineexpeditehistory.do?radianPo="+ HaasPO + "&poLine=" + POLine + "&action=search";
   openWinGeneric(loc,"POLineHistoryPage"+HaasPO+"","800","450","yes","50","50","no")  ;
}

function showItemHistory(){	
   
   var HaasPO = cellValue(beangrid.getSelectedRowId(),"radianPo");	 
   var POLine = cellValue(beangrid.getSelectedRowId(), "poLine");	   	
   var itemId = cellValue(beangrid.getSelectedRowId(), "itemId");  
   var now = new Date();   
   loc = "polineexpeditehistory.do?itemId="+ itemId + "&action=search";
   openWinGeneric(loc,"POLineItemHistoryPage"+HaasPO+"","800","450","yes","50","50","no") ;
}


function showPurchOrder()
{
   var HaasPO = cellValue(beangrid.getSelectedRowId(),"radianPo");
   var loc = "/tcmIS/supply/purchaseorder.do?po="+HaasPO+"&Action=searchlike&subUserAction=po";
   //openWinGeneric(loc,"showradianPo","800","600","yes","50","50");

  try
  {
    parent.parent.openIFrame("purchaseOrder"+HaasPO+"",loc,""+messagesData.purchaseorder+" "+HaasPO+"","","N");
  }
  catch (ex)
  {
    openWinGeneric(loc,"showradianPo"+HaasPO+"","800","600","yes","50","50");
  }
}





//Function for updating records.
var editing = true;
function update(){
	try {
	    if( editing ) {
		    editing = false;
			setTimeout("update()",200);
			return false;
	    }
	}catch(ex){}

	editing = true;
	
	var selectedRowsCounter = 0;
	for(var i=1;i<=beangrid.getRowsNum();i++)
	{   
	  try
      {
		if ((cellValue(i,"ok")=="true") || ((cellValue(i,"ok")==true)))
		{	
			selectedRowsCounter++;
		
		}
      }
        catch(ex){}
	}
	
	if(selectedRowsCounter==0)
	{
		alert(messagesData.norow);
		return false;
	}	
	
	else
	{
	/*Set any variables you want to send to the server*/
	document.genericForm.target='';
	document.getElementById('action').value = 'updatenewpoexpedite';
	showPleaseWait();
    beangrid.parentFormOnSubmit(); //prepare grid for data sending	 
    document.genericForm.submit();
    
	}
}