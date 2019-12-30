function initWin() {
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
	 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}

	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	I they are not initialized onLoad they tend to act weird*/
	showConfirmErrorMsgWin = new YAHOO.widget.Panel("confirmErrorMsgArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:false, visible:false,
	draggable:true, modal:false } );
	showConfirmErrorMsgWin.render();
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showConfirmErrorMessages()
{
  var confirmErrorMsgArea = document.getElementById("confirmErrorMsgArea");
  confirmErrorMsgArea.style.display="";

  showConfirmErrorMsgWin.show();
}

function showAuditError(msg)
{
	var errorMessagesAreaBody = document.getElementById("confirmErrorMsgAreaBody");
  errorMessagesAreaBody.innerHTML = msg;
  showConfirmErrorMsgWin.show();

  var errorMessagesArea = document.getElementById("confirmErrorMsgArea");
  errorMessagesArea.style.display="";
}

function myResultOnload()
{
	var updateErrorFlag = document.getElementById("updateErrorFlag");
	if (updateErrorFlag.value == 'Updated') {
		returnToMain();
	}else if (updateErrorFlag.value == 'Error') {
	    setTimeout('showConfirmErrorMessages()',200);    
		opener.getSearchFrame().dosearch();
	}
}

function confirmShipment() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("userAction");
    action.value = 'confirmshipment';
    submitOnlyOnce();
    document.supplierLocationForm.submit();
  }
}


function udpate() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("userAction");
    action.value = 'updateConfirmShipment';
    submitOnlyOnce();
    document.supplierLocationForm.submit();
  }
}


function validateForm() {
	var rowCount = document.getElementById("rowCount");
	var dataChecked = false;
  for(var i=0; i<rowCount.value; i++) {
    var checkbox = document.getElementById("checkbox" + i);
    if (checkbox.checked == true) {
	    dataChecked = true;
	    break;
    }
  }
	if (!dataChecked) {
		alert(messagesData.noRowChecked);
		return false;
	}
	if( document.getElementById('deliveredDate').value.length != 10 || document.getElementById('dateShipped').value.length != 10 ) {
		alert(messagesData.datesrequired);
		return false;
	}
    return true;
}

function isAnyRowChecked() {
  var rowCount = document.getElementById("rowCount");
  var rowChecked = false;
  //var resultdoc = getResultFrame();
  for(var i=0; i<rowCount.value && !rowChecked; i++) {
    var checkbox = document.getElementById("checkbox" + i);
    if(checkbox.checked == true) {
      rowChecked = true;
    }
  }
  return rowChecked;
}

function cancel() {
  window.close();
}

function returnToMain() {
  opener.getSearchFrame().dosearch();
  window.close();
}

function okClick(rowNum) {
	var ok = document.getElementById('checkbox'+rowNum);
	
	if( !ok.checked ) return;
	var sel = document.getElementById('currentCarrierName'+rowNum).value.trim();
	if( sel == 'Airgas Truck' ) 
		return;
	if( sel != '' && document.getElementById('currentTrackingNumber'+rowNum).value.trim() == '' ) {
		alert(messagesData.trackingNumberRequired);
		ok.checked = false;
		return false;
	}
}

function okCClick() {
	var updateErrorFlag = document.getElementById("updateErrorFlag");
/*	if (updateErrorFlag.value == 'Error') {
		returnToMain();
	}
	else
*/
	showConfirmErrorMsgWin.hide();
}