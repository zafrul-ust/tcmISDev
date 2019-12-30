
function setDropDowns()
{
	buildDropDownNew(opshubig,'',"opsEntityId");
	//buildDropDownNew(altCompany,'',"companyId");
    if(defaultOpsEntityId != null && defaultOpsEntityId.length > 0){
    	$('opsEntityId').value = defaultOpsEntityId;
    }
}

function buildDropDownNew( arr, defaultObj, eleName ) {
	   var obj = $(eleName);
	   for (var i = obj.length; i > 0;i--)
	     obj[i] = null;
	  // if size = 1 or 2 show last one, first one is all, second one is the only choice.
	  if( arr == null || arr.length == 0 ) 
		  setOption(0,"","", eleName); 
	  else if( arr.length == 1 )
		  setOption(0,arr[0].name,arr[0].id, eleName);
	  else {
	      var start = 0;	  	  
		  for ( var i=0; i < arr.length; i++) {
		    	setOption(start++,arr[i].name,arr[i].id,eleName);
		  }
	  }
	  obj.selectedIndex = 0;
}

function populateGrids(data) {
	if (approvalChainData && approvalChainData.rows && approvalChainData.rows.length > 0) {
		initGridWithGlobal(approvalChainConfig);
	}
	
	if (approverListData && approverListData.rows && approverListData.rows.length > 0) {
		initGridWithGlobal(approverListConfig);		
	}
}

function updateBuyerLimit() {
	showPleaseWait();
	$("uAction").value = "update";
	document.genericForm.submit();
}

function addApprover() {
	showPleaseWait();
	$("uAction").value = "addToList";
	approverListGrid.parentFormOnSubmit();
	document.genericForm.submit();
}

function removeApprover() {
	showPleaseWait();
	$("uAction").value = "removeFromList";
	approvalChainGrid.parentFormOnSubmit();
	document.genericForm.submit();
}

function showPleaseWait() {
	$("resultGridsDiv").style.display = "none";
	$("transitPage").style.display = "";
}


j$().ready(function() {
	var _unlimited = 1000000000000000;
	
	function log(event, data, formatted) {
		var split = formatted.split(":");
		j$('#buyerPersonnelId').val(split[0]);
		var limit = split[2];
		if (limit == "null") {
			j$('#buyerLimit').val("0");
		}
		else if (limit == _unlimited) {
			j$('#unlimited').prop("checked", true);
			j$('#unlimited').trigger("change");
		}
		else {
			if (j$('#unlimited').is(":checked")) {
				j$('#unlimited').prop("checked", false);
				j$('#unlimited').trigger("change");
			}
			j$('#buyerLimit').val(limit);
			j$("#savedBuyerLimit").val(limit);
		}
		j$("#homeCurrencyDisp").html(split[3]);
		j$("#homeCurrencyId").val(split[3]);
		$("buyerName").className = "inputBox"; 
	} 

	j$("#buyerName").autocomplete("poapprovermain.do",{
		extraParams: {opsEntityId: function() { return j$('#opsEntityId').val(); },
					  companyId: function() { return j$("#companyId").val(); },
					  uAction: function() { return "buyerSearch";}},
		width: 200,
		max: 10,  // default value is 10
		cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
		scrollHeight: 200,
		formatItem: function(data, i, n, value) {
			return  value.split(":")[1].substring(0,40);
		},
		formatResult: function(data, value) {
			return value.split(":")[1];
		}
	});
	
	j$('#buyerName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);
	
		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateBuyer();
	}));
	
	j$("#opsEntityId").change(function() {
		var hasPerm = opsEntityPerms[j$(this).val()];
		var disp = (hasPerm == 'N')?"none":"";
		j$("#updateBuyerLimit").css("display",disp);
	});
	
	j$("#buyerName").result(log).next().click(function() {
		j$(this).prev().search();
	});
	
	j$("#Search").click(function() {
		$("uAction").value = "search";
		$("buyerLimit").value = $v("savedBuyerLimit");
		document.genericForm.submit();
	});
	
	j$("#unlimited").change(function() {
		var buyerLimit = j$("#buyerLimit");
		if (this.checked) {
			buyerLimit.hide();
			buyerLimit.data("limit",buyerLimit.val());
			buyerLimit.val(_unlimited);
			j$("label:has(#unlimited)").css("color","black");
		}
		else {
			buyerLimit.show();
			buyerLimit.val(buyerLimit.data("limit"));
			j$("label:has(#unlimited)").css("color","darkgray");
		}
	});
});



function invalidateBuyer() {
	var buyerName  =  document.getElementById("buyerName");
	var buyerPersonnelId  =  document.getElementById("buyerPersonnelId");
	if (buyerName.value.length == 0) {
		buyerName.className = "inputBox";
	}else {
		buyerName.className = "inputBox red";
	}
	//set to empty
	buyerPersonnelId.value = "";
}