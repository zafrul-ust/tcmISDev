
function validateMsdsSearch()
{
    var result = true;
    var errorMsg = "";
    var specificErrorMsg = "";

	 var ph = $v("ph");
	    if (!isFloat(ph,true) || (ph.length!= 0 && ph > 14*1) || (ph.length!= 0 &&ph < 0*1))
	    {
	        errorMsg += "\n"+messagesDataTemplate.ph;
	        result = false;
	    }

	    var flashPoint = $v("flashPoint");
	    if (!isFloat(flashPoint,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.flashpoint;
	        result = false;
	    }

	    var vaporPressure = $v("vaporPressure");
	    if (!isFloat(vaporPressure,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.vaporpressure;
	        result = false;
	    }else if (vaporPressure != '' && $v("vaporPressureUnit").length == 0) {
                errorMsg += "\n"+messagesDataTemplate.vaporpressureunit;
	            result = false;
        }

	    var vocPercent = $v("vocPercent");
	    if (!isFloat(vocPercent,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.voc;
	        result = false;
	    }

	    var vocLwesPercent = $v("vocLwesPercent");
	    if (!isFloat(vocLwesPercent,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.vocLwesPercent;
	        result = false;
	    }

	    var solidsPercent = $v("solidsPercent");
	    if (!isFloat(solidsPercent,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.solids;
	        result = false;
	    }

	 	var health = $v("health");
	    if (!isInteger(health,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.nfpa+":"+messagesDataTemplate.health;
	        result = false;
	    }

	    var flammability = $v("flammability");
	    if (!isInteger(flammability,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.nfpa+":"+messagesDataTemplate.flammability;
	        result = false;
	    }

	    var reactivity = $v("reactivity");
	    if (!isInteger(reactivity,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.nfpa+":"+messagesDataTemplate.reactivity;
	        result = false;
	    }

	    var hmisHealth = $v("hmisHealth");
	    if (!isInteger(hmisHealth,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.hmis+":"+messagesDataTemplate.health;
	        result = false;
	    }

	    var hmisFlammability = $v("hmisFlammability");
	    if (!isInteger(hmisFlammability,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.hmis+":"+messagesDataTemplate.flammability;
	        result = false;
	    }

	    var hmisReactivity = $v("hmisReactivity");
	    if (!isInteger(hmisReactivity,true))
	    {
	        errorMsg += "\n"+messagesDataTemplate.hmis+":"+messagesDataTemplate.reactivity;
	        result = false;
	    }

	    var hazardDesc = $("specificHazard").options[$("specificHazard").selectedIndex].text;
	    $("specificHazardDesc").value = hazardDesc;
	    
	    var personalProtectionDesc = $("personalProtection").options[$("personalProtection").selectedIndex].text;
	    $("personalProtectionDesc").value = personalProtectionDesc;
	    
	    var matchTypeDesc = $("matchType").options[$("matchType").selectedIndex].text;
	    $("matchTypeDesc").value = matchTypeDesc;
	    
	    var searchFieldDesc = $("searchField").options[$("searchField").selectedIndex].text;
	    $("searchFieldDesc").value = searchFieldDesc;

	    if ($("manufacturer").className == "inputBox red")
	    {
	        errorMsg += "\n"+messagesDataTemplate.manufacturer;
	        result = false;
	    }

		 for(var i = 0; i < listBeanGrid.getRowsNum();i++)
		 {
			var pos = listBeanGrid.getRowId(i);
		 	listSelected[i] = gridCellValue(listBeanGrid,pos,'listId');
		 }

		if(result == false) {
	        if (specificErrorMsg.length > 0) {
	            if (errorMsg.length > 0) {
	                alert(specificErrorMsg+"\n"+messagesDataTemplate.validvalues+"\n"+errorMsg);
	            }else {
	                alert(specificErrorMsg);
	            }
	        }else {
	            alert(messagesDataTemplate.validvalues+"\n"+errorMsg);
	        }
	    }
	    return result;
}

/*todo I don't think this is use so I am blocking it out for now.
function popStorageAreas(reportType)
{

if($v("facilityId") == "All") {
	alert(messagesData.pleaseselect.replace('{0}',messagesData.facility));
	return false;
}

var idString = '';
var rowNum = storBeanGrid.getRowsNum();
if(rowNum > 0) {
	for (var p = 0; p < rowNum; p ++) {
		var pos = storBeanGrid.getRowId(p);
		if(idString == '')
			idString = gridCellValue(storBeanGrid,pos,"storageAreaId");
		else
			idString += "|"+gridCellValue(storBeanGrid,pos,"storageAreaId");
	}
}
else {
	idString = '';
}

var loc = "storagearea.do?reportType="+reportType+"&StorageAreaIdString="+idString;
if($v("facilityId") != 'All')
	loc += "&facilityId="+$v("facilityId");
if($v("areaId") != 'All')
	loc += "&areaId="+$v("areaId");
if($v("buildingId") != 'All')
	loc += "&buildingId="+$v("buildingId");
if($v("floorId") != 'All')
	loc += "&floorId="+$v("floorId");

children[children.length] = openWinGeneric(loc,'StorageArea',"720","550","yes","50","50","20","20","no");
}
*/

function showOrHideMsdsNoSpan() {	
		if($v("searchField") == 'item_id' || $v("searchField") == 'material_id' || $v("searchField") == 'customer_msds_number')
			$("matchType").value = 'in csv list';
		else
			$("matchType").value = 'like';
	}

function openMsdsNoTextArea(v)
{
	if(v == "create list")
	{
		children[children.length] = openWinGeneric("advancedmsdsviewermain.do?uAction=showtextarea","copypasteformattextarea","325","370","yes","50","50","20","20","no");
		$('matchType').value = 'in csv list';
	}
}

function setStorageAreas(idString)
{
	var idArray=idString.split("|");

	// Set up the displayed report fields
	for(var i=storBeanGrid.getRowsNum();i >= 0;i--){
		storBeanGrid.deleteRow(i);
	}

	var storageAreaIdString = "";
	var storageAreaDescString = "";
	for ( var i=0; i < idArray.length; i++) {
  		var fieldInfo = idArray[i].split(";");
  		var j = 0;
  		addedRowId = storBeanGrid.getRowsNum();
		var newData = new Array();
		newData[j++] = fieldInfo[1];
		newData[j++] = fieldInfo[0];
		newData[j++] = fieldInfo[2];
		newData[j++] = fieldInfo[3];
		newData[j++] = fieldInfo[4];
		newData[j++] = fieldInfo[5];
	    var thisrow = storBeanGrid.addRow(addedRowId,newData,addedRowId);

	    storageAreaIdString += "|" + fieldInfo[0];
	    storageAreaDescString += fieldInfo[1] + '&@#';

	}

	$("storageAreaId").value = storageAreaIdString.substring(1);
	$("storageAreaDesc").value = storageAreaDescString;
}
