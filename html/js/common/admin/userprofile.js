//variable used by autocomplete to indicate if the given id is ready for use or not
var isAvailable = false;

function $(a) {
	return document.getElementById(a);
}

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
}

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

function openCompaniesPages() {
 var fullName = getSearchFrame().document.getElementById('lastName').value + ", "+
				getSearchFrame().document.getElementById('firstName').value;
 loc = "showcompanypermissions.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=display"+
 "&fullName="+encodeURI(getFullName());
 
 parent.children[parent.children.length] = openWinGeneric(loc,"companypermissionpage","650","600","yes","20","20","no");
}

function openPagesPages() {
 parent.children[parent.children.length] = openWinGeneric("pagepermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=display"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"pagepermissionpage","600","600","yes","25","25","no");
}

function openOperationsPages() {
 parent.children[parent.children.length] = openWinGeneric("operationspermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=init"+
	                                    "&fullName="+encodeURIComponent(getFullName())+"&companyId="+encodeURIComponent('Radian')//getSearchFrame().$v('companyId'))
                 ,"operationspermissionpage","1000","600","yes","25","25","no");
}

function openEntityPages() {
 parent.children[parent.children.length] = openWinGeneric("entitypermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=init"+
	                                    "&fullName="+encodeURIComponent(getFullName())+"&companyId="+encodeURIComponent('Radian')//getSearchFrame().$v('companyId'))
                 ,"entitypermissionpage","1000","600","yes","25","25","no");
}

function openFacilitiesPages() {
 parent.children[parent.children.length] = openWinGeneric("facilitypermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=display"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"facilitypermissionpage","800","600","yes","30","30","no");
}

function openFacilityLocalePages() {
 parent.children[parent.children.length] = openWinGeneric("facilitylocalepermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=display"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"facilitylocalepermissionpage","800","600","yes","30","30","no");
}

function openWorkareasPages() {
 parent.children[parent.children.length] = openWinGeneric("workareapermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=display"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"workareapermission","1000","600","yes","35","35","no");
}

function openCabinetsPages() {
	 parent.children[parent.children.length] = openWinGeneric("cabinetpermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=init"+
		                                    "&fullName="+encodeURI(getFullName())
	                 ,"cabinetpermission","600","600","yes","35","35","no");
}
	     
function openDocksPages() {         
 parent.children[parent.children.length] = openWinGeneric("dockpermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=display"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"dockpermission","600","600","yes","40","40","no");
}

function openWasteLocationPages() {
 parent.children[parent.children.length] = openWinGeneric("wastelocpermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&action=init"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"wastelocationpermission","600","600","yes","45","45","no");
}

function openUsergroupPages() {
 parent.children[parent.children.length] = openWinGeneric("usergrouppermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=init"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"usergrouppermission","600","600","yes","50","50","no");
}

function openCatelogPages() {
 parent.children[parent.children.length] = openWinGeneric("catelogpermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&action=init"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"catelogpermission","800","600","yes","55","55","no");
}

function openHubsPages() {
 parent.children[parent.children.length] = openWinGeneric("hubpermsmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&action=init"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"hubspermission","800","600","yes","60","60","no");
}

function openInventorygroupPages() {
 openWinGeneric("invgrouppermissionsmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&action=init"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"inventorygrouppermission","800","600","yes","65","65","no");
}

function openSupplierPages() {
 parent.children[parent.children.length] = openWinGeneric("supplierpermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&action=init"+
                                       "&logonId="+getSearchFrame().document.getElementById('logonId').value
                 ,"supplierpermission","800","600","yes","70","70","no");
}


function openSupplierLocationPages() {

 parent.children[parent.children.length] = openWinGeneric("supplierlocationpermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&action=init"+
                                       "&logonId="+getSearchFrame().document.getElementById('logonId').value
                 ,"supplierlocationpermission","800","600","yes","75","75","no");
}

function openFinancialApprover() {
 parent.children[parent.children.length] = openWinGeneric("financialapprovermain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=init"+
	                                    "&fullName="+encodeURI(getFullName())
                 ,"financialapprover","600","600","yes","80","80","no");
}

function openPoApprover() {
	 parent.children[parent.children.length] = openWinGeneric("poapprovermain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+"&uAction=init"+
		                                    "&fullName="+encodeURI(getFullName())
	                 ,"poapprover","700","600","yes","80","80","no");
	}

function openUserGroupPermission() {
 parent.children[parent.children.length] = openWinGeneric("usergrouppermissionmain.do?userGroupType=Approval&personnelId="+getSearchFrame().document.getElementById('personnelId').value+
										"&fullName="+encodeURI(getFullName())
                 ,"usergrouppermission","600","600","yes","80","80","no");
}

function openUserGroupReportPublishPermission() {
 parent.children[parent.children.length] = openWinGeneric("companydefusergrouppermissionmain.do?userGroupType=ReportPublish&personnelId="+getSearchFrame().document.getElementById('personnelId').value+
										"&fullName="+encodeURI(getFullName())
                 ,"companydefusergrouppermission","600","600","yes","80","80","no");
}

function openFacilityListApproval() {
 parent.children[parent.children.length] = openWinGeneric("listapprovalpermissionmain.do?personnelId="+getSearchFrame().document.getElementById('personnelId').value+
										"&fullName="+encodeURI(getFullName())
                 ,"listapprovalpermission","600","600","yes","80","80","no");
}

