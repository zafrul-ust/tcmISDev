
var ImageData = "";
var flashState = false;

    function doCapture(){
        if(isMotorola()){
            imager.capture();
        }
        else{
            onImageCapture("../tabletDevImages/DEFT_1280x960.png");
        }
   }

	function cameraOn(){
	    if(isMotorola()){
    		$("#imageTag").css("display","none");		
    		$("#imageTag").attr("src","");
            imager.desiredHeight = 960;
            imager.desiredWidth = 1280;
    		imager.width = $("#imageDisplayDiv").width();
    		imager.height = $("#imageDisplayDiv").height();
    		imager.left = $("#imageDisplayDiv").offset().left;
    		imager.top = $("#imageDisplayDiv").offset().top;
    		imager.aim = "ON";
    		imager.imagerCaptureEvent = "onImageCapture(%json)";
    		imager.enabled = "cam0";
        }
        else{
            alert("cameraOn");
        }
	}
	
	
	function cameraOff(){
	    if(isMotorola()){
    		imager.disable();
        }
        else{
    		alert("camera Off");
        }
		$.mobile.changePage("#imageView");
	}
	
	
	function onImageCapture(captureData){
        if(isMotorola()){
    	   	imager.disable();	
    		ImageData = captureData.imageData;
		}
		else{
		    ImageData = captureData
    		alert("Imager Event");
		}
        $("#cameraControlDiv").css("display","none");
        $("#imageAcceptDiv").css("display","block");
		$("#imageTag").attr("src", ImageData);	
		$("#imageTag").css("display","block");
	}
	

	function onImageAccept(){
		var receiptImage = new receiptImageObj($("#cameraPage H1").text(), ImageData);
//        receiptImage.saveImage(9102338);
		receiptImageList.images.push(receiptImage);
		receiptImageList.currentImage = receiptImageList.images.length - 1;
        var newImageListItem = '<li data-imageIndex="';
            newImageListItem += receiptImageList.currentImage;
            newImageListItem += '">';
            newImageListItem += receiptImageList.images[receiptImageList.currentImage].imageType;
            newImageListItem += '</li>';
        $("#receiptImageListView").append(newImageListItem);
        $("#receiptImageListView").listview("refresh");
        $("#receiptImageListView li").on("click",function(e){showReceiptImage(e);});
        receiptImageList.images[receiptImageList.currentImage].display();
        $("#imageAcceptDiv").css("display","none");
        $("#cameraControlDiv").css("display","block");
		$("#imageTag").attr("src", "");	
        $("#imageTag").css("display","none");
        $("#shutterButton").on("click", function(e){$("#shutterButton").off("click");
                                                     doCapture();});
		$.mobile.changePage("#imageView");
    } 

	function onImageReject(){
        $("#cameraControlDiv").css("display","block");
        $("#imageAcceptDiv").css("display","none");
		$("#imageTag").attr("src","");
        $("#imageTag").css("display","none");
        $("#shutterButton").on("click", function(e){$("#shutterButton").off("click");
                                                     doCapture();});
        if(isMotorola()){
    		imager.enable();
		}
	}

    function flashControl(command){
        if(isMotorola()){
            if(flashState == false){
                imager.lamp = "ON";
                flashState = true;
            }
            else{
                imager.lamp = "OFF";
                flashState = false;
            }
        }
    }



