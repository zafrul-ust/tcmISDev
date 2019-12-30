windowCloseOnEsc = true;
var children = new Array();

function okCClick() {
	showConfirmErrorMsgWin.hide();
	window.close();
}

function $(a) {
	return document.getElementById(a);
}

function initWin() {
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
	 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}
	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	If they are not initialized onLoad they tend to act weird*/
	showConfirmErrorMsgWin = new YAHOO.widget.Panel("confirmErrorMsgArea", { width:"200px", height:"150px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:false, visible:false,
	draggable:true, modal:false } );
	showConfirmErrorMsgWin.render();
}

  
function showErrorMessages()
{
  var confirmErrorMsgArea = document.getElementById("confirmErrorMsgArea");
  confirmErrorMsgArea.style.display="";
  showConfirmErrorMsgWin.show();
}

function changeFreightAdvice() {
	 if( $("refresh").value.trim() == '' ) {
		 transportationModeChanged();
		 return ; 
	 }
    // if errorMsg is null or "" then the form close and parent search will re-search.
	 opener.parent.submitSearchForm();
	 if( $("messageToUser").value.trim() != '' )
		setTimeout('showErrorMessages()',200);
	 else
		window.close(); // no message to display.
}

function validate() {
	for(i=0;i>-1;i++) {
		var q = document.getElementById('quantity_'+i);
		if( q == null )
			return true;
		q.value = q.value.trim();
		// must be non-empty
		if( !isPositiveInteger(q.value,false) ) {
			alert(messagesData.quantity +" "+ messagesData.mustbepositive );
			return false;
		}  
		
	}
}

function auditData() {
	var errorMsg = "";
	if ($v("transportationMode") == 'TL') {
		if ($v("dateShipped") == null || $v("dateShipped").length == 0) {
			errorMsg += messagesData.validValues+" "+messagesData.projectedShipDate;
		}
		if ($v("consolidationNumber") == null || $v("consolidationNumber").length == 0) {
			errorMsg += messagesData.validValues+" "+messagesData.consolidationNo;
		}
	}
	if (errorMsg.length > 0) {
		alert(errorMsg);
		return false;
	}else {
		if($v("originalConsolidationNumber") == $v("consolidationNumber") && 
		  ($v("originalTransportationMode") == 'LTL' && $v("transportationMode") == 'TL')) {
			var answer = confirm(messagesData.reallychangeallorderforconsolidationnumbertotl+"\n\n"+messagesData.okforupdate+"\n"+messagesData.clickcanceltopick);
		    if (answer)
		      return true;
		    else
		      return false;
		}
		else {
		      return true;
		}  
	}
}

function submitAdvice ()
{
	if (auditData()) {
		$("transitPage").style["display"]="";
		$("mainPage").style["display"]="none";
		document.genericForm.target='';
		document.genericForm.submit();
		return true;
	}else {
		return false;
	}
}


function searchConsolidationNumber() {
	var tmpDateShipped = $v("dateShipped");
	if (tmpDateShipped != null && tmpDateShipped.length > 0) {
		children[children.length] = openWinGeneric("searchconsolidationnumber.do?uAction=search&hub="+$v("hub")+"&shipDate="+$v("dateShipped")+"&transportationMode="+$v("transportationMode")
	  													       ,"searchconsolidationnumber","400","300","yes","40","40","no");
	}else {
		alert(messagesData.validValues+" "+messagesData.projectedShipDate);
	}
}

function closeAllchildren()
{
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

function consolidationNumberChanged(selectedConsolidationNumber) {
	$("consolidationNumber").value = selectedConsolidationNumber;
}

function clearConsolidationNumber() {
	if ($("consolidationNumber") != null) {
		$("consolidationNumber").value = "";
	}
}

function transportationModeChanged() {
	if ($v("transportationMode") == 'Parcel') {
		$("consolidationNumberDiv").style.visibility = 'hidden';
		clearConsolidationNumber();
	}else {
		$("consolidationNumberDiv").style.visibility = 'visible';
	}
}