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

function dataChanged(rowId) {
	//first set row as modified
	document.getElementById("modified"+rowId+"").value = "modified";
	if( !document.getElementById('checkedDisplay'+rowId).checked) {
		document.getElementById('checked'+rowId).value = "";
	}else {
		document.getElementById('checked'+rowId).value = "checked";
	}
	//check to see if access is unchecked
	var totalLines = parseInt(document.getElementById("totalLines").value);
	var userGroupCount =  parseInt(document.getElementById("userGroupCount").value);
	var rowForCheck = userGroupCount/totalLines;
	var rowIndex = parseInt(rowId)-parseInt(rowId)%rowForCheck;
	//current element is access
	if (rowIndex == parseInt(rowId)) {
		//uncheck other elements if Access is unchecked
		if (!document.getElementById('checkedDisplay'+rowId).checked) {
			for (var i = 1; i < rowForCheck; i++) {
				if (document.getElementById('checkedDisplay'+(rowIndex+i)).checked) {
					document.getElementById('checkedDisplay'+(rowIndex+i)).checked = false;
					document.getElementById('checked'+(rowIndex+i)).value = "";
					document.getElementById("modified"+(rowIndex+i)+"").value = "modified";
				}
			}
		}
	}else {
		if (document.getElementById('checkedDisplay'+rowId).checked) {
			document.getElementById('checkedDisplay'+(rowIndex)).checked = true;
			document.getElementById('checked'+(rowIndex)).value = "checked";
			document.getElementById("modified"+(rowIndex)+"").value = "modified";
		}
	}
}

function checkAll(num) {
	var userGroupCount = parseInt(document.getElementById("userGroupCount").value);
	var totalLines = parseInt(document.getElementById("totalLines").value);
	var rowForCheck = userGroupCount/totalLines;
	if(document.getElementById('checkAll'+num).checked) {
		for (var i = 0; i < userGroupCount; i+=rowForCheck) {
			if (document.getElementById('checkedDisplay'+i) != null) {
				if (!document.getElementById('checkedDisplay'+i).checked) {
					document.getElementById('checkedDisplay'+i).checked = true;
					document.getElementById('checked'+i).value = "checked";
					document.getElementById("modified"+i+"").value = "modified";
				}
			}
		}
		//also checking the header check boxes
		for (var i = 0; i < totalLines; i+=10) {
			if (document.getElementById('checkAll'+i) != null) {
				document.getElementById('checkAll'+i).checked = true;
			}
		}
	}else {
		for (var i = 0; i < userGroupCount; i+=rowForCheck) {
			if (document.getElementById('checkedDisplay'+i) != null) {
				document.getElementById('checkedDisplay'+i).checked = false;
				document.getElementById('checked'+i).value = "";
				document.getElementById("modified"+i+"").value = "modified";
				for (var j = 1; j < rowForCheck; j++) {
					if (document.getElementById('checkedDisplay'+(j+i)) != null) {
						if (document.getElementById('checkedDisplay'+(j+i)).checked) {
							document.getElementById('checkedDisplay'+(j+i)).checked = false;
							document.getElementById('checked'+(j+i)).value = "";
							document.getElementById("modified"+(j+i)+"").value = "modified";
						}
					}
				}
			}
		}
		//also checking the header check boxes
		for (var i = 0; i < totalLines; i+=10) {
			if (document.getElementById('checkAll'+i) != null) {
				document.getElementById('checkAll'+i).checked = false;
			}
		}		
	}
}