windowCloseOnEsc = true;

function init() { /*This is to initialize the YUI*/
  this.cfg = new YAHOO.util.Config(this);
  if (this.isSecure)
  {
    this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
  }

  /*Yui pop-ups need to be initialized onLoad to make them work correctly.
  If they are not initialized onLoad they tend to act weird*/
  errorMessagesAreaWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px",height:"200px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
  errorMessagesAreaWin.render();

  showMrLineNotesWin = new YAHOO.widget.Panel("mrLineNotesMessagesArea", { width:"300px",height:"200px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
  showMrLineNotesWin.render();

  showDocumentMenuWin = new YAHOO.widget.Panel("showDocumentMenuArea", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
  showDocumentMenuWin.render();
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  errorMessagesAreaWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}

function showMrLineNotes(notes)
{
  var mrLineNotesMessagesAreaBody = document.getElementById("mrLineNotesMessagesAreaBody");
  mrLineNotesMessagesAreaBody.innerHTML = notes;

  var mrLineNotesMessagesArea = document.getElementById("mrLineNotesMessagesArea");
  mrLineNotesMessagesArea.style.display="";

  showMrLineNotesWin.show();
}

function showDocumentMenu() {
  var showDocumentMenuArea = document.getElementById("showDocumentMenuArea");
  showDocumentMenuArea.style.display="";
  showDocumentMenuWin.show();
}

function showDocument(receiptId, documentId, documentUrl) {
    var openByUrl = true;
    if(documentUrl != undefined && documentUrl != '') {
        if (documentUrl.indexOf("receipt_documents") > 0)
            openByUrl = false;
    }

    if (openByUrl) {
        if(documentUrl != undefined && documentUrl != '')
    	    openWinGeneric(documentUrl,'_MrAllocationShowDocument','650','600','yes');
    }else {
        var tmpUrl = "";
        if ($v("secureDocViewer") == 'true')
            tmpUrl = "/DocViewer/client/";
	    openWinGeneric(tmpUrl + "receiptdocviewer.do?uAction=viewReceiptDoc&documentId="+documentId+"&receiptId="+receiptId+"&companyId="+$v("companyId")
			,'_MrAllocationShowDocument','650','600','yes');
    }
    var showDocumentMenuArea = document.getElementById("showDocumentMenuArea");
   showDocumentMenuArea.style.display="none";
}