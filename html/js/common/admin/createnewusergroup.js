windowCloseOnEsc = true;

function onMyLoad() {
    var tcmisError = document.getElementById("tcmisError");
    if (tcmisError.value == "initialLoad") {
        //do nothing
    }else {
        if (tcmisError.value.length > 0) {
            alert(tcmisError.value);
        }else {
            returnToMain();
        }
    }
}

function createNewUserGroup() {
	var userGroupDesc = document.getElementById("userGroupDesc");
	var requestorName = document.getElementById("requestorName");
	if (userGroupDesc == null || userGroupDesc.value.length < 1) {
		alert(messagesData.missingData);
		return false;
	}else if (document.getElementById("userGroupType").value == "ReportPublish" && (requestorName == null || requestorName.value.length < 1)) {
		alert(messagesData.missingUser);
		return false;
	} else {
		var action = document.getElementById("action");
		action.value = "createNewUserGroup";
		return true;
	}
}

function returnToMain() {
  opener.reSearchForm();
  window.close();
}

function searchRequestor()
{
   loc = "searchpersonnelmain.do?displayElementId=requestorName&valueElementId=userList";
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