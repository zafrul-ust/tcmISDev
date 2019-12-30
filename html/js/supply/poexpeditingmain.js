children = new Array(); 

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.poExpeditingForm.target='resultFrame';
 document.getElementById("action").value = 'search'; 
 //set start search time
 var now = new Date();
 var startSearchTime = document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var flag = validateForm();
 if(flag) {
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function showBuyers() {
	  var buyerId = document.getElementById("buyerId");  
	  var personId = document.getElementById("personnelLoggedIn");  
	  for (var i = buyerId.length; i >= 0; i--) {
	    buyerId[i] = null;
	  }
      var selectedIndex = 0;
	  var j = 0
	  for (var i=0; i < allBuyers.length; i++) {
       if (allBuyers[i].status == "A")
       {
        setOption(j,allBuyers[i].lastName,allBuyers[i].personnelId, "buyerId");
       }
       else
       {
        setOption(j,allBuyers[i].lastName,allBuyers[i].personnelId, "buyerId","lightgray");
       }

       if(allBuyers[i].personnelId == personId.value)
       {
          selectedIndex = j;
       }
       j++;   
	  }
	  setOption(0,messagesData.allbuyers,"", "buyerId")
	  buyerId.selectedIndex = selectedIndex;
	}


function showHub() {
  hubChanged();
}

function hubChanged() {
  var hub0 = document.getElementById("hubIdArray");
  var inventoryGroup0 = document.getElementById("inventoryGroup");
  var selectedHub = hub0.value;

  count = 0;
  for (i=0;i<hub0.length;i++)
  {
    if ((hub0.options[i].selected) == true)
     count = count + 1;
  }

  for (var i = inventoryGroup0.length; i > 0; i--) {
    inventoryGroup0[i] = null;
  }

  showInventoryGroupOptions(selectedHub,count);
  inventoryGroup0.selectedIndex = 0;
}

function showInventoryGroupOptions(selectedHub,count) {
  var inventoryGroupArray = new Array();
  inventoryGroupArray = altInventoryGroup[selectedHub];

  if(inventoryGroupArray == null || inventoryGroupArray.length == 0 || count > 1) {
    setOption(0,messagesData.all,"All", "inventoryGroup")
  }
  else {
    for (var i=0; i < inventoryGroupArray.length; i++) {
      setOption(i,inventoryGroupArray[i].name,inventoryGroupArray[i].id, "inventoryGroup")
    }
  }
}

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_expeditereportGenerateExcel','650','600','yes');
    document.poExpeditingForm.target='_expeditereportGenerateExcel';
    var a = window.setTimeout("document.poExpeditingForm.submit();",500);
  }
}

function validateForm(){
	// validates the age input field
	var errorMessage = "";
	var errorCount = 0;
	
	try
	{
	 var age = document.getElementById("expediteAge");
	 var searchInput = document.getElementById("searchArgument");
	 var searchField = document.getElementById("searchField");	 

	 if ( age.value.trim().length != 0 && (!(isPositiveInteger(age.value.trim())) || age.value*1 == 0 ) )
	 {
	    errorMessage +=  messagesData.age + "\n";
	    errorCount = 1;
	    age.value = "";
	 }	

	 if ((searchField.value.trim().length != 0 ) && (  (searchField.value == "itemId") || (searchField.value == "radianPo")      ) )			
	 {
	    if((searchInput.value.trim().length != 0) && (!(isSignedFloat(searchInput.value.trim())) || (searchInput.value*1 == 0 )  ))
	    { 
	    errorMessage +=  messagesData.searchInput + "\n";
	    errorCount = 1;
	    searchInput.value = "";
	    }
	 }	
	 
	} catch (ex) {
	  errorCount++;
	}
	if (errorCount >0)
	 {
	    alert(errorMessage);
	    return false;
	 }	
    return true;
}

function getSuppliers()
{
   loc = "posuppliermain.do?displayElementId=supplierName&valueElementId=supplier&statusFlag=";

   openWinGeneric(loc,"supplierssearch","800","500","yes","50","50")
}

function clearSupplier()
{
    document.getElementById("supplierName").value = "";
    document.getElementById("supplier").value = "";
}


function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchMode');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if((selectedValue == 'radianPo' || selectedValue == 'itemId') && (searchMyArr[i].value == 'like' || searchMyArr[i].value == 'endsWith'))
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
		    if ((selectedValue == 'carrier' || selectedValue == 'companyId' || selectedValue == 'supplierName') && searchMyArr[i].value == 'is')
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
}

function uploadList() {
	children[children.length] = openWinGeneric("poexpeditingupload.do?action=showUploadList","uploadList","450","170","yes","80","80");
}

function openExampleTemplate()
{
	children[children.length] = openWinGenericViewFile('/template/po/OpenPoUploadTemplate.xlsx','_expeditereportExampleExcel','650','600','yes');
    document.poExpeditingForm.target='_expeditereportExampleExcel';
}


function closeAllchildren()
{
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
}


