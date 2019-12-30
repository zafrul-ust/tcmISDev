var selectedRowId=null;
var showWAWFInsRequestLink=false;

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
}

function selectRow(rowId)
{
   var selectedRow = document.getElementById("rowId"+rowId+"");
   var selectedRowClass = document.getElementById("colorClass"+rowId+"");
   selectedRow.className = "selected"+selectedRowClass.value+"";

   if (selectedRowId != null && rowId != selectedRowId)
   {
     var previouslySelectedRow = document.getElementById("rowId"+selectedRowId+"");
     var previouslySelectedRowClass = document.getElementById("colorClass"+selectedRowId+"");
     previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
   }
   selectedRowId =rowId;

 //  toggleContextMenu('showPalletDetail');
}  //end of selectRow

function showPalletDetail()
{
  if (selectedRowId != null)
  {
     var sourceHub = document.getElementById("sourceHub");
	   var palletId = document.getElementById("palletId"+selectedRowId);
     var loc = "palletdetail.do?action=palletDetail&sourceHub="+sourceHub.value+"&palletId="+palletId.value;
     openWinGeneric(loc,"showPalletDetail22","800","500","yes","80","80")

  }
}

function showMrLines(consolidationNumber)
{
  if (consolidationNumber != null)
  {
     var loc = "packshipconfirmresults.do?action=mrLineSearch&consolidationNumber="+consolidationNumber;
     openWinGeneric(loc,"ltlMrLines","400","450","yes","80","80")
  }
  return false;
}

function checkSerialNumber(selectedRow) {
    var checkbox = document.getElementById("selected" + selectedRow);
    if (checkbox.checked) {
        var trackSerialNumber = document.getElementById("trackSerialNumber" + selectedRow + "");
        var missingSerialNumber = document.getElementById("missingSerialNumber" + selectedRow + "");
        if (trackSerialNumber.value == 'Y' && missingSerialNumber.value == 'Y') {
            alert(messagesData.missingSerialNumber.replace(/\{0\}/g, messagesData.trackingNumber).replace(/\{1\}/g, messagesData.sendToWawfInspection)+"\n\t"+document.getElementById("leadTrackingNumber" + selectedRow + "").value);
            checkbox.checked = false;
        }
    }
}