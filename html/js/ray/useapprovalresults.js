var beanGrid;
var selectedRowId = null;
var inputChangedCount=0;
var isItmanagedWorkArea;

var resizeGridWithWindow = true;

var multiplePermissions = true;
var permissionColumns = new Array();
permissionColumns={"ok":true,"mwLimitQuantityPeriod1":true,"mwDaysPeriod1":true,"mwLimitQuantityPeriod2":true,
					"mwDaysPeriod2":true,"mwProcessDesc":true,"mwOrderQuantity":true,"mwEstimateOrderQuantityPrd":true,
					"customerDeliverTo":true,"dockLocationId":true,"deliveryPointBarcode":true,"dockDeliveryPoint":true,
					"mwOrderQuantityRule":true};


function resultOnLoad() {
	
		
	totalLines = document.getElementById("totalLines").value;
	 if (totalLines > 0) {
        document.getElementById("useApprovalStatusViewBean").style["display"] = "";
        try {
	        initializeGrid();
        }
        catch(ex) {}
         
    }else {
        document.getElementById("useApprovalStatusViewBean").style["display"] = "none";
    }
	 if (!showUpdateLinks)
	 {
	  parent.document.getElementById("updateResultLink").style["display"] = "none";
	 }
	 else
	 {
	      parent.document.getElementById("updateResultLink").style["display"] = "";
		  var managedWorkArea = document.getElementById("managedWorkArea");
		  var managedWorkAreaLink = parent.document.getElementById("managedWorkAreaLink");
		  if (managedWorkArea.value == "YES")
		  {
		    managedWorkAreaLink.innerHTML=messagesData.turnoverrideoff;
		    parent.document.getElementById("manageLink").style["display"] = "";
		    		    
		  }
		  else
		  {
		    managedWorkAreaLink.innerHTML=messagesData.turnoverrideon;
		    parent.document.getElementById("manageLink").style["display"] = "none";
		    
		  }
		 
	 }
	displayGridSearchDuration();
        /*It is important to call this after all the divs have been turned on or off.*/
    setResultFrameSize();
    setDeliveryPoint();
            	
}


function initializeGrid(){
	beanGrid = new dhtmlXGridObject('useApprovalStatusViewBean');

	initGridWithConfig(beanGrid,config,false,true,true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		beanGrid._haas_bg_render_enabled = true;
		beanGrid.parse(jsonMainData,"json");
	}	
	beanGrid.attachEvent("onAfterHaasRenderRow",modifyRow)
	beangrid.attachEvent("onRowSelect",selectRow);
	
}


function setDeliveryPoint(){
	var numberOfRows = beanGrid.getRowsNum(); 
	 for(var i=1; i<=numberOfRows; i++)
	 {
		 docLocChanged(i,"dockLocationId",gridCellValue(beanGrid,i,"dockDeliveryPoint"));
	 }
	
}

function selectRow(rowId,cellInd) {
	 var columnId = beanGrid.getColumnId(cellInd);  
	 selectedRowId = rowId;
}

function modifyRow(){
	var managedWorkArea = document.getElementById("managedWorkArea");
	var managedWorkAreaLink = parent.document.getElementById("managedWorkAreaLink");
	 var numberOfRows = beanGrid.getRowsNum(); 
     for(var i=1; i<=numberOfRows; i++){
    	 if (managedWorkArea.value == "YES"){
    	   beanGrid.haasSetColSpanStyle(i, 16, 19, "background-color: #BEBEBE");
    	 }
    	 else
    	 {
    	   beanGrid.haasSetColSpanStyle(i, 20, 29, "background-color: #BEBEBE");
	    }
     }
}


function automatedFeedChanged(rowId, columnId)
{
	gridCell(beanGrid,rowId,"automatedFeedChanged").setValue("Y");
	
	if(columnId == "dockLocationId")
	{
	  docLocChanged(rowId,"dockLocationId",gridCellValue(beanGrid,rowId,"dockDeliveryPoint"));
	}
		
}

function useApprovalChanged(rowId)
{
	gridCell(beanGrid,rowId,"useApprovalChanged").setValue("Y");
		
    checkInput(rowId);
}

function docLocChanged(rowId,columnId,invval) {
	
	  var selectedDocLoc = gridCellValue(beanGrid,rowId,"dockLocationId");
	  	  
	  var dp = $("dockDeliveryPoint"+rowId);
	if(dp != null) {
	  for (var i = dp.length; i > 0; i--) {
		  dp[i] = null;
	  }
	  var selectedIndex = 0 ;
	  var dparr = buildNewDp(selectedDocLoc);
	  
	   for (var i=0; i < dparr.length; i++) {
		   setOption(i,dparr[i].text,dparr[i].value, "dockDeliveryPoint"+rowId);
		 
	    if( dparr[i].value == invval ) selectedIndex = i;
	  }
	  dp.selectedIndex = selectedIndex;
	  //gridCell(beanGrid,rowId,"dockLocationId").setValue(gridCellValue(beanGrid,rowId,columnId));
	}
}

