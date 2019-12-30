var submitcount=0;
var reportFieldCount = 0;

function hubChanged(object)
{
   var artist;
   artist = object.options[object.selectedIndex].text;

   var selectedIndex = object.selectedIndex;
   //second reload work area for selected facility
   for (var i = document.invoicereports.facilityName.length; i > 0;i--)
   {
      document.invoicereports.facilityName.options[i] = null;
   }
   showFacilitylinks(selectedIndex);
   window.document.invoicereports.facilityName.selectedIndex = 0;
   //reset selected index the first element in the facility list
   selectedIndex = 1;
   var facID = window.document.invoicereports.facilityName.options[window.document.invoicereports.facilityName.selectedIndex].text;
   //second reload work area for selected facility
   for (var i = document.invoicereports.workAreaName.length; i > 0;i--)
   {
      document.invoicereports.workAreaName.options[i] = null;
   }

   showWorkArealinks(facID);
   window.document.invoicereports.workAreaName.selectedIndex = 0;
   //next reload accounting system for selected facility
   for (var j = document.invoicereports.actSystem.length; j > 0;j--)
   {
      document.invoicereports.actSystem.options[j] = null;
   }
   showActSyslinks(facID);
   window.document.invoicereports.actSystem.selectedIndex = 0;
}

function showFacilitylinks(selectedIndex)
{
    var showFac = hubID[selectedIndex];
    var nickNameValue = new Array();
    var nickNameValueid = new Array();
    nickNameValue = altFacDesc[showFac];
    nickNameValueid = altFacID[showFac];
    for (var i=0; i < nickNameValue.length; i++)
    {
        setFacility(i,nickNameValue[i],nickNameValueid[i])
    }
}

function setFacility(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.invoicereports.facilityName.options[href] = optionName;
}

function dateDeliveredGroupByChanged(object) {
   var dayC = document.getElementById("dayC");
   var monthC = document.getElementById("monthC");
   var yearC = document.getElementById("yearC");
   if (object == dayC) {
       window.document.invoicereports.dayC.checked = true;
       window.document.invoicereports.monthC.checked = false;
       window.document.invoicereports.yearC.checked = false;
   }else if (object == monthC) {
       window.document.invoicereports.dayC.checked = false;
       window.document.invoicereports.monthC.checked = true;
       window.document.invoicereports.yearC.checked = false;
   }else {
       window.document.invoicereports.dayC.checked = false;
       window.document.invoicereports.monthC.checked = false;
       window.document.invoicereports.yearC.checked = true;
   }
}

function clearRequestor(object,object2)
{
   object.value = "0";
   object2.value = "";
}

function getUserID(last,first,userID)
{
   opener.document.invoicereports.RequestorID.value = userID;
   opener.document.invoicereports.requestorName.value = last+", "+first;
   closeUserWin();
}

function lastNameChecked() {
   var lastName = document.getElementById("lastR");
   if (lastName.checked) {
       window.document.searchUserWin.firstR.checked = false;
   }else {
       window.document.searchUserWin.firstR.checked = true;
   }
}

function firstNameChecked() {
   var firstName = document.getElementById("firstR");
   if (firstName.checked) {
       window.document.searchUserWin.lastR.checked = false;
   }else {
       window.document.searchUserWin.lastR.checked = true;
   }
}

function searchUser()
{
   var tmp = document.getElementById("searchString");
   if (tmp.value.length > 1) {
       return true;
   }else {
       alert("Search text is blank.");
       return false;
   }
}

function closeUserWin() {
   window.close();
}

function searchUserWin()
{
   var companyID = document.getElementById("companyID");
   var loc = "/tcmIS/"+companyID.value+"/costreport?Action=searchWin";
   if (companyID.value == "boeing") {
     loc = "/tcmIS/ula/costreport?Action=searchWin";
   }
   openWinGeneric(loc,"searchWin","410","330","yes","240","280");
}

