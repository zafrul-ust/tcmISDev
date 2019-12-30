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

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function mySearchOnload()
{
	setSearchFrameSize();
}
 
function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var action = document.getElementById("uAction");
 action.value = 'search';
 
 var now = new Date();
 document.getElementById("startSearchTime").value = now.getTime();

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
var errorMessage = messagesData.validvalues+ "\n\n";
var searchField = document.getElementById("searchField");

if (searchField.value == 'itemId')
{
 var searchArgument = document.getElementById("searchArgument");
 if (searchArgument.value.trim().length > 0 && !(isInteger(searchArgument.value.trim())))
 {
  errorMessage = errorMessage + messagesData.itemid + "\n";
  alert(errorMessage);
  return false;
 }
}
  return true;
} 

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function doSubmitUpdates()
{
  var errorMessage = messagesData.validvalues+"\n\n";
  var totalLines = document.getElementById("totalLines").value;
  
  var i=0;
  for (i=0;i<totalLines;i++)
  {
	     var needDate = document.getElementById("needDate"+i+"");
	       if (needDate != null && needDate.value.trim() == "" && document.getElementById("okDoUpdate"+i+"").checked == true)
	       {  
	         alert(errorMessage + messagesData.needDate );
	         needDate.value = document.getElementById("todayDate").value;
	         document.getElementById("okDoUpdate"+i+"").checked = false;
	         return;
	       }
  }
     
    	/*Set any variables you want to send to the server*/
    	var uAction = document.getElementById("uAction");
    	uAction.value = 'update';
    	parent.showPleaseWait();
    	/*Submit the form in the result frame*/
   		document.genericForm.submit();
   		
}

function checkQtyValue (rownum)
{
  var okDoUpdate  =  document.getElementById("okDoUpdate"+rownum+"");
  if (okDoUpdate.checked)
  {
	var replenishQty  =  document.getElementById("replenishQty"+rownum+"");

	if (!(isPositiveInteger(replenishQty.value.trim(),false)))
   {
   	alert(messagesData.validvalues+"\n\n"+messagesData.rpl);
		replenishQty.value = "";
		okDoUpdate.checked=false;
   }
  }
}

function checkValues(rownum)
{
checkQtyValue(rownum);
checkCalendarValue(rownum);
}

function checkCalendarValue(rownum)
{
var errorMessage = messagesData.validvalues+"\n\n";

if( document.getElementById("okDoUpdate"+rownum+"").checked == false ) return;
var needDate = document.getElementById("needDate"+rownum+"");
  if (needDate.value.trim() == "")
  {  
       needDate.value = document.getElementById("todayDate").value;
       alert(errorMessage + messagesData.needDate );
       document.getElementById("okDoUpdate"+rownum+"").checked = false;
  }
}

function generateExcel() {
    var action = document.getElementById("uAction");
    action.value = 'createExcel';

	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ForceBuyGenerateExcel','650','600','yes');
     document.genericForm.target='_ForceBuyGenerateExcel';
     var a = window.setTimeout("document.genericForm.submit();",500);
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
   
  // toggleContextMenu('showDetails');
}  //end of selectRow

function limitText(rownum, limitNum) {
    var limitCount;
    var limitField  =  document.getElementById("comment"+rownum+"");
// alert(limitField.value.length);
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
		alert(messagesData.maximum4000);
	} 
}

