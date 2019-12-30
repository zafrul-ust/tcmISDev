////////// search area scripts.
function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if( !isValidSearchForm()) return;
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
    
}

function isValidSearchForm()
{
	var errorMessage = messagesData.validvalues + "\n\n";
	var errorCount = 0;
	var searchField = $v("searchField");
	var searchArgument = $v("searchArgument");
	
	if (!searchArgument == '' && searchField == 'itemId' && !isInteger(searchArgument,true))
	{  
		errorMessage = errorMessage + messagesData.itemInteger;
		$("searchArgument").value = "";
		errorCount = 1;
	}
	if (!searchArgument == '' && searchField == 'transferRequestId' && !isInteger(searchArgument,true))
	{  
		errorMessage = errorMessage + messagesData.integer;
		$("searchArgument").value = "";
		errorCount = 1;
	}
	
	if (errorCount >0)
	{
	 alert(errorMessage);
	 return false;
	}
	
	return true;
}

///////////////

function createXSL(){
	if( !isValidSearchForm() ) return;
	
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_TransfersExcel','650','600','yes');
    document.genericForm.target='_TransfersExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  
}


function buildDropDown( arr, defaultObj, eleName ) {
   var obj = $(eleName);
   for (var i = obj.length; i > 0;i--)
     obj[i] = null;
  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
  if( arr == null || arr.length == 0 ) 
	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
  else if( arr.length == 1 )
	  setOption(0,arr[0].name,arr[0].id, eleName);
  else {
	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
	  for ( var i=0; i < arr.length; i++) {
	    	setOption(i+1,arr[i].name,arr[i].id,eleName);
	  }
  }
  obj.selectedIndex = 0;
}

function setOps() {
 	buildDropDown(opshubig,defaults.ops,"sourceOpsEntityId");
 	$('sourceOpsEntityId').onchange = opsChanged;
    $('sourceHub').onchange = hubChanged;	
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
    	$('sourceOpsEntityId').value = defaultOpsEntityId;
    }
    opsChanged();
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0) {
    	$('sourceHub').value = defaultHub;
    	hubChanged();
    }
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0 && preferredInventoryGroup != null && preferredInventoryGroup.length > 0)
    	$('sourceInventoryGroup').value = preferredInventoryGroup;
}

function opsChanged()
{  
	var opshubigColl;
	var defaultArr;
	opshubigColl = opshubig;
    defaultArr = defaults;
  	
  	
   var opsO = $("sourceOpsEntityId");
   var arr = null;
   if( opsO.value != '' && opsO.value != 'all') {
   	   for(i=0;i< opshubigColl.length;i++)
   	   		if( opshubigColl[i].id == opsO.value ) {
   	   			arr = opshubigColl[i].coll;
   	   			break;
   	   		}
   }

   buildDropDown(arr,defaultArr.hub,"sourceHub");
   hubChanged();
  checkDestOpsChanged();
//   if( defaults.ops.callback ) defaults.ops.callback();
}

