var windowCloseOnEsc = true;
function init() {
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure) {
		this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}
	
	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	 * I they are not initialized onLoad they tend to act weird*/
	showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", 
		{
			width:"300px",
			height:"300px",
			fixedcenter: true,
			constraintoviewport: true,
			underlay:"none",
			close:true,
			visible:false,
			draggable:true,
			modal:false 
		} 
	);
	showErrorMessagesWin.render();
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages() {
	var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
	var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
	errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;
	
	showErrorMessagesWin.show();
	
	var errorMessagesArea = document.getElementById("errorMessagesArea");
	errorMessagesArea.style.display="";
}

function submitUpdate() {
	/*Set any variables you want to send to the server*/
	var action = window.frames["resultFrame"].document.getElementById("action");
	action.value = 'update';
	showPleaseWait();
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	/*Submit the form in the result frame*/
	window.frames["resultFrame"].document.genericForm.target = "resultFrame";
	window.frames["resultFrame"].document.genericForm.submit();
}

function submitAdd() {
	var selectedIndex = parseInt(window.frames["resultFrame"].currentIndex);
	if (selectedIndex < 0) {
		alert(messagesData.selectGroup);
	} else {
		/*Set any variables you want to send to the server*/
		var action = window.frames["resultFrame"].document.getElementById("action");
		action.value = 'add';
		var now = new Date();
		var startSearchTime = document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();
		var obj = window.frames["resultFrame"].objs[selectedIndex];
		window.frames["resultFrame"].document.getElementById("userGroupId").value = obj.userGroupId;
		window.frames["resultFrame"].document.getElementById("userGroupAccess").value = "checked";
		showPleaseWait();
		/*Submit the form in the result frame*/
		window.frames["resultFrame"].document.genericForm.submit();
	}
}

function submitDelete() {
	var selectedIndex = parseInt(window.frames["resultFrame"].currentIndex);
	if (selectedIndex < 0) {
		alert(messagesData.selectUser);
	}else {
		var selectedNode = window.frames["resultFrame"].currentXId;
		if (selectedNode == "UG") {
			alert(messagesData.selectUser);
		}else {
			/*Set any variables you want to send to the server*/
			var action = window.frames["resultFrame"].document.getElementById("action");
			action.value = 'delete';
			var obj = window.frames["resultFrame"].objs[selectedIndex];
			window.frames["resultFrame"].document.getElementById("personnelId").value = obj.personnelId;
			window.frames["resultFrame"].document.getElementById("userGroupId").value = obj.userGroupId;
			showPleaseWait();
			/*Submit the form in the result frame*/
			window.frames["resultFrame"].document.genericForm.submit();
		}
	}
}

function submitDeleteGroup() {
	var selectedIndex = parseInt(window.frames["resultFrame"].currentIndex);
	if (selectedIndex < 0) {
		alert(messagesData.selectDeleteGroup);
	}else {
		/*Set any variables you want to send to the server*/
		var action = window.frames["resultFrame"].document.getElementById("action");
		action.value = 'deleteGroup';
		var now = new Date();
		var startSearchTime = document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();
		var obj = window.frames["resultFrame"].objs[selectedIndex];
		window.frames["resultFrame"].document.getElementById("userGroupId").value = obj.userGroupId;
		showPleaseWait();
		/*Submit the form in the result frame*/
		window.frames["resultFrame"].document.genericForm.submit();
	}
}