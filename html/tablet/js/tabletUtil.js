if (window.location.hostname != 'www.tcmis.com' && window.location.hostname != 'qa2.tcmis.com') {
	window.debug = function(text) {
		console.log(text);
	};
}
else {
	window.debug = function(text) {
	};
}

if (new RegExp(".*MotorolaWebKit.*").test(navigator.userAgent)) {
	window.isMotorola = function() {
		return true;
	};
}
else {
	window.isMotorola = function() {
		return false;
	};
}

function ifDefined(value) {
	if (typeof (value) != "undefined" && value != "undefined") {
		return value;
	}
	return "";
}

function checkSession() {
	$.post("/tcmIS/haas/tabletSessionCheck.do", function(data) {
		new responseObj(data);
	});
}

function convertDateToTimeStamp(dateString) {
	var callArgs = new Object();
	callArgs.date = dateString;
	return $.Deferred(function(def) {
		$.post("/tcmIS/haas/tabletGetTimestamp.do", $.param(callArgs), function(data) {
			var response = new responseObj(data);
			if (response.isOK()) {
					def.resolve(response.Timestamp);
			}
			else {
				alert("Error: " + response.Message);
				def.resolve("");
			}
		});
	});
}

function refreshSession() {
	var refreshPopup = new String;

	var messages = JSON.parse(sessionStorage.getItem("localizedMessages"));

	refreshPopup = "<div data-role='popup' id='refreshPopupDiv' class='ui-content' data-overlay-theme='a'>";
	refreshPopup += "<div><h2>" + messages.tablet_sessionexpired + "</h2></div>";
	refreshPopup += "<div id='refreshPopupErrText' style='color:red;text-align:center'></div>";
	refreshPopup += "<div class='ui-grid-a'>";
	refreshPopup += "<div class='ui-block-a ui-content' style='padding-right:20px; padding-left:20px;'>";
	refreshPopup += "<label for='refreshUserName'>" + messages.label_username + ": </label>";
	refreshPopup += "<input type='text' id='refreshUserName'></input>";
	refreshPopup += "<button type='button' data-role='button' id='refreshCnx'>" + messages.label_cancel + "</button>";
	refreshPopup += "</div>";
	refreshPopup += "<div class='ui-block-b ui-content' style='padding-right:20px; padding-left:20px;'>";
	refreshPopup += "<label for='refreshPassword'>" + messages.label_password + ": </label>";
	refreshPopup += "<input type='password' id='refreshPassword'></input>";
	refreshPopup += "<button type='button' data-role='button' id='refreshOk'>" + messages.label_ok + "</button>";
	refreshPopup += "</div>";
	refreshPopup += "</div>";
	refreshPopup += "</div>";

	if (isMotorola()) {
		sip.automatic();
	}

	$.mobile.activePage.append(refreshPopup);
	$("#refreshPopupDiv").trigger("create");
	$("#refreshPopupDiv").popup();
	$("#refreshOk").on("click", function() {
		if (ifDefined($("#refreshUserName").val()) == "") {
			$("#refreshPopupErrText").html(messages.tablet_pleaseenteruname);
			return;
		}
		else if (ifDefined($("#refreshPassword").val()) == "") {
			$("#refreshPopupErrText").html(messages.tablet_pleaseenterpwd);
			return;
		}

		$.mobile.loading('show', {
			text : 'Refreshing Session'
		});

		var args = new Object;
		args.logonId = $("#refreshUserName").val();
		args.password = $("#refreshPassword").val();
		args.locale = localStorage.getItem("locale");
		$.post("/tcmIS/haas/tabletLogin.do", $.param(args), function(data) {
			$.mobile.loading('hide');
			authReturn = $.parseJSON(data);
			if (authReturn.Status == "OK") {
				if (isMotorola()) {
					sip.manual();
				}
				$("#refreshPopupDiv").popup("close");
				$("#refreshPopupDiv").remove();
				return;
			}
			else {
				$("#refreshPopupErrText").html(authReturn.Message);
				return;
			}
		});
	});
	$("#refreshCnx").on("click", function(e) {
		$("#refreshPopupDiv").popup("close");
		$("#refreshPopupDiv").remove();
		window.location.href = "/tablet/index.html";
	});
	$("#refreshPopupDiv").popup("open");
}

function getServerDateTime(type) {
	var thisReturn;
	$.ajax({
		url : '/tcmIS/haas/tabletTime.do',
		type : "GET",
		async : false,
		timeout : 1000,
		success : function(data) {
			var response = $.parseJSON(data);
			if (response.Status == "OK") {
				sessionStorage.setItem("indefinite", response.Indefinite);
				thisReturn = _.omit(response, "Status");
			}
			else if (response.Status == "SESSION EXPIRED") {
				refreshSession();
			}
			else {
				thisReturn = "Error";
			}
		},
		error : function() {
			thisReturn = "Error";
		}
	});
	if (thisReturn == "Error") {
		return thisReturn;
	}
	else {
		return thisReturn[type];
	}
}

function showQualityControlItemNote(note) {
	if (note && note.length > 0) {
		var localmessages =  sessionStorage.getItem("localizedBinMessages");
		if (!localmessages) {
			localmessages =  sessionStorage.getItem("localizedReceivingMessages");
		}
		var messages = JSON.parse(localmessages);
		var qciPopup = "<div data-role='popup' id='qciPopupDiv' class='ui-content' data-overlay-theme='a'>";
		qciPopup += "<div id='qciPopupErrText' style='color:red;text-align:center'>" + note + "</div>";
		qciPopup += "<div style='display:block;text-align:center;'>";
		qciPopup += "<button type='button' data-role='button' id='qicOK' style='margin:0 auto; display:block;'>" + messages.label_ok + "</button>";
		qciPopup += "</div>";
		qciPopup += "</div>";

		$.mobile.activePage.append(qciPopup);
		$("#qciPopupDiv").trigger("create");
		$("#qciPopupDiv").popup();
		$("#qicOK").on("click", function(e) {
			$("#qciPopupDiv").popup("close");
			$("#qciPopupDiv").remove();
		});
		$("#qciPopupDiv").popup("open");
	}
}

function getQualityControlItemNote(itemId, inventoryGroup) {
	var callArgs = new Object();
	callArgs.itemId = itemId;
	callArgs.inventoryGroup = inventoryGroup;

	$.post("/tcmIS/haas/tabletQualityControlItemLabel.do", $.param(callArgs), function(data) {
		var response = new responseObj(data);
		if (response.isError()) {
			$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + response.Message).css("color", "red").css('font-size', '20px');
			$("#errorMessagesArea").show();
		}
		else {
			showQualityControlItemNote(response.QualityControlItemLabel);
		}
	});

}