function lookupPersonnel() {
  loc = "searchpersonnelmain.do?";
  parent.children[parent.children.length] = openWinGeneric(loc,"lookuppersonnel","600","450","yes","50","50","20","20","no");
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

function createNewUser() {
	var errorMessage = messagesData.required+"\n\n";
	var requiredF = new Array(
			"firstName",
			"lastName",
			"email",
			"password",
			"confirmPassword",
			"newLogonId"
	);
	
	var focus = null;
	for(jj=0;jj< requiredF.length;jj++) {
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
	if(	$("password").value != $("confirmPassword").value )  {
		alert( messagesData.passwordEqual );
		$("confirmPassword").focus();
		return;
	}
	if (!isAvailable) {
		alert( messagesData.logonidexist.replace("{0}", $("newLogonId").value) );
		$("newLogonId").focus();
		return;
	}
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

function showNewUserView(retainInfo) {
	$('topLinks').innerHTML = '<a href="#" onClick="createNewUser()">'+messagesData.createUser+'</a>' +
								  ' | <a href="#" onClick="cancel()">'+messagesData.cancel+'</a>';
	$('logonIdLabel').innerHTML=$('logonIdLabel').innerHTML.trim()+"*";
	$('logonIdText').innerHTML= "<input class=\"inputBox\" type=\"text\" name=\"newLogonId\" id=\"newLogonId\" value=\"\" size=\"20\"/>";
	$('firstNameLabel').innerHTML=$('firstNameLabel').innerHTML.trim()+"*";
	$('lastNameLabel').innerHTML=$('lastNameLabel').innerHTML.trim()+"*";
	$('emailLabel').innerHTML=$('emailLabel').innerHTML+"*";
	$("personnelIdLabel").innerHTML=messagesData.password+": *";
	$("personnelIdText").innerHTML="<input class=\"inputBox\" type=\"password\" name=\"password\" id=\"password\" value=\"\" size=\"20\"/>";
	$("confirmPasswordLabel").style["display"]="";
	$("confirmPasswordText").style["display"]="";
	$('tableSearch').rows[6].style["display"]="none";
	$('tableSearch').rows[1].style["display"]="";
	$('companyLabel').style["display"]="";
	$('companyDropbox').style["display"]="";
	$('companyId').readonly=null;
	parent.document.getElementById('resultFrameDiv').style["display"]="none";
	
	if (!retainInfo) {
		$('firstName').value = "";
		$('lastName').value = "";
		$('midInitial').value = "";
		$('phone').value = "";
		$('fax').value = "";
		$('altPhone').value = "";
		$('email').value = "";
		if ($('printerLocation')) {
			$('printerLocation').selectedIndex = 0;
		}
	}
	
	var companyDropbox = $('companyId');
	var selectedCompany = companyDropbox.value;
	var companyIdArr = altCompanyId;
	var companyNameArr = altCompanyName;
	for (var i = companyDropbox.length; i > 0; i--) 
		companyDropbox[i] = null;
    var selectedI = 0;
	for(i=1; i< companyIdArr.length;i++) {
		var pos = i-1;
	    setOption(pos,companyNameArr[i],companyIdArr[i], "companyId");
	    if( companyIdArr[i] == selectedCompany )
	    	selectedI = pos;
	}
	companyDropbox.selectedIndex = selectedI;
	
	//autocomplete to show suggestions for available ids
	j$().ready(function() {
		function log(event, data, formatted) {
			$("newLogonId").className = "inputBox";
			isAvailable = true;
		} 

		j$("#newLogonId").autocomplete("/tcmIS/haas/lookuplogonid.do",{
			width: 200,
			cacheLength:0, //if cache is allow, when user manually enters one of the previous suggestions, the suggestion list will show the entered suggestion 
			scrollHeight: 150,
			formatItem: function(data, i, n, value) {
				//value sometimes include line break
				var formattedVal = value.replace(/(?:\r\n|\r|\n)/g, "");
				
				//since the backend algorithm will check input string and put it at the top of the list if it is available,
				//we only need to compare input string with first value of the suggestion
				if (i == 1)
					validateId(formattedVal);
				
				return  formattedVal;
			}
		});
		
		j$('#newLogonId').keyup(function () {
			//when user delete the whole input string in one go, we want to return the text box to normal state
			if ($("newLogonId").value.length == 0)
				$("newLogonId").className = "inputBox";
		});

		j$("#newLogonId").result(log).next().click(function() {
			j$(this).prev().search();
		});
	});
}

function myOnload() {
	setSearchFrameSize();

	var action = $('action');
	if (action.value == "") {
		action.value="showResult";
		document.genericForm.target="resultFrame";
		document.genericForm.submit();
	} else if (action.value == "newUser")
		showNewUserView(true);
		
	
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

function printTestLabel() {
	var select = document.getElementById('printerLocation');
	var loc = select.options[select.selectedIndex].text;
	if(loc == 'Please Select')
		alert(messagesData.pleaseSelectAHub);
	else
		parent.children[parent.children.length] = openWinGeneric("printtestlabels.do?loc="+ loc + "&action=search","PrintTestLabel","800","450","yes","50","50","no")  ;
}

function validateId(firstOption) {
	if (firstOption != $('newLogonId').value) {
		$("newLogonId").className = "inputBox red";
		isAvailable = false;
	} else {
		$("newLogonId").className = "inputBox";
		isAvailable = true;
	}
}
