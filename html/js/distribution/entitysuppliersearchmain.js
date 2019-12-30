var windowCloseOnEsc = true;
var returnSelectedValue = null;
var returnSelectedId=null;
var returnAddressLine1=null;
var returnAddressLine2=null;
var returnAddressLine3=null;
var returnMfgPhone=null;
var returnPaymentTerms=null;
var returnQualificationLevel=null;
var returnEmail=null;

function selectedRow() {
	var fromNewPrinter = parent.document.getElementById("fromNewPrinter").value;
	if (fromNewPrinter != '') {
		var openervalueElementId = opener.document.getElementById(valueElementId);
		openervalueElementId.value = returnSelectedId;
		var openerdisplayElementId = opener.document.getElementById(displayElementId);
		openerdisplayElementId.value = returnSelectedValue;
	} else if ($v("fromPurchaseOrderPage") == 'Y' || $v("fromPoApproval") == 'Y') {
		
	} else if ($v("fromSupplierPriceList") == 'Y') {
		opener.$("supplier").value = returnSelectedId;
		opener.$("supplierName").value = returnSelectedValue;
	} else {
		//call from legacy code
		var rowNumber = document.getElementById("rowNumber").value;
		if ($v("secondarySupplier") == 'Y') {
			var secondarysuppliercelldivrow = opener.document.getElementById("secondarysuppliercelldivrow" + rowNumber + "" + rowNumber + "");
			var innHtmlline = "<input type=\"text\" size=\"5\" CLASS=\"HEADER\" name=\"secondarysupplier"+rowNumber+"\" id=\"secondarysupplier"+rowNumber+"\">";
			innHtmlline =  innHtmlline +"<BUTTON name='secondarysupplierbutton' OnClick=getSecondarySupplier(\""+rowNumber+"\") type=button><IMG src=\"/images/search_small.gif\" alt=\"Secondary Supplier\"></BUTTON>";
			innHtmlline =  innHtmlline + returnSelectedValue +" "+ returnAddressLine1 +" "+returnAddressLine2+" "+returnAddressLine3;
			secondarysuppliercelldivrow.innerHTML = innHtmlline;
			secondarysupplier = opener.document.getElementById("secondarysupplier" + rowNumber + "");
			secondarysupplier.className = "HEADER";
			secondarysupplier.value = returnSelectedId;
			linechange = opener.document.getElementById("linechange" + rowNumber + "");
			linechange.value = "Yes";
		} else {
			var supplierid = opener.document.getElementById("supplierid");
			supplierid.className = "HEADER";
			supplierid.value = returnSelectedId;
			var validsupplier = opener.document.getElementById("validsupplier");
			validsupplier.value = "Yes";
			var validordertaker = opener.document.getElementById("validordertaker");
			validordertaker.value = "No";
			var faxdate = opener.document.getElementById("faxdate");
			faxdate.value = "";
			var accepteddate = opener.document.getElementById("accepteddate");
			accepteddate.value = "";
			var mfgphoneno = opener.document.getElementById("mfgphoneno");
			mfgphoneno.value = returnMfgPhone;
			var paymentterms1 = opener.document.getElementById("paymentterms");
			paymentterms1.value = returnPaymentTerms;
			var supplieremail = opener.document.getElementById("supplieremail");
			supplieremail.innerHTML = "" + returnEmail + "";
			var suppRating = opener.document.getElementById("suppRating");
			suppRating.value = "" + returnQualificationLevel + "";

			var supplierline1 = opener.document.getElementById("supplierline1");
			supplierline1.innerHTML = "<FONT COLOR=\"Maroon\">" + returnSelectedValue + "<FONT>";
			var supplierline2 = opener.document.getElementById("supplierline2");
			supplierline2.innerHTML = "<FONT COLOR=\"Maroon\">" + returnAddressLine1 + "<FONT>";
			if (returnAddressLine2 > 0) {
				var supplierline3 = opener.document.getElementById("supplierline3");
				supplierline3.innerHTML = "<FONT COLOR=\"Maroon\">" + returnAddressLine2 + "<FONT>";
				var supplierline4 = opener.document.getElementById("supplierline4");
				supplierline4.innerHTML = "<FONT COLOR=\"Maroon\">" + returnAddressLine3 + "<FONT>";
			} else {
				var supplierline3 = opener.document.getElementById("supplierline3");
				supplierline3.innerHTML = "<FONT COLOR=\"Maroon\">" + returnAddressLine3 + "<FONT>";
				var supplierline4 = opener.document.getElementById("supplierline4");
				supplierline4.innerHTML = "<FONT COLOR=\"Maroon\"><FONT>";
			}
			sendPaymentTerms();
		}
	}

	if ($v("secondarySupplier") == 'Y') {
		try {
			var s = new supplierInfo();
			window.opener.secondarySupplierChanged(s);
		} catch (exx) {}
	} else {
		try {
			var s = new supplierInfo();
			window.opener.supplierChanged(s);
		} catch (exx) {}
	}
	window.close();
	// reset valiable
	returnSelectedValue = null;
	returnSelectedId = null;
	valueElementId = null;
	displayElementId = null;
	statusFlag = null;
}

function supplierInfo() {	
	this.returnSelectedValue = returnSelectedValue;
	this.returnSelectedId=returnSelectedId;
	this.returnAddressLine1=returnAddressLine1;
	this.returnAddressLine2=returnAddressLine2;
	this.returnAddressLine3=returnAddressLine3;
	this.returnMfgPhone=returnMfgPhone;
	this.returnPaymentTerms=returnPaymentTerms;
	this.returnQualificationLevel=returnQualificationLevel;
	this.returnEmail=returnEmail;	
} 

/*TODO Move this to sturts*/
function sendPaymentTerms()
{
 var rowNumber = document.getElementById("rowNumber").value;
 var inventoryGroup = document.getElementById("inventoryGroup").value;
 //var loc = "/tcmIS/purchase/posupplier?session=Active&Action=sendPaymentTerms&SearchString=";
 //loc = loc + returnSelectedId;
 //loc = loc + "&userAction=";
 //loc = loc;
 //loc = loc + "&rowNumber=";
 //loc = loc + rowNumber;
 //loc = loc + "&inventoryGroup=";
 //loc = loc + inventoryGroup;
  var supplierrep = returnSelectedId;
  supplierrep = supplierrep.replace(/&/gi, "%26");
  //loc = loc + "&suppID=" + supplierrep;

  window.opener.getCorrectPaymentTerms(rowNumber,inventoryGroup,returnSelectedId);
  //window.location.replace(loc);
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
	 var searchInput = document.getElementById("supplierName");	 

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

