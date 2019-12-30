function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function myResultOnload()
{
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/

 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
}

//only marked record modified if active is checked 
function changeStatus(rowIndex) {
	if (document.getElementById("status"+rowIndex).checked) {
		document.getElementById("modified"+rowIndex).value="modified";
	}
}

//whenever user change active to inactive or vise-versa
function changeActive(rowIndex) {
	document.getElementById("modified"+rowIndex).value="modified";
	/*
	alert("row: "+rowIndex);
		if (document.getElementById("periodLimit"+rowIndex) != null) {
				if(document.getElementById("periodLimit"+rowIndex).value.length > 0) {
					if (isNaN(document.getElementById("periodLimit"+rowIndex).value)) {
						alert("mrLimitWrongFormat");
					}else {
						alert("is number");
					}
				}else {
					alert("length greater than 0");
				}
			}else {
	       alert("null");
			}
			*/
}

function preUpdateAudit() {
	var totalCount = document.getElementById("totalLines").value;
	var result = true;
	for(var i=0; i < totalCount; i++) {
		if (document.getElementById("status"+i).checked) {
			var hasLimit = false;
			//checking mr limit
			if (document.getElementById("mrLimit"+i) != null) {
				if(document.getElementById("mrLimit"+i).value.length > 0) {
					if (isNaN(document.getElementById("mrLimit"+i).value)) {
						alert(messagesData.line+" "+(i+1)+": "+messagesData.mrLimitWrongFormat);
						return false;
					}
					hasLimit = true;
				}
			}
			if	(document.getElementById("mrThreshold"+i) != null) {
				if	(document.getElementById("mrThreshold"+i).checked) {
					hasLimit = true;
				}
			}
			//checking ytd limit
			if (document.getElementById("ytdLimit"+i) != null) {
				if(document.getElementById("ytdLimit"+i).value.length > 0) {
					if (isNaN(document.getElementById("ytdLimit"+i).value)) {
						alert(messagesData.line+" "+(i+1)+": "+messagesData.ytdLimitWrongFormat);
						return false;
					}
					hasLimit = true;
				}
			}
			if	(document.getElementById("ytdThreshold"+i) != null) {
				if	(document.getElementById("ytdThreshold"+i).checked) {
					hasLimit = true;
				}
			}
			//checking mr limit
			if (document.getElementById("periodLimit"+i) != null) {
				if(document.getElementById("periodLimit"+i).value.length > 0) {
					if (isNaN(document.getElementById("periodLimit"+i).value)) {
						alert(messagesData.line+" "+(i+1)+": "+messagesData.periodLimitWrongFormat);
						return false;
					}
					if (document.getElementById("periodDays"+i) != null) {
						if(document.getElementById("periodDays"+i).value.length > 0) {
							if (isNaN(document.getElementById("periodDays"+i).value)) {
								alert(messagesData.line+" "+(i+1)+": "+messagesData.missingPeriodDays);
								return false;
							}
						}else {
							alert(messagesData.line+" "+(i+1)+": "+messagesData.missingPeriodDays);
							return false;
						}
					}else {
						alert(messagesData.line+" "+(i+1)+": "+messagesData.missingPeriodDays);
						return false;
					}
					hasLimit = true;
				}
			}
			if	(document.getElementById("periodThreshold"+i) != null) {
				if	(document.getElementById("periodThreshold"+i).checked) {
					hasLimit = true;
				}
			}

			if (!hasLimit) {
				alert(messagesData.line+" "+(i+1)+": "+messagesData.setAtLeastOneLimit);
				return false;
			}
		} //end of if active is checked
	} //end of for loop
	return result;
}
