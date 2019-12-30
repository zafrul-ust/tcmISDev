var showCheckAllBox = false;

function $(a) {
	return document.getElementById(a);
}

function validatePickingForm() {
  document.genericForm.target='';
  if ($("searchArgument").value.trim().length > 0 && $("searchField").value == "prNumber")
  {
  if(!isInteger($("searchArgument").value.trim(), true)) {
    alert(messagesData.mrNumberInteger);
    return false;
  }
  }
/*
  if(!isInteger(document.genericForm.expireDays.value.trim(), true)) {
    alert(messagesData.expireDaysInteger);
    return false;
  }
*/
  return true;
}

function submitSearchForm() {
	if( !validatePickingForm() ) return;
	var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
	var userAction = document.getElementById("action");
	userAction.value = 'search';

   	document.genericForm.target='resultFrame';
   	parent.showPleaseWait();
//	return true;
}

function doregenerate() {
  var loc = "/tcmIS/hub/openpicklistmain.do?fromCarrierPicking=Y&hub="+$("hub").value+"&inventoryGroup="+$("inventoryGroup").value+"&sortBy="+$("sortBy").value;
  openWinGeneric(loc,"openpicklistreprint","800","600","yes","80","80");  
}

function createExcel() {
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'show_excel_report_file','800','600','yes');
		document.genericForm.target='show_excel_report_file';
  	var userAction = document.getElementById("action");
 		userAction.value = 'createExcel';
    var a = window.setTimeout("document.genericForm.submit();",1000);
    //document.genericForm.submit();
   	return false;
}

function showNotes(fieldid)
{
   var section = 'div' + fieldid;
   var pgphBlock = 'pgph' + fieldid;
   var current = (document.getElementById(section).style.display == 'block') ? 'none' : 'block';
   document.getElementById(section).style.display = current;
   document.getElementById(pgphBlock).innerHTML = (current == 'block') ? '<i>-</i>' : '<i>+</i>';
}

function resultOnLoad()
{
 displaySearchDuration(); 
 setResultFrameSize();
 if (!showCheckAllBox) /*Dont show any update links if the user does not have permissions*/
 {
   parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
   parent.document.getElementById("updateResultLink").style["display"] = "";
 }

 if ($("picklistId").value.trim().length > 0)
 {
   var loc = "/tcmIS/hub/picklistreprintmain.do?action=search&picklistId="+$("picklistId").value+"&sortBy="+$("sortBy").value;
   openWinGeneric(loc,"picklistreprint","800","400","yes","80","80");
 }
}

