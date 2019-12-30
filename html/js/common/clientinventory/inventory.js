var submitcount=0;
var selectedrow=null;

function init() { /*This is to initialize the YUI*/
 this.cfg = new YAHOO.util.Config(this);
 if (this.isSecure)
 {
  this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
 }
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
}

function mySearchOnload()
{
	setSearchFrameSize();
	showFacility();
}

function myOnload()
{
	//it's import to put this before setResultFrameSize(), otherwise it will not resize correctly
	displaySearchDuration();
	setResultFrameSize();
}

function submitSearchForm()
{
 document.genericForm.target='resultFrame';
 var action = document.getElementById("action");
 action.value = 'search';
 //set start search time
 var now = new Date();
 var startSearchTime = parent.window.document.getElementById("startSearchTime");
 startSearchTime.value = now.getTime();
 //var companyId = document.getElementById("companyId");
 //var companyName = document.getElementById("companyName");
 //companyName.value = companyId[companyId.selectedIndex].text;
 //var facilityId = document.getElementById("facilityId");
 //var facilityName = document.getElementById("facilityName");
 //facilityName.value = facilityId[facilityId.selectedIndex].text;
 var flag = validateForm();
  if(flag) {
    parent.showPleaseWait();
    return true;
  }
  else
  {
    return false;
  }
}

function validateForm() {
   var result = true;
   if (checkexpiresWithin()) {
    result = false;
   }
   if (checkexpiresAfter()) {
    result = false;
   }
   if (checkarrivesWithin()) {
    result = false;
   }
   if($v('inventory') == '' && $v('facilityId') == 'ALL' && $v('searchText') == '')
	   {
	   		alert(messagesData.searchMessage);
	   		result = false;
	   }
   
  return result;
} //end of validateForm

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

function generateExcel() {
  var flag = validateForm();
  if(flag) {
    var action = document.getElementById("action");
    action.value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_InventoryGenerateExcel','650','600','yes');
    document.genericForm.target='_InventoryGenerateExcel';
    var a = window.setTimeout("document.genericForm.submit();",500);
  }
}

function checkexpiresWithin()
{
 var expiresWithin = document.getElementById("expiresWithin");

 if ( expiresWithin.value.trim().length > 0 && !(isSignedInteger(expiresWithin.value)) )
 {
    alert(messagesData.entervalidintegerforexpireswithin);
    expiresWithin.value = "";
    return true;
 }
 else
 {
	return false;
 }
}

function checkexpiresAfter()
{
 var expiresAfter = document.getElementById("expiresAfter");

 if ( expiresAfter.value.trim().length > 0 && !(isSignedInteger(expiresAfter.value)) )
 {
    alert(messagesData.entervalidintegerforexpiresafter);
    expiresAfter.value = "";
    return true;
 }
 else
 {
	return false;
 }
}

