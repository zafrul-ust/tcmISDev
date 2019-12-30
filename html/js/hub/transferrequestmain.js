function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	document.genericForm.target = 'resultFrame';
	document.getElementById("uAction").value = 'search';
	// set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	if (flag) {
		showPleaseWait();
		return true;
	}
	else {
		return false;
	}
}

// Validate the search form
function validateForm() {
	var errorMessage = "";
	var errorCount = 0;

	if(document.genericForm.searchField.value == 'itemId') {
		    if(!isInteger(document.genericForm.searchArgument.value.trim(), true)) {
		      alert(messagesData.itemInteger);
		      return false;
		    }
	}
	if(document.genericForm.destHub.value == document.genericForm.hub.value) {
	    {
	      alert(messagesData.sameHub);
	      return false;
	    }
    }
	
	if(document.genericForm.destInventoryGroup.value == '') {
		alert(messagesData.destInventoryGroup);
	      return false;
	}
	
	if(document.genericForm.destInventoryGroup.value == document.genericForm.inventoryGroup.value) {
	    {
	      alert(messagesData.sameInventoryGroup);
	      return false;
	    }
    }
	
		
    return true;
}

function createResultsWindow()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

  if(window.frames["resultFrame"].showGrid)
		setTimeout("initPopupGrid()",50);

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window("errorWin")) {
  // create window first time
  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 700, 400);
  errorWin.setText(messagesData.results);
  errorWin.clearIcon();  // hide icon
  errorWin.denyResize(); // deny resizing
  errorWin.denyPark();   // deny parking
  errorWin.attachObject("errorMessagesArea");
  errorWin.attachEvent("onClose", function(errorWin){errorWin.hide();});
  errorWin.center();
  }
  else
  {
    // just show
    dhxWins.window("errorWin").show();
  }
}

function initPopupGrid() {
	transferResultGrid = new dhtmlXGridObject('successMessageArea');

	initGridWithConfig(transferResultGrid, window.frames['resultFrame'].tConfig, false, false, false);
	if (typeof (window.frames['resultFrame'].tjsonMainData) != 'undefined') {
		transferResultGrid.parse(window.frames['resultFrame'].tjsonMainData, "json");
	}
	
	transferResultGrid.attachEvent("onRowSelect", selectResultRow);
	transferResultGrid.attachEvent("onRightClick", selectResultRow);
}

function selectResultRow(rowId,cellInd) {
	var opsEntityId = gridCellValue(transferResultGrid, transferResultGrid.getSelectedRowId(), "opsEntityId");
 
	if (opsEntityId == 'HAASTCMSIN' || opsEntityId == 'HAASTCMAUS' || opsEntityId == 'HAASPSUSA' || opsEntityId == 'WESCOSCMUK' || opsEntityId == 'WESCOTCMUK' || opsEntityId == 'HAASSCMEIR' || opsEntityId == 'HAASSCMDEU')
		toggleContextMenu('rightClickMenu');  
	
	selectedRowId = rowId;
}


function allocationPopup(searchKey) {

	var companyId = gridCellValue(transferResultGrid, transferResultGrid.getSelectedRowId(), "companyId");
	var itemId = gridCellValue(transferResultGrid, transferResultGrid.getSelectedRowId(), "itemId");
	var itemDesc = gridCellValue(transferResultGrid, transferResultGrid.getSelectedRowId(), "itemDesc");
	var inventoryGroup = gridCellValue(transferResultGrid, transferResultGrid.getSelectedRowId(), "inventoryGroup");
	var transferRequestId = gridCellValue(transferResultGrid, transferResultGrid.getSelectedRowId(), "transferRequestId");
	var curpath = "MR " + gridCellValue(transferResultGrid, transferResultGrid.getSelectedRowId(), "transferRequestId");
	var transferQuantity = gridCellValue(transferResultGrid, transferResultGrid.getSelectedRowId(), "transferQuantity");
	
	showTransitWin();
	  
	  var loc = 
		  "/tcmIS/distribution/allocation.do"+
		  "?companyId=" + encodeURIComponent(companyId) +
		  "&facilityId="+
		  "&itemId="+ itemId +
		  "&inventoryGroup="+ encodeURIComponent(inventoryGroup) +
		  "&specList="+ 
		  "&specification="+ 
		  "&orderPrNumber="+ transferRequestId +
		  "&shipToCompanyId="+
		  "&shipToLocationId="+
		  "&billToCompanyId="+
		  "&billToLocationId="+
		  "&curpath="+ encodeURIComponent(curpath) +
		  "&opsEntityId="+
		  "&priceGroupId="+
		  "&promisedDate="+
          "&needDate="+ $v('toDate')+
          "&labelSpec="+
		  "&incoTerms="+
		  "&searchKey="+searchKey+
		  "&opsCompanyId="+
		  "&remainingShelfLifePercent="+
          "&shipComplete="+
		  "&consolidateShipment="+
		  "&specDetailList="+ 
		  "&specLibraryList="+ 
		  "&specCocList="+ 
		  "&specCoaList="+ 
		  "&currencyId="+
		  "&scratchPadLineItem=0"+
		  "&orderQuantity="+ transferQuantity +
		  "&orderType=MR"+
         	"&itemType="+
          "&unitOfMeasure="+
          "&shipped=0"+ 
          "&pickList=0"+ 
          "&previousPage=AllocationAnalysis"+
		  "&callbackparam="+encodeURIComponent(""+selectedRowId)+
		  "&partDesc="+ encodeURIComponent(itemDesc);
	
	 openWinGeneric(loc, "AllocationDetail_"+transferRequestId.replace(/[.]/,"_"), "1024", "500", "yes", "50", "50");
}

