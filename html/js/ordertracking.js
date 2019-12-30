var submitcount=0;
var selectedrow=null;

function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        try
        {
         var target = document.all.item("TRANSITPAGE");
         target.style["display"] = "";
         var target1 = document.all.item("MAINPAGE");
         target1.style["display"] = "none";
         var target2 = document.all.item("RESULTSPAGE");
         target2.style["display"] = "none";
        }
        catch (ex)
        {
        }

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

function doexcelpopup()
{
 excelfileurl = "/tcmIS/common/viewexcel.jsp";

 openWinGenericViewFile(excelfileurl,"show_excel_report_file","610","600","yes");
}


function showDetailsLineNotes(rowid)
{
  var notesVisible = document.getElementById("notesVisible"+rowid+"");
  if (notesVisible.value == "No")
  {
  var lineNotesDiv = document.getElementById("lineNotes"+rowid+"");
  lineNotesDiv.style.display = "block";
  lineNoteslink

  /*var lineNoteslink = document.getElementById("lineNoteslink"+rowid+"");
  lineNoteslink.style.display = "none";*/

  notesVisible.value = "Yes";
  }
  else
  {
  	var lineNotesDiv = document.getElementById("lineNotes"+rowid+"");
	lineNotesDiv.style.display = "none";
 	lineNoteslink

	var lineNoteslink = document.getElementById("lineNoteslink"+rowid+"");
   lineNoteslink.style.display = "block";

   notesVisible.value = "No";
  }
}


function showLineNotes(rowid)
{
  var notesVisible = document.getElementById("notesVisible"+rowid+"");
  if (notesVisible.value == "No")
  {
  var lineNotesDiv = document.getElementById("lineNotes"+rowid+"");
  lineNotesDiv.style.display = "block";
  lineNoteslink

  var lineNoteslink = document.getElementById("lineNoteslink"+rowid+"");
  lineNoteslink.style.display = "none";

  notesVisible.value = "Yes";
  }
  else
  {
  	var lineNotesDiv = document.getElementById("lineNotes"+rowid+"");
	lineNotesDiv.style.display = "none";
 	lineNoteslink

	var lineNoteslink = document.getElementById("lineNoteslink"+rowid+"");
   lineNoteslink.style.display = "block";

   notesVisible.value = "No";
  }
}

function catchdetailsevent(rowid)
{
    var selectedRow = document.getElementById("rowId"+rowid+"");
	 var selectedRowClass = document.getElementById("colorClass"+rowid+"");
	 selectedRow.className = "selected"+selectedRowClass.value+"";

	 try
	 {
      var grandChildRowsSize = document.getElementById("grandChildRowsSize"+rowid+"0");
      for(k=1;k<grandChildRowsSize.value;k++)
      {
       //alert("grandchildRowId"+rowid+"0"+(k)+"");
	    var grnadChildRowId = document.getElementById("grnadChildRowId"+rowid+"0"+(k)+"");
	    grnadChildRowId.className = "selected"+selectedRowClass.value+"";
       }
    }
    catch (ex)
    {

    }

	 var childRowsSize = document.getElementById("childRowsSize"+rowid+"");
    for(j=1;j<childRowsSize.value;j++)
    {
	   //alert("childRowId"+rowid+""+(j)+"");
	   var selectedchildRow = document.getElementById("childRowId"+rowid+""+(j)+"");
	   selectedchildRow.className = "selected"+selectedRowClass.value+"";

	   var grandChildRowsSize = document.getElementById("grandChildRowsSize"+rowid+""+(j)+"");
      for(k=1;k<grandChildRowsSize.value;k++)
      {
      //alert("grandchildRowId"+rowid+""+(j)+""+(k)+"");
	   var grnadChildRowId = document.getElementById("grnadChildRowId"+rowid+""+(j)+""+(k)+"");
	   grnadChildRowId.className = "selected"+selectedRowClass.value+"";

      }
    }

	 if (selectedrow != null && rowid != selectedrow)
	 {
		var previouslySelectedRow = document.getElementById("rowId"+selectedrow+"");
	   var previouslySelectedRowClass = document.getElementById("colorClass"+selectedrow+"");
	   previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";

      try
	   {
       var previouslygrandChildRowsSize = document.getElementById("grandChildRowsSize"+selectedrow+"0");
       for(k=1;k<previouslygrandChildRowsSize.value;k++)
       {
        //alert("grandchildRowId"+rowid+"0"+(k)+"");
	     var previouslygrnadChildRowId = document.getElementById("grnadChildRowId"+selectedrow+"0"+(k)+"");
	     previouslygrnadChildRowId.className = ""+previouslySelectedRowClass.value+"";
        }
      }
      catch (ex)
      {

      }

		//alert(previouslySelectedRowClass.value);
      var previouslychildRowsSize = document.getElementById("childRowsSize"+selectedrow+"");
      for(j=1;j<previouslychildRowsSize.value;j++)
      {
	    //alert("childRowId"+selectedrow+""+(j)+"");
	    var previouslyselectedchildRow = document.getElementById("childRowId"+selectedrow+""+(j)+"");
	    previouslyselectedchildRow.className = ""+previouslySelectedRowClass.value+"";

	    var previouslygrandChildRowsSize = document.getElementById("grandChildRowsSize"+selectedrow+""+(j)+"");
       for(k=1;k<previouslygrandChildRowsSize.value;k++)
       {
       //alert("grandchildRowId"+selectedrow+""+(j)+""+(k)+"");
	    var previouslygrnadChildRowId = document.getElementById("grnadChildRowId"+selectedrow+""+(j)+""+(k)+"");
	    previouslygrnadChildRowId.className = ""+previouslySelectedRowClass.value+"";
       }
     }
	 }
	 selectedrow =rowid;
}