function hubChanged()
{
	var opshubigColl;
	var defaultArr;
	opshubigColl = opshubig;
    defaultArr = defaults;
  		

   var opsO = $("sourceOpsEntityId");
   var hubO = $("sourceHub");
   var arr = null;
   if( opsO.value != '' && hubO.value != '' && opsO.value != 'all' && hubO.value != 'all' ) {
   	   for(i=0;i< opshubigColl.length;i++)
   	   		if( opshubigColl[i].id == opsO.value ) {
		   	   for(j=0;j< opshubigColl[i].coll.length;j++)
	   	   		if( opshubigColl[i].coll[j].id == hubO.value ) {
	   	   			arr = opshubigColl[i].coll[j].coll;
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   buildDropDown(arr,defaultArr.inv,"sourceInventoryGroup");
//   if( defaults.hub.callback ) defaults.hub.callback();

}

function checkDestOpsChanged()
{
   var opsO = $("sourceOpsEntityId");
   var oldOpsEntityId = $("oldOpsEntityId");
   var opshubigColl;
	var defaultArr;
	opshubigColl = opshubig;
   defaultArr = defaults;
   
   if ((opsO.value != oldOpsEntityId.value))
   {
	   
	   destOpsChanged(opshubigColl,defaultArr);
       var oldOpsEntityId =document.getElementById("oldOpsEntityId");
       oldOpsEntityId.value = opsO.value;
   }
}


function setDestOps(opshubigColl,defaultArr) {
 	//buildDropDown(opshubigColl,defaultArr.ops,"destinationOpsEntityId");
 	//$('destinationOpsEntityId').onchange = destOpsChanged;
    $('destinationHub').onchange = destHubChanged;	
    destOpsChanged(opshubigColl,defaultArr);
    var opsO = $("sourceOpsEntityId");
    var oldOpsEntityId =document.getElementById("oldOpsEntityId");
    oldOpsEntityId.value = opsO.value;
    
}

function destOpsChanged(opshubigColl,defaultArr)
{  
    var opshubigColl;
	var defaultArr;
	opshubigColl = opshubig;
    defaultArr = defaults;
  	  	
   var opsO = $("sourceOpsEntityId");
   
   var arr = null;
   if( opsO.value != '' && opsO.value != 'all' ) {
	    for(i=0;i< opshubigColl.length;i++)
   	   		if( opshubigColl[i].id == opsO.value ) {
   	   			arr = opshubigColl[i].coll;
   	   			break;
   	   		}
   }

   buildDropDown(arr,defaultArr.hub,"destinationHub");
   destHubChanged(opshubigColl,defaultArr);
 //  if( defaults.ops.callback ) defaults.ops.callback();
}

function destHubChanged(opshubigColl,defaultArr)
{
  	var opshubigColl;
	var defaultArr;
	opshubigColl = opshubig;
    defaultArr = defaults;
  	  		
   var opsO = $("sourceOpsEntityId");
   var hubO = $("destinationHub");
   var arr = null;
   if( opsO.value != '' && hubO.value != '' && opsO.value != 'all' && hubO.value != 'all' ) {
   	   for(i=0;i< opshubigColl.length;i++)
   	   		if( opshubigColl[i].id == opsO.value ) {
		   	   for(j=0;j< opshubigColl[i].coll.length;j++)
	   	   		if( opshubigColl[i].coll[j].id == hubO.value ) {
	   	   			arr = opshubigColl[i].coll[j].coll;
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   buildDropDown(arr,defaultArr.inv,"destinationInventoryGroup");
 //  if( defaults.hub.callback ) defaults.hub.callback();
}

var selectedRowId = null;
function selectRow(rowId,cellInd){
    selectedRowId = rowId;
}

function selectRightClick(rowId,cellInd) {
	selectRow(rowId,cellInd);
	if(cellValue(rowId,"quantityNeeded") > cellValue(rowId,"totalQuantityReceived")*1) 
		toggleContextMenu('rightClickMenu');
	else
		toggleContextMenu('contextMenu');
	
}

function allocationPopup(searchKey,auto) {
	parent.showTransitWin();
	
	var loc = 
		  "/tcmIS/distribution/allocation.do"+
		  "?companyId=" +encodeURIComponent(cellValue(selectedRowId,"companyId"))+
		  "&facilityId="+
		  "&itemId="+ encodeURIComponent(cellValue(selectedRowId,"itemId"))+
//		  "&refInventoryGroup="+pp('inventoryGroup')+
		  "&inventoryGroup="+encodeURIComponent(cellValue(selectedRowId,"sourceInventoryGroup"))+
		  "&specList="+ encodeURIComponent(cellValue(selectedRowId,"specListConcat"))+
		  "&specification="+ encodeURIComponent(cellValue(selectedRowId,"specList"))+
		  "&orderPrNumber="+encodeURIComponent(cellValue(selectedRowId,"transferRequestId"))+
		  "&shipToCompanyId="+
		  "&shipToLocationId="+
		  "&billToCompanyId="+
		  "&billToLocationId="+
		  "&curpath="+"IT "+ encodeURIComponent(cellValue(selectedRowId,"transferRequestId"))+
		  "&opsEntityId="+encodeURIComponent(cellValue(selectedRowId,"sourceOpsEntityId"))+
		  "&priceGroupId="+
		  "&promisedDate="+
          "&needDate="+encodeURIComponent(cellValue(selectedRowId,"requestDate"))+
		  "&searchKey="+searchKey+
		  "&opsCompanyId="+encodeURIComponent(cellValue(selectedRowId,"sourceOpsCompanyId"))+ //Source_ops_Company_Id 
		  "&scratchPadLineItem=0"+
		  "&orderQuantity="+encodeURIComponent(cellValue(selectedRowId,"quantityNeeded"))+  // quantity_needed
		  "&orderType=MR"+ 
          "&previousPage=Transfers"+
		  "&callbackparam="+encodeURIComponent(""+selectedRowId)+  // Guess don't need this
		  "&partDesc="+encodeURIComponent(cellValue(selectedRowId,"itemDesc"))+
		  "&itemType="+
          "&unitOfMeasure="+ 	
		  "&labelSpec="+
		  "&incoTerms="+
		  "&remainingShelfLifePercent="+encodeURIComponent(cellValue(selectedRowId,"remainingShelfLifePercent"))+
          "&shipComplete="+ 
		  "&consolidateShipment="+
		  "&specDetailList="+ encodeURIComponent(cellValue(selectedRowId,"specDetailConcat"))+
		  "&specLibraryList="+ encodeURIComponent(cellValue(selectedRowId,"specLibraryConcat"))+
		  "&specCocList="+ encodeURIComponent(cellValue(selectedRowId,"specCocConcat"))+
		  "&specCoaList="+ encodeURIComponent(cellValue(selectedRowId,"specCoaConcat"))+
		  "&currencyId="+
		  "&shipped="+encodeURIComponent(cellValue(selectedRowId,"totalQuantityShipped"))+ 
		  "&isSpecHold="+cellValue(selectedRowId,"rcptQualityHoldSpec")+
		  "&isSlHold="+cellValue(selectedRowId,"rcptQualityHoldShelfLife")+
		  "&catalogId="+cellValue(selectedRowId,"catalogId")+
		  "&catPartNo="+cellValue(selectedRowId,"catPartNo")+
		  "&partGroupNo="+cellValue(selectedRowId,"partGroupNo")+
		  "&catalogCompanyId="+cellValue(selectedRowId,"catalogCompanyId");
//	  children[children.length] = 
	  openWinGeneric(loc, "AllocationDetail_"+encodeURIComponent('transferRequestId').replace(/[.]/,"_"), "1024", "500", "yes", "50", "50");

}


var dhxWins = null;
//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function releaseTransfers() {
	var oneRowChecked = false;
	for(var p=0;p<beanGrid.getRowsNum();p++) {
		var rowId = beanGrid.getRowId(p);
		var ok = $("ok"+rowId);
		if (ok != null && typeof(ok) != 'undefined' && ok.checked)
			oneRowChecked=true;
	}
	if(!oneRowChecked) 
		{
			alert(messagesData.pleasemakeselection)
				return false;
		}
	else
	{
		document.genericForm.target='';
		
		if (beanGrid != null) 
			beanGrid.parentFormOnSubmit();

		document.getElementById('uAction').value = 'releaseTransferRequest';
		parent.showPleaseWait();
		var now = new Date();
		parent.document.getElementById("startSearchTime").value = now.getTime();
		document.genericForm.submit();
	}
}

function closeTransfers() {
	var oneRowChecked = false;
	for(var p=0;p<beanGrid.getRowsNum();p++) {
		var rowId = beanGrid.getRowId(p);
		var okClose = $("okClose"+rowId);
		if (okClose != null && typeof(okClose) != 'undefined' && okClose.checked)
			oneRowChecked=true;
	}
	
	if(!oneRowChecked) 
	{
		alert(messagesData.pleasemakeselection)
		return false;
	}
	else
	{
		document.genericForm.target='';
		
		if (beanGrid != null) 
			beanGrid.parentFormOnSubmit();

		document.getElementById('uAction').value = 'closeTransfer';
		parent.showPleaseWait();
		var now = new Date();
		parent.document.getElementById("startSearchTime").value = now.getTime();
		document.genericForm.submit();
	}
}


function changeSearchTypeOptions(selectedValue) {
	var searchType = $('searchMode');
	for ( var i = searchType.length; i > 0; i--)
		searchType[i] = null;

	var actuallyAddedCount = 0;
	for ( var i = 0; i < searchMyArr.length; i++) {
		if ((selectedValue == 'itemId' || selectedValue == 'transferRequestId')
				&& (searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith'))
			continue;
		setOption(actuallyAddedCount, searchMyArr[i].text,
				searchMyArr[i].value, "searchMode")
		if (selectedValue == 'distCustomerPartList'
				&& searchMyArr[i].value == 'is')
			searchType.selectedIndex = actuallyAddedCount;
		actuallyAddedCount++;
	}
}