/*The reason for this is to show the error messages from the main page*/
function showErrorMessages()
{
  parent.showErrorMessages();
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

function genPicklist()
{
   var checked = countChecked();
   if (checked==0) {
      alert(messagesData.pleasemakeselection);
      return false;
   } 
   else {
      document.genericForm.target='';
      document.getElementById('action').value = 'generate';
      parent.showPleaseWait();
      document.genericForm.submit();
      return true;
   }
}        

function checkall(checkbx, formname, checkboxname, headername)
{    
    var totallines = $("totalLines").value;
    var result;
    var valueq;

    if (checkbx.checked)
    {
        result = true;
        valueq = "true";
    }
    else
    {
        result = false;
        valueq = "false";
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
        catch (ex1){}
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
        catch (ex1){}
    }
}

// Generated id and name listed here, search and replace with the correct values.
// Level 0 id and name: inventoryGroup inventoryGroup
// Level 1 id and name: mode modeName
// Level 2 id and name: bldgId carrierName
// top commons for all...

// the set function that called on onload
function setTopDropDown() {
	var oldL0 = "";
//	var oldL1 = "";
//	var oldL2 = "";
	try {
	var oldhub = $("oldhub").value;
	oldL0 = $("oldinventoryGroup").value;
//	oldL1 = $("oldmode").value;
//	oldL2 = $("oldcarrierCode").value;
	} catch (ex) {}
	showhub(oldhub,oldL0);
//	showL0(oldL0,oldL1,oldL2);
;}
// Show drop downs with particular value
function showhub(L0value,L1value) {
	idArray = althub;	nameArray = althubName;

	var reset = $("hub").options;
	for ( i = 0; i < reset.length; i++) {
		reset[i] = null;
	}
	var selectI = 0;
	var insertAt = 0;
	for ( i = 0; i < nameArray.length; i++) 
		if( i!=0 && nameArray[i] != null ) {
			setOption(insertAt,nameArray[i],idArray[i],"hub");
			if( L0value == idArray[i] )
				selectI = insertAt;
			insertAt++;
		}
	$("hub").selectedIndex = selectI;
	showinv($("hub").value,L1value);
}

function showinv(L0value,L1value) {
	var idArray = new Array("");
	var nameArray = new Array(emptyValues[0]);
	var idArray1 = null;
	var nameArray1 = null;
	try{
		idArray1 = altinvGrp_[L0value];	nameArray1 = altinvGrpName_[L0value];
// if use default value instead of empty value, comment out the below (1) line
		if( nameArray1.length == 1 ) nameArray1 = null;
	}catch(ex){}
	if( idArray1 != null && nameArray1 != null )  {
	idArray = idArray1;
	nameArray = nameArray1;
	}
	var reset = $("inventoryGroup").options;
	for (i = reset.length; i> 0;i--) {
		reset[i] = null;
	}
	var selectI = 0;
	var insertAt = 0;
	for (var i=0; i < nameArray.length; i++) 
		if( nameArray[i] != null ) {
			setOption(insertAt,nameArray[i],idArray[i],"inventoryGroup");
			if( L1value == idArray[i] )
				selectI = insertAt;
			insertAt++;
		}
	$("inventoryGroup").selectedIndex = selectI;
//	showL1($("inventoryGroup").value,"","");
}
/*
function showL0(L0value,L1value,L2value) {
	var idArray = new Array("");
	var nameArray = new Array(emptyValues[0]);
	var idArray1 = null;
	var nameArray1 = null;
	try{
		idArray1 = altinv;	nameArray1 = altinvName;
// if use default value instead of empty value, comment out the below (1) line
		if( nameArray1.length == 1 ) nameArray1 = null;
	}catch(ex){}
	if( idArray1 != null && nameArray1 != null )  {
	idArray = idArray1;
	nameArray = nameArray1;
	}
	var reset = $("inventoryGroup");
	for ( i = 0; i < reset.length; i++) {
		reset[i] = null;
	}
	var selectI = 0;
	var insertAt = 0;
	for (var i=0; i < nameArray.length; i++) 
		if( i !=0 || nameArray[i] != null ) {
			setOption(insertAt,nameArray[i],idArray[i],"inventoryGroup");
			if( L0value == idArray[i] )
				selectI = insertAt;
			insertAt++;
		}
	$("inventoryGroup").selectedIndex = selectI;
	showL1($("inventoryGroup").value,L1value,L2value);
}
*/
function showL1(L0value,L1value,L2value) {
	var idArray = new Array("");
	var nameArray = new Array(emptyValues[1]);
	var idArray1 = null;
	var nameArray1 = null;
	try{
		idArray1 = altmode[L0value];	nameArray1 = altmodeName[L0value];
// if use default value instead of empty value, comment out the below (1) line
		if( nameArray1.length == 1 ) nameArray1 = null;
	}catch(ex){}
	if( idArray1 != null && nameArray1 != null )  {
	idArray = idArray1;
	nameArray = nameArray1;
	}
	var reset = $("transportationMode").options;
	for (i = reset.length; i> 0;i--) {
		reset[i] = null;
	}
	var selectI = 0;
	var insertAt = 0;
	for (var i=0; i < nameArray.length; i++) 
		if( nameArray[i] != null ) {
			setOption(insertAt,nameArray[i],idArray[i],"transportationMode");
			if( L1value == idArray[i] )
				selectI = insertAt;
			insertAt++;
		}
	$("transportationMode").selectedIndex = selectI;
	showL2($("inventoryGroup").value,$("transportationMode").value,L2value);
}
/*
function showL2(L0value,L1value,L2value) {
	var idArray = new Array("");
	var nameArray = new Array(emptyValues[2]);
	var idArray1 = null;
	var nameArray1 = null;

  try{
		idArray1 = altcarrierCode[L0value][L1value];	nameArray1 = altcarrierName[L0value][L1value];
// if use default value instead of empty value, comment out the below (1) line
		if( nameArray1.length == 1 ) nameArray1 = null;
	}catch(ex){}
	if( idArray1 != null && nameArray1 != null )  {
	idArray = idArray1;
	nameArray = nameArray1;
	}
	var reset = $("carrierCode").options;
	for (i = reset.length; i> 0;i--) {
		reset[i] = null;
	}
	var selectI = 0;
	var insertAt = 0;
	for (var i=0; i < nameArray.length; i++) 
		if( nameArray[i] != null ) {
			setOption(insertAt,nameArray[i],idArray[i],"carrierCode");
			if( L2value == idArray[i] )
				selectI = insertAt;
			insertAt++;
		}
	$("carrierCode").selectedIndex = selectI;
}
*/
function hubChanged() {
		showinv($("hub").value,"");
}
function L0Changed() {
		showL1($("inventoryGroup").value,"","","");
}
/*
function L1Changed() {
		showL2($("inventoryGroup").value,$("transportationMode").value,"");
}
*/
function showpickingpagelegend()
{
  openWinGeneric("/tcmIS/help/pickingpagelegend.html","pickingpagelegenddd","290","300","no","50","50");
}

function showDeliveryComments2(rowId)
{
  var deliveryCommentsMessagesAreaBody = parent.document.getElementById("deliveryCommentsMessagesAreaBody");
  deliveryCommentsMessagesAreaBody.innerHTML = $v("mrNotes"+rowId);

  var deliveryCommentsMessagesArea = parent.document.getElementById("deliveryCommentsMessagesArea");
  deliveryCommentsMessagesArea.style.display="";

  parent.deliveryCommentsWin.show();
}