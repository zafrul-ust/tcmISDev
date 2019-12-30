function okCClick() {
	showConfirmErrorMsgWin.hide();
}

function $(a) {
	return document.getElementById(a);
}

function initWin() {
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
	 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}
	/*Yui pop-ups need to be initialized onLoad to make them work correctly.
	If they are not initialized onLoad they tend to act weird*/
	showConfirmErrorMsgWin = new YAHOO.widget.Panel("confirmErrorMsgArea", { width:"200px", height:"150px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:false, visible:false,
	draggable:true, modal:false } );
	showConfirmErrorMsgWin.render();
}


function showErrorMessages()
{
  var confirmErrorMsgArea = document.getElementById("confirmErrorMsgArea");
  confirmErrorMsgArea.style.display="";
  showConfirmErrorMsgWin.show();
}

function changeSupplier() {
    if( $("refresh").value.trim() != '' ) 
    {   window.close();
    	opener.reSubmit();}
        
		
 // if errorMsg is null or "" then the form close and parent search will re-search.
		if( $("messageToUser").value.trim() != '' )
			setTimeout('showErrorMessages()',200);
		
}