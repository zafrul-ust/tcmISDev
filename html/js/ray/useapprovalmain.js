var windowCloseOnEsc = true;

function submitSearchForm()
{
	 var facilityId  =  document.getElementById("facilityId");
	    var application  =  document.getElementById("application");
	    var userGroupId  =  document.getElementById("userGroupId");

		 if (facilityId.value == "All" || facilityId.value.trim() == 0 )
		 {
		  alert(formatMessage(messagesData.mustbeselected, messagesData.facility));
		  return false
		 }
	    else if (application.value == "All" || application.value.trim() == 0 )
	    {
	     alert(formatMessage(messagesData.mustbeselected, messagesData.workarea));
	     return false
	    }
	    else if (userGroupId.value.trim() == 0 )
	    {
	     alert(formatMessage(messagesData.mustbeselected, messagesData.usergroup));
	     return false
	    }
		  else
		  {
			  var now = new Date();
			  document.getElementById("startSearchTime").value = now.getTime();
			  $('uAction').value = 'search';
			   document.genericForm.target='resultFrame';
			   showPleaseWait();
			   document.genericForm.submit();
		  }
}

function facilityIdChanged()
{
	var application = document.getElementById("application");
	var facilityId = document.getElementById("facilityId");
	var selfacilityId = facilityId.value;

	for (var i = application.length; i >= 0;i--)
   {
      application[i] = null;
   }
	showWorkareas(selfacilityId);
	application.selectedIndex = 0;
	removeAllUserGroups();
	showUserGroups(selfacilityId);
    var userGroupId = document.getElementById("userGroupId");
   userGroupId.value = "All";
}


function showWorkareas(selfacilityId)
{
    var application = new Array();
    application = altapplication[selfacilityId];

	 var applicationdesc = new Array();
    applicationdesc = altapplicationdesc[selfacilityId];

	 try
	 {
     setApplication(0,messagesData.pleaseselect,"All")

     for (var i=0; i < application.length; i++)
     {
        setApplication(i+1,applicationdesc[i],application[i])
     }
    }
    catch (ex)
    {
     setApplication(0,messagesData.pleaseselect,"All")
    }
}

function removeAllUserGroups()
{
	var userGroupId = document.getElementById("userGroupId");
	 if(userGroupId.length <= 0)
          return;

	for (var j = userGroupId.length; j >= 0;j--)
   {
      userGroupId[j] = null;
   }
}

function showUserGroups(selfacilityId)
{
    var usergroupid = new Array();
    usergroupid = altusergroupid[selfacilityId];

	 var usergroupdesc = new Array();
    usergroupdesc = altusergroupdesc[selfacilityId];

	 try
	 {
     //setUserGroup(0,"Please Select","All")
     for (var i=0; i < usergroupid.length; i++)
     {
        setUserGroup(i,usergroupdesc[i],usergroupid[i])
     }
    }
    catch (ex)
    {
	   //setUserGroup(0,"Approved to all users","All")
    }
}

function setApplication(href,text,id)
{
    text = htmlDencode(text);
    var optionName = new Option(text, id, false, false)
    var application = document.getElementById("application");
	 application[href] = optionName;
}

function setUserGroup(href,text,id)
{
    text = htmlDencode(text);
    var optionName = new Option(text, id, false, false)
    var userGroupId = document.getElementById("userGroupId");
	 userGroupId[href] = optionName;
}

function applicationChanged()
{
 
}

function createXSL(){
	  if(validateForm()) {
		$('uAction').value = 'createExcel';
	    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_WorkAreaManagementExcel','650','600','yes');
	    document.genericForm.target='_WorkAreaManagementExcel';
	    var a = window.setTimeout("document.genericForm.submit();",50);
	  }
	}


function showUseApprovers(facilityId,application)
{
   var facilityId  =  document.getElementById("facilityId");
   var application  =  document.getElementById("application");

	 if (facilityId.value == "All" || facilityId.value.trim() == 0 )
	 {
	  alert(formatMessage(messagesData.mustbeselected, messagesData.facility));
	  //return false;
	 }
    else if (application.value == "All" || application.value.trim() == 0 )
    {
     alert(formatMessage(messagesData.mustbeselected, messagesData.workarea));
     //return false;
    }
    else
    {
     
     loc = "useapprovalmain.do?uAction=showUseApprovers&facilityId="+facilityId.value+"&application="+application.value+"";
     
     openWinGeneric(loc,"NewCustomerContact","500","400","yes","50","50");

    }
}




