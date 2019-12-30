var returnSelctedPO;
var returnSelctedInvGrp;
	
function submitSearchForm()
{
  document.getElementById("uAction").value = 'search';
  
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  
  if (checkSearchPOsInput()) {
    showPleaseWait();
    return true; 
  } else 
    return false;
}

function checkSearchPOsInput()
{
  var errorMessage = messagesData.validValues + "\n\n";
  var warningMessage = messagesData.alert + "\n\n";
  var errorCount = 0;
  var warnCount = 0;
  var hubVal = document.getElementById("branchPlant");
  var inventoryGroupVal = document.getElementById("inventoryGroup");
  var buyerVal = document.getElementById("buyer");
  var dropDownEmptyCnt = 0; 
  
	
  var srchFld1 = document.getElementById("searchField");
  var srchStr1 = document.getElementById("searchString");
  if (srchFld1.value == "itemId") {
    if (srchStr1.value.trim().length != 0 && (!(isInteger(srchStr1.value.trim())) || srchStr1.value*1 == 0 ) )
    {
      errorMessage = errorMessage + messagesData.itemId + "\n\n";
      errorCount = 1;
      srchStr1.value = "";
    }
  }
  
  var srchFld2 = document.getElementById("searchField2");
  var srchStr2 = document.getElementById("searchString2");
  if (srchFld2.value == "itemId") {
    if (srchStr2.value.trim().length != 0 && (!(isInteger(srchStr2.value.trim())) || srchStr2.value*1 == 0 ) )
    {
      errorMessage = errorMessage + messagesData.itemId + "\n\n";
      errorCount = 1;
      srchStr2.value = "";
    }
  }
    
var confirmedFromDate = document.getElementById("confirmedFromDate");
var confirmedToDate = document.getElementById("confirmedToDate");
var supplierId = document.getElementById("supplierId");
var promisedFromDate = document.getElementById("promisedFromDate");
var promisedToDate = document.getElementById("promisedToDate");

if (srchStr2.value.trim().length == 0 && srchStr1.value.trim().length == 0 &&
    confirmedFromDate.value.trim().length == 0 && confirmedToDate.value.trim().length == 0 &&
    supplierId.value.trim().length == 0 && promisedFromDate.value.trim().length == 0 &&
    promisedToDate.value.trim().length == 0)
{
  if( hubVal.value.trim().length== 0 )
	 {
		 dropDownEmptyCnt++; 
	 }	 
	 
	 if(inventoryGroupVal.value.trim().length== 0)
	 {
		 dropDownEmptyCnt++; 
	 }	 
	 
	 if(buyerVal.value.trim().length== 0)
	 {
		dropDownEmptyCnt++; 
	 }
	 
	 if(dropDownEmptyCnt > 1)
	 {	 
	 errorCount =1;
	 errorMessage = errorMessage + messagesData.inputText+ "\n";
	 
	 } 
}
    
 if (errorCount >0)
 {
    alert(errorMessage);
    return false;
 }
 
 return true;
}

function hubChanged()
{
   var hubO = document.getElementById("branchPlant");
   var inventoryGroupO = document.getElementById("inventoryGroup");
   var selhub = hubO.value;

   for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
   
   count = 0;
  for (i=0;i<hubO.length;i++)
  {
    if ((hubO.options[i].selected) == true)
     count = count + 1;
  }

   if (count == 1 ) {
     showInventoryGroupOptions(selhub);
   } else {
     setOption(0,messagesData.all,"","inventoryGroup");
   }
   
   inventoryGroupO.selectedIndex = 0;
}

function showInventoryGroupOptions(selectedhub)
{
    var invgrpid = new Array();

    invgrpid = altinvid[selectedhub];

    var invgrpName = new Array();

    invgrpName = altinvName[selectedhub]; 
    if(selectedhub.trim().length != 0) {
      if(invgrpid.length == 0)   
        setOption(0,messagesData.all,"","inventoryGroup");

      for (var i=0; i < invgrpid.length; i++)
      {
        setOption(i,invgrpName[i],invgrpid[i],"inventoryGroup")
      }
   }
}

function getSuppliers()
{
   loc = "posuppliermain.do?displayElementId=supplierName&valueElementId=supplierId";

   openWinGeneric(loc,"supplierssearch","800","500","yes","50","50")
}

function clearSupplier()
{
    document.getElementById("supplierName").value = "";
    document.getElementById("supplierId").value = "";
}

var oritarget;
function createExcel() {
		document.getElementById('uAction').value="createXSL";
		oritarget= document.searchPosForm.target;
		document.searchPosForm.target='_excel_report_file';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
		setTimeout("document.searchPosForm.submit();document.searchPosForm.target=oritarget;document.getElementById('uAction').value='search';",300);
		return false;
}

function changeSearchTypeOptions(selectedValue,selectId)
{
	  var searchModeId = 'searchMode';

	  if(selectId == 'searchField2')
		  searchModeId = 'searchMode2';
	  
	  searchType = $(searchModeId);
	  
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if(selectedValue == 'itemId' && (searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith'))
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, searchModeId)
		    if ((selectedValue == 'supplierName' || selectedValue == 'partNumber' || selectedValue == 'customerPo'|| selectedValue == 'mfgPartNo' || selectedValue == 'manufacturer') && searchMyArr[i].value == 'is')
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
}

function returnSelected(){

    if(returnSelctedPO == null || returnSelctedPO == "") {
  		alert(messagesData.selecttocontinue);
  		return false;
  	}    
  	window.opener.populateRadianPo(returnSelctedPO,returnSelctedInvGrp);
  	window.close();
}