function doShowDocument()
{
  opener.addDocumentLine();
  var documentTable = opener.document.getElementById("documentstable");
  var allTRs = documentTable.getElementsByTagName("tr");
  var rownum = (allTRs.length)*1 - 2;

  var newDocumentUrl = document.getElementById("newDocumentUrl");
  var documentName = document.getElementById("documentName");
  var	documentId =document.getElementById("documentId");

  var documentUrlCell = opener.document.getElementById("documentUrl"+rownum+"");
  var documentUrlHTML = "<input type=\"hidden\" name=\"peiProjectDocumentBean["+rownum+"].url\" id=\"url"+rownum+"\" value=\""+newDocumentUrl.value+"\">";
  documentUrlHTML += "<input type=\"hidden\" name=\"peiProjectDocumentBean["+rownum+"].documentId\" id=\"documentId"+rownum+"\" value=\""+documentId.value+"\">";
  documentUrlHTML += "<a href=\""+newDocumentUrl.value+"\" target=\"newDocument"+rownum+"\" style=\"color:#0000ff;cursor:hand;text-decoration:underline\">"+documentName.value+"</a>";
  documentUrlCell.innerHTML = documentUrlHTML;
//alert(documentUrlHTML);
  var documentDeleteCell = opener.document.getElementById("documentDelete"+rownum+"");
  var deletePermission = opener.document.getElementById("deletePermission");
  if (deletePermission.value == 'Yes')
  {
  var documentDeleteHTML = "<input type=\"checkbox\" name=\"peiProjectDocumentBean["+rownum+"].delete\" id=\"delete"+rownum+"\" value=\"Y\">";
  documentDeleteCell.innerHTML = documentDeleteHTML;
  }

//alert(documentDeleteHTML);
  var loc = newDocumentUrl.value;
  openWinGeneric(loc,"doShowDocumentsaved","800","600","yes","80","80");
  window.close();
}

/*function doShowDocuments(rowNumber)
{
  var receiptId = document.getElementById("receiptId"+rowNumber+"");
  var loc = "/tcmIS/hub/showhubbin.do?receiptId="+receiptId.value+"&showDocuments=Yes";
  openWinGeneric(loc,"showVvHubBins","300","150","no","80","80");
}*/

function checkInput()
{
	if(!validateFileExtension()) return false;
	
var errorMessage = messagesData.validvalues +"\n\n";
var errorCount = 0;

var documentName = document.getElementById("documentName");
if (documentName.value.trim().length == 0)
{
 errorMessage = errorMessage + messagesData.document + messagesData.name + ".\n" ;
 errorCount = 1;
}

var theFile = document.getElementById("theFile");
if (theFile.value.trim().length == 0)
{
 errorMessage = errorMessage + messagesData.file + ".\n" ;
 errorCount = 1;
}

/*
var documentType = document.getElementById("documentType");
if (documentType.value.trim().length == 0)
{
 errorMessage = errorMessage + " File. \n" ;
 errorCount = 1;
}*/

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

function cancel()
{
    window.close();
}