function showAdditionalCharge(sqlFields,sqlValues,sqlFieldDelim)
{
   var companyID = document.getElementById("companyID");
   var loc = "/tcmIS/"+companyID.value+"/costreport?Action=addCharge&sqlFields="+sqlFields+"&sqlValues="+sqlValues+"&sqlFieldDelim="+sqlFieldDelim;
   if (companyID.value == "boeing") {
     loc = "/tcmIS/ula/costreport?Action=addCharge&sqlFields="+sqlFields+"&sqlValues="+sqlValues+"&sqlFieldDelim="+sqlFieldDelim;
   }
   openWinGeneric(loc,"addCharge","410","330","yes","240","280");
}

function generateXlsReport()
{
   var companyID = document.getElementById("companyID");
   var loc = "/tcmIS/"+companyID.value+"/costreport?Action=generateXlsReport";
   if (companyID.value == "boeing") {
     loc = "/tcmIS/ula/costreport?Action=generateXlsReport";
   }
   windowprops = "toolbar=yes,location=yes,directories=yes,menubar=yes,scrollbars=yes,status=yes,resizable=yes";
   preview = window.open(loc,"OpenXlsReport",windowprops);
}

function clearDate(object,object2)
{
   object.value = "";
   object2.value = "";
}

function reportFieldChanged(object)
{
   try {
        if (object.checked) {
            reportFieldCount++;
        }else {
            reportFieldCount--;
        }
   }catch (ex) {
        //don't do anything
   }
}

function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
		  /*
		  try {
        	var target = document.all.item("TRANSITPAGE");
        	target.style["display"] = "";
        	var target1 = document.all.item("MAINPAGE");
        	target1.style["display"] = "none";
    	  }catch (ex) {
    	  	//alert("-------- catch except");
    	  }*/
        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

function resetReportFieldCount()
{
   var tmp = document.getElementById("ReportFieldCount");
   reportFieldCount = tmp.value;
}

function initialDisplay()
{
	disableAccountSysInfo();
}

function accountSysChanged(object)		//object is the parent's object
{
	var artist;
   artist = object.options[object.selectedIndex].text;
	if (artist == "All Accounting Systems") {
		disableAccountSysInfo();
	}else {
		window.document.invoicereports.direct.checked = true;
		window.document.invoicereports.inDirect.checked = false;
		showAccountSysInfo(artist);
	}
}

function facilityChanged(object)		//object is the parent's object
{
    var artist;
    artist = object.options[object.selectedIndex].text;

	 var selectedIndex = object.selectedIndex;
	 //first reload work area for selected facility
    for (var i = document.invoicereports.workAreaName.length; i > 0;i--)
    {
        document.invoicereports.workAreaName.options[i] = null;
    }
    reloading = true;
    //showWorkArealinks(selectedIndex);
    showWorkArealinks(artist);
    window.document.invoicereports.workAreaName.selectedIndex = 0;
    //next reload accounting system for selected facility
	 for (var j = document.invoicereports.actSystem.length; j > 0;j--)
    {
        document.invoicereports.actSystem.options[j] = null;
    }
    //showActSyslinks(selectedIndex);
    showActSyslinks(artist);
    window.document.invoicereports.actSystem.selectedIndex = 0;
 }

function showActSyslinks(showActSys)
{
    //var showActSys = facID[selectedIndex];
    var nickNameValueID = new Array();
    nickNameValueID = altActSysID[showActSys];
    var count = 0;
    for (var i=0; i < nickNameValueID.length; i++)
    {
        setActSystem(i,nickNameValueID[i],nickNameValueID[i]);
        count++;
    }
    //if only one accounting system then display that accounting system info
    //otherwise, don't display account info
    if (count > 1) {
		disableAccountSysInfo();
    }else {
		if (nickNameValueID[0] == "All Accounting Systems") {
			disableAccountSysInfo();
    	}else {
			showAccountSysInfo(nickNameValueID[0]);
		}
    }
}

