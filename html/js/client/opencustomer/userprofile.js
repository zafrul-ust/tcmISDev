/* Don't know why this function is here
function validateForm() {
  if(!isInteger(document.genericForm.itemId.value.trim(), true)) {
    alert(messagesData.itemInteger);
    return false;
  }
  if(!isInteger(document.genericForm.unitCostMin.value.trim(), true)) {
    alert(messagesData.moneyInteger);
    return false;
  }
  if(!isInteger(document.genericForm.unitCostMax.value.trim(), true)) {
    alert(messagesData.moneyInteger);
    return false;
  }
  if(!isInteger(document.genericForm.receiptDaySpan.value.trim(), true)) {
    alert(messagesData.daysInteger);
    return false;
  }
  if(!isInteger(document.genericForm.inventoryValueMin.value.trim(), true)) {
    alert(messagesData.moneyInteger);
    return false;
  }
  if(!isInteger(document.genericForm.inventoryValueMax.value.trim(), true)) {
    alert(messagesData.moneyInteger);
    return false;
  }
  if(!isInteger(document.genericForm.binCountDays.value.trim(), true)) {
    alert(messagesData.daysInteger);
    return false;
  }
  return true;
}  */

function search() {
  var flag = validateForm();
  if(!flag)  {
	alert(errorMessage);
	return false;
  }
  else
  {
   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
  	return true;
  }
}

function showSearchAreaPleaseWait() {
	document.getElementById("contentArea").style["display"]="none";
	document.getElementById("transitPage").style["display"]="";
}

function updatePersonalInfo() {
	showSearchAreaPleaseWait();
	document.genericForm.target="searchFrame";
	document.getElementById('action').value="update";
	parent.document.getElementById('resultFrameDiv').style["display"]="none";
	document.genericForm.submit();
}

function showErrorMessages()
{
		parent.showErrorMessages();
}

function getFullName() {
 var fullName = getSearchFrame().document.getElementById('lastName').value + ", "+
				getSearchFrame().document.getElementById('firstName').value;
 return fullName;
}

function openStartupPages() {
 parent.children[parent.children.length] = openWinGeneric("startuppages.do?personnelId="+document.getElementById('personnelId').value+"&action=init"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"startuppage","600","600","yes","20","20","no");
}

function openCustomersPages() {
 var fullName = getSearchFrame().document.getElementById('lastName').value + ", "+
				getSearchFrame().document.getElementById('firstName').value;
 parent.children[parent.children.length] = openWinGeneric("customerpermissions.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&action=init"+
                                       "&fullName="+encodeURI(getFullName())
                 ,"customermissionpage","675","600","yes","20","20","no");
}

function openShiptoPages() {
 parent.children[parent.children.length] = openWinGeneric("shiptopermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=init"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"workareapermission","760","600","yes","35","35","no");
}


function changePersonnel() {
  loc = "searchpersonnelmain.do?";
  parent.children[parent.children.length] = openWinGeneric(loc,"changepersonnel","600","450","yes","50","50","20","20","no");
}

function chooseDefaultCustomer() {
  loc = "defaultcustomer.do?";
  parent.children[parent.children.length] = openWinGeneric(loc,"defaultcustomer","350","400","yes","50","50","20","20","no");
}

function changePassword() {
  loc = "changepassword.do?";
  parent.children[parent.children.length] = openWinGeneric(loc,"changepassword","350","250","yes","50","50","20","20","no");
}

function resetPassword() {
  loc = "resetpassword.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value;
  parent.children[parent.children.length] = openWinGeneric(loc,"resetpassword","350","250","yes","50","50","20","20","no");
}

function personnelChanged(pid) {
	showSearchAreaPleaseWait();
	document.getElementById("personnelId").value = pid;
	document.getElementById('action').value="change";
	document.genericForm.target="searchFrame";
	parent.document.getElementById('resultFrameDiv').style["display"]="none";
	document.genericForm.submit();
}

