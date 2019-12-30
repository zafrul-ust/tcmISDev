/*Call this if you want to initialize something on load in the search frame.*/
function mySearchOnload()
{
 setSearchFrameSize();
}

function reSearchForm()
{
var now = new Date();
 document.getElementById("startSearchTime").value = now.getTime();
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
 var flag = validateForm();
  if(flag) {
   parent.showPleaseWait();
   document.genericForm.submit()
  }
}

function submitSearchForm()
{
var now = new Date();
 document.getElementById("startSearchTime").value = now.getTime();

 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
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

function submitEndOfDayClose() {
	var hub = document.getElementById("sourceHub");
	var sourceHub = hub.options[hub.selectedIndex].value;
	var opsEntityId = document.getElementById("opsEntityId");
	if (opsEntityId == null) {
		opsEntityId = "";
	}
	//showTransitWin("justPleaseWait");
	var myUrl = "packshipconfirmsearch.do?action=endOfDayFedexClose&sourceHub=" + sourceHub + "&opsEntityId=" + opsEntityId;
	callToServer(myUrl);
}

function validateForm() {
	return true;
}

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_PackShipConfirmGenerateExcel','650','600','yes');
    document.genericForm.target='_PackShipConfirmGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function hubchanged()
{
	var hubO = document.getElementById("sourceHub");

	try
   {
     var sourceHubName = null;
     sourceHubName =  hubO.options[hubO.selectedIndex].text;

     var sourceHubNameObject = document.getElementById("sourceHubName");
     sourceHubNameObject.value = sourceHubName;
   }
   catch (ex)
   {
     //alert("Non-Pickable Status");
   }

	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,messagesData.all,"")
    }

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = document.getElementById("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}

function init() { /*This is to initialize the YUI*/
	this.cfg = new YAHOO.util.Config(this);
 	if (this.isSecure) {
		this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 	}

	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	If they are not initialized onLoad they tend to act weird*/
 	statusWin = new YAHOO.widget.Panel("showStatusArea", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
	statusWin.render();

}

function showStatus(msg, isError){
	var showStatusArea = document.getElementById("showStatusArea");
	var statusAreaBody = document.getElementById("showStatusAreaBody");
	statusAreaBody.innerHTML = msg;
	showStatusArea.style.display="";
	statusWin.show();
}