function showAccountSysInfo(actSys)
{
	var accountSysD = new Array();
	accountSysD = altActType[actSys+"d"];
	var accountSysI = new Array();
	accountSysI = altActType[actSys+"i"];
	//first got to decide wether to display charge type
	var tmp = document.getElementById("directCell");
	if (accountSysD != null && accountSysI != null) {
		tmp.style["display"] = "";
		tmp = document.getElementById("indirectCell");
		tmp.style["display"] = "";
		//set the blank row 1 to invisible
		tmp = document.getElementById("blankRow1");
		tmp.style["display"] = "None";
	}else {
		tmp.style["display"] = "None";
		window.document.invoicereports.direct.checked = true;
		tmp = document.getElementById("indirectCell");
		tmp.style["display"] = "None";
		window.document.invoicereports.inDirect.checked = false;
		//set the blank row 1 to visible
		tmp = document.getElementById("blankRow1");
		tmp.style["display"] = "";
	}
	//determine selected charge type
	var chargeType;
	var direct = document.getElementById("direct");
	if (direct.checked) {
		chargeType = "d";
	}else {
		chargeType = "i";
	}
	//display account info according to charge type
	if (chargeType == "d") {
		if(accountSysD == null) {
			alert("------ account direct is null");
		}else {
			if (accountSysD[0] == "p") {
				window.document.invoicereports.PoRequired.value = "p";
				tmp = document.getElementById("chargeNumber1Label");
				tmp.innerHTML = "<B>PO/BPO:</B>\n";
				tmp.style["display"] = "";
				tmp = document.getElementById("chargeNumber1");
				tmp.style["display"] = "";
				//set the blank row 2 to invisible
				tmp = document.getElementById("blankRow2");
				tmp.style["display"] = "None";
				//set the blank row 3 to visible
				//tmp = document.getElementById("blankRow3");
				//tmp.style["display"] = "";
			}else {
				window.document.invoicereports.PoRequired.value = "n";
				tmp = document.getElementById("chargeNumber1Label");
				tmp.innerHTML = "<B>"+accountSysD[1]+":</B>\n";
				tmp.style["display"] = "";
				tmp = document.getElementById("chargeNumber1");
				tmp.style["display"] = "";
				//set the blank row 2 to invisible
				tmp = document.getElementById("blankRow2");
				tmp.style["display"] = "None";
				if (accountSysD[2] != "Charge Number 2") {
					tmp = document.getElementById("chargeNumber2Label");
					tmp.innerHTML = "<B>"+accountSysD[2]+":</B>\n";
					tmp.style["display"] = "";
					tmp = document.getElementById("chargeNumber2");
					tmp.style["display"] = "";
					//set the blank row 3 to invisible
					//tmp = document.getElementById("blankRow3");
					//tmp.style["display"] = "None";
				}else {
					tmp = document.getElementById("chargeNumber2Label");
					tmp.innerHTML = "<B>Charge Number 2:</B>\n";
					tmp.style["display"] = "None";
					tmp = document.getElementById("chargeNumber2");
					tmp.style["display"] = "None";
					//set the blank row 3 to visible
					//tmp = document.getElementById("blankRow3");
					//tmp.style["display"] = "";
				}
			}
		}
	}else {
		if (accountSysI == null) {
			alert("------ account indirect is null");
		}else {
			if (accountSysI[0] == "p") {
				window.document.invoicereports.PoRequired.value = "p";
				tmp = document.getElementById("chargeNumber1Label");
				tmp.innerHTML = "<B>PO/BPO:</B>\n";
				tmp.style["display"] = "";
				tmp = document.getElementById("chargeNumber1");
				tmp.style["display"] = "";
				//set the blank row 2 to invisible
				tmp = document.getElementById("blankRow2");
				tmp.style["display"] = "None";
				//set the blank row 3 to visible
				//tmp = document.getElementById("blankRow3");
				//tmp.style["display"] = "";
			}else {
				window.document.invoicereports.PoRequired.value = "n";
				tmp = document.getElementById("chargeNumber1Label");
				tmp.innerHTML = "<B>"+accountSysI[1]+":</B>\n";
				tmp.style["display"] = "";
				tmp = document.getElementById("chargeNumber1");
				tmp.style["display"] = "";
				//set the blank row 2 to invisible
				tmp = document.getElementById("blankRow2");
				tmp.style["display"] = "None";
				if (accountSysI[2] != "Charge Number 2") {
					tmp = document.getElementById("chargeNumber2Label");
					tmp.innerHTML = "<B>"+accountSysI[2]+":</B>\n";
					tmp.style["display"] = "";
					tmp = document.getElementById("chargeNumber2");
					tmp.style["display"] = "";
					//set the blank row 3 to invisible
					//tmp = document.getElementById("blankRow3");
					//tmp.style["display"] = "None";
				}else {
					tmp = document.getElementById("chargeNumber2Label");
					tmp.innerHTML = "<B>Charge Number 2:</B>\n";
					tmp.style["display"] = "None";
					tmp = document.getElementById("chargeNumber2");
					tmp.style["display"] = "None";
					//set the blank row 2 to visible
					//tmp = document.getElementById("blankRow3");
					//tmp.style["display"] = "";
				}
			}
		}
	}	//end of charge type = 'i'
 }	//end of method

