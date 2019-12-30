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

function myResultsOnload()
{
 displaySearchDuration();
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/
 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
}

function doSubmitUpdates()
{
  var errorMessage = messagesData.validvalues+"\n\n";
  var totalLines = document.getElementById("totalLines").value;
  
  var i=0;
  for (i=0;i<totalLines;i++)
  {
     var needDate = document.getElementById("needDate"+i+"");
       if (needDate.value.trim() == "" && document.getElementById("okDoUpdate"+i+"").checked == true)
       {  
         alert(errorMessage + messagesData.needDate );
         needDate.value = document.getElementById("todayDate").value;
         document.getElementById("okDoUpdate"+i+"").checked = false;
         return;
       }
  }
     
    	/*Set any variables you want to send to the server*/
    	var userAction = document.getElementById("userAction");
    	userAction.value = 'update';
    	parent.showPleaseWait();
    	/*Submit the form in the result frame*/
   		document.genericForm.submit();  		
}

function checkQtyValue (rownum)
{
  var okDoUpdate  =  document.getElementById("okDoUpdate"+rownum+"");
  if (okDoUpdate.checked)
  {
	var replenishQty  =  document.getElementById("replenishQty"+rownum+"");

	if (!(isPositiveInteger(replenishQty.value.trim(),false)))
   {
   	alert(messagesData.validvalues+"\n\n"+messagesData.replenishqty);
		replenishQty.value = "";
		okDoUpdate.checked=false;
   }
  }
}

function checkValues(rownum)
{
checkQtyValue(rownum);
checkCalendarValue(rownum);
}

function checkCalendarValue(rownum)
{
var errorMessage = messagesData.validvalues+"\n\n";

if( document.getElementById("okDoUpdate"+rownum+"").checked == false ) return;
var needDate = document.getElementById("needDate"+rownum+"");
  if (needDate.value.trim() == "")
  {  
       needDate.value = document.getElementById("todayDate").value;
       alert(errorMessage + messagesData.needDate );
       document.getElementById("okDoUpdate"+rownum+"").checked = false;
  }
}

function limitText(rownum, limitNum) {
    var limitCount;
    var limitField  =  document.getElementById("comment"+rownum+"");
// alert(limitField.value.length);
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
		alert(messagesData.maximum4000);
	} 
}

function showMinMax(catPartNo, invengrp, partGroupNo, count_uom)
{
	var HubName  =  document.getElementById("hub");
	//var invengrp  =  document.getElementById("invengrp");
	/* loc = "/tcmIS/hub/minmaxchg?UserAction=showlevels&searchlike=CAT_PART_NO&searchtext="
    loc = loc + catPartNo;
    loc = loc + "&HubName=" + HubName.value;
    loc = loc + "&invengrp=" + invengrp;
    loc = loc + "&catPartNo=" + catPartNo;
	 loc = loc + "&partGroupNo=" + partGroupNo;
	 //loc = loc + "&origOperatingMethod=" + operatingMethod;
	 //loc = loc + "&countUom=" + count_uom;
    loc = loc + "&changeOperatingMethod=Yes";*/

    loc = "/tcmIS/hub/minmaxlevelmain.do?uAction=showlevels&criterion=partNumber&criteria="
    loc = loc + catPartNo;
    loc = loc + "&hub=" + HubName.value;
    loc = loc + "&inventoryGroup=" + invengrp;

    openWinGeneric(loc,"Show_Min_Max","600","600","yes")
}