var children = new Array();

function init() { /*This is to initialize the YUI*/
  this.cfg = new YAHOO.util.Config(this);
  if (this.isSecure)
  {
    this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
  }

  /*Yui pop-ups need to be initialized onLoad to make them work correctly.
  If they are not initialized onLoad they tend to act weird*/
  errorMessagesAreaWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px",height:"200px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
  errorMessagesAreaWin.render();
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  errorMessagesAreaWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}


function update() {
    var runUpdate = true;
    var calendarAction = window.frames["resultFrame"].document.getElementById("calendarAction");
    var requestLineStatus = window.frames["resultFrame"].document.getElementById("requestLineStatus");
    var orderedQty = window.frames["searchFrame"].document.getElementById("orderedQty");
    var scheduledQty = window.frames["searchFrame"].document.getElementById("scheduledQty");
    var userViewType = window.frames["resultFrame"].document.getElementById("userViewType");  
    if (orderedQty.innerHTML.trim()*1 == scheduledQty.innerHTML.trim()*1) {
    	orderedQty.innerHTML = scheduledQty.innerHTML;
        window.frames["searchFrame"].document.getElementById("orderedQtyV").value = scheduledQty.innerHTML;
        window.frames["searchFrame"].document.getElementById("scheduledQtyV").value = scheduledQty.innerHTML;
		/*setting this so we can update rli.quantity */
        var totalOrderedQuantity = window.frames["resultFrame"].document.getElementById("totalOrderedQuantity");
        totalOrderedQuantity.value = orderedQty.innerHTML;
    }
    else {
    	if ( "entered" == $v("prStatus")) {
    		if (confirm(messagesData.orderedqtymismatch)) {
 	            orderedQty.innerHTML = scheduledQty.innerHTML;
 	            window.frames["searchFrame"].document.getElementById("orderedQtyV").value = scheduledQty.innerHTML;
 	            window.frames["searchFrame"].document.getElementById("scheduledQtyV").value = scheduledQty.innerHTML;
 			    /*setting this so we can update rli.quantity */
 	            var totalOrderedQuantity = window.frames["resultFrame"].document.getElementById("totalOrderedQuantity");
 	            totalOrderedQuantity.value = orderedQty.innerHTML;
 	        } else {
 	            runUpdate = false;
 	        }
    	}
    	else {
    		if (userViewType.value == "editMRQty" || userViewType.value == "approverEditMRQty") {
    			if (confirm(messagesData.orderedqtymismatch)) {
    	            orderedQty.innerHTML = scheduledQty.innerHTML;
    	            window.frames["searchFrame"].document.getElementById("orderedQtyV").value = scheduledQty.innerHTML;
    	            window.frames["searchFrame"].document.getElementById("scheduledQtyV").value = scheduledQty.innerHTML;
    			    /*setting this so we can update rli.quantity */
    	            var totalOrderedQuantity = window.frames["resultFrame"].document.getElementById("totalOrderedQuantity");
    	            totalOrderedQuantity.value = orderedQty.innerHTML;
    	        } else {
    	            runUpdate = false;
    	        }
    		}
    		else {
    			alert(messagesData.orderedqtyisdiffqtyscheduled);
    	        runUpdate = false;
    		}
    	}
    }
    if (calendarAction.value == 'calendar' )
    {
	    if(runUpdate) {
	        /*Set any variables you want to send to the server*/
	        var action = window.frames["resultFrame"].document.getElementById("action");
	        action.value = 'updateShowCalendar';
	        var updateCount = document.getElementById("updateCount");
	        
	        updateCount.value =1;
	        showPleaseWait();
	        window.frames['resultFrame'].document.getElementById('userChangedData').value = 'n';
	        /*Submit the form in the result frame*/
	        window.frames["resultFrame"].document.genericForm.submit();
        }
    }
    else
    {
	    if (runUpdate) {
	        //make sure that if user is adding a new row.  he/she must enter requested schedule date
	    	//and requested qty
	    	var auditResult = window.frames["resultFrame"].preUpdateAudit();
	    	if (auditResult.length == 0) {
	    		/*Set any variables you want to send to the server*/
	    		var action = window.frames["resultFrame"].document.getElementById("action");
	    		action.value = 'update';
	    		var updateCount = document.getElementById("updateCount");
	    		updateCount.value =1;
	        
	    		showPleaseWait();
	    		window.frames['resultFrame'].document.getElementById('userChangedData').value = 'n';
	        	/*Submit the form in the result frame*/
	    		window.frames["resultFrame"].document.genericForm.submit();
	    	}else if (auditResult == "missingData") {
	    		alert(messagesData.missingdata);
	    	}else if (auditResult == "partialDelivered") {
	    		alert(messagesData.partialDeliveredQtyError);
	    	}else if (auditResult == "increaseQtyError") {
	    		alert(messagesData.increaseQtyError);
	    	}
	    }
    }
} //end of method

function showCalendar() {
  var action = window.frames["resultFrame"].document.getElementById("action");
  action.value = 'showCalendar';
  showPleaseWait();
  /*Submit the form in the result frame*/
  window.frames["resultFrame"].document.genericForm.submit();
}