function buildNewDp(selectedDocLoc)
{
	var newDpArray = new Array();
	for( i=0;i < dockdparr.length; i++) {
		if( dockdparr[i].id == selectedDocLoc ) {
			var Dpcoll = dockdparr[i].coll;
			newDpArray[0] = {text:messagesData.pleaseselect,value:""};
				for( k=0;k< Dpcoll.length;k++ ){
					
					newDpArray[newDpArray.length] = {text:Dpcoll[k].name,value:Dpcoll[k].id};
				}
			}	
		}
	
	return newDpArray;
}

function getBarcodeRequester(rowId)
{
 loc = "searchpersonnelmain.do?callbackparam="+rowId;
 openWinGeneric(loc,"searchpersonnelforrequestro","650","455","yes","50","50");
}

function clearBarcodeRequester(rowId) {
	cell(rowId,"barcodeRequesterName").setValue('');
	cell(rowId,"barcodeRequester").setValue('');
	
}

function personnelChanged(id,name,rowId) {
	cell(rowId,"barcodeRequester").setValue(id);
	cell(rowId,"barcodeRequesterName").setValue(name);
	
}

function callUpdateManagedWorkArea()
{
 var managedWorkArea = document.getElementById("managedWorkArea");
 if (managedWorkArea.value == "YES")
 {
	 document.getElementById("managedUseApproval").value = "NO";
	 managedWorkArea.value = "NO"
 }
 else
 {
	 document.getElementById("managedUseApproval").value = "YES";
	 managedWorkArea.value = "YES"
       
 }

    document.getElementById("uAction").value = 'UpdateManagedUseApproval';
    beanGrid.parentFormOnSubmit();
    document.genericForm.submit();
	parent.showPleaseWait();
 
}


function updateManagedWorkArea()
{
var managedWorkArea  =  document.getElementById("managedWorkArea");
var managedWorkAreaAlertMsg = "";

if (managedWorkArea.value == "YES")
{
 managedWorkAreaAlertMsg = messagesData.makeselectedworkareanotmanaged;
}
else
{
 managedWorkAreaAlertMsg = messagesData.makeselectedworkareamanaged;
}

 if (confirm(managedWorkAreaAlertMsg))
 {
  updateManagedWorkAreaCout = 0;
  //waitUpdateManagedWorkAreaSomeTime();
  setTimeout('callUpdateManagedWorkArea()',10);
 }
 else
 {

 }
}


function updateActivateAll()
{
	try
    {
     if (confirm(messagesData.willactivatepartsapproved))
     {
    	 
        var updateAllRows  =  document.getElementById("updateAllRows");
        updateAllRows.value = "AddAll";
        var submitUpdate  =  document.getElementById("submitUpdate");
        submitUpdate.value = "submitUpdate";

        document.getElementById("uAction").value = 'update';
        
        beanGrid.parentFormOnSubmit();
	    document.genericForm.submit();
		parent.showPleaseWait();
        
     }
    }
    catch (ex)
    {
    }
}

function updateDeleteAll()
{
    try
    {
     if (confirm(messagesData.willmakepartsactivatemsg))
     {
        var updateAllRows  =  document.getElementById("updateAllRows");
        updateAllRows.value = "deleteAll";
        var submitUpdate  =  document.getElementById("submitUpdate");
        submitUpdate.value = "submitUpdate";

        document.getElementById("uAction").value = 'update';
        
        beanGrid.parentFormOnSubmit();
	    document.genericForm.submit();
		parent.showPleaseWait();
       
}
     else
     {

     }
    }
    catch (ex)
    {
    }
}


function update()
{
	try
    {
		
    	if(validationforUpdate()) {
    		
    		var updateAllRows  =  document.getElementById("updateAllRows");
            updateAllRows.value = "";

            var submitUpdate  =  document.getElementById("submitUpdate");
            submitUpdate.value = "submitUpdate";
            document.getElementById("uAction").value = 'update';
            
            beanGrid.parentFormOnSubmit();
    	    document.genericForm.submit();
    		parent.showPleaseWait();
    	 }
        
    }
    catch (ex)
    {
    }
}

function validationforUpdate() {
	var rowsNum = beanGrid.getRowsNum();
	for (var p = 1; p < (rowsNum+1) ; p ++)
    {
		if (checkInput(p) == false) {
			beanGrid.selectRowById(p,null,false,false);	// Make the selected row fall on the one which does pass the validation
			return false;
		}
		return true; 
     }
	
   }

