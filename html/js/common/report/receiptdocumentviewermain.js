function resetDropdown(elementSel, elementName, elementId) {
    elementSel.style['display'] = '';
    elementName.style['display'] = 'none';
    elementId.value = "";
}

function sortMyObjectArray(arr) {
    arr.sort(function(a,b){
        var firstArrayText = a.text.toLowerCase();
        var secondeArrayText = b.text.toLowerCase();
        if (firstArrayText < secondeArrayText){return -1;}
        if (firstArrayText > secondeArrayText){return 1;}
        return 0;
    });
}

function removingDuplicate(arr) {
    var filteredArray = new Array();
    var seenBefore = new Array();
    for (var i = 0; i < arr.length; i++) {
        if (seenBefore[arr[i].value]) continue;
        seenBefore[arr[i].value] = true;
        filteredArray.push(arr[i]);
    }
    return filteredArray;
}

function multiSelRet(retArr,which)
{
    if(retArr.length < 2)
    {
        if(which == 'facilityGroupId')
        {
            $('facilityGroupName').style['display'] = 'none';
            var facilityGroupIdSel = $("facilityGroupIdSel");
            facilityGroupIdSel.style['display'] = '';
            if(retArr.length > 0)
            {
                var selected = retArr[0].split('#^%&*!$@')[1];
                var options = facilityGroupIdSel.options;
                for(var i = 0; i < options.length; i++)
                    if(options[i].value == selected)
                    {
                        facilityGroupIdSel.selectedIndex = i;
                        break;
                    }
            }
            else
                facilityGroupIdSel.selectedIndex = 0;
            $('facilityGroupId').value = "";
            facilityGroupIdSelChanged();
        }
        else if(which == 'facilityId')
        {
            $('facilityName').style['display'] = 'none';
            var facilityIdSel = $("facilityIdSel");
            facilityIdSel.style['display'] = '';
            if(retArr.length > 0)
            {
                var selected = retArr[0].split('#^%&*!$@')[1];
                var options = facilityIdSel.options;
                for(var i = 0; i < options.length; i++)
                    if(options[i].value == selected)
                    {
                        facilityIdSel.selectedIndex = i;
                        break;
                    }
            }
            else
                facilityIdSel.selectedIndex = 0;
            $('facilityId').value = "";
            facilityIdSelChanged();
        }
    }
    else{
        if(which == 'facilityGroupId')
        {
            $('facilityGroupIdSel').style['display'] = 'none';
            var facilityGroupName = $('facilityGroupName');
            facilityGroupName.style['display'] = '';
            var facilityGroupDisplay = '';
            var facilityGroupIds = '';
            var facilityGroupTooltipDisplay = '';
            for(var i = 0; i < retArr.length; i++)
            {
                var selectedArr = retArr[i].split('#^%&*!$@');
                facilityGroupDisplay += selectedArr[0];
                facilityGroupTooltipDisplay += selectedArr[0];
                if(i != retArr.length - 1) {
                    facilityGroupDisplay += "; ";
                    facilityGroupTooltipDisplay += "\n";
                }
                facilityGroupIds += selectedArr[1];
                if(i != retArr.length - 1)
                    facilityGroupIds += "|";
            }
            facilityGroupName.value = facilityGroupDisplay;
            facilityGroupName.title = facilityGroupTooltipDisplay;
            $('facilityGroupId').value = facilityGroupIds;
            facilityGroupIdSelChanged();
        }
        else if(which == 'facilityId')
        {
            $('facilityIdSel').style['display'] = 'none';
            var facilityName = $('facilityName');
            facilityName.style['display'] = '';
            var facilityDisplay = '';
            var facilityIds = '';
            var facilityTooltipDisplay = '';
            for(var i = 0; i < retArr.length; i++)
            {
                var selectedArr = retArr[i].split('#^%&*!$@');
                facilityDisplay += selectedArr[0];
                facilityTooltipDisplay += selectedArr[0];
                if(i != retArr.length - 1) {
                    facilityDisplay += "; ";
                    facilityTooltipDisplay += "\n";
                }
                facilityIds += selectedArr[1];
                if(i != retArr.length - 1)
                    facilityIds += "|";
            }
            facilityName.value = facilityDisplay;
            facilityName.title = facilityTooltipDisplay;
            $('facilityId').value = facilityIds;
            facilityIdSelChanged();
        }
    }
}

