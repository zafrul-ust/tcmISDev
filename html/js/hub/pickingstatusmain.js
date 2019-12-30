
function buildDropDown(arr, defaultObj, eleName) {
	var obj = $(eleName);
	for ( var i = obj.length; i > 0; i--)
		obj[i] = null;
	// if size = 1 or 2 show last one, first one is all, second one is the
	// only choice.
	if (arr == null || arr.length == 0)
		setOption(0, defaultObj.name, defaultObj.id, eleName);
	else if (arr.length == 1)
		setOption(0, arr[0].name, arr[0].id, eleName);
	else {
		var start = 0;
		if (defaultObj.nodefault)
			start = 0; // will do something??
		else {
			setOption(0, defaultObj.name, defaultObj.id, eleName);
			start = 1;
		}
		for ( var i = 0; i < arr.length; i++) {
			setOption(start++, arr[i].name, arr[i].id, eleName);
		}
	}
	obj.selectedIndex = 0;
}


function hubChanged() {	
	var pickingGroup = document.getElementById("pickingGroupId");
	  
	var arr = null;
	var opsO = $("opsEntityId");
	var hubO = $("sourceHub");
	$("facility").value = hubO.options[hubO.selectedIndex].text;
	buildPickingGroupDropDown(pickingGroups, opsO, hubO);
}



function buildDropDownNew( arr, defaultObj, eleName ) {
	   var obj = $(eleName);
	   for (var i = obj.length; i > 0;i--)
	     obj[i] = null;
	  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
	  if( arr == null || arr.length == 0 ) 
		  setOption(0,"","", eleName); 
	  else if( arr.length == 1 )
		  setOption(0,arr[0].name,arr[0].id, eleName);
	  else {
	      var start = 0;	  	  
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(start++,arr[i].name,arr[i].id,eleName);
		  }
	  }
	  obj.selectedIndex = 0;
	}

function validatePickingForm() {
	if( $v('pickingState').length == 0 ) {
		if( $v('searchText').length == 0 ) {
			alert( messagesData.selectpickingstate );
			return false;
		}
	}
  return true;      
}


function generatePickListExcel() {
	  var flag = validatePickingForm();
	  if(flag) {
	    var action = document.getElementById("action");
	    action.value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_PickingPickListGenerateExcel','650','600','yes');
	    document.genericForm.target='_PickingPickListGenerateExcel';
	    var a = window.setTimeout("document.genericForm.submit();",500);
	  }
	}


function submitSearchForm() {
	 /*Make sure to not set the target of the form to anything other than resultFrame*/
	 var flag = validatePickingForm();
	 var now = new Date();
	 document.getElementById("startSearchTime").value = now.getTime();   
	  if(flag) {
	// for auto resubmit search..
	   var action = document.getElementById("uAction");
	   action.value = "search";
	   document.genericForm.target='resultFrame';	   
	   showPleaseWait();	  
	   return true;
	  }
	  else
	  return false; 
	}



	
	function buildPickingGroupDropDown(pgArray, opsO, hubO)
	{
		for (x in pgArray) {
			pgHub = pgArray[x];
			if ($v("sourceHub").length == 0) {
				buildDropDownNew(pgArray[0].groups,null,"pickingGroupId");
			}
			else if (pgHub.hub == $v("sourceHub")) {
				buildDropDownNew(pgHub.groups,null,"pickingGroupId");
			}
		}
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
        }
        hubChanged();
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
       if( defaults.ops.callback ) defaults.ops.callback();
    }
    
    function getSelectedSearchWhat() {
    	if ($("searchWhat").selectedIndex < 4)
    		$("selectedSearchWhat").value = $v("searchWhat");
    	else
    		$("selectedSearchWhat").value = '';
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

    	if ($v("selectedSearchWhat") == '')
    		obj.selectedIndex = 0;
    	// obj.selectedIndex = 0;
    }
    
    function setDefault() {
    	if ($v("selectedOpsEntityId").length > 0) {
    		$("opsEntityId").value = $v("selectedOpsEntityId");
    		opsChanged();
    	}

    	if ($v("selectedHub").length > 0) {
    		$("sourceHub").value = $v("selectedHub");
    		hubChanged();
    	}
    }