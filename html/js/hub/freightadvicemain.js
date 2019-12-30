
function validateForm() {
	var searchArgument = document.getElementById('searchArgument');
  searchArgument.value   = searchArgument.value.trim();

  if(    document.getElementById('searchField').value == 'packingGroupId'
		&& !isInteger(searchArgument.value,true )      )
			{
				alert(messagesData.packingInt);
				return false;
			}

  if( document.getElementById('searchField').value == 'prNumber'  && !isInteger(searchArgument.value, true)  )
	{
		alert(messagesData.mrNumber);
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
 var now = new Date();
 document.getElementById("startSearchTime").value = now.getTime();   
  if(flag) {
// for auto resubmit search..
   var action = document.getElementById("action");
   action.value = "search";
   document.genericForm.target='resultFrame';
   showPleaseWait();
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

function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchMode');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if((selectedValue == 'packingGroupId' || selectedValue == 'prNumber') && (searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith'))
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
		    if (searchMyArr[i].value == 'is')
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
}

