function reImageListObj() {
	debug("reImageListObj");
	this.images = new Array();
	this.currentImage = 0;
	// empty the image list on the image view page.
	$("#reImageImageListView").html("");
}

reImageListObj.prototype.deleteImage = function() {
	this.images.splice(this.currentImage, 1);
	$("#reImageImageListView li[data-imageindex='" + this.currentImage + "']").remove();
	if (this.images.length > 0) {
		$("#reImageImageListView").html(this.buildImageList());
		$("#reImageImageListView").listview("refresh");
		$("#reImageImageListView li").on("click", function(e) {
			showReImageImage(e);
		});
		this.currentImage = 0;
		$("#reImageImageDeleteConfirmPopup").popup("close");
		this.images[this.currentImage].display();
	}
	else {
		$("#reImageImageDeleteConfirmPopup").popup("close");
		$("#reImageImageTag").css("display", "none");
	}
};

reImageListObj.prototype.buildImageList = function() {
	var imageListHTML = new String();
	for ( var x = 0; x < this.images.length; x++) {
		imageListHTML += '<li data-imageIndex="' + x + '">';
		imageListHTML += this.images[x].imageType;
		imageListHTML += '</li>';
	}
	return imageListHTML;
};
