var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce()
{
alert("SubmitOnlyOnce");
    if (submitcount == 0)
    {
        submitcount++;
        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

String.prototype.replaceBr = function()
{
  // replace <BR> with \n
  return this.replace(/&lt;BR&gt;/g, "\n");
}

function doNothing(entered)
{
    return false;
}

function doexcelpopup()
{
 excelfileurl = "/tcmIS/common/viewexcel.jsp";
 openWinGenericViewFile(excelfileurl,"show_excel_report_file","800","600","yes");
}


function init() {
this.cfg = new YAHOO.util.Config(this);
if (this.isSecure)
{
 this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
}

legendWin = new YAHOO.widget.Panel("win", { width:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false, draggable:true, modal:false } );
legendWin.render();

showUseApproversWin = new YAHOO.widget.Panel("showApprovers", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:false } );
showUseApproversWin.render();
}

function showLegend()
{
var win = document.getElementById("win");
win.style.display="";

legendWin.show();
}


function resizeGrid()
{
 showWaitDialog();
 if (!_isIE)
 {
 try
 {
  bodyOffsetHeight = window.document.body.offsetHeight;
  mygrid.enableAutoHeigth(true,(bodyOffsetHeight-382));
 }
 catch(ex)
 {

 }
 }
 setTimeout('waitResizeGrid()',100);
}

function waitResizeGrid()
{
 if (_isIE)
 {
 try
 {
  bodyOffsetHeight = window.document.body.offsetHeight;
  mygrid.enableAutoHeigth(true,(bodyOffsetHeight-382));
 }
 catch(ex)
 {

 }
 }
 stopWaitDialog();
}


function checkInput(rownum)
{
var errorMessage = "Please enter valid values for: \n\n";
var warningMessage = "Alert: \n\n";
var errorCount = 0;
var warnCount = 0;
var ok = document.getElementById("ok"+rownum+"");
var useApprovalChanged  =  document.getElementById("useApprovalChanged"+rownum+"");
inputChangedCount++;

if (ok.checked)
{
 if (workAreaManagementData[rownum].mwApprovalId.length == 0 && useApprovalChanged.value != "Y")
 {
   var mwLimitQuantityPeriod1 = document.getElementById("mwLimitQuantityPeriod1"+rownum+"");
   mwLimitQuantityPeriod1.value= workAreaManagementData[rownum].limitQuantityPeriod1;
   var mwDaysPeriod1 = document.getElementById("mwDaysPeriod1"+rownum+"");
   mwDaysPeriod1.value= workAreaManagementData[rownum].daysPeriod1;

   var mwLimitQuantityPeriod2 = document.getElementById("mwLimitQuantityPeriod2"+rownum+"");
   mwLimitQuantityPeriod2.value= workAreaManagementData[rownum].limitQuantityPeriod2;
   var mwDaysPeriod2 = document.getElementById("mwDaysPeriod2"+rownum+"");
   mwDaysPeriod2.value= workAreaManagementData[rownum].daysPeriod2;

   var mwProcessDesc = document.getElementById("mwProcessDesc"+rownum+"");
   mwProcessDesc.value= workAreaManagementData[rownum].processDesc;
 }

var mwLimitQuantityPeriod1 = document.getElementById("mwLimitQuantityPeriod1"+rownum+"");
var mwDaysPeriod1 = document.getElementById("mwDaysPeriod1"+rownum+"");
if ( mwLimitQuantityPeriod1.value.trim().length > 0 && !(isInteger(mwLimitQuantityPeriod1.value)))
{
  errorMessage = errorMessage + " Limit 1 Quantity. \n";
  errorCount = 1;
  mwLimitQuantityPeriod1.value = "";
}
else if ((mwLimitQuantityPeriod1.value.trim().length > 0) && ( mwDaysPeriod1.value.trim().length == 0 || !(isInteger(mwDaysPeriod1.value)) || mwDaysPeriod1.value*1 == 0))
{
  errorMessage = errorMessage + " Limit 1 Days. \n";
  errorCount = 1;
  mwDaysPeriod1.value = "";
}
else if (workAreaManagementData[rownum].limitQuantityPeriod1.length > 0 && workAreaManagementData[rownum].daysPeriod1.length > 0)
{
 if ((mwLimitQuantityPeriod1.value*1/mwDaysPeriod1.value*1) > (workAreaManagementData[rownum].limitQuantityPeriod1*1/workAreaManagementData[rownum].daysPeriod1*1)*1)
 {
  errorMessage = errorMessage + " Limit 1 cannot be grerater than the Use Approval limits. \n";
  mwLimitQuantityPeriod1.value = "";
  mwDaysPeriod1.value = "";
  errorCount = 1;
 }
}

var mwLimitQuantityPeriod2 = document.getElementById("mwLimitQuantityPeriod2"+rownum+"");
var mwDaysPeriod2 = document.getElementById("mwDaysPeriod2"+rownum+"");
if ( mwLimitQuantityPeriod2.value.trim().length > 0 && !(isInteger(mwLimitQuantityPeriod2.value)))
{
  errorMessage = errorMessage + " Limit 2 Quantity. \n";
  errorCount = 1;
  mwLimitQuantityPeriod2.value = "";
}
else if ( (mwLimitQuantityPeriod2.value.trim().length > 0) && ( mwDaysPeriod2.value.trim().length == 0 || !(isInteger(mwDaysPeriod2.value)) || mwDaysPeriod2.value*1 == 0) )
{
  errorMessage = errorMessage + " Limit 2 Days. \n";
  errorCount = 1;
  mwDaysPeriod2.value = "";
}
else if (workAreaManagementData[rownum].limitQuantityPeriod2.length > 0 && workAreaManagementData[rownum].daysPeriod2.length > 0)
{
 if ((mwLimitQuantityPeriod2.value*1/mwDaysPeriod2.value*1) > (workAreaManagementData[rownum].limitQuantityPeriod2*1/workAreaManagementData[rownum].daysPeriod2*1))
 {
  errorMessage = errorMessage + " Limit 2 cannot be grerater than the Use Approval limits. \n";
  mwLimitQuantityPeriod2.value = "";
  mwDaysPeriod2.value = "";
  errorCount = 1;
 }
}

var mwOrderQuantity = document.getElementById("mwOrderQuantity"+rownum+"");
if (mwOrderQuantity.value.trim().length > 0 && !(isInteger(mwOrderQuantity.value)))
{
  errorMessage = errorMessage + " Order Quantity. \n";
  errorCount = 1;
  mwOrderQuantity.value = "";
}
else if (mwOrderQuantity.value.trim().length > 0)
{
 var mwOrderQuantityRule = document.getElementById("mwOrderQuantityRule"+rownum+"");
 if (mwOrderQuantityRule.value.trim().length == 0 )
 {
  errorMessage = errorMessage + " Order Qty Type. \n";
  errorCount = 1;
  mwOrderQuantityRule.value = "";
 }
}

var mwEstimateOrderQuantityPeriod = document.getElementById("mwEstimateOrderQuantityPrd"+rownum+"");
if (mwEstimateOrderQuantityPeriod.value.trim().length > 0 && (!(isInteger(mwEstimateOrderQuantityPeriod.value)) || mwEstimateOrderQuantityPeriod.value*1 == 0))
{
  errorMessage = errorMessage + " Estimated Coverage Period. \n";
  errorCount = 1;
  mwEstimateOrderQuantityPeriod.value = "";
}

if (errorCount >0)
   {
    alert(errorMessage);
   	ok.checked = false;
		return false;
   }
}

 useApprovalChanged.value = "Y";
 return true;
}

function showUseApprovers(facilityId,application)
{
   var facilityId  =  document.getElementById("facilityId");
   var application  =  document.getElementById("application");

	 if (facilityId.value == "All" || facilityId.value.trim() == 0 )
	 {
	  alert("Facility Id must be selected.");
	  //return false;
	 }
    else if (application.value == "All" || application.value.trim() == 0 )
    {
     alert("Work Area must be selected.");
     //return false;
    }
    else
    {
     // var selectedFacilityId = document.getElementById("facilityId");
     // var selectedApplication = document.getElementById("application");
     loc = "useapprovalstatus.do?submitShowUseApprovers=Y&facilityId="+facilityId.value+"&application="+application.value+"";
     showWaitDialog();
     callToServer(loc);
//     setTimeout('showUseApproversWindow()',5000);
    }
}

function showUseApproversWindow()
{
 //alert("Here showUseApproversWindow");
// showUseApproversWin = new YAHOO.widget.Panel("showApprovers", { width:"20em", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:false, modal:false } );
// showUseApproversWin.render();

 var showApprovers = document.getElementById("showApprovers");
 showApprovers.style.display="";

 showUseApproversWin.show();
 stopWaitDialog();
 fixProgressBar();
}

// This function replaces the src of the hidden Iframe to stop the progress bar in IE
function fixProgressBar()
{
  try
  {
   if(_isIE)
  {
   var RSIFrame = document.getElementById("RSIFrame");
   RSIFrame.src="/blank.html";
   RSIFrame.blur();
  }
  }
  catch (ex)
  {
  }
}

var buildFirstSetRowsAfterUpdateCout = 0;
function waitbuildFirstSetRowsAfterUpdate()
{
 buildFirstSetRowsAfterUpdateCout++;
 if (buildFirstSetRowsAfterUpdateCout ==1)
 {
  setTimeout('buildFirstSetRows()',10);
 }
}

function buildFirstSetRowsAfterUpdate()
{
  showWaitDialog();
  buildFirstSetRowsAfterUpdateCout = 0;
  waitbuildFirstSetRowsAfterUpdate();
}


var updateManagedWorkAreaCout = 0;
/*function waitUpdateManagedWorkAreaSomeTime()
{
 updateManagedWorkAreaCout++;
 if (updateManagedWorkAreaCout ==1)
 {
  setTimeout('callUpdateManagedWorkArea()',10);
 }
}*/
function updateManagedWorkArea()
{
var managedWorkArea  =  document.getElementById("managedWorkArea");
var managedWorkAreaAlertMsg = "";

if (managedWorkArea.value == "Y")
{
 managedWorkAreaAlertMsg ="This will make the selected work area not be a managed work area.\n\nDo you want to continue?";
}
else
{
 managedWorkAreaAlertMsg ="This will make the selected work area be a managed work area.\n\nDo you want to continue?";
}

 if (confirm(managedWorkAreaAlertMsg))
 {
  showWaitDialog();
  updateManagedWorkAreaCout = 0;
  //waitUpdateManagedWorkAreaSomeTime();
  setTimeout('callUpdateManagedWorkArea()',10);
 }
 else
 {

 }
}

function callUpdateManagedWorkArea()
{
 var facilityId = document.getElementById("facilityId");
 var application = document.getElementById("application");
 var managedWorkArea = document.getElementById("managedWorkArea");
 var managedWorkAreaLink = document.getElementById("managedWorkAreaLink");
 var managedUseApproval = "NO";
 if (managedWorkArea.value == "Y")
 {
  isItmanagedWorkArea = "NO";
  managedWorkArea.value = "N";
  managedWorkAreaLink.innerHTML="Turn Override On";
 }
 else
 {
  isItmanagedWorkArea = "YES";
  managedUseApproval = "YES"
  managedWorkArea.value = "Y";
  managedWorkAreaLink.innerHTML="Turn Override Off";
 }

 loc = "useapprovalstatus.do?updateManagedUseApproval=Y&facilityId="+facilityId.value+"&application="+application.value+"&managedUseApproval="+managedUseApproval+"&useApprovalLimitsOption=Y";
 callToServer(loc);

 setTimeout('stopUpdateManagedWorkArea()',1000);
}

function stopUpdateManagedWorkArea()
{
  for (var i = 0; i < endingRowNumber+1; i++)
  {
   if (isItmanagedWorkArea == "YES")
   {
    mygrid.setColumnColor(",,,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,,,,,,,,,,,,,");
    var c  =  document.getElementById("c_"+i+"_3");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_4");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_5");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_6");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_7");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_8");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_9");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_10");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_11");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_13");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_12");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_14");
    c.bgColor = "";
   }
   else
   {
    mygrid.setColumnColor(",,,,,,,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,,,,,");
    var c  =  document.getElementById("c_"+i+"_3");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_4");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_5");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_6");
    c.bgColor = "";

    var c  =  document.getElementById("c_"+i+"_7");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_8");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_9");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_10");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_11");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_13");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_12");
    c.bgColor = "#dfdfdf";

    var c  =  document.getElementById("c_"+i+"_14");
    c.bgColor = "#dfdfdf";
   }
  }
  stopWaitDialog();
  fixProgressBar();
}