function showSummary() {
  var action = window.frames["resultFrame"].document.getElementById("action");
  action.value = 'showSummary';
  showPleaseWait();
  /*Submit the form in the result frame*/
  window.frames["resultFrame"].document.genericForm.submit();
}


function generateFrequency() {
    children[children.length] = window.frames["resultFrame"].generateFrequency();
}


function deleteNonDeliveredDate() {
  var tmp = document.getElementById("deleteUndo");
  var tmpLabel = messagesData.deleteNonDeliveredDate;
  if (tmpLabel == tmp.innerHTML.substr(0,tmp.innerHTML.indexOf('|')-1)) {
    window.frames["resultFrame"].deleteNonDeliveredDate();
    tmp.innerHTML = messagesData.undoDeleteData +" |";
  }else {
    window.frames["resultFrame"].undoDeletedData();
    tmp.innerHTML = messagesData.deleteNonDeliveredDate +" |";
  }
}

function refreshData(totalFreqQty)
{    
    var companyId = window.frames["resultFrame"].document.getElementById("companyId");
    var prNumber = window.frames["resultFrame"].document.getElementById("prNumber");
    var lineItem = window.frames["resultFrame"].document.getElementById("lineItem");
    var calendarAction = window.frames["resultFrame"].document.getElementById("calendarAction");
    var selectedRowId = document.getElementById("selectedRowId");

    var action = "";
    if (calendarAction.value == 'calendar' )
    {
    action = "showCalendar";
    }
    var loc = "deliveryschedulemain.do?source=materialRequest&action=reload&selectedRowId="+selectedRowId.value+"&companyId="+companyId.value+"&prNumber="+prNumber.value+"&lineItem="+lineItem.value+"";

    var orderedQtyV = window.frames["searchFrame"].document.getElementById("orderedQtyV");
    orderedQtyV.value = totalFreqQty;
    var scheduledQtyV = window.frames["searchFrame"].document.getElementById("scheduledQtyV");
    scheduledQtyV.value = totalFreqQty;

    var orderedQty = window.frames["searchFrame"].document.getElementById("orderedQty");
    orderedQty.innerHTML = totalFreqQty;
    var scheduledQty = window.frames["searchFrame"].document.getElementById("scheduledQty");
    scheduledQty.innerHTML = totalFreqQty;

    
    window.opener.document.getElementById("qty"+selectedRowId.value+"").value = totalFreqQty;
	 window.opener.setLineItemPrice(selectedRowId.value);
	 window.opener.setExtendedPrice();

	 showPleaseWait();
    var action = document.getElementById("action");
    action.value = "reload";
    window.location =loc;  
}

function updateGridQuantity()
{
    var orderedQtyV = window.frames["searchFrame"].document.getElementById("orderedQtyV");
    var scheduledQty = window.frames["searchFrame"].document.getElementById("scheduledQty");
    var selectedRowId = document.getElementById("selectedRowId");

    window.opener.document.getElementById("qty"+selectedRowId.value+"").value = scheduledQty.innerHTML;
	 window.opener.setLineItemPrice(selectedRowId.value);
	 window.opener.setExtendedPrice();
}

function returnToMr(X)
{//alert("userChangedData"+window.frames["resultFrame"].document.getElementById("userChangedData").value);
	var answer = false;
	if(window.frames["resultFrame"].document.getElementById("userChangedData").value == "y") {
		var text = messagesData.scheduledinformationchangedcontinue;
	 	var answer = confirm(text);
	}
	
	if(window.frames["resultFrame"].document.getElementById("userChangedData").value == "y" && answer == false) 
		return false;
	
	$("callReturnToMR").value = 'Y';

	if((window.frames["resultFrame"].document.getElementById("userChangedData").value == "y" && answer == true) || window.frames["resultFrame"].document.getElementById("userChangedData").value == "n") {
	    var orderedQtyV = window.frames["searchFrame"].document.getElementById("orderedQtyV");
	    var scheduledQtyV = window.frames["searchFrame"].document.getElementById("scheduledQtyV");
	    var requestedDateToDeliver0 = window.frames["resultFrame"].document.getElementById("requestedDateToDeliver0").value;
	    var selectedRowId = document.getElementById("selectedRowId");
	    var updateCount = document.getElementById("updateCount").value;

	    try
	    {
	        if (updateCount > 0)
	        {
	            window.opener.document.getElementById("qty"+selectedRowId.value+"").value = scheduledQtyV.value;
	            window.opener.setDeliverDate(requestedDateToDeliver0);
				window.opener.setLineItemPrice(selectedRowId.value);
		 		window.opener.setExtendedPrice();
			}
	    }
	    catch (ex)
	    {
	    }
	    
	    window.close();
	   
	    return true;
    } 
    
//    $("callReturnToMR").value = 'N';
    return true;
}

function closeAllchildren()
{
    var currentAction = document.getElementById("action").value;
    if (currentAction != 'reload')
    {
    	try {
     		window.frames["resultFrame"].closeAllchildren(); //??
     	} catch(ex) { }
// You need to add all your submit button vlues here. If not, the window will close by itself right after we hit submit button.	
		try {
			for(var n=0;n<children.length;n++) {
				children[n].closeAllchildren();
			}
		} catch(ex)
		{
		}
//Oh, and close self!

        if(!window.closed)
			window.close();
    }
    
    return false;
}

