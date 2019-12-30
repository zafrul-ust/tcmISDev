var resizeGridWithWindow = true;
var windowCloseOnEsc = true; 
var multiplePermissions = true;

function $(a) {
	return document.getElementById(a);
}

function validateSearchCriteriaInput()
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
	if ($v("inventoryGroup") == '')
	{  
		errorMessage = errorMessage + messagesData.inventoryGroup;
		errorCount = 1;
	}
	
	if (errorCount >0)
	{
	 alert(errorMessage);
	 return false;
	}
	
	return true;
}

function submitSearchForm() {
	if( !validateSearchCriteriaInput() ) return;
	  var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
	var userAction = document.getElementById("uAction");
	userAction.value = 'search';

   	document.genericForm.target='resultFrame';
   	showPleaseWait();
   	document.genericForm.submit();
}




function showErrorMessages() {
  parent.showErrorMessages();
}


function createExcel() {
	if( !validateSearchCriteriaInput() ) return;
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'item_direct_supply','800','600','yes');
		document.genericForm.target='item_direct_supply';
  	var userAction = document.getElementById("uAction");
 		userAction.value = 'createExcel';
    var a = window.setTimeout("document.genericForm.submit();",1000);
    //document.genericForm.submit();
   	return false;
}

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value;
	if (totalLines > 0)
	{
		document.getElementById("itemDirectSupplyBean").style["display"] = "";
		
		initializeGrid();
		if(showUpdateLinks)
		{
			parent.document.getElementById('mainUpdateLinks').style["display"] = "";
			parent.document.getElementById("additemSpan").style["display"] = "";
			parent.document.getElementById("deleteitemSpan").style["display"] = "";
		}
		
	}  
	else
	{
		document.getElementById("itemDirectSupplyBean").style["display"] = "none";
		if(showUpdateLinks)
		{
		parent.document.getElementById("additemSpan").style["display"] = "";
		parent.document.getElementById("deleteitemSpan").style["display"] = "none";
		}
	}

	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
	
	if (totalLines == 0)
	{
		parent.document.getElementById('mainUpdateLinks').style["display"] = "";
		if(showUpdateLinks){
		    parent.document.getElementById("additemSpan").style["display"] = "";
		    parent.document.getElementById("deleteitemSpan").style["display"] = "none";
		}
	    else{
			parent.document.getElementById("additemSpan").style["display"] = "none";
			parent.document.getElementById("deleteitemSpan").style["display"] = "none";
		}
						
	}
 
}

function initializeGrid(){
	
	beangrid = new dhtmlXGridObject('itemDirectSupplyBean');
    initGridWithConfig(beangrid, config, false, true, true);
	if( typeof( jsonMainData ) != 'undefined' ) {
		
	  beangrid.parse(jsonMainData,"json");
	}
	beangrid.attachEvent("onRowSelect", selectRow);
}

function selectRow(rowId, cellInd) {
	selectedRowId = rowId; 
}


function addItem() {
	var opsEntityId = document.getElementById("opsEntityId").value;
	var inventoryGroup = document.getElementById("inventoryGroup").value;
	var hub = document.getElementById("hub").value;
	
	var loc = "/tcmIS/hub/addnewitem.do?uAction=new"+
		"&opsEntityId="+opsEntityId+
		"&inventoryGroup="+inventoryGroup+
		"&hub="+hub;
	
		
	var winId= 'addnewitem';
	openWinGeneric(loc,winId.replace('.','a'),"550","300","yes","50","50","20","20","no");
	
}

function doDelete() {
	  var numberOfRows = $v('totalLines');
	  var flag = validateDeleteForm(numberOfRows);
	  if(flag) {
		document.getElementById("uAction").value = 'delete';
	     beangrid.parentFormOnSubmit();
	    document.genericForm.submit();
		parent.showPleaseWait();
	  }
	  return flag;
}

function validateDeleteForm(numberOfRows) {
	  var flag = true;
	  var selected = false;
	  if(numberOfRows != null) {
	    for(var i=1; i<=numberOfRows; i++) {
		  var checked = false;
		  try {
			  checked = cellValue(i,'okDoUpdate');
		  } catch(ex){}
		  if( !checked ) continue;
		  selected = true;
	    }
	  }
		  if( !selected ) {
			  alert(messagesData.norowselected);
			  return false;
		  }	  
		  
		  return flag;
	}

function refreshPage()
{			
	document.genericForm.target='';
	var action = document.getElementById("uAction");
	action.value="search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	document.genericForm.submit();
	
}