function getpersonnelID(rownum)
{
 loc = "searchpersonnel.do?displayElementId=barcodeRequesterName"+rownum+"&valueElementId=barcodeRequester"+rownum+"";
 openWinGeneric(loc,"searchpersonnelforrequestro","650","455","yes","50","50")
}

function disableGridData()
{
 if (inputChangedCount > 0)
 {
  if (confirm("You changed some values in the results and have not been saved.\n\nYour Changes will be lost. Do you want to continue?\n\nClick Cancel to update your changes."))
  {
   /*var gridtopLinks  =  document.getElementById("gridtopLinks");
   gridtopLinks.style["display"] = "none";*/

   var resultsMaskTable  =  document.getElementById("resultsMaskTable");
   resultsMaskTable.style["display"] = "none";

   var footer  =  document.getElementById("footer");
   footer.innerHTML= "";

   inputChangedCount=0;
  }
  else
  {

  }
 }
 else
 {
   /*var gridtopLinks  =  document.getElementById("gridtopLinks");
   gridtopLinks.style["display"] = "none";*/

   var resultsMaskTable  =  document.getElementById("resultsMaskTable");
   resultsMaskTable.style["display"] = "none";

   var footer  =  document.getElementById("footer");
   footer.innerHTML= "";
 }
}

function dockLocationIdChanged(rownum)
{
  var dockLocationId = document.getElementById("dockLocationId"+rownum+"");
  var seldockLocationId = dockLocationId.value;
  var dockDeliveryPoint = document.getElementById("dockDeliveryPoint"+rownum+"");

  for (var i = dockDeliveryPoint.length; i >= 0;i--)
  {
     dockDeliveryPoint[i] = null;
  }
  showDeliveryPoints(seldockLocationId,rownum);
  dockDeliveryPoint.selectedIndex = 0;
  automatedFeedChanged(rownum);
}

