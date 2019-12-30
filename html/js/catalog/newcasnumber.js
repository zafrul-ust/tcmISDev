function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }

  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
  showErrorMessagesWin.render();
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function submitNew(){
 	return validateForm();
}

var casNumberPattern = /^\d+-\d\d-\d$/;
function validateForm() {
	var casNumber = $v("casNumber");
	if (casNumber != null && casNumber.length > 1 && !casNumber.startsWith("TS") && !casNumber.startsWith("NI")) {
		if (!casNumber.match(casNumberPattern)) {
			$("casNumber").focus();
			alert(messagesData.invalidcasnumber + " " + casNumber);
			return false;
		}
		else {
			var casPieces = casNumber.split("-");
			var checksum = (casPieces[1].charAt(1) * 1) + (casPieces[1].charAt(0) * 2);
			var cntr = 3;
			for (var index = casPieces[0].length - 1; index >= 0; index--) {
				checksum += casPieces[0].charAt(index) * cntr++;
			}
			checksum %= 10;
			if (checksum != (casPieces[2] * 1) || ((casPieces[0] + casPieces[1]) * 1) < 5000) {
				$("casNumber").focus();
				alert(messagesData.invalidcasnumber + " " + casNumber);
				return false;
			}
		}
	}
	return true;
}


