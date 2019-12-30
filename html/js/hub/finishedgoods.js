function $(a) {
	return document.getElementById(a);
}

function validateCriteria()
{
var errorMessage = messagesData.validvalues +"\n\n";
var errorCount = 0;

  var dailyDate = $("dailyDate");
	if (dailyDate.value.trim().length == 10)
	{
	  if (!checkdate(dailyDate))
	  {
	  errorMessage = errorMessage + messagesData.date + "("+messagesData.dateformat+")" +".\n" ;
	  errorCount = 1;
	  dailyDate.value = "";
	  }
	}
	else if (dailyDate.value.trim().length > 0 || dailyDate.value.trim().length == 0)
	{
	 errorMessage = errorMessage + messagesData.date + "("+messagesData.dateformat+")" +".\n" ;
	 errorCount = 1;
	 dailyDate.value = "";
	}

if (errorCount >0)
  return false;
return true;
}


function hubchanged()
{
	var hubO = $("hub");

	try
   {
     var sourceHubName = null;
     sourceHubName =  hubO.options[hubO.selectedIndex].text;

     var sourceHubNameObject = $("sourceHubName");
     sourceHubNameObject.value = sourceHubName;
   }
   catch (ex)
   {
     //alert("Non-Pickable Status");
   }

	var inventoryGroupO = $("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,"All","")
    }

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = $("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}
function search() {
  var flag = validateCriteria();
  if(!flag)  {
	alert(errorMessage);
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
/*
 excelfileurl = "/tcmIS/common/viewexcel.jsp";

 openWinGenericExcel(excelfileurl,"show_excel_report_file","610","600","yes");
 */
	  if(!validateCriteria())  {
		alert(errorMessage);
		}
		else {
  	  	var userAction = $("userAction");
  		userAction.value = 'buttonCreateExcel';
	  	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"show_excel_report_file","800","800","yes");             
		document.genericForm.target='show_excel_report_file';
    	var a = window.setTimeout("document.genericForm.submit();",500);
  		}
}
function showErrorMessages()
{
/*
	if( window.name == 'show_excel_report_file' ) 
		window.$('errorMessagesAreaBody').style['display'] = '';
	else
*/
		parent.showErrorMessages();
}

function update() {
    for(i=0;i>-1;i++) {
    	if( $('quantityRepackaged_'+i) == null ) break;
   	    $('modified_'+i).value = '';
   	    $('quantityRepackaged_'+i).value = 
	   	    $('quantityRepackaged_'+i).value.trim(); 
    	if( $('quantityRepackaged_'+i).value != '' )
//    	    $('old_quantityRepackaged_'+i).value )
    	    $('modified_'+i).value = 'Y';
    }
   	parent.showPleaseWait();
	document.genericForm.submit();
}

/*function showVvHubBins(rowNumber)
{
  var itemId = $("itemId_"+rowNumber).value;
  var branchPlant = $("hub_"+rowNumber).value;
  var loc = "/tcmIS/hub/newhubbinsmain.do?hub="+branchPlant+"&_action=showBins&itemId=bin_"+rowNumber+"&rowNumber="+rowNumber;
  openWinGeneric(loc,"showVvHubBins","180","300","no","80","80");

}
*/
function showVvHubBins(rowNumber)
{
  var itemId = $("itemId_"+rowNumber+"");
  var branchPlant = $("hub_"+rowNumber+"");

  var loc = "/tcmIS/hub/showhubbin.do?branchPlant="+branchPlant.value+"&userAction=showBins&rowNumber="+rowNumber+"&itemId=";
  loc = loc + itemId.value;
  openWinGeneric(loc,"showVvHubBins","300","150","no","80","80");
}

// move these code in js file once its finialized

function updateBin(name,values) {
	var len = $(name).options.length;
	for(ii =0 ;ii < values.length; ii ++) {
   		setOption(len+ii,values[ii],values[ii],name);
	}
}


function addBinCallback( vvHubBin,itemId,selectedRowNumber) {
	for(i = 0 ; i>-1; i++ ) {
		var item = $('itemId_'+i);
		if( item == null ) return; 
		if( item.value != itemId.value ) continue;
		var len = $('bin_'+i).options.length;
	    if( $('bin_'+i).options[len-1].text == messagesData.none ) 
	    	setOption(0,vvHubBin,vvHubBin,'bin_'+i);
	    else
	    	setOption(len,vvHubBin,vvHubBin,'bin_'+i);
	    if( $('quantityRepackaged_'+i) == null || $('quantityRepackaged_'+i).value.trim() == "" )
			$('bin_'+i).selectedIndex = $('bin_'+i).options.length-1;
	}
}


function mydup(rowNum) {
//totalRow is the current total number of rows.
	var tableName = "resultsPageTable";
	var table = $(tableName);
	var itemType = $(  'itemType_'+rowNum ).value ;
	var item_id = $(  'itemId_'+rowNum ).value ;
	var insertAt = -1;
	var resultLen = 0 ;
	var newArray = new Array();
	var oriRow = -1;
	var rowspan = 0;


	for( ii = 0 ;ii < table.rows.length; ii++ )  {  
			var dupthis = false;
			var rowLen = table.rows[ii].cells.length;   
			var htmlval = table.rows[ii].cells[ 1 ].innerHTML;
			if( htmlval == item_id ) {
					rowspan = table.rows[ii].cells[ 1 ].rowSpan;
					if( rowspan == null || rowspan == 0 || rowspan == 1 )
						rowspan = 1;
					oriRow = ii; 
		        	insertAt = oriRow + rowspan;
					resultLen = rowLen;
			        for(jj = 0 ; jj < rowLen; jj++ ) 
			        	newArray[jj] = table.rows[ii].cells[jj].innerHTML;  	
			        break;
			}
	}

	oldrow = table.rows[oriRow];
    row = table.insertRow(insertAt);
    row.className = oldrow.className; 
	rowspan++;

	for( jj = 0 ; jj < 5 ; jj ++ ) {
		oldrow.cells[jj].rowSpan = rowspan ;
	}

    var inner = "<input name=\"RepackageBatchViewBean["+totalRow+"].quantityRepackaged\" id=\"quantityRepackaged_"+totalRow+"\" value=\"\"/ size=\"4\">";
    if( itemType == "MV" )  {
    	inner += " " + messagesData.of +  " <input name=\"RepackageBatchViewBean["+totalRow+"].sizeUnit\" id=\"sizeUnit_" + totalRow + "\" value=\"\" size=\"4\"/>";
    } 
	var cell = row.insertCell(0);
	cell.innerHTML = inner;

	inner = "<input type=\"button\" class=\"smallBtns\" onmouseover=\"this.className='smallBtns smallBtnsOver'\" onmouseout=\"this.className='smallBtns'\" name=\"addBin_"+totalRow+"\" value=\"+\" onclick=\"showVvHubBins("+totalRow+")\" >";
	cell = row.insertCell(1);
	cell.innerHTML = inner;
	
	inner =
		 "<input type=\"hidden\" name=\"RepackageBatchViewBean["+totalRow+"].modified\" id=\"modified_"+totalRow+"\" value=\"Y\"/>"+
     	 "<input type=\"hidden\" name=\"RepackageBatchViewBean["+totalRow+"].receiptId\" id=\"receiptId_"+totalRow+"\" value=''/>" +
     	 "<input type=\"hidden\" name=\"RepackageBatchViewBean["+totalRow+"].batchId\" id=\"batchId_"+totalRow+"\" value=''/>" +
    	 "<input type=\"hidden\" name=\"RepackageBatchViewBean["+totalRow+"].hub\" id=\"hub_"+totalRow+"\" value=''/>"+
     	 "<input type=\"hidden\" name=\"RepackageBatchViewBean["+totalRow+"].itemId\" id=\"itemId_"+totalRow+"\" value=''/>" +
     	 "<input type=\"hidden\" name=\"RepackageBatchViewBean["+totalRow+"].itemType\" id=\"itemType_"+totalRow+"\" value=''/>" +
		 "<select name=\"RepackageBatchViewBean["+totalRow+"].bin\" id=\"bin_"+totalRow+"\"></select>";
	cell = row.insertCell(2);
	cell.innerHTML = inner;

	oldrow.cells[8].rowSpan = rowspan ;
	oldrow.cells[9].rowSpan = rowspan ;
	oldrow.cells[10].rowSpan = rowspan ;
	
	$("quantityRepackaged_"+totalRow).value = 
	$("quantityRepackaged_"+rowNum).value;

    if( itemType == "MV" )  {
		$("sizeUnit_"+totalRow).value = 
		$("sizeUnit_"+rowNum).value;
	}
	$("receiptId_"+totalRow).value = 
	$("receiptId_"+rowNum).value;

	$("batchId_"+totalRow).value = 
	$("batchId_"+rowNum).value;

	$("hub_"+totalRow).value = 
	$("hub_"+rowNum).value;
	
	$("itemId_"+totalRow).value = 
	$("itemId_"+rowNum).value;

	$("itemType_"+totalRow).value = 
	$("itemType_"+rowNum).value;
	
	var select1 = 	$("bin_"+rowNum);
	var select2 = 	$("bin_"+totalRow);
	
	for(ii=0;ii< select1.options.length; ii++) {
		var opt = select1.options[ii];
		setOption(ii,opt.text,opt.value,"bin_"+totalRow);
	}
	select2.selectedIndex = 
		select1.selectedIndex ;
		
	totalRow++;
	$("totallines").value = totalRow;
	$("totalLines").value = totalRow;
	setResultFrameSize();
}


function showUpdateLink() {
	if( showUpdateLinks ) 
		parent.document.getElementById("updateResultLink").style['display'] = "";
}