
function selectedItem()
{
 if (returnSelectedItemId.length > 0)
 {
  var receiptList = document.getElementById("receipts").value;
  var listItemId = document.getElementById("listItemId").value;
  loc = "receivingitemsearchresults.do?action=update&receiptList="+receiptList+"&listItemId="+listItemId+"&itemId="+returnSelectedItemId+"";
  callToServer(loc);
 }

 setTimeout('submitMainPage()',1000);
}

function submitMainPage()
{
 opener.submitSearchForm();
 window.close();
}

function submitNewChemRequest()
{
  showPleaseWait();
  document.genericForm.target='';
  document.genericForm.submit();
}