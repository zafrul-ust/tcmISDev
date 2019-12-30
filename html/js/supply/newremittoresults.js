var beangrid;

var resizeGridWithWindow = true;

function resultOnLoad()
{	
 try
 {
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}
	
 totalLines = document.getElementById("totalLines").value;
 
 
 if (totalLines > 0)
 {
  document.getElementById("supplierBillingLocViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("supplierBillingLocViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	 beangrid = new dhtmlXGridObject('supplierBillingLocViewBean');

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

function updateSupplierInfo()
{
	 /*Set any variables you want to send to the server*/	
	var flag = validateForm();
	if(flag) {
	var action = document.getElementById("action");		
	action.value = 'update';
	parent.showPleaseWait();
	beangrid.parentFormOnSubmit(); //prepare grid for data sending	 
    document.genericForm.submit(); 
	}
}

function changeStatusRejected()
{
	var action = document.getElementById("action");		
	action.value = 'reject';
	parent.showPleaseWait();
	beangrid.parentFormOnSubmit(); //prepare grid for data sending	 
    document.genericForm.submit(); 
}

var sapCode;
var finalCode;
var lookupClick = false;
var elementCounter;
var statusColIndex = 6;
var sapdCodeColIndex = 5;
var rId;
var cId;

function getVendorCode(elementName, i)
{
   loc = "vendorcodesearchmain.do"
   openWinGeneric(loc,"_vendorcodesearch","800","600","yes","50","50","no")   
   sapCode=elementName;
   elementCounter=i;
}

function selectRow(rowId,cellInd) {
	beangrid.selectRowById(rowId,null,false,false);	 
	var errorMessage = messagesData.validvalues+"\n\n";	
	rId = rowId;
	cId = cellInd;
	var columnId = beangrid.getColumnId(cellInd);
	var okValue=  cellValue (rowId,"ok");	
	var statusValue = cellValue (rowId,"statusCol");
	var tempStr = "sapvendorcode"+rId;

	var sapCodeValue = $(tempStr);
 	switch (columnId)
 	{
 	// check the revised promise date field value.
 	case "ok": 
 		if( okValue == false ) 
		return;
	else
	{		
		if ((sapCodeValue.value=='') && (statusValue == false) )
		{    alert(errorMessage + messagesData.sapvendorcode);
		     beangrid.cellById(rowId,cellInd).setValue(false);
		}		
		
	} 		
 	break;
 	case "statusCol":
 		if( statusValue == false ) 
		return;
 		else
 		{	
 		var str = "<input class='inputBox' type='text' id='sapvendorcode"+rId +  "' value='' size='10'  readonly />&nbsp;&nbsp;<input type='button' class='lookupBtn'  onmouseover=\"this.className='lookupBtn lookupBtnOver'\"   onmouseout=\"this.className='lookupBtn'\" name=\"sapvendorSelector"+rId+"\" value=\"...\" align=\"right\" onClick=\"getVendorCode($('sapvendorcode"+rId+"'),"+rId+");\"/>";
 		 //beangrid.cellById(rId,sapdCodeColIndex).setValue(str);
         cell(rId,"tempSapVendorCode").setValue(str);
         beangrid.cellById(rId,beangrid.getColIndexById("sapVendorCode")).setValue("");
 		
 		}
 		break;
 	
 	default:   
 	};	
 	document.getElementById("rowIndex").value = rowId*1 - 1;
}

function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);
    rId = rowId;
	cId = cellInd;
    var permission = cellValue (rowId,"permission");
     if (permission == "Y")
     {
        toggleContextMenu('reject');
     }
     else
     {
         toggleContextMenu('contextMenu');
     }
    document.getElementById("rowIndex").value = rowId*1 - 1;
}

function vendorCodeChanged(code,name) {				
	var str = "<input class='inputBox' type='text' id='sapvendorcode"+elementCounter +  "' value='"+code+"' size='10' readonly/>&nbsp;&nbsp;<input type='button' class='lookupBtn'  onmouseover=\"this.className='lookupBtn lookupBtnOver'\"   onmouseout=\"this.className='lookupBtn'\" name=\"sapvendorSelector"+elementCounter+"\" value=\"...\" align=\"right\" onClick=\"getVendorCode($('sapvendorcode"+elementCounter+"'),"+elementCounter+");\"/>";	
	cell(rId,"tempSapVendorCode").setValue(str);
	cell(rId,"sapVendorCode").setValue(code);	
	cell(rId,"ok").setValue(false);	
}
