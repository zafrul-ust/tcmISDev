function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
 if (showErrorMessage)
 {
  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
  showErrorMessagesWin.render();
  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
 }
}

function myOnload()
{
   setResultFrameSize();
}

function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var flag = validateForm();
  if(flag) {
   parent.showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function searchManifest() {
   openWinGeneric('manifestsearchmain.do','_ManifestSearch','650','600','yes');
}

function generatePdf() {
  var flag = validateForm();
  if(flag) {
    openWinGeneric('manifestreport.do?action=createPdf','_ManifestReportGeneratePdf','650','600','yes');
  }
}

function validateForm() {
  var manifest = document.getElementById("manifest");
  if (manifest.value.trim().length < 1) {
     alert(messagesData.manifestRequired);
     return false;
  }else {
     return true;
  }
} //end of validateForm

