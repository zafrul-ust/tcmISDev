

function altPopup(caption, targetDiv, closeCallback, buttonText){
    var thisPopupId = "#" + targetDiv + "_popup";
    var thisPopupIdObj = $(thisPopupId);
    debug("open popup " + thisPopupId);
    if(thisPopupIdObj.length){
        $(thisPopupId + " h2").html(caption);
        if(ifDefined(buttonText) != ""){
            $(thisPopupId + " .ui-btn-text").text(buttonText);
        }
        else{
            $(thisPopupId + " .ui-btn-text").text("Cancel");
        }
        $(thisPopupId).popup("open");
    }
    else{
        var altPopupHTML = new String();
        altPopupHTML += "<div data-role='popup' data-dismissible='false' id='" + targetDiv + "_popup" + "' class='ui-content'>";
            altPopupHTML += "<div id='caption' style='text-align:center;'><h2>" + caption + "</h2></div>";
            altPopupHTML += "<button data-role='button'>"
            if(ifDefined(buttonText) != ""){
                altPopupHTML += buttonText + "</button>"
            }
            else{
                altPopupHTML += "Cancel</button>"
            }
        altPopupHTML += "</div>";
        $("#" + targetDiv).append(altPopupHTML);
        $(thisPopupId).popup();
        $(thisPopupId).trigger("create");
        $(thisPopupId + " button").on("click", function(e){altPopupHide(thisPopupId);});
        debug("Callback = " + closeCallback);
        $(thisPopupId).on("popupafterclose", closeCallback);
        $(thisPopupId).popup("open");
    }
}


function altPopupHide(popupName){
    debug("inside altPopupHide for " + popupName);
    $(popupName + " button.altCmd").off("click");
    $(popupName).popup("close");
}

function altPopupDestroy(){
    debug("inside altPopupDestroy");
    $("#altPopup").popup("close");
    $("#altPopup").remove();
}

/**
 * @alias altAlphaInput
 * @author Stephen Skidmore
 * @classDescription displays a qwerty keyboard in a jQueryMobile popup div 
 * @classDescription triggers change event on targetInput
 * @param {string} caption - string to be displayed at top of popup in <h2> tags
 * @param {string} targetDiv - unique id of the jQueryMobile page div to insert the popup markup
 * @param {string} targetInput - unique id of the input tag to receive the input.  
 * @param If target input has a value it will be used to populate the popup input element.  
 */
function altAlphaInput(caption, targetDiv, targetInput){
    var thisKeyPadId = "#" + targetDiv + "_keyPad";
    var thisKeyPadIdObj = $(thisKeyPadId);
    if(thisKeyPadIdObj.length){
        $(thisKeyPadId + " h2.keyPadCaption").html(caption)
        $(thisKeyPadId + " .keyAccumulator").val(ifDefined($("#" + targetInput).val()));
        $(thisKeyPadId + " button.altCmd").on("click", function(e){altCmdKeyClick(e, targetInput, thisKeyPadId);});
        $(thisKeyPadId).popup("open");
    }
    else{
    	var keys = new Array;
    	keys.push("`1234567890-=".split(""));
    	keys.push("qwertyuiop[]\\".split(""));
    	keys.push("asdfghjkl;'".split(""));
    	keys.push("zxcvbnm,./".split(""));
    	keys.push(new Array("SPACE"));
    	keys.push("~!@#$%^&*()_+".split(""));
    	keys.push("QWERTYUIOP{}|".split(""));
    	keys.push("ASDFGHJKL:\"".split(""));
    	keys.push("ZXCVBNM<>?".split(""));
    	keys.push(new Array("SPACE"));
    
    	var keyPad = new String();
    	keyPad += "<div data-role='popup' data-dismissible='false' data-overlay-theme='a' id='" + 
    	           targetDiv + "_keyPad' class='ui-content altKeyPad'>";
    		keyPad += "<div id='caption' style='text-align:center;'><h2 class='keyPadCaption'>" + caption + "</h2></div>";
    	    keyPad += "<input class='keyAccumulator' data-clear-btn='true' value='" + $("#" + targetInput).val() + "' readonly/>";
    		for(var row = 0; row < keys.length; row++){
    			if(row < 5){
    				keyPad += "<div id='" + targetDiv + "_keyPad_row" + row + "' class='keyPadLowerRow'>";
    			}
    			else{
    				keyPad += "<div id='" + targetDiv + "_keyPad_row" + row + "' class='keyPadUpperRow'>";
    			}
    			for(var col = 0; col < keys[row].length; col++){
    				keyPad += "<button data-role='button' data-inline='true' class='altInput'>" + keys[row][col] + "</button>";
    			}
    			keyPad += "</div>";
    			
    		}
    	keyPad += "</div>";
    
    
    	$("#" + targetDiv).append(keyPad);
        $("#" + targetDiv).attr("data-theme", "a");
    	$(thisKeyPadId).popup();
    	$(thisKeyPadId + "_row0").append("<button data-role='button' data-inline='true' class='altCmd'>BS</button>");
    	$(thisKeyPadId + "_row2").prepend("<button data-role='button' data-inline='true' class='altCmd'>CAPS</button>");
    	$(thisKeyPadId + "_row2").append("<button data-role='button' data-inline='true' class='altCmd'>DONE</button>");
    	$(thisKeyPadId + "_row3").prepend("<button data-role='button' data-inline='true' class='altCmd'>SHIFT</button>");
    	$(thisKeyPadId + "_row3").append("<button data-role='button' data-inline='true' class='altCmd'>CANCEL</button>");
    	$(thisKeyPadId + "_row5").append("<button data-role='button' data-inline='true' class='altCmd'>BS</button>");
    	$(thisKeyPadId + "_row7").prepend("<button data-role='button' data-inline='true' class='altCmd'>CAPS</button>");
    	$(thisKeyPadId + "_row7").append("<button data-role='button' data-inline='true' class='altCmd'>DONE</button>");
    	$(thisKeyPadId + "_row8").prepend("<button data-role='button' data-inline='true' class='altCmd'>SHIFT</button>");
    	$(thisKeyPadId + "_row8").append("<button data-role='button' data-inline='true' class='altCmd'>CANCEL</button>");
    	$(thisKeyPadId + "_row4 button").attr("data-inline","false").addClass("altCmd").removeClass("altInput");
    	$(thisKeyPadId + "_row9 button").attr("data-inline","false").addClass("altCmd").removeClass("altInput");
		$(thisKeyPadId + "_row0").addClass("keyPadLowerNumRow").removeClass("keyPadLowerRow");
		$(thisKeyPadId + "_row5").addClass("keyPadUpperNumRow").removeClass("keyPadUpperRow");
    	$(thisKeyPadId).trigger("create");
    	$(thisKeyPadId + " button.altInput").on("click", function(e){altInputKeyClick(e, thisKeyPadId);});
    	$(thisKeyPadId + " button.altCmd").on("click", function(e){altCmdKeyClick(e, targetInput, thisKeyPadId);});
    	$(thisKeyPadId + " div.keyPadUpperRow").css({"display":"block", "text-align":"center"});
    	$(thisKeyPadId + " div.keyPadLowerNumRow").css({"display":"block", "text-align":"center"});
    	$(thisKeyPadId + " div.keyPadLowerRow").css("display","none");
    	$(thisKeyPadId + " div.keyPadUpperNumRow").css("display","none");
    	$(thisKeyPadId).popup("open");
    }
}


