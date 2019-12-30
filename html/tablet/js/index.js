var userMenuHasReimageRequests = false;
var i18nloginlabel = null;
var dataMissingMessage = null;
var indexMessages = null;

var entityMap = {
	"&" : "&amp;",
	"<" : "&lt;",
	">" : "&gt;",
	'"' : '&quot;',
	"'" : '&#39;',
	"/" : '&#x2F;'
};

function escapeHtml(string) {
	return String(string).replace(/[&<>"'\/]/g, function(s) {
		return entityMap[s];
	});
}

function verifySession(res) {
	var results = new responseObj(res, true);
	$.mobile.loading('hide');
	if (!results.isOK()) {
		sessionStorage.clear();
		getLocaleOptions();
		localizeMessageList();
		$.mobile.changePage('#login');
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		localizeMessageList();
		var hubs = sessionStorage.getItem("hubs");
		if (hubs && hubs.length > 1) {
			$('#userId').html(sessionStorage.getItem("userName") + " &#183; <span id='currentHubSpan'>" + sessionStorage.getItem("currentHubName") + "</span>");
			$("#currentHubSpan").click(function(e) {
				changeHub();
			});

		}
		else {
		$('#userId').html(sessionStorage.getItem("userName") + " &#183; " + sessionStorage.getItem("currentHubName"));
		}
		if (window.location.hostname != 'www.tcmis.com') {
			document.getElementById('environment').innerHTML = indexMessages.tablet_environment + " " + window.location.hostname;
			document.getElementById('environment').style['display'] = "";
		}
		$.mobile.changePage('#springboard');
		parseMenuList(results.Pages);
		if (userMenuHasReimageRequests) {
			checkForExistingReimageRequests();
		}
	}
}

function loginReturn(res) {
	var results = new responseObj(res);
	$('#password').val("");
	if (!results.isOK()) {
		$.mobile.loading('hide');
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();

	}
	else if (results.Pages.length == 0) {
		$.mobile.loading('hide');
		$("#noPermMessagesArea").show();
		$('#menu').hide();
	}
	else {
		sessionStorage.setItem("userId", results.LogonId);
		sessionStorage.setItem("userName", results.userName);

		parseMenuList(results.Pages);
		promptUserForHub(results.hubs);
	}
}

function checkForExistingReimageRequests() {
	var currentHub = sessionStorage.getItem("currentHub");
	$.post("/tcmIS/haas/tabletReImageRequestsCheck.do", {
		"hub" : currentHub
	}, toggleReimageRequests);
}

function toggleReimageRequests(res) {
	var results = new responseObj(res);
	debug("toggleReimageRequests -> " + results.Exists);
	if (results.Exists) {
		$('#tabletReImageRequests').show();
	}
}

function parseMenuList(pageIds) {
	debug("parseMenuList");
	$('#menu').empty();
	userMenuHasReimageRequests = false;

	for ( var i = 0; i < pageIds.length; i++) {
		$('#menu').append($('<li/>', { // here appending `<li>`
			'id' : pageIds[i].pageId
		}).append($('<a/>', { // here appending `<a>` into `<li>`
			'href' : pageIds[i].pageUrl,
			'rel' : 'external'
		}).append('<img src="images/' + pageIds[i].iconImage + '"/><h2>' + pageIds[i].pageNameFromLocale + '</h2><p/>')));

		if ("tabletReImageRequests" == pageIds[i].pageId) {
			$('#tabletReImageRequests').hide();
			userMenuHasReimageRequests = true;
		}
	}

	$.mobile.changePage($('#springboard'));
	$('#menu').listview('refresh');
	$('#menu').show();

	if (window.location.hostname != 'www.tcmis.com') {
		$('#environment').html(indexMessages.tablet_environment + " " + window.location.hostname);
		$('#environment').show();
	}

}

