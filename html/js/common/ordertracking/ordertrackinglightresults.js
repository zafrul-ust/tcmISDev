var beangrid;
var homeCompanyLogin;
var resizeGridWithWindow = true;

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value;

	if (totalLines > 0)
	{
		document.getElementById("prOrderTrackBean").style["display"] = "";

		initializeGrid();  
	}  
	else
	{
		document.getElementById("prOrderTrackBean").style["display"] = "none";   
	}

	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('prOrderTrackBean');

	initGridWithConfig(beangrid,config,false,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beangrid.parse(jsonMainData,"json");

	}

    beangrid.attachEvent("onRightClick",onRightclick);
}


function onRightclick(rowId,cellInd){
    var orderType = cellValue(beangrid.getSelectedRowId(),"orderType");
	var lineStatus = cellValue(beangrid.getSelectedRowId(),"requestLineStatus");		
	var shipped =  cellValue(beangrid.getSelectedRowId(),"totalShipped");	
	var showAllocation = false;
	var showSchedule = false;
	var partiallyDelivered = false;
	var allowTruncateMR = cellValue(beangrid.getSelectedRowId(),"allowTruncateMR");	
		
	if (lineStatus == "In Progress" || lineStatus == "Partial Del." || lineStatus == "Open" || lineStatus == "Pending Cancellation" ||
		 lineStatus == "Delivered") {
		showAllocation = true;
	}
	
	if (orderType == "SCH"){
		showSchedule = true;
	}
	
	if (shipped != null && shipped > 0 && lineStatus == "Partial Del."){
		partiallyDelivered = true;
	}

	var aitems = new Array();
	if (orderType == 'EVAL') {
		if (showAllocation) {
			aitems[aitems.length] = "text="+messagesData.mrlineallocation+";url=javascript:showMrLineAllocationReport();";
		}
		if (lineStatus == 'Draft Eval') {
			aitems[aitems.length] = "text="+messagesData.vieweval+";url=javascript:showEvalDetail();";
		}else {
			aitems[aitems.length] = "text="+messagesData.approvaldetail+";url=javascript:showEvalApprovalDetail();";
			aitems[aitems.length] = "text="+messagesData.vieweval+";url=javascript:showEvalDetail();";
		}
	}else {
		//mr allocation
		if (showAllocation) {
			aitems[aitems.length] = "text="+messagesData.mrallocation+";url=javascript:showMrAllocationReport();";
			aitems[aitems.length] = "text="+messagesData.mrlineallocation+";url=javascript:showMrLineAllocationReport();";
		}
		//schedule
		if (showSchedule) {
			aitems[aitems.length] = "text="+messagesData.mrlineschedule+";url=javascript:showDeliverySchedule();";
		}
		//view MR
		if ($("intcmIsApplication").value == 'Y') {
			aitems[aitems.length] = "text="+messagesData.viewmr+";url=javascript:viewMR();";
		}

		//approval detail
		if (lineStatus != "Draft") {
			aitems[aitems.length] = "text="+messagesData.approvaldetail+";url=javascript:showMrApprovalDetail();";
		}
		
		if(allowTruncateMR && !showSchedule && partiallyDelivered) {
			aitems[aitems.length] = "text="+messagesData.truncatemr+";url=javascript:truncateMR();";
		}
	} //end of if MR
	replaceMenu('orderTrackingMenu',aitems);
	toggleContextMenu('orderTrackingMenu');

} //end of method

// all same level, at least one item
function replaceMenu(menuname,menus){
	var i = mm_returnMenuItemCount(menuname);
	for(;i> 1; i-- )
		mm_deleteItem(menuname,i);

	for( i = 0; i < menus.length; ){
 		var str = menus[i];
 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
   }
}

function doNothing() {
}

