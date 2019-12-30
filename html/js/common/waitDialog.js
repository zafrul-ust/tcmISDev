YAHOO.namespace("example.panel");
var WaitDialogCount=0;
function showWaitDialog() {
if (WaitDialogCount ==0)
{
 YAHOO.example.panel.wait =
 new YAHOO.widget.Panel("wait",
 {
  width:"240px",
  fixedcenter:true,
  underlay:"shadow",
  close:false,
  visible:true,
  draggable:false,
  modal:true
 }
);

 YAHOO.example.panel.wait.setHeader("&nbsp;");
 YAHOO.example.panel.wait.setBody("<img src=\"https://www.tcmis.com/images/rel_interstitial_loading.gif\"/>");
 YAHOO.example.panel.wait.render(document.body);
 WaitDialogCount++;
}
}

function stopWaitDialog()
{
  if (WaitDialogCount>0)
  {
   YAHOO.example.panel.wait.hide();
  }
  WaitDialogCount = 0;
}