function checkInput(rowId)
{
var errorMessage = messagesData.validvalues;
var warningMessage = messagesData.alert;
var errorCount = 0;
var warnCount = 0;
 

var mwLimitQuantityPeriod1 = cellValue(rowId,"mwLimitQuantityPeriod1");
var mwDaysPeriod1 = cellValue(rowId,"mwDaysPeriod1");
if (mwLimitQuantityPeriod1.trim().length > 0 && !(isInteger(mwLimitQuantityPeriod1)))
{
  errorMessage = errorMessage + messagesData.limit1quantity+"\n";
  errorCount = 1;
  cell(rowId,"mwLimitQuantityPeriod1").setValue("");
}
else if ((mwLimitQuantityPeriod1.trim().length > 0) && ( mwDaysPeriod1.trim().length == 0 || !(isInteger(mwDaysPeriod1)) || mwDaysPeriod1*1 == 0))
{
  errorMessage = errorMessage + messagesData.limit1days + "\n";
  errorCount = 1;
  cell(rowId,"mwDaysPeriod1").setValue("");
 
}
else if (cellValue(rowId,"limitQuantityPeriod1").length > 0 && cellValue(rowId,"daysPeriod1").length > 0)
{
 if ((mwLimitQuantityPeriod1*1/mwDaysPeriod1*1) > (cellValue(rowId,"limitQuantityPeriod1")*1/cellValue(rowId,"daysPeriod1")*1)*1)
 {
  errorMessage = errorMessage + messagesData.limit1msg+"\n";
  cell(rowId,"mwLimitQuantityPeriod1").setValue("");
  cell(rowId,"mwDaysPeriod1").setValue("");
  errorCount = 1;
 }
}

var mwLimitQuantityPeriod2 = cellValue(rowId,"mwLimitQuantityPeriod2");
var mwDaysPeriod2 = cellValue(rowId,"mwDaysPeriod2");
if (mwLimitQuantityPeriod2.trim().length > 0 && !(isInteger(mwLimitQuantityPeriod2)))
{
  errorMessage = errorMessage + messagesData.limit2quantity+"\n";
  errorCount = 1;
  cell(rowId,"mwLimitQuantityPeriod2").setValue("");
  
}
else if ((mwLimitQuantityPeriod2.trim().length > 0) && ( mwDaysPeriod2.trim().length == 0 || !(isInteger(mwDaysPeriod2)) || mwDaysPeriod2*1 == 0) )
{
  errorMessage = errorMessage + messagesData.limit2days+" \n";
  errorCount = 1;
  cell(rowId,"mwDaysPeriod2").setValue("");
  
}
else if (cellValue(rowId,"limitQuantityPeriod2").length > 0 && cellValue(rowId,"daysPeriod2").length > 0)
{
 if ((mwLimitQuantityPeriod2*1/mwDaysPeriod2*1) > (cellValue(rowId,"limitQuantityPeriod2")*1/cellValue(rowId,"daysPeriod2").daysPeriod2*1))
 {
  errorMessage = errorMessage + messagesData.limit2msg+" \n";
  cell(rowId,"mwLimitQuantityPeriod2").setValue("");
  cell(rowId,"mwDaysPeriod2").setValue("");
  errorCount = 1;
 }
}

var mwOrderQuantity = cellValue(rowId,"mwOrderQuantity");
if (mwOrderQuantity.trim().length > 0 && !(isInteger(mwOrderQuantity)))
{
  errorMessage = errorMessage + messagesData.orderquantity+"\n";
  errorCount = 1;
  cell(rowId,"mwOrderQuantity").setValue("");
  
}
else if (mwOrderQuantity.trim().length > 0)
{
 var mwOrderQuantityRule = cellValue(rowId,"mwOrderQuantityRule");
 if (mwOrderQuantityRule.trim().length == 0 )
 {
  errorMessage = errorMessage + messagesData.orderqtytype+"\n";
  errorCount = 1;
  cell(rowId,"mwOrderQuantityRule").setValue("");
 
 }
}

var mwEstimateOrderQuantityPeriod = cellValue(rowId,"mwEstimateOrderQuantityPrd");
if (mwEstimateOrderQuantityPeriod.trim().length > 0 && (!(isInteger(mwEstimateOrderQuantityPeriod)) || mwEstimateOrderQuantityPeriod*1 == 0))
{
  errorMessage = errorMessage + messagesData.estimatedcovereage+"\n";
  errorCount = 1;
  cell(rowId,"mwEstimateOrderQuantityPrd").setValue("");
  
}

if (errorCount >0)
   {
    alert(errorMessage);
    cell(rowId,"ok").setValue(false);
   		return false;
   }
 
 return true;
}




