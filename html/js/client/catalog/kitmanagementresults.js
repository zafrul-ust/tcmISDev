function updateKit() {
	if (validationforUpdate()) 
	{
		document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
		parent.document.getElementById("uAction").value = 'update';	
		document.getElementById('uAction').value = 'update';// $('formVariableName') is a utility function that does a document.getElementById('variableName')
							// $v('formVariableName') does the same with document.getElementById('variableName').value
		// Reset search time for update
		var now = new Date();
		var startSearchTime = parent.document.getElementById("startSearchTime");
		startSearchTime.value = now.getTime();	

		parent.showPleaseWait(); // Show "please wait" while updating

		if (beanGrid != null) {
			// This function prepares the grid dat for submitting top the server
			beanGrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
	}
}


function validationforUpdate()
{
	var completeErrorMsg = "";
	var percentAmount = 0;
	var requirePercentVolUnitCountCheck = 0;
	var requirePercentWtUnitCountCheck = 0;
	var completeVolWeightCheck = "";
	var perVolWeightCheck = false;
	var perVolWeightUnitCheck = false;
	var perAmountCheck = false;
	var vocUpperCheck = false;
	var vocCheck = false;
	var vocCheckString = "";
	var vocLwesUpperCheck = false;
	var vocLwesCheckString = "";
	var vocLwesCheck = false;
	var densityUpperCheck = false;
	var densityCheckString = "";
	var densityCheck = false;
	var oneRowSelected = false;
	var perGTZero = false;
	var sizeUnitCheck = false;
	
	var beanGridSize = beanGrid.getRowsNum();
	for(var i = 1; i <= beanGridSize;i++) 
	{
	   var currSpanStart = beanGrid.haasGetRowSpanStart(i) + 1;
	   okDoUpdate = $('okDoUpdate'+currSpanStart); 
	   if(okDoUpdate && okDoUpdate.checked)
	   {
		    oneRowSelected = true;
		    var currSpanEnd = beanGrid.haasGetRowSpanEndIndex(i);
			if(currSpanEnd == i)
			{
					var spanVoc = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("voc")).getValue().trim();
					var spanVocUpper = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocUpper")).getValue().trim();
				
					if(spanVoc != "")
						{
							if(isNaN(spanVoc) || spanVoc < 0)
								vocCheckString += 'Please enter a postive number for all Voc values selected\n\n';
							var vocDetect = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocDetect")).getValue().trim();
							if(!vocCheck && vocDetect == "")
								vocCheckString += 'Please enter Voc detects for all Voc values selected\n\n';
						
							var vocUnit = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocUnit")).getValue().trim();
							if(!vocCheck && vocUnit == "")
								vocCheckString += 'Please enter Voc units for all Voc values selected\n\n';
							
							var vocSource = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocSource")).getValue().trim();
							if(!vocCheck && vocSource == "")
								vocCheckString +=  'Please enter Voc sources for all Voc values selected\n\n';
							
							if(!vocCheck && vocCheckString != "")
								{
									vocCheck = true;
									completeErrorMsg += vocCheckString;
								}
						}
					if(!vocUpperCheck && spanVocUpper != "")
					{
						
						var vocDetect = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocDetect")).getValue().trim();
						if(vocDetect != "=")
							completeErrorMsg += 'Voc Lwes Detect must be set to = when Voc and Voc Upper values are entered\n\n';
	
						if(isNaN(spanVocUpper) || spanVocUpper < 0)
							completeErrorMsg += 'Please enter a postive number for all Voc Upper values selected\n\n';
						else if(spanVoc == "")
							completeErrorMsg += 'Please enter Voc values for all Voc Upper values selected\n\n';
						else if(spanVocUpper < spanVoc) 
							completeErrorMsg += 'Voc values must be less that Voc Upper values\n\n';
						
						vocUpperCheck = true;
					}
					
					var spanVocLwes = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocLwes")).getValue().trim();
					var spanVocLwesUpper = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocLwesUpper")).getValue().trim();
					
					if(spanVocLwes != "")
						{
							if(isNaN(spanVocLwes) || spanVocLwes < 0)
								vocLwesCheckString += 'Please enter a postive number for all Voc Lwes values selected\n\n';
							
							var vocLwesDetect = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocLwesDetect")).getValue().trim();
							if(!vocLwesCheck && vocLwesDetect == "")
								vocLwesCheckString += 'Please enter Voc Lwes detects for all Voc Lwes values selected\n\n';
						
							var vocLwesUnit = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocLwesUnit")).getValue().trim();
							if(!vocLwesCheck && vocLwesUnit == "")
								vocLwesCheckString += 'Please enter Voc Lwes units for all Voc Lwes values selected\n\n';
							
							var vocLwesSource = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocLwesSource")).getValue().trim();
							if(!vocLwesCheck && vocLwesSource == "")
								vocLwesCheckString += 'Please enter Voc Lwes sources for all Voc Lwes values selected\n\n';
							
							if(!vocLwesCheck && vocLwesCheckString != "")
							{
								vocLwesCheck = true;
								completeErrorMsg += vocLwesCheckString;
							}
						}
					if(!vocLwesUpperCheck && spanVocLwesUpper != "" )
					{
					
						var vocLwesDetect = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("vocLwesDetect")).getValue().trim();
						if(vocLwesDetect != "=")
							completeErrorMsg += 'Voc Lwes Detect must be set to = when Voc Lwes and Voc Lwes Upper values are entered\n\n';
						
						if(isNaN(spanVocLwesUpper) || spanVocLwesUpper < 0)
							completeErrorMsg += 'Please enter a postive number for all Voc Lwes Upper values selected\n\n';
						else if(spanVocLwes == "")
							completeErrorMsg += 'Please enter Voc Lwes values for all Voc Lwes Upper values selected\n\n';
						else if(spanVocLwesUpper < spanVocLwes) 
							completeErrorMsg += 'Voc Lwes values must be less that Voc Lwes Upper values\n\n';
						
						vocLwesUpperCheck = true;
				
					}
					
					/*var spanDensity = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("density")).getValue().trim();
					var spanDensityUpper = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("densityUpper")).getValue().trim();
					
					if(spanDensity != "")
						{
							if(isNaN(spanDensity) || spanDensity < 0)
								densityCheckString += 'Please enter a postive number for all Density values selected\n\n';
							
							var densityDetect = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("densityDetect")).getValue().trim();
							if(!densityCheck && densityDetect == "")
								densityCheckString += 'Please enter Density detects for all Density values selected\n\n';
						
							var densityUnit = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("densityUnit")).getValue().trim();
							if(!densityCheck && densityUnit == "")
								densityCheckString += 'Please enter Density units for all Density values selected\n\n';
							
							var densitySource = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("densitySource")).getValue().trim();
							if(!densityCheck && densitySource == "")
								densityCheckString += 'Please enter Density sources for all Density values selected\n\n';
							
							if(!densityCheck && densityCheckString != "")
							{
								densityCheck = true;
								completeErrorMsg += densityCheckString;
							}
						}
					if(!densityUpperCheck && spanDensityUpper != "" )
					{
					
						var densityDetect = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("densityDetect")).getValue().trim();
						if(densityDetect != "=")
							completeErrorMsg += 'Density Detect must be set to = when Density and Density Upper values are entered\n\n';
						
						if(isNaN(spanDensityUpper) || spanDensityUpper < 0)
							completeErrorMsg += 'Please enter a postive number for all Density Upper values selected\n\n';
						else if(spanDensity == "")
							completeErrorMsg += 'Please enter Density values for all Density Upper values selected\n\n';
						else if(spanDensityUpper < spanDensity) 
							completeErrorMsg += 'Density values must be less that Density Upper values\n\n';
						
						densityUpperCheck = true;
				
					}*/
					
					var physicalState = beanGrid.cells(currSpanStart,beanGrid.getColIndexById("physicalState")).getValue().trim();
					if(physicalState == '')
						completeErrorMsg += 'Please enter a Physical State for selected\n\n';
					if(beanGrid.cells(i,beanGrid.getColIndexById("amountPermission")).getValue() == "Y")
					{
						var sizeUnitVal =	beanGrid.cells(i,beanGrid.getColIndexById("sizeUnit")).getValue().trim();
						if(!sizeUnitCheck && sizeUnitVal == '')
							{
								sizeUnitCheck = true;
								completeErrorMsg += "Please enter Mix Ratio Units for each kit\n\n";
							}
							
						  
						var tmpPartSize = beanGrid.cells(i,beanGrid.getColIndexById("amount")).getValue().trim();
						  if(!perGTZero && (tmpPartSize == "" || isNaN(tmpPartSize) || tmpPartSize <= 0))
								  {
								  	completeErrorMsg += 'Please enter percent amounts greater than zero\n\n';
								  	perGTZero = true;
								  }
							  else
								  percentAmount += parseFloat(tmpPartSize);
						 /*else if(!perAmountCheck)
							  {
								perAmountCheck = true;
							  	completeErrorMsg += "Please enter Mix Ratio Amounts that equal 100% for each kit\n\n";
							  }*/
	
						if(!perVolWeightUnitCheck && percentAmount != 100)
							{
								perVolWeightUnitCheck = true;
								completeErrorMsg += messagesData.percentVolWeight+"\n\n";
							}

						percentAmount = 0;
					}
				}
				else if(beanGrid.cells(i,beanGrid.getColIndexById("amountPermission")).getValue() == "Y")
				{
					/*var sizeUnitVal =	beanGrid.cells(i,beanGrid.getColIndexById("sizeUnit")).getValue().trim();
					if(sizeUnitVal == '%(v/v)')
						  ++requirePercentVolUnitCountCheck;
					  else if( sizeUnitVal == '%(w/w)')
						  ++requirePercentWtUnitCountCheck;*/
					  var tmpPartSize = beanGrid.cells(i,beanGrid.getColIndexById("amount")).getValue().trim();
					  if(!perGTZero && (tmpPartSize == "" || isNaN(tmpPartSize) || tmpPartSize <= 0))
					  {
						  	completeErrorMsg += 'Please enter percent amounts greater than zero\n\n';
						  	perGTZero = true;
					  }
					  else
						  percentAmount += parseFloat(tmpPartSize);
					 /*else if(!perAmountCheck)
						  {
						  	perAmountCheck = true;
						  	completeErrorMsg += "Please enter Mix Ratio Amounts that equal 100% for each kit\n\n";
						  }*/
				}
		}
	}
	   if(!oneRowSelected)
		   completeErrorMsg += messagesData.pleasemakeselection;
			   
	   if(completeErrorMsg != "")
	   {
		alert(completeErrorMsg);
	   	return false;
	   }
	   else
		   return true;
}

