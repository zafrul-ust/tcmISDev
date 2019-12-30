windowCloseOnEsc = true;
function init() {
this.cfg = new YAHOO.util.Config(this);
if (this.isSecure)
{
 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
}

/*Yui pop-ups need to be initialized onLoad to make them work correctly.
I they are not initialized onLoad they tend to act weird*/
showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,
draggable:true, modal:false } );
showErrorMessagesWin.render();
}

/*The reson this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
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
	 document.getElementById("startSearchTime").value = now.getTime();
	 window.frames["resultFrame"].mygrid.parentFormOnSubmit();
	 /*Submit the form in the result frame*/
    window.frames["resultFrame"].document.genericForm.target = "resultFrame";
    window.frames["resultFrame"].document.genericForm.submit();
}

function submitAdd() {
    var selectedIndex = parseInt(window.frames["resultFrame"].currentIndex);
    if (selectedIndex < 0) {
        alert(messagesData.selectAddUserGroup);
    }else {
        /*Set any variables you want to send to the server*/
        var action = window.frames["resultFrame"].document.getElementById("action");
        action.value = 'add';
		  var now = new Date();
		  document.getElementById("startSearchTime").value = now.getTime();
		  var obj = window.frames["resultFrame"].objs[selectedIndex];
        window.frames["resultFrame"].document.getElementById("userGroupId").value = obj.userGroupId;
        window.frames["resultFrame"].document.getElementById("userGroupAccess").value = "Y";
        showPleaseWait();
        /*Submit the form in the result frame*/
        window.frames["resultFrame"].document.genericForm.submit();
    }
}

function submitDelete() {
    var selectedIndex = parseInt(window.frames["resultFrame"].currentIndex);
    if (selectedIndex < 0) {
        alert(messagesData.selectDeleteUser);
    }else {
        var selectedNode = window.frames["resultFrame"].currentXId;
        if (selectedNode == "UG") {
            alert(messagesData.selectUser);
        }else {
            /*Set any variables you want to send to the server*/
            var action = window.frames["resultFrame"].document.getElementById("action");
            action.value = 'delete';
			   var now = new Date();
				document.getElementById("startSearchTime").value = now.getTime();
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
		  document.getElementById("startSearchTime").value = now.getTime();
		  var obj = window.frames["resultFrame"].objs[selectedIndex];
        window.frames["resultFrame"].document.getElementById("userGroupId").value = obj.userGroupId;
        showPleaseWait();
        /*Submit the form in the result frame*/
        window.frames["resultFrame"].document.genericForm.submit();
    }
}

function submitSearchData() {
	/*Make sure to not set the target of the form to anything other than resultFrame*/
 	document.genericForm.target='resultFrame';
	var flag = validateForm();
	if(flag) {
		if (document.getElementById('viewTypeMy').checked) {
			document.getElementById("action").value = 'search';
			document.getElementById("resultView").value = 'resultTable';
		}else {
			document.getElementById("action").value = 'searchTree';
			document.getElementById("resultView").value = 'resultTree';
		}
		showPleaseWait();
		var now = new Date();
		document.getElementById("startSearchTime").value = now.getTime();
		return true;
	}else {
    return false;
  }
}

function reSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 var resultView = document.getElementById("resultView");
 if ("resultTree" == resultView.value) {
   action.value = 'searchTree';
 }else {
   action.value = 'search';
 }
 var flag = validateForm();
  if(flag) {
	var now = new Date();  
	document.getElementById("startSearchTime").value = now.getTime();
	showPleaseWait();
   document.genericForm.submit();
  }
}

function validateForm() {
	var result = true;

   return result;
}

function createNewUserGroup(){
    var flag = validateForm();
    var fullName = document.getElementById("lastName").value;
    var personnelId = document.getElementById("personnelId").value;
    if(document.getElementById("firstName").value != null && document.getElementById("firstName").value != "" )
    	fullName = fullName + ", " + document.getElementById("firstName").value;
    if(document.getElementById("midInitial").value != null && document.getElementById("midInitial").value != "" )
    	fullName = fullName + " " + document.getElementById("midInitial").value;
    
    if (flag) {
        var loc = "createnewusergroup.do?userGroupType="+$v("userGroupType")+"&companyId="+$v("companyId")+"&fullName="+fullName+"&personnelId="+personnelId;
        openWinGeneric(loc,"createnewusergroup123","570","130","yes","80","80","no")
    }
}

function createXSL() {
	var flag = validateForm();
   if (flag) {
		document.getElementById('action').value="createXSL";
		if (document.getElementById('viewTypeMy').checked) {
			document.getElementById('displayView').value="tableView";
		}else {
			document.getElementById('displayView').value="treeView";
		}
		document.getElementById('action').value="createXSL";
		document.genericForm.target='_excel_report_file';
	   openWinGenericExcel('/tcmIS/common/loadingfile.jsp','_excel_report_file','650','600','yes');
		setTimeout("document.genericForm.submit()",300);
	 }
}