var topLink = null;
function createNewUser() {
	var errorMessage = messagesData.required+"\n\n";;
	var requiredF = new Array( 	"firstName",
					"lastName",
					"email",
					"defaultCustomer",
					"newLogonId" );
	var focus = null;
	for(var jj=0;jj< requiredF.length;jj++) {
  		fieldName  = requiredF[jj];
  		dataField  = $(fieldName);
  		dataField.value = dataField.value.trim();
  		dataValue  = dataField.value;
		if( dataValue.length == 0  ) {
			errorMessage += requiredFTitle[jj]+"\n" ;
		  	if( focus == null )
		  	 	focus = dataField;
		}
	}
	
	if( focus != null ) {
		alert( errorMessage );
		focus.focus();
		return;
	}
/*	
	if(	$("password").value != $("confirmPassword").value )  {
		alert( messagesData.passwordEqual );
		$("confirmPassword").focus();
		return;
	}  */
	
	showSearchAreaPleaseWait();
	document.genericForm.target="searchFrame";
	document.getElementById('action').value="newUser";
	document.genericForm.submit();
}

function cancel() {
	showSearchAreaPleaseWait();
	document.genericForm.target="searchFrame";
	document.getElementById('action').value="login";
	document.genericForm.submit();
}

function newUser() {
	if(topLink == null ) topLink = $('DIV_topLinks').innerHTML;

	$('DIV_topLinks').innerHTML = '<a href="#" onClick="createNewUser()">'+messagesData.createUser+'</a>' +
								  ' | <a href="#" onClick="cancel()">'+messagesData.cancel+'</a>';
	$('DIVlogonId').innerHTML= "<input class=\"inputBox\" type=\"text\" name=\"newLogonId\" id=\"newLogonId\" value=\"\" size=\"6\"/>";
	$('DIVlookupId').innerHTML= "";
//	$('DIVButtonsId').innerHTML= "";
	$('firstName').value = "";
	$('lastName').value = "";
	$('midInitial').value = "";
	$('phone').value = "";
	$('fax').value = "";
	$('altPhone').value = "";
	$('email').value = "";
//	$('printerLocation').selectedIndex = 0;
	$("DIVPasswordLabel").style["display"]="none";
	$("DIVPassword").style["display"]="none";
	$("DIVComfirmPasswordLabel").style["display"]="none";
	$("DIVComfirmPassword").style["display"]="none";
	$('DIVlogonIdLabel').style["display"]="none";
	$('DIVPersonnelId').style["display"]="none";
	$('tableSearch').rows[5].style["display"]="none"; // Hide Startup Pages btn
	$('defaultcustomer1').style["display"]="";
	$('defaultcustomer2').style["display"]="";
//	$('tableSearch').rows[1].style["display"]="";  -->company dropdown
//	$('DivCompanyLabel').style["display"]="";
//	$('DivCompany').style["display"]="";
//	$('companyId').readonly=null;
	parent.document.getElementById('resultFrameDiv').style["display"]="none";
/*	var com = $('companyId');
	var comValue = com.value;
	var comId   = altCompanyId;
	var comName   = altCompanyName;
	for (var i = com.length; i > 0; i--) 
    		com[i] = null;
    var selectedI = 0;
	for(i=1; i< comId.length;i++) {
		var pos = i-1;
	    setOption(pos,comName[i],comId[i], "companyId");
	    if( comId[i] == comValue ) selectedI = pos; 
	}
	com.selectedIndex = selectedI;  */
}

function myOnload() {
	setSearchFrameSize();

	document.getElementById('action').value="showResult";
	document.genericForm.target="resultFrame";
	document.genericForm.submit();
	
	if( window.dontShowButtom != null && window.dontShowButtom ) {
//		parent.document.getElementById('resultFrameDiv').style["display"]="none";
//		parent.document.getElementById('resultFrameDiv').innerHTML="";
	}
//	else 
	{
//		parent.document.getElementById('resultFrameDiv').style["display"]="";
//		getResultFrame().setResultFrameSize();
	}
	if( showErrorMessage ) {
		getResultFrame().document.getElementById('errorMessagesAreaBody').innerHTML = 
						 document.getElementById('errorMessagesAreaBody').innerHTML;
		parent.showErrorMessages();
	}
}

function myResultOnLoad() {
   var showResultFrame = document.getElementById('showResultFrame');
	if (showResultFrame.value == 'false') {
		parent.document.getElementById('resultFrameDiv').style["display"]="none";
	}else {
		//this won't work if user resize screen
		//parent.document.getElementById('resultFrameDiv').style["display"]="";
		setResultFrameSize();
	}
}

function changeLang() {
	showSearchAreaPleaseWait();
	document.getElementById('action').value="changeLang";
	document.genericForm.target="searchFrame";
	parent.document.getElementById('resultFrameDiv').style["display"]="none";
	document.genericForm.submit();
}