/*function bindManufacturer(mfgDrop,rowNum)
{
    if (mfgDrop.removeEventListener) {
    	mfgDrop.removeEventListener("onfocus", bindManufacturer, false);
    } else if (mfgDrop.detachEvent) {
    	mfgDrop.detachEvent("onfocus", bindManufacturer);
    } else {
    	mfgDrop["onfocus"] = null;
    }
	mfgDrop["onfocus"] = null;


			j$("#"+mfgDrop.id).autocomplete("kitmanagementresults.do",{
					extraParams: {uAction: function() { return "mfgSearch"; },
								  mixtureMfg: function() { return mfgDrop.value; } },
					width: 200,
					max: 100,  // default value is 10
					cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
					scrollHeight: 200
			}).result(function (event, data, formatted) {
				mfgDrop.className = "inputBox"; 
				setCellValue(rowNum, 'mixtureMfg', data);
		    });
			
			j$('#'+mfgDrop.id).bind('keyup',(function(e) {
				  var keyCode = (e.keyCode ? e.keyCode : e.which);
		
				  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
				  	invalidateRequestor(mfgDrop);
			}));
}

function invalidateRequestor(mfgDropId)
{
 var requestorName  =  document.getElementById(mfgDropId.);
 var requestorId  =  document.getElementById(mfgDropId);
 if (mfgDropId.value.length == 0) {
	 mfgDropId.className = "inputBox";
 }else {
	 mfgDropId.className = "inputBox red";
 }
 //set to empty
// requestorId.value = "";
}*/

