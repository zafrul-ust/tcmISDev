
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

function hubValChanged() {	
	  var inventoryGroup0 = document.getElementById("inventoryGroup");
	  var facility0 = document.getElementById("facilityId");	  
	  
	  var arr = null;
	  var opsO = $("opsEntityId");
	   var hubO = $("hub");
	   if( opsO.value != '' && hubO.value != '' ) {
	   	   for(i=0;i< opshubig.length;i++)
	   	   		if( opshubig[i].id == opsO.value ) {
			   	   for(j=0;j< opshubig[i].coll.length;j++)
		   	   		if( opshubig[i].coll[j].id == hubO.value ) {
						setHubAdditionalData(opshubig[i].coll[j].autoPutHub);
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

function setHubAdditionalData(automatedPutAway) {
	$("isWmsHub").value = automatedPutAway;
	if(automatedPutAway == 'Y')
		$("regeneratePicklist").style["display"]="none";
	else
		$("regeneratePicklist").style["display"]="";
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
   buildCsrDropDown(csrArray);
   
   //inventoryGroupChanged();
}

function showNotes(fieldid)
{
   var section = 'div' + fieldid;
   var pgphBlock = 'pgph' + fieldid;
   var current = (document.getElementById(section).style.display == 'block') ? 'none' : 'block';
   document.getElementById(section).style.display = current;
   document.getElementById(pgphBlock).innerHTML = (current == 'block') ? '<i>-</i>' : '<i>+</i>';   
}




function checkall(checkbx, formname, checkboxname, headername)
{
    // *note: requires TOTAL_ROWS to be defined prior to the inclusion of this

    var totallines = TOTAL_ROWS;

    var result;
    var valueq;
    if (checkbx.checked)
    {
        result = true;
        valueq = "yes";
    }
    else
    {
        result = false;
        valueq = "no";
    }

    // first do rows 
    for ( var p = 0; p <= totallines; p++ )
    {
        try
        {            
            var chkname = 'document.' + formname + '.' + checkboxname+p;
            var linecheck = eval(chkname);

            linecheck.checked =result;
            linecheck.value = valueq;
        }
        catch (ex1)
        {

        }
    }

    // next do headers
    for ( p = 1; p <= totallines; p++ )
    {
        try
        {
            var chkname = 'document.' + formname + '.' + headername+p;
            var linecheck = eval(chkname);

            linecheck.checked =result;
            linecheck.value = valueq;
        }
        catch (ex1)
        {

        }
    }
}

function validatePickingForm() {
	
 var searchField = document.getElementById("searchField");
  if ((searchField.value.trim().length != 0 ) && ( searchField.value == "prNumber"    ) )			
  {
   if(!isFloat(document.genericForm.searchArgument.value.trim(), true)) {
     alert(messagesData.validvalues+"\n\n"+messagesData.mr);
     return false;
   }  
  }
  if(!isSignedInteger(document.genericForm.expireDays.value.trim(), true)) {
    alert(messagesData.expireDaysInteger);
    return false;
  }
  return true;      
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
 
function doPrintBolShort()
{
    var testurl3 = "/tcmIS/Hub/PickingQC?session=Active&generate_bols=yes&UserAction=generatebols";
    var paperSize  =  window.document.picking.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}

function doPrintBolLong()
{
    var testurl3 = "/tcmIS/Hub/PickingQC?session=Active&generate_bols=yes&UserAction=generatebols";
    var paperSize  =  window.document.picking.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}

function doPrintCons()
{

}

function doPrintbox()
{
    var testurl3 = "/tcmIS/hub/reprintboxlbls?";
    HubNoforlabel = document.getElementById("HubName");
    testurl3 = testurl3 + "HubNoforlabel=" + HubNoforlabel.value ;
    openWinGeneric(testurl3,"Generate_Boxlabels","640","600","yes")
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
	   var action = document.getElementById("action");
	   action.value = "search";
	   document.genericForm.target='resultFrame';	   
	   showPleaseWait();	  
	   return true;
	  }
	  else
	  return false; 
	}


function doregenerate() {
	  //var loc = "/tcmIS/hub/openpicklistmain.do?fromPickingPicklist=Y&hub="+$("hub").value+"&inventoryGroup="+$("inventoryGroup").value+"&sortBy="+$("sortBy").value+"&opsEntityId="+$("opsEntityId").value;
	  var loc = "tabletpickableunitlistmain.do?fromPickingPicklist=Y&hub="+$("hub").value+"&inventoryGroup="+$("inventoryGroup").value+"&opsEntityId="+$("opsEntityId").value;
	  
	  if ($v("personnelCompanyId") == 'Radian') 
		  loc = "/tcmIS/hub/" + loc;
	  
	  openWinGeneric(loc,"openpicklistreprint","800","600","yes","80","80");  
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


	
	function buildCsrDropDown( csrArray)
	{ 
	
		var opsEntityId =$v('opsEntityId');
	   if (!opsEntityId || opsEntityId == '')
	   {
	      opsEntityId = $("opsEntityId").value;
	   }
	   
	   var obj = $("customerServiceRepId");
	   for (var i = obj.length; i >= 0;i--)
	     obj[i] = null;
	   
	  	  setOption(0,messagesData.all,'',"customerServiceRepId"); 
	      var offset = 1 ;
		  for ( var i=0; i < csrArray.length; i++) {
			 
		  	if(opsEntityId == csrArray[i].opsEntityId) {
		  		//alert("csrArray[i].opsEntityId:"+csrArray[i].opsEntityId+" opsEntityId:"+opsEntityId);
		    	setOption(offset,csrArray[i].name,csrArray[i].id,"customerServiceRepId");
		    	offset++;	
		    }      
		  }
	//  }
	  obj.selectedIndex = 0;
	}

	function setInitialFacility()
	{
		setOption(0,messagesData.all,"", "facilityId");
	}
	
	function lookupCustomer() {    
		  var loc = "customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
		  
		  if ($v("personnelCompanyId") == 'Radian') 
			  loc = "../distribution/" + loc;
		  
		  openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
    }

    function clearCustomer()
    {
        document.getElementById("customerName").value = "";
        document.getElementById("customerName").setAttribute("className", "inputBox");
        document.getElementById("customerId").value = "";
    }
    
    function customerChanged(id, name) {
    	document.getElementById("customerName").className = "inputBox";
    }