function disableAccountSysInfo() 		//make element invisible
{
	var tmp = document.getElementById("directCell");
	tmp.style["display"] = "none";
	window.document.invoicereports.direct.checked = true;
	tmp = document.getElementById("indirectCell");
	tmp.style["display"] = "none";
	window.document.invoicereports.inDirect.checked = false;
	tmp = document.getElementById("chargeNumber1Label");
	tmp.style["display"] = "none";
	tmp = document.getElementById("chargeNumber1");
	tmp.style["display"] = "none";
	tmp.value = "";
	tmp = document.getElementById("chargeNumber2Label");
	tmp.style["display"] = "none";
	tmp = document.getElementById("chargeNumber2");
	tmp.style["display"] = "none";
	tmp.value = "";
	tmp = document.getElementById("blankRow1");
	tmp.style["display"] = "";
	tmp = document.getElementById("blankRow2");
	tmp.style["display"] = "";
	//tmp = document.getElementById("blankRow3");
	//tmp.style["display"] = "";
}

function setActSystem(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.invoicereports.actSystem.options[href] = optionName;
}

function showWorkArealinks(showWA)
{
    /*
    var showWA;
    if (facID == "UseIndex") {
        showWA = facID[selectedIndex];
    }else {
        showWA = facID;
    }*/
    var nickNameValue = new Array();
    var nickNameValueid = new Array();
    nickNameValue = altWADesc[showWA];
    nickNameValueid = altWAID[showWA];
    for (var i=0; i < nickNameValue.length; i++)
    {
        setWorkArea(i,nickNameValue[i],nickNameValueid[i])
    }
}

function setWorkArea(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.invoicereports.workAreaName.options[href] = optionName;
}

function generateReport(name, entered)
{
	//set action value
	var actvalue = name.toString();
   window.document.invoicereports.Action.value = actvalue;

	//audit report data
	//first can't entered a search text without choosing a search text option
	var tmp = document.getElementById("searchBy");
	if (tmp.value == "None") {
		var tmp2 = document.getElementById("searchText");
		if (tmp2.value.length > 0) {
			alert("Please select a Search By for the given search text: "+tmp2.value);
			return false;
		}
	}

   //make sure the the end date is not before start date
   //first invoice date
   tmp = document.getElementById("invoiceStartDate");
   tmp2 = document.getElementById("invoiceEndDate");
   if (tmp.value.length > 0 && tmp2.value.length > 0) {
       if (!validateDate(tmp,tmp2)) {
           alert("Invoice Between: 'End Date' cannot be before 'Start Date'.");
           return false;
       }
   }
   //next delivered date
   tmp = document.getElementById("startDate");
   tmp2 = document.getElementById("endDate");
   if (tmp.value.length > 0 && tmp2.value.length > 0) {
       if (!validateDate(tmp,tmp2)) {
           alert("Delivered Between: 'End Date' cannot be before 'Start Date'.");
           return false;
       }
   }

   //can't generate report without report field(s)
   if (reportFieldCount < 1) {
       alert("Please select at least one report field.");
       return false;
   }else {
      window.document.invoicereports.ReportFieldCount.value = reportFieldCount;
   }

   var itype = document.getElementById("commodity");
   return true;
}

