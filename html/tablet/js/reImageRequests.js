var currentReceiptId;
var ImageData = "";
var flashState = false;
var reImageImageList = new reImageListObj();
var reImageMessages = null;

function retrieveReImageRequests() {
	debug("retrieveReImageRequests");
	var currentHub = sessionStorage.getItem("currentHub");
	$.mobile.loading('show', {text: 'Retreiving ReImage Requests'});
	$.post("/tcmIS/haas/tabletReImageRequests.do", {"hub" : currentHub}, displayReImageRequests);
}

function displayReImageRequests(data) {
	debug("displayReImageRequests");
	var response = new responseObj(data);
	if (response.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + response.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		parseRequests(response.ReceiptStatuses);
	}
	$.mobile.loading('hide');
}

function parseRequests(statuses, createMenuList) {
	debug("parseRequests");
	reImageImageList = new reImageListObj();
	$("#reImageImageListView li").remove();
	$("#reImageImageTag").attr("src", "");
	$("#reImageImageTag").css("display", "none");
	$('#reImageRequests').empty();

	if (statuses.length == 0) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + reImageMessages.tablet_noRcptsinReImagestatus).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		for ( var i = 0; i < statuses.length; i++) {
			var rec = statuses[i];
			var displayText = "<img src='/tcmIS/haas/thumbnail.do?image=" + rec.imageUrl + "'/>";
			displayText += "<table width='100%' class='reImageTable'><tr><td>" + reImageMessages.tablet_receiptid + ": " + rec.receiptId;
			displayText += "</td><td align='center'>" + reImageMessages.tablet_itemid + ": " + rec.itemId;
			displayText += "</td><td align='center'>" + reImageMessages.tablet_location + ": " + rec.bin;
			displayText += "</td><td align='right'>" + reImageMessages.tablet_arrivalscanuser + ": " + rec.arrivalScanUserName;
			displayText += "</td></TR><TR><td colspan='4'>" + reImageMessages.tablet_inventorygroup + ": " + rec.inventoryGroupName;
			displayText += "</td></TR><TR><td colspan='4'>" + rec.itemDesc;
			if (rec.internalReceiptNotes) {
				displayText += "</td></TR><TR><td colspan='4' style='color: red;'>" + reImageMessages.tablet_internalnotes + ": " + rec.internalReceiptNotes;
			}
			if (rec.receivingNotes) {
				displayText += "</td></TR><TR><td colspan='4'>" + reImageMessages.tablet_notes + ": " + rec.receivingNotes;
			}

			displayText += "</td></TR></table>";

			$('#reImageRequests').append($('<li/>', {
				'receipt-id' : rec.receiptId,
				'data-theme' : 'b'
			}).append(displayText));
		}
		$("#reImageRequests li").on("click", function(event) {
			event.stopPropagation();
			event.preventDefault();
			showReImageViewPage(event);
		});

		$('#reImageRequests').listview('refresh');
		$('#reImageRequests').show();

	}
	$.mobile.changePage($('#reImageRequestQueue'));

}

function showReImageViewPage(e) {
	currentReceiptId = $(e.currentTarget).attr("receipt-id");
	$.mobile.changePage("#reImageImageView");
	checkSession();
}

function saveReImages() {
	if (reImageImageList && reImageImageList.images.length > 0) {
		for ( var x = 0; x < reImageImageList.images.length; x++) {
			reImageImageList.images[x].saveImage(currentReceiptId);
		}
		$.post("/tcmIS/haas/tabletReImageComplete.do", {"receiptId" : currentReceiptId}, retrieveReImageRequests);
	}
	else {
		retrieveReImageRequests();
	}
}

function updateReImageHeader(e) {
	var thisPage = "#" + e.currentTarget.id;
	var hdrString = "<div class='ui-header-trx-desc'>" + reImageMessages.tablet_reimagereceiptid + " " + currentReceiptId + "</div>";
	if ($(thisPage + " .ui-header-trx-desc").length > 0) {
		$(thisPage + " .ui-header-trx-desc").html(hdrString);
	}
	else {
		$(thisPage + " [data-role = 'header']").append("<div class='ui-header-trx-desc'>" + hdrString + "</div>");
	}
}

