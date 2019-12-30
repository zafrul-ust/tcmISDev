function $(a) {
	return document.getElementById(a);
}


function submitSearchForm() {
	if( !validatePickingForm() ) return;
	
	var userAction = document.getElementById("action");
	userAction.value = 'search';

   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
   	document.genericForm.submit();
}

function doregenerate() {
	if( !validatePickingForm() ) return;
	
	var userAction = document.getElementById("action");
	userAction.value = 'regenerate';

   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
   	document.genericForm.submit();
}

function createExcel() {
	if( !validatePickingForm() ) return;
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'show_excel_report_file','800','600','yes');
		document.genericForm.target='show_excel_report_file';
  	var userAction = document.getElementById("userAction");
 		userAction.value = 'createExcel';
    var a = window.setTimeout("document.genericForm.submit();",1000);
    //document.genericForm.submit();
   	return false;
}

function resultOnLoad()
{
 setResultFrameSize();
 displaySearchDuration(); 
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function rePrintPicklist()
{
 if ($("picklistId").value.trim().length > 0)
 {
     if ($("fromPickingPicklist").value.trim() == 'Y')
     {
         if ($v("distribution") == 'Y')
         {
        	 if($v("opsEntityId") == 'AVICHAAS')
        	 {
        		 var loc = "/HaasReports/report/printpicklist.do?picklistId="+$("picklistId").value+"&personnelId="+$("personnelId").value+"&locale="+$("personnelLocale").value;
        	 }
        	 else
        	 {
        	   var loc = "/HaasReports/report/printpicklist.do?picklistId="+$("picklistId").value+"&personnelId="+$("personnelId").value+"&locale="+$("personnelLocale").value;
        	 }
         }
         else
         {
        	 //var loc = "/tcmIS/Hub/searchpicklist?session=Active&generate_picklist=yes&UserAction=generatepicks&SubUserAction=printpicklist&SortBy="+$("sortBy").value+"";
        	 if($v("opsEntityId") == 'HAASTCMDEU' && $v("inventoryGroup") == 'Bad Neustadt')
        	 {
        		 var loc = "/HaasReports/report/printConfigurableReport.do?pr_picklist_id="+$("picklistId").value+
       			"&rpt_ReportBeanId=GermanyPrintablePickListReportDefinition";
        	 }
        	 else
        	 {
            	 var loc = "/HaasReports/report/printConfigurableReport.do?pr_picklist_id="+$("picklistId").value+
      			"&rpt_ReportBeanId=printablePickListReportDefinition";
        	 }
         }
      openWinGeneric(loc,"printpicklistddd","800","600","yes","80","80");
     }
     else
     {
    	 var personnelId = document.getElementById("personnelId").value;
    	 var loc = "/tcmIS/hub/printpicklist.do?picklistId="+$("picklistId").value+"&sortBy="+$("sortBy").value+"&personnelId="+$("personnelId").value;
    	 openWinGeneric(loc,"printpicklistddd","800","600","yes","80","80");
     }
 }
}

function printBolShort() {
 if ($("picklistId").value.trim().length > 0)
 {
   var checked = countChecked();
   if (checked==0) {
      alert(messagesData.pleasemakeselection);
      return false;
   }
   else {
   openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printBolShortddd','800','600','yes',"80","80");
	 document.genericForm.target='printBolShortddd';
  	var action = document.getElementById("action");
 		action.value = 'printBolShort';
    var a = window.setTimeout("document.genericForm.submit();",1000);
   }
   }
}

function printBolLong() {
 if ($("picklistId").value.trim().length > 0)
 {
   var checked = countChecked();
   if (checked==0) {
      alert(messagesData.pleasemakeselection);
      return false;
   }
   else {
   openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printBolLongddd','800','600','yes',"80","80");
	 document.genericForm.target='printBolLongddd';
  	var action = document.getElementById("action");
 		action.value = 'printBolLong';
    var a = window.setTimeout("document.genericForm.submit();",1000);
    }
   }
}

function printBoxLabels() {
 if ($("picklistId").value.trim().length > 0)
 {
   var checked = countChecked();
   if (checked==0) {
      alert(messagesData.pleasemakeselection);
      return false;
   }
   else {
   openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printBoxLabelsddd','800','600','yes',"80","80");
	 document.genericForm.target='printBoxLabelsddd';
  	var action = document.getElementById("action");
 		action.value = 'printBoxLabels';
    var a = window.setTimeout("document.genericForm.submit();",1000);
    }
   }
}

function countChecked() {
  var totallines = $("totalLines").value;
  // first do rows
  var totalChecked = 0;
  for ( var p = 0; p <= totallines; p++ )
  {
     try
     {
        var checkboxname = 'ok' + p;
        var chkname = 'document.genericForm.' + checkboxname;

        var linecheck = eval(chkname);

        if (linecheck.checked==true) {
          totalChecked++;
        }
     }
     catch (ex1)
     {
     }
  }
  return totalChecked;
}

function checkall(checkbx, formname, checkboxname, headername)
{
    var totallines = $("totalLines").value;
    var result;
    var valueq;

    if (checkbx.checked)
    {
        result = true;
        valueq = "yes";
    }
    else
    {
        result = false;
        valueq = "no";
    }

    // first do rows
    for ( var p = 0; p <= totallines; p++ )
    {
        try
        {
            var chkname = 'document.' + formname + '.' + checkboxname+p;
            var linecheck = eval(chkname);

            linecheck.checked =result;
            linecheck.value = valueq;
        }
        catch (ex1)
        {

        }
    }

    // next do headers
    for ( p = 1; p <= totallines; p++ )
    {
        try
        {
            var chkname = 'document.' + formname + '.' + headername+p;
            var linecheck = eval(chkname);

            linecheck.checked =result;
            linecheck.value = valueq;
        }
        catch (ex1)
        {

        }
    }
}

function printConsolidatedBol()
{
  if ($("picklistId").value.trim().length > 0)
 {
   //http://localhost/tcmIS/Hub/searchpicklist?session=Active&generate_picklist=yes&UserAction=genconsolidatedpicks
   var loc = "/tcmIS/Hub/searchpicklist?session=Active&generate_picklist=yes&UserAction=genconsolidatedpicks&opsEntityId="+$v("opsEntityId");
   openWinGeneric(loc,"printConsolidatedBolddd","800","600","yes","80","80");
 }
}

function showDeliveryComments2(rowId)
{
  var deliveryCommentsMessagesAreaBody = parent.document.getElementById("deliveryCommentsMessagesAreaBody");
  deliveryCommentsMessagesAreaBody.innerHTML = $v("mrNotes"+rowId);

  var deliveryCommentsMessagesArea = parent.document.getElementById("deliveryCommentsMessagesArea");
  deliveryCommentsMessagesArea.style.display="";

  parent.deliveryCommentsWin.show();
}

function consolidatedBolExcel()
{
    var action = document.getElementById("action");
    action.value = 'createConsPLExcel';	
	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_PickListGenerateExcel','650','600','yes');
    document.genericForm.target='_PickListGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
}

function updateSerialNumbers()
{
	var action = document.getElementById("action");
	action.value = 'updateSerialNumbers';

   	parent.showPleaseWait();
   	document.genericForm.submit();
}

function printUnitExtLabels() {
	var valid = countChecked();

	if (valid) {
		$("labelType").value="UNITEXT";
		openWinGeneric('/tcmIS/common/loadingfile.jsp', 'printUnitExtddd','800','600','yes',"80","80");
		 document.genericForm.target='printUnitExtddd';
	  	var action = document.getElementById("action");
	 		action.value = 'printUnitExtLabels';
	    var a = window.setTimeout("document.genericForm.submit();",1000);
	}
	else {
		alert(messagesData.pleasemakeselection);
	}
}

