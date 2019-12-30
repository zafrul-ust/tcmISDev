var selectedRowId = null;

function submitSearchForm()
{
	// alert("this is the new search js ...");
    if ( validateSearchForm() == false )
    {
     	return false;
    }
    var now = new Date();
    document.getElementById("startSearchTime").value = now.getTime();
    
    $('uAction').value = 'search';
    document.genericForm.target='resultFrame';
    showPleaseWait();
    return true;
}

function validateSearchForm()
{
	var errorMessage = "";
	
	var errorCount = 0;
	var opsEntity = $('opsEntityId');
	var hubO = $('hub');
	var searchField  =  document.getElementById("searchField").value;	
	var searchArgument  =  document.getElementById("searchArgument");
	if((opsEntity.options[opsEntity.selectedIndex].text=="My Entities") && (hubO.options[hubO.selectedIndex].text=="My Hubs"))
	{	
		
		if ( searchArgument.value.trim().length == 0 )
		{
		   errorMessage +=  messagesData.searchField + "\n";
		   errorCount++;		 	
			
		}
		
	}

	
	
  	if(searchField == 'ITEM_ID') 
  	{
  		
    	if(!isInteger(searchArgument.value.trim(), true)) 
    	{
    		errorMessage = errorMessage + messagesData.itemInteger + "\n" ;     		
      		errorCount++;
    	}
  	}
	var doLimitToRecentField = document.getElementById("doLimitToRecent");
	if (doLimitToRecentField.checked == true)
	{
		var daysSinceIssuedLimitField = document.getElementById("daysSinceIssuedLimit").value.trim();
  		if(daysSinceIssuedLimitField.length == 0 ) 
  		{
    		errorMessage = errorMessage + messagesData.specifyDaysSinceIssuedLimit + ".\n" ;
    		errorCount++;
  		}
		else
		{
  			if(!isInteger( daysSinceIssuedLimitField, true) ) 
  			{
    			errorMessage = errorMessage + messagesData.daysSinceIssuedLimitInteger + ".\n" ;
    			errorCount++;
  			}
  		}
  	}
  	
	if (errorCount > 0)
	{
  		alert(errorMessage);
  		return false;
	}
	else
	{
  		return true;
	}
}

function generateExcel() 
{
  	var isValidForm = validateSearchForm();
  	if ( isValidForm ) 
  	{
  		var uAction = document.getElementById("uAction");
    	uAction.value = 'createExcel';
    	openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_newGenerateExcel','650','600','yes');
    	document.genericForm.target='_newGenerateExcel';
    	var a = window.setTimeout("document.genericForm.submit();", 500);
  	}
}

function doOnRowSelected(rowId,cellInd) {
	var columnId = beanGrid.getColumnId(cellInd);
	selectedRowId = rowId;
/* 	switch (columnId)
 	{
 	case "radianPo":
 		var po = mygrid.cellById(rowId,cellInd).getValue();
 		showPurchOrder(po);
 		break;		
 	default:
 	};  
*/
	
}


function selectRightclick(rowId, cellInd) {
	
	beanGrid.selectRowById(rowId, null, false, false);
	toggleContextMenu('invLevelManagementMenu');		
}

function showMinMax(itemID, invengrp)
{
	/*if (hubname == 'Goleta Rio Hub')
	{
	loc = "/cgi-bin/rayedit/golmmchk.cgi?bp=";
	}
	else if (hubname == 'El Segundo Hub')
	{
	loc = "/cgi-bin/rayedit/elsegmmchk.cgi?bp=";
	}
	else
	{
	 loc = "/cgi-bin/radadmin/mmchk.cgi?bp=";
	}*/
	//var HubName  =  document.getElementById("HubName");
	
	var opsEntityId  = cellValue(beanGrid.getSelectedRowId(),"opsEntityId");	
	var hub  = cellValue(beanGrid.getSelectedRowId(),"hHub");	
	
	var itemID = cellValue(beanGrid.getSelectedRowId(),"hItemId"); 
		
	var invengrp = cellValue(beanGrid.getSelectedRowId(),"inventoryGroup");  
		   

	//var invengrp  =  document.getElementById("invengrp");
//	  loc = "/tcmIS/hub/minmaxchg?UserAction=showlevels&searchlike=ITEM_ID&searchtext="
//    loc = loc + itemID;
//    loc = loc + "&HubName=" + hub;
//    loc = loc + "&invengrp=" + invengrp;
//    link new minmaxlevel page..

	loc = "/tcmIS/hub/minmaxlevelmain.do?uAction=showlevels&criterion=itemId&criteria="
    loc = loc + itemID;
    loc = loc + "&opsEntityId=" + opsEntityId;
    loc = loc + "&hub=" + hub;
    loc = loc + "&inventoryGroup=" + invengrp;
    // alert("url = [" + loc + "]; ");
    
    openWinGeneric(loc,"Show_Min_Max", "800", "600", "yes")
}

function showcrosstab(itemID,invengrp)
{
	// var HubName  =  document.getElementById("HubName");
	
	var hub  = cellValue(beanGrid.getSelectedRowId(),"hHub");	
	
	var itemID = cellValue(beanGrid.getSelectedRowId(),"hItemId"); 
		
	var invengrp = cellValue(beanGrid.getSelectedRowId(),"inventoryGroup");    
	 //var invengrp  =  document.getElementById("invengrp");
	 loc = "/tcmIS/hub/crosstablevels?UserAction=showcrosstablevels&searchlike=ITEM_ID&SearchField="
    loc = loc + itemID;
    loc = loc + "&HubName=" + hub;
    loc = loc + "&invengrp=" + invengrp;
    // alert("url = [" + loc + "]; ");
    
    openWinGeneric(loc, "Show_cross_tab", "600", "400", "yes");
}

