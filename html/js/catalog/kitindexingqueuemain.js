function submitSearchForm()
{
	
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  if(validateSearchForm()) { 
   $('uAction').value = 'search';
      document.genericForm.target='resultFrame';
   
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function validateSearchForm() {
	var searchField = document.getElementById("searchField").value;
	//var searchArgument = document.getElementById("searchArgument").value;
	//if(searchArgument != ''){
		if(searchField == 'requestId') {
			 if (!isInteger(searchArgument.trim())) {
			    messagesData.validvalues;
			    return false;
			 }
		}
	//}
	return true;
}

function createXSL(){
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Catalog_AddQc_Excel','650','600','yes');
    document.genericForm.target='_Catalog_AddQc_Excel';
    var a = window.setTimeout("document.genericForm.submit();",50);
}

function getNewKitNumber(){
    $('uAction').value = 'getNewKitNumber';
    document.genericForm.target='resultFrame';
    showPleaseWait();
    var a = window.setTimeout("document.genericForm.submit();",50);
}


/*function changeSearchTypeOptions(selectedValue)
{
	  var searchType = $('searchMode');
	  for (var i = searchType.length; i > 0;i--)
		  searchType[i] = null;

	  var actuallyAddedCount = 0;
	  for (var i=0; i < searchMyArr.length; i++) 
	  {
		    if(selectedValue == 'requestId' && searchMyArr[i].value == 'contains')
		    	continue;
		    setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchMode")
		    if ( selectedValue == 'manufacturer' && searchMyArr[i].value == 'is')
		    	searchType.selectedIndex = actuallyAddedCount;
		    actuallyAddedCount++;
	  }
}*/