/**
 * @alias altNumInput
 * @author Stephen Skidmore
 * @classDescription displays a digit only keyboard in a jQueryMobile popup div
 * @param {string} caption - string to be displayed at top of popup in <h2> tags
 * @param {string} targetDiv - unique id of the jQueryMobile page div to insert the popup markup
 * @param {string} targetInput - unique id of the input tag to receive the input.  
 * @param If target input has a value it will be used to populate the popup input element.  
 */
function altNumInput(caption, decimalEntry, targetDiv, targetInput, min, max){
//    hourglass.visibility = "visibile";
    var thisNumPadId = "#" + targetDiv + "_numPad";
    var thisNumPadIdObj = $(thisNumPadId);
    if(thisNumPadIdObj.length){
        $(thisNumPadId + " h2.numPadCaption").html(caption)
        $(thisNumPadId + " .keyAccumulator").val(ifDefined($("#" + targetInput).val()));
        $(thisNumPadId + " button.altCmd").on("click", function(e){altCmdKeyClick(e, targetInput, thisNumPadId, min, max);});
//        hourglass.visibility = "hidden";
        $(thisNumPadId).popup("open");
    }
    else{
    	var numPad = new String();
    	numPad += "<div data-role='popup' data-dismissible='false' data-overlay-theme='a' id='" + 
                   targetDiv + "_numPad' class='ui-content numPadCaption'>";
    		numPad += "<div id='caption' style='text-align:center;'><h2 class='numPadCaption'>" + caption + "</h2></div>";
            numPad += "<div style='text-align:center; color:red;'><h4 id='captionError'></h4></div>";
    		numPad += "<input class='keyAccumulator' data-clear-btn='true' value='" + $("#" + targetInput).val() + "'readonly/>";
    		for(var row = 0; row < 3; row++){
    			numPad += "<div id='" + thisNumPadId + "_row" + row + "' class='numPadRow'>";
    			for(var col = 1; col <= 3; col++){
    				numPad += "<button data-role='button' data-inline='true' class='altInput'>" + (col + (row * 3)) + "</button>";
    			}
    			numPad += "</div>";
    		}
    		numPad += "<div id='" + thisNumPadId + "_row4' class='numPadRow'>";
    			numPad += "<button data-role='button' data-inline='true' class='altCmd'>BS</button>";
    			numPad += "<button data-role='button' data-inline='true' class='altInput'>0</button>";
                numPad += "<button data-role='button' data-inline='true' class='altInput altInputDecimalKey'>.</button>";
    		numPad += "</div>";
    		numPad += "<div id='" + thisNumPadId + "_row5' class='numPadRow'>";
                numPad += "<button data-role='button' data-inline='true' class='altCmd'>CANCEL</button>";
    			numPad += "<button data-role='button' data-inline='true' class='altCmd'>DONE</button>";
    		numPad += "</div>";
    	numPad += "</div>";
    	
    	$("#" + targetDiv).append(numPad);
        $("#" + targetDiv).attr("data-theme", "a");
    	$(thisNumPadId).popup();
    	$(thisNumPadId).trigger("create");
    	$(thisNumPadId + " div.ui-btn-inner").css({"font-size":"24px","padding":"0.6em 30px"});
    	$(thisNumPadId + " div.numPadRow").css({"display":"block", "text-align":"center"});
    	$(thisNumPadId + " button.altInput").on("click", function(e){altInputKeyClick(e, thisNumPadId);});
    	$(thisNumPadId + " button.altCmd").on("click", function(e){altCmdKeyClick(e, targetInput, thisNumPadId, min, max);});
        if(decimalEntry == "Y"){
            $(thisNumPadId + " div.altInputDecimalKey").css({"visibility":"visible"});                    
        }
        else{
            $(thisNumPadId + " div.altInputDecimalKey").css({"visibility":"hidden"});                    
        }
//        hourglass.visibility = "hidden";
    	$(thisNumPadId).popup("open");
    }
}

