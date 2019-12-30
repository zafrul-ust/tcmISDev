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
}

function applicationChanged()
{
}

function showWorkareas(selfacilityId)
{
    var application = new Array();
    application = altapplication[selfacilityId];

	 var applicationdesc = new Array();
    applicationdesc = altapplicationdesc[selfacilityId];

	 try
	 {
     setApplication(0,messagesData.pleaseSelect,"All")

     for (var i=0; i < application.length; i++)
     {
        setApplication(i+1,applicationdesc[i],application[i])
     }
    }
    catch (ex)
    {
     setApplication(0,messagesData.pleaseSelect,"All")
    }
}

function setApplication(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var application = document.getElementById("application");
	 application[href] = optionName;
}

function validateCriteria()
{
    var facilityId  =  document.getElementById("facilityId");
    var application  =  document.getElementById("application");
    var dateDeliveredStart = document.getElementById("dateDeliveredStart");
    var dateDeliveredEnd = document.getElementById("dateDeliveredEnd");

	 if (facilityId.value == "All" || facilityId.value.trim() == 0 )
	 {
	  alert(messagesData.selectFacility);
	  return false;
	 }
    else if (application.value == "All" || application.value.trim() == 0 )
    {
     alert(messagesData.selectWorkarea);
     return false;
    }
	 else
    {
    
    return true;
    }
}

function search() {
  var flag = validateCriteria();
  if(!flag)  {
	return false;
	}
	else
	{
   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
  	return true;
	}
}

function doexcelpopup()
{
	  if(validateCriteria())  {
  	  	var userAction = document.getElementById("userAction");
  		userAction.value = 'buttonCreateExcel';
	  openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"show_excel_report_file","610","600","yes");             
		document.genericForm.target='show_excel_report_file';
    	var a = window.setTimeout("document.genericForm.submit();",500);
  		}
}


function showMsds(materialId,facilityId)
{
 loc = "/tcmIS/ray/ViewMsds?act=msds&showspec=N&id="+materialId+"&cl=Raytheon&facility="+facilityId+"";
 openWinGeneric(loc,"showRayMsds","800","600","yes","50","50");
}

function showErrorMessages()
{
		parent.showErrorMessages();
}
