function myOnload() {
    //setSearchFrameSize();
    facilityChanged();
}

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
		  if( nameArray.length == 2){document.getElementById("createNewGroup").style.display = "";}
    	if( nameArray.length != 2 || i == 1 ) {
	    	setOption(inserted,nameArray[i],idArray[i], "facilityId");
    		if( selectedInv == idArray[i] ) 
    			selectI = inserted;
    		inserted++;
    	}
  	  }
  	if( inserted == 0 )
    	setOption(inserted,messagesData.selectOne,"", "facilityId");
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
 var oldCompany = "";
 var oldFacility = "";
 try {
 showCompany(oldCompany);
 showFacility($("companyId").value,oldFacility);
 } catch (ex) {}
}

function CompanyChanged() {

  var Company = $("companyId");
  var selectedCompany = Company.value;
  showFacility(selectedCompany,null);
}

function showErrorMessages()
{
		parent.showErrorMessages();
}

function submitSearchData() {
	/*Make sure to not set the target of the form to anything other than resultFrame*/
 	document.genericForm.target='resultFrame';
	var flag = validateForm();
	if(flag) {
		if (document.getElementById('viewTypeMy').checked) {
			document.getElementById("uAction").value = 'search';
			document.getElementById("resultView").value = 'resultTable';
		}else {
			document.getElementById("uAction").value = 'searchTree';
			document.getElementById("resultView").value = 'resultTree';
		}
		parent.showPleaseWait();
		var now = new Date();
		var startSearchTime = document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();
		return true;
	}else {
    return false;
  }
}

function reSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 var action = document.getElementById("uAction");
 var resultView = document.getElementById("resultView");
 if ("resultTree" == resultView.value) {
   action.value = 'searchTree';
 }else {
   action.value = 'search';
 }
 var flag = validateForm();
  if(flag) {
	var now = new Date();
	parent.document.getElementById("startSearchTime").value = now.getTime();
	parent.showPleaseWait();
   document.genericForm.submit()
  }
}

function validateForm() {
	var result = true;
	var facilityId  =  document.getElementById("facilityId");
	if (facilityId.value.length == 0 || facilityId.value == "Select One") {
		alert(messagesData.selectFacility);
		result = false;
	}
    return result;
}

function editFacilityListApproval(){
    var flag = validateForm();
    if (flag) {
        var facilityId  =  document.getElementById("facilityId");
        var facilityName = facilityId.options[facilityId.selectedIndex].text;
        var companyId  =  document.getElementById("companyId");
    	var now = new Date();
        var loc = "editfacilitylistapproval.do?action=search&" +
        		"companyId="+companyId.value +
        		"&facilityId="+facilityId.value +
        		"&facilityName="+facilityName;
        openWinGeneric(loc,"editfacilitylistapproval123","950","600","yes","80","80","no")
    }
}

function facilityChanged() {
  var facilityId  =  document.getElementById("facilityId").value;
  if(facilityId != "") {
    document.getElementById("createNewGroup").style.display = "";
  }else {
    document.getElementById("createNewGroup").style.display = "none";
  }
}

function createXSL() {
	var flag = validateForm();
   if (flag) {
		var facilityId  =  document.getElementById("facilityId");
		document.getElementById('facilityName').value=facilityId.options[facilityId.selectedIndex].text;
		if (document.getElementById('companySize').value > 1 ) {
			var companyId  =  document.getElementById("companyId");
			document.getElementById('companyName').value=companyId.options[companyId.selectedIndex].text;
		}else {
			document.getElementById('companyName').value=document.getElementById('companyId').value;
		}
		document.getElementById('uAction').value="createXSL";
		if (document.getElementById('viewTypeMy').checked) {
			document.getElementById('displayView').value="tableView";
		}else {
			document.getElementById('displayView').value="treeView";
		}
		document.genericForm.target='_excel_report_file';
	    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	 }
}