//return true if the two date is respectively valid - startDate must be before endDate
function validateDate(startDate,endDate)
{
   try {
        //start date
        var startDateArray = startDate.value.split("/");
        var startDateMonth = startDateArray[0];
        var startDateDay = startDateArray[1];
        var startDateYear = startDateArray[2];
        //end date
        var endDateArray = endDate.value.split("/");
        var endDateMonth = endDateArray[0];
        var endDateDay = endDateArray[1];
        var endDateYear = endDateArray[2];
        //first compare the year
        if (endDateYear < startDateYear) {
           return false;
        }else if (endDateYear == startDateYear) {
           //next compare the month
           if (endDateMonth < startDateMonth) {
               return false;
           }else if (endDateMonth == startDateMonth) {
               //finally compare the day
               if (endDateDay < startDateDay) {
                   return false;
               }else {
                   return true;
               }
           }else {
              return true;
           }
        }else {
           return true;
        }
   } catch (ex) {
     return false;
   }
}

function clearTemplate(name, entered)
{
   window.document.invoicereports.Action.value = "New";
   return true;
}

function saveTemplate(name, entered)
{
	var actvalue = name.toString();
   window.document.invoicereports.Action.value = actvalue;
   window.document.invoicereports.ReportFieldCount.value = reportFieldCount;

	var templateName = document.getElementById("TemplateName");
	while (true) {
		var tmp;
		if (templateName.value == "default") {
			tmp = prompt("Please enter a template name in the text area below.",'');
		}else {
			tmp = prompt("Please enter a template name.",templateName.value);
		}
		if (tmp == null) {
			return false;
		}
		if (tmp == templateName.value) {
			var ok = confirm("Template name already exist. Do you want to replace it?");
			if (ok) {
				break;
			}
		}else {
			window.document.invoicereports.TemplateName.value = tmp;
			break;
		}
	}
   return true;
}

function templateLoaded()
{
	//display account system info
	var tmp = document.getElementById("actSystem");
	if (tmp.value == "All Accounting Systems") {
		disableAccountSysInfo();
	}else {
		showAccountSysInfo(tmp.value);
	}
   //set report field count
   resetReportFieldCount();
}

function openTemplateOk(object)
{
   //alert("User click okay: "+object.options[object.selectedIndex].text);
   opener.document.invoicereports.TemplateName.value = object.options[object.selectedIndex].text;
   closeUserWin();
   opener.document.invoicereports.submit();
}

