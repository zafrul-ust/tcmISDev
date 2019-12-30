function selectedRow()
{ 
  try {
  var openervalueElementId = opener.document.getElementById(valueElementId);
  openervalueElementId.value = returnSelectedId;
  var openerdisplayElementId = opener.document.getElementById(displayElementId);
  //openerdisplayElementId.className = "inputBox";
  openerdisplayElementId.value = returnSelectedValue;
  } catch( ex ) {}
  
  try {
  if( window.opener.supplierChanged ) {
  	window.opener.supplierChanged(returnSelectedId);
  }
  } catch(exx) {}
  window.close();
  //reset valiable
  returnSelectedValue = null;
  returnSelectedId=null;
  valueElementId=null;
  displayElementId=null;
  statusFlag=null;
}

  
function showErrorMessages()
{
  parent.showErrorMessages();
}


function validateForm()
{
	// validates the search input field
	var errorMessage = "";
	var errorCount = 0;
	
	try
	{
	 var searchInput = document.getElementById("searchArgument");	 

	 if ( searchInput.value.trim().length == 0  )
	 {
	    errorMessage +=  messagesData.searchInput + "\n";
	    errorCount = 1;	    
	 }

	 
	} catch (ex) {
	  errorCount++;
	}
	if (errorCount >0)
	 {
	    alert(errorMessage);
	    return false;
	 }	
    return true;
}
function myMainOnload()
{
  if (valueElementId.length>0 && displayElementId.length>0 )
 {
  document.getElementById("selectedRow").innerHTML="";
 }
 
}



function submitSearchForm()
{	
	document.genericForm.target='resultFrame';
	$('uAction').value  ="search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	
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


function generateExcel() {
	  var flag = validateForm();
	  if(flag) {
	    $('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_supplierSearchGenerateExcel','650','600','yes');
	    document.genericForm.target='_supplierSearchGenerateExcel';
	    var a = window.setTimeout("document.genericForm.submit();",500);
	  }
	}

/*
function subnew() {
  $('uAction').value = "new";
  document.genericForm.target='newviewrequestdetail';
  document.genericForm.action='suplierrequest';
  openWinGeneric('/tcmIS/common/loadingfile.jsp'
                 ,"newviewrequestdetail","800","600","yes","25","25","no");
  setTimeout("document.genericForm.action='supplierrequest.do',document.genericForm.submit(),document.genericForm.action='posupplierresults.do'",300);
  return false;
}
*/

function subnew() {
	/*$('uAction').value = "new";
  	document.genericForm.target='newviewrequestdetail';

  	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp'
                 ,"newviewrequestdetail","800","600","yes","25","25","no");
  	setTimeout("document.genericForm.submit()",300);*/

  var loc = "/tcmIS/supply/supplierrequestupdate.do?uAction=new";
  try
  {
    parent.parent.openIFrame("newSupplierRequestNew",loc,""+messagesData.newsupplierrequest+"","","N");
  }
  catch (ex)
  {
    openWinGeneric(loc,"newSupplierRequestDetail"+"","800","600","yes","50","50");
  }

  	return false;
}

