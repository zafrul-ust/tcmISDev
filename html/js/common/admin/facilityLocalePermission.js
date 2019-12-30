function createXSL() {
	return true;
}

function companyChanged() {
	var company = $("companyId");
	var selectedCompany = company.value;
	showFacility(selectedCompany,null);
}

function showFacility(selectedCompany, selectedInv) {
	var idArray = altFacilityId[selectedCompany];
	var nameArray = altFacilityName[selectedCompany];
	var selectI = 0;
	var inserted = 0;
	
	var inv = $("facilityId");
	// remove all facilities then repopulate
	while (inv.size > 0) {
		inv.remove(0);
	}
	if (nameArray != null) {
		for (i in nameArray) {
			if( nameArray.length != 2 || i != 0 ) {
				setOption(inserted,nameArray[i],idArray[i], "facilityId");
				// if a desired index was passed in, set it to be selected
				if (selectedInv == idArray[i]) {
					selectI = inserted;
				}
				inserted++;
			}
		}
	}
	if (inserted == 0) {
		setOption(inserted,messagesData.myfacilities,"","facilityId");
	}
	$("facilityId").selectedIndex = selectI;
}

function showCompany(oldCompany) {
	var idArray = altCompanyId;
	var nameArray = altCompanyName;
	var selectI = 0;
	var inserted = 0;
	
	if (nameArray.length == 1) {
		setOption(0,nameArray[0],idArray[0],"companyId");
	}
	else {
		for (i in nameArray) {
			setOption(inserted,nameArray[i],idArray[i],"companyId");
			// if there is a desired index, set it to be selected
			if (oldCompany == idArray[i]) {
				selectI = inserted;
			}
			inserted++;
		}
	}
	$("companyId").selectedIndex = selectI;
}

function setCompany() {
	var oldCompany = $("oldcompanyId").value;
	var oldinv = $("oldfacilityId").value;
	try {
		showCompany(oldCompany);
		showFacility($("companyId").value,oldinv);
	}
	catch(ex){}
}

function validateSearchForm() {
	var valid = false;
	if ($v("companyId").trim().length == 0) {
		alert(messagesData.pleaseselect.replace('{0}', messagesData.company));
	}
	else if ($v("facilityId").trim().length == 0) {
		alert(messagesData.pleaseselect.replace('{0}', messagesData.facility));
	}
	else {
		valid = true;
	}
	return valid;
}

function submitSearchForm() {
	var valid = validateSearchForm();
	if (valid) {
		document.getElementById('uAction').value="search";
	   	document.genericForm.target='resultFrame';
	   	parent.showPleaseWait();
	   	$("startSearchTime").value = new Date().getTime();
		//document.genericForm.submit();
	}
	return valid;
}

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value; 

	if (totalLines > 0)
	{
		initializeGrid(); 
		parent.document.getElementById("mainUpdateLinks").style["display"] = "";
		if(showUpdateLinks)
		{
			parent.document.getElementById("updateResultLink").style["display"] = "";
		}
		else
		{
			parent.document.getElementById("updateResultLink").style["display"] = "none";
		}
	}  
	else
	{
		parent.document.getElementById("mainUpdateLinks").style["display"] = "none";
	}
	
	/*This dislpays our standard footer message*/
	displayGridSearchDuration();

	/*It is important to call this after all the divs have been turned on or off.*/
	setResultFrameSize();
		
    try{
    parent.resetTimer();
   }
   catch (ex)
   {
   }
}