function showDeliveryPoints(seldockLocationId,rownum)
{
  var deliveryPoint = new Array();
  deliveryPoint = altdeliveryPoint[seldockLocationId];

  var deliveryPointDesc = new Array();
  deliveryPointDesc = altdeliveryPointDesc[seldockLocationId];
  //alert("Here   seldockLocationId:"+seldockLocationId.trim().length+"");
  try
  {
    if(deliveryPoint.length == 0)
    {
      setDeliveryPoints(0,"Please Select Valid Dock","",rownum);
    }
    else
    {
      setDeliveryPoints(0,"Please Select","",rownum);
    }

    for (var i=0; i < deliveryPoint.length; i++)
    {
       setDeliveryPoints(i+1,deliveryPointDesc[i],deliveryPoint[i],rownum)
    }
  }
  catch (ex)
  {
   setDeliveryPoints(0,"Please Select","",rownum)
  }
}

function setDeliveryPoints(href,text,id,rownum)
{
    text = htmlDencode(text);
    var optionName = new Option(text, id, false, false)
    var dockDeliveryPoint = document.getElementById("dockDeliveryPoint"+rownum+"");
    dockDeliveryPoint[href] = optionName;
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

   //var submitUpdate = document.getElementById("submitUpdate");
   //submitUpdate.style["display"] = "none";

   //var cataddrule = document.getElementById("cataddrule");
   //cataddrule.innerHTML="";
  //var managedWorkAreaFlag  =  document.getElementById("managedWorkAreaFlag");
  //managedWorkAreaFlag.style["display"] = "none";
  disableGridData();
  showUseApproversWin.hide();
}

