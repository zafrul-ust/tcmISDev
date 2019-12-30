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


function mySearchOnload()
{
 setSearchFrameSize();
}

function submitSearchForm()
{
 document.genericForm.target="resultFrame";
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
//alert('excel');
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_ConsignInventoryGenerateExcel','650','600','yes');
    document.genericForm.target='_ConsignInventoryGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function myResultOnload()
{
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page
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

function validateForm() {
  var flag = true;
  return flag;
}

function hubChanged() {
   var hubO = document.getElementById("hub");
   var inventoryGroupO = document.getElementById("inventoryGroup");
   var selhub = hubO.value;

   for (var i = inventoryGroupO.length; i > 0; i--) {
      inventoryGroupO[i] = null;
   }
   if (selhub.length > 0 ) {
     showInventoryGroupOptions(selhub);
   } else {
     setOption(0,messagesData.all,"","inventoryGroup");
   }
   inventoryGroupO.selectedIndex = 0;
}

function showInventoryGroupOptions(selectedhub) {
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];
    var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0) {
        setOption(0,"All","","inventoryGroup");
    }

    for (var i=0; i < invgrpid.length; i++) {
        setOption(i,invgrpName[i],invgrpid[i],"inventoryGroup")
    }
}

