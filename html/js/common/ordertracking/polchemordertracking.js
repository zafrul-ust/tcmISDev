var submitcount=0;
var selectedrow=null;
var selectedRowId=null;

function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}


/*The reason for this is to show the error messages from the main page
function showErrorMessages()
{
  parent.showErrorMessages();
}
*/

function mySearchOnload()
{
	setSearchFrameSize();
	//showInventoryLinks(document.getElementById("facilityId").value);
	//showHub();
}

function myOnload()
{   
	//it's import to put this before setResultFrameSize(), otherwise it will not resize correctly
	displaySearchDuration();
	setResultFrameSize();
}

function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var action = document.getElementById("uAction");
 action.value = 'search';
 
 var now = new Date();
 document.getElementById("startSearchTime").value = now.getTime();

//	document.genericForm.submit();
 //set start search time
 /*
 var now = new Date();
 var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var hubId = document.getElementById("hub");
 document.getElementById("hubName").value = hubId[hubId.selectedIndex].text;
 var inventoryGroup = document.getElementById("inventoryGroup");
 document.getElementById("inventoryGroupName").value = inventoryGroup[inventoryGroup.selectedIndex].text;
 var searchWhat = document.getElementById("searchWhat");
 document.getElementById("searchWhatDesc").value = searchWhat[searchWhat.selectedIndex].text;
 var searchType = document.getElementById("searchType");
 document.getElementById("searchTypeDesc").value = searchType[searchType.selectedIndex].text;
 var sortBy = document.getElementById("sortBy");
 document.getElementById("sortByDesc").value = sortBy[sortBy.selectedIndex].text;
 var uom = document.getElementById("unitOfMessure");
 document.getElementById("unitOfMessureDesc").value = uom[uom.selectedIndex].text;
*/

 var flag = validateForm();
  if(flag) {
    parent.showPleaseWait();
    return true;
  }
  else
  {
    return false;
  }

}

function validateForm() {
  var result = true;
//  var facilityName = document.getElementById("facilityName");
//	 document.getElementById("facilityName").value = facilityName.value.trim();
	 if( document.getElementById("searchArgument").value.trim() == '' ) {
	   alert('Search argument is required'); 
	   result = false;
  }
  return result;
} 

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function generateExcel() {
//  var flag = validateForm();
//  if(flag) {
    var action = document.getElementById("uAction");
    action.value = 'createExcel';
//	 var hubId = document.getElementById("hub");
//	 document.getElementById("hubName").value = hubId[hubId.selectedIndex].text;
//	 var inventoryGroup = document.getElementById("inventoryGroup");
//	 document.getElementById("inventoryGroupName").value = inventoryGroup[inventoryGroup.selectedIndex].text;
//	 var searchType = document.getElementById("searchType");
//	 document.getElementById("searchTypeText").value = searchType[searchType.selectedIndex].text;
	
	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_PolchemOrderTrackingGenerateExcel','650','600','yes');
     document.genericForm.target='_PolchemOrderTrackingGenerateExcel';
     var a = window.setTimeout("document.genericForm.submit();",500);
//  }
}

function selectRow(rowId)
{
   var selectedRow = document.getElementById("rowId"+rowId+"");
   
   var selectedRowClass = document.getElementById("colorClass"+rowId+"");
  
   selectedRow.className = "selected"+selectedRowClass.value+"";
  
   if (selectedRowId != null && rowId != selectedRowId)
   {
     var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
     var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
     previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
     
   }
   selectedRowId =rowId;
   
   /*Depending on the different conditions I set the right click menu to have different options*/
   
   toggleContextMenu('showDetails');
}  //end of selectRow

function showDetailsReport()
{
  if (selectedRowId != null)
  {
    window.open('http://www.tcmis.com','DetailsWindow','width=200,height=200');
//   var companyId  =  document.getElementById("companyId"+selectedRowId+"");
//   var mrNumber  =  document.getElementById("prNumber"+selectedRowId+"");
//   var lineItem  =  document.getElementById("lineItem"+selectedRowId+"");

//   if ( mrNumber.value != null && mrNumber.value.length > 0 && mrNumber.value != 0)
//   {
//     var loc = "mrallocationreportmain.do?companyId="+companyId.value+"&mrNumber="+mrNumber.value+"&lineItem=";
//     openWinGeneric(loc,"showMrAllocationReport22","800","500","yes","80","80")
//   }
  }
}


/*Yui pop-ups need to be initialized onLoad to make them work correctly.
I they are not initialized onLoad they tend to act weird
showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,
draggable:true, modal:false } );
showErrorMessagesWin.render();
}
*/
/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}