function catchevent(rowid)
{
    var selectedRow = document.getElementById("rowId"+rowid+"");
	 var selectedRowClass = document.getElementById("colorClass"+rowid+"");
	 selectedRow.className = "selected"+selectedRowClass.value+"";

	 if (selectedrow != null && rowid != selectedrow)
	 {
		var previouslySelectedRow = document.getElementById("rowId"+selectedrow+"");
	   var previouslySelectedRowClass = document.getElementById("colorClass"+selectedrow+"");
	   previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
	 }

	 selectedrow =rowid;
	 showRightClickMenu();
}

//this is for the MSDS viewer on the toolbar
function openwin ()
 {
 MSDS_Help = window.open("/tcmIS/help/new/index.html", "MSDS_Help",
 "toolbar=no,location=no,directories=no,status=yes" +
 ",menubar=no,scrollbars=yes,resizable=yes," +
 "width=730,height=520");
 }

function facilityChanged() {
  var facilityO = document.getElementById("facilityId");
  var applicationIdO = document.getElementById("applicationId");
  var selectedFacility = facilityO.value;

  for (var i = applicationIdO.length; i > 0;i--) {
    applicationIdO[i] = null;
  }

  if (facilityO.value == "All")
  {
   setApplication(0,"All","")
  }
  else if (facilityO.value == "My Facilities")
  {
    setApplication(0,"All","")
    setApplication(1,"My Work Areas","My Work Areas")
  }
  else
  {
  showApplicationLinks(selectedFacility);
  }
  applicationIdO.selectedIndex = 0;
}

function showApplicationLinks(selectedFacility)
{
  var applicationIdArray = new Array();
  applicationIdArray = altApplication[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];

  if(applicationIdArray.length == 0) {
    setApplication(0,"All","")
  }

  for (var i=0; i < applicationIdArray.length; i++) {
    setApplication(i,applicationDescArray[i],applicationIdArray[i])
  }
}

