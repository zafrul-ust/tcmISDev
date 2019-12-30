function doexcelpopup()
{
 excelfileurl = "/tcmIS/common/viewexcel.jsp";

 openWinGenericViewFile(excelfileurl,"show_excel_report_file","610","600","yes");
}

function hubchanged()
{
	var hubO = document.getElementById("hub");
	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
	removeAllInvocieDates();
}

function companyIdchanged()
{
	var companyId = document.getElementById("companyId");
	var facilityId = document.getElementById("facilityId");
	var selcompanyId = companyId.value;

	for (var i = facilityId.length; i > 0;i--)
   {
      facilityId[i] = null;
   }
	showFacilities(selcompanyId);
	facilityId.selectedIndex = 0;
	removeAllInvocieDates();
	showInvoiceDates(facilityId.value);
}

function showFacilities(selcompanyId)
{
    var facilityid = new Array();
    facilityid = altfacilityid[selcompanyId];

	 var facilityName = new Array();
    facilityName = altfacilityName[selcompanyId];

	 try
	 {
     setFacility(0,messagesData.pleaseselect,messagesData.all);

     for (var i=0; i < facilityid.length; i++)
     {
        setFacility(i+1,facilityName[i],facilityid[i])
     }
    }
    catch (ex)
    {
     setFacility(0,messagesData.pleaseselect,messagesData.all);
    }
}

function setFacility(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var facilityId = document.getElementById("facilityId");
	 facilityId[href] = optionName;
}

function facilityIdChanged()
{
	var facilityId = document.getElementById("facilityId");
	var selfacilityId = facilityId.value;
	var invoiceDate = document.getElementById("invoiceDate");

	for (var i = invoiceDate.length; i > 0;i--)
   {
      invoiceDate[i] = null;
   }
	showInvoiceDates(selfacilityId);
	invoiceDate.selectedIndex = 0;
}

function showInvoiceDates(selfacilityId)
{
    var invoicePeriod = new Array();
    invoicePeriod = altinvoiceperiod[selfacilityId];

	 var endDate = new Array();
    endDate = altenddate[selfacilityId];

	 try
	 {
     setInvoiceDate(0,messagesData.pleaseselect,messagesData.all)

     for (var i=0; i < invoicePeriod.length; i++)
     {
        setInvoiceDate(i+1,endDate[i],invoicePeriod[i])
     }
    }
    catch (ex)
    {
	   setInvoiceDate(0,messagesData.pleaseselect,messagesData.all);
    }
}

function setInvoiceDate(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var invoiceDate = document.getElementById("invoiceDate");
	 invoiceDate[href] = optionName;
}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,messagesData.pleaseselect,messagesData.all)
    }

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = document.getElementById("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}

function removeAllInvocieDates()
{
	var invoiceDateO = document.getElementById("invoiceDate");
	 if(invoiceDateO.length <= 0)
          return;

	for (var i = invoiceDateO.length; i > 0;i--)
   {
      invoiceDateO[i] = null;
   }
}

function customerSearch(entered)
{
 var invoiceDate  =  document.getElementById("invoiceDate");
    if (invoiceDate.value == "All")
    {
     alert("Please Select a Invoice Period");
     return false
    }
	 else
    {
    var userAction  =  document.getElementById("userAction");
    userAction.value = "search";
    return true;
    }
}

function validateCriteria()
{
var errorMessage = messagesData.validvalues +"\n\n";
var errorCount = 0;

var invoiceDate  =  document.getElementById("invoiceDate");
var facilityId  =  document.getElementById("facilityId");

if (facilityId.value == "All" || facilityId.value.lentgh==0)
{
  errorMessage = errorMessage + messagesData.facilityid + ".\n" ;
  errorCount = 1;
}

if (invoiceDate.value == "All" || invoiceDate.value.length==0)
{
 errorMessage = errorMessage + messagesData.invoicedate + ".\n" ;
 errorCount = 1;
}

if (errorCount >0)
{
  alert(errorMessage);
  return false;
}
else
{
  return true;
}
}

function search()
{
  if (validateCriteria())
  {
   var userAction  =  document.getElementById("userAction");
   userAction.value = "search";
   return true;
  }
  else
  {
   return false;
  }
}

function createxlsreport()
{
if (validateCriteria())
    {
	var userAction  =  document.getElementById("userAction");
  userAction.value = "createxlsreport";
  return true;
}
else
{
return false;
}
}

function getinvociedates(entered)
{
	 var userAction  =  document.getElementById("userAction");
   userAction.value = "getinvociedates";

   window.document.monthylInvenDetailForm.submit();
}