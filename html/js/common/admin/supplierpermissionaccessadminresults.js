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

  displaySearchDuration(); 
	if (!showUpdateLinks){
    parent.document.getElementById("updateResultLink").style["display"] = "none";
  }else{
    parent.document.getElementById("updateResultLink").style["display"] = "";
  }
	//always show cancel
	parent.document.getElementById("cancelResultLink").style["display"] = "";
	//close window and refresh parent
	if( document.getElementById('closeAndRefresh').value != "" ) {
		closeFresh();
	}
}

function dataChanged(rowId) {
	//first set row as modified
	document.getElementById("modified"+rowId+"").value = "modified";
	//if access is unchecked then auto unchecked admin if it is checked
	if( !document.getElementById('access'+rowId).checked ) {
		if(document.getElementById('adminDisplay'+rowId) != null) {
			if (document.getElementById('adminDisplay'+rowId).checked) {
				document.getElementById('adminDisplay'+rowId).checked = false;
				document.getElementById('admin'+rowId).value = "";
			}
		}else if(document.getElementById('admin'+rowId) != null) {
			if (document.getElementById('admin'+rowId).checked) {
				document.getElementById('admin'+rowId).checked = false;
			}
		}
	}
}

function closeFresh() {
	window.parent.opener.refresh();
	window.parent.close();
}

function checkAll(num) {
	var totalLines = parseInt(document.getElementById("totalLines").value);
	if(document.getElementById('checkAll'+num).checked) {
		for (var i = 0; i < totalLines; i++) {
			if (document.getElementById('access'+i) != null) {
				if (!document.getElementById('access'+i).checked) {
					document.getElementById("modified"+i+"").value = "modified";
				}
				document.getElementById('access'+i).checked = true;
			}
			//also checking the header check boxes
			if (document.getElementById('checkAll'+i) != null) {
				document.getElementById('checkAll'+i).checked = true;
			}
		}
	}else {
		for (var i = 0; i < totalLines; i++) {
			if (document.getElementById('access'+i) != null) {
				if (document.getElementById('access'+i).checked) {
					document.getElementById("modified"+i+"").value = "modified";
				}
				document.getElementById('access'+i).checked = false;
				if(document.getElementById('adminDisplay'+i) != null) {
					if (document.getElementById('adminDisplay'+i).checked) {
						document.getElementById('adminDisplay'+i).checked = false;
						document.getElementById('admin'+i).value = "";
						document.getElementById("modified"+i+"").value = "modified";
					}
				}else if(document.getElementById('admin'+i) != null) {
					if (document.getElementById('admin'+i).checked) {
						document.getElementById('admin'+i).checked = false;
						document.getElementById("modified"+i+"").value = "modified";
					}
				}
			}
			//also checking the header check boxes
			if (document.getElementById('checkAll'+i) != null) {
				document.getElementById('checkAll'+i).checked = false;
			}
		}
	}
}