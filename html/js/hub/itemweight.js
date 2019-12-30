/*Call this if you want to initialize something on load in the search frame.*/
function submitSearchForm() {
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var flag = true;
  if(flag) {
// for auto resubmit search..
   document.genericForm.target='resultFrame';
   parent.showPleaseWait();
   document.genericForm.submit();
  }
}

function validateForm() {
	var obj;
	var i = 0 ;
	var total = parseInt(document.getElementById('totalLines').value);
	for(;i <total ; i++) {
		if( document.getElementById('ok_'+i) == null || !document.getElementById('ok_'+i).checked ) continue;
		obj = document.getElementById('ui_'+i);
		obj.value = obj.value.trim();
		if( obj.value == '' )  {
			alert(messagesData.cannotbeempty+"["+messagesData.ui+"]");
			obj.focus();
			return false;
		}
		obj = document.getElementById('grossWeightLbs_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.grossWeightLbs+"]");
			obj.focus();
			return false;
		}
		obj = document.getElementById('cubicFeet_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.cubicFeet+"]");
			obj.focus();
			return false;
		}
/*		obj = document.getElementById('containersPerUi_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.containersPerUi+"]");
			obj.focus();
			return false;
		}*/
		obj = document.getElementById('maxNsnPerBox_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.maxNsnPerBox+"]");
			obj.focus();
			return false;
		}
		obj = document.getElementById('maxNsnPerPallet_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.maxNsnPerPallet+"]");
			obj.focus();
			return false;
		}
		obj = document.getElementById('externalContainer_'+i);
		obj.value = obj.value.trim();
		if( obj.value == '' )  {
			alert(messagesData.cannotbeempty+"["+messagesData.externalContainer+"]");
			obj.focus();
			return false;
		}
	}
	return true;
}


function update() {
 /*Make sure to not set the target of the form to anything other than resultFrame*/
 var flag = true;//validateForm();
  if(flag) {
// for auto resubmit search..
   var action = document.getElementById("action");
   action.value = "update";
   document.genericForm.target='resultFrame';
   parent.showPleaseWait();
   document.genericForm.submit();
  }
}

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

function okClick(j) {
	var i=j;
	var flag = true;
	for(;i==j;i++)	{
		if( document.getElementById('ok_'+i) == null || !document.getElementById('ok_'+i).checked ) return;
		obj = document.getElementById('ui_'+i);
		obj.value = obj.value.trim();
		if( obj.value == '' )  {
			alert(messagesData.cannotbeempty+"["+messagesData.ui+"]");
			obj.focus();
			flag = false;
			break;
		}
		obj = document.getElementById('grossWeightLbs_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.grossWeightLbs+"]");
			obj.focus();
			flag = false;
			break;
		}
		obj = document.getElementById('cubicFeet_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.cubicFeet+"]");
			obj.focus();
			flag = false;
			break;
		}
/*		obj = document.getElementById('containersPerUi_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.containersPerUi+"]");
			obj.focus();
			flag = false;
			break;
		}*/
		obj = document.getElementById('maxNsnPerBox_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.maxNsnPerBox+"]");
			obj.focus();
			flag = false;
			break;
		}
		obj = document.getElementById('maxNsnPerPallet_'+i);
		obj.value = obj.value.trim();
		if( !isFloat(obj.value) )  {
			alert(messagesData.mustbeanumber+"["+messagesData.maxNsnPerPallet+"]");
			obj.focus();
			flag = false;
			break;
		}
		obj = document.getElementById('externalContainer_'+i);
		obj.value = obj.value.trim();
		if( obj.value == '' )  {
			alert(messagesData.cannotbeempty+"["+messagesData.externalContainer+"]");
			obj.focus();
			flag = false;
			break;
		}
	}
	document.getElementById('ok_'+j).checked = flag;
}	
