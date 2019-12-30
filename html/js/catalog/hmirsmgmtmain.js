
var children = [];
var resizeGridWithWindow = true;

function submitSearchForm() {
	showPleaseWait();
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();
	document.genericForm.target='resultFrame';
	if (auditSearch()) {
		$("uAction").value = "search";
		document.genericForm.submit();
	}
	else {
		alert(messagesData.searchError);
	}
}

function auditSearch() {
	if ($v("searchText").trim().length > 0
			|| $v("loadDateFrom").trim().length > 0 
			|| $v("loadDateTo").trim().length > 0) {
		return true;
	}
	return false;
}

function generateExcel() {
	if (auditSearch()) {
		var searchType = $("searchType");
		var searchTypeDesc = $("searchTypeDesc");
		searchTypeDesc.value = searchType.options[searchType.selectedIndex].text;
		var searchWhat = $("searchWhat");
		var searchWhatDesc = $("searchWhatDesc");
		searchWhatDesc.value = searchWhat.options[searchWhat.selectedIndex].text;
		document.getElementById("uAction").value = 'createExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_hmirsMgmtGenerateExcel','650','600','yes');
	    document.genericForm.target='_hmirsMgmtGenerateExcel';
	    var a = window.setTimeout("document.genericForm.submit();",500);
	}
}

function closeAllchildren() {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren();
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = [];
}

function setSearchWhat(searchWhatArray) {
	var obj = $("searchWhat");
	for ( var i = obj.length; i >= 0; i--)
		obj[i] = null;

	for ( var j = 0; j < searchWhatArray.length; j++) {
		setOption(j, searchWhatArray[j].text, searchWhatArray[j].id,
				"searchWhat");
	}
	//$("searchWhat").value = $v("selectedSearchWhat");

	//if ($v("selectedSearchWhat") == '')
	//	obj.selectedIndex = 0;
}
    
function changeSearchTypeOptions(selectedValue) {
	var searchType = $('searchType');
	if ($v("searchWhat") == 'itemId') {
		$("displayOnlyUnmappedNsns").checked = false;
		$("displayOnlyUnmappedNsns").disabled = true;
	}
	else {
		$("displayOnlyUnmappedNsns").checked = true;
		$("displayOnlyUnmappedNsns").disabled = false;
	}
	for (var i = searchType.length; i > 0;i--)
		searchType[i] = null;

	var actuallyAddedCount = 0;
	for (var i=0; i < searchMyArr.length; i++) {
		if((selectedValue == 'itemId') && (searchMyArr[i].value == 'contains' || searchMyArr[i].value == 'endsWith'))
			continue;
		setOption(actuallyAddedCount,searchMyArr[i].text,searchMyArr[i].value, "searchType")
		if (selectedValue == 'nsn' && searchMyArr[i].value == 'is')
			searchType.selectedIndex = actuallyAddedCount;
		actuallyAddedCount++;
	}
}