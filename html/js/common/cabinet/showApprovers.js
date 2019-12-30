function showApproversWindow()
{
 var showApproversBody  =  document.getElementById("showApproversBody");

var openerShowApproversBody  =  window.parent.document.getElementById("showApproversBody");
openerShowApproversBody.innerHTML = showApproversBody.innerHTML;

window.parent.showUseApproversWindow();
}