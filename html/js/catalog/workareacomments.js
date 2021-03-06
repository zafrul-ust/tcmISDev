var submitcount=0;
var selectedrow=null;

var selectedCommentRow=null;

function catchcommentevent(rowid)
{
    var selectedRow = document.getElementById("commentrowId"+rowid+"");
	 var selectedRowClass = document.getElementById("colorClass"+rowid+"");
	 selectedRow.className = "selected"+selectedRowClass.value+"";

	 if (selectedCommentRow != null && rowid != selectedCommentRow)
	 {
		var previouslySelectedRow = document.getElementById("commentrowId"+selectedCommentRow+"");
	   var previouslySelectedRowClass = document.getElementById("colorClass"+selectedCommentRow+"");
	   previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";
	 }
	 selectedCommentRow =rowid;
}


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

 openWinGeneric(excelfileurl,"show_excel_report_file","610","600","yes");
}

function workAreaApprovedChanged()
{
 var workAreaApprovedOnly  =  document.getElementById("workAreaApprovedOnly");
 var applicationId  =  document.getElementById("applicationId");

 if (applicationId.value == 'All')
 {
  workAreaApprovedOnly.checked=false;
 }
}

function workAreaChanged()
{
 var workAreaApprovedOnly  =  document.getElementById("workAreaApprovedOnly");
 var applicationId  =  document.getElementById("applicationId");

 if (applicationId.value == 'All')
 {
  workAreaApprovedOnly.checked=false;
 }
 else
 {
  workAreaApprovedOnly.checked=true;
 }
}

function facilityOrAllCatalogChanged()
{
 var facilityOrAllCatalog  =  document.getElementById("facilityOrAllCatalog");

 /*if (facilityOrAllCatalog.value == 'All Catalog')
 {
  var workAreaApprovedOnly  =  document.getElementById("workAreaApprovedOnly");
  workAreaApprovedOnly.disabled=true

   var applicationId  =  document.getElementById("applicationId");
   applicationId.disabled=true

   var facilityId  =  document.getElementById("facilityId");
   facilityId.disabled=true
   var activeOnly  =  document.getElementById("activeOnly");
   activeOnly.disabeled=false
 }
 else
 {
  var workAreaApprovedOnly  =  document.getElementById("workAreaApprovedOnly");
  workAreaApprovedOnly.disabled=false

   var applicationId  =  document.getElementById("applicationId");
   applicationId.disabled=false

   var facilityId  =  document.getElementById("facilityId");
   facilityId.disabled=false
   var activeOnly  =  document.getElementById("activeOnly");
   activeOnly.disabled=true
 }*/

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

  showApplicationLinks(selectedFacility);

  applicationIdO.selectedIndex = 0;
}

function showApplicationLinks(selectedFacility)
{
  var applicationIdArray = new Array();
  applicationIdArray = altApplication[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];

  setApplication(0,"Please Select","All")
  var j = 0;
  for (var i=0; i < applicationIdArray.length; i++) {
   j++;
    setApplication(j,applicationDescArray[i],applicationIdArray[i])
  }
}

function setApplication(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var applicationIdO = document.getElementById("applicationId");
  applicationIdO[href] = optionName;
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


function cancel()
{
    parent.window.close();
}


function showApprovedWorkAreas()
{
  if (selectedrow != null)
  {
  var catPartNo  =  document.getElementById("printCheckBox"+selectedrow+"");
  var partGroupNo  =  document.getElementById("partGroupNo"+selectedrow+"");
  var catalogId  =  document.getElementById("catalogId"+selectedrow+"");
  var selectedFacilityId  =  document.getElementById("selectedFacilityId");

  if ( (catPartNo.value != null && partGroupNo.value != null && catalogId.value != null && catPartNo.value != 0) && (catPartNo.value.length > 0 && partGroupNo.value.length > 0) )
  {
  var loc = "approvedworkareas.do?facPartNo="+catPartNo.value+"&partGroupNo="+partGroupNo.value+"&catalogId="+catalogId.value+"&facilityId="+selectedFacilityId.value+"";
  openWinGeneric(loc,"showApprovedWorkAreas","800","500","yes","80","80")
  }
  else
  {
	alert("There is no valid Part Number selected.");
  }
  }
}

function showWorkAreaComments()
{
  if (selectedrow != null)
  {
  var catPartNo  =  document.getElementById("printCheckBox"+selectedrow+"");
  var partGroupNo  =  document.getElementById("partGroupNo"+selectedrow+"");
  var catalogId  =  document.getElementById("catalogId"+selectedrow+"");
  var selectedFacilityId  =  document.getElementById("selectedFacilityId");
  var selectedWorkArea  =  document.getElementById("applicationId");

  if ( (catPartNo.value != null && partGroupNo.value != null && catalogId.value != null && catPartNo.value != 0) && (catPartNo.value.length > 0 && partGroupNo.value.length > 0) )
  {
  var loc = "workareacomments.do?catPartNo="+catPartNo.value+"&partGroupNo="+partGroupNo.value+"&catalogId="+catalogId.value+"&facilityId="+selectedFacilityId.value+"&applicationId="+selectedWorkArea.value+"";
  openWinGeneric(loc,"showWorkAreaComments","800","500","yes","80","80")
  }
  else
  {
	alert("There is no valid Part Number selected.");
  }
  }
}

function showPartNumberComments()
{
  if (selectedrow != null)
  {
  var catPartNo  =  document.getElementById("printCheckBox"+selectedrow+"");
  var partGroupNo  =  document.getElementById("partGroupNo"+selectedrow+"");
  var catalogId  =  document.getElementById("catalogId"+selectedrow+"");
  var selectedFacilityId  =  document.getElementById("selectedFacilityId");

  if ( (catPartNo.value != null && partGroupNo.value != null && catalogId.value != null && catPartNo.value != 0) && (catPartNo.value.length > 0 && partGroupNo.value.length > 0) )
  {
  var loc = "partnumbercomments.do?catPartNo="+catPartNo.value+"&partGroupNo="+partGroupNo.value+"&catalogId="+catalogId.value+"&facilityId="+selectedFacilityId.value+"";
  openWinGeneric(loc,"showPartNumberComments","800","500","yes","80","80")
  }
  else
  {
	alert("There is no valid Part Number selected.");
  }
  }
}

/*function checkDisabled()
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

function showDetailsLineNotes(rowid)
{
alert("Here showLineNotes");
  var notesVisible = document.getElementById("notesVisible"+rowid+"");
  if (notesVisible.value == "No")
  {
  var lineNotesDiv = document.getElementById("lineNotes"+rowid+"");
  lineNotesDiv.style.display = "block";
  lineNoteslink

  /*var lineNoteslink = document.getElementById("lineNoteslink"+rowid+"");
  lineNoteslink.style.display = "none";*/

  /*notesVisible.value = "Yes";
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
}*/