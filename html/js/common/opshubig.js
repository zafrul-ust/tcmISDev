var uploadMinMaxLevelPermission = false;

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
    $('hub').onchange = hubChanged;	
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
    	$('opsEntityId').value = defaultOpsEntityId;
    }
    opsChanged();
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0) {
    	$('hub').value = defaultHub;
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

   buildDropDown(arr,defaults.hub,"hub");
   hubChanged();
   if( defaults.ops.callback ) defaults.ops.callback();
   
   if (uploadMinMaxLevelPermission) checkOpsEntityPermission(opsO.options[opsO.selectedIndex].value);
   
}

function hubChanged()
{
   var opsO = $("opsEntityId");
   var hubO = $("hub");
   var arr = null;   
   if( opsO.value != '' && hubO.value != '' ) {
   	   for(i=0;i< opshubig.length;i++)
   	   		if( opshubig[i].id == opsO.value ) {
		   	   for(j=0;j< opshubig[i].coll.length;j++)
	   	   		if( opshubig[i].coll[j].id == hubO.value ) {
	   	   		    var isWmsHub = $("isWmsHub");
	   	   		    if (isWmsHub)
                        isWmsHub.value = opshubig[i].coll[j].autoPutHub;
	   	   			arr = opshubig[i].coll[j].coll;
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   buildDropDown(arr,defaults.inv,"inventoryGroup");
   if( defaults.hub.callback ) defaults.hub.callback();   
}