function promptUserForHub(hubs) {
	debug("promptUserForHub");
	var hubList = new String();
	sessionStorage.setItem("hubs", JSON.stringify(hubs));
	if (hubs.length == 0) {
		$.mobile.changePage('#login');
		$("#errorMessagesArea").html(indexMessages.tablet_nohubpermissions_message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else if (hubs.length == 1) {
		sessionStorage.setItem("currentHub", hubs[0].hubId);
		sessionStorage.setItem("currentHubName", hubs[0].hubName);
		$('#userId').html(sessionStorage.getItem("userName") + " &#183; " + sessionStorage.getItem("currentHubName"));
	}
	else {
		for ( var x = 0; x < hubs.length; x++) {
			hubList += "<li data-hubId='" + hubs[x].hubId + "' data-hubName='" + escapeHtml(hubs[x].hubName) + "'>";
			hubList += hubs[x].hubName;
			hubList += "</li>";
		}
		$("#hubList").html(hubList);
		$("#hubList").listview("refresh");
		$("#hubList li").click(function(e) {
			selectHub(e);
		});

		$("#selectHubPopup").popup({
			dismissible : false,
			y : 0
		});
		$("#selectHubPopup").popup("open");
	}
}

function changeHub() {
	var hubList = new String();
	var hubs = JSON.parse(sessionStorage.getItem("hubs"));
	for ( var x = 0; x < hubs.length; x++) {
		hubList += "<li data-hubId='" + hubs[x].hubId + "' data-hubName='" + escapeHtml(hubs[x].hubName) + "'>";
		hubList += hubs[x].hubName;
		hubList += "</li>";
	}
	$("#hubList").html(hubList);
	$("#hubList").listview("refresh");
	$("#hubList li").click(function(e) {
		selectHub(e);
	});

	$("#selectHubPopup").popup({
		dismissible : false,
		y : 0
	});
	$("#selectHubPopup").popup("open");
}

function selectHub(e) {
	debug("selectHub");
	sessionStorage.setItem("currentHub", $(e.currentTarget).attr("data-hubId"));
	sessionStorage.setItem("currentHubName", $(e.currentTarget).attr("data-hubName"));
	$('#userId').html(sessionStorage.getItem("userName") + " &#183; <span id='currentHubSpan'>" + sessionStorage.getItem("currentHubName") + "</span>");
	$("#currentHubSpan").click(function(e) {
		$("#selectHubPopup").popup("open");
	});

	$("#selectHubPopup").popup("close");
	if (userMenuHasReimageRequests) {
		checkForExistingReimageRequests();
	}
}

function logOut() {
	debug("logOut");
	sessionStorage.clear();
	getLocaleOptions();
	$.mobile.changePage('#empty');
	$("#noPermMessagesArea").hide();
	$("#errorMessagesArea").hide();
	$.post("/tcmIS/haas/logout.do");
	$.mobile.changePage('#login');
}

function getLocaleOptions() {
	$.post("/tcmIS/haas/tabletLocale.do", loadLanguageOptions);
}

function loadLanguageOptions(res) {
	debug("loadLanguageOptions");
	var results = new responseObj(res);
	$.mobile.loading('hide');

	if (results.isError()) {
		sessionStorage.clear();
		$.mobile.changePage('#login');
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		var locales = results.locales;
		i18nloginlabel = new Array();
		var localeList = "";
		for ( var x = 0; x < locales.length; x++) {
			localeList += "<option value='" + locales[x].localeCode + "'>";
			localeList += locales[x].localeDesc;
			localeList += "</option>";

			var localeLabels = locales[x].localeLabels;
			i18nloginlabel[locales[x].localeCode] = {
				label_login : localeLabels.logonid,
				label_pass : localeLabels.password,
				label_lang : localeLabels.language,
				btn_login : localeLabels.login,
				label_nopermission : localeLabels.nopermission,
				label_nounamepwd : localeLabels.nounamepwd
			};
		}
		// builds the select box
		$("#locale").empty();
		$("#locale").append(localeList);
		var lastLocale = localStorage.getItem("locale");
		if (lastLocale && lastLocale != results.defaultLocale) {
			$("#locale").val(lastLocale);
			localeChange();
		}
		else {
			$("#locale").val(results.defaultLocale);
		}
		$("#locale").selectmenu('refresh', true);
	}
}

function localeChange() {
	localeSelected = $("#locale").val();
	localStorage.setItem("locale", localeSelected);
	if (i18nloginlabel == null)
		return;
	localizeMessageList(localeSelected);
	$("#label_login").html(i18nloginlabel[localeSelected].label_login);
	$("#label_pass").html(i18nloginlabel[localeSelected].label_pass);
	$("#label_lang").html(i18nloginlabel[localeSelected].label_lang);
	$("#label_btnlogin").html(i18nloginlabel[localeSelected].btn_login);
	$("#label_nopermission").html(i18nloginlabel[localeSelected].label_nopermission);
	dataMissingMessage = i18nloginlabel[localeSelected].label_nounamepwd;
}

function localizeMessageList(locale) {
	debug("localizeMessageList");
	var messages = sessionStorage.getItem("localizedIndexMessages");

	if (messages == null || locale) {
		var callArgs = new Object();
		callArgs.input = "tablet_selecthub,label_logout,tablet_environment,tablet_nohubpermissions_message,tablet_sessionexpired,label_username,label_cancel,label_password,label_ok,tablet_pleaseenteruname,tablet_pleaseenterpwd";
		if (locale) {
			callArgs.locale = locale;
		}
		$.post("/tcmIS/haas/tabletLocalizeLabel.do", $.param(callArgs), localizeMessageListReturn);
	}
	else {
		indexMessages = JSON.parse(messages);
		applyLocalizedMessages();
	}
}

function localizeMessageListReturn(res) {
	var results = new responseObj(res);

	if (results.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		indexMessages = results.localizedList;
		sessionStorage.setItem("localizedIndexMessages", JSON.stringify(indexMessages));
		applyLocalizedMessages();
	}
}

function applyLocalizedMessages() {
	debug("applyLocalizedMessages");
	$("#label_selecthub").html(indexMessages.tablet_selecthub);
	$("#label_logout").html(indexMessages.label_logout);
}
