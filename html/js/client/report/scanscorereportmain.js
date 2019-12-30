
function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(validateSearchForm()) { 
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
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
	return true;
}

function createXSL(){
	  
	  if(validateSearchForm()) {
		$('uAction').value = 'createExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_Cabinet_Turns_Excel','650','600','yes');
	    document.genericForm.target='_Cabinet_Turns_Excel';
	    var a = window.setTimeout("document.genericForm.submit();",50);
	  }
}

function buildDropDown( arr, eleName ) {
   var obj = $(eleName);
   for (var i = obj.length; i > 0;i--)
     obj[i] = null;
  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
  if( arr == null || arr.length == 0 ) 
	  setOption(0,messagesData.all,"", eleName); 
  else if( arr.length == 1 )
	  setOption(0,arr[0].name,arr[0].id, eleName);
  else {
      var start = 1;
  	  setOption(0,messagesData.all,"", eleName); 
  	  
	  for ( var i=0; i < arr.length; i++) {
	    	setOption(start++,arr[i].name,arr[i].id,eleName);
	  }
  }
  obj.selectedIndex = 0;
}

function loadOptions() {
 	buildDropDown(divFacGrpFacReApp,"divisionId");
    
    divisionChanged();
}

function divisionChanged()
{  
   var divisionId = $("divisionId");
   var arr = null;
   if( divisionId.value != '' ) {
   	   for(i=0;i< divFacGrpFacReApp.length;i++)
   	   		if( divFacGrpFacReApp[i].id == divisionId.value ) {
   	   			arr = divFacGrpFacReApp[i].coll;
   	   			break;
   	   		}
   }

   buildDropDown(arr,"facilityGroupId");
   facilityGroupChanged();
}

function facilityGroupChanged()
{
   var divisionId = $("divisionId");
   var facilityGroupId = $("facilityGroupId");
   var arr = null;
   if( divisionId.value != '' && facilityGroupId.value != '' ) {
   	   for(i=0;i< divFacGrpFacReApp.length;i++)
   	   		if( divFacGrpFacReApp[i].id == divisionId.value ) {
		   	   for(j=0;j< divFacGrpFacReApp[i].coll.length;j++)
	   	   		if( divFacGrpFacReApp[i].coll[j].id == facilityGroupId.value ) {
	   	   			arr = divFacGrpFacReApp[i].coll[j].coll;
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   buildDropDown(arr, "facilityId");
   facilityChanged();
}

function facilityChanged()
{
   var divisionId = $("divisionId");
   var facilityGroupId = $("facilityGroupId");
   var facilityId = $("facilityId");
   var arr = null;
   if( divisionId.value != '' && facilityGroupId.value != '' && facilityId.value != '' ) {
   	   for(i=0;i< divFacGrpFacReApp.length;i++)
   	   		if( divFacGrpFacReApp[i].id == divisionId.value ) {
		   	   for(j=0;j< divFacGrpFacReApp[i].coll.length;j++)
	   	   		if( divFacGrpFacReApp[i].coll[j].id == facilityGroupId.value ) {
	   	   			for(k=0;k< divFacGrpFacReApp[i].coll[j].coll.length;k++)
		   	   		if( divFacGrpFacReApp[i].coll[j].coll[k].id == facilityId.value ) {
		   	   			arr = divFacGrpFacReApp[i].coll[j].coll[k].coll;
		   	   			break;
	   	   		    }
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   buildDropDown(arr, "reportingEntityId");
   reportingEntityIdChanged();
}

function reportingEntityIdChanged()
{
   var divisionId = $("divisionId");
   var facilityGroupId = $("facilityGroupId");
   var facilityId = $("facilityId");
   var reportingEntityId = $("reportingEntityId");
   var arr = null;
   if( divisionId.value != '' && facilityGroupId.value != ''  && facilityId.value != ''  && reportingEntityId.value != '' ) {
   	   for(i=0;i< divFacGrpFacReApp.length;i++)
   	   		if( divFacGrpFacReApp[i].id == divisionId.value ) {
		   	   for(j=0;j< divFacGrpFacReApp[i].coll.length;j++)
	   	   		if( divFacGrpFacReApp[i].coll[j].id == facilityGroupId.value ) {
	   	   			for(k=0;k< divFacGrpFacReApp[i].coll[j].coll.length;k++)
		   	   		if( divFacGrpFacReApp[i].coll[j].coll[k].id == facilityId.value ) {
		   	   			for(l=0;l< divFacGrpFacReApp[i].coll[j].coll[k].coll.length;l++)
				   	   		if( divFacGrpFacReApp[i].coll[j].coll[k].coll[l].id == reportingEntityId.value ) {
				   	   			arr = divFacGrpFacReApp[i].coll[j].coll[k].coll[l].coll;
				   	   			break;
			   	   		    }
		   	   			break;
	   	   		    }
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   buildDropDown(arr, "application");
}