function setApplication(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var applicationIdO = document.getElementById("applicationId");
  applicationIdO[href] = optionName;
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function openWinGenericViewFile(destination,WinName,WinWidth, WinHeight, Resizable,topCoor,leftCoor,scrollbars )
{
  if (topCoor == null || topCoor.trim().length == 0)
  {
    topCoor = "200";
  }

  if (leftCoor == null || leftCoor.trim().length == 0)
  {
    leftCoor = "200";
  }

  if (scrollbars == null || scrollbars.trim().length == 0)
  {
    scrollbars = "yes";
  }

  windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top="+topCoor+",left="+leftCoor+",width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
  preview = window.open(destination, WinName,windowprops);
}

function checkDisabled()
{
var needMyApproval  =  document.getElementById("needMyApproval");
if (needMyApproval.checked)
{
 var facilityId  =  document.getElementById("facilityId");
 facilityId.disabled=true;

 var status  =  document.getElementById("status");
 status.disabled=true;

 var applicationId  =  document.getElementById("applicationId");
 applicationId.disabled=true;

 var searchText  =  document.getElementById("searchText");
 searchText.disabled=true;

 var searchWhat  =  document.getElementById("searchWhat");
 searchWhat.disabled=true;

 var searchType  =  document.getElementById("searchType");
 searchType.disabled=true;

 var critical  =  document.getElementById("critical");
 critical.disabled=true;

 var requestorName  =  document.getElementById("requestorName");
 requestorName.disabled=true;
}
else
{
 var facilityId  =  document.getElementById("facilityId");
 facilityId.disabled=false;

 var status  =  document.getElementById("status");
 status.disabled=false;

 var applicationId  =  document.getElementById("applicationId");
 applicationId.disabled=false;

 var searchText  =  document.getElementById("searchText");
 searchText.disabled=false;

 var searchWhat  =  document.getElementById("searchWhat");
 searchWhat.disabled=false;

 var searchType  =  document.getElementById("searchType");
 searchType.disabled=false;

 var critical  =  document.getElementById("critical");
 critical.disabled=false;

 var requestorName  =  document.getElementById("requestorName");
 requestorName.disabled=false;
}
}

function invalidateRequestor()
{
 var requestorName  =  document.getElementById("requestorName");
 var requestorId  =  document.getElementById("requestorId");

 if (requestorName.value.trim().length == 0 )
 {
  requestorId.value = "";
 }
}

function update(entered)
{
    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
        var target2 = document.all.item("RESULTSPAGE");
        target2.style["display"] = "none";
    }
    catch (ex)
    {
    }
    return true;
}

function search(entered)
{
    try
    {
        var target = document.all.item("TRANSITPAGE");
        target.style["display"] = "";
        var target1 = document.all.item("MAINPAGE");
        target1.style["display"] = "none";
        var target2 = document.all.item("RESULTSPAGE");
        target2.style["display"] = "none";
    }
    catch (ex)
    {
    }

    return true;
}

function getPersonnel()
{
   loc = "searchpersonnel.do?displayElementId=requestorName&valueElementId=requestorId";

   openWinGeneric(loc,"searchpersonnel","600","450","yes","50","50")
}

function showMrLineAllocationReport()
{
  if (selectedrow != null)
  {
  var mrNumber  =  document.getElementById("prNumber"+selectedrow+"");
  var lineItem  =  document.getElementById("lineItem"+selectedrow+"");

  if ( (mrNumber.value != null && lineItem.value != null && mrNumber.value != 0) && (mrNumber.value.length > 0 && lineItem.value.length > 0) )
  {
  var loc = "mrallocationreport.do?mrNumber="+mrNumber.value+"&lineItem="+lineItem.value+"";
  openWinGeneric(loc,"showMrAllocationReport22","800","500","yes","80","80")
  }
  else
  {
	alert("There is no valid MR-Line selected.");
  }
  }
}

function showMrAllocationReport()
{
  if (selectedrow != null)
  {
   var mrNumber  =  document.getElementById("prNumber"+selectedrow+"");
   var lineItem  =  document.getElementById("lineItem"+selectedrow+"");

   if ( mrNumber.value != null && mrNumber.value.length > 0 && mrNumber.value != 0)
   {
  	 var loc = "mrallocationreport.do?mrNumber="+mrNumber.value+"&lineItem=";
  	 openWinGeneric(loc,"showMrAllocationReport22","800","500","yes","80","80")
   }
	else
   {
   	alert("There is no valid MR selected.");
   }
  }
}

function showDeliverySchedule()
{
  if (selectedrow != null)
  {
   var orderType  =  document.getElementById("orderType"+selectedrow+"");
	if (orderType.value == "SCH")
	{
	   var mrNumber  =  document.getElementById("prNumber"+selectedrow+"");
	   var lineItem  =  document.getElementById("lineItem"+selectedrow+"");

   	var loc = "deliveryschedule.do?mrNumber="+mrNumber.value+"&lineItem="+lineItem.value+"";
	   openWinGeneric(loc,"showDeliverySchedule22","800","500","yes","100","100")
   }
   else
   {
		alert("There is no Schedule for this MR-Line");
   }
  }
}

function cancel()
{
    window.close();
}