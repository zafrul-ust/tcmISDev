function $(a) {
	return document.getElementById(a);
}

function isArray(testObject) { 
      return testObject && 
             !( testObject.propertyIsEnumerable('length')) && 
             typeof testObject === 'object' && 
             typeof testObject.length === 'number';
}

function showFacility(selectedCompany,selectedInv) {
  var idArray = altFacilityId[selectedCompany];
  var nameArray = altFacilityName[selectedCompany];
  var selectI = 0;
  var inserted = 0 ;
  
  var inv = $("facilityId");
  for (var i = inv.length; i > 0; i--) {
    inv[i] = null;
  }
  if( nameArray != null ) 
	  for (var i=0; i < nameArray.length; i++) {
    	if( nameArray.length != 2 || i == 1 ) {
	    	setOption(inserted,nameArray[i],idArray[i], "facilityId");
    		if( selectedInv == idArray[i] ) 
    			selectI = inserted;
    		inserted++;
    	}
  	  }
  	if( inserted == 0 )
    	setOption(inserted,messagesData.myfacilities,"", "facilityId");
  	$("facilityId").selectedIndex = selectI;
}

function showCompany(oldCompany){
  var idArray = altCompanyId;
  var nameArray = altCompanyName;
  var selectI = 0 ;
  var inserted =0;

	  for (var i=0; i < nameArray.length; i++) {
    	if( nameArray.length != 2 || i != 0 ) {
    		setOption(inserted,nameArray[i],idArray[i], "companyId");
    		if( oldCompany == idArray[i] ) 
    			selectI = inserted;
    		inserted++;
    	}
	  }
  $("companyId").selectedIndex = selectI;
}

function setCompany() {
 var oldCompany =  $("oldcompanyId").value;
 var oldinv =  $("oldfacilityId").value;
 try {
 showCompany(oldCompany);
 showFacility($("companyId").value,oldinv);
 } catch (ex) {}
}

function CompanyChanged() {

  var Company = $("companyId");
  var selectedCompany = Company.value;
  showFacility(selectedCompany,null);
}

function validateForm() {
  return true;
}
function search() {
   	parent.showPleaseWait();
	document.genericForm.submit();
}

function showErrorMessages()
{
		parent.showErrorMessages();
}

function usergroupPermissionsUpdate() {
   	parent.showPleaseWait();
	document.genericForm.submit();
}

function myOnload() {
	parent.document.getElementById('transitPage').style["display"]="none";
	parent.document.getElementById('resultFrameDiv').style["display"]="";
	setResultFrameSize();
	if( showUpdateLinks == true ) {
		parent.document.getElementById('updateResultLink').style["display"]="";
	}
	else {
		parent.document.getElementById('updateResultLink').style["display"]="none";
	}
}

function checkGroupAdmin(i) {
    document.getElementById('applicationAccess_'+i).value = 
    	( document.getElementById('_applicationAccess_'+i).checked )?"1":"0";
}

function search() {
   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
	document.genericForm.submit();
  	return true;
}
