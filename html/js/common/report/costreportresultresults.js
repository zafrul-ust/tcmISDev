var selectedrow=null;
var showRighClickMenu=false;

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

function mySearchOnLoad()
{
  setSearchFrameSize();
    var action = document.getElementById("action");
    action.value = 'result';
    document.genericForm.target='resultFrame';
    parent.showPleaseWait();
    window.setTimeout("document.genericForm.submit();",500);
}

function myOnload()
{
   setResultFrameSize();
}

function showAdditionalCharge(rowIndex) {
	parent.opener.document.getElementById('action').value="showAdditionalCharge";
	parent.opener.document.getElementById('whereClauseForLink').value=document.getElementById('whereClauseForLink'+rowIndex).value;
	parent.opener.document.costReportForm.target='_show_add_charge';
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_show_add_charge','400','400','yes');
	setTimeout("parent.opener.document.costReportForm.submit()",300);
}

function showSalesTax(rowIndex) {
	parent.opener.document.getElementById('action').value="showSalesTax";
	parent.opener.document.getElementById('whereClauseForLink').value=document.getElementById('whereClauseForLink'+rowIndex).value;
	parent.opener.document.costReportForm.target='_show_sales_tax';
	openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_show_sales_tax','400','400','yes');
	setTimeout("parent.opener.document.costReportForm.submit()",300);
}