var children = new Array();
function closeAllchildren() {
    try {
        for ( var n = 0; n < children.length; n++) {
            try {
                children[n].closeAllchildren();
            }
            catch (ex) {
            }
            children[n].close();
        }
    }
    catch (ex) {
    }
    children = new Array();
}

function createElement(which,id)
{
    var input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", which + id);
    var span = document.getElementById(which + 'Span');
    span.appendChild(input);
}

function clearElements(which)
{
    var span = document.getElementById(which + 'Span');
    span.innerHTML = '';
}

function popMultiSel(dropDownId)
{
    children[children.length] = openWinGeneric("receiptdocumentviewerresults.do?uAction=multiSelect&dropDownId="+dropDownId,"edit_field_list","800","500","yes");
    showTransitWin();
}

function buildDropDown( arr, eleName ) {
    var obj = $(eleName);
    for (var i = obj.length; i > 0;i--)
        obj[i] = null;

    var start = 0;
    var defaultIndex = 0;
    var myDefaultFacilityId = '';
    if( arr == null || arr.length != 1)  {
        if (eleName == 'facilityIdSel') {
            myDefaultFacilityId = $v("myDefaultFacilityId");
            if (altFacilityGroup.length > 0)
                setOption(start++, messagesData.myFacilities, '', eleName);
        }
        if (eleName == 'applicationId')
            setOption(start++,messagesData.myWorkAreas,'', eleName);

        if( arr != null) {
            for (var i = 0; i < arr.length; i++) {
                if (eleName == 'facilityIdSel') {
                    if (arr[i].value == myDefaultFacilityId)
                        defaultIndex = start;
                }
                setOption(start++, arr[i].text, arr[i].value, eleName);
            }
        }
    }else
        setOption(0, arr[0].text, arr[0].value, eleName);

    obj.selectedIndex = defaultIndex;
}

function loadDropdown() {
    if (altFacilityGroup.length > 0)
	    buildDropDown(altFacilityGroup,"facilityGroupIdSel");
    facilityGroupIdSelChanged();
} //end of method



function facilityGroupIdSelChanged() {
    var idArray = new Array();
    if (altFacilityGroup.length > 0) {
        if ($v('facilityGroupId').length > 1) {
            var facilityGroupArr = $v('facilityGroupId').split('|');
            for (var i = 0; i < facilityGroupArr.length; i++) {
                var newIdArray = altFacilityGroupFacility[facilityGroupArr[i]];
                idArray = idArray.concat(newIdArray);
            }
            sortMyObjectArray(idArray);
            idArray = removingDuplicate(idArray);
        } else
            idArray = altFacilityGroupFacility[$v("facilityGroupIdSel")];
        resetDropdown($("facilityIdSel"),$("facilityName"),$("facilityId"));
    }else
        idArray = altFacility;
    buildDropDown(idArray,"facilityIdSel");
    facilityIdSelChanged();
} //end of method

function facilityIdSelChanged() {
    var idArray = new Array();
    if ($v('facilityId').length > 1) {
        var facilityArr = $v('facilityId').split('|');
        for(var i = 0; i < facilityArr.length; i++) {
            var newIdArray = altApplication[facilityArr[i]];
            idArray = idArray.concat(newIdArray);
        }
        sortMyObjectArray(idArray);
        idArray = removingDuplicate(idArray);
    }else
        idArray = altApplication[$v("facilityIdSel")];
    buildDropDown(idArray,"applicationId");
    setSearchBy();
} //end of method