function handleRightClick(e)
{
	 j$.ajax({
	     type: "POST",
	     url: "kitmanagementresults.do",
	   data: {
			   uAction:'getRevHist',
			   mixtureId: beanGrid.cells(e,beanGrid.getColIndexById("mixtureId")).getValue()
			  },
	     success: rcCallBack
	   });
}

function rcCallBack(res)
{
	var results = jQuery.parseJSON(res);
	if(results.Message != null && results.Message != undefined) 
		 alert(results.Message);
	else
	{
		var summItems = new Array();
		var histItems = new Array();
		mrdr = results.mixtureRevisionDateResults;
		for(var i = 0; i < mrdr.length; i++)	{
			mrd = mrdr[i].mixtureRevisionDateDisplay;
			if(mrdr[i].dataSource == 'summary')
				summItems[summItems.length] = "text="+messagesData.revision+": "+mrd+";url=javascript:rightClickOptions(3,'"+mrdr[i].mixtureRevisionDate+"');";
			else
				histItems[histItems.length] = "text="+messagesData.revision+": "+mrd+";url=javascript:rightClickOptions(2,'"+mrd+"');";
		}
		if(summItems.length == 0)
			summItems[summItems.length] = "text=<font color='gray'>"+messagesData.nodatafound+"</font>;url=javascript:doNothing();";
		if(histItems.length == 0)
			histItems[histItems.length] = "text=<font color='gray'>"+messagesData.nodatafound+"</font>;url=javascript:doNothing();";

		replaceMenu("historyDates",histItems);
	    replaceMenu('summaryDates',summItems);
		replaceMenu('rightClickMenu',vitems);
		toggleContextMenu('rightClickMenu');
	}
}

