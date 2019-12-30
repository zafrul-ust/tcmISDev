function receiptImageObj(type, data){
    debug("receiptImageObj");
	this.imageType = type;
	this.imageData = data;
	this.imageURL = new String();
}


receiptImageObj.prototype.saveImage = function(imageReceiptId){
    debug("receiptImageObj.saveImage");
//  if the url is not empty it has alread been saved		
//	alert("saveImage\n" + imageReceiptId + "\n" + this.imageType + "\n" + this.imageData);
	if(this.imageURL.length == 0){
        $.mobile.loading('show',{
            text : "Saving " + this.imageType,
            textVisible : true
        });
	   var thisObj = this;
		var callArgs = new Object();
			callArgs.receiptId = imageReceiptId; 
			callArgs.imageType = this.imageType;
			callArgs.imageData = this.imageData;
		debug("calling params");
		var params = $.param(callArgs);
        debug(params);
		debug("calling post");
		$.post("../tcmIS/haas/tabletReceiptImageUpload.do",
			callArgs,
			function(data){
			    debug("call back begins");
			    debug(data);
			    var saveImageReturn = $.parseJSON(data);
				if(saveImageReturn.Status == "OK"){
                    thisObj.imageURL = saveImageReturn.ImageUrl;
                    
				}
				else{
					alert("image not saved\n" + saveImageReturn.Message);
				}
				$.mobile.loading("hide");
			}
		);

	}
}

receiptImageObj.prototype.display = function(){
    debug("receiptImageObj.display");
    $("#receiptImageTag").attr("src", this.imageData);
    $("#receiptImageTag").css("display", "block");
}

function receiptImageListObj(){
    debug("receiptImageListObj");
    this.images = new Array();
    this.currentImage = 0;
// empty the image list on the image view page.    
    $("#receiptImageListView").html("");
}

receiptImageListObj.prototype.deleteImage = function(){
    this.images.splice(this.currentImage, 1);
    $("#receiptImageListView li[data-imageindex='" + this.currentImage + "']").remove();
    if(this.images.length > 0){
        $("#receiptImageListView").html(this.buildImageList());
        $("#receiptImageListView").listview("refresh");
        $("#receiptImageListView li").on("click",function(e){showReceiptImage(e);});
        this.currentImage = 0;
        $("#imageDeleteConfirmPopup").popup("close");
        this.images[this.currentImage].display();
    }
    else{
        $("#imageDeleteConfirmPopup").popup("close");
        $("#receiptImageTag").css("display", "none");
    }
}

receiptImageListObj.prototype.buildImageList = function(){
    var imageListhtml = new String();
    for(var x = 0; x < this.images.length; x++){
        imageListhtml += '<li data-imageIndex="' + x + '">';
        imageListhtml += receiptImageList.images[x].imageType;
        imageListhtml += '</li>';
    }
    debug("imageListhtml = " + imageListhtml);
    return imageListhtml;
}
