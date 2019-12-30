/*Call this if you want to initialize something on load in the search frame.*/
function mySearchOnload()
{
 setSearchFrameSize();
}

function validateForm() {
	var searchArgument = document.getElementById('searchArgument');
  searchArgument.value   = searchArgument.value.trim();

  if(    document.getElementById('searchField').value == 'packingGroupId'
		&& !isInteger(searchArgument.value,true )      )
			{
				alert(messagesData.packingInt);
				return false;
			}

  var hub0 = document.getElementById("hub");
  if (hub0.value.trim().length ==0 && searchArgument.value.length ==0)
  {
    alert(messagesData.inputSearchText);
		return false;
  }

  return true;
}

function submitSearchForm() {
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var flag = validateForm();
  if(flag) {
// for auto resubmit search..
   var action = document.getElementById("action");
   action.value = "search";
   document.genericForm.target='resultFrame';
   parent.showPleaseWait();
   document.genericForm.submit();
  }
}

function hubChanged() {
  var hub0 = document.getElementById("hub");
  var inventoryGroup0 = document.getElementById("inventoryGroup");

  var selectedHub = hub0.value;
  for (var i = inventoryGroup0.length; i > 0; i--) {
    inventoryGroup0[i] = null;
  }

  if (hub0.value.trim().length >0)
  {
  showInventoryGroupOptions(selectedHub);
  inventoryGroup0.selectedIndex = 0;
  }
  else
  {
    setOption(0,messagesData.all,"", "inventoryGroup");
  }
}

function showInventoryGroupOptions(selectedHub) {
  var inventoryGroupArray = new Array();
  inventoryGroupArray = altInventoryGroup[selectedHub];

  var inventoryGroupNameArray = new Array();
  inventoryGroupNameArray = altInventoryGroupName[selectedHub];

  if(inventoryGroupArray.length == 0) {
    setOption(0,messagesData.all,"", "inventoryGroup")
  }

  for (var i=0; i < inventoryGroupArray.length; i++) {
    setOption(i,inventoryGroupNameArray[i],inventoryGroupArray[i], "inventoryGroup")
  }
}

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_FreightAdviceGenerateExcel','650','600','yes');
    document.genericForm.target='_FreightAdviceGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultOnload()
{
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/
 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
}

function freightAdvice()
{
  selPackingGroupId = document.getElementById("packingGroupId"+selectedRowId);
  //alert (selPackingGroupId.value);
  var action = document.getElementById("action");
  action.value = 'changefreightadvice';
  var packingGroupId = document.getElementById("packingGroupId");
  packingGroupId.value = selPackingGroupId.value;
  if (selPackingGroupId.value.trim().length > 0)
  {
    openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_ChangeFreightAdvice','400','200','yes',"50","50","no");
    document.genericForm.target='_ChangeFreightAdvice';
    var a = window.setTimeout("document.genericForm.submit();",500);
    //openWinGeneric("freightadviceresults.do?changefreightadvice='yes'&packingGroupId="+packingGroupId.value+"","changefreightadvice","400","200","yes","20","20","no");
  }
}

var selectedRowId = null;
var preClass = null;

function selectRow(rowId)
{
   var selectedRow = document.getElementById("rowId"+rowId+"");
   if ( rowId != selectedRowId )
    {
		if( selectedRowId != null ) {
	    	document.getElementById("rowId"+selectedRowId).className = preClass;
	    }
    	selectedRowId = rowId;
   		preClass = selectedRow.className;
      selectedRow.className = "selected";
    }
    else {
/* no desecting processc
    	document.getElementById("rowId"+prerow).className = preClass;
		prerow = null;
*/
    }

  var selRowShipConfirmDate = document.getElementById("shipConfirmDate"+selectedRowId+"");
  var permission = document.getElementById("permission"+selectedRowId+"");
  if (selRowShipConfirmDate.value.trim().length > 0 || permission.value != 'Yes')
  {
   toggleContextMenu('contextMenu');
  }
  else
  {
   toggleContextMenu('freightAdvice');
  }
}
