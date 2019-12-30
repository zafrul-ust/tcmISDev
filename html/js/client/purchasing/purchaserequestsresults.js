function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultOnload()
{
	//it's import to put this before setResultFrameSize(), otherwise it will not resize correctly
	displaySearchDuration();
	setResultFrameSize();

 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/
 if (showUpdateLinks) {
	  parent.document.getElementById("updateResultLink").style["display"] = "";
	  if (showConvertLink) {
		  parent.document.getElementById("convertPurchaseLink").style["display"] = "";
	  }
	  else {
		  parent.document.getElementById("convertPurchaseLink").style["display"] = "none";
	  }
 }
 else {
	  parent.document.getElementById("updateResultLink").style["display"] = "none";
	  parent.document.getElementById("convertPurchaseLink").style["display"] = "none";
 }
}

function checkBuyorder(rowId) {
	//do nothing for now	
}

function validateInputData() {
	var result = "";
	var noRowSelected = true;
  var rowNum = document.getElementById('totalLines');
  for(var i = 0; i < rowNum.value; i++) {
    var ok = document.getElementById("ok"+i+"");
	  if (ok == null) {
		  continue;
	  }
	  if (ok.checked) {
		  noRowSelected = false;
			var customerPoNumber = document.getElementById("customerPoNumber"+i+"");
			if (customerPoNumber == null) {
				result = "missingData";
				break;
			}else if (customerPoNumber.value.length < 1) {
				result = "missingData";
				break;
			}
			var orderQuantity = document.getElementById("orderQuantity"+i+"");
			if (orderQuantity == null) {
				result = "missingData";
				break;
			}else if (orderQuantity.value.length == 0) {
				result = "missingData";
				break;
			}
			var promisedDate = document.getElementById("promisedDate"+i+"");
			if (promisedDate == null) {
				result = "missingData";
				break;
			}else if (promisedDate.value.length == 0) {
				result = "missingData";
				break;
			}
	  }
	}

	if (noRowSelected) {
		result = "noRowSelected";
	}
	return result;
}

function showPreviousPo(poNumber) {
   loc = "purchaseordersmain.do?action=showPreviousPo&customerPoNumber="+poNumber;
   openWinGeneric(loc,"purchaseorder12311","800","700","yes","50","50","no")
}