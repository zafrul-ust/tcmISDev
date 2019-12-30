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


function validateSearchForm() {
    var result = true;
    if ($("beginDateJsp").value == '') {
        alert(messagesData.validvalues+" "+messagesData.qcedDate)
        result = false;
    }
    return result;
}

function submitSearch()
{
    //set start search time
    var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    if(validateSearchForm()) {
        showPleaseWait();
        document.genericForm.target='resultFrame';
        document.getElementById("userAction").value = 'search';
        document.genericForm.submit();
    }
}

function createExcel()
{
    if (validateSearchForm()) {
        var tmpOpsEntity = $('opsEntityId');
        $('opsEntityName').value = tmpOpsEntity.options[tmpOpsEntity.selectedIndex].text;
        var tmpHub = $('sourceHub');
        $('sourceHubName').value = tmpHub.options[tmpHub.selectedIndex].text;
        var tmpInventory = document.getElementById('inventoryGroup');
        $('inventoryGroupName').value = tmpInventory.options[tmpInventory.selectedIndex].text;
        document.getElementById("userAction").value = "createExcel";
        document.genericForm.target='_ReceivingQcExcel';
        openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReceivingQcExcel','650','600','yes');
        setTimeout("document.genericForm.submit()",300);
    }
}

