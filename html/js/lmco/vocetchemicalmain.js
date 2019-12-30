var children = new Array();

function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	document.genericForm.target = 'resultFrame';
	document.getElementById("uAction").value = 'search';
	// set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	if (flag) {
		showPleaseWait();
		document.genericForm.submit();
	}
	else {
		return false;
	}
}

function createXSL() {
	var flag = validateForm();
	if (flag) {
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_VocetMsdsExcel', '650', '600', 'yes');
		document.genericForm.target = '_VocetMsdsExcel';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
}

// Validate the search form
function validateForm() {
	//var errorMessage = "";
	//var errorCount = 0;

    /*
    if ($v("searchText").length == 0) {
        alert(messagesData.missingSearchText);
        return false;
    }

    if (!(isInteger(materialId, true))) {
		alert(messagesData.xxpositiveinteger.replace(/[{]0[}]/g,messagesData.materialid) );
		errorCount = 1;
		$("materialId").value = "";
		return false;
	}*/

	return true;
}

function showOrHideMsdsNoSpan() {
	if($v("searchField") == 'material_id' || $v("searchField") == 'msds_number' || $v("searchField") == 'cas_number')
		$("matchType").value = '=';
	else
		$("matchType").value = 'like';
}

function onKeySearch1(e,doFunction) {
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

function openMsdsNoTextArea(v)
{
	if(v == "create list")
	{
		children[children.length] = openWinGeneric("advancedmsdsviewermain.do?uAction=showtextarea","copypasteformattextarea","325","370","yes","50","50","20","20","no");
		$('matchType').value = 'in csv list';
	}
}

function closeAllchildren()
{
	// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
	//	if (document.getElementById("uAction").value != 'new') {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren();
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = new Array();
}

function facilityChanged() {
		var selectedFacility = $v("facilityId");
	    var arr = null;
	    arr =  vocetSourceArr[selectedFacility];
	    buildDropDown(arr,defaults,"vocetSourceId");
		arr =  vocetCategoryArr[selectedFacility];
	    buildDropDown(arr,defaults,"vocetCategoryId");
    
	    if('Y' == permissionArr[selectedFacility])
	    	$('uploadB').style.display="";
	    else
	    	$('uploadB').style.display="none";
}

function buildDropDown( arr, defaultObj, eleName ) {
	   var obj = $(eleName);
	   for (var i = obj.length; i > 0;i--)
	     obj[i] = null;
	  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
	  if( arr == null || arr.length == 0 )
		  setOption(0,defaultObj.text,defaultObj.value, eleName);
	  else if( arr.length == 1 )
		  setOption(0,arr[0].text,arr[0].value, eleName);
	  else {
	      var start = 0;
	  	  if( defaultObj.nodefault )
	  	  	start = 0 ; // will do something??
	  	  else {
		  setOption(0,defaultObj.text,defaultObj.value, eleName);
			  start = 1;
		  }
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(start++,arr[i].text,arr[i].value,eleName);
		  }
	  }
	  obj.selectedIndex = 0;
}

function invalidateUpdatedBy() {
     var updatedByName  =  document.getElementById("updatedByName");
     var updatedBy  =  document.getElementById("updatedBy");
     if (updatedByName.value.length == 0) {
       updatedByName.className = "inputBox";
     }else {
       updatedByName.className = "inputBox red";
     }
     //set to empty
     updatedBy.value = "";
}
