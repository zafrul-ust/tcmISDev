windowCloseOnEsc = true;
var children = new Array();

function openReceiptDoc(receiptId, documentId, documentUrl) {
    var openByUrl = false;
    if(documentUrl) {
        if (documentUrl.indexOf("receipt_documents") > 0) {
            openByUrl = false;
        }
        else {
            openByUrl = true;
        }
    }

    if (openByUrl) {
            openWinGeneric(documentUrl,"_ShowReceiptDocument","800","450","yes","50","50","no");
    }
    else {
        var tmpUrl = "";
        if ($v("secureDocViewer") == 'true')
            tmpUrl = "/DocViewer/client/";
        openWinGeneric(tmpUrl + "receiptdocviewer.do?uAction=viewReceiptDoc&documentId="+documentId+"&receiptId="+receiptId+"&companyId="+$v("docCompanyId")
        		,'_ShowReceiptDocument','650','600','yes');
    }
}

function doShowDocument()
{
  var newDocumentUrl = document.getElementById("newDocumentUrl");
  var receiptId = document.getElementById("receiptId");
  var documentId = document.getElementById("documentId");
  
  var openByUrl = true;
  if(newDocumentUrl != '') {
      if (newDocumentUrl.indexOf("receipt_documents") > 0)
          openByUrl = false;
  }
  
  if (openByUrl) {
	    if(newDocumentUrl != '')
	        openWinGeneric(newDocumentUrl,"_ShowReceiptDocument","800","450","yes","50","50","no");
  
  }else {
	  var tmpUrl = "";
	  if ($v("secureDocViewer") == 'true')
	      tmpUrl = "/DocViewer/client/";
	  var loc = tmpUrl + "receiptdocviewer.do?uAction=viewReceiptDoc&documentId="+documentId+"&receiptId="+receiptId+"&companyId="+$v("docCompanyId");
	  try {
	 	children[children.length] = openWinGeneric(loc,'_ShowReceiptDocument','650','600','yes');
	  } catch (ex){
	 	openWinGeneric(loc,'_ShowReceiptDocument','650','600','yes');
	  }
  }

  window.opener.location.reload();
  window.close();
}

function doShowDocuments(rowNumber)
{
  var receiptId = document.getElementById("receiptId"+rowNumber+"");
  var loc = "/tcmIS/hub/receiptdocuments.do?receiptId="+receiptId.value+"&showDocuments=Yes";
  try {
 	children[children.length] = openWinGeneric(loc,"showVvHubBins","300","150","no","80","80");
  } catch (ex){
 	openWinGeneric(loc,"showVvHubBins","300","150","no","80","80");
  }
}

function addNewReceiptDocument()
{
  var receiptId = document.getElementById("receiptId");
  var documentId = document.getElementById("documentId");
  var inventoryGroup = document.getElementById("inventoryGroup");
  // set to 0 if empty to avoid NumberFormatException in Java
  if (documentId.value == "") { 
	  documentId.value = "0";
  }

  //var loc = "/tcmIS/hub/addreceiptdocument.do?receiptId="+receiptId.value+"&documentId="+documentId.value+"&inventoryGroup="+inventoryGroup.value+"";
  var loc = "addreceiptdocument.do?receiptId="+receiptId.value+"&documentId="+documentId.value+"&inventoryGroup="+inventoryGroup.value+"";
  
  if ($v("docCompanyId") == 'Radian') 
	  loc = "/tcmIS/hub/" + loc;
  
  try {
 	children[children.length] = openWinGeneric(loc,"showaddNewReceiptDocument","600","300","no","100","100");
  } catch (ex){
 	openWinGeneric(loc,"showaddNewReceiptDocument","600","300","no","100","100");
  }
  
}

function checkInput()
{
	if (!validateFileExtension()) return false;
	
	var errorMessage = messagesData.validvalues +"\n\n";
	var errorCount = 0;
	
	var documentName = document.getElementById("documentName");
	if (documentName.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.document + messagesData.name + ".\n" ;
	 errorCount = 1;
	}
	
	var documentDate = document.getElementById("documentDate");
	if (documentDate.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.document + messagesData.date + ".\n" ;
	 errorCount = 1;
	}
	
	var theFile = document.getElementById("theFile");
	if (theFile.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.file + ".\n" ;
	 errorCount = 1;
	}
	
	var documentType = document.getElementById("documentType");
	if (documentType.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.document + messagesData.type + ".\n" ;
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

function closeAllchildren() 
{
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.
//	if (document.getElementById("uAction").value != 'new') {
		try {
			for(var n=0;n<children.length;n++) {
				try {
				children[n].closeAllchildren(); 
				} catch(ex){}
			children[n].close();
			}
		} catch(ex){}
		children = new Array(); 
}

function cancel()
{
    window.close();
}