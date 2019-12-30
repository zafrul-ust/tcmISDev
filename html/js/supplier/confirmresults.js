function $(a) {
	return document.getElementById(a);
}
// assuming mm/dd/yyyy format.
function dateToInt(strval) {
 var ind = strval.indexOf('/');
 var intval = strval.substr(ind+4,4)+strval.substr(ind-2,2) + strval.substr(ind+1,2);
 return intval;
}

/*function okCheck(rowNum) {
	// delivery date >= ship date, both are already valid.
	var errorHeader = messagesData.validValues + "\n\n";
	var errorBody = "";
	var errorMsg = "";
	var errorCount = 0 ;
	var focus = null;
	var requiredF = new Array (
		);
  	var compareDate = true;
  if( $('ok_'+rowNum).checked )
  {
  for(ii = 0 ; ii < requiredF.length; ii ++ ) {
  		requiredF[ii].value = requiredF[ii].value.trim();
		if( ii == 0 ) { // dont check, should be database error.
		*//*&& requiredF[ii].value.length == 0 ) {
			errorCount++;
			errorBody += requiredTitle[ii]+ "\n";
			if( focus == null ) 
				focus = requiredF[ii];
		*//*
		}
		else
		if( ii > 0 && (requiredF[ii].value.length !=10 || !checkdate(requiredF[ii]))) {
			errorCount++;
			errorBody += requiredTitle[ii+3]+ "\n";
			if( focus == null ) 
				focus = requiredF[ii];
			compareDate = false;
		}
	}
	
	if( errorCount > 0 ) 
		errorMsg = errorHeader + errorBody;
	if( compareDate ) // already known valid dates. 
		if( dateToInt($('dateDelivered_'+rowNum).value) < dateToInt($('dateShipped_'+rowNum).value) ) {
				errorMsg += messagesData.shipltdelivery;
				if( focus == null ) 
					focus = $('dateDelivered_'+rowNum);
				errorCount++;
		}
	}

  if( errorCount == 0 )
  {
    return true;
  }
  else
  {
  alert( errorMsg ) ;
  $('ok_'+rowNum).checked = false;
  focus.focus();
  return false;
  }
}*/

function confirm() {
 if ($("dataChanged").value == "Yes")
 {
   alert("Please update changes to data before confirming.");
   return;
 }
 $('userAction').value = 'confirm';
document.supplierLocationForm.action='confirmresults.do';

openWinGeneric('/tcmIS/common/loadingfile.jsp','_confirmshipment','600','400','yes');
document.supplierLocationForm.target='_confirmshipment';
var a = window.setTimeout("document.supplierLocationForm.submit();",300);
}

function update() {
   	parent.showPleaseWait();
   	$('userAction').value = 'update';
	document.supplierLocationForm.action='confirmresults.do';
	document.supplierLocationForm.target='';
   	document.supplierLocationForm.submit();
}

/*function create() {
   	parent.showPleaseWait();
   	$('userAction').value = 'create';
	document.supplierLocationForm.action='confirmresults.do';
	document.supplierLocationForm.target='resultFrame';
   	document.supplierLocationForm.submit();
}*/

function showErrorMessages()
{
		parent.showErrorMessages();
}

function showUpdateLink() {
	if( showUpdateLinks )
		parent.document.getElementByid("updateResultLink").style['display'] = "";
}

function createExcel() {    
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', 'show_excel_report_file','800','600','yes');
		document.supplierLocationForm.target='show_excel_report_file';
		document.supplierLocationForm.action='usgovlabelingresults.do';
  	var userAction = document.getElementById("userAction");
 		userAction.value = 'createExcel';
   	var a = window.setTimeout("document.supplierLocationForm.submit();",1000);

   	return false;
}

function valueChanged(elementChanged)
{
  $(elementChanged).style.backgroundColor = "#FFFF99";
  $("dataChanged").value = "Yes";
}

function resultOnLoad()
{
 setResultFrameSize();
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
}

function checkAllOkBoxes(checkBoxName)
{
var checkAll = document.getElementById("checkAll");
var totalLines = document.getElementById("totalLines").value;
totalLines = totalLines*1;
var result =  checkAll.checked;
var checkedCount = 0;

for ( var p = 0 ; p < totalLines ; p ++)
 {
	try
	{
	var lineCheck = document.getElementById(""+checkBoxName+""+(p)+"");
  if (lineCheck.checked && !result)
  {
    lineCheck.checked =false;
  }
  else if (!lineCheck.checked && result)
  {
    lineCheck.checked=true;
    checkedCount ++;
  }
  }
	catch (ex1)
	{
	}
 }
 if (checkedCount ==0 && result)
 {
   checkAll.checked = false;
 }
}