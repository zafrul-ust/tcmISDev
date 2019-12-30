windowCloseOnEsc = true;
function $(a) {
	return document.getElementById(a);
}

function isArray(testObject) { 
      return testObject && 
             !( testObject.propertyIsEnumerable('length')) && 
             typeof testObject === 'object' && 
             typeof testObject.length === 'number';
}

function setValues() {
	if( $('refresh').value == 'true' ) {
		if( $("tcmISError").value != '' ) 
			alert( $("tcmISError").value );
		opener.refresh();
		close();
	}
	$('facilityId').value = window.opener.$('facilityId').value;
	$('facilityName').innerHTML = window.opener.$('facilityName').value;
	$('companyId').value = window.opener.$('companyId').value;

	var obj = opener.getCurrentObject();

	var act = $('uAction').value;
	if( act == 'Add' ) {
		$('personnelName').value = '';
		$('personnelId').value = '';
	}
	else {
		//prepare clean username by removing limit values from string
		var userName = obj.userName;
		if (obj.costLimit != '-1')
			userName = userName.replace(obj.costLimit, "");
		else
			userName = userName.replace("Unlimited", "");
		if (obj.approvalLimit != '-1')
			userName = userName.replace(obj.approvalLimit, "");
		else
			userName = userName.replace("Unlimited", "");
		userName = userName.replace("/", "");
		
		$('personnelName').innerHTML = userName;
		$('personnelId').value = obj.personnelId;
	}
	if( act != 'Delete' ) {
		if( obj == null ) 
			$('costLimit').value = -1;
		else
			$('costLimit').value = obj.costLimit;
		if( $('costLimit').value == -1 )
			$('costLimitDisplay').value = 'Unlimited';
		else
			$('costLimitDisplay').value = $('costLimit').value;
		if( obj == null ) 
			$('approvalLimit').value = -1;
		else
			$('approvalLimit').value = obj.approvalLimit;
		if( $('approvalLimit').value == -1 )
			$('approvalLimitDisplay').value = 'Unlimited';
		else
			$('approvalLimitDisplay').value = $('approvalLimit').value;
	}
	var tree = opener.getTree();
	
	var inserted = 1;
	var insertAt = 0 ;
	setOption(0,"",'',"approverId");
	if( act == 'Add' ) {
		var ppid = '';
		if( obj != null ) ppid = obj.approverId;
		for (var i=0; i < tree.length; i++) {
			if( tree[i].inTree ) {
				setOption(inserted,tree[i].userName,tree[i].personnelId,"approverId");
				if( tree[i].personnelId == ppid ) insertAt = inserted;
    			inserted++;
    		}
		}
	}
	else if( act == 'Edit' ) {
		for (var i=0; i < tree.length; i++) {
			if( tree[i].personnelId != obj.personnelId && tree[i].inTree ) {
				setOption(inserted,tree[i].userName,tree[i].personnelId,"approverId");
				if( tree[i].personnelId == obj.approverId ) insertAt = inserted;
    			inserted++;
    		}
		}
	}
	else if( act == 'Delete' ) {
		var max = opener.getMaxLimit(obj.personnelId);
		for (var i=0; i < tree.length; i++) {
			if( tree[i].personnelId != obj.personnelId && tree[i].inTree ) {
				if( tree[i].approvalLimit == -1 || max < tree[i].approvalLimit ) {
					setOption(inserted,tree[i].userName,tree[i].personnelId,"approverId");
    				inserted++;
    			}
    		}
		}
		alert(messagesData.replaceapprover);
	}
	$('approverId').options.selectedIndex = insertAt;
}

function search() {
	if( $('personnelId').value == '' ) {
		alert( messagesData.pleaseselectuser );
		return;
	}
	var pid = $('approverId').value;
	if( $('uAction').value != 'Delete' ) {
		if( $('costLimitDisplay').value == "Unlimited" )
			$('costLimit').value = -1;
		else
			$('costLimit').value = $('costLimitDisplay').value;
		if( $('approvalLimitDisplay').value == "Unlimited" )
			$('approvalLimit').value = -1;
		else
			$('approvalLimit').value = $('approvalLimitDisplay').value;
		var parentLimit = opener.getApprovalLimit(pid);
		if( $('uAction').value == 'Edit' ) {
			var val = parseInt( $('approvalLimit').value );
			if( pid != '' && ( val == -1 || ( parentLimit != -1 && parentLimit <= val ) ) ) {
				alert(messagesData.canootexceedapproverlimit);
				return;
			}
		}
	opener.setExpandTo(  $('personnelId').value );
	document.genericForm.submit();
	}
	else { // uAction is 'Delete'
		if( pid == '' ) pid = '';
		opener.replaceNodeWait("'"+$('personnelId').value+"'","'"+pid+"'");
		close();
	}
}