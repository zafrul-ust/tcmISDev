windowCloseOnEsc = true;

function myOnload() {	
	if ( showErrorMessage ) 
 		showErrorMessages();
 
 
 	if ( done ) {
 	
 		if(fromContactLookup)
 		{ 							
 			
 			if ($v('customerId')== 1782) {
			opener.opener.$("requestor").value = 0;
			}
			else {
				opener.opener.$("requestor").value = $v('personnelId');
			}
			
			opener.opener.$("requestorName").value = $v('lastName')+','+$v('firstName'); 
			opener.opener.$("requestorPhone").value = $v('phone');
			opener.opener.$("requestorFax").value = $v('fax');
			opener.opener.$("requestorEmail").value = $v('email');
			
			if(typeof(opener.opener.requestorChanged) != 'undefined')
			{
				try{
					opener.opener.requestorChanged();
				}catch(ex){}
			}
			
			opener.window.close();
 		}
 		
	 	if(fromCustomerDetail)
	 	{
		  //var a = new shipinfo();
		  var cusId = document.getElementById("customerId").value;	 
		  var loc = "/tcmIS/distribution/customerupdate.do?customerId="+cusId;	  
		 
		   try
		   {
			   opener.parent.parent.openIFrame("customerDetails"+cusId+"",loc,""+messagesData.customerdetails+" "+cusId+"","","N");
		   }	
		   catch (ex)
		   {
			   openWinGeneric(loc,"customerDetails"+cusId,"900","600","yes","80","80","yes");	
		   }
		   
	 	 }
		 window.close();
	}
}

function validateInput() {
  var errorMsg = "";
   if(isEmptyV("firstName")){
    errorMsg += "\n"+messagesData.firstnameRequired;
  }
  
  if(isEmptyV("lastName")){
    errorMsg += "\n"+messagesData.lastnameRequired;
  }
  if(isEmptyV("phone")) {
    errorMsg += "\n"+messagesData.phoneRequired;
  }
 
  if( errorMsg != '' ) {
   	alert(messagesData.validvalues+errorMsg);
   	return false;
  }
  return true;
}

function contactinfo() {
	this.contactName = $v('lastName')+", "+$v('lastName');
	this.contactPersonnelId = $v('contactPersonnelId');
	this.contactType = $v('contactType');
	this.phone = $v('phone');
	this.fax = $v('fax');
	this.mobile = $v('mobile');
	this.email = $v('email');
}

function newContact() {	
	if(validateInput())
	{
	   showPleaseWait();
	   
	   if(fromContactLookup || fromQuickCustomerView)
	   {
	   	 document.getElementById("uAction").value = "addAndShow";
	   }
	   else
	   {
		  if((document.getElementById("uAction").value >0) && (document.getElementById("modifyRecord").value=="Yes"))
		   	document.getElementById("uAction").value = "modify";
		  else
			document.getElementById("uAction").value = "new";
	   }  
	   if(!fromContactLookup && !fromQuickCustomerView)
	   {
		   try {
	   			window.opener.showTransitWin();
		   } catch(ex){}
	   }	   
	   
	   document.genericForm.target='_self';
 	   window.setTimeout("document.genericForm.submit();",500);
	}
}



