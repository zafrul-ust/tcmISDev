function buildDropDown( arr, defaultObj, eleName ) {
   var obj = $(eleName);
   for (var i = obj.length; i > 0;i--)
     obj[i] = null;
  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
  if( arr == null || arr.length == 0 ) 
	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
  else if( arr.length == 1 )
	  setOption(0,arr[0].name,arr[0].id, eleName);
  else {
      var start = 0;
  	  if( defaultObj.nodefault )
  	  	start = 0 ; // will do something??
  	  else {
	  setOption(0,defaultObj.name,defaultObj.id, eleName); 
		  start = 1;
	  }
	  for ( var i=0; i < arr.length; i++) {
	    	setOption(start++,arr[i].name,arr[i].id,eleName);
	  }
  }
  obj.selectedIndex = 0;
}

function setOps() {
 	buildDropDown(opshubig,defaults.ops,"opsEntityId");
 	$('opsEntityId').onchange = opsChanged;
    $('sourceHub').onchange = hubChanged;
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
    	$('opsEntityId').value = defaultOpsEntityId;
    }
    opsChanged();
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0) {
    	$('sourceHub').value = defaultHub;
    	hubChanged();
}
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0 && preferredInventoryGroup != null && preferredInventoryGroup.length > 0)
    	$('inventoryGroup').value = preferredInventoryGroup;
}

function opsChanged()
{
   var opsO = $("opsEntityId");
   var arr = null;
   if( opsO.value != '' ) {
   	   for(i=0;i< opshubig.length;i++)
   	   		if( opshubig[i].id == opsO.value ) {
   	   			arr = opshubig[i].coll;
   	   			break;
   	   		}
   }

   buildDropDown(arr,defaults.hub,"sourceHub");
   hubChanged();
   if( defaults.ops.callback ) defaults.ops.callback();
}

function hubChanged()
{
   var opsO = $("opsEntityId");
   var hubO = $("sourceHub");
   var arr = null;
   if( opsO.value != '' && hubO.value != '' ) {
   	   for(i=0;i< opshubig.length;i++)
   	   		if( opshubig[i].id == opsO.value ) {
		   	   for(j=0;j< opshubig[i].coll.length;j++)
	   	   		if( opshubig[i].coll[j].id == hubO.value ) {
	   	   			document.getElementById("sourceHubName").value =  hubO.options[hubO.selectedIndex].text;
	   	   			arr = opshubig[i].coll[j].coll;
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   buildDropDown(arr,defaults.inv,"inventoryGroup");
   if( defaults.hub.callback ) defaults.hub.callback();

}

function setDefault() {
	if ($v("selectedHub").length > 0) {
		$("opsEntityId").value = $v("selectedOpsEntityId");
		opsChanged();
	}

	if ($v("selectedHub").length > 0) {
		$("sourceHub").value = $v("selectedHub");
		hubChanged();
	}

	if ($v("selectedInventoryGroup").length > 0)
		$("inventoryGroup").value = $v("selectedInventoryGroup");

}

function changeSearchCriterion() {
	if ($v("category") == "Non-Chemicals") {
		setSearchWhat(searchWhatNonChemArray);
	} else {
		setSearchWhat(searchWhatChemArray);
	}
}

function setSearchWhat(searchWhatArray) {
	var obj = $("searchWhat");
	for ( var i = obj.length; i >= 0; i--)
		obj[i] = null;

	for ( var j = 0; j < searchWhatArray.length; j++) {
		setOption(j, searchWhatArray[j].text, searchWhatArray[j].id,
				"searchWhat");
	}
	$("searchWhat").value = $v("selectedSearchWhat");

	if ($v("selectedSearchWhat") == ''
			|| ($v("category") == "Non-Chemicals" && ($v("selectedSearchWhat") == 'transferRequestId' || $v("selectedSearchWhat") == 'customerRmaId')))
		obj.selectedIndex = 0;
	else
		$("searchWhat").value = $v("selectedSearchWhat");
	// obj.selectedIndex = 0;
}

function getSelectedSearchWhat() {
	if ($("searchWhat").selectedIndex < 4)
		$("selectedSearchWhat").value = $v("searchWhat");
	else
		$("selectedSearchWhat").value = '';
}

function addnewBin() {
	var sourceHub = document.getElementById("sourceHub");
	var sourceHubName = document.getElementById("sourceHubName");
    
	if (sourceHubName.value.length > 0 && sourceHub.value.length > 0) {
		/*var loc = "/tcmIS/hub/addhubbin.do?branchPlant=" + sourceHub.value
				+ "&sourceHubName=" + sourceHubName.value
				+ "&userAction=addNewBin";*/
		var loc = "addhubbin.do?branchPlant=" + sourceHub.value
					+ "&sourceHubName=" + escape(sourceHubName.value)
					+ "&userAction=addNewBin";
		
		if ($v("personnelCompanyId") == 'Radian') 
			  loc = "/tcmIS/hub/" + loc;
		
		try {
			children[children.length] = openWinGeneric(loc, "addnewBin", "600",
					"200", "no", "80", "80");
		} catch (ex) {
			openWinGeneric(loc, "addnewBin", "600", "200", "no", "80", "80");
		}
	}
	else
	{
		alert("Please Select Hub")
	}
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 document.getElementById("userAction").value = 'search'; 
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

function createXSL()
{
  
      document.getElementById("userAction").value = "createExcel"; 
		document.genericForm.target='_ReceivingQcExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReceivingQcExcel','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	
}

