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
   /* if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0 && defaultHub != null && defaultHub.length > 0 && preferredInventoryGroup != null && preferredInventoryGroup.length > 0)
    	$('inventoryGroup').value = preferredInventoryGroup;*/
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

function hubChanged() {	
	var pickingGroup = document.getElementById("pickingGroupId");
	  
	var arr = null;
	var opsO = $("opsEntityId");
	var hubO = $("sourceHub");
	//buildPickingGroupDropDown(pickingGroups, opsO, hubO);
}

function submitSearchForm() {
	var now = new Date();
	$("startSearchTime").value = now.getTime(); 
	$("uAction").value = "search";
	document.genericForm.submit();
}

function showLegend()
{
  var showLegendArea = document.getElementById("showLegendArea");
  showLegendArea.style.display="";

  var dhxWins = new dhtmlXWindows();
  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
  if (!dhxWins.window(messagesData.showlegend)) {
  // create window first time
  var legendWin = dhxWins.createWindow(messagesData.showlegend, 0, 0, 400, 64);
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