function checkarrivesWithin()
{
 var arrivesWithin = document.getElementById("arrivesWithin");

 if ( arrivesWithin.value.trim().length > 0 && !(isSignedInteger(arrivesWithin.value)) )
 {
    alert(messagesData.entervalidintegerforarriveswithin);
    arrivesWithin.value = "";
    return true;
 }
 else
 {
	return false;
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

function showFacility() {
    var facilityIdArray = altFacilityId;
    var facilityNameArray = altFacilityName;
    //if user does not have default facility then default it to My Facilities
    var defaultFacilityId = $v("myDefaultFacilityId");
    if (defaultFacilityId == null || defaultFacilityId.length == 0)
        defaultFacilityId = "My Facilities";

    var selectedIndex = 0;
    for (var i=0; i < facilityIdArray.length; i++) {
        setOption(i,facilityNameArray[i],facilityIdArray[i], "facilityId")
        if (facilityIdArray[i] == defaultFacilityId)
            selectedIndex = i;
    }
    $("facilityId").selectedIndex = selectedIndex;
    facilityChanged();
}

function facilityChanged() {
    var inventoryO = document.getElementById("inventory");

    for (var i = inventoryO.length; i > 0;i--) {
        inventoryO[i] = null;
    }
    showInventoryLinks($v("facilityId"));
    inventoryO.selectedIndex = 0;
}

function showInventoryLinks(selectedFacility) {
    var inventoryGroup = new Array();
    inventoryGroup = altInventoryGroup[selectedFacility];

    var inventoryGroupName = new Array();
    inventoryGroupName = altInventoryGroupName[selectedFacility];

    if(inventoryGroup == null || inventoryGroup.length == 0)
    {
      var inventoryO = document.getElementById("inventory");
      for (var i = inventoryO.length; i > 0;i--) {
    		    inventoryO[i] = null;
      }
      setCab(0,messagesData.all,"ALL")
    }
    if(inventoryGroup != null)
    {
	    for (var i=0; i < inventoryGroup.length; i++)
	    {
	        setCab(i,inventoryGroupName[i],inventoryGroup[i])
	    }
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryO = document.getElementById("inventory");
    inventoryO[href] = optionName;
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

function showInventoryDetails()
{
  if (selectedrow != null)
  {
	  var catPartNo  =  cellValue(selectedrow,"catPartNo");
	  var inventoryGroup  =  cellValue(selectedrow,"inventoryGroup");
	  var catalogId  =  cellValue(selectedrow,"catalogId");
	  var partGroupNo  =  cellValue(selectedrow,"partGroupNo");
	  var catalogCompanyId  =  cellValue(selectedrow,"catalogCompanyId");

	  if ( (catPartNo != null && inventoryGroup != null && catPartNo != 0) && (catPartNo.length > 0 && inventoryGroup.length > 0) )
	  {
	  var loc = "inventorydetail.do?catPartNo="+catPartNo+"&inventoryGroup="+inventoryGroup+"&catalogId="+catalogId+"&partGroupNo="+partGroupNo+"&catalogCompanyId="+catalogCompanyId;
	  openWinGeneric(loc,"showInventoryDetails22","800","500","yes","80","80")
	  }
	  else
	  {
	   alert(messagesData.novalidlineselected);
	  }
  }
}

function showInventoryPlot(monthSpan)
{
  if (selectedrow != null)
  {
     var catPartNo  =   cellValue(selectedrow,"catPartNo");
          var inventoryGroup  =  cellValue(selectedrow,"inventoryGroup");
          var catalogId  =  cellValue(selectedrow,"catalogId");
          var partGroupNo  =  cellValue(selectedrow,"partGroupNo");
     var inventoryGroupName  =  cellValue(selectedrow,"inventoryGroupName");
     var issueGeneration  =  cellValue(selectedrow,"issueGeneration");
	  var catalogCompanyId  =  cellValue(selectedrow,"catalogCompanyId");
	  var randomnumber=Math.floor(Math.random()*1001);

          if ( (catPartNo != null && inventoryGroup != null && catPartNo != 0) && (catPartNo.length > 0 && inventoryGroup.length > 0) )
          {
          var loc = "createinventorychart.do?catPartNo="+catPartNo+"&inventoryGroup="+inventoryGroup+"&catalogId="+catalogId+"&partGroupNo="+partGroupNo+"&inventoryGroupName="+inventoryGroupName+"&issueGeneration="+issueGeneration+"&monthSpan="+monthSpan+"&catalogCompanyId="+catalogCompanyId;
          openWinGeneric(loc,"showInventoryPlot22"+randomnumber+"","550","350","yes","80","80")
          }
          else
          {
           alert(messagesData.novalidlineselected);
          }
  }
}

function showLeadTimePlot()
{
  if (selectedrow != null)
  {
     var catPartNo  =  document.getElementById("catPartNo"+selectedrow+"");
	  var inventoryGroup  =  document.getElementById("inventoryGroup"+selectedrow+"");
	  var catalogId  =  document.getElementById("catalogId"+selectedrow+"");
	  var partGroupNo  =  document.getElementById("partGroupNo"+selectedrow+"");
	  var inventoryGroupName  =  document.getElementById("inventoryGroupName"+selectedrow+"");
	  var issueGeneration  =  document.getElementById("issueGeneration"+selectedrow+"");
	  var catalogCompanyId  =  document.getElementById("catalogCompanyId"+selectedrow+"");
	  var randomnumber=Math.floor(Math.random()*1001);

	  if ( (catPartNo.value != null && inventoryGroup.value != null && catPartNo.value != 0) && (catPartNo.value.length > 0 && inventoryGroup.value.length > 0) )
	  {
	  var loc = "createleadtimechart.do?catPartNo="+catPartNo.value+"&inventoryGroup="+inventoryGroup.value+"&catalogId="+catalogId.value+"&partGroupNo="+partGroupNo.value+"&inventoryGroupName="+inventoryGroupName.value+"&issueGeneration="+issueGeneration.value+"&catalogCompanyId="+catalogCompanyId.value;
	  openWinGeneric(loc,"showLeadTimePlot22"+randomnumber+"","550","350","yes","80","80")
	  }
	  else
	  {
	   alert(messagesData.novalidlineselected);
	  }
  }
}

function showMrLeadTimePlot()
{
  if (selectedrow != null)
  {
     var catPartNo  =  cellValue(selectedrow,"catPartNo");
	  var inventoryGroup  =  cellValue(selectedrow,"inventoryGroup");
	  var catalogId  =  cellValue(selectedrow,"catalogId");
	  var partGroupNo  =  cellValue(selectedrow,"partGroupNo");
	  var inventoryGroupName  =  cellValue(selectedrow,"inventoryGroupName");
	  var issueGeneration  =  cellValue(selectedrow,"issueGeneration");
	  var catalogCompanyId  =  cellValue(selectedrow,"catalogCompanyId");
	  var randomnumber=Math.floor(Math.random()*1001);

          if ( (catPartNo != null && inventoryGroup != null && catPartNo != 0) && (catPartNo.length > 0 &&inventoryGroup.length > 0) )
          {
          var loc = "createmrleadtimechart.do?catPartNo="+catPartNo+"&inventoryGroup="+inventoryGroup+"&catalogId="+catalogId+"&partGroupNo="+partGroupNo+"&inventoryGroupName="+inventoryGroupName+"&issueGeneration="+issueGeneration+"&catalogCompanyId="+catalogCompanyId;
          openWinGeneric(loc,"showLeadTimePlot22"+randomnumber+"","550","350","yes","80","80")
          }
          else
          {
           alert(messagesData.novalidlineselected);
          }
  }
}

function showSupplyLeadTimePlot()
{
  if (selectedrow != null)
  {
     var catPartNo  =  cellValue(selectedrow,"catPartNo");
          var inventoryGroup  =  cellValue(selectedrow,"inventoryGroup");
          var catalogId  =  cellValue(selectedrow,"catalogId");
          var partGroupNo  =  cellValue(selectedrow,"partGroupNo");
          var inventoryGroupName  =  cellValue(selectedrow,"inventoryGroupName");
          var issueGeneration  =  cellValue(selectedrow,"issueGeneration");
	  var catalogCompanyId  =  cellValue(selectedrow,"catalogCompanyId");
	  var randomnumber=Math.floor(Math.random()*1001);

          if ( (catPartNo != null && inventoryGroup != null && catPartNo != 0) && (catPartNo.length > 0 &&inventoryGroup.length > 0) )
          {
          var loc = "createsupplyleadtimechart.do?catPartNo="+catPartNo+"&inventoryGroup="+inventoryGroup+"&catalogId="+catalogId+"&partGroupNo="+partGroupNo+"&inventoryGroupName="+inventoryGroupName+"&issueGeneration="+issueGeneration+"&catalogCompanyId="+catalogCompanyId;
          openWinGeneric(loc,"showLeadTimePlot22"+randomnumber+"","550","350","yes","80","80")
          }
          else
          {
           alert(messagesData.novalidlineselected);
          }
  }
}

function showIssues()
{
  if (selectedrow != null)
  {
     var catPartNo  =  cellValue(selectedrow,"catPartNo");
	  var inventoryGroup  =  cellValue(selectedrow,"inventoryGroup");
	  var catalogId  =  cellValue(selectedrow,"catalogId");
	  var partGroupNo  =  cellValue(selectedrow,"partGroupNo");
	  var inventoryGroupName  =  cellValue(selectedrow,"inventoryGroupName");
	  var issueGeneration  =  cellValue(selectedrow,"issueGeneration");
	  var catalogCompanyId  =  cellValue(selectedrow,"catalogCompanyId");
	  var randomnumber=Math.floor(Math.random()*1001);

	  if ( (catPartNo != null && inventoryGroup != null && catPartNo != 0) && (catPartNo.length > 0 && inventoryGroup.length > 0) )
	  {
	  var loc = "createissueschart.do?catPartNo="+catPartNo+"&inventoryGroup="+inventoryGroup+"&catalogId="+catalogId+"&partGroupNo="+partGroupNo+"&inventoryGroupName="+inventoryGroupName+"&issueGeneration="+issueGeneration+"&catalogCompanyId="+catalogCompanyId;
	  openWinGeneric(loc,"showIssues22"+randomnumber+"","550","350","yes","80","80")
	  }
	  else
	  {
	   alert(messagesData.novalidlineselected);
	  }
  }
}

function showReceipts()
{
  if (selectedrow != null)
  {
     var catPartNo  =  cellValue(selectedrow,"catPartNo");
	  var inventoryGroup  =  cellValue(selectedrow,"inventoryGroup");
	  var catalogId  =  cellValue(selectedrow,"catalogId");
	  var partGroupNo  =  cellValue(selectedrow,"partGroupNo");
	  var inventoryGroupName  =  cellValue(selectedrow,"inventoryGroupName");
	  var issueGeneration  =  cellValue(selectedrow,"issueGeneration");
	  var catalogCompanyId  =  cellValue(selectedrow,"catalogCompanyId");
	  var randomnumber=Math.floor(Math.random()*1001);

	  if ( (catPartNo != null && inventoryGroup != null && catPartNo != 0) && (catPartNo.length > 0 && inventoryGroup.length > 0) )
	  {
	  var loc = "createreceiptschart.do?catPartNo="+catPartNo+"&inventoryGroup="+inventoryGroup+"&catalogId="+catalogId+"&partGroupNo="+partGroupNo+"&inventoryGroupName="+inventoryGroupName+"&issueGeneration="+issueGeneration+"&catalogCompanyId="+catalogCompanyId;
	  openWinGeneric(loc,"showReceipts22"+randomnumber+"","550","350","yes","80","80")
	  }
	  else
	  {
	   alert(messagesData.novalidlineselected);
	  }
  }
}

function cancel()
{
    window.close();
}

function onRightclick(rowId)
{
	selectedrow=rowId;
	// Show right-click menu
	toggleContextMenu('showAll');
}
