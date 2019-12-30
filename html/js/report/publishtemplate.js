var dhxWins = null;

/*
function SubmitOnlyOnce() {
    if(!auditSave()){
        return false;
    }
    else {
        submitOnlyOnce();
    }
}
*/

function auditPublishSubmit() {
	$("calledFrom").value = "submitPublishTemplate";
	var result = true;

	if (publishIndividualTemplate) {
		if ($("usersTemplateView") != null) {
			if ($("usersTemplateView").checked) {
				if ($v("userIdList").length == 0) {
					result = false;
					alert(messagesData.enterUser);
				}
			}
		}
	}

	if (publishUserGroupTemplate) {
		if ($("userGroupsTemplateView") != null) {
			if ($("userGroupsTemplateView").checked) {
				var hasUserGroup = false;
				for(var i=0; i < $("userGroupIdArray").length;i++) {
					if( $("userGroupIdArray").options[i].selected ) {
						hasUserGroup = true;
					}
				}
				if (!hasUserGroup) {
					result = false;
					alert(messagesData.selectUserGroup);
				}
			}
		}
	}

	if (result) {
		document.publishTemplateForm.submit();
	}
}

function auditUnpublishSubmit() {
	$("calledFrom").value = "submitPublishTemplate";
	var result = true;

	if ($v("owner") == 'PERSONNEL_ID') {
		if ($("usersTemplateView") != null) {
			if ($("usersTemplateView").checked) {
				var tempVal = "";
				for(var i=0; i < $("userIdArray").length;i++) {
					if( $("userIdArray").options[i].selected ) {
						tempVal += "ok";
					}
				}
				if (tempVal.length == 0) {
					result = false;
					alert(messagesData.selectUser);
				}
			}
		}
	}

	if (result) {
		document.publishTemplateForm.submit();
	}
}

String.prototype.trim = function() {
   // skip leading and trailing whitespace
   // and return everything in between
   return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function closeWindow() {
    window.close();
}

function onMyLoad() {
	if (showErrorMessage) {
		alert($v("tcmISError"));
	}
	submitOk();

}

function submitOk() {
	if ($v("calledFrom") == "submitPublishTemplate" && $v("successFlag") == "Ok") {
		closeWindow();
	}
}

function searchRequestor()
{
   loc = "searchpersonnelmain.do?";
   openWinGeneric(loc,"searchRequestor","600","450","yes","50","50","20","20","no");
}

function personnelChanged(returnSelectedUserId,returnSelectedUserName) {
	if ($v("requestorName").length == 0) {
		$("requestorName").value = returnSelectedUserName;
		$("userIdList").value = returnSelectedUserId;
	}else {
		$("requestorName").value = $v("requestorName")+"; "+returnSelectedUserName;
		$("userIdList").value = $v("userIdList")+";"+returnSelectedUserId;
	}
}

function clearUsers() {
	$("requestorName").value = '';
	$("userIdList").value = '';
}

function companyViewChanged() {
	if ($("companyTemplateView").checked) {
		if (publishUserGroupTemplate) {
			if ($("userGroupsTemplateView") != null) {
				$("userGroupsTemplateView").checked = false;
				$("userGroupsTemplateView").disabled = true;
				$("userGroupIdArray").disabled = true;
			}
		}
		if (publishIndividualTemplate) {
			if ($("usersTemplateView") != null) {
				$("usersTemplateView").checked = false;
				$("usersTemplateView").disabled = true;
			}
			if ($v("action") == 'publish') {
				$("requestorName").disabled = true;
				$("selectedRequestor").disabled = true;
				$("clearB").disabled = true;
			}else {
				if ($("userIdArray") != null) {
					$("userIdArray").disabled = true;
				}
			}
		}
	}else {
		if (publishUserGroupTemplate) {
			if ($("userGroupsTemplateView") != null) {
				$("userGroupsTemplateView").disabled = false;
				$("userGroupIdArray").disabled = false;
			}
		}
		if (publishIndividualTemplate) {
			if ($("usersTemplateView") != null) {
				$("usersTemplateView").disabled = false;
			}
			if ($v("action") == 'publish') {
				$("requestorName").disabled = false;
				$("selectedRequestor").disabled = false;
				$("clearB").disabled = false;
			}else {
				if ($("userIdArray") != null) {
					$("userIdArray").disabled = false;
				}
			}
		}
	}
}