function validateForm()
{
	var searchText = document.getElementById("searchText");
	  if (isWhitespace(searchText.value) && $v("deliveryToDate").length == 0 && $v("deliveryFromDate").length == 0
			  && $v("expireToDate").length == 0 && $v("expireFromDate").length == 0) 
	  {
	       alert(messagesData.missingSearchText);
	       return false;
	  }
	  
	return true;
}


function submitForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();
  if(validateForm()) { 
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   document.genericForm.submit();
  }
}

function generateExcel() 
{
	var flag = validateForm();
	if(flag) 
	{	
	 $('uAction').value = 'createExcel';
	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"_ReceiptDocumentViewGenerateExcel","650","600","yes");             
	 document.genericForm.target='_ReceiptDocumentViewGenerateExcel';
	 var a = window.setTimeout("document.genericForm.submit();",500);
	}	
}

var dhxWins = null;
function initializeDhxWins() {
	if (dhxWins == null) {
		dhxWins = new dhtmlXWindows();
		dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showAccountInputDialog(accountSysArray)
{
	var inputDailogWin = document.getElementById("accountSysDailogWin");
	inputDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("showAccountInputDialog")) {
		// create window first time
		inputWin = dhxWins.createWindow("showAccountInputDialog",0,0, 400, 118);
		inputWin.setText(messagesData.accountsysteminputdialog);
		inputWin.clearIcon();  // hide icon
		inputWin.denyResize(); // deny resizing
		inputWin.denyPark();   // deny parking	
		setOption(0,messagesData.pleaseselect,"", "accountSystemSelectBox");

		for ( var i=0; i < accountSysArray.length; i++)
		{			
			setOption(i+1,accountSysArray[i].text,accountSysArray[i].value, "accountSystemSelectBox");

		}		
		inputWin.attachObject("accountSysDailogWin");
		inputWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		inputWin.center();	   
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		inputWin.setPosition(328, 131); 
		inputWin.button("close").hide();
		inputWin.button("minmax1").hide();
		inputWin.button("park").hide();
		inputWin.setModal(true);
	}
	else
	{
		// just show
		inputWin.setModal(true);
		dhxWins.window("showAccountInputDialog").show();
	}
}

function showTransitWin()
{
	document.getElementById("transitLabel").innerHTML = messagesData.pleasewait;

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxWins.window("transitDailogWin").show();
	}
}

function closeTransitWin() {
	if (dhxWins != null) {
		if (dhxWins.window("transitDailogWin")) {
			dhxWins.window("transitDailogWin").setModal(false);
			dhxWins.window("transitDailogWin").hide();
		}
	}
}



function accountSysOkClose()
{
	var selectedAccountSysId = document.getElementById("accountSystemSelectBox");	
	if((selectedAccountSysId.length>0) && (selectedAccountSysId.value!=""))
	{
	  resultFrame.$("accountSysId").value = selectedAccountSysId.value;
	  dhxWins.window("showAccountInputDialog").setModal(false); 	
	  dhxWins.window("showAccountInputDialog").hide();
	  resultFrame.popMovementofMaterial();
	}	
} //end of method

function setSearchBy()
{
    //remove PO LINE
    var searchBy = $('searchBy').options;
    if(searchBy[searchBy.length - 1].value == 'poLine')
        searchBy[searchBy.length - 1] = null;
    $("poRequired").value = "";
    //add PO LINE if needed
    var facPoRequired = null;
    if ($v('facilityId').length > 1) {
        var facilityArr = $v('facilityId').split('|');
        for(var i = 0; i < facilityArr.length; i++) {
            var tmpFacPoRequired = poRequiredFacsMap[facilityArr[i]];
            if(tmpFacPoRequired != null && tmpFacPoRequired != undefined) {
                facPoRequired = "p";
                break;
            }
        }
    }else
        facPoRequired = poRequiredFacsMap[$v('facilityIdSel')];

	if(facPoRequired != null && facPoRequired != undefined) {
        setOption($('searchBy').options.length, messagesData.poLine, "poLine", "searchBy");
        $("poRequired").value = "Y";
    }
}