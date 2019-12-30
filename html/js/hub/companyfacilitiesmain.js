var windowCloseOnEsc = true;
var returnSelectedValue = null;
var returnSelectedId=null;

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
    	if( nameArray.length != 2 || i != 0 ) {
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

  if( nameArray.length == 1 ) 
  	setOption(0,nameArray[0],idArray[0],"companyId");
  else
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

function selectedRow()
{ 
	var valueElementId = document.getElementById("valueElementId").value;
	var displayElementId = document.getElementById("displayElementId").value;
	try {
	  var openervalueElementId = opener.document.getElementById(valueElementId);
	   openervalueElementId.value = returnSelectedId;
	  var openerdisplayElementId = opener.document.getElementById(displayElementId);
	  openerdisplayElementId.value = returnSelectedValue;
  } catch( ex ) {}
  
   window.close();
   returnSelectedValue = null;
  returnSelectedId=null;
  valueElementId=null;
  displayElementId=null;
 }


function submitSearchForm()
{	
	document.genericForm.target='resultFrame';
	$('uAction').value  ="search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	if(fromNewPrinter != '')
	{
	  var flag = validateForm();
	}
	else
	{
		document.genericForm.submit();
		showPleaseWait();
	}
	if(flag) 
  	{
		showPleaseWait();
   		return true;
  	}
  	else
  	{
    	return false;
  	}
}


function createXSL() {
	document.getElementById('uAction').value="createXSL";
try {
	var comObj = document.getElementById("companyId");
	document.getElementById("companyName").value=
		comObj[comObj.selectedIndex].text;
} 
catch (ex){}
	document.genericForm.target='_excel_report_file';
//	the regular result frame use following commented line
//	document.genericForm.target='resultFrame';
    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
	setTimeout("document.genericForm.submit()",300);
}