/**
 * @alias altInputKeyClick
 * @author Stephen Skidmore
 * @classDescription processes button clicks for buttons with class = altInput
 * @param {event} the button click event that fired this function
 */
function altInputKeyClick(e, sourceName){
	var keyPressed = $(e.currentTarget).text();
	var currentValue = new String($(sourceName + " .keyAccumulator").val());
    $("#captionError").empty();
	if($(sourceName + " .keyAccumulator").val() != "undefined"){
		$(sourceName + " .keyAccumulator").val(currentValue.concat(keyPressed));
	}
	else{
        $(sourceName + " .keyAccumulator").val(keyPressed);
	}
	if($(sourceName).attr("data-caps") == "true"){
		$(sourceName + " div.keyPadLowerRow").css({"display":"block", "text-align":"center"});
		$(sourceName + " div.keyPadLowerNumRow").css({"display":"block", "text-align":"center"});
		$(sourceName + " div.keyPadUpperRow").css("display", "none");
		$(sourceName + " div.keyPadUpperNumRow").css("display", "none");
		$(sourceName).attr("data-caps", "false");
	}
}

/**
 * @alias altCmdKeyClick
 * @author Stephen Skidmore
 * @classDescription processes button clicks for buttons with class = altCmd
 * @param {event} the button click event that fired this function
 * @param {string} name of the input element to receive the entered data.  To be used if DONE key is pressed.
 */
function altCmdKeyClick(e, targetInput, sourceName, min, max){
	var keyPressed = $(e.currentTarget).text();
	var currentValue = new String($(sourceName + " .keyAccumulator").val());
	$("#captionError").empty();
	if(keyPressed == "BS"){
		if(currentValue.length > 0){
			$(sourceName + " .keyAccumulator").val(currentValue.slice(0, currentValue.length - 1));		
		}
	}
	else if(keyPressed == "DONE"){
	    if(typeof(min) != "undefined"){
           if(min > currentValue){
               $("#captionError").html("Minimum value = " + min);
               return;
           }
	    }
        if(typeof(max) != "undefined"){
           if(max < currentValue){
               $("#captionError").html("Maximun value = " + max);
               return;
           }
        }
	    debug("altInput - setting " + targetInput + " to " + currentValue);
		$("#" + targetInput).val(currentValue);
        altPopupHide(sourceName);
        $("#" + targetInput).trigger("change");
	}
	else if(keyPressed == "CANCEL"){
        altPopupHide(sourceName);
//		$(sourceName).popup("close");
//		$(sourceName).remove();
	}
	else if(keyPressed == "SHIFT" || keyPressed == "CAPS"){
		if($(sourceName + " div.keyPadLowerRow").css("display") == "none"){
			$(sourceName + " div.keyPadLowerRow").css({"display":"block", "text-align":"center"});
			$(sourceName + " div.keyPadUpperRow").css("display", "none");
				$(sourceName).attr("data-caps", "false");
			if(keyPressed == "SHIFT"){
				$(sourceName + " div.keyPadLowerNumRow").css({"display":"block", "text-align":"center"});
				$(sourceName + " div.keyPadUpperNumRow").css("display", "none");
			}
		}
		else{
			$(sourceName + " div.keyPadLowerRow").css("display", "none");
			$(sourceName + " div.keyPadUpperRow").css({"display":"block", "text-align":"center"});
			if(keyPressed == "SHIFT"){
				$(sourceName).attr("data-caps", "true");
				$(sourceName + " div.keyPadUpperNumRow").css({"display":"block", "text-align":"center"});
				$(sourceName + " div.keyPadLowerNumRow").css("display", "none");
			}
			else{
				$(sourceName).attr("data-caps", "locked")
			}
		}
	}
	else if(keyPressed == "SPACE"){
//		var currentValue = new String($(sourceName + ".keyAccumulator").val());
		$(sourceName + " .keyAccumulator").val(currentValue.concat(" "));
	}
}

