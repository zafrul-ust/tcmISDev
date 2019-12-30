/*
function hubchanged() {alert("there?????");
  var hubIdO = document.getElementById("branchPlant");
  var invGroupIdO = document.getElementById("inventoryGroup");
  var selectedHub = hubIdO.value;

  for (var i = invGroupIdO.length; i > 0;i--) {
    invGroupIdO[i] = null;
  }

  showInventoryGroup(selectedHub);
  invGroupIdO.selectedIndex = 0;
  igchanged();
}  

function showInventoryGroup(selectedHub) {alert("there!!!!!!");
  var inventoryGroupIdArray = new Array();
  inventoryGroupIdArray = altinvid[selectedHub];
  var inventoryGroupNameArray = new Array();
  inventoryGroupNameArray = altinvName[selectedHub];

  for (var i=0; i < inventoryGroupIdArray.length; i++) {
    setOption(i,inventoryGroupNameArray[i],inventoryGroupIdArray[i], "inventoryGroup")
  }
}  
*/
function igchanged(){
	var inventoryGroupO = document.getElementById("inventoryGroup"); 
	var dockIdO = document.getElementById("dockId");
	var selectedInventoryGroup = inventoryGroupO.value;	
	if(inventoryGroupO.value!=='')
	{	 
		for (var i = dockIdO.length; i > 0;i--) {
			dockIdO[i] = null;
		}

		showDockLinks(selectedInventoryGroup);
		dockIdO.selectedIndex = 0;
	}
	else
	{
		for (var i = dockIdO.length; i > 0;i--)
		{	  
			dockIdO[i] = null;
		}
		setOption(0,messagesData.alldocks,"", "dockId");
//		showAllDocks();	
	}
}

function showAllDocks() {
  var count = 0;
  var opsO = $("opsEntityId");
  var hubO = $("hub");

  for(i=0;i< opshubig.length;i++) {
   	   	if( opshubig[i].id == opsO.value ) {
		   	   for(j=0;j< opshubig[i].coll.length;j++)
	   	   		if( opshubig[i].coll[j].id == hubO.value ) {
	   	   			arr = opshubig[i].coll[j].coll;
	   	   			for(k=0;k<arr.length;k++) {
	   	   				  var dockIdArray = new Array();
						  dockIdArray = altDock[arr[k].id];
										
						  var dockDescArray = new Array();
						  dockDescArray = altDockDesc[arr[k].name];
						
						  for (var p=count; p < dockIdArray.length + count; p++) {
						    setDock(p,dockDescArray[p],dockIdArray[p]);
						    count++;
						  }
	   	   			} 
	   	   			break;
   	   		    }
   	   		 break;
   	   	}
  }
}

function showDockLinks(selectedInventoryGroup)
{
  var dockIdArray = new Array();
  dockIdArray = altDock[selectedInventoryGroup];
				
  var dockDescArray = new Array();
  dockDescArray = altDockDesc[selectedInventoryGroup];

  for (var i=0; i < dockIdArray.length; i++) {
    setDock(i,dockDescArray[i],dockIdArray[i])
  }
}

function setDock(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var dockIdO = document.getElementById("dockId");
  dockIdO[href] = optionName;
}

function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
 //set start search time
 var now = new Date();
 var startSearchTime = parent.window.frames["searchFrame"].document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 var flag = validateForm();
  if(flag) {
    parent.showPleaseWait();
    return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {
   var result = true;
   var what = document.getElementById('searchWhat');
   if( what.value != "itemDescription" &&
   	   !isInteger(document.getElementById('searchText').value,true) ) {
   	   alert( what.options[what.selectedIndex].text+ " : "+messagesData.mustBeInteger);
   	   document.getElementById('searchText').focus();
   	   result = false;
   }
   
   //var expectedWithin = document.getElementById('expectedWithin');
   if( !isSignedInteger(document.getElementById('expectedWithin').value,true) ) {
   	   alert( messagesData.exptdwithin+ " : "+messagesData.mustBeInteger);
   	   document.getElementById('expectedWithin').focus();
   	   result = false;
   }
   return result;
}
function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    //var hubId = document.getElementById("facilityId");

	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReceivingReportGenerateExcel','650','600','yes');
    document.genericForm.target='_ReceivingReportGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function attachInvGroupUpdate()
{
	defaults.hub.callback = igchanged;
}