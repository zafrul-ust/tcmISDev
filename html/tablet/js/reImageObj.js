function reImageObj(type, data) {
	debug("reImageObj(" + type + ", " + data + ")");
	this.imageType = type;
	this.imageData = data;
	this.imageURL = new String();
}

reImageObj.prototype.saveImage = function(imageReceiptId) {
	debug("reImageObj.saveImage");
	// if the url is not empty it has already been saved
	if (this.imageURL.length == 0) {
		$.mobile.loading('show', {
			text : "Saving " + this.imageType,
			textVisible : true
		});
		var thisObj = this;
		var callArgs = new Object();
		callArgs.receiptId = imageReceiptId;
		callArgs.imageType = this.imageType;
		callArgs.imageData = this.imageData;
		var params = $.param(callArgs);
		debug("calling params" + params);
		$.post("../tcmIS/haas/tabletReceiptImageUpload.do", callArgs, function(data) {
			debug("call back begins");
			debug(data);
			var saveImageReturn = $.parseJSON(data);
			if (saveImageReturn.Status == "OK") {
				thisObj.imageURL = saveImageReturn.ImageUrl;

			}
			else {
				alert("image not saved\n" + saveImageReturn.Message);
			}
			$.mobile.loading("hide");
		});

	}
};

reImageObj.prototype.display = function() {
	debug("reImageObj.display");
	$("#reImageImageTag").attr("src", this.imageData);
	$("#reImageImageTag").css("display", "block");
};

