function initWin() {
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
	 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}

	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	I they are not initialized onLoad they tend to act weird*/
	showConfirmErrorMsgWin = new YAHOO.widget.Panel("confirmErrorMsgArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,
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

function showAuditError()
{
	var errorMessagesAreaBody = document.getElementById("confirmErrorMsgAreaBody");
  errorMessagesAreaBody.innerHTML = messagesData.noRowChecked;
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
	}
}

function confirmShipment() {
  var flag = validateForm();
  var shippedDate = document.getElementById("deliveredDate").value;
  var confirmMsg = messagesData.pleaseconfirmshipeddate + " " + shippedDate + ".";
  
  if(flag) {
    if (confirm(confirmMsg)) {
      var action = document.getElementById("action");
      action.value = 'confirmShipment';
      submitOnlyOnce();
      document.genericForm.submit();
    }
    else {
      return false;
    }
  }
}


function validateForm() {
	var rowCount = document.getElementById("rowCount");
	var deliveredDate = document.getElementById("deliveredDate");
	var dataChecked = false;
	var errorMessage = messagesData.validvalues+ "\n\n";
  if (deliveredDate.value.trim().length == 0) {
     errorMessage = errorMessage + " "+messagesData.dateshipped+"\n" ;
     alert(errorMessage);
     return false;
  }
  for(var i=0; i<rowCount.value; i++) {
    var checkbox = document.getElementById("checkbox" + i);
    if (checkbox.checked == true) {
	    dataChecked = true;
	    break;
    }
  }
	if (!dataChecked) {
		showAuditError();
		return false;
	}else {
    return true;
	}
}

function checkall()
{
    var checkall = document.getElementById("chkAll");
    var totallines = document.getElementById("rowCount").value;
    totallines = totallines*1;
    var result ;
    if (checkall.checked)
        result = true;
    else
        result = false;

    var errorMsg = "";
    for(var i=0; i<totallines; i++) {
      var checkbox = document.getElementById("checkbox" + i);
      if (result) {
          var trackSerialNumber = document.getElementById("trackSerialNumber" + i);
          var missingSerialNumber = document.getElementById("missingSerialNumber" + i);
          if (trackSerialNumber.value == 'Y' && missingSerialNumber.value == 'Y') {
              errorMsg += "\t";
              errorMsg += document.getElementById("leadTrackingNumber" + i).value;
              errorMsg += "\n";
          }else
              checkbox.checked = result;
      }else
          checkbox.checked = result;
    }
    //is missing serial number
    if (errorMsg.length > 0) {
        alert(messagesData.missingSerialNumber.replace(/\{0\}/g, messagesData.trackingNumber).replace(/\{1\}/g, messagesData.shipConfirmed)+"\n"+errorMsg);
        result = false;
    }
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
  opener.frames["searchFrame"].reSearchForm();  
  window.close();
}

function checkSerialNumber(selectedRow) {
    var checkbox = document.getElementById("checkbox" + selectedRow);
    if (checkbox.checked) {
        var trackSerialNumber = document.getElementById("trackSerialNumber" + selectedRow + "");
        var missingSerialNumber = document.getElementById("missingSerialNumber" + selectedRow + "");
        if (trackSerialNumber.value == 'Y' && missingSerialNumber.value == 'Y') {
            alert(messagesData.missingSerialNumber.replace(/\{0\}/g, messagesData.trackingNumber).replace(/\{1\}/g, messagesData.shipConfirmed)+"\n\t"+document.getElementById("leadTrackingNumber" + selectedRow + "").value);
            checkbox.checked = false;
        }
    }
}