function openTemplate(name, entered)
{
   var actvalue = name.toString();
   window.document.invoicereports.Action.value = actvalue;

   windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=240,left=280,width=550,height=230,resizable=yes";
   preview = window.open("","OpenTemplateWin123",windowprops);
   preview.document.write("<HTML><HEAD>\n");
   preview.document.write("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
   preview.document.write("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
   preview.document.write("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
   preview.document.write("<LINK REL=\"SHORTCUT ICON\" HREF=\"/images/buttons/tcmIS.ico\"></LINK>\n");
   preview.document.write("<TITLE>Open Template</TITLE>\n");
   preview.document.write("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
   preview.document.write("<SCRIPT SRC=\"/clientscripts/invoice.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
   preview.document.write("</HEAD>  \n");
   preview.document.write("<BODY onBlur=\"self.focus()\">\n");
   preview.document.write("<TABLE BORDER=\"0\" WIDTH=\"100%\" CLASS=\"moveupmore\"><TR VALIGN=\"TOP\"><TD WIDTH=\"200\">\n");
   preview.document.write("<img src=\"/images/tcmtitlegif.gif\" border=\"0\" align=\"left\"></TD>\n");
   preview.document.write("<TD ALIGN=\"right\"><img src=\"/images/tcmistcmis32.gif\" border=\"0\" align=\"right\"></TD></TR>\n");
   preview.document.write("</Table>\n");
   preview.document.write("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
   preview.document.write("<TR><TD WIDTH=\"70%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">Open Template</TD></TR></TABLE>\n");
   preview.document.write("<FORM method=\"POST\" NAME=\"openTemplateWin\"><TR><TD>&nbsp;</TD></TR>\n");
   preview.document.write("<TABLE ALIGN=\"CENTER\" BORDER=\"0\" CELLPADDING=\"3\" cellspacing=\"3\" BGCOLOR=\"#FFFFFF\">\n");
   preview.document.write("<TR><TD WIDTH=\"40%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
   preview.document.write("<SELECT CLASS=\"HEADER\" NAME=\"tmpName\" size=\"1\">\n");

   var templateList = window.document.getElementById("TemplateList");
   //template name is separated by a semicolon ';'
   var templateListArray = templateList.value.split(";");
   for (var i=0; i < templateListArray.length; i++)
   {
      preview.document.write("<option value=\""+templateListArray[i]+"\">"+templateListArray[i]+"</option>\n");
   }
   preview.document.write("</SELECT></TD></TR>\n");
   preview.document.write("<TR><TD WIDTH=\"40%\" ALIGN=\"CENTER\" VALIGN=\"MIDDLE\">\n");
   preview.document.write("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Open Selected Template\" onClick= \"return openTemplateOk(document.openTemplateWin.tmpName)\" NAME=\"OkB\">\n");
   preview.document.write("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close Window\" onClick= \"closeUserWin()\" NAME=\"closeB\">\n");
   preview.document.write("</TD></TR></TABLE></FORM></BODY></HTML>\n");

   return false;
}

//  this function is for testing of how to display message to user while going to the server to get report data
function openReport()
{
   windowprops = "toolbar=yes,location=yes,directories=yes,menubar=yes,scrollbars=yes,status=yes,resizable=yes";
   preview = window.open("","OpenReport54",windowprops);
   preview.document.write("<HTML><HEAD>\n");
   //preview.document.write("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
   //preview.document.write("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
   preview.document.write("<TITLE>Invoice Summary Report</TITLE>\n");
   preview.document.write("</HEAD>  \n");
   preview.document.write("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
   preview.document.write("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
   preview.document.write("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Report Generator</b></font><P></P><BR>\n");
   preview.document.write("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Report</b></font><P></P><BR>\n");
   preview.document.write("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait ...</b></font><P></P><BR>\n");
   preview.document.write("</CENTER>\n");
   preview.document.write("</BODY></HTML>    \n");

   var companyID = document.getElementById("companyID");
   var loc = "/tcmIS/"+companyID.value+"/costreport?Action=invoiceDetail";
   if (companyID.value == "boeing") {
     loc = "/tcmIS/ula/costreport?Action=invoiceDetail";
   }
   preview.location.replace(loc);

	//display account system info
	var tmp = document.getElementById("actSystem");
	if (tmp.value == "All Accounting Systems") {
		disableAccountSysInfo();
	}else {
		showAccountSysInfo(tmp.value);
	}
   //set report field count
   resetReportFieldCount();
}

//I am not using this method, but I keep it around for references
function openReportOld()
{
	var loc = document.getElementById("reportURL");
   //windowprops = "toolbar=yes,location=yes,directories=yes,menubar=yes,scrollbars=yes,status=yes,top=240,left=280,width=550,height=330,resizable=yes";
   windowprops = "toolbar=yes,location=yes,directories=yes,menubar=yes,scrollbars=yes,status=yes,resizable=yes";
	preview = window.open(loc.value,"OpenReport",windowprops);

	//display account system info
	var tmp = document.getElementById("actSystem");
	if (tmp.value == "All Accounting Systems") {
		disableAccountSysInfo();
	}else {
		showAccountSysInfo(tmp.value);
	}
   //set report field count
   resetReportFieldCount();
}

function directChecked() {
	var direct = document.getElementById("direct");
	if (direct.checked) {
		window.document.invoicereports.inDirect.checked = false;
	}else {
		window.document.invoicereports.inDirect.checked = true;
	}
	//display account system info
	var tmp = document.getElementById("actSystem");
	if (tmp.value == "All Accounting Systems") {
		disableAccountSysInfo();
	}else {
		showAccountSysInfo(tmp.value);
	}
}

function inDirectChecked() {
	var inDirect = document.getElementById("inDirect");
	if (inDirect.checked) {
		window.document.invoicereports.direct.checked = false;
	}else {
		window.document.invoicereports.direct.checked = true;
	}
	//display account system info
	var tmp = document.getElementById("actSystem");
	if (tmp.value == "All Accounting Systems") {
		disableAccountSysInfo();
	}else {
		showAccountSysInfo(tmp.value);
	}
}


function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination,WinName,windowprops);
}