function showMrLineAllocationReport()
{
	var companyId  = cellValue(beangrid.getSelectedRowId(),"companyId");    	
	var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");    	
	var lineItem  =  cellValue(beangrid.getSelectedRowId(),"lineItem");
	if (mrNumber!= null && lineItem != null && mrNumber!= 0 )
	{
		var loc = "mrallocationreportmain.do?companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem="+lineItem+"";
		openWinGeneric(loc,"showMrAllocationReport22","800","550","yes","80","80","no")
	}

}

function showMrAllocationReport()
{
	var companyId  = cellValue(beangrid.getSelectedRowId(),"companyId");    	
	var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");    	
	var lineItem  =  cellValue(beangrid.getSelectedRowId(),"lineItem"); 

	if ( mrNumber != null &&  mrNumber != 0)
	{
		var loc = "mrallocationreportmain.do?companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem=";
		openWinGeneric(loc,"showMrAllocationReport22","800","550","yes","80","80","no")
	} 
}

function showDeliverySchedule()
{
	var orderType  = cellValue(beangrid.getSelectedRowId(),"orderType");	  
	if (orderType == "SCH")
	{
		var companyId =  cellValue(beangrid.getSelectedRowId(),"companyId");    	
		var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");    	
		var lineItem  =  cellValue(beangrid.getSelectedRowId(),"lineItem"); 
		var loc = "deliveryschedulemain.do?companyId="+companyId+"&prNumber="+mrNumber+"&lineItem="+lineItem+"";
		openWinGeneric(loc,"showDeliverySchedule22","850","550","yes","100","100","no")
	}
} 
 
function viewMR()
{  	
 var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");

 if ( mrNumber != null &&  mrNumber != 0)
 {
  var loc = "materialrequest.do?action=int&prNumber="+mrNumber;
  try
  {
    parent.parent.openIFrame("materialrequest"+mrNumber,loc,""+messagesData.materialrequest+" "+mrNumber,"","N");
  }
  catch (ex)
  {
    openWinGeneric(loc,"materialrequest"+mrNumber,"800","600","yes","50","50");
  }
 }
}

function showEvalApprovalDetail()
{
	var orderType  = cellValue(beangrid.getSelectedRowId(),"orderType");
	if (orderType == "EVAL")
	{
  		var requestId = cellValue(beangrid.getSelectedRowId(),"requestId");
  		if(requestId.value!='')  {
	 		parent.showEvalApprovalDetail(requestId);
		}
	}
}

function showEvalDetail() {
	var orderType  = cellValue(beangrid.getSelectedRowId(),"orderType");
	if (orderType == "EVAL")
	{
		var requestId  =  cellValue(beangrid.getSelectedRowId(),"requestId");
		if ( requestId != null &&  requestId != 0) {
		  var loc = "engeval.do?uAction=view&requestId="+requestId;
		  try {
			 parent.parent.openIFrame("engeval",loc,""+messagesData.engineeringevaluation+"","","N");
		  }catch (ex) {
			 openWinGeneric(loc,"engeval"+"","800","600","yes","50","50");
		  }
		}
	}
}

function showMrApprovalDetail()
{
	var orderType  = cellValue(beangrid.getSelectedRowId(),"orderType");
	if (orderType != "EVAL")
	{
  		var prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
		if(prNumber.value!='')  {
	 		parent.mrApprovalDetail(cellValue(beangrid.getSelectedRowId(),"companyId"),prNumber,cellValue(beangrid.getSelectedRowId(),"lineItem"));
  		}
	}
}

function truncateMR() {
	document.genericForm.target='';
	document.getElementById('action').value = 'truncateMR';
	setCellValue(beangrid.getSelectedRowId(), "selected", true);

    var now = new Date();
    parent.document.getElementById("startSearchTime").value = now.getTime();
	parent.showPleaseWait();
	
	// Call this function to send the data from grid back to the server
	if (beangrid != null) {
		beangrid.parentFormOnSubmit();
	}

	document.genericForm.submit();
}
