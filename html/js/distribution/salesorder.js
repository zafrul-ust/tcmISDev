var beangrid;

var resizeGridWithWindow = true;
var selectedRowId = null;

function resultOnLoad()
{
try{

 var creditHoldCount = $("creditHoldCount").value;

if (!showUpdateLinks || (creditHoldCount*1 == 0)) //Dont show any update links if the user does not have permissions
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else if (creditHoldCount*1 > 0)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 
 }
 catch(ex)
 {}

 try {
 	parent.document.getElementById("selectedRow").innerHTML = "";	
 } catch(ex){}
 
 totalLines = document.getElementById("totalLines").value;
 
 if (totalLines > 0)
 {
  document.getElementById("salesOrderViewBean").style["display"] = "";
  
  initializeGrid();  
 }  
 else
 {
   document.getElementById("salesOrderViewBean").style["display"] = "none";   
 }

 displayGridSearchDuration();
 
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();
}

function initializeGrid(){
	beangrid = new dhtmlXGridObject('salesOrderViewBean');

	initGridWithConfig(beangrid,config,false,false);
	if( typeof( jsonMainData ) != 'undefined' ) {
		
	  beangrid.parse(jsonMainData,"json");
	}
//	beangrid.attachEvent("onBeforeSorting",sortValues);
	beangrid.attachEvent("onRightClick",selectRightclick);
	
	beangrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
    beangrid.attachEvent("onRowSelect",doOnRowSelected);
	
}

//Function to sort date  and other fields on the grid.
//Save the old selected color here
var saveRowClass = null;
function doOnBeforeSelect(rowId,oldRowId) {
	if (oldRowId != 0) {
		setRowClass(oldRowId, saveRowClass);
	}
	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);
	return true;
}
/*
function sortValues(ind,type,direction)
{	
	var columnId = beangrid.getColumnId(ind);
	var colIndex;
	switch (columnId)
 	{
 	case "needDate":
 		colIndex=beangrid.getColIndexById("hiddenNeedDate");
 		break; 	
 	case "promisedShipDate":
 		colIndex=beangrid.getColIndexById("hiddenPromisedShipDate");
 		break; 	
 	case "orderDate":
 		colIndex=beangrid.getColIndexById("hiddenOrderDate");
 		break; 			
 	default:
 		return true;   // Do not block normal sorting
 	};  
	beangrid.sortRows(colIndex,type,direction);         //sort grid by the column with prepared values
	beangrid.setSortImgState(true,ind,direction);    //set a correct sorting image
	return false; //block default sorting		
}  */

function doOnRowSelected(rowId,cellInd) {
	ee     = arguments[2];
  
    rightClick = false; 
    popdown();
	externalevent = null;
    if( ee != null ) {
    		if( ee.button != null && ee.button == 2 ) rightClick = true;
    		else if( ee.which == 3  ) rightClick = true;
    		if( rightClick) externalevent = ee;
    }
    
	var selectedRowId = rowId; 
	try {
		var prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
		var selectedMR = parent.document.getElementById("selectedRow");
	
		selectedMR.innerHTML = "| <a href=\"#\" onclick=call('openMR'); return false;>"+messagesData.open+' MR '+prNumber+"</a>";
	} catch(ex) {}
	
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');
}

function selectRightclick(rowId,cellInd,ee){
	beangrid.selectRowById(rowId,null,false,false);
	doOnRowSelected(rowId,cellInd,ee);
	
/*	saveRowClass = getRowClass(rowId);
	if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		overrideDefaultSelect(rowId,saveRowClass);

    if (saveRowClass.search(/haas/) == -1 && saveRowClass.search(/Selected/) == -1)
		setRowClass(rowId,''+saveRowClass+'Selected');  */
//	beangrid.selectRowById(rowId,null,false,false);
	toggleContextMenu('salesOrderRightClick');
}

