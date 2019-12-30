var  sourceHubName = null;
var  sourceHub = null;


function submitSearchForm()
{
	/*Make sure to not set the target of the form to anything other than resultFrame*/
	 document.genericForm.target='resultFrame';
	 document.getElementById("action").value = 'search';
	      //set start search time
	 var now = new Date(); 
	 var startSearchTime = document.getElementById("startSearchTime");
	 startSearchTime.value = now.getTime();
	 var isValidSearchForm = validateSearchForm();
	  if(isValidSearchForm) 
	  {
	   showPleaseWait();
	   return true;
	  }
	  else
	  {
	    return false;
	  }
	
 
}

function validateSearchForm()
{
	var roomSelect = $("room");
	if (roomSelect.options.length > 1 && roomSelect.value.length == 0) {
		alert(messagesData.selectaroom);
		return false;
	}
	return true;
}

function setDropDowns()
{
	setOps();
    $('showActiveOnly').checked=true;
    $('sourceHubName').value =  $('hub').options[$('hub').selectedIndex].text;
    $('branchPlant').value = $v('hub');
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
}

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


/*
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
	   	   			arr = opshubig[i].coll[j].coll;
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   buildDropDown(arr,defaults.inv,"inventoryGroup");
   if( defaults.hub.callback ) defaults.hub.callback();

}
*/

function buildDropDown( arr, defaultObj, eleName ) {
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

function hubChanged() 
{

  var hubO = document.getElementById("hub");
  var roomO = document.getElementById("room");
  var selectedHub = hubO.value;

  for (var i = roomO.length; i > 0; i--) 
  {
    roomO[i] = null;
  }
  showRoomOptions(selectedHub);
  roomO.selectedIndex = 0;
  $('sourceHubName').value =  $('hub').options[$('hub').selectedIndex].text;
  $('branchPlant').value = $v('hub');
}


function showRoomOptions(selectedHub) 
{
  var roomArray = new Array();
  roomArray = altRoomId[selectedHub];

  var roomNameArray = new Array();
  roomNameArray = altRoomName[selectedHub];

  if(roomArray != null && roomArray.length == 0) 
  {
    setOption(0, messagesData.none, "", "room")
  }
    
  if (roomArray != null)
  {
  for (var i=0; i < roomArray.length; i++) 
  {
    setOption(i,roomNameArray[i],roomArray[i], "room")
  }
  }
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
}

function setDefaults() {
	if ($v("selectedHub").length > 0) {
		$("opsEntityId").value = $v("selectedOpsEntityId");
		opsChanged();
	}
	if ($v("selectedHub").length > 0) {
		$("sourceHub").value = $v("selectedHub");
		hubChanged();
	}
}


