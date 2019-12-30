function sendPaymentTerms()
{
 loc = "/tcmIS/purchase/posupplier?session=Active&Action=sendPaymentTerms&SearchString=";
 loc = loc + window.document.SupplierLike.SearchString.value;
 loc = loc + "&userAction=";
 loc = loc + window.document.SupplierLike.userAction.value;
 loc = loc + "&rowNumber=";
 loc = loc + window.document.SupplierLike.rowNumber.value;
 loc = loc + "&inventoryGroup=";
 loc = loc + window.document.SupplierLike.inventoryGroup.value;
  var supplierrep = window.document.SupplierLike.supplierid.value;
  supplierrep = supplierrep.replace(/&/gi, "%26");
  loc = loc + "&suppID=" + supplierrep;
 
  window.location.replace(loc);
}