function newConsignedItem() {
  	showTransitWin();
  	var loc = "/tcmIS/distribution/newconsigneditem.do?new&shipToId=&hub=&opsEntityId=HAASTCMUSA&supplier=&supplierName=&companyId=Radian&destInventoryGroup="+encodeURIComponent($v('destInventoryGroup'))+"&sourceInventoryGroup="+encodeURIComponent($v('sourceInventoryGroup')); 
	openWinGeneric(loc,'newConsignedItem',"800","228","yes","50","50","20","20","no");
	return  false;	
}

var dhxWins = null;
//this function will intialize dhtmlXWindow if it's null
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
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

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
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
	      var start = 0;
	  	  if( defaultObj.nodefault )
	  	  	start = 0 ; // will do something??
	  	  else {
		  setOption(0,defaultObj.name,defaultObj.id, eleName); 
			  start = 1;
		  }
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(start++,arr[i].name,arr[i].id,eleName);
		  }
	  }
	  obj.selectedIndex = 0;
	}

	function setDestOps()
	{
	 	$('destHub').onchange = destHubChanged;
	    destOpsChanged();
        var opsO = $("opsEntityId");
        var oldOpsEntityId =document.getElementById("oldOpsEntityId");
        oldOpsEntityId.value = opsO.value;
    }

    function checkDestOpsChanged()
	{
	   var opsO = $("opsEntityId");
       var oldOpsEntityId = $("oldOpsEntityId");
       if (oldOpsEntityId.value.length > 0 && (opsO.value != oldOpsEntityId.value))
       {
           destOpsChanged();
           var oldOpsEntityId =document.getElementById("oldOpsEntityId");
           oldOpsEntityId.value = opsO.value;
       }
    }

    function destOpsChanged()
	{  
	   var opsO = $("opsEntityId");
       var oldOpsEntityId = $("oldOpsEntityId");
       var arr = null;
	   if( opsO.value != '' ) {
	   	   for(i=0;i< allopshubig.length;i++)
	   	   		if( allopshubig[i].id == opsO.value ) {
	   	   			arr = allopshubig[i].coll;
	   	   			break;
	   	   		}
	   }

	   buildDropDown(arr,defaults.hub,"destHub");
	   destHubChanged();
	   //if( defaults.ops.callback ) defaults.ops.callback();
	}
	
	
	function destHubChanged()
	{
	   var opsO = $("opsEntityId");
	   var hubO = $("destHub");
	   var arr = null;
	   if( opsO.value != '' && hubO.value != '' ) {
	   	   for(i=0;i< allopshubig.length;i++)
	   	   		if( allopshubig[i].id == opsO.value ) {
			   	   for(j=0;j< allopshubig[i].coll.length;j++)
		   	   		if( allopshubig[i].coll[j].id == hubO.value ) {
		   	   			arr = allopshubig[i].coll[j].coll;
		   	   			break;
	   	   		    }
	   	   		 break;
	   	   		}
	   }
	   buildDropDown(arr,defaults.inv,"destInventoryGroup");
	   //if( defaults.hub.callback ) defaults.hub.callback();
	}
	
	function createXSL(){
		  var flag = validateForm();
		  if(flag) {
			$('uAction').value = 'createExcel';
		    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_TransferRequestExcel','650','600','yes');
		    document.genericForm.target='_TransferRequestExcel';
		    var a = window.setTimeout("document.genericForm.submit();",50);
		  }
		}
	
	function changeSearchTypeOptions(selectedValue)
	{
		  var searchType = $('searchMode');
		  for (var i = searchType.length; i > 0;i--)
			  searchType[i] = null;

		  var actuallyAddedCount = 0;
		  for (var i=0; i < searchMyArr.length; i++) 
		  {
			    if(selectedValue == 'itemId' && (searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith'))
			    	continue;
			    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
			    if (selectedValue == 'distCustomerPartList'  && searchMyArr[i].value == 'is')
			    	searchType.selectedIndex = actuallyAddedCount;
			    actuallyAddedCount++;
		  }
	}
	