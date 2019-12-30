var selectedRowId=null;
var showWAWFInsRequestLink=false;
var showPrintHazardousGoodsManifest=false;

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultOnload()
{
   setResultFrameSize();
	 /*Dont show any update links if the user does not have permissions.
   Remove this section if you don't have any links on the main page*/
   if (!showUpdateLinks)
   {
     parent.document.getElementById("updateResultLink").style["display"] = "none";
   }
   else
   {
     parent.document.getElementById("updateResultLink").style["display"] = "";
   }

   if (!showShipConfirmLinks)
   {
     parent.document.getElementById("shipConfirmResultLink").style["display"] = "none";
   }
   else
   {
     parent.document.getElementById("shipConfirmResultLink").style["display"] = "";
   }

   if (!showReprintPalletMslResultLink)
   {
     parent.document.getElementById("reprintPalletMslResultLink").style["display"] = "none";
   }
   else
   {
     parent.document.getElementById("reprintPalletMslResultLink").style["display"] = "";
   }		
   
   if (!showWAWFInsRequestLink)
   {
     parent.document.getElementById("WAWFInsRequestLink").style["display"] = "none";
   }
   else
   {
     parent.document.getElementById("WAWFInsRequestLink").style["display"] = "";
   }		
   
   if (!showPrintHazardousGoodsManifest)
   {
     parent.document.getElementById("printHazardousLink").style["display"] = "none";
   }
   else
   {
     parent.document.getElementById("printHazardousLink").style["display"] = "";
   }	
   
   if($v("popUp") == "Y") {
   	 var loc = "/HaasReports/report/printhazmatmanifest.do?manifestId="+$v("FedexParcelHazDocId");
     openWinGeneric(loc,"addnewroom","800","500","yes","80","80")
   }
}

function selectRow(rowId)
{
   var selectedRow = document.getElementById("rowId"+rowId+"");
   var selectedRowClass = document.getElementById("colorClass"+rowId+"");
   selectedRow.className = "selected"+selectedRowClass.value+"";

	 var childRowsSize = document.getElementById("childRowsSize"+rowId+"");
   for(j=1;j<childRowsSize.value;j++)
   {
	   var selectedchildRow = document.getElementById("childRowId"+rowId+""+(j)+"");
	   selectedchildRow.className = "selected"+selectedRowClass.value+"";
   }

	 if (selectedRowId != null && rowId != selectedRowId){
	   var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
	   var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
	   previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";

     var previouslychildRowsSize = document.getElementById("childRowsSize"+selectedRowId+"");
     for(j=1;j<previouslychildRowsSize.value;j++) {
	    var previouslyselectedchildRow = document.getElementById("childRowId"+selectedRowId+""+(j)+"");
	    previouslyselectedchildRow.className = ""+previouslySelectedRowClass.value+"";
     }
	 }
   selectedRowId =rowId;

	//RIGHT MOUSE CLICKED
   //toggleContextMenu('showPutShipmentOnPallet');
}  //end of selectRow

function showPutShipmentOnPallet()
{
  if (selectedRowId != null)
  {
	   var prNumber = document.getElementById("prNumber"+selectedRowId+"");
	   var lineItem = document.getElementById("lineItem"+selectedRowId+"");
     var loc = "putshipmentonpallet.do?action=putShipmentOnPallet"+"&prNumber="+prNumber.value+"&lineItem="+lineItem.value;
     openWinGeneric(loc,"showPutShipmentOnPallet22","800","500","yes","80","80")
  }
}

function validateInputData() {
	var result = "";
	var noRowSelected = true;
  var rowNum = document.getElementById('totalLines');
  for(var i = 0; i < rowNum.value; i++) {
    var ok = document.getElementById("selected"+i+"");
	  if (ok == null) {
		  continue;
	  }
	  if (ok.checked) {
		  noRowSelected = false;
			var carrierCode = document.getElementById("carrierCode"+i+"");
			if (carrierCode == null) {
				result = "missingData";
				break;
			}else if (carrierCode.value.length < 1) {
				result = "missingData";
				break;
			}
			var leadTrackingNumber = document.getElementById("leadTrackingNumber"+i+"");
			if (leadTrackingNumber == null) {
				result = "missingData";
				break;
			}else if (leadTrackingNumber.value.length < 1) {
				result = "missingData";
				break;
			}
			var boxTrackingNumber = document.getElementById("boxTrackingNumber"+i+"");
			if (boxTrackingNumber == null) {
				result = "missingData";
				break;
			}else if (boxTrackingNumber.value.length == 0) {
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


function wawfInsRequestAudit() {
    var result = true;
    var rowNum = document.getElementById('totalLines');
    var errorMsg = "";
    for(var i = 0; i < rowNum.value; i++) {
        var ok = document.getElementById("selected" + i + "");
        if (ok == null) {
            continue;
        }
        if (ok.checked) {
            var facilityId = document.getElementById("facilityId" + i + "");
            var originInspectionRequired = document.getElementById("originInspectionRequired" + i + "");
            var trackSerialNumber = document.getElementById("trackSerialNumber" + i + "");
            var missingSerialNumber = document.getElementById("missingSerialNumber" + i + "");
            if (facilityId.value.toUpperCase() == 'DLA GASES' && originInspectionRequired.value == 'Y' && trackSerialNumber.value == 'Y' && missingSerialNumber.value == 'Y') {
                errorMsg += "\t";
                errorMsg += document.getElementById("mrLine" + i + "").value;
                errorMsg += "\n";
            }
        }
    }
    //is missing serial number
    if (errorMsg.length > 0) {
        alert(messagesData.missingSerialNumber.replace(/\{0\}/g, messagesData.mrLine).replace(/\{1\}/g, messagesData.sendToWawfInspection)+"\n"+errorMsg);
        result = false;
    }

    return result;
}