var windowCloseOnEsc = true;

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
	  for (var i=1; i < nameArray.length; i++) {
	    	setOption(inserted,nameArray[i],idArray[i], "facilityId");
    		if( selectedInv == idArray[i] ) 
    			selectI = inserted;
    		inserted++;
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

	  for (var i=1; i < nameArray.length; i++) {
    		setOption(inserted,nameArray[i],idArray[i], "companyId");
    		if( oldCompany == idArray[i] ) 
    			selectI = inserted;
    		inserted++;
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
   	document.genericForm.target='resultFrame';
	var fac = document.getElementById('facilityId');   	
	document.getElementById('facilityName').value = fac.options[fac.selectedIndex].text;
   	parent.showPleaseWait();
	document.genericForm.submit();
  	return true;
}

function createXSL() {
	document.getElementById('uAction').value="createXSL";
	document.genericForm.target='_excel_report_file';
    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
	setTimeout("document.genericForm.submit()",300);
}

function showErrorMessages()
{
		parent.showErrorMessages();
}

function CurrentIndex() {
	if( currentIndex == -1 ) {
		alert(messagesData.pleaseselectapprover);
		return -1;
	}
	return currentIndex;
}

function Add() {
	if( currentIndex !=-1 )
	 openWinGeneric("financialapproverresults.do?personnelId="+document.getElementById('personnelId').value+"&uAction=Add&userId="+document.getElementById('userId').value
	 	 				+ "&companyId="+encodeURIComponent(document.getElementById('companyId').value)
		   				+ "&facilityId="+encodeURIComponent(document.getElementById('facilityId').value)
		   				+ "&currencyId="+objs[currentIndex].currencyId
	                  ,"financialapprovereadd","500","300","yes","75","75","no");
	else
	 openWinGeneric("financialapproverresults.do?personnelId=&uAction=Add&userId="+document.getElementById('userId').value
	 	 				+ "&companyId="+encodeURIComponent(document.getElementById('companyId').value)
		   				+ "&facilityId="+encodeURIComponent(document.getElementById('facilityId').value)
		   				+ "&currencyId=USD"
	                  ,"financialapprovereadd","500","300","yes","75","75","no");
}

function Edit() {
	if( CurrentIndex() !=-1 )
	 openWinGeneric("financialapproverresults.do?personnelId="+document.getElementById('personnelId').value+"&uAction=Edit&userId="+document.getElementById('userId').value
	 	 				+ "&companyId="+encodeURIComponent(document.getElementById('companyId').value)
		   				+ "&facilityId="+encodeURIComponent(document.getElementById('facilityId').value)
		   				+ "&currencyId="+objs[currentIndex].currencyId
	                  ,"financialapproveredit","500","300","yes","75","75","no");
}

function Delete() {
	if( CurrentIndex() !=-1 ){	
		getSearchFrame().expandedNode = objs[currentIndex].approverId;
		if( approverChild[objs[currentIndex].personnelId] != null ) {
		 	openWinGeneric("financialapproverresults.do?personnelId="+document.getElementById('personnelId').value+"&uAction=Delete&userId="+document.getElementById('userId').value
	 	 				+ "&companyId="+encodeURIComponent(document.getElementById('companyId').value)
		   				+ "&facilityId="+encodeURIComponent(document.getElementById('facilityId').value)
		   				+ "&approverName=Node"
		   				+ "&currencyId="+objs[currentIndex].currencyId
	                  	,"financialapprovereadd","500","300","yes","75","75","no");
			return;	
		}
		document.getElementById('uAction').value = "Delete";
	    document.getElementById('personnelId').value = objs[currentIndex].personnelId;
	    document.getElementById('approverId').value  = objs[currentIndex].approverId;
	    document.getElementById('costLimit').value = 0;
	    document.getElementById('approvalLimit').value  = 0;
	    
		parent.showPleaseWait();
		document.genericForm.submit();
	}
}

function replaceNodeWait(Old,New) {
	setTimeout('replaceNode('+Old+','+New+')',300);
}
function replaceNode(Old,New) {
		getSearchFrame().expandedNode = New ;
		document.getElementById('uAction').value = "Delete";
	    document.getElementById('personnelId').value = Old;
	    document.getElementById('approverId').value  = New;
	    document.getElementById('costLimit').value = 0;
	    document.getElementById('approvalLimit').value  = 0;
		parent.showPleaseWait();
		document.genericForm.submit();
}

function AltApprover() {
	if( CurrentIndex() !=-1 )
	 openWinGeneric("financialapproverresults.do?approver="+objs[currentIndex].personnelId
	 				+ "&companyId="+encodeURIComponent(document.getElementById('companyId').value)
	 				+ "&facilityId="+encodeURIComponent(document.getElementById('facilityId').value)
	 				+ "&userId="+document.getElementById('userId').value
	 				+ "&facilityName="+encodeURIComponent(document.getElementById('facilityName').value)
	 				+ "&userName="+encodeURIComponent(objs[currentIndex].userName)
					 +"&uAction=AltApprover"
	                  ,"financialapproveralt","500","300","yes","75","75","no");
}

function refresh() {
	setTimeout('getSearchFrame().search()',500);
}

function setExpandedNode() {
	var expNode = new Array();
	var tree = objs;
	var cur = getSearchFrame().expandedNode;
	var e = approverChild;
	while( cur != '' ) {
		expNode[cur] = true;
/*
		alert( cur ) ;
		alert( personIndex[ cur ] );
		alert( tree[ personIndex[ cur ] ].approverId );
*/      if( personIndex[ cur ] == null )
			cur = '';
		else
			cur = tree[ personIndex[ cur ] ].approverId;
	} 
	lastExpanded = expNode;
}

function setExpandTo( personnelId ) {
	getSearchFrame().expandedNode = personnelId;
}


