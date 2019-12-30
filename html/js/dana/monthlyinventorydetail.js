function doexcelpopup()
{
 excelfileurl = "/tcmIS/common/viewexcel.jsp";

 openWinGenericViewFile(excelfileurl,"show_excel_report_file","610","600","yes");
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

function createxlsreport(entered)
{
	 var userAction  =  document.getElementById("userAction");
    userAction.value = "createxlsreport";
    return true;
}

function getinvociedates(entered)
{
	 var userAction  =  document.getElementById("userAction");
   userAction.value = "getinvociedates";
   submitOnlyOnce();
   window.document.monthylInvenDetailForm.submit();
}