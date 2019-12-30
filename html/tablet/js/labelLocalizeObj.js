
function messageListToLocalize(messageList) {
	if (messageList.length != 0) {
		console.log("translateList");
		$.ajax( {
			type : "POST",
			url : "../tcmIS/haas/tabletLocalizeLabel.do",
			data : ( {
				input : messageList
			}),
			cache : false,
			dataType : "text",
			success : localizeReturn
		});
	}
}

function localizeReturn(res) {
	var results = JSON.parse(res);

	if (results.Status != 'OK') {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + results.Message).css("color","red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	} else {
		translateLabelList = results.translatedList;
	}
	localizedList();
}