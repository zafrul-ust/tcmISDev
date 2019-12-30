function init() {
this.cfg = new YAHOO.util.Config(this);
if (this.isSecure)
{
 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
}

/*Yui pop-ups need to be initialized onLoad to make them work correctly.
I they are not initialized onLoad they tend to act weird*/
showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,
draggable:true, modal:false } );
showErrorMessagesWin.render();
}

function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	if(this.validateForm())
	{
		document.genericForm.target = 'resultFrame';
		document.getElementById("uAction").value = 'search';
		// set start search time
		var now = new Date();
		var startSearchTime = document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();
		var flag = validateForm();
		if (flag) {
			showPleaseWait();
			return true;
		}
		else {
			return false;
		}
	}
	else {
		return false;
	}
}
function validateForm() {
	  var result = true;
	  var index = $('searchWhat').selectedIndex;
	  if ((document.getElementById("searchText").value.length == 0) &&
			(document.getElementById("beginDate").value.length == 0) &&
			(document.getElementById("endDate").value.length == 0)) {
		  alert(messagesData.missingData);
		  result = false;
	  }
	  else if(index == 0 || index == 4)
		  {
			  if(!isInteger(document.getElementById("searchText").value,true))
			  {
			  alert(messagesData.search + " " + messagesData.searchInteger);
			  result = false;
			  }
		  }
	  return result;
	} //end of validateForm

function generateExcel(){
	  
	  if(validateForm()) {
		$('uAction').value = 'createExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ReceivingReportExcel','650','600','yes');
	    document.genericForm.target='_ReceivingReportExcel';
	    var a = window.setTimeout("document.genericForm.submit();",50);
	  }
	}