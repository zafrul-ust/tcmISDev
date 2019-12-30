function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }

  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
  showErrorMessagesWin.render();
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myOnload()
{
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/
/*
 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
*/
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
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

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    document.genericForm.target='_newGenerateExcel';
    //document.genericForm.action='/tcmIS/hub/allocationanalysis.do';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function generateExcelDetail() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcelDetail';
    openWinGeneric('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    document.genericForm.target='_newGenerateExcel';
    //document.genericForm.action='/tcmIS/hub/allocationanalysis.do';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function validateForm() {
  document.genericForm.target='resultFrame';
  var action = document.getElementById("action");
  action.value = '';
  return true;
}

function showDetail(paymentNumber) {
  openWinGeneric("/tcmIS/supplier/vendorpaymentdetail.do?action=detail&paymentNum="+paymentNumber,"vendorPaymentDetail","700","400","yes");
}

