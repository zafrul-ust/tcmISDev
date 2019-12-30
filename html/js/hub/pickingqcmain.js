var children = new Array();
/*The reason this is here because you might want a different width/proprties for the pop-up div depending on the page*/

function setDropDowns()
{
	buildDropDownNew(opshubig,'',"opsEntityId");	
 	$('opsEntityId').onchange =opsValChanged;
    $('hub').onchange = hubValChanged;
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
    	$('opsEntityId').value = defaultOpsEntityId;
    }
    opsValChanged();
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0) {
    	$('hub').value = defaultHub;
    	hubValChanged();
    }
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0 && preferredInventoryGroup != null && preferredInventoryGroup.length > 0)
    	$('inventoryGroup').value = preferredInventoryGroup;
    	
    $('inventoryGroup').onchange = inventoryGroupChanged;	
    setInitialFacility();
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

function opsValChanged()
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
   if( opsO.value != '' )
   {	   
   buildDropDownNew(arr,null,"hub");
   }
   else
   {	   
   buildDropDown(arr,defaults.hub,"hub");
   }
   hubValChanged();
   if( defaults.ops.callback ) defaults.ops.callback();
}

function hubValChanged() {
	  var inventoryGroup0 = document.getElementById("inventoryGroup");
	  var facility0 = document.getElementById("facilityId");	  
	  
	  var arr = null;
	  var opsO = $("opsEntityId");
	   var hubO = $("hub");
	   getPickLists();
	   if( opsO.value != '' && hubO.value != '' ) {
	   	   for(i=0;i< opshubig.length;i++)
	   	   		if( opshubig[i].id == opsO.value ) {
			   	   for(j=0;j< opshubig[i].coll.length;j++)
		   	   		if( opshubig[i].coll[j].id == hubO.value ) {
						$("isWmsHub").value = opshubig[i].coll[j].autoPutHub;
		   	   			arr = opshubig[i].coll[j].coll;
		   	   			break;
	   	   		    }
	   	   		 break;
	   	   }
	   }
	  
	  for (var i = inventoryGroup0.length; i > 0; i--) {
	    inventoryGroup0[i] = null;
	  }

	  for (var i = facility0.length; i > 0; i--) {
	    facility0[i] = null;
	  }

	  buildDropDown(arr,defaults.inv,"inventoryGroup");
	  if( defaults.hub.callback ) defaults.hub.callback();

	  setInitialFacility();
}

function setInitialFacility()
{
	setOption(0,messagesData.all,"", "facilityId");
}


function showFacilityIdOptions(selectedInventoryGroup) {
	
	  var facilityIdArray = new Array();
	  facilityIdArray = altFacilityId[selectedInventoryGroup];
	  var facilityNameArray = new Array();
	  facilityNameArray = altFacilityName[selectedInventoryGroup];
	  
	  
	  if(facilityIdArray == null || facilityIdArray.length == 0) {
	    setOption(0,messagesData.all,"", "facilityId")
	  }
	  else {
	    for (var i=0; i < facilityIdArray.length; i++) {
          setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId");
	    }
	  }
	}

function inventoryGroupChanged() {
	  var inventoryGroup0 = document.getElementById("inventoryGroup");
	  var facilityId0 = document.getElementById("facilityId");
	  var selectedInventoryGroup = inventoryGroup0.value;
	  if(facilityId0 != null) {
	    for (var i = facilityId0.length; i > 0; i--) {
	      facilityId0[i] = null;
	    }
	  }
	  showFacilityIdOptions(selectedInventoryGroup);
	 // facilityId0.selectedIndex = 0;
	}

function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}

function submitUpdate() 
{
    /*Set any variables you want to send to the server*/
    var action = window.frames["resultFrame"].document.getElementById("action");
    action.value = 'update';
    showPleaseWait();
    /*Submit the form in the result frame*/
    window.frames["resultFrame"].document.genericForm.submit();
}
/*
function showpickingpagelegend()
{
 	children[children.length] = openWinGeneric("/tcmIS/help/pickingpagelegend.html","pickingpagelegend","290","300","no","50","50")
}
*/
function generateExcel() 
{
    if(! validatePickingQcForm() ) 
    {
        return false;
    }
    else{
		 var action = document.getElementById("userAction");
		 action.value = 'createExcel';
		
		 children[children.length] = openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_PickingQCGenerateExcel','650','600','yes');
		 document.genericForm.target='_PickingQCGenerateExcel';
		 var a = window.setTimeout("document.genericForm.submit();",500);
    }
}

function submitSearchForm()
{
	var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
    if(! validatePickingQcForm() ) 
    {
        return false;
    }
    document.genericForm.target='resultFrame';

    var userAction = document.getElementById("userAction");
    userAction.value = 'search';
    var hub = $('hub');
    $('selectedHubName').value = hub.options[hub.selectedIndex].text;
    document.genericForm.submit();
    showPleaseWait();
    return true;

}

function validatePickingQcForm() 
{
  if(document.genericForm.hub.value == "") 
  {
	alert(messagesData.pleaseSelectAHub);
	return false;
  }
  if(!isFloat(document.genericForm.prNumber.value.trim(), true)) 
  {
    alert(messagesData.errorfloat.replace('{0}',messagesData.mrnumber));
    return false;
  }
  if(!isInteger(document.genericForm.picklistId.value.trim(), true)) 
  {
    alert(messagesData.picklistIdInteger);
    return false;
  }
  return true;
}
//the following 4 functions came from hub/allocationanalysis.js...


function getPickLists()
{
	var url ="pickingqcmain.do?callback=popPickList&uAction=popPickList&hub=" + $("hub").value;
	callToServer(url);
}

function popPickList(pickListArray)
{
	pickLists[$("hub").value] = pickListArray;
	showPicklistOptions($("hub").value);

}


function showFacilityOptions(txt) 
{
  var facilityId0 = document.getElementById("facilityId");
  if(facilityId0 != null) 
  {
    for (var i = facilityId0.length; i > 0; i--) 
    {
      facilityId0[i] = null;
    }
  }

  var facilityArray = facs[txt];
  setOption(0,"All","", "facilityId");
  if(facilityArray != null) 
  {
	  for (var i=0; i < facilityArray.length; i++) 
	  {
	     setOption(i+1,facilityArray[i].facilityName,facilityArray[i].facilityId, "facilityId")
	  }
  }
}

function showPicklistOptions(txt) 
{
	if(txt != "")
	{ 
	  var picklistId0 = document.getElementById("picklistId");
	  if(picklistId0 != null) 
	  {
	    for (var i = picklistId0.length; i > 0; i--) 
	    {
	    	picklistId0[i] = null;
	    }
	  }
	
	  var pickListsArray = pickLists[txt];
	  setOption(0,"All","", "picklistId");
	  if(pickListsArray != null)
		  for (var i=0; i < pickListsArray.length; i++) 
		  {
		     setOption(i+1,pickListsArray[i].picklistName,pickListsArray[i].picklistId, "picklistId")
		  }
	}
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
		children = new Array();
}

function showpickingpagelegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows()
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 350);
  legendWin.setText(messagesData.showlegend);
  legendWin.clearIcon();  // hide icon
  legendWin.denyResize(); // deny resizing
  legendWin.denyPark();   // deny parking
  legendWin.attachObject("showLegendArea");
  legendWin.attachEvent("onClose", function(legendWin){legendWin.hide();});
  legendWin.center();
  }
  else
  {
    // just show
    dhxWins.window("legendwin").show();
  }
}


