function search() {
   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
  	return true;
}

function pagePermissionsUpdate() {
    for(i=0;i>-1;i++) {
    	if( document.getElementById('pageAccess_'+i) == null ) break;
    	if( document.getElementById('pageAccess_'+i).checked &&
    	    ( document.getElementById('old_pageAccess_'+i).value != "Y" ) ) {
    	    document.getElementById('modified_'+i).value = 'Y';
    	}
    	if( !document.getElementById('pageAccess_'+i).checked &&
    	    (document.getElementById('old_pageAccess_'+i).value == "Y" ) ) {
    	    document.getElementById('modified_'+i).value = 'Y';
    	}
    	if( document.getElementById('adminRole_'+i).checked &&
    	    ( document.getElementById('old_adminRole_'+i).value != "Admin" ) ) {
    	    document.getElementById('modified_'+i).value = 'Y';
    	}
    	if( !document.getElementById('adminRole_'+i).checked &&
    	    (document.getElementById('old_adminRole_'+i).value == "Admin" ) ) {
    	    document.getElementById('modified_'+i).value = 'Y';
    	}
    }
	document.getElementById('action').value="update";
	document.genericForm.submit();
}