//all same level, at least one item
function replaceMenu(menuname,menus){
	  var i = mm_returnMenuItemCount(menuname);
	  
	  for(;i > 1; i-- )
			mm_deleteItem(menuname,i);

	  for( i = 0; i < menus.length; ){
 		var str = menus[i];

 		i++;
		mm_insertItem(menuname,i,str);
		// delete original first item.
    	if( i == 1 ) mm_deleteItem(menuname,1);
      }
}

function rightClickOptions(val,date)
{
	var mainChildren = window.parent.children;
	switch(val)
	{
		case 1:
			url = "kitmanagementresults.do?uAction=revHist&mixtureId="+beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("mixtureId")).getValue()+"&maxData=100&customerMixtureNumber="
			+beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("customerMixtureNumber")).getValue();
			mainChildren[mainChildren.length] = openWinGeneric(url,"revisionHistory","1600","600","yes","100","100");
		break;
		case 2:
			url = "kitmanagementresults.do?uAction=editHist&mixtureId="+beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("mixtureId")).getValue()
			+"&maxData=100&mixtureRevisionDate="+date+"&customerMixtureNumber="
			+beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("customerMixtureNumber")).getValue();
			mainChildren[mainChildren.length] = openWinGeneric(url,"editHistory","1600","600","yes","100","100");
		break;
		case 3:
			reportLoc = "/HaasReports/report/printConfigurableReport.do"+
            "?pr_companyId="+$v('companyId')+
            "&pr_custMsdsDb="+encodeURIComponent(beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("customerMsdsDb")).getValue())+
			"&pr_custMsdsNo="+ beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("customerMixtureNumber")).getValue()+
			"&pr_revisionDate="+ encodeURIComponent(date)+
			"&rpt_ReportBeanId=MSDSKitReportDefinition";
			mainChildren[mainChildren.length] = openWinGeneric(reportLoc,"viewKitMsds","800","550","yes","100","100");
		break;
		case 4:
			mainChildren[mainChildren.length] = openWinGeneric('showmsdsdocuments.do?showDocuments=Yes&calledFrom=kitManagement&mixtureRevisionDate='+beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("mixtureRevisionDate")).getValue()
		    		+'&mixtureId='+beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("mixtureId")).getValue()
		    		+'&customerMixtureNumber='+beanGrid.cells(beanGrid.getSelectedRowId(),beanGrid.getColIndexById("customerMixtureNumber")).getValue()+'&documentTypeSource=Mixture',"showMsdsDocuments","800","350",'yes' );
		break;
	}
}


