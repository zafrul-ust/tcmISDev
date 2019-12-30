
function lookupCategories() {
	var loc = "mfrnotificationmgmtmain.do?uAction=categoryLookup&selectedCategories="
		+ $v("selectedCategories");
	//mfrNotification.showWait(formatMessage(messagesData.waitingFor, messagesData.material));
	children[children.length] = openWinGeneric(loc,"categoryLookup","800","180","yes","50","50","20","20","no");
}

function clearCategories() {
	$("selectedCategories").value = "";
	$("selectedCategoriesDisp").value = "";
	$("selectedCategoriesDisp").title = "";
}

function categoriesChanged(categories, display) {
	$("selectedCategories").value = String(categories);
	$("selectedCategoriesDisp").value = String(display);
	$("selectedCategoriesDisp").title = String(display);
}

function validateForm() {
	var approvedWindow = $v("approvedWindow");
	if (approvedWindow.length == 0 || isNaN(approvedWindow)) {
		$("approvedWindow").value = 30;
	}
	
	var requestorName  =  document.getElementById("requesterName");
	var requestorId  =  document.getElementById("requester");
	if (requestorName.value.length == 0) {
		requestorName.className = "inputBox";
		//set to empty
		requestorId.value = "";
	}
	
	return true;
}

function submitSearchForm() {
	var now = new Date();
	document.getElementById("startSearchTime").value = now.getTime();   
	if(validateForm()) {
		$("uAction").value = "search";
		document.genericForm.target='resultFrame';	   
		showPleaseWait();	  
		return true;
	}
	return false;
}

function getPersonnel() {
	var searchText = "";
	var requestorName  =  document.getElementById("requestorName");
	if (requestorName != null) {
		var requestorId  =  document.getElementById("requestorId");
		if(requestorName.value.length > 0 && requestorId != null) {
			if(requestorId.value.length == 0) {
				searchText = requestorName.value;
			}
		}
	}
	loc = "searchpersonnelmain.do?displayElementId=requestorName&valueElementId=requestorId&searchText="+searchText;
	openWinGeneric(loc,"searchpersonnel","600","450","yes","50","50","no");
}

function invalidateRequestor() {
	var requestorName  =  document.getElementById("requesterName");
	var requestorId  =  document.getElementById("requester");
	if (requestorName.value.length == 0) {
		requestorName.className = "inputBox";
	}else {
		requestorName.className = "inputBox red";
	}
	//set to empty
	requestorId.value = "";
}

function addUserToEmailList() {
	loc = "addusertoemaillistmain.do?userGroupId=MfrNotification&levelOfControl=catalog";
	children[children.length] = openWinGeneric(loc,"addusertoemaillist","600","450","yes","50","50","no");
}

function changeSearchTypeOptions(selectedValue) {
	  var searchType = $('searchType');

	  for (var i = 0; i < searchType.length; i++) {
		    if(selectedValue == 'notificationId' || selectedValue == 'materialId' 
		    	|| selectedValue == 'mfrId' || selectedValue == 'itemId' || selectedValue == 'acquiredMfrId') {
		    	if (searchType[i].value == "is") {
		    		searchType[i].disabled = "";
		    		searchType.selectedIndex = i;
		    	}
		    	else {
		    		searchType[i].disabled = "disabled";
		    	}
		    }
		    else if (selectedValue == 'internalComments') {
		    	if (searchType[i].value == "is") {
		    		searchType[i].disabled = "disabled";
		    		searchType.selectedIndex = i+1;
		    	}
		    	else {
		    		searchType[i].disabled = "";
		    	}
		    }
		    else {
		    	searchType[i].disabled = "";
		    }
	  }
}

function setSearchWhat(searchWhatArray) {
	var obj = $("searchWhat");
	for ( var i = obj.length; i >= 0; i--)
		obj[i] = null;

	for ( var j = 0; j < searchWhatArray.length; j++) {
		setOption(j, searchWhatArray[j].text, searchWhatArray[j].id,
				"searchWhat");
		if (j == 0) {
			changeSearchTypeOptions(searchWhatArray[j].id);
		}
	}
}

function closeAllchildren() {
	try {
		for(var n=0;n<children.length;n++) {
			try {
				children[n].closeAllchildren(); 
			} catch(ex){}
			children[n].close();
		}
	} catch(ex){}
	children = new Array(); 
}

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#requester').val(formatted.split(":")[0]);
		$("requesterName").className = "inputBox"; 
	} 
	
	j$("#requesterName").autocomplete("/tcmIS/haas/getpersonneldata.do",{
			extraParams: {activeOnly: function() { return j$('#activeOnly').is(':checked'); },
						  companyId: function() { return j$("#companyId").val(); } },
			width: 200,
			max: 100,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 200,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[1].substring(0,40);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});
	
	j$('#requesterName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateRequestor();
	}));
	
	
	j$("#requesterName").result(log).next().click(function() {
		j$(this).prev().search();
	});
	
	j$("#createNewRequest").click(function() {
		try {
			j$.ajax({
				url:"mfrnotificationmgmtmain.do",
				cache:false,
				type:"GET",
				dataType:"text",
				data:{"uAction": "create"},
				success: function(data) {
					var loc = "/tcmIS/catalog/mfrnotificationmain.do?uAction=init&notificationId="+data;
					parent.parent.openIFrame("mfrnotification"+data,loc,"Manufacturer Notification " + data,"","N");
				}
			});
		}
		catch (ex) {
			children[children.length] = openWinGeneric(loc,"mfrnotification","700","500","yes","50","50","20","20","no");
		}
	});
	
	setSearchWhat(searchArray);
});