function applicationChanged()
{
 //var submitUpdate = document.getElementById("submitUpdate");
 //submitUpdate.style["display"] = "none";

 //var cataddrule = document.getElementById("cataddrule");
 //cataddrule.innerHTML="";
  //var managedWorkAreaFlag  =  document.getElementById("managedWorkAreaFlag");
  //managedWorkAreaFlag.style["display"] = "none";
 showUseApproversWin.hide();
 disableGridData();
}

/*function userGroupChanged()
{
 disableGridData();
}*/

function showWorkareas(selfacilityId)
{
    var application = new Array();
    application = altapplication[selfacilityId];

	 var applicationdesc = new Array();
    applicationdesc = altapplicationdesc[selfacilityId];

	 try
	 {
     setApplication(0,"Please Select","All")

     for (var i=0; i < application.length; i++)
     {
        setApplication(i+1,applicationdesc[i],application[i])
     }
    }
    catch (ex)
    {
     setApplication(0,"Please Select","All")
    }
}

function setApplication(href,text,id)
{
    text = htmlDencode(text);
    var optionName = new Option(text, id, false, false)
    var application = document.getElementById("application");
	 application[href] = optionName;
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

function setUserGroup(href,text,id)
{
    text = htmlDencode(text);
    var optionName = new Option(text, id, false, false)
    var userGroupId = document.getElementById("userGroupId");
	 userGroupId[href] = optionName;
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

function setLastSearchOptions(action)
{
 var searchText  =  document.getElementById("searchText");
 searchText.value = lastSearchText;

 var showActiveOnly  =  document.getElementById("showActiveOnly");
 if (lastShowActiveOnly == "Y")
 {
   showActiveOnly.checked = true;
 }
 else
 {
   showActiveOnly.checked = false;
 }

 if (totalRows > 0)
 {
 var application  =  document.getElementById("application");
 application.value = workAreaManagementData[0].application;
 var userGroupId  =  document.getElementById("userGroupId");
 userGroupId.value = workAreaManagementData[0].userGroupId;
 }

 if (action == "search")
 {
  search();
 }
 else
 {
  submitForm();
 }
}

function refresh()
{
 if (totalRows > 0)
 {
 var facilityId  =  document.getElementById("facilityId");
 facilityId.value = workAreaManagementData[0].facilityId;
 facilityIdChanged();
 }

 setTimeout('setLastSearchOptions(\"search\")',100);
}

function search()
{
    var facilityId  =  document.getElementById("facilityId");
    var application  =  document.getElementById("application");
    var userGroupId  =  document.getElementById("userGroupId");

	 if (facilityId.value == "All" || facilityId.value.trim() == 0 )
	 {
	  alert("Facility Id must be selected.");
	  return false
	 }
    else if (application.value == "All" || application.value.trim() == 0 )
    {
     alert("Work Area must be selected.");
     return false
    }
    else if (userGroupId.value.trim() == 0 )
    {
     alert("User Group must be selected.");
     return false
    }
	  else
    {
    try
    {
       showWaitDialog();
       disableGridData();

       /*var resultsMaskTable  =  document.getElementById("resultsMaskTable");
       resultsMaskTable.style["display"] = "";*/
       if (grilLoadedCount > 0)
       {
         //mygrid.clearAll();
       }

var searchText  =  document.getElementById("searchText");
    //var submitSearch  =  document.getElementById("submitSearch");
    var sortBy  =  document.getElementById("sortBy");
    var showApprovedOnly  =  document.getElementById("showApprovedOnly");

    var showActiveOnly  =  document.getElementById("showActiveOnly");
    var showActiveOnlyValue = "N";
    if (showActiveOnly.checked)
    {
	   showActiveOnlyValue = "Y";
    }

    var showOnlyWithLimits  =  document.getElementById("showOnlyWithLimits");
    var showOnlyWithLimitsValue = "N";
    if (showOnlyWithLimits.checked)
    {
	   showOnlyWithLimitsValue = "Y";
    }

       var footer  =  document.getElementById("footer");
       footer.innerHTML="&nbsp;";

       callToServer("useapprovalstatus.do?showApprovedOnly="+showApprovedOnly.value+"&showActiveOnly="+showActiveOnlyValue+"&showOnlyWithLimits="+showOnlyWithLimitsValue+"&userGroupId="+userGroupId.value+"&submitSearch=Y&searchText="+searchText.value+"&facilityId="+facilityId.value+"&application="+application.value+"&sortBy="+sortBy.value+"");

        /*var target  =  document.getElementById("TRANSITPAGE");
        target.style["display"] = "";
        var target1  =  document.getElementById("MAINPAGE");
        target1.style["display"] = "none";*/
    }
    catch (ex)
    {
      alert("here search exception 761");
    }
    return true;
    }
}

function submitForm()
{
 showWaitDialog();
 window.document.genericForm.submit();
 //window.document.forms["genericForm"].submit()
}

function update()
{
    try
    {
        inputChangedCount=0;
        var updateAllRows  =  document.getElementById("updateAllRows");
        updateAllRows.value = "";

        var submitUpdate  =  document.getElementById("submitUpdate");
        submitUpdate.value = "submitUpdate";

        if (totalRows > 0)
        {
         var facilityId  =  document.getElementById("facilityId");
         facilityId.value = workAreaManagementData[0].facilityId;
         facilityIdChanged();
        }

        setTimeout('setLastSearchOptions(\"update\")',100);
    }
    catch (ex)
    {
    }
}

function updateActivateAll()
{
    try
    {
     if (confirm("This will activate all the parts approved in Use Approval for the selected work area.\n\nDo you want to continue?"))
     {
        inputChangedCount=0;
        var updateAllRows  =  document.getElementById("updateAllRows");
        updateAllRows.value = "AddAll";
        var submitUpdate  =  document.getElementById("submitUpdate");
        submitUpdate.value = "submitUpdate";

        if (totalRows > 0)
        {
         var facilityId  =  document.getElementById("facilityId");
         facilityId.value = workAreaManagementData[0].facilityId;
         facilityIdChanged();
        }

        setTimeout('setLastSearchOptions(\"update\")',100);
     }
     else
     {

     }
    }
    catch (ex)
    {
    }
}

function updateDeleteAll()
{
    try
    {
     if (confirm("This will make all parts active for this work area to be not managed by limits.\n\nYou will lose the limits data.\n\nDo you want to continue?"))
     {
        inputChangedCount=0;
        var updateAllRows  =  document.getElementById("updateAllRows");
        updateAllRows.value = "deleteAll";
        var submitUpdate  =  document.getElementById("submitUpdate");
        submitUpdate.value = "submitUpdate";

        if (totalRows > 0)
        {
         var facilityId  =  document.getElementById("facilityId");
         facilityId.value = workAreaManagementData[0].facilityId;
         facilityIdChanged();
        }

        setTimeout('setLastSearchOptions(\"update\")',100);
}
     else
     {

     }
    }
    catch (ex)
    {
    }
}


function creatExcelReport()
{
    try
    {
     if (totalRows > 0)
     {
       var facilityId = workAreaManagementData[0].facilityId;
       var application = workAreaManagementData[0].application;
       var userGroupId = workAreaManagementData[0].userGroupId;
       var showApprovedOnly  =  document.getElementById("showApprovedOnly");
       var sortBy  =  document.getElementById("sortBy");
       var location = "useapprovalstatus.do?showApprovedOnly="+showApprovedOnly.value+"&showActiveOnly="+lastShowActiveOnly+"&showOnlyWithLimits="+lastShowOnlyWithLimits+"&userGroupId="+userGroupId+"&buttonCreateExcel=Y&searchText="+lastSearchText+"&facilityId="+facilityId+"&application="+application+"&sortBy="+sortBy.value+"";
       openWinGeneric(location,"show_excel_report_file","610","400","yes");
    }
    else
    {
     alert("No Records to create Excel file.");
    }
    }
    catch (ex)
    {
    }
}

function cancel()
{
window.close();
}