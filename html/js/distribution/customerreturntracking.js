var selectedRowId = null;
var beanGrid;
function setValue(selectedOption) {
  document.getElementById("searchOption").value = selectedOption;
}



function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	var now = new Date();
	$("startSearchTime").value = now.getTime();

	if (null != $("selectedRow")) {
		$("selectedRow").innerHTML = "";
	}

	if (validateSearchForm()) {
		$('uAction').value = 'search';
		document.genericForm.target = 'resultFrame';
		showPleaseWait();
		document.genericForm.submit();
		return true;
	} else {
		return false;
	}
}

function validateSearchForm() {
  var errorMessage = messagesData.validvalues + "\n\n";
  var errorCount = 0;

  var srchFld1 = document.getElementById("searchField");
  var srchStr1 = document.getElementById("searchArgument");
  if (srchFld1.value == "prNumber") {
    if (srchStr1.value.trim().length != 0 && !isFloat(srchStr1.value.trim()) )
    {
      errorMessage = errorMessage + messagesData.materialrequest + "\n";
      errorCount = 1;
      srchStr1.value = "";
    }
  }
  if (srchFld1.value == "customerRmaId") {
	    if (srchStr1.value.trim().length != 0 && !isInteger(srchStr1.value.trim()) )
	    {
	      errorMessage = errorMessage + messagesData.rma + "\n";
	      errorCount = 1;
	      srchStr1.value = "";
	    }
	  }
  
  var searchOption = document.getElementById("searchOption").value;
  var days = document.getElementById("days");
  if (searchOption == 2) {  
    if (!isInteger(days.value.trim(),false) )
    {
      errorMessage = errorMessage + messagesData.days + "\n";
      errorCount = 1;
      days.value = "";
    }
  }
  
 if (errorCount >0)
 {
    alert(errorMessage);
    return false;
 }
 return true;
}

function createXSL(){
  var flag = true;//validateForm();
  if(flag) {
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_NoSalesExcel','650','600','yes');
    document.genericForm.target='_NoSalesExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}



function onKeySearch(e,doFunction) {
  var keyCode;
  if(window.event)
  {
    keyCode = window.event.keyCode;     //IE
  }else
  {
    try
    {
      keyCode = e.which;     //firefox
    }
    catch (ex){
      //return false;
    }
  }
  if (keyCode==13) {
    doFunction();
  }
}

function doOnRowSelected(rowId,cellInd) {
 var columnId = beanGrid.getColumnId(cellInd);  
 selectedRowId = rowId;
 var hasPermission = cellValue(beanGrid.getSelectedRowId(),"hasPermission");
 if (hasPermission)
 { 
  var selectedRMA = parent.window.document.getElementById("selectedRow");
  var customerRmaId = beanGrid.cellById(rowId, beanGrid.getColIndexById("customerRmaId")).getValue();
  selectedRMA.innerHTML = "<a href=\"#\" onclick=call('viewRMA'); return false;>"+messagesData.viewrma+" : "+customerRmaId+"</a>";
 }
}

function viewRMA() {
	var lineItem = gridCellValue(beanGrid, selectedRowId, "lineItem");
	var prNumber = gridCellValue(beanGrid, selectedRowId, "prNumber");
	var rmaId = gridCellValue(beanGrid, selectedRowId, "customerRmaId");
	
	var loc;
	var tabName;
	if (gridCellValue(beanGrid, selectedRowId, "isDistribution") == 'N') {
		loc = "/tcmIS/hub/cmscustomerreturnrequest.do?";
		tabName = messagesData.cmscustomerreturnrequest;
	} else {
		loc = "/tcmIS/distribution/customerreturnrequest.do?action=search&";
		tabName = messagesData.customerreturnrequest;
	}
	loc += "rmaId=" + rmaId
		+ "&lineItem=" + lineItem
		+ "&prNumber=" + prNumber;

	try {
		parent.parent.openIFrame("showcustomerreturnrequest" + rmaId, loc, tabName + " " + rmaId, "", "N");
	} catch (ex) {
		openWinGeneric(loc, "showcustomerreturnrequest", "900", "600", "yes", "80", "80", "yes");
	}
}

function selectRightclick(rowId,cellInd){
	beanGrid.selectRowById(rowId,null,false,false);	
	selectedRowId = rowId;	 
	var hasPermission = cellValue(beanGrid.getSelectedRowId(),"hasPermission");
	 if (hasPermission)
	 {    	
	  toggleContextMenu('viewRMA');	
	 }	
}

function myResultOnLoad(gridConfig)
{
	// Initial setting this to display which is later  based on per row.
	parent.document.getElementById("updateResultLink").style["display"] = "";
	
	 initGridWithGlobal(gridConfig);

	 /*This dislpays our standard footer message*/
	 displayGridSearchDuration();

	 /*It is important to call this after all the divs have been turned on or off.*/
	 setResultFrameSize();
}

function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchMode');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if((selectedValue == 'prNumber' || selectedValue == 'customerRmaId') && (searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith') )
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
		    if ( (selectedValue == 'customerName' || selectedValue == 'poNumber') && searchMyArr[i].value == 'is')
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
}
