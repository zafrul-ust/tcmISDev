var beanGrid;
var selectedRowId = null;

var resizeGridWithWindow = true;

var multiplePermissions = true;
var permissionColumns = new Array();
permissionColumns={"partNumber":true,"unitOfMeasure":true,"poundsPerUnit":true,
				"comments":true,"tierIIStorage":true,"tierIITemperature":true,"purchasingMethodId":true,"vocetFugitiveCatId":true};

function resultOnLoad() {
	try{
		//Dont show any update links if the user does not have permissions
		if (!showUpdateLinks)  {
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
		else {
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
	}
 	catch(ex){
 	}	
	
	document.getElementById("usageImportConversionBean").style["display"] = "";
	
	try {
		initGridWithGlobal(gridConfig);
	} catch(ex) {}
	
	beanGrid.enableEditEvents(true, false, false);
	
	beanGrid.attachEvent("onBeforeSelect",doOnBeforeSelect);
		
    var totalLines = $v("totalLines")*1 - 1;
    
    if (totalLines > 0)
    	parent.document.getElementById("hasDataLink").style["display"] = "";
    	
 	var startSearchTime = parent.window.document.getElementById("startSearchTime");
    var now = new Date();
    var minutes = 0;
    var seconds = 0;
    //the duration is in milliseconds
    var searchDuration = now.getTime()-(startSearchTime.value*1);
    if (searchDuration > (1000*60)) {   //calculating minutes
         minutes = Math.round((searchDuration / (1000*60)));
         var remainder = searchDuration % (1000*60);
         if (remainder > 0) {   //calculating seconds
           seconds = Math.round(remainder / 1000);
         }
    }else {  //calculating seconds
         seconds = Math.round(searchDuration / 1000);
    }
    var footer = parent.document.getElementById("footer");
    if (minutes != 0) {
         footer.innerHTML = messagesData.recordFound+": "+totalLines+" -- "+messagesData.searchDuration+": "+minutes+" "+messagesData.minutes+" "+seconds+" "+messagesData.seconds;
    }else {
         footer.innerHTML = messagesData.recordFound+": "+totalLines+" -- "+messagesData.searchDuration+": "+seconds+" "+messagesData.seconds;
    }
 
	/*It is important to call this after all the divs have been turned on or off.*/
 	setResultFrameSize();
}

function selectRow(rowId,cellInd) {
	selectedRowId = rowId;
	
	/*if(cellInd == beanGrid.getColIndexById("customerMsdsOrMixtureNoDisplay"))
		initMsdsAutocomplete(rowId);*/
	
	if("deleteByMsds" == cellValue(rowId,"updateStatus") || "deleteByPart" == cellValue(rowId,"updateStatus"))
		colorAllDeleted(rowId);

	if(oldRowId != 0 )
		colorAllDeleted(oldRowId);	
}

function selectRightclick(rowId,cellInd) {
//  beanGrid.selectRowById(rowId,null,false,false);
  
  selectRow(rowId,cellInd);
  
  if("TCMIS" == cellValue(rowId,"status")) {
  	toggleContextMenu('emptyMenu');
  	return false;
  }
  
  var columnId = beanGrid.getColumnId(cellInd);
  
  vitems = new Array();
  
  vitems[vitems.length ] = "text="+messagesData.deletepart+";url=javascript:deletePart('deleteByPart')";
  
  /*if("deleteByPart" != cellValue(rowId, "updateStatus") && cellInd > beanGrid.getColIndexById("partNumber"))
  	vitems[vitems.length ] = "text="+messagesData.addmsds+";url=javascript:addMSDS()";
  
  if(cellInd > beanGrid.getColIndexById("partNumber")) 
  	vitems[vitems.length ] = "text="+messagesData.deletemsds+";url=javascript:deletePart('deleteByMsds')";*/
 
  replaceMenu('rightClickMenu',vitems);  
  toggleContextMenu('rightClickMenu');

}

function replaceMenu(menuname,menus){
	  var i = mm_returnMenuItemCount(menuname);

	  for(;i> 1; i-- )
			mm_deleteItem(menuname,i);

	  for( i = 0; i < menus.length; ){
 		var str = menus[i];

 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}

function getCustomerMsdsDbB4AddPart() {
	try {
		parent.showTransitWin('');
	} catch(ex) {}
	
	callToServer("usageimportinforesults.do?uAction=changeCustomerMsdsDb&callBack=addPart&facilityId="+$v("facilityId")); 
}

function setCompanyIdandCustomerMsdsDbvalidateLine(companyIdcustomerMsdsDb) {
	var result = companyIdcustomerMsdsDb.split("|");
	beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("companyId")).setValue(result[0]); 
    beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("customerMsdsDb")).setValue(result[1]);
    try {
    	parent.closeTransitWin();
    } catch(ex) {}
} 
//var autoCompleteCounter = 0;
function addPart(companyIdcustomerMsdsDb) {
   try {
    	parent.closeTransitWin();
   } catch(ex) {}
   
   var ind = 0; //beanGrid.getRowsNum(); 
   var rowid = 1; //ind*1+1;

   var rowData = [ 'Y',   	//permission
                   'Y',   	//partNumberPermission
                   'Y',   	//unitOfMeasurePermission
                   'Y',   	//poundsPerUnitPermission
                   'Y',    	//commentsPermission
                   'Y',    	//purchasingMethodPermission
                   'Y',    	//vocetFugitiveCatIdPermission
                   '',     //customerMsdsDb
                   '',   //partNumber
                   '',  //vocetFugitiveCatId
                   'Y',  //customerMsdsOrMixtureNoDisplayPermission
                    '',  //customerMsdsOrMixtureNoDisplay
                    '',  //unitOfMeasure
                    '',   //poundsPerUnit
                    '', //packaging
                    '',  //componentMsds,
                    '',  //materialId,
                    '',  //materialDesc,
                    '',  //approvalCode
                    'Y',   //tierIIStoragePermission
                    'Y',  //tierIITemperaturePermission
                    '', //tierIIStorage, 
                    '', //tierIIPressure,
                    '', //tierIITemperature, 
                    '',   	//comments,
                    '',   //lastModifiedByName,
                    '',  //createdByName,
                    '', //purchasingMethod
                    '', //companyId
                    $v("facilityId"),  //facilityId
                    '',  //originalPartNumber
                    '',  //originalCustomerMsdsOrMixtureNo
                    '',  //originalComments
                    '',  //customerMsdsOrMixtureNo
                    '',  //status
                    '',  //catalogCompanyId
                    '',  //catalogId
                    'newPart',  //updateStatus
                    'new'  //fugitiveCategoryStatus
                    ];
   
  var thisrow = beanGrid.haasAddRowToRowSpan(rowData,ind);
	 
   beanGrid.haasRenderRow(rowid);
   beanGrid.selectRow(ind);

   var result = companyIdcustomerMsdsDb.split("|");
   beanGrid.cells(rowid,beanGrid.getColIndexById("companyId")).setValue(result[0]); 
   beanGrid.cells(rowid,beanGrid.getColIndexById("customerMsdsDb")).setValue(result[1]);
	    
   parent.document.getElementById("hasDataLink").style["display"] = "";
   $("partNumber"+rowid).focus();
   initMsdsAutocomplete(ind + 1);
   //autoCompleteCounter++;
}

function addMSDS() {
	var rowId = beanGrid.getSelectedRowId();
	var ind = beanGrid.getRowIndex(rowId);
	var parentIndex = beanGrid.haasGetRowSpanStart(rowId);
	var newLinePosition = beanGrid.haasGetRowSpanEndIndex(rowId); 
	
	   var newRowData = [ 'Y',   	//permission
	                   'Y',   	//partNumberPermission
	                   'Y',   	//unitOfMeasurePermission
	                   'Y',   	//poundsPerUnitPermission
	                   'Y',    	//commentsPermission
	                   'Y',    	//purchasingMethodPermission
	                   'Y',    	//vocetFugitiveCatIdPermission
	                   cellValue(rowId,"customerMsdsDb"),     //customerMsdsDb
	                   cellValue(rowId,"partNumber"),   //partNumber
	                   cellValue(rowId,"vocetFugitiveCatId"),  //vocetFugitiveCatId
	                   'Y',  //customerMsdsOrMixtureNoDisplayPermission
	                    '',  //customerMsdsOrMixtureNoDisplay
	                    '',  //unitOfMeasure
	                    '',   //poundsPerUnit
	                     '', //packaging
	                    '',  //componentMsds,
	                    '',  //materialId,
	                    '',  //materialDesc,
	                    '',  //approvalCode
	                    'Y',   //tierIIStoragePermission
	                    'Y',  //tierIITemperaturePermission
	                    '', //tierIIStorage, 
	                    '', //tierIIPressure,
	                    '', //tierIITemperature, 
	                    '',   	//comments,
	                    '',   //lastModifiedByName,
	                    '',  //createdByName,
	                    '', //purchasingMethod
	                    cellValue(rowId,"companyId"), //companyId
	                    cellValue(rowId,"facilityId"),  //facilityId
	                    '',  //originalPartNumber
	                    '',  //originalCustomerMsdsOrMixtureNo
	                    '',  //originalComments
	                    '',  //customerMsdsOrMixtureNo
	                    '',  //status
	                    '',  //catalogCompanyId
	                    '',  //catalogId
	                    'new',  //updateStatus
	                    'new'  //fugitiveCategoryStatus
	                    ];

	beanGrid.haasAddRowToRowSpan(newRowData,newLinePosition, parentIndex);
	beanGrid.haasRenderRow(beanGrid.getRowId(parentIndex));

	initMsdsAutocomplete(newLinePosition + 1);
	//autoCompleteCounter++;
	beanGrid.selectRow(parentIndex);
}

function colorAllDeleted(oldRowId) {
	if("deleteByMsds" == cellValue(oldRowId,"updateStatus")) {
		colorDeletedRow(oldRowId);
	}
	else if ("deleteByPart" == cellValue(oldRowId,"updateStatus")){
		var parentIndex = beanGrid.haasGetRowSpanStart(oldRowId);
		var endIndex = beanGrid.haasGetRowSpanEndIndex(oldRowId);
			
		for ( var index = parentIndex; index < endIndex; index++) {
			colorDeletedRow(beanGrid.getRowId(index));
		}
	}
}


function deletePart(action) {
	if("deleteByMsds" == action) {
		beanGrid.cells(beanGrid.getSelectedRowId(), beanGrid.getColIndexById("updateStatus")).setValue("deleteByMsds"); 
		
		colorDeletedRow(beanGrid.getSelectedRowId());
	}
	else if ("deleteByPart" == action){
//		if (confirm(messagesData.verifyDelete)) {
			var parentIndex = beanGrid.haasGetRowSpanStart(beanGrid.getSelectedRowId());
			var endIndex = beanGrid.haasGetRowSpanEndIndex(beanGrid.getSelectedRowId());

			for ( var index = parentIndex; index < endIndex; index++) {
				beanGrid.cells(beanGrid.getRowId(index), beanGrid.getColIndexById("updateStatus")).setValue("deleteByPart");
			}
			
			for ( var index = parentIndex; index < endIndex; index++) {
				colorDeletedRow(beanGrid.getRowId(index));
			}
			
			beanGrid.cells(beanGrid.getRowId(parentIndex), beanGrid.getColIndexById("fugitiveCategoryStatus")).setValue("deleted");  
//		}
	}
}

function markChanged(rowId,cellInd) {
	
	if("new" != cellValue(rowId, "updateStatus") && "newPart" != cellValue(rowId, "updateStatus") && "deleteByMsds" != cellValue(rowId, "updateStatus") && "deleteByPart" != cellValue(rowId, "updateStatus")) {
		beanGrid.cells(rowId, beanGrid.getColIndexById("updateStatus")).setValue("changed");
	}

}

function fugitiveCategoryChanged(rowId,cellInd) {
	beanGrid.cells(rowId, beanGrid.getColIndexById("fugitiveCategoryStatus")).setValue("changed");
}

function updateParts() {
	if (validationforUpdate()) {
		document.genericForm.target = ''; 
		$('uAction').value = 'update';		
		
		var now = new Date();
		var startSearchTime = parent.document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();
		
		parent.showPleaseWait(); 

		if (beanGrid != null) {
			// This function prepares the grid dat for submitting top the server
			beanGrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
	}
}

// validate the whole grid
function validationforUpdate() {
	var rowsNum = beanGrid.getRowsNum();

	// This reflects the rowId we put in the JSON data 
	for ( var rowId = 1; rowId <= rowsNum; rowId++) {
		if (!validateLine(rowId)) {
			//Select the failing line
			beanGrid.selectRowById(rowId, null, false, false); 
			return false;
		}
	}

	return true;
}

// validate one line here
function validateLine(rowId,cellInd) {
	var errorMessage = messagesData.validvalues+"\n\n";
	var errorCount = 0;

	if( "TCMIS" == cellValue(rowId,"status") || "deleteByMsds" == cellValue(rowId,"updateStatus") || "deleteByPart" == cellValue(rowId,"updateStatus")) return true;
	
	var partNumber = cellValue(rowId, "partNumber");
	if (partNumber.length == 0) { 
		errorMessage = errorMessage +"\n"+ messagesData.partno;
		errorCount = 1;
	}
	
	var customerMsdsOrMixtureNo = cellValue(rowId, "customerMsdsOrMixtureNo");
	if (customerMsdsOrMixtureNo.length == 0) { 
		errorMessage = errorMessage +"\n"+ messagesData.msds;
		errorCount = 1;
	}

	var poundsPerUnit = cellValue(rowId, "poundsPerUnit");
	if (cellValue(rowId,"updateStatus") == 'newPart' &&  !isFloat(poundsPerUnit, false)) { 
		errorMessage = errorMessage +"\n"+ messagesData.lbsuom;
		errorCount = 1;
	}
	else if(poundsPerUnit != '' && !isFloat(poundsPerUnit, false))
	{ 
		errorMessage = errorMessage +"\n"+ messagesData.decimal.replace('{0}',messagesData.lbsuom);
		errorCount = 1;
	}
	
	if (errorCount > 0)
 	{
	     alert(errorMessage);
		 return false;
	}

	return true;
}

var oldRowId = 0;
function doOnBeforeSelect(rowId,oldRowId0) {
	oldRowId = oldRowId0;
	return true;
}

var globalFormatted;
var count = 0;
var customerMsdsOrMixtureNoDisplayFlag = new Array();
function initMsdsAutocomplete(rowId) {

	 /* if( customerMsdsOrMixtureNoDisplayFlag[rowId] == undefined)   
	  {     
	      j$("#customerMsdsOrMixtureNoDisplay"+rowId).livequery(function() 
	    		  {*/
		   //j$("#customerMsdsOrMixtureNoDisplay"+rowId).autocomplete("destroy");

			j$("#customerMsdsOrMixtureNoDisplay"+rowId).autocomplete("getmsds.do",{
					extraParams: {customerMsdsDb: function() { return cellValue(rowId, "customerMsdsDb"); },
							  companyId: function() { return cellValue(rowId, "companyId"); } },
					width: 500,
					max: 20,  // default value is 10
					cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
					scrollHeight: 150,
					formatItem: function(data, i, n, value) {
						return  value.split(":;~")[0].split("::")[1];
					},
					formatResult: function(data, value) {
						return value.split(":;~")[0].split("::")[1];
					}

			});
			
			j$("#customerMsdsOrMixtureNoDisplay"+rowId).bind('keydown',(function(e) {
				  var keyCode = (e.keyCode ? e.keyCode : e.which);
				  if( keyCode == 27) // 27 is ESC
					  return false;
				  
			}));
			
			j$("#customerMsdsOrMixtureNoDisplay"+rowId).bind('keyup',(function(e) {
				  var keyCode = (e.keyCode ? e.keyCode : e.which);
		
				  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
				  	invalidatecustomerMsdsOrMixtureNoDisplayInput(rowId);
				  
			}));			  
			
			j$("#customerMsdsOrMixtureNoDisplay"+rowId).result(log).next().click(function() {
					j$(this).prev().search();
					j$(this).unbind();
			});
			
			function log(event, data, formatted) {
				globalFormatted = formatted;
				createRows(rowId);
			} 

	/*}
	);
	customerMsdsOrMixtureNoDisplayFlag[rowId] = "true";
	  }
	  else {
		  
		       if( customerMsdsOrMixtureNoDisplayFlag[autoCompleteCounter] == undefined) 
		            initMsdsAutocomplete(autoCompleteCounter);
	
	 }  */

}

function createRows(rowId)
{
	formatted = globalFormatted;
	//if(!$('customerMsdsOrMixtureNoDisplay'+rowId).readOnly)
	{
		$("customerMsdsOrMixtureNoDisplay"+rowId).className = "inputBox"; 
	
		var fVals = formatted.split(":;~");
		
		/*var parent2Index = beanGrid.haasGetRowSpanStartLvl2(rowId);
		var parent2IndexEnd = beanGrid.haasGetRowSpanEndIndexLvl2(rowId);
		/*if((parent2IndexEnd - parent2Index) > 1)
		{
			var parentIndex = beanGrid.haasGetRowSpanStart(rowId);
			for(var j = parent2IndexEnd - 1;j > parent2Index;--j)
				{
				   var id = beanGrid.getRowId(j);
				 	beanGrid.haasDeleteRowFromRowSpan(id);
				}

			var autoList = j$(":input[id*='customerMsdsOrMixtureNoDisplay']");
			for(var k = 0; k < autoList.length;k++)
				initMsdsAutocomplete(autoList[k].id.replace(/[^\d]/g, ""));

			beanGrid.cells(rowId,beanGrid.getColIndexById("customerMsdsOrMixtureNo")).setValue(fVals[0].split("::")[0]);
			beanGrid.cells(rowId,beanGrid.getColIndexById("customerMsdsNumber")).setValue(fVals[1].split("::")[2]);
			beanGrid.cells(rowId,beanGrid.getColIndexById("materialId")).setValue(fVals[1].split("::")[0]);
			beanGrid.cells(rowId,beanGrid.getColIndexById("materialDesc")).setValue(fVals[1].split("::")[1]);
		}
		else*/
			{
				beanGrid.cells(rowId,beanGrid.getColIndexById("customerMsdsOrMixtureNo")).setValue(fVals[0].split("::")[0]);
				beanGrid.cells(rowId,beanGrid.getColIndexById("customerMsdsNumber")).setValue(fVals[1].split("::")[2]);
				beanGrid.cells(rowId,beanGrid.getColIndexById("materialId")).setValue(fVals[1].split("::")[0]);
				beanGrid.cells(rowId,beanGrid.getColIndexById("materialDesc")).setValue(fVals[1].split("::")[1]);
			}
		
		if(fVals.length > 2)
		{
				for(var i = 2; i < fVals.length;i++)
				{	   
					   var newRowData = [ 'Y',   	//permission
						                   'Y',   	//partNumberPermission
						                   'Y',   	//unitOfMeasurePermission
						                   'Y',   	//poundsPerUnitPermission
						                   'Y',    	//commentsPermission
						                   'Y',    	//purchasingMethodPermission
						                   'Y',    	//vocetFugitiveCatIdPermission
						                   cellValue(rowId,"customerMsdsDb"),     //customerMsdsDb
						                   cellValue(rowId,"partNumber"),   //partNumber
						                   cellValue(rowId,"vocetFugitiveCatId"),  //vocetFugitiveCatId
						                   'N',  //customerMsdsOrMixtureNoDisplayPermission
						                   cellValue(rowId,"customerMsdsOrMixtureNo"),  //customerMsdsOrMixtureNoDisplay
						                    '',  //unitOfMeasure
						                    '',   //poundsPerUnit
						                    '', //containerSize
						                    fVals[i].split("::")[2],  //componentMsds,
						                    fVals[i].split("::")[0],  //materialId,
						                    fVals[i].split("::")[1],  //materialDesc,
						                    '',  //approvalCode
						                    'Y',   //tierIIStoragePermission
						                    'Y',  //tierIITemperaturePermission
						                    '', //tierIIStorage, 
						                    '', //tierIIPressure,
						                    '', //tierIITemperature, 
						                    '',   	//comments,
						                    '',   //lastModifiedByName,
						                    '',  //createdByName,
						                    '', //purchasingMethod
						                    cellValue(rowId,"companyId"), //companyId
						                    cellValue(rowId,"facilityId"),  //facilityId
						                    '',  //originalPartNumber
						                    '',  //originalCustomerMsdsOrMixtureNo
						                    '',  //originalComments
						                    cellValue(rowId,"customerMsdsOrMixtureNo"),  //customerMsdsOrMixtureNo
						                    '',  //status
						                    '',  //catalogCompanyId
						                    '',  //catalogId
						                    'new',  //updateStatus
						                    'new'  //fugitiveCategoryStatus
						                    ];
					   
					/*var parentIndex = beanGrid.haasGetRowSpanStart(rowId);
					var parent2Index = beanGrid.haasGetRowSpanStartLvl2(rowId);
					var newLinePosition = beanGrid.haasGetRowSpanEndIndexLvl2(rowId);
					beanGrid.haasAddRowToRowSpan(newRowData, newLinePosition, parentIndex, parent2Index);
					*/
					
					var parentIndex = beanGrid.haasGetRowSpanStart(rowId);
					var newLinePosition = beanGrid.haasGetRowSpanEndIndex(rowId); 
					beanGrid.haasAddRowToRowSpan(newRowData,newLinePosition, parentIndex);
					beanGrid.haasRenderRow(beanGrid.getRowId(parentIndex));
					//initMsdsAutocomplete(parentIndex + 1);
					beanGrid.selectRow(parentIndex, true);
				}
		}
		j$("#customerMsdsOrMixtureNoDisplay"+rowId).unbind();
		j$("#customerMsdsOrMixtureNoDisplay"+rowId).autocomplete( "destroy" );
		$('customerMsdsOrMixtureNoDisplay' + rowId).readOnly = true;
	}
}




function invalidatecustomerMsdsOrMixtureNoDisplayInput(rowId)
{
	 var customerMsdsOrMixtureNoDisplay = document.getElementById("customerMsdsOrMixtureNoDisplay"+rowId);
	 
	 if (customerMsdsOrMixtureNoDisplay.value.length == 0) {
	   	customerMsdsOrMixtureNoDisplay.className = "inputBox";
	 }else {
	   	customerMsdsOrMixtureNoDisplay.className = "inputBox red";
	 }
	 
	 beanGrid.cells(rowId,beanGrid.getColIndexById("customerMsdsOrMixtureNo")).setValue("");
}	

function colorDeletedRow(rowId) {
	if("deleteByMsds" == cellValue(rowId,"updateStatus")) {
		var start = beanGrid.haasGetRowSpanStartLvl2(rowId);
		var end = beanGrid.haasGetRowSpanEndIndexLvl2(rowId)
		if(end - start > 1)
			for (var i = start + 1; i < end + 1; i++)
				beanGrid.haasSetColSpanStyle(i, beanGrid.getColIndexById("customerMsdsOrMixtureNoDisplay"), beanGrid.getColIndexById("createdByName"), "background-color: #BEBEBE; text-decoration:line-through;");
		else
			beanGrid.haasSetColSpanStyle(rowId, beanGrid.getColIndexById("customerMsdsOrMixtureNoDisplay"), beanGrid.getColIndexById("createdByName"), "background-color: #BEBEBE; text-decoration:line-through;");

	}
	else if("deleteByPart" == cellValue(rowId,"updateStatus"))
		beanGrid.haasSetColSpanStyle(rowId, beanGrid.getColIndexById("partNumber"), beanGrid.getColIndexById("createdByName"), "background-color: #BEBEBE; text-decoration:line-through;");
}
	