function showReImageCameraPage() {
	$.mobile.changePage("#reImageCameraPage");
}

function doCapture() {
	if (isMotorola()) {
		imager.capture();
	}
	else {
		onImageCapture("../tabletDevImages/DEFT_1280x960.png");
	}
}

function cameraOn() {
	if (isMotorola()) {
		$("#reImageCameraImage").css("display", "none");
		$("#reImageCameraImage").attr("src", "");
		imager.desiredHeight = 960;
		imager.desiredWidth = 1280;
		imager.width = $("#reImageDisplayDiv").width();
		imager.height = $("#reImageDisplayDiv").height();
		imager.left = $("#reImageDisplayDiv").offset().left;
		imager.top = $("#reImageDisplayDiv").offset().top;
		imager.aim = "ON";
		imager.imagerCaptureEvent = "onImageCapture(%json)";
		imager.enabled = "cam0";
	}
	else {
		alert("cameraOn");
	}
}

function cameraOff() {
	if (isMotorola()) {
		imager.disable();
	}
	else {
		alert("camera Off");
	}
	$.mobile.changePage("#reImageImageView");
}

function onImageCapture(captureData) {
	if (isMotorola()) {
		imager.disable();
		ImageData = captureData.imageData;
	}
	else {
		ImageData = captureData;
		alert("Imager Event");
	}
	$("#reImageCameraControlDiv").css("display", "none");
	$("#reImageAcceptDiv").css("display", "block");
	$("#reImageCameraImage").attr("src", ImageData);
	$("#reImageCameraImage").css("display", "block");
}

function onReImageAccept() {
	var reImageImage = new reImageObj($("#reImageCameraPage H1").text(), ImageData);
	reImageImageList.images.push(reImageImage);
	reImageImageList.currentImage = reImageImageList.images.length - 1;
	$("#reImageAcceptDiv").css("display", "none");
	$("#reImageCameraControlDiv").css("display", "block");
	$("#reImageCameraImage").attr("src", "");
	$("#reImageCameraImage").css("display", "none");
	$("#reImageShutterButton").on("click", function(e) {
		$("#reImageShutterButton").off("click");
		doCapture();
	});
	refreshImageListView();
	$.mobile.changePage("#reImageImageView");
}

function refreshImageListView() {
	$("#reImageImageListView").html(reImageImageList.buildImageList());
	$("#reImageImageListView").listview("refresh");
	$("#reImageImageListView li").on("click", function(e) {
		showReImageImage(e);
	});
	reImageImageList.images[reImageImageList.currentImage].display();
}

function onReImageReject() {
	$("#reImageCameraControlDiv").css("display", "block");
	$("#reImageAcceptDiv").css("display", "none");
	$("#reImageImageTag").attr("src", "");
	$("#reImageImageTag").css("display", "none");
	$("#reImageShutterButton").on("click", function(e) {
		$("#reImageShutterButton").off("click");
		doCapture();
	});
	if (isMotorola()) {
		imager.enable();
	}
}

function flashControl() {
	if (isMotorola()) {
		flashState = !flashState;
		imager.lamp = flashState ? "OFF" : "ON";
	}
}

function showReImageImageTypePopup() {
	debug("showReImageImageTypePopup");
	$("#reImageImageTypePopup").popup("open");
}

function reImageImageViewPrep() {
	if ($("#reImageImageTypeList").html() == "") {
		$.post("/tcmIS/haas/tabletListReceiptDocumentImageTypes.do", completeReImageImageViewPrep);
	}
}

function completeReImageImageViewPrep(data) {
	var response = new responseObj(data);
	if (response.isError()) {
		alert("Error fetching image types.");
	}
	else {
		var imageTypeList = response.Results;
		var imageTypeListHTML = "";
		for ( var x = 0; x < imageTypeList.length; x++) {
			imageTypeListHTML += "<li data-docType='";
			imageTypeListHTML += imageTypeList[x].documentType + "'>";
			imageTypeListHTML += imageTypeList[x].descFromLabel;
			imageTypeListHTML += "</li>";
		}
		$("#reImageImageTypeList").html(imageTypeListHTML);
		$("#reImageImageTypeList.ui-listview").listview("refresh");
		$("#reImageImageTypeList li").on("click", function(e) {
			reImageImageTypeListClick(e);
		});
	}
}