function openMR() {
	var prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
    var tabId = 'scratchPad'+prNumber+'';
    var loc = "/tcmIS/distribution/scratchpadmain.do?uAction=searchScratchPadId&scratchPadId="+prNumber+"&tabId="+encodeURIComponent(tabId);
    
	try
	{
        parent.parent.openIFrame(tabId,loc,'MR '+prNumber+'','','N');
    }
	catch (ex)
	{
		parent.children[parent.children.length] = openWinGeneric(loc,"scratchPad"+prNumber,"900","600","yes","80","80","yes");
	}
}


function validateForm()
{
	return true;
}

function showMRDocuments()
{ 
	var prNumber = cellValue(beangrid.getSelectedRowId(),"prNumber");
	var companyId = cellValue(beangrid.getSelectedRowId(),"companyId");
	var inventoryGroup = cellValue(beangrid.getSelectedRowId(),"inventoryGroup");
    var opsEntityId  = cellValue(beangrid.getSelectedRowId(),"opsEntityId");
 var loc = "/tcmIS/distribution/showmrdocuments.do?showDocuments=Yes&orderType=MR"+
           "&opsEntityId="+opsEntityId+"&prNumber="+prNumber+"&inventoryGroup="+inventoryGroup+"&companyId="+companyId+"";
 try {
 	parent.children[parent.children.length] = openWinGeneric(loc,"showAllMrDocuments","600","300","yes","80","80");
 } catch (ex){
 	openWinGeneric(loc,"showAllMrDocuments","450","300","no","80","80");
 }
}

function updateSalesOrder()
{
	 /*Set any variables you want to send to the server*/	
	var flag = validateForm();
	if(flag) {
	$('uAction').value = 'update';	
	parent.showPleaseWait();
	beangrid.parentFormOnSubmit(); //prepare grid for data sending	 
    document.salesordersearch.submit(); 
	}
}

function showMrLineAllocationReport()
{
	var companyId  = cellValue(beangrid.getSelectedRowId(),"companyId");   
	var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");    	
	var lineItem  =  cellValue(beangrid.getSelectedRowId(),"lineItem"); 
	if ((mrNumber!= null && lineItem != null && mrNumber!= 0) && (companyId.length>0) )
	{
		var loc = "mrallocationreportmain.do?fromCustomerOrdertracking=Y&companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem="+lineItem+"";
		
		parent.children[parent.children.length] = openWinGeneric(loc,"showMrAllocationReport","800","550","yes","80","80","no");
	}
}

function showMrAllocationReport()
{
	var companyId  = cellValue(beangrid.getSelectedRowId(),"companyId");    	
	var mrNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");    	
	var lineItem  =  cellValue(beangrid.getSelectedRowId(),"lineItem"); 

	if ( mrNumber != null &&  mrNumber != 0)
	{
		var loc = "mrallocationreportmain.do?fromCustomerOrdertracking=Y&companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem=";
		parent.children[parent.children.length] = openWinGeneric(loc,"showMrAllocationReport22","800","550","yes","80","80","no")
	} 
}

function showCustomerReturnRequest()
{
	var prNumber  =  cellValue(beangrid.getSelectedRowId(),"prNumber");    	
	var lineItem  =  cellValue(beangrid.getSelectedRowId(),"lineItem");
//	var unitPriceCurrenty = cellValue(beangrid.getSelectedRowId(),"unitPriceCurrenty");
//	var unitPrice = cellValue(beangrid.getSelectedRowId(),"unitPrice");
//	var quantity  = cellValue(beangrid.getSelectedRowId(),"quantity");
	var companyId  = cellValue(beangrid.getSelectedRowId(),"companyId");
	var opsEntityId  = cellValue(beangrid.getSelectedRowId(),"opsEntityId");
	if ((prNumber!= null && lineItem != null && prNumber!= 0)  )
	{
		var loc = "/tcmIS/distribution/customerreturnrequest.do?prNumber="+prNumber+"&lineItem="+lineItem+"&action=insert&companyId="+companyId+"&opsEntityId="+opsEntityId;
		try
		{
			parent.parent.openIFrame("showcustomerreturnrequest",loc,""+messagesData.customerreturnrequest+"","","N");
		}
		catch (ex)
		{
			parent.children[parent.children.length] = openWinGeneric(loc,"showcustomerreturnrequest","900","600","yes","80","80","yes");
		}	
		
		
	}

}
