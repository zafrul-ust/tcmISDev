
windowCloseOnEsc = true;

function submitSearchForm()
{
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  document.getElementById("selectedMSDS").innerHTML = "";

  if(validateSearchForm()) { 
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   document.genericForm.submit();
  }
  else
  {
    return false;
  }
}

function validateSearchForm()
{
	return true;
}

var returnCustomerMsdsNumber=null;
var returnMaterialId=null;
var returnMaterialDesc=null;
var returnManufacturer=null;

function msdsInfo() {
    this.customerMsdsNumber = returnCustomerMsdsNumber;
    this.materialId = returnMaterialId;
	this.materialDesc = returnMaterialDesc;
	this.manufacturer = returnManufacturer; 
} 