function reImageImageTypeListClick(e) {
	$("#reImageImageTypePopup").popup("close");
	var imageType = $(e.currentTarget).attr("data-docType");
	$("#reImageCameraPage H1").html(imageType);
}

function showReImageImage(e) {
	debug("showReImageImage");
	var imageIndex = $(e.currentTarget).attr("data-imageIndex");
	reImageImageList.currentImage = imageIndex;
	reImageImageList.images[imageIndex].display();

	$("#imageTag").css("display", "block");
}

function localizeLabels() {
	var storedLabels = sessionStorage.getItem("localizedReImageLabels");

	if (storedLabels == null) {
		debug("localizing labels");
		var labels = "tablet_reimagerequests,label_home,tablet_done,label_images,tablet_addimage,label_deleteimage,"
            + "tablet_selectimagetype,tablet_deletethisimage,label_delete,label_cancel,tablet_camera,"
            + "label_back,tablet_shutter,tablet_flashon,tablet_accept,label_reject";
		$.post("/tcmIS/haas/tabletLocalizeLabel.do", {"input" : labels}, localizeLabelsReturn);
	}
	else {
		applyLocalizedLabels(JSON.parse(storedLabels));
	}
}

function localizeLabelsReturn(data) {
	var response = new responseObj(data);

	if (response.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + response.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		var labels = response.localizedList;
		applyLocalizedLabels(labels);
		sessionStorage.setItem("localizedReImageLabels", JSON.stringify(labels));
	}
}

function applyLocalizedLabels(labels) {
	debug("applying localized labels");
	$("#tablet_reimagerequests").html(labels.tablet_reimagerequests);
	$("#label_home").html(labels.label_home);
	$("#tablet_done").html(labels.tablet_done);
	$("#label_images").html(labels.label_images);
	$("#tablet_addimage").html(labels.tablet_addimage);
	$("#label_deleteimage").html(labels.label_deleteimage);
	$("#tablet_selectimagetype").html(labels.tablet_selectimagetype);
	$("#tablet_deletethisimage").html(labels.tablet_deletethisimage);
	$("#label_delete").html(labels.label_delete);
	$("#label_cancel").html(labels.label_cancel);
	$("#tablet_camera").html(labels.tablet_camera);
	$("#label_back").html(labels.label_back);
	$("#tablet_shutter").html(labels.tablet_shutter);
	$("#tablet_flashon").html(labels.tablet_flashon);
	$("#tablet_accept").html(labels.tablet_accept);
	$("#label_reject").html(labels.label_reject);
}

function localizeMessages() {
	var storedMessages = sessionStorage.getItem("localizedReImageMessages");

	if (storedMessages == null) {
		debug("localizeMessages");
		var messages = "tablet_receiptid,tablet_itemid,tablet_location,tablet_arrivalscanuser,tablet_inventorygroup,"
			+ "tablet_internalnotes,tablet_notes,tablet_reimagereceiptid,tablet_reImageReqText,tablet_noRcptsinReImagestatus";
		$.post("/tcmIS/haas/tabletLocalizeLabel.do", {"input" : messages}, localizeMessagesReturn);
	}
	else {
		reImageMessages = JSON.parse(storedMessages);
	}
}

function localizeMessagesReturn(data) {
	var response = new responseObj(data);

	if (response.isError()) {
		$("#errorMessagesArea").html('&nbsp;&nbsp;&nbsp;&nbsp;' + response.Message).css("color", "red").css('font-size', '20px');
		$("#errorMessagesArea").show();
	}
	else {
		reImageMessages = response.localizedList;
		sessionStorage.setItem("localizedReImageMessages", JSON.stringify(reImageMessages